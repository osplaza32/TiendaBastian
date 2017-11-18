package com.example.bastian.tienda.http;

/**
 * Created by Bastian on 08-10-2017.
 */

public class HttpResponse {

    /**
     * Success status
     */
    public static final int SUCCESS = 0;

    /**
     * Internet connection error
     */
    public static final int ERROR_INTERNET_CONNECTION = 1;

    /**
     * Connection timed out error
     */
    public static final int ERROR_CONNECTION_TIMED_OUT = 2;

    /**
     * Socket timed out error
     */
    public static final int ERROR_SOCKET_TIMED_OUT = 3;

    /**
     * Protocol client error
     */
    public static final int ERROR_PROTOCOL_CLIENT = 4;

    /**
     * Unsupported encoding response error
     */
    public static final int ERROR_UNSUPPORTED_ENCODING = 5;

    /**
     * Client protocol error
     */
    public static final int ERROR_CLIENT_PROTOCOL = 6;

    /**
     * Generic error
     */
    public static final int ERROR_ANYWHERE = 7;

    /**
     * Http request response
     */
    private String response;

    /**
     * Http request status
     */
    private int status;

    /**
     * Http request message
     */
    private String message;

    /**
     * The operation id
     */
    private String operationId;

    /**
     * Constructor to be used when http request failed
     * @param status Error status
     * @param message message
     * @param operationId The operation id
     */
    public HttpResponse(int status, String message, String operationId){
        this.status = status;
        this.message = message;
        this.operationId = operationId;
        this.response = "";
    }

    /**
     * Default constructor
     * @param response Response string
     * @param operationId The operation id
     */
    public HttpResponse(String response, String operationId){
        this.status = SUCCESS;
        this.message = "Ok";
        this.response = response;
        this.operationId = operationId;
    }

    /**
     * Get operation status
     * @return The status
     */
    public int getStatus(){
        return status;
    }

    /**
     * Get operation message
     * @return The message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Get operation result
     * @return The response
     */
    public String getResponse(){
        return response;
    }

    /**
     * Get operation id
     * @return the operation id
     */
    public String getOperationId(){
        return operationId;
    }
}