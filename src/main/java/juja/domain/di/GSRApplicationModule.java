package juja.domain.di;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import juja.domain.dao.ProgressDao;
import juja.domain.service.BlackListedProgressService;
import juja.domain.service.ProgressService;
import juja.google.spreadsheet.api.SpreadSheetReader;
import juja.google.spreadsheet.api.gdata.GdataSpreadSheetReader;
import juja.google.spreadsheet.api.gdata.SpreadsheetServiceProvider;
import juja.google.spreadsheet.dao.GdataProgressDao;

import javax.inject.Scope;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GSRApplicationModule implements Module {

    @Override
    public void configure(Binder binder) {
        Properties properties = new Properties();
        try {
            InputStream resourceStream = this.getClass().getResourceAsStream("/user.properties");

            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Names.bindProperties(binder, properties);
        binder.bind(ProgressService.class).to(BlackListedProgressService.class);
        binder.bind(ProgressDao.class).to(GdataProgressDao.class);
        binder.bind(SpreadsheetService.class).toProvider(SpreadsheetServiceProvider.class).in(Singleton.class);
        binder.bind(SpreadSheetReader.class).annotatedWith(Names.named("progress")).toProvider(ProgressSpreadsheetProvider.class);

    }

}