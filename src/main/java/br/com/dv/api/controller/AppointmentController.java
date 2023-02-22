package br.com.dv.api.controller;

import br.com.dv.api.domain.appointment.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentScheduler scheduler;

    @Autowired
    public AppointmentController(AppointmentScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentResponseDto> schedule(@RequestBody @Valid AppointmentSchedulingDto dto) {
        scheduler.schedule(dto);

        return ResponseEntity.ok(new AppointmentResponseDto(
                null,
                null,
                null,
                null,
                null)
        );
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentListingDto>> listAll(@PageableDefault(size = 2, sort = {"scheduledDateTime"})
                                                               Pageable pageable) {
        // TODO

        return ResponseEntity.ok(null);
    }

}
