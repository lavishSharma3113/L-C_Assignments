package com.learnandcode;

import org.json.JSONObject;

public class TumblrBlog {
    public static void main(String[] args) {
        UserInputService userInputService = new UserInputService();
        TumblrApiService tumblrApiService = new TumblrApiService();
        JsonParserService jsonParserService = new JsonParserService();
        BlogInfoService blogInfoService = new BlogInfoService();
        TumblrPostsService postService = new TumblrPostsService();

        String blogName = userInputService.getBlogName();
        int[] range = userInputService.getRange();
        int start = range[0];
        int end = range[1];

        String apiUrl = constructApiUrl(blogName, start, end);

        try {
            String responseData = String.valueOf(tumblrApiService.fetchApiResponse(apiUrl));
            String cleanJson = jsonParserService.cleanApiResponse(responseData);
            JSONObject jsonDocument = jsonParserService.parseJson(cleanJson);

            blogInfoService.displayBlogInfo(jsonDocument);
            postService.displayPostImages(jsonDocument);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private static String constructApiUrl(String blogName, int start, int end) {
        return "https://" + blogName + ".tumblr.com/api/read/json?type=photo&num=" + (end - start + 1) + "&start=" + (start - 1);
    }
}

