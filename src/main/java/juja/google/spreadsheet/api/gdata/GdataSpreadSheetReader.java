package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.SpreadSheetReader;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GdataSpreadSheetReader implements SpreadSheetReader {

    final SpreadsheetService gdataService;

    @Inject
    public GdataSpreadSheetReader(SpreadsheetService service) {
        this.gdataService = service;
    }

    public List<String> getColumnValues(String columnName, String googleSpreadSheetURL) throws IOException, ServiceException {
        List<ListEntry> rows = readRows(gdataService, googleSpreadSheetURL);
        return extractColumnValues(rows, columnName);
    }

    public WorksheetEntry getDefaultWorkSheetEntry(SpreadsheetService service, String googleSpreadSheetURL) throws IOException, ServiceException {
        SpreadsheetEntry spreadsheet = service.getEntry(new URL(googleSpreadSheetURL), SpreadsheetEntry.class);

        return spreadsheet.getDefaultWorksheet();
    }

    public List<ListEntry> readRows(SpreadsheetService service, String url) throws IOException, ServiceException {
        WorksheetEntry worksheet = getDefaultWorkSheetEntry(service, url);

        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);
        return feed.getEntries();
    }

    public List<String> extractColumnValues(List<ListEntry> rows, String columnName) {
        return rows.stream().map(row -> {
            CustomElementCollection customElements = row.getCustomElements();
            String colValue = customElements.getValue(columnName);
            return colValue;
        }).filter(value -> !Optional.ofNullable(value).orElse("null").equals("null")).collect(Collectors.toList());
    }
}
