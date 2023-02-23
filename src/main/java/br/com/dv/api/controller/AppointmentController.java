package br.com.dv.api.controller;

import br.com.dv.api.domain.appointment.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentScheduler scheduler;
    private final AppointmentRepository repository;

    @Autowired
    public AppointmentController(AppointmentScheduler scheduler, AppointmentRepository repository) {
        this.scheduler = scheduler;
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentResponseDto> schedule(@RequestBody @Valid AppointmentSchedulingDto dto) {
        var appointment = scheduler.schedule(dto);

        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentListingDto>> listAll(@PageableDefault(size = 5, sort = {"scheduledDateTime"})
                                                               Pageable pageable) {
        var page = repository
                .findAll(pageable)
                .map(AppointmentListingDto::new);

        return ResponseEntity.ok(page);
    }

}
