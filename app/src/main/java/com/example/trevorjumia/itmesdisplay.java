package com.example.trevorjumia;





import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.R.layout.simple_spinner_item;

public class itmesdisplay extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
public Button cartstart;
    // Movies json url
    private static final String url = "https://api.androidhive.info/json/movies.json";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/addcart.php";
    String url3 = "http://192.168.43.78/www/html/trevor/getpictures.php";
    String url4 = "http://192.168.43.78/www/html/trevor/indexcart.php";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    private ArrayList<products2> statuscheckArrayList;
    FloatingActionButton ftn;
   public  SessionManager sessionManager;
    public  String userdetails,receivedName,email,user,phone,previlage;
    private ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itmesdisplay);


        sessionManager=new SessionManager(this);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);
        ftn=findViewById(R.id.cart_btn);
startcart();
//secs();
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        Intent intent = getIntent();
        String mName,mEmail,mprevelage,mlocation;



      receivedName =  intent.getStringExtra("name");
         email=  intent.getStringExtra("email");
     previlage =  intent.getStringExtra("previlage");
      phone =  intent.getStringExtra("phone");

        Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_SHORT).show();


        // changing action bar color

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url3,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        final String [][] arr1 = new String [response.length()][20];
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("name"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(obj.getString("source"));
                                movie.setYear(obj.getString("diffrence"));
                                movie.setPrice(obj.getString("price"));
                                movie.setdescription(obj.getString("description"));








                                // Genre is json array
                                arr1[i][0]=obj.getString("name");
                                arr1[i][1]=obj.getString("image");
                                arr1[i][2]=obj.getString("source");
                                arr1[i][3]=obj.getString("price");
                                arr1[i][4]=obj.getString("diffrence");
                                arr1[i][5]=obj.getString("id");


                                System.out.println( arr1[i][2]);
                                //System.out.println( arr[0][0]);


                                // Genre is json array
                                movieList.add(movie);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                                        Intent intent1= getIntent();
                                        phone =  intent1.getStringExtra("phone");
                                        Intent intent = new Intent(getApplicationContext(),makepurchase.class);
                                        intent.putExtra("name",arr1[position][0]);
                                        intent.putExtra("image",arr1[position][1]);
                                        intent.putExtra("price",arr1[position][3]);
                                        intent.putExtra("diffrence",arr1[position][4]);
                                        intent.putExtra("id",arr1[position][5]);
                                        intent.putExtra("user",receivedName);
                                        intent.putExtra("phone",phone);
                                        intent.putExtra("previlage",previlage);
                                        startActivity(intent);
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }



    public void startcart(){
ftn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {




                            JSONObject obj = new JSONObject(response);
        if(obj.optString("status").equals("true")){

            statuscheckArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {

                products2 playerModel = new products2();
                JSONObject dataobj = dataArray.getJSONObject(i);

                playerModel.setId(dataobj.getString("id"));

String index=dataobj.getString("id");
Intent k=getIntent();

               String name=  k.getStringExtra("name");
                String cartidentifier=index+name;

                String name1="",email1="",prevelage1="",location1="";
                sessionManager.createSession(cartidentifier,email1,prevelage1,location1);
               Toast.makeText(itmesdisplay.this, name+index, Toast.LENGTH_SHORT).show();
                statuscheckArrayList.add(playerModel);

            }

            for (int i = 0; i < statuscheckArrayList.size(); i++){

            }





        }

    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    },
            new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //displaying the error in toast if occurrs
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });

    // request queue
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            requestQueue.add(stringRequest);






    }
});
    }


//    public void colorchange(){
//        if( ftn.getText().toString()=="start"){
//           // cartstart.setVisibility(View.GONE);
//            cartstart.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            cartstart.setText("cart");
//        }
//        else{
//           // cartstart.setVisibility(View.VISIBLE);
//            cartstart.setText("start");
//            cartstart.setBackgroundColor(getResources().getColor(R.color.list_row_end_color));
//        }
//
//
//    }



//    public void secs(){
//        final Handler handler = new Handler();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                colorchange();
//                handler.postDelayed(this, 200);
//            }
//        }, 10000);
  //  }
}
