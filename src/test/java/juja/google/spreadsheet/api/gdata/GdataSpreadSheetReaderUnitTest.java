package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.*;

public class GdataSpreadSheetReaderUnitTest {

    @Test
    public void shouldGetColumnValues() throws Exception {
        //Given
        SpreadsheetService service = mock(SpreadsheetService.class);
        String url = "url";
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service, url));

        List<ListEntry> rows = new ArrayList<>();
        doReturn(rows).when(spreadsheet).readRows();
        doReturn(asList("row1", "row2")).when(spreadsheet).extractColumnValues(rows, "col");

        //When
        List<String> columns = spreadsheet.getColumnValues("col");

        //Then
        assertThat(columns, hasItem("row1"));
        assertThat(columns, hasItem("row2"));
    }

    @Test
    public void shouldGetSpreadsheetEntry() throws Exception {
        //Given
        String key = "key";
        URL url = new URL(GdataSpreadSheetReader.TEMPLATE + key);
        SpreadsheetService service = mock(SpreadsheetService.class);
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service, key));

        SpreadsheetEntry spreadsheetEntry = mock(SpreadsheetEntry.class);

        when(service.getEntry(url, SpreadsheetEntry.class)).thenReturn(spreadsheetEntry);

        //When
        spreadsheet.getDefaultWorkSheetEntry(service);

        //Then
        verify(service).getEntry(url, SpreadsheetEntry.class);
        verify(spreadsheetEntry).getDefaultWorksheet();
    }

    @Test
    public void getRowsForSpreadsheetEntry() throws Exception {
        //Given
        SpreadsheetService service = mock(SpreadsheetService.class);
        String url = "file://url";
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service, url));

        WorksheetEntry worksheetEntry = mock(WorksheetEntry.class);

        doReturn(worksheetEntry).when(spreadsheet).getDefaultWorkSheetEntry(service);
        when(worksheetEntry.getListFeedUrl()).thenReturn(new URL(url));
        ListFeed listFeed = mock(ListFeed.class);
        when(service.getFeed(new URL(url), ListFeed.class)).thenReturn(listFeed);

        //When
        spreadsheet.readRows();

        //Then
        verify(listFeed).getEntries();
    }

    @Test
    public void filterRowsByColumnName() throws Exception {
        //Given
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(mock(SpreadsheetService.class), "url"));

        //When
        ListEntry row1 = prepareRow("columnName", "row1");
        ListEntry row2 = prepareRow("columnName", "row2");
        List rows = asList(row1, row2);

        List<String> values = spreadsheet.extractColumnValues(rows, "columnName");

        //Then
        assertThat(values, hasItem("row1"));
        assertThat(values, hasItem("row2"));
    }

    @Test
    public void excludeNullColumnValues() throws Exception {
        //Given
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(mock(SpreadsheetService.class), "url"));

        //When
        ListEntry row1 = prepareRow("columnName", "null");
        ListEntry row2 = prepareRow("columnName", "row2");
        List rows = asList(row1, row2);

        List<String> values = spreadsheet.extractColumnValues(rows, "columnName");

        //Then
        assertThat(values, hasSize(1));
        assertThat(values, hasItem("row2"));
    }

    @Test
    public void excludeNullColumnReferences() throws Exception {
        //Given
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(mock(SpreadsheetService.class), "url"));

        //When
        ListEntry row1 = prepareRow("columnName", null);
        ListEntry row2 = prepareRow("columnName", "row2");
        List rows = asList(row1, row2);

        List<String> values = spreadsheet.extractColumnValues(rows, "columnName");

        //Then
        assertThat(values, hasSize(1));
        assertThat(values, hasItem("row2"));
    }

    @Test
    public void findRowByColumnHeaderAndColumnValue() throws Exception {
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(mock(SpreadsheetService.class), "url"));

        String header = "columnHeader";
        ListEntry first = prepareRow(header, "first");
        ListEntry second = prepareRow(header, "second");

        doReturn(asList(first, second)).when(spreadsheet).readRows();

        //When
        ListEntry row = spreadsheet.findRowByColumnValue(header, "first");

        //Then
        assertThat(row, is(first));
    }

    private ListEntry prepareRow(String columnHeader, String columnValue) {
        ListEntry row = mock(ListEntry.class);
        CustomElementCollection elementCollection = mock(CustomElementCollection.class);
        when(row.getCustomElements()).thenReturn(elementCollection);
        when(elementCollection.getValue(columnHeader)).thenReturn(columnValue);
        return row;
    }
}