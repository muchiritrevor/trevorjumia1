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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public Button login,togeg;
    private ArrayList<arrivals_session> statuscheckArrayList;
    EditText logintxt,passtxt;
    String loginstr,passstr;
    String url2 = "http://192.168.43.78/www/html/trevor/login.php";
public ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.button);
       logintxt=findViewById(R.id.loginname);
       passtxt=findViewById(R.id.loginpassword);
       loading=findViewById(R.id.progressBar);
       togeg=findViewById(R.id.registerbtn);
        loading.setVisibility(View.GONE);
       login();
       reg();
    }

public void reg(){

        togeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(".register");
                startActivity(i);
            }
        });
}



    public void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logintxt.getText().toString().equalsIgnoreCase("") ||passtxt.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"check the credentilas",Toast.LENGTH_SHORT).show();
                }
                else{
                    save_raw_mat_dat();
                    loading.setVisibility(View.VISIBLE);
                }}
        });
    }

    public void save_raw_mat_dat() {


        login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
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

                                    // arrivals_session playerModel = new arrivals_session();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    String name1=dataobj.getString("username").trim();
                                    String email1=dataobj.getString("email").trim();
                                    String phone=dataobj.getString("phone_no").trim();
                                    String prevelage=dataobj.getString("privelage").trim();


                                    Intent intent=new Intent(getApplicationContext(),home.class);// playerModel.setName(dataobj.getString("name"));
                                    intent.putExtra("name",name1);
                                    intent.putExtra("email",email1);
                                    intent.putExtra("prevelage",prevelage);
                                    intent.putExtra("phone",phone);
                                    startActivity(intent);
                                   // playerModel.setEmail(dataobj.getString("email"));



                                    //statuscheckArrayList.add(playerModel);

                                }







                            }
                            else {
                                Toast.makeText(getApplicationContext(),"wronng credentials contact admin or try again",Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while reading nertwork", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                loginstr=logintxt.getText().toString();
                passstr=passtxt.getText().toString();

                params.put("email", loginstr);
                params.put("password", passstr);

                return params;
            }
        };
        MySingleton.getInstance(MainActivity .this).addToRequestQueue(stringRequest);}
}
