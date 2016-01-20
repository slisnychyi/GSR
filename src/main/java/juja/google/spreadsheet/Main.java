package juja.google.spreadsheet;

import com.google.gdata.util.ServiceException;
import juja.google.spreadsheet.api.SpreadSheetReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ServiceException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        String columnName = "log-код";
        SpreadSheetReader reader = (SpreadSheetReader) context.getBean("spreadSheetReader");
        List<String> columnValues = reader.getColumnValues(columnName, "https://spreadsheets.google.com/feeds/spreadsheets/1rT2bXxtSRFvnQc2of1XBIXy3zh-vlkfRFD476Bw9GQk");

        System.out.println(columnValues);
    }
}
