/************************************************************************
 * Description: 代码对照类
 * Author     : 
 * CreateDate : 2011-09-02
 * UpdateLog  : Name           Date         Reason/Content
 *          ------------------------------------------------------------
 *
 ************************************************************************/
package com.sinosoft.app.common.service.spring;

import com.sinosoft.app.common.model.PerfCodeTransfer;
import com.sinosoft.app.common.model.PerfCodeTransferId;
import com.sinosoft.app.common.service.facade.PerfCodeTransferService;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
 
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;
 
import java.util.List;
 

@SuppressWarnings("unchecked")
public class PerfCodeTransferServiceSpringImpl extends GenericDaoHibernate<PerfCodeTransfer, String> implements PerfCodeTransferService {
	/**
	 * 初始缓存实例
	 */
	private static CacheService cacheManager = CacheManager.getInstance("perfCodeTransfer");


	/**
	 * PerfCodeTransfer表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PerfCodeTransfer findPerfCodeTransferById(PerfCodeTransferId id) throws Exception {
		
		PerfCodeTransfer perfCodeTransfer = super.get(PerfCodeTransfer.class, id);
		return perfCodeTransfer;
	}
	
	/**
	 * 查询代码类型
	 * @param PerfType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	@SuppressWarnings("unchecked")
	public Page queryPerfCodeTransfer(QueryRule queryRule, int pageNo, int pageSize)throws Exception{	
		Page page = super.find(queryRule, pageNo, pageSize);
		//代码翻译
		List<PerfCodeTransfer> perfCodeTransfers = page.getResult();
		return new Page(0, page.getTotalCount(), pageSize, perfCodeTransfers);
	}
	
	/**
	 * 删除代码类型
	 * @param PerfCodeTransferId id
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void deletePerfCodeTransfer(PerfCodeTransferId id)throws Exception{
		PerfCodeTransfer perfCodeTransfer = findPerfCodeTransferById(id);
		//物理删除
		//perfCodeTransfer.setValidStatus("0");
		super.delete(perfCodeTransfer);
	}
	
	/**
	 * 保存代码类型
	 * @param perfCodeTransfer
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void savePerfCodeTransfer(PerfCodeTransfer perfCodeTransfer) throws Exception {
		PerfCodeTransfer type = findPerfCodeTransferById(perfCodeTransfer.getId());
		System.out.println("type="+type);
		if(type == null){//新增
			perfCodeTransfer.setValidStatus("1");
			super.save(perfCodeTransfer);
		}else{//修改
			super.getHibernateTemplate().merge(perfCodeTransfer);
		}
	}
	
	/**
	 * 代码转换方法
	 * @param transferId
	 * @param codeType
	 * @param codeCode
	 * @return
	 * @throws Exception
	 */
	public String getTransferToCode(String transferId,String codeType,String codeCode) throws Exception {
		String key = cacheManager.generateCacheKey("translateCode", transferId,codeType, codeCode);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (String) result;
		}
		String hql = "select a from PerfCodeTransfer a where transferId=? and codeType=? and codeCode=?";
		List<PerfCodeTransfer> list = super.findByHql(hql, transferId,codeType,codeCode);
		if(list!=null&&list.size()>0){
			String codeName = list.get(0).getToCode();
			cacheManager.putCache(key, codeName);
			return codeName;
		}else{
			throw new BusinessException(transferId+",類型："+codeType+",代碼："+codeCode+"沒有配置!",false);
		}
	}
}
