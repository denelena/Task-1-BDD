package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.UserInfo;
import ru.netology.pages.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class TestCardTransactions {

    UserInfo _testUserInfo;

    @BeforeEach
    public void setUp(){
        _testUserInfo = DataGenerator.createTestUserInfo();
    }

    @Test
    @DisplayName("Should successfully login, transfer 1% from first card to second, then return to the cards page")
    public void shouldReturnToCardsPageAfterTransferFirstToSecond() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        int transferAmount = (int)(0.01 * originalBalanceFirstCard); //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToSecond();

        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getFirstCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, transfer 1% from second card to first, then return to the cards page")
    public void shouldReturnToCardsPageAfterTransferSecondToFirst() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = (int)(0.01 * originalBalanceSecondCard); //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToFirst();

        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getSecondCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, setup to transfer 1% from first card to second, then cancel and return to the cards page and display original balances")
    public void shouldReturnToCardsPageAfterTransferCancelFirstToSecond() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = (int)(0.01 * originalBalanceFirstCard); //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToSecond();

        DashboardPage afterTransferDashboard = tp.doCancelTransfer(Integer.toString(transferAmount), _testUserInfo.getFirstCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard;
        float expectedSecondCardBalance = originalBalanceSecondCard;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "First Card balance " + originalBalanceFirstCard + " should remain " + originalBalanceFirstCard + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "Second Card balance " + originalBalanceSecondCard + " should remain " + originalBalanceSecondCard + " but ends up with " + newBalanceSecondCard);

    }

    @Test
    @DisplayName("Should successfully login, setup to transfer 1% from second card to first, then cancel and return to the cards page and display original balances")
    public void shouldReturnToCardsPageAfterTransferCancelSecondToFirst() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = (int)(0.01 * originalBalanceSecondCard); //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToFirst();

        DashboardPage afterTransferDashboard = tp.doCancelTransfer(Integer.toString(transferAmount), _testUserInfo.getSecondCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard;
        float expectedSecondCardBalance = originalBalanceSecondCard;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "First Card balance " + originalBalanceFirstCard + " should remain " + originalBalanceFirstCard + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "Second Card balance " + originalBalanceSecondCard + " should remain " + originalBalanceSecondCard + " but ends up with " + newBalanceSecondCard);

    }


    @Test
    @DisplayName("Should successfully login, transfer 1% from first card to second - integer roubles, should return to the cards page and display correct new balances")
    public void shouldMakeSingleTransferFromFirstToSecondInteger() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = (int)(0.01 * originalBalanceFirstCard); //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToSecond();

        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getFirstCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard - transferAmount;
        float expectedSecondCardBalance = originalBalanceSecondCard + transferAmount;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "From First Card starting balance of " + originalBalanceFirstCard + " subtracted " + transferAmount + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "To Second Card starting balance of " + originalBalanceSecondCard + " added " + transferAmount + " but ends up with " + newBalanceSecondCard);
    }


    @Test
    @DisplayName("Should successfully login, transfer 1% from second card to first - integer roubles, should return to the cards page and display correct new balances")
    public void shouldMakeSingleTransferFromSecondToFirstInteger() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = (int)(0.01 * originalBalanceSecondCard); //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToFirst();

        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getSecondCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard + transferAmount;
        float expectedSecondCardBalance = originalBalanceSecondCard - transferAmount;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "To First Card starting balance of " + originalBalanceFirstCard + " added " + transferAmount + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "From Second Card starting balance of " + originalBalanceSecondCard + " subtracted " + transferAmount + " but ends up with " + newBalanceSecondCard);
    }

    @Test
    @DisplayName("Should successfully login, transfer Zero from first card to second, should return to the cards page and display original balances")
    public void shouldMakeZeroTransferFromFirstToSecond() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = 0;

        TransferPage tp = originalDashboard.transferToSecond();
        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getFirstCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard;
        float expectedSecondCardBalance = originalBalanceSecondCard;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "First Card balance " + originalBalanceFirstCard + " should remain " + originalBalanceFirstCard + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "Second Card balance " + originalBalanceSecondCard + " should remain " + originalBalanceSecondCard + " but ends up with " + newBalanceSecondCard);
    }

    @Test
    @DisplayName("Should successfully login, transfer Zero from second card to first, should return to the cards page and display original balances")
    public void shouldMakeZeroTransferFromSecondToFirst() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        int transferAmount = 0;

        TransferPage tp = originalDashboard.transferToFirst();
        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getSecondCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard;
        float expectedSecondCardBalance = originalBalanceSecondCard;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "First Card balance " + originalBalanceFirstCard + " should remain " + originalBalanceFirstCard + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "Second Card balance " + originalBalanceSecondCard + " should remain " + originalBalanceSecondCard + " but ends up with " + newBalanceSecondCard);
    }

    @Test
    @DisplayName("Should successfully login, transfer ~1% from first card to second - non-integer roubles, should return to the cards page and display correct new balances")
    public void shouldMakeSingleTransferFromFirstToSecondFloat() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        float transferAmount = (0.01f * originalBalanceFirstCard) +0.25f; //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToSecond();
        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Float.toString(transferAmount), _testUserInfo.getFirstCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard - transferAmount;
        float expectedSecondCardBalance = originalBalanceSecondCard + transferAmount;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "From First Card starting balance of " + originalBalanceFirstCard + " subtracted " + transferAmount + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "To Second Card starting balance of " + originalBalanceSecondCard + " added " + transferAmount + " but ends up with " + newBalanceSecondCard);
    }


    @Test
    @DisplayName("Should successfully login, transfer ~1% from second card to first - non-integer roubles, should return to the cards page and display correct new balances")
    public void shouldMakeSingleTransferFromSecondToFirstFloat() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();

        float transferAmount = (0.01f * originalBalanceSecondCard) +0.25f; //try 1% - should be enough funds at the source card

        TransferPage tp = originalDashboard.transferToFirst();

        DashboardPage afterTransferDashboard = tp.doValidMoneyTransfer(Float.toString(transferAmount), _testUserInfo.getSecondCardNumber());

        float newBalanceFirstCard = afterTransferDashboard.getFirstCardBalance();
        float newBalanceSecondCard = afterTransferDashboard.getSecondCardBalance();

        float expectedFirstCardBalance = originalBalanceFirstCard + transferAmount;
        float expectedSecondCardBalance = originalBalanceSecondCard - transferAmount;

        assertEquals(expectedFirstCardBalance, newBalanceFirstCard, "To First Card starting balance of " + originalBalanceFirstCard + " added " + transferAmount + " but ends up with " + newBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, newBalanceSecondCard, "From Second Card starting balance of " + originalBalanceSecondCard + " subtracted " + transferAmount + " but ends up with " + newBalanceSecondCard);
    }

    @Test
    @DisplayName("Should successfully login, then attempt to transfer 10 roubles from the fake card number to First card. Expecting error popup")
    public void shouldTryTransferFromWrongCardToFirst() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        int transferAmount = 10;
        TransferPage tp = originalDashboard.transferToFirst();
        ErrorPage errPopup = tp.doInvalidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getInvalidCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, then attempt to transfer 10 roubles from the fake card number to Second card. Expecting error popup")
    public void shouldTryTransferFromWrongCardToSecond() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        int transferAmount = 10;
        TransferPage tp = originalDashboard.transferToSecond();
        ErrorPage errPopup = tp.doInvalidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getInvalidCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, then attempt to transfer negative amount from the first card number. Expecting error popup")
    public void shouldTryTransferFromFirstCardNegativeAmount() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        int transferAmount = -10; // :-)))

        TransferPage tp = originalDashboard.transferToSecond();

        ErrorPage errPopup = tp.doInvalidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getFirstCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, then attempt to transfer negative amount from the second card number. Expecting error popup")
    public void shouldTryTransferFromSecondCardNegativeAmount() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        int transferAmount = -10; // :-)))

        TransferPage tp = originalDashboard.transferToFirst();

        ErrorPage errPopup = tp.doInvalidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getSecondCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, then attempt to transfer too much from the First to Second card. Expecting error popup")
    public void shouldTryTransferFromFirstToSecondOverdraft() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceFirstCard = originalDashboard.getFirstCardBalance();
        int transferAmount = (int)(originalBalanceFirstCard * 2);

        TransferPage tp = originalDashboard.transferToSecond();
        ErrorPage errPopup = tp.doInvalidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getFirstCardNumber());
    }

    @Test
    @DisplayName("Should successfully login, then attempt to transfer too much from the Second to First card. Expecting error popup")
    public void shouldTryTransferFromSecondToFirstOverdraft() {
        open("http://localhost:9999");

        LoginPage lp =new LoginPage();
        VerificationPage vp = lp.validLogin(_testUserInfo);
        DashboardPage originalDashboard = vp.validVerify(_testUserInfo);

        float originalBalanceSecondCard = originalDashboard.getSecondCardBalance();
        int transferAmount = (int)(originalBalanceSecondCard * 2);

        TransferPage tp = originalDashboard.transferToFirst();
        ErrorPage errPopup = tp.doInvalidMoneyTransfer(Integer.toString(transferAmount), _testUserInfo.getSecondCardNumber());
    }
}
