package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {

    public static UserInfo createTestUserInfo(){
        Faker fkr = new Faker(new Locale("ru"));
        String invalidCard = fkr.finance().creditCard();

        return new UserInfo("vasya", "qwerty123", "12345", "5559 0000 0000 0001", "5559 0000 0000 0002", invalidCard);
    }
}
