package com.example.trevorjumia;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class makepurchase extends AppCompatActivity {
    TextView listdata;
    ImageView imageView;
    private static final String url = "https://api.androidhive.info/json/movies.json";
    String url2 = "http://192.168.43.78/www/html/trevor/addcart.php";
    String url3= "http://192.168.43.78/www/html/trevor/MpesaTesting/initiate.php";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    public EditText location,amount;
    public String user;
    public Button addcart,pay;
    String receivedName,prevelage,phone;
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepurchase);
location=findViewById(R.id.location);
amount=findViewById(R.id.amount);
addcart=findViewById(R.id.addcart);
        listdata = findViewById(R.id.listdata);
        imageView = findViewById(R.id.thumbnail);
        Intent intent = getIntent();
        String mName,mEmail,mprevelage,mlocation;

        pay=findViewById(R.id.pay);

      receivedName =  intent.getStringExtra("name");
       prevelage =  intent.getStringExtra("previlage");

        String image =  intent.getStringExtra("image");
        user =  intent.getStringExtra("user");

        Picasso.get().load(image).error(R.mipmap.ic_launcher).into(imageView);

        listdata.setText(receivedName);
        addcat();
        makepayment();

        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }
    //getting back to listview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





    public void   save_cart_data(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.d(TAG, response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while reading googl", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
String cashamount=amount.getText().toString();
String locationcart=location.getText().toString();
                String quantity="50";

                params.put("cartuser", user);
                params.put("amount", cashamount);
                params.put("location", locationcart);
                params.put("quantity", quantity);
                params.put("item", receivedName);

                return params;
            }
        };
        MySingleton.getInstance(makepurchase .this).addToRequestQueue(stringRequest);






                }



              public void   sendpaymentdata(){
                  StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          // Log.d(TAG, response);

                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Toast.makeText(getApplicationContext(), "Error while reading googl", Toast.LENGTH_SHORT).show();

                      }
                  }) {
                      @Override
                      protected Map<String, String> getParams() throws AuthFailureError {
                          Map<String, String> params = new HashMap<String,String>();

String amount="40";
                          String orderno="1025";
                          String account_no="2000";
                          Intent intent = getIntent();
                          phone=intent.getStringExtra("phone");

                          params.put("phone", phone);
                          params.put("amount", amount);
                          params.put("orderno", orderno);
                          params.put("quantity", account_no);


                          return params;
                      }
                  };
                  MySingleton.getInstance(makepurchase .this).addToRequestQueue(stringRequest);



              }



                public void addcat(){
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_cart_data();
            }
        });
                }



                public void makepayment(){
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendpaymentdata();
            }
        });
                }
}
