package com.example.bastian.tienda;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bastian on 29-10-2017.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL="http://192.168.0.22/tienda/rest/loginbast.php";
    private Map<String,String> params;
    public LoginRequest(String usuario, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params=new HashMap<>();
        params.put("usuario",usuario);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams(){ return params;}
}
