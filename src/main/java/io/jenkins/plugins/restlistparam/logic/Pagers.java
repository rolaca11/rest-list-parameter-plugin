package io.jenkins.plugins.restlistparam.logic;

import com.jayway.jsonpath.JsonPath;
import okhttp3.HttpUrl;
import okhttp3.Request;

public enum Pagers {
  NO_PAGER() {

  },
  NEXUS() {
    @Override
    public boolean isLastPage(String responseBody) {
      if (responseBody == null) {
        return false;
      }

      return JsonPath.parse(responseBody).read(CONTINUATION_TOKEN, String.class) == null;
    }

    @Override
    public Request applyPaging(String previousResponseBody, Request request) {
      if (previousResponseBody == null) {
        return request;
      }

      String continuationToken = JsonPath.parse(previousResponseBody).read(CONTINUATION_TOKEN, String.class);

      HttpUrl url = request.url().newBuilder().addQueryParameter("continuationToken", continuationToken).build();
      return request.newBuilder().url(url).build();
    }
  };

  private static final String CONTINUATION_TOKEN = "$.continuationToken";

  /**
   * Determines if there are additional pages that can be requested.
   *
   * @param responseBody response of previous request, or null if first request
   * @return true if there are additional pages that can be requested, false otherwise
   */
  public boolean isLastPage(String responseBody) {
    return responseBody != null;
  }

  /**
   * Adds properties to the request to get the next page
   * @param previousResponseBody response of previous request, or null if first request.
   * @param request upcoming request
   */
  public Request applyPaging(String previousResponseBody, Request request) {
    return request;
  }
}
