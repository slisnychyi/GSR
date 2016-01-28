package juja.domain.di;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import juja.google.spreadsheet.api.gdata.GdataSpreadSheetReader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.net.MalformedURLException;

public class ProgressSpreadsheetProvider implements Provider<GdataSpreadSheetReader> {

    private final GdataSpreadSheetReader spreadsheet;

    @Inject
    public ProgressSpreadsheetProvider(SpreadsheetService service,
                                       @Named("google.spreadsheet.progress.key") String key) throws MalformedURLException {
        this.spreadsheet = new GdataSpreadSheetReader(service, key);
    }

    @Override
    public GdataSpreadSheetReader get() {
        return spreadsheet;
    }
}
