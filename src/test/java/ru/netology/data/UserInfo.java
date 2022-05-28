package ru.netology.data;

public class UserInfo {
    private String login;
    private String password;
    private String smsCode;

    private String firstCardNumber;
    private String secondCardNumber;

    private String invalidCardNumber;

    public UserInfo(String login, String password, String smsCode, String firstCardNumber, String secondCardNumber, String invalidCardNumber){
        this.login = login;
        this.password = password;
        this.smsCode = smsCode;
        this.firstCardNumber = firstCardNumber;
        this.secondCardNumber = secondCardNumber;
        this.invalidCardNumber = invalidCardNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public String getFirstCardNumber() {
        return firstCardNumber;
    }

    public String getSecondCardNumber() {
        return secondCardNumber;
    }

    public String getInvalidCardNumber() {
        return invalidCardNumber;
    }
}
