package com.ecom.webscrape.Scraper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ecom.webscrape.Utility.HttpUtil;
import com.ecom.webscrape.model.Products;

@Service
public class EbayScraper {

    private final String API_KEY = "45b5543169f07cea893dafe82bd1490c2eb7c655bce02add747f678bf112ab16";

    public List<Products> scrapeEbayProduct(String query) {
        List<Products> list = new ArrayList<>();

        try {
            String url = "https://serpapi.com/search.json"
                    + "?engine=ebay"
                    + "&api_key=" + API_KEY
                    + "&_nkw="+query;

            String json = HttpUtil.get(url);
            

            JSONObject obj = new JSONObject(json);
            JSONArray results = obj.optJSONArray("organic_results");
            if (results == null) results = obj.optJSONArray("items"); // fallback
            if (results == null) return list;

            for (int i = 0; i < results.length(); i++) {
             JSONObject p = results.getJSONObject(i);

              double rating = p.optDouble("rating", 0.0);
               if (rating == 0.0 && p.has("seller")) {
                  JSONObject seller = p.optJSONObject("seller");
                   if (seller != null) {
                    rating = seller.optDouble("feedback_rating", seller.optDouble("feedback_score", 0.0));
                      }
                }
           
                String title = p.optString("title", "N/A");
                String price = getPrice(p);
                // double rating = p.optDouble("rating", 0.0);
                String link = p.optString("link", "");

                list.add(new Products(title, price, rating, link));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private String getPrice(JSONObject p) {
    Object priceObj = p.opt("price");
    if (priceObj instanceof JSONObject) {
        JSONObject priceJson = (JSONObject) priceObj;
        String raw = priceJson.optString("raw");
        if (raw != null && !raw.isEmpty()) return raw;
        double extracted = priceJson.optDouble("extracted", 0.0);
        if (extracted > 0) return "$" + extracted;
    } else if (priceObj instanceof String) {
        String price = (String) priceObj;
        if (!price.startsWith("$")) price = "$" + price;
        return price;
    }
    return "N/A";
}

}
