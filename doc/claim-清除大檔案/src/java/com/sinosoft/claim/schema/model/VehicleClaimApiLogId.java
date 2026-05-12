// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 * POJO类VehicleClaimApiLogId
 */
@Embeddable
public class VehicleClaimApiLogId  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
     private String logId;//記錄ID

	/**
	 * 类PrpTfeeId的默认构造方法
	 */
    public VehicleClaimApiLogId() {
    }

   
    /**
     * 属性投保单号码的getter方法
     */ 

    @Column(name="LOGID")
    public String getLogId() {
 		return logId;
 	}


 	public void setLogId(String logId) {
 		this.logId = logId;
 	}


   public boolean equals(Object other) {
         if ( (this == other ) ) {return true;}
		 if ( (other == null ) ) {return false;}
		 if ( !(other instanceof VehicleClaimApiLogId) ) {return false;}
		 VehicleClaimApiLogId castOther = ( VehicleClaimApiLogId ) other; 
         
		 return ( (this.getLogId()==castOther.getLogId()) || ( this.getLogId()!=null && castOther.getLogId()!=null && this.getLogId().equals(castOther.getLogId())  
				 ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLogId() == null ? 0 : this.getLogId().hashCode() );
         return result;
   }   


}


