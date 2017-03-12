package com.example.mcnewz.regencycare.manager;

import android.content.Context;

import com.example.mcnewz.regencycare.dao.ItemAcidentCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NewsAcidentsListManager {


    private Context mContext;
    private ItemAcidentCollectionDao dao;

    public NewsAcidentsListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ItemAcidentCollectionDao getDao() {
        return dao;
    }

    public void setDao(ItemAcidentCollectionDao dao) {
        this.dao = dao;
    }
}
