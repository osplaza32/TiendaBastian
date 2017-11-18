package com.example.bastian.tienda;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bastian.tienda.Entidades.adapterProduct;
import com.example.bastian.tienda.Entidades.producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        final ListView lv= (ListView) findViewById(R.id.lista_productos);
        Log.d("TAG", "onCreate: Main");
        String url ="https://bastianvastian.000webhostapp.com/rest/getProduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "onResponse: ");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<producto>>(){}.getType();
                        ArrayList<producto> contactList = gson.fromJson(response, type);
                        lv.setAdapter(new adapterProduct(MainActivity.this,contactList));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }


}
