/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.tools.net.httpclient3;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.enumeration.HttpMethodType;
import com.feilong.commons.core.net.ParamUtil;
import com.feilong.commons.core.net.URIUtil;
import com.feilong.commons.core.tools.json.JsonUtil;
import com.feilong.commons.core.util.Validator;

/**
 * HttpClient相关工具类.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2010-11-18 上午09:35:27
 * @version 1.0.1 2011-7-27 11:59
 * @version 1.0.6 2014-5-7 23:12
 * @see org.apache.commons.httpclient.HttpMethod
 * @see org.apache.commons.httpclient.HttpClient
 * @since 1.0.6
 */
public final class HttpClientUtil{

	/** The Constant log. */
	private final static Logger	log					= LoggerFactory.getLogger(HttpClientUtil.class);

	/** 伪造的 useragent. */
	private final static String	DEFAULT_USER_AGENT	= "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21";

	/**
	 * Gets the http method response body as string.
	 * 
	 * @param httpClientConfig
	 *            the http client config
	 * @return the http method response body as string<br>
	 *         仅支持 {@link HttpMethodType} 类型协议,其他抛出 {@link UnsupportedOperationException}
	 * @throws HttpClientException
	 *             the http client util exception
	 */
	public static String getResponseBodyAsString(HttpClientConfig httpClientConfig) throws HttpClientException{
		HttpMethod httpMethod = getHttpMethod(httpClientConfig);
		return getHttpMethodResponseBodyAsString(httpMethod, httpClientConfig);
	}

	/**
	 * Gets the http method.
	 * 
	 * @param httpClientConfig
	 *            the http client config
	 * @return the http method
	 * @throws HttpClientException
	 *             the http client util exception
	 */
	private static HttpMethod getHttpMethod(HttpClientConfig httpClientConfig) throws HttpClientException{
		if (log.isDebugEnabled()){
			log.debug("[httpClientConfig]:{}", JsonUtil.format(httpClientConfig));
		}

		String uri = httpClientConfig.getUri();
		HttpMethodType httpMethodType = httpClientConfig.getHttpMethodType();

		Map<String, String> params = httpClientConfig.getParams();

		NameValuePair[] nameValuePairs = null;
		if (Validator.isNotNullOrEmpty(params)){
			nameValuePairs = NameValuePairUtil.fromMap(params);
		}

		//		HttpMethod httpMethod = null;

		switch (httpMethodType) {
			case GET:// 使用get方法
				GetMethod getMethod = new GetMethod(uri);

				HttpMethodParams httpMethodParams = getMethod.getParams();
				// TODO
				httpMethodParams.setParameter(HttpMethodParams.USER_AGENT, DEFAULT_USER_AGENT);

				// 使用系统提供的默认的恢复策略
				httpMethodParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

				//				if (Validator.isNotNullOrEmpty(params)){
				//					for (Map.Entry<String, String> entry : params.entrySet()){
				//						String key = entry.getKey();
				//						String value = entry.getValue();
				//						httpMethodParams.setParameter(key, value);
				//					}
				//				}

				//TODO 暂时还不支持 uri中含有参数且  nameValuePairs也有值的情况
				if (Validator.isNotNullOrEmpty(nameValuePairs) && uri.indexOf("?") != -1){
					throw new NotImplementedException("not implemented!");
				}
				getMethod.setQueryString(nameValuePairs);

				return getMethod;

			case POST: // 使用post方法
				PostMethod postMethod = new PostMethod(uri);

				if (Validator.isNotNullOrEmpty(nameValuePairs)){
					postMethod.setRequestBody(nameValuePairs);
				}
				return postMethod;
			default:
				throw new UnsupportedOperationException("httpMethod:" + httpMethodType + " not support!");
		}
	}

	/**
	 * (底层方法)use httpState to create httpmethod.
	 * 
	 * @param httpMethod
	 *            the http method
	 * @param httpClientConfig
	 *            the http client config
	 * @return the http method
	 * @throws HttpClientException
	 *             如果代码执行有异常会以HttpClientUtilException的形式抛出
	 */
	private static HttpMethod executeMethod(HttpMethod httpMethod,HttpClientConfig httpClientConfig) throws HttpClientException{
		HttpClient httpClient = new HttpClient();

		// 认证
		setAuthentication(httpMethod, httpClientConfig, httpClient);

		// 代理
		setProxy(httpClientConfig, httpClient);

		try{

			if (log.isDebugEnabled()){
				// String[] excludes = new String[] { "defaults" };
				// HttpClientParams httpClientParams = httpClient.getParams();
				//
				// log.debug("[httpClient.getParams()]:{}", JsonUtil.format(httpClientParams, excludes));
				//
				// HttpMethodParams httpMethodParams = httpMethod.getParams();
				// log.debug("[httpMethod.getParams()]:{}", JsonUtil.format(httpMethodParams, excludes));

				Map<String, Object> map = getHttpMethodRequestAttributeMapForLog(httpMethod);
				String[] excludes = new String[] { "values", "elements"
				// "rawAuthority",
				// "rawCurrentHierPath",
				// "rawPath",
				// "rawPathQuery",
				// "rawQuery",
				// "rawScheme",
				// "rawURI",
				// "rawURIReference",
				// "rawUserinfo",
				// "rawFragment",
				// "rawHost",
				// "rawName",
				// "protocol",
				// "defaults",
				// "class"
				};
				log.debug(JsonUtil.format(map, excludes));
			}

			// 执行该方法后服务器返回的状态码
			// 该状态码能表示出该方法执行是否成功、需要认证或者页面发生了跳转
			// （默认状态下GetMethod的实例是自动处理跳转的）
			int statusCode = httpClient.executeMethod(httpMethod);
			if (statusCode != HttpStatus.SC_OK){
				log.warn("statusCode is:[{}]", statusCode);
			}

		}catch (Exception e){
			Map<String, Object> map = getHttpMethodResponseAttributeMapForLog(httpMethod);
			log.error(JsonUtil.format(map));

			e.printStackTrace();
			throw new HttpClientException(e);
		}
		return httpMethod;
	}

