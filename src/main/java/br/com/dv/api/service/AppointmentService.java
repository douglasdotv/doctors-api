package br.com.dv.api.service;

import br.com.dv.api.domain.appointment.Appointment;
import br.com.dv.api.domain.appointment.AppointmentListingDto;
import br.com.dv.api.domain.appointment.AppointmentResponseDto;
import br.com.dv.api.domain.appointment.AppointmentSchedulingDto;
import br.com.dv.api.domain.appointment.exception.AppointmentValidationException;
import br.com.dv.api.domain.appointment.validation.AppointmentCancellationValidation;
import br.com.dv.api.domain.appointment.validation.AppointmentSchedulingValidation;
import br.com.dv.api.domain.doctor.Doctor;
import br.com.dv.api.repository.AppointmentRepository;
import br.com.dv.api.repository.DoctorRepository;
import br.com.dv.api.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final List<AppointmentSchedulingValidation> schedulingValidations;
    private final List<AppointmentCancellationValidation> cancellationValidations;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository,
                              List<AppointmentSchedulingValidation> schedulingValidations,
                              List<AppointmentCancellationValidation> cancellationValidations) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.schedulingValidations = schedulingValidations;
        this.cancellationValidations = cancellationValidations;
    }

    public AppointmentResponseDto schedule(AppointmentSchedulingDto dto) {
        if (!patientRepository.existsById(dto.patientId())) {
            throw new AppointmentValidationException("Patient id not found");
        }

        if (dto.doctorId() != null && !doctorRepository.existsById(dto.doctorId())) {
            throw new AppointmentValidationException("Doctor id not found");
        }

        schedulingValidations.forEach(v -> v.validate(dto));

        var patient = patientRepository.getReferenceById(dto.patientId());
        var doctor = chooseDoctor(dto);

        var appointment = new Appointment(dto, patient, doctor);
        appointmentRepository.save(appointment);

        return new AppointmentResponseDto(appointment);
    }

    private Doctor chooseDoctor(AppointmentSchedulingDto dto) {
        /*
        'doctorId' is optional.
        If present, the doctor will be chosen based on the id.
        If not present, a random one will be chosen based on the specialty.
         */
        if (dto.doctorId() != null) {
            return doctorRepository.getReferenceById(dto.doctorId());
        }

        if (dto.specialty() == null) {
            throw new AppointmentValidationException("Specialty is required if doctor is not chosen");
        }

        var doctor = doctorRepository.chooseRandomDoctorBySpecialtyAndAvailability(dto.specialty(),
                dto.scheduledDateTime());
        if (doctor == null) {
            throw new AppointmentValidationException("No doctor available for this specialty");
        }
        return doctor;
    }

    public Page<AppointmentListingDto> listAll(Pageable pageable) {
        return appointmentRepository
                .findAllByIsActiveIsTrue(pageable)
                .map(AppointmentListingDto::new);
    }

    public void cancel(Long id) {
        var appointment = appointmentRepository.getReferenceById(id);
        cancellationValidations.forEach(v -> v.validate(appointment));
        appointment.softDelete();
    }

}
