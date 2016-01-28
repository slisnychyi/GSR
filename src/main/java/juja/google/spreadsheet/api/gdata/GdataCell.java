package juja.google.spreadsheet.api.gdata;

import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.Cell;
import juja.google.spreadsheet.api.SpreadSheetReader;

import java.io.IOException;

public class GdataCell implements Cell {

    private final ListEntry entry;
    private final String searchColumn;
    private final String header;

    public GdataCell(
            SpreadSheetReader spreadsheet, String searchColumn, String header, String colValue
    ) throws IOException, ServiceException {
        this.searchColumn = searchColumn;
        this.header = header;

        entry = spreadsheet.findRowByColumnValue(header, colValue);
    }

    @Override
    public void update(String value) throws IOException, ServiceException {
        CustomElementCollection customElements = entry.getCustomElements();
        customElements.setValueLocal(searchColumn, value);
        String escaped = escapePlus(customElements.getValue(header));
        customElements.setValueLocal(header, escaped);

        entry.update();
    }

    private String escapePlus(String value) {
        StringBuilder res = new StringBuilder();
        if (value.startsWith("+")) {
            res.append("'").append(value);
        } else {
            res.append(value);
        }
        return res.toString();
    }

    @Override
    public String value() {
        return this.entry.getCustomElements().getValue(searchColumn);
    }

}
