package br.com.dv.api.domain.doctor;

import br.com.dv.api.domain.address.AddressDto;
import br.com.dv.api.domain.appointment.Appointment;
import br.com.dv.api.domain.patient.Patient;
import br.com.dv.api.domain.patient.PatientRegistrationDto;
import br.com.dv.api.repository.DoctorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("""
                When a doctor is available for a specialty and not at the scheduled date and time,
                chooseRandomDoctorBySpecialtyAndAvailability should return null.
                """)
    void shouldReturnNullWhenDoctorUnavailableForSpecialtyAndDate() {
        var doctor = registerDoctor("Doctor", "doctor@email.com",
                "123456", Specialty.CARDIOLOGY);

        var patient = registerPatient("Patient", "patient@email.com", "12345678900");

        var nextMondayAt10AM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        registerAppointment(doctor, patient, nextMondayAt10AM);

        var chosenDoctor = doctorRepository.chooseRandomDoctorBySpecialtyAndAvailability(
                Specialty.CARDIOLOGY, nextMondayAt10AM);

        assertThat(chosenDoctor).isNull();
    }

    @Test
    @DisplayName("""
                When a doctor is available for a specialty and at the scheduled date and time,
                chooseRandomDoctorBySpecialtyAndAvailability should return a Doctor object.
                """)
    void shouldReturnDoctorWhenAvailableOnDate () {
        var doctor = registerDoctor("Doctor", "doctor@email.com",
                "123456", Specialty.CARDIOLOGY);

        var patient = registerPatient("Patient", "patient@email.com", "12345678900");

        var nextMondayAt10AM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        registerAppointment(doctor, patient, nextMondayAt10AM);

        var chosenDoctor = doctorRepository.chooseRandomDoctorBySpecialtyAndAvailability(
                Specialty.CARDIOLOGY, nextMondayAt10AM.plusHours(1));

        assertThat(chosenDoctor).isNotNull();
    }

    /*
    The private methods below are used to register the entities in the database.
     */

    private void registerAppointment(Doctor doctor, Patient patient, LocalDateTime scheduledDateTime) {
        entityManager.persist(new Appointment(null, doctor, patient, scheduledDateTime,
                null, true));
    }

    private Doctor registerDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(doctorData(name, email, crm, specialty));
        entityManager.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        entityManager.persist(patient);
        return patient;
    }

    private DoctorRegistrationDto doctorData(String name, String email, String crm, Specialty specialty) {
        return new DoctorRegistrationDto(
                name,
                email,
                crm,
                specialty,
                addressData()
        );
    }

    private PatientRegistrationDto patientData(String name, String email, String cpf) {
        return new PatientRegistrationDto(
                name,
                email,
                cpf,
                addressData()
        );
    }

    private AddressDto addressData() {
        return new AddressDto(
                "the best street in the world",
                "Santos",
                "SP",
                "00000000"
        );
    }

}
