package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import br.com.dv.api.domain.appointment.validation.AppointmentValidation;
import br.com.dv.api.domain.doctor.Doctor;
import br.com.dv.api.domain.doctor.DoctorRepository;
import br.com.dv.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentScheduler {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final List<AppointmentValidation> validations;

    @Autowired
    public AppointmentScheduler(AppointmentRepository appointmentRepository,
                                DoctorRepository doctorRepository,
                                PatientRepository patientRepository,
                                List<AppointmentValidation> validations) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.validations = validations;
    }

    public AppointmentResponseDto schedule(AppointmentSchedulingDto dto) {
        validations.forEach(v -> v.validate(dto));

        var patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new AppointmentValidationException("Patient id not found"));

        var doctor = chooseDoctor(dto);

        var appointment = new Appointment(null, doctor, patient, dto.scheduledDateTime(), doctor.getSpecialty());

        appointmentRepository.save(appointment);
        return new AppointmentResponseDto(appointment);
    }

    private Doctor chooseDoctor(AppointmentSchedulingDto dto) {
        /*
        'doctorId' is optional.
        If present, the doctor will be chosen based on the id.
        If not present, a random one will be chosen based on the specialty.
         */
        boolean doctorIdIsPresent = dto.doctorId() != null;

        if (doctorIdIsPresent) {
            return doctorRepository.findById(dto.doctorId())
                    .orElseThrow(() -> new AppointmentValidationException("Doctor id not found"));
        }

        if (dto.specialty() == null) {
            throw new AppointmentValidationException("Specialty is required if doctor is not chosen");
        }

        var doctor = doctorRepository.chooseRandomDoctorBySpecialtyAndAvailability(dto.specialty(), dto.scheduledDateTime());
        if (doctor == null) {
            throw new AppointmentValidationException("No doctor available for this specialty");
        }
        return doctor;
    }

}
