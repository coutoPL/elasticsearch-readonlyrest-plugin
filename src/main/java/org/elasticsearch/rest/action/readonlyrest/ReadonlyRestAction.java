package org.elasticsearch.rest.action.readonlyrest;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestFilter;
import org.elasticsearch.rest.RestFilterChain;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.rest.StringRestResponse;

/**
 * Readonly REST plugin. Adding some access control to the fast Netty based REST
 * interface of Elasticsearch.
 * 
 * This plugin is configurable from elasticsearch.yml. Example configuration:
 * 
 * readonlyrest: 
 *  enable: true 
 *  allow_localhost: true 
 *  whitelist: [192.168.1.144]
 *  forbidden_uri_re: .*bar_me_pls.* 
 *  barred_reason_string: <h1>unauthorized</h1>
 * 
 * @author <a href="mailto:scarduzio@gmail.com">Simone Scarduzio</a>
 * @see <a href="https://github.com/sscarduzio/elasticsearch-readonlyrest-plugin/">Github Project</a>
 */

public class ReadonlyRestAction extends BaseRestHandler {

  private static ConfigurationHelper conf;
  private Gatekeeper                 gk;

  @Inject
  public ReadonlyRestAction(final Settings settings, Client client, RestController controller) {
    super(settings, client);
    conf = new ConfigurationHelper(settings, logger);
    gk = new Gatekeeper(logger, conf);
    controller.registerFilter(new RestFilter() {

      @Override
      public void process(RestRequest request, RestChannel channel, RestFilterChain filterChain) {
        if (!gk.isHostInternal(channel) && gk.checkIsRequestReadonly(request.method(), request.uri(), request.content().length()) && gk.checkRegexp(conf.getForbiddenUriRe(), request.uri())) {
          logger.warn("barring request: " + request.method() + ":" + request.uri());
          channel.sendResponse(new StringRestResponse(RestStatus.FORBIDDEN, conf.getBarredReasonString()));
          return;
        }

        filterChain.continueProcessing(request, channel);
      }
    });
  }

  public void handleRequest(final RestRequest request, final RestChannel channel) {
  }
}
