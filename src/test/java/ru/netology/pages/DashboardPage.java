package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.utils.BalanceParser;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement headerField = $("[data-test-id='dashboard']");
    private SelenideElement subheaderField = $("[id='root'] h1");

    private ElementsCollection cardEntries = $$(".list__item div");
    private ElementsCollection transferButtons = $$("[data-test-id='action-deposit']");


    public DashboardPage() {
        headerField.shouldBe(visible);

        headerField.shouldHave(Condition.text("Личный кабинет"));
        subheaderField.shouldHave(Condition.text("Ваши карты"));

        cardEntries.shouldHave(size(2));
        transferButtons.shouldHave(size(2));
    }

    public float getFirstCardBalance() {
        String bal1Label = cardEntries.get(0).getText();
        return BalanceParser.parseCardBalance(bal1Label);
    }

    public float getSecondCardBalance() {
        String bal2Label = cardEntries.get(1).getText();
        return BalanceParser.parseCardBalance(bal2Label);
    }

    public TransferPage transferToFirst() {
        transferButtons.get(0).click();
        return new TransferPage();
    }

    public TransferPage transferToSecond() {
        transferButtons.get(1).click();
        return new TransferPage();
    }
}
