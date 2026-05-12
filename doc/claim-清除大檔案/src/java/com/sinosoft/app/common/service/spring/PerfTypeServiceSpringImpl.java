package com.sinosoft.app.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;


import java.util.ArrayList;
import java.util.List;

import com.sinosoft.app.common.model.PerfCode;
import com.sinosoft.app.common.model.PerfType;
import com.sinosoft.app.common.service.facade.PerfCodeService;
import com.sinosoft.app.common.service.facade.PerfTypeService;
import com.sinosoft.app.common.vo.PerfTypeVo;
import com.sinosoft.sys.platform.common.Contacts;



public class PerfTypeServiceSpringImpl extends
		GenericDaoHibernate<PerfType, String> implements PerfTypeService {

	PerfCodeService perfCodeService;
	
	public PerfCodeService getPerfCodeService() {
		return perfCodeService;
	}

	public void setPerfCodeService(PerfCodeService perfCodeService) {
		this.perfCodeService = perfCodeService;
	}

	/**
	 * 查询代码类型
	 * @param PerfType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	@SuppressWarnings("unchecked")
	public Page queryPerfType(QueryRule queryRule, int pageNo, int pageSize)throws Exception{
		Page page = super.find(queryRule, pageNo, pageSize);
		//代码翻译
		List<PerfType> perfTypeList = page.getResult();
		List<PerfTypeVo> perfTypeVoList = new ArrayList<PerfTypeVo>();
		for(PerfType perfType:perfTypeList){
			PerfTypeVo perfTypeVo = new PerfTypeVo();
			perfTypeVo.setCodeType(perfType.getCodeType());
			perfTypeVo.setCodeTypeDesc(perfType.getCodeTypeDesc());
			perfTypeVo.setValidStatus(perfType.getValidStatus());
			if(perfType.getPerfCodes()==null)
			{
				perfTypeVo.setCodeNumber("0");
			}else{
				perfTypeVo.setCodeNumber(perfType.getPerfCodes().size()+"");
			}
			perfTypeVoList.add(perfTypeVo);
			super.evict(perfTypeVo);
		}
		return new Page(0, page.getTotalCount(), pageSize, perfTypeVoList);
	}
	
	/**
	 * 按主键查看代码类型
	 * @param String codeType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public PerfType findByPK(String codeType)throws Exception{
		return super.get(codeType);
	}
	
	/**
	 * 删除代码类型
	 * @param String codeType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void deletePerfType(String codeType)throws Exception{
		try{
			PerfType perfType = findByPK(codeType);
			//逻辑删除
//			perfType.setValidStatus("0");
//			System.out.println("perfType="+perfType.getCodeType());
//			List<PerfCode> perfCodeList = perfType.getPerfCodes();
//			for(PerfCode perfCode:perfCodeList){
//				//System.out.println("perfCode="+perfCode.getId().getCodeCode());
//				perfCode.setValidStatus("0");
//				super.getHibernateTemplate().merge(perfCode);
//			}
			super.delete(perfType);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		
		}
			
	}
	
	/**
	 * 保存代码类型
	 * @param PerfType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void savePerfType(PerfType perfType,String operateType) throws Exception {
		
		PerfType type = findByPK(perfType.getCodeType());
		System.out.println("type="+type);
		if(Contacts.OperateADD.equals(operateType))
		{
			if(type == null){//新增
				perfType.setValidStatus("1");
				super.save(perfType);
			}else{
					throw new BusinessException(perfType.getCodeType()+"已存在！",false);
			}
		}else if(Contacts.OperateUPDATE.equals(operateType))
		{
			if("0".equals(perfType.getValidStatus()))
			{
				List<PerfCode> perfCodeList = type.getPerfCodes();
				for(PerfCode perfCode:perfCodeList){
					perfCode.setValidStatus("0");
					super.getHibernateTemplate().merge(perfCode);
				}
			}
			super.getHibernateTemplate().merge(perfType);
		}
	}

}
