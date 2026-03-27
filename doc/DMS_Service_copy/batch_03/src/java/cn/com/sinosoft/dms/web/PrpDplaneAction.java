package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.service.facade.PrpDplaneService;

public class PrpDplaneAction extends Struts2Action{

	private static final long serialVersionUID = 1L;
	 private PrpDplaneService   prpDplaneService;
	 private PrpDplane          prpDplane;
	 private String             licenceNo;
	 private String             editType;
	 private String          chkbox;

	
	 




	public String getChkbox() {
		return chkbox;
	}


	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}


	public PrpDplaneService getPrpDplaneService() {
		return prpDplaneService;
	}


	public void setPrpDplaneService(PrpDplaneService prpDplaneService) {
		this.prpDplaneService = prpDplaneService;
	}


	public PrpDplane getPrpDplane() {
		return prpDplane;
	}


	public void setPrpDplane(PrpDplane prpDplane) {
		this.prpDplane = prpDplane;
	}


	public String getLicenceNo() {
		return licenceNo;
	}


	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}


	public String getEditType() {
		return editType;
	}


	public void setEditType(String editType) {
		this.editType = editType;
	}


	public String prepareQueryPrpDplane() {
	        // �˴��Ȩ�޿����߼�
	        return SUCCESS;
	 }
	 
	  
	    public String prepareInsertPrpDplane(){
	    	return SUCCESS;
	    }
	    
	    public String insertPrpDplane(){
	    	logger.debug("�������µĴ��롿");
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDplaneService.insertPrpDplane(prpDplane,userCode);
	    	return SUCCESS;
	    }

	    public String queryPrpDplane() {
	        logger.debug("����ѯ���ڻ����prpDplane��ʼ��");
	        try {
	            Page page = prpDplaneService.getPrpDplaneList(prpDplane, this.pageNo, this.pageSize);
	            logger.debug("����ѯ�����" + page.getTotalCount() + "��");
	            this.writeJSONData(page, "licenceNo", "planeType", "model", "factoryNo","airlineCname","validStatus");
	            logger.debug("��writeJSONData over��");
	        } catch (Exception e) {
	            e.printStackTrace();
	            this.writeJSONMsg(e.getMessage());
	        }
	        return null;
	    }

	    public String prepareUpdatePrpDplane() {
	        // TODO �༭�����Ȩ��У��
	        logger.debug("���޸�ҳ���ѯ���ڻ�");
	        logger.debug("��" + getLicenceNo() + "��");
	        logger.debug("��" + getEditType() + "��");
	        setPrpDplane(prpDplaneService.findByPrimaryKey(getLicenceNo()));
	        return SUCCESS;
	    }

	    public String updatePrpDplane(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	        prpDplaneService.updatePrpDplane(prpDplane,userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	    
	    public void deletePrpDplane(){
	    	//�Ժ���jspҳ�����Ӷ�ѡ��ʵ������ɾ��
	       List list = new ArrayList();
	    	String[] checkedValues = chkbox.split(",");
	    	for(int i=0;i<checkedValues.length;i++){
	    		String code = checkedValues[i];
		    		 setPrpDplane(prpDplaneService.findByPrimaryKey(code));
//			    	 prpDplaneService.deletePrpDplane(prpDplane);
			    	 list.add(prpDplane);
	    	}
	    	prpDplaneService.delsteAll(list);
	    }
	    
	    public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
//	    	licenceNo = prpDplane.getLicenceNo();
			prpDplane = prpDplaneService.findByPrimaryKey(licenceNo);
//			String validStatus = prpDplane.getValidStatus();
//			if("1".equals(validStatus)){
//					prpDplane.setValidStatus("0");
//					prpDplaneService.updatePrpDplane(prpDplane,userCode);
//			}else{
//					prpDplane.setValidStatus("1");
//					prpDplaneService.updatePrpDplane(prpDplane,userCode);
//			}
		}

}
