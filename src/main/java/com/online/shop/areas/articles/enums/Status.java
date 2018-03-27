package com.online.shop.areas.articles.enums;

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
}
