// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Embeddable
public class PrpQairlineId  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


   
 /** 属性要保号码 */
     private String proposalNo;
   
 /** 属性序號 */
     private Integer seriesNo;
   
     /**
 	 * 类PrpQairlineId的默认构造方法
 	 */
     public PrpQairlineId(){
    	 
     }
     
	/**
	 * 属性proposalNo的getter方法
	 */
	@Column(name="PROPOSALNO")
	public String getProposalNo() {
		return proposalNo;
	}
	/**
	 * 属性proposalNo的setter方法
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	/**
	 * 属性seriesNo的getter方法
	 */
	@Column(name="SERIESNO")
	public Integer getSeriesNo() {
		return seriesNo;
	}
	/**
	 * 属性seriesNo的setter方法
	 */
	public void setSeriesNo(Integer seriesNo) {
		this.seriesNo = seriesNo;
	}
	
	public boolean equals(Object other) {
        if ( (this == other ) ) {return true;}
		 if ( (other == null ) ) {return false;}
		 if ( !(other instanceof PrpQairlineId) ) {return false;}
		 PrpQairlineId castOther = ( PrpQairlineId ) other; 
        
		 return ( (this.getProposalNo()==castOther.getProposalNo()) || ( this.getProposalNo()!=null && castOther.getProposalNo()!=null && this.getProposalNo().equals(castOther.getProposalNo()) ) )
&& ( (this.getSeriesNo()==castOther.getSeriesNo()) || ( this.getSeriesNo()!=null && castOther.getSeriesNo()!=null && this.getSeriesNo().equals(castOther.getSeriesNo()) ) );
  }
  
  public int hashCode() {
        int result = 17;
        
        result = 37 * result + ( getProposalNo() == null ? 0 : this.getProposalNo().hashCode() );
        result = 37 * result + ( getSeriesNo() == null ? 0 : this.getSeriesNo().hashCode() );
        return result;
  }   
	
	
}


