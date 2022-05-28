package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement headerField = $("[data-test-id='dashboard']");
    private SelenideElement subheaderField = $("[id='root'] h1");

    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromCardField = $("[data-test-id='from'] input");

    private SelenideElement transferButtonField = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButtonField = $("[data-test-id='action-cancel']");

    public TransferPage(){
        headerField.shouldBe(visible);

        headerField.shouldHave(Condition.text("Личный кабинет"));
        subheaderField.shouldHave(Condition.text("Пополнение карты"));
    }

    public DashboardPage doValidMoneyTransfer(String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromCardField.setValue(fromCardNumber);

        transferButtonField.click();

        //ожидаем успех - переход на страницу с балансом карт
        return new DashboardPage();
    }

    public ErrorPage doInvalidMoneyTransfer(String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromCardField.setValue(fromCardNumber);

        transferButtonField.click();

        //ожидаем fail - popup с шапкой "Ошибка"
        return new ErrorPage();
    }

    public DashboardPage doCancelTransfer(String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromCardField.setValue(fromCardNumber);

        cancelButtonField.click();

        //ожидаем успех - переход на страницу с балансом карт
        return new DashboardPage();
    }
}
