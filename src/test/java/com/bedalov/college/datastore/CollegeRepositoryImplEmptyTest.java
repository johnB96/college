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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test-empty")
public class CollegeRepositoryImplEmptyTest {

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
        Logger logger = (Logger) LoggerFactory.getLogger(CollegeRepositoryImpl.class);
        logger.addAppender(appender);
    }

    @Test
    public void handleEmptyKey_givenEmptyKey_thenMessageIsLogged() throws IOException {
        new CollegeRepositoryImpl(config);

        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals("Found college without name; skipping", argument.getValue().getFormattedMessage());
    }

    @After
    public void tearDown() {
        Logger logger = (Logger) LoggerFactory.getLogger(CollegeRepositoryImpl.class);
        logger.detachAppender(appender);
    }
}
