package com.tlg.aps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  */
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps050DetailResultVo;
import com.tlg.aps.vo.Aps050ResultVo;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileChannelType;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.service.ApplicationService;
import com.tlg.msSqlMob.service.CustomerService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaMiNoticeCancle;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;
import com.tlg.xchg.entity.MobileInsSms;
import com.tlg.xchg.service.LiaMiNoticeCancleService;
import com.tlg.xchg.service.LiaMiNoticeUnpaidService;
import com.tlg.xchg.service.MobileInsSmsService;

@SuppressWarnings("serial")
public class APS050Action extends BaseAction {
	// 下拉
	private Map<String, String> dataStatusMap = new LinkedHashMap<String, String>();// 受理檔狀態
	private Map<String, String> formTypeMap = new LinkedHashMap<String, String>();// 要保書狀態/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 */
	
	/* mantis：，處理人員：BJ016，需求單編號：行動裝置險批次號手動下載作業 start */
	private RunFetPolicyService runFetPolicyService;
	private CustomerService customerService;
	private ApplicationService applicationService;
	private MobileInsSmsService mobileInsSmsService;
	private LiaMiNoticeCancleService liaMiNoticeCancleService;
	private LiaMiNoticeUnpaidService liaMiNoticeUnpaidService;
	private FetPolicyService fetPolicyService;
	
	private String rptBatchNo;
	private List<Aps050ResultVo> devResults = new ArrayList<>();
	private Aps050DetailResultVo aps050DetailResultVo;
	private String hdTransactionIds;
	private InputStream fileInputStream;
	private ConfigUtil configUtil;
	
