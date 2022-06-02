package io.devynlab.eldotrans.system.booking.rest;

import io.devynlab.eldotrans.generic.controller.BaseController;
import io.devynlab.eldotrans.system.booking.dto.BookingDTO;
import io.devynlab.eldotrans.system.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class BookingController extends BaseController {

  private final BookingService bookingService;

  @PostMapping()
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity post(@RequestBody @Valid BookingDTO bookingDTO) {
    return entity(bookingService.save(bookingDTO));
  }

  @GetMapping()
  @ResponseBody
  @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_DRIVER'})")
  public ResponseEntity findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                @RequestParam(name = "search", required = false) String search) {
    return entity(bookingService.findAllPaginated(page, pageSize, search));
  }

  @GetMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity getOne(@PathVariable("id") Long bookingId) {
    return entity(bookingService.findById(bookingId));
  }

  @PutMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity update(@PathVariable("id") Long bookingId, @RequestBody @Valid BookingDTO bookingDTO) {
    return entity(bookingService.update(bookingId, bookingDTO));
  }

}
