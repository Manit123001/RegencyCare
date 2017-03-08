package com.example.mcnewz.regencycare.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mcnewz.regencycare.dao.ItemCollectionDao;
import com.example.mcnewz.regencycare.dao.ItemDao;
import com.example.mcnewz.regencycare.manager.AlertListManager;
import com.example.mcnewz.regencycare.view.AlertItem;

import java.text.SimpleDateFormat;

/**
 * Created by MCNEWZ on 08-Feb-17.
 */

public class AlertListAdapter extends BaseAdapter {
   ItemCollectionDao dao;

    public void setDao(ItemCollectionDao dao) {
        this.dao = dao;
    }
    @Override
    public int getCount() {

        if(dao == null){
            return 0;
        }
        if (dao.getData() == null)
            return 0;

        return dao.getData().size() ;
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlertItem item ;
        if (convertView != null) {
            item = (AlertItem) convertView;
        } else {
            item = new AlertItem(parent.getContext());
        }

        ItemDao dao = (ItemDao) getItem(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        item.setTitle(dao.getLng());
        item.setName(dao.getSubject());
        item.setDescription(dao.getDetail());
        item.setImageUrl(dao.getPhoto());
        item.setDate(dateFormat.format(dao.getCreate_date()));

        return item;
    }
}
