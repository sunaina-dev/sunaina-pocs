/**
 * 
 */
package com.cisco.alfresco.repo.rest.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * @author mkatnam
 *
 */
public class AlfrescoUtils {
	
	public AlfrescoUtils() {
		super();
	}
	
	private static Logger log = Logger.getLogger(AlfrescoUtils.class);
	static String alf_logintoken_url="http://alfwsx-app-dev-01:38080/alfresco/s/api/login?u=admin&pw=admin";
	
	public static void main(String[] args) throws IOException, ParseException{
		//String ticketURLResponse = getAlfTicket();
		//getRecordInfo(ticketURLResponse,"f9689061-9957-4245-a0ae-7ab58d933ce7");
		//getVersionHistory(ticketURLResponse,"f9689061-9957-4245-a0ae-7ab58d933ce7");
		test();
	}
	 
	 /**
		 * Method to get alfresco ticket
		 * 
		 * @param url
		 * @return
		 * @throws IOException
		 */
		public static String getAlfTicket() throws IOException {
			String response = null;
			String ticketURLResponse = null;
			
			HttpClient client = new HttpClient();
			GetMethod httpget = new GetMethod(alf_logintoken_url);
			httpget.setDoAuthentication(true);
			try {
				// execute the GET
				int status = client.executeMethod(httpget);
				if (status != HttpStatus.SC_OK) {
					log.error("Method failed: " + httpget.getStatusLine());
				}

				response = new String(httpget.getResponseBodyAsString());
				int startindex = response.indexOf("<ticket>") + 8;
				int endindex = response.indexOf("</ticket>");
				ticketURLResponse = response.substring(startindex, endindex);
				System.out.println("ticket = " + ticketURLResponse);

			} finally {

				// release any connection resources used by the method
				httpget.releaseConnection();
			}
			return ticketURLResponse;
		}
		
		/**
		 * Method to run get script
		 *  
		 * @param url
		 * @return
		 * @throws IOException
		 * @throws ParseException 
		 * @throws JSONException 
		 */
		public static String httpGetConnection(String url) throws IOException
		{
			log.debug("httpConnection method start!! ");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpFileGet = new HttpGet(url);
			CloseableHttpResponse response = null;
			String json = null;
			try
			{
				response = httpclient.execute(httpFileGet);
				log.info(response.getStatusLine().getStatusCode());
				json = EntityUtils.toString(response.getEntity());
				//JSONObject myObject = new JSONObject(json);
				System.out.println(" JSON OBj :: "+json);
			}
			catch (ClientProtocolException e)
			{
				log.error("ClientProtocolException caught !! " + e);
			}
			catch (IOException e)
			{
				log.info("IOException caught !! " , e);
			}finally{
				httpclient.close();
			}
			log.debug("httpConnection method end!! ");
			return json;
		}
		
		/**
		 * Method to run get script
		 *  
		 * @param url
		 * @return
		 * @throws IOException
		 * @throws ParseException 
		 * @throws JSONException 
		 */
		public static JSONObject httpGetConnectionJSON(String url) throws IOException, ParseException
		{
			log.debug("httpConnection method start!! ");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpFileGet = new HttpGet(url);
			CloseableHttpResponse response = null;
			JSONObject json = null;
			try
			{
				response = httpclient.execute(httpFileGet);
				log.info(response.getStatusLine().getStatusCode());
				String jsonStr = EntityUtils.toString(response.getEntity());
				//JSONObject myObject = new JSONObject(json);
				System.out.println(" JSON OBj :: "+jsonStr);
				JSONParser parser = new JSONParser();
				json = (JSONObject) parser.parse(jsonStr);
				System.out.println("response.getEntity() ==== >>"+json);
			}
			catch (ClientProtocolException e)
			{
				log.error("ClientProtocolException caught !! " + e);
			}
			catch (IOException e)
			{
				log.info("IOException caught !! " , e);
			}finally{
				httpclient.close();
			}
			log.debug("httpConnection method end!! ");
			return json;
		}
		
		public static JSONObject httpGetConnectionVersion(String url) throws IOException, ParseException
		{
			log.debug("httpConnection method start!! ");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpFileGet = new HttpGet(url);
			CloseableHttpResponse response = null;
			JSONObject json = new JSONObject();
			try
			{
				response = httpclient.execute(httpFileGet);
				log.info(response.getStatusLine().getStatusCode());
				String jsonStr = EntityUtils.toString(response.getEntity());
				System.out.println(" JSON OBj :: "+jsonStr);
				JSONParser parser = new JSONParser();
				JSONArray array = (JSONArray)parser.parse(jsonStr);
				json = (JSONObject)parser.parse(array.get(0).toString());
				System.out.println("response.getEntity() ==== >>"+json);
			}
			catch (ClientProtocolException e)
			{
				log.error("ClientProtocolException caught !! " + e);
			}
			catch (IOException e)
			{
				log.info("IOException caught !! " , e);
			}finally{
				httpclient.close();
			}
			log.debug("httpConnection method end!! ");
			return json;
		}
		
		public static String getRecordInfo(String ticketURLResponse, String nodeRef) throws IOException{
			String recordUrl = "http://alfwsx-app-dev-01:38080/alfresco/service/rm/recordinfo?nodeRef=workspace://SpacesStore/"+nodeRef+"&alf_ticket="+ticketURLResponse;
			return httpGetConnection(recordUrl);
		}
		public static JSONObject getRecordInfoJSON(String ticketURLResponse, String nodeRef) throws IOException, ParseException{
			String recordUrl = "http://alfwsx-app-dev-01:38080/alfresco/service/rm/recordinfo?nodeRef=workspace://SpacesStore/"+nodeRef+"&alf_ticket="+ticketURLResponse;
			return httpGetConnectionJSON(recordUrl);
		}

		public static JSONObject getVersionHistory(String ticketURLResponse, String nodeRef) throws IOException, ParseException {
			String versionUrl = "http://alfwsx-app-dev-01:38080/alfresco/service/api/rm/rm-version?nodeRef=workspace://SpacesStore/"+nodeRef+"&alf_ticket="+ticketURLResponse;
			return httpGetConnectionVersion(versionUrl);
		}
		
		public static void test(){
			StringBuilder alive = new StringBuilder();
			String strURL = "http://alfcms-app-dev-02:8080/";
			String strAuditURL = "http://alfcms-app-dev-02:18080/";
			
			alive.append(strURL).append("<br /> ").append(strAuditURL);
			System.out.println(alive.toString());
		}
}
