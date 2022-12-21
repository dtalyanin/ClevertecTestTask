package ru.clevertec.task.utils;

public class PriceHelper {
    private static final int COINS_AMOUNT = 100;

    public static int getPricePercentWithRound(int price, int percent) {
        return (int) Math.round(price * percent / (COINS_AMOUNT * 1.0));
    }

    public static String getPriceRepresentation(int price) {
        int rubles = price / COINS_AMOUNT;
        int coins = price % COINS_AMOUNT;
        return String.format("%d.%02d", rubles, coins);
    }

    public static double getPriceAsDouble(int price) {
        return price / (COINS_AMOUNT * 1.0);
    }
}
