package io.devynlab.eldotrans.generic.exception;

import io.devynlab.eldotrans.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @Autowired
  private MessageSource messages;
  @Autowired
  HttpServletRequest servletRequest;

  private HttpHeaders getHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    //        httpHeaders.add("Content-Type", "application/json");
    //        httpHeaders.add("Access-Control-Allow-Origin", "*");
    //        httpHeaders.add("Accept", "*");
    return httpHeaders;
  }

  public RestResponseEntityExceptionHandler() {
    super();
  }

  // 403
  @ExceptionHandler({CustomWebApplicationException.class})
  public ResponseEntity<Object> handleAccessDenied(final CustomWebApplicationException ex, final WebRequest request) {
    logger.error("Custom Web error");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), ex.getStatus().value(), ex.getStatus().value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), ex.getStatus(), request);
  }

  // 400
  @Override
  protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    logger.error("400 Status Code");
    final BindingResult result = ex.getBindingResult();
    final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }


  /**
   * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
   *
   * @param ex      MissingServletRequestParameterException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    logger.error("Parameter missing");
    String error = ex.getParameterName() + " parameter is missing";

    final GenericResponse bodyOfResponse = new GenericResponse(error, ex.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }


  /**
   * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
   *
   * @param ex      HttpMediaTypeNotSupportedException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

    final GenericResponse bodyOfResponse = new GenericResponse(builder.substring(0, builder.length() - 2), ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
  }

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    GenericResponse bodyOfResponse = new GenericResponse("Validation error", ex.getMessage()
        , status.value(), status.value(),
        getRequestPath());
    bodyOfResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
    bodyOfResponse.addValidationError(ex.getBindingResult().getGlobalErrors());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), status, request);
  }

  /**
   * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
   *
   * @param ex      HttpMessageNotReadableException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String error = "Malformed JSON request";
    final GenericResponse bodyOfResponse = new GenericResponse(error, ex.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);

  }

  /**
   * Handle HttpMessageNotWritableException.
   *
   * @param ex      HttpMessageNotWritableException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String error = "Error writing JSON output";
    final GenericResponse bodyOfResponse = new GenericResponse(error, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.error("handleNoHandlerFoundException ====");
    final GenericResponse bodyOfResponse = new GenericResponse(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()),
        ex.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);

  }

  /**
   * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
   *
   * @param ex the ConstraintViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
    logger.error("Validatio eerro");
    final GenericResponse bodyOfResponse = new GenericResponse("Validation error", ex.getMessage()
        , HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    bodyOfResponse.addValidationErrors(ex.getConstraintViolations());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
   *
   * @param ex the EntityNotFoundException
   * @return the ApiError object
   */
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      EntityNotFoundException ex) {
    logger.error("Enetity  not found");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value(),
        getRequestPath());
    return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
   *
   * @param ex the DataIntegrityViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String messages = ex.getMessage();
    if (ex.getCause() instanceof ConstraintViolationException) {
      status = HttpStatus.CONFLICT;
      messages = "Database error";
    }

    final GenericResponse bodyOfResponse = new GenericResponse(messages, ex.getMessage(), status.value(), status.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), status, request);
  }

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    WebRequest request) {
    final GenericResponse bodyOfResponse = new GenericResponse(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()),
        ex.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);

  }


  @ExceptionHandler({InvalidOldPasswordException.class})
  public ResponseEntity<Object> handleInvalidOldPassword(final RuntimeException ex, final WebRequest request) {
    logger.error("400 Status Code");
    final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.invalidOldPassword", null, request.getLocale()), "InvalidOldPassword"
        , HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }


  // 401
  @ExceptionHandler({UnauthorizedException.class})
  public ResponseEntity<Object> handleUnauthorized(final RuntimeException ex, final WebRequest request) {
    logger.error("Unauthrized error");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), 401, 401,
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }

  // 403
  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDenied(final RuntimeException ex, final WebRequest request) {
    logger.error("Access debied");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), 403, 403,
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_GATEWAY, request);
  }

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
    logger.error("Bad request");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  // 422
  @ExceptionHandler({InvalidInputException.class})
  public ResponseEntity<Object> handleInvalidInput(final RuntimeException ex, final WebRequest request) {
    logger.error("Invalid Input");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
  }

  // 403
  @ExceptionHandler({InvalidDataException.class})
  public ResponseEntity<Object> handleInvalidData(final RuntimeException ex, final WebRequest request) {
    logger.error("Invalid data");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.FORBIDDEN, request);
  }

  // 404
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
    logger.error("Not found");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  // 409
  @ExceptionHandler({UserAlreadyExistException.class})
  public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
    logger.error("409 Status Code");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage()
        , HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.value(),
        getRequestPath());
    return handleExceptionInternal(ex, bodyOfResponse, getHttpHeaders(), HttpStatus.CONFLICT, request);
  }

  // 500
  /* @ExceptionHandler({MailAuthenticationException.class})
  public ResponseEntity<Object> handleMail(final RuntimeException ex, final WebRequest request) {
    logger.error("Handle Mail Error");
    final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getMessage()
        , HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
        getRequestPath());
    return new ResponseEntity<Object>(bodyOfResponse, getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  } */


  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {

    logger.info("Local Message" + ex.getLocalizedMessage());
    logger.info("Message " + ex.getMessage());
    logger.info("Context PAth 4" + servletRequest.getRequestURI() + "?" + servletRequest.getQueryString());
    final GenericResponse bodyOfResponse = new GenericResponse("Internal Error Occurred", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
        getRequestPath());
    return new ResponseEntity<Object>(bodyOfResponse, getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }


  private String getRequestPath() {
    logger.error("Custom Web error path==" + servletRequest.getServletPath());
    String path = servletRequest.getRequestURI();
    if (servletRequest.getQueryString() == null)
      return path;
    return path + "?" + servletRequest.getQueryString();
  }

}
