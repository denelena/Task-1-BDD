package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField =$("[data-test-id=login] input");
    private SelenideElement passwordField =$("[data-test-id=password] input");
    private SelenideElement loginButton =$("[data-test-id=action-login]");

    public VerificationPage validLogin(UserInfo li) {
        loginField.setValue(li.getLogin());
        passwordField.setValue(li.getPassword());
        loginButton.click();

        return new VerificationPage();
    }

}
