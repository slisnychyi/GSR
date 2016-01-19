package juja.google.spreadsheet.dao;

import juja.domain.dao.ProgressDao;
import juja.google.spreadsheet.api.SpreadSheetReader;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * User: viktor
 * Date: 1/19/16
 */
public class GdataProgressDao implements ProgressDao {

    public GdataProgressDao(SpreadSheetReader spreadSheetReader) {

    }

    @Override
    public Set<String> fetchProgressCodes() {
        return new HashSet<>(asList("+quiz10", "+q"));
    }
}
