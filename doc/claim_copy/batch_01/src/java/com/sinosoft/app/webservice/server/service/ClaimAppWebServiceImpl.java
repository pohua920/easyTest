package com.sinosoft.app.webservice.server.service;

import ins.framework.common.QueryRule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import sun.misc.BASE64Decoder;

import com.sinosoft.app.webservice.server.schema.model.claimQuery.ClaimData;
import com.sinosoft.app.webservice.server.schema.model.claimQuery.ReqClaimQuery;
import com.sinosoft.app.webservice.server.schema.model.claimQuery.RespClaimQueryResult;
import com.sinosoft.app.webservice.server.schema.model.claimQuery.UndwrtData;
import com.sinosoft.app.webservice.server.schema.model.common.Company;
import com.sinosoft.app.webservice.server.schema.model.common.FileUploadUtils;
import com.sinosoft.app.webservice.server.schema.model.login.ReqLogin;
import com.sinosoft.app.webservice.server.schema.model.login.RespLoginResult;
import com.sinosoft.app.webservice.server.schema.model.workflow.ClaimStatus;
import com.sinosoft.app.webservice.server.schema.model.workflow.ReqWorkFlow;
import com.sinosoft.app.webservice.server.schema.model.workflow.RespWorkFlowResult;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.ProcessCodeInputService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.spring.ClaimPrintServiceSpringImpl;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.UtiUserGradeDto;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
@WebService(endpointInterface="com.sinosoft.app.webservice.server.service.ClaimAppWebService")
public class ClaimAppWebServiceImpl implements ClaimAppWebService {
    private PrpDuserService prpDuserService;
    /** 基础代码处理接口 */
    private ProcessCodeInputService processCodeInputService;
    /**單號服務*/
    private BillService billService;
    /** 备案service */
    private PrpLregistService prpLregistService;
    /** 工作流日志表接口service */
    private SwfLogService swfLogService;
    /** 保单数据传输对象服务 */
    private PolicyService policyService;
    /** 赔案保单关联信息服务 */
    private PrplregistrpolicyService prpLregistrpolicyService;
    /**立案服务*/
    private PrpLclaimService prpLclaimService;
    /**赔款计算书服务*/
    private PrpLcompensateService prpLcompensateService;
    private CodeService codeService;
    private ClaimPrintServiceSpringImpl ClaimPrintService;
    private PrpCitemKindService prpCitemKindService;
    private PrpLpersonTraceService prpLpersonTraceService;

