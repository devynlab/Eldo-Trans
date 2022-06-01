package io.devynlab.eldotrans.generic.exception;

public class IncompleteRestUserCreationData extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message = "Incomplete user information for creating user";

  public IncompleteRestUserCreationData() {
    super();
  }

  public IncompleteRestUserCreationData(String message) {
    super(message);
    this.message = message;
  }

  public IncompleteRestUserCreationData(Throwable cause) {
    super(cause);
  }

  public IncompleteRestUserCreationData(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
