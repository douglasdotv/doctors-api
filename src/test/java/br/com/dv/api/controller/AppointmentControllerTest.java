package br.com.dv.api.controller;

import br.com.dv.api.domain.appointment.AppointmentResponseDto;
import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.AppointmentService;
import br.com.dv.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<AppointmentSchedulingDto> schedulingJacksonTester;
    @Autowired
    private JacksonTester<AppointmentResponseDto> responseJacksonTester;
    @MockBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("When scheduling an appointment with invalid data, HTTP error 400 should be returned.")
    @WithMockUser
    void shouldReturnBadRequestWhenSchedulingAppointmentWithInvalidData() throws Exception {
        var response = mockMvc.perform(post("/appointments"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("""
            When scheduling an appointment with valid data, HTTP status 200 should be returned.
            Also, the response json should match the expected json.
            """)
    @WithMockUser
    void shouldReturnSuccessfulResponseAndMatchingJsonWhenSchedulingValidAppointment() throws Exception {
        var testDateTime = LocalDateTime.now().plusHours(168);
        var testSpecialty = Specialty.CARDIOLOGY;

        var testResponse = new AppointmentResponseDto(null, 1L, 1L,
                testDateTime, testSpecialty);
        when(appointmentService.schedule(any())).thenReturn(testResponse);

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(schedulingJacksonTester.write(
                                        new AppointmentSchedulingDto(
                                                1L, 1L, testDateTime, testSpecialty))
                                .getJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJacksonTester.write(testResponse).getJson()));
    }

}
