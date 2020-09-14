package lars.refactoring;

import javax.servlet.http.HttpServletRequest;

public class ParameterSource {

  private final HttpServletRequest request;

  public ParameterSource(HttpServletRequest request) {
    this.request = request;
  }

  public HttpServletRequest getRequest() {
    return request;
  }
}
