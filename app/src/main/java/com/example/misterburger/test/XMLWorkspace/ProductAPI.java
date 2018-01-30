package com.example.misterburger.test.XMLWorkspace;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductAPI {

    @GET("/test/test.xml")
    Call<ResponseBody> downloadFileWithFixedUrl();
}
