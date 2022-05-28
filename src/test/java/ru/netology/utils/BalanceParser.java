package ru.netology.utils;

import java.security.InvalidParameterException;

public class BalanceParser {

    private static String _firstAnchor = "баланс:";
    private static String _secondAnchor = " р.";

    public static float parseCardBalance(String balanceLabel) {
        int firstPosition = balanceLabel.indexOf(_firstAnchor);
        if(firstPosition == -1)
             throw new InvalidParameterException("Input string " + balanceLabel + " does not have substring " + _firstAnchor);

        int secondPosition = balanceLabel.indexOf(_secondAnchor);
        if(secondPosition == -1)
            throw new InvalidParameterException("Input string " + balanceLabel + " does not have substring " + _secondAnchor);

        if(secondPosition <= firstPosition + _firstAnchor.length())
            throw new InvalidParameterException("YOU ARE REALLY PISSING ME OFF !!!");

        String substringBalance = balanceLabel.substring(firstPosition + _firstAnchor.length(), secondPosition);

        return Float.parseFloat(substringBalance);
    }
}
