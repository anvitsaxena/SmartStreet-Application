package com.example.asaxena.smarttreeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    //Heroku Cloud URL for storing values in backend server
    String BASE_URL = "https://nameless-bastion-79278.herokuapp.com/";
    static final String[] Headings = new String[] {
           "About", "Photo/Video", "Add User Profile","Comment", "Share","Nearby"};

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting Title on the Action Bar
        getSupportActionBar().setTitle("Smart Street Application");
        //sets the grid view
        gridView = (GridView) findViewById(R.id.gridView1);

        //this is used to create a new ImageAdapter to display the text of the items
        gridView.setAdapter(new ImageAdapter(this, Headings));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.grid_item_label))
                                .getText(), Toast.LENGTH_SHORT).show();

                Intent intent;
                String ItemSelected= ((TextView) v.findViewById(R.id.grid_item_label)).getText().toString();

                switch (ItemSelected){
                    //Control is sent to BarcodeActivity in the below case
                    case "About" :
                        intent = new Intent(getApplicationContext(), BarcodeActivity.class);
                        intent.putExtra("data"," ");
                        startActivity(intent);
                        break;
                    //Control is sent to BarcodeActivity in the below case
                    /*case "Interact" :
                        intent = new Intent(getApplicationContext(), BarcodeActivity.class);
                        intent.putExtra("data"," ");
                        startActivity(intent);
                        break;*/
                    //Control is sent to MapsActivity in the below case
                    case "Nearby" :
                        intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                        break;
                    //Control is sent to CameraActivity in the below case
                    case "Photo/Video" :
                        intent = new Intent(getApplicationContext(), CameraActivity.class);
                        startActivity(intent);
                        break;
                    //Control is sent to ShareActivity in the below case
                    case "Share" :
                        intent = new Intent(getApplicationContext(), ShareActivity.class);
                        startActivity(intent);
                        break;
                    //Control is sent to CommentActivity in the below case
                    case "Comment" :
                        intent = new Intent(getApplicationContext(), CommentActivity.class);
                        startActivity(intent);
                        break;
                    //Control is sent to AddUserActivity in the below case
                    case "Add User Profile" :
                        intent = new Intent(getApplicationContext(), AddUserActivity.class);
                        intent.putExtra("mobile","");
                        intent.putExtra("email","");
                        intent.putExtra("name","");
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}
