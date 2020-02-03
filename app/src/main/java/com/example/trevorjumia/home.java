package com.example.trevorjumia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class home extends AppCompatActivity {
    public Button addgoods,view,addfeeds,viewfeeds;
 public    String receivedName, email,previlage,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addgoods=findViewById(R.id.addproducts);
        view=findViewById(R.id.viewgoods);
        addfeeds=findViewById(R.id.addfeeds);
        viewfeeds=findViewById(R.id.viewgoods22);
        addgoods();
        viewgoods();
        addfeeds();
        viewfeeds();









    }

    public void addgoods(){

        addgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = getIntent();
                String  price =  intent.getStringExtra("price");
                String  prevelage =  intent.getStringExtra("prevelage");
                if(prevelage.equalsIgnoreCase("farmer")){
                Intent i=new Intent(".addimages");
                startActivity(i);
            }
            else{
                    Toast.makeText(getApplicationContext(),"accessible to seller only",Toast.LENGTH_LONG).show();
            }}
        });


    }

    public  void viewgoods(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                receivedName =  intent1.getStringExtra("name");
                email=  intent1.getStringExtra("email");
                previlage =  intent1.getStringExtra("prevelage");
                phone =  intent1.getStringExtra("phone");
                if(previlage.equalsIgnoreCase("buyer")){
                Intent intent=new Intent(getApplicationContext(),itmesdisplay.class);// playerModel.setName(dataobj.getString("name"));
                intent.putExtra("name",receivedName);
                intent.putExtra("email",email);
                intent.putExtra("prevelage",previlage);
                    intent.putExtra("category","all");
                intent.putExtra("phone",phone);
                startActivity(intent);

            }
                else{
                    Toast.makeText(getApplicationContext(),"accessible to buyer only",Toast.LENGTH_LONG).show();
                }}
        });
    }
    public  void addfeeds(){
        addfeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                receivedName =  intent1.getStringExtra("name");
                email=  intent1.getStringExtra("email");
                previlage =  intent1.getStringExtra("prevelage");
                phone =  intent1.getStringExtra("phone");
                if(previlage.equalsIgnoreCase("admin")){
                    Intent intent=new Intent(getApplicationContext(),addfeeds.class);// playerModel.setName(dataobj.getString("name"));
                    intent.putExtra("name",receivedName);
                    intent.putExtra("email",email);
                    intent.putExtra("prevelage",previlage);

                    intent.putExtra("phone",phone);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(),"accessible to admin only",Toast.LENGTH_LONG).show();
                }}
        });
    }
    public  void viewfeeds(){
        viewfeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                receivedName =  intent1.getStringExtra("name");
                email=  intent1.getStringExtra("email");
                previlage =  intent1.getStringExtra("prevelage");
                phone =  intent1.getStringExtra("phone");

                    Intent intent=new Intent(getApplicationContext(),viewfeeds.class);// playerModel.setName(dataobj.getString("name"));
                    intent.putExtra("name",receivedName);
                    intent.putExtra("email",email);
                    intent.putExtra("prevelage",previlage);

                    intent.putExtra("phone",phone);
                    startActivity(intent);


              }
        });
    }

}
