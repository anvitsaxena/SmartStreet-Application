package com.example.asaxena.smarttreeapplication;

/**
 * Created by asaxena on 10/22/2017.
 */

import java.util.HashMap;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public class AppConfig {

    //Inserts comments entered by user into the database.
    public interface addcomment {
        @POST("/userlist")
        void add_comment(
                @Body HashMap<String, Object> body,
                Callback<Response> callback);

    }

    //Retrieves comments entered by user from the database using name.
    public interface getcomment {
        @GET("/userlist/{name}")
        void get_comment(
                @Path("name") String name,
                Callback<Response> callback);


    }

    //User registration infomration is added into the database.
    public interface adduserprofile {
        @POST("/userprofile")
        void add_user_profile(
                @Body HashMap<String, Object> body,
                Callback<Response> callback);


    }


}
