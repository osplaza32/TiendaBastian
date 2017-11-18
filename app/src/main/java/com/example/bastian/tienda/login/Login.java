package com.example.bastian.tienda.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bastian.tienda.BaseActivity;
import com.example.bastian.tienda.Constants;
import com.example.bastian.tienda.LoginRequest;
import com.example.bastian.tienda.MainActivity;
import com.example.bastian.tienda.R;
import com.example.bastian.tienda.Registro;
import com.example.bastian.tienda.Tools.Tools;
import com.example.bastian.tienda.Updater;
import com.example.bastian.tienda.http.HttpResponse;
import com.example.bastian.tienda.http.OnHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends BaseActivity implements View.OnClickListener, OnHttpResponse {

    TextView tv_registrar;

    Button login;
    EditText usuario, passwordd;
    JSONArray ja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = (EditText)findViewById(R.id.user);
        passwordd = (EditText)findViewById(R.id.pass);
        login = (Button)findViewById(R.id.login);

        tv_registrar= (TextView) findViewById(R.id.btn_registrar);

        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(Login.this, Registro.class);
                Login.this.startActivity(intentReg);
            }
        });

        //CONEXION *O*


        login.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {

               ConsultaPass("https://bastianvastian.000webhostapp.com/rest/consultausuario.php?user="+usuario.getText().toString());

            }
        });
    }

    private void ConsultaPass(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);
                    if(contra.equals(passwordd.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"verifique su contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"El usuario no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);



    }
}
