package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.SpreadSheetReader;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GdataSpreadSheetReader implements SpreadSheetReader {

    final SpreadsheetService gdataService;

    @Inject
    public GdataSpreadSheetReader(SpreadsheetService service) {
        this.gdataService = service;
    }

    public List<String> getColumnValues(String columnName, String googleSpreadSheetURL) throws IOException, ServiceException {
        List<ListEntry> rows = readRows(gdataService);
        return filterColumnValues(rows, columnName);
//        SpreadsheetEntry spreadsheet = getSpreadSheetEntry(gdataService, googleSpreadSheetURL);
//        if(spreadsheet != null){
//            List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();
//            for (WorksheetEntry worksheet : worksheets) {
//                String title = worksheet.getTitle().getPlainText();
//                int rowCount = worksheet.getRowCount();
//                int colCount = worksheet.getColCount();
//                System.out.println("\t" + title + "- readRows:" + rowCount + " cols: " + colCount);
//
//                URL listFeedUrl = worksheet.getListFeedUrl();
//                ListFeed listFeed = gdataService.getFeed(listFeedUrl, ListFeed.class);
//
//                for (ListEntry row : listFeed.getEntries()) {
//                    CustomElementCollection customElements = row.getCustomElements();
//                    String colValue = customElements.getValue(columnName); //"log-код"
//                    if(colValue!= null && !colValue.equals("null")){
//                        result.add(colValue);
//                    }
//                }
//
//            }
//        }
//        return result;
    }

    private static SpreadsheetEntry getSpreadSheetEntry(SpreadsheetService service, String googleSpreadSheetURL) {
        SpreadsheetEntry result = null;
        try {
            URL googleDocURL = new URL(googleSpreadSheetURL);
            result = service.getEntry(googleDocURL, SpreadsheetEntry.class);
        } catch (IOException | ServiceException e) {
            //TODO throw custom exception
            e.printStackTrace();
        }
        return result;

    }

    public List<ListEntry> readRows(SpreadsheetService service) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    public List<String> filterColumnValues(List<ListEntry> rows, String columnName) {
        //TODO implement
        throw new UnsupportedOperationException();
    }
}
