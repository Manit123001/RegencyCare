package com.example.mcnewz.regencycare.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class ItemAcidentCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("result") private List<ItemAcidentDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ItemAcidentDao> getData() {
        return data;
    }

    public void setData(List<ItemAcidentDao> data) {
        this.data = data;
    }
}
