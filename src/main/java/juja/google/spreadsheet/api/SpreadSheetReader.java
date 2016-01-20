package juja.google.spreadsheet.api;

import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.util.List;

public interface SpreadSheetReader {
    List<String> getColumnValues(String columnName, String googleSpreadSheetURL) throws IOException, ServiceException;
}
