package com.online.shop.areas.articles.enums;

public enum Gender {
    BOYS,
    GIRLS;

    Gender() {
    }

    public static boolean isPresent(String value){
        for (Gender s : Gender.values()) {
            if (s.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
