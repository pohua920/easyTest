package com.ctbcins.api.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.error.UserException;
import com.tlg.commons.util.api.soap.SoapXmlFormatter;
import com.tlg.commons.util.api.soap.aml.AmlServiceImplService;
import com.tlg.commons.util.api.soap.aml.Exception_Exception;
import com.tlg.commons.util.api.soap.aml.entity.AmlInsured;
import com.tlg.commons.util.api.soap.aml.entity.AmlResponseVo;
import com.tlg.commons.util.api.soap.aml.entity.Insrueds;

/**
 * mantis：CLM0062 ，處理人員：BK007 蘇哲，需求單編號：CLM0062.AML換新的理賠新核心
 * AMLQueryServlet
 * 洗錢後段統一接口
 * @author bk007
 *
 */
public class AMLQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Log logger = LogFactory.getLog(this.getClass());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAmlOpen()){
			response.getOutputStream().write("M".getBytes());
			return;
		}
		response.getOutputStream().write(doAML(request).getBytes());
	}

	private String doAML(HttpServletRequest request){
		String result = "M";
		try {
			SoapXmlFormatter formatter = new SoapXmlFormatter();
			AmlServiceImplService amlService = new AmlServiceImplService(new URL(getMetaAmlUrl()));
			AmlResponseVo resp = formatter.parse(AmlResponseVo.class,amlService.getAmlServiceImplPort().amlQuery(formatter.format(Insrueds.class,getAmlRequest(request))));
			System.out.println(resp);
			if("01".equals(resp.getListDetection())){ // 01 - 未命中
				result = "N";
			}else if("02".equals(resp.getListDetection())){ // 02 - 命中未判定
				result = "Y";
			}else if("03".equals(resp.getListDetection())){ // 03 - 命中已判定
				result = "Y";
			}else{ // 其他
				result = "M";
			}
		} catch (JAXBException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Exception_Exception e) {
			logger.error(e);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}

	private Insrueds getAmlRequest(HttpServletRequest request)
			throws UnsupportedEncodingException {
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String registNo = request.getParameter("registNo");
		String riskCode = request.getParameter("riskCode");
		String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
		String type = request.getParameter("type");//01-身份證號;02-統一編號;03-護照號碼;04-居留證號碼;99-其他  ConstantsCollection.prpdpaymentaccountCertificateTypeList
		String code = request.getParameter("code");

		Insrueds amlRequest = new Insrueds();
		amlRequest.setAppCode("NEWIMS_CLAIM");
		amlRequest.setBusinessNo(registNo);
		amlRequest.setAmlUniKey(registNo+"_"+new Date().getTime());
		amlRequest.setClassCode(getClassCode(registNo,riskCode));
		amlRequest.setRiskCode(riskCode);
		amlRequest.setComCode(user.getComCode());
		amlRequest.setType("C");
		amlRequest.setAmlType("2");
		amlRequest.setResend("0");
		
		AmlInsured amlItem = new AmlInsured();
		amlItem.setSerialNo("1");
		amlItem.setName(name);
		amlItem.setInsuredType("02".equals(type)?"2":"1"); //1 - 自然人 2 - 法人
		amlItem.setInsuredFlag("8");
		amlItem.setId(code);
		amlRequest.getAmlInsuredList().add(amlItem);
		return amlRequest;
	}

	private boolean isAmlOpen() {
		LinkedHashMap<Integer, String> prams = new LinkedHashMap<>();
		prams.put(1, "MANUALMAINTENANCESWITCH");
		return "0".equals(selectDB("select valueType from uticonfig where configcode = ?",prams));
	}

	private String getClassCode(String registNo, String riskCode) {
		LinkedHashMap<Integer, String> prams = new LinkedHashMap<>();
		prams.put(1, registNo);
		prams.put(2, riskCode);
		return selectDB("SELECT CLASSCODE FROM PRPLCLAIM WHERE REGISTNO = ? AND RISKCODE = ?",prams);
	}

	private String getMetaAmlUrl() {
		LinkedHashMap<Integer, String> prams = new LinkedHashMap<>();
		prams.put(1, "METAAML_WS_URL");
		return selectDB("SELECT RULE from UtiPlatConfigRule where SYSTEMCODE in ('claim','prpall') and PARAMCODE=? and SERIALNO=1 order by systemcode desc",prams);
	}

	private String selectDB(String sql,LinkedHashMap<Integer,String> prams){
		DBManager dbManager = new DBManager();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String result = "";
		try {
			dbManager.open(SysConfig.getProperty("DDCCDATASOURCE"));
			connection = dbManager.getConnection();
			statement = connection.prepareStatement(sql); 
			for(Entry<Integer, String> entry:prams.entrySet()){
				statement.setString(entry.getKey(), entry.getValue());
			}
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getString(1);
			}
		} catch (UserException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			try {
				if(resultSet != null){
					resultSet.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
			try {
				if(statement != null){
					statement.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
			try {
				if(connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
			try {
				dbManager.close();
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return result;
	}

}