	/**
	 * 设置 proxy.
	 * 
	 * @param httpClientConfig
	 *            the http client config
	 * @param httpClient
	 *            the http client
	 */
	// TODO未测试
	private static void setProxy(HttpClientConfig httpClientConfig,HttpClient httpClient){
		// 设置代理
		String hostName = httpClientConfig.getProxyAddress();
		if (Validator.isNotNullOrEmpty(hostName)){
			int port = httpClientConfig.getProxyPort();
			HostConfiguration hostConfiguration = httpClient.getHostConfiguration();
			hostConfiguration.setProxy(hostName, port);
		}
	}

	/**
	 * 设置 authentication.
	 * 
	 * @param httpMethod
	 *            the http method
	 * @param httpClientConfig
	 *            the http client config
	 * @param httpClient
	 *            the http client
	 */
	private static void setAuthentication(HttpMethod httpMethod,HttpClientConfig httpClientConfig,HttpClient httpClient){
		UsernamePasswordCredentials usernamePasswordCredentials = httpClientConfig.getUsernamePasswordCredentials();
		// 设置认证
		if (Validator.isNotNullOrEmpty(usernamePasswordCredentials)){
			httpMethod.setDoAuthentication(true);

			AuthScope authScope = AuthScope.ANY;
			Credentials credentials = usernamePasswordCredentials;

			httpClient.getState().setCredentials(authScope, credentials);

			// 1.1抢先认证(Preemptive Authentication)
			// 在这种模式时，HttpClient会主动将basic认证应答信息传给服务器，即使在某种情况下服务器可能返回认证失败的应答，
			// 这样做主要是为了减少连接的建立。

			HttpClientParams httpClientParams = new HttpClientParams();
			httpClientParams.setAuthenticationPreemptive(true);
			httpClient.setParams(httpClientParams);
		}
	}

	/**
	 * 获得HttpMethod请求结果.
	 * 
	 * @param httpMethod
	 *            httpMethod
	 * @param httpClientConfig
	 *            the http client config
	 * @return the http method response body as string
	 * @throws HttpClientException
	 *             如果代码执行有异常会以HttpClientUtilException的形式抛出
	 */
	private static String getHttpMethodResponseBodyAsString(HttpMethod httpMethod,HttpClientConfig httpClientConfig)
					throws HttpClientException{

		try{
			httpMethod = executeMethod(httpMethod, httpClientConfig);
			// httpMethod.getParams().setContentCharset(charSet);

			// 第一种，getResponseBody，该方法返回的是目标的二进制的byte流；
			// 第二种，getResponseBodyAsString，这个方法返回的是String类型，值得注意的是该方法返回的String的编码是根据系统默认的编码方式，所以返回的String值可能编码类型有误，在本文的"字符编码"部分中将对此做详细介绍；
			// 第三种，getResponseBodyAsStream，这个方法对于目标地址中有大量数据需要传输是最佳的。

			// 得到返回的数据
			String responseBodyAsString = httpMethod.getResponseBodyAsString();
			return responseBodyAsString;

		}catch (Exception e){
			e.printStackTrace();
			throw new HttpClientException(e);
		}finally{
			// 释放连接
			httpMethod.releaseConnection();
		}
	}

	/**
	 * 请求信息log.
	 * 
	 * @param httpMethod
	 *            the http method
	 * @return the http method attribute map for log
	 */
	private static Map<String, Object> getHttpMethodRequestAttributeMapForLog(HttpMethod httpMethod){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try{
			map.put("httpMethod.getName()", httpMethod.getName());
			map.put("httpMethod.getPath()", httpMethod.getPath());
			map.put("httpMethod.getQueryString()", httpMethod.getQueryString());
			map.put("httpMethod.getDoAuthentication()", httpMethod.getDoAuthentication());
			map.put("httpMethod.getFollowRedirects()", httpMethod.getFollowRedirects());
			map.put("httpMethod.getHostAuthState()", httpMethod.getHostAuthState().toString());

			// HttpMethodParams httpMethodParams = httpMethod.getParams();
			// map.put("httpMethod.getParams()", httpMethodParams);
			map.put("httpMethod.getProxyAuthState()", httpMethod.getProxyAuthState().toString());
			map.put("httpMethod.getURI()", httpMethod.getURI().toString());
			map.put("httpMethod.getRequestHeaders()", httpMethod.getRequestHeaders());
		}catch (Exception e){
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 返回信息log.
	 * 
	 * @param httpMethod
	 *            the http method
	 * @return the http method response attribute map for log
	 */
	private static Map<String, Object> getHttpMethodResponseAttributeMapForLog(HttpMethod httpMethod){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try{
			map.put("httpMethod.getStatusCode()", httpMethod.getStatusCode());
			map.put("httpMethod.getStatusText()", httpMethod.getStatusText());
			map.put("httpMethod.getStatusLine()", httpMethod.getStatusLine().toString());
			map.put("httpMethod.getResponseHeaders()", httpMethod.getResponseHeaders());
			map.put("httpMethod.getResponseFooters()", httpMethod.getResponseFooters());
		}catch (Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
