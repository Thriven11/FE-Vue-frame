package com.vonage.design.restful.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.vonage.core.constants.VonageConstants;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.models.Response;

/**
 * Base class to handle Restful web service. Do not use it directly to hit a
 * REST API. Instead use public classes e.g. BasicClient or OAuthClient.
 *
 * @author Vonage
 */
class RestClient {

    /**
     * Method to execute a request.
     *
     * @param <T>     - Super-type
     * @param request - HttpRequestBase
     * @return Response - Response
     * @throws RestClientException - exception
     */
    <T extends HttpRequestBase> Response execute(final T request) throws RestClientException {
        Response response = null;
        RequestConfig config = RequestConfig.custom().setConnectTimeout(VonageConstants.HTTP_TIMEOUT * 1000)
                .setConnectionRequestTimeout(VonageConstants.HTTP_TIMEOUT * 1000)
                .setSocketTimeout(VonageConstants.HTTP_TIMEOUT * 1000).build();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        public boolean isTrusted(final X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            return true;
                        }
                    }).build()).build();
            response = buildResponse(client.execute(request));
        } catch (IOException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new RestClientException("Could not send request- " + e.getMessage());
        }
        if (response.getStatusCode() != HttpStatus.SC_OK && response.getStatusCode() != HttpStatus.SC_CREATED) {
            throw new RestClientException(
                    "Request did not send 200 or 201. Raw error response:\n".concat(response.getBody()));
        }
        return response;
    }

    /**
     * Build the Response object
     *
     * @param closeableresponse - CloseableHttpResponse
     * @return Response - response object
     * @throws IOException - Exception
     */
    private Response buildResponse(final CloseableHttpResponse closeableresponse) throws IOException {
        Response response = new Response();
        response.setStatusCode(closeableresponse.getStatusLine().getStatusCode());
        HttpEntity entity = closeableresponse.getEntity();
        InputStream stream = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        response.setBody(body.toString());
        reader.close();
        return response;
    }
}
