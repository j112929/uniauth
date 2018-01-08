package com.dianrong.common.uniauth.cas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientUtils {

    private static final String        APPLICATION_X_WWW_FORM_URLENCODED = ContentType.APPLICATION_FORM_URLENCODED
                                                                                 .getMimeType();
    private static final String        APPLICATION_JSON                  = ContentType.APPLICATION_JSON.getMimeType();

    private static final String        CHARTSET                          = "UTF-8";

    private static final int           CONNTIMEOUT                       = 60000;

    private static final int           READTIMEOUT                       = 60000;

    private static final int     MAX_RETRY                         = 3;

    private static CloseableHttpClient httpClient                        = null;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();

        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", plainsf).register("https", sslsf).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);

        // 将最大连接数增加到1000
        cm.setMaxTotal(1000);

        // 将每个路由基础的连接增加到100
        cm.setDefaultMaxPerRoute(50);

        cm.setValidateAfterInactivity(10000);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        	@Override
            public boolean retryRequest(IOException exception,
                    int executionCount, HttpContext context) {
                if (executionCount >= MAX_RETRY) {// 如果已经重试了MAX_RETRY次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                return false;
            }
        };

        httpClient = HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler).build();
    }

    public static <T> T postForObject(String url, String body, Class<T> toClass) throws IOException {
        String res = post(url, body, APPLICATION_X_WWW_FORM_URLENCODED, CHARTSET, CONNTIMEOUT, READTIMEOUT);
        return JsonHelper.parseToObject(res, toClass);
    }

    public static Map<?, ?> postForMap(String url, String body) throws IOException {
        String res = post(url, body, APPLICATION_X_WWW_FORM_URLENCODED, CHARTSET, CONNTIMEOUT, READTIMEOUT);
        return JsonHelper.parseToMap(res);
    }

    public static String post(String url, String body) throws IOException {
        return post(url, body, APPLICATION_X_WWW_FORM_URLENCODED, CHARTSET, CONNTIMEOUT, READTIMEOUT);
    }

    public static <T> T postJsonData(String url, String body, Class<T> toClass) throws IOException {
        String res = post(url, body, APPLICATION_JSON, CHARTSET, CONNTIMEOUT, READTIMEOUT);
        return JsonHelper.parseToObject(res, toClass);
    }

    public static <T> T postFormData(String url, Map<String, String> params, Class<T> toClass) throws IOException {
        String res = postForm(url, params, null, CONNTIMEOUT, READTIMEOUT);
        return JsonHelper.parseToObject(res, toClass);
    }

    public static String postFormData(String url, Map<String, String> params) throws IOException {
        return postForm(url, params, null, CONNTIMEOUT, READTIMEOUT);
    }

    public static <T> T getForObject(String url, Class<T> toClass) throws UnsupportedOperationException, IOException {
        String res = get(url, null, CHARTSET, CONNTIMEOUT, READTIMEOUT);
        return JsonHelper.parseToObject(res, toClass);
    }

    public static String get(String url, String charset) throws IOException {
        return get(url, null, charset == null ? CHARTSET : charset, CONNTIMEOUT, READTIMEOUT);
    }

    public static String get(String url, Map<String, Object> headers, String charset) throws IOException {
        return get(url, headers, charset == null ? CHARTSET : charset, CONNTIMEOUT, READTIMEOUT);
    }

    public static String post(String url, String body, String mimeType, String charset, Integer connTimeout,
                              Integer readTimeout) throws IOException {
        HttpPost post = new HttpPost(url);
        String result = null;
        try {
            if (body!=null && "".equals(body)) {
                HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
                post.setEntity(entity);
            }
            RequestConfig customReqConfig = getCustomReqConfig(connTimeout, readTimeout);
            post.setConfig(customReqConfig);
            HttpResponse res = httpClient.execute(post);
            result = EntityUtils.toString(res.getEntity(), charset);
        } finally {
            post.releaseConnection();
        }
        return result;
    }
    /**
     * Post Request
     * @return
     * @throws Exception
     */
    public static String connect(String url,String param,String method,Map<String, String> head) throws Exception {
        //LoggerUtil.info(logger, String.format("url:%s", url));
        //LoggerUtil.info(logger, String.format("param:%s", param));
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(method);
        
        Iterator<Map.Entry<String, String>> iterator=head.entrySet().iterator();
        while(iterator.hasNext()){
        	Map.Entry<String, String> e=iterator.next();
        	httpURLConnection.setRequestProperty(e.getKey(), e.getValue());
        }
//        System.out.println(param);
//		Content-Type: application/x-www-form-urlencoded; charset=UTF-8
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            
            outputStreamWriter.write(param.toString());
            outputStreamWriter.flush();
            
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = (reader.readLine())) != null) {
                resultBuffer.append(tempLine);
            }
            
        } finally {
            
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }
        //LoggerUtil.info(logger, String.format("result:%s", resultBuffer));

        return resultBuffer.toString();
    }

    public static String postForm(String url, Map<String, String> params, Map<String, String> headers,
                                  Integer connTimeout, Integer readTimeout) throws IOException {
        HttpPost post = new HttpPost(url);
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<>();
                Set<Entry<String, String>> entrySet = params.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }
            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            RequestConfig customReqConfig = getCustomReqConfig(connTimeout, readTimeout);
            post.setConfig(customReqConfig);
            HttpResponse res = httpClient.execute(post);
            return EntityUtils.toString(res.getEntity(), CHARTSET);
        } finally {
            post.releaseConnection();
        }
    }

    public static String sendHttpPost(HttpPost httpPost) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;

        try {
            httpClient = HttpClients.createDefault();
            if (httpPost.getConfig() == null) {
                httpPost.setConfig(getCustomReqConfig(CONNTIMEOUT, READTIMEOUT));
            }
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            return responseContent;
        } finally {
            if(response != null) {
                response.close();
            }
            if(httpClient != null) {
                httpClient.close();
            }
            httpPost.releaseConnection();
        }
    }

    public static String postForm(String url, Map<String, Object> params, Map<String, Object> headers, String charset,
                                  Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {

        HttpPost post = new HttpPost(url);
        try {
            Map.Entry<String, Object> entry;
            if (params != null && !params.isEmpty()) {
                List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
                Set<Map.Entry<String, Object>> entrySet = params.entrySet();
                for (Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator(); iterator.hasNext();) {
                    entry = (Map.Entry<String, Object>) iterator.next();
                    formParams.add(new BasicNameValuePair(entry.getKey(), "" + entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams,
                        charset != null ? Charset.forName(charset.toUpperCase()) : Consts.UTF_8);
                post.setEntity(entity);
            }
            if ((headers != null) && (!headers.isEmpty())) {
                for (Map.Entry<String, Object> entry1 : headers.entrySet()) {
                    post.addHeader(entry1.getKey(), (String) entry1.getValue());
                }
            }

            RequestConfig customReqConfig = getCustomReqConfig(connTimeout, readTimeout);
            post.setConfig(customReqConfig);
            long sl = System.currentTimeMillis();
            HttpResponse res = httpClient.execute(post);
            long el = System.currentTimeMillis();
            log.info(" httpClient.execute 耗时:"+(el-sl)+" ms ,url="+url);
            return EntityUtils.toString(res.getEntity(), CHARTSET);
        } finally {
            post.releaseConnection();
        }
    }

    public static String get(String url, Map<String, Object> headers, String charset, Integer connTimeout,
                             Integer readTimeout) throws UnsupportedOperationException, IOException {
        HttpGet get = new HttpGet(url);
        String result = "";
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                get.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }
        try {
            RequestConfig customReqConfig = getCustomReqConfig(connTimeout, readTimeout);
            get.setConfig(customReqConfig);
            HttpResponse res = httpClient.execute(get);
            result = EntityUtils.toString(res.getEntity(), charset);
        } finally {
            get.releaseConnection();
        }
        return result;
    }

    private static RequestConfig getCustomReqConfig(Integer connTimeout, Integer readTimeout) {
        Builder customReqConf = RequestConfig.custom();
        if (connTimeout != null) {
            customReqConf.setConnectTimeout(connTimeout);
        }
        if (readTimeout != null) {
            customReqConf.setSocketTimeout(readTimeout);
        }
        return customReqConf.build();
    }
    public static class Head extends LinkedHashMap<String,String>{

    	private String type;
    	
    	public String getType() {
			return type;
		}
		public Head setType(String type) {
			this.type = type;
			return this;
		}
		private Head(String[] ... head){
    		for(String[] h:head){
    			addHead(h[0],h[1]);
    		}
    	}
    	private Head(String name,String value){
    		addHead(name,value);
    	}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
    	
		public Head addHead(String name,String value){
			put(name,value);
			return this;
		}
    }
    public static  Head getJsonHead(){
	    return new Head(
    			new String[]{"Accept-Charset", "utf-8"},
    			new String[]{"Content-Type", "application/json"}
    		).setType("JSON");
    }
}
