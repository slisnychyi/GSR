package juja.domain.service;

import juja.domain.dao.ProgressDao;
import juja.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlackListedProgressServiceTest {

    @InjectMocks
    private BlackListedProgressService service = new BlackListedProgressService();

    @Mock
    private ProgressDao progressDao;

    @Test
    public void shouldFetchAllProgressCodes() throws Exception {
        //Given
        when(progressDao.fetchProgressCodes()).thenReturn(asList("+code1", "+code2", "+code1"));

        //When
        Set<String> actualProgressCodes = service.fetchProgressCodes();

        //Then
        assertThat(actualProgressCodes, hasSize(3));
        assertThat(actualProgressCodes, hasItem("+code1"));
        assertThat(actualProgressCodes, hasItem("+code2"));
    }

    @Test
    public void shouldExcludeBlackListedCodes() throws Exception {
        //Given
        service.setCodesBlackList("+blackListCode1;+blackListCode2");
        when(progressDao.fetchProgressCodes()).thenReturn(asList("+code1", "+blackListCode1", "+blackListCode2", "+code2"));

        //When
        Set<String> actualProgressCodes = service.fetchProgressCodes();

        //Then
        assertThat(actualProgressCodes, hasSize(2));
        assertThat(actualProgressCodes, hasItem("+code1"));
        assertThat(actualProgressCodes, hasItem("+code2"));
    }

    @Test
    public void shouldMarkProgressByCodes() throws Exception {
        //Given
        User user = User.create().withSlackNick("slackNick").build();
        //When
        service.markProgressDone(user, "+quiz1", "+quiz2");

        //Then
        verify(progressDao).markProgressForUser("slackNick", "+quiz1", "+quiz2");
    }

    //TODO negative scenario
}