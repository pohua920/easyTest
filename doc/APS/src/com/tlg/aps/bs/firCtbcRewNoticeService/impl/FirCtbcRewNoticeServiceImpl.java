package com.tlg.aps.bs.firCtbcRewNoticeService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firCtbcRewNoticeService.FirCtbcRewNoticeService;
import com.tlg.aps.bs.firCtbcRewNoticeService.RewDataInsertService;
import com.tlg.aps.util.AreaMappingUtil;
import com.tlg.aps.util.BuildingGradeMappingUtil;
import com.tlg.aps.util.PhoneMappingUtil;
import com.tlg.aps.util.XlsUtil;
import com.tlg.aps.vo.FirCtbcRewNoticeBatchVo;
import com.tlg.aps.vo.PhoneMappingSrcItem;
import com.tlg.exception.SystemException;

import com.tlg.prpins.entity.FirCtbcRewDontnotice;
import com.tlg.prpins.entity.FirCtbcRewFix180;
import com.tlg.prpins.entity.FirCtbcRewMatchLog;
import com.tlg.prpins.entity.FirCtbcRewMatchname;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;
import com.tlg.prpins.entity.FirCtbcRewSnn;
import com.tlg.prpins.entity.Prpcaddress;
import com.tlg.prpins.entity.Prpcinsured;
import com.tlg.prpins.entity.Prpcinsurednature;
import com.tlg.prpins.entity.Prpcitemkind;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.entity.Prpcmainprop;
import com.tlg.prpins.service.FirCtbcRewDontnoticeService;
import com.tlg.prpins.service.FirCtbcRewFix180Service;
import com.tlg.prpins.service.FirCtbcRewMatchLogService;
import com.tlg.prpins.service.FirCtbcRewMatchnameService;
import com.tlg.prpins.service.FirCtbcRewNoshowwordService;
import com.tlg.prpins.service.FirCtbcRewNoticeBatchService;
import com.tlg.prpins.service.FirCtbcRewSnnService;
import com.tlg.prpins.service.PrpcaddressService;
import com.tlg.prpins.service.PrpcinsuredService;
import com.tlg.prpins.service.PrpcinsurednatureService;
import com.tlg.prpins.service.PrpcitemkindService;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.prpins.service.PrpcmainpropService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewNoticeServiceImpl implements  FirCtbcRewNoticeService {
	
	private static final Logger logger = Logger.getLogger(FirCtbcRewNoticeServiceImpl.class);
	private ConfigUtil configUtil;
	
	private RewDataInsertService rewDataInsertService;
	private FirCtbcRewNoticeBatchService firCtbcRewNoticeBatchService;
	private FirCtbcRewFix180Service firCtbcRewFix180Service;
	private FirCtbcRewDontnoticeService firCtbcRewDontnoticeService;
	private FirCtbcRewSnnService firCtbcRewSnnService;
	private FirCtbcRewMatchLogService firCtbcRewMatchLogService;
	private PrpcaddressService prpcaddressService;
	private PrpcinsuredService prpcinsuredService;
	private PrpcinsurednatureService prpcinsurednatureService;
	private PrpcitemkindService prpcitemkindService;
	private PrpcmainpropService prpcmainpropService;
	private PrpcmainService prpcmainService;
	private RfrcodeService rfrcodeService;
	private FirCtbcRewMatchnameService firCtbcRewMatchnameService;
	private FirCtbcRewNoshowwordService firCtbcRewNoshowwordService;
	
	private Map<String,String> mapBuildingLevel = new HashMap<String,String> ();
	private FirCtbcRewMatchLog updateFirCtbcRewMatchLog180;

	@Override
	public Result policyDataImport(UserInfo userInfo, FirCtbcRewNoticeBatchVo voObject) throws SystemException, Exception {

		Result result = new Result();
		
		//新增執行記錄檔-----start
		long startTime=System.currentTimeMillis();
		Date executeTime = new Date();
		result = rewDataInsertService.inputFirCtbcRewNoticeBatch(executeTime, userInfo, voObject);
		FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = null;
		BigDecimal batchOid;
		if(result != null && result.getResObject() != null) {
			firCtbcRewNoticeBatch = (FirCtbcRewNoticeBatch)result.getResObject();
			batchOid = firCtbcRewNoticeBatch.getOid();
			if(batchOid == null) {
				return this.getReturnResult("批次號碼取號失敗");
			}
		}else {
			return this.getReturnResult("新增FirCtbcRewNoticeBatch批次程式執行記錄檔失敗");
		}
		//新增執行記錄檔-----end
		
//		//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----start
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("prgId", "FIR_CTBC_01_STATUS");
//		result = this.firBatchInfoService.findFirBatchInfoByUK(params);
//		if(result != null && result.getResObject() != null) {
//			FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
//			if("N".equals(firBatchInfo.getMailTo())) {
//				updateFirBatchLog(firBatchLog, "S", "FIR_BATCH_INFO設定檔設定為排程暫停執行。", userInfo);
//				return this.getReturnResult("FIR_BATCH_INFO設定檔設定為排程暫停執行。");
//			}
//		}
//		//判斷排程是否要執行(可透過資料庫來人工介入中斷或執行排程)-----end
//		
		//檔案內容寫入落地檔-----start
		try {
			File file = null;
			String fileName = "";
			if(voObject.getP180File() != null) {
				file = voObject.getP180File();
				fileName = voObject.getP180FileName();
				rewDataInsertService.inputRawData(firCtbcRewNoticeBatch, executeTime, file, fileName, "P180", userInfo);
			}
			
			if(voObject.getC180File() != null) {
				file = voObject.getC180File();
				fileName = voObject.getC180FileName();
				rewDataInsertService.inputRawData(firCtbcRewNoticeBatch, executeTime, file, fileName, "C180", userInfo);
			}
			
			if(voObject.getDontnoticeFile() != null) {
				file = voObject.getDontnoticeFile();
				fileName = voObject.getDontnoticeFileName();
				rewDataInsertService.inputRawData(firCtbcRewNoticeBatch, executeTime, file, fileName, "", userInfo);
			}
		}catch(Exception e) {
			firCtbcRewNoticeBatch.setTransing("E");
			rewDataInsertService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
			e.printStackTrace();
			throw e;
		}
		//檔案內容寫入落地檔-----end
		
		//解析檔案-----START
		try {
			rewDataInsertService.insertAnalyzeRawData(batchOid, executeTime, userInfo);
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			firCtbcRewNoticeBatch.setTransing("E");
			rewDataInsertService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
			e.printStackTrace();
			throw e;
		}
		//解析檔案-----END

		long endTime=System.currentTimeMillis();
		long transtime = endTime - startTime;
		rewDataInsertService.updateFirCtbcRewNoticeBatch(transtime/1000, firCtbcRewNoticeBatch);
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public File genCSV(String oid) throws SystemException,Exception{
		Map params = new HashMap();
		params.put("batchOid", oid);
		params.put("sortBy", "linenum");
		params.put("sortType", "ASC");
		Result result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
		if(result != null && result.getResObject() != null) {
			List<FirCtbcRewFix180> searchResult = (List<FirCtbcRewFix180>)result.getResObject();
			
			File Writerfile = new File("D://temp/TAI.txt");
			if (!Writerfile.exists()) {
				Writerfile.getParentFile().mkdirs();
			} else { // 檔案存在時刪除

				Writerfile.delete();

			}

			BufferedWriter fw = null;
			try {
				fw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(Writerfile, true),"BIG5"));// 指點編碼格式，以免讀取時中文字符異常
				System.out.println("---000----");
				fw.append("保單號碼,第保單號碼,被保險人姓名,被保險人ID,被保險人郵遞區號,被保險人通訊地址,要保人姓名,要保人ID,要保人郵遞區號,要保人通訊地址,");
				fw.append("火險總保額,地震基本保額,火險保費,地震基本保費,合計總保費,保險期間-起,保險期間-迄,標的物序號,標的物郵遞區號,標的物地址,構造,屋頂,");
				fw.append("總樓層數,建築等級,建造年份,使用面積,火險承保編號,保險標的物,動/不動產,保險費率,短期係數,使用性質,使用性質代號,建築等級代號,抵押權人代號,");
				fw.append("抵押權人名稱,服務人員,單位,登錄字號,姓名,業務來源(代號),放款部門別,自動續保,");
				//增加第2-5位要被保人
				fw.append("被保人1生日,被保人1國籍,職業代碼,被保人1職業,被保人1電話,註冊地,上市櫃,");
				fw.append("被保人2生日,被保人2國籍,職業代碼,被保人2職業,被保人2電話,註冊地,上市櫃,");
				fw.append("被保人3生日,被保人3國籍,職業代碼,被保人3職業,被保人3電話,註冊地,上市櫃,");
				fw.append("被保人4生日,被保人4國籍,職業代碼,被保人4職業,被保人4電話,註冊地,上市櫃,");
				fw.append("被保人5生日,被保人5國籍,職業代碼,被保人5職業,被保人5電話,註冊地,上市櫃,");
				fw.append("要保人1生日,要保人1國籍,職業代碼,要保人1職業,要保人1電話,註冊地,上市櫃,");
				fw.append("要保人2生日,要保人2國籍,職業代碼,要保人2職業,要保人2電話,註冊地,上市櫃,");
				fw.append("要保人3生日,要保人3國籍,職業代碼,要保人3職業,要保人3電話,註冊地,上市櫃,");
				fw.append("要保人4生日,要保人4國籍,職業代碼,要保人4職業,要保人4電話,註冊地,上市櫃,");
				fw.append("要保人5生日,要保人5國籍,職業代碼,要保人5職業,要保人5電話,註冊地,上市櫃,");
				//增加第6-7位要被保人
				fw.append("被保人6生日,被保人6國籍,職業代碼,被保人6職業,被保人6電話,註冊地,上市櫃,");
				fw.append("被保人7生日,被保人7國籍,職業代碼,被保人7職業,被保人7電話,註冊地,上市櫃,");
				fw.append("要保人6生日,要保人6國籍,職業代碼,要保人6職業,要保人6電話,註冊地,上市櫃,");
				fw.append("要保人7生日,要保人7國籍,職業代碼,要保人7職業,要保人7電話,註冊地,上市櫃,");
				fw.append("被保險人姓名2,被保險人ID2,被保險人姓名3,被保險人ID3,被保險人姓名4,被保險人ID4,被保險人姓名5,被保險人ID5,被保險人姓名6,被保險人ID6,被保險人姓名7,被保險人ID7,");
				fw.append("要保人姓名2,要保人ID2,要保人姓名3,要保人ID3,要保人姓名4,要保人ID4,要保人姓名5,要保人ID5,要保人姓名6,要保人ID6,要保人姓名7,要保人ID7,");
				//mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整
				fw.append("屋頂中文,建材中文");
				fw.newLine();

				for(FirCtbcRewFix180 u : searchResult) {

					fw.append(u.getPolicynumber().substring(0, 4) + ","); // 保單號碼
					fw.append(u.getPolicynumber().substring(4,
							u.getPolicynumber().length())
							+ "\b,"); // 第保單號碼
					fw.append(StringUtil.nullToSpace(u.getOwnername()) + ","); // 被保險人姓名
					fw.append(StringUtil.nullToSpace(u.getHolderid()) + ","); // 被保險人ID
					fw.append(u.getMailingaddress().substring(0, 3) + ","); // 被保險人郵遞區號
					fw.append(u.getMailingaddress().substring(5) + ","); // 被保險人通訊地址
					fw.append(StringUtil.nullToSpace(u.getInsuredname()) + ","); // 要保人姓名
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber()) + ","); // 要保人ID
					fw.append(u.getMailingaddress().substring(0, 3) + ","); // 要保人郵遞區號
					fw.append(u.getMailingaddress().substring(5) + ","); // 要保人通訊地址
					fw.append(u.getAmount() + ","); // 火險總保額
					fw.append(u.getAmount1() + ","); // 地震基本保額
					fw.append(u.getPremium() + ","); // 火險保費
					fw.append(u.getPremium1() + ","); // 地震基本保費
					fw.append(u.getTotalpremiums() + ","); // 合計總保費
					fw.append(u.getStartdate().substring(1) + ","); // 保險期間-起
					fw.append(u.getEnddate().substring(1) + ","); // 保險期間-迄
					fw.append("1,"); // 標的物序號
					fw.append(u.getAddress().substring(0, 3) + ","); // 標的物郵遞區號
					fw.append(u.getAddress().substring(4) + ","); // 標的物地址
																					// (更新五都，用location換掉行政區)
					fw.append(StringUtil.nullToSpace(u.getMaterial()) + ","); // 構造
					fw.append(StringUtil.nullToSpace(u.getRoof()) + ","); // 屋頂
					fw.append(StringUtil.nullToSpace(u.getSumfloors()) + ","); // 總樓層數
					fw.append(BuildingGradeMappingUtil.getlvDescription(u
							.getBuildinglevelcode()) + ","); // 建築等級
					fw.append(StringUtil.nullToSpace(u.getBuildyears()) + ","); // 建造年份
					fw.append(StringUtil.nullToSpace(u.getBuildingsarea()) + ","); // 使用面積
					fw.append("01,"); // 火險承保編號
					fw.append("建築物,"); // 保險標的物
					fw.append("2,"); // 動/不動產
					double rateD = u.getRates().doubleValue();
					rateD = rateD*1000;
					fw.append(String.valueOf(rateD) + ","); // 保險費率 (需乘上1000)
					fw.append("1.00,"); // 短期係數
					fw.append("住宅,"); // 使用性質
					fw.append("A0001A8,"); // 使用性質代號
					fw.append(BuildingGradeMappingUtil.getlv(u
							.getBuildinglevelcode()) + ","); // 建築等級代號
					fw.append("8220000,"); // 抵押權人代號
					fw.append("中國信託商業銀行股份有限公司,"); // 抵押權人名稱
					/**20211202：BJ016：FIR0220_中信代理投保通知處理，更換服務人員為BL030----START*/
					fw.append("BL030,"); // 服務人員
					/**20211202：BJ016：FIR0220_中信代理投保通知處理，更換服務人員BL030----END*/
					fw.append("A90000,"); // 單位
					
					String[] agentIds = { "AB1D049099", "FB1I049131", "FB1I243144",
					"HB1F019644" }; //若為I99050，則以100件為單位輪流更換登錄字號
					int pidx = 0;
					int threadshold = 100;
					int serial = 1;							
					/**
					 * 20200602:bj016:FIR0162:取消I99051業務來源-----start
					 * */
					fw.append(agentIds[pidx]+","); // 登錄字號
					if (serial % threadshold == 0) {
						pidx++;
						if (pidx == 4) {
							pidx = 0;
						}
					}
					serial++;					
					fw.append("楊凱勝,"); // 姓名
					fw.append("349011-" + "I99050,"); // 業務來源(代號)
					/**
					 * 20200602:bj016:FIR0162:取消I99051業務來源-----end
					 * */
					
					fw.append(StringUtil.nullToSpace(u.getBranchcode()) + ","); // 放款部門別
					fw.append(StringUtil.nullToSpace(u.getAutorenew()) +","); //是否續保
					//增加第2-5位要被保人 start
					fw.append(this.getTwDate(u.getOwnerbirthday(),true)+",");//被保人1生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality())+",");//被保人1國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname())+",");//被保人1職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber())+",");//被保人1電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getOwnerbirthday2(),false)+",");//被保人2生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality2())+",");//被保人2國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode2())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname2())+",");//被保人2職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber2())+",");//被保人2電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality2())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany2())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getOwnerbirthday3(),false)+",");//被保人3生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality3())+",");//被保人3國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode3())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname3())+",");//被保人3職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber3())+",");//被保人3電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality3())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany3())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getOwnerbirthday4(),false)+",");//被保人4生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality4())+",");//被保人4國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode4())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname4())+",");//被保人4職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber4())+",");//被保人4電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality4())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany4())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getOwnerbirthday5(),false)+",");//被保人5生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality5())+",");//被保人5國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode5())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname5())+",");//被保人5職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber5())+",");//被保人5電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality5())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany5())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getBirthday(),true)+",");//要保人1生日
					fw.append(StringUtil.nullToSpace(u.getNationality())+",");//要保人1國籍
					fw.append(StringUtil.nullToSpace(u.getOccupationcode())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname())+",");//要保人1職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber())+",");//要保人1電話
					fw.append(StringUtil.nullToSpace(u.getNationality())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany())+",");//上市櫃

					fw.append(this.getTwDate(u.getBirthday2(),false)+",");//要保人2生日
					fw.append(StringUtil.nullToSpace(u.getNationality2())+",");//要保人2國籍
					fw.append(StringUtil.nullToSpace(u.getOwnerbirthday2())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname2())+",");//要保人2職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber2())+",");//要保人2電話
					fw.append(StringUtil.nullToSpace(u.getNationality2())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany2())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getBirthday3(),false)+",");//要保人3生日
					fw.append(StringUtil.nullToSpace(u.getNationality3())+",");//要保人3國籍
					fw.append(StringUtil.nullToSpace(u.getOwnerbirthday3())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname3())+",");//要保人3職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber3())+",");//要保人3電話
					fw.append(StringUtil.nullToSpace(u.getNationality3())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany3())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getBirthday4(),false)+",");//要保人4生日
					fw.append(StringUtil.nullToSpace(u.getNationality4())+",");//要保人4國籍
					fw.append(StringUtil.nullToSpace(u.getOwnerbirthday4())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname4())+",");//要保人4職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber4())+",");//要保人4電話
					fw.append(StringUtil.nullToSpace(u.getNationality4())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany4())+",");//上市櫃
					
					fw.append(this.getTwDate(u.getBirthday5(),false)+",");//要保人5生日
					fw.append(StringUtil.nullToSpace(u.getNationality5())+",");//要保人5國籍
					fw.append(StringUtil.nullToSpace(u.getOwnerbirthday5())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname5())+",");//要保人5職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber5())+",");//要保人5電話
					fw.append(StringUtil.nullToSpace(u.getNationality5())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany5())+",");//上市櫃
					//增加第2-5位要被保人 end
					//增加第6-7位要被保人 start
					fw.append(this.getTwDate(u.getOwnerbirthday6(),true)+",");//被保人6生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality6())+",");//被保人6國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode6())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname6())+",");//被保人6職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber6())+",");//被保人6電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality6())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany6())+",");//上市櫃

					fw.append(this.getTwDate(u.getOwnerbirthday7(),true)+",");//被保人7生日
					fw.append(StringUtil.nullToSpace(u.getOwnernationality7())+",");//被保人7國籍
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationcode7())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOwneroccupationname7())+",");//被保人7職業
					fw.append(StringUtil.nullToSpace(u.getOwnerphonenumber7())+",");//被保人7電話
					fw.append(StringUtil.nullToSpace(u.getOwnernationality7())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getOwnerislistedcompany7())+",");//上市櫃

					fw.append(this.getTwDate(u.getBirthday6(),false)+",");//要保人6生日
					fw.append(StringUtil.nullToSpace(u.getNationality6())+",");//要保人6國籍
					fw.append(StringUtil.nullToSpace(u.getOccupationcode6())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname6())+",");//要保人6職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber6())+",");//要保人6電話
					fw.append(StringUtil.nullToSpace(u.getNationality6())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany6())+",");//上市櫃

					fw.append(this.getTwDate(u.getBirthday7(),false)+",");//要保人7生日
					fw.append(StringUtil.nullToSpace(u.getNationality7())+",");//要保人7國籍
					fw.append(StringUtil.nullToSpace(u.getOccupationcode7())+",");//職業代碼
					fw.append(StringUtil.nullToSpace(u.getOccupationname7())+",");//要保人7職業
					fw.append(StringUtil.nullToSpace(u.getPhonenumber7())+",");//要保人7電話
					fw.append(StringUtil.nullToSpace(u.getNationality7())+",");//註冊地
					fw.append(StringUtil.nullToSpace(u.getIslistedcompany7())+",");//上市櫃
					
					fw.append(StringUtil.nullToSpace(u.getOwnername2())+",");//被保險人姓名2
					fw.append(StringUtil.nullToSpace(u.getHolderid2())+",");//被保險人ID2
					fw.append(StringUtil.nullToSpace(u.getOwnername3())+",");//被保險人姓名3
					fw.append(StringUtil.nullToSpace(u.getHolderid3())+",");//被保險人ID3
					fw.append(StringUtil.nullToSpace(u.getOwnername4())+",");//被保險人姓名4
					fw.append(StringUtil.nullToSpace(u.getHolderid4())+",");//被保險人ID4
					fw.append(StringUtil.nullToSpace(u.getOwnername5())+",");//被保險人姓名5
					fw.append(StringUtil.nullToSpace(u.getHolderid5())+",");//被保險人ID5
					fw.append(StringUtil.nullToSpace(u.getOwnername6())+",");//被保險人姓名6
					fw.append(StringUtil.nullToSpace(u.getHolderid6())+",");//被保險人ID6
					fw.append(StringUtil.nullToSpace(u.getOwnername7())+",");//被保險人姓名7
					fw.append(StringUtil.nullToSpace(u.getHolderid7())+",");//被保險人ID7
					
					fw.append(StringUtil.nullToSpace(u.getInsuredname2())+",");//要保人姓名2
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber2())+",");//要保人ID2
					fw.append(StringUtil.nullToSpace(u.getInsuredname3())+",");//要保人姓名3
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber3())+",");//要保人ID3
					fw.append(StringUtil.nullToSpace(u.getInsuredname4())+",");//要保人姓名4
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber4())+",");//要保人ID4
					fw.append(StringUtil.nullToSpace(u.getInsuredname5())+",");//要保人姓名5
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber5())+",");//要保人ID5
					fw.append(StringUtil.nullToSpace(u.getInsuredname6())+",");//要保人姓名6
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber6())+",");//要保人ID6
					fw.append(StringUtil.nullToSpace(u.getInsuredname7())+",");//要保人姓名7
					fw.append(StringUtil.nullToSpace(u.getIdentifynumber7())+",");//要保人ID7

					//增加第6-7位要被保人 end
					/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 start*/
					fw.append(getRoofChinese(StringUtil.nullToSpace(u.getRoof()))+",");//屋頂中文
					fw.append(getMaterialName(StringUtil.nullToSpace(u.getMaterial()))+",");//建材中文
					/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 end*/
					fw.newLine();
				
				}
				fw.flush();
				fw.close();
			}catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Writerfile;
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public File genExcel(String oid, String o180filename) throws SystemException, Exception {
		
		//剔除自動續保件----START
		Map params = new HashMap();
		params.put("batchOid", oid);
		params.put("o180filename", o180filename);
		params.put("noAutoRenew", "Y");
		params.put("sortBy", "linenum");
		params.put("sortType", "ASC");
		Result result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
		//剔除自動續保件
		List<FirCtbcRewFix180> xlslists1 = new ArrayList<FirCtbcRewFix180>();
				
		if(result != null && result.getResObject() != null) {
			xlslists1 = (List<FirCtbcRewFix180>)result.getResObject();
		}
		//剔除自動續保件----END
		
		// 不通知list----START
		List<FirCtbcRewFix180> xlslists2 = new ArrayList<FirCtbcRewFix180>();
		params = new HashMap();
		params.put("batchOid", oid);
		params.put("sortBy", "linenum");
		params.put("sortType", "ASC");
		result = this.firCtbcRewDontnoticeService.findFirCtbcRewDontnoticeByParams(params);
		List<String> relateidList = null;
		if(result != null && result.getResObject() != null) {
			relateidList = new ArrayList<String>();
			List<FirCtbcRewDontnotice> searchResultFCRD = (List<FirCtbcRewDontnotice>)result.getResObject();
			for(FirCtbcRewDontnotice entity : searchResultFCRD) {
				relateidList.add(entity.getOwnerid());
			}
		}
		List<Long> linenumList = null;
		if(relateidList != null) {
			params = new HashMap();
			params.put("batchOid", oid);
			params.put("o180filename", o180filename);
			params.put("relateIdList", relateidList);
			params.put("sortBy", "linenum");
			params.put("sortType", "ASC");
			result = this.firCtbcRewSnnService.findFirCtbcRewSnnByParams(params);
			if(result != null && result.getResObject() != null) {
				linenumList = new ArrayList<Long>();
				List<FirCtbcRewSnn> searchResult = (List<FirCtbcRewSnn>)result.getResObject();
				for(FirCtbcRewSnn entity : searchResult) {
					linenumList.add(entity.getLinenum());
				}
			}
		}
		if(linenumList != null) {
			params = new HashMap();
			params.put("batchOid", oid);
			params.put("o180filename", o180filename);
			params.put("linenumList", linenumList);
			params.put("sortBy", "linenum");
			params.put("sortType", "ASC");
			result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
			if(result != null && result.getResObject() != null) {
				xlslists2 = (List<FirCtbcRewFix180>)result.getResObject();
			}
		}
		// 不通知list----END
		
		//剔除自動續保件再剔除不通知list----START
		List<FirCtbcRewFix180> xlslistsNeedNotice = new ArrayList<FirCtbcRewFix180>();
		boolean bNeedNotice = true;
		for(FirCtbcRewFix180 needNotice : xlslists1) {
			bNeedNotice = true;
			for(FirCtbcRewFix180 dontNotice : xlslists2) {
				if(dontNotice.getLinenum().longValue() == needNotice.getLinenum().longValue()) {
					bNeedNotice = false;
					break;
				}
			}
			if(bNeedNotice) {
				xlslistsNeedNotice.add(needNotice);
			}
		}
		//剔除自動續保件再剔除不通知list----END
		
		// 自動續保list-----Start
		List<FirCtbcRewFix180> xlslists3 = new ArrayList<FirCtbcRewFix180>();
		params = new HashMap();
		params.put("batchOid", oid);
		params.put("o180filename", o180filename);
		params.put("autorenew", "Y");
		params.put("sortBy", "linenum");
		params.put("sortType", "ASC");
		result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
		if(result != null && result.getResObject() != null) {
			xlslists3 = (List<FirCtbcRewFix180>)result.getResObject();
		}
		// 自動續保list-----End
		
		//產生EXCEL----START
		Date date = new Date();
		// 設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 進行轉換
		String dateString = sdf.format(date);
		dateString = String.valueOf(Integer.valueOf(dateString) - 19110000);
		File xlsFile = new File("D:\\temp\\" + dateString + "defaultCIBFireRenewal.xls");
		FileOutputStream fileOut = null;
		String tDate = "2021-05-01,2021-05-14,2021-04-30,2021-05-16,2021-05-31,2021-05-14";
		try {
			fileOut = new FileOutputStream(xlsFile);
			String xlsTemplateFile = "xls/defaultCIBFireRenewal.xls";
			HSSFWorkbook workbook = XlsUtil.openXls(xlsTemplateFile);

			// 產生通知sheet
			createNoteSheet(workbook, xlslistsNeedNotice, tDate, 0);

			// 產生不通知sheet
			createNoteSheet(workbook, xlslists2, tDate, 1);

			// 自動續保sheet
			createNoteSheet(workbook, xlslists3, tDate, 2);

			// 產生錯誤msg
			Map<Object, String> errorMsgs = new LinkedHashMap<Object, String>();
			params = new HashMap();
			params.put("batchOid", oid);
			params.put("o180filename", o180filename);
			params.put("hasErrorMsg", "Y");
			params.put("sortBy", "linenum");
			params.put("sortType", "ASC");
			result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewFix180> searchResult = (List<FirCtbcRewFix180>)result.getResObject();
				for(FirCtbcRewFix180 entity : searchResult) {
					errorMsgs.put(entity.getLinenum(), entity.getPolicynumber()+ ":" + entity.getErrormes());
				}
			}
			createErrorSheet(workbook, errorMsgs);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}finally {
			try {
				fileOut.flush();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		//產生EXCEL----END
		return xlsFile;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result compareData(UserInfo userInfo, String oid) throws SystemException, Exception {
		Result returnResult = new Result();
		Message message = new Message();
		FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = null;
		try {
			BigDecimal matchOid = this.firCtbcRewMatchLogService.getOid();
			Map params = new HashMap();
			params.put("oid", oid);
			Result result = this.firCtbcRewNoticeBatchService.findFirCtbcRewNoticeBatchByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewNoticeBatch> searchResult = (List<FirCtbcRewNoticeBatch>)result.getResObject();
				firCtbcRewNoticeBatch = searchResult.get(0);
				firCtbcRewNoticeBatch.setMatchcoreoid(matchOid.longValue());
				firCtbcRewNoticeBatch.setMatchcoretime(new Date());
				firCtbcRewNoticeBatch.setMatchcoreuser(userInfo.getUserId());
				firCtbcRewNoticeBatch.setMatchcoreing("Y");
				rewDataInsertService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
			}
			params = new HashMap();
			params.put("batchOid", oid);
			params.put("sortBy", "linenum");
			params.put("sortType", "ASC");
			result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewFix180> searchResult = (List<FirCtbcRewFix180>)result.getResObject();
				FirCtbcRewMatchLog firCtbcRewMatchLog1;
				FirCtbcRewMatchLog firCtbcRewMatchLog2;
				FirCtbcRewMatchLog firCtbcRewMatchLog3;
				FirCtbcRewMatchname firCtbcRewMatchname1;
				FirCtbcRewMatchname firCtbcRewMatchname2;
				FirCtbcRewMatchname firCtbcRewMatchname3;

				Date createDate = new Date();
				String userId = userInfo.getUserId();
				String matchType = "";
				for(FirCtbcRewFix180 firCtbcRewFix180 : searchResult) {
					firCtbcRewMatchLog1 = this.gigo180(firCtbcRewFix180, matchOid, userId, createDate);
					firCtbcRewMatchLog2 = this.gigoOldPolicyData(firCtbcRewFix180, matchOid, userId, createDate);
					firCtbcRewMatchLog3 = this.gigoCompareResult(firCtbcRewMatchLog1, firCtbcRewMatchLog2);
					firCtbcRewMatchname1 = this.gigo180ForFirCtbcRewMatchname(firCtbcRewFix180, matchOid, userId, createDate);
					firCtbcRewMatchname2 = this.gigoOldPolicyDataForFirCtbcRewMatchname(firCtbcRewFix180, matchOid, userId, createDate);
					firCtbcRewMatchname3 = this.gigoCompareResultForFirCtbcRewMatchname(firCtbcRewMatchname1, firCtbcRewMatchname2);
					
					/*20210602:BJ016:更新難字----START*/
					if(updateFirCtbcRewMatchLog180 != null) {
						firCtbcRewMatchLog1 = this.updateFirCtbcRewMatchLog180;
						firCtbcRewFix180.setOwnername(this.updateFirCtbcRewMatchLog180.getOwnername());
						firCtbcRewFix180.setOwnername2(this.updateFirCtbcRewMatchLog180.getOwnername2());
						firCtbcRewFix180.setOwnername3(this.updateFirCtbcRewMatchLog180.getOwnername3());
						firCtbcRewFix180.setOwnername4(this.updateFirCtbcRewMatchLog180.getOwnername4());
						firCtbcRewFix180.setOwnername5(this.updateFirCtbcRewMatchLog180.getOwnername5());
						firCtbcRewFix180.setOwnername6(this.updateFirCtbcRewMatchLog180.getOwnername6());
						firCtbcRewFix180.setOwnername7(this.updateFirCtbcRewMatchLog180.getOwnername7());
						
						firCtbcRewFix180.setInsuredname(this.updateFirCtbcRewMatchLog180.getInsuredname());
						firCtbcRewFix180.setInsuredname2(this.updateFirCtbcRewMatchLog180.getInsuredname2());
						firCtbcRewFix180.setInsuredname3(this.updateFirCtbcRewMatchLog180.getInsuredname3());
						firCtbcRewFix180.setInsuredname4(this.updateFirCtbcRewMatchLog180.getInsuredname4());
						firCtbcRewFix180.setInsuredname5(this.updateFirCtbcRewMatchLog180.getInsuredname5());
						firCtbcRewFix180.setInsuredname6(this.updateFirCtbcRewMatchLog180.getInsuredname6());
						firCtbcRewFix180.setInsuredname7(this.updateFirCtbcRewMatchLog180.getInsuredname7());
						
						this.rewDataInsertService.updateFirCtbcRewFix180(firCtbcRewFix180, userInfo);
					}
					/*20210602:BJ016:更新難字----END*/
					
					matchType = firCtbcRewMatchLog2.getMatchType();
					if(matchType == null || matchType.length() <= 0) {
						matchType = firCtbcRewMatchLog3.getMatchType();
					}
					
					firCtbcRewMatchLog1.setMatchType(matchType);
					firCtbcRewMatchLog2.setMatchType(matchType);
					firCtbcRewMatchLog3.setMatchType(matchType);
					
					this.rewDataInsertService.insertCompareData(firCtbcRewMatchLog1, firCtbcRewMatchLog2, firCtbcRewMatchLog3, userInfo);
					//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
					String logMatchType = matchType;
					/*20210602:BJ016:更換難字前的比對----START*/
					matchType = firCtbcRewMatchname2.getMatchType();
					if(matchType == null || matchType.length() <= 0) {
						matchType = firCtbcRewMatchname3.getMatchType();
					}
					
					firCtbcRewMatchname1.setMatchType(matchType);
					firCtbcRewMatchname2.setMatchType(matchType);
					firCtbcRewMatchname3.setMatchType(matchType);

					this.rewDataInsertService.insertCompareData(firCtbcRewMatchname1, firCtbcRewMatchname2, firCtbcRewMatchname3, userInfo);
					/*20210602:BJ016:更換難字前的比對----END*/
					
					/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start
					 * 依照matchType更新firCtbcRewFix180.matchType*/
					String nameMatchType = matchType;
					this.rewDataInsertService.updateFirCtbcRewFix180MatchType(firCtbcRewFix180, logMatchType, nameMatchType);
					/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end */
				}
			}
			
			if(firCtbcRewNoticeBatch != null) {
				firCtbcRewNoticeBatch.setMatchcoreing("N");
				rewDataInsertService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
			}
			
			returnResult.setResObject(Boolean.TRUE);
			message.setMessageString("比對成功");
			returnResult.setMessage(message);
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			if(firCtbcRewNoticeBatch != null) {
				firCtbcRewNoticeBatch.setMatchcoreing("");
				rewDataInsertService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
			}
			returnResult.setResObject(Boolean.FALSE);
			message.setMessageString("比對失敗。程式比對發生異常錯誤，請洽資訊部人員!" );
			returnResult.setMessage(message);
		}

		return returnResult;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public File genExcelDiff(String matchcoreoid) throws SystemException, Exception {
		Map params = new HashMap();
		params.put("matchOid", matchcoreoid);
		params.put("matchType", "2");
		params.put("sortBy", "LINENUM,DATATYPE");
		Result result = this.firCtbcRewMatchLogService.findFirCtbcRewMatchLogByParams(params);

		File xlsFile = null;
		
		if(result != null && result.getResObject() != null) {
			List<FirCtbcRewMatchLog> searchResult = (List<FirCtbcRewMatchLog>)result.getResObject();

			Date date = new Date();
			// 設定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// 進行轉換
			String dateString = sdf.format(date);
			dateString = String.valueOf(Integer.valueOf(dateString) - 19110000);
			xlsFile = new File("D:\\temp\\" + dateString + "defaultCIBFireRenewal.xls");
			FileOutputStream fileOut = new FileOutputStream(xlsFile);
			String xlsTemplateFile = "xls/defaultDiff.xls";
			HSSFWorkbook workbook = XlsUtil.openXls(xlsTemplateFile);
			HSSFSheet Sheet = workbook.getSheetAt(0);
			HSSFRow masterRow = null;
			int rowNum = 0;
			String dataType = "";
			
			for(FirCtbcRewMatchLog firCtbcRewMatchLog : searchResult) {
				rowNum++;
				System.out.println("rowNum="+rowNum);
				masterRow = Sheet.createRow(rowNum);
				// A~E
				masterRow.createCell(0).setCellValue(firCtbcRewMatchLog.getMatchOid().toString());// A比對批次號
				masterRow.createCell(1).setCellValue(firCtbcRewMatchLog.getBatchOid().toString());// B批次號
				masterRow.createCell(2).setCellValue(firCtbcRewMatchLog.getO180filename());// C180檔案名稱
				masterRow.createCell(3).setCellValue(firCtbcRewMatchLog.getLinenum());// D行號
				if("1".equals(firCtbcRewMatchLog.getDatatype())) dataType = "180檔資料";
				else if("2".equals(firCtbcRewMatchLog.getDatatype())) dataType = "核心資料";
				else if("3".equals(firCtbcRewMatchLog.getDatatype())) dataType = "比對結果";
				else dataType = "";
				masterRow.createCell(4).setCellValue(dataType);// E資料類型
				// F~J
				masterRow.createCell(5).setCellValue(firCtbcRewMatchLog.getIcreate());// F建檔人員
				masterRow.createCell(6).setCellValue(sdf.format(firCtbcRewMatchLog.getDcreate()));// G建檔日期
				masterRow.createCell(7).setCellValue("有差異");// H比對狀態
				masterRow.createCell(8).setCellValue(firCtbcRewMatchLog.getStartdate());// I保單起日
				masterRow.createCell(9).setCellValue(firCtbcRewMatchLog.getPolicynumber());// J保單號碼
				// K~P
				masterRow.createCell(10).setCellValue(firCtbcRewMatchLog.getRates());// K費率
				masterRow.createCell(11).setCellValue(firCtbcRewMatchLog.getEnddate());// L保單迄日
				masterRow.createCell(12).setCellValue(firCtbcRewMatchLog.getAmount());// M火險保額
				masterRow.createCell(13).setCellValue(firCtbcRewMatchLog.getAmount1());// N地震保額
				masterRow.createCell(14).setCellValue(firCtbcRewMatchLog.getMailingaddresspostcode());// O通訊郵遞區號
				masterRow.createCell(15).setCellValue(firCtbcRewMatchLog.getMailingaddress());// P通訊地址
				// Q~V
				masterRow.createCell(16).setCellValue(firCtbcRewMatchLog.getAddresspostcode());// Q標的物郵遞區號
				masterRow.createCell(17).setCellValue(firCtbcRewMatchLog.getAddress());// R標的物地址
				masterRow.createCell(18).setCellValue(firCtbcRewMatchLog.getBuildingsarea());// S不動產坪數
				masterRow.createCell(19).setCellValue(firCtbcRewMatchLog.getSumfloors());// T樓層
				masterRow.createCell(20).setCellValue(firCtbcRewMatchLog.getBuildyears());// U建造年份
				masterRow.createCell(21).setCellValue(firCtbcRewMatchLog.getIdentifynumber());// V被保險人ID

				// W~AA
				masterRow.createCell(22).setCellValue(firCtbcRewMatchLog.getInsuredname());// W被保險人姓名
				masterRow.createCell(23).setCellValue(firCtbcRewMatchLog.getIdentifynumber2());// X被保險人ID2
				masterRow.createCell(24).setCellValue(firCtbcRewMatchLog.getInsuredname2());// Y被保險人姓名2
				masterRow.createCell(25).setCellValue(firCtbcRewMatchLog.getIdentifynumber3());// Z被保險人ID3
				masterRow.createCell(26).setCellValue(firCtbcRewMatchLog.getInsuredname3());// AA被保險人姓名3
				
				// AB~AE
				masterRow.createCell(27).setCellValue(firCtbcRewMatchLog.getIdentifynumber4());// AB被保險人ID4
				masterRow.createCell(28).setCellValue(firCtbcRewMatchLog.getInsuredname4());// AC被保險人姓名4
				masterRow.createCell(29).setCellValue(firCtbcRewMatchLog.getIdentifynumber5());// AD被保險人ID5
				masterRow.createCell(30).setCellValue(firCtbcRewMatchLog.getInsuredname5());// AE被保險人姓名5
				// AF~AI
				masterRow.createCell(31).setCellValue(firCtbcRewMatchLog.getIdentifynumber6());// AF 被保險人ID6
				masterRow.createCell(32).setCellValue(firCtbcRewMatchLog.getInsuredname6());// AG被保險人姓名6
				masterRow.createCell(33).setCellValue(firCtbcRewMatchLog.getHolderid());// AH要保人ID
				masterRow.createCell(34).setCellValue(firCtbcRewMatchLog.getOwnername());// AI要保人姓名
				
				//AJ~AS
				masterRow.createCell(35).setCellValue(firCtbcRewMatchLog.getHolderid2());//AJ要保人ID2
				masterRow.createCell(36).setCellValue(firCtbcRewMatchLog.getOwnername2());//AK要保人姓名2
				masterRow.createCell(37).setCellValue(firCtbcRewMatchLog.getHolderid3());//AL要保人ID3
				masterRow.createCell(38).setCellValue(firCtbcRewMatchLog.getOwnername3());//AM要保人姓名3
				masterRow.createCell(39).setCellValue(firCtbcRewMatchLog.getHolderid4());//AN要保人ID4
				masterRow.createCell(40).setCellValue(firCtbcRewMatchLog.getOwnername4());//AO要保人姓名4
				masterRow.createCell(41).setCellValue(firCtbcRewMatchLog.getHolderid5());//AP要保人ID5
				masterRow.createCell(42).setCellValue(firCtbcRewMatchLog.getOwnername5());//AQ要保人姓名5
				masterRow.createCell(43).setCellValue(firCtbcRewMatchLog.getHolderid6());//AR要保人ID6
				masterRow.createCell(44).setCellValue(firCtbcRewMatchLog.getOwnername6());//AS要保人姓名6
				
				//AT~BF
				masterRow.createCell(45).setCellValue(firCtbcRewMatchLog.getAutorenew());//AT自動續保
				masterRow.createCell(46).setCellValue(firCtbcRewMatchLog.getBirthday());//AU被保人生日/設立日
				masterRow.createCell(47).setCellValue(firCtbcRewMatchLog.getNationality());//AV被保人國籍/註冊地中文
				masterRow.createCell(48).setCellValue(firCtbcRewMatchLog.getBirthday2());//AW被保人生日/設立日2
				masterRow.createCell(49).setCellValue(firCtbcRewMatchLog.getNationality2());//AX被保人國籍/註冊地中文2
				masterRow.createCell(50).setCellValue(firCtbcRewMatchLog.getBirthday3());//AY被保人生日/設立日3
				masterRow.createCell(51).setCellValue(firCtbcRewMatchLog.getNationality3());//AZ被保人國籍/註冊地中文3
				masterRow.createCell(52).setCellValue(firCtbcRewMatchLog.getBirthday4());//BA被保人生日/設立日4
				masterRow.createCell(53).setCellValue(firCtbcRewMatchLog.getNationality4());//BB被保人國籍/註冊地中文4
				masterRow.createCell(54).setCellValue(firCtbcRewMatchLog.getBirthday5());//BC被保人生日/設立日5
				masterRow.createCell(55).setCellValue(firCtbcRewMatchLog.getNationality5());//BD被保人國籍/註冊地中文5
				masterRow.createCell(56).setCellValue(firCtbcRewMatchLog.getBirthday6());//BE被保人生日/設立日6
				masterRow.createCell(57).setCellValue(firCtbcRewMatchLog.getNationality6());//BF被保人國籍/註冊地中文6
				
				//BG~BR
				masterRow.createCell(58).setCellValue(firCtbcRewMatchLog.getOwnerbirthday());//BG要保人1生日/設立日
				masterRow.createCell(59).setCellValue(firCtbcRewMatchLog.getOwnernationality());//BH要保人1國籍/註冊地中文
				masterRow.createCell(60).setCellValue(firCtbcRewMatchLog.getOwnerbirthday2());//BI要保人2生日/設立日
				masterRow.createCell(61).setCellValue(firCtbcRewMatchLog.getOwnernationality2());//BJ要保人2國籍/註冊地中文
				masterRow.createCell(62).setCellValue(firCtbcRewMatchLog.getOwnerbirthday3());//BK要保人3生日/設立日
				masterRow.createCell(63).setCellValue(firCtbcRewMatchLog.getOwnernationality3());//BL要保人3國籍/註冊地中文
				masterRow.createCell(64).setCellValue(firCtbcRewMatchLog.getOwnerbirthday4());//BM要保人4生日/設立日
				masterRow.createCell(65).setCellValue(firCtbcRewMatchLog.getOwnernationality4());//BN要保人4國籍/註冊地中文
				masterRow.createCell(66).setCellValue(firCtbcRewMatchLog.getOwnerbirthday5());//BO要保人5生日/設立日
				masterRow.createCell(67).setCellValue(firCtbcRewMatchLog.getOwnernationality5());//BP要保人5國籍/註冊地中文
				masterRow.createCell(68).setCellValue(firCtbcRewMatchLog.getOwnerbirthday6());//BQ要保人6生日/設立日
				masterRow.createCell(69).setCellValue(firCtbcRewMatchLog.getOwnernationality6());//BR要保人6國籍/註冊地中文
				
				//BS~CD
				masterRow.createCell(70).setCellValue(firCtbcRewMatchLog.getInsuredname7());//BS被保險人id7
				masterRow.createCell(71).setCellValue(firCtbcRewMatchLog.getInsuredname7());//BT被保險人姓名7
				masterRow.createCell(72).setCellValue(firCtbcRewMatchLog.getBirthday7());//BU被保人出生年月日/設立日7
				masterRow.createCell(73).setCellValue(firCtbcRewMatchLog.getNationality7());//BV被保人國籍/註冊地中文7
				masterRow.createCell(74).setCellValue(firCtbcRewMatchLog.getHolderid7());//BW要保人ID7
				masterRow.createCell(75).setCellValue(firCtbcRewMatchLog.getOwnername7());//BX要保人姓名7
				masterRow.createCell(76).setCellValue(firCtbcRewMatchLog.getOwnerbirthday7());//BY要保人出生年月日/設立日7
				masterRow.createCell(77).setCellValue(firCtbcRewMatchLog.getOwnernationality7());//BZ要保人國籍/註冊地中文7
				masterRow.createCell(78).setCellValue(firCtbcRewMatchLog.getRoof());//CA屋頂代碼
				masterRow.createCell(79).setCellValue(firCtbcRewMatchLog.getMaterial());//CB外牆代碼
				masterRow.createCell(80).setCellValue(firCtbcRewMatchLog.getBuildinglevelcode());//CC建築等級代號
				masterRow.createCell(81).setCellValue(firCtbcRewMatchLog.getBuildinglevel());//CD建築等級

			}
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		return xlsFile;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public File genExcelDiff2(String matchcoreoid) throws SystemException, Exception {
		Map params = new HashMap();
		params.put("matchOid", matchcoreoid);
		params.put("matchType", "2");
		params.put("sortBy", "O180FILENAME,LINENUM,DATATYPE");
		Result result = this.firCtbcRewMatchnameService.findFirCtbcRewMatchnameByParams(params);

		File xlsFile = null;
		
		if(result != null && result.getResObject() != null) {
			List<FirCtbcRewMatchname> searchResult = (List<FirCtbcRewMatchname>)result.getResObject();

			Date date = new Date();
			// 設定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// 進行轉換
			String dateString = sdf.format(date);
			dateString = String.valueOf(Integer.valueOf(dateString) - 19110000);
			xlsFile = new File("D:\\temp\\" + dateString + "defaultDiff2.xls");
			FileOutputStream fileOut = new FileOutputStream(xlsFile);
			String xlsTemplateFile = "xls/defaultDiff2.xls";
			HSSFWorkbook workbook = XlsUtil.openXls(xlsTemplateFile);
			HSSFSheet Sheet = workbook.getSheetAt(0);
			HSSFRow masterRow = null;
			int rowNum = 0;
			String dataType = "";
			
			for(FirCtbcRewMatchname firCtbcRewMatchname : searchResult) {
				rowNum++;
				System.out.println("rowNum="+rowNum);
				masterRow = Sheet.createRow(rowNum);
				//A~J
				masterRow.createCell(0).setCellValue(firCtbcRewMatchname.getMatchOid().toString());// A比對批次號
				masterRow.createCell(1).setCellValue(firCtbcRewMatchname.getBatchOid().toString());// B批次號
				masterRow.createCell(2).setCellValue(firCtbcRewMatchname.getO180filename());// C180檔案名稱
				masterRow.createCell(3).setCellValue(firCtbcRewMatchname.getLinenum());// D行號
				if("1".equals(firCtbcRewMatchname.getDatatype())) dataType = "180檔資料";
				else if("2".equals(firCtbcRewMatchname.getDatatype())) dataType = "核心資料";
				else if("3".equals(firCtbcRewMatchname.getDatatype())) dataType = "比對結果";
				else dataType = "";
				masterRow.createCell(4).setCellValue(dataType);// E資料類型
				masterRow.createCell(5).setCellValue(firCtbcRewMatchname.getIcreate());// F建檔人員
				masterRow.createCell(6).setCellValue(sdf.format(firCtbcRewMatchname.getDcreate()));// G建檔日期
				masterRow.createCell(7).setCellValue("有差異");// H比對狀態
				masterRow.createCell(8).setCellValue(firCtbcRewMatchname.getPolicynumber());// I保單號碼
				masterRow.createCell(9).setCellValue(firCtbcRewMatchname.getMailingaddresspostcode());// J通訊郵遞區號
				
				//K~T
				masterRow.createCell(10).setCellValue(firCtbcRewMatchname.getMailingaddress());// K通訊地址
				masterRow.createCell(11).setCellValue(firCtbcRewMatchname.getAddresspostcode());// L標的物郵遞區號
				masterRow.createCell(12).setCellValue(firCtbcRewMatchname.getAddress());// M標的物地址
				masterRow.createCell(13).setCellValue(firCtbcRewMatchname.getIdentifynumber());// N被保險人ID
				masterRow.createCell(14).setCellValue(firCtbcRewMatchname.getInsuredname());// O被保險人姓名
				masterRow.createCell(15).setCellValue(firCtbcRewMatchname.getIdentifynumber2());// P被保險人ID2
				masterRow.createCell(16).setCellValue(firCtbcRewMatchname.getInsuredname2());// Q被保險人姓名2
				masterRow.createCell(17).setCellValue(firCtbcRewMatchname.getIdentifynumber3());// R被保險人ID3
				masterRow.createCell(18).setCellValue(firCtbcRewMatchname.getInsuredname3());// S被保險人姓名3
				masterRow.createCell(19).setCellValue(firCtbcRewMatchname.getIdentifynumber4());// T被保險人ID4
				
				//U~AD
				masterRow.createCell(20).setCellValue(firCtbcRewMatchname.getInsuredname4());// U被保險人姓名4
				masterRow.createCell(21).setCellValue(firCtbcRewMatchname.getIdentifynumber5());// V被保險人ID5
				masterRow.createCell(22).setCellValue(firCtbcRewMatchname.getInsuredname5());// W被保險人姓名5
				masterRow.createCell(23).setCellValue(firCtbcRewMatchname.getIdentifynumber6());// X 被保險人ID6
				masterRow.createCell(24).setCellValue(firCtbcRewMatchname.getInsuredname6());// Y被保險人姓名6
				masterRow.createCell(25).setCellValue(firCtbcRewMatchname.getHolderid());// Z要保人ID
				masterRow.createCell(26).setCellValue(firCtbcRewMatchname.getOwnername());// AA要保人姓名
				masterRow.createCell(27).setCellValue(firCtbcRewMatchname.getHolderid2());//AB要保人ID2
				masterRow.createCell(28).setCellValue(firCtbcRewMatchname.getOwnername2());//AC要保人姓名2
				masterRow.createCell(29).setCellValue(firCtbcRewMatchname.getHolderid3());//AD要保人ID3
				
				//AE~AN
				masterRow.createCell(30).setCellValue(firCtbcRewMatchname.getOwnername3());//AE要保人姓名3
				masterRow.createCell(31).setCellValue(firCtbcRewMatchname.getHolderid4());//AF要保人ID4
				masterRow.createCell(32).setCellValue(firCtbcRewMatchname.getOwnername4());//AG要保人姓名4
				masterRow.createCell(33).setCellValue(firCtbcRewMatchname.getHolderid5());//AH要保人ID5
				masterRow.createCell(34).setCellValue(firCtbcRewMatchname.getOwnername5());//AI要保人姓名5
				masterRow.createCell(35).setCellValue(firCtbcRewMatchname.getHolderid6());//AJ要保人ID6
				masterRow.createCell(36).setCellValue(firCtbcRewMatchname.getOwnername6());//AK要保人姓名6
				masterRow.createCell(37).setCellValue(firCtbcRewMatchname.getInsuredname7());//AL被保險人id7
				masterRow.createCell(38).setCellValue(firCtbcRewMatchname.getInsuredname7());//AM被保險人姓名7
				masterRow.createCell(39).setCellValue(firCtbcRewMatchname.getHolderid7());//AN要保人ID7
				
				//AO
				masterRow.createCell(40).setCellValue(firCtbcRewMatchname.getOwnername7());//AO要保人姓名7

			}
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		return xlsFile;
	}
	
	public static void main(String args[]) throws Exception{
//		List<String> downloadFileList = new ArrayList<String>();
//		downloadFileList.add("FRIFIN202003261717.180.zip");
//		downloadFileList.add("FRIFIN202003261716.180.zip");
//		downloadFileList.add("FRIFIN202003161717.180.zip");
//		if(downloadFileList != null && downloadFileList.size() > 0) {
//			Collections.sort(downloadFileList);
//			for(String filename : downloadFileList) {
//				System.out.println(filename);
//			}
//		}
		
//		String test = "a|b||||||c";
//		String[] arrTest = test.split("\\|");
//		System.out.println("arrTest size : " + arrTest.length);
//		String[] arrTest3 = Arrays.copyOf(arrTest, 8);
//		System.out.println("arrTest3 size : " + arrTest3.length);
//		System.out.println("arrTest3 = " + Arrays.toString(arrTest3));
//		String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
//		date = "202004231328";
//		String filePath = "D:"+ File.separator + "ctbcFile" + File.separator + (Integer.valueOf(date.substring(0, 6)) - 191100) + File.separator + date.substring(0, 8) + File.separator + "FRIRBK" + date + ".180" + File.separator;
//		File fFilePath = new File(filePath);
//		if(!fFilePath.exists()) {
//			fFilePath.mkdirs();
//		}
//		//ZIP檔處理-----start
//		ZipUtil zipUtil = new ZipUtil();
//		ArrayList<File> filesToAdd = new ArrayList<File>();
//		if(fFilePath != null && fFilePath.isDirectory()) {
//			for (File fileEntry : fFilePath.listFiles()) {
//				if (fileEntry.isFile()) {
//					filesToAdd.add(fileEntry);
//				}
//			}
//		}
//		if(filesToAdd.size() > 0) {
//			zipUtil.writeZip(filePath.substring(0, filePath.length()-1), filesToAdd);
//		}
//		System.out.println("make zip file finished...");
//		//ZIP檔處理-----end
//		//全型轉半型-----start
//		String str = "０";
//		 System.out.println("before str : " + str);
//		for(char c:str.toCharArray()){
//		    str = str.replaceAll("　", " ");
//		    if((int)c >= 65281 && (int)c <= 65374){
//		      str = str.replace(c, (char)(((int)c)-65248));
//		    }
//		  }
//		  System.out.println("after str : " + str);
//		//全型轉半型-----end
//		//測試取得檔案list----start
//		String riskCode = "F";
//		String businessNo = "A1234567890";
//       
//        String responseString = downloadListService(riskCode, businessNo);
//        System.out.println("responseString : " + responseString);
//        Map classMap = new HashMap();
//        classMap.put("fileList", FileVo.class);
//        FileListResponseVo vo = (FileListResponseVo)JsonUtil.getDTO(responseString, FileListResponseVo.class, classMap);
//        ArrayList<FileVo> list = vo.getFileList();
//        for(FileVo fv : list) {
//        	System.out.println("----------");
//        	System.out.println("oid : " + fv.getOid());
//        	System.out.println("name : " + fv.getName());
//        	System.out.println("downloadPath : " + fv.getDownloadPath());
//        	System.out.println("----------");
//        }
//        
//		//測試取得檔案list----end
		//測試上傳檔案----start
//		String filePath = "D:\\aptoap\\insco\\180\\folderB\\FRISIGFK2020052200003.tiff";
//		String businessNo = "FK2020042200001";
//       
//        String responseString = uploadFile(filePath, businessNo);
//        System.out.println("responseString : " + responseString);
//        FileUploadResponseVo vo = (FileUploadResponseVo)JsonUtil.getDTO(responseString, FileUploadResponseVo.class);
//        System.out.println("status:" + vo.getStatus());
//        System.out.println("message:" + vo.getMessage());
//        System.out.println("uploadOid:" + vo.getUploadOid());
		//測試上傳檔案----end
		
		//測試資料夾內是否沒有檔案，沒有檔案的話就刪除資料夾---start
		String folderPath = "D:\\aptoap\\insco\\180\\RECASPO\\FRIFIN202007101001.180";
		Path path = Paths.get(folderPath);
		boolean hasFile = false;
		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
			hasFile = dirStream.iterator().hasNext();
			System.out.println("hasFile:" + hasFile);
			if(!hasFile) {
				File folder = new File(folderPath);
				if(folder != null && folder.isDirectory()) {
					folder.delete();
					System.out.println("delete folder");
				}
			}else {
				System.out.println("folder has file,can not delete.");
			}
	    }
		//測試資料夾內是否沒有檔案，沒有檔案的話就刪除資料夾---end
	}
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	private void createNoteSheet(HSSFWorkbook workbook, List<FirCtbcRewFix180> sh1, String tDate, int shnum)  throws Exception{

		HSSFSheet Sheet = workbook.getSheetAt(shnum);
		HSSFRow masterRow = null;

		logger.info("---------2---------->sh1.size() = " + sh1.size());

		int rowNum = 0;
		if (sh1 != null && sh1.size() > 0) {
			String postalCode;
			String address;
			String makeInsuredName;
			PhoneMappingSrcItem tmpPhone;
			String tel;
			String ext;
			String fax;
			String phoneAddress;
			for (FirCtbcRewFix180 sh : sh1) {

				postalCode = sh.getMailingaddress().substring(0, 5).trim();
				address = AreaMappingUtil.genAddressData(postalCode,sh.getMailingaddress().substring(5,sh.getMailingaddress().length()));
				makeInsuredName = PerDataProtect(1, StringUtil.rightTrim((sh.getOwnername())));
				tmpPhone = PhoneMappingUtil.getAreaCode(sh.getBranchcode());
				if(tmpPhone != null) {
					tel = tmpPhone.getTel();
					ext = (tmpPhone.getExt() == null ? "" : tmpPhone.getExt());
					fax = (tmpPhone.getFox() == null ? "" : tmpPhone.getFox());
					phoneAddress = (tmpPhone.getAddress() == null ? "" : tmpPhone.getAddress());
				}else {
					tel = "";
					ext = "";
					fax = "";
					phoneAddress = "";
				}
				
				String[] starts = sh.getStartdate().split("/");
				String sYYY = starts[0];
				//bh061 清除民國年前面的0，例如0106-->106 20171123 start
				if (sYYY.matches("\\d{4}")){
					sYYY=sYYY.substring(1,4);
				}
				//bh061 清除民國年前面的0，例如0106-->106 20171123 end
				String sMM = starts[1];
				String sDD = starts[2];

				String[] ends = sh.getEnddate().split("/");
				String eYYY = ends[0];
				//bh061 清除民國年前面的0，例如0106-->106 20171123 start
				if (eYYY.matches("\\d{4}")){
					eYYY=eYYY.substring(1,4);
				}
				//bh061 清除民國年前面的0，例如0106-->106 20171123 end
				String eMM = ends[1];
				String eDD = ends[2];

				String newAddress = AreaMappingUtil.genAddressData(sh.getAddress().substring(0, 5).trim(), sh.getAddress().substring(5, sh.getAddress().length()));
				String makeBuildingsArea = PerDataProtect(4, sh.getAddress().substring(0, 5) + newAddress);
				
//				String dYYY = "";
//				String dMM = "";
//				String dDD = "";
//				String exYYY = "";
//				String exMM = "";
//				String exDD = "";

//				String[] tmpdates = tDate.split(",");
//				String[] spdate1 = tmpdates[1].split("-");// 切割區間一
//				String[] spdate2 = tmpdates[3].split("-");// 切割區間二
//				String[] cdate1 = tmpdates[2].split("-");// 扣一
//				String[] cdate2 = tmpdates[5].split("-");// 扣二

				try {

//					if (Integer.valueOf(sDD) <= Integer.valueOf(spdate1[2])) {
//						dYYY = String.valueOf(Integer.valueOf(cdate1[0]) - 1911);
//						dMM = cdate1[1];
//						dDD = cdate1[2];
//					} else if (Integer.valueOf(sDD) >= Integer.valueOf(spdate2[2])) {
//						dYYY = String.valueOf(Integer.valueOf(cdate2[0]) - 1911);
//						dMM = cdate2[1];
//						dDD = cdate2[2];
//					}
//					
//					// 執行日
//					String[] executionDate = genTimeData(sh.getStartdate()).split("/");
//					exYYY = executionDate[0];
//					exMM = executionDate[1];
//					exDD = executionDate[2];
				} catch (Exception e) {
					logger.error("", e);

				}
				rowNum++;
				System.out.println("rowNum="+rowNum);
				masterRow = Sheet.createRow(rowNum);
				// A~E
				masterRow.createCell(0).setCellValue(sh.getInsuredname());// A借款人
				masterRow.createCell(1).setCellValue(postalCode);// B郵遞區號
				masterRow.createCell(2).setCellValue(address);// C通訊地址
				masterRow.createCell(3).setCellValue(tel);// D洽詢電話
				masterRow.createCell(4).setCellValue(ext);// E分機
				// F~J
				masterRow.createCell(5).setCellValue(fax);// F傳真電話
				masterRow.createCell(6).setCellValue(phoneAddress);// G地址
				masterRow.createCell(7).setCellValue(makeInsuredName);// H被保險人
				masterRow.createCell(8).setCellValue(sYYY);// I保險期間起年
				masterRow.createCell(9).setCellValue(sMM);// J保險期間起月
				// K~O
				masterRow.createCell(10).setCellValue(sDD);// K保險期間起日
				masterRow.createCell(11).setCellValue(eYYY);// L保險期間迄年
				masterRow.createCell(12).setCellValue(eMM);// M保險期間迄月
				masterRow.createCell(13).setCellValue(eDD);// N保險期間迄日
				masterRow.createCell(14).setCellValue(sh.getTotalpremiums());// O總保險費
				// P~T
				masterRow.createCell(15).setCellValue(makeBuildingsArea);// P保險標的物所在地址
				masterRow.createCell(16).setCellValue(sh.getAmount().toString());// Q建築物火險保額
				masterRow.createCell(17).setCellValue(sh.getPremium());// R建築物火險保費
				masterRow.createCell(18).setCellValue(sh.getAmount1().toString());// S建築物基本地震險保額
				masterRow.createCell(19).setCellValue(sh.getPremium1());// T建築物基本地震險保費

				// U~Y
				masterRow.createCell(20).setCellValue("中國信託商業銀行股份有限公司");// U抵押銀行
				// 1712260015 變更扣款日 改為以保單生效日做為扣款日 bh061 start 
//				masterRow.createCell(21).setCellValue(dYYY);// V扣款年
//				masterRow.createCell(22).setCellValue(dMM);// W扣款月
//				masterRow.createCell(23).setCellValue(dDD);// X扣款日
				masterRow.createCell(21).setCellValue(sYYY);// V扣款年
				masterRow.createCell(22).setCellValue(sMM);// W扣款月
				masterRow.createCell(23).setCellValue(sDD);// X扣款日
				// 1712260015 變更扣款日 bh061 end
				
				masterRow.createCell(24).setCellValue(sh.getSumfloors());// Y總樓層
				// Z~AD
				// 1712260015 變更扣款日 改為以保單生效日做為扣款日 bh061 start 
//				masterRow.createCell(25).setCellValue(exYYY);// Z執行日期年
//				masterRow.createCell(26).setCellValue(exMM);// AA執行日期月
//				masterRow.createCell(27).setCellValue(exDD);// AB執行日期日
				masterRow.createCell(25).setCellValue(sYYY);// Z執行日期年
				masterRow.createCell(26).setCellValue(sMM);// AA執行日期月
				masterRow.createCell(27).setCellValue(sDD);// AB執行日期日
				// 1712260015 變更扣款日 改為以保單生效日做為扣款日 bh061 end 
				masterRow.createCell(28).setCellValue(sh.getPolicynumber());// AC保單號碼
				// AD~AG
				masterRow.createCell(29).setCellValue("");// AD
				masterRow.createCell(30).setCellValue(sh.getBuildinglevel());// AE建築構造
				masterRow.createCell(31).setCellValue(sh.getBuildyears());// AF建築完成年
				masterRow.createCell(32).setCellValue(sh.getBuildingsarea());// AG使用面積
				
				//AH~AN
				masterRow.createCell(33).setCellValue(sh.getFireamt());//前一年度之住宅火險保險金額
				masterRow.createCell(34).setCellValue(sh.getFireprem());//前一年度之住宅火險保險費
				masterRow.createCell(35).setCellValue(sh.getQuakeamt());//前一年度之地震基本保險金額
				masterRow.createCell(36).setCellValue(sh.getQuakeprem());//前一年度之地震基本保險費
				masterRow.createCell(37).setCellValue(sh.getTtlpremium());//前一年度之總保險費
				masterRow.createCell(38).setCellValue(sh.getFiresuggestamount());//今年度的住火險建議保額
				masterRow.createCell(39).setCellValue(sh.getOldpolicyno());//前一年度之保單號碼(舊保單號碼)
				
				/**20211022 : BJ016 : 議題重啟，新增要被保險人2-7姓名-------START*/
				//AO~BA
				masterRow.createCell(40).setCellValue(sh.getInsuredname2());//要保人2
				masterRow.createCell(41).setCellValue(sh.getInsuredname3());//要保人3
				masterRow.createCell(42).setCellValue(sh.getInsuredname4());//要保人4
				masterRow.createCell(43).setCellValue(sh.getInsuredname5());//要保人5
				masterRow.createCell(44).setCellValue(sh.getInsuredname6());//要保人6
				masterRow.createCell(45).setCellValue(sh.getInsuredname7());//要保人7
				masterRow.createCell(46).setCellValue(sh.getOwnername2());//被保人2
				masterRow.createCell(47).setCellValue(sh.getOwnername3());//被保人3
				masterRow.createCell(48).setCellValue(sh.getOwnername4());//被保人4
				masterRow.createCell(49).setCellValue(sh.getOwnername5());//被保人5
				masterRow.createCell(50).setCellValue(sh.getOwnername6());//被保人6
				masterRow.createCell(51).setCellValue(sh.getOwnername7());//被保人7
				/**20211022 : BJ016 : 議題重啟，新增要被保險人2-7姓名-------END*/
			}
		}
	}
	
	private void createErrorSheet(HSSFWorkbook workbook,Map<Object, String> errorMsgs) {
		HSSFSheet Sheet2 = workbook.getSheetAt(3);
		HSSFRow masterRow = null;

		logger.info("---------3---------->errorMsgs.size() = " + errorMsgs.size());

		// 記錄處理資料錯誤訊息
		if (errorMsgs != null && errorMsgs.size() > 0) {
			int rowNum2 = 0;
			String errorMsg = "";
			String formId = "";
			for (Map.Entry<Object, String> entry : errorMsgs.entrySet()) {
				logger.debug(entry.getValue());
				errorMsg = "";
				formId = "";
				int firstWordPlace = entry.getValue().indexOf(":", 0);
				if(firstWordPlace > 0){
					errorMsg = entry.getValue().substring(firstWordPlace + 1);
					formId = entry.getValue().substring(0,firstWordPlace);
				}
				
				rowNum2++;
				masterRow = Sheet2.createRow(rowNum2);
				// Master columns
				// A.序號
				masterRow.createCell(0).setCellValue(String.valueOf(rowNum2));
				masterRow.createCell(1).setCellValue(entry.getKey().toString());
				masterRow.createCell(2).setCellValue(formId);
				masterRow.createCell(3).setCellValue(errorMsg);
				
//				String[] strErrArray = null;
//				strErrArray = entry.getValue().split(":");
//
//				if (strErrArray[1].indexOf("地震基金比對") == -1) {
//					rowNum2++;
//					masterRow = Sheet2.createRow(rowNum2);
//					// Master columns
//					// A.序號
//					masterRow.createCell(0).setCellValue(String.valueOf(rowNum2));
//					masterRow.createCell(1).setCellValue(entry.getKey().toString());
//					masterRow.createCell(2).setCellValue(strErrArray[0]);
//					masterRow.createCell(3).setCellValue(strErrArray[1] + ";" + strErrArray[2]);
//				}
			}
		}
	}
	
	/**
	 * 個資需隱藏的部份 Personal Data Protection 個資遮罩規則 
	 * 1.姓名 "除頭尾兩字外皆為”＊”雙字名第二字為”＊”" ex."李＊仁、歐＊＊雙、張＊"
	 * 2.身分證號 第5~8碼為”*” ex.A123****89 3.牌照號碼
	 * 第3~5碼/字元為”*” ex.G1-2345 G1***45 4.地址 區鄉鎮市~路街 之間都是*
	 */
	private String PerDataProtect(int type, String inString) {
		String result = "";
		
		if(inString != null && !inString.equals("")){
			String str1 = inString.trim();
			int totLen = str1.length();
			String str2 = "";
			int dist1 = 0;
			int dist2 = 0;

			if (type == 1) { // 姓名
				if (totLen < 3) {
					str2 = inString.substring(1, totLen);
				} else if (totLen == 3) {
					str2 = inString.substring(1, totLen-1);
				} else {
					str2 = inString.substring(1, 3);
				}

				String token = "";
				for (int a = 0; a < str2.length(); a++) {
					token += "*";
				}
				result = inString.replace(str2, token);
			} else if (type == 2) { // 身分證號
				if (totLen >= 8) {
					str2 = inString.substring(4, 8);
					result = inString.replace(str2, "****");
				}
			} else if (type == 3) { // 牌照號碼
				if (totLen >= 5) {
					str2 = inString.substring(2, 5);
					result = inString.replace(str2, "***");
				}
			} else if (type == 4) { // 地址
				dist1 = genAdress(1, inString);
				dist2 = genAdress(2, inString);

				if (dist1 > 0 && dist2 > 0 && (dist2 > (dist1 + 1))) {
					str2 = inString.substring(dist1 + 1, dist2);
				} else {
					str2 = inString.substring(dist1 + 1, dist1 + 3);
				}

				String token = "";
				for (int a = 0; a < str2.length(); a++) {
					token += "*";
				}
				result = inString.replace(str2, token);
			}
		}
		
		return result;
	}
	
	/*
	 * 鄉鎮市區 街路
	 */
	private int genAdress(int type, String inString) {
		int result = 0;

		if (type == 1) { // 鄉鎮市區
			if (inString.indexOf("鄉") >= 0) {
				result = inString.indexOf("鄉");
			} else if (inString.indexOf("鎮") >= 0) {
				result = inString.indexOf("鎮");
			} else if (inString.indexOf("區") >= 0) {
				result = inString.indexOf("區");
			} else if (inString.indexOf("市") >= 0) {
				result = inString.indexOf("市");
			}
		} else if (type == 2) {// 街路
			if (inString.indexOf("街") >= 0) {
				result = inString.indexOf("街");
			} else if (inString.indexOf("路") >= 0) {
				result = inString.indexOf("路");
			} else if (inString.indexOf("道") >= 0) {
				result = inString.indexOf("道");
			} else if (inString.indexOf("巷") >= 0) {
				result = inString.indexOf("巷");
			} else if (inString.indexOf("腳") >= 0) {
				result = inString.indexOf("腳");
			} else if (inString.indexOf("村") >= 0) {
				result = inString.indexOf("村");
			} else if (inString.indexOf("里") >= 0) {
				result = inString.indexOf("里");
			}
		}

		return result;
	}
	
	private FirCtbcRewMatchLog gigo180(FirCtbcRewFix180 firCtbcRewFix180, BigDecimal matchOid, String userId, Date createDate) {
		FirCtbcRewMatchLog firCtbcRewMatchLog = new FirCtbcRewMatchLog();
		firCtbcRewMatchLog.setMatchOid(matchOid);
		firCtbcRewMatchLog.setBatchOid(firCtbcRewFix180.getBatchOid());
		firCtbcRewMatchLog.setO180filename(firCtbcRewFix180.getO180filename());
		firCtbcRewMatchLog.setLinenum(firCtbcRewFix180.getLinenum());
		firCtbcRewMatchLog.setDatatype("1");
		firCtbcRewMatchLog.setIcreate(userId);
		firCtbcRewMatchLog.setDcreate(createDate);
		firCtbcRewMatchLog.setMatchType("");
		firCtbcRewMatchLog.setStartdate(firCtbcRewFix180.getStartdate());
		firCtbcRewMatchLog.setPolicynumber(firCtbcRewFix180.getPolicynumber());
		firCtbcRewMatchLog.setRates(firCtbcRewFix180.getRates().toString());
		firCtbcRewMatchLog.setEnddate(firCtbcRewFix180.getEnddate());
		firCtbcRewMatchLog.setAmount(firCtbcRewFix180.getAmount().toString());
		firCtbcRewMatchLog.setAmount1(firCtbcRewFix180.getAmount1().toString());
		
		String address = firCtbcRewFix180.getMailingaddress();
		String zipCode = "";
		if(address != null && address.length() >= 5) {
			zipCode = address.substring(0, 5);
			zipCode = zipCode.replaceAll("　","");//去除全形空白
			zipCode = zipCode.trim();
			address = address.substring(5);
			address = address.trim();
		}
		firCtbcRewMatchLog.setMailingaddress(address);
		firCtbcRewMatchLog.setMailingaddresspostcode(zipCode);
		
		address = firCtbcRewFix180.getAddress();
		zipCode = "";
		if(address != null && address.length() >= 5) {
			zipCode = address.substring(0, 5);
			zipCode = zipCode.replaceAll("　","");//去除全形空白
			zipCode = zipCode.trim();
			address = address.substring(5);
			address = address.trim();
		}
		firCtbcRewMatchLog.setAddress(address);
		firCtbcRewMatchLog.setAddresspostcode(zipCode);
		
		firCtbcRewMatchLog.setBuildingsarea(firCtbcRewFix180.getBuildingsarea());
		firCtbcRewMatchLog.setSumfloors(firCtbcRewFix180.getSumfloors());
		firCtbcRewMatchLog.setBuildyears(firCtbcRewFix180.getBuildyears());
		firCtbcRewMatchLog.setHolderid(firCtbcRewFix180.getHolderid());
		firCtbcRewMatchLog.setOwnername(firCtbcRewFix180.getOwnername());
		firCtbcRewMatchLog.setHolderid2(firCtbcRewFix180.getHolderid2());
		firCtbcRewMatchLog.setOwnername2(firCtbcRewFix180.getOwnername2());
		firCtbcRewMatchLog.setHolderid3(firCtbcRewFix180.getHolderid3());
		firCtbcRewMatchLog.setOwnername3(firCtbcRewFix180.getOwnername3());
		firCtbcRewMatchLog.setHolderid4(firCtbcRewFix180.getHolderid4());
		firCtbcRewMatchLog.setOwnername4(firCtbcRewFix180.getOwnername4());
		firCtbcRewMatchLog.setHolderid5(firCtbcRewFix180.getHolderid5());
		firCtbcRewMatchLog.setOwnername5(firCtbcRewFix180.getOwnername5());
		firCtbcRewMatchLog.setHolderid6(firCtbcRewFix180.getHolderid6());
		firCtbcRewMatchLog.setOwnername6(firCtbcRewFix180.getOwnername6());
		firCtbcRewMatchLog.setHolderid7(firCtbcRewFix180.getHolderid7());
		firCtbcRewMatchLog.setOwnername7(firCtbcRewFix180.getOwnername7());
		firCtbcRewMatchLog.setIdentifynumber(firCtbcRewFix180.getIdentifynumber());
		firCtbcRewMatchLog.setInsuredname(firCtbcRewFix180.getInsuredname());
		firCtbcRewMatchLog.setIdentifynumber2(firCtbcRewFix180.getIdentifynumber2());
		firCtbcRewMatchLog.setInsuredname2(firCtbcRewFix180.getInsuredname2());
		firCtbcRewMatchLog.setIdentifynumber3(firCtbcRewFix180.getIdentifynumber3());
		firCtbcRewMatchLog.setInsuredname3(firCtbcRewFix180.getInsuredname3());
		firCtbcRewMatchLog.setIdentifynumber4(firCtbcRewFix180.getIdentifynumber4());
		firCtbcRewMatchLog.setInsuredname4(firCtbcRewFix180.getInsuredname4());
		firCtbcRewMatchLog.setIdentifynumber5(firCtbcRewFix180.getIdentifynumber5());
		firCtbcRewMatchLog.setInsuredname5(firCtbcRewFix180.getInsuredname5());
		firCtbcRewMatchLog.setIdentifynumber6(firCtbcRewFix180.getIdentifynumber6());
		firCtbcRewMatchLog.setInsuredname6(firCtbcRewFix180.getInsuredname6());
		firCtbcRewMatchLog.setIdentifynumber7(firCtbcRewFix180.getIdentifynumber7());
		firCtbcRewMatchLog.setInsuredname7(firCtbcRewFix180.getInsuredname7());
		firCtbcRewMatchLog.setAutorenew(firCtbcRewFix180.getAutorenew());
		firCtbcRewMatchLog.setOwnerbirthday(firCtbcRewFix180.getOwnerbirthday());
		firCtbcRewMatchLog.setOwnernationality(firCtbcRewFix180.getOwnernationality());
		firCtbcRewMatchLog.setOwnerbirthday2(firCtbcRewFix180.getOwnerbirthday2());
		firCtbcRewMatchLog.setOwnernationality2(firCtbcRewFix180.getOwnernationality2());
		firCtbcRewMatchLog.setOwnerbirthday3(firCtbcRewFix180.getOwnerbirthday3());
		firCtbcRewMatchLog.setOwnernationality3(firCtbcRewFix180.getOwnernationality3());
		firCtbcRewMatchLog.setOwnerbirthday4(firCtbcRewFix180.getOwnerbirthday4());
		firCtbcRewMatchLog.setOwnernationality4(firCtbcRewFix180.getOwnernationality4());
		firCtbcRewMatchLog.setOwnerbirthday5(firCtbcRewFix180.getOwnerbirthday5());
		firCtbcRewMatchLog.setOwnernationality5(firCtbcRewFix180.getOwnernationality5());
		firCtbcRewMatchLog.setOwnerbirthday6(firCtbcRewFix180.getOwnerbirthday6());
		firCtbcRewMatchLog.setOwnernationality6(firCtbcRewFix180.getOwnernationality6());
		firCtbcRewMatchLog.setOwnerbirthday7(firCtbcRewFix180.getOwnerbirthday7());
		firCtbcRewMatchLog.setOwnernationality7(firCtbcRewFix180.getOwnernationality7());
		firCtbcRewMatchLog.setBirthday(firCtbcRewFix180.getBirthday());
		firCtbcRewMatchLog.setNationality(firCtbcRewFix180.getNationality());
		firCtbcRewMatchLog.setBirthday2(firCtbcRewFix180.getBirthday2());
		firCtbcRewMatchLog.setNationality2(firCtbcRewFix180.getNationality2());
		firCtbcRewMatchLog.setBirthday3(firCtbcRewFix180.getBirthday3());
		firCtbcRewMatchLog.setNationality3(firCtbcRewFix180.getNationality3());
		firCtbcRewMatchLog.setBirthday4(firCtbcRewFix180.getBirthday4());
		firCtbcRewMatchLog.setNationality4(firCtbcRewFix180.getNationality4());
		firCtbcRewMatchLog.setBirthday5(firCtbcRewFix180.getBirthday5());
		firCtbcRewMatchLog.setNationality5(firCtbcRewFix180.getNationality5());
		firCtbcRewMatchLog.setBirthday6(firCtbcRewFix180.getBirthday6());
		firCtbcRewMatchLog.setNationality6(firCtbcRewFix180.getNationality6());
		firCtbcRewMatchLog.setBirthday7(firCtbcRewFix180.getBirthday7());
		firCtbcRewMatchLog.setNationality7(firCtbcRewFix180.getNationality7());
		firCtbcRewMatchLog.setRoof(firCtbcRewFix180.getRoof());
		firCtbcRewMatchLog.setMaterial(firCtbcRewFix180.getMaterial());

		firCtbcRewMatchLog.setBuildinglevelcode(this.getBuildingLevelCodeMapping(firCtbcRewFix180.getBuildinglevelcode()));
		
		String buildinglevel = firCtbcRewFix180.getBuildinglevel();
		if(buildinglevel.indexOf("水泥平屋頂") >= 0) {
			buildinglevel = buildinglevel.replaceAll("水泥平屋頂", "平屋頂");
		}
		firCtbcRewMatchLog.setBuildinglevel(buildinglevel);
		
		return firCtbcRewMatchLog;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private FirCtbcRewMatchLog gigoOldPolicyData(FirCtbcRewFix180 firCtbcRewFix180, BigDecimal matchOid, String userId, Date createDate) throws Exception{
		String startDate = "";
		String policyNo = "";
		String rates = null;
		String endDate = "";
		String amount = null;
		String amount1 = null;
		String mailingAddress = "";
		String mailingAddressPostcode = "";
		String address = "";
		String addressPostcode = "";
		String buildingsArea = "";
		String sumFloors = "";
		String buildYears = "";
		String[] holderId = new String[7];

		String[] ownerName = new String[7];

		String[] identifyNumber = new String[7];

		String[] insuredName = new String[7];

		String autoRenew = "";
		String[] ownerBirthday = new String[7];

		String[] ownerNationality = new String[7];

		String[] birthday = new String[7];

		String[] nationality = new String[7];

		String roof = "";
		String material = "";
		String buildingLevelCode = "";
		String buildingLevel = "";
		
		FirCtbcRewMatchLog firCtbcRewMatchLog = new FirCtbcRewMatchLog();
		firCtbcRewMatchLog.setMatchOid(matchOid);
		firCtbcRewMatchLog.setBatchOid(firCtbcRewFix180.getBatchOid());
		firCtbcRewMatchLog.setO180filename(firCtbcRewFix180.getO180filename());
		firCtbcRewMatchLog.setLinenum(firCtbcRewFix180.getLinenum());
		firCtbcRewMatchLog.setDatatype("2");
		firCtbcRewMatchLog.setIcreate(userId);
		firCtbcRewMatchLog.setDcreate(createDate);
		firCtbcRewMatchLog.setMatchType("");
		
		/**20211221:BJ016:會有舊保單號碼欄位是空的情況，所以這邊要作舊保單號的檢查----START*/
		if(firCtbcRewFix180.getOldpolicyno() != null && firCtbcRewFix180.getOldpolicyno().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Map params = new HashMap();
			params.put("policyno", firCtbcRewFix180.getOldpolicyno());
			Result result = this.prpcmainService.findPrpcmainByParams(params);
			if(result != null && result.getResObject() != null) {
				List<Prpcmain> searchResult = (List<Prpcmain>)result.getResObject();
				Prpcmain prpcmain = searchResult.get(0);
				
				startDate = DateUtils.convertDateTimeTwo(prpcmain.getStartdate(),"/").toString();
				policyNo = prpcmain.getPolicyno();
				endDate = DateUtils.convertDateTimeTwo(prpcmain.getEnddate(),"/").toString();
				
				result = this.prpcitemkindService.findPrpcitemkindByParams(params);
				if(result != null && result.getResObject() != null) {
					List<Prpcitemkind> searchResult2 = (List<Prpcitemkind>) result.getResObject();
					for(Prpcitemkind prpcitemkind : searchResult2) {
						if("FR3".equals(prpcitemkind.getKindcode())) {
							rates = prpcitemkind.getRate().toString();
							amount = prpcitemkind.getAmount().toString();
						}
						if("FR2".equals(prpcitemkind.getKindcode())) {
							amount1 = prpcitemkind.getAmount().toString();
						}
					}
				}

				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				params.put("insuredflag", "1");
				params.put("sortBy", "SERIALNO");
				params.put("sortType", "ASC");
				result = this.prpcinsuredService.findPrpcinsuredByParams(params);
				
				Result resultTemp;
				Map paramsTemp = new HashMap();
				Prpcinsurednature prpcinsurednature;
				
				if(result != null && result.getResObject() != null) {
					List<Prpcinsured> searchResult2 = (List<Prpcinsured>) result.getResObject();
					int i = 0;
					for(Prpcinsured prpcinsured : searchResult2) {
						holderId[i] = prpcinsured.getIdentifynumber();
						ownerName[i] = prpcinsured.getInsuredname();
						if("4".equals(prpcinsured.getInsurednature()) && prpcinsured.getRegistdate() != null) {
							birthday[i] =sdf.format(prpcinsured.getRegistdate());
						}else {
							paramsTemp.clear();
							paramsTemp.put("policyno", prpcinsured.getPolicyno());
							paramsTemp.put("serialno", prpcinsured.getSerialno());
							resultTemp = this.prpcinsurednatureService.findPrpcinsurednatureByParams(paramsTemp);
							if(resultTemp != null && resultTemp.getResObject() != null) {
								List<Prpcinsurednature> searchResult3 = (List<Prpcinsurednature>)resultTemp.getResObject();
								prpcinsurednature = searchResult3.get(0);
								birthday[i] =sdf.format(prpcinsurednature.getBirthday());
							}
						}
						nationality[i] = prpcinsured.getDomicile();
					}
				}
				
				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				params.put("insuredflag", "2");
				params.put("sortBy", "SERIALNO");
				params.put("sortType", "ASC");
				result = this.prpcinsuredService.findPrpcinsuredByParams(params);
				
				if(result != null && result.getResObject() != null) {
					List<Prpcinsured> searchResult2 = (List<Prpcinsured>) result.getResObject();
					int i = 0;
					for(Prpcinsured prpcinsured : searchResult2) {
//						if(mailingAddress.length() <=0) mailingAddress = prpcinsured.getPostcode() + prpcinsured.getPostaddress();
						mailingAddress = prpcinsured.getPostaddress();
						mailingAddressPostcode = prpcinsured.getPostcode();
						identifyNumber[i] = prpcinsured.getIdentifynumber();
						insuredName[i] = prpcinsured.getInsuredname();
						if("4".equals(prpcinsured.getInsurednature()) && prpcinsured.getRegistdate() != null) {
							ownerBirthday[i] =sdf.format(prpcinsured.getRegistdate());
						}else {
							paramsTemp.clear();
							paramsTemp.put("policyno", prpcinsured.getPolicyno());
							paramsTemp.put("serialno", prpcinsured.getSerialno());
							resultTemp = this.prpcinsurednatureService.findPrpcinsurednatureByParams(paramsTemp);
							if(resultTemp != null && resultTemp.getResObject() != null) {
								List<Prpcinsurednature> searchResult3 = (List<Prpcinsurednature>)resultTemp.getResObject();
								prpcinsurednature = searchResult3.get(0);
								ownerBirthday[i] =sdf.format(prpcinsurednature.getBirthday());
							}
						}
						ownerNationality[i] = prpcinsured.getDomicile();
					}
				}
				
				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				result = this.prpcaddressService.findPrpcaddressByParams(params);
				if(result != null && result.getResObject() != null) {
					List<Prpcaddress> searchResult2 = (List<Prpcaddress>)result.getResObject();
					Prpcaddress prpcaddress = searchResult2.get(0);
					address = prpcaddress.getAddressdetailinfo();
					addressPostcode = prpcaddress.getAddresscode();
				}
				
				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				result = this.prpcmainpropService.findPrpcmainpropByParams(params);
				if(result != null && result.getResObject() != null) {
					List<Prpcmainprop> searchResult2 = (List<Prpcmainprop>)result.getResObject();
					Prpcmainprop prpcmainprop = searchResult2.get(0);
					buildingsArea = prpcmainprop.getBuildarea().toString();
					sumFloors = prpcmainprop.getSumfloors();
					buildYears = prpcmainprop.getBuildyears();
					roof = prpcmainprop.getRoofmaterial();
					material = prpcmainprop.getWallmaterial();
					buildingLevelCode = prpcmainprop.getStructure();
					buildingLevel = this.getMaterialName(material) + this.getRoofName(roof) + sumFloors + "層樓-" + this.getStructureMapping(buildingLevelCode) + "建築";
				}
			}else {
				firCtbcRewMatchLog.setMatchType("3");
			}
		}else {
			firCtbcRewMatchLog.setMatchType("4");
		}
		/**20211221:BJ016:會有舊保單號碼欄位是空的情況，所以這邊要作舊保單號的檢查----END*/
		
		firCtbcRewMatchLog.setStartdate(startDate);
		firCtbcRewMatchLog.setPolicynumber(policyNo);
		firCtbcRewMatchLog.setRates(rates);
		firCtbcRewMatchLog.setEnddate(endDate);
		firCtbcRewMatchLog.setAmount(amount);
		firCtbcRewMatchLog.setAmount1(amount1);
		firCtbcRewMatchLog.setMailingaddress(mailingAddress);
		firCtbcRewMatchLog.setMailingaddresspostcode(mailingAddressPostcode);
		firCtbcRewMatchLog.setAddress(address);
		firCtbcRewMatchLog.setAddresspostcode(addressPostcode);
		firCtbcRewMatchLog.setBuildingsarea(buildingsArea);
		firCtbcRewMatchLog.setSumfloors(sumFloors);
		firCtbcRewMatchLog.setBuildyears(buildYears);
		firCtbcRewMatchLog.setHolderid(holderId[0]);
		firCtbcRewMatchLog.setOwnername(ownerName[0]);
		firCtbcRewMatchLog.setHolderid2(holderId[1]);
		firCtbcRewMatchLog.setOwnername2(ownerName[1]);
		firCtbcRewMatchLog.setHolderid3(holderId[2]);
		firCtbcRewMatchLog.setOwnername3(ownerName[2]);
		firCtbcRewMatchLog.setHolderid4(holderId[3]);
		firCtbcRewMatchLog.setOwnername4(ownerName[3]);
		firCtbcRewMatchLog.setHolderid5(holderId[4]);
		firCtbcRewMatchLog.setOwnername5(ownerName[4]);
		firCtbcRewMatchLog.setHolderid6(holderId[5]);
		firCtbcRewMatchLog.setOwnername6(ownerName[5]);
		firCtbcRewMatchLog.setHolderid7(holderId[6]);
		firCtbcRewMatchLog.setOwnername7(ownerName[6]);
		firCtbcRewMatchLog.setIdentifynumber(identifyNumber[0]);
		firCtbcRewMatchLog.setInsuredname(insuredName[0]);
		firCtbcRewMatchLog.setIdentifynumber2(identifyNumber[1]);
		firCtbcRewMatchLog.setInsuredname2(insuredName[1]);
		firCtbcRewMatchLog.setIdentifynumber3(identifyNumber[2]);
		firCtbcRewMatchLog.setInsuredname3(insuredName[2]);
		firCtbcRewMatchLog.setIdentifynumber4(identifyNumber[3]);
		firCtbcRewMatchLog.setInsuredname4(insuredName[3]);
		firCtbcRewMatchLog.setIdentifynumber5(identifyNumber[4]);
		firCtbcRewMatchLog.setInsuredname5(insuredName[4]);
		firCtbcRewMatchLog.setIdentifynumber6(identifyNumber[5]);
		firCtbcRewMatchLog.setInsuredname6(insuredName[5]);
		firCtbcRewMatchLog.setIdentifynumber7(identifyNumber[6]);
		firCtbcRewMatchLog.setInsuredname7(insuredName[6]);
		firCtbcRewMatchLog.setAutorenew(autoRenew);
		firCtbcRewMatchLog.setOwnerbirthday(ownerBirthday[0]);
		firCtbcRewMatchLog.setOwnernationality(ownerNationality[0]);
		firCtbcRewMatchLog.setOwnerbirthday2(ownerBirthday[1]);
		firCtbcRewMatchLog.setOwnernationality2(ownerNationality[1]);
		firCtbcRewMatchLog.setOwnerbirthday3(ownerBirthday[2]);
		firCtbcRewMatchLog.setOwnernationality3(ownerNationality[2]);
		firCtbcRewMatchLog.setOwnerbirthday4(ownerBirthday[3]);
		firCtbcRewMatchLog.setOwnernationality4(ownerNationality[3]);
		firCtbcRewMatchLog.setOwnerbirthday5(ownerBirthday[4]);
		firCtbcRewMatchLog.setOwnernationality5(ownerNationality[4]);
		firCtbcRewMatchLog.setOwnerbirthday6(ownerBirthday[5]);
		firCtbcRewMatchLog.setOwnernationality6(ownerNationality[5]);
		firCtbcRewMatchLog.setOwnerbirthday7(ownerBirthday[6]);
		firCtbcRewMatchLog.setOwnernationality7(ownerNationality[6]);
		firCtbcRewMatchLog.setBirthday(birthday[0]);
		firCtbcRewMatchLog.setNationality(nationality[0]);
		firCtbcRewMatchLog.setBirthday2(birthday[1]);
		firCtbcRewMatchLog.setNationality2(nationality[1]);
		firCtbcRewMatchLog.setBirthday3(birthday[2]);
		firCtbcRewMatchLog.setNationality3(nationality[2]);
		firCtbcRewMatchLog.setBirthday4(birthday[3]);
		firCtbcRewMatchLog.setNationality4(nationality[3]);
		firCtbcRewMatchLog.setBirthday5(birthday[4]);
		firCtbcRewMatchLog.setNationality5(nationality[4]);
		firCtbcRewMatchLog.setBirthday6(birthday[5]);
		firCtbcRewMatchLog.setNationality6(nationality[5]);
		firCtbcRewMatchLog.setBirthday7(birthday[6]);
		firCtbcRewMatchLog.setNationality7(nationality[6]);
		firCtbcRewMatchLog.setRoof(roof);
		firCtbcRewMatchLog.setMaterial(material);
		firCtbcRewMatchLog.setBuildinglevelcode(buildingLevelCode);
		firCtbcRewMatchLog.setBuildinglevel(buildingLevel);
		
		return firCtbcRewMatchLog;
	}
	
	private FirCtbcRewMatchLog gigoCompareResult(FirCtbcRewMatchLog data180, FirCtbcRewMatchLog dataOldPolicy) throws Exception{
		FirCtbcRewMatchLog firCtbcRewMatchLog = new FirCtbcRewMatchLog(); 
		firCtbcRewMatchLog.setMatchOid(data180.getMatchOid());
		firCtbcRewMatchLog.setBatchOid(data180.getBatchOid());
		firCtbcRewMatchLog.setO180filename(data180.getO180filename());
		firCtbcRewMatchLog.setLinenum(data180.getLinenum());
		firCtbcRewMatchLog.setDatatype("3");
		firCtbcRewMatchLog.setIcreate(data180.getIcreate());
		firCtbcRewMatchLog.setDcreate(data180.getDcreate());
		firCtbcRewMatchLog.setMatchType("");
		
		String matchType = "1";
		String strData180 = "";
		String strDataOldPolicy = "";
		String strNoShowWord = "";
		//保險起日比對，只比對月與日----START
		if(data180 != null && data180.getStartdate() != null) {
			strData180 = data180.getStartdate();
			if(strData180.length() >= 6) {
				strData180 = strData180.substring(strData180.length() - 6, strData180.length());
			}
		}
		if(dataOldPolicy != null && dataOldPolicy.getStartdate() != null) {
			strDataOldPolicy = dataOldPolicy.getStartdate();
			if(strDataOldPolicy.length() >= 6) {
				strDataOldPolicy = strDataOldPolicy.substring(strDataOldPolicy.length() - 6, strDataOldPolicy.length());
			}
		}
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setStartdate("Y");
		}else {
			firCtbcRewMatchLog.setStartdate("N");
			matchType = "2";
		}
		//保險起日比對，只比對月與日----END
		
		//保單號碼比對，因為保單號新舊一定不同，這邊使用者要求直接給Y就可以了----START
//		if(data180.getPolicynumber() != null && (data180.getPolicynumber().equals(dataOldPolicy.getPolicynumber()))) {
//			firCtbcRewMatchLog.setPolicynumber("Y");
//		}else {
//			firCtbcRewMatchLog.setPolicynumber("N");
//			matchType = "2";
//		}
		firCtbcRewMatchLog.setPolicynumber("Y");
		//保單號碼比對，因為保單號新舊一定不同，這邊使用者要求直接給Y就可以了----END
		
		//費率比對，中信180檔的費率與核心資料庫的費率差1000倍，所以比對時要注意差1000倍的問題----START
		strData180 = "";
		strDataOldPolicy = "";
		BigDecimal number = null;
		if(data180 != null && data180.getRates() != null) {
			strData180 = data180.getRates();
			if(strData180.length() >= 0) {
				number = new BigDecimal(strData180);
				number = number.multiply(new BigDecimal("1000"));
				strData180 = String.valueOf(number.doubleValue());
			}
		}
		if(dataOldPolicy != null && dataOldPolicy.getRates() != null) {
			strDataOldPolicy = dataOldPolicy.getRates();
		}
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setRates("Y");
		}else {
			firCtbcRewMatchLog.setRates("N");
			matchType = "2";
		}
		//費率比對，中信180檔的費率與核心資料庫的費率差1000倍，所以比對時要注意差1000倍的問題----END
		
		//保險迄日比對，只比對月與日----START
		strData180 = "";
		strDataOldPolicy = "";
		if(data180 != null && data180.getEnddate() != null) {
			strData180 = data180.getEnddate();
			if(strData180.length() >= 6) {
				strData180 = strData180.substring(strData180.length() - 6, strData180.length());
			}
		}
		if(dataOldPolicy != null && dataOldPolicy.getEnddate() != null) {
			strDataOldPolicy = dataOldPolicy.getEnddate();
			if(strDataOldPolicy.length() >= 6) {
				strDataOldPolicy = strDataOldPolicy.substring(strDataOldPolicy.length() - 6, strDataOldPolicy.length());
			}
		}
		
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setEnddate("Y");
		}else {
			firCtbcRewMatchLog.setEnddate("N");
			matchType = "2";
		}
		//保險迄日比對，只比對月與日----END
		
		strData180 = StringUtil.nullToSpace(data180.getAmount());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAmount());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setAmount("Y");
		}else {
			firCtbcRewMatchLog.setAmount("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getAmount1());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAmount1());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setAmount1("Y");
		}else {
			firCtbcRewMatchLog.setAmount1("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getAddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strData180.length() >= 3) {
			strData180 = strData180.substring(0, 3);
		}
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strDataOldPolicy.length() >= 3) {
			strDataOldPolicy = strDataOldPolicy.substring(0, 3);
		}
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setAddresspostcode("Y");
		}else {
			firCtbcRewMatchLog.setAddresspostcode("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getAddress());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAddress());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setAddress("Y");
		}else {
			firCtbcRewMatchLog.setAddress("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getMailingaddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strData180.length() >= 3) {
			strData180 = strData180.substring(0, 3);
		}
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getMailingaddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strDataOldPolicy.length() >= 3) {
			strDataOldPolicy = strDataOldPolicy.substring(0, 3);
		}
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setMailingaddresspostcode("Y");
		}else {
			firCtbcRewMatchLog.setMailingaddresspostcode("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getMailingaddress());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getMailingaddress());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setMailingaddress("Y");
		}else {
			firCtbcRewMatchLog.setMailingaddress("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBuildingsarea());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBuildingsarea());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBuildingsarea("Y");
		}else {
			firCtbcRewMatchLog.setBuildingsarea("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getSumfloors());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getSumfloors());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setSumfloors("Y");
		}else {
			firCtbcRewMatchLog.setSumfloors("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBuildyears());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBuildyears());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBuildyears("Y");
		}else {
			firCtbcRewMatchLog.setBuildyears("N");
			matchType = "2";
		}
		
		/*20210602:BJ016:更新難字----START*/
		boolean checkIdAndNameIsSame = true;
		boolean needToUpdateFirCtbcRewMatchLog180 = false;
		strData180 = StringUtil.nullToSpace(data180.getHolderid());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid("Y");
		}else {
			firCtbcRewMatchLog.setHolderid("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start
		 * 若180被保險人不存在核心，一樣以ID去查詢FIR_CTBC_REW_NOSHOWWORD更換難字*/
		boolean checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getOwnername());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername("Y");
		}else {
			firCtbcRewMatchLog.setOwnername("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid() != null && data180.getHolderid().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername("Y");
					}else {
						firCtbcRewMatchLog.setOwnername("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getHolderid2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid2("Y");
		}else {
			firCtbcRewMatchLog.setHolderid2("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/

		strData180 = StringUtil.trim(data180.getOwnername2());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername2());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername2("Y");
		}else {
			firCtbcRewMatchLog.setOwnername2("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid2() != null && data180.getHolderid2().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid2());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername2(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername2("Y");
					}else {
						firCtbcRewMatchLog.setOwnername2("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getHolderid3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid3("Y");
		}else {
			firCtbcRewMatchLog.setHolderid3("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}

		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getOwnername3());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername3());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername3("Y");
		}else {
			firCtbcRewMatchLog.setOwnername3("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid3() != null && data180.getHolderid3().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid3());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername3(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername3("Y");
					}else {
						firCtbcRewMatchLog.setOwnername3("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getHolderid4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid4("Y");
		}else {
			firCtbcRewMatchLog.setHolderid4("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getOwnername4());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername4());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername4("Y");
		}else {
			firCtbcRewMatchLog.setOwnername4("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid4() != null && data180.getHolderid4().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid4());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername4(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername4("Y");
					}else {
						firCtbcRewMatchLog.setOwnername4("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getHolderid5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid5("Y");
		}else {
			firCtbcRewMatchLog.setHolderid5("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getOwnername5());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername5());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername5("Y");
		}else {
			firCtbcRewMatchLog.setOwnername5("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid5() != null && data180.getHolderid5().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid5());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername5(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername5("Y");
					}else {
						firCtbcRewMatchLog.setOwnername5("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getHolderid6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid6("Y");
		}else {
			firCtbcRewMatchLog.setHolderid6("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getOwnername6());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername6());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername6("Y");
		}else {
			firCtbcRewMatchLog.setOwnername6("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid6() != null && data180.getHolderid6().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid6());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername6(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername6("Y");
					}else {
						firCtbcRewMatchLog.setOwnername6("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getHolderid7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setHolderid7("Y");
		}else {
			firCtbcRewMatchLog.setHolderid7("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getOwnername7());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername7());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnername7("Y");
		}else {
			firCtbcRewMatchLog.setOwnername7("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getHolderid7() != null && data180.getHolderid7().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getHolderid7());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setOwnername7(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setOwnername7("Y");
					}else {
						firCtbcRewMatchLog.setOwnername7("N");
						matchType = "2";
					}
				}
			}
		}

		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber() != null && data180.getIdentifynumber().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber2("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber2("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname2());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname2());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname2("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname2("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber2() != null && data180.getIdentifynumber2().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber2());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname2(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname2("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname2("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber3("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber3("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname3());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname3());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname3("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname3("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber3() != null && data180.getIdentifynumber3().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber3());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname3(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname3("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname3("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber4("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber4("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname4());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname4());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname4("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname4("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber4() != null && data180.getIdentifynumber4().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber4());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname4(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname4("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname4("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber5("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber5("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname5());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname5());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname5("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname5("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber5() != null && data180.getIdentifynumber5().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber5());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname5(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname5("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname5("N");
						matchType = "2";
					}
				}
			}
		}
		
		checkIdAndNameIsSame = true;
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber6("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber6("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname6());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname6());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname6("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname6("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber6() != null && data180.getIdentifynumber6().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber6());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname6(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname6("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname6("N");
						matchType = "2";
					}
				}
			}
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setIdentifynumber7("Y");
		}else {
			firCtbcRewMatchLog.setIdentifynumber7("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		checkIdAndNameIsNull = false;
		if(StringUtil.isSpace(strDataOldPolicy)) {
			checkIdAndNameIsNull = true;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
		
		strData180 = StringUtil.trim(data180.getInsuredname7());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname7());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setInsuredname7("Y");
		}else {
			firCtbcRewMatchLog.setInsuredname7("N");
			matchType = "2";
			checkIdAndNameIsSame = false;
		}
		
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if(checkIdAndNameIsSame || checkIdAndNameIsNull) {
			if(data180.getIdentifynumber7() != null && data180.getIdentifynumber7().length() > 0) {
				strNoShowWord = this.findNoShowWord(data180.getIdentifynumber7());
				if(strNoShowWord != null && strNoShowWord.length() > 0) {
					data180.setInsuredname7(strNoShowWord);
					needToUpdateFirCtbcRewMatchLog180 = true;
					
					if(strNoShowWord.equals(strDataOldPolicy)) {
						firCtbcRewMatchLog.setInsuredname7("Y");
					}else {
						firCtbcRewMatchLog.setInsuredname7("N");
						matchType = "2";
					}
				}
			}
		}
		
		/**20210602:BJ016:如果需要更新難字，要設定資料*/
		if(needToUpdateFirCtbcRewMatchLog180) {
			this.updateFirCtbcRewMatchLog180 = data180;
		}else {
			this.updateFirCtbcRewMatchLog180 = null;
		}
		
		/*20210602:BJ016:更新難字----END*/
		
		strData180 = StringUtil.nullToSpace(data180.getAutorenew());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAutorenew());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setAutorenew("Y");
		}else {
			firCtbcRewMatchLog.setAutorenew("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday2("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday3("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday4("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday5("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday6("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnerbirthday7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnerbirthday7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnerbirthday7("Y");
		}else {
			firCtbcRewMatchLog.setOwnerbirthday7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality2("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality3("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality4("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality5("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality6("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getOwnernationality7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getOwnernationality7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setOwnernationality7("Y");
		}else {
			firCtbcRewMatchLog.setOwnernationality7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday("Y");
		}else {
			firCtbcRewMatchLog.setBirthday("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday2("Y");
		}else {
			firCtbcRewMatchLog.setBirthday2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday3("Y");
		}else {
			firCtbcRewMatchLog.setBirthday3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday4("Y");
		}else {
			firCtbcRewMatchLog.setBirthday4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday5("Y");
		}else {
			firCtbcRewMatchLog.setBirthday5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday6("Y");
		}else {
			firCtbcRewMatchLog.setBirthday6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBirthday7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBirthday7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBirthday7("Y");
		}else {
			firCtbcRewMatchLog.setBirthday7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality("Y");
		}else {
			firCtbcRewMatchLog.setNationality("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality2("Y");
		}else {
			firCtbcRewMatchLog.setNationality2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality3("Y");
		}else {
			firCtbcRewMatchLog.setNationality3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality4("Y");
		}else {
			firCtbcRewMatchLog.setNationality4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality5("Y");
		}else {
			firCtbcRewMatchLog.setNationality5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality6("Y");
		}else {
			firCtbcRewMatchLog.setNationality6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getNationality7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getNationality7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setNationality7("Y");
		}else {
			firCtbcRewMatchLog.setNationality7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getRoof());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getRoof());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setRoof("Y");
		}else {
			firCtbcRewMatchLog.setRoof("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getMaterial());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getMaterial());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setMaterial("Y");
		}else {
			firCtbcRewMatchLog.setMaterial("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBuildinglevelcode());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBuildinglevelcode());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBuildinglevelcode("Y");
		}else {
			firCtbcRewMatchLog.setBuildinglevelcode("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getBuildinglevel());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getBuildinglevel());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchLog.setBuildinglevel("Y");
		}else {
			firCtbcRewMatchLog.setBuildinglevel("N");
			matchType = "2";
		}
		
		firCtbcRewMatchLog.setMatchType(matchType);

		return firCtbcRewMatchLog;
	}
	
	private FirCtbcRewMatchname gigo180ForFirCtbcRewMatchname(FirCtbcRewFix180 firCtbcRewFix180, BigDecimal matchOid, String userId, Date createDate) {
		FirCtbcRewMatchname firCtbcRewMatchname = new FirCtbcRewMatchname();
		firCtbcRewMatchname.setMatchOid(matchOid);
		firCtbcRewMatchname.setBatchOid(firCtbcRewFix180.getBatchOid());
		firCtbcRewMatchname.setO180filename(firCtbcRewFix180.getO180filename());
		firCtbcRewMatchname.setLinenum(firCtbcRewFix180.getLinenum());
		firCtbcRewMatchname.setDatatype("1");
		firCtbcRewMatchname.setIcreate(userId);
		firCtbcRewMatchname.setDcreate(createDate);
		firCtbcRewMatchname.setMatchType("");
		firCtbcRewMatchname.setPolicynumber(firCtbcRewFix180.getPolicynumber());
		
		String address = firCtbcRewFix180.getMailingaddress();
		String zipCode = "";
		if(address != null && address.length() >= 5) {
			zipCode = address.substring(0, 5);
			zipCode = zipCode.replaceAll("　","");//去除全形空白
			zipCode = zipCode.trim();
			address = address.substring(5);
			address = address.trim();
		}
		firCtbcRewMatchname.setMailingaddress(address);
		firCtbcRewMatchname.setMailingaddresspostcode(zipCode);
		
		address = firCtbcRewFix180.getAddress();
		zipCode = "";
		if(address != null && address.length() >= 5) {
			zipCode = address.substring(0, 5);
			zipCode = zipCode.replaceAll("　","");//去除全形空白
			zipCode = zipCode.trim();
			address = address.substring(5);
			address = address.trim();
		}
		firCtbcRewMatchname.setAddress(address);
		firCtbcRewMatchname.setAddresspostcode(zipCode);
		
		firCtbcRewMatchname.setHolderid(firCtbcRewFix180.getHolderid());
		firCtbcRewMatchname.setOwnername(firCtbcRewFix180.getOwnername());
		firCtbcRewMatchname.setHolderid2(firCtbcRewFix180.getHolderid2());
		firCtbcRewMatchname.setOwnername2(firCtbcRewFix180.getOwnername2());
		firCtbcRewMatchname.setHolderid3(firCtbcRewFix180.getHolderid3());
		firCtbcRewMatchname.setOwnername3(firCtbcRewFix180.getOwnername3());
		firCtbcRewMatchname.setHolderid4(firCtbcRewFix180.getHolderid4());
		firCtbcRewMatchname.setOwnername4(firCtbcRewFix180.getOwnername4());
		firCtbcRewMatchname.setHolderid5(firCtbcRewFix180.getHolderid5());
		firCtbcRewMatchname.setOwnername5(firCtbcRewFix180.getOwnername5());
		firCtbcRewMatchname.setHolderid6(firCtbcRewFix180.getHolderid6());
		firCtbcRewMatchname.setOwnername6(firCtbcRewFix180.getOwnername6());
		firCtbcRewMatchname.setHolderid7(firCtbcRewFix180.getHolderid7());
		firCtbcRewMatchname.setOwnername7(firCtbcRewFix180.getOwnername7());
		firCtbcRewMatchname.setIdentifynumber(firCtbcRewFix180.getIdentifynumber());
		firCtbcRewMatchname.setInsuredname(firCtbcRewFix180.getInsuredname());
		firCtbcRewMatchname.setIdentifynumber2(firCtbcRewFix180.getIdentifynumber2());
		firCtbcRewMatchname.setInsuredname2(firCtbcRewFix180.getInsuredname2());
		firCtbcRewMatchname.setIdentifynumber3(firCtbcRewFix180.getIdentifynumber3());
		firCtbcRewMatchname.setInsuredname3(firCtbcRewFix180.getInsuredname3());
		firCtbcRewMatchname.setIdentifynumber4(firCtbcRewFix180.getIdentifynumber4());
		firCtbcRewMatchname.setInsuredname4(firCtbcRewFix180.getInsuredname4());
		firCtbcRewMatchname.setIdentifynumber5(firCtbcRewFix180.getIdentifynumber5());
		firCtbcRewMatchname.setInsuredname5(firCtbcRewFix180.getInsuredname5());
		firCtbcRewMatchname.setIdentifynumber6(firCtbcRewFix180.getIdentifynumber6());
		firCtbcRewMatchname.setInsuredname6(firCtbcRewFix180.getInsuredname6());
		firCtbcRewMatchname.setIdentifynumber7(firCtbcRewFix180.getIdentifynumber7());
		firCtbcRewMatchname.setInsuredname7(firCtbcRewFix180.getInsuredname7());
		
		return firCtbcRewMatchname;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private FirCtbcRewMatchname gigoOldPolicyDataForFirCtbcRewMatchname(FirCtbcRewFix180 firCtbcRewFix180, BigDecimal matchOid, String userId, Date createDate) throws Exception{
		String policyNo = "";
		String mailingAddress = "";
		String mailingAddressPostcode = "";
		String address = "";
		String addressPostcode = "";

		String[] holderId = new String[7];

		String[] ownerName = new String[7];

		String[] identifyNumber = new String[7];

		String[] insuredName = new String[7];

		String[] ownerBirthday = new String[7];

		String[] ownerNationality = new String[7];

		String[] birthday = new String[7];

		String[] nationality = new String[7];

		FirCtbcRewMatchname firCtbcRewMatchname = new FirCtbcRewMatchname();
		firCtbcRewMatchname.setMatchOid(matchOid);
		firCtbcRewMatchname.setBatchOid(firCtbcRewFix180.getBatchOid());
		firCtbcRewMatchname.setO180filename(firCtbcRewFix180.getO180filename());
		firCtbcRewMatchname.setLinenum(firCtbcRewFix180.getLinenum());
		firCtbcRewMatchname.setDatatype("2");
		firCtbcRewMatchname.setIcreate(userId);
		firCtbcRewMatchname.setDcreate(createDate);
		firCtbcRewMatchname.setMatchType("");
		
		/**20211221:BJ016:會有舊保單號碼欄位是空的情況，所以這邊要作舊保單號的檢查----START*/
		if(firCtbcRewFix180.getOldpolicyno() != null && firCtbcRewFix180.getOldpolicyno().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Map params = new HashMap();
			params.put("policyno", firCtbcRewFix180.getOldpolicyno());
			Result result = this.prpcmainService.findPrpcmainByParams(params);
			if(result != null && result.getResObject() != null) {
				List<Prpcmain> searchResult = (List<Prpcmain>)result.getResObject();
				Prpcmain prpcmain = searchResult.get(0);
				
				policyNo = prpcmain.getPolicyno();

				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				params.put("insuredflag", "1");
				params.put("sortBy", "SERIALNO");
				params.put("sortType", "ASC");
				result = this.prpcinsuredService.findPrpcinsuredByParams(params);
				
				Result resultTemp;
				Map paramsTemp = new HashMap();
				Prpcinsurednature prpcinsurednature;
				
				if(result != null && result.getResObject() != null) {
					List<Prpcinsured> searchResult2 = (List<Prpcinsured>) result.getResObject();
					int i = 0;
					for(Prpcinsured prpcinsured : searchResult2) {
						holderId[i] = prpcinsured.getIdentifynumber();
						ownerName[i] = prpcinsured.getInsuredname();
						if("4".equals(prpcinsured.getInsurednature()) && prpcinsured.getRegistdate() != null) {
							birthday[i] =sdf.format(prpcinsured.getRegistdate());
						}else {
							paramsTemp.clear();
							paramsTemp.put("policyno", prpcinsured.getPolicyno());
							paramsTemp.put("serialno", prpcinsured.getSerialno());
							resultTemp = this.prpcinsurednatureService.findPrpcinsurednatureByParams(paramsTemp);
							if(resultTemp != null && resultTemp.getResObject() != null) {
								List<Prpcinsurednature> searchResult3 = (List<Prpcinsurednature>)resultTemp.getResObject();
								prpcinsurednature = searchResult3.get(0);
								birthday[i] =sdf.format(prpcinsurednature.getBirthday());
							}
						}
						nationality[i] = prpcinsured.getDomicile();
					}
				}
				
				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				params.put("insuredflag", "2");
				params.put("sortBy", "SERIALNO");
				params.put("sortType", "ASC");
				result = this.prpcinsuredService.findPrpcinsuredByParams(params);
				
				if(result != null && result.getResObject() != null) {
					List<Prpcinsured> searchResult2 = (List<Prpcinsured>) result.getResObject();
					int i = 0;
					for(Prpcinsured prpcinsured : searchResult2) {
//						if(mailingAddress.length() <=0) mailingAddress = prpcinsured.getPostcode() + prpcinsured.getPostaddress();
						mailingAddress = prpcinsured.getPostaddress();
						mailingAddressPostcode = prpcinsured.getPostcode();
						identifyNumber[i] = prpcinsured.getIdentifynumber();
						insuredName[i] = prpcinsured.getInsuredname();
						if("4".equals(prpcinsured.getInsurednature()) && prpcinsured.getRegistdate() != null) {
							ownerBirthday[i] =sdf.format(prpcinsured.getRegistdate());
						}else {
							paramsTemp.clear();
							paramsTemp.put("policyno", prpcinsured.getPolicyno());
							paramsTemp.put("serialno", prpcinsured.getSerialno());
							resultTemp = this.prpcinsurednatureService.findPrpcinsurednatureByParams(paramsTemp);
							if(resultTemp != null && resultTemp.getResObject() != null) {
								List<Prpcinsurednature> searchResult3 = (List<Prpcinsurednature>)resultTemp.getResObject();
								prpcinsurednature = searchResult3.get(0);
								ownerBirthday[i] =sdf.format(prpcinsurednature.getBirthday());
							}
						}
						ownerNationality[i] = prpcinsured.getDomicile();
					}
				}
				
				params.clear();
				params.put("policyno", firCtbcRewFix180.getOldpolicyno());
				result = this.prpcaddressService.findPrpcaddressByParams(params);
				if(result != null && result.getResObject() != null) {
					List<Prpcaddress> searchResult2 = (List<Prpcaddress>)result.getResObject();
					Prpcaddress prpcaddress = searchResult2.get(0);
					address = prpcaddress.getAddressdetailinfo();
					addressPostcode = prpcaddress.getAddresscode();
				}
				
			}else {
				firCtbcRewMatchname.setMatchType("3");
			}
		}else {
			firCtbcRewMatchname.setMatchType("4");
		}
		/**20211221:BJ016:會有舊保單號碼欄位是空的情況，所以這邊要作舊保單號的檢查----END*/

		firCtbcRewMatchname.setPolicynumber(policyNo);
		firCtbcRewMatchname.setMailingaddress(mailingAddress);
		firCtbcRewMatchname.setMailingaddresspostcode(mailingAddressPostcode);
		firCtbcRewMatchname.setAddress(address);
		firCtbcRewMatchname.setAddresspostcode(addressPostcode);
		firCtbcRewMatchname.setHolderid(holderId[0]);
		firCtbcRewMatchname.setOwnername(ownerName[0]);
		firCtbcRewMatchname.setHolderid2(holderId[1]);
		firCtbcRewMatchname.setOwnername2(ownerName[1]);
		firCtbcRewMatchname.setHolderid3(holderId[2]);
		firCtbcRewMatchname.setOwnername3(ownerName[2]);
		firCtbcRewMatchname.setHolderid4(holderId[3]);
		firCtbcRewMatchname.setOwnername4(ownerName[3]);
		firCtbcRewMatchname.setHolderid5(holderId[4]);
		firCtbcRewMatchname.setOwnername5(ownerName[4]);
		firCtbcRewMatchname.setHolderid6(holderId[5]);
		firCtbcRewMatchname.setOwnername6(ownerName[5]);
		firCtbcRewMatchname.setHolderid7(holderId[6]);
		firCtbcRewMatchname.setOwnername7(ownerName[6]);
		firCtbcRewMatchname.setIdentifynumber(identifyNumber[0]);
		firCtbcRewMatchname.setInsuredname(insuredName[0]);
		firCtbcRewMatchname.setIdentifynumber2(identifyNumber[1]);
		firCtbcRewMatchname.setInsuredname2(insuredName[1]);
		firCtbcRewMatchname.setIdentifynumber3(identifyNumber[2]);
		firCtbcRewMatchname.setInsuredname3(insuredName[2]);
		firCtbcRewMatchname.setIdentifynumber4(identifyNumber[3]);
		firCtbcRewMatchname.setInsuredname4(insuredName[3]);
		firCtbcRewMatchname.setIdentifynumber5(identifyNumber[4]);
		firCtbcRewMatchname.setInsuredname5(insuredName[4]);
		firCtbcRewMatchname.setIdentifynumber6(identifyNumber[5]);
		firCtbcRewMatchname.setInsuredname6(insuredName[5]);
		firCtbcRewMatchname.setIdentifynumber7(identifyNumber[6]);
		firCtbcRewMatchname.setInsuredname7(insuredName[6]);
		
		return firCtbcRewMatchname;
	}
	
	private FirCtbcRewMatchname gigoCompareResultForFirCtbcRewMatchname(FirCtbcRewMatchname data180, FirCtbcRewMatchname dataOldPolicy) throws Exception{
		FirCtbcRewMatchname firCtbcRewMatchname = new FirCtbcRewMatchname(); 
		firCtbcRewMatchname.setMatchOid(data180.getMatchOid());
		firCtbcRewMatchname.setBatchOid(data180.getBatchOid());
		firCtbcRewMatchname.setO180filename(data180.getO180filename());
		firCtbcRewMatchname.setLinenum(data180.getLinenum());
		firCtbcRewMatchname.setDatatype("3");
		firCtbcRewMatchname.setIcreate(data180.getIcreate());
		firCtbcRewMatchname.setDcreate(data180.getDcreate());
		firCtbcRewMatchname.setMatchType("");
		
		String matchType = "1";
		String strData180 = "";
		String strDataOldPolicy = "";
		
		//保單號碼比對，因為保單號新舊一定不同，這邊使用者要求直接給Y就可以了----START
//		if(data180.getPolicynumber() != null && (data180.getPolicynumber().equals(dataOldPolicy.getPolicynumber()))) {
//			firCtbcRewMatchLog.setPolicynumber("Y");
//		}else {
//			firCtbcRewMatchLog.setPolicynumber("N");
//			matchType = "2";
//		}
		firCtbcRewMatchname.setPolicynumber("Y");
		//保單號碼比對，因為保單號新舊一定不同，這邊使用者要求直接給Y就可以了----END
		
		strData180 = StringUtil.nullToSpace(data180.getAddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strData180.length() >= 3) {
			strData180 = strData180.substring(0, 3);
		}
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strDataOldPolicy.length() >= 3) {
			strDataOldPolicy = strDataOldPolicy.substring(0, 3);
		}
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setAddresspostcode("Y");
		}else {
			firCtbcRewMatchname.setAddresspostcode("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getAddress());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getAddress());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setAddress("Y");
		}else {
			firCtbcRewMatchname.setAddress("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getMailingaddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strData180.length() >= 3) {
			strData180 = strData180.substring(0, 3);
		}
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getMailingaddresspostcode());
		//20210525：BJ016：郵遞區號只要比對前三碼就可以了
		if(strDataOldPolicy.length() >= 3) {
			strDataOldPolicy = strDataOldPolicy.substring(0, 3);
		}
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setMailingaddresspostcode("Y");
		}else {
			firCtbcRewMatchname.setMailingaddresspostcode("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getMailingaddress());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getMailingaddress());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setMailingaddress("Y");
		}else {
			firCtbcRewMatchname.setMailingaddress("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid("Y");
		}else {
			firCtbcRewMatchname.setHolderid("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid2("Y");
		}else {
			firCtbcRewMatchname.setHolderid2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid3("Y");
		}else {
			firCtbcRewMatchname.setHolderid3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid4("Y");
		}else {
			firCtbcRewMatchname.setHolderid4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid5("Y");
		}else {
			firCtbcRewMatchname.setHolderid5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid6("Y");
		}else {
			firCtbcRewMatchname.setHolderid6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getHolderid7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getHolderid7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setHolderid7("Y");
		}else {
			firCtbcRewMatchname.setHolderid7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername("Y");
		}else {
			firCtbcRewMatchname.setOwnername("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername2());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername2());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername2("Y");
		}else {
			firCtbcRewMatchname.setOwnername2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername3());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername3());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername3("Y");
		}else {
			firCtbcRewMatchname.setOwnername3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername4());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername4());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername4("Y");
		}else {
			firCtbcRewMatchname.setOwnername4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername5());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername5());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername5("Y");
		}else {
			firCtbcRewMatchname.setOwnername5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername6());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername6());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername6("Y");
		}else {
			firCtbcRewMatchname.setOwnername6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getOwnername7());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getOwnername7());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setOwnername7("Y");
		}else {
			firCtbcRewMatchname.setOwnername7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber2());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber2());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber2("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber3());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber3());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber3("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber4());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber4());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber4("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber5());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber5());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber5("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber6());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber6());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber6("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.nullToSpace(data180.getIdentifynumber7());
		strDataOldPolicy = StringUtil.nullToSpace(dataOldPolicy.getIdentifynumber7());
		if(strData180.equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setIdentifynumber7("Y");
		}else {
			firCtbcRewMatchname.setIdentifynumber7("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname("Y");
		}else {
			firCtbcRewMatchname.setInsuredname("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname2());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname2());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname2("Y");
		}else {
			firCtbcRewMatchname.setInsuredname2("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname3());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname3());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname3("Y");
		}else {
			firCtbcRewMatchname.setInsuredname3("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname4());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname4());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname4("Y");
		}else {
			firCtbcRewMatchname.setInsuredname4("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname5());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname5());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname5("Y");
		}else {
			firCtbcRewMatchname.setInsuredname5("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname6());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname6());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname6("Y");
		}else {
			firCtbcRewMatchname.setInsuredname6("N");
			matchType = "2";
		}
		
		strData180 = StringUtil.trim(data180.getInsuredname7());
		strDataOldPolicy = StringUtil.trim(dataOldPolicy.getInsuredname7());
		if(strData180.trim().equals(strDataOldPolicy)) {
			firCtbcRewMatchname.setInsuredname7("Y");
		}else {
			firCtbcRewMatchname.setInsuredname7("N");
			matchType = "2";
		}
		
		firCtbcRewMatchname.setMatchType(matchType);

		return firCtbcRewMatchname;
	}
	
	private String getRoof(String roofName) {
		if (roofName.contains("平屋頂")) {
			return "50";
		} else if (roofName.contains("瓦屋頂")) {
			return "51";
		} else if (roofName.contains("石棉板屋頂")) {
			return "52";
		} else if (roofName.contains("金屬屋頂")) {
			return "53";
		} else if (roofName.contains("塑膠屋頂")) {
			return "54";
		} else if (roofName.contains("油毛氈屋頂")) {
			return "55";
		} else if (roofName.contains("平、瓦屋頂")) {
			return "56";
		} else if (roofName.contains("石棉瓦屋頂")) {
			return "57";
		} else if (roofName.contains("金屬鐵皮屋頂")) {
			return "58";
		} else if (roofName.contains("烤漆板屋頂")) {
			return "59";
		} else if (roofName.contains("烤漆鋼板屋頂")) {
			return "60";
		} else if (roofName.contains("玻璃纖維屋頂")) {
			return "61";
		} else if (roofName.contains("木屋頂")) {
			return "62";
		} else if (roofName.contains("詳附圖")) {
			return "98";
		} else if (roofName.contains("略")) {
			return "99";
		} else {
			return "X";
		}
	}
	
	private String getRoofName(String roof) {
		if("50".equals(roof)) {
			return "平屋頂";
		}else if("51".equals(roof)) {
			return "瓦屋頂";
		}else if("52".equals(roof)) {
			return "石棉板屋頂";
		}else if("53".equals(roof)) {
			return "金屬屋頂";
		}else if("54".equals(roof)) {
			return "塑膠屋頂";
		}else if("55".equals(roof)) {
			return "油毛氈屋頂";
		}else if("56".equals(roof)) {
			return "平、瓦屋頂";
		}else if("57".equals(roof)) {
			return "石棉瓦屋頂";
		}else if("58".equals(roof)) {
			return "金屬鐵皮屋頂";
		}else if("59".equals(roof)) {
			return "烤漆板屋頂";
		}else if("60".equals(roof)) {
			return "烤漆鋼板屋頂";
		}else if("61".equals(roof)) {
			return "玻璃纖維屋頂";
		}else if("62".equals(roof)) {
			return "木屋頂";
		}else if("98".equals(roof)) {
			return "詳附圖";
		}else if("99".equals(roof)) {
			return "略";
		} else {
			return "X";
		}
		
	}
	
	private String getMaterial(String roofName) {
		if (roofName.contains("鋼骨水泥造")) {
			return "01";
		} else if (roofName.contains("鋼筋水泥造")) {
			return "02";
		} else if (roofName.contains("鋼筋混凝土造") && !roofName.contains("鋼骨鋼筋混凝土造")) {
			return "03";
		} else if (roofName.contains("磚造") && !roofName.contains("加強磚造")) {
			return "04";
		} else if (roofName.contains("磚水泥造")) {
			return "05";
		} else if (roofName.contains("加強磚造")) {
			return "06";
		} else if (roofName.contains("磚、鐵架造")) {
			return "07";
		} else if (roofName.contains("磚、鐵皮造")) {
			return "08";
		} else if (roofName.contains("磚、石棉板造")) {
			return "09";
		} else if (roofName.contains("磚、石造")) {
			return "10";
		} else if (roofName.contains("磚、木造")) {
			return "11";
		} else if (roofName.contains("木造")) {
			return "12";
		} else if (roofName.contains("鐵皮造")) {
			return "13";
		} else if (roofName.contains("鐵架造")) {
			return "14";
		} else if (roofName.contains("鐵架木造")) {
			return "15";
		} else if (roofName.contains("鐵架、石棉板造鐵架、石棉板造")) {
			return "16";
		} else if (roofName.contains("石棉板造")) {
			return "17";
		} else if (roofName.contains("塑膠造")) {
			return "18";
		} else if (roofName.contains("土塊造")) {
			return "19";
		} else if (roofName.contains("露天")) {
			return "20";
		} else if (roofName.contains("石棉瓦造")) {
			return "21";
		} else if (roofName.contains("金屬板造")) {
			return "22";
		} else if (roofName.contains("烤漆板造")) {
			return "23";
		} else if (roofName.contains("烤漆鋼板造")) {
			return "24";
		} else if (roofName.contains("磚、烤漆板造")) {
			return "25";
		} else if (roofName.contains("玻璃纖維造")) {
			return "26";
		} else if (roofName.contains("鋼骨混凝土造")) {
			return "27";
		} else if (roofName.contains("鋼骨鋼筋混凝土造")) {
			return "28";
		} else if (roofName.contains("石造")) {
			return "29";
		} else if (roofName.contains("鋼筋混凝土加強磚")) {
			return "30";
		} else if (roofName.contains("磚、石、木")) {
			return "31";
		} else if (roofName.contains("鋼骨造")) {
			return "32";
		} else if (roofName.contains("土、木造")) {
			return "33";
		} else if (roofName.contains("鋼鐵造")) {
			return "34";
		} else if (roofName.contains("預鑄混凝土造")) {
			return "35";
		} else if (roofName.contains("其他")) {
			return "49";
		} else {
			return "X";
		}
	}
	
	/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 start*/
	private String getMaterialName(String mateial) {
		if("03".equals(mateial)) {
			return "鋼筋混凝土造";
		}else if("04".equals(mateial)) {
			return "磚造";
		}else if("06".equals(mateial)) {
			return "加強磚造";
		}else if("12".equals(mateial)) {
			return "木造";
		}else if("13".equals(mateial)) {
			return "鐵造";
		}else if("28".equals(mateial)) {
			return "鋼骨鋼筋混凝土";
		}else if("29".equals(mateial)) {
			return "石造";
		}else if("30".equals(mateial)) {
			return "鋼筋混凝土加強磚造";
		}else if("33".equals(mateial)) {
			return "土木造";
		}else if("36".equals(mateial)) {
			return "鋼筋混凝土加強空心磚造";
		}else if("39".equals(mateial)) {
			return "鋼骨結構";
		}else if("40".equals(mateial)) {
			return "混擬土造";
		}else if("41".equals(mateial)) {
			return "預力混擬土造";
		}else if("42".equals(mateial)) {
			return "壁式預鑄鋼筋混凝土造";
		}else if("AA".equals(mateial)) {
			return "鋼造";
		}else if("AB".equals(mateial)) {
			return "土造";
		}else if("AC".equals(mateial)) {
			return "土石造";
		}else if("AD".equals(mateial)) {
			return "土磚石混合造";
		}else if("AE".equals(mateial)) {
			return "竹造";
		}else if("AF".equals(mateial)) {
			return "鋁架造";
		}else{
			return "X";
		}
	}
	/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 end*/
	
	/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 start*/
	private String getRoofChinese(String roofcode) {
		if("50".equals(roofcode)) {
			return "水泥平屋頂";
		}else if("51".equals(roofcode)) {
			return "瓦屋頂";
		}else if("53".equals(roofcode)) {
			return "鐵皮屋頂";
		}else if("62".equals(roofcode)) {
			return "木屋頂";
		} else {
			return "X";
		}
	}
	/*mantis：FIR0373，處理人員：BJ085，需求單編號：FIR0373 住火-中信新增建材代號-APS新版代理投保通知調整 end*/
	
	private String getStructureMapping(String structure) {
		String returnValue = "";
		if(this.getMapBuildingLevel().containsKey(structure)) {
			returnValue = this.getMapBuildingLevel().get(structure);
		}else {
			returnValue = "X";
		}
//		if("1".equals(structure)) {
//			//return "A1-特一等";
//			return "特一等";
//		}else if("2".equals(structure)) {
//			//return "A2-特二等";
//			return "特二等";
//		}else if("3".equals(structure)) {
//			//return "B1-頭等";
//			return "頭等";
//		}else if("5".equals(structure)) {
//			//return "B2-二等";
//			return "二等";
//		}else if("6".equals(structure)) {
//			//return "B3-三等";
//			return "三等";
//		}else if("7".equals(structure)) {
//			return "露天";
//		}else {
//			return "X";
//		}
		return returnValue;
	}
	
	private String getBuildingLevelCodeMapping(String buildingLevelCode) {

		if("A1".equals(buildingLevelCode)) {
			//return "A1-特一等";
			return "1";
		}else if("A2".equals(buildingLevelCode)) {
			//return "A2-特二等";
			return "2";
		}else if("B1".equals(buildingLevelCode)) {
			//return "B1-頭等";
			return "3";
		}else if("B2".equals(buildingLevelCode)) {
			//return "B2-二等";
			return "5";
		}else if("B3".equals(buildingLevelCode)) {
			//return "B3-三等";
			return "6";
		}else {
			return "X";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String findNoShowWord(String id) {
		try {
			Map params = new HashMap();
			params.put("datatype", "1");
			params.put("ownerid", id);
			Result result = this.firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewNoshowword> list = (List<FirCtbcRewNoshowword>)result.getResObject();
				FirCtbcRewNoshowword firCtbcRewNoshowword = list.get(0);
				return firCtbcRewNoshowword.getThename();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private void searchNoShowWord(String mystr) {
		for(int i=0;i<mystr.length();i++){
			char ch;
			ch=mystr.charAt(i);//單個地獲取每個字元回
			System.out.print((int)ch);
		}
		System.out.println("");
	}

	public String getTwDate(String date, boolean defaltDate) {
		if(date == null) return "";
		String twDate = "";
		if (defaltDate) {
			twDate = "0010101"; // 預設值為民國1年1月1日
		}
		if (date.matches("\\d{8}")) {
			int year = Integer.parseInt(date.substring(0, 4));
			String twYear = "";
			if (year < 1912) {
				twYear = "001";
			} else {
				twYear = "00" + String.valueOf(year - 1911); //民國年補上0
				twYear = twYear.substring(twYear.length() - 3, twYear.length()); //民國年補上00後取右邊3位
				//			String twYear = "0" + String.valueOf(year - 1911); //民國年補上0
				//			twYear = twYear.substring(0, 3); //民國年補上00後取右邊3位
			}
			twDate = twYear + date.substring(4);
		}

		return twDate;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirCtbcRewNoticeBatchService getFirCtbcRewNoticeBatchService() {
		return firCtbcRewNoticeBatchService;
	}

	public void setFirCtbcRewNoticeBatchService(FirCtbcRewNoticeBatchService firCtbcRewNoticeBatchService) {
		this.firCtbcRewNoticeBatchService = firCtbcRewNoticeBatchService;
	}

	public RewDataInsertService getRewDataInsertService() {
		return rewDataInsertService;
	}

	public void setRewDataInsertService(RewDataInsertService rewDataInsertService) {
		this.rewDataInsertService = rewDataInsertService;
	}

	public FirCtbcRewFix180Service getFirCtbcRewFix180Service() {
		return firCtbcRewFix180Service;
	}

	public void setFirCtbcRewFix180Service(FirCtbcRewFix180Service firCtbcRewFix180Service) {
		this.firCtbcRewFix180Service = firCtbcRewFix180Service;
	}

	public FirCtbcRewDontnoticeService getFirCtbcRewDontnoticeService() {
		return firCtbcRewDontnoticeService;
	}

	public void setFirCtbcRewDontnoticeService(FirCtbcRewDontnoticeService firCtbcRewDontnoticeService) {
		this.firCtbcRewDontnoticeService = firCtbcRewDontnoticeService;
	}

	public FirCtbcRewSnnService getFirCtbcRewSnnService() {
		return firCtbcRewSnnService;
	}

	public void setFirCtbcRewSnnService(FirCtbcRewSnnService firCtbcRewSnnService) {
		this.firCtbcRewSnnService = firCtbcRewSnnService;
	}

	public FirCtbcRewMatchLogService getFirCtbcRewMatchLogService() {
		return firCtbcRewMatchLogService;
	}

	public void setFirCtbcRewMatchLogService(FirCtbcRewMatchLogService firCtbcRewMatchLogService) {
		this.firCtbcRewMatchLogService = firCtbcRewMatchLogService;
	}

	public PrpcaddressService getPrpcaddressService() {
		return prpcaddressService;
	}

	public void setPrpcaddressService(PrpcaddressService prpcaddressService) {
		this.prpcaddressService = prpcaddressService;
	}

	public PrpcinsuredService getPrpcinsuredService() {
		return prpcinsuredService;
	}

	public void setPrpcinsuredService(PrpcinsuredService prpcinsuredService) {
		this.prpcinsuredService = prpcinsuredService;
	}

	public PrpcitemkindService getPrpcitemkindService() {
		return prpcitemkindService;
	}

	public void setPrpcitemkindService(PrpcitemkindService prpcitemkindService) {
		this.prpcitemkindService = prpcitemkindService;
	}

	public PrpcmainpropService getPrpcmainpropService() {
		return prpcmainpropService;
	}

	public void setPrpcmainpropService(PrpcmainpropService prpcmainpropService) {
		this.prpcmainpropService = prpcmainpropService;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}

	public PrpcinsurednatureService getPrpcinsurednatureService() {
		return prpcinsurednatureService;
	}

	public void setPrpcinsurednatureService(PrpcinsurednatureService prpcinsurednatureService) {
		this.prpcinsurednatureService = prpcinsurednatureService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> getMapBuildingLevel() {
		if(this.mapBuildingLevel != null && this.mapBuildingLevel.size() > 0) {
			return mapBuildingLevel;
		}else {
			try {
				this.mapBuildingLevel = new HashMap<String,String> ();
				Map params = new HashMap();
				params.put("codetype", "Structure");
				Result result = this.rfrcodeService.findRfrcodeByParams(params);
				if(result != null && result.getResObject() != null) {
					List<Rfrcode> resultList = (List<Rfrcode>)result.getResObject();
					for(Rfrcode entity : resultList) {
						this.mapBuildingLevel.put(entity.getCodecode(), entity.getCodename());
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return mapBuildingLevel;
	}

	public void setMapBuildingLevel(Map<String, String> mapBuildingLevel) {
		this.mapBuildingLevel = mapBuildingLevel;
	}

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public FirCtbcRewMatchnameService getFirCtbcRewMatchnameService() {
		return firCtbcRewMatchnameService;
	}

	public void setFirCtbcRewMatchnameService(FirCtbcRewMatchnameService firCtbcRewMatchnameService) {
		this.firCtbcRewMatchnameService = firCtbcRewMatchnameService;
	}

	public FirCtbcRewNoshowwordService getFirCtbcRewNoshowwordService() {
		return firCtbcRewNoshowwordService;
	}

	public void setFirCtbcRewNoshowwordService(FirCtbcRewNoshowwordService firCtbcRewNoshowwordService) {
		this.firCtbcRewNoshowwordService = firCtbcRewNoshowwordService;
	}

	public FirCtbcRewMatchLog getUpdateFirCtbcRewMatchLog180() {
		return updateFirCtbcRewMatchLog180;
	}

	public void setUpdateFirCtbcRewMatchLog180(FirCtbcRewMatchLog updateFirCtbcRewMatchLog180) {
		this.updateFirCtbcRewMatchLog180 = updateFirCtbcRewMatchLog180;
	}

}
