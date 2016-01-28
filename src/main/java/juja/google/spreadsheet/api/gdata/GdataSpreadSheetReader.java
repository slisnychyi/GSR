package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.*;
import juja.google.spreadsheet.api.Cell;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GdataSpreadSheetReader implements SpreadSheetReader {

    final static String TEMPLATE = "https://spreadsheets.google.com/feeds/spreadsheets/";

    final SpreadsheetService gdataService;
    private URL url;

    @Inject
    public GdataSpreadSheetReader(SpreadsheetService service, String key) throws MalformedURLException {
        this.gdataService = service;
        this.url = new URL(TEMPLATE + key);
    }

    public List<String> getColumnValues(String columnName) throws IOException, ServiceException {
        List<ListEntry> rows = readRows();
        return extractColumnValues(rows, columnName);
    }

    public WorksheetEntry getDefaultWorkSheetEntry(SpreadsheetService service) throws IOException, ServiceException {
        SpreadsheetEntry spreadsheet = service.getEntry(url, SpreadsheetEntry.class);

        return spreadsheet.getDefaultWorksheet();
    }

    public List<ListEntry> readRows() throws IOException, ServiceException {
        WorksheetEntry worksheet = getDefaultWorkSheetEntry(gdataService);

        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed feed = gdataService.getFeed(listFeedUrl, ListFeed.class);
        return feed.getEntries();
    }

    @Override
    public ListEntry findRowByColumnValue(String header, String value) throws IOException, ServiceException {
        return readRows().stream().filter(r -> {
            String actual = Optional.ofNullable(r.getCustomElements().getValue(header)).orElse("");
            return actual.equals(value);
        }).findFirst().get();
    }

    @Override
    public Cell findCellByColumnValue(String searchColumn, String header, String value) throws IOException, ServiceException {
        return new GdataCell(this, searchColumn, header, value);
    }

    public List<String> extractColumnValues(List<ListEntry> rows, String columnName) {
        return rows.stream().map(row -> {
            CustomElementCollection customElements = row.getCustomElements();
            String colValue = customElements.getValue(columnName);
            return colValue;
        }).filter(
                value -> !Optional.ofNullable(value).orElse("null").equals("null")
        ).collect(Collectors.toList());
    }
}
