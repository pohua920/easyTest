package com.sinosoft.claim.common.service.facade;

import java.util.List;
import java.util.Map;
import com.sinosoft.claim.schema.model.PrpLperson;
/**
 * 理赔车险列印数据字节数组获取接口
 * @author 中科软
 */
public interface ClaimPrintService {
	/***
	 * 查勘列印
	 * @param path 查勘列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param registNo 备案号码
	 * @return 列印数据字节数组
	 */
	public byte[] checkBytes(String path, Map<String, Object> emptyHashMap, String registNo);

	/***
	 * 失竊車輛應備明細表列印
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @return
	 */
	public byte[] lossCarPrint(String path, Map<String, Object> emptyHashMap);

	/***
	 * 車險理賠申請所需文件清单列印
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @return
	 */
	public byte[] carClaim(String path, Map<String, Object> emptyHashMap);

	/***
	 * 強制險現金給付審核表
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param prepayNo 预赔号码
	 * @return
	 */
	public byte[] bzPay(String path, Map<String, Object> emptyHashMap, String prepayNo);

	/**
	 * 汽車險賠案查證記錄表
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @return
	 * @throws Exception
	 */
	public byte[] carCase(String path, Map<String, Object> emptyHashMap) throws Exception;

	/***
	 * 汽車險理賠計算書
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param compensateNo 计算书号码
	 * @return
	 */
	public byte[] claimStatement(String path, Map<String, Object> emptyHashMap, String compensateNo);
	/**
	 * 汽車險追償計算書計算書
	 * @param path 列印模板的路徑
	 * @param emptyHashMap Map中有列印模板的路徑加上公司logo地址，列印模板的路徑，計算書號
	 * @param compensateNo 計算書號
	 * @return checkBytes 列印的字節
	 */
	public byte[] claimStatementReplevy(String path, Map<String, Object> emptyHashMap, String compensateNo)  throws Exception;
	/***
	 * 汽車險理賠申請書列印
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param compensateNo 计算书号码
	 * @return
	 */
	public byte[] claimApplication(String path, Map<String, Object> emptyHashMap, String compensateNo);

	/***
	 * 理算報告書列印
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param compensateNo 计算书号码
	 * @return
	 */
	public byte[] compensateReport(String path, Map<String, Object> emptyHashMap, String compensateNo);

	/***
	 * 汽車保險報案記錄（承保理賠資訊）
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param registNo 备案号码
	 * @return
	 */
	public byte[] findRegist(String path, Map<String, Object> emptyHashMap, String registNo);

	/***
	 * 失竊車客戶訪談表列印
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @return
	 */
	public byte[] customerInterview(String path, Map<String, Object> emptyHashMap);
	/***
	 * 事故照片粘貼單列印
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param claimNo 立案号码
	 * @return
	 */
	public byte[] accidentPhotoCard(String path, Map<String, Object> emptyHashMap, String claimNo);
	/***
	 * 列印強制險醫療給付費用明細
	 * @param path 列印报表路径
	 * @param emptyHashMap 报表参数
	 * @param compensateNo 计算书号码
	 * @return
	 */
	public byte[] printPrpLcompelMedical(String path,Map<String, Object> emptyHashMap,List<PrpLperson> prpLpersonList);
}
