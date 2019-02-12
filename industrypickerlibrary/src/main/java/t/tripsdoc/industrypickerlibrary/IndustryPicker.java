package t.tripsdoc.industrypickerlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import t.tripsdoc.industrypickerlibrary.language.in;
import t.tripsdoc.industrypickerlibrary.language.th;
import t.tripsdoc.industrypickerlibrary.listener.BottomSheetInteractionListener;
import t.tripsdoc.industrypickerlibrary.listener.OnIndustryPickerListener;
import t.tripsdoc.industrypickerlibrary.listener.OnItemClickListener;
import t.tripsdoc.industrypickerlibrary.language.en;

public class IndustryPicker implements BottomSheetInteractionListener{

    // Set Search
    public static final int SORT_BY_NONE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_ID = 2;
    public static final String LOCALE_DEFAULT = "en";

    private Context context;
    private String locale = LOCALE_DEFAULT;
    private int sortBy = SORT_BY_NAME;
    private OnIndustryPickerListener onIndustryPickerListener;
    private boolean canSearch = true;

    private List<Industry> industries;
    private List<Industry> searchResults;
    private EditText searchtext;
    private RecyclerView industriesRec;
    private RelativeLayout rootView;
    private IndustrPickerAdapter adapter;
    private AlertDialog alertDialog;
    private BottomSheetInteractionListener bottomSheetInteractionListener;
    private Dialog dialog;

    private IndustryPicker(){}

    IndustryPicker(Builder builder){
        sortBy = builder.sortBy;
        locale = builder.locale;
        if(builder.onIndustryPickerListener != null){
            onIndustryPickerListener = builder.onIndustryPickerListener;
        }
        context = builder.context;
        canSearch = builder.canSearch;
        switch (locale) {
            case "en":
                industries = new ArrayList<>(Arrays.asList(en.INDUSTRIES));
                break;
            case "in":
                industries = new ArrayList<>(Arrays.asList(in.INDUSTRIES));
                break;
            case "th":
                industries = new ArrayList<>(Arrays.asList(th.INDUSTRIES));
                break;
            default:
                industries = new ArrayList<>(Arrays.asList(en.INDUSTRIES));
                break;
        }
        sortIndustries(industries);
    }

    private void sortIndustries(@NonNull List<Industry> industries) {
        if (sortBy == SORT_BY_NAME) {
            Collections.sort(industries, new Comparator<Industry>() {
                @Override
                public int compare(Industry industry1, Industry industry2) {
                    return industry1.getName().compareTo(industry2.getName());
                }
            });
        }
        else if (sortBy == SORT_BY_ID) {
            Collections.sort(industries, new Comparator<Industry>() {
                @Override
                public int compare(Industry industry1, Industry industry2) {
                    Integer data1 = Integer.parseInt(industry1.getId().toString());
                    Integer data2 = Integer.parseInt(industry2.getId().toString());
                    return data1.compareTo(data2);
                }
            });
        }
    }

    public void showDialog(@NonNull Activity activity){
        if(industries == null || industries.isEmpty()){
            throw new IllegalArgumentException("No Industry found");
        } else {
            dialog = new Dialog(activity);
            View dialogView = activity.getLayoutInflater().inflate(R.layout.content_industries, null);
            initiateUi(dialogView);
            setSearchEditText();
            setupRecyclerView(dialogView);
            dialog.setContentView(dialogView);
            if (dialog.getWindow() != null) {
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(params);
            }
            dialog.show();
        }
    }

    @Override public void setupRecyclerView(View sheetView) {
        searchResults = new ArrayList<>();
        searchResults.addAll(industries);
        adapter = new IndustrPickerAdapter(sheetView.getContext(), searchResults,
                new OnItemClickListener() {
                    @Override public void onItemClicked(Industry industry) {
                        if (onIndustryPickerListener != null) {
                            onIndustryPickerListener.onSelectIndustry(industry);
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            dialog = null;
                        }
                    }
                });
        industriesRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(sheetView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        industriesRec.setLayoutManager(layoutManager);
        industriesRec.setAdapter(adapter);
    }

    @Override public void setSearchEditText() {
        if (canSearch) {
            searchtext.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Intentionally Empty
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Intentionally Empty
                }

                @Override
                public void afterTextChanged(Editable searchQuery) {
                    search(searchQuery.toString());
                }
            });
            searchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    InputMethodManager imm = (InputMethodManager) searchtext.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(searchtext.getWindowToken(), 0);
                    }
                    return true;
                }
            });
        } else {
            searchtext.setVisibility(View.GONE);
        }
    }

    private void search(String searchQuery) {
        searchResults.clear();
        for (Industry industry : industries) {
            if (industry.getName().toLowerCase(Locale.ENGLISH).contains(searchQuery.toLowerCase())) {
                searchResults.add(industry);
            }
        }
        sortIndustries(searchResults);
        adapter.notifyDataSetChanged();
    }

    @Override public void initiateUi(View sheetView) {
        searchtext = sheetView.findViewById(R.id.search);
        industriesRec = sheetView.findViewById(R.id.recycleview);
        rootView = sheetView.findViewById(R.id.view_foreground);
    }

    public void setIndustries(@NonNull List<Industry> industries) {
        this.industries.clear();
        this.industries.addAll(industries);
        sortIndustries(this.industries);
    }

    public Industry getIndustryByName(String industryName){
        Collections.sort(industries, new NameComparator());
        Industry industry = new Industry();
        industry.setName(industryName);
        int i = Collections.binarySearch(industries, industry, new NameComparator());
        if(i < 0){
            return null;
        } else {
            return industries.get(i);
        }
    }

    public Industry getIndustryByID(String industryID){
        Collections.sort(industries, new IDComparator());
        Industry industry = new Industry();
        industry.setId(industryID);
        int i = Collections.binarySearch(industries, industry, new IDComparator());
        if(i < 0){
            return null;
        } else {
            return industries.get(i);
        }
    }

    public static class Builder {
        private Context context;
        private String locale;
        private int sortBy = SORT_BY_NONE;
        private boolean canSearch = true;
        private OnIndustryPickerListener onIndustryPickerListener;
        private int style;

        public Builder with(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public Builder style(@NonNull @StyleRes int style) {
            this.style = style;
            return this;
        }

        public Builder sortBy(@NonNull int sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder locale(@NonNull String locale) {
            this.locale = locale;
            return this;
        }

        public Builder listener(@NonNull OnIndustryPickerListener onIndustryPickerListener) {
            this.onIndustryPickerListener = onIndustryPickerListener;
            return this;
        }

        public Builder canSearch(@NonNull boolean canSearch) {
            this.canSearch = canSearch;
            return this;
        }

        public IndustryPicker build() {
            return new IndustryPicker(this);
        }
    }

    public static class IDComparator implements Comparator<Industry>{
        @Override
        public int compare(Industry industry, Industry nextIndustry) {
            Integer data1 = Integer.parseInt(industry.getId().toString());
            Integer data2 = Integer.parseInt(nextIndustry.getId().toString());
            return data1.compareTo(data2);
        }
    }

    public static class NameComparator implements Comparator<Industry> {
        @Override
        public int compare(Industry industry, Industry nextIndustry) {
            return industry.getName().compareTo(nextIndustry.getName());
            //return industry.getName().compareToIgnoreCase(nextIndustry.getName());
        }
    }
}
