package com.example.trevorjumia;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class addimages extends AppCompatActivity {
    public Button upload,choose;
    public ImageView img;
    final int CODE_GALLERY_REQUEST=999;
    Bitmap bitmap;
    String urlupload="http://192.168.60.188/www/html/trevor/upload.php";
    ProgressDialog progressbar;
    public EditText sourcetxt,pricetxt,quantitytxt,productnametxt;
Spinner descriptiontxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addimages);
        upload=findViewById(R.id.upload);
        choose=findViewById(R.id.choose);
        img=findViewById(R.id.imageupload);
        descriptiontxt=findViewById(R.id.description);
        sourcetxt=findViewById(R.id.source);
        pricetxt=findViewById(R.id.price);
        quantitytxt=findViewById(R.id.quantity);
        productnametxt=findViewById(R.id.productname);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.planets_array2, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        descriptiontxt.setAdapter(adapter);

choose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ActivityCompat.requestPermissions(addimages.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_GALLERY_REQUEST);
    }
});
upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressbar=new ProgressDialog(addimages.this);
        progressbar.setTitle("Uploading");
        progressbar.setMessage("please wait......");
        progressbar.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbar.dismiss();
                Toast.makeText(getApplicationContext(),"successfully uploaded details",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.dismiss();
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();

String imageData=imageToString(bitmap);
              descriptiontxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        final   String       farmer2=(String)parentView.getItemAtPosition(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
                String descriptionstr,sourcestr,pricestr,quantitystr,productnamestr;

                sourcestr=sourcetxt.getText().toString();
                pricestr=pricetxt.getText().toString();
                quantitystr=quantitytxt.getText().toString();
                productnamestr=productnametxt.getText().toString();
                params.put("image", imageData);
                params.put("source", sourcestr);
                params.put("price", pricestr);
                params.put("quantity", quantitystr);
                params.put("name",productnamestr);
                params.put("description",descriptiontxt.getSelectedItem().toString());

                return params;
            }
        };
        MySingleton.getInstance(addimages .this).addToRequestQueue(stringRequest);
        Intent i=new Intent(addimages.this,home.class);
        startActivity(i);
    }
});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if(requestCode==CODE_GALLERY_REQUEST){
             if(grantResults.length> 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                 Intent intent=new Intent(Intent.ACTION_PICK);
                 intent.setType("image/*");
                 startActivityForResult(Intent.createChooser(intent,"select imaege"),CODE_GALLERY_REQUEST);

             }
             else{
                 Toast.makeText(getApplicationContext(),"you have no permission",Toast.LENGTH_LONG).show();
             }
         return;
         }

             super.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode==CODE_GALLERY_REQUEST && resultCode== RESULT_OK && data != null){
           Uri filePath=data.getData();

           try{
               InputStream inputStream=getContentResolver().openInputStream(filePath);
               bitmap= BitmapFactory.decodeStream(inputStream);
               img.setImageBitmap(bitmap);

           } catch (FileNotFoundException e){
               e.printStackTrace();
           }

       }
       super.onActivityResult(requestCode,resultCode,data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes=outputStream.toByteArray();


                String encodeImage=Base64.encodeToString(imageBytes,Base64.DEFAULT);
                 return encodeImage;
    }
}
