package io.devynlab.eldotrans.system.trip.rest;

import io.devynlab.eldotrans.generic.controller.BaseController;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController extends BaseController {

  private final TripService tripService;

  @PostMapping()
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity post(@RequestBody @Valid TripDTO tripDTO) {
    return entity(tripService.save(tripDTO));
  }

  @GetMapping()
  @ResponseBody
  @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_DRIVER'})")
  public ResponseEntity findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                @RequestParam(name = "search", required = false) String search) {
    return entity(tripService.findAllPaginated(page, pageSize, search));
  }

  @GetMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity getOne(@PathVariable("id") Long tripId) {
    return entity(tripService.findById(tripId));
  }

  @PutMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity update(@PathVariable("id") Long tripId, @RequestBody @Valid TripDTO tripDTO) {
    return entity(tripService.update(tripId, tripDTO));
  }

}
