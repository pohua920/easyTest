package com.tlg.aps.bs.stakeHolderQuery.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.tlg.aps.bs.stakeHolderQuery.StakeHolderService;
import com.tlg.aps.vo.StakeHolderVo;
import com.tlg.exception.SystemException;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;

public class StakeHolderServiceImpl implements StakeHolderService {
	
	private ConfigUtil configUtil;

	public StakeHolderVo queryStakeHolder(StakeHolderVo stakeHolderVo) throws SystemException,Exception{
		
		HttpClient httpclient = new DefaultHttpClient();
		String postRequest = JsonUtil.getJSONString(stakeHolderVo);
		try {
		    String url = configUtil.getString("stakeHolderUrl");
		    HttpPost request = new HttpPost(url);
		    request.setHeader("Content-Type", "application/json");       
		    // Request body
		    StringEntity reqEntity = new StringEntity(postRequest,"UTF-8");
		    request.setEntity(reqEntity);
		    System.out.println(request.toString());
		    HttpResponse response = httpclient.execute(request);
		    System.out.println(response.getStatusLine().getStatusCode());
		    HttpEntity entity = response.getEntity();
		    String result = EntityUtils.toString(entity);
		    System.out.println(result);
		    StakeHolderVo stakeHolderVoResult = (StakeHolderVo) JsonUtil.getDTO(result, StakeHolderVo.class);
		    return stakeHolderVoResult;
	    } catch (Exception e) {
		    System.out.println(e.getMessage());
	    }
		return null;
	}
	
	public static void main(String args[]) throws Exception{

		
		StakeHolderVo stakeHolderVo = new StakeHolderVo();
		
		stakeHolderVo.setListNo("TEST00000001");
		stakeHolderVo.setStakeId("27938888");
		stakeHolderVo.setUserCode("bh055");
		
		System.out.println(JsonUtil.getJSONString(stakeHolderVo));
		
		StakeHolderServiceImpl shi = new StakeHolderServiceImpl();
		shi.queryStakeHolder(stakeHolderVo);
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
}
