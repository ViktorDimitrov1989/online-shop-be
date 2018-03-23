package com.online.shop.response;

public final class ResponseMessageConstants {

    public static final String LOGIN_SUCCESS = "Успешен вход!";
    public static final String LOGOUT_SUCCESS = "Успешен изход!";
    public static final String INVALID_CREDENTIALS = "Невалидни имейл/парола.";
    public static final String REGISTER_SUCCESS = "Успешна регистрация.";
    public static final String PASSWORDS_MISMATCH = "Паролите не са еднакви.";
    public static final String EMAIL_ALREADY_TAKEN = "Има потребител регистриран с този имейл адрес.";
    public static final String PHONE_NUMBER_ALREADY_TAKEN = "Има потребител регистриран с този телефонен номер.";
    public static final String INVALID_ROLE = "Невалидна роля.";

    public static final String INVALID_USER= "Такъв потребител не съществува.";
    public static final String FORBIDDEN = "Нямате права да достъпвате тази фунционалност.";
    public static final String ALREADY_ADMIN = "Потребителя е вече администратор.";
    public static final String ADMIN = "Потребителя вече има администраторски права.";
    public static final String BLOCKED = "Потребителя е блокиран.";
    public static final String ADMIN_PRIVILЕGES_TAKEN = "Администраторските права на потребителя са отнени.";
    public static final String NOT_ADMIN = "Потребителя не е администратор.";
    public static final String UNBLOCKED = "Потребителския акаунт е отблокиран.";
    public static final String ACCOUNT_IS_LOCKED = "Този акаунт е заключен.";


    private ResponseMessageConstants(){}
}
