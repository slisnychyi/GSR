package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.*;

public class GdataSpreadSheetReaderUnitTest {

    @Test
    public void shouldGetColumnValues() throws Exception {
        //Given
        SpreadsheetService service = mock(SpreadsheetService.class);
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service));

        List<ListEntry> rows = new ArrayList<>();
        doReturn(rows).when(spreadsheet).readRows(service, "url");
        doReturn(asList("row1", "row2")).when(spreadsheet).extractColumnValues(rows, "col");

        //When
        List<String> columns = spreadsheet.getColumnValues("col", "url");

        //Then
        assertThat(columns, hasItem("row1"));
        assertThat(columns, hasItem("row2"));
    }

    @Test
    public void shouldGetSpreadsheetEntry() throws Exception {
        //Given
        SpreadsheetService service = mock(SpreadsheetService.class);
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service));

        SpreadsheetEntry spreadsheetEntry = mock(SpreadsheetEntry.class);
        URL url = new URL("file://url");
        when(service.getEntry(url, SpreadsheetEntry.class)).thenReturn(spreadsheetEntry);

        //When
        spreadsheet.getDefaultWorkSheetEntry(service, "file://url");

        //Then
        verify(service).getEntry(url, SpreadsheetEntry.class);
        verify(spreadsheetEntry).getDefaultWorksheet();
    }

    @Test
    public void getRowsForSpreadsheetEntry() throws Exception {
        //Given
        SpreadsheetService service = mock(SpreadsheetService.class);
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service));

        WorksheetEntry worksheetEntry = mock(WorksheetEntry.class);
        String url = "file://url";
        doReturn(worksheetEntry).when(spreadsheet).getDefaultWorkSheetEntry(service, url);
        when(worksheetEntry.getListFeedUrl()).thenReturn(new URL(url));
        ListFeed listFeed = mock(ListFeed.class);
        when(service.getFeed(new URL(url), ListFeed.class)).thenReturn(listFeed);

        //When
        spreadsheet.readRows(service, url);

        //Then
        verify(listFeed).getEntries();
    }

    @Test
    public void filterRowsByColumnName() throws Exception {
        //Given
        GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(mock(SpreadsheetService.class)));

        //When
        ListEntry row1 = prepareRow("columnName", "row1");
        ListEntry row2 = prepareRow("columnName", "row2");
        List rows = asList(row1, row2);

        List<String> values = spreadsheet.extractColumnValues(rows, "columnName");

        //Then
        assertThat(values, hasItem("row1"));
        assertThat(values, hasItem("row2"));
    }

    private ListEntry prepareRow(String columnName, String columnValue) {
        ListEntry row = mock(ListEntry.class);
        CustomElementCollection elementCollection = mock(CustomElementCollection.class);
        when(row.getCustomElements()).thenReturn(elementCollection);
        when(elementCollection.getValue(columnName)).thenReturn(columnValue);
        return row;
    }
}