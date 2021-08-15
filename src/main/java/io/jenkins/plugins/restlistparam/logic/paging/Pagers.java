package io.jenkins.plugins.restlistparam.logic.paging;

import java.io.IOException;

import com.jayway.jsonpath.JsonPath;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public enum Pagers implements Paging {
  NO_PAGER() {

  },

  NEXUS() {
    @Override
    public boolean isLastPage(Response response) throws IOException {
      ResponseBody responseBody = response.body();
      assert responseBody != null;

      return JsonPath.parse(responseBody.string()).read(CONTINUATION_TOKEN, String.class) == null;
    }

    @Override
    public void applyPaging(Response previousResponse, Request request) throws IOException {
      ResponseBody responseBody = previousResponse.body();
      assert responseBody != null;

      String continuationToken = JsonPath.parse(responseBody.string()).read(CONTINUATION_TOKEN, String.class);

      request.url().newBuilder().addQueryParameter("continuationToken", continuationToken);
    }
  };

  private static final String CONTINUATION_TOKEN = "$.continuationToken";
}
