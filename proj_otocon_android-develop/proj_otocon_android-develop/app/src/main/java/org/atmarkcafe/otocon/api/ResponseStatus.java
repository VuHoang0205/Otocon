package org.atmarkcafe.otocon.api;

public enum  ResponseStatus {
    exception,// 0
    success,// 1
    error_show_popup,// 2
    error_authen,// 3
    error_left_group ;//4
    public static ResponseStatus factory(int index){
        return factory(index + "");
    }

    private static ResponseStatus factory(String s) {
        try{
            return ResponseStatus.values()[Integer.parseInt(s)];
        }catch (Exception e){
            return exception;
        }

    }
}
