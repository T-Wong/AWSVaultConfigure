package com.twong.awsconfig;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Vault {
	private String vaultAddress;		// the vault HTTP address
	private char[] vaultToken;			// the vault authentication token
	
	/**
	 * The Vault object constructor.
	 * 
	 * @param vaultAddress	the vault HTTP root address
	 * @param vaultToken	the vault authentication token
	 */
	public Vault(String vaultAddress, char[] vaultToken) {
		this.vaultAddress = vaultAddress;
		this.vaultToken = vaultToken;
	}
	
	/**
	 * Gets an AWS credential from the vault AWS backend.
	 * 
	 * @param secretPath	path to the AWS secret backend. Appened onto the vaultAddress.
	 * @return				the AWS access key and secret key, stored in [0] and [1] respectively.
	 */
	public String[] getAWSSecret(String secretPath) {
		String[] data = new String[2];
		
		String secretURL = String.join("/", Arrays.asList(vaultAddress, secretPath));
		
		try {
			// Do an HTTP GET with headers on the vault url
			HttpClient client = HttpClients.custom().build();
			HttpUriRequest request = RequestBuilder.get()
				.setUri(secretURL)
				.setHeader("X-Vault-Token", String.valueOf(vaultToken))
				.build();
			HttpResponse httpResponse = client.execute(request);
			
			// Parse JSON response
			String response = EntityUtils.toString(httpResponse.getEntity());
			
	        JsonParser jsonParser = new JsonParser();
	        JsonObject jsonData = jsonParser.parse(response)
	        	.getAsJsonObject().getAsJsonObject("data");
	        
			String accessKey = jsonData.get("access_key").getAsString();
			String secretKey = jsonData.get("secret_key").getAsString();
			
			data[0] = accessKey;
			data[1] = secretKey;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
