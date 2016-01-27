package juja.domain.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import juja.domain.di.GSRApplicationModule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

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


}