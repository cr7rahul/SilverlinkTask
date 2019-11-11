package com.solutionsmax.silverlinktask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    @BindView(R.id.progressDialog)
    ProgressBar progressDialog;
    FactsListViewModel factsListViewModel;
    FactsListAdapter adapter;
    FactsCachedAdapter cachedAdapter;
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Not Connected to network");
            alertDialogBuilder.setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(getApplicationContext(), FactsActivity.class));
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            loadCachedData();
        }
    }

    //Load Cached Data when not connected to network
    private void loadCachedData() {
        // Show contents from room db
        factsRoomDBViewModel.getCachedFacts().observe(this, new Observer<List<Facts>>() {
            @Override
            public void onChanged(List<Facts> facts) {
                for (int i = 0; i < facts.size(); i++) {
                    cachedAdapter = new FactsCachedAdapter(FactsActivity.this, facts);
                    factsRecyclerView.setAdapter(cachedAdapter);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Setting data to RecyclerView
        progressDialog.setVisibility(View.VISIBLE);
        factsListViewModel.retrieveFactsList().observe(this, new Observer<List<FactsListItem>>() {
            @Override
            public void onChanged(List<FactsListItem> factsListItems) {
                progressDialog.setVisibility(View.GONE);
                adapter = new FactsListAdapter(FactsActivity.this, factsListItems);
                factsRecyclerView.setAdapter(adapter);
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
