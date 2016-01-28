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
    public void returnValue() throws Exception {
        SpreadSheetReader spreadsheet = mock(SpreadSheetReader.class);
        ListEntry row = mock(ListEntry.class);
        when(spreadsheet.findRowByColumnValue("log-code", "+lms")).thenReturn(row);
        GdataCell cell = spy(new GdataCell(spreadsheet, "userid", "log-code", "+lms"));

        CustomElementCollection elementCollection = new CustomElementCollection();
        when(row.getCustomElements()).thenReturn(elementCollection);
        elementCollection.setValueLocal("userid", "newValue");

        String expected = "newValue";
        String actual = cell.value();

        assertThat(actual, is(expected));
    }

    @Test
    public void updateValueAndEscapeCode() throws Exception {
        SpreadSheetReader spreadsheet = mock(SpreadSheetReader.class);
        ListEntry row = mock(ListEntry.class);
        String header = "log-code";
        String code = "+lms";
        when(spreadsheet.findRowByColumnValue(header, code)).thenReturn(row);

        CustomElementCollection elementCollection = new CustomElementCollection();
        elementCollection.setValueLocal(header, code);
        when(row.getCustomElements()).thenReturn(elementCollection);

        GdataCell cell = spy(new GdataCell(spreadsheet, "userid", header, code));

        String value = "newValue";
        cell.update(value);

        verify(row).update();
        assertThat(elementCollection.getValue(header), is("'" + code));
        assertThat(elementCollection.getValue("userid"), is(value));

    }
}