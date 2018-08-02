package com.mynews.flooo.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

import com.mynews.flooo.mynews.AlarmNotifications.GetInfoPreferences;
import com.mynews.flooo.mynews.ApiRest.ApiCalls;
import com.mynews.flooo.mynews.ApiRest.Results;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements ApiCalls.Callbacks
{

    Results listNews;
    AdapterRecyclerView adapterRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);
        Toolbar toolbar =  findViewById(R.id.toolbar);


        Intent in = getIntent();
        Bundle bundle = in.getExtras();

        GetInfoPreferences getInfo = new GetInfoPreferences();

        if(bundle!=null)
        {
            toolbar.setTitle("Results by Search");
            String sectionsSearchActives = getInfo.buildSectionsStringForSearch(bundle.getStringArrayList("CheckBox"));
            String queryTerm = getInfo.queryTermBuild(bundle.getString("EditText"));
            ApiCalls.getArticleSearch(this,sectionsSearchActives,queryTerm);
        }
        else
        {
            toolbar.setTitle("Results by Notification");
            String sections = getInfo.buildSectionsStringForNotification(this.getBaseContext());
            ApiCalls.getArticleSearch(this,sections,getInfo.getQueryTerm());
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listNews = new Results();

        RecyclerView recyclerView = findViewById(R.id.listSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapterRecyclerView = new AdapterRecyclerView(listNews,this.getBaseContext(),"Search Articles");
        recyclerView.setAdapter(adapterRecyclerView);


    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponse(@Nullable Results listResult)
    {
        if(listResult!=null)
        {
            System.out.println(listResult.size());

            this.listNews.clear();
            this.listNews.addAll(listResult) ;
            adapterRecyclerView.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure()
    {

    }
}
