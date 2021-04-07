package juja.domain.service;

import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import juja.domain.di.GSRApplicationModule;
import juja.domain.model.User;
import juja.google.spreadsheet.api.Cell;
import juja.google.spreadsheet.api.SpreadSheetReader;
import juja.google.spreadsheet.api.gdata.GdataCell;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@Ignore
public class BlackListedProgressServiceIntegrationTest {

    private static Injector injector;


    @BeforeClass
    public static void createInjector() {
        injector = Guice.createInjector(new GSRApplicationModule());
    }

    @Test
    public void fetchCodesFromRealSpreadsheet() throws Exception {
        ProgressService progressService = injector.getInstance(ProgressService.class);

        Set<String> progressCodes = progressService.fetchProgressCodes();

        assertThat(progressCodes, hasSize(251));
        assertThat(progressCodes, not(hasItem("")));
    }

    @Test
    public void markProgressForUser() throws Exception {
        ProgressService progressService = injector.getInstance(ProgressService.class);
        SpreadSheetReader spreadsheet = injector.getInstance(
                Key.get(SpreadSheetReader.class,
                        Names.named("progress"))
        );

        Cell progressCell = new GdataCell(spreadsheet, "viktorkuchyn", "log-код", "+lms");
        progressCell.update("");

        progressService.markProgressDone(User.create().withSlackNick("viktorkuchyn").build(), "+lms");

        progressCell = new GdataCell(spreadsheet, "viktorkuchyn", "log-код", "+lms");
        assertThat(progressCell.value(), is("DONE"));
    }


    @Test
    public void createsNewColumnForUserId() throws Exception {
        SpreadSheetReader spreadsheet = injector.getInstance(
                Key.get(SpreadSheetReader.class,
                        Names.named("progress"))
        );
        String header = "TEST_HEADER";
        CellEntry cellEntry = spreadsheet.createNewHeader(header);
        assertThat(cellEntry.getCell().getInputValue(), is(header));
    }


}