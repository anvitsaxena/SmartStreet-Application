package com.example.asaxena.smarttreeapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.example.asaxena.smarttreeapplication.AppConfig;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Anvit on 9/19/17.
 */

public class CommentActivity extends AppCompatActivity {


    Button ok, retrive;
    EditText name, comment;

    String BASE_URL = "https://nameless-bastion-79278.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set layout to the activity
        setContentView(R.layout.activity_comment);

        ok = (Button) findViewById(R.id.btn_comment_ok);
        retrive = (Button) findViewById(R.id.btn_comment_retrive);
        name = (EditText) findViewById(R.id.comment_name);
        comment = (EditText) findViewById(R.id.comment_comment);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestAdapter.Builder builder = new RestAdapter.Builder()
                        .setEndpoint(BASE_URL) //Setting the Root URL
                        .setClient(new OkClient(new OkHttpClient()));

                RestAdapter adapter = builder.build();

                //get reference of the interface
                AppConfig.addcomment api = adapter.create(AppConfig.addcomment.class);

                //map for data
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", name.getText().toString());
                map.put("comment", comment.getText().toString());
                Log.d("check map", map.toString());

                // call method to store data
                // pass map and Callback as a parameter
                api.add_comment(
                        map,
                        new Callback<Response>() {
                            @Override
                            public void success(Response result, Response response) {

                                try {

                                    //retrive json response
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                                    String resp;
                                    resp = reader.readLine();

                                    // get the value of success fron json response
                                    Log.d("success", "" + resp);

                                    JSONObject jObj = new JSONObject(resp);
                                    int success = jObj.getInt("success");

                                    if (success == 1) {

                                        Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                        ;

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (IOException e) {
                                    Log.d("Exception", e.toString());
                                } catch (JSONException e) {
                                    Log.d("JsonException", e.toString());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestAdapter.Builder builder = new RestAdapter.Builder()
                        .setEndpoint(BASE_URL) //Setting the Root URL
                        .setClient(new OkClient(new OkHttpClient()));

                RestAdapter adapter = builder.build();

                //get reference if the interface
                AppConfig.getcomment api = adapter.create(AppConfig.getcomment.class);

                // call api method
                api.get_comment(name.getText().toString(), new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        // The network call was a success and we got a response
                        // TODO: use the repository list and display it

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            //retrive data from json response
                            JSONArray jObj = new JSONArray(resp);
                            JSONObject obj = (JSONObject) jObj.get(0);
                            String comm = obj.getString("comment");

                            if (comm != null) {
                                comment.setText(comm);

                            } else {
                                //if data not available
                                Toast.makeText(getApplicationContext(), "Feedback data not available", Toast.LENGTH_SHORT).show();
                            }


                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Feedback data not available", Toast.LENGTH_SHORT).show();
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // the network call was a failure or the server send an error
                        // TODO: handle error
                    }
                });
            }
        });
    }
}