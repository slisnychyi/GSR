package juja.google.spreadsheet.api;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.util.List;

public interface SpreadSheetReader {
    //TODO exclude get
    List<String> getColumnValues(String columnName) throws IOException, ServiceException;

    List<ListEntry> readRows() throws IOException, ServiceException;

    ListEntry findRow(String colName, String colValue);

}
