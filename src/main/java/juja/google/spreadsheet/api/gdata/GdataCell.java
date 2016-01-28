package juja.google.spreadsheet.api.gdata;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.Cell;
import juja.google.spreadsheet.api.SpreadSheetReader;

import java.io.IOException;

public class GdataCell implements Cell {

    private final ListEntry entry;
    private final String header;

    public GdataCell(
            SpreadSheetReader spreadsheet, String searchColumn, String colName, String colValue
    ) throws IOException, ServiceException {
        this.header = searchColumn;

        entry = spreadsheet.findRowByColumnValue(colName, colValue);
    }

    @Override
    public void update(String value) throws IOException, ServiceException {
        entry.getCustomElements().setValueLocal(header, value);
        entry.update();
    }

    @Override
    public String value() {
        return this.entry.getCustomElements().getValue(header);
    }

}
