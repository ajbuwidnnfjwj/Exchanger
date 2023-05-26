package com.example.exchanger;

import java.io.IOException;

public class Convert {
    public static String from;
    public static String to;
    public static double value;

    public Convert() { }
    public static double FromToKRW() throws IOException {
        double rate = 0;
        try{
            String s = new String();
            s = Request.getExchangeRate(from).replaceAll(",", "");

            rate = Double.parseDouble(s);
            if (from.equals("JPY") || from.equals("IDR")) {
                rate /= 100;
            }
        } catch (IOException e) {
            System.out.println("Convert failed : From to KRW");
        } catch (NullPointerException nullPointerException) {
            System.out.println("NullPointerException :  : From to KRW");
        }
        return rate * value;
    }

    public static double KRWToResult() throws IOException {
        double rate = 0;
        try{
            String s = new String();
            s = Request.getExchangeRate(to).replaceAll(",", "");

            rate = Double.parseDouble(s);
            if (to.equals("JPY") || to.equals("IDR")) {
                rate /= 100;
            }
        } catch (IOException e) {
            System.out.println("Convert failed : From to KRW");
        } catch (NullPointerException nullPointerException) {
            System.out.println("NullPointerException :  : From to KRW");
        };
        return (1 / rate) * FromToKRW();
    }

    public static String getCode(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        sb.append(s.charAt(1));
        sb.append(s.charAt(2));
        return sb.toString();
    }
}
