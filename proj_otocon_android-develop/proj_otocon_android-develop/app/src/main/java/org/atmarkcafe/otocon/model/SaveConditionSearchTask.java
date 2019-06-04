package org.atmarkcafe.otocon.model;

import android.content.Context;
import android.os.AsyncTask;

import org.atmarkcafe.otocon.model.parameter.ConditionSearchParams;

public class SaveConditionSearchTask extends AsyncTask<ConditionSearchParams, Void, ConditionSearchParams> {

    private Context context;

    public SaveConditionSearchTask(Context context) {
        this.context = context;
    }

    @Override
    protected ConditionSearchParams doInBackground(ConditionSearchParams... conditionSearches) {
        DBManager.saveConditionSearch(context, conditionSearches[0]);
        return null;
    }
}
