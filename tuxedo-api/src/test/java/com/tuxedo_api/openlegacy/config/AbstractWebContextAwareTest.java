package com.tuxedo_api.openlegacy.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for spring boot web context aware tests.<br/>
 * Sets base configuration to run tests in spring boot environment.<br/>
 * Sets a mock http servlet environment.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS, listeners = {
        WithSecurityContextTestExecutionListener.class
})
@ActiveProfiles({ "common-test" })
public abstract class AbstractWebContextAwareTest {
    private static final Logger logger = LoggerFactory.getLogger(AbstractWebContextAwareTest.class);

    @LocalServerPort
    protected int randomServerPort;

    protected String baseUrl;

    private String oauthTokenUrl;
    private String accessToken;

    protected ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        baseUrl = String.format("http://localhost:%s", randomServerPort);
        oauthTokenUrl = String.format("%s/oauth/token", baseUrl);
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, Object> extractJsonResponse(ResponseEntity<Object> response) throws Exception {
        Object json = response.getBody();
        Assert.assertNotNull(json);
        String jsonString = mapper.writeValueAsString(json);
        Map<String, Object> jsonMap = mapper.readValue(jsonString, Map.class);
        logger.debug("Client response:\n" + this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
        return jsonMap;
    }
    
    /**
     * Initializes a provided {@link HttpHeaders} instance with default header
     * values. If provided {@link HttpHeaders} instance is null - creates a new
     * instance.
     * 
     * @param headers
     *            (optional) {@link HttpHeaders} instance. Could be {@code null}.
     * @return initialized {@link HttpHeaders} instance with default header values
     * @throws Exception
     */
    private HttpHeaders initHttpHeaders(HttpHeaders headers) throws Exception {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        return headers;
    }
    
    protected ResponseEntity<Object> sendServiceRequest(String url, HttpMethod httpMethod, Object body) throws Exception {
        return sendServiceRequest(url, httpMethod, initHttpHeaders(null), null, body);
    }
    
    protected ResponseEntity<Object> sendServiceRequest(String url, HttpMethod httpMethod, HttpHeaders headers,
            Map<String, Object> queryParameters, Object body, Object... uriVariables) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        if (uriVariables == null) {
            uriVariables = new Object[] {};
        }
        url = formatQueryParametersUrl(url, queryParameters);
        headers = initHttpHeaders(headers);
        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

        return restTemplate.exchange(url, httpMethod, entity, Object.class, uriVariables);
    }
    
    protected String formatQueryParametersUrl(String url, Map<String, Object> queryParameters) {
        if (queryParameters == null || queryParameters.isEmpty() || url == null || url.isEmpty()) {
            return url;
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        for(Entry<String, Object> entry : queryParameters.entrySet()) {
            builder.queryParam(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return builder.build().toUriString();
    }

    private String getAccessToken() throws Exception {
        if (accessToken != null) {
            return accessToken;
        }
        
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("grant_type", "client_credentials");
        parameters.add("scope", "test");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue());
        
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        ResponseEntity<Object> response = restTemplate.exchange(oauthTokenUrl, HttpMethod.POST, entity, Object.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        
        Map<String, Object> result = extractJsonResponse(response);
        String accessToken = (String) result.get("access_token");
        Assert.assertTrue(StringUtils.isNotEmpty(accessToken));
        
        return accessToken;
    }

    private String getAuthorizationHeaderValue() {
        String auth = String.format("%s:%s","client_id", "client_secret");
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.defaultCharset()));
        String authHeaderValue = "Basic " + new String( encodedAuth );
        
        return authHeaderValue;
    }
    
}

