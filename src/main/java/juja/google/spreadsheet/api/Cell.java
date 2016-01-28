package juja.google.spreadsheet.api;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;

import java.io.IOException;

public interface Cell {
    void update(String value) throws IOException, ServiceException;

    String value();

}
