package org.atmarkcafe.otocon.function.notification;

public enum  PushType {
    none,
    newlist,
    couponList,
    rematchDetail,
    rematchListRequest,
    rematchTopPage,
    partyDetail,
    notice;

    public static PushType factory(String str){
        try {
            return PushType.values()[Integer.parseInt(str)];
        }catch (Exception e){
            return none;
        }
    }

}
