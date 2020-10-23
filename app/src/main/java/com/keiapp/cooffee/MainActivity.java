package com.keiapp.cooffee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import com.google.firebase.auth.FirebaseAuth;
import com.keiapp.cooffee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MAIN_ACTIVITY_LOG";
    private FirebaseAuth auth;
    private NavController navController;
    private  Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        final Toolbar centerToolbar = binding.toolbar2.toolbarCenter;
        final Toolbar startToolbar = binding.toolbar.toolbarStart;
        final Toolbar activityToolbar = binding.toolbar3.toolbarActivity;

        final NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if (destination.getLabel() != null) {
                    switch (destination.getLabel().toString()){
                        case "fragment_home":
                            setSupportActionBar(startToolbar);
                            startToolbar.setVisibility(View.VISIBLE);
                            centerToolbar.setVisibility(View.GONE);
                            activityToolbar.setVisibility(View.GONE);
                            binding.bottomNavigationView.setVisibility(View.VISIBLE);
                            break;
                        case "fragment_account":
                        case "fragment_menu":
                        case "fragment_order":
                        case "fragment_cart":
                            //Second toolbar will sit on the top of the default toolbar
                            startToolbar.setVisibility(View.VISIBLE);
                            centerToolbar.setVisibility(View.VISIBLE);
                            activityToolbar.setVisibility(View.GONE);
                            binding.bottomNavigationView.setVisibility(View.VISIBLE);
                            break;
                        case "fragment_profile_settings":
                        case "fragment_order_history":
                        case "fragment_manage_notifications":
                            setSupportActionBar(activityToolbar);
                            centerToolbar.setVisibility(View.GONE);
                            startToolbar.setVisibility(View.GONE);
                            activityToolbar.setVisibility(View.VISIBLE);
                            activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    navController.navigateUp();
                                }
                            });
                            binding.bottomNavigationView.setVisibility(View.GONE);
                            break;

                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_start_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()){
            case "Log Out":
                auth.signOut();
                Intent intent = new Intent(this,StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}