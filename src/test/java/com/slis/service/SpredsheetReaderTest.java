package com.slis.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lisnychyis on 15.01.16.
 */
public class SpredsheetReaderTest {

    @Test
    public void getLogCodesCheckSize(){
        //inputParams
        //mocks
        //class to test
        SpredsheetReader reader = new SpredsheetReader();
        //expected

        //method invoke
        Set<LogCode> result = reader.getLogCodes();
        //assert
        Assert.assertTrue(result.size()>0);
    }


}