package com.example.bastian.tienda.http;

/**
 * Created by Bastian on 08-10-2017.
 */


import com.example.bastian.tienda.Tools.Tools;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



public class HttpRequest {
    /**
     * Tag to be shown on logs
     */
    private final String TAG = "NETC"+"HttpRequest";

    /**
     * Flag to post data
     */
    public static final int POST = 0;

    /**
     * Flag to get data
     */
    public static final int GET = 1;

    /**
     * Flag to get data
     */
    public static final int GET2 = 4;

    /**
     * Flag to put data
     */
    public static final int PUT = 2;
    /**
     * Flag to put data
     */
    public static final int POSTJ = 3;

    /**
     * Content type json
     */
    private final String CONTENT_TYPE_JSON = "application/json";

    /**
     * Content type form encoded
     */
    private final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=UTF-8";
    private final String CONTENT_TYPE_FORM2 = "application/x-www-form-urlencoded;charset=ISO-8859-1";


    /**
     * Parameters to send
     */
    private ArrayList<NameValuePair> parameters;

    /**
     * Indicates if request method will be POST or GET
     */
    private int requestMethod;

    /**
     * The url
     */
    private String url;

    /**
     * The operation id
     */
    private String operationId;

    /**
     * Default constructor
     * @param method Method to send parameters. It can be POST or GET
     * @param requestMethod Method to call
     */
    public HttpRequest(String method,String operationId, int requestMethod){
        this.requestMethod = requestMethod;
        this.operationId = operationId;
        url = method;
        parameters = new ArrayList<NameValuePair>();
    }

    /**
     * Adds parameter to send
     * @param name Parameter name
     * @param value Parameter name
     */
    public void addParameter(String name, String value){
        parameters.add(new CustomNameValuePair(name, value));

    }

    public void addParameter(String name, Object value){
        parameters.add(new CustomNameValuePair(name, value));
    }

    /**
     * Build a POST request
     * @return HttpPost request
     * @throws UnsupportedEncodingException
     */
    public HttpPost makeHttpPostRequest() throws UnsupportedEncodingException{
        HttpPost httpPost = new HttpPost(url);


        httpPost.setEntity(getPostParameters());
        httpPost.setHeader("Content-Type", CONTENT_TYPE_FORM);
        Tools.logLine(TAG, "Se añade header" + httpPost.getAllHeaders());

        return httpPost;
    }

    /**
     * Build a GET request
     * @return HttpGet request
     */
    public HttpGet makeHttpGetRequest(){
        HttpGet httpGet = new HttpGet(getGetParameters(url));
        httpGet.setHeader("User-Agent","Custom-Agent 1.0");
        return httpGet;
    }
    /**
     * Build a GET request
     * @return HttpGet request
     */
    public HttpGet makeHttpGetWithParamsRequest(){
        HttpGet httpGet = new HttpGet(getGetParameters(url));
        return httpGet;
    }

    /**
     * Build a PUT request
     * @return HttpPut request
     * @throws UnsupportedEncodingException
     */
    public HttpPost makeHttpPutRequest() throws UnsupportedEncodingException{
        HttpPost httpPut = new HttpPost(url);

        httpPut.setHeader("Content-Type", CONTENT_TYPE_JSON);
        httpPut.setHeader("Accept", CONTENT_TYPE_JSON);

        httpPut.setEntity(getJSONParameters());

        return httpPut;
    }

    /**
     * Obtain parameters for GET request
     * @param url Web service url
     * @return url with parameters
     */
    private String getGetParameters(String url){
        if(parameters == null || parameters.size() == 0){
            return url;
        }

        String params = parametersToString();

        Tools.logLine(TAG, "GET params: " + params);

        return url + params;
    }

    /**
     * Obtain parameters to send as POST
     * @return
     * @throws UnsupportedEncodingException
     */
    private UrlEncodedFormEntity getPostParameters() throws UnsupportedEncodingException{
        UrlEncodedFormEntity encoded = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);

        Tools.logLine(TAG, "POST params: " + parametersToString());

        return encoded;
    }

    private StringEntity getJSONParameters() throws UnsupportedEncodingException{
        JSONObject json = new JSONObject();

        for(int i = 0; i < parameters.size(); i++){
            CustomNameValuePair param = (CustomNameValuePair) parameters.get(i);
            try{

                json.put(param.getName(), (param.isObject()) ?
                        (param.getObject() instanceof JSONObject? (JSONObject) param.getObject() :
                                (JSONArray) param.getObject()) : param.getValue());

            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        StringEntity entity = new StringEntity(json.toString());
        entity.setContentType(CONTENT_TYPE_JSON);
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, CONTENT_TYPE_JSON));

        Tools.logLine(TAG, "JSON Parameters: " + json.toString());

        return entity;
    }

    /**
     * Convert parameters to String
     * @return Parameters as String
     */
    private String parametersToString(){
        StringBuilder params = new StringBuilder();

        for(int i = 0; i < parameters.size(); i++){
            NameValuePair param = parameters.get(i);

            String name = param.getName();
            String value = param.getValue();

            params.append((name.equals("")) ? "" : "/").append(name).append("/").append(value);
        }

        return params.toString().trim();
    }

    /**
     * Obtain request method
     * @return The request method
     */
    public int getRequestMethod() {
        return requestMethod;
    }

    /**
     * Obtain the url
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Obtain the operation id
     * @return
     */
    public String getOperationId(){
        return operationId;
    }

    /**
     * Custom class to store parameters to be sent to ws
     */
    private class CustomNameValuePair implements NameValuePair {

        /**
         * Key name
         */
        private String name;

        /**
         * The value
         */
        private String value;

        /**
         * The value when is not String. It can be JSONObject or JSONArray
         */
        private Object object;

        /**
         * Indicates if the value is Object or String. True if it's a json, false otherwise
         */
        private boolean isObject;

        /**
         * Default constructor
         * @param name Parameter name
         * @param value Parameter value
         */
        public CustomNameValuePair(String name, String value){
            this.name = name;
            this.value = value;
            this.isObject = false;
        }

        /**
         * Default constructor
         * @param name Parameter name
         * @param value Parameter value
         */
        public CustomNameValuePair(String name, Object value){
            this.name = name;
            this.object = value;
            this.isObject = true;
        }

        /**
         * Method to obtain the parameter name
         * @return Parameter name
         */
        public String getName(){
            return name;
        }

        /**
         * Method to obtain parameter value
         * @return The parameter value
         */
        public String getValue(){
            return value;
        }

        /**
         * Method to obtain parameter value when it's not String
         * @return Json value
         */
        public Object getObject(){
            return object;
        }

        /**
         * Method to know if value is String or Json.
         * @return True if it's Json, false otherwise
         */
        public boolean isObject(){
            return isObject;
        }
    }
}