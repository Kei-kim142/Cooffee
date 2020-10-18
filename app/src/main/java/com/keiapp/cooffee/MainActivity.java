package com.keiapp.cooffee;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.keiapp.cooffee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MAIN_ACTIVITY_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final Toolbar centerToolbar = binding.toolbar2.toolbarCenter;
        final Toolbar startToolbar = binding.toolbar.toolbarStart;

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if (destination.getLabel() != null) {
                    switch (destination.getLabel().toString()){
                        case "fragment_home":
                        case "fragment_account":
                            startToolbar.setVisibility(View.VISIBLE);
                            centerToolbar.setVisibility(View.GONE);
                            break;
                        case "fragment_menu":
                        case "fragment_order":
                        case "fragment_cart":
                            startToolbar.setVisibility(View.GONE);
                            centerToolbar.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
    }

}