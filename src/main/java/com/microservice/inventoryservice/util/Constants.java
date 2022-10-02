package com.microservice.inventoryservice.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Constants {
    public static final String RECORD_ADDED="Inventory record added successfully!!";
    public static final String RECORD_UPDATED="Inventory record updated successfully!!";
    public static final String STATUS_ENABLED="Inventory record enable successfully!!";
    public static final String STATUS_DISABLED="Inventory record disable successfully!!";
    public static final Boolean ACTIVE_STATUS=true;
    public static final Boolean DIACTIVE_STATUS=false;

    public static final String formatedDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }
}
