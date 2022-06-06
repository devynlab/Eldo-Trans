package io.devynlab.eldotrans.system.trip.rest;

import io.devynlab.eldotrans.generic.controller.BaseController;
import io.devynlab.eldotrans.system.trip.dto.BookingDTO;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import io.devynlab.eldotrans.system.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController extends BaseController {

  private final TripService tripService;

  @PostMapping()
  @ResponseBody
  @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_DRIVER'})")
  public ResponseEntity post(@RequestBody @Valid TripDTO tripDTO) {
    return entity(tripService.save(tripDTO));
  }

  @GetMapping()
  @ResponseBody
  public ResponseEntity findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                @RequestParam(name = "search", required = false) String search) {
    return entity(tripService.findAllPaginated(page, pageSize, search));
  }

  @GetMapping("/filtered")
  @ResponseBody
  public ResponseEntity findAllFiltered(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                        @RequestParam(name = "tripFrom") Destinations tripFrom,
                                        @RequestParam(name = "tripTo") Destinations tripTo,
                                        @RequestParam(name = "date") Date date) {
    return entity(tripService.findAllFiltered(page, pageSize, tripFrom, tripTo, date));
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

  @PutMapping("/{id}/booking")
  @ResponseBody
  public ResponseEntity booking(@PathVariable("id") Long tripId, @RequestBody @Valid BookingDTO bookingDTO) {
    return entity(tripService.booking(tripId, bookingDTO));
  }

  @GetMapping("/{id}/departure")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity departure(@PathVariable("id") Long tripId) {
    return entity(tripService.departure(tripId));
  }

  @GetMapping("/{id}/arrival")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity arrival(@PathVariable("id") Long tripId) {
    return entity(tripService.arrival(tripId));
  }

}
