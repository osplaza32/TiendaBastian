package com.example.bastian.tienda.http;

import android.content.Context;
import android.os.AsyncTask;

import com.example.bastian.tienda.BaseActivity;
import com.example.bastian.tienda.Constants;
import com.example.bastian.tienda.R;
import com.example.bastian.tienda.Session;
import com.example.bastian.tienda.Tools.Tools;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bastian on 08-10-2017.
 */

public class HttpTask extends AsyncTask<Void, Void, HttpResponse> {

    /**
     * Tag to be shown on log
     */
    private final String TAG = "NETC"+"HttpTask";

    /**
     * On post execute
     */
    private OnHttpResponse postExecuteAction;

    /**
     * Http request, contains all of parameters and configurations
     */
    private HttpRequest request;

    /**
     * Application context
     */
    private Context context;

    private String error;


    public HttpTask(HttpRequest request, Context context, OnHttpResponse postExecuteAction){
        this.request = request;
        this.postExecuteAction = postExecuteAction;
        this.context = context;
    }

    @Override
    protected HttpResponse doInBackground(Void... params) {

        HttpClient httpclient = new DefaultHttpClient(getHttpParams());
        org.apache.http.HttpResponse connectionResponse = null;
        HttpResponse response = null;

        Tools.logLine(TAG, "Call service: " + request.getUrl());

        try {
            switch (request.getRequestMethod()) {

                case HttpRequest.POST:
                    Tools.logLine(TAG, "Metodo: POST");

                    List<Cookie> cookiesPost = Session.getInstance().getCookie();
                    if (cookiesPost != null) {
                        CookieStore storesPost = new BasicCookieStore();
                        for (Cookie cookie : cookiesPost) {

                            Tools.logLine(TAG, "Se añade cookie : " + cookie.toString());
                            storesPost.addCookie(cookie);
                        }
                        ((AbstractHttpClient) httpclient).setCookieStore(storesPost);
                    }

                    Header[] headers = request.makeHttpPostRequest().getAllHeaders();
                    HashMap<String, String> result = new HashMap<String, String>(headers.length);
                    for (Header header : headers) {
                        result.put(header.getName(), header.getValue());
                        Tools.logLine(TAG, "header name " + header.getName() + "header value " + header.getValue());
                    }

                    connectionResponse = httpclient.execute(request.makeHttpPostRequest());

                    break;

                case HttpRequest.GET:
                    Tools.logLine(TAG, "Metodo: GET");

                    List<Cookie> cookies = Session.getInstance().getCookie();

                    CookieStore store = new BasicCookieStore();

//                    for (Cookie cookie : cookies) {

  //                      Tools.logLine(TAG, "Se añade cookie : " + cookie.toString());
      //                  store.addCookie(cookie);
    //                }

         //           ((AbstractHttpClient) httpclient).setCookieStore(store);

                    connectionResponse = httpclient.execute(request.makeHttpGetRequest());


                    break;

                case HttpRequest.PUT:
                    Tools.logLine(TAG, "Metodo: PUT");

                    List<Cookie> cookiesPut = Session.getInstance().getCookie();
                    CookieStore stores = new BasicCookieStore();
                    for (Cookie cookie : cookiesPut) {

                        Tools.logLine(TAG, "Se añade cookie : " + cookie.toString());
                        stores.addCookie(cookie);
                    }
                    ((AbstractHttpClient) httpclient).setCookieStore(stores);


                    connectionResponse = httpclient.execute(request.makeHttpPutRequest());

                    break;

                case HttpRequest.GET2:
                    Tools.logLine(TAG, "Metodo: GET");

                    connectionResponse = httpclient.execute(request.makeHttpGetRequest());

                    break;
            }

            if (connectionResponse != null) {

                response = new HttpResponse(EntityUtils.toString(connectionResponse.getEntity()),
                        request.getOperationId());

                List<Cookie> cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();


                CookieStore store = new BasicCookieStore();
                for (int i = 0; i < cookies.size(); i++) {
                    Session.getInstance().setCookie(cookies);
                }
                ((AbstractHttpClient) httpclient).setCookieStore(store);

            }

            httpclient.getConnectionManager().shutdown();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response = new HttpResponse(HttpResponse.ERROR_UNSUPPORTED_ENCODING,
                    "codificacion respuesta",
                    request.getOperationId());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            response = new HttpResponse(HttpResponse.ERROR_CLIENT_PROTOCOL,
                    context.getResources().getString(R.string.http_client_protocol_error),
                    request.getOperationId());

        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            response = new HttpResponse(HttpResponse.ERROR_CONNECTION_TIMED_OUT,
                    context.getResources().getString(R.string.http_timed_out_error),
                    request.getOperationId());

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            response = new HttpResponse(HttpResponse.ERROR_SOCKET_TIMED_OUT,
                    context.getResources().getString(R.string.http_socket_timed_out_error),
                    request.getOperationId());

        } catch(UnknownHostException e){
            e.printStackTrace();
            response = new HttpResponse(HttpResponse.ERROR_INTERNET_CONNECTION,
                    context.getResources().getString(R.string.http_internet_connection_error),
                    request.getOperationId());

        } catch (Exception e) {
            e.printStackTrace();
            response = new HttpResponse(HttpResponse.ERROR_ANYWHERE,
                    context.getResources().getString(R.string.http_generic_error),
                    request.getOperationId());

        }

        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse response){

        try{
            JSONObject jsonResponse = new JSONObject(response.getResponse());
            Tools.logLine(TAG, "Response ws:\n" + jsonResponse.toString(1));
        } catch (JSONException e) {
            Tools.logLine(TAG, "Response ws: " + response.getResponse());
        }

        if(response.getStatus() != HttpResponse.SUCCESS){

            ((BaseActivity) context).hideProgressDialog();
            ((BaseActivity) context).showErrorMessage(response.getMessage());

        }else if(((BaseActivity) context).responseHasError(response.getResponse())){

            String message = "";

            try {
                JSONObject jsonResponse = new JSONObject(response.getResponse());
                message = jsonResponse.getString("errorMessage");
            } catch (JSONException e) {
                e.printStackTrace();
                message = context.getString(R.string.http_generic_error);
            }

            ((BaseActivity) context).hideProgressDialog();
            ((BaseActivity) context).showErrorMessage(message);
        }else {
            postExecuteAction.onHttpResponse(response);
        }

    }

    // Establish connection and socket (data retrieval) timeouts
    private HttpParams getHttpParams() {

        HttpParams http = new BasicHttpParams();

        HttpConnectionParams.setSoTimeout(http, Constants.SOCKET_TIMEOUT);

        return http;
    }





}