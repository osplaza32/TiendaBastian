package com.example.bastian.tienda;

import android.content.Context;

import com.example.bastian.tienda.http.HttpRequest;
import com.example.bastian.tienda.http.HttpTask;
import com.example.bastian.tienda.http.OnHttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Bastian on 08-10-2017.
 */

public class Updater {
    /**
     * Singleton instance
     */
    protected static Updater updater;

    /**
     * Default constructor
     */
    public static Updater getInstance(){
        if(updater == null)
            updater = new Updater();

        return updater;
    }

    /**
     * Make a post request
     * @param method Method to call
     * @param parameters Parameters to send
     * @param context The context
     */
    public void post(String method, String operationId, Map<String, Object> parameters, Context context, OnHttpResponse postExecuteAction){
        HttpRequest request = new HttpRequest(method, operationId, HttpRequest.POST);
        makeRequest(request, parameters, context, postExecuteAction);
    }

    /**
     * Make a get request
     * @param method Method to call
     * @param parameters Parameters to send
     * @param context The context
     */
    public void get(String method, String operationId, Map<String, Object> parameters, Context context, OnHttpResponse postExecuteAction){
        HttpRequest request = new HttpRequest(method, operationId, HttpRequest.GET);
        makeRequest(request, parameters, context, postExecuteAction);
    }
    /**
     * Make a get request
     * @param method Method to call
     * @param parameters Parameters to send
     * @param context The context
     */
    public void get2(String method, String operationId, Map<String, Object> parameters, Context context, OnHttpResponse postExecuteAction){
        HttpRequest request = new HttpRequest(method, operationId, HttpRequest.GET2);
        makeRequest(request, parameters, context, postExecuteAction);
    }

    /**
     * Make a put request
     * @param method Method to call
     * @param parameters Parameters to send
     * @param context The context
     */
    public void put(String method, String operationId, Map<String, Object> parameters, Context context, OnHttpResponse postExecuteAction){
        HttpRequest request = new HttpRequest(method, operationId, HttpRequest.PUT);
        makeRequest(request, parameters, context, postExecuteAction);
    }

    /**
     * Build a request and call web service
     * @param request
     * @param parameters
     * @param context
     */
    private void makeRequest(HttpRequest request, Map<String, Object> parameters, Context context, OnHttpResponse postExecuteAction){
        if(parameters != null){
            for(String key : parameters.keySet()){
                Object value = parameters.get(key);

                if(value instanceof String){
                    request.addParameter(key, (String) parameters.get(key));

                } else if(value instanceof JSONObject){
                    request.addParameter(key, (JSONObject) value);

                } else if(value instanceof JSONArray){
                    request.addParameter(key, (JSONArray) parameters.get(key));
                }


            }
        }

        HttpTask task = new HttpTask(request, context, postExecuteAction);
        task.execute();
    }
}