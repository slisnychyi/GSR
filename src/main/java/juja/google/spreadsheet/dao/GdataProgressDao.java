package juja.google.spreadsheet.dao;

import com.google.gdata.util.ServiceException;
import com.google.inject.name.Named;
import juja.domain.dao.ProgressDao;
import juja.google.spreadsheet.api.SpreadSheetReader;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class GdataProgressDao implements ProgressDao {

    public static final String CODE_COLUMN_NAME = "Log-код";
    public static String PROGRESS_TOKEN = "1rT2bXxtSRFvnQc2of1XBIXy3zh-vlkfRFD476Bw9GQk";

    private SpreadSheetReader spreadSheetReader;
    @Inject
    @Named("google.spreadsheet.url.template")
    String spreadsheetUrlTemplate;

    @Inject
    public GdataProgressDao(SpreadSheetReader spreadSheetReader) {
        this.spreadSheetReader = spreadSheetReader;
    }

    @Override
    public List<String> fetchProgressCodes() {
        return getColumnValuesFromSpreadsheet();
    }

    @Override
    public void markProgressForUser(String slackNick, String... codes) {
        throw new UnsupportedOperationException();
    }

    private List<String> getColumnValuesFromSpreadsheet() {
        try {
            String googleSpreadSheetURL = spreadsheetUrlTemplate + PROGRESS_TOKEN;
            return spreadSheetReader.getColumnValues(CODE_COLUMN_NAME, googleSpreadSheetURL);
        } catch (IOException | ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
