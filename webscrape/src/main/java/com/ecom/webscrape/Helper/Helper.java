package com.ecom.webscrape.Helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import com.ecom.webscrape.model.Products;

public class Helper {

    public static String[] HEADER = {
        "title",
        "price",
        "rating",
        "link"
    };
   
    public static String SHEET_NAME = "product_data";

    public static ByteArrayInputStream dataToExcel(List<Products> list){

        try {
           
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
           
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            Row headerRow = sheet.createRow(0);

            for(int i=0;i<HEADER.length;i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADER[i]);
            }

            int rowIndex =1;
            for(Products p : list){
               Row dataRow = sheet.createRow(rowIndex++);
                
                dataRow.createCell(0).setCellValue(p.getTitle());
                dataRow.createCell(1).setCellValue(p.getPrice());
                dataRow.createCell(2).setCellValue(p.getRating());
                dataRow.createCell(3).setCellValue(p.getLink());

            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch(Exception e){
            e.printStackTrace();
            return null;
        } 
    }

}
