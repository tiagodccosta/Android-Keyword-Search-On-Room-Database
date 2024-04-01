package com.incheymus.tiledmediachallenge;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoRepository {
    private final PhotoDao photoDao;
    private final PhotoApiService photoApiService;
    private final ExecutorService executorService;

    public PhotoRepository(PhotoDao photoDao, PhotoApiService photoApiService) {
        this.photoDao = photoDao;
        this.photoApiService = photoApiService;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void fetchPhotosFromServer(int page, int pageSize) {
        Call<List<Photo>> call = photoApiService.getPhotos(page, pageSize);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Photo>> call, @NonNull Response<List<Photo>> response) {
                if(response.isSuccessful()) {
                    List<Photo> photos = response.body();
                    if (photos != null && getPhotoCount() == 0) {
                        insertPhotosIntoDatabase(photos);
                    } else {
                        insertNewPhotosIntoDatabase(photos);
                    }
                } else {
                    Log.d("PHOTOS FETCH", "Fetch unsuccessful " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Photo>> call, @NonNull Throwable t) {
                Log.d("PHOTOS FETCH", "Fetch unsuccessful " + t.getMessage());
            }
        });
    }

    private void insertNewPhotosIntoDatabase(List<Photo> newPhotos) {
        executorService.execute(() -> {
            List<Photo> existingPhotos = photoDao.getAllPhotos();
            List<Photo> photosToInsert = new ArrayList<>();

            for (Photo newPhoto : newPhotos) {
                boolean isNew = true;
                for (Photo existingPhoto : existingPhotos) {
                    if (newPhoto.getContentUrl().equals(existingPhoto.getContentUrl())) {
                        isNew = false;
                        break;
                    }
                }
                if (isNew) {
                    photosToInsert.add(newPhoto);
                }
            }

            if (!photosToInsert.isEmpty()) {
                Log.d("PhotoRepository", "Inserting " + photosToInsert.size() + " new photos into the database.");
                photoDao.insertAll(photosToInsert);
            } else {
                Log.d("PhotoRepository", "No new photos to insert.");
            }
        });
    }

    private void insertPhotosIntoDatabase(List<Photo> photos) {
        executorService.execute(() -> {
            for (Photo photo : photos) {
                Log.d("PhotoRepository", "Inserting photo: " + photo.getContentUrl());
                photoDao.insert(photo);
            }
            Log.d("DATABASE", "Database size: " + photoDao.getPhotoCount());
        });
    }

    private int getPhotoCount() {
        Callable<Integer> getCountCallable = photoDao::getPhotoCount;
        Future<Integer> future = executorService.submit(getCountCallable);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0; // Handle the error appropriately in your application
        }
    }

    public PhotoDao getPhotoDao() {
        return photoDao;
    }
}