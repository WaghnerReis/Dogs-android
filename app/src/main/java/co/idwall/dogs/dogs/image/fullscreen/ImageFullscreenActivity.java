package co.idwall.dogs.dogs.image.fullscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import co.idwall.dogs.R;

public class ImageFullscreenActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen);
        setupActionBar();

        initImage();
    }

    private void initImage() {
        ImageView image = findViewById(R.id.image);

        String url = getIntent().getStringExtra("URL");
        Picasso.get().load(url).into(image);
        image.setTransitionName(getIntent().getStringExtra("TRANSITION"));
    }

    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}
