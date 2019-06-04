package org.atmarkcafe.otocon.utils;

public enum UserRole {
    none,
    nomarl,
    premium;

    public static UserRole factory(String userRole) {
        try{
            return UserRole.values()[Integer.parseInt(userRole)];
        }catch (Exception e){

        }
        return nomarl;
    }

    public static UserRole factory(int userRole) {
        try{
            return UserRole.values()[userRole];
        }catch (Exception e){

        }
        return nomarl;
    }
}
