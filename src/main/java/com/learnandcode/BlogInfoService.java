package com.learnandcode;
import org.json.JSONObject;

public class BlogInfoService {

    public void displayBlogInfo(JSONObject jsonData) {
        // Extract blog information
        JSONObject blogInfo = jsonData.getJSONObject("tumblelog");
        String title = blogInfo.getString("title");
        String description = blogInfo.getString("description");
        String name = blogInfo.getString("name");
        int totalPosts = jsonData.getInt("posts-total");


        System.out.println("\nTitle: " + title);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Number of Posts: " + totalPosts + "\n");
    }
}


