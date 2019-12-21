package com.example.trevorjumia;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class addproducts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final Calendar myCalendar = Calendar.getInstance();
    public FloatingActionButton myFab,myFab2;
    public String name;
    public EditText nametxt;
    public Button save;

    final String TAG=this.getClass().getSimpleName();
    String url = "http://192.168.43.78/www/html/trevor/addproducts.php";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/selectproduct.php";
    String url3 = "http://192.168.43.78/www/html/Naile_progect/deleteproduct.php";
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    private ArrayList<String> names = new ArrayList<String>();
    public String typeexpected;
    private static ProgressDialog mProgressDialog;
    private ArrayList<deleteraw> statuscheckArrayList;
    private ArrayList<productsraw> statuscheckArrayList2;
    private ArrayList<productsconfirm> statuscheckArrayList3;
    public Spinner spinner;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproducts);
        mdrawerLayout=findViewById(R.id.drawerlayout);
        spinner=findViewById(R.id.spinner2rawmmat);
        nametxt=findViewById(R.id.name);
        sessionManager=new SessionManager(this);

        checkconn();
        secs();
        drawable();
        click();
    }


    public void secs(){
        final Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkconn2();
                handler.postDelayed(this, 10000);
            }
        }, 10000);
    }




    public void  checkconn2(){
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {




        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }

    public void  checkconn(){
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {



           // getjson();
           // datepicker();


         save_raw_mat_data();

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }



    public void  datepicker(){
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            }

        };


    }










    public void save_raw_mat_data() {
save=findViewById(R.id.savedata);
Intent i=new Intent(".addimages");
startActivity(i);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nametxt.getText().toString().equals("")) {
                    Toast.makeText(addproducts.this,"You did not enter a name", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);

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
                            name=nametxt.getText().toString();

                            params.put("name", name);

                            return params;
                        }
                    };
                    MySingleton.getInstance(addproducts .this).addToRequestQueue(stringRequest);}}

        });



    }
    public void drawable(){
        ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();
        NavigationView nav_view=findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_rawm_entry:
                Intent i=new Intent(".rawm_entry");
                startActivity(i);
                break;
            case R.id.nav_raw_mixing:
                Intent i2=new Intent(".rawmmixing");
                startActivity(i2);
                break;
            case R.id.nav_get_contents:
                Intent i3=new Intent(".get_products_contents");
                startActivity(i3);
                break;
            case R.id.nav_home:
                Intent i4=new Intent(".home");
                startActivity(i4);
                break;
            case R.id.trans_chuka:
                Intent i8=new Intent(".transist");
                startActivity(i8);
                break;


            case R.id.status_chuka:
                Intent i7=new Intent(".status");
                startActivity(i7);
                break;

            case R.id.addproduct:
                Intent i13=new Intent(".addproducts");
                startActivity(i13);
                break;
            case R.id.productgen:
                Intent i14=new Intent(".packaging");
                startActivity(i14);
                break;
            case R.id.reports:
                Intent i1r=new Intent(".balances");
                startActivity(i1r);
                break;
            case R.id.addusers:
                Intent i2r=new Intent(".addusers");
                startActivity(i2r);
                break;

            case R.id.addraw:
                Intent i2r1=new Intent(".addnewrawmat");
                startActivity(i2r1);
                break;
            case R.id.addcar:
                Intent i2r2=new Intent(".addcar");
                startActivity(i2r2);
                break;
            case R.id.addsupplier:
                Intent i2r21=new Intent(".addsupplier");
                startActivity(i2r21);
                break;
            case R.id.logout:
                sessionManager.logout();
                (this).finish();
                break;

        }
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mdrawerLayout.isDrawerOpen(GravityCompat.START)){
            mdrawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();}

    }

    public void checktext(){
        if (nametxt.getText().toString().equals("")) {
            Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();

        }
    }

    public void clickalalter() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set alert_dialog.xml to alertdialog builder


        // set dialog message
        alertDialogBuilder
                .setTitle("confirm to delete ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        save_raw_mat_data2();

                        Toast.makeText(addproducts.this,"status saved",Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void save_raw_mat_data2() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

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
                name=nametxt.getText().toString();

                params.put("name", typeexpected);

                return params;
            }
        };
        MySingleton.getInstance(addproducts .this).addToRequestQueue(stringRequest);}







    public void click() {

        myFab2 = findViewById(R.id.deleteaction);
        myFab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickalalter();

            }

        });



    }


    private void getjson(){


        showSimpleProgressDialog(this, "Loading...","Fetching Json",true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
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

                                    deleteraw playerModel = new deleteraw();
                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    playerModel.setName(dataobj.getString("productname"));


                                    statuscheckArrayList.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList.size(); i++){
                                    names.add(statuscheckArrayList.get(i).getName());

                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(addproducts.this,simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname, Toast.LENGTH_LONG).show();
                                        typeexpected=slectedname;

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                removeSimpleProgressDialog();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    private void removeSimpleProgressDialog() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }

    private void showSimpleProgressDialog(addproducts addproducts, String s, String fetching_json, boolean b) {
    }
}

