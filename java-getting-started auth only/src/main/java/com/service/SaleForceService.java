package com.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

public class SaleForceService {
    String baseUrl = "https://login.salesforce.com/services";
    String callUrl = "https://warm-bayou-67102.herokuapp.com/callback";
    public String authen(String code) {
        HttpClient hc = getHttpClient();
        PostMethod post = new PostMethod(getAuthenticateUrl());

        NameValuePair[] data = {
                new NameValuePair("code", code),
                new NameValuePair("client_secret", "07F0ED608A1173E23147E018AA0DEFC310EAEA6FAB71756E91B120F86CD2B9AD"),
                new NameValuePair("client_id", "3MVG9l2zHsylwlpQ3vbgaGImBey5grym43Gg2E0Uf3YmdyaoQv839xTCbcdeATN40QgtbPk1eg9ruc0v1El58"),
                new NameValuePair("rediret_uri", callUrl),
                new NameValuePair("", "code"),
        };
        post.setRequestBody(data);
        try {
            hc.executeMethod(post);
            String body = post.getResponseBodyAsString();
            JSONObject json = new JSONObject(body);
            return json.getString("access_token");

        } catch (Exception e) {

        }
        return "";
    }

    public String initial() {
        HttpClient hc = getHttpClient();
        GetMethod get = new GetMethod(getInitialUrl());
        try {
            hc.executeMethod(get);
            String body = get.getResponseBodyAsString();
            return body;
        } catch (Exception e) {

        }
        return "";
    }

    private static HttpClient getHttpClient() {
        HttpClient hc = new HttpClient();
        hc.getParams().setParameter("http.connection.timeout", 10000);
        hc.getParams().setParameter("http.socket.timeout", 10000);
        return hc;
    }

    String getAuthenticateUrl() {
        return baseUrl + "/oauth2/token";
    }

    String getInitialUrl() {
        String parameter = "client_id=3MVG9l2zHsylwlpQ3vbgaGImBey5grym43Gg2E0Uf3YmdyaoQv839xTCbcdeATN40QgtbPk1eg9ruc0v1El58&"+
                "response_type=code&"+
                "redirect_uri="+callUrl;
        return baseUrl + "/oauth2/authorize?"+parameter;
    }
}
