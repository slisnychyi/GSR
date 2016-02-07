package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.*;
import juja.google.spreadsheet.api.Cell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GdataSpreadSheetReader implements SpreadSheetReader {

    private final static String NULL_VALUE = "null";
    private final static int HEADER_ROW_NUM = 0;
    final static String TEMPLATE = "https://spreadsheets.google.com/feeds/spreadsheets/";

    final SpreadsheetService gdataService;
    private URL url;

    @Inject
    public GdataSpreadSheetReader(SpreadsheetService service, String key) throws MalformedURLException {
        this.gdataService = service;
        this.url = new URL(TEMPLATE + key);
    }

    public static Predicate<String> isNotNull(){
        return p -> !Optional.ofNullable(p).orElse(NULL_VALUE).equals(NULL_VALUE);
    }

    public static Predicate<String> isNotEmpty(){
        return p -> p.trim().length() > 0;
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
        if (!isHeaderExist(searchColumn)) createNewHeader(searchColumn);
        return new GdataCell(this, searchColumn, header, value);
    }

    public List<String> extractColumnValues(List<ListEntry> rows, String columnName) {
        return rows.stream().map(row -> {
            CustomElementCollection customElements = row.getCustomElements();
            return customElements.getValue(columnName);
        }).filter(isNotNull()
        ).collect(Collectors.toList());
    }

    @Override
    public CellEntry createNewHeader(String header) throws IOException, ServiceException {
        int gDataRowNum = HEADER_ROW_NUM + 1;
        int maxColumnNumber = getColumnNumber(HEADER_ROW_NUM);
        CellFeed cellFeed = getCellFeed();
        CellEntry newColumn = new CellEntry(gDataRowNum, maxColumnNumber + 1, header);
        return cellFeed.insert(newColumn);
    }

    @Override
    public boolean isHeaderExist(String header) throws IOException, ServiceException {
        Set<String> rowValues = getRowValues(HEADER_ROW_NUM);
        return rowValues.contains(header);
    }

    @Override
    public Set<String> getRowValues(int rowNum) throws ServiceException, IOException {
        List<ListEntry> rows = readRows();
        if (rows.size() > 0) {
            CustomElementCollection elements = rows.get(rowNum).getCustomElements();
            return elements.getTags().stream()
                    .map(elements::getValue)
                    .filter(isNotNull().and(isNotEmpty()))
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    private CellFeed getCellFeed() throws IOException, ServiceException {
        WorksheetEntry worksheet = getDefaultWorkSheetEntry(gdataService);
        URL listFeedUrl = worksheet.getCellFeedUrl();
        return gdataService.getFeed(listFeedUrl, CellFeed.class);
    }


    private int getColumnNumber(int rowNum) throws IOException, ServiceException {
        return readRows().get(rowNum).getCustomElements().getTags().size();
    }
}
