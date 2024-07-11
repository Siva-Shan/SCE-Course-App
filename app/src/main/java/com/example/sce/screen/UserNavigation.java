package com.example.sce.screen;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.sce.R;
import com.example.sce.common.CommonConstant;
import com.example.sce.helper.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sce.databinding.ActivityUserNavigationBinding;

public class UserNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityUserNavigationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //String username=getIntent().getExtras().getString("username").toString();
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        String userType = preferenceManager.getUserType();
        String userID = null;

        if (userType.equals(CommonConstant.SIGNING_USER)) {
            userID = preferenceManager.getUserId();
        }

        setSupportActionBar(binding.appBarUserNavigation.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        Menu menu = navigationView.getMenu();
        MenuItem navProfileItem = menu.findItem(R.id.nav_profile);
        MenuItem navEnrollmentItem = menu.findItem(R.id.nav_enrollment);
        if (userID == null) {
            navProfileItem.setVisible(false);
            navEnrollmentItem.setVisible(false);
        } else {
            navProfileItem.setVisible(true);
            navEnrollmentItem.setVisible(true);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}