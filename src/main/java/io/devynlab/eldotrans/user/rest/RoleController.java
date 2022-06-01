package io.devynlab.eldotrans.user.rest;

import io.devynlab.eldotrans.generic.controller.BaseController;
import io.devynlab.eldotrans.user.dto.RoleDTO;
import io.devynlab.eldotrans.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {

  private final RoleService roleService;

  @PostMapping
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity post(@RequestBody @Valid RoleDTO roleDTO) {
    return entity(roleService.save(roleDTO));
  }

  @GetMapping()
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity findAll() {
    return entity(roleService.findAll());
  }

}
