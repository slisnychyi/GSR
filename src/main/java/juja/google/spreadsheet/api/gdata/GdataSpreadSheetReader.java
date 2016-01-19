package juja.google.spreadsheet.api.gdata;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.SpreadSheetReader;
import juja.google.spreadsheet.utils.SpreadsheetServiceUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GdataSpreadSheetReader implements SpreadSheetReader {

    private final String googleSpreadSheetURL;
    private final SpreadsheetServiceUtil spredsheetService;

    public GdataSpreadSheetReader(SpreadsheetServiceUtil spreadsheetService, String googleSpreadSheetURL) {
        this.spredsheetService = spreadsheetService;
        this.googleSpreadSheetURL = googleSpreadSheetURL;
    }

    public List<String> getColumnValues(String columnName) throws IOException, ServiceException {
        List<String> result = new ArrayList<>();
        SpreadsheetService service = spredsheetService.generateSpreadSheetService();
        SpreadsheetEntry spreadsheet = getSpreadSheetEntry(service);

        if(spreadsheet != null){
            List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();
            for (WorksheetEntry worksheet : worksheets) {
                String title = worksheet.getTitle().getPlainText();
                int rowCount = worksheet.getRowCount();
                int colCount = worksheet.getColCount();
                System.out.println("\t" + title + "- rows:" + rowCount + " cols: " + colCount);

                URL listFeedUrl = worksheet.getListFeedUrl();
                ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

                for (ListEntry row : listFeed.getEntries()) {
                    CustomElementCollection customElements = row.getCustomElements();
                    String colValue = customElements.getValue(columnName); //"log-код"
                    if(colValue!= null && !colValue.equals("null")){
                        result.add(colValue);
                    }
                }

            }
        }
        return result;
    }

    private SpreadsheetEntry getSpreadSheetEntry(SpreadsheetService service) {
        SpreadsheetEntry result = null;
        try {
            URL googleDocURL = new URL(googleSpreadSheetURL);
            result = service.getEntry(googleDocURL, SpreadsheetEntry.class);
        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }
        return result;

    }

}
