package com.example.trevorjumia;





import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class itmesdisplay extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
public Button cartstart;
    // Movies json url
    private static final String url = "https://api.androidhive.info/json/movies.json";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/addcart.php";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    SessionManager sessionManager;
    public  String userdetails,receivedName,email,user,phone,previlage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itmesdisplay);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);

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
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
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
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(((Number) obj.get("rating"))
                                        .doubleValue());
                                movie.setYear(obj.getInt("releaseYear"));










                                // Genre is json array
                                arr1[i][0]=obj.getString("title");
                                arr1[i][1]=obj.getString("image");
                                arr1[i][2]=obj.getString("rating");

                                System.out.println( arr1[i][2]);
                                //System.out.println( arr[0][0]);


                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);

                                // adding movie to movies array
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

    }
}
