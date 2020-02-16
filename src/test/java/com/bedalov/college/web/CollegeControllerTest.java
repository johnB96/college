package com.bedalov.college.web;

import com.bedalov.college.datastore.CollegeEntity;
import com.bedalov.college.datastore.CollegeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CollegeControllerTest {

    private static final String KEY = "foo";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollegeRepository repository;

    private final CollegeEntity collegeEntity1 = new CollegeEntity(KEY, 1.0, 1.0, 1.0);

    @Before
    public void setUp() {
        doReturn(collegeEntity1).when(repository).getEntity(KEY);
    }

    @Test
    public void doGet() throws Exception {
        mockMvc.perform(buildGet(KEY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getResult(KEY, 2.0, true)));
    }

    @Test
    public void doGet_givenBoardingIsTrue() throws Exception {
        mockMvc.perform(buildGetWithBoarding(KEY, "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getResult(KEY, 2.0, true)));
    }

    @Test
    public void doGet_givenBoardingIsFalse() throws Exception {
        mockMvc.perform(buildGetWithBoarding(KEY, "false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getResult(KEY, 1.0, false)));
    }

    @Test
    public void doGet_givenInvalidCollege_thenNotFoundIsReturned() throws Exception {
        mockMvc.perform(buildGet("bar"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(CollegeController.NOT_FOUND_MESSAGE));
    }

    @Test
    public void doGet_givenMissingCollege_thenBadRequestIsReturned() throws Exception {
        mockMvc.perform(buildGet(""))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CollegeController.BAD_REQUEST_MESSAGE));
    }

    private MockHttpServletRequestBuilder buildGetWithBoarding(String college, String boarding) {
        return buildGet(college)
                .param(CollegeController.BOARDING_PARAM, boarding);
    }

    private MockHttpServletRequestBuilder buildGet(String college) {
        return get(CollegeController.COST_ROUTE)
                .param(CollegeController.COLLEGE_PARAM, college);
    }

    private String getResult(String college, double cost, boolean boarding) {
        return String.format("{\"college\":\"%s\",\"cost\":%f,\"boarding\":%b}", college, cost, boarding);
    }
}
