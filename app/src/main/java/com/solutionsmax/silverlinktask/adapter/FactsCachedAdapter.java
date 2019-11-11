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
import com.solutionsmax.silverlinktask.db.Facts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FactsCachedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Facts> listItems;

    public FactsCachedAdapter(Context context, List<Facts> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.facts_list_adapter, parent, false);
        return new FactsCachedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FactsCachedViewHolder factsCachedViewHolder = (FactsCachedViewHolder) holder;
        Facts facts = listItems.get(position);
        factsCachedViewHolder.lblTitle.setText(facts.getTitle());
        factsCachedViewHolder.lblDescription.setText(facts.getDescription());
        Glide.with(context).load(facts.getImageHref())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(factsCachedViewHolder.imgFacts);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class FactsCachedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lblDescription)
        TextView lblDescription;
        @BindView(R.id.lblTitle)
        TextView lblTitle;
        @BindView(R.id.imgFacts)
        CircleImageView imgFacts;

        public FactsCachedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
