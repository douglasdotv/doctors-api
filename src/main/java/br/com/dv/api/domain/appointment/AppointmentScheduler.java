package br.com.dv.api.domain.appointment;

import br.com.dv.api.domain.doctor.DoctorRepository;
import br.com.dv.api.domain.patient.PatientRepository;
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
        var doctor = doctorRepository.findById(dto.doctorId()).get();
        var patient = patientRepository.findById(dto.patientId()).get();
        var appointment = new Appointment(null, doctor, patient, dto.date(), dto.specialty());
        appointmentRepository.save(appointment);
    }

}
