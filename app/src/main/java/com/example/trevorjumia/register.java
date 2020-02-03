package com.example.trevorjumia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    String url2 = "http://192.168.60.188/www/html/trevor/register.php";
public EditText loginnametxt,loginpasswordtxt,emailtxt,phoneno;
Spinner farmer_buyertxt;
public  String loginname,logipass,email,farmer,phone_no;
public Button register1;
public ProgressBar prg;
public  Spinner spinner,categorysp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginnametxt=findViewById(R.id.loginname);
        loginpasswordtxt=findViewById(R.id.loginpassword);
        emailtxt=findViewById(R.id.email);
        farmer_buyertxt=findViewById(R.id.farmer_buyer);
        phoneno=findViewById(R.id.phoneno);
        prg=findViewById(R.id.progressBar);
        prg.setVisibility(View.GONE);

       register1=findViewById(R.id.registerbtn);
 spinner = (Spinner) findViewById(R.id.farmer_buyer);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

       reg();

    }






    public  void registeruser( final String farmer2){
prg.setVisibility(View.VISIBLE);
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

                email=emailtxt.getText().toString();
                loginname=loginnametxt.getText().toString();
                logipass=loginpasswordtxt.getText().toString();
                phone_no=phoneno.getText().toString();
                params.put("username", loginname);
                params.put("password", logipass);
                params.put("email", email);
                params.put("privelage", farmer2);
                params.put("phone_no", phone_no);
               // params.put("category", category);


                return params;
            }
        };
        MySingleton.getInstance(register .this).addToRequestQueue(stringRequest);
prg.setVisibility(View.GONE);

    }

    public void reg(){
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        final   String       farmer2=(String)parentView.getItemAtPosition(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

                email=emailtxt.getText().toString();
                loginname=loginnametxt.getText().toString();
                logipass=loginpasswordtxt.getText().toString();
                phone_no=phoneno.getText().toString();




                if(!email.contains("@")){
                    Toast.makeText(register.this, "please check email", Toast.LENGTH_SHORT).show();
                }
                else if(loginname.equalsIgnoreCase("")){
                    Toast.makeText(register.this, "check the user name", Toast.LENGTH_SHORT).show();
                }
                else if(logipass.equalsIgnoreCase("")){
                    Toast.makeText(register.this, "please check your password", Toast.LENGTH_SHORT).show();
                }
                else if(phone_no.equalsIgnoreCase("")){
                    Toast.makeText(register.this, "check the phone number", Toast.LENGTH_SHORT).show();

                }
                else if(!phone_no.startsWith("254")){
                    Toast.makeText(register.this, "phone number must start with 254", Toast.LENGTH_SHORT).show();
                }
                else {
                    registeruser(spinner.getSelectedItem().toString());
                    Toast.makeText(getApplicationContext(),"successfully registered",Toast.LENGTH_SHORT).show();
                    register.this.finish();
                }


            }
        });
    }



}





