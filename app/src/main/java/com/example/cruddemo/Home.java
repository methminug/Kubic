package com.example.cruddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cruddemo.item.DeleteItem;
import com.example.cruddemo.user.ProfileFragment;
import com.example.cruddemo.user.Users;
import com.example.cruddemo.user.profile;
import com.example.cruddemo.wish.AddNewWish;
import com.example.cruddemo.wish.DataBaseServices;
import com.example.cruddemo.wish.FirstFragment;
import com.example.cruddemo.wish.SwipeCards;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i("test","In home class");

        // TODO delete after testing
//        SharedPreferences sharedPreferences = getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("currentUser","-MITimME3wm7nA8CTDSO");
//        editor.apply();

        //Drawer Menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        sideMenu = findViewById(R.id.sideMenuIcon);

        navigationDrawer();



    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_wishList);

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_newWish:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), AddNewWish.class);
                startActivity(intent);
                break;

            case R.id.nav_wishList:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intentw = new Intent(getApplicationContext(), Home.class);
                startActivity(intentw);
                break;

            case R.id.nav_profile:
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).commit();
                break;

            case R.id.nav_items:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intentItems = new Intent(getApplicationContext(), DeleteItem.class);
                startActivity(intentItems);
                break;

            case R.id.nav_browse:
                Intent intents = new Intent(getApplicationContext(), SwipeCards.class);
                startActivity(intents);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}