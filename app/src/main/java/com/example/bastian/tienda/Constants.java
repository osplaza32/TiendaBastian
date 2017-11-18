package com.example.bastian.tienda;

/**
 * Created by Bastian on 08-10-2017.
 */

public class Constants {


    public static final boolean TRACE_ALLOWED = true;//TODO activate logs true

    public static final String ENVIRONMENT = "http://192.168.0.22/tienda/rest/";

        public static final String WS_LOGIN= ENVIRONMENT + "loginUsuario.php";
    public static final String WS_ID_LOGIN= "1";


    // Time out configurations

    /**
     * Time out to connect to server
     */
    public static final int CONNECTION_TIMEOUT = 300000;

    /**
     * Time out to connect to socket
     */
    public static final int SOCKET_TIMEOUT = 60000;
}
