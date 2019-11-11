package com.solutionsmax.silverlinktask;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solutionsmax.silverlinktask.adapter.FactsCachedAdapter;
import com.solutionsmax.silverlinktask.adapter.FactsListAdapter;
import com.solutionsmax.silverlinktask.db.Facts;
import com.solutionsmax.silverlinktask.model.FactsListItem;
import com.solutionsmax.silverlinktask.util.ConnectivityReceiver;
import com.solutionsmax.silverlinktask.util.DialogUtils;
import com.solutionsmax.silverlinktask.view_model.FactsListViewModel;
import com.solutionsmax.silverlinktask.view_model.FactsRoomDBViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.factsRecyclerView)
    RecyclerView factsRecyclerView;
    FactsListViewModel factsListViewModel;

    FactsRoomDBViewModel factsRoomDBViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        ButterKnife.bind(this);
        //Check Network Connection
        checkConnection();

        factsRecyclerView.setLayoutManager(new LinearLayoutManager(FactsActivity.this));
        factsListViewModel = ViewModelProviders.of(this).get(FactsListViewModel.class);
        factsRoomDBViewModel = ViewModelProviders.of(this).get(FactsRoomDBViewModel.class);
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
        factsListViewModel.retrieveFactsList().observe(this, new Observer<List<FactsListItem>>() {
            @Override
            public void onChanged(List<FactsListItem> factsListItems) {
                factsRecyclerView.setAdapter(new FactsListAdapter(FactsActivity.this, factsListItems));
                //Save data to room db
                saveDataToCache(factsListItems);
            }
        });

        //Setting data to Toolbar
        factsListViewModel.toolbarTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                toolbar.setTitle(s);
            }
        });

        // Show contents from room db
        factsRoomDBViewModel.getCachedFacts().observe(this, new Observer<List<Facts>>() {
            @Override
            public void onChanged(List<Facts> facts) {
                for (int i = 0; i < facts.size(); i++) {
                    factsRecyclerView.setAdapter(new FactsCachedAdapter(FactsActivity.this, facts));
                }
            }
        });

    }

    private void saveDataToCache(List<FactsListItem> factsListItems) {
        for (int i = 0; i < factsListItems.size(); i++) {
            Facts facts = new Facts();
            facts.setTitle(factsListItems.get(i).getsTitle());
            facts.setDescription(factsListItems.get(i).getsDescription());
            facts.setImageHref(factsListItems.get(i).getsImageRef());
            factsRoomDBViewModel.insert(facts);
        }
        Log.d("Items Saved", "Saved");
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showNetworkDialog(isConnected);
    }

}
