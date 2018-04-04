package com.online.shop.areas.articles.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Status {
    PROMO,
    REGULAR,
    SALE;

    Status() {
    }

    public static boolean isPresent(String value){
        for (Season s : Season.values()) {
            if (s.name().equals(value)) {
                return true;
            }
        }
        return false;
    }


    public static List<Status> getStatuses(){
        return new ArrayList<Status>(Arrays.asList(PROMO, REGULAR, SALE));
    }
}
