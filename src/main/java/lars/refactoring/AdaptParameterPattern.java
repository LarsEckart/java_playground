package lars.refactoring;

import javax.servlet.http.HttpServletRequest;

class AdaptParameterPattern {

  public void populate(HttpServletRequest request) {
    String[] values = request.getParameterValues("pageStateName");
    if (values != null && values.length > 0) {
      doSomething(values[0]);
    }
  }

  public void populateAdapted(ParameterSource parameterSource) {
    String value = null;
    String[] values = parameterSource.getRequest().getParameterValues("pageStateName");
    if (values != null && values.length > 0) {
      value = values[0];
    }
    if (values != null && values.length > 0) {
      doSomething(value);
    }
  }

  private void doSomething(String value) {
    throw new UnsupportedOperationException("implement me");
  }
}
