package com.solutionsmax.silverlinktask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.solutionsmax.silverlinktask.adapter.FactsListAdapter;
import com.solutionsmax.silverlinktask.model.FactsListItem;
import com.solutionsmax.silverlinktask.view_model.FactsListViewModel;

import java.nio.file.attribute.FileAttribute;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.factsRecyclerView)
    RecyclerView factsRecyclerView;
    FactsListViewModel factsListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        ButterKnife.bind(this);
        factsRecyclerView.setLayoutManager(new LinearLayoutManager(FactsActivity.this));
        factsListViewModel = ViewModelProviders.of(this).get(FactsListViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        factsListViewModel.retrieveFactsList().observe(this, new Observer<List<FactsListItem>>() {
            @Override
            public void onChanged(List<FactsListItem> factsListItems) {
                factsRecyclerView.setAdapter(new FactsListAdapter(FactsActivity.this, factsListItems));
            }
        });
    }
}
