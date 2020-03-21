package lars.refactoring;

import java.util.Objects;

class QueryString {

  private String _query;

  public QueryString(String queryString) {
    Objects.requireNonNull(queryString);
    _query = queryString;
  }

  public int count() {
    if ("".equals(_query)) {
      return 0;
    }
    String[] pairs = _query.split("&");
    return pairs.length;
  }

  public String valueFor(String name) {
    String[] pairs = _query.split("&");
    for (String pair : pairs) {
      String[] nameAndValue = pair.split("=");
      if (nameAndValue[0].equals(name)) {
        return nameAndValue[1];
      }
    }
    throw new RuntimeException(name + " not found");
  }
}
