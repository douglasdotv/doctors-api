package br.com.dv.api.controller;

import br.com.dv.api.domain.address.Address;
import br.com.dv.api.domain.address.AddressDto;
import br.com.dv.api.domain.doctor.DoctorRegistrationDto;
import br.com.dv.api.domain.doctor.DoctorResponseDto;
import br.com.dv.api.domain.doctor.Specialty;
import br.com.dv.api.service.DoctorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DoctorRegistrationDto> registrationJacksonTester;
    @Autowired
    private JacksonTester<DoctorResponseDto> responseJacksonTester;
    @MockBean
    private DoctorService doctorService;

    @Test
    @DisplayName("When registering a doctor with invalid data, HTTP error 400 should be returned.")
    @WithMockUser
    void shouldReturnBadRequestWhenRegisteringDoctorWithInvalidData() throws Exception {
        var registrationDto = new DoctorRegistrationDto("", "", "1234567",
                Specialty.CARDIOLOGY, new AddressDto("", "", "", ""));

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationJacksonTester.write(registrationDto).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("""
            When registering a doctor with valid data, HTTP status 201 should be returned.
            Also, the response json should match the expected json.
            """)
    @WithMockUser
    void shouldReturnSuccessfulResponseAndMatchingJsonWhenSchedulingValidAppointment() throws Exception {
        var registrationDto = new DoctorRegistrationDto("Test", "testing@email.com", "123456",
                Specialty.CARDIOLOGY, new AddressDto(
                        "Test", "Test", "TS", "12345678"));

        var testResponse = new DoctorResponseDto(null, "Test", "testing@email.com", "123456",
                Specialty.CARDIOLOGY, new Address("Test", "Test", "Test", "Test"));

        when(doctorService.create(any())).thenReturn(testResponse);

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationJacksonTester.write(registrationDto).getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJacksonTester.write(testResponse).getJson()));
    }

}
