package co.idwall.dogs.dogs.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import co.idwall.dogs.R;

public class DogsActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                DogsFragment.newInstance("")).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String category = "";

        switch (menuItem.getItemId()) {
            case R.id.navigation_hound:
                category = "hound";
                break;
            case R.id.navigation_pug:
                category = "pug";
                break;
            case R.id.navigation_labrador:
                category = "labrador";
                break;
        }

        Fragment fragment = DogsFragment.newInstance(category);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        return true;
    }
}
