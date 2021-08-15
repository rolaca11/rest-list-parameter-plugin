package io.jenkins.plugins.restlistparam.logic.paging;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public interface Paging {

  /**
   * Determines if there are additional pages that can be requested.
   *
   * @param response response of previous request, or null if first request
   * @return true if there are additional pages that can be requested, false otherwise
   */
  default boolean isLastPage(Response response) throws IOException {
    return true;
  }

  /**
   * Adds properties to the request to get the next page
   * @param previousResponse response of previous request, or null if first request.
   * @param request upcoming request
   */
  default void applyPaging(Response previousResponse, Request request) throws IOException {

  }
}
