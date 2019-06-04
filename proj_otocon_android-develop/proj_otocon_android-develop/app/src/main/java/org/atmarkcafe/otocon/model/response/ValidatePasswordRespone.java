package org.atmarkcafe.otocon.model.response;

import org.atmarkcafe.otocon.model.ErrorValidatePasswordModel;
import org.atmarkcafe.otocon.model.LoginInfoModel;
import org.atmarkcafe.otocon.model.ValidateUserInfo;

import java.util.List;

public class ValidatePasswordRespone extends OnResponse {
    List<String> data;
    ErrorValidatePasswordModel errors;

    public List<String> getData() {
        return data;
    }

    public ErrorValidatePasswordModel getErrors() {
        return errors!=null?errors:new ErrorValidatePasswordModel();
    }
}
