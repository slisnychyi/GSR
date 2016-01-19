package juja.google.spreadsheet.dao;

import juja.google.spreadsheet.api.SpreadSheetReader;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: viktor
 * Date: 1/19/16
 */
public class GdataProgressDaoTest {

    @Test
    public void fetchCodesFromSpreadsheet() throws Exception {
        //Given
        SpreadSheetReader spreadSheetReader = mock(SpreadSheetReader.class);
        GdataProgressDao progressDao = new GdataProgressDao(spreadSheetReader);
        when(spreadSheetReader.getColumnValues("Log-код")).thenReturn(asList("+q", "+quiz10", "+q"));

        //When
        Set<String> actualCodes = progressDao.fetchProgressCodes();

        //Then
        assertThat(actualCodes.size(), is(2));
        assertThat(actualCodes, hasItem("+q"));
        assertThat(actualCodes, hasItem("+quiz10"));
    }

    @Test
    public void fetchEmptyCollection() throws Exception {
        //Given
        SpreadSheetReader spreadSheetReader = mock(SpreadSheetReader.class);
        GdataProgressDao progressDao = new GdataProgressDao(spreadSheetReader);
        when(spreadSheetReader.getColumnValues("Log-код")).thenReturn(emptyList());

        //When
        Set<String> actualCodes = progressDao.fetchProgressCodes();

        //Then
        assertThat(actualCodes, is(empty()));
    }
}