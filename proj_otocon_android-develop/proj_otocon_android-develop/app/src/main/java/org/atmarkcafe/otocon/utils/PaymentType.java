package org.atmarkcafe.otocon.utils;

public enum PaymentType {
    none,
    card,
    money;

    public static PaymentType factory(String userRole) {
        try{
            return PaymentType.values()[Integer.parseInt(userRole)];
        }catch (Exception e){
            return none;
        }
    }
}
