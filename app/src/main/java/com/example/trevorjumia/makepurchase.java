package com.example.trevorjumia;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
    String url5= "http://192.168.43.78/www/html/trevor/carttotal.php";
    String url6= "http://192.168.43.78/www/html/trevor/getcart.php";
    String url7= "http://192.168.43.78/www/html/trevor/viewcart.php";

    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomlistAdapter2 adapter;
    public EditText location,amount;
    public String user,urlmain;
    private ArrayList<product3> statuscheckArrayList;
    public  ListView lvProduct;
    public Button addcart,pay,viewcart;
    final String TAG=this.getClass().getSimpleName();
    String receivedName,prevelage,phone;
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepurchase);
        lvProduct=findViewById(R.id.listview);
viewcart=findViewById(R.id.viewcarts);
location=findViewById(R.id.location);
amount=findViewById(R.id.amount);
addcart=findViewById(R.id.addcart);
        listdata = findViewById(R.id.listdata);
        imageView = findViewById(R.id.thumbnail);
        Intent intent = getIntent();
        String mName,mEmail,mprevelage,mlocation,price;
        adapter = new CustomlistAdapter2(this, movieList);
        pay=findViewById(R.id.pay);
        sessionManager=new SessionManager(this);
        //sessionManager.checkLogin();

   String   receivedName =  intent.getStringExtra("name");

       //revelage =  intent.getStringExtra("previlage");




                //
        user =  intent.getStringExtra("user");
        String urlimage="http://192.168.43.78/www/html/trevor/";
        String suburl=intent.getStringExtra("image");
        String Combined=urlimage.concat(suburl);
       Picasso.get().load(Combined).error(R.mipmap.ic_launcher).into(imageView);

        listdata.setText(receivedName);
        addcat();
        gettotal();
        viewcart();

      //  inflateimage(Combined);

        //enable back Button
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);





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



public void viewcartclick(){
        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewcart();
            }
        });

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

                Intent intent = getIntent();
                String  price =  intent.getStringExtra("price");
                String   receivedName =  intent.getStringExtra("name");
                int cash1=Integer.parseInt(price)*Integer.parseInt(amount.getText().toString());
                String cash=String.valueOf(cash1);
                String locationcart=location.getText().toString();
                HashMap<String,String> user1=sessionManager.getUserDetail();
          String      mName=user1.get(sessionManager.NAME);


                params.put("cartuser", mName);
                params.put("amount", cash);
                params.put("location", locationcart);
                params.put("quantity", amount.getText().toString());
                params.put("item", receivedName);

                return params;
            }
        };
        MySingleton.getInstance(makepurchase .this).addToRequestQueue(stringRequest);






                }

                public void viewcart(){
        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_good_id();
            }
        });
                }




              public void   sendpaymentdata(final String totals){

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




                                String orderno="1025";
                                String account_no="2000";
                                Intent intent = getIntent();
                                String  price =  intent.getStringExtra("price");

                                int cash=Integer.parseInt(price)*Integer.parseInt(amount.getText().toString());
                                phone=intent.getStringExtra("phone");
                                String payamount= String.valueOf(cash);

                                params.put("phone", phone);
                                params.put("amount", totals);
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






public void gettotal(){
        pay.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url5, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("strrrrr", ">>" + response);


            try {




                JSONObject obj = new JSONObject(response);
                if(obj.optString("status").equals("true")){

                    statuscheckArrayList = new ArrayList<>();
                    JSONArray dataArray  = obj.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {

                        product3 playerModel = new product3();
                        JSONObject dataobj = dataArray.getJSONObject(i);

                        playerModel.setId(dataobj.getString("orderTotal"));

                        String index=dataobj.getString("orderTotal");
                       sendpaymentdata(index);


                        statuscheckArrayList.add(playerModel);

                    }

                    for (int i = 0; i < statuscheckArrayList.size(); i++){

                    }





                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
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

            HashMap<String,String> user1=sessionManager.getUserDetail();
            String      mName=user1.get(sessionManager.NAME);

            params.put("name", mName);


            return params;
        }
    };
    MySingleton.getInstance(makepurchase .this).addToRequestQueue(stringRequest);




        }});



}



    public void get_good_id(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url6, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);

                ArrayList<Products> jsonConverter=new JsonConverter<Products>().toArrayList(response, Products.class);

                BindDictionary<Products> dictionary=new BindDictionary<>();
                dictionary.addStringField(R.id.item, new StringExtractor<Products>() {
                    @Override
                    public String getStringValue(Products products, int position) {



                        return products.item;



                    }
                });
                dictionary.addStringField(R.id.quantity, new StringExtractor<Products>() {
                    @Override
                    public String getStringValue(Products products, int position) {



                        return products.quantity ;



                    }
                });

                dictionary.addStringField(R.id.pricepaid, new StringExtractor<Products>() {
                    @Override
                    public String getStringValue(Products products, int position) {



                        return products.price_paid ;



                    }
                });
                dictionary.addStringField(R.id.location, new StringExtractor<Products>() {
                    @Override
                    public String getStringValue(Products products, int position) {



                        return products.location;



                    }
                });


                FunDapter<Products> adapter=new FunDapter<>(getApplicationContext(),jsonConverter,R.layout.product_layout,dictionary);
                lvProduct.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while reading googl", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                HashMap<String,String> user1=sessionManager.getUserDetail();

                String      mName=user1.get(sessionManager.NAME);





                params.put("id", mName);



                return params;
            }
        };
        MySingleton.getInstance(makepurchase.this).addToRequestQueue(stringRequest);


    }



    public void   viewcatt(){
        JsonArrayRequest movieReq = new JsonArrayRequest(url3,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("name"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(obj.getString("source"));
                                movie.setYear(obj.getString("quantity"));
                                movie.setPrice(obj.getString("price"));









                                // Genre is json array



                                // Genre is json array
                                movieList.add(movie);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while reading googl", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();


                HashMap<String,String> user1=sessionManager.getUserDetail();
                String      mName=user1.get(sessionManager.NAME);


                params.put("cartuser", mName);



                return params;
            }
        };
        MySingleton.getInstance(makepurchase .this).addToRequestQueue(movieReq);






    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }




}
