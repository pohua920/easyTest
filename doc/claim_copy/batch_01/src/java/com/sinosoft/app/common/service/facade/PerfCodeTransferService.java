package com.sinosoft.app.common.service.facade;
import com.sinosoft.app.common.model.PerfCodeTransfer;
import com.sinosoft.app.common.model.PerfCodeTransferId;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
/**
 * 基础代码服务接口
 * 
 */
public interface PerfCodeTransferService {

	/**
	 * PerfCodeTransfer表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PerfCodeTransfer findPerfCodeTransferById(PerfCodeTransferId id) throws Exception;

	/**
	 * 查询代码类型
	 * @param QueryRule queryRule, int pageNo, int pageSize
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public Page queryPerfCodeTransfer(QueryRule queryRule, int pageNo, int pageSize)throws Exception;
	/**
	 * 删除代码类型
	 * @param String codeType
	 * @param String codeCode
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void deletePerfCodeTransfer(PerfCodeTransferId id)throws Exception;
	
	/**
	 * 保存代码类型
	 * @param PerfCodeTransfer
	 * @param operateCode
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void savePerfCodeTransfer(PerfCodeTransfer perfCodeTransfer)throws Exception;

	/**
	 * 代码转换方法
	 * @param transferId
	 * @param codeType
	 * @param codeCode
	 * @return
	 * @throws Exception
	 */
	public String getTransferToCode(String transferId,String codeType,String codeCode) throws Exception;
}
