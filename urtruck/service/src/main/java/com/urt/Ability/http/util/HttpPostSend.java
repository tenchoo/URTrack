package com.urt.Ability.http.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.SSLException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpPostSend {
	protected static final Logger logger = Logger.getLogger(HttpPostSend.class);

	private final static int _defaultWaitTime = 20 * 1000;
	private final static int _defaultMaxConnections = 60;
	private final static int _defaultIdleConnectionPersistTime = 30 * 60 * 1000;
	private final static int _defaultRetryNum = 3;
	public final static String DEFAULT_CHARSET = "UTF-8";
	public final static String DEFAULT_CONTENT_TYPE = "application/json";
	private boolean retry = false;
	private int retryNum = _defaultRetryNum;
	private int waitTime = _defaultWaitTime;
	private boolean pooledConnections = true;
	private int maxConnections = _defaultMaxConnections;
	private int idleConnectionPersistTime = _defaultIdleConnectionPersistTime;

	private HttpClient client;

	private boolean isInited = false;
	private Object initMonitor = new Object();
	

	private void init() {
		synchronized (initMonitor) {
			if(!isInited){
				HttpClientBuilder clientBuilder = HttpClients.custom();

				if (pooledConnections) {
					PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
					cm.setDefaultMaxPerRoute(maxConnections);
					cm.setMaxTotal(maxConnections);
					cm.setValidateAfterInactivity(idleConnectionPersistTime / 3);
					clientBuilder.setConnectionManager(cm);
				}

				if (retry) {
					clientBuilder.setRetryHandler(new UipHttpRequestRetryHandler());
				}

				this.client = clientBuilder.build();
				this.isInited = true;
			}else{
				return;
			}
		}
		
	}
	
	public String invoke(String message,String url){
		return _invoke(message,url);
	}	
	private String _invoke(String message,String url) {
		HttpPost post = new HttpPost(url);
		logger.info("1111111111111111111111111"+post);
		HttpEntity content = new StringEntity(message, ContentType.create(DEFAULT_CONTENT_TYPE, DEFAULT_CHARSET));
		RequestConfig timeOut = RequestConfig.custom().setSocketTimeout(waitTime).setConnectTimeout(waitTime).build();
		post.setEntity(content);
		post.setConfig(timeOut);
		if(logger.isDebugEnabled()){
			logger.debug("forward request.METHOD=PSOT,URI="+post.getURI());
		}
			if(!isInited){
				init();
			}
			String str = "";
			try {
				 str =  this.client.execute(post, new ResponseHandler<String>() {
					@Override
					public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
						StatusLine statusLine = response.getStatusLine();
						HttpEntity entity = response.getEntity();
						if (statusLine.getStatusCode() >= 300) {
							logger.error("request failed."+EntityUtils.toString(response.getEntity()));
							throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
						}
						if (entity == null) {
							throw new ClientProtocolException("Response contains no content");
						}
						return EntityUtils.toString(entity, "UTF-8");
					}
				});
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return str;
	}

	class UipHttpRequestRetryHandler implements HttpRequestRetryHandler {

		@Override
		public boolean retryRequest(IOException e, int count, HttpContext context) {
			if (count > retryNum) {
				return false;
			}

			if (e instanceof InterruptedIOException) {
				// Timeout
				return true;
			}
			if (e instanceof UnknownHostException) {
				// Unknown host
				return false;
			}
			if (e instanceof ConnectTimeoutException) {
				// Connection refused
				return false;
			}
			if (e instanceof SSLException) {
				// SSL handshake exception
				return false;
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			if (idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}

	}
}
