package com.ecom.webscrape.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    private String title;
    private String price;
    private Double rating;
    private String link;
    
}
