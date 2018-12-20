package t.tripsdoc.industrypickerlibrary;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import t.tripsdoc.industrypickerlibrary.listener.OnItemClickListener;

public class IndustrPickerAdapter extends RecyclerView.Adapter<IndustrPickerAdapter.IndustryPickerHolder> implements Filterable {

    private Context context;
    private List<Industry> industriesList;
    private List<Industry> industriesListFiltered;
    private OnItemClickListener listener;

    public class IndustryPickerHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public RelativeLayout viewforeground;

        public IndustryPickerHolder(View view) {
            super(view);
            viewforeground = view.findViewById(R.id.view_foreground);
            name = view.findViewById(R.id.industriesname);
        }
    }

    public IndustrPickerAdapter(Context context, List<Industry> industriesList, OnItemClickListener listener) {
        this.context = context;
        this.industriesList = industriesList;
        this.industriesListFiltered = industriesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IndustrPickerAdapter.IndustryPickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.industries_items, parent, false);
        return new IndustrPickerAdapter.IndustryPickerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustryPickerHolder holder, int position) {
        final Industry industries = industriesListFiltered.get(position);
        holder.name.setText(industries.getName());
        holder.viewforeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(industries);
            }
        });
    }

    @Override
    public int getItemCount() {
        return industriesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    industriesListFiltered = industriesList;
                } else {
                    List<Industry> filteredList = new ArrayList<>();
                    for (Industry row : industriesList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    industriesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = industriesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                industriesListFiltered = (ArrayList<Industry>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
