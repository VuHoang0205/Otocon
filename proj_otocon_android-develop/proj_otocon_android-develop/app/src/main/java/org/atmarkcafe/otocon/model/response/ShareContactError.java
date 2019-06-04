package org.atmarkcafe.otocon.model.response;

import java.util.List;

public class ShareContactError {
    public List<String> rematch_user_receive_id;
    public List<String> event_id;
    public List<String> rematch_user_request_email;
    public List<String> rematch_user_request_tel;
    public String messageError;

    public String getRematch_user_request_email() {
        return rematch_user_request_email != null ? rematch_user_request_email.get(0) : null;
    }

    public String getRematch_user_request_tel() {
        return rematch_user_request_tel != null ? rematch_user_request_tel.get(0) : null;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getMessageError() {
        return hasError() ? null : messageError;
    }

    public boolean errorEmail() {
        if (hasAllError()) {
            return true;
        }

        return rematch_user_request_email != null;
    }

    public boolean errorTel() {
        if (hasAllError()) {
            return true;
        }
        return rematch_user_request_tel != null;
    }

    public boolean hasError() {
        return rematch_user_request_email != null || rematch_user_request_tel != null;
    }

    public boolean hasAllError() {
        return rematch_user_request_email == null && rematch_user_request_tel == null;
    }
}
