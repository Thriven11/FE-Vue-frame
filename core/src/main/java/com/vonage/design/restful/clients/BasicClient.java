package com.vonage.design.restful.clients;

import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.models.Response;

/**
 * Basic Client - Use this to access APIs that require no authentication
 *
 * @author Vonage
 */
public class BasicClient extends RestClient {

    /**
     * Method to hit a GET request.
     *
     * @param url - The request URL
     * @return Response - HttpResponse
     * @throws RestClientException - exception
     */
    protected final Response get(final String url) throws RestClientException {
        HttpGet httpGet = new HttpGet(url);
        return super.execute(httpGet);
    }

    /**
     * Method to hit a POST request.
     *
     * @param url   - The request URL
     * @param pairs - fields for posting
     * @return Response - HttpResponse
     * @throws RestClientException - exception
     */
    protected final Response post(final String url, final List<NameValuePair> pairs) throws RestClientException {
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));
        return super.execute(httpost);
    }
}