    @Override
    public RespLoginResult LOGIN(ReqLogin reqLogin) {
        // TODO Auto-generated method stub
        //ReqLogin reqLogin=(ReqLogin)JaxbUtil.xml2Bean(xmlStr, ReqLogin.class);
        String userCode=reqLogin.getUserCode();
        String password=reqLogin.getPassword();
        DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
        /*wsContext = new org.apache.cxf.jaxws.context.WebServiceContextImpl();  
		//		MessageContext mc;       
		//		HttpSession session = null;   
		mc = wsContext.getMessageContext();     
		session = ((javax.servlet.http.HttpServletRequest) mc     
				.get(MessageContext.SERVLET_REQUEST)).getSession();     

		((javax.servlet.ServletContext) mc.get(MessageContext.SERVLET_CONTEXT))     
		.setAttribute("session", session);// 把session放到ServletContext中。     
         */
        String token = UUID.randomUUID().toString(); // 生成Token     

        //session.setAttribute("token", token); // 将生成的token放入Session 
        RespLoginResult respResult=new RespLoginResult();
        String comCode="";
        String comName="";
        String userName="";
        List<Company> comList=new ArrayList<Company>();
        try {
            List<UtiUserGradeDto> UtiUserGradeDtos =(List) processCodeInputService.getComCodeOptionsText(userCode.trim());

            if(UtiUserGradeDtos.size()>0){
                Company company=new Company();
                comCode=UtiUserGradeDtos.get(0).getComCode();
                comName=UtiUserGradeDtos.get(0).getComName();
                company.setComCode(comCode);
                company.setComCName(comName);
                comList.add(company);
            }else{
                respResult.setReturnCode("1");
                respResult.setReturnMsg("用戶沒有可登入機構！");
            }
        } catch (Exception e) {
            respResult.setReturnCode("1");
            respResult.setReturnMsg("獲取用戶機構失敗！");
        }

        PrpDuser prpDuser = prpDuserService.findPrpDuser(userCode);
        PrpDuserDto prpDuserDto = new PrpDuserDto();
        prpDuserDto.setUserCode(userCode);
        prpDuserDto.setPassword(password);
        prpDuserDto.setLoginComCode(comCode);
        prpDuserDto.setLoginGradeCodes("");
        prpDuserDto.setLoginSystemCode("claim");
        // 以下信息 表中无此字段，为对象中增加的属性 如何处理
        prpDuserDto.setComName(comName);
        prpDuserDto.setSid(token);
        prpDuserDto.setRemoteAddr("");
        prpDuserDto.setLoginTime(now);
        if (prpDuser != null) {
            userName=prpDuser.getUserName();
            prpDuserDto.setUserName(prpDuser.getUserName());
            prpDuserDto.setUserEName(prpDuser.getUserEName());
            prpDuserDto.setComCode(prpDuser.getComCode());

        }

        try {
            UIPowerAction.login(prpDuserDto);
            respResult.setReturnCode("0");
            respResult.setReturnMsg("登入成功！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            respResult.setReturnCode("1");
            respResult.setReturnMsg("登入用戶名與密碼不匹配！");
        }
        respResult.setUserName(userName);
        respResult.setComList(comList);
        //String resultXml=JaxbUtil.bean2Xml(respResult);
        return respResult;
    }

//    @Override
//    public RespRegistResult RECORD(ReqRegist reqRegist) {
//        //ReqRegist reqRegist = (ReqRegist)JaxbUtil.xml2Bean(xmlStr, ReqRegist.class);
//        PrpLregist prpLregist = new PrpLregist();
//        PrpLpersonTrace prpLpersonTrace = new PrpLpersonTrace() ;
//        RespRegistResult respRegistResult = new RespRegistResult();
//        //備案信息
//        //生成備案號
//        Map<String, Object> infoMap = new HashMap<String, Object>();
//        infoMap.put("damageCode", reqRegist.getDamageCode());
//        infoMap.put("policyNo", reqRegist.getPolicyNo());
//        String registNo = null;
//        try {
//            registNo = this.getBillService().getNoByPolciyYear("prplregist", "", infoMap);
//        } catch (Exception e2) {
//            respRegistResult.setReturnCode("1");
//            respRegistResult.setReturnMsg("備案號生成失敗,請檢查保單號,出險原因,是否錄入正確!!!");
//            return respRegistResult; 
//        }
//        prpLregist.setRegistNo(registNo);
//        prpLregist.setReportDate(reqRegist.getReportDate());//設置備案時間
//        prpLregist.setReportorName(reqRegist.getReportorName());
//        prpLregist.setAddressCode(reqRegist.getDamageAddressCode());
//        prpLregist.setAddressName(reqRegist.getDamageAddressName());
//        prpLregist.setDamageAddress(reqRegist.getDamageAddress());
//        prpLregist.setDamageStartDate(reqRegist.getDamageStartDate ());
//        prpLregist.setDamageCode(reqRegist.getDamageCode());
//        prpLregist.setPolicyNo(reqRegist.getPolicyNo());
//        prpLregist.setInsuredName(reqRegist.getInsuredName());
//        prpLregist.setLicenseNo(reqRegist.getLicenseNo());
//        prpLregist.setPhoneNumber(reqRegist.getPhoneNumber());
//        prpLregist.setComCode(reqRegist.getComcode());
//        prpLregist.setLflag("L");//理賠類型
//        prpLregist.setAcceptFlag("Y");//是否受理標誌
//        prpLregist.setRepeatInsureFlag("N");// 是否向别的保险公司投保
//        prpLregist.setInsuredCode(reqRegist.getIdentifyNumber());
//        prpLregist.setReportType("03");
//        PrpLdriver prpLdriver ;
//        
//        if("D".equals(reqRegist.getCode()) ){// 车险
//            prpLregist.setClassCode("D");
//            prpLregist.setHandleUnit(reqRegist.getHandleUnit());
//            prpLregist.setRemark(reqRegist.getRemark());
//            //與被保險人關係
//            prpLregist.setRelationType(reqRegist.getRelationType());
//            //車險(駕駛人)
//            int n  = 1;
//            prpLdriver = new PrpLdriver();
//            prpLdriver.getId().setRegistNo(registNo);
//            prpLdriver.setPolicyNo(reqRegist.getPolicyNo());
//            prpLdriver.getId().setSerialNo(n);
//            prpLdriver.setDriverName(reqRegist.getDriverName());
//            //國籍
//            prpLdriver.setDriverIdentity(reqRegist.getDriverIdentity());
//            prpLdriver.setDrivingLicenseNo(reqRegist.getDrivingLicenseNo());
//            prpLdriver.setBirthday(reqRegist.getBirthday());
//            prpLdriver.setDriverSex(reqRegist.getDriverSex());
//            
//            
//            //對方資料
//            prpLregist.getPrpLdrivers().add(prpLdriver);
//            List<ThirdInfo> thirdInfoLsit = reqRegist.getThirdInfoList();
//            for(int i = n; i<thirdInfoLsit.size();i++){
//                prpLdriver = new PrpLdriver();
//                prpLdriver.getId().setRegistNo(registNo);
//                prpLdriver.setPolicyNo(reqRegist.getPolicyNo());
//                prpLdriver.getId().setSerialNo(i);
//                prpLdriver.setDrivingLicenseNo(thirdInfoLsit.get(i).getDrivingLicenseNo());
//                prpLdriver.setDriverName(thirdInfoLsit.get(i).getDriverName());
//                prpLdriver.setDriverPhone(thirdInfoLsit.get(i).getPhoneNumber());
//                prpLdriver.setMobilePhone(thirdInfoLsit.get(i).getPhoneNumber());
//                //傷者姓名
//                prpLdriver.setBirthday(reqRegist.getBirthday());
//                prpLregist.getPrpLdrivers().add(prpLdriver);
//            }
//        }else if("F02".equals(reqRegist.getCode())){//住火险
//            prpLregist.setClassCode("F");
//            prpLregist.setRiskCode(reqRegist.getCode());
//            prpLregist.setRemark(reqRegist.getRemark());
//            prpLregist.setLossName(reqRegist.getLossName());
//        }else if("C1".equals(reqRegist.getCode())){//伤建险
//            prpLregist.setClassCode("C1");
//            prpLregist.setLinkerName(reqRegist.getLinkerName());
//            prpLregist.setPhoneNumber(reqRegist.getPhone());
//            prpLregist.setReportorMobile(reqRegist.getPhoneNumber());
//            
//            prpLpersonTrace.getId().setRegistNo(registNo);
//            prpLpersonTrace.setClaimNo(""); // 改为存空值
//            prpLpersonTrace.setPolicyNo(reqRegist.getPolicyNo());
//            prpLpersonTrace.getId().setPersonNo(1);
//            prpLpersonTrace.setPersonName(reqRegist.getLinkerName());
//            prpLpersonTrace.setWoundRemark(reqRegist.getRemark());
//        }else if("TA".equals(reqRegist.getCode())){//旅平险
//            prpLregist.setClassCode("C1");
//            prpLregist.setRiskCode(reqRegist.getCode());
//            prpLregist.setLinkerName(reqRegist.getLinkerName());
//        }   
//        //保存備案處理信息 并反饋消息
//        try {
//            this.getPrpLregistService().saveOrUpdate(prpLregist);
//            if("C1".equals(reqRegist.getCode())){
//                this.prpLpersonTraceService.save(prpLpersonTrace);
//            }
//            respRegistResult.setReturnCode("0");
//            respRegistResult.setRegistNo(registNo);
//            respRegistResult.setReturnMsg("備案信息保存成功");
//        } catch (Exception e) {
//            respRegistResult.setReturnCode("1");
//            respRegistResult.setReturnMsg("備案信息保存失敗");
//            e.printStackTrace();
//        }
//        //String resultXml=JaxbUtil.bean2Xml(respRegistResult);
//        return respRegistResult;
//    }

//    @Override
//    public RespPictureResult PICTURE(ReqPicture reqPicture){
//        String registNo= reqPicture.getRegistNo();
//        String image = reqPicture.getImage();
//        String originalFileName = reqPicture.getFileName();
//        String imageTypeCode  = reqPicture.getImageTypeCode();
//
//        BASE64Decoder decoder = new BASE64Decoder();  
//
//        Date now  = new Date();
//        SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy/MM/dd/HH");
//        String dateStr = dateFormat.format(now);
//        String typePath = "/claim/"+registNo+"/"+imageTypeCode.substring(0, 2)+"/"+imageTypeCode;
//        String appendPath = "prpall/"+dateStr+"/claim"+registNo+imageTypeCode.substring(0, 2)+imageTypeCode+"/";
//        String fileName = appendPath.replace("/", "-")+originalFileName;
//        String fileTransServiceUrl = "http://192.168.100.64:7005/filemanager/fileupload/FileUpload";
//        String fileTransServiceUrl1 = "http://192.168.100.64:7005/filemanager/services/FileTrans";
//        String fileIndexServiceUrl = "http://192.168.100.64:7005/filemanager/fileIndex";
//        byte[] content = null;
//
//        RespPictureResult respPicture = new RespPictureResult();
//
//        try{
//            if(image.length()>0&&!CommonUtils.isEmpty(image)){
//                content = decoder.decodeBuffer(image);  
//            }
//            long fileSize = content.length;
//            File cachePath = new File("C:\\Users\\Administrator\\AppData\\Local\\Temp\\FileUploaderTemp\\");
//            if(!cachePath.getAbsolutePath().contains(registNo)){
//                cachePath = new File(cachePath,registNo);
//            }
//            cachePath = new File(cachePath,"Thumb");
//            if (!cachePath.exists()) {
//                cachePath.mkdirs();
//            }
//            String imagePath = "C:\\Users\\Administrator\\AppData\\Local\\Temp\\FileUploaderTemp\\"+registNo+"\\Thumb\\"+fileName;
//
//            FileUploadUtils.stringConvertImg(image, imagePath);
//
//            FileUploadUtils.uploadFile(fileTransServiceUrl, registNo, appendPath, originalFileName, content, "",fileSize, "", "", "");
//            FileUploadUtils.saveIndex(registNo, typePath, appendPath, fileName, fileSize, originalFileName, "AG142", "00", "1", "", fileTransServiceUrl1, "AG142", "", null, registNo.substring(5, 9), null, null, null,fileIndexServiceUrl , "", "370820178414009876", "", "", "");
//
//            respPicture.setReturnCode("0");
//            respPicture.setReturnMsg("上傳成功");
//        }catch(Exception e){
//            respPicture.setReturnCode("1");
//            respPicture.setReturnMsg("上傳失敗");
//            e.printStackTrace();
//        }
//        return respPicture;
//    }

    @Override
    public RespWorkFlowResult WORKFLOW(ReqWorkFlow reqWorkFlow){
        String identifyNumber = reqWorkFlow.getIdentifyNumber();
        RespWorkFlowResult respWorkFlowResult = new RespWorkFlowResult();
        String conditions = " ( nodeType='regis' or nodeType = 'Broker' ) ";
        String tempStr = "";
        List<SwfLog> swflogList = null;
        try {
            tempStr = this.prpLregistrpolicyService.getPolicyNoByInsuredIdentifyNumber(identifyNumber);
            if (tempStr == null) {
                respWorkFlowResult.setReturnCode("1");
                respWorkFlowResult.setReturnMsg("查詢失敗,請檢信息是否錄入正確!!! ");
                return respWorkFlowResult;
            }
            conditions += " and (" + tempStr + ") and  (flowstatus='1' or flowstatus='2') order by handleTime desc ";
            swflogList = this.getSwfLogService().findSwfLog(QueryRule.getInstance().addSql(conditions));
            if (swflogList.size()>0) {
                List<ClaimStatus> claimList = new ArrayList<ClaimStatus>();
                ClaimStatus claimStatus = null;
                List<SwfLog> list = null;
                for (SwfLog s : swflogList) {
                    conditions = " RegistNo='" + s.getRegistNo() + "' order by handleTime desc";
                    list = this.getSwfLogService().findByConditions(conditions);
                    claimStatus = new ClaimStatus();
                    //備案號碼
                    claimStatus.setRegistNo(s.getRegistNo());
                    String status = "";
                    int N = 0;
                    for(SwfLog swflog : list){
                        //賠案號碼
                        if("claim".equals(swflog.getNodeType())&&(!("0").equals(swflog.getNodeStatus()))){
                            claimStatus.setClaimNo(swflog.getBusinessNo());
                        }
                        //賠案狀態 未處理---需求变更，返回最后一个已处理的节点
//                        if("0".equals(swflog.getNodeStatus())){
//                            status += swflog.getNodeName()+",";
//                        }
                        if("4".equals(swflog.getNodeStatus()) && swflog.getId().getLogNo() > N){
                            N = swflog.getId().getLogNo();
                            status = swflog.getNodeName();
                        }
                    }
//                    if(status.length()>0){
//                        status =status.substring(0, status.length()-1);
//                    }
                    claimStatus.setStauts(status);
                    claimList.add(claimStatus);
                }
                respWorkFlowResult.setClaimStatusList(claimList);
                respWorkFlowResult.setReturnCode("0");
                respWorkFlowResult.setReturnMsg("查詢成功! ");

            }
        }catch(Exception e){
            respWorkFlowResult.setReturnCode("1");
            respWorkFlowResult.setReturnMsg("查詢失敗! ");
        }
        return respWorkFlowResult;
    }
    @Override
    public RespClaimQueryResult CLAIMQUERY(ReqClaimQuery reqClaimQuery){
        //身份证
        String identifyNumber = reqClaimQuery.getIdentifyNumber();
        //车牌号
        String licenseNo = reqClaimQuery.getLicenseNo();
        //案件状态
        String claimStatus = reqClaimQuery.getStatus();//0-结案  1-未结案 2-全部
        //险类 险种
        String code = reqClaimQuery.getCode();

        RespClaimQueryResult respClaimQueryResult = new RespClaimQueryResult();
        //身份证号码、车牌号码查询备案信息
        String conditions = " ( nodeType='regis' or nodeType = 'Broker' ) and TO_DATE(substr(flowintime,1,10), 'yyyy-mm-dd') > add_months(SYSDATE, -60)";
        String tempStr = "";
        List<SwfLog> swflogList = null;
        try {
            tempStr = this.prpLregistrpolicyService.findPolicyNoByIdentifyNumberCode(identifyNumber,code);
            if (tempStr == null) {
                respClaimQueryResult.setReturnCode("1");
                respClaimQueryResult.setReturnMsg("查詢失敗,請檢信息是否錄入正確!!! ");
                return respClaimQueryResult;
            }
            conditions += " and (" + tempStr + ") " + StringConvert.convertString(" lossitemName", licenseNo, "=");
            //判断流程流转数据是否已经结束  --是否结案
            if ("1".equals(claimStatus)) {//未结案
                conditions = conditions + " and  (flowstatus='1' or flowstatus='2') order by handleTime desc";
                swflogList = this.getSwfLogService().findSwfLog(QueryRule.getInstance().addSql(conditions));
            } else if("0".equals(claimStatus)){//结案
                conditions = conditions + " and  flowstatus='0' order by handleTime desc"; 
                swflogList = this.getSwfLogService().findViewSwfLogAll(conditions);
            }else{//全部
                conditions = conditions + " order by handleTime desc"; 
                swflogList = this.getSwfLogService().findViewSwfLogAll(conditions);
            }
            if (swflogList.size()>0) {
                List<ClaimData> claimList = new ArrayList<ClaimData>();
                List<UndwrtData> undwrtList ;
                ClaimData claimData = null;
                UndwrtData undwrtData = null;
                List<SwfLog> list = null;
                PrpLregist prpLregist = null;
                PrpLclaim prpLclaim = null;
                PrpLcompensate prplcompensate = null;
                for (SwfLog s : swflogList) {
                    conditions = " RegistNo='" + s.getRegistNo() + "' order by handleTime desc";
                    if("1".equals(claimStatus)){//未结案
                        list = this.getSwfLogService().findByConditions(conditions);
                    }else{//全部,结案
                        list = this.getSwfLogService().findViewSwfLogAll(conditions);
                    };
                    claimData = new ClaimData();
                    undwrtList = new ArrayList<UndwrtData>();
                    String status = "";
//                    String strKindName = "";
                    //保單險類
                    prpLregist = this.prpLregistService.findPrpLregist(s.getRegistNo());
                    if(prpLregist != null){
                        if("A01".equals(prpLregist.getRiskCode())||"B01".equals(prpLregist.getRiskCode())){
                            claimData.setCode("D");
                        }else{
                            claimData.setCode(prpLregist.getRiskCode());
                        }
                    }
                    for(SwfLog swflog : list){
                        String kindType = "";
                        //結案日期、賠案號碼、賠案金額
                        if("claim".equals(swflog.getNodeType())){
                            prpLclaim = this.prpLclaimService.findPrpLclaim(swflog.getBusinessNo());
                            if(prpLclaim != null){
                                claimData.setEndCaseDate(prpLclaim.getEndCaseDate());
                                claimData.setSumPaid(prpLclaim.getSumPaid());
                                claimData.setClaimNo(swflog.getBusinessNo());
                            }
                        }
                        //賠付日期
                        if("compp".equals(swflog.getNodeType())){
                            prplcompensate = this.prpLcompensateService.findPrpLcompensate(swflog.getBusinessNo());
                            if(prplcompensate != null){
                                claimData.setPayDate(prplcompensate.getUnderWriteEndDate());
//                                claimData.setSumPaid(prplcompensate.getSumPaid());
                                if(("1".equals(prplcompensate.getUnderWriteFlag())||"3".equals(prplcompensate.getUnderWriteFlag()))&&(prplcompensate.getMutualCompensateNo()==null||"".equals(prplcompensate.getMutualCompensateNo()))){
                                    //核賠资料  互冲计算书不计算
                                    undwrtData = new UndwrtData();
                                    List<PrpCitemKind> prpCitemKindList;
                                    //当前理算书涉及险别
                                    List<String> referenceKinds = this.ClaimPrintService.getReferenceKinds(prplcompensate.getCompensateNo());
                                    for(String kind : referenceKinds){
//                                      strKindName = codeService.translateKindCode(prplcompensate.getRiskCode(),kind, true );
                                        prpCitemKindList= prpCitemKindService.findByConditions("policyNo = '" + prplcompensate.getPolicyNo() + "'" + " and kindCode = '" + kind + "' ");
                                        kindType += kind+prpCitemKindList.get(0).getKindName() +",";
                                    }
                                    if(kindType.length()>0){
                                        kindType =kindType.substring(0, kindType.length()-1);
                                    }
                                    undwrtData.setKindType(kindType);
                                    //核賠金额
                                    undwrtData.setSumRealPay(prplcompensate.getSumThisPaid());
                                    undwrtList.add(undwrtData);
                                }
                            }
                        }
                        //賠案狀態
                        if("0".equals(swflog.getNodeStatus())){                 
                            status += swflog.getNodeName()+",";
                        }
                    }
                    if(status.length()>0){
                        status =status.substring(0, status.length()-1);
                        if("0".equals(claimStatus)){
                            status = "";
                        }
                    }
                    claimData.setStatus(status);
                    claimData.setUndwrtList(undwrtList);
                    claimList.add(claimData);
                }
                respClaimQueryResult.setClaimList(claimList);
                respClaimQueryResult.setReturnCode("0");
                respClaimQueryResult.setReturnMsg("查詢成功! ");
            }else{
                respClaimQueryResult.setReturnCode("1");
                respClaimQueryResult.setReturnMsg("查詢無結果!!! ");
            }
        } catch (Exception e) {
            respClaimQueryResult.setReturnCode("1");
            respClaimQueryResult.setReturnMsg("查詢失敗!!! ");
        } 
        return respClaimQueryResult;
    }

    public PrpCitemKindService getPrpCitemKindService() {
        return prpCitemKindService;
    }

    public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
        this.prpCitemKindService = prpCitemKindService;
    }

