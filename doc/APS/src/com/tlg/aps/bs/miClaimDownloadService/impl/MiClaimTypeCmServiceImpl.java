package com.tlg.aps.bs.miClaimDownloadService.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.bs.miClaimDownloadService.MiClaimTypeCmService;
import com.tlg.aps.vo.ChubbClaimResponseVo;
import com.tlg.aps.vo.ChubbClaimReturnVo;
import com.tlg.aps.vo.ChubbClaimXmlHeaderVo;
import com.tlg.aps.vo.ChubbClaimXmlItem;
import com.tlg.aps.vo.ChubbClaimXmlVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetclaimTypecm;
import com.tlg.msSqlMob.service.FetclaimTypecmService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Constants;
import com.tlg.util.FakeTrustManager;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;
import com.tlg.util.encoders.AesGcm256;


@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MiClaimTypeCmServiceImpl implements MiClaimTypeCmService {
	
	private static final Logger logger = Logger.getLogger(MiClaimTypeCmServiceImpl.class);
	

	private ConfigUtil configUtil;
	private FetclaimTypecmService fetclaimTypecmService;
	
	
	@Override
	public Result send(UserInfo userInfo, FetclaimTypecm fetclaimTypecm) throws SystemException,
			Exception {

		if(fetclaimTypecm == null ){
			throw new SystemException("無法取得FetclaimTypecm資料(1)");
		}
		//判斷是否已有資料
		Result result = new Result();
		BigDecimal oid = fetclaimTypecm.getOid();
		if(oid == null){
			result = this.createData(userInfo, fetclaimTypecm);
			if(result.getResObject() == null){
				return result;
			}
			fetclaimTypecm = (FetclaimTypecm)result.getResObject();
		}else{
			result = this.fetclaimTypecmService.updateFetclaimTypecm(fetclaimTypecm);
			if(result.getResObject() == null){
				return result;
			}
		}
		result = this.callChubbApi(fetclaimTypecm);
		if(result.getResObject() == null){
			return result;
		}
		FetclaimTypecm fetclaimTypecmTmp = (FetclaimTypecm)result.getResObject();

		Message message = new Message();
		message.setMessageString("資料已發送～retcode：" + fetclaimTypecmTmp.getRetcode() + ", message：" + fetclaimTypecmTmp.getMessage());
		result.setMessage(message);
		
		return result;
	}
	
	@Override
	public Result upload(UserInfo userInfo, FetclaimTypecm fetclaimTypecmTmp) throws SystemException,
			Exception {

		if(fetclaimTypecmTmp == null ){
			throw new SystemException("無法取得FetclaimTypecm資料(upload-1)");
		}
		//取出既有資料
		BigDecimal oid = fetclaimTypecmTmp.getOid();
		Result result = this.fetclaimTypecmService.findFetclaimTypecmByOid(oid);
		if(result.getResObject() == null){
			throw new SystemException("無法取得FetclaimTypecm資料(upload-2)");
		}
		FetclaimTypecm fetclaimTypecm = (FetclaimTypecm)result.getResObject();
		fetclaimTypecm.setListprice1(fetclaimTypecmTmp.getListprice1());
		fetclaimTypecm.setAsuClaimAmount(fetclaimTypecmTmp.getAsuClaimAmount());
		fetclaimTypecm.setFinalAuth(fetclaimTypecmTmp.getFinalAuth());
		fetclaimTypecm.setErDate(fetclaimTypecmTmp.getErDate());
		fetclaimTypecm.setFinishDate(fetclaimTypecmTmp.getFinishDate());
		result = this.fetclaimTypecmService.updateFetclaimTypecm(fetclaimTypecm);
		if(result.getResObject() == null){
			return result;
		}
		
		ChubbClaimXmlVo rootVo = new ChubbClaimXmlVo();
		ChubbClaimXmlHeaderVo chubbClaimXmlHeaderVo = new ChubbClaimXmlHeaderVo();
		rootVo.setChubbClaimXmlHeaderVo(chubbClaimXmlHeaderVo);
		ChubbClaimXmlItem item = new ChubbClaimXmlItem();
		BeanUtils.copyProperties(item, fetclaimTypecm);
		List<ChubbClaimXmlItem> itemList = new ArrayList<ChubbClaimXmlItem>();
		itemList.add(item);
		rootVo.setItem(itemList);
		
		Marshaller marshaller = JAXBContext.newInstance(ChubbClaimXmlVo.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING,"BIG5");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //是否生成xml字串
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); //是否省略xml head訊息
		ByteArrayOutputStream objByteArrayOutputStream = new ByteArrayOutputStream();
		marshaller.marshal(rootVo, objByteArrayOutputStream);
		byte[] vbResultXML = objByteArrayOutputStream.toByteArray();
		String xmlTxt = "<?xml version=\"1.0\" encoding=\"big5\"?>\n" + new String(vbResultXML, "BIG5");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String fileName = "claim_CM_" + simpleDateFormat.format(new Date()) + "_" + fetclaimTypecm.getMtnNo();
		String tmpDir = System.getProperty("java.io.tmpdir");
//		String tmpDir = "D:/";
		File f = new File(tmpDir + "/" + fileName + ".xml");
        FileUtils.touch(f);
		try (FileOutputStream outputStream = new FileOutputStream(f)) {
		    outputStream.write(xmlTxt.getBytes(Charset.forName("BIG5")));
		}
		System.out.println("xmlTxt = " + xmlTxt);
		String remoteDir = configUtil.getString("mobClaimCMFilePath") + "/" + simpleDateFormat.format(new Date());
		boolean boo = uploadZipFileToSftp(remoteDir, f.getAbsolutePath());
		if(!boo){
			fetclaimTypecmTmp.setXmlStatus("1");
			this.fetclaimTypecmService.updateFetclaimTypecm(fetclaimTypecmTmp);
			Message message = new Message();
			message.setMessageString("檔案上傳失敗～");
			result.setMessage(message);
			return result;
		}
		//刪除檔案
		FileUtils.deleteQuietly(f);
		fetclaimTypecmTmp.setXmlStatus("2");
		result = this.fetclaimTypecmService.updateFetclaimTypecm(fetclaimTypecmTmp);
		Message message = new Message();
		message.setMessageString("資料已上傳～");
		result.setMessage(message);
		
		return result;
	}
	
	@Override
	public synchronized Result createData(UserInfo userInfo, FetclaimTypecm fetclaimTypecm) throws SystemException,Exception {
		
		if(fetclaimTypecm == null ){
			throw new SystemException("無法取得FetclaimTypecm資料(1)");
		}

		if(userInfo == null ){
			throw new SystemException("無法取得使用者資訊");
		}
		//資料檢查
		List<String> msdList = checkData(fetclaimTypecm);
		if(msdList != null && msdList.size() > 0){
			String srt = "";
			for(String err:msdList){
				srt = srt + err + "\\r\\n";
			}
			throw new SystemException("資料檢核問題如下：\\r\\n" + srt);
		}
		//取號
		SimpleDateFormat simpleDateFormatYYYY = new SimpleDateFormat("yyyy");
		SimpleDateFormat simpleDateFormatMMdd = new SimpleDateFormat("MM");
		String mtnNo = "";
		if("M".equals(fetclaimTypecm.getClaimType())){
			mtnNo = "MM" + simpleDateFormatYYYY.format(new Date()).substring(2) + simpleDateFormatMMdd.format(new Date());
		}
		if("C".equals(fetclaimTypecm.getClaimType())){
			mtnNo = "CC" + simpleDateFormatYYYY.format(new Date()).substring(2) + simpleDateFormatMMdd.format(new Date());
		}
		// mantis：MOB0021，處理人員：DP0714，新商品上線APS作業調整 -- start
		if("E".equals(fetclaimTypecm.getClaimType())){
			mtnNo = "EE" + simpleDateFormatYYYY.format(new Date()).substring(2) + simpleDateFormatMMdd.format(new Date());
		}
		// mantis：MOB0021，處理人員：DP0714，新商品上線APS作業調整 -- end
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("claimType", fetclaimTypecm.getClaimType());
		params.put("notifDateByMonth", fetclaimTypecm.getNotifDate().substring(0, 7));
		int count = this.fetclaimTypecmService.countFetclaimTypecm(params);
		mtnNo = mtnNo + StringUtil.adjustNumber(String.valueOf(++count), "0", 6, true);
		fetclaimTypecm.setMtnNo(mtnNo);
		fetclaimTypecm.setApiStatus("0");
		fetclaimTypecm.setCreater(userInfo.getUserId());
		fetclaimTypecm.setCreatdate(new Date());
		Result result = this.fetclaimTypecmService.insertFetclaimTypecm(fetclaimTypecm);
		if(result.getResObject() != null){
			result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		}else{
			result.setMessage(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		return result;
	}
	
	
	@Override
	public Result callChubbApi(FetclaimTypecm fetclaimTypecm) throws SystemException,Exception {
		
		String chubbClaimApi = configUtil.getString("chubbClaimApi");
		String encode1 = configUtil.getString("encode1");
		String encode2 = configUtil.getString("encode2");
		
		if(StringUtil.isSpace(chubbClaimApi)){
			throw new SystemException("無法取得安達API位置");
		}
		if(StringUtil.isSpace(encode1) || StringUtil.isSpace(encode2)){
			throw new SystemException("無法取得安達安達加密資訊");
		}
		if(fetclaimTypecm == null ){
			throw new SystemException("無法取得FetclaimTypecm資料(1)");
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(fetclaimTypecm);
		String callChubbApiEncrypt = AesGcm256.encrypt(json, encode1, encode2);
		String callChubbApiContent = "{\"claimAuth\":\""+callChubbApiEncrypt+"\"}";
		String returnJson = callChubbApi(chubbClaimApi, callChubbApiContent);
		System.out.println("returnJson = " + returnJson);
		ChubbClaimResponseVo callChubbApiResultVo = objectMapper.readValue(returnJson, ChubbClaimResponseVo.class);//{"policyQueryResponse":"...."} 轉成物件
		String policyQueryResponse = AesGcm256.decrypt(callChubbApiResultVo.getClaimAuthResponse(), encode1, encode2);
		ChubbClaimReturnVo chubbClaimReturnVo = objectMapper.readValue(policyQueryResponse,ChubbClaimReturnVo.class);//json to Object
		
		String retCode = "";
		String retMessage = "";
		
		if("S".equals(chubbClaimReturnVo.getOperationResult())) {//回傳成功
			//全虹回覆物件
			retCode = "E000";
			retMessage = "成功";
			//發送成功
			fetclaimTypecm.setApiStatus("2");
		} else { //回傳失敗
			retCode = "E002";
			if("00".equals(chubbClaimReturnVo.getDetailDescription())) {
				retMessage = "系統無權限";
			} else if("10".equals(chubbClaimReturnVo.getDetailDescription())) {
				retMessage = "保險合約編號錯誤";
			}else{
				retMessage = chubbClaimReturnVo.getDetailDescription();
			}
			//發送失敗
			fetclaimTypecm.setApiStatus("1");
		}
		Result result = new Result();
		if(chubbClaimReturnVo != null){
			result.setResObject(chubbClaimReturnVo);
		}else{
			Message message = new Message();
			message.setMessageString("發送API失敗");
			result.setMessage(message);
			return result;
		}
		fetclaimTypecm.setIclaimno(chubbClaimReturnVo.getIclaimNo());
		fetclaimTypecm.setRetcode(retCode);
		fetclaimTypecm.setMessage(retMessage);
		
		result = this.fetclaimTypecmService.updateFetclaimTypecm(fetclaimTypecm);
		
		return result;
		
		
	}
	
	
	private boolean uploadZipFileToSftp(String remoteDir, String filePath) throws Exception{
		boolean result = false;
		String sftpHost = configUtil.getString("mobSFTP");
		String sftpUser = configUtil.getString("mobSftpUserPut");
		String sftpPwd = configUtil.getString("mobSftpPwdPut");
		String sftpPort = configUtil.getString("mobSftpPort");
		//String remoteDir = configUtil.getString("panhsinBackFileRemotePath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp3(remoteDir, filePath);
		if("success".equals(strResult)) {
			result = true;
		}
		
		return result;
	}
	
	private String callChubbApi(String httpURL, String content) {
		String returnValue = "";
		try {
			
			CloseableHttpClient httpClientToken = createAcceptSelfSignedCertificateClient();
			
			StringBuilder stringBuilderToken = new StringBuilder();
			HttpPost httpPostToken = new HttpPost(httpURL);
			StringEntity stringEntityToken = new StringEntity(content, "UTF-8");
			stringEntityToken.setContentEncoding("UTF-8");
			httpPostToken.setEntity(stringEntityToken);
			httpPostToken.setHeader("Accept", "application/json");
			httpPostToken.setHeader("Content-type", "application/json");
			HttpResponse responseToken = httpClientToken.execute(httpPostToken);
			HttpEntity httpEntityToken = responseToken.getEntity();
			// get the response contenth
			InputStream inputStreamToken = httpEntityToken.getContent();
			BufferedReader bufferedReaderToken = new BufferedReader(new InputStreamReader(inputStreamToken, "UTF-8"));
			String strReadLineToken = bufferedReaderToken.readLine();
			// iterate to get the data and append in StringBuilder
			while (strReadLineToken != null) {
				stringBuilderToken.append(strReadLineToken);
				strReadLineToken = bufferedReaderToken.readLine();
				if (strReadLineToken != null) {
					stringBuilderToken.append("\n");
				}
			}
			logger.info("callChubbApi return:" + stringBuilderToken.toString());
			returnValue = stringBuilderToken.toString();
		}catch(Exception e) {
			logger.error("callChubbApi失敗", e);
		}
		return returnValue;
	}
	
	
	private ArrayList<String> checkData(FetclaimTypecm fetclaimTypecm) throws Exception{
		ArrayList<String> msgList = new ArrayList<String>();
		if(StringUtil.isSpace(fetclaimTypecm.getContractId())){
			msgList.add("保險合約編號不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getMatnrNo())){
			msgList.add("報修料號代碼不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getImei())){
			msgList.add("IMEI不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getClaimType())){
			msgList.add("理賠類型不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getNotifDate())){
			msgList.add("送修日期不可為空值");
		}else{
			try{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date date = simpleDateFormat.parse(fetclaimTypecm.getNotifDate());
			}catch (Exception e) {
				msgList.add("送修日期格式錯誤，格式必須為：yyyy-MM-ddTHH:mm:ss");
			}
		}
		if(fetclaimTypecm.getListprice1() == null){
			msgList.add("申請授權維修金額不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getFinalAuth())){
			msgList.add("授權階段代號不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getSpecialApproval())){
			msgList.add("案件授權類別代號不可為空值");
		}
		if(fetclaimTypecm.getEventDate() == null){
			msgList.add("事故日期不可為空值");
		}else{
			try{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = simpleDateFormat.parse(fetclaimTypecm.getEventDate());
			}catch (Exception e) {
				msgList.add("事故日期格式錯誤，格式必須為：yyyy-MM-dd");
			}
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPolicyNo())){
			msgList.add("保險單代碼不可為空值");
		}
		if(fetclaimTypecm.getAsuClaimAmount() == null){
			msgList.add("申請授權維修金額不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription1())){
			msgList.add("受款人姓名不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription2())){
			msgList.add("受款人身分證號不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription3())){
			msgList.add("受款人銀行代碼不可為空值");
		}
		if("B".equals(fetclaimTypecm.getPartDescription10()) && StringUtil.isSpace(fetclaimTypecm.getPartDescription4())){
			msgList.add("受款人分行代碼不可為空值");
		}
		if("B".equals(fetclaimTypecm.getPartDescription10()) && StringUtil.isSpace(fetclaimTypecm.getPartDescription5())){
			msgList.add("受款人銀行帳號不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription6())){
			msgList.add("受款人郵遞區號不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription7())){
			msgList.add("受款人電話不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription8())){
			msgList.add("受款人地址不可為空值");
		}
		if("Q".equals(fetclaimTypecm.getPartDescription10()) && StringUtil.isSpace(fetclaimTypecm.getPartDescription9())){
			msgList.add("是否取消禁背不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getPartDescription10())){
			msgList.add("支付方式不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getEventCause())){
			msgList.add("事故損毀說明不可為空值");
		}
		if(StringUtil.isSpace(fetclaimTypecm.getCauseDescription1())){
			msgList.add("故障原因說明不可為空值");
		}
		
		return msgList;
	}

	public static void main(String args[]) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = "2023-11-10 22:35:10";
		
		Date date = simpleDateFormat.parse(dateStr);
		System.out.println(date.toString());
		
		SimpleDateFormat simpleDateFormatYYYY = new SimpleDateFormat("yyyy");
		System.out.println(simpleDateFormatYYYY.format(new Date()).substring(2));
		SimpleDateFormat simpleDateFormatMMdd = new SimpleDateFormat("MMdd");
		System.out.println(simpleDateFormatMMdd.format(new Date()));
	}

    private static CloseableHttpClient createAcceptSelfSignedCertificateClient()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	
//    	SSLContextBuilder builder = new SSLContextBuilder().useProtocol("TLSv1.2");

        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        SSLContext sslContext = new SSLContextBuilder().useProtocol("TLSv1.2").build();
        
////        loadTrustMaterial(new TrustSelfSignedStrategy())
//        
//        sslContext.
        
        sslContext.init(new KeyManager[0], new TrustManager[] {new FakeTrustManager()}, new SecureRandom());
        SSLContext.setDefault(sslContext);
        
        // we can optionally disable hostname verification. 
        // if you don't want to further weaken the security, you don't have to include this.
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all hosts verifier.
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

        // finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
        return HttpClients
                .custom()
                .setSSLSocketFactory(connectionFactory)
                .build();
    }
	
	@Override
	public Result generateXmlToFtp(FetclaimTypecm fetclaimTypecm)
			throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}


	public FetclaimTypecmService getFetclaimTypecmService() {
		return fetclaimTypecmService;
	}


	public void setFetclaimTypecmService(FetclaimTypecmService fetclaimTypecmService) {
		this.fetclaimTypecmService = fetclaimTypecmService;
	}

}
