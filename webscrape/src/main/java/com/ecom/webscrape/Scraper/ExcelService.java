package com.ecom.webscrape.Scraper;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom.webscrape.Helper.Helper;
import com.ecom.webscrape.model.Products;

@Service
public class ExcelService {

  public ByteArrayInputStream getActualData(List<Products> products){
   return Helper.dataToExcel(products);
  }   

}
