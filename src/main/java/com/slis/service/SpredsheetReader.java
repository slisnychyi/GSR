package com.slis.service;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;

import java.util.Set;

/**
 * Created by lisnychyis on 15.01.16.
 */
public class SpredsheetReader {

    public void connect() throws AuthenticationException {
        //read from property file
        String googleUserName = "";
        String googleUserPassword = "";
        String googleSpreadSheetURL =
                "https://spreadsheets.google.com/feeds/spreadsheets/1vXsuIuq6Chzxg2C4KzH9c5y5f_tHgKjvD_2zYMpww1c/edit#gid=0";

        SpreadsheetService service = new SpreadsheetService("googleSpreadSheetReader");
        service.setUserCredentials(googleUserName, googleUserPassword);
    }

    public Set<LogCode> getLogCodes() {
        return null;
    }




}
