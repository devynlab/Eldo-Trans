package io.devynlab.eldotrans.generic.controller;

import org.springframework.http.ResponseEntity;

public abstract class BaseController {
  public ResponseEntity success() {
    return ResponseEntity.status(200).body(new SuccessResponse("true", "Successful"));
  }

  public ResponseEntity success(String message) {
    return ResponseEntity.status(200).body(new SuccessResponse("true", message));
  }

  public ResponseEntity failure(String message) {
    return ResponseEntity.status(400).body(new SuccessResponse("false", message));
  }

  public ResponseEntity accessDenied() {
    return ResponseEntity.status(403).body(new SuccessResponse("false", "Access denied"));
  }

  public ResponseEntity notPermitted() {
    return ResponseEntity.status(404).body(new SuccessResponse("false", "Operation Not Permitted"));
  }

  public ResponseEntity notFound() {
    return ResponseEntity.status(404).body(new SuccessResponse("false", "Not found"));
  }

  public ResponseEntity notFound(String message) {
    return ResponseEntity.status(404).body(new SuccessResponse("false", message));
  }

  public ResponseEntity invalidInput() {
    return ResponseEntity.status(405).body(new SuccessResponse("false", "Invalid input"));
  }

  public ResponseEntity invalidInput(String message) {
    return ResponseEntity.status(405).body(new SuccessResponse("false", message));
  }

  public ResponseEntity serverFailure() {
    return ResponseEntity.status(500).body(new SuccessResponse("false", "Server Error"));
  }

  public ResponseEntity entity(Object entity) {
    return ResponseEntity.status(200).header("Content-Type", "application/json").body(entity);
  }

  public ResponseEntity response(Object entity, String contentType) {
    if (contentType == null) contentType = "application/json";
    return ResponseEntity.status(200).header("Content-Type", contentType).body(entity);
  }
}
