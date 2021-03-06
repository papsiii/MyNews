package com.mynews.flooo.mynews.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.mynews.flooo.mynews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {



    private ArrayList<String> tabTitles = new ArrayList<>();
    private  ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        //add the different categories for the TableLayout
        tabTitles.add("Top Stories");
        tabTitles.add("Most Popular");
        tabTitles.add("world");

        setContentView(R.layout.activity_main);

        // Get the toolbar and set it
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get tabLayout of the view xml
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // CONFIGURATION OF VIEWPAGER WITH TableLayout
        ////////////////////////////////////////////////////
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(new AdapterPageActuality(getSupportFragmentManager(),tabTitles));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Attach the TabLayout with the viewPager
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setInlineLabel(true);

        // Configuration of Navigation Drawer
        DrawerLayout drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView =  findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        ImageView headerDrawer = header.findViewById(R.id.imageViewHeaderNavigationDrawer);
        Picasso.with(this).load("https://static.nytimes.com/images/icons/ios-ipad-144x144.png").into(headerDrawer);



    }


    // Inflate the menu to activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    //Listener for item clicked on menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.notifications:
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                intent.putExtra("LoadLayout","Notifications");
                startActivity(intent);
                return true;
            case R.id.action_search:
                Intent intentSearch = new Intent(MainActivity.this, OptionsActivity.class);
                intentSearch.putExtra("LoadLayout","Search Articles");
                startActivity(intentSearch);
                return true;
            case R.id.menu_about:
                Toast.makeText(getBaseContext(),R.string.about, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_help:
                Intent goToUrlPage = new Intent(this, WebViewActivity.class);
                goToUrlPage.putExtra("URL","https://github.com/papsiii/MyNews");
                goToUrlPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(goToUrlPage);
        }
        return super.onOptionsItemSelected(item);
    }

    //Listener for item clicked on menu NavigationDrawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id = menuItem.getItemId();

        switch (id)
        {
            case R.id.TopstoriesDrawer:
                pager.setCurrentItem(0);
                break;
            case R.id.MostPopularDrawer:
                pager.setCurrentItem(1);
                break;
            case R.id.UserSectionDrawer:
                pager.setCurrentItem(2);
                break;
            case R.id.notifDrawer:
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                intent.putExtra("LoadLayout","Notifications");
                startActivity(intent);
                break;
            case R.id.searchDrawer:
                Intent intentSearch = new Intent(MainActivity.this, OptionsActivity.class);
                intentSearch.putExtra("LoadLayout","Search Articles");
                startActivity(intentSearch);
                break;
            default:
                return false;
        }

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
