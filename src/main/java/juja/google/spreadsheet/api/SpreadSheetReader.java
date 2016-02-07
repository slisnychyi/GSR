package juja.google.spreadsheet.api;

import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface SpreadSheetReader {
    //TODO exclude get
    List<String> getColumnValues(String columnName) throws IOException, ServiceException;

    List<ListEntry> readRows() throws IOException, ServiceException;

    ListEntry findRowByColumnValue(String header, String value) throws IOException, ServiceException;

    Cell findCellByColumnValue(String searchColumn, String header, String value) throws IOException, ServiceException;

    boolean isHeaderExist(String value) throws IOException, ServiceException;

    CellEntry createNewHeader(String header) throws IOException, ServiceException;

    Set<String> getRowValues(int rowNum) throws ServiceException, IOException;

}
