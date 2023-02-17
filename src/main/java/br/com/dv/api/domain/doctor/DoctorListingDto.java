package br.com.dv.api.domain.doctor;

public record DoctorListingDto(Long id,
                               String name,
                               String email,
                               String crm,
                               Specialty specialty) {

    public DoctorListingDto(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }

}
