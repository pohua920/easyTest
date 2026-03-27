/************************************************************************
 * Description: 理赔系统代码获取接口类
 * Author     : 中科软
 * CreateDate : 2013-03-02
 * UpdateLog  : Name           Date         Reason/Content
 *          ------------------------------------------------------------
 *
 ************************************************************************/
package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.claim.vo.Code;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDcurrency;
import com.sinosoft.claim.schema.model.PrpDexch;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.UtiUserGrade;

/**
 * 代码翻译服务
 */
public interface CodeService {

	/**
	 * 翻译代码<br>
	 * 支持的代码类型有：<br>
	 * 
	 * <pre>
	 * UserCode 员工代码
	 * ComCode  机构代码
	 * </pre>
	 * 
	 * 例如以下代码，查询性别代码为1的中文名称
	 * 
	 * <pre>
	 * String value = codeService.translateCode(&quot;SexCode&quot;, &quot;1&quot;, &quot;DAA&quot;, &quot;C&quot;);
	 * </pre>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public String translateCode(String codeType, String codeCode, String riskCode, String language);
	
	/**
	 * 翻译限额代码
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public String translateLimitType(String typeCode, boolean isChinese) throws Exception;

	/**
	 * 翻译代码
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public String translateCode(String codeType, String codeCode, String language);

	/**
	 * 翻译机构代码
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public String translateComCode(String codeCode, boolean isChinese);

	/**
	 * 翻译员工代码
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public String translateUserCode(String codeCode, boolean isChinese);

	/**
	 * 翻译险种代码
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public String translateRiskCode(String riskCode, boolean isChinese) throws Exception;

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * 例如以下代码，查询性别代码为1的中文名称
	 * 
	 * <pre>
	 * Map&lt;String, String&gt; codes = codeService.listCodes(&quot;SexCode&quot;, &quot;DAA&quot;, &quot;C&quot;);
	 * </pre>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public Map<String, String> listCodes(String codeType, String riskCode, String language);

	/**
	 * 代码服务
	 * @param codeType 代码类型
	 * @param riskCode 险种
	 * @param language 语种
	 * @param otherCondition 其它条件,实体别名为a,例如 a.id.codecode in('01','02','03')
	 * @return
	 */
	public Map<String, String> listCodes(String codeType, String riskCode, String language, String otherCondition);

	/**
	 * 代码服务
	 * @param codeType 代码类型
	 * @param riskCode 险种
	 * @param language 语种
	 * @param otherCondition 其它条件,实体别名为a,例如 a.id.codecode in('01','02','03')
	 * @return
	 */
	public Map<String, String> listCodesBySql(String codeType, String riskCode, String language);

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * 例如以下代码，查询性别代码为1的中文名称
	 * 
	 * <pre>
	 * List&lt;Code&gt; codes = codeService.listCodeList(&quot;SexCode&quot;, &quot;DAA&quot;, &quot;C&quot;);
	 * </pre>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	public List<Code> listCodeList(String codeType, String riskCode, String language);

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * 例如以下代码，查询性别代码为1的中文名称
	 * 
	 * <pre>
	 * List&lt;Code&gt; codes = codeService.listCodeList(&quot;SexCode&quot;, &quot;DAA&quot;, &quot;C&quot;, &quot;asc&quot;);
	 * </pre>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @param order 排序(asc:升序/desc:降序)
	 * @return 代码名称
	 */
	public List<Code> listOrderCodeList(String codeType, String riskCode, String language, String order);

