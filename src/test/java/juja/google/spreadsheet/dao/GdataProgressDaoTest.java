package juja.google.spreadsheet.dao;

import juja.google.spreadsheet.api.SpreadSheetReader;
import org.junit.Test;

import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
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
}