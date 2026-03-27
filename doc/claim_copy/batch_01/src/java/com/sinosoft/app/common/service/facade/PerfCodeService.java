package com.sinosoft.app.common.service.facade;

import com.sinosoft.app.common.model.PerfCode;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

/**
 * 基础代码服务接口
 * 
 */
public interface PerfCodeService {

	/**
	 * PerfCode表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @return 代码列表
	 */
	public List<PerfCode> findPerfCodeList(String codeType) throws Exception;

	/**
	 * PerfCode表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PerfCode findPerfCodeById(String codeType, String codeCode) throws Exception;

	/**
     * 根据代码类型获取页面Select初始化列表
     * @param isConnect 是否需要代码和名称连接显示
     * @param codeType 列表类型
     * @param isBlankLine 是否有空白行
     * @return String 列表值
     */
	public String getSelectValue(boolean isConnect,String codeType, boolean isBlankLine) throws Exception;
	
	/**
     * List转换String
     * @param isConnect 是否需要代码和名称连接显示
     * @param codeType 列表类型
     * @param isBlankLine 是否有空白行
     * @param order 列表类型
     * @return String 列表值
     */
	public String listToString(boolean isConnect,List<PerfCode> codes, boolean isBlankLine) throws Exception;
	
	/**
	 * 查询代码类型
	 * @param QueryRule queryRule, int pageNo, int pageSize
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public Page queryPerfCode(QueryRule queryRule, int pageNo, int pageSize)throws Exception;
	/**
	 * 删除代码类型
	 * @param String codeType
	 * @param String codeCode
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void deletePerfCode(String codeType, String codeCode)throws Exception;
	
	/**
	 * 保存代码类型
	 * @param PerfCode
	 * @param operateCode
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void savePerfCode(PerfCode perfCode,String operateType)throws Exception;
	
	/**
	 * 查询代码数据，不通过缓存
	 * @param queryRule
	 * @throws Exception
	 */
	public List<PerfCode> findPerfCodeByRule(QueryRule queryRule)throws Exception;
	
	public void updatePerfCode(PerfCode perfCode) throws Exception;
	/**
	 * 查询区域与机构对应信息 机构班子成员管理专用
	 * @param comCode二级机构编码
	 * */
	public PerfCode findPerfCodeByComcode(String comCode) throws Exception;
}
