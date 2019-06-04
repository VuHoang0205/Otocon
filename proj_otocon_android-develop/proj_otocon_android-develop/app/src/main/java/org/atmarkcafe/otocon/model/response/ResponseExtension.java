package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseExtension <Data> extends OnResponse{
    @SerializedName("data")
    public List<Data> data;


    @SerializedName("errors")
    public Map<String,List<String> >errors;

    public List<Data> getDataList() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public boolean hasError(String key) {
        return errors != null && errors.containsKey(key);
    }

    public String getErrorMessage(String key) {
        if (errors != null && errors.containsKey(key)) {
            List<String> messageList = errors.get(key);

            if (messageList != null && messageList.size() > 0) {
                return messageList.get(0);
            }
        }

        return null;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
