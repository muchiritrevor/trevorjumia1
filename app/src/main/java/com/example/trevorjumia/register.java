package com.example.trevorjumia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    String url2 = "http://192.168.43.78/www/html/trevor/register.php";
public EditText loginnametxt,loginpasswordtxt,emailtxt,farmer_buyertxt,phoneno;
public  String loginname,logipass,email,farmer,phone_no;
public Button register1;
public ProgressBar prg;
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

       reg();

    }






    public  void registeruser(){
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

                loginname=loginnametxt.getText().toString();
                logipass=loginpasswordtxt.getText().toString();
                email=emailtxt.getText().toString();
                farmer=farmer_buyertxt.getText().toString();
                phone_no=phoneno.getText().toString();
                params.put("username", loginname);
                params.put("password", logipass);
                params.put("email", email);
                params.put("privelage", farmer);
                params.put("phone_no", phone_no);


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
                registeruser();




                Toast.makeText(getApplicationContext(),"successfully registered",Toast.LENGTH_SHORT).show();
               register.this.finish();
            }
        });
    }



}





