package com.vonage.core.services.impl;

import java.io.IOException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.replication.AgentConfig;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationResult;
import com.day.cq.replication.ReplicationTransaction;
import com.day.cq.replication.TransportContext;
import com.day.cq.replication.TransportHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.utils.ServiceUtils;

/**
 * Transport handler to send test and purge requests to Cloudflare and handle
 * responses. The handler sets up basic authentication with the user/pass from
 * the replication agent's transport config and sends a GET request as a test
 * and POST as purge request. A valid test response is 200 while a valid purge
 * response is 201.
 *
 * The Flush agent must be configured with following 3 properties:
 *
 * 1. The transport handler is triggered by setting your replication agent's
 * transport URL's protocol to "cloudflare://". E.g. -
 * cloudflare://api.cloudflare.com/client/v4/zones/{zone-id}/purge_cache
 *
 * 2. User: The X-Auth-Email value of cloudflare account
 *
 * 3. Password: X-Auth-Key value of cloudflare account
 *
 * The transport handler builds the POST request body in accordance with
 * Cloudflare's REST APIs
 * {@link https://api.cloudflare.com/#zone-purge-files-by-url/} using the
 * replication agent properties.
 */
@Component(service = TransportHandler.class, name = "Cloudflare-Purge-Agent", immediate = true)
public class CloudflareTransportHandler implements TransportHandler {

    /**
     * externalizer
     */
    @Reference
    private Externalizer externalizer;

    /**
     * resolverFactory
     */
    @Reference
    private ResourceResolverFactory resolverFactory;



  /**
   * logger for logging.
   */
  private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean canHandle(final AgentConfig config) {
        final String transportURI = config.getTransportURI();
        if (transportURI != null) {
            return transportURI.toLowerCase().startsWith(VonageConstants.CF_PROTOCOL);
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ReplicationResult deliver(final TransportContext ctx, final ReplicationTransaction tx) {
        final ReplicationActionType replicationType = tx.getAction().getType();
        if (replicationType == ReplicationActionType.TEST) {
            return doTest(ctx, tx);
        } else if (replicationType == ReplicationActionType.ACTIVATE
                || replicationType == ReplicationActionType.DEACTIVATE) {
            return doActivate(ctx, tx);
        }
        return ReplicationResult.OK;
    }

    /**
     * Send test request to Cloudflare via a GET request.
     *
     * Cloudflare will respond with a 200 HTTP status code if the request was
     * successfully submitted. The response will have information about the queue
     * length, but we're simply interested in the fact that the request was
     * authenticated.
     *
     * @param ctx Transport Context
     * @param tx  Replication Transaction
     * @return ReplicationResult OK if 200 response from Cloudflare
     */
    private ReplicationResult doTest(final TransportContext ctx, final ReplicationTransaction tx) {
        String uri = ctx.getConfig().getTransportURI().replace(VonageConstants.CF_PROTOCOL, "");
        String domain = uri.substring(0, uri.indexOf(AppConstants.SLASH));
        final HttpGet request = new HttpGet(String.format(VonageConstants.CF_TEST_ENDPOINT, domain));
        return getResult(sendRequest(request, ctx, tx), tx);
    }

    /**
     * Send purge request to Cloudflare via a POST request
     *
     * Cloudflare will respond with a 201 HTTP status code if the purge request was
     * successfully submitted.
     *
     * @param ctx - Transport Context
     * @param tx  - Replication Transaction
     * @return ReplicationResult - OK if 201 response from Cloudflare
     */
    private ReplicationResult doActivate(final TransportContext ctx, final ReplicationTransaction tx) {
        final String cfEndPoint = ctx.getConfig().getTransportURI().replace(VonageConstants.CF_PROTOCOL, "https://");
        final HttpPost request = new HttpPost(cfEndPoint);
        tx.getLog().info(request.toString());
        tx.getLog().info("---------------------------------------");
        createPostBody(request, tx);
        return getResult(sendRequest(request, ctx, tx), tx);
    }

    /**
     * Get Replication Result
     *
     * @param response - HttpResponse object
     * @param tx       - ReplicationTransaction object
     * @return ReplicationResult - result
     */
    private ReplicationResult getResult(final HttpResponse response, final ReplicationTransaction tx) {
        if (response != null) {
            final int statusCode = response.getStatusLine().getStatusCode();
            tx.getLog().info("Response :");
            tx.getLog().info(response.toString());
            tx.getLog().info("---------------------------------------");
            if (statusCode == HttpStatus.SC_OK) {
                return ReplicationResult.OK;
            }
        }
        return new ReplicationResult(false, 0, "Replication failed");
    }

    /**
     * Build preemptive basic authentication headers and send request.
     *
     * @param <T>     Type
     * @param request - The request to send to Cloudflare
     * @param ctx     - The TransportContext containing the username and password
     * @param tx      - Replication Transaction
     * @return HttpResponse - The HTTP response from Cloudflare
     */
    private <T extends HttpRequestBase> HttpResponse sendRequest(final T request, final TransportContext ctx,
            final ReplicationTransaction tx) {
        request.setHeader(VonageConstants.CF_PARAM_KEY, ctx.getConfig().getTransportPassword());
        request.setHeader(VonageConstants.CF_PARAM_EMAIL, ctx.getConfig().getTransportUser());
        request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        RequestConfig config = RequestConfig.custom().setConnectTimeout(VonageConstants.HTTP_TIMEOUT * 1000)
                .setConnectionRequestTimeout(VonageConstants.HTTP_TIMEOUT * 1000)
                .setSocketTimeout(VonageConstants.HTTP_TIMEOUT * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
      tx.getLog().info("Request headers");
      logger.info("Request headers");
      Header[] headers = request.getAllHeaders();
      for (Header header : headers) {
        tx.getLog().info(header.getName() + " : " + header.getValue());
        logger.info(header.getName() + " : " + header.getValue());
      }
      tx.getLog().info("---------------------------------------");
      logger.info("---------------------------------------");
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            tx.getLog().error("Could not send replication request- " + e.getMessage());
        }
        return response;
    }

    /**
     * Build the Cloudflare purge request body based on the replication agent
     * settings and append it to the POST request.
     *
     * @param request The HTTP POST request to append the request body
     * @param tx      ReplicationTransaction
     */
    private void createPostBody(final HttpPost request, final ReplicationTransaction tx) {
        JsonObject json = new JsonObject();
        JsonArray purgeObjects = new JsonArray();
        for (String path : tx.getAction().getPaths()) {
            if (StringUtils.isNotBlank(path) && path.startsWith(AppConstants.CONTENT_PATH)) {
                purgeObjects.add(externalizer.externalLink(ServiceUtils.getReadResourceResolver(resolverFactory),
                        Externalizer.PUBLISH, path));
            }
        }
        if (purgeObjects.size() > 0) {
            json.add(VonageConstants.CF_PARAM_FILES, purgeObjects);
            final StringEntity entity = new StringEntity(json.toString(), CharEncoding.ISO_8859_1);
            tx.getLog().info("Clearing cache for paths param: " + json);
            logger.info("Clearing cache for paths param: " + json);
            tx.getLog().info("---------------------------------------");
            logger.info("---------------------------------------");
            request.setEntity(entity);
            tx.getLog().info("request entity: ");
            logger.info("request entity:");
            tx.getLog().info(entity.toString());
            logger.info(entity.toString());
            tx.getLog().info("---------------------------------------");
            logger.info("---------------------------------------");
        }
    }
}
