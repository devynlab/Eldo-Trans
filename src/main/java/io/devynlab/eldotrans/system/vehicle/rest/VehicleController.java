package io.devynlab.eldotrans.system.vehicle.rest;

import io.devynlab.eldotrans.generic.controller.BaseController;
import io.devynlab.eldotrans.system.vehicle.dto.VehicleDTO;
import io.devynlab.eldotrans.system.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController extends BaseController {

  private final VehicleService vehicleService;

  @PostMapping()
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity post(@RequestBody @Valid VehicleDTO vehicleDTO) {
    return entity(vehicleService.save(vehicleDTO));
  }

  @GetMapping()
  @ResponseBody
  @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_DRIVER'})")
  public ResponseEntity findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                @RequestParam(name = "search", required = false) String search) {
    return entity(vehicleService.findAllPaginated(page, pageSize, search));
  }

  @GetMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity getOne(@PathVariable("id") Long vehicleId) {
    return entity(vehicleService.findById(vehicleId));
  }

  @PutMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity update(@PathVariable("id") Long vehicleId, @RequestBody @Valid VehicleDTO vehicleDTO) {
    return entity(vehicleService.update(vehicleId, vehicleDTO));
  }

}
