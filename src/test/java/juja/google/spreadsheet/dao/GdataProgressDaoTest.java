package juja.google.spreadsheet.dao;

import juja.google.spreadsheet.api.SpreadSheetReader;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
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
        progressDao.spreadsheetUrlTemplate = "https://google.spreadsheet.com/";
        when(spreadSheetReader.getColumnValues("Log-код", "https://google.spreadsheet.com/1rT2bXxtSRFvnQc2of1XBIXy3zh-vlkfRFD476Bw9GQk")).
                thenReturn(asList("+q", "+quiz10", "+q"));

        //When
        List<String> actualCodes = progressDao.fetchProgressCodes();

        //Then
        assertThat(actualCodes.size(), is(3));
        assertThat(actualCodes, hasItem("+q"));
        assertThat(actualCodes, hasItem("+quiz10"));
    }

    @Test
    public void fetchEmptyCollection() throws Exception {
        //Given
        SpreadSheetReader spreadSheetReader = mock(SpreadSheetReader.class);
        GdataProgressDao progressDao = new GdataProgressDao(spreadSheetReader);
        progressDao.spreadsheetUrlTemplate = "https://google.spreadsheet.com/";
        when(spreadSheetReader.getColumnValues("Log-код", "https://google.spreadsheet.com/1rT2bXxtSRFvnQc2of1XBIXy3zh-vlkfRFD476Bw9GQk")).
                thenReturn(emptyList());

        //When
        List<String> actualCodes = progressDao.fetchProgressCodes();

        //Then
        assertThat(actualCodes, is(empty()));
    }
}