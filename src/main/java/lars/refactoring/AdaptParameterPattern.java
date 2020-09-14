package lars.refactoring;

import javax.servlet.http.HttpServletRequest;

class AdaptParameterPattern {

  public void populate(HttpServletRequest request) {
    String[] values = request.getParameterValues("pageStateName");
    if (values != null && values.length > 0) {
      doSomething(values[0]);
    }
  }

  private void doSomething(String value) {
    throw new UnsupportedOperationException("implement me");
  }
}
