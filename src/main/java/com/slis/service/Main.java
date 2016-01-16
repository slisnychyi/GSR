package com.slis.service;

import com.google.gdata.util.AuthenticationException;

/**
 * Created by lisnychyis on 15.01.16.
 */
public class Main {
    public static void main(String[] args) throws AuthenticationException {
        SpredsheetReader reader = new SpredsheetReader();
        reader.connect();
    }
}
