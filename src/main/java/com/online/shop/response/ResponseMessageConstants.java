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
    public static final String EXISTING_BRAND_NAME = "Марка с това име вече съществува.";
    public static final String CREATE_BRAND_SUCCESS = "Марката е създадена успешно.";
    public static final String CREATE_CATEGORY_SUCCESS = "Категорията е създадена успешно.";
    public static final String EXISTING_CATEGORY = "Категория с това име вече съществува.";
    public static final String INVALID_SEASON = "Невалиден сезон.";
    public static final String INVALID_GENDER = "Невалиден пол.";
    public static final String CREATE_ARTICLE_SUCCESS = "Артикула е създаден успешно.";
    public static final String EDIT_ARTICLE_SUCCESS = "Артикула е променен успешно.";
    public static final String INVALID_BRAND_NAME = "Невалидно име на марка.";
    public static final String INVALID_SIZE = "Невалиден размер.";
    public static final String INVALID_CATEGORY_ID = "Невалидно ID на категория.";
    public static final String INVALID_STATUS_ID = "Невалидно ID на статус.";
    public static final String INVALID_STATUS_END_DATE = "Некоректна дата на изтичане.";
    public static final String EDIT_ARTICLE_STATUS_SUCCESS = "Статуса е променен.";
    public static final String INVALID_ARTICLE_ID = "Невалиден артикул.";
    public static final String ARTICLE_NAME_DUPLICATE = "Вече има артикул с това име.";
    public static final String DELETE_ARTICLE_SUCCESS = "Артикула е изтрит успешно.";
    public static final String INVALID_COLOR_NAME = "Цвят с това име не съществува.";
    public static final String INVALID_CART_ID = "Невалидно ID на количка.";
    public static final String EXISTING_SHOPPING_CAR_ARTICLE = "Този артикул вече е добавен във вашата количка.";
    public static final String INVALID_SHOPPING_CART_ARTICLE_ID = "Невалидно ID на артикул от кошницата.";
    public static final String INCREASE_SHOPPING_CART_ARTICLE_SUCCESS = "Количеството на продукта е увеличено.";
    public static final String DECREASE_SHOPPING_CART_ARTICLE_SUCCESS = "Количеството на продукта е намалено.";
    public static final String REMOVE_SHOPPING_CART_ARTICLE_SUCCESS = "Продукта е премахнат от количката.";
    public static final String ADD_SHOPPING_CART_ARTICLE_SUCCESS = "Продукта е добавен в количката.";
    public static final String CLEAR_BASKET_SUCCESS = "Продуктите са поръчани, очаквайте обаждане от наш служител за потвърждение";


    private ResponseMessageConstants(){}
}
