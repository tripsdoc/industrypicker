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

import t.tripsdoc.industrypickerlibrary.listener.BottomSheetInteractionListener;
import t.tripsdoc.industrypickerlibrary.listener.OnIndustryPickerListener;
import t.tripsdoc.industrypickerlibrary.listener.OnItemClickListener;

public class IndustryPicker implements BottomSheetInteractionListener{
    private final Industry[] INDUSTRIES = {
            new Industry("1","Accounting"),
            new Industry("2","Airlines/Aviation"),
            new Industry("3","Alternative Dispute Resolution"),
            new Industry("4","Alternative Medicine"),
            new Industry("5","Animation"),
            new Industry("6","Apparel & Fashion"),
            new Industry("7","Architecture"),
            new Industry("8","Arts and Crafts"),
            new Industry("9","Automotive"),
            new Industry("10","Aviation & Aerospace"),

            new Industry("11","Banking"),
            new Industry("12","Biotechnology"),
            new Industry("13","Broadcast Media"),
            new Industry("14","Building Materials"),
            new Industry("15","Business Supplies and Equipment"),
            new Industry("16","Capital Markets"),
            new Industry("17","Chemicals"),
            new Industry("18","Civic & Social Organization"),
            new Industry("19","Civil Engineering"),
            new Industry("20","Commercial Real Estate"),

            new Industry("21","Computer & Network Security"),
            new Industry("22","Computer Games"),
            new Industry("23","Computer Hardware"),
            new Industry("24","Computer Networking"),
            new Industry("25","Computer Software"),
            new Industry("26","Construction"),
            new Industry("27","Consumer Electronics"),
            new Industry("28","Consumer Goods"),
            new Industry("29","Consumer Services"),
            new Industry("30","Cosmetics"),

            new Industry("31","Dairy"),
            new Industry("32","Defense & Space"),
            new Industry("33","Design"),
            new Industry("34","Education Management"),
            new Industry("35","E-Learning"),
            new Industry("36","Electrical/Electronic Manufacturing"),
            new Industry("37","Entertainment"),
            new Industry("38","Environmental Services"),
            new Industry("39","Events Services"),
            new Industry("40","Executive Office"),

            new Industry("41","Facilities Services"),
            new Industry("42","Farming"),
            new Industry("43","Financial Services"),
            new Industry("44","Fine Art"),
            new Industry("45","Fishery"),
            new Industry("46","Food & Beverages"),
            new Industry("47","Food Production"),
            new Industry("48","Fund-Raising"),
            new Industry("49","Furniture"),
            new Industry("50","Gambling"),

            new Industry("51","Glass, Ceramics & Concrete"),
            new Industry("52","Government Administration"),
            new Industry("53","Government Relations"),
            new Industry("54","Graphic Design"),
            new Industry("55","Health, Wellness and Fitness"),
            new Industry("56","Higher Education"),
            new Industry("57","Hospital & Health Care"),
            new Industry("58","Hospitality"),
            new Industry("59","Human Resources"),
            new Industry("60","Import and Export"),

            new Industry("61","Individual & Family Services"),
            new Industry("62","Industrial Automation"),
            new Industry("63","Information Services"),
            new Industry("64","Information Technology and Services"),
            new Industry("65","Insurance"),
            new Industry("66","International Affairs"),
            new Industry("67","International Trade and Development"),
            new Industry("68","Internet"),
            new Industry("69","Investment Banking"),
            new Industry("70","Investment Management"),

            new Industry("71","Judiciary"),
            new Industry("72","Law Enforcement"),
            new Industry("73","Law Practice"),
            new Industry("74","Legal Services"),
            new Industry("75","Legislative Office"),
            new Industry("76","Leisure, Travel & Tourism"),
            new Industry("77","Libraries"),
            new Industry("78","Logistics and Supply Chain"),
            new Industry("79","Luxury Goods & Jewelry"),
            new Industry("80","Machinery"),

            new Industry("81","Management Consulting"),
            new Industry("82","Maritime"),
            new Industry("83","Market Research"),
            new Industry("84","Marketing and Advertising"),
            new Industry("85","Mechanical or Industrial Engineering"),
            new Industry("86","Media Production"),
            new Industry("87","Medical Devices"),
            new Industry("88","Medical Practice"),
            new Industry("89","Mental Health Care"),
            new Industry("90","Military"),

            new Industry("91","Mining & Metals"),
            new Industry("92","Motion Pictures and Film"),
            new Industry("93","Museums and Institutions"),
            new Industry("94","Music"),
            new Industry("95","Nanotechnology"),
            new Industry("96","Newspapers"),
            new Industry("97","Non-Profit Organization Management"),
            new Industry("98","Oil & Energy"),
            new Industry("99","Online Media"),
            new Industry("100","Outsourcing/Offshoring"),

            new Industry("101","Package/Freight Delivery"),
            new Industry("102","Packaging and Containers"),
            new Industry("103","Paper & Forest Products"),
            new Industry("104","Performing Arts"),
            new Industry("105","Pharmaceuticals"),
            new Industry("106","Philanthropy"),
            new Industry("107","Photography"),
            new Industry("108","Plastics"),
            new Industry("109","Political Organization"),
            new Industry("110","Primary/Secondary Education"),

            new Industry("111","Printing"),
            new Industry("112","Professional Training & Coaching"),
            new Industry("113","Program Development"),
            new Industry("114","Public Policy"),
            new Industry("115","Public Relations and Communications"),
            new Industry("116","Public Safety"),
            new Industry("117","Publishing"),
            new Industry("118","Railroad Manufacture"),
            new Industry("119","Ranching"),
            new Industry("120","Real Estate"),

            new Industry("121","Recreational Facilities and Services"),
            new Industry("122","Religious Institutions"),
            new Industry("123","Renewables & Environment"),
            new Industry("124","Research"),
            new Industry("125","Restaurants"),
            new Industry("126","Retail"),
            new Industry("127","Security and Investigations"),
            new Industry("128","Semiconductors"),
            new Industry("129","Shipbuilding"),
            new Industry("130","Sporting Goods"),

            new Industry("131","Sports"),
            new Industry("132","Staffing and Recruiting"),
            new Industry("133","Supermarkets"),
            new Industry("134","Telecommunications"),
            new Industry("135","Textiles"),
            new Industry("136","Think Tanks"),
            new Industry("137","Tobacco"),
            new Industry("138","Translation and Localization"),
            new Industry("139","Transportation/Trucking/Railroad"),
            new Industry("140","Utilities"),

            new Industry("141","Venture Capital & Private Equity"),
            new Industry("142","Veterinary"),
            new Industry("143","Warehousing"),
            new Industry("144","Wholesale"),
            new Industry("145","Wine and Spirits"),
            new Industry("146","Wireless"),
            new Industry("147","Writing and Editing"),
    };

    // Set Search
    public static final int SORT_BY_NONE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_ID = 2;

    private Context context;
    private int sortBy = SORT_BY_NONE;
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
        if(builder.onIndustryPickerListener != null){
            onIndustryPickerListener = builder.onIndustryPickerListener;
        }
        context = builder.context;
        canSearch = builder.canSearch;
        industries = new ArrayList<>(Arrays.asList(INDUSTRIES));
        sortIndustries(industries);
    }

    private void sortIndustries(@NonNull List<Industry> industries) {
        if (sortBy == SORT_BY_NAME) {
            Collections.sort(industries, new Comparator<Industry>() {
                @Override
                public int compare(Industry industry1, Industry industry2) {
                    return industry1.getName().trim().compareToIgnoreCase(industry2.getName().trim());
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
            return industry.getName().compareToIgnoreCase(nextIndustry.getName());
        }
    }
}
