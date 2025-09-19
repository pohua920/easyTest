package com.tlg.util;

/**
 * @author 4555
 */
public interface Constants {
	public static final int DELAY_TIME = 60000;
	public static final String dbName = "WWWUNION_TEST";
	public static final String schemaName = "dbo";
	//本機
//	public static final String nfsPath = "D:\\Workspace_DES\\APS\\WebContent\\WEB-INF\\classes";
	//192.168.112.122 and 192.168.2.122
	public static final String nfsPath = "D:/tlgProperty";
	public static final String conifgPath = nfsPath + "/apsSys.properties";
//	public static final String ruleSettingPath = nfsPath + "/rule/ruleSetting.properties";
//	public static final String commonRulePath = nfsPath + "/rule/commonRule.properties";
//	public static final String specificRulePath = nfsPath + "/rule/specificRule.properties";
//	public static final String passPath = nfsPath + "/pass.properties";
//	public static final String serverInfo = nfsPath + "/serverInfo.properties";
//	public static final String premCalculate = nfsPath + "/premCalculate.properties";
//	public static final String ldapUrl = "ldap://10.224.1.6:389";
	public static final String TMP_FOLDER = "D:\\temp";
//	public static final String fileFolderConfigPath = nfsPath + "/filePath.properties";
//	public static final String batchFileFolderConfigPath = nfsPath + "/batchFilePath.properties";
//	public static final String ssoPath = "";///opt/coresystem/sso
//	public static final String ssoConfig = ssoPath + "/SSOConfig.properties";
//	public static final String loginPath = ssoPath + "/login.conf";
//	public static final String krb5Path = ssoPath + "/krb5.conf";
	
	/**上傳檔案目錄*/
	public static final String FILE_UPLOAD_FOLDER = "c:\\fileUpload\\";
	
	/**查無資料*/
	public static final String SEARCH_NO_DATA = "PUB130";
	
	/**資料不存在*/
	public static final String DATA_NOT_EXIST = "PUB131";
	
	/**刪除資料失敗*/
	public static final String DELETE_DATA_FAIL = "PUB111";
	
	/**該資料仍有被其他資料參照，不可刪除*/
	public static final String DELETE_DATA_RELATED_FAIL = "PUB132";
	
	/**資料刪除成功*/
	public static final String DELETE_DATA_SUCCESS = "PUB110";
	
	/**新增資料成功*/
	public static final String SAVE_DATA_SUCCESS = "PUB100";
	
	/**新增資料失敗*/
	public static final String SAVE_DATA_FAIL = "PUB101";
	
	/**資料儲存成功*/
	public static final String UPDATE_DATA_SUCCESS = "PUB120";
	
	/**資料儲存失敗*/
	public static final String UPDATE_DATA_FAIL = "PUB121";
	
	/**排序 */
	public static final String ORDER = "sortType";
	
	/**DESC*/
	public static final String DESC = "DESC";
	
	/**ASC*/
	public static final String ASC = "ASC";
	
	/**排序的欄位*/
	public static final String ORDER_BY_COLUMN_NAME ="sortBy";
	
	/**上傳檔案過大 錯誤訊息*/
	public static final String FILE_UPLOAD_OVER_MAX_SIZE = "PUB122";
	
	/**查無明細資料*/
	public static final String SEARCH_NO_DETAIL_DATA = "PUB133";
	
	/**刪除明細資料失敗*/
	public static final String DELETE_DETAIL_DATA_FAIL = "PUB116";
	
	/**明細資料刪除成功*/
	public static final String DELETE_DETAIL_DATA_SUCCESS = "PUB115";
	
	/**新增明細資料成功*/
	public static final String SAVE_DETAIL_DATA_SUCCESS = "PUB105";
	
	/**新增明細資料失敗*/
	public static final String SAVE_DETAIL_DATA_FAIL = "PUB106";
	
	/**明細資料儲存成功*/
	public static final String UPDATE_DETAIL_DATA_SUCCESS = "PUB125";
	
	/**明細資料儲存失敗*/
	public static final String UPDATE_DETAIL_DATA_FAIL = "PUB126";
	
	
	/**
	 * 程式類的錯誤
	 * COD100~COD119 暫定為程式傳入參數錯誤
	 */
	/**程式傳入參數錯誤*/
	public static final String PARAMETER_ERROR = "COD100";
	
	
	/*
	 * Report 相關參數
	 */
//	public static final String rpRootPath="D:/workspace2011/CMN/CoreSysReportRep";  //報表相關根目錄
	public static final String rptRootPath = "D:/workspace2011/RPT/CoreSysReportRep";  //報表相關根目錄
	public static final String rpTemplatePath = rptRootPath + "/ReportTemplate/"; //報表樣板目錄
	public static final String rpOutputPath = rptRootPath + "/Output/"; //報表輸出目錄

	
	/*
	 * Excel Report 相關參數
	 */
	public static final String excelRptRootPath = "D:/workspace2011/CLM/CoreSysReportRep";  //Excel報表相關根目錄
	public static final String excelRptProtocol = "http";
	public static final String excelRptPort = "8082";// 報表port
	public static final String excelRptOutputPath = "D:/workspace2011/CMN/WebContent/pages/RPT/";// 報表輸出目錄
	public static final String excelRptTemplatePath = excelRptRootPath + "/ExcelRpt/";// 報表樣板目錄
	public static final String excelRptFolder = "";// 組報表url中間目錄
	public static final String cmnFolder = "CMN/";// 報表樣板子目錄
	
	/**
	 * mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
	 */
	public static final String aps061QueryPath = nfsPath + "/RptCoredata.sql";
}