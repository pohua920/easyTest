package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.print.util.AcciPrintViewHelper;
import com.sinosoft.claim.print.vo.AcciPrintObject;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 伤害险打印类
 * @author 中科软
 */
public class AcciPrintAction extends Struts2Action  implements Preparable{
	private static final long serialVersionUID = 1L;
	/** List类型数据源 */
	private List<Object> resultList = new ArrayList<Object>(); // 相当于dataSource,集合属性，查询的结果集[如果想使用list,则connection一定为null，否则没用]
	/** 数据源连接 */
	private Connection connection = null;
	/** 输出文档格式 */
	private String format = "PDF";
	/** 传递的参数 */
	private Map<String, Object> param = new HashMap<String, Object>();
	/** 列印業務號 */
	private String businessNo;
	/** 伤害险列印帮助类   */
	private AcciPrintViewHelper acciPrintViewHelper;

	public void prepare() throws Exception {
		param.put("IMGPATH", super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/image/logo.png");
		param.put("SUBREPORT_DIR", super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/Acci/");
	}
	/***
	 * 保險金給付通知書
	 * @return
//	 * @throws Exception
	 */
	public String printPaymentNotice() throws Exception {
		try {
			AcciPrintObject acciPrintObject = acciPrintViewHelper.printPaymentNotice(param, businessNo);
			resultList.add(acciPrintObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 台壽保產物保險公證公司委託申請單
	 * @return
	 * @throws Exception
	 */
	public String printCommissioned() throws Exception {
		try {
			acciPrintViewHelper.printCommissioned(param, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 賠款同意書暨領款收據
	 * @return
	 * @throws Exception
	 */
	public String printReceipt() throws Exception {
		try {
			AcciPrintObject acciPrintObject = acciPrintViewHelper.printReceipt(param, businessNo);
			resultList.add(acciPrintObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 債權讓與契約暨通知書
	 * @return
	 * @throws Exception
	 */
	public String printContract() throws Exception {
		try {
			acciPrintViewHelper.printContract(param, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 台壽保產物保險股份有限公司新種險理賠查案單
	 * @return
	 * @throws Exception
	 */
	public String printInvestigative() throws Exception {
		try {
			acciPrintViewHelper.printInvestigative(param, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 匯款同意書(賠款同意書、代位求償權承諾書)
	 * @return
	 * @throws Exception
	 */
	public String printRemittance() throws Exception {
		try {
			AcciPrintObject acciPrintObject = acciPrintViewHelper.printRemittance(param, businessNo);
			resultList.add(acciPrintObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 調查報告
	 * @return
	 * @throws Exception
	 */
	public String printReport() throws Exception {
		try {
			acciPrintViewHelper.printReport(param, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的備案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 撤銷申請理賠同意書
	 * @return
	 * @throws Exception
	 */
	public String printRevocation() throws Exception {
		try {
			acciPrintViewHelper.printRevocation(param, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 理賠申請書
	 * @return
	 * @throws Exception
	 */
	public String printClaimApplication() throws Exception {
		try {
			acciPrintViewHelper.printClaimApplication(param, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 補件通知函
	 * @return
	 * @throws Exception
	 */
	public String printNotification() throws Exception {
		try {
			AcciPrintObject acciPrintObject = acciPrintViewHelper.printNotification(param, businessNo);
			resultList.add(acciPrintObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 意健险计算书
	 * @return
	 * @throws Exception
	 */
	public String printCompensate() throws Exception {
		try {
			AcciPrintObject acciPrintObject = acciPrintViewHelper.printCompensate(param, businessNo);
			resultList.add(acciPrintObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的計算書號碼！");
		}
		return SUCCESS;
	}

	public List<Object> getResultList() {
		if (CommonUtils.isEmpty(resultList)) {
			resultList.add(new JREmptyDataSource());
		}
		return resultList;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

	public List<Object> getList() {
		return resultList;
	}

	public void setList(List<Object> list) {
		this.resultList = list;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public AcciPrintViewHelper getAcciPrintViewHelper() {
		return acciPrintViewHelper;
	}
	public void setAcciPrintViewHelper(AcciPrintViewHelper acciPrintViewHelper) {
		this.acciPrintViewHelper = acciPrintViewHelper;
	}

}
