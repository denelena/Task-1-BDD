package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ErrorPage {
    private SelenideElement headerField = $("[data-test-id='error-notification'] .notification__title");

    public ErrorPage(){
        headerField.shouldBe(visible);
        headerField.shouldHave(text("Ошибка"));
    }
}