	private InputStream inputStream;
	private String fileName;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		if("query".equals(type)) {
			dataStatusMap.put("", "");
			dataStatusMap.put("INIT", "初始");
			dataStatusMap.put("READY", "可匯入核心中介資料表");
			dataStatusMap.put("FAIL", "匯入核心中介資料表失敗");
			dataStatusMap.put("FINISH", "已匯入核心中介資料表");
			dataStatusMap.put("PROCESS", "執行匯入中");
			/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  START*/
			formTypeMap.put("", "");
			formTypeMap.put("E", "電子");
			formTypeMap.put("N", "紙本");
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢  START*/
			formTypeMap.put("W", "ESTORE");
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢  END*/
			/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  END*/
		}
		
	}
	
	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
			formLoad("query");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("query");
			getPageInfo().setCurrentPage(1);
			/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  start*/
			/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題 START*/
			getPageInfo().getFilter().put("sortBy", "POLICY_NO");
			/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題 END*/
			getPageInfo().getFilter().put("sortType", "DESC");
			/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  end*/
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 重送終止通知書簡訊 */
	@SuppressWarnings("unchecked")
	public String btnSendTerminationNotice() throws Exception {
		try{
			if(this.hdTransactionIds.length() > 0) {
				String[] arrTransactionId = this.hdTransactionIds.split(",");
				String noDataPolicyNo = "";
				Map<String,String> params = new HashMap<String,String>();
				String type = "";
				String serialno = "";
				/**
				 * 20231016:BJ016
				 * 有跟嘉澤確認過，他的發送簡訊程式會用MobileInsSms的OID看LiaMiNoticeCancle或LiaMiNoticeUnpaid有沒有相對應的SERIALNO，
				 * 如果沒有則不會寄送EMAIL。
				 * */
				String policyNo = "";
				Application application = null;
				for(String transactionId : arrTransactionId) {
					params.clear();
					policyNo = "";
					if(transactionId != null && transactionId.length() >= 0) {
						Result result = applicationService.findApplicationByUK(transactionId);
						application = null;
						if(result != null && result.getResObject() != null) {
							application = (Application)result.getResObject();
							policyNo = application.getPolicyNo();
						}
						if(policyNo != null && policyNo.length() >= 0) {
							params.put("policyno", policyNo);
							type = "cancel";
							result = this.liaMiNoticeCancleService.findLiaMiNoticeCancleByParams(params);
							if(result.getResObject() == null) {
								type = "unpaid";
								result = this.liaMiNoticeUnpaidService.findLiaMiNoticeUnpaidByParams(params);
								if(result.getResObject() == null) {
									type = "noData";
								}
							}
							if("noData".equals(type)) {
								noDataPolicyNo += policyNo;
								continue;
							}
							
							if("cancel".equals(type)) {
								List<LiaMiNoticeCancle> searchResult = (List<LiaMiNoticeCancle>)result.getResObject();
								serialno = searchResult.get(0).getSerialno();
								searchResult = null;
							} else if("unpaid".equals(type)) {
								List<LiaMiNoticeUnpaid> searchResult =  (List<LiaMiNoticeUnpaid>)result.getResObject();
								serialno = searchResult.get(0).getSerialno();
								searchResult = null;
							}
							
							params.clear();
							params.put("likeOid", serialno);
							result = this.mobileInsSmsService.findMobileInsSmsByParams(params);
							if(result.getResObject() != null) {
								List<MobileInsSms> searchResult = (List<MobileInsSms>)result.getResObject();
								String originSerialno = "";
								int checkSerialNo = 0;
								String[] arrOid;
								MobileInsSms finalEntity = null;
								for(MobileInsSms entity : searchResult) {
									originSerialno = entity.getOid();
									arrOid = originSerialno.split("-");
									
									if(finalEntity == null) finalEntity = entity;
									
									if(arrOid.length == 2) {
										try {
											if(Integer.parseInt(arrOid[1]) > checkSerialNo) {
												checkSerialNo = Integer.parseInt(arrOid[1]);
												finalEntity = entity;
											}
										}catch(Exception ex) {
											ex.printStackTrace();
										}
									}
								}
								checkSerialNo += 1;
								finalEntity.setOid(serialno + "-" + checkSerialNo);
								finalEntity.setIcreate("system");
								finalEntity.setDcreate(new Date());
								this.mobileInsSmsService.insertMobileInsSms(finalEntity);
							}
						}
					}
				}
			}
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 發送終止通知書簡訊(自取消) */
	public String btnSendTerminationNoticeCancel() throws Exception {
		try{
			if(this.hdTransactionIds.length() > 0) {
				String[] arrTransactionId = this.hdTransactionIds.split(",");
				for(String transactionId : arrTransactionId) {
					if(transactionId != null && transactionId.length() >= 0) {
						Result result = this.runFetPolicyService.doTerminationNoticeCancel(transactionId);
					}
				}
			}
			query();
			setMessage("作業執行成功");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 發送終止通知書簡訊(未繳費) */
	public String btnSendTerminationNoticeUnpaid() throws Exception {
		try{
			if(this.hdTransactionIds.length() > 0) {
				String[] arrTransactionId = this.hdTransactionIds.split(",");
				for(String transactionId : arrTransactionId) {
					if(transactionId != null && transactionId.length() >= 0) {
						Result result = this.runFetPolicyService.doTerminationNoticeUnpaid(transactionId);
					}
				}
			}
			query();
			setMessage("作業執行成功");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnGenExcel() throws Exception {
		int nowPageSize = getPageInfo().getPageSize();
		try{
			formLoad("query");
			getPageInfo().setPageSize(0);//PageSize設為0，查詢出來的資料將不會出現分頁後的結果，會查出所有的資料
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "BATCH_DATE");
			getPageInfo().getFilter().put("sortType", "ASC");
			query();
			if(this.devResults != null && this.devResults.size() > 0) {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
				String[] titleArr = {"收件日","合約編號","要保人姓名","要保人ID","手機門號","方案名稱","保險起日","保險迄日","安達處理狀態",
						"安達處理說明","受理檔狀態","保單號碼","批單號碼","要保書是否完整","門市編號","批改內容"};
				XSSFRow rowTitle = sheet.createRow(0);
				for(int i=0;i<titleArr.length;i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}

				Aps050ResultVo vo = null;
				String chubbReturnStatusDesc = "";
				String dataStatusDesc = "";
				String transactionDesc = "";
				for (int i = 0; i < devResults.size(); i++) {
					vo = devResults.get(i);
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(vo.getBatchDate());
					row.createCell(1).setCellValue(vo.getContractId());
					row.createCell(2).setCellValue(vo.getName());
					row.createCell(3).setCellValue(vo.getCustomerId());
					row.createCell(4).setCellValue(vo.getMsisdn());
					row.createCell(5).setCellValue(vo.getFeatureName());
					row.createCell(6).setCellValue(vo.getStartDate());
					row.createCell(7).setCellValue(vo.getEndDate());
					
					chubbReturnStatusDesc = "";
					if("1".equals(vo.getChubbReturnStatus())) {
						chubbReturnStatusDesc = "成功";
					} else if("2".equals(vo.getChubbReturnStatus())) {
						chubbReturnStatusDesc = "失敗";
					} else if("3".equals(vo.getChubbReturnStatus())) {
						chubbReturnStatusDesc = "不受理";
					}
					row.createCell(8).setCellValue(chubbReturnStatusDesc);
					
					row.createCell(9).setCellValue(vo.getChubbReturnMsg());
					
					dataStatusDesc = "";
					if("INIT".equals(vo.getDataStatus())) {
						dataStatusDesc = "初始";
					} else if("READY".equals(vo.getDataStatus())) {
						dataStatusDesc = "可匯入核心中介資料表";
					} else if("FAIL".equals(vo.getDataStatus())) {
						dataStatusDesc = "匯入核心中介資料表失敗";
					} else if("FINISH".equals(vo.getDataStatus())) {
						dataStatusDesc = "已匯入核心中介資料表";
					} else if("PROCESS".equals(vo.getDataStatus())) {
						dataStatusDesc = "執行匯入中";
					}
					row.createCell(10).setCellValue(dataStatusDesc);
					
					row.createCell(11).setCellValue(vo.getPolicyNo());
					row.createCell(12).setCellValue(vo.getEndorseNo());
					row.createCell(13).setCellValue(vo.getProposalFileCheck());
					row.createCell(14).setCellValue(vo.getStoreId());
					
					transactionDesc = "";
					if("MODIFY".equals(vo.getTransactionType())) {
						transactionDesc = "資料修改";
					} else if("CANCEL".equals(vo.getTransactionType())) {
						transactionDesc = "退保";
					}
					row.createCell(15).setCellValue(transactionDesc);
					
				}
				
				File fileFolder = new File(configUtil.getString("tempFolder"));
				if(!fileFolder.exists()) {
					fileFolder.mkdirs();
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				this.fileName = sdf.format(new Date())+".xlsx";
				String filepath = configUtil.getString("tempFolder") + fileName;
				try (FileOutputStream fileOut = new FileOutputStream(filepath)) {
		            workbook.write(fileOut);
		            workbook.close();
		        }
				
				this.inputStream = new DeleteAfterDownloadFileInputStream(new File(filepath));
			}
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally {
			this.devResults = null;//將查詢出來的結果清空，不然頁面上會將本次查詢結果SHOW出來
			getPageInfo().setPageSize(nowPageSize);//將PageSize設為非0的數字，如果使用者操作頁面其他的查詢功能才不會出現奇怪的結果
		}
		return Action.SUCCESS;
	}
	
	public String btnSendProposalInsufficientNotify() throws Exception {
		try{
			String runResult = "";
			if(this.hdTransactionIds.length() > 0) {
				String[] arrTransactionId = this.hdTransactionIds.split(",");
				Application application = null;
				Result result = null;
				boolean check = false;
				for(String transactionId : arrTransactionId) {
					if(transactionId != null && transactionId.length() >= 0) {
						application = null;
						check = false;
						result = this.applicationService.findApplicationByUK(transactionId);
						if(result != null && result.getResObject() != null) {
							application = (Application)result.getResObject();
						}
						
						if(application != null) {
							result = this.runFetPolicyService.storeProposalInsufficientSms(application);
							if(result != null && result.getResObject() != null) {
								check = (Boolean)result.getResObject();
								if(!check) {
									runResult += result.getMessage().getMessageString() + ";";
								}
							}
						} else {
							runResult += "查無資料(transactionId:"+transactionId+");";
						}
					}
				}
			}
			query();
			if(runResult != null && runResult.length() > 0) {
				setMessage(runResult);
			} else {
				setMessage("作業執行成功");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 連結至查詢頁面 
	 * @throws Exception */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("query");
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS050E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS050E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  start*/
		getPageInfo().getFilter().put("noDeprecateStatus", "Y");
		String batchDateStart = (String)getPageInfo().getFilter().get("batchDateStart");
		String batchDateEnd = (String)getPageInfo().getFilter().get("batchDateEnd");
		if(StringUtils.isNotBlank(batchDateStart) && StringUtils.isBlank(batchDateEnd)) {
			getPageInfo().getFilter().put("batchDateEnd", batchDateStart);
		}
		/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  end*/
		Result result = customerService.selectForAps050(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<Aps050ResultVo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** 連結至批次號手動下載頁面 */
	public String lnkGoDownloadData() throws Exception {
		this.formLoad("downloadData");
		return Action.SUCCESS;	
	}
	
	/** 連結至明細頁面 */
	public String lnkGoDetail() throws Exception {
		try {
			if (null == aps050DetailResultVo || null == aps050DetailResultVo.getTransactionId() || aps050DetailResultVo.getTransactionId().length() <= 0) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
				return Action.INPUT;
			}
			Result result = customerService.selectForAps050Detail(aps050DetailResultVo.getTransactionId());
			if (null == result.getResObject()) {
				setMessage(result.getMessage().toString());
			} else {
				aps050DetailResultVo = (Aps050DetailResultVo) result.getResObject();
				setChannelNameAndEmployeeId(aps050DetailResultVo);/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 */
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 START*/
	private void setChannelNameAndEmployeeId(Aps050DetailResultVo aps050DetailResultVo) {
		String channelType = aps050DetailResultVo.getChannelType();
		EnumMobileChannelType channel = EnumMobileChannelType.fromCode(channelType);
		if(channel != null) {
			aps050DetailResultVo.setChannelTypeName(channel.getValue());
			String code = channel.getCode();
			// 操作人員代碼為employee_id，若是身分證編號且CUSTOMER.CHANNEL_TYPE為 F加盟、 A全虹、E:EBU時，操作人員代碼呈現門市編號。
			if(code.equals("F") || code.equals("A") || code.equals("E") ) {
				aps050DetailResultVo.setEmployeeId(aps050DetailResultVo.getStoreId());
			}
		}
	}
	/** mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 END*/

	
	
	/** APS050A0.jsp 按下下載，下載遠傳保單批單資料 */
	public String btnDownloadData() throws Exception {
		if(this.rptBatchNo == null || this.rptBatchNo.length() <= 0) {
			setMessage("批次號不得為空");
			return Action.INPUT;
		}
		//遠傳佣金檔下載
		Result result = this.runFetPolicyService.getDailyInsuranceDataFromFet(this.rptBatchNo);
		if(result != null) {
			setMessage(result.getMessage().getMessageString());
		}
		return Action.SUCCESS;
	}
	
	public String lnkDownloadProposal() throws Exception {
			
			String transactionId = aps050DetailResultVo.getTransactionId();

	        try{
	        	if(transactionId == null || transactionId.length() <= 0) {
	        		setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
					return Action.INPUT;
	        	}
	        	Result result = applicationService.findApplicationByUK(transactionId);
	        	
	        	if(result == null || result.getResObject() == null) {
	        		setMessage("查無資料");
					return Action.INPUT;
	        	}
	        	Application application = (Application)result.getResObject();
	        	String applicationId = application.getApplicationId();
	        	if(applicationId == null || applicationId.length() <= 0) {
	        		setMessage("查無資料");
					return Action.INPUT;
	        	}
	        	result = customerService.selectForAps050Detail(transactionId);
	        	if(result == null || result.getResObject() == null) {
	        		setMessage("查無資料");
					return Action.INPUT;
	        	}
	        	aps050DetailResultVo = (Aps050DetailResultVo) result.getResObject();
	        	String proposalZipFileName = aps050DetailResultVo.getProposalFileName();
	        	if(proposalZipFileName == null || proposalZipFileName.length() <= 0) {
	        		setMessage("查無資料");
					return Action.INPUT;
	        	}
	        	String proposalFileFolderName = proposalZipFileName.replace(".zip", "");
	        	String strFolder = configUtil.getString("mobRootDirectory") + "\\"  + configUtil.getString("mobProposalFileRootPath")  + "\\" + proposalFileFolderName;
	        	File folder = new File(strFolder);
	        	if(!folder.exists()) {
	        		setMessage("查無資料");
					return Action.INPUT;
	        	}
	        	boolean hasFile = false;
	        	for (File fileEntry : folder.listFiles()) {
	    	        if (!fileEntry.isDirectory()) {
	    	        	logger.info(fileEntry.getName());
	    	        	if(fileEntry.getName().indexOf(applicationId) >= 0) {
	    	        		if(fileEntry.exists()) {
	    	        			hasFile = true;
	    						this.fileInputStream = new FileInputStream(fileEntry);
	    						break;
	    					}else {
	    						setMessage("查無檔案");
	    						return Action.INPUT;
	    					}
	    	        	}
	    	        }
	    	    }
	        	if(!hasFile) {
	        		setMessage("查無檔案");
					return Action.INPUT;
	        	}
			}catch (IOException e) {
			            e.printStackTrace();
			} catch (SystemException se) {
				setMessage(se.getMessage());
			} catch (Exception e) {
				getRequest().setAttribute("exception", e);
				throw e;
			}finally{

			}
			return Action.SUCCESS;
	}
	
	/** APS050E0.jsp 要保書審核 */
	public String btnProposalFileCheck() throws Exception {
		if(this.hdTransactionIds.length() <= 0) {
			setMessage("請選擇項目");
			return Action.INPUT;
		}
		try{
			String[] arrTransactionId = this.hdTransactionIds.split(",");
			String userId = getUserInfo().getUserId();
			for(String transactionId : arrTransactionId) {
				fetPolicyService.proposalFileCheck(transactionId, userId);
			}
			this.hdTransactionIds = "";
			setMessage("作業執行成功");
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			this.formLoad("query");
			this.query();
		}

		return Action.SUCCESS;
	}

	public RunFetPolicyService getRunFetPolicyService() {
		return runFetPolicyService;
	}

	public void setRunFetPolicyService(RunFetPolicyService runFetPolicyService) {
		this.runFetPolicyService = runFetPolicyService;
	}

	public String getRptBatchNo() {
		return rptBatchNo;
	}

	public void setRptBatchNo(String rptBatchNo) {
		this.rptBatchNo = rptBatchNo;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<Aps050ResultVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Aps050ResultVo> devResults) {
		this.devResults = devResults;
	}

	public Aps050DetailResultVo getAps050DetailResultVo() {
		return aps050DetailResultVo;
	}

	public void setAps050DetailResultVo(Aps050DetailResultVo aps050DetailResultVo) {
		this.aps050DetailResultVo = aps050DetailResultVo;
	}

	public MobileInsSmsService getMobileInsSmsService() {
		return mobileInsSmsService;
	}

	public void setMobileInsSmsService(MobileInsSmsService mobileInsSmsService) {
		this.mobileInsSmsService = mobileInsSmsService;
	}

	public Map<String, String> getDataStatusMap() {
		return dataStatusMap;
	}

	public void setDataStatusMap(Map<String, String> dataStatusMap) {
		this.dataStatusMap = dataStatusMap;
	}

	public LiaMiNoticeCancleService getLiaMiNoticeCancleService() {
		return liaMiNoticeCancleService;
	}

	public void setLiaMiNoticeCancleService(LiaMiNoticeCancleService liaMiNoticeCancleService) {
		this.liaMiNoticeCancleService = liaMiNoticeCancleService;
	}

	public LiaMiNoticeUnpaidService getLiaMiNoticeUnpaidService() {
		return liaMiNoticeUnpaidService;
	}

	public void setLiaMiNoticeUnpaidService(LiaMiNoticeUnpaidService liaMiNoticeUnpaidService) {
		this.liaMiNoticeUnpaidService = liaMiNoticeUnpaidService;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public String getHdTransactionIds() {
		return hdTransactionIds;
	}

	public void setHdTransactionIds(String hdTransactionIds) {
		this.hdTransactionIds = hdTransactionIds;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public FetPolicyService getFetPolicyService() {
		return fetPolicyService;
	}

	public void setFetPolicyService(FetPolicyService fetPolicyService) {
		this.fetPolicyService = fetPolicyService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getFormTypeMap() {
		return formTypeMap;
	}

	public void setFormTypeMap(Map<String, String> formTypeMap) {
		this.formTypeMap = formTypeMap;
	}

}


