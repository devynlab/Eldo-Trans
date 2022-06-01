package io.devynlab.eldotrans.auth.service;

import io.devynlab.eldotrans.user.model.User;
import io.devynlab.eldotrans.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements Serializable {
  private final UserRepository userRepo;

  public User loggedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    return userRepo.findByUsername(username);
  }
}
