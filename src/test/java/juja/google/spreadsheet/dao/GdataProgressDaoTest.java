package juja.google.spreadsheet.dao;

import juja.google.spreadsheet.api.Cell;
import juja.google.spreadsheet.api.SpreadSheetReader;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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
        when(spreadSheetReader.getColumnValues(GdataProgressDao.CODE_COLUMN_NAME)).
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
        when(spreadSheetReader.getColumnValues(GdataProgressDao.CODE_COLUMN_NAME)).
                thenReturn(emptyList());

        //When
        List<String> actualCodes = progressDao.fetchProgressCodes();

        //Then
        assertThat(actualCodes, is(empty()));
    }

    //TODO negative scenario

    @Test
    public void markProgressComplete() throws Exception {
        //Given
        SpreadSheetReader spreadSheetReader = mock(SpreadSheetReader.class);
        GdataProgressDao progressDao = new GdataProgressDao(spreadSheetReader);
        Cell first = mock(Cell.class);
        when(spreadSheetReader.findCellByColumnValue("nick", GdataProgressDao.CODE_COLUMN_NAME, "code1"))
                .thenReturn(first);
        Cell second = mock(Cell.class);
        when(spreadSheetReader.findCellByColumnValue("nick", GdataProgressDao.CODE_COLUMN_NAME, "code2"))
                .thenReturn(second);
        //When
        progressDao.markProgressForUser("nick", "code1", "code2");

        //Then
        verify(first).update("DONE");
        verify(second).update("DONE");
    }
}