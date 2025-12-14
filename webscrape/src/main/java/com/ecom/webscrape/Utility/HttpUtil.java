package com.ecom.webscrape.Utility;



import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws Exception {

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
