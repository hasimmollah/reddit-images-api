
package com.lp.reddittopimages.redditimages_api;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lp.reddittopimages.redditimages_api.service.RedditIntegrationService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("IntegrationTest")
public class RedditImagesControllerTest {

    private static final String FETCH_IMAGES_PATH = "/images";

    @Autowired
    private MockMvc mockMvc;

    private static ClientAndServer mockServer;
   
    private static int port = 8686;
    
    @Autowired
    RedditIntegrationService redditIntegrationService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /*System.setProperty("applicationLogDir", "log");
        System.setProperty("logFilePrefix", "logger-ut");
        System.setProperty("debugLevel", "debug");
        System.setProperty("infoLevel", "info");
        System.setProperty("consoleOutput", "true");
        System.setProperty("currentSchema", "ddaaf002");
        System.setProperty("auditServiceUrl", "http://audit");
        System.setProperty("hystrixThreadTimeoutInMillis", "10000");
        System.setProperty("cmasCardApiUrl", "http://127.0.0.1:" + port + "/cmas-card-api");
        System.setProperty("cmasCardApiUrl", "http://127.0.0.1:" + port + "/cmas-card-api");
        System.setProperty("cryptoServiceUrl", "http://127.0.0.1:" + port + "/verify");
        System.setProperty("acspMockUrl", "http://127.0.0.1:" + port + "/validateSignatureACSP");

        System.setProperty("redditBaseUrl", "http://127.0.0.1:" + port + "/r/");*/

        mockServer = ClientAndServer.startClientAndServer(port);

    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }


   /* @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("applicationLogDir", "log");
        System.setProperty("logFilePrefix", "logger-ut");
        System.setProperty("debugLevel", "debug");
        System.setProperty("infoLevel", "info");
        System.setProperty("consoleOutput", "true");
        System.setProperty("currentSchema", "ddaaf002");
        System.setProperty("auditServiceUrl", "http://audit");
        System.setProperty("hystrixThreadTimeoutInMillis", "10000");
        System.setProperty("cmasCardApiUrl", "http://127.0.0.1:" + port + "/cmas-card-api");
        System.setProperty("cmasCardApiUrl", "http://127.0.0.1:" + port + "/cmas-card-api");
        System.setProperty("cryptoServiceUrl", "http://127.0.0.1:" + port + "/verify");
        System.setProperty("acspMockUrl", "http://127.0.0.1:" + port + "/validateSignatureACSP");



    }
*/
   
    private void prepareMockResponse(String host, int port, String method, String path,String response) {
        new MockServerClient(
            host,
            port).when(request().withMethod(method).withPath(path))
                .respond(response().withBody(response));
    }

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        
    }

    @Test
    public void testCheckHealth() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/")
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(response.getContentAsString(), "Application is up");
    }
    
    
    @Test
    public void testFetchImagesvalidDataNotFound() throws Exception {
       
        HttpHeaders headers = createValidHeader("test1");
        String mockResponse = prepareMockData("src/test/data/reddit_rsp_err.json");
        prepareMockResponse("127.0.0.1", port, "GET", "/r/test1/.json",  mockResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(FETCH_IMAGES_PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).headers(headers);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(response.getStatus()));
        
        
        
    }
    
    @Test
    public void testFetchImages() throws Exception {
       
        HttpHeaders headers = createValidHeader("Sun");
        String mockResponse = prepareMockData("src/test/data/reddit_rsp.json");
        prepareMockResponse("127.0.0.1", port, "GET", "/r/Sun/.json",  mockResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(FETCH_IMAGES_PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).headers(headers);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getStatus()));
        
        
        
    }
    
    @Test
    public void testFetchImagesDataNotFound() throws Exception {
       
        HttpHeaders headers = createValidHeader("Sun1");
        String mockResponse = prepareMockData("src/test/data/reddit_rsp.json");
        prepareMockResponse("127.0.0.1", port, "GET", "/r/Sun/.json",  mockResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(FETCH_IMAGES_PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).headers(headers);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(response.getStatus()));
        
        
        
    }
    
    @Test
    public void testFetchImagesJunkData() throws Exception {
       
        HttpHeaders headers = createValidHeader("test3");
        String mockResponse = prepareMockData("src/test/data/reddit_rsp_junk.json");
        prepareMockResponse("127.0.0.1", port, "GET", "/r/test3/.json",  mockResponse);
        
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(FETCH_IMAGES_PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).headers(headers);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.valueOf(response.getStatus()));
        
        
        
    }
    
    @Test
    public void testFetchImagesMissingInputs() throws Exception {
       
        HttpHeaders headers = createValidHeader("test4");
        headers.remove("topic");
        String mockResponse = prepareMockData("src/test/data/reddit_rsp_junk.json");
        prepareMockResponse("127.0.0.1", port, "GET", "/r/test4/.json",  mockResponse);
        
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(FETCH_IMAGES_PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).headers(headers);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.valueOf(response.getStatus()));
        
        
        
    }
    
    
    @Test
    public void testFetchImagesInvalidData() throws Exception {
       
        HttpHeaders headers = createValidHeader("test7");
        String mockResponse = prepareMockData("src/test/data/reddit_rsp_invalid.json");
        prepareMockResponse("127.0.0.1", port, "GET", "/r/test7/.json",  mockResponse);
        
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(FETCH_IMAGES_PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).headers(headers);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.valueOf(response.getStatus()));
        
        
        
    }
    
    
    
    
    
    

    private HttpHeaders createValidHeader(String topic) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("topic", topic);
       
        headers.add("Content-Type", "application/json");
        return headers;
    }

    
    public static String prepareMockData(String dataFile) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(
                dataFile));
            return jsonObject.toJSONString();
        } catch (IOException | NullPointerException | ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }
   

    
}
