package com.incheymus.tiledmediachallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView photoDisplayView;
    PhotoAdapter adapter;
    PhotoRepository photoRepository;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                performSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        setAdapter();
        initializePhotoRepository();
    }

    private void setAdapter() {
        photoDisplayView = findViewById(R.id.displayPhotosView);
        adapter = new PhotoAdapter(this, new ArrayList<>());
        photoDisplayView.setLayoutManager(new LinearLayoutManager(this));
        photoDisplayView.setAdapter(adapter);
    }

    private void initializePhotoRepository() {
        PhotoDatabase photoDatabase = PhotoDatabase.getInstance(getApplicationContext());
        PhotoApiService photoApiService = RetrofitClient.getClient().create(PhotoApiService.class);
        photoRepository = new PhotoRepository(photoDatabase.photoDao(), photoApiService);
        fetchPhotosFromServer();
    }

    private void fetchPhotosFromServer() {
        photoRepository.fetchPhotosFromServer(1, 200);
    }

    private void performSearch(String query) {
        adapter.clearAdapter();
        AsyncTask.execute(() -> {
            String[] keywords = query.trim().split("\\s+");

            if(keywords.length > 3) {
                runOnUiThread(() -> Toast.makeText(this, "Only Enter Up To 3 Keywords", Toast.LENGTH_SHORT).show());
                return;
            }

            List<Photo> searchResults = new ArrayList<>();

            for (String keyword : keywords) {
                List<Photo> keywordResults = photoRepository.getPhotoDao().searchPhotosByKeyword(keyword);
                searchResults.addAll(keywordResults);
            }

            runOnUiThread(() -> adapter.addPhotosToAdapter(searchResults));
        });
    }
}