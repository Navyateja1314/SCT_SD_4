package com.ecom.webscrape.controller;
import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import  org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.webscrape.Scraper.AmazonScraper;
import com.ecom.webscrape.Scraper.EbayScraper;
import com.ecom.webscrape.Scraper.ExcelService;
import com.ecom.webscrape.Scraper.WalmartScraper;
import com.ecom.webscrape.model.Products;


@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class ScraperController {
 
    private final AmazonScraper amazonScraper;
    private final WalmartScraper walmartScraper;
    private final EbayScraper ebayScraper;
    private final ExcelService excelService;

    public ScraperController(AmazonScraper amazonScraper, WalmartScraper walmartScraper,EbayScraper ebayScraper, ExcelService excelService) {
        this.amazonScraper = amazonScraper;
        this.walmartScraper = walmartScraper;
        this.ebayScraper = ebayScraper;
        this.excelService = excelService;
    }


    @GetMapping("/amazon")
    public List<Products> getAmazonProducts(@RequestParam String query) {
        return amazonScraper.scrapeAmazonProduct(query);
    }

    @GetMapping("/walmart")
    public List<Products> getWalmartProducts(@RequestParam String query) {
        return walmartScraper.scrapeWalmartProduct(query);
    }

    @GetMapping("/ebay")
    public List<Products> getEbayProducts(@RequestParam String query) {
        return ebayScraper.scrapeEbayProduct(query);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download( @RequestParam String query,
        @RequestParam String shop) {
        
        List<Products> products;
        
        switch (shop.toLowerCase()) {
        case "amazon":
            products = amazonScraper.scrapeAmazonProduct(query);
            break;
        case "ebay":
            products = ebayScraper.scrapeEbayProduct(query);
            break;
        case "walmart":
            products = walmartScraper.scrapeWalmartProduct(query);
            break;
        default:
            throw new RuntimeException("Invalid shop: " + shop);
    }

        ByteArrayInputStream actualData = excelService.getActualData(products);
        InputStreamResource file = new InputStreamResource(actualData);
    
      ResponseEntity<Resource> body = ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Products.xlsx")
        .contentType(MediaType.parseMediaType(
                     "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(file);

        return body;
    
    }


}
