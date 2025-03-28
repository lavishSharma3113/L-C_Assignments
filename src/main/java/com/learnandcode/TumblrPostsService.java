package com.learnandcode;

import org.json.JSONArray;
import org.json.JSONObject;

public class TumblrPostsService {

    public void displayPostImages(JSONObject jsonData) {
        if (!jsonData.has("posts")) {
            System.out.println("No posts available.");
            return;
        }

        JSONArray posts = jsonData.getJSONArray("posts");
        processPosts(posts);
    }

    private void processPosts(JSONArray posts) {
        int postCount = 1;
        System.out.println("the psots are"+posts.toString());
        for (int i = 0; i < posts.length(); i++) {
            JSONObject post = posts.getJSONObject(i);
            System.out.println(postCount + ".");

            if (post.has("photos")) {
                displayPhotos(post.getJSONArray("photos"));
            } else {
                System.out.println("No photos available for this post.");
            }

            postCount++;
        }
    }

    private void displayPhotos(JSONArray photos) {
        for (int j = 0; j < photos.length(); j++) {
            JSONObject photo = photos.getJSONObject(j);
            extractPhotoUrl(photo);

        }
    }

    private void extractPhotoUrl(JSONObject photo) {
        System.out.println("ALL URLS ARE" + photo.keySet().toString());
        for (String key : photo.keySet()) {
            if (key.startsWith("photo-url")) {
                System.out.println(photo.optString(key, "No image available"));
            }
        }

    }

}

