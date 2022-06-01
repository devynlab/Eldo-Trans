package io.devynlab.eldotrans.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenericResponse {
  private String message;
  private int code;
  private int status;
  private String developerMessage;
  private String path;
  private String timestamp;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
  private List<ApiSubError> subErrors;


  public GenericResponse(final String message) {
    super();
    this.message = message;
    timestamp = formatter.format(LocalDateTime.now());
  }

  public GenericResponse(final String message, final String developerMessage, int code, int status, String path) {
    super();
    this.message = message;
    this.developerMessage = developerMessage;
    this.code = code;
    this.status = status;
    this.path = path;
    timestamp = formatter.format(LocalDateTime.now());
  }

  public GenericResponse(List<ObjectError> allErrors, String developerMessage, int code, int status, String path) {
    this.developerMessage = developerMessage;
    this.code = code;
    this.status = status;
    this.path = path;
    timestamp = formatter.format(LocalDateTime.now());

    String temp = allErrors.stream().map(e -> {
      if (e instanceof FieldError) {
        return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
      } else {
        return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
      }
    }).collect(Collectors.joining(","));
    this.message = "[" + temp + "]";
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public List<ApiSubError> getSubErrors() {
    return subErrors;
  }

  public void setSubErrors(List<ApiSubError> subErrors) {
    this.subErrors = subErrors;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void addValidationErrors(List<FieldError> fieldErrors) {
    fieldErrors.forEach(this::addValidationError);
  }

  public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    constraintViolations.forEach(this::addValidationError);
  }

  public void addValidationError(ConstraintViolation<?> cv) {
    this.addValidationError(
        cv.getRootBeanClass().getSimpleName(),
        ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
        cv.getInvalidValue(),
        cv.getMessage());
  }

  private void addValidationError(FieldError fieldError) {
    this.addValidationError(
        fieldError.getObjectName(),
        fieldError.getField(),
        fieldError.getRejectedValue(),
        fieldError.getDefaultMessage());
  }

  private void addValidationError(ObjectError objectError) {
    this.addValidationError(objectError.getObjectName(),
        objectError.getDefaultMessage());
  }

  private void addValidationError(String object, String message) {
    addSubError(new ApiValidationError(object, message));
  }

  private void addValidationError(String object, String field, Object rejectedValue, String message) {
    addSubError(new ApiValidationError(object, field, rejectedValue, message));
  }

  public void addValidationError(List<ObjectError> globalErrors) {
    globalErrors.forEach(this::addValidationError);
  }

  private void addSubError(ApiSubError subError) {
    if (subErrors == null) {
      subErrors = new ArrayList<>();
    }
    subErrors.add(subError);
  }


  abstract class ApiSubError {
    //
  }

  @Data
  @EqualsAndHashCode
  class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
      this.object = object;
      this.message = message;
    }

    ApiValidationError(String object, String field, Object rejectedValue, String message) {
      this.object = object;
      this.message = message;
      this.field = field;
      this.rejectedValue = rejectedValue;
    }
  }
}
