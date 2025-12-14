package com.ecom.webscrape.Scraper;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ecom.webscrape.Utility.HttpUtil;
import com.ecom.webscrape.model.Products;

@Service
public class WalmartScraper {

    private final String API_KEY = "45b5543169f07cea893dafe82bd1490c2eb7c655bce02add747f678bf112ab16";

    public List<Products> scrapeWalmartProduct(String  query) {
        List<Products> list = new ArrayList<>();

        try {
            String url = "https://serpapi.com/search.json"
                    + "?engine=walmart"
                    + "&api_key=" + API_KEY
                    + "&query="+query;

            String json = HttpUtil.get(url);
            JSONObject obj = new JSONObject(json);

            JSONArray results = obj.optJSONArray("organic_results");

            for (int i = 0; results != null && i < results.length(); i++) {

                JSONObject p = results.getJSONObject(i);

                double rating = p.optDouble("rating", 0.0);

                String link = p.optString("product_page_url", "");

                list.add(new Products(
                        p.optString("title", "N/A"), getPrice(p), rating,link ));
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // Minimal price extractor with $ symbol
    private String getPrice(JSONObject p) {
        JSONObject primaryOffer = p.optJSONObject("primary_offer");
        String price = null;

        if (primaryOffer != null) {
            if (primaryOffer.has("price")) price = primaryOffer.optString("price");
            else if (primaryOffer.has("offer_price")) price = primaryOffer.optString("offer_price");
            else if (primaryOffer.has("displayed_price")) price = primaryOffer.optString("displayed_price");
        }

        if (price == null || price.isEmpty()) return "N/A";

        return "$" + price; 
    }

}
