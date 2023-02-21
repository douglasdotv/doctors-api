package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.appointment.validation.AppointmentValidationException;
import br.com.dv.api.domain.doctor.Doctor;
import br.com.dv.api.domain.doctor.DoctorRepository;
import br.com.dv.api.domain.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentScheduler {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentScheduler(AppointmentRepository appointmentRepository,
                                DoctorRepository doctorRepository,
                                PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public void schedule(AppointmentSchedulingDto dto) {
        boolean doctorIdIsPresent = dto.doctorId() != null;

        var patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new AppointmentValidationException("Patient id not found"));

        var doctor = doctorIdIsPresent ?
                doctorRepository.findById(dto.doctorId())
                        .orElseThrow(() -> new AppointmentValidationException("Doctor id not found")) :
                chooseDoctor(dto);

        var appointment = new Appointment(null, doctor, patient, dto.date(), dto.specialty());

        appointmentRepository.save(appointment);
    }

    private Doctor chooseDoctor(AppointmentSchedulingDto dto) {
        // TODO
        return null;
    }

}
