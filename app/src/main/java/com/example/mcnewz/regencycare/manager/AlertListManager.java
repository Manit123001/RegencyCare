package com.example.mcnewz.regencycare.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mcnewz.regencycare.dao.ItemCollectionDao;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AlertListManager {


    private Context mContext;
    private ItemCollectionDao dao;

    public AlertListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(ItemCollectionDao dao) {
        this.dao = dao;
    }
}
