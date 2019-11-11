package com.solutionsmax.silverlinktask;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solutionsmax.silverlinktask.adapter.FactsListAdapter;
import com.solutionsmax.silverlinktask.model.FactsListItem;
import com.solutionsmax.silverlinktask.util.ConnectivityReceiver;
import com.solutionsmax.silverlinktask.util.DialogUtils;
import com.solutionsmax.silverlinktask.view_model.FactsListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
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
        //Check Network Connection
        checkConnection();

        factsRecyclerView.setLayoutManager(new LinearLayoutManager(FactsActivity.this));
        factsListViewModel = ViewModelProviders.of(this).get(FactsListViewModel.class);
    }

    //Checking Network connection
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showNetworkDialog(isConnected);
    }

    //Show alert dialog if not connected to network
    private void showNetworkDialog(boolean isConnected) {
        if (!isConnected) {
            DialogUtils.showDialog(FactsActivity.this, "Not Connected to Internet");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Setting data to RecyclerView
        factsListViewModel.retrieveFactsList().observe(this, factsListItems ->
                factsRecyclerView.setAdapter(new FactsListAdapter(FactsActivity.this, factsListItems)));

        //Setting data to Toolbar
        factsListViewModel.toolbarTitle().observe(this, s ->
                toolbar.setTitle(s));
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showNetworkDialog(isConnected);
    }
}
