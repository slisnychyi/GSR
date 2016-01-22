package juja.domain.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class BlackListedProgressServiceIntegrationTest {

    @Test
    public void fetchCodesFromRealSpreadsheet() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        ProgressService progressService = context.getBean("progressService", ProgressService.class);

        Set<String> progressCodes = progressService.fetchProgressCodes();

        System.out.println(progressCodes);
        assertThat(progressCodes, hasSize(251));
        assertThat(progressCodes, not(hasItem("")));
    }
}