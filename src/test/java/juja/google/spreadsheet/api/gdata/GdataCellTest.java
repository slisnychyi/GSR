package juja.google.spreadsheet.api.gdata;

import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import juja.google.spreadsheet.api.SpreadSheetReader;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class GdataCellTest {

    @Test
    public void updateValue() throws Exception {
        SpreadSheetReader spreadsheet = mock(SpreadSheetReader.class);
        ListEntry row = mock(ListEntry.class);
        when(spreadsheet.findRow("log-code", "+lms")).thenReturn(row);
        GdataCell cell = spy(new GdataCell(spreadsheet, "userid", "log-code", "+lms"));

        CustomElementCollection elementCollection = new CustomElementCollection();
        when(row.getCustomElements()).thenReturn(elementCollection);

        String value = "newValue";
        cell.update(value);

        verify(row).update();
        assertThat(elementCollection.getValue("userid"), is(value));
    }
}