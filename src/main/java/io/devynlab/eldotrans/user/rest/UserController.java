package io.devynlab.eldotrans.user.rest;

import io.devynlab.eldotrans.generic.controller.BaseController;
import io.devynlab.eldotrans.user.dto.UserDTO;
import io.devynlab.eldotrans.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

  private final UserService userService;

  @PostMapping()
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity post(@RequestBody @Valid UserDTO userDTO) {
    return entity(userService.save(userDTO));
  }

  @GetMapping()
  @ResponseBody
  @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_DRIVER'})")
  public ResponseEntity findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                @RequestParam(name = "search", required = false) String search) {
    return entity(userService.findAllPaginated(page, pageSize, search));
  }

  @GetMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity getOne(@PathVariable("id") Long userId) {
    return entity(userService.findById(userId));
  }

  @PutMapping("/{id}")
  @ResponseBody
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity update(@PathVariable("id") Long userId, @RequestBody @Valid UserDTO userDTO) {
    return entity(userService.update(userId, userDTO));
  }

}