	/**
	 * 代码选择服务<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @param order 排序(asc:升序/desc:降序)
	 * @param matches 匹配字符串
	 * @param typeParam 过滤查询参数
	 * @return 代码List
	 */
	public Page listCodeSelect(String codeType, String riskCode, String language, String matches, int pageNo, int pageSize, String userCode, String typeParam, String extraCond);

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, boolean isBlankLine);

	// add by hanyuanqiang 团队长管理增加方法 2011-07-12 begin
	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param isConnect 是否需要连接
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @return String 列表值
	 */
	public String getSelectValue(boolean isConnect, String codeType, boolean isBlankLine);

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param isConnect 是否需要连接
	 * @param codeType 列表类型
	 * @param riskCode 险种
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(boolean isConnect, String codeType, String riskCode, String language, String order, boolean isBlankLine);

	// add by hanyuanqiang 团队长管理增加方法 2011-07-12 end

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param isFieldSeparator 是否增加字段分隔符
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, boolean isBlankLine, boolean isFieldSeparator);

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, String language, String order, boolean isBlankLine);

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param riskCode 险种
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, String riskCode, String language, String order, boolean isBlankLine);

	// add by zhangliang 机构获取 2011-08-04 begin
	/**
	 * 根据代码类型获取页面Select初始化列表SQL
	 * @param codeType 列表类型
	 * @param riskCode 险种
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValueSql(String codeType, String riskCode, String language, String order, boolean isBlankLine);

	/**
	 * List转换String
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String listToString(List<Code> codes, boolean isBlankLine);

	/**
	 * 验证sql条件是否正确
	 * @author 中科软
	 * @param tableName 表名
	 * @param conditionSql sql语句
	 * @return
	 * @throws Exception
	 */
	public String checkConditionSql(String tableName, String conditionSql) throws Exception;

	/**
	 * 根据业务类型及险种查询业务代码
	 * @param codetype：业务类型
	 * @param riskcode：险种代码
	 * @return PrpDcodeDto 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<PrpDcode> getCodeType(String codetype, String riskcode) throws Exception;

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateProductCode(String string);

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCodetoConfigCode(String riskCode);

	/**
	 * 根据报案号码查询对应的赔案号码
	 * @param currencyCode String
	 * @param isSearchClaimNo boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateBusinessCode(String businessCode, boolean isSearchClaimNo);

	/**
	 * 根据险种，险别代码得到险别名称
	 * @param riskCode 险别
	 * @param kindCode 险种
	 * @param isChinese 是否中文名称
	 * @return String 返回中午或者英文名称
	 * @throws Exception
	 */
	public String translateKindCode(String riskCode, String kindCode, boolean isChinese);

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCodetoRiskType(String riskCode);

	/**
	 * 获得PrpDcode的集合
	 * @param conditions 查询条件
	 * @return 查询的结果集
	 * @throws Exception
	 */
	public List<PrpDcode> findPrpDcodeByConditions(String conditions);

	/**
	 * 根据币别得到币别名称
	 * @param currencyCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCurrencyCode(String currencyCode, boolean isChinese);

	/**
	 * 得到代理人名称
	 */
	public String translateAgentName(String agentCode);

	/**
	 * 根据代码类型，代码查询代码名称
	 * @param codeType String
	 * @param codeCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCodeCode(String codeType, String codeCode, boolean isChinese);

	/**
	 * 根据车辆种类代码和险类查询
	 * @param codetype：业务类型
	 * @param classcode：险类代码
	 * @return PrpDcodeDto 代码查询
	 */
	public List<PrpDcode> getCodeTypeCarKind(String codetype, String classCode);

	public String translateRiskCode(String riskCode, String language) throws Exception;

	/**
	 * 根据客户代码查询客户类别
	 * @param customerCode：客户代码
	 * @return 客户类别
	 * @throws Exception
	 */
	public String getCustomerType(String customerCode) throws Exception;

	/**
	 * 查询本报案的相关车牌号码的列表
	 * @param registNo 报案号码
	 * @return List 代码查询
	 * @throws Exception
	 */
	public List<PrpLthirdParty> getLicenseNoList(String registNo) throws Exception;

	/**
	 * 返回货币名称，代码列表
	 * @return List<PrpDcurrency> 代码查询
	 * @throws Exception
	 */
	public List<PrpDcurrency> getCurrencyList() throws Exception;

	/**
	 * 查询PrpdLimit表，赔偿限额专用转换
	 * @author 中科软
	 * @param riskCode 险种
	 * @param limitCode 限额代码
	 * @param isChinese 是否中文
	 * @return
	 * @throws Exception
	 */
	public String translateLimit(String riskCode, String limitCode, boolean isChinese) throws Exception;

	/**
	 * 查询免赔条件
	 * @param Collection：查询代码
	 * @throws Exception
	 */
	public List<PrpDcode> getDeductCondition(String riskCode) throws Exception;

	/**
	 * 根据险种代码得到险类的代码
	 * @param riskCode 险种
	 * @return 险类
	 * @throws Exception
	 */
	public String translateClassCodeByRiskCode(String riskCode) throws Exception;

	/**
	 * 关联报案，一个报案号对应多个立案号
	 * @param businessCode 报案号码或者立案号码
	 * @param isSearchClaimNo 是查询立案号码，还是报案号码
	 * @return 立案号或者报案号
	 */
	public String[] translateBusinessCodes(String businessCode, boolean isSearchClaimNo) throws Exception;

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateRiskCodetoInnerCode(String riskCode) throws Exception;

	/**
	 * 报案号生成规则调整 规则：机构设置除总公司外，其他取省分机构
	 * @param comCode 机构代码
	 * @return
	 */
	public String getRegistComCode(String comCode) throws Exception;

	/**
	 * 根据子险种代码，险种得到子险种名称
	 * @param userCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCode(String riskCode, String kindCode, boolean isChinese) throws Exception;

	/**
	 * 查询代码
	 * @param List：查询代码
	 * @throws Exception
	 */
	public List<PrpDcode> findByConditions(String codeType, String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 查询接口:查询某险种,某部门(可选,null为险种配置属性,非null为业务配置属 性),配置代码为configCode的配置项
	 * @param comCode String 部门代码,如果为null,则说明为险种配置属性,如果 为 代码
	 *            值,则说明是业务配置属性.业务配置代码查询时,对部门代码采取上溯 处理,找最近一级部门.
	 * @param riskCode String 险种代码,这个是必要给的
	 * @param configCode String 配置项代码.
	 * @throws Exception 查询异常
	 */
	public PrpDriskConfig riskConfigQuery(String comCode, String riskCode, String configCode) throws Exception;

	/**
	 * 根据机构代码查询机构级别
	 * @param comCode：机构代码
	 * @return 机构级别
	 * @throws Exception
	 */
	public String getComLevel(String comCode) throws Exception;

	/**
	 * 查询PrpdLimit表，赔偿限额专用转换
	 * @author 中科软
	 * @param riskCode 险种
	 * @param limitCode 限额代码
	 * @param isChinese 是否中文
	 * @return
	 * @throws Exception
	 */
	public String translateLimitQuery(String riskCode, String limitCode, boolean isChinese) throws Exception;

	/**
	 * 根据险种，险别代码得到计入总保额标志
	 * @param riskCode String
	 * @param kindCode String
	 * @throws Exception
	 * @return String
	 */
	public String translateCalculateFlag(String riskCode, String kindCode) throws Exception;

	/**
	 * 根据客户代码得到客户姓名
	 * @param agentCode 客户代码
	 */
	public String translateCustomerCName(String customerCode) throws Exception;

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String getRiskCodebyRiskType(String riskType) throws Exception;

	/**
	 * 查询接口:查询某险种,某部门(可选,null为险种配置属性,非null为业务配置属 性),配置代码为configCode的配置项
	 * @param comCode String 部门代码,如果为null,则说明为险种配置属性,如果 为 代码
	 *            值,则说明是业务配置属性.业务配置代码查询时,对部门代码采取上溯 处理,找最近一级部门.
	 * @param riskCode String 险种代码,这个是必要给的
	 * @param configCode String 配置项代码.
	 * @throws Exception 查询异常
	 */
	public PrpDriskConfig queryRiskConfig(String comCode, String riskCode, String configCode) throws Exception;

	/**
	 * 根据主键获得PrpDcompany
	 * @param comcode
	 * @return
	 * @throws Exception
	 */
	public PrpDcompany findPrpDcompanyByPrimaryKey(String comCode) throws Exception;

	/**
	 * 通过一次查询获得某个用户的UtiUserGrade所有结果集
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public List<UtiUserGrade> findUtiUserGradeListByUserCode(String userCode) throws Exception;

	/**
	 * 获得某个用户所有分配的机构
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public List<PrpDcompany> findUserGradeCompanyListByUserCode(String userCode) throws Exception;
	/**
	 * 查询支付币别的内容集
	 * @return
	 */
	public Map<String,String> findPayCurrencyMap();
	/**
	 * 查询支付币别的内容集
	 * @return
	 */
	public Map<String,String> findPayCurrencyMap(boolean isChinese);
	/***
	 * 获取指定本位币 本日的汇率
	 * @param exchDate 日期 （ 为空则默认当日 ）
	 * @param baseCurrency 本位币
	 * @return
	 */
	public List<PrpDexch> findBasePrpDexch(Date exchDate, String baseCurrency);
	/***
	 * 获取指定目标币别 本日的汇率
	 * @param exchDate 日期 （ 为空则默认当日 ）
	 * @param baseCurrency 目标币别
	 * @return
	 */
	public List<PrpDexch> findExchPrpDexch(Date exchDate, String exchCurrency);
	/**
	 * 根据行业类别，查询出一级行业和二级行业。
	 * @param jobCode
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public PrpDcode[] translateJobCode(String jobCode,String riskCode) throws Exception;
	/**
	 * 获取prpCitemKind的itemName的值
	 * @param prpCitemKind
	 * @return
	 */
	public String getItemName(PrpCitemKind prpCitemKind) throws Exception;
	/**
	 * 获取prpCitemKind的itemName的值
	 * @param prpCitemKind
	 * @return
	 */
	public String getItemCode(PrpCitemKind prpCitemKind) throws Exception;
	
	/***
	 * 取得車體險和責任險險種
	 * @param type  大於0取車體，否則取責任
	 * @return
	 */
	public List<String> getResponKindCode(int type);
	
	/** mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程  start**/
	public List<PrpDcode> getNewCodeCode(String codetype, String codeCode) throws Exception;
	/** mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程  end**/

	/**mantis：CLM0296 ，處理人員：DP0713，需求單編號：新核心-調整醫療給付費用明細費用放寬卡控限額 START**/
	public List<PrpDcode> getNewCodeType(String codetype) throws Exception ;

}
