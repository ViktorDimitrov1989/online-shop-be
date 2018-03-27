package com.online.shop.areas.articles.enums;

public enum Season {
    SPRING_SUMMER,
    FALL_WINTER;

    Season() {
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
