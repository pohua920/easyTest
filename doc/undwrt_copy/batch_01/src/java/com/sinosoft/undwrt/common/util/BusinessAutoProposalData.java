package com.sinosoft.undwrt.common.util;

/**
 * <p>Title: 业务数据</p>
 * <p>Description:根据投保单号得到业务数据 </p>
 * <p>Copyright: Copyright (c) 2005/7</p>
 * <p>Company: Sinosoft</p>
 * @author qinyongli
 * @version 1.0
 */
import java.sql.ResultSet;
import java.util.Date;

import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTitemKind;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * The Class BusinessAutoProposalData.
 */
public class BusinessAutoProposalData extends BusinessData {
	
	/** 屬性日期 now. */
	private DateTime dateNow = new DateTime().current(); //当前时间

	/**
	 * Instantiates a new business auto proposal data.
	 * 
	 * @param iBusinessNo
	 *            the i business no
	 * @param dbManager
	 *            the db manager
	 * @throws Exception
	 *             the exception
	 */
	public BusinessAutoProposalData(String iBusinessNo, DBManager dbManager)
			throws Exception {
		try {
			/****************公共部分**********************/
			double pureRate = 0;
			double sumquantity = 1; //家财险分户数
			String strSQL = "select * from prpTmain where proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rs1 = dbManager.executeQuery(strSQL);
			if (rs1.next()) {
				this.riskCode = rs1.getString("riskcode"); //险种
				this.classCode = rs1.getString("classCode"); //险别
				this.currency = rs1.getString("currency"); //币别信息
				this.sumAmount = rs1.getDouble("sumAmount"); //总保额
				this.discount = rs1.getInt("discount"); //折扣率
				this.disRate1 = rs1.getDouble("disrate1"); //中间成本
				this.disRate = rs1.getInt("disrate"); //经济费和手续费比例
				this.contractno = rs1.getString("contractno"); //合同号
				this.SumPremium = rs1.getDouble("SumPremium"); //总保费
				this.businessNature = rs1.getString("BusinessNature"); //业务性质
				this.policySort = rs1.getString("policySort"); //保单种类
				pureRate = rs1.getDouble("PureRate"); //法三手续费比例(净费比例用)
				sumquantity = rs1.getDouble("sumquantity");
				this.operateDate = rs1.getDate("operateDate"); //签单日期-车险
				this.signDate = rs1.getDate("SignDate"); //签单日期-非车险
				this.startDate = rs1.getDate("StartDate"); //起始日期
				//this.AmountCount = rs1.getDouble("sumAmount"); //总保额
				this.userCode = rs1.getString("handlerCode"); //出单员代码
				this.comCode  = rs1.getString("ComCode");    //出单机构代码
			} else {
				return;//说明业务类型不对，没有找到数据；
			}
			rs1.close();
			String strWhere = "select * from prpTitemCar where proposalno = '"
				+ iBusinessNo + "'";
			ResultSet rsItemCar = dbManager.executeQuery(strWhere);
			if (rsItemCar.next()) {                      
				this.useNatureCode = rsItemCar.getString("useNatureCode");
				this.useYears = rsItemCar.getInt("UseYears"); //使用年限
				this.SeatCount = rsItemCar.getInt("SeatCount");
				this.TonCount = rsItemCar.getDouble("TonCount");
				this.CarKindCode = rsItemCar.getString("CarKindCode");
				this.RegistModelCode = rsItemCar.getString("RegistModelCode");
				this.LicenseNo = rsItemCar.getString("LicenseNo");
			}
			rsItemCar.close();
			
			DBPrpTitemKind dbPrpTitemKind = new DBPrpTitemKind(dbManager);
			strSQL ="proposalno = '"+ iBusinessNo + "'";
			int count =dbPrpTitemKind.getCount(strSQL);
			if(count==1){
				strWhere = "select * from prptitemkind where proposalno = '"
					+ iBusinessNo + "'"; 
				ResultSet rsItemKind = dbManager.executeQuery(strWhere);
				if (rsItemKind.next()) {
					this.KindCode = rsItemKind.getString("KindCode");
					this.AmountCount = rsItemKind.getDouble("amount");  //单一险别保险金额
				}
				rsItemKind.close();
			}
			strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'A' and  proposalno = '"
			        + iBusinessNo + "'";
	       ResultSet rsAmount = dbManager.executeQuery(strSQL);
	       if (rsAmount.next()) {
		        amountA = rsAmount.getDouble("amount");
	          }
	       rsAmount.close();
//	     返回全车盗抢险
			strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'G' and proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountG = dbManager.executeQuery(strSQL);
			if (rsAmountG.next()) {
				amountG = rsAmountG.getDouble("amount");
			}
			rsAmountG.close();
//			返回第三者综合险
			strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'B' and proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountB = dbManager.executeQuery(strSQL);
			if (rsAmountB.next()) {
				amountB = rsAmountB.getDouble("amount");
			}
			rsAmountB.close();
			//返回车上人员责任险/每座
			String strAmountD11 = "SELECT amount FROM PrpTitemkind WHERE KindCode = 'D11' AND proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountD11 = dbManager.executeQuery(strAmountD11);
			if (rsAmountD11.next()) {
				amountD11 = rsAmountD11.getDouble("amount");
			}
			rsAmountD11.close();
			
//			返回车上人员责任险/每座
			String strAmountD12 = "SELECT sum(amount)/sum(quantity) as amount FROM PrpTitemkind WHERE KindCode = 'D12' AND proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountD12 = dbManager.executeQuery(strAmountD12);
			if (rsAmountD12.next()) {
				amountD12 = rsAmountD12.getDouble("amount");  //车上人员责任险(乘客)每座责任限额
                   
			}
			rsAmountD12.close();
//			返回车上货物责任险
			String strAmountD2 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'D2' AND proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountD2 = dbManager.executeQuery(strAmountD2);
			if (rsAmountD2.next()) {
				amountD2 = rsAmountD2.getDouble("amount");
			}
			rsAmountD2.close();
//			返回车上货物责任险
			String strAmountL = "SELECT * FROM PrpTitemkind WHERE KindCode = 'L' AND proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountL = dbManager.executeQuery(strAmountL);
			if (rsAmountL.next()) {
				amountL = rsAmountL.getDouble("amount");
			}
			rsAmountL.close();
			if("H0".equals(this.CarKindCode)){
		    //n吨以上货车保险金额
			  String strAmountH0Upp = "SELECT * FROM PrpTitemkind WHERE KindCode = 'B' AND proposalno = '"
				+ iBusinessNo + "'";
		      ResultSet rsAmountH0Upp = dbManager.executeQuery(strAmountH0Upp);
		      if (rsAmountH0Upp.next()) {
		    	  AmountH0Upp = rsAmountH0Upp.getDouble("amount");
		      }
		      rsAmountH0Upp.close();
		      //n吨以下货车保险金额
		      String strAmountH0Low = "SELECT * FROM PrpTitemkind WHERE KindCode = 'B' AND proposalno = '"
					+ iBusinessNo + "'";
			      ResultSet rsAmountH0Low = dbManager.executeQuery(strAmountH0Low);
			      if (rsAmountH0Low.next()) {
			    	  AmountH0Low = rsAmountH0Low.getDouble("amount");
			      }
			      rsAmountH0Low.close();
			      //n吨以上货车车损险保险金额
			      String strAmountH0UppA = "SELECT * FROM PrpTitemkind WHERE KindCode = 'A' AND proposalno = '"
						+ iBusinessNo + "'";
				      ResultSet rsAmountH0UppA = dbManager.executeQuery(strAmountH0UppA);
				      if (rsAmountH0UppA.next()) {
				    	  AmountH0UppA = rsAmountH0UppA.getDouble("amount");
				      }
				      rsAmountH0UppA.close();
				      //n吨以下货车车损险保险金额
				      String strAmountH0LowA = "SELECT * FROM PrpTitemkind WHERE KindCode = 'A' AND proposalno = '"
							+ iBusinessNo + "'";
					      ResultSet rsAmountH0LowA = dbManager.executeQuery(strAmountH0LowA);
					      if (rsAmountH0LowA.next()) {
					    	  AmountH0LowA = rsAmountH0LowA.getDouble("amount");
					      }
					      rsAmountH0LowA.close();
			  }
//			返回车上货物责任险
			String strAmountNX = "SELECT * FROM PrpTitemkind WHERE KindCode = 'NX' AND proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountNX = dbManager.executeQuery(strAmountNX);
			if (rsAmountNX.next()) {
				AmountNX = rsAmountNX.getDouble("amount");
			}
			rsAmountNX.close();
			String strAmountNY = "SELECT * FROM PrpTitemkind WHERE KindCode = 'NY' AND proposalno = '"
				+ iBusinessNo + "'";
		    ResultSet rsAmountNY = dbManager.executeQuery(strAmountNY);
		    if (rsAmountNY.next()) {
			    AmountNY = rsAmountNY.getDouble("amount");
		    }
		    rsAmountNY.close();
		    
		    String strInsured = "SELECT insuredname FROM PRPTINSURED  WHERE SERIALNO='2' AND PROPOSALNO ='"
				+ iBusinessNo + "'";
		    ResultSet rsInsured = dbManager.executeQuery(strInsured);
		    if (rsInsured.next()) {
		    	this.AppliLinkerName = rsInsured.getString("insuredname");
		    }
		    rsInsured.close();
		} catch (Exception e) {
			throw e;
		}
	}
}

