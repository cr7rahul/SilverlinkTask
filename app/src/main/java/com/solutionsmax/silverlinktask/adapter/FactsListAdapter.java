package com.solutionsmax.silverlinktask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.solutionsmax.silverlinktask.R;
import com.solutionsmax.silverlinktask.model.FactsListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FactsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FactsListItem> listItems;

    public FactsListAdapter(Context context, List<FactsListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.facts_list_adapter, parent, false);
        return new FactsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FactsListViewHolder factsListViewHolder = (FactsListViewHolder) holder;
        FactsListItem factsListItem = listItems.get(position);
        factsListViewHolder.lblTitle.setText(factsListItem.getsTitle());
        factsListViewHolder.lblDescription.setText(factsListItem.getsDescription());
        Glide.with(context).load(factsListItem.getsImageRef())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(factsListViewHolder.imgFacts);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class FactsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lblDescription)
        TextView lblDescription;
        @BindView(R.id.lblTitle)
        TextView lblTitle;
        @BindView(R.id.imgFacts)
        CircleImageView imgFacts;

        public FactsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
