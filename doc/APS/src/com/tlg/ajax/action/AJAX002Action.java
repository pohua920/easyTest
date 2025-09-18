package com.tlg.ajax.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.opensymphony.xwork2.Action;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;


@SuppressWarnings("serial")
public class AJAX002Action extends BaseAction implements Serializable {

	private String result;
	
	public String stage1() {
		try{
		      
			String genUrl = "http://192.168.2.34/sane1/secureAPIHelperGetCerts";
			String resultJson = "";
		    try{
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(genUrl).openStream()));
		    	resultJson = reader.readLine();
		    	System.out.println("resultJson : " + resultJson );
		    	try {
		    		Map<String,Object> map = new LinkedHashMap<>();
		    	     JSONObject jsonObject = new JSONObject(resultJson);
		    	     if(jsonObject.has("b64TACert")){
		    	    	 System.out.println("b64TACert = " + jsonObject.getString("b64TACert"));
		    	    	 map.put("isExist", "true");
		    	    	 map.put("b64TACert", jsonObject.getString("b64TACert"));
		    	     }
		    	     if(jsonObject.has("b64DVCert")){
		    	    	 System.out.println("b64DVCert = " + jsonObject.getString("b64DVCert"));
		    	    	 map.put("isExist", "true");
		    	    	 map.put("b64DVCert", jsonObject.getString("b64DVCert"));
		    	     }

		    	     result = JsonUtil.getJSONString(map);
		    	}catch (JSONException err){
//		    	     Log.d("Error", err.toString());
		    	}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }

		}catch(Exception e){
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String stage2() {
		try{
			String challenge = getRequest().getParameter("challenge");
			URL url = new URL ("http://192.168.2.34/sane1/secureAPIHelperDoSign");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			String jsonInputString = "{\"dtbsB64\": \"" + challenge + "\"}";
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}

			try(BufferedReader br = new BufferedReader(
					  new InputStreamReader(con.getInputStream(), "utf-8"))) {
					    StringBuilder response = new StringBuilder();
					    String responseLine = null;
					    while ((responseLine = br.readLine()) != null) {
					        response.append(responseLine.trim());
					    }
					    System.out.println(response.toString());
					    JSONObject jsonObject = new JSONObject(response.toString());
					    Map<String,Object> map = new LinkedHashMap<>();
					    if(jsonObject.has("sigB64")){
			    	    	 System.out.println("sigB64 = " + jsonObject.getString("sigB64"));
			    	    	 map.put("isExist", "true");
			    	    	 map.put("sigB64", jsonObject.getString("sigB64"));
			    	     }

			    	     result = JsonUtil.getJSONString(map);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}