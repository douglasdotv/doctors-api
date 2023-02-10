package br.com.dv.api.domain.doctor;

public record DoctorListingData(Long id,
                                String name,
                                String email,
                                String crm,
                                Specialty specialty) {

    public DoctorListingData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }

}
