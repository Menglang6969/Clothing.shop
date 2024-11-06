package com.menglang.Clothing.shop.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class DateFormat {

    public DateFormat(String dateTo) {
    }

    //2024/11/06
    public Date formatToSimpleDate(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try{
            return dateFormat.parse(String.valueOf(date));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
