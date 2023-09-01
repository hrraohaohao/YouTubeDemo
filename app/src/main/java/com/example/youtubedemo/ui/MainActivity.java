package com.example.youtubedemo.ui;

import android.os.Bundle;

import com.example.youtubedemo.listener.OnNextListener;
import com.example.youtubedemo.ui.detail.DetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hao.usbproject.R;
import com.hao.usbproject.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements OnNextListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onNext() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, DetailFragment.newInstance(), DetailFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DetailFragment fragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentByTag(DetailFragment.TAG);
        if (fragment != null && fragment.isVisible()) {
            fragment.closeFragment();
        } else {
            super.onBackPressed();
        }
    }
}