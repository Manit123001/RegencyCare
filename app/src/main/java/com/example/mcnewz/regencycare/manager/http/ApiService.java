package com.example.mcnewz.regencycare.manager.http;

import com.example.mcnewz.regencycare.dao.ItemAcidentCollectionDao;
import com.example.mcnewz.regencycare.dao.ItemCollectionDao;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by MCNEWZ on 08-Feb-17.
 */

public interface ApiService {

    @POST("ReadData.php")
    Call<ItemCollectionDao> loadItemList();

    @POST("icareservice/jsonAcidentShowMain.php")
    Call<ItemAcidentCollectionDao> loadAcidentItemList();

}
