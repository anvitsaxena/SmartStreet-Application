package com.example.asaxena.smarttreeapplication;

/**
 * Created by asaxena on 10/22/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.asaxena.smarttreeapplication.AppConfig;
import com.google.zxing.Result;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class AddUserActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    Button reg, scan;
    EditText name, email, mobile;

    //This is the URL to my backend server I created at Heroku cloud
    String BASE_URL = "https://nameless-bastion-79278.herokuapp.com/";

    private ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //All the below are the references to my .xml file in the layout folder
        reg = (Button) findViewById(R.id.btn_register);
        scan = (Button) findViewById(R.id.btn_scan_reg);
        name = (EditText) findViewById(R.id.profile_name);
        email = (EditText) findViewById(R.id.profile_email);
        mobile = (EditText) findViewById(R.id.profile_mobile);

        if(getIntent()!= null) {

            //Getting user info from the bundle
            Bundle extras = getIntent().getExtras();
            String data_name = extras.getString("name");
            String data_mobile = extras.getString("mobile");
            String data_email = extras.getString("email");

            //Setting user information in the below edit texts.
            name.setText(data_name);
            mobile.setText(data_mobile);
            email.setText(data_email);
        }
    }

    //When the register button is clicked by the user
    public void registration(View v) {

        //connection with BASE_URL
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .setClient(new OkClient(new OkHttpClient()));

        //getting the reference of the adapter
        RestAdapter adapter = builder.build();

        //getting reference of adduserprofile interface
        AppConfig.adduserprofile api = adapter.create(AppConfig.adduserprofile.class);
        // create hashmap to store the user info into the database like name, mobile number, email id
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", name.getText().toString());
        map.put("mobile", mobile.getText().toString());
        map.put("email", email.getText().toString());

        //api call method to insert user registration details
        api.add_user_profile(
                map,
                new Callback<Response>() {

                    //when api called successfully
                    @Override
                    public void success(Response result, Response response) {

                        try {
                            //getting response in JSON format
                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if(success == 1){
                                //if data is stored successfully
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();

                            } else{
                                //if data insertion failed
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
                }
        );
    }
    //when scan button is clicked
    public void scan_reg(View view){

        //barcode reader view
        zXingScannerView =new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        //starting camera
        zXingScannerView.startCamera();

    }

    @Override
    public void handleResult(Result result) {

        //to handle the values of name, email and mobile number
        StringTokenizer token = new StringTokenizer(result.getText(),";");

        String[] res = new String[3];
        int i=0;

        while(token.hasMoreElements())
        {
            String s = token.nextToken();

            int index = s.lastIndexOf(":");
            res[i] = s.substring(index+1);
            i++;
        }
        //after barcode is captured, redirect to the AddUserctivity.java class.
        Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);

        //sends user data to the AddUserActivity activity
        intent.putExtra("mobile",res[0]);
        intent.putExtra("email",res[1]);
        intent.putExtra("name",res[2]);

        //starts activity
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //camera stopped during pause position of the activity.
        zXingScannerView.stopCamera();
    }
}
