package com.example.bastian.tienda;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by Bastian on 08-10-2017.
 */

public class Session {

    /**
     * Singleton
     */
    private static Session session;

    private List<Cookie> cookie;

    /**
     * Method to obtain the instance
     * @return
     */
    public static Session getInstance(){
        if(session == null){
            session = new Session();
        }

        return session;
    }

    /**
     * Default constructor
     */
    private Session(){

    }

    public List<Cookie> getCookie() {
        return cookie;
    }

    public void setCookie(List<Cookie> cookie) {
        this.cookie = cookie;
    }



    public void clearData(){
        cookie = null;

    }
}