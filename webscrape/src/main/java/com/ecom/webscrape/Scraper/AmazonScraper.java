package com.ecom.webscrape.Scraper;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ecom.webscrape.Utility.HttpUtil;
import com.ecom.webscrape.model.Products;


@Service
public class AmazonScraper {

    private final String API_KEY = "45b5543169f07cea893dafe82bd1490c2eb7c655bce02add747f678bf112ab16";

    public List<Products> scrapeAmazonProduct(String query) {
        List<Products> list = new ArrayList<>();

        try {
            String url = "https://serpapi.com/search.json"
                         + "?engine=amazon"
                         + "&api_key=" + API_KEY
                         + "&amazon_domain=amazon.com"
                         + "&k="+query;

            String json = HttpUtil.get(url);
            JSONObject obj = new JSONObject(json);

            JSONArray results = obj.optJSONArray("organic_results");

            for (int i = 0; results != null && i < results.length(); i++) {
                JSONObject p = results.getJSONObject(i);

                list.add(new Products(
                        p.optString("title"),
                        p.optString("price"),
                        p.optDouble("rating"),
                        p.optString("link")
                       
                ));
            }

        } catch (Exception e) {
            
            e.printStackTrace();
        }

        return list;
    }
}
