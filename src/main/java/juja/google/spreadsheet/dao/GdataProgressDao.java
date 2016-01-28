package juja.google.spreadsheet.dao;

import com.google.gdata.util.ServiceException;
import juja.domain.dao.ProgressDao;
import juja.google.spreadsheet.api.Cell;
import juja.google.spreadsheet.api.SpreadSheetReader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Singleton
public class GdataProgressDao implements ProgressDao {

    public static final String CODE_COLUMN_NAME = "log-код";

    private SpreadSheetReader spreadSheetReader;

    @Inject
    public GdataProgressDao(@Named("progress") SpreadSheetReader spreadSheetReader) {
        this.spreadSheetReader = spreadSheetReader;
    }

    @Override
    public List<String> fetchProgressCodes() {
        return getColumnValuesFromSpreadsheet();
    }

    @Override
    public void markProgressForUser(String slackNick, String... codes) {
        Stream.of(codes).forEach(findAndUpdateCellAndHandleExceptions(slackNick));
    }

    private Consumer<String> findAndUpdateCellAndHandleExceptions(String slackNick) {
        return (code) -> {
            try {
                Cell cell = spreadSheetReader.findCellByColumnValue(slackNick, CODE_COLUMN_NAME, code);
                cell.update("DONE");
            } catch (IOException | ServiceException e) {
                //TODO process exception
                e.printStackTrace();
            }
        };
    }

    private List<String> getColumnValuesFromSpreadsheet() {
        try {
            return spreadSheetReader.getColumnValues(CODE_COLUMN_NAME);
        } catch (IOException | ServiceException e) {
            //TODO process exceptions
            e.printStackTrace();
            return null;
        }
    }
}
