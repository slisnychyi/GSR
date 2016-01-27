package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.*;

public class GdataSpreadSheetReaderUnitTest {

    private SpreadsheetService service = mock(SpreadsheetService.class);

    private GdataSpreadSheetReader spreadsheet = spy(new GdataSpreadSheetReader(service));

    @Test
    public void shouldGetColumnValues() throws Exception {
        //Given
        List<ListEntry> rows = new ArrayList<>();
        doReturn(rows).when(spreadsheet).readRows(service);
        doReturn(asList("row1", "row2")).when(spreadsheet).filterColumnValues(rows, "col");

        //When
        List<String> columns = spreadsheet.getColumnValues("col", "url");

        //Then
        assertThat(columns, hasItem("row1"));
        assertThat(columns, hasItem("row2"));
    }

    @Test
    public void getRowsForSpreadsheetEntry() throws Exception {

        //When
        List<ListEntry> rows = spreadsheet.readRows(service);


    }
}