    public PrpDuserService getPrpDuserService() {
        return prpDuserService;
    }

    public void setPrpDuserService(PrpDuserService prpDuserService) {
        this.prpDuserService = prpDuserService;
    }

    public ProcessCodeInputService getProcessCodeInputService() {
        return processCodeInputService;
    }

    public void setProcessCodeInputService(
            ProcessCodeInputService processCodeInputService) {
        this.processCodeInputService = processCodeInputService;
    }

    public BillService getBillService() {
        return billService;
    }

    public void setBillService(BillService billService) {
        this.billService = billService;
    }

    public PrpLregistService getPrpLregistService() {
        return prpLregistService;
    }

    public void setPrpLregistService(PrpLregistService prpLregistService) {
        this.prpLregistService = prpLregistService;
    }

    public SwfLogService getSwfLogService() {
        return swfLogService;
    }

    public void setSwfLogService(SwfLogService swfLogService) {
        this.swfLogService = swfLogService;
    }

    public PolicyService getPolicyService() {
        return policyService;
    }

    public void setPolicyService(PolicyService policyService) {
        this.policyService = policyService;
    }

    public PrplregistrpolicyService getPrpLregistrpolicyService() {
        return prpLregistrpolicyService;
    }

    public void setPrpLregistrpolicyService(
            PrplregistrpolicyService prpLregistrpolicyService) {
        this.prpLregistrpolicyService = prpLregistrpolicyService;
    }

    public PrpLclaimService getPrpLclaimService() {
        return prpLclaimService;
    }

    public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
        this.prpLclaimService = prpLclaimService;
    }

    public PrpLcompensateService getPrpLcompensateService() {
        return prpLcompensateService;
    }

    public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
        this.prpLcompensateService = prpLcompensateService;
    }

    public CodeService getCodeService() {
        return codeService;
    }

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public ClaimPrintServiceSpringImpl getClaimPrintService() {
        return ClaimPrintService;
    }

    public void setClaimPrintService(ClaimPrintServiceSpringImpl claimPrintService) {
        ClaimPrintService = claimPrintService;
    }

    public PrpLpersonTraceService getPrpLpersonTraceService() {
        return prpLpersonTraceService;
    }

    public void setPrpLpersonTraceService(
            PrpLpersonTraceService prpLpersonTraceService) {
        this.prpLpersonTraceService = prpLpersonTraceService;
    }
    
}
