package com.bedalov.college.datastore;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.bedalov.college.config.CollegeRepositoryConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test-duplicates")
public class CollegeRepositoryImplDuplicateTest {

    private static final String KEY = "Adelphi University";

    @MockBean
    private CollegeRepository deliberateUnusedMock;

    @Mock
    private Appender<ILoggingEvent> appender;

    @Captor
    private ArgumentCaptor<LoggingEvent> argument;

    @Autowired
    private CollegeRepositoryConfiguration config;

    @Before
    public void setup() {
        ((Logger) LoggerFactory.getLogger(CollegeRepositoryImpl.class)).addAppender(appender);
    }

    @Test
    public void handleDuplicate_givenDuplicatesAreRead_thenMessageIsLoggedAndFirstIsInRepo() throws IOException {
        CollegeRepositoryImpl collegeRepository = new CollegeRepositoryImpl(config);

        CollegeEntity entity = collegeRepository.getEntity(KEY);

        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals("Found duplicate college: " + KEY, argument.getValue().getFormattedMessage());
        assertEquals(KEY, entity.getCollegeName());
        assertThat(entity.getInState(), equalTo(111.0));
        assertThat(entity.getOutOfState(), equalTo(222.0));
        assertThat(entity.getRoomAndBoard(), equalTo(333.0));
    }

    @After
    public void tearDown() {
        ((Logger) LoggerFactory.getLogger(CollegeRepositoryImpl.class)).detachAppender(appender);
    }
}
