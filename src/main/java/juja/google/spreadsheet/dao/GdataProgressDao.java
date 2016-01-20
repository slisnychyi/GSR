package juja.google.spreadsheet.dao;

import com.google.gdata.util.ServiceException;
import juja.domain.dao.ProgressDao;
import juja.google.spreadsheet.api.SpreadSheetReader;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GdataProgressDao implements ProgressDao {

    public static final String CODE_COLUMN_NAME = "Log-код";
    public static String PROGRESS_TOKEN = "1rT2bXxtSRFvnQc2of1XBIXy3zh-vlkfRFD476Bw9GQk";

    private SpreadSheetReader spreadSheetReader;
    String spreadsheetUrlTemplate;


    public GdataProgressDao(SpreadSheetReader spreadSheetReader) {

        this.spreadSheetReader = spreadSheetReader;
    }

    @Override
    public Set<String> fetchProgressCodes() {
        HashSet<String> progressCodes = new HashSet<>(getColumnValuesFromSpreadsheet());
        return progressCodes;
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
