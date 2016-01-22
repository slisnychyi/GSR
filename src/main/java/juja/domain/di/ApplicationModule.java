package juja.domain.di;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import juja.domain.dao.ProgressDao;
import juja.domain.service.BlackListedProgressService;
import juja.domain.service.ProgressService;
import juja.google.spreadsheet.api.SpreadSheetReader;
import juja.google.spreadsheet.api.gdata.GdataSpreadSheetReader;
import juja.google.spreadsheet.api.gdata.SpreadsheetServiceProvider;
import juja.google.spreadsheet.dao.GdataProgressDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationModule implements Module {

    @Override
    public void configure(Binder binder) {
        Properties userProperties = new Properties();
        try {
            InputStream resourceStream = this.getClass().getResourceAsStream("/user.properties");

            userProperties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Names.bindProperties(binder, userProperties);
        binder.bind(ProgressService.class).to(BlackListedProgressService.class);
        binder.bind(ProgressDao.class).to(GdataProgressDao.class);
        binder.bind(SpreadSheetReader.class).to(GdataSpreadSheetReader.class);
        binder.bind(SpreadsheetService.class).toProvider(SpreadsheetServiceProvider.class);
    }
}