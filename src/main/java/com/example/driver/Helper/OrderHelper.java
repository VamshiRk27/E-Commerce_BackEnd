package com.example.driver.Helper;

public class OrderHelper {
    public static String generateMaskedCard(String cardNumber){
        String maskedCardNumber="";
        for(int i=0;i<cardNumber.length()-4;i++){
            maskedCardNumber+="X";
        }
        maskedCardNumber+=cardNumber.substring((cardNumber.length()-4));
        return maskedCardNumber;
    }
}
