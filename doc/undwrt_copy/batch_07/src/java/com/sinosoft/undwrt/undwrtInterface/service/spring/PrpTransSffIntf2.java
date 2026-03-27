package com.sinosoft.undwrt.undwrtInterface.service.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.sinosoft.function.insutil.ui.control.action.DateUtil;
import com.sinosoft.prpall.bl.action.custom.BLPrpCCommission;
import com.sinosoft.prpall.bl.action.custom.BLPrpCCommissionDetail;
import com.sinosoft.prpall.bl.action.custom.BLPrpDcompany;
import com.sinosoft.prpall.bl.action.custom.BLPrpPCommission;
import com.sinosoft.prpall.bl.action.custom.BLPrpPCommissionDetail;
import com.sinosoft.prpall.bl.action.custom.BLPrpQCommissionDetail;
import com.sinosoft.prpall.bl.action.custom.BLPrpQitemCar;
import com.sinosoft.prpall.bl.action.custom.BLPrpQitemKind;
import com.sinosoft.prpall.bl.action.custom.BLPrpQplan;
import com.sinosoft.prpall.bl.action.custom.BLPrpTCommission;
import com.sinosoft.prpall.bl.action.custom.BLPrpTCommissionDetail;
import com.sinosoft.prpall.blsvr.cb.BLPrpCcharge;
import com.sinosoft.prpall.blsvr.cb.BLPrpCcoins;
import com.sinosoft.prpall.blsvr.cb.BLPrpCcoinsDetail;
import com.sinosoft.prpall.blsvr.cb.BLPrpCitemCar;
import com.sinosoft.prpall.blsvr.cb.BLPrpCitemKind;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmainCovernote;
import com.sinosoft.prpall.blsvr.cb.BLPrpCplan;
import com.sinosoft.prpall.blsvr.cb.BLPrpCproject;
import com.sinosoft.prpall.blsvr.pg.BLPrpPcharge;
import com.sinosoft.prpall.blsvr.pg.BLPrpPcoinsDetail;
import com.sinosoft.prpall.blsvr.pg.BLPrpPcost;
import com.sinosoft.prpall.blsvr.pg.BLPrpPfee;
import com.sinosoft.prpall.blsvr.pg.BLPrpPitemCar;
import com.sinosoft.prpall.blsvr.pg.BLPrpPitemKind;
import com.sinosoft.prpall.blsvr.tb.BLPrpTcarshipTax;
import com.sinosoft.prpall.blsvr.tb.BLPrpTcoins;
import com.sinosoft.prpall.blsvr.tb.BLPrpTcoinsDetail;
import com.sinosoft.prpall.blsvr.tb.BLPrpTitemCar;
import com.sinosoft.prpall.blsvr.tb.BLPrpTitemKind;
import com.sinosoft.prpall.blsvr.tb.BLPrpTplan;
import com.sinosoft.prpall.db.dtofactory.domain.DBPrpCmain;
import com.sinosoft.prpall.db.dtofactory.domain.DBPrpQmain;
import com.sinosoft.prpall.db.dtofactory.domain.DBPrpTmain;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCPmain;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCitemCarExt;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCmainCovernote;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCmainSub;
import com.sinosoft.prpall.dbsvr.pg.DBPrpPAccount;
import com.sinosoft.prpall.dbsvr.pg.DBPrpPhead;
import com.sinosoft.prpall.dbsvr.pg.DBPrpPheadCovernote;
import com.sinosoft.prpall.dbsvr.pg.DBPrpPmainCovernote;
import com.sinosoft.prpall.dbsvr.tb.DBPrpTitemCarExt;
import com.sinosoft.prpall.schema.PrpCcoinsDetailSchema;
import com.sinosoft.prpall.schema.PrpCmainSubSchema;
import com.sinosoft.prpall.schema.PrpPcoinsDetailSchema;
import com.sinosoft.prpall.schema.PrpTcoinsDetailSchema;
import com.sinosoft.reins.utility.dto.domain.PrpDAccountDto;
import com.sinosoft.reins.utility.resource.dtofactory.domain.DBPrpDAccount;
import com.sinosoft.sff.dbsvr.DBUtiPaymentToolsLog;
import com.sinosoft.sff.interf.PrpallCarShipInterf;
import com.sinosoft.sff.interf.blsvr.BLPrpCJplan;
import com.sinosoft.sff.interf.blsvr.BLPrpCJplanKind;
import com.sinosoft.sff.interf.dbsvr.DBPrpCJplan;
import com.sinosoft.sff.interf.schema.PrpCJplanKindSchema;
import com.sinosoft.sff.interf.schema.PrpCJplanSchema;
import com.sinosoft.sff.schema.PrpJplanFeeSchema;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.utiall.blsvr.BLPrpDagent;
import com.sinosoft.utiall.blsvr.BLPrpDration;
import com.sinosoft.utiall.blsvr.BLPrpDuser;
import com.sinosoft.utiall.dbsvr.DBPrpDrisk;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.database.DbPool;
import com.sinosoft.utility.error.UserException;
import com.sinosoft.utility.log.Log;
import com.sinosoft.utility.string.ChgDate;
import com.sinosoft.utility.string.Str;



/**
 * @author dp0728
 * mantis： MAR0070，處理人員：Sam.Su，需求單編號：MAR0070
 * JAR檔移出反編，主要使用method為 splitToKind
 */
public class PrpTransSffIntf2 {
	private Vector schemas = new Vector();

	public void initArr() throws Exception {
		this.schemas = new Vector();
	}

	public void setArr(PrpCJplanSchema iPrpCJplanSchema) throws Exception {
		try {
			this.schemas.add(iPrpCJplanSchema);
		} catch (Exception arg2) {
			throw arg2;
		}
	}

	public PrpCJplanSchema getArr(int index) throws Exception {
		PrpCJplanSchema prpCJplanSchema = null;

		try {
			prpCJplanSchema = (PrpCJplanSchema) this.schemas.get(index);
			return prpCJplanSchema;
		} catch (Exception arg3) {
			throw arg3;
		}
	}

	public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception arg2) {
			throw arg2;
		}
	}

	public int getSize() throws Exception {
		return this.schemas.size();
	}

	public void save(DbPool dbpool) throws Exception {
		DBPrpCJplan dbPrpCJplan = new DBPrpCJplan();

		for (int i = 0; i < this.schemas.size(); ++i) {
			dbPrpCJplan.setSchema((PrpCJplanSchema) this.schemas.get(i));
			dbPrpCJplan.insert(dbpool);
		}

	}

	public void transData(String iCertiType, String iCertiNo)
			throws UserException, Exception {
		System.err.println("当前数据连接名称: "
				+ SysConfig.getProperty("PAYMENTDATASOURCE"));
		DbPool dbpool = new DbPool();
		dbpool.open(SysConfig.getProperty("PAYMENTDATASOURCE"));

		try {
			dbpool.beginTransaction();
			this.transData(dbpool, iCertiType, iCertiNo);
			dbpool.commitTransaction();
		} catch (UserException arg8) {
			dbpool.rollbackTransaction();
			throw arg8;
		} catch (Exception arg9) {
			dbpool.rollbackTransaction();
			throw arg9;
		} finally {
			dbpool.close();
		}

	}

	public void transData(DbPool dbpool, String iCertiType, String iCertiNo)
			throws UserException, Exception {
		if (iCertiType.equals("P")) {
			this.transPolicy(dbpool, iCertiNo);
		} else if (iCertiType.equals("T")) {
			this.transProposalNo(dbpool, iCertiNo);
		} else if (iCertiType.equals("E")) {
			this.transEndor(dbpool, iCertiNo);
		} else {
			if (!iCertiType.equals("B")) {
				throw new UserException(-98, -1167,
						"PrpTransSffIntf.transData", "没有此业务类型：" + iCertiType);
			}

			this.transProposalNoQ(dbpool, iCertiNo);
		}

		this.save(dbpool);
	}

	public void transPolicy(DbPool dbpool, String iPolicyNo)
			throws UserException, Exception {
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
		BLPrpCplan blPrpCplan = new BLPrpCplan();
		String strWherePart = "";
		boolean intReturn = false;
		double dbSumPremium = 0.0D;
		double dbSumPremium2 = 0.0D;
		boolean blFlag = false;
		boolean isChinese = true;
		boolean intPlanCount = true;
		boolean isCombin = false;
		String strCenterCode = "";
		String strBranchCode = "";
		String strJFeeFlag = "";
		String strCarTypeCode = "";
		int arg28 = dbPrpCmain.getInfo(dbpool, iPolicyNo);
		if (arg28 == 100) {
			throw new UserException(-98, -1167, "PrpTransSff.transPolicy",
					"无此保单信息：" + iPolicyNo);
		} else {
			BLPrpCJplan schema;
			if (dbPrpCmain.getQuoteNo() != null
					&& !"".equals(dbPrpCmain.getQuoteNo())) {
				String dbPrpDrisk = " certiType=\'B\' and CertiNo=\'"
						+ dbPrpCmain.getQuoteNo() + "\'";
				schema = new BLPrpCJplan();
				schema.query(dbpool, dbPrpDrisk);
				if (schema.getSize() > 0) {
					this.updatecjPlanQToTorP(dbpool, dbPrpCmain.getQuoteNo());
				}
			}

			DBPrpDrisk arg30 = new DBPrpDrisk();
			arg30.getInfo(dbpool, dbPrpCmain.getRiskCode());
			if (arg30.getFlag().length() >= 2
					&& arg30.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			strWherePart = "PolicyNo=\'" + iPolicyNo
					+ "\' AND (EndorseNo IS NULL OR EndorseNo=\'\')";
			blPrpCplan.query(dbpool, strWherePart, 0);
			if (blPrpCplan.getSize() == 0) {
				throw new UserException(-98, -1167, "PrpTransSff.transPolicy",
						"无此保单信息：" + iPolicyNo);
			} else {
				int arg29 = blPrpCplan.getSize();
				schema = null;

				for (int blPrpDcompany = 0; blPrpDcompany < blPrpCplan
						.getSize(); ++blPrpDcompany) {
					dbSumPremium += Str.round(Double.parseDouble(blPrpCplan
							.getArr(blPrpDcompany).getPlanFee()), 2);
					dbSumPremium2 += Str.round(Double.parseDouble(blPrpCplan
							.getArr(blPrpDcompany).getPlanFee2()), 2);
				}

				BLPrpDcompany arg32 = null;
				BLPrpDagent blPrpDagent = null;
				BLPrpDuser blPrpDuser = null;

				int intCount;
				for (intCount = 0; intCount < blPrpCplan.getSize(); ++intCount) {
					PrpCJplanSchema arg31 = new PrpCJplanSchema();
					arg31.setCertiType("P");
					arg31.setCertiNo(iPolicyNo);
					arg31.setPolicyNo(iPolicyNo);
					arg31.setSerialNo(blPrpCplan.getArr(intCount).getSerialNo());
					arg31.setPayRefReason(blPrpCplan.getArr(intCount)
							.getPayReason());
					arg31.setClassCode(dbPrpCmain.getClassCode());
					arg31.setRiskCode(dbPrpCmain.getRiskCode());
					arg31.setContractNo(dbPrpCmain.getContractNo());
					arg31.setAppliCode(dbPrpCmain.getAppliCode());
					arg31.setAppliName(dbPrpCmain.getAppliName());
					arg31.setInsuredCode(dbPrpCmain.getInsuredCode());
					arg31.setInsuredName(dbPrpCmain.getInsuredName());
					arg31.setStartDate(dbPrpCmain.getStartDate());
					arg31.setEndDate(dbPrpCmain.getEndDate());
					arg31.setValidDate(dbPrpCmain.getOperateDate());
					arg31.setTCol1(dbPrpCmain.getInputDate());
					String strRelated = dbPrpCmain.getSubBusinessNature();
					arg31.setTCol2(strRelated);
					String strDisRate = dbPrpCmain.getDisRate();
					if (strDisRate != null && !"".equals(strDisRate)) {
						try {
							if (Double.parseDouble(strDisRate) > 0.0D) {
								arg31.setTCol3(strDisRate);
							}
						} catch (Exception arg27) {
							;
						}
					}

					arg31.setEndorType("");
					arg31.setPayNo(blPrpCplan.getArr(intCount).getPayNo());
					arg31.setTotalPayNo("" + arg29);
					arg31.setPlanFeeCurrency(blPrpCplan.getArr(intCount)
							.getCurrency2());
					arg31.setPlanFee(blPrpCplan.getArr(intCount).getPlanFee2());
					arg31.setPlanFeeCNY(blPrpCplan.getArr(intCount)
							.getPlanFee());
					arg31.setExchangeRate(blPrpCplan.getArr(intCount)
							.getExchangeRateCNY());
					arg31.setPlanDate(blPrpCplan.getArr(intCount).getPlanDate());
					arg31.setComCode(dbPrpCmain.getComCode());
					arg31.setMakeCom(dbPrpCmain.getMakeCom());
					arg31.setBusinessNature(dbPrpCmain.getBusinessNature());
					arg31.setChannelType(dbPrpCmain.getChannelType());
					arg31.setAgentCode(dbPrpCmain.getAgentCode());
					blPrpDagent = new BLPrpDagent();
					arg31.setAgentName(blPrpDagent.translateCode(
							dbPrpCmain.getAgentCode(), isChinese));
					arg31.setHandler1Code(dbPrpCmain.getHandler1Code());
					blPrpDuser = new BLPrpDuser();
					arg31.setHandler1Name(blPrpDuser.translateCode(
							dbPrpCmain.getHandler1Code(), isChinese));
					arg31.setHandlerCode(dbPrpCmain.getHandlerCode());
					arg31.setUnderWriteDate(dbPrpCmain.getUnderWriteEndDate());
					arg31.setUnderWriteFlag(dbPrpCmain.getUnderWriteFlag());
					arg31.setCoinsFlag(dbPrpCmain.getCoinsFlag());
					arg31.setCoinsCode("");
					arg31.setCoinsName("");
					arg31.setOthFlag("0");
					arg31.setLocationFlag(this.getLocationFlag(dbpool,
							dbPrpCmain.getNationFlag()));
					arg32 = new BLPrpDcompany();
					strWherePart = "ComCode=\'" + dbPrpCmain.getComCode()
							+ "\'";
					arg32.query(dbpool, strWherePart);
					strCenterCode = arg32.getArr(0).getAcntUnit();
					strBranchCode = strCenterCode;
					if (strCenterCode == null || strCenterCode.equals("")) {
						strBranchCode = strCenterCode;
					}

					arg31.setCenterCode(strCenterCode);
					arg31.setBranchCode(strBranchCode);
					arg31.setCarModel(strCarTypeCode);
					if (isCombin) {
						arg31.setIsCombin("1");
					} else {
						arg31.setIsCombin("0");
					}

					arg31.setAgriType(dbPrpCmain.getAgriType());
					strJFeeFlag = dbPrpCmain.getJFeeFlag();
					if (strJFeeFlag == null || strJFeeFlag.equals("")) {
						strJFeeFlag = "0";
					}

					arg31.setJFeeFlag(strJFeeFlag);
					arg31.setBankSerialNo("");
					arg31.setBankPosNo("");
					arg31.setRealPayRefFee("0");
					arg31.setRealPayRefFeeCNY("0");
					new DateTime();
					DateTime dateTime = new DateTime(DateTime.current(), 16);
					arg31.setInputDate(dateTime.toString());
					arg31.setFlag("");
					arg31.setProcessFlag("0");
					arg31.setAgent1code(dbPrpCmain.getAgent1code());
					this.setArr(arg31);
				}

				this.splitToKind(dbpool, "P", dbPrpCmain.getRiskCode());
				if (!dbPrpCmain.getCoinsFlag().equals("1")
						&& !dbPrpCmain.getCoinsFlag().equals("3")) {
					intCount = this.getSize();
					strWherePart = " PolicyNo=\'" + iPolicyNo + "\'";
				}

			}
		}
	}

	private void updatecjPlanQToTorP(DbPool dbpool, String certiNoQ)
			throws SQLException, Exception {
		String sql = "";
		sql = "DELETE FROM　PRPCJPLAN where certiNo= \'" + certiNoQ + "\'";
		dbpool.delete(sql);
		sql = "DELETE FROM　PRPCJPLANKIND where certiNo= \'" + certiNoQ + "\'";
		dbpool.delete(sql);
	}

	public void transProposalNo(DbPool dbpool, String iPolicyNo)
			throws UserException, Exception {
		DBPrpTmain dbPrpTmain = new DBPrpTmain();
		BLPrpTplan blPrpTplan = new BLPrpTplan();
		String strWherePart = "";
		boolean intReturn = false;
		double dbSumPremium = 0.0D;
		double dbSumPremium2 = 0.0D;
		boolean isChinese = true;
		boolean intPlanCount = true;
		boolean isCombin = false;
		String strCarTypeCode = "";
		int arg26 = dbPrpTmain.getInfo(dbpool, iPolicyNo);
		if (arg26 == 100) {
			throw new UserException(-98, -1167, "BLPrpJplanFee.transTPolicy",
					"无此投保单信息：" + iPolicyNo);
		} else {
			if (dbPrpTmain.getQuoteNo() != null
					&& !"".equals(dbPrpTmain.getQuoteNo())) {
				String dbPrpDrisk = " certiType=\'B\' and CertiNo=\'"
						+ dbPrpTmain.getQuoteNo() + "\'";
				BLPrpCJplan blPrpDcompany = new BLPrpCJplan();
				blPrpDcompany.query(dbpool, dbPrpDrisk);
				if (blPrpDcompany.getSize() > 0) {
					this.updatecjPlanQToTorP(dbpool, dbPrpTmain.getQuoteNo());
				}
			}

			DBPrpDrisk arg28 = new DBPrpDrisk();
			arg28.getInfo(dbpool, dbPrpTmain.getRiskCode());
			if (arg28.getFlag().length() >= 2
					&& arg28.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			BLPrpDcompany arg29 = new BLPrpDcompany();
			strWherePart = "ComCode=\'" + dbPrpTmain.getComCode() + "\'";
			arg29.query(dbpool, strWherePart);
			String strCenterCode = arg29.getArr(0).getAcntUnit();
			String strBranchCode = strCenterCode;
			strWherePart = "proposalno=\'" + iPolicyNo
					+ "\' AND (EndorseNo IS NULL OR EndorseNo=\'\')";
			blPrpTplan.query(dbpool, strWherePart, 0);
			if (blPrpTplan.getSize() == 0) {
				throw new UserException(-98, -1167,
						"BLPrpJplanFee.transTPolicy", "无此投保单信息：" + iPolicyNo);
			} else {
				int arg27 = blPrpTplan.getSize();
				PrpCJplanSchema schema = null;

				for (int blPrpDagent = 0; blPrpDagent < blPrpTplan.getSize(); ++blPrpDagent) {
					dbSumPremium += Str.round(Double.parseDouble(blPrpTplan
							.getArr(blPrpDagent).getPlanFee()), 2);
					dbSumPremium2 += Str.round(Double.parseDouble(blPrpTplan
							.getArr(blPrpDagent).getPlanFee2()), 2);
				}

				BLPrpDagent arg30 = null;
				BLPrpDuser blPrpDuser = null;

				int intCount;
				for (intCount = 0; intCount < blPrpTplan.getSize(); ++intCount) {
					schema = new PrpCJplanSchema();
					schema.setCertiType("T");
					schema.setCertiNo(iPolicyNo);
					schema.setSerialNo(blPrpTplan.getArr(intCount)
							.getSerialNo());
					schema.setPolicyNo(iPolicyNo);
					schema.setPayRefReason(blPrpTplan.getArr(intCount)
							.getPayReason());
					schema.setContractNo(dbPrpTmain.getContractNo());
					schema.setClassCode(dbPrpTmain.getClassCode());
					schema.setRiskCode(dbPrpTmain.getRiskCode());
					schema.setAppliCode(dbPrpTmain.getAppliCode());
					schema.setAppliName(dbPrpTmain.getAppliName());
					schema.setInsuredCode(dbPrpTmain.getInsuredCode());
					schema.setInsuredName(dbPrpTmain.getInsuredName());
					schema.setStartDate(dbPrpTmain.getStartDate());
					schema.setEndDate(dbPrpTmain.getEndDate());
					schema.setValidDate(dbPrpTmain.getOperateDate());
					schema.setTCol1(dbPrpTmain.getInputDate());
					String strRelated = dbPrpTmain.getSubBusinessNature();
					schema.setTCol2(strRelated);
					String strDisRate = dbPrpTmain.getDisRate();
					if (strDisRate != null && !"".equals(strDisRate)) {
						try {
							if (Double.parseDouble(strDisRate) > 0.0D) {
								schema.setTCol3(strDisRate);
							}
						} catch (Exception arg25) {
							;
						}
					}

					schema.setPayNo(blPrpTplan.getArr(intCount).getPayNo());
					schema.setTotalPayNo("" + arg27);
					schema.setPlanFeeCurrency(blPrpTplan.getArr(intCount)
							.getCurrency2());
					schema.setPlanFee(blPrpTplan.getArr(intCount).getPlanFee2());
					schema.setPlanFeeCNY(blPrpTplan.getArr(intCount)
							.getPlanFee());
					schema.setExchangeRate(blPrpTplan.getArr(intCount)
							.getExchangeRateCNY());
					schema.setPlanDate(blPrpTplan.getArr(intCount)
							.getPlanDate());
					schema.setComCode(dbPrpTmain.getComCode());
					schema.setMakeCom(dbPrpTmain.getMakeCom());
					schema.setBusinessNature(dbPrpTmain.getBusinessNature());
					schema.setChannelType(dbPrpTmain.getChannelType());
					schema.setAgentCode(dbPrpTmain.getAgentCode());
					arg30 = new BLPrpDagent();
					schema.setAgentName(arg30.translateCode(
							dbPrpTmain.getAgentCode(), isChinese));
					schema.setHandler1Code(dbPrpTmain.getHandler1Code());
					blPrpDuser = new BLPrpDuser();
					schema.setHandler1Name(blPrpDuser.translateCode(
							dbPrpTmain.getHandler1Code(), isChinese));
					schema.setHandlerCode(dbPrpTmain.getHandlerCode());
					schema.setUnderWriteDate(DateTime.current().toString()
							.substring(0, 10));
					schema.setUnderWriteFlag("1");
					schema.setCoinsFlag(dbPrpTmain.getCoinsFlag());
					schema.setOthFlag("0");
					schema.setLocationFlag(this.getLocationFlag(dbpool,
							dbPrpTmain.getNationFlag()));
					schema.setCenterCode(strCenterCode);
					schema.setBranchCode(strBranchCode);
					schema.setCarModel(strCarTypeCode);
					if (isCombin) {
						schema.setIsCombin("1");
					} else {
						schema.setIsCombin("0");
					}

					schema.setAgriType(dbPrpTmain.getAgriType());
					schema.setJFeeFlag("1");
					schema.setRealPayRefFee("0");
					schema.setRealPayRefFeeCNY("0");
					new DateTime();
					DateTime dateTime = new DateTime(DateTime.current(), 16);
					schema.setInputDate(dateTime.toString());
					schema.setFlag("");
					schema.setProcessFlag("0");
					schema.setAgent1code(dbPrpTmain.getAgent1code());
					this.setArr(schema);
				}

				this.splitToKind(dbpool, "T", dbPrpTmain.getRiskCode());
				if (!dbPrpTmain.getCoinsFlag().equals("1")
						&& !dbPrpTmain.getCoinsFlag().equals("3")) {
					intCount = this.getSize();
					strWherePart = " Proposalno=\'" + iPolicyNo + "\'";
				}

			}
		}
	}

	public void transProposalNoQ(DbPool dbpool, String iPolicyNo)
			throws UserException, Exception {
		if("BA01201940002061".equals(iPolicyNo)){
			iPolicyNo = iPolicyNo;
		}
		DBPrpQmain dbPrpQmain = new DBPrpQmain();
		BLPrpQplan blPrpQplan = new BLPrpQplan();
		String strWherePart = "";
		boolean intReturn = false;
		double dbSumPremium = 0.0D;
		double dbSumPremium2 = 0.0D;
		boolean isChinese = true;
		boolean intPlanCount = true;
		boolean isCombin = false;
		String strCarTypeCode = "";
		int arg27 = dbPrpQmain.getInfo(dbpool, iPolicyNo);
		if (arg27 == 100) {
			throw new UserException(-98, -1167, "BLPrpJplanFee.transTPolicy",
					"无此投保单信息：" + iPolicyNo);
		} else {
			DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
			dbPrpDrisk.getInfo(dbpool, dbPrpQmain.getRiskCode());
			if (dbPrpDrisk.getFlag().length() >= 2
					&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			strWherePart = "ComCode=\'" + dbPrpQmain.getComCode() + "\'";
			blPrpDcompany.query(dbpool, strWherePart);
			String strCenterCode = blPrpDcompany.getArr(0).getAcntUnit();
			String strBranchCode = strCenterCode;
			strWherePart = "proposalno=\'" + iPolicyNo
					+ "\' AND (EndorseNo IS NULL OR EndorseNo=\'\')";
			blPrpQplan.query(dbpool, strWherePart, 0);
			if (blPrpQplan.getSize() == 0) {
				throw new UserException(-98, -1167,
						"BLPrpJplanFee.transTPolicy", "无此投保单信息：" + iPolicyNo);
			} else {
				int arg28 = blPrpQplan.getSize();
				PrpCJplanSchema schema = null;

				for (int blPrpDagent = 0; blPrpDagent < blPrpQplan.getSize(); ++blPrpDagent) {
					dbSumPremium += Str.round(Double.parseDouble(blPrpQplan
							.getArr(blPrpDagent).getPlanFee()), 2);
					dbSumPremium2 += Str.round(Double.parseDouble(blPrpQplan
							.getArr(blPrpDagent).getPlanFee2()), 2);
				}

				BLPrpDagent arg29 = null;
				BLPrpDuser blPrpDuser = null;

				int intCount;
				for (intCount = 0; intCount < blPrpQplan.getSize(); ++intCount) {
					schema = new PrpCJplanSchema();
					schema.setCertiType("B");
					schema.setCertiNo(iPolicyNo);
					schema.setSerialNo(blPrpQplan.getArr(intCount)
							.getSerialNo());
					schema.setPolicyNo(iPolicyNo);
					schema.setPayRefReason(blPrpQplan.getArr(intCount)
							.getPayReason());
					schema.setContractNo(dbPrpQmain.getContractNo());
					schema.setClassCode(dbPrpQmain.getClassCode());
					schema.setRiskCode(dbPrpQmain.getRiskCode());
					schema.setAppliCode(dbPrpQmain.getAppliCode());
					schema.setAppliName(dbPrpQmain.getAppliName());
					schema.setInsuredCode(dbPrpQmain.getInsuredCode());
					schema.setInsuredName(dbPrpQmain.getInsuredName());
					schema.setStartDate(dbPrpQmain.getStartDate());
					schema.setEndDate(dbPrpQmain.getEndDate());
					schema.setValidDate(dbPrpQmain.getOperateDate());
					schema.setTCol1(dbPrpQmain.getInputDate());
					String strRelated = dbPrpQmain.getSubBusinessNature();
					schema.setTCol2(strRelated);
					String strDisRate = dbPrpQmain.getDisRate();
					if (strDisRate != null && !"".equals(strDisRate)) {
						try {
							if (Double.parseDouble(strDisRate) > 0.0D) {
								schema.setTCol3(strDisRate);
							}
						} catch (Exception arg26) {
							;
						}
					}

					schema.setPayNo(blPrpQplan.getArr(intCount).getPayNo());
					schema.setTotalPayNo("" + arg28);
					schema.setPlanFeeCurrency(blPrpQplan.getArr(intCount)
							.getCurrency2());
					schema.setPlanFee(blPrpQplan.getArr(intCount).getPlanFee2());
					schema.setPlanFeeCNY(blPrpQplan.getArr(intCount)
							.getPlanFee());
					schema.setExchangeRate(blPrpQplan.getArr(intCount)
							.getExchangeRateCNY());
					schema.setPlanDate(blPrpQplan.getArr(intCount)
							.getPlanDate());
					schema.setComCode(dbPrpQmain.getComCode());
					schema.setMakeCom(dbPrpQmain.getMakeCom());
					schema.setBusinessNature(dbPrpQmain.getBusinessNature());
					schema.setChannelType(dbPrpQmain.getChannelType());
					schema.setAgentCode(dbPrpQmain.getAgentCode());
					arg29 = new BLPrpDagent();
					schema.setAgentName(arg29.translateCode(
							dbPrpQmain.getAgentCode(), isChinese));
					schema.setHandler1Code(dbPrpQmain.getHandler1Code());
					blPrpDuser = new BLPrpDuser();
					schema.setHandler1Name(blPrpDuser.translateCode(
							dbPrpQmain.getHandler1Code(), isChinese));
					schema.setHandlerCode(dbPrpQmain.getHandlerCode());
					schema.setUnderWriteDate(DateTime.current().toString()
							.substring(0, 10));
					schema.setUnderWriteFlag("1");
					schema.setCoinsFlag(dbPrpQmain.getCoinsFlag());
					schema.setOthFlag("0");
					schema.setLocationFlag(this.getLocationFlag(dbpool,
							dbPrpQmain.getNationFlag()));
					schema.setCenterCode(strCenterCode);
					schema.setBranchCode(strBranchCode);
					schema.setCarModel(strCarTypeCode);
					if (isCombin) {
						schema.setIsCombin("1");
					} else {
						schema.setIsCombin("0");
					}

					schema.setAgriType(dbPrpQmain.getAgriType());
					String strJFeeFlag = dbPrpQmain.getJFeeFlag();
					if (strJFeeFlag == null || strJFeeFlag.equals("")) {
						strJFeeFlag = "0";
					}

					schema.setJFeeFlag(strJFeeFlag);
					schema.setRealPayRefFee("0");
					schema.setRealPayRefFeeCNY("0");
					new DateTime();
					DateTime dateTime = new DateTime(DateTime.current(), 16);
					schema.setInputDate(dateTime.toString());
					schema.setFlag("");
					schema.setProcessFlag("0");
					schema.setAgent1code(dbPrpQmain.getAgent1code());
					this.setArr(schema);
				}

				this.splitToKind(dbpool, "B", dbPrpQmain.getRiskCode());
				if (!dbPrpQmain.getCoinsFlag().equals("1")
						&& !dbPrpQmain.getCoinsFlag().equals("3")) {
					intCount = this.getSize();
					strWherePart = " Proposalno=\'" + iPolicyNo + "\'";
				}

			}
		}
	}

	public void transEndor(DbPool dbpool, String iEndorseNo)
			throws UserException, Exception {
		DBPrpPhead dbPrpPhead = new DBPrpPhead();
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
		DBPrpCPmain dbPrpCPmain = new DBPrpCPmain();
		DBPrpPAccount dbPrpPAccount = new DBPrpPAccount();
		BLPrpPfee blPrpPfee = new BLPrpPfee();
		String strWherePart = "";
		boolean isChinese = true;
		boolean isCombin = false;
		double dblDisFee = 0.0D;
		boolean intReturn = false;
		int arg33 = dbPrpPhead.getInfo(dbpool, iEndorseNo);
		if (arg33 == 100) {
			throw new UserException(-98, -1167, "BLPrpJplanFee.transEndor",
					"无此批单信息：" + iEndorseNo);
		} else {
			dbPrpCmain.getInfo(dbpool, dbPrpPhead.getPolicyNo());
			dbPrpCPmain.getInfo(dbpool, dbPrpPhead.getPolicyNo());
			DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
			dbPrpDrisk.getInfo(dbpool, dbPrpCmain.getRiskCode());
			if (dbPrpDrisk.getFlag().length() >= 2
					&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			String strCarTypeCode = "";
			DBPrpCitemCarExt dbPrpCitemCarExt = new DBPrpCitemCarExt();
			dbPrpCitemCarExt.getInfo(dbpool, dbPrpPhead.getPolicyNo());
			strCarTypeCode = dbPrpCitemCarExt.getCartypeCode();
			strWherePart = "EndorseNo=\'" + iEndorseNo + "\' AND ChgPremium!=0";
			blPrpPfee.query(dbpool, strWherePart);
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			strWherePart = "ComCode=\'" + dbPrpPhead.getComCode() + "\'";
			blPrpDcompany.query(dbpool, strWherePart);
			String strCenterCode = blPrpDcompany.getArr(0).getAcntUnit();
			String strBranchCode = strCenterCode;
			boolean intPAccount = true;
			int arg34 = dbPrpPAccount.getInfo(dbpool, iEndorseNo);
			PrpCJplanSchema schema = null;
			BLPrpDagent blPrpDagent = null;
			BLPrpDuser blPrpDuser = null;

			String arrayEndorseType;
			int intCount;
			String strDisRate;
			for (intCount = 0; intCount < blPrpPfee.getSize(); ++intCount) {
				schema = new PrpCJplanSchema();
				schema.setCertiType("E");
				schema.setCertiNo(iEndorseNo);
				schema.setSerialNo("" + (intCount + 1));
				schema.setPolicyNo(dbPrpPhead.getPolicyNo());
				if (dbPrpPhead.getEndorType().equals("21")) {
					schema.setPayRefReason("P30");
				} else if (Double.parseDouble(blPrpPfee.getArr(intCount)
						.getChgPremium1()) > 0.0D) {
					schema.setPayRefReason("R30");
				} else {
					schema.setPayRefReason("P10");
				}

				schema.setClassCode(dbPrpCmain.getClassCode());
				schema.setRiskCode(dbPrpCmain.getRiskCode());
				schema.setContractNo(dbPrpCmain.getContractNo());
				schema.setAppliCode(dbPrpCPmain.getAppliCode());
				schema.setAppliName(dbPrpCPmain.getAppliName());
				schema.setInsuredCode(dbPrpCmain.getInsuredCode());
				schema.setInsuredName(dbPrpCmain.getInsuredName());
				schema.setStartDate(dbPrpPhead.getValidDate());
				schema.setEndDate(dbPrpCmain.getEndDate());
				schema.setTCol1(dbPrpPhead.getInputDate());
				arrayEndorseType = dbPrpCmain.getSubBusinessNature();
				schema.setTCol2(arrayEndorseType);
				String resultSet = dbPrpCmain.getDisRate();
				if (resultSet != null && !"".equals(resultSet)) {
					try {
						if (Double.parseDouble(resultSet) > 0.0D) {
							schema.setTCol3(resultSet);
						}
					} catch (Exception arg32) {
						;
					}
				}

				schema.setValidDate(dbPrpPhead.getValidDate());
				schema.setEndorType(dbPrpPhead.getEndorType());
				schema.setPayNo("1");
				schema.setTotalPayNo("1");
				schema.setPlanFeeCurrency(blPrpPfee.getArr(intCount)
						.getCurrency2());
				schema.setPlanFee(blPrpPfee.getArr(intCount).getChgPremium2());
				schema.setPlanFeeCNY(blPrpPfee.getArr(intCount)
						.getChgPremium1());
				schema.setExchangeRate(String.valueOf(Str.round(
						Double.parseDouble(Str.chgStrZero(blPrpPfee.getArr(
								intCount).getChgPremium1()))
								/ Double.parseDouble(Str.chgStrZero(blPrpPfee
										.getArr(intCount).getChgPremium2())), 6)));
				schema.setPlanDate(dbPrpPhead.getValidDate());
				schema.setComCode(dbPrpCmain.getComCode());
				schema.setMakeCom(dbPrpCmain.getMakeCom());
				schema.setBusinessNature(dbPrpCmain.getBusinessNature());
				schema.setChannelType(dbPrpCmain.getChannelType());
				schema.setAgentCode(dbPrpCmain.getAgentCode());
				blPrpDagent = new BLPrpDagent();
				schema.setAgentName(blPrpDagent.translateCode(
						dbPrpCmain.getAgentCode(), isChinese));
				schema.setHandler1Code(dbPrpCmain.getHandler1Code());
				blPrpDuser = new BLPrpDuser();
				schema.setHandler1Name(blPrpDuser.translateCode(
						dbPrpCmain.getHandler1Code(), isChinese));
				schema.setHandlerCode(dbPrpCmain.getHandlerCode());
				schema.setUnderWriteDate(DateTime.current().toString()
						.substring(0, 10));
				schema.setUnderWriteFlag(dbPrpPhead.getUnderWriteFlag());
				schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
				schema.setCarModel(strCarTypeCode);
				if (isCombin) {
					schema.setIsCombin("1");
				} else {
					schema.setIsCombin("0");
				}

				schema.setAgriType(dbPrpCmain.getAgriType());
				schema.setCenterCode(strCenterCode);
				schema.setBranchCode(strBranchCode);
				schema.setRealPayRefFee("0");
				schema.setRealPayRefFeeCNY("0");
				new DateTime();
				DateTime strRelated = new DateTime(DateTime.current(), 16);
				schema.setInputDate(strRelated.toString());
				schema.setFlag("");
				schema.setProcessFlag("0");
				schema.setAgent1code(dbPrpCmain.getAgent1code());
				strDisRate = "";
				String dateTime = "";
				if (dbPrpPhead.getJFeeFlag() != null
						&& !dbPrpPhead.getJFeeFlag().equals("")
						&& !"0".equals(dbPrpPhead.getJFeeFlag())) {
					if (dbPrpPhead.getJFeeFlag().equals("1")) {
						strDisRate = "1";
						schema.setJFeeFlag(dbPrpPhead.getJFeeFlag());
						schema.setUnderWriteFlag(strDisRate);
					}
				} else {
					dateTime = "0";
					schema.setJFeeFlag(dateTime);
				}

				schema.setLocationFlag(this.getLocationFlag(dbpool,
						dbPrpCmain.getNationFlag()));
				if (dbPrpPhead.getEndorType().equals("19")) {
					schema.setOthFlag("1");
				} else {
					schema.setOthFlag("0");
				}

				if (arg34 == 0) {
					schema.setAccountCode(dbPrpPAccount.getAccountNO());
					schema.setCustomBankCode(dbPrpPAccount.getBankCode());
					schema.setCustomBankName(dbPrpPAccount.getBankName());
					schema.setCertificateCode(dbPrpPAccount.getExtendChar1());
					schema.setOwnerName(dbPrpPAccount.getAccountName());
					schema.setOwnership(dbPrpPAccount.getAccountType());
				}

				this.setArr(schema);
			}

			if (blPrpPfee.getSize() == 0) {
				SimpleDateFormat arg35 = new SimpleDateFormat("yyyy-MM-dd");
				schema = new PrpCJplanSchema();
				schema.setCertiType("E");
				schema.setCertiNo(iEndorseNo);
				schema.setSerialNo("1");
				schema.setPolicyNo(dbPrpPhead.getPolicyNo());
				arrayEndorseType = "";
				ResultSet arg36 = dbpool
						.query("select ArrayEndorType from prpphead where endorseno=\'"
								+ iEndorseNo + "\'");
				if (arg36.next()) {
					arrayEndorseType = arg36.getString(1);
				}

				if (!"".equals(arrayEndorseType) && arrayEndorseType != null) {
					if (arrayEndorseType.indexOf("88") > -1) {
						schema.setPayRefReason("P50");
					} else {
						schema.setPayRefReason("P40");
					}
				} else {
					schema.setPayRefReason("P40");
				}

				schema.setClassCode(dbPrpCmain.getClassCode());
				schema.setRiskCode(dbPrpCmain.getRiskCode());
				schema.setContractNo(dbPrpCmain.getContractNo());
				schema.setAppliCode(dbPrpCPmain.getAppliCode());
				schema.setAppliName(dbPrpCPmain.getAppliName());
				arg36 = dbpool
						.query("select INSUREDCODE,INSUREDNAME from prpcpinsured where policyno=\'"
								+ dbPrpCPmain.getPolicyNo()
								+ "\' and serialno=\'1\'");
				if (arg36.next()) {
					schema.setInsuredCode(arg36.getString(1));
					schema.setInsuredName(arg36.getString(2));
				} else {
					schema.setInsuredCode(dbPrpCPmain.getInsuredCode());
					schema.setInsuredName(dbPrpCPmain.getInsuredCode());
				}

				if (dbPrpPhead.getEndorseNo().equals("01")) {
					arg36 = dbpool
							.query("select MOVESTARTDATE,MOVEENDDATE  from prpphead where EndorseNo=\'"
									+ iEndorseNo + "\' ");
					if (arg36.next()) {
						schema.setStartDate(arg35.format(arg36.getDate(1)));
						schema.setEndDate(arg35.format(arg36.getDate(2)));
					}
				} else {
					schema.setStartDate(dbPrpPhead.getValidDate());
					schema.setEndDate(dbPrpCmain.getEndDate());
				}

				schema.setTCol1(dbPrpPhead.getInputDate());
				String arg37 = dbPrpCPmain.getSubBusinessNature();
				schema.setTCol2(arg37);
				strDisRate = dbPrpCmain.getDisRate();
				if (strDisRate != null && !"".equals(strDisRate)) {
					try {
						if (Double.parseDouble(strDisRate) > 0.0D) {
							schema.setTCol3(strDisRate);
						}
					} catch (Exception arg31) {
						;
					}
				}

				schema.setValidDate(dbPrpPhead.getValidDate());
				schema.setEndorType(dbPrpPhead.getEndorType());
				schema.setPayNo("1");
				schema.setTotalPayNo("1");
				schema.setPlanFeeCurrency("NTD");
				schema.setPlanFee("0");
				schema.setPlanFeeCNY("0");
				schema.setExchangeRate("1");
				schema.setPlanDate(dbPrpPhead.getValidDate());
				schema.setComCode(dbPrpCPmain.getComCode());
				schema.setMakeCom(dbPrpCPmain.getMakeCom());
				schema.setBusinessNature(dbPrpCPmain.getBusinessNature());
				schema.setChannelType(dbPrpCPmain.getChannelType());
				schema.setAgentCode(dbPrpCPmain.getAgentCode());
				blPrpDagent = new BLPrpDagent();
				schema.setAgentName(blPrpDagent.translateCode(
						dbPrpCPmain.getAgentCode(), isChinese));
				schema.setHandler1Code(dbPrpCPmain.getHandler1Code());
				blPrpDuser = new BLPrpDuser();
				schema.setHandler1Name(blPrpDuser.translateCode(
						dbPrpCPmain.getHandler1Code(), isChinese));
				schema.setHandlerCode(dbPrpCPmain.getHandlerCode());
				schema.setUnderWriteDate(DateTime.current().toString()
						.substring(0, 10));
				schema.setUnderWriteFlag(dbPrpPhead.getUnderWriteFlag());
				schema.setCoinsFlag(dbPrpCPmain.getCoinsFlag());
				arg36 = dbpool
						.query("select modelcode from prpcpitemcar where policyno=\'"
								+ dbPrpCPmain.getPolicyNo()
								+ "\' and itemno=\'1\'");
				if (arg36.next()) {
					schema.setCarModel(arg36.getString(1));
				} else {
					schema.setCarModel(strCarTypeCode);
				}

				if (isCombin) {
					schema.setIsCombin("1");
				} else {
					schema.setIsCombin("0");
				}

				schema.setAgriType(dbPrpCPmain.getAgriType());
				schema.setCenterCode(strCenterCode);
				schema.setBranchCode(strBranchCode);
				schema.setRealPayRefFee("0");
				schema.setRealPayRefFeeCNY("0");
				new DateTime();
				DateTime arg38 = new DateTime(DateTime.current(), 16);
				schema.setInputDate(arg38.toString());
				schema.setFlag("");
				schema.setProcessFlag("0");
				schema.setAgent1code(dbPrpCmain.getAgent1code());
				String underWriteFlag = "";
				String strJFeeFlag = "";
				if (dbPrpPhead.getJFeeFlag() != null
						&& !dbPrpPhead.getJFeeFlag().equals("")
						&& !"0".equals(dbPrpPhead.getJFeeFlag())) {
					if (dbPrpPhead.getJFeeFlag().equals("1")) {
						underWriteFlag = "1";
						schema.setJFeeFlag(dbPrpPhead.getJFeeFlag());
						schema.setUnderWriteFlag(underWriteFlag);
					}
				} else {
					strJFeeFlag = "0";
					schema.setJFeeFlag(strJFeeFlag);
				}

				schema.setLocationFlag(this.getLocationFlag(dbpool,
						dbPrpCPmain.getNationFlag()));
				if (dbPrpPhead.getEndorType().equals("19")) {
					schema.setOthFlag("1");
				} else {
					schema.setOthFlag("0");
				}

				if (arg34 == 0) {
					schema.setAccountCode(dbPrpPAccount.getAccountNO());
					schema.setCustomBankCode(dbPrpPAccount.getBankCode());
					schema.setCustomBankName(dbPrpPAccount.getBankName());
					schema.setCertificateCode(dbPrpPAccount.getExtendChar1());
					schema.setOwnerName(dbPrpPAccount.getAccountName());
					schema.setOwnership(dbPrpPAccount.getAccountType());
				}

				this.setArr(schema);
			}

			if (!dbPrpCmain.getCoinsFlag().equals("1")
					&& !dbPrpCmain.getCoinsFlag().equals("3")) {
				intCount = this.getSize();
				strWherePart = " EndorseNo=\'" + iEndorseNo + "\'";
			}

			if (dbPrpPhead.getEndorType().indexOf("60") > -1
					|| dbPrpPhead.getEndorType().indexOf("04") > -1) {
				strWherePart = "UPDATE PrpJpayRefRec SET AppliCode=\'"
						+ dbPrpCPmain.getAppliCode() + "\',AppliName=\'"
						+ dbPrpCPmain.getAppliName() + "\' WHERE PolicyNo=\'"
						+ dbPrpCPmain.getPolicyNo() + "\'";
				dbpool.update(strWherePart);
			}

			this.splitToKind(dbpool, "E", dbPrpCmain.getRiskCode());
		}
	}

	public void transPlanfee(DbPool dbpool, String iPolicyNo) throws Exception {
		Log.init("sffTransAccount.log", "sffTransAccount.log", true);
		System.err.println("删除暂保单" + iPolicyNo + "未收款数据");
		Log.print((new ChgDate()).getCurrentTime("yyyy-MM-dd HH:mm:ss"));
		String strSQL = "Delete prpjplanfee Where PolicyNo=\'" + iPolicyNo
				+ "\' And realpayreffee=0 AND PayRefReason=\'R03\' ";
		Log.println(new String(("删除暂保单" + iPolicyNo + "未收款数据:" + strSQL)
				.getBytes("GBK"), "ISO8859_1"));
		dbpool.executeUpdate(strSQL);
	}

	private void printLog(DbPool dbpool) throws Exception {
		DBUtiPaymentToolsLog dbLogSchema = null;
		PrpJplanFeeSchema prpJplanFeeSchema = null;

		for (int i = 0; i < this.schemas.size(); ++i) {
			DateTime dateTime = DateTime.current();
			prpJplanFeeSchema = (PrpJplanFeeSchema) this.schemas.get(i);
			dbLogSchema = new DBUtiPaymentToolsLog();
			dbLogSchema.setCertiType(prpJplanFeeSchema.getCertiType());
			dbLogSchema.setCertiNo(prpJplanFeeSchema.getCertiNo());
			dbLogSchema.setSerialNo(prpJplanFeeSchema.getSerialNo());
			dbLogSchema.setPayrefreason(prpJplanFeeSchema.getPayRefReason());
			dbLogSchema.setPayrefTimes("1");
			System.out.println(dateTime.toString(13));
			dbLogSchema.setOperateDate(dateTime.toString(13));
			dbLogSchema.setOperateTime(dateTime.toString(45));
			dbLogSchema.setFlag("");
			dbLogSchema.setRemark(prpJplanFeeSchema.getPlanFee());
			dbLogSchema.setContext("利用工具进行补数：补数内容" + dbLogSchema.getCertiType()
					+ "|" + dbLogSchema.getCertiNo() + "|"
					+ dbLogSchema.getSerialNo() + "|"
					+ dbLogSchema.getPayrefreason() + "|"
					+ dateTime.toString(15));
			dbLogSchema.insert(dbpool);
		}

	}

	public String getLocationFlag(DbPool dbpool, String iNationFlag)
			throws Exception {
		String strLocationFlag = "";
		if (iNationFlag != null && !iNationFlag.equals("")
				&& !iNationFlag.equals("1")) {
			if (iNationFlag.equals("0")) {
				strLocationFlag = "2";
			}
		} else {
			strLocationFlag = "1";
		}

		return strLocationFlag;
	}

	public void transCoinsDetail(DbPool dbpool, String iWherePart,
			PrpCJplanSchema iSchema, double dbSumPremium, String iBizType,
			boolean iFlag) throws Exception {
		if ("POLICY".equals(iBizType)) {
			this.transCoinsPolicy(dbpool, iWherePart, iSchema, dbSumPremium,
					iFlag);
		} else if ("ENDORSE".equals(iBizType)) {
			this.transCoinsEndorse(dbpool, iWherePart, iSchema, dbSumPremium,
					iFlag);
		} else {
			"COMPENSATE".equals(iBizType);
		}

	}

	public void transTCoinsDetail(DbPool dbpool, String iWherePart,
			PrpCJplanSchema iSchema, double dbSumPremium, String iBizType,
			boolean iFlag) throws Exception {
		if ("POLICY".equals(iBizType)) {
			this.transCoinsTPolicy(dbpool, iWherePart, iSchema, dbSumPremium,
					iFlag);
		} else if ("ENDORSE".equals(iBizType)) {
			this.transCoinsEndorse(dbpool, iWherePart, iSchema, dbSumPremium,
					iFlag);
		} else {
			"COMPENSATE".equals(iBizType);
		}

	}

	public void transCoinsPolicy(DbPool dbpool, String iWherePart,
			PrpCJplanSchema iSchema, double dbSumPremium, boolean iFlag)
			throws Exception {
		BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
		BLPrpCcoinsDetail blPrpCcoinsDetail = new BLPrpCcoinsDetail();
		blPrpCcoins.query(dbpool, iWherePart);
		blPrpCcoinsDetail.query(dbpool, iWherePart);
		PrpCJplanSchema schema = null;
		String strSerialNo = "";
		double dblPlanFee = 0.0D;
		int intSerialNoOperatefee = 1;
		int intSerialNo = 1 + (Integer.parseInt(iSchema.getPayNo()) - 1)
				* blPrpCcoins.getSize()
				+ (Integer.parseInt(iSchema.getPayNo()) - 1)
				* (blPrpCcoins.getSize() - 1);
		PrpCcoinsDetailSchema prpCcoinsDetailSchema = new PrpCcoinsDetailSchema();
		BLPrpCcharge blPrpCcharge = new BLPrpCcharge();
		blPrpCcharge.query(dbpool, iWherePart);

		int i;
		for (i = 0; i < blPrpCcharge.getSize(); ++i) {
			double strPayrefreason = 0.0D;
			double blPrpCcoinsDetail1 = 0.0D;
			schema = new PrpCJplanSchema();
			schema.setSchema(iSchema);
			strSerialNo = "" + intSerialNo;
			schema.setSerialNo(strSerialNo);
			if ("001".equals(blPrpCcharge.getArr(i).getChargeCode())) {
				schema.setPayRefReason("P37");
			} else if ("002".equals(blPrpCcharge.getArr(i).getChargeCode())) {
				schema.setPayRefReason("T60");
			} else {
				schema.setPayRefReason("P37");
			}

			schema.setCertiType("F");
			strPayrefreason = Double.parseDouble(blPrpCcharge.getArr(i)
					.getPlanFee());
			blPrpCcoinsDetail1 = Double.parseDouble(blPrpCcharge.getArr(i)
					.getPlanFee2());
			if (iFlag) {
				double m = 0.0D;
				double dblChargePlanFeeSum2 = 0.0D;

				for (int k = 0; k < this.getSize(); ++k) {
					if (this.getArr(k).getPayRefReason()
							.equals(schema.getPayRefReason())
							&& !this.getArr(k).getPayNo()
									.equals(schema.getPayNo())) {
						m += Double.parseDouble(this.getArr(k).getPlanFeeCNY());
						dblChargePlanFeeSum2 += Double.parseDouble(this.getArr(
								k).getPlanFee());
					}
				}

				strPayrefreason = Double.parseDouble(blPrpCcharge.getArr(i)
						.getPlanFee()) - m;
				blPrpCcoinsDetail1 = Double.parseDouble(blPrpCcharge.getArr(i)
						.getPlanFee2()) - dblChargePlanFeeSum2;
			} else {
				strPayrefreason = Double.parseDouble(blPrpCcharge.getArr(i)
						.getPlanFee())
						* Double.parseDouble(iSchema.getPlanFeeCNY())
						/ dbSumPremium;
			}

			strPayrefreason = Str.round(strPayrefreason, 2);
			if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
				if (iFlag) {
					schema.setPlanFee("" + Str.round(blPrpCcoinsDetail1, 0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									strPayrefreason
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				}
			} else if (iFlag) {
				schema.setPlanFee("" + Str.round(blPrpCcoinsDetail1, 2));
			} else {
				schema.setPlanFee(""
						+ Str.round(
								strPayrefreason
										/ Double.parseDouble(Str
												.chgStrZero(schema
														.getExchangeRate())), 2));
			}

			schema.setPlanFeeCNY("" + strPayrefreason);
			this.setArr(schema);
		}

		for (i = 0; i < blPrpCcoins.getSize(); ++i) {
			for (int arg37 = 0; arg37 < blPrpCcoinsDetail.getSize(); ++arg37) {
				if (blPrpCcoins.getArr(i).getSerialNo()
						.equals(blPrpCcoinsDetail.getArr(arg37).getSerialNo())) {
					prpCcoinsDetailSchema = blPrpCcoinsDetail.getArr(arg37);
					break;
				}
			}

			String arg36 = iSchema.getPayRefReason();
			double dblSumPlanfee;
			int dblSumOperatefee;
			double dbOperatefee;
			if (blPrpCcoins.getArr(i).getCoinsType().equals("1")) {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				if ("R00".equals(schema.getPayRefReason())) {
					schema.setPayRefReason("R00");
				} else {
					schema.setPayRefReason("R10");
				}

				schema.setCertiType("P");
				schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
				dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
						.getCoinsPremium());
				if (iFlag) {
					dblSumPlanfee = 0.0D;

					for (dblSumOperatefee = 0; dblSumOperatefee < this
							.getSize(); ++dblSumOperatefee) {
						if (this.getArr(dblSumOperatefee).getPayRefReason()
								.equals(schema.getPayRefReason())
								&& this.getArr(dblSumOperatefee).getCoinsCode()
										.equals(schema.getCoinsCode())
								&& this.getArr(dblSumOperatefee).getCoinsType()
										.equals(schema.getCoinsType())
								&& !this.getArr(dblSumOperatefee).getPayNo()
										.equals(schema.getPayNo())) {
							dblSumPlanfee += Double.parseDouble(this.getArr(
									dblSumOperatefee).getPlanFeeCNY());
						}
					}

					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium()) - dblSumPlanfee;
				} else {
					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium())
							* Double.parseDouble(iSchema.getPlanFeeCNY())
							/ dbSumPremium;
				}

				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
				if (Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema
						.getAgentFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					schema.setPayRefReason("P90");
					schema.setCertiType("S");
					schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
					dblPlanFee = Double.parseDouble(Str
							.chgStrZero(prpCcoinsDetailSchema.getAgentFee()));
					if (!iFlag) {
						dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
								.getAgentFee())
								* Double.parseDouble(iSchema.getPlanFeeCNY())
								/ dbSumPremium;
					} else {
						dblSumPlanfee = 0.0D;

						for (dblSumOperatefee = 0; dblSumOperatefee < this
								.getSize(); ++dblSumOperatefee) {
							if (this.getArr(dblSumOperatefee).getPayRefReason()
									.equals(schema.getPayRefReason())
									&& this.getArr(dblSumOperatefee)
											.getCoinsCode()
											.equals(schema.getCoinsCode())
									&& this.getArr(dblSumOperatefee)
											.getCoinsType()
											.equals(schema.getCoinsType())
									&& !this.getArr(dblSumOperatefee)
											.getPayNo()
											.equals(schema.getPayNo())) {
								dblSumPlanfee += Double.parseDouble(this
										.getArr(dblSumOperatefee)
										.getPlanFeeCNY());
							}
						}

						dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
								.getAgentFee()) - dblSumPlanfee;
					}

					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}

				BLPrpCcoins arg39 = new BLPrpCcoins();
				BLPrpCcoinsDetail arg41 = new BLPrpCcoinsDetail();
				arg39.query(dbpool, iWherePart + " AND SerialNo!=\'"
						+ blPrpCcoins.getArr(i).getSerialNo() + "\' ");
				arg41.query(dbpool, iWherePart + " AND SerialNo!=\'"
						+ blPrpCcoins.getArr(i).getSerialNo() + "\' ");
				PrpCcoinsDetailSchema arg43 = new PrpCcoinsDetailSchema();

				for (int arg40 = 0; arg40 < arg39.getSize(); ++arg40) {
					for (int arg42 = 0; arg42 < arg41.getSize(); ++arg42) {
						if (arg39.getArr(arg40).getSerialNo()
								.equals(arg41.getArr(arg42).getSerialNo())) {
							arg43 = arg41.getArr(arg42);
							break;
						}
					}

					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					strSerialNo = "" + intSerialNo;
					++intSerialNo;
					schema.setSerialNo(strSerialNo);
					if (arg39.getArr(arg40).getCoinsType().equals("3")) {
						schema.setPayRefReason("R81");
					} else if (arg39.getArr(arg40).getCoinsType().equals("2")) {
						schema.setPayRefReason("R82");
					}

					schema.setCertiType("P");
					schema.setCoinsCode(arg39.getArr(arg40).getCoinsCode());
					schema.setCoinsName(arg39.getArr(arg40).getCoinsName());
					schema.setCoinsType("1");
					dblPlanFee = Double.parseDouble(arg43.getCoinsPremium());
					int arg44;
					if (!iFlag) {
						dblPlanFee = Double
								.parseDouble(arg43.getCoinsPremium())
								* Double.parseDouble(iSchema.getPlanFeeCNY())
								/ dbSumPremium;
					} else {
						dbOperatefee = 0.0D;

						for (arg44 = 0; arg44 < this.getSize(); ++arg44) {
							if (this.getArr(arg44).getPayRefReason()
									.equals(schema.getPayRefReason())
									&& this.getArr(arg44).getCoinsCode()
											.equals(schema.getCoinsCode())
									&& this.getArr(arg44).getCoinsType()
											.equals(schema.getCoinsType())
									&& !this.getArr(arg44).getPayNo()
											.equals(schema.getPayNo())) {
								dbOperatefee += Double.parseDouble(this.getArr(
										arg44).getPlanFeeCNY());
							}
						}

						dblPlanFee = Double
								.parseDouble(arg43.getCoinsPremium())
								- dbOperatefee;
					}

					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(arg43.getCurrency2())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
					if (Double.parseDouble(Str.chgStrZero(arg43.getAgentFee())) != 0.0D) {
						schema = new PrpCJplanSchema();
						schema.setSchema(iSchema);
						schema.setSerialNo(strSerialNo);
						if (arg39.getArr(arg40).getCoinsType().equals("3")) {
							schema.setPayRefReason("P91");
						} else if (arg39.getArr(arg40).getCoinsType()
								.equals("2")) {
							schema.setPayRefReason("P92");
						}

						schema.setCertiType("S");
						schema.setCoinsCode(arg39.getArr(arg40).getCoinsCode());
						schema.setCoinsName(arg39.getArr(arg40).getCoinsName());
						schema.setCoinsType("1");
						dblPlanFee = Double.parseDouble(arg43.getAgentFee());
						if (iFlag) {
							dbOperatefee = 0.0D;

							for (arg44 = 0; arg44 < this.getSize(); ++arg44) {
								if (this.getArr(arg44).getPayRefReason()
										.equals(schema.getPayRefReason())
										&& this.getArr(arg44).getCoinsCode()
												.equals(schema.getCoinsCode())
										&& this.getArr(arg44).getCoinsType()
												.equals(schema.getCoinsType())
										&& !this.getArr(arg44).getPayNo()
												.equals(schema.getPayNo())) {
									dbOperatefee += Double.parseDouble(this
											.getArr(arg44).getPlanFeeCNY());
								}
							}

							dblPlanFee = Double
									.parseDouble(arg43.getAgentFee())
									- dbOperatefee;
						} else {
							dblPlanFee = Double
									.parseDouble(arg43.getAgentFee())
									* Double.parseDouble(iSchema
											.getPlanFeeCNY()) / dbSumPremium;
						}

						dblPlanFee = Str.round(dblPlanFee, 2);
						if ("JPY".equals(arg43.getCurrency2())) {
							schema.setPlanFee(""
									+ Str.round(
											dblPlanFee
													/ Double.parseDouble(Str.chgStrZero(schema
															.getExchangeRate())),
											0));
						} else {
							schema.setPlanFee(""
									+ Str.round(
											dblPlanFee
													/ Double.parseDouble(Str.chgStrZero(schema
															.getExchangeRate())),
											2));
						}

						schema.setPlanFeeCNY("" + dblPlanFee);
						this.setArr(schema);
					}
				}
			} else {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				if (blPrpCcoins.getArr(i).getCoinsType().equals("3")) {
					schema.setPayRefReason("P81");
				} else if (blPrpCcoins.getArr(i).getCoinsType().equals("2")) {
					schema.setPayRefReason("P82");
				}

				schema.setCertiType("P");
				schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
				dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
						.getCoinsPremium());
				if (iFlag) {
					dblSumPlanfee = 0.0D;

					for (dblSumOperatefee = 0; dblSumOperatefee < this
							.getSize(); ++dblSumOperatefee) {
						if (this.getArr(dblSumOperatefee).getPayRefReason()
								.equals(schema.getPayRefReason())
								&& this.getArr(dblSumOperatefee).getCoinsCode()
										.equals(schema.getCoinsCode())
								&& this.getArr(dblSumOperatefee).getCoinsType()
										.equals(schema.getCoinsType())
								&& !this.getArr(dblSumOperatefee).getPayNo()
										.equals(schema.getPayNo())) {
							dblSumPlanfee += -1.0D
									* Double.parseDouble(this.getArr(
											dblSumOperatefee).getPlanFeeCNY());
						}
					}

					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium()) - dblSumPlanfee;
					dblPlanFee *= -1.0D;
				} else {
					dblPlanFee = -1.0D
							* Double.parseDouble(prpCcoinsDetailSchema
									.getCoinsPremium())
							* Double.parseDouble(iSchema.getPlanFeeCNY())
							/ dbSumPremium;
				}

				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
				if (Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema
						.getAgentFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					if (blPrpCcoins.getArr(i).getCoinsType().equals("3")) {
						schema.setPayRefReason("R91");
					} else if (blPrpCcoins.getArr(i).getCoinsType().equals("2")) {
						schema.setPayRefReason("R92");
					}

					schema.setCertiType("S");
					schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getAgentFee());
					if (!iFlag) {
						dblPlanFee = -1.0D
								* Double.parseDouble(prpCcoinsDetailSchema
										.getAgentFee())
								* Double.parseDouble(iSchema.getPlanFeeCNY())
								/ dbSumPremium;
					} else {
						dblSumPlanfee = 0.0D;

						for (dblSumOperatefee = 0; dblSumOperatefee < this
								.getSize(); ++dblSumOperatefee) {
							if (this.getArr(dblSumOperatefee).getPayRefReason()
									.equals(schema.getPayRefReason())
									&& this.getArr(dblSumOperatefee)
											.getCoinsCode()
											.equals(schema.getCoinsCode())
									&& this.getArr(dblSumOperatefee)
											.getCoinsType()
											.equals(schema.getCoinsType())
									&& !this.getArr(dblSumOperatefee)
											.getPayNo()
											.equals(schema.getPayNo())) {
								dblSumPlanfee += -1.0D
										* Double.parseDouble(this.getArr(
												dblSumOperatefee)
												.getPlanFeeCNY());
							}
						}

						dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
								.getAgentFee()) - dblSumPlanfee;
						dblPlanFee *= -1.0D;
					}

					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}

				if (iFlag
						&& !blPrpCcoins.getArr(i).getCoinsType().equals("1")
						&& Double.parseDouble(Str
								.chgStrZero(prpCcoinsDetailSchema
										.getOperateFee())) != 0.0D) {
					dblSumPlanfee = 0.0D;
					double arg38 = 0.0D;
					dbOperatefee = 0.0D;
					double dbOperatefeeUSD = 0.0D;
					double dblSumOperatefeeUSD = 0.0D;
					dbOperatefee = Double.parseDouble(Str
							.chgStrZero(prpCcoinsDetailSchema.getOperateFee()));
					if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
						dbOperatefeeUSD = Str
								.round(Double.parseDouble(Str
										.chgStrZero(prpCcoinsDetailSchema
												.getOperateFee()))
										/ Double.parseDouble(Str
												.chgStrZero(schema
														.getExchangeRate())), 0);
					} else if (!"CNY".equals(prpCcoinsDetailSchema
							.getCurrency2())) {
						dbOperatefeeUSD = Str
								.round(Double.parseDouble(Str
										.chgStrZero(prpCcoinsDetailSchema
												.getOperateFee()))
										/ Double.parseDouble(Str
												.chgStrZero(schema
														.getExchangeRate())), 2);
					}

					BLPrpCplan blPrpCplan = new BLPrpCplan();
					blPrpCplan.query(dbpool, iWherePart);

					int g;
					for (g = 0; g < blPrpCplan.getSize(); ++g) {
						if ("R10".equals(blPrpCplan.getArr(g).getPayReason())
								|| "R20".equals(blPrpCplan.getArr(g)
										.getPayReason())) {
							dblSumPlanfee += Double.parseDouble(blPrpCplan
									.getArr(g).getPlanFee());
						}
					}

					for (g = 0; g < blPrpCplan.getSize(); ++g) {
						double dblOperatefee = 0.0D;
						double dblOperatefeeUSD = 0.0D;
						double dblOperatefeeUSDLast = 0.0D;
						schema = new PrpCJplanSchema();
						schema.setSchema(iSchema);
						schema.setSerialNo("" + intSerialNoOperatefee);
						++intSerialNoOperatefee;
						schema.setPayRefReason("P95");
						schema.setCertiType("F");
						schema.setPayNo(blPrpCplan.getArr(g).getPayNo());
						schema.setCoinsCode(blPrpCcoins.getArr(i)
								.getCoinsCode());
						schema.setCoinsName(blPrpCcoins.getArr(i)
								.getCoinsName());
						schema.setCoinsType(blPrpCcoins.getArr(i)
								.getCoinsType());
						dblOperatefee = Double.parseDouble(Str
								.chgStrZero(prpCcoinsDetailSchema
										.getOperateFee()))
								* Double.parseDouble(Str.chgStrZero(blPrpCplan
										.getArr(g).getPlanFee()))
								/ dblSumPlanfee;
						if (g == blPrpCplan.getSize() - 1) {
							dblOperatefee = dbOperatefee - arg38;
							if (!"CNY".equals(prpCcoinsDetailSchema
									.getCurrency2())) {
								dblOperatefeeUSDLast = dbOperatefeeUSD
										- dblSumOperatefeeUSD;
							}
						}

						if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
							schema.setPlanFee(String.valueOf(Str.round(
									dblOperatefee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0)));
							dblOperatefeeUSD = Str
									.round(dblOperatefee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
											0);
						} else {
							schema.setPlanFee(String.valueOf(Str.round(
									dblOperatefee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2)));
							dblOperatefeeUSD = Str
									.round(dblOperatefee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
											2);
						}

						if (g == blPrpCplan.getSize() - 1
								&& !"CNY".equals(prpCcoinsDetailSchema
										.getCurrency2())) {
							schema.setPlanFee(String.valueOf(Str.round(
									dblOperatefeeUSDLast, 2)));
						}

						schema.setPlanFeeCNY(String.valueOf(Str.round(
								dblOperatefee, 2)));
						if (g != blPrpCplan.getSize() - 1) {
							arg38 += Str.round(dblOperatefee, 2);
							if (!"CNY".equals(prpCcoinsDetailSchema
									.getCurrency2())) {
								dblSumOperatefeeUSD += dblOperatefeeUSD;
							}
						}

						schema.setIsCombin("0");
						this.setArr(schema);
					}
				}
			}
		}

	}

	public void transCoinsTPolicy(DbPool dbpool, String iWherePart,
			PrpCJplanSchema iSchema, double dbSumPremium, boolean iFlag)
			throws Exception {
		BLPrpTcoins blPrpCcoins = new BLPrpTcoins();
		BLPrpTcoinsDetail blPrpCcoinsDetail = new BLPrpTcoinsDetail();
		blPrpCcoins.query(dbpool, iWherePart);
		blPrpCcoinsDetail.query(dbpool, iWherePart);
		PrpCJplanSchema schema = null;
		String strSerialNo = "";
		double dblPlanFee = 0.0D;
		int intSerialNo = 1 + (Integer.parseInt(iSchema.getPayNo()) - 1)
				* blPrpCcoins.getSize()
				+ (Integer.parseInt(iSchema.getPayNo()) - 1)
				* (blPrpCcoins.getSize() - 1);
		PrpTcoinsDetailSchema prpCcoinsDetailSchema = new PrpTcoinsDetailSchema();

		for (int i = 0; i < blPrpCcoins.getSize(); ++i) {
			for (int strPayrefReason = 0; strPayrefReason < blPrpCcoinsDetail
					.getSize(); ++strPayrefReason) {
				if (blPrpCcoins
						.getArr(i)
						.getSerialNo()
						.equals(blPrpCcoinsDetail.getArr(strPayrefReason)
								.getSerialNo())) {
					prpCcoinsDetailSchema = blPrpCcoinsDetail
							.getArr(strPayrefReason);
					break;
				}
			}

			String arg23 = iSchema.getPayRefReason();
			double dblPlanFeeSum;
			int k;
			if (blPrpCcoins.getArr(i).getCoinsType().equals("1")) {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				if ("R00".equals(schema.getPayRefReason())) {
					schema.setPayRefReason("R00");
				} else {
					schema.setPayRefReason("R10");
				}

				schema.setCertiType("T");
				schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
				dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
						.getCoinsPremium());
				if (iFlag) {
					dblPlanFeeSum = 0.0D;

					for (k = 0; k < this.getSize(); ++k) {
						if (this.getArr(k).getPayRefReason()
								.equals(schema.getPayRefReason())
								&& this.getArr(k).getCoinsCode()
										.equals(schema.getCoinsCode())
								&& this.getArr(k).getCoinsType()
										.equals(schema.getCoinsType())
								&& !this.getArr(k).getPayNo()
										.equals(schema.getPayNo())) {
							dblPlanFeeSum += Double.parseDouble(this.getArr(k)
									.getPlanFeeCNY());
						}
					}

					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium()) - dblPlanFeeSum;
				} else {
					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium())
							* Double.parseDouble(iSchema.getPlanFeeCNY())
							/ dbSumPremium;
				}

				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
				if (Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema
						.getAgentFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					schema.setPayRefReason("P90");
					schema.setCertiType("S");
					schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getAgentFee());
					if (!iFlag) {
						dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
								.getAgentFee())
								* Double.parseDouble(iSchema.getPlanFeeCNY())
								/ dbSumPremium;
					} else {
						dblPlanFeeSum = 0.0D;

						for (k = 0; k < this.getSize(); ++k) {
							if (this.getArr(k).getPayRefReason()
									.equals(schema.getPayRefReason())
									&& this.getArr(k).getCoinsCode()
											.equals(schema.getCoinsCode())
									&& this.getArr(k).getCoinsType()
											.equals(schema.getCoinsType())
									&& !this.getArr(k).getPayNo()
											.equals(schema.getPayNo())) {
								dblPlanFeeSum += Double.parseDouble(this
										.getArr(k).getPlanFeeCNY());
							}
						}

						dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
								.getAgentFee()) - dblPlanFeeSum;
					}

					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}

				BLPrpTcoins arg24 = new BLPrpTcoins();
				BLPrpTcoinsDetail blPrpTcoinsDetail1 = new BLPrpTcoinsDetail();
				arg24.query(dbpool, iWherePart + " AND SerialNo!=\'"
						+ blPrpCcoins.getArr(i).getSerialNo() + "\' ");
				blPrpTcoinsDetail1.query(dbpool, iWherePart
						+ " AND SerialNo!=\'"
						+ blPrpCcoins.getArr(i).getSerialNo() + "\' ");
				PrpTcoinsDetailSchema arg25 = new PrpTcoinsDetailSchema();

				for (int m = 0; m < arg24.getSize(); ++m) {
					for (int dblPlanFeeSum1 = 0; dblPlanFeeSum1 < blPrpTcoinsDetail1
							.getSize(); ++dblPlanFeeSum1) {
						if (arg24
								.getArr(m)
								.getSerialNo()
								.equals(blPrpTcoinsDetail1.getArr(
										dblPlanFeeSum1).getSerialNo())) {
							arg25 = blPrpTcoinsDetail1.getArr(dblPlanFeeSum1);
							break;
						}
					}

					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					strSerialNo = "" + intSerialNo;
					++intSerialNo;
					schema.setSerialNo(strSerialNo);
					if (arg24.getArr(m).getCoinsType().equals("3")) {
						schema.setPayRefReason("R81");
					} else if (arg24.getArr(m).getCoinsType().equals("2")) {
						schema.setPayRefReason("R82");
					}

					schema.setCertiType("T");
					schema.setCoinsCode(arg24.getArr(m).getCoinsCode());
					schema.setCoinsName(arg24.getArr(m).getCoinsName());
					schema.setCoinsType(arg24.getArr(m).getCoinsType());
					dblPlanFee = Double.parseDouble(arg25.getCoinsPremium());
					int k1;
					double arg26;
					if (!iFlag) {
						dblPlanFee = Double
								.parseDouble(arg25.getCoinsPremium())
								* Double.parseDouble(iSchema.getPlanFeeCNY())
								/ dbSumPremium;
					} else {
						arg26 = 0.0D;

						for (k1 = 0; k1 < this.getSize(); ++k1) {
							if (this.getArr(k1).getPayRefReason()
									.equals(schema.getPayRefReason())
									&& this.getArr(k1).getCoinsCode()
											.equals(schema.getCoinsCode())
									&& this.getArr(k1).getCoinsType()
											.equals(schema.getCoinsType())
									&& !this.getArr(k1).getPayNo()
											.equals(schema.getPayNo())) {
								arg26 += Double.parseDouble(this.getArr(k1)
										.getPlanFeeCNY());
							}
						}

						dblPlanFee = Double
								.parseDouble(arg25.getCoinsPremium()) - arg26;
					}

					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(arg25.getCurrency2())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
					if (Double.parseDouble(Str.chgStrZero(arg25.getAgentFee())) != 0.0D) {
						schema = new PrpCJplanSchema();
						schema.setSchema(iSchema);
						schema.setSerialNo(strSerialNo);
						if (arg24.getArr(m).getCoinsType().equals("3")) {
							schema.setPayRefReason("P91");
						} else if (arg24.getArr(m).getCoinsType().equals("2")) {
							schema.setPayRefReason("P92");
						}

						schema.setCertiType("S");
						schema.setCoinsCode(arg24.getArr(m).getCoinsCode());
						schema.setCoinsName(arg24.getArr(m).getCoinsName());
						schema.setCoinsType(arg24.getArr(m).getCoinsType());
						dblPlanFee = Double.parseDouble(arg25.getAgentFee());
						if (iFlag) {
							arg26 = 0.0D;

							for (k1 = 0; k1 < this.getSize(); ++k1) {
								if (this.getArr(k1).getPayRefReason()
										.equals(schema.getPayRefReason())
										&& this.getArr(k1).getCoinsCode()
												.equals(schema.getCoinsCode())
										&& this.getArr(k1).getCoinsType()
												.equals(schema.getCoinsType())
										&& !this.getArr(k1).getPayNo()
												.equals(schema.getPayNo())) {
									arg26 += Double.parseDouble(this.getArr(k1)
											.getPlanFeeCNY());
								}
							}

							dblPlanFee = Double
									.parseDouble(arg25.getAgentFee()) - arg26;
						} else {
							dblPlanFee = Double
									.parseDouble(arg25.getAgentFee())
									* Double.parseDouble(iSchema
											.getPlanFeeCNY()) / dbSumPremium;
						}

						dblPlanFee = Str.round(dblPlanFee, 2);
						if ("JPY".equals(arg25.getCurrency2())) {
							schema.setPlanFee(""
									+ Str.round(
											dblPlanFee
													/ Double.parseDouble(Str.chgStrZero(schema
															.getExchangeRate())),
											0));
						} else {
							schema.setPlanFee(""
									+ Str.round(
											dblPlanFee
													/ Double.parseDouble(Str.chgStrZero(schema
															.getExchangeRate())),
											2));
						}

						schema.setPlanFeeCNY("" + dblPlanFee);
						this.setArr(schema);
					}
				}
			} else {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				if (blPrpCcoins.getArr(i).getCoinsType().equals("3")) {
					schema.setPayRefReason("P81");
				} else if (blPrpCcoins.getArr(i).getCoinsType().equals("2")) {
					schema.setPayRefReason("P82");
				}

				schema.setCertiType("T");
				schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
				dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
						.getCoinsPremium());
				if (iFlag) {
					dblPlanFeeSum = 0.0D;

					for (k = 0; k < this.getSize(); ++k) {
						if (this.getArr(k).getPayRefReason()
								.equals(schema.getPayRefReason())
								&& this.getArr(k).getCoinsCode()
										.equals(schema.getCoinsCode())
								&& this.getArr(k).getCoinsType()
										.equals(schema.getCoinsType())
								&& !this.getArr(k).getPayNo()
										.equals(schema.getPayNo())) {
							dblPlanFeeSum += -1.0D
									* Double.parseDouble(this.getArr(k)
											.getPlanFeeCNY());
						}
					}

					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium()) - dblPlanFeeSum;
					dblPlanFee *= -1.0D;
				} else {
					dblPlanFee = -1.0D
							* Double.parseDouble(prpCcoinsDetailSchema
									.getCoinsPremium())
							* Double.parseDouble(iSchema.getPlanFeeCNY())
							/ dbSumPremium;
				}

				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
				if (Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema
						.getAgentFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					if (blPrpCcoins.getArr(i).getCoinsType().equals("3")) {
						schema.setPayRefReason("R91");
					} else if (blPrpCcoins.getArr(i).getCoinsType().equals("2")) {
						schema.setPayRefReason("R92");
					}

					schema.setCertiType("S");
					schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getAgentFee());
					if (!iFlag) {
						dblPlanFee = -1.0D
								* Double.parseDouble(prpCcoinsDetailSchema
										.getAgentFee())
								* Double.parseDouble(iSchema.getPlanFeeCNY())
								/ dbSumPremium;
					} else {
						dblPlanFeeSum = 0.0D;

						for (k = 0; k < this.getSize(); ++k) {
							if (this.getArr(k).getPayRefReason()
									.equals(schema.getPayRefReason())
									&& this.getArr(k).getCoinsCode()
											.equals(schema.getCoinsCode())
									&& this.getArr(k).getCoinsType()
											.equals(schema.getCoinsType())
									&& !this.getArr(k).getPayNo()
											.equals(schema.getPayNo())) {
								dblPlanFeeSum += -1.0D
										* Double.parseDouble(this.getArr(k)
												.getPlanFeeCNY());
							}
						}

						dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
								.getAgentFee()) - dblPlanFeeSum;
						dblPlanFee *= -1.0D;
					}

					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}

				if (iFlag
						&& !blPrpCcoins.getArr(i).getCoinsType().equals("1")
						&& Double.parseDouble(Str
								.chgStrZero(prpCcoinsDetailSchema
										.getOperateFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					schema.setPayRefReason("P95");
					schema.setCertiType("F");
					schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
					if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
						schema.setPlanFee(String.valueOf(Str.round(
								Double.parseDouble(Str
										.chgStrZero(prpCcoinsDetailSchema
												.getOperateFee()))
										/ Double.parseDouble(Str
												.chgStrZero(schema
														.getExchangeRate())), 0)));
					} else {
						schema.setPlanFee(String.valueOf(Str.round(
								Double.parseDouble(Str
										.chgStrZero(prpCcoinsDetailSchema
												.getOperateFee()))
										/ Double.parseDouble(Str
												.chgStrZero(schema
														.getExchangeRate())), 2)));
					}

					schema.setPlanFeeCNY(prpCcoinsDetailSchema.getOperateFee());
					schema.setIsCombin("0");
					this.setArr(schema);
				}
			}
		}

	}

	public void transCoinsEndorse(DbPool dbpool, String iWherePart,
			PrpCJplanSchema iSchema, double dbSumPremium, boolean iFlag)
			throws Exception {
		BLPrpCcoins blPrpPcoins = new BLPrpCcoins();
		BLPrpPcoinsDetail blPrpPcoinsDetail = new BLPrpPcoinsDetail();
		blPrpPcoinsDetail.query(dbpool, iWherePart);
		if (blPrpPcoinsDetail.getSize() > 0) {
			blPrpPcoins.query(dbpool,
					"PolicyNo=\'" + blPrpPcoinsDetail.getArr(0).getPolicyNo()
							+ "\'");
		}

		PrpCJplanSchema schema = null;
		String strSerialNo = "";
		int intserialno = 1;
		double dblPlanFee = 0.0D;
		int intSerialNo = 1 + (Integer.parseInt(iSchema.getPayNo()) - 1)
				* blPrpPcoins.getSize()
				+ (Integer.parseInt(iSchema.getPayNo()) - 1)
				* (blPrpPcoins.getSize() - 1);
		PrpPcoinsDetailSchema prpPcoinsDetailSchema = new PrpPcoinsDetailSchema();
		BLPrpPcharge blPrpPcharge = new BLPrpPcharge();
		blPrpPcharge.query(dbpool, iWherePart);

		int i;
		for (i = 0; i < blPrpPcharge.getSize(); ++i) {
			if (Double.parseDouble(Str.chgStrZero(blPrpPcharge.getArr(i)
					.getChgPlanFee2())) != 0.0D) {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				schema.setSerialNo(strSerialNo);
				if ("001".equals(blPrpPcharge.getArr(i).getChargeCode())) {
					if (Double.parseDouble(blPrpPcharge.getArr(i)
							.getChgPlanFee2()) > 0.0D) {
						schema.setPayRefReason("P38");
					} else {
						schema.setPayRefReason("R37");
					}
				} else if ("002".equals(blPrpPcharge.getArr(i).getChargeCode())) {
					schema.setPayRefReason("T60");
				} else {
					schema.setPayRefReason("R37");
				}

				schema.setCertiType("F");
				schema.setValidDate(iSchema.getStartDate());
				dblPlanFee = Double.parseDouble(blPrpPcharge.getArr(i)
						.getChgPlanFee());
				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(schema.getPlanFeeCurrency())) {
					schema.setPlanFee(""
							+ Str.round(Double.parseDouble(blPrpPcharge.getArr(
									i).getChgPlanFee2()), 0));
				} else {
					schema.setPlanFee(""
							+ Str.round(Double.parseDouble(blPrpPcharge.getArr(
									i).getChgPlanFee2()), 2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
			}
		}

		for (i = 0; i < blPrpPcoins.getSize(); ++i) {
			for (int blPrpPcoins1 = 0; blPrpPcoins1 < blPrpPcoinsDetail
					.getSize(); ++blPrpPcoins1) {
				if (blPrpPcoins
						.getArr(i)
						.getSerialNo()
						.equals(blPrpPcoinsDetail.getArr(blPrpPcoins1)
								.getSerialNo())) {
					prpPcoinsDetailSchema = blPrpPcoinsDetail
							.getArr(blPrpPcoins1);
					break;
				}
			}

			if (!blPrpPcoins.getArr(i).getCoinsType().equals("1")) {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				if (blPrpPcoins.getArr(i).getCoinsType().equals("3")) {
					schema.setPayRefReason("P81");
				} else if (blPrpPcoins.getArr(i).getCoinsType().equals("2")) {
					schema.setPayRefReason("P82");
				}

				schema.setCertiType("E");
				schema.setValidDate(iSchema.getStartDate());
				schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
				dblPlanFee = -1.0D
						* Double.parseDouble(prpPcoinsDetailSchema
								.getChgCoinsPremium());
				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(schema.getPlanFeeCurrency())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
				if (Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema
						.getChgAgentFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					if (blPrpPcoins.getArr(i).getCoinsType().equals("3")) {
						schema.setPayRefReason("R91");
					} else if (blPrpPcoins.getArr(i).getCoinsType().equals("2")) {
						schema.setPayRefReason("R92");
					}

					schema.setCertiType("S");
					schema.setValidDate(iSchema.getStartDate());
					schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
					dblPlanFee = -1.0D
							* Double.parseDouble(prpPcoinsDetailSchema
									.getChgAgentFee());
					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(schema.getPlanFeeCurrency())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}

				if (Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema
						.getChgOperateFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo("" + intserialno);
					++intserialno;
					schema.setPayRefReason("P95");
					schema.setCertiType("F");
					schema.setValidDate(iSchema.getStartDate());
					schema.setPayNo("1");
					schema.setTotalPayNo("1");
					schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
					dblPlanFee = Double.parseDouble(prpPcoinsDetailSchema
							.getChgOperateFee());
					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(schema.getPlanFeeCurrency())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}
			} else {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				schema.setCertiType("E");
				schema.setValidDate(iSchema.getStartDate());
				schema.setEndorType(iSchema.getEndorType());
				schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
				dblPlanFee = Double.parseDouble(prpPcoinsDetailSchema
						.getChgCoinsPremium());
				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(iSchema.getPlanFeeCurrency())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
				if (Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema
						.getChgAgentFee())) != 0.0D) {
					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					schema.setSerialNo(strSerialNo);
					schema.setPayRefReason("P90");
					schema.setCertiType("S");
					schema.setValidDate(iSchema.getStartDate());
					schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
					schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
					schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
					dblPlanFee = Double.parseDouble(prpPcoinsDetailSchema
							.getChgAgentFee());
					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(schema.getPlanFeeCurrency())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
				}

				BLPrpCcoins arg22 = new BLPrpCcoins();
				BLPrpPcoinsDetail blPrpPcoinsDetail1 = new BLPrpPcoinsDetail();
				blPrpPcoinsDetail1.query(dbpool, iWherePart
						+ " AND SerialNo!=\'"
						+ blPrpPcoins.getArr(i).getSerialNo() + "\' ");
				if (blPrpPcoinsDetail1.getSize() > 0) {
					arg22.query(dbpool, "PolicyNo=\'"
							+ blPrpPcoinsDetail1.getArr(0).getPolicyNo()
							+ "\' AND SerialNo!=\'"
							+ blPrpPcoins.getArr(i).getSerialNo() + "\' ");
				}

				PrpPcoinsDetailSchema prpPcoinsDetailSchema1 = new PrpPcoinsDetailSchema();

				for (int m = 0; m < arg22.getSize(); ++m) {
					for (int n = 0; n < blPrpPcoinsDetail1.getSize(); ++n) {
						if (arg22
								.getArr(m)
								.getSerialNo()
								.equals(blPrpPcoinsDetail1.getArr(n)
										.getSerialNo())) {
							prpPcoinsDetailSchema1 = blPrpPcoinsDetail1
									.getArr(n);
							break;
						}
					}

					schema = new PrpCJplanSchema();
					schema.setSchema(iSchema);
					strSerialNo = "" + intSerialNo;
					++intSerialNo;
					schema.setSerialNo(strSerialNo);
					if (arg22.getArr(m).getCoinsType().equals("3")) {
						schema.setPayRefReason("R81");
					} else if (arg22.getArr(m).getCoinsType().equals("2")) {
						schema.setPayRefReason("R82");
					}

					schema.setCertiType("E");
					schema.setCoinsCode(arg22.getArr(m).getCoinsCode());
					schema.setCoinsName(arg22.getArr(m).getCoinsName());
					schema.setCoinsType("1");
					schema.setValidDate(iSchema.getStartDate());
					dblPlanFee = Double.parseDouble(prpPcoinsDetailSchema1
							.getChgCoinsPremium());
					dblPlanFee = Str.round(dblPlanFee, 2);
					if ("JPY".equals(schema.getPlanFeeCurrency())) {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 0));
					} else {
						schema.setPlanFee(""
								+ Str.round(
										dblPlanFee
												/ Double.parseDouble(Str.chgStrZero(schema
														.getExchangeRate())), 2));
					}

					schema.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(schema);
					if (Double
							.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema1
									.getChgAgentFee())) != 0.0D) {
						schema = new PrpCJplanSchema();
						schema.setSchema(iSchema);
						schema.setSerialNo(strSerialNo);
						if (arg22.getArr(m).getCoinsType().equals("3")) {
							schema.setPayRefReason("P91");
						} else if (arg22.getArr(m).getCoinsType().equals("2")) {
							schema.setPayRefReason("P92");
						}

						schema.setCertiType("S");
						schema.setValidDate(iSchema.getStartDate());
						schema.setCoinsCode(arg22.getArr(m).getCoinsCode());
						schema.setCoinsName(arg22.getArr(m).getCoinsName());
						schema.setCoinsType("1");
						dblPlanFee = Double.parseDouble(prpPcoinsDetailSchema1
								.getChgAgentFee());
						dblPlanFee = Str.round(dblPlanFee, 2);
						if ("JPY".equals(schema.getPlanFeeCurrency())) {
							schema.setPlanFee(""
									+ Str.round(
											dblPlanFee
													/ Double.parseDouble(Str.chgStrZero(schema
															.getExchangeRate())),
											0));
						} else {
							schema.setPlanFee(""
									+ Str.round(
											dblPlanFee
													/ Double.parseDouble(Str.chgStrZero(schema
															.getExchangeRate())),
											2));
						}

						schema.setPlanFeeCNY("" + dblPlanFee);
						this.setArr(schema);
					}
				}
			}
		}

	}

	public void transPolicyCommission(DbPool dbpool, String iWherePart,
			int iCount) throws Exception {
		BLPrpCCommission blPrpCCommission = new BLPrpCCommission();
		blPrpCCommission.query(dbpool, iWherePart);
		if (blPrpCCommission.getSize() != 0) {
			double dblDisFee = Double.parseDouble(blPrpCCommission.getArr(0)
					.getCostFee());
			double dblLeftFee = dblDisFee;
			double dblPlanFee = 0.0D;
			byte intSameCount = 0;
			if (dblDisFee != 0.0D) {
				double dblSumPremium = 0.0D;

				for (int schema = 0; schema < iCount; ++schema) {
					dblSumPremium += Double.parseDouble(this.getArr(schema)
							.getPlanFee());
				}

				PrpCJplanSchema arg15 = null;

				for (int i = 0; i < iCount; ++i) {
					arg15 = new PrpCJplanSchema();
					arg15.setSchema(this.getArr(i));
					arg15.setSerialNo(""
							+ (Integer.parseInt(arg15.getSerialNo()) + intSameCount));
					arg15.setCertiType("S");
					arg15.setPayRefReason("P90");
					if (i == iCount - 1) {
						dblPlanFee = dblLeftFee;
					} else {
						dblPlanFee = dblDisFee
								* (Double.parseDouble(this.getArr(i)
										.getPlanFee()) / dblSumPremium);
					}

					dblPlanFee = Str.round(Str.round(dblPlanFee, 8), 2);
					dblLeftFee -= dblPlanFee;
					arg15.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(arg15
															.getExchangeRate())),
									2));
					arg15.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(arg15);
				}

				this.splitToKindPolicy(dbpool, "S", this.getArr(0)
						.getRiskCode());
			}
		}
	}

	public void transCommission(DbPool dbpool, String iWherePart, int iCount)
			throws Exception {
		BLPrpTCommission blPrpTCommission = new BLPrpTCommission();
		blPrpTCommission.query(dbpool, iWherePart);
		if (blPrpTCommission.getSize() != 0) {
			double dblDisFee = Double.parseDouble(blPrpTCommission.getArr(0)
					.getCostFee());
			double dblLeftFee = dblDisFee;
			double dblPlanFee = 0.0D;
			byte intSameCount = 0;
			if (dblDisFee != 0.0D) {
				double dblSumPremium = 0.0D;

				for (int schema = 0; schema < iCount; ++schema) {
					dblSumPremium += Double.parseDouble(this.getArr(schema)
							.getPlanFee());
				}

				PrpCJplanSchema arg15 = null;

				for (int i = 0; i < iCount; ++i) {
					arg15 = new PrpCJplanSchema();
					arg15.setSchema(this.getArr(i));
					arg15.setSerialNo(""
							+ (Integer.parseInt(arg15.getSerialNo()) + intSameCount));
					arg15.setCertiType("S");
					arg15.setPayRefReason("P90");
					if (i == iCount - 1) {
						dblPlanFee = dblLeftFee;
					} else {
						dblPlanFee = dblDisFee
								* (Double.parseDouble(this.getArr(i)
										.getPlanFee()) / dblSumPremium);
					}

					dblPlanFee = Str.round(Str.round(dblPlanFee, 8), 2);
					dblLeftFee -= dblPlanFee;
					arg15.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(arg15
															.getExchangeRate())),
									2));
					arg15.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(arg15);
				}

				this.splitToKind(dbpool, "S", this.getArr(0).getRiskCode());
			}
		}
	}

	public void transCommissionQ(DbPool dbpool, String iWherePart, int iCount)
			throws Exception {
		BLPrpTCommission blPrpTCommission = new BLPrpTCommission();
		blPrpTCommission.query(dbpool, iWherePart);
		if (blPrpTCommission.getSize() != 0) {
			double dblDisFee = Double.parseDouble(blPrpTCommission.getArr(0)
					.getCostFee());
			double dblLeftFee = dblDisFee;
			double dblPlanFee = 0.0D;
			byte intSameCount = 0;
			if (dblDisFee != 0.0D) {
				double dblSumPremium = 0.0D;

				for (int schema = 0; schema < iCount; ++schema) {
					dblSumPremium += Double.parseDouble(this.getArr(schema)
							.getPlanFee());
				}

				PrpCJplanSchema arg15 = null;

				for (int i = 0; i < iCount; ++i) {
					arg15 = new PrpCJplanSchema();
					arg15.setSchema(this.getArr(i));
					arg15.setSerialNo(""
							+ (Integer.parseInt(arg15.getSerialNo()) + intSameCount));
					arg15.setCertiType("S");
					arg15.setPayRefReason("P90");
					if (i == iCount - 1) {
						dblPlanFee = dblLeftFee;
					} else {
						dblPlanFee = dblDisFee
								* (Double.parseDouble(this.getArr(i)
										.getPlanFee()) / dblSumPremium);
					}

					dblPlanFee = Str.round(Str.round(dblPlanFee, 8), 2);
					dblLeftFee -= dblPlanFee;
					arg15.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(arg15
															.getExchangeRate())),
									2));
					arg15.setPlanFeeCNY("" + dblPlanFee);
					this.setArr(arg15);
				}

				this.splitToKindQ(dbpool, "S", this.getArr(0).getRiskCode());
			}
		}
	}

	public void transCommission(DbPool dbpool, String iWherePart, int iCount,
			DBPrpPhead dbPrpPhead, DBPrpCmain dbPrpCmain) throws Exception {
		if (dbPrpPhead.getEndorType().equals("19")
				&& dbPrpPhead.getClassCode().equals("29")) {
			iCount = 0;
		}

		BLPrpPCommission blPrpPCommission = new BLPrpPCommission();
		blPrpPCommission.query(dbpool, iWherePart);
		if (blPrpPCommission.getSize() != 0) {
			double dblDisFee = Double.parseDouble(blPrpPCommission.getArr(0)
					.getChgCostFee());
			double dblLeftFee = dblDisFee;
			double dblPlanFee = 0.0D;
			boolean isCombin = false;
			if (dblDisFee != 0.0D) {
				double dblSumPremium = 0.0D;

				for (int dbPrpDrisk = 0; dbPrpDrisk < iCount; ++dbPrpDrisk) {
					dblSumPremium += Double.parseDouble(this.getArr(dbPrpDrisk)
							.getPlanFeeCNY());
				}

				DBPrpDrisk arg33 = new DBPrpDrisk();
				arg33.getInfo(dbpool, dbPrpPhead.getRiskCode());
				if (arg33.getFlag().length() >= 2
						&& arg33.getFlag().substring(1, 2).equals("2")) {
					isCombin = true;
				}

				String strCarTypeCode = "";
				DBPrpCitemCarExt dbPrpCitemCarExt = new DBPrpCitemCarExt();
				dbPrpCitemCarExt.getInfo(dbpool, dbPrpPhead.getPolicyNo());
				strCarTypeCode = dbPrpCitemCarExt.getCartypeCode();
				PrpCJplanSchema schema = null;
				String strEndorType = "";
				strEndorType = dbPrpPhead.getEndorType();
				BLPrpDagent blPrpDagent = null;
				BLPrpDuser blPrpDuser = null;
				BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
				boolean isChinese = true;
				String strCenterCode = "";
				String strBranchCode = "";
				String strWherePart = "ComCode=\'" + dbPrpPhead.getComCode()
						+ "\'";
				blPrpDcompany.query(dbpool, strWherePart);
				strCenterCode = blPrpDcompany.getArr(0).getAcntUnit();
				strBranchCode = strCenterCode;
				if (strCenterCode == null || strCenterCode.equals("")) {
					strBranchCode = strCenterCode;
				}

				if (!"".equals(strEndorType) && strEndorType != null) {
					int k = 0;

					for (int size = blPrpPCommission.getSize(); k < size; ++k) {
						String strJFeeFlag;
						if (strEndorType.indexOf("57") <= -1
								&& strEndorType.indexOf("89") <= -1) {
							for (int arg34 = 0; arg34 < iCount; ++arg34) {
								schema = new PrpCJplanSchema();
								schema.setSchema(this.getArr(arg34));
								schema.setCertiType("S");
								schema.setPayRefReason("P90");
								schema.setPlanFeeCurrency(dbPrpCmain
										.getCurrency());
								if (arg34 == iCount - 1) {
									dblPlanFee = dblLeftFee;
								} else {
									dblPlanFee = dblDisFee
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFeeCNY()) / dblSumPremium);
								}

								dblPlanFee = Str.round(
										Str.round(dblPlanFee, 8), 2);
								dblLeftFee -= dblPlanFee;
								schema.setPlanFee(""
										+ Str.round(
												dblPlanFee
														/ Double.parseDouble(Str
																.chgStrZero(schema
																		.getExchangeRate())),
												2));
								schema.setPlanFeeCNY("" + dblPlanFee);
								this.setArr(schema);
							}

							if (iCount == 0) {
								schema = new PrpCJplanSchema();
								schema.setCertiType("S");
								schema.setCertiNo(dbPrpPhead.getEndorseNo());
								schema.setSerialNo("1");
								schema.setPolicyNo(dbPrpPhead.getPolicyNo());
								schema.setPayRefReason("P90");
								schema.setClassCode(dbPrpCmain.getClassCode());
								schema.setRiskCode(dbPrpCmain.getRiskCode());
								schema.setContractNo(dbPrpCmain.getContractNo());
								schema.setAppliCode(dbPrpCmain.getAppliCode());
								schema.setAppliName(dbPrpCmain.getAppliName());
								schema.setInsuredCode(dbPrpCmain
										.getInsuredCode());
								schema.setInsuredName(dbPrpCmain
										.getInsuredName());
								schema.setStartDate(dbPrpPhead.getValidDate());
								schema.setEndDate(dbPrpCmain.getEndDate());
								schema.setValidDate(dbPrpPhead.getValidDate());
								schema.setEndorType(strEndorType);
								schema.setPayNo("1");
								schema.setTotalPayNo("1");
								schema.setPlanFeeCurrency(dbPrpCmain
										.getCurrency());
								schema.setPlanFee(blPrpPCommission.getArr(k)
										.getChgCostFee());
								schema.setPlanFeeCNY(String.valueOf(dblDisFee));
								schema.setExchangeRate("1");
								schema.setPlanDate(dbPrpPhead.getValidDate());
								schema.setComCode(dbPrpCmain.getComCode());
								schema.setMakeCom(dbPrpCmain.getMakeCom());
								schema.setBusinessNature(dbPrpCmain
										.getBusinessNature());
								schema.setAgentCode(dbPrpCmain.getAgentCode());
								blPrpDagent = new BLPrpDagent();
								schema.setAgentName(blPrpDagent.translateCode(
										dbPrpCmain.getAgentCode(), isChinese));
								schema.setHandler1Code(dbPrpCmain
										.getHandler1Code());
								blPrpDuser = new BLPrpDuser();
								schema.setHandler1Name(blPrpDuser
										.translateCode(
												dbPrpCmain.getHandler1Code(),
												isChinese));
								schema.setHandlerCode(dbPrpCmain
										.getHandlerCode());
								schema.setUnderWriteDate(DateTime.current()
										.toString().substring(0, 10));
								schema.setUnderWriteFlag(dbPrpPhead
										.getUnderWriteFlag());
								schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
								schema.setCenterCode(strCenterCode);
								schema.setBranchCode(strBranchCode);
								schema.setRealPayRefFee("0");
								schema.setRealPayRefFeeCNY("0");
								new DateTime();
								DateTime arg35 = new DateTime(
										DateTime.current(), 16);
								schema.setInputDate(arg35.toString());
								schema.setFlag("");
								schema.setProcessFlag("0");
								schema.setChannelType(dbPrpCmain
										.getChannelType());
								schema.setCarModel(strCarTypeCode);
								schema.setAgriType(dbPrpCmain.getAgriType());
								strJFeeFlag = dbPrpPhead.getJFeeFlag();
								if (strJFeeFlag == null
										|| strJFeeFlag.equals("")) {
									strJFeeFlag = "0";
								}

								schema.setJFeeFlag(strJFeeFlag);
								schema.setLocationFlag(this.getLocationFlag(
										dbpool, dbPrpCmain.getNationFlag()));
								if (!dbPrpPhead.getEndorType().equals("19")
										&& !dbPrpPhead.getEndorType().equals(
												"89")) {
									schema.setOthFlag("0");
								} else {
									schema.setOthFlag("1");
								}

								this.setArr(schema);
							}

							this.splitToKindEndorse(dbpool, "S", this.getArr(0)
									.getRiskCode());
						} else {
							boolean dateTime = false;
							strJFeeFlag = dbPrpPhead.getRiskCode();
							schema = new PrpCJplanSchema();
							schema.setCertiType("S");
							schema.setCertiNo(dbPrpPhead.getEndorseNo());
							schema.setSerialNo("1");
							schema.setPolicyNo(dbPrpPhead.getPolicyNo());
							schema.setClassCode(dbPrpCmain.getClassCode());
							schema.setRiskCode(dbPrpCmain.getRiskCode());
							schema.setContractNo(dbPrpCmain.getContractNo());
							schema.setAppliCode(dbPrpCmain.getAppliCode());
							schema.setAppliName(dbPrpCmain.getAppliName());
							schema.setInsuredCode(dbPrpCmain.getInsuredCode());
							schema.setInsuredName(dbPrpCmain.getInsuredName());
							schema.setStartDate(dbPrpPhead.getValidDate());
							schema.setEndDate(dbPrpCmain.getEndDate());
							schema.setEndorType(strEndorType);
							schema.setValidDate(dbPrpPhead.getValidDate());
							schema.setPayNo("1");
							schema.setTotalPayNo("1");
							schema.setPlanFeeCurrency(blPrpPCommission
									.getArr(0).getCurrency());
							schema.setPlanFee(blPrpPCommission.getArr(0)
									.getChgCostFee());
							schema.setPlanFeeCNY(String.valueOf(dblDisFee));
							schema.setExchangeRate("1");
							schema.setPlanDate(dbPrpPhead.getValidDate());
							schema.setComCode(dbPrpCmain.getComCode());
							schema.setMakeCom(dbPrpCmain.getMakeCom());
							schema.setBusinessNature(dbPrpCmain
									.getBusinessNature());
							schema.setChannelType(dbPrpCmain.getChannelType());
							schema.setAgentCode(dbPrpCmain.getAgentCode());
							blPrpDagent = new BLPrpDagent();
							schema.setAgentName(blPrpDagent.translateCode(
									dbPrpCmain.getAgentCode(), isChinese));
							schema.setHandler1Code(dbPrpCmain.getHandler1Code());
							blPrpDuser = new BLPrpDuser();
							schema.setHandler1Name(blPrpDuser.translateCode(
									dbPrpCmain.getHandler1Code(), isChinese));
							schema.setHandlerCode(dbPrpCmain.getHandlerCode());
							schema.setUnderWriteDate(DateTime.current()
									.toString().substring(0, 10));
							schema.setUnderWriteFlag(dbPrpPhead
									.getUnderWriteFlag());
							schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
							schema.setCenterCode(strCenterCode);
							schema.setBranchCode(strBranchCode);
							schema.setRealPayRefFee("0");
							schema.setRealPayRefFeeCNY("0");
							new DateTime();
							DateTime dateTime1 = new DateTime(
									DateTime.current(), 16);
							schema.setInputDate(dateTime1.toString());
							schema.setFlag("");
							schema.setProcessFlag("0");
							schema.setCarModel(strCarTypeCode);
							schema.setAgriType(dbPrpCmain.getAgriType());
							String strJFeeFlag1 = dbPrpPhead.getJFeeFlag();
							if (strJFeeFlag1 == null || strJFeeFlag1.equals("")) {
								strJFeeFlag1 = "0";
							}

							schema.setJFeeFlag(strJFeeFlag1);
							if (!dbPrpPhead.getEndorType().equals("19")
									&& !dbPrpPhead.getEndorType().equals("89")) {
								schema.setOthFlag("0");
							} else {
								schema.setOthFlag("1");
							}

							schema.setLocationFlag(this.getLocationFlag(dbpool,
									dbPrpCmain.getNationFlag()));
							this.setArr(schema);
							dateTime = this.chkCombinRisk(dbpool, strJFeeFlag);
							if (dateTime) {
								schema.setIsCombin("1");
							} else {
								schema.setIsCombin("0");
							}

							if (dateTime) {
								this.splitToKindEndorse(dbpool, "S",
										strJFeeFlag);
							}
						}
					}
				}

			}
		}
	}

	public boolean transTCarShipTax(DbPool dbpool, String iCertiType,
			String iCertiNo) throws UserException, Exception {
		boolean blTaxFlag = false;
		PrpCJplanSchema prpCJplanSchema = null;
		boolean isChinese = true;
		boolean isCombin = false;
		boolean intReturn = false;
		DBPrpTitemCarExt dbPrpTitemCarExt = new DBPrpTitemCarExt();
		dbPrpTitemCarExt.getInfo(dbpool, iCertiNo, "1");
		String strCarTypeCode = dbPrpTitemCarExt.getCartypeCode();
		if (iCertiType.equals("T")) {
			DBPrpTmain dbPrpTmain = new DBPrpTmain();
			BLPrpTcarshipTax blPrpTcarshipTax = new BLPrpTcarshipTax();
			int arg27 = dbPrpTmain.getInfo(dbpool, iCertiNo);
			DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
			dbPrpDrisk.getInfo(dbpool, dbPrpTmain.getRiskCode());
			if (dbPrpDrisk.getFlag().length() >= 2
					&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			if (arg27 == 100) {
				throw new UserException(-98, -1167,
						"PrpTransSff.transTCarShipTax", "无此保单信息：" + iCertiNo);
			} else {
				String strWherePart = "";
				strWherePart = " Proposalno=\'"
						+ iCertiNo
						+ "\' AND SerialNo=\'1\' "
						+ " AND (TaxActual!=0 OR PreviousPay!=0 OR LateFee!=0 )";
				BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
				strWherePart = "ComCode=\'" + dbPrpTmain.getComCode() + "\'";
				blPrpDcompany.query(dbpool, strWherePart);
				String strCenterCode = blPrpDcompany.getArr(0).getAcntUnit();
				String strBranchCode = strCenterCode;
				if (strCenterCode.equals("")) {
					strBranchCode = strCenterCode;
				}

				BLPrpDagent blPrpDagent = null;
				BLPrpDuser blPrpDuser = null;
				blPrpTcarshipTax.query(dbpool, strWherePart);

				for (int i = 0; i < blPrpTcarshipTax.getSize(); ++i) {
					double dblTaxActual = Double.parseDouble(Str
							.chgStrZero(blPrpTcarshipTax.getArr(i)
									.getTaxActual()));
					double dblPreviousPay = Double.parseDouble(Str
							.chgStrZero(blPrpTcarshipTax.getArr(i)
									.getPreviousPay()));
					double dblLateFee = Double
							.parseDouble(Str.chgStrZero(blPrpTcarshipTax
									.getArr(i).getLateFee()));
					DateTime dateTime;
					if (dblTaxActual != 0.0D) {
						blTaxFlag = true;
						prpCJplanSchema = new PrpCJplanSchema();
						prpCJplanSchema.setCertiType("T");
						prpCJplanSchema.setCertiNo(iCertiNo);
						prpCJplanSchema.setSerialNo("" + (i + 1));
						prpCJplanSchema.setPolicyNo(iCertiNo);
						prpCJplanSchema.setPayRefReason("R72");
						prpCJplanSchema.setClassCode(dbPrpTmain.getClassCode());
						prpCJplanSchema.setRiskCode(dbPrpTmain.getRiskCode());
						prpCJplanSchema.setContractNo(dbPrpTmain
								.getContractNo());
						prpCJplanSchema.setAppliCode(dbPrpTmain.getAppliCode());
						prpCJplanSchema.setAppliName(dbPrpTmain.getAppliName());
						prpCJplanSchema.setInsuredCode(dbPrpTmain
								.getInsuredCode());
						prpCJplanSchema.setInsuredName(dbPrpTmain
								.getInsuredName());
						prpCJplanSchema.setStartDate(dbPrpTmain.getStartDate());
						prpCJplanSchema.setEndDate(dbPrpTmain.getEndDate());
						prpCJplanSchema.setPayNo("1");
						prpCJplanSchema.setTotalPayNo("1");
						prpCJplanSchema.setPlanFeeCurrency("CNY");
						prpCJplanSchema.setPlanFee("" + dblTaxActual);
						prpCJplanSchema.setPlanFeeCNY("" + dblTaxActual);
						prpCJplanSchema.setExchangeRate("1.0");
						prpCJplanSchema.setPlanDate(dbPrpTmain.getStartDate());
						prpCJplanSchema.setComCode(dbPrpTmain.getComCode());
						prpCJplanSchema.setMakeCom(dbPrpTmain.getMakeCom());
						prpCJplanSchema.setBusinessNature(dbPrpTmain
								.getBusinessNature());
						prpCJplanSchema.setChannelType(dbPrpTmain
								.getChannelType());
						prpCJplanSchema.setAgentCode(dbPrpTmain.getAgentCode());
						blPrpDagent = new BLPrpDagent();
						prpCJplanSchema.setAgentName(blPrpDagent.translateCode(
								dbPrpTmain.getAgentCode(), isChinese));
						prpCJplanSchema.setHandler1Code(dbPrpTmain
								.getHandler1Code());
						blPrpDuser = new BLPrpDuser();
						prpCJplanSchema.setHandler1Name(blPrpDuser
								.translateCode(dbPrpTmain.getHandler1Code(),
										isChinese));
						prpCJplanSchema.setHandlerCode(dbPrpTmain
								.getHandlerCode());
						prpCJplanSchema.setUnderWriteDate(DateTime.current()
								.toString().substring(0, 10));
						prpCJplanSchema.setUnderWriteFlag("1");
						prpCJplanSchema.setCoinsFlag(dbPrpTmain.getCoinsFlag());
						prpCJplanSchema.setCenterCode(strCenterCode);
						prpCJplanSchema.setBranchCode(strBranchCode);
						prpCJplanSchema.setCarModel(strCarTypeCode);
						prpCJplanSchema.setIsCombin("0");
						prpCJplanSchema.setAgriType(dbPrpTmain.getAgriType());
						prpCJplanSchema.setRealPayRefFee("0");
						prpCJplanSchema.setRealPayRefFeeCNY("0");
						prpCJplanSchema.setFlag("");
						prpCJplanSchema.setJFeeFlag("1");
						prpCJplanSchema.setProcessFlag("0");
						prpCJplanSchema.setLocationFlag(this.getLocationFlag(
								dbpool, dbPrpTmain.getNationFlag()));
						prpCJplanSchema.setOthFlag("0");
						new DateTime();
						dateTime = new DateTime(DateTime.current(), 16);
						prpCJplanSchema.setInputDate(dateTime.toString());
						prpCJplanSchema.setValidDate(dbPrpTmain
								.getOperateDate());
						prpCJplanSchema.setTCol1(dbPrpTmain.getInputDate());
						this.setArr(prpCJplanSchema);
					}

					if (dblPreviousPay != 0.0D) {
						blTaxFlag = true;
						prpCJplanSchema = new PrpCJplanSchema();
						prpCJplanSchema.setCertiType("T");
						prpCJplanSchema.setCertiNo(iCertiNo);
						prpCJplanSchema.setSerialNo("" + (i + 1));
						prpCJplanSchema.setPolicyNo(iCertiNo);
						prpCJplanSchema.setPayRefReason("R73");
						prpCJplanSchema.setClassCode(dbPrpTmain.getClassCode());
						prpCJplanSchema.setRiskCode(dbPrpTmain.getRiskCode());
						prpCJplanSchema.setContractNo(dbPrpTmain
								.getContractNo());
						prpCJplanSchema.setAppliCode(dbPrpTmain.getAppliCode());
						prpCJplanSchema.setAppliName(dbPrpTmain.getAppliName());
						prpCJplanSchema.setInsuredCode(dbPrpTmain
								.getInsuredCode());
						prpCJplanSchema.setInsuredName(dbPrpTmain
								.getInsuredName());
						prpCJplanSchema.setStartDate(dbPrpTmain.getStartDate());
						prpCJplanSchema.setEndDate(dbPrpTmain.getEndDate());
						prpCJplanSchema.setPayNo("1");
						prpCJplanSchema.setTotalPayNo("1");
						prpCJplanSchema.setPlanFeeCurrency("CNY");
						prpCJplanSchema.setPlanFeeCurrency("CNY");
						prpCJplanSchema.setPlanFee("" + dblPreviousPay);
						prpCJplanSchema.setPlanFeeCNY("" + dblPreviousPay);
						prpCJplanSchema.setExchangeRate("1.0");
						prpCJplanSchema.setPlanDate(dbPrpTmain.getStartDate());
						prpCJplanSchema.setComCode(dbPrpTmain.getComCode());
						prpCJplanSchema.setMakeCom(dbPrpTmain.getMakeCom());
						prpCJplanSchema.setBusinessNature(dbPrpTmain
								.getBusinessNature());
						prpCJplanSchema.setChannelType(dbPrpTmain
								.getChannelType());
						prpCJplanSchema.setAgentCode(dbPrpTmain.getAgentCode());
						blPrpDagent = new BLPrpDagent();
						prpCJplanSchema.setAgentName(blPrpDagent.translateCode(
								dbPrpTmain.getAgentCode(), isChinese));
						prpCJplanSchema.setHandler1Code(dbPrpTmain
								.getHandler1Code());
						prpCJplanSchema.setHandler1Name(dbPrpTmain
								.getHandler1Name());
						blPrpDuser = new BLPrpDuser();
						prpCJplanSchema.setHandler1Name(blPrpDuser
								.translateCode(dbPrpTmain.getHandler1Code(),
										isChinese));
						prpCJplanSchema.setHandlerCode(dbPrpTmain
								.getHandlerCode());
						prpCJplanSchema.setUnderWriteDate(DateTime.current()
								.toString().substring(0, 10));
						prpCJplanSchema.setUnderWriteFlag("1");
						prpCJplanSchema.setCoinsFlag(dbPrpTmain.getCoinsFlag());
						prpCJplanSchema.setCenterCode(strCenterCode);
						prpCJplanSchema.setBranchCode(strBranchCode);
						prpCJplanSchema.setCarModel(strCarTypeCode);
						prpCJplanSchema.setIsCombin("0");
						prpCJplanSchema.setAgriType(dbPrpTmain.getAgriType());
						prpCJplanSchema.setRealPayRefFee("0");
						prpCJplanSchema.setRealPayRefFeeCNY("0");
						prpCJplanSchema.setFlag("");
						prpCJplanSchema.setJFeeFlag("1");
						prpCJplanSchema.setProcessFlag("0");
						prpCJplanSchema.setLocationFlag(this.getLocationFlag(
								dbpool, dbPrpTmain.getNationFlag()));
						prpCJplanSchema.setOthFlag("0");
						new DateTime();
						dateTime = new DateTime(DateTime.current(), 16);
						prpCJplanSchema.setInputDate(dateTime.toString());
						prpCJplanSchema.setValidDate(dbPrpTmain
								.getOperateDate());
						prpCJplanSchema.setTCol1(dbPrpTmain.getInputDate());
						this.setArr(prpCJplanSchema);
					}

					if (dblLateFee != 0.0D) {
						blTaxFlag = true;
						prpCJplanSchema = new PrpCJplanSchema();
						prpCJplanSchema.setCertiType("T");
						prpCJplanSchema.setCertiNo(iCertiNo);
						prpCJplanSchema.setSerialNo("" + (i + 1));
						prpCJplanSchema.setPolicyNo(iCertiNo);
						prpCJplanSchema.setPayRefReason("R74");
						prpCJplanSchema.setClassCode(dbPrpTmain.getClassCode());
						prpCJplanSchema.setRiskCode(dbPrpTmain.getRiskCode());
						prpCJplanSchema.setContractNo(dbPrpTmain
								.getContractNo());
						prpCJplanSchema.setAppliCode(dbPrpTmain.getAppliCode());
						prpCJplanSchema.setAppliName(dbPrpTmain.getAppliName());
						prpCJplanSchema.setInsuredCode(dbPrpTmain
								.getInsuredCode());
						prpCJplanSchema.setInsuredName(dbPrpTmain
								.getInsuredName());
						prpCJplanSchema.setStartDate(dbPrpTmain.getStartDate());
						prpCJplanSchema.setEndDate(dbPrpTmain.getEndDate());
						prpCJplanSchema.setPayNo("1");
						prpCJplanSchema.setTotalPayNo("1");
						prpCJplanSchema.setPlanFeeCurrency("CNY");
						prpCJplanSchema.setPlanFee("" + dblLateFee);
						prpCJplanSchema.setPlanFeeCNY("" + dblLateFee);
						prpCJplanSchema.setExchangeRate("1.0");
						prpCJplanSchema.setPlanDate(dbPrpTmain.getStartDate());
						prpCJplanSchema.setComCode(dbPrpTmain.getComCode());
						prpCJplanSchema.setMakeCom(dbPrpTmain.getMakeCom());
						prpCJplanSchema.setBusinessNature(dbPrpTmain
								.getBusinessNature());
						prpCJplanSchema.setChannelType(dbPrpTmain
								.getChannelType());
						prpCJplanSchema.setAgentCode(dbPrpTmain.getAgentCode());
						blPrpDagent = new BLPrpDagent();
						prpCJplanSchema.setAgentName(blPrpDagent.translateCode(
								dbPrpTmain.getAgentCode(), isChinese));
						prpCJplanSchema.setHandler1Code(dbPrpTmain
								.getHandler1Code());
						blPrpDuser = new BLPrpDuser();
						prpCJplanSchema.setHandler1Name(blPrpDuser
								.translateCode(dbPrpTmain.getHandler1Code(),
										isChinese));
						prpCJplanSchema.setHandlerCode(dbPrpTmain
								.getHandlerCode());
						prpCJplanSchema.setUnderWriteDate(DateTime.current()
								.toString().substring(0, 10));
						prpCJplanSchema.setUnderWriteFlag("1");
						prpCJplanSchema.setCoinsFlag(dbPrpTmain.getCoinsFlag());
						prpCJplanSchema.setCenterCode(strCenterCode);
						prpCJplanSchema.setBranchCode(strBranchCode);
						prpCJplanSchema.setCarModel(strCarTypeCode);
						prpCJplanSchema.setIsCombin("0");
						prpCJplanSchema.setAgriType(dbPrpTmain.getAgriType());
						prpCJplanSchema.setRealPayRefFee("0");
						prpCJplanSchema.setRealPayRefFeeCNY("0");
						prpCJplanSchema.setFlag("");
						prpCJplanSchema.setJFeeFlag("1");
						prpCJplanSchema.setProcessFlag("0");
						prpCJplanSchema.setLocationFlag(this.getLocationFlag(
								dbpool, dbPrpTmain.getNationFlag()));
						prpCJplanSchema.setOthFlag("0");
						new DateTime();
						dateTime = new DateTime(DateTime.current(), 16);
						prpCJplanSchema.setInputDate(dateTime.toString());
						prpCJplanSchema.setValidDate(dbPrpTmain
								.getOperateDate());
						prpCJplanSchema.setTCol1(dbPrpTmain.getInputDate());
						this.setArr(prpCJplanSchema);
					}
				}

				return blTaxFlag;
			}
		} else {
			throw new UserException(-98, -1167, "PrpTransSff.transTCarShipTax",
					"不存在该业务类型的税金处理：" + iCertiType);
		}
	}

	public void splitToKind(DbPool dbpool, String iCertiType, String iRiskCode)
			throws Exception {
		DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
		dbPrpDrisk.getInfo(dbpool, iRiskCode);
		if (dbPrpDrisk.getFlag().length() >= 2
				&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
			this.splitToKind(dbpool, iCertiType);
		}
	}

	public void splitToKindEndorse(DbPool dbpool, String iCertiType,
			String iRiskCode) throws Exception {
		DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
		dbPrpDrisk.getInfo(dbpool, iRiskCode);
		if (dbPrpDrisk.getFlag().length() >= 2
				&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
			this.splitToKindEndorse(dbpool, iCertiType);
		}
	}

	public void splitToKindEndorse(DbPool dbpool, String iCertiType)
			throws Exception {
		if (this.getSize() != 0) {
			String strWherePart = "";
			BLPrpCJplanKind blPrpCJplanKind = new BLPrpCJplanKind();
			PrpCJplanKindSchema prpCJplanKindSchema = null;
			HashMap dKindFeeTemper = new HashMap();
			HashMap dKindFeeCNYTemper = new HashMap();
			String sKey = "";
			int iRecorderCount = 0;
			String sCondition = "";
			if ("S".equals(iCertiType)) {
				sCondition = " EndorseNo = \'" + this.getArr(0).getCertiNo()
						+ "\'";
				BLPrpCplan i = new BLPrpCplan();
				i.query(dbpool, sCondition);
				iRecorderCount = i.getSize();
			}

			for (int arg34 = 0; arg34 < this.getSize(); ++arg34) {
				if (this.getArr(arg34).getCertiType().equals(iCertiType)
						&& !"0".equals(this.getArr(arg34).getIsCombin())
						&& this.getArr(arg34).getCertiType().equals("S")) {
					double dblSumPremium2 = 0.0D;
					double dblSumPremiumCNY = 0.0D;
					double dblRation = 0.0D;
					double dblKindFee = 0.0D;
					double dblKindFeeSum = 0.0D;
					double dblKindFeeCNY = 0.0D;
					double dblKindFeeSumCNY = 0.0D;
					strWherePart = " EndorseNo=\'"
							+ this.getArr(arg34).getCertiNo() + "\' ";
					BLPrpPCommissionDetail blPrpPCommissionDetail = new BLPrpPCommissionDetail();
					blPrpPCommissionDetail.query(dbpool, strWherePart);

					int j;
					for (j = 0; j < blPrpPCommissionDetail.getSize(); ++j) {
						dblSumPremium2 += Double.parseDouble(Str
								.chgStrZero(blPrpPCommissionDetail.getArr(j)
										.getChgCostFee()));
						dblSumPremiumCNY += Double.parseDouble(Str
								.chgStrZero(blPrpPCommissionDetail.getArr(j)
										.getChgCostFee()));
					}

					for (j = 0; j < blPrpPCommissionDetail.getSize(); ++j) {
						prpCJplanKindSchema = new PrpCJplanKindSchema();
						prpCJplanKindSchema.setCertiType(this.getArr(arg34)
								.getCertiType());
						prpCJplanKindSchema.setCertiNo(this.getArr(arg34)
								.getCertiNo());
						prpCJplanKindSchema.setPolicyNo(this.getArr(arg34)
								.getPolicyNo());
						prpCJplanKindSchema.setSerialNo(this.getArr(arg34)
								.getSerialNo());
						prpCJplanKindSchema.setPayRefReason(this.getArr(arg34)
								.getPayRefReason());
						prpCJplanKindSchema
								.setItemKindNo(blPrpPCommissionDetail.getArr(j)
										.getSerialNo());
						prpCJplanKindSchema.setRiskCode(this.getArr(arg34)
								.getRiskCode());
						prpCJplanKindSchema.setKindCode(blPrpPCommissionDetail
								.getArr(j).getKindCode());
						prpCJplanKindSchema
								.setSubRiskCode(blPrpPCommissionDetail
										.getArr(j).getKindCode());
						String sItemKindRate;
						if ("A".equals(this.getArr(arg34).getClassCode())
								|| "B".equals(this.getArr(arg34).getClassCode())) {
							BLPrpPitemCar dateTime = new BLPrpPitemCar();
							strWherePart = " EndorseNo=\'"
									+ this.getArr(arg34).getCertiNo()
									+ "\' AND ItemNo=\'1\'";
							dateTime.query(dbpool, strWherePart);
							String temp = this.getArr(arg34).getRiskCode();
							String dTempTotal = blPrpPCommissionDetail
									.getArr(j).getKindCode();
							String useNatureCode = dateTime.getArr(0)
									.getUseNatureCode();
							sItemKindRate = dateTime.getArr(0).getCarKindCode();
							int years = DateUtil.getYearsCount(new DateTime(
									this.getArr(arg34).getStartDate()),
									new DateTime(this.getArr(arg34)
											.getEndDate()));
							String subRiskCode = this.findSubRiskCode(temp,
									dTempTotal, useNatureCode, years,
									sItemKindRate);
							prpCJplanKindSchema.setSubRiskCode(subRiskCode);
						}

						prpCJplanKindSchema.setClauseType("");
						sKey = blPrpPCommissionDetail.getArr(j).getKindCode()
								+ "_1";
						if (j == blPrpPCommissionDetail.getSize() - 1
								&& arg34 != iRecorderCount - 1) {
							dblKindFee = Double.parseDouble(this.getArr(arg34)
									.getPlanFee()) - dblKindFeeSum;
							prpCJplanKindSchema.setKindFeeRate(""
									+ (100.0D - dblRation));
							dblKindFeeCNY = Str.round(
									Double.parseDouble(this.getArr(arg34)
											.getPlanFeeCNY())
											- dblKindFeeSumCNY, 2);
						} else if (iRecorderCount > 1
								&& arg34 == iRecorderCount - 1) {
							double arg35 = ((Double) dKindFeeCNYTemper
									.get(sKey)).doubleValue();
							double arg36 = Double
									.parseDouble(blPrpPCommissionDetail.getArr(
											j).getChgCostFee());
							sItemKindRate = ""
									+ 100.0D
									* Double.parseDouble(blPrpPCommissionDetail
											.getArr(j).getChgCostFee())
									/ dblSumPremium2;
							if (j == blPrpPCommissionDetail.getSize() - 1) {
								arg35 = dblKindFeeSumCNY;
								arg36 = Double.parseDouble(this.getArr(arg34)
										.getPlanFeeCNY());
								sItemKindRate = "" + (100.0D - dblRation);
							}

							dblKindFee = Double.parseDouble(Str
									.chgStrZero(blPrpPCommissionDetail
											.getArr(j).getChgCostFee()))
									- ((Double) dKindFeeTemper.get(sKey))
											.doubleValue();
							dblKindFeeCNY = Str.round(arg36 - arg35, 2);
							prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
							dblKindFeeSumCNY += dblKindFeeCNY;
						} else if (dblSumPremium2 != 0.0D
								&& dblSumPremiumCNY != 0.0D) {
							dblKindFee = Str
									.round(Double
											.parseDouble(Str
													.chgStrZero(blPrpPCommissionDetail
															.getArr(j)
															.getChgCostFee()))
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFee()) / dblSumPremium2),
											2);
							dblKindFeeSum += Str.round(dblKindFee, 2);
							prpCJplanKindSchema.setKindFeeRate(""
									+ 100.0D
									* Double.parseDouble(blPrpPCommissionDetail
											.getArr(j).getChgCostFee())
									/ dblSumPremium2);
							dblKindFeeCNY = Str
									.round(Double
											.parseDouble(Str
													.chgStrZero(blPrpPCommissionDetail
															.getArr(j)
															.getChgCostFee()))
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFeeCNY()) / dblSumPremiumCNY),
											2);
							dblKindFeeSumCNY += dblKindFeeCNY;
						}

						if (dblSumPremium2 != 0.0D) {
							dblRation += Str
									.round(100.0D
											* Double.parseDouble(blPrpPCommissionDetail
													.getArr(j).getChgCostFee())
											/ dblSumPremium2, 2);
						}

						if (Str.round(dblKindFee, 2) != 0.0D
								&& Str.round(dblKindFeeCNY, 2) != 0.0D) {
							prpCJplanKindSchema.setKindFee("" + dblKindFee);
							prpCJplanKindSchema.setPlanFeeCurrency(this.getArr(
									arg34).getPlanFeeCurrency());
							prpCJplanKindSchema.setPlanFeeCNY(""
									+ dblKindFeeCNY);
							prpCJplanKindSchema.setExchangeRate("1");
							prpCJplanKindSchema.setComCode(this.getArr(arg34)
									.getComCode());
							prpCJplanKindSchema.setLossType("");
							prpCJplanKindSchema.setRealPayRefFee("0");
							prpCJplanKindSchema.setFlag("");
							prpCJplanKindSchema.setOnAccFlag("0");
							prpCJplanKindSchema.setRealPayRefFlag("0");
							new DateTime();
							DateTime arg37 = new DateTime(DateTime.current(),
									16);
							prpCJplanKindSchema.setInputDate(arg37.toString());
							blPrpCJplanKind.setArr(prpCJplanKindSchema);
							if (!dKindFeeTemper.containsKey(sKey)) {
								dKindFeeTemper.put(sKey,
										Double.valueOf(dblKindFee));
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(dblKindFeeCNY));
							} else {
								double arg38 = ((Double) dKindFeeTemper
										.get(sKey)).doubleValue() + dblKindFee;
								dKindFeeTemper.put(sKey,
										Double.valueOf(Str.round(arg38, 2)));
								arg38 = ((Double) dKindFeeCNYTemper.get(sKey))
										.doubleValue() + dblKindFeeCNY;
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(Str.round(arg38, 2)));
							}
						}
					}
				}
			}

			blPrpCJplanKind.save(dbpool);
		}
	}

	public void splitToKindPolicy(DbPool dbpool, String iCertiType,
			String iRiskCode) throws Exception {
		DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
		dbPrpDrisk.getInfo(dbpool, iRiskCode);
		if (dbPrpDrisk.getFlag().length() >= 2
				&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
			this.splitToKindPolicy(dbpool, iCertiType);
		}
	}

	public void splitToKindPolicy(DbPool dbpool, String iCertiType)
			throws Exception {
		if (this.getSize() != 0) {
			String strWherePart = "";
			BLPrpCJplanKind blPrpCJplanKind = new BLPrpCJplanKind();
			PrpCJplanKindSchema prpCJplanKindSchema = null;
			HashMap dKindFeeTemper = new HashMap();
			HashMap dKindFeeCNYTemper = new HashMap();
			String sKey = "";
			int iRecorderCount = 0;
			String sCondition = "";
			if ("S".equals(iCertiType)) {
				sCondition = " PolicyNo = \'" + this.getArr(0).getCertiNo()
						+ "\' And (EndorseNo Is Null Or EndorseNo = \'\')";
				BLPrpCplan i = new BLPrpCplan();
				i.query(dbpool, sCondition);
				iRecorderCount = i.getSize();
			}

			for (int arg34 = 0; arg34 < this.getSize(); ++arg34) {
				if (this.getArr(arg34).getCertiType().equals(iCertiType)
						&& !"0".equals(this.getArr(arg34).getIsCombin())
						&& this.getArr(arg34).getCertiType().equals("S")) {
					double dblSumPremium2 = 0.0D;
					double dblSumPremiumCNY = 0.0D;
					double dblRation = 0.0D;
					double dblKindFee = 0.0D;
					double dblKindFeeSum = 0.0D;
					double dblKindFeeCNY = 0.0D;
					double dblKindFeeSumCNY = 0.0D;
					strWherePart = " PolicyNo=\'"
							+ this.getArr(arg34).getCertiNo() + "\' ";
					BLPrpCCommissionDetail blPrpCCommissionDetail = new BLPrpCCommissionDetail();
					blPrpCCommissionDetail.query(dbpool, strWherePart);

					int j;
					for (j = 0; j < blPrpCCommissionDetail.getSize(); ++j) {
						dblSumPremium2 += Double.parseDouble(Str
								.chgStrZero(blPrpCCommissionDetail.getArr(j)
										.getCostFee()));
						dblSumPremiumCNY += Double.parseDouble(Str
								.chgStrZero(blPrpCCommissionDetail.getArr(j)
										.getCostFee()));
					}

					for (j = 0; j < blPrpCCommissionDetail.getSize(); ++j) {
						prpCJplanKindSchema = new PrpCJplanKindSchema();
						prpCJplanKindSchema.setCertiType(this.getArr(arg34)
								.getCertiType());
						prpCJplanKindSchema.setCertiNo(this.getArr(arg34)
								.getCertiNo());
						prpCJplanKindSchema.setPolicyNo(this.getArr(arg34)
								.getPolicyNo());
						prpCJplanKindSchema.setSerialNo(this.getArr(arg34)
								.getSerialNo());
						prpCJplanKindSchema.setPayRefReason(this.getArr(arg34)
								.getPayRefReason());
						prpCJplanKindSchema
								.setItemKindNo(blPrpCCommissionDetail.getArr(j)
										.getSerialNo());
						prpCJplanKindSchema.setRiskCode(this.getArr(arg34)
								.getRiskCode());
						prpCJplanKindSchema.setKindCode(blPrpCCommissionDetail
								.getArr(j).getKindCode());
						prpCJplanKindSchema
								.setSubRiskCode(blPrpCCommissionDetail
										.getArr(j).getKindCode());
						String sItemKindRate;
						if ("A".equals(this.getArr(arg34).getClassCode())
								|| "B".equals(this.getArr(arg34).getClassCode())) {
							BLPrpCitemCar dateTime = new BLPrpCitemCar();
							strWherePart = " PolicyNo=\'"
									+ this.getArr(arg34).getCertiNo()
									+ "\' AND ItemNo=\'1\'";
							dateTime.query(dbpool, strWherePart);
							String temp = this.getArr(arg34).getRiskCode();
							String dTempTotal = blPrpCCommissionDetail
									.getArr(j).getKindCode();
							String useNatureCode = dateTime.getArr(0)
									.getUseNatureCode();
							sItemKindRate = dateTime.getArr(0).getCarKindCode();
							int years = DateUtil.getYearsCount(new DateTime(
									this.getArr(arg34).getStartDate()),
									new DateTime(this.getArr(arg34)
											.getEndDate()));
							String subRiskCode = this.findSubRiskCode(temp,
									dTempTotal, useNatureCode, years,
									sItemKindRate);
							prpCJplanKindSchema.setSubRiskCode(subRiskCode);
						}

						prpCJplanKindSchema.setClauseType("");
						sKey = blPrpCCommissionDetail.getArr(j).getKindCode()
								+ "_1";
						if (j == blPrpCCommissionDetail.getSize() - 1
								&& arg34 != iRecorderCount - 1) {
							dblKindFee = Double.parseDouble(this.getArr(arg34)
									.getPlanFee()) - dblKindFeeSum;
							prpCJplanKindSchema.setKindFeeRate(""
									+ (100.0D - dblRation));
							dblKindFeeCNY = Str.round(
									Double.parseDouble(this.getArr(arg34)
											.getPlanFeeCNY())
											- dblKindFeeSumCNY, 2);
						} else if (iRecorderCount > 1
								&& arg34 == iRecorderCount - 1) {
							double arg35 = ((Double) dKindFeeCNYTemper
									.get(sKey)).doubleValue();
							double arg36 = Double
									.parseDouble(blPrpCCommissionDetail.getArr(
											j).getCostFee());
							sItemKindRate = ""
									+ 100.0D
									* Double.parseDouble(blPrpCCommissionDetail
											.getArr(j).getCostFee())
									/ dblSumPremium2;
							if (j == blPrpCCommissionDetail.getSize() - 1) {
								arg35 = dblKindFeeSumCNY;
								arg36 = Double.parseDouble(this.getArr(arg34)
										.getPlanFeeCNY());
								sItemKindRate = "" + (100.0D - dblRation);
							}

							dblKindFee = Double.parseDouble(Str
									.chgStrZero(blPrpCCommissionDetail
											.getArr(j).getCostFee()))
									- ((Double) dKindFeeTemper.get(sKey))
											.doubleValue();
							dblKindFeeCNY = Str.round(arg36 - arg35, 2);
							prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
							dblKindFeeSumCNY += dblKindFeeCNY;
						} else if (dblSumPremium2 != 0.0D
								&& dblSumPremiumCNY != 0.0D) {
							dblKindFee = Str
									.round(Double.parseDouble(Str
											.chgStrZero(blPrpCCommissionDetail
													.getArr(j).getCostFee()))
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFee()) / dblSumPremium2),
											2);
							dblKindFeeSum += Str.round(dblKindFee, 2);
							prpCJplanKindSchema.setKindFeeRate(""
									+ 100.0D
									* Double.parseDouble(blPrpCCommissionDetail
											.getArr(j).getCostFee())
									/ dblSumPremium2);
							dblKindFeeCNY = Str
									.round(Double.parseDouble(Str
											.chgStrZero(blPrpCCommissionDetail
													.getArr(j).getCostFee()))
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFeeCNY()) / dblSumPremiumCNY),
											2);
							dblKindFeeSumCNY += dblKindFeeCNY;
						}

						if (dblSumPremium2 != 0.0D) {
							dblRation += Str
									.round(100.0D
											* Double.parseDouble(blPrpCCommissionDetail
													.getArr(j).getCostFee())
											/ dblSumPremium2, 2);
						}

						if (Str.round(dblKindFee, 2) != 0.0D
								&& Str.round(dblKindFeeCNY, 2) != 0.0D) {
							prpCJplanKindSchema.setKindFee("" + dblKindFee);
							prpCJplanKindSchema.setPlanFeeCurrency(this.getArr(
									arg34).getPlanFeeCurrency());
							prpCJplanKindSchema.setPlanFeeCNY(""
									+ dblKindFeeCNY);
							prpCJplanKindSchema.setExchangeRate("1");
							prpCJplanKindSchema.setComCode(this.getArr(arg34)
									.getComCode());
							prpCJplanKindSchema.setLossType("");
							prpCJplanKindSchema.setRealPayRefFee("0");
							prpCJplanKindSchema.setFlag("");
							prpCJplanKindSchema.setOnAccFlag("0");
							prpCJplanKindSchema.setRealPayRefFlag("0");
							new DateTime();
							DateTime arg37 = new DateTime(DateTime.current(),
									16);
							prpCJplanKindSchema.setInputDate(arg37.toString());
							blPrpCJplanKind.setArr(prpCJplanKindSchema);
							if (!dKindFeeTemper.containsKey(sKey)) {
								dKindFeeTemper.put(sKey,
										Double.valueOf(dblKindFee));
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(dblKindFeeCNY));
							} else {
								double arg38 = ((Double) dKindFeeTemper
										.get(sKey)).doubleValue() + dblKindFee;
								dKindFeeTemper.put(sKey,
										Double.valueOf(Str.round(arg38, 2)));
								arg38 = ((Double) dKindFeeCNYTemper.get(sKey))
										.doubleValue() + dblKindFeeCNY;
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(Str.round(arg38, 2)));
							}
						}
					}
				}
			}

			blPrpCJplanKind.save(dbpool);
		}
	}

	public void splitToKindQ(DbPool dbpool, String iCertiType, String iRiskCode)
			throws Exception {
		DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
		dbPrpDrisk.getInfo(dbpool, iRiskCode);
		if (dbPrpDrisk.getFlag().length() >= 2
				&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
			this.splitToKindQ(dbpool, iCertiType);
		}
	}

	public void splitToKindQ(DbPool dbpool, String iCertiType) throws Exception {
		if (this.getSize() != 0) {
			String strWherePart = "";
			BLPrpCJplanKind blPrpCJplanKind = new BLPrpCJplanKind();
			PrpCJplanKindSchema prpCJplanKindSchema = null;
			HashMap dKindFeeTemper = new HashMap();
			HashMap dKindFeeCNYTemper = new HashMap();
			String sKey = "";
			int iRecorderCount = 0;
			String sCondition = "";
			if ("S".equals(iCertiType)) {
				sCondition = " ProposalNo = \'" + this.getArr(0).getCertiNo()
						+ "\'";
				BLPrpQplan i = new BLPrpQplan();
				i.query(dbpool, sCondition);
				iRecorderCount = i.getSize();
			}

			for (int arg34 = 0; arg34 < this.getSize(); ++arg34) {
				if (this.getArr(arg34).getCertiType().equals(iCertiType)
						&& !"0".equals(this.getArr(arg34).getIsCombin())
						&& this.getArr(arg34).getCertiType().equals("S")) {
					double dblSumPremium2 = 0.0D;
					double dblSumPremiumCNY = 0.0D;
					double dblRation = 0.0D;
					double dblKindFee = 0.0D;
					double dblKindFeeSum = 0.0D;
					double dblKindFeeCNY = 0.0D;
					double dblKindFeeSumCNY = 0.0D;
					strWherePart = " ProposalNo=\'"
							+ this.getArr(arg34).getCertiNo() + "\' ";
					BLPrpQCommissionDetail blPrpQCommissionDetail = new BLPrpQCommissionDetail();
					blPrpQCommissionDetail.query(dbpool, strWherePart);

					int j;
					for (j = 0; j < blPrpQCommissionDetail.getSize(); ++j) {
						dblSumPremium2 += Double.parseDouble(Str
								.chgStrZero(blPrpQCommissionDetail.getArr(j)
										.getCostFee()));
						dblSumPremiumCNY += Double.parseDouble(Str
								.chgStrZero(blPrpQCommissionDetail.getArr(j)
										.getCostFee()));
					}

					for (j = 0; j < blPrpQCommissionDetail.getSize(); ++j) {
						prpCJplanKindSchema = new PrpCJplanKindSchema();
						prpCJplanKindSchema.setCertiType(this.getArr(arg34)
								.getCertiType());
						prpCJplanKindSchema.setCertiNo(this.getArr(arg34)
								.getCertiNo());
						prpCJplanKindSchema.setPolicyNo(this.getArr(arg34)
								.getPolicyNo());
						prpCJplanKindSchema.setSerialNo(this.getArr(arg34)
								.getSerialNo());
						prpCJplanKindSchema.setPayRefReason(this.getArr(arg34)
								.getPayRefReason());
						prpCJplanKindSchema
								.setItemKindNo(blPrpQCommissionDetail.getArr(j)
										.getSerialNo());
						prpCJplanKindSchema.setRiskCode(this.getArr(arg34)
								.getRiskCode());
						prpCJplanKindSchema.setKindCode(blPrpQCommissionDetail
								.getArr(j).getKindCode());
						prpCJplanKindSchema
								.setSubRiskCode(blPrpQCommissionDetail
										.getArr(j).getKindCode());
						String sItemKindRate;
						if ("A".equals(this.getArr(arg34).getClassCode())
								|| "B".equals(this.getArr(arg34).getClassCode())) {
							BLPrpQitemCar dateTime = new BLPrpQitemCar();
							strWherePart = " ProposalNo=\'"
									+ this.getArr(arg34).getCertiNo()
									+ "\' AND ItemNo=\'1\'";
							dateTime.query(dbpool, strWherePart);
							String temp = this.getArr(arg34).getRiskCode();
							String dTempTotal = blPrpQCommissionDetail
									.getArr(j).getKindCode();
							String useNatureCode = dateTime.getArr(0)
									.getUseNatureCode();
							sItemKindRate = dateTime.getArr(0).getCarKindCode();
							int years = DateUtil.getYearsCount(new DateTime(
									this.getArr(arg34).getStartDate()),
									new DateTime(this.getArr(arg34)
											.getEndDate()));
							String subRiskCode = this.findSubRiskCode(temp,
									dTempTotal, useNatureCode, years,
									sItemKindRate);
							prpCJplanKindSchema.setSubRiskCode(subRiskCode);
						}

						prpCJplanKindSchema.setClauseType("");
						sKey = blPrpQCommissionDetail.getArr(j).getKindCode()
								+ "_1";
						if (j == blPrpQCommissionDetail.getSize() - 1
								&& arg34 != iRecorderCount - 1) {
							dblKindFee = Double.parseDouble(this.getArr(arg34)
									.getPlanFee()) - dblKindFeeSum;
							prpCJplanKindSchema.setKindFeeRate(""
									+ (100.0D - dblRation));
							dblKindFeeCNY = Str.round(
									Double.parseDouble(this.getArr(arg34)
											.getPlanFeeCNY())
											- dblKindFeeSumCNY, 2);
						} else if (iRecorderCount > 1
								&& arg34 == iRecorderCount - 1) {
							double arg35 = ((Double) dKindFeeCNYTemper
									.get(sKey)).doubleValue();
							double arg36 = Double
									.parseDouble(blPrpQCommissionDetail.getArr(
											j).getCostFee());
							sItemKindRate = ""
									+ 100.0D
									* Double.parseDouble(blPrpQCommissionDetail
											.getArr(j).getCostFee())
									/ dblSumPremium2;
							if (j == blPrpQCommissionDetail.getSize() - 1) {
								arg35 = dblKindFeeSumCNY;
								arg36 = Double.parseDouble(this.getArr(arg34)
										.getPlanFeeCNY());
								sItemKindRate = "" + (100.0D - dblRation);
							}

							dblKindFee = Double.parseDouble(Str
									.chgStrZero(blPrpQCommissionDetail
											.getArr(j).getCostFee()))
									- ((Double) dKindFeeTemper.get(sKey))
											.doubleValue();
							dblKindFeeCNY = Str.round(arg36 - arg35, 2);
							prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
							dblKindFeeSumCNY += dblKindFeeCNY;
						} else if (dblSumPremium2 != 0.0D
								&& dblSumPremiumCNY != 0.0D) {
							dblKindFee = Str
									.round(Double.parseDouble(Str
											.chgStrZero(blPrpQCommissionDetail
													.getArr(j).getCostFee()))
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFee()) / dblSumPremium2),
											2);
							dblKindFeeSum += Str.round(dblKindFee, 2);
							prpCJplanKindSchema.setKindFeeRate(""
									+ 100.0D
									* Double.parseDouble(blPrpQCommissionDetail
											.getArr(j).getCostFee())
									/ dblSumPremium2);
							dblKindFeeCNY = Str
									.round(Double.parseDouble(Str
											.chgStrZero(blPrpQCommissionDetail
													.getArr(j).getCostFee()))
											* (Double.parseDouble(this.getArr(
													arg34).getPlanFeeCNY()) / dblSumPremiumCNY),
											2);
							dblKindFeeSumCNY += dblKindFeeCNY;
						}

						if (dblSumPremium2 != 0.0D) {
							dblRation += Str
									.round(100.0D
											* Double.parseDouble(blPrpQCommissionDetail
													.getArr(j).getCostFee())
											/ dblSumPremium2, 2);
						}

						if (Str.round(dblKindFee, 2) != 0.0D
								&& Str.round(dblKindFeeCNY, 2) != 0.0D) {
							prpCJplanKindSchema.setKindFee("" + dblKindFee);
							prpCJplanKindSchema.setPlanFeeCurrency(this.getArr(
									arg34).getPlanFeeCurrency());
							prpCJplanKindSchema.setPlanFeeCNY(""
									+ dblKindFeeCNY);
							prpCJplanKindSchema.setExchangeRate("1");
							prpCJplanKindSchema.setComCode(this.getArr(arg34)
									.getComCode());
							prpCJplanKindSchema.setLossType("");
							prpCJplanKindSchema.setRealPayRefFee("0");
							prpCJplanKindSchema.setFlag("");
							prpCJplanKindSchema.setOnAccFlag("0");
							prpCJplanKindSchema.setRealPayRefFlag("0");
							new DateTime();
							DateTime arg37 = new DateTime(DateTime.current(),
									16);
							prpCJplanKindSchema.setInputDate(arg37.toString());
							blPrpCJplanKind.setArr(prpCJplanKindSchema);
							if (!dKindFeeTemper.containsKey(sKey)) {
								dKindFeeTemper.put(sKey,
										Double.valueOf(dblKindFee));
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(dblKindFeeCNY));
							} else {
								double arg38 = ((Double) dKindFeeTemper
										.get(sKey)).doubleValue() + dblKindFee;
								dKindFeeTemper.put(sKey,
										Double.valueOf(Str.round(arg38, 2)));
								arg38 = ((Double) dKindFeeCNYTemper.get(sKey))
										.doubleValue() + dblKindFeeCNY;
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(Str.round(arg38, 2)));
							}
						}
					}
				}
			}

			blPrpCJplanKind.save(dbpool);
		}
	}

	public String findSubRiskCode(String riskCode, String kindCode,
			String useNatureCode, int years, String carKindCode) {
		String subRiskCode = "";
		String strWherePart = "";
		BLPrpDration blPrpDration = new BLPrpDration();
		strWherePart = " RiskCode=\'" + riskCode + "\' AND KindCode=\'"
				+ kindCode + "\'";

		try {
			if ("B01".equals(riskCode)) {
				if (!"01".equals(carKindCode) && !"02".equals(carKindCode)
						&& !"34".equals(carKindCode)
						&& !"32".equals(carKindCode)) {
					strWherePart = strWherePart + " AND UseNatureCode=\'"
							+ useNatureCode + "\'";
				} else {
					strWherePart = strWherePart + " AND Years=" + years;
				}
			} else {
				strWherePart = strWherePart + " AND UseNatureCode=\'"
						+ useNatureCode + "\' AND Years=" + years;
			}

			blPrpDration.query(strWherePart);
			if (blPrpDration.getSize() <= 0) {
				throw new UserException(-98, -1167,
						"PrpTransSff.findSubRiskCode",
						"PrpDration表中沒有該訊息：RiskCode=\'" + riskCode
								+ "\' AND KindCode=\'" + kindCode
								+ "\' AND UseNatureCode=\'" + useNatureCode
								+ "\' AND Years=" + years);
			}

			subRiskCode = blPrpDration.getArr(0).getSubRiskCode();
		} catch (UserException arg9) {
			arg9.printStackTrace();
		} catch (Exception arg10) {
			arg10.printStackTrace();
		}

		return subRiskCode;
	}

	public String findSubRiskCodeForCME(String riskCode) {
		String subRiskCode = "";
		String strWherePart = "";
		BLPrpDration blPrpDration = new BLPrpDration();
		strWherePart = " RiskCode=\'" + riskCode + "\'";

		try {
			blPrpDration.query(strWherePart);
			if (blPrpDration.getSize() > 0) {
				subRiskCode = blPrpDration.getArr(0).getSubRiskCode();
			} else {
				subRiskCode = riskCode;
			}
		} catch (UserException arg5) {
			arg5.printStackTrace();
		} catch (Exception arg6) {
			arg6.printStackTrace();
		}

		return subRiskCode;
	}

	public String findSubRiskCodeForFC(String riskCode, String kindCode) {
		String subRiskCode = "";
		String strWherePart = "";
		BLPrpDration blPrpDration = new BLPrpDration();
		strWherePart = " RiskCode=\'" + riskCode + "\'  AND kindCode = \'"
				+ kindCode + "\'";

		try {
			blPrpDration.query(strWherePart);
			if (blPrpDration.getSize() > 0) {
				subRiskCode = blPrpDration.getArr(0).getSubRiskCode();
			} else {
				subRiskCode = riskCode;
			}
		} catch (UserException arg6) {
			arg6.printStackTrace();
		} catch (Exception arg7) {
			arg7.printStackTrace();
		}

		return subRiskCode;
	}

	public void splitToKind(DbPool dbpool, String iCertiType) throws Exception {
		if (this.getSize() != 0) {
			String strWherePart = "";
			BLPrpCJplanKind blPrpCJplanKind = new BLPrpCJplanKind();
			PrpCJplanKindSchema prpCJplanKindSchema = null;
			HashMap dKindFeeTemper = new HashMap();
			HashMap dKindFeeCNYTemper = new HashMap();
			String sKey = "";
			int iRecorderCount = 0;
			String sCondition = "";
			BLPrpTplan prpTplan;
			if ("T".equals(iCertiType)) {
				sCondition = " ProposalNo = \'" + this.getArr(0).getCertiNo()
						+ "\'";
				prpTplan = new BLPrpTplan();
				prpTplan.query(dbpool, sCondition);
				iRecorderCount = prpTplan.getSize();
			} else {
				BLPrpCplan prpCplan;
				if ("P".equals(iCertiType)) {
					sCondition = " PolicyNo = \'" + this.getArr(0).getCertiNo()
							+ "\' And (EndorseNo Is Null Or EndorseNo = \'\')";
					prpCplan = new BLPrpCplan();
					prpCplan.query(dbpool, sCondition);
					iRecorderCount = prpCplan.getSize();
				} else if ("E".equals(iCertiType)) {
					sCondition = " EndorseNo = \'"
							+ this.getArr(0).getCertiNo() + "\'";
					prpCplan = new BLPrpCplan();
					prpCplan.query(dbpool, sCondition);
					iRecorderCount = prpCplan.getSize();
				} else if ("S".equals(iCertiType)) {
					sCondition = " ProposalNo = \'"
							+ this.getArr(0).getCertiNo() + "\'";
					prpTplan = new BLPrpTplan();
					prpTplan.query(dbpool, sCondition);
					iRecorderCount = prpTplan.getSize();
				} else if ("B".equals(iCertiType)) {
					sCondition = " ProposalNo = \'"
							+ this.getArr(0).getCertiNo() + "\'";
					BLPrpQplan prpQplan = new BLPrpQplan();
					prpQplan.query(dbpool, sCondition);
					iRecorderCount = prpQplan.getSize();
				}
			}
			System.out.println("this.getSize():"+this.getSize());
			for (int i = 0; i < this.getSize(); ++i) {
				if (this.getArr(i).getCertiType().equals(iCertiType)
						&& !"0".equals(this.getArr(i).getIsCombin())) {
					double dblSumPremium;
					double dblSumPremium2;
					double dblSumPremiumCNY1;
					int dblRation1;
					double dblKindFee1;
					double dblKindFeeSum1;
					double dblKindFeeCNY1;
					double dblKindFeeSumCNY1;
					int j;
					String temp;
					String sItemKindRate;
					int years;
					String dTempTotal;
					String useNatureCode;
					String subRiskCode;
					double money1;
					double money2;
					double money3;
					DateTime arg53;
					double money4;
					if (this.getArr(i).getCertiType().equals("T")) {
						strWherePart = " ProposalNo=\'"+ this.getArr(i).getCertiNo() + "\' ";
						BLPrpTitemKind itemKind = new BLPrpTitemKind();
						itemKind.query(dbpool, strWherePart);
						dblSumPremium = 0.0D;
						dblSumPremium2 = 0.0D;
						dblSumPremiumCNY1 = 0.0D;

						for (dblRation1 = 0; dblRation1 < itemKind.getSize(); ++dblRation1) {
							dblSumPremium += Double.parseDouble(Str.chgStrZero(itemKind.getArr(dblRation1).getPremium()));
							dblSumPremium2 += Double.parseDouble(Str.chgStrZero(itemKind.getArr(dblRation1).getPremium2()));
							dblSumPremiumCNY1 += Double.parseDouble(Str.chgStrZero(itemKind.getArr(dblRation1).getPremiumCny()));
						}

//						System.out.println("dblSumPremium:"+dblSumPremium);
//						System.out.println("dblSumPremium2:"+dblSumPremium2);
//						System.out.println("dblSumPremiumCNY1:"+dblSumPremiumCNY1);
						money1 = 0.0D;
						dblKindFee1 = 0.0D;
						dblKindFeeSum1 = 0.0D;
						dblKindFeeCNY1 = 0.0D;
						dblKindFeeSumCNY1 = 0.0D;
						System.out.println("itemKind.getSize():"+itemKind.getSize());
						for (j = 0; j < itemKind.getSize(); ++j) {
							prpCJplanKindSchema = new PrpCJplanKindSchema();
							prpCJplanKindSchema.setCertiType(this.getArr(i).getCertiType());
							prpCJplanKindSchema.setCertiNo(this.getArr(i).getCertiNo());
							prpCJplanKindSchema.setPolicyNo(this.getArr(i).getPolicyNo());
							prpCJplanKindSchema.setSerialNo(this.getArr(i).getSerialNo());
							prpCJplanKindSchema.setPayRefReason(this.getArr(i).getPayRefReason());
							prpCJplanKindSchema.setItemKindNo(itemKind.getArr(j).getItemKindNo());
							prpCJplanKindSchema.setRiskCode(this.getArr(i).getRiskCode());
							prpCJplanKindSchema.setKindCode(itemKind.getArr(j).getKindCode());System.out.println("KindCode:"+itemKind.getArr(j).getKindCode());
							prpCJplanKindSchema.setSubRiskCode(itemKind.getArr(j).getKindCode());
							
							if ("A".equals(this.getArr(i).getClassCode())|| "B".equals(this.getArr(i).getClassCode())) {
//								System.out.println("車險:");
								BLPrpTitemCar prpTitemCar = new BLPrpTitemCar();
								strWherePart = " ProposalNo=\'"+ itemKind.getArr(j).getProposalNo()+ "\' AND ItemNo=\'1\'";
								prpTitemCar.query(dbpool, strWherePart);
								temp = this.getArr(i).getRiskCode();
								dTempTotal = itemKind.getArr(j).getKindCode();
								useNatureCode = prpTitemCar.getArr(0).getUseNatureCode();
								sItemKindRate = prpTitemCar.getArr(0).getCarKindCode();
								years = DateUtil.getYearsCount(new DateTime(itemKind.getArr(j).getStartDate()),new DateTime(itemKind.getArr(j).getEndDate()));
								subRiskCode = this.findSubRiskCode(temp,dTempTotal, useNatureCode, years,sItemKindRate);
								prpCJplanKindSchema.setSubRiskCode(subRiskCode);
								
							}
							
							prpCJplanKindSchema.setClauseType("");
							sKey = itemKind.getArr(j).getKindCode() + "_"+ itemKind.getArr(j).getItemKindNo();
//							System.out.println("sKey:"+sKey);
							/* 
							if (j == itemKind.getSize() - 1&& i != iRecorderCount - 1) {//最後一筆
//								System.out.println("this.getArr(i).getPlanFee()):"+this.getArr(i).getPlanFee());
//								System.out.println("dblKindFeeSum1:"+dblKindFeeSum1);
//								System.out.println("money1:"+money1);
								dblKindFee1 = Double.parseDouble(this.getArr(i).getPlanFee())- dblKindFeeSum1;
								prpCJplanKindSchema.setKindFeeRate(""+ (100.0D - money1));
								dblKindFeeCNY1 = Str.round(Double.parseDouble(this.getArr(i).getPlanFeeCNY())- dblKindFeeSumCNY1, 0);
//								System.out.println("dblKindFeeCNY1:"+dblKindFeeCNY1);
							} else
							*/ 
							if (iRecorderCount > 1 && i == iRecorderCount - 1) {
								money2 = 0.0D;
								if (dKindFeeCNYTemper.get(sKey) != null) {
									money2 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue();
								}

								money3 = Double.parseDouble(itemKind.getArr(j).getPremiumCny());
								sItemKindRate = ""+ 100.0D * Double.parseDouble(itemKind.getArr(j).getPremium2()) / dblSumPremium2;
//								if (j == itemKind.getSize() - 1) {
//									money2 = dblKindFeeSumCNY1;
//									money3 = Double.parseDouble(this.getArr(i).getPlanFeeCNY());
//									sItemKindRate = "" + (100.0D - money1);
//								}

								if (dKindFeeTemper.get(sKey) != null) {
									dblKindFee1 = Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getPremium2()))- ((Double) dKindFeeTemper.get(sKey)).doubleValue();
									System.out.println("dblKindFee1:"+dblKindFee1);
								} else {
									dblKindFee1 = Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getPremium2()));
									System.out.println("2 dblKindFee1:"+dblKindFee1);
								}

								dblKindFeeCNY1 = Str.round(money3 - money2, 0);
								prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
								dblKindFeeSumCNY1 += dblKindFeeCNY1;
							} else {
								dblKindFee1 = Str.round(Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getPremium2()))* (Double.parseDouble(this.getArr(i).getPlanFee()) / dblSumPremium2),2);
								dblKindFeeSum1 += Str.round(dblKindFee1, 2);
								prpCJplanKindSchema.setKindFeeRate(""+ 100.0D* Double.parseDouble(itemKind.getArr(j).getPremium2())/ dblSumPremium2);
								dblKindFeeCNY1 = Str.round(dblKindFee1* Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getExchangeRateCny())),0);
								dblKindFeeSumCNY1 += dblKindFeeCNY1;
							}
							money1 += Str.round( 100.0D * Double.parseDouble(itemKind .getArr(j).getPremium2()) / dblSumPremium2, 2);
							prpCJplanKindSchema.setKindFee("" + dblKindFee1);
							prpCJplanKindSchema.setPlanFeeCurrency(itemKind.getArr(j).getCurrency2());
							prpCJplanKindSchema.setPlanFeeCNY(""+ dblKindFeeCNY1);
							prpCJplanKindSchema.setExchangeRate(itemKind.getArr(j)
									.getExchangeRateCny());
							prpCJplanKindSchema.setComCode(this.getArr(i)
									.getComCode());
							prpCJplanKindSchema.setLossType("");
							prpCJplanKindSchema.setRealPayRefFee("0");
							prpCJplanKindSchema.setFlag("");
							prpCJplanKindSchema.setOnAccFlag("0");
							prpCJplanKindSchema.setRealPayRefFlag("0");
							new DateTime();
							arg53 = new DateTime(DateTime.current(), 16);
							prpCJplanKindSchema.setInputDate(arg53.toString());
							blPrpCJplanKind.setArr(prpCJplanKindSchema);
							if (!dKindFeeTemper.containsKey(sKey)) {
								dKindFeeTemper.put(sKey,
										Double.valueOf(dblKindFee1));
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(dblKindFeeCNY1));
							} else {
								money4 = ((Double) dKindFeeTemper.get(sKey))
										.doubleValue() + dblKindFee1;
								dKindFeeTemper.put(sKey,
										Double.valueOf(Str.round(money4, 2)));
								money4 = ((Double) dKindFeeCNYTemper.get(sKey))
										.doubleValue() + dblKindFeeCNY1;
								dKindFeeCNYTemper.put(sKey,
										Double.valueOf(Str.round(money4, 2)));
							}
						} // end for
					} else {// end CertiType().equals("T")
						String arg47;
						if (this.getArr(i).getCertiType().equals("P")) {
							strWherePart = " PolicyNo=\'" + this.getArr(i).getCertiNo() + "\' ";
							BLPrpCitemKind itemKind = new BLPrpCitemKind();
							itemKind.query(dbpool, strWherePart);
							dblSumPremium = 0.0D;
							dblSumPremium2 = 0.0D;
							dblSumPremiumCNY1 = 0.0D;

							for (dblRation1 = 0; dblRation1 < itemKind.getSize(); ++dblRation1) {
								dblSumPremium += Double.parseDouble(Str.chgStrZero(itemKind.getArr(dblRation1).getPremium()));
								dblSumPremium2 += Double.parseDouble(Str.chgStrZero(itemKind.getArr(dblRation1).getPremium2()));
								dblSumPremiumCNY1 += Double.parseDouble(Str.chgStrZero(itemKind.getArr(dblRation1).getPremiumCny()));
							}

							money1 = 0.0D;
							dblKindFee1 = 0.0D;
							dblKindFeeSum1 = 0.0D;
							dblKindFeeCNY1 = 0.0D;
							dblKindFeeSumCNY1 = 0.0D;

							for (j = 0; j < itemKind.getSize(); ++j) {
								prpCJplanKindSchema = new PrpCJplanKindSchema();
								prpCJplanKindSchema.setCertiType(this.getArr(i).getCertiType());
								prpCJplanKindSchema.setCertiNo(this.getArr(i).getCertiNo());
								prpCJplanKindSchema.setPolicyNo(this.getArr(i).getPolicyNo());
								prpCJplanKindSchema.setSerialNo(this.getArr(i).getSerialNo());
								prpCJplanKindSchema.setPayRefReason(this.getArr(i).getPayRefReason());
								prpCJplanKindSchema.setItemKindNo(itemKind.getArr(j).getItemKindNo());
								prpCJplanKindSchema.setRiskCode(this.getArr(i).getRiskCode());
								prpCJplanKindSchema.setKindCode(itemKind.getArr(j).getKindCode());
								prpCJplanKindSchema.setSubRiskCode(itemKind.getArr(j).getKindCode());
								if ("A".equals(this.getArr(i).getClassCode())|| "B".equals(this.getArr(i).getClassCode())) {
									BLPrpCitemCar arg59 = new BLPrpCitemCar();
									strWherePart = " PolicyNo=\'"+ itemKind.getArr(j).getPolicyNo()+ "\' AND ItemNo=\'1\'";
									arg59.query(dbpool, strWherePart);
									temp = this.getArr(i).getRiskCode();
									dTempTotal = itemKind.getArr(j).getKindCode();
									useNatureCode = arg59.getArr(0).getUseNatureCode();
									sItemKindRate = arg59.getArr(0).getCarKindCode();
									years = DateUtil.getYearsCount(new DateTime(itemKind.getArr(j).getStartDate()),new DateTime(itemKind.getArr(j).getEndDate()));
									subRiskCode = this.findSubRiskCode(temp,dTempTotal, useNatureCode, years,sItemKindRate);
									prpCJplanKindSchema.setSubRiskCode(subRiskCode);
								}

								if ("C".equals(this.getArr(i).getClassCode())|| "M".equals(this.getArr(i).getClassCode())|| "E".equals(this.getArr(i).getClassCode())) {
									arg47 = this.getArr(i).getRiskCode();
									temp = this.findSubRiskCodeForCME(arg47);
									prpCJplanKindSchema.setSubRiskCode(temp);
								}

								if ("F".equals(this.getArr(i).getClassCode())|| "C1".equals(this.getArr(i).getClassCode())) {
									arg47 = this.getArr(i).getRiskCode();
									temp = itemKind.getArr(j).getKindCode();
									dTempTotal = this.findSubRiskCodeForFC(arg47, temp);
									prpCJplanKindSchema.setSubRiskCode(dTempTotal);
								}

								prpCJplanKindSchema.setClauseType("");
								sKey = itemKind.getArr(j).getKindCode() + "_"+ itemKind.getArr(j).getItemKindNo();
								if (j == itemKind.getSize() - 1 && i != iRecorderCount - 1) {//每一期的 最後一筆
									if(!"C".equals(itemKind.getArr(j).getRiskCode()) ){
										dblKindFee1 = Double.parseDouble(this.getArr(i).getPlanFee())- dblKindFeeSum1;
										prpCJplanKindSchema.setKindFeeRate(""+ (100.0D - money1));
										dblKindFeeCNY1 = Str.round(Double.parseDouble(this.getArr(i).getPlanFeeCNY())- dblKindFeeSumCNY1, 0);
									}else if("1".equals(itemKind.getArr(j).getFlag().substring(1, 2))){//主險
										dblKindFee1 = Double.parseDouble(this.getArr(i).getPlanFee())- dblKindFeeSum1;
										prpCJplanKindSchema.setKindFeeRate(""+ (100.0D - money1));
										dblKindFeeCNY1 = Str.round(Double.parseDouble(this.getArr(i).getPlanFeeCNY())- dblKindFeeSumCNY1, 0);
									}
								} else if (iRecorderCount > 1&& i == iRecorderCount - 1) {
									money2 = 0.0D;
									if (dKindFeeCNYTemper.get(sKey) != null) {
										money2 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue();
									}
									money3 = Double.parseDouble(itemKind.getArr(j).getPremiumCny());
									sItemKindRate = ""+ 100.0D* Double.parseDouble(itemKind.getArr(j).getPremium2())/ dblSumPremium2;
									if (j == itemKind.getSize() - 1) {
										money2 = dblKindFeeSumCNY1;
										money3 = Double.parseDouble(this.getArr(i).getPlanFeeCNY());
										sItemKindRate = "" + (100.0D - money1);
									}

									if (dKindFeeTemper.get(sKey) != null) {
										dblKindFee1 = Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getPremium2()))- ((Double) dKindFeeTemper.get(sKey)).doubleValue();
									} else {
										dblKindFee1 = Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getPremium2()));
									}
									dblKindFeeCNY1 = Str.round(money3 - money2, 0);
									prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
									dblKindFeeSumCNY1 += dblKindFeeCNY1;
								} else {
									if (dblSumPremium2 == 0.0D) {
										dblKindFee1 = 0.0D;
										prpCJplanKindSchema.setKindFeeRate("0");
										dblKindFeeSum1 += 0.0D;
									} else {
										dblKindFee1 = Str.round(Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getPremium2()))
														* (Double.parseDouble(this.getArr(i).getPlanFee()) / dblSumPremium2),2);
										prpCJplanKindSchema.setKindFeeRate(""+ 100.0D* Double.parseDouble(itemKind.getArr(j).getPremium2())
												/ dblSumPremium2);
										dblKindFeeSum1 += Str.round(100.0D* Double.parseDouble(itemKind.getArr(j).getPremium2())/ dblSumPremium2, 2);
									}
									if("MC".equals(itemKind.getArr(j).getRiskCode())){
										dblKindFeeCNY1 = Str.round(Double.parseDouble(itemKind.getArr(j).getPremiumCny()),0);
									}else{
										dblKindFeeCNY1 = Str.round(dblKindFee1* Double.parseDouble(Str.chgStrZero(itemKind.getArr(j).getExchangeRateCny())),0);	
									}
									dblKindFeeSumCNY1 += dblKindFeeCNY1;
								}

								if (dblSumPremium2 == 0.0D) {
									money1 += 0.0D;
								} else {
									money1 += Str.round(100.0D* Double.parseDouble(itemKind.getArr(j).getPremium2())/ dblSumPremium2, 2);
								}

								prpCJplanKindSchema.setKindFee("" + dblKindFee1);
								prpCJplanKindSchema.setPlanFeeCurrency(itemKind.getArr(j).getCurrency2());
								prpCJplanKindSchema.setPlanFeeCNY(""+ dblKindFeeCNY1);
								prpCJplanKindSchema.setExchangeRate(itemKind.getArr(j).getExchangeRateCny());
								prpCJplanKindSchema.setComCode(this.getArr(i).getComCode());
								prpCJplanKindSchema.setLossType("");
								prpCJplanKindSchema.setRealPayRefFee("0");
								prpCJplanKindSchema.setFlag("");
								prpCJplanKindSchema.setOnAccFlag("0");
								prpCJplanKindSchema.setRealPayRefFlag("0");
								new DateTime();
								arg53 = new DateTime(DateTime.current(), 16);
								prpCJplanKindSchema.setInputDate(arg53.toString());
								blPrpCJplanKind.setArr(prpCJplanKindSchema);
								if (!dKindFeeTemper.containsKey(sKey)) {
									dKindFeeTemper.put(sKey,Double.valueOf(dblKindFee1));
									dKindFeeCNYTemper.put(sKey,Double.valueOf(dblKindFeeCNY1));
								} else {
									money4 = ((Double) dKindFeeTemper.get(sKey)).doubleValue() + dblKindFee1;
									dKindFeeTemper.put(sKey, Double.valueOf(Str.round(money4, 2)));
									money4 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue()+ dblKindFeeCNY1;
									dKindFeeCNYTemper.put(sKey, Double.valueOf(Str.round(money4, 2)));
								}
							}
						} else if (this.getArr(i).getCertiType().equals("E")) {
							strWherePart = " EndorseNo=\'"+ this.getArr(i).getCertiNo() + "\' ";
							BLPrpPitemKind arg41 = new BLPrpPitemKind();
							arg41.query(dbpool, strWherePart);
							dblSumPremium = 0.0D;
							dblSumPremium2 = 0.0D;
							dblSumPremiumCNY1 = 0.0D;

							for (dblRation1 = 0; dblRation1 < arg41.getSize(); ++dblRation1) {
								dblSumPremium += Double.parseDouble(Str.chgStrZero(arg41.getArr(dblRation1).getChgPremium()));
								dblSumPremium2 += Double.parseDouble(Str.chgStrZero(arg41.getArr(dblRation1).getChgPremium2()));
								dblSumPremiumCNY1 += Double.parseDouble(Str.chgStrZero(arg41.getArr(dblRation1).getChgPremiumCny()));
							}

							money1 = 0.0D;
							dblKindFee1 = 0.0D;
							dblKindFeeSum1 = 0.0D;
							dblKindFeeCNY1 = 0.0D;
							dblKindFeeSumCNY1 = 0.0D;

							for (j = 0; j < arg41.getSize(); ++j) {
								prpCJplanKindSchema = new PrpCJplanKindSchema();
								prpCJplanKindSchema.setCertiType(this.getArr(i).getCertiType());
								prpCJplanKindSchema.setCertiNo(this.getArr(i).getCertiNo());
								prpCJplanKindSchema.setPolicyNo(this.getArr(i).getPolicyNo());
								prpCJplanKindSchema.setSerialNo(this.getArr(i).getSerialNo());
								prpCJplanKindSchema.setPayRefReason(this.getArr(i).getPayRefReason());
								prpCJplanKindSchema.setItemKindNo(arg41.getArr(j).getItemKindNo());
								prpCJplanKindSchema.setRiskCode(this.getArr(i).getRiskCode());
								prpCJplanKindSchema.setKindCode(arg41.getArr(j).getKindCode());
								prpCJplanKindSchema.setSubRiskCode(arg41.getArr(j).getKindCode());
								if ("A".equals(this.getArr(i).getClassCode())|| "B".equals(this.getArr(i).getClassCode())) {
									BLPrpPitemCar arg58 = new BLPrpPitemCar();
									strWherePart = " EndorseNo=\'"+ arg41.getArr(j).getEndorseNo()+ "\' AND ItemNo=\'1\'";
									arg58.query(dbpool, strWherePart);
									temp = this.getArr(i).getRiskCode();
									dTempTotal = arg41.getArr(j).getKindCode();
									useNatureCode = arg58.getArr(0).getUseNatureCode();
									sItemKindRate = arg58.getArr(0).getCarKindCode();
									years = DateUtil.getYearsCount(new DateTime(arg41.getArr(j).getStartDate()),new DateTime(arg41.getArr(j).getEndDate()));
									subRiskCode = this.findSubRiskCode(temp,dTempTotal, useNatureCode, years,sItemKindRate);
									prpCJplanKindSchema.setSubRiskCode(subRiskCode);
								}

								if ("C".equals(this.getArr(i).getClassCode())|| "M".equals(this.getArr(i).getClassCode())|| "E".equals(this.getArr(i).getClassCode())) {
									arg47 = this.getArr(i).getRiskCode();
									temp = this.findSubRiskCodeForCME(arg47);
									prpCJplanKindSchema.setSubRiskCode(temp);
								}

								if ("F".equals(this.getArr(i).getClassCode())|| "C1".equals(this.getArr(i).getClassCode())) {
									arg47 = this.getArr(i).getRiskCode();
									temp = arg41.getArr(j).getKindCode();
									dTempTotal = this.findSubRiskCodeForFC(arg47, temp);
									prpCJplanKindSchema.setSubRiskCode(dTempTotal);
								}

								prpCJplanKindSchema.setClauseType("");
								sKey = arg41.getArr(j).getKindCode() + "_"+ arg41.getArr(j).getItemKindNo();
								if (j == arg41.getSize() - 1&& i != iRecorderCount - 1) {
									dblKindFee1 = Double.parseDouble(this.getArr(i).getPlanFee())- dblKindFeeSum1;
									prpCJplanKindSchema.setKindFeeRate(""+ (100.0D - money1));
									dblKindFeeCNY1 = Str.round(Double.parseDouble(this.getArr(i).getPlanFeeCNY())- dblKindFeeSumCNY1, 0);
								} else if (iRecorderCount > 1&& i == iRecorderCount - 1) {
									money2 = 0.0D;
									if (dKindFeeCNYTemper.get(sKey) != null) {
										money2 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue();
									}

									money3 = Double.parseDouble(arg41.getArr(j).getChgPremiumCny());
									sItemKindRate = ""+ 10000.0D* Double.parseDouble(arg41.getArr(j).getChgPremium2())/ dblSumPremium2;
									if (j == arg41.getSize() - 1) {
										money2 = dblKindFeeSumCNY1;
										money3 = Double.parseDouble(this.getArr(i).getPlanFeeCNY());
										sItemKindRate = "" + (100.0D - money1);
									}

									if (dKindFeeTemper.get(sKey) != null) {
										dblKindFee1 = Double.parseDouble(Str.chgStrZero(arg41.getArr(j).getChgPremium2()))- ((Double) dKindFeeTemper.get(sKey)).doubleValue();
									} else {
										dblKindFee1 = Double.parseDouble(Str.chgStrZero(arg41.getArr(j).getChgPremium2()));
									}

									dblKindFeeCNY1 = Str.round(money3 - money2, 0);
									prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
									dblKindFeeSumCNY1 += dblKindFeeCNY1;
								} else if (!arg41.getArr(j).getChgPremium2().equals("")&& !arg41.getArr(j).getChgPremium2().equals("0.0")) {
									if (!"0".equals(this.getArr(i).getPlanFee())&& dblSumPremium2 != 0.0D) {
										dblKindFee1 = Str.round(Double.parseDouble(Str.chgStrZero(arg41.getArr(j).getChgPremium2()))
														* (Double.parseDouble(this.getArr(i).getPlanFee()) / dblSumPremium2),2);
										prpCJplanKindSchema.setKindFeeRate(""+ 100.0D* Double.parseDouble(arg41.getArr(j).getChgPremium2())/ dblSumPremium2);
									} else {
										dblKindFee1 = 0.0D;
										prpCJplanKindSchema.setKindFeeRate("0");
									}

									dblKindFeeSum1 += Str.round(dblKindFee1, 2);
									//mantis：MAR0069，處理人員：DP0713，需求單編號：MC的批單收據列印顯示調整 Start
									if("MC".equals(arg41.getArr(j).getRiskCode())){
										dblKindFeeCNY1 = Str.round(Double.parseDouble(arg41.getArr(j).getChgPremiumCny()),0);
									}else{
										dblKindFeeCNY1 = Str.round(dblKindFee1* Double.parseDouble(Str.chgStrZero(arg41.getArr(j).getExchangeRateCny())),0);
									}
									//mantis：MAR0069，處理人員：DP0713，需求單編號：MC的批單收據列印顯示調整 End
									dblKindFeeSumCNY1 += dblKindFeeCNY1;
								} else {
									prpCJplanKindSchema.setKindFeeRate("0");
									dblKindFee1 = 0.0D;
									dblKindFeeCNY1 = 0.0D;
								}

								if (dblSumPremium2 != 0.0D) {
									money1 += Str.round(100.0D* Double.parseDouble(arg41.getArr(j).getChgPremium2())/ dblSumPremium2, 2);
								}

								prpCJplanKindSchema.setKindFee("" + dblKindFee1);
								prpCJplanKindSchema.setPlanFeeCurrency(arg41.getArr(j).getCurrency2());
								prpCJplanKindSchema.setPlanFeeCNY(""+ dblKindFeeCNY1);
								prpCJplanKindSchema.setExchangeRate(arg41.getArr(j).getExchangeRateCny());
								prpCJplanKindSchema.setComCode(this.getArr(i).getComCode());
								prpCJplanKindSchema.setLossType("");
								prpCJplanKindSchema.setRealPayRefFee("0");
								prpCJplanKindSchema.setFlag("");
								prpCJplanKindSchema.setOnAccFlag("0");
								prpCJplanKindSchema.setRealPayRefFlag("0");
								new DateTime();
								arg53 = new DateTime(DateTime.current(), 16);
								prpCJplanKindSchema.setInputDate(arg53.toString());
								blPrpCJplanKind.setArr(prpCJplanKindSchema);
								if (!dKindFeeTemper.containsKey(sKey)) {
									dKindFeeTemper.put(sKey,Double.valueOf(dblKindFee1));
									dKindFeeCNYTemper.put(sKey,Double.valueOf(dblKindFeeCNY1));
								} else {
									money4 = ((Double) dKindFeeTemper.get(sKey)).doubleValue() + dblKindFee1;
									dKindFeeTemper.put(sKey, Double.valueOf(Str.round(money4, 2)));
									money4 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue()+ dblKindFeeCNY1;
									dKindFeeCNYTemper.put(sKey, Double.valueOf(Str.round(money4, 2)));
								}
							}
						} else if (this.getArr(i).getCertiType()
								.equals("S")) {
							double arg38 = 0.0D;
							double dblSumPremiumCNY = 0.0D;
							double dblRation = 0.0D;
							double dblKindFee = 0.0D;
							double dblKindFeeSum = 0.0D;
							double dblKindFeeCNY = 0.0D;
							double dblKindFeeSumCNY = 0.0D;
							strWherePart = " ProposalNo=\'"+ this.getArr(i).getCertiNo() + "\' ";
							BLPrpTCommissionDetail blPrpTCommissionDetail = new BLPrpTCommissionDetail();
							blPrpTCommissionDetail.query(dbpool, strWherePart);

							int arg44;
							for (arg44 = 0; arg44 < blPrpTCommissionDetail.getSize(); ++arg44) {
								arg38 += Double.parseDouble(Str.chgStrZero(blPrpTCommissionDetail.getArr(arg44).getCostFee()));
								dblSumPremiumCNY += Double.parseDouble(Str.chgStrZero(blPrpTCommissionDetail.getArr(arg44).getCostFee()));
							}

							if (arg38 != 0.0D) {
								for (arg44 = 0; arg44 < blPrpTCommissionDetail.getSize(); ++arg44) {
									prpCJplanKindSchema = new PrpCJplanKindSchema();
									prpCJplanKindSchema.setCertiType(this.getArr(i).getCertiType());
									prpCJplanKindSchema.setCertiNo(this.getArr(i).getCertiNo());
									prpCJplanKindSchema.setPolicyNo(this.getArr(i).getPolicyNo());
									prpCJplanKindSchema.setSerialNo(this.getArr(i).getSerialNo());
									prpCJplanKindSchema.setPayRefReason(this.getArr(i).getPayRefReason());
									prpCJplanKindSchema.setItemKindNo(blPrpTCommissionDetail.getArr(arg44).getSerialNo());
									prpCJplanKindSchema.setRiskCode(this.getArr(i).getRiskCode());
									prpCJplanKindSchema.setKindCode(blPrpTCommissionDetail.getArr(arg44).getKindCode());
									prpCJplanKindSchema.setSubRiskCode(blPrpTCommissionDetail.getArr(arg44).getKindCode());
									if ("A".equals(this.getArr(i).getClassCode())|| "B".equals(this.getArr(i).getClassCode())) {
										strWherePart = " ProposalNo=\'"+ this.getArr(i).getCertiNo()+ "\' AND KindCode=\'"+ blPrpTCommissionDetail.getArr(arg44).getKindCode() + "\'";
										BLPrpTitemKind dateTime = new BLPrpTitemKind();
										dateTime.query(dbpool, strWherePart);
										BLPrpTitemCar arg52 = new BLPrpTitemCar();
										strWherePart = " ProposalNo=\'"+ dateTime.getArr(0).getProposalNo()+ "\' AND ItemNo=\'1\'";
										arg52.query(dbpool, strWherePart);
										arg47 = this.getArr(i).getRiskCode();
										temp = blPrpTCommissionDetail.getArr(arg44).getKindCode();
										dTempTotal = arg52.getArr(0).getUseNatureCode();
										useNatureCode = arg52.getArr(0).getCarKindCode();
										int arg56 = DateUtil.getYearsCount(new DateTime(dateTime.getArr(0).getStartDate()),new DateTime(dateTime.getArr(0).getEndDate()));
										String arg57 = this.findSubRiskCode(arg47, temp, dTempTotal, arg56,useNatureCode);
										prpCJplanKindSchema.setSubRiskCode(arg57);
									}

									String arg45;
									String arg50;
									if ("C".equals(this.getArr(i).getClassCode())|| "M".equals(this.getArr(i).getClassCode())|| "E".equals(this.getArr(i).getClassCode())) {
										arg45 = this.getArr(i).getRiskCode();
										arg50 = this.findSubRiskCodeForCME(arg45);
										prpCJplanKindSchema.setSubRiskCode(arg50);
									}

									if ("F".equals(this.getArr(i).getClassCode())|| "C1".equals(this.getArr(i).getClassCode())) {
										arg45 = this.getArr(i).getRiskCode();
										arg50 = blPrpTCommissionDetail.getArr(arg44).getKindCode();
										arg47 = this.findSubRiskCodeForFC(arg45, arg50);
										prpCJplanKindSchema.setSubRiskCode(arg47);
									}

									prpCJplanKindSchema.setClauseType("");
									sKey = blPrpTCommissionDetail.getArr(arg44).getKindCode() + "_1";
									if (arg44 == blPrpTCommissionDetail.getSize() - 1&& i != iRecorderCount - 1) {
										dblKindFee = Double.parseDouble(this.getArr(i).getPlanFee())- dblKindFeeSum;
										prpCJplanKindSchema.setKindFeeRate(""+ (100.0D - dblRation));
										dblKindFeeCNY = Str.round(Double.parseDouble(this.getArr(i).getPlanFeeCNY())- dblKindFeeSumCNY, 0);
									} else if (iRecorderCount > 1&& i == iRecorderCount - 1) {
										double arg49 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue();
										money2 = Double.parseDouble(blPrpTCommissionDetail.getArr(arg44).getCostFee());
										dTempTotal = ""+ 100.0D* Double.parseDouble(blPrpTCommissionDetail.getArr(arg44).getCostFee()) / arg38;
										if (arg44 == blPrpTCommissionDetail.getSize() - 1) {
											arg49 = dblKindFeeSumCNY;
											money2 = Double.parseDouble(this.getArr(i).getPlanFeeCNY());
											dTempTotal = ""+ (100.0D - dblRation);
										}

										dblKindFee = Double.parseDouble(Str.chgStrZero(blPrpTCommissionDetail.getArr(arg44).getCostFee()))
												- ((Double) dKindFeeTemper.get(sKey)).doubleValue();
										dblKindFeeCNY = Str.round(money2 - arg49, 0);
										prpCJplanKindSchema.setKindFeeRate(dTempTotal);
										dblKindFeeSumCNY += dblKindFeeCNY;
									} else {
										dblKindFee = Str.round(Double.parseDouble(Str.chgStrZero(blPrpTCommissionDetail.getArr(arg44).getCostFee()))
														* (Double.parseDouble(this.getArr(i).getPlanFee()) / arg38),2);
										dblKindFeeSum += Str.round(dblKindFee,2);
										prpCJplanKindSchema.setKindFeeRate(""+ 100.0D* Double.parseDouble(blPrpTCommissionDetail.getArr(arg44).getCostFee())/ arg38);
										dblKindFeeCNY = Str.round(Double.parseDouble(Str.chgStrZero(blPrpTCommissionDetail.getArr(arg44).getCostFee()))
														* (Double.parseDouble(this.getArr(i).getPlanFeeCNY()) / dblSumPremiumCNY),0);
										dblKindFeeSumCNY += dblKindFeeCNY;
									}

									dblRation += Str.round(100.0D* Double.parseDouble(blPrpTCommissionDetail.getArr(arg44).getCostFee())/ arg38, 2);
									if (Str.round(dblKindFee, 2) != 0.0D&& Str.round(dblKindFeeCNY, 2) != 0.0D) {
										prpCJplanKindSchema.setKindFee(""+ dblKindFee);
										prpCJplanKindSchema.setPlanFeeCurrency(this.getArr(i).getPlanFeeCurrency());
										prpCJplanKindSchema.setPlanFeeCNY(""+ dblKindFeeCNY);
										prpCJplanKindSchema.setExchangeRate("1");
										prpCJplanKindSchema.setComCode(this.getArr(i).getComCode());
										prpCJplanKindSchema.setLossType("");
										prpCJplanKindSchema.setRealPayRefFee("0");
										prpCJplanKindSchema.setFlag("");
										prpCJplanKindSchema.setOnAccFlag("0");
										prpCJplanKindSchema.setRealPayRefFlag("0");
										new DateTime();
										DateTime arg51 = new DateTime(DateTime.current(), 16);
										prpCJplanKindSchema.setInputDate(arg51.toString());
										blPrpCJplanKind.setArr(prpCJplanKindSchema);
										if (!dKindFeeTemper.containsKey(sKey)) {
											dKindFeeTemper.put(sKey,Double.valueOf(dblKindFee));
											dKindFeeCNYTemper.put(sKey, Double.valueOf(dblKindFeeCNY));
										} else {
											double arg46 = ((Double) dKindFeeTemper.get(sKey)).doubleValue()+ dblKindFee;
											dKindFeeTemper.put(sKey, Double.valueOf(Str.round(arg46, 2)));
											arg46 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue()+ dblKindFeeCNY;
											dKindFeeCNYTemper.put(sKey, Double.valueOf(Str.round(arg46, 2)));
										}
									}
								}
							}
						} else if (this.getArr(i).getCertiType().equals("B")) {
							strWherePart = " ProposalNo=\'"+ this.getArr(i).getCertiNo() + "\' ";
							BLPrpQitemKind blPrpQitemKind = new BLPrpQitemKind();
							blPrpQitemKind.query(dbpool, strWherePart);
							dblSumPremium = 0.0D;
							dblSumPremium2 = 0.0D;
							dblSumPremiumCNY1 = 0.0D;

							for (dblRation1 = 0; dblRation1 < blPrpQitemKind.getSize(); ++dblRation1) {
								dblSumPremium += Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(dblRation1).getPremium()));
								dblSumPremium2 += Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(dblRation1).getPremium2()));
								dblSumPremiumCNY1 += Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(dblRation1).getPremiumCny()));
							}

							money1 = 0.0D;
							dblKindFee1 = 0.0D;
							dblKindFeeSum1 = 0.0D;
							dblKindFeeCNY1 = 0.0D;
							dblKindFeeSumCNY1 = 0.0D;

							for (j = 0; j < blPrpQitemKind.getSize(); ++j) {
								prpCJplanKindSchema = new PrpCJplanKindSchema();
								prpCJplanKindSchema.setCertiType(this.getArr(i).getCertiType());
								prpCJplanKindSchema.setCertiNo(this.getArr(i).getCertiNo());
								prpCJplanKindSchema.setPolicyNo(this.getArr(i).getPolicyNo());
								prpCJplanKindSchema.setSerialNo(this.getArr(i).getSerialNo());
								prpCJplanKindSchema.setPayRefReason(this.getArr(i).getPayRefReason());
								prpCJplanKindSchema.setItemKindNo(blPrpQitemKind.getArr(j).getItemKindNo());
								prpCJplanKindSchema.setRiskCode(this.getArr(i).getRiskCode());
								prpCJplanKindSchema.setKindCode(blPrpQitemKind.getArr(j).getKindCode());
								prpCJplanKindSchema.setSubRiskCode(blPrpQitemKind.getArr(j).getKindCode());
								if ("A".equals(this.getArr(i).getClassCode())|| "B".equals(this.getArr(i).getClassCode())) {
									BLPrpQitemCar dateTime1 = new BLPrpQitemCar();
									strWherePart = " ProposalNo=\'"+ blPrpQitemKind.getArr(j).getProposalNo()+ "\' AND ItemNo=\'1\'";
									dateTime1.query(dbpool, strWherePart);
									temp = this.getArr(i).getRiskCode();
									dTempTotal = blPrpQitemKind.getArr(j).getKindCode();
									useNatureCode = dateTime1.getArr(0).getUseNatureCode();
									sItemKindRate = dateTime1.getArr(0).getCarKindCode();
									years = DateUtil.getYearsCount(new DateTime(blPrpQitemKind.getArr(j).getStartDate()),new DateTime(blPrpQitemKind.getArr(j).getEndDate()));
									subRiskCode = this.findSubRiskCode(temp,dTempTotal, useNatureCode, years,sItemKindRate);
									prpCJplanKindSchema.setSubRiskCode(subRiskCode);
								}

								if ("C".equals(this.getArr(i).getClassCode())|| "M".equals(this.getArr(i).getClassCode())|| "E".equals(this.getArr(i)												.getClassCode())) {
									arg47 = this.getArr(i).getRiskCode();
									temp = this.findSubRiskCodeForCME(arg47);
									prpCJplanKindSchema.setSubRiskCode(temp);
								}

								if ("F".equals(this.getArr(i).getClassCode())|| "C1".equals(this.getArr(i).getClassCode())) {
									arg47 = this.getArr(i).getRiskCode();
									temp = blPrpQitemKind.getArr(j).getKindCode();
									dTempTotal = this.findSubRiskCodeForFC(arg47, temp);
									prpCJplanKindSchema.setSubRiskCode(dTempTotal);
								}

								prpCJplanKindSchema.setClauseType("");
								sKey = blPrpQitemKind.getArr(j).getKindCode()+ "_"+ blPrpQitemKind.getArr(j).getItemKindNo();
								if (j == blPrpQitemKind.getSize() - 1
										&& i != iRecorderCount - 1) {
									dblKindFee1 = Double.parseDouble(this.getArr(i).getPlanFee())- dblKindFeeSum1;
									prpCJplanKindSchema.setKindFeeRate(""+ (100.0D - money1));
									dblKindFeeCNY1 = Str.round(Double.parseDouble(this.getArr(i).getPlanFeeCNY())- dblKindFeeSumCNY1, 0);
								} else if (iRecorderCount > 1 && i == iRecorderCount - 1) {
									money2 = 0.0D;
									if (dKindFeeCNYTemper.get(sKey) != null) {
										money2 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue();
									}

									money3 = Double.parseDouble(blPrpQitemKind.getArr(j).getPremiumCny());
									sItemKindRate = ""+ 100.0D* Double.parseDouble(blPrpQitemKind.getArr(j).getPremium2())/ dblSumPremium2;
									if (j == blPrpQitemKind.getSize() - 1) {
										money2 = dblKindFeeSumCNY1;
										money3 = Double.parseDouble(this.getArr(i).getPlanFeeCNY());
										sItemKindRate = "" + (100.0D - money1);
									}

									if (dKindFeeTemper.get(sKey) != null) {
										dblKindFee1 = Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(j).getPremium2()))
												- ((Double) dKindFeeTemper.get(sKey)).doubleValue();
									} else {
										dblKindFee1 = Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(j).getPremium2()));
									}

									dblKindFeeCNY1 = Str.round(money3 - money2, 0);
									prpCJplanKindSchema.setKindFeeRate(sItemKindRate);
									dblKindFeeSumCNY1 += dblKindFeeCNY1;
								} else {
									dblKindFee1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(j).getPremium2()))
													* (Double.parseDouble(this.getArr(i).getPlanFee()) / dblSumPremium2),2);
									dblKindFeeSum1 += Str.round(dblKindFee1, 2);
									prpCJplanKindSchema.setKindFeeRate(""+ 100.0D* Double.parseDouble(blPrpQitemKind.getArr(j).getPremium2())/ dblSumPremium2);
									dblKindFeeCNY1 = Str.round(dblKindFee1* Double.parseDouble(Str.chgStrZero(blPrpQitemKind.getArr(j).getExchangeRateCny())),0);
									dblKindFeeSumCNY1 += dblKindFeeCNY1;
								}

								money1 += Str.round(100.0D* Double.parseDouble(blPrpQitemKind.getArr(j).getPremium2())/ dblSumPremium2, 2);
								prpCJplanKindSchema.setKindFee("" + dblKindFee1);
								prpCJplanKindSchema.setPlanFeeCurrency(blPrpQitemKind.getArr(j).getCurrency2());
								prpCJplanKindSchema.setPlanFeeCNY(""+ dblKindFeeCNY1);
								prpCJplanKindSchema.setExchangeRate(blPrpQitemKind.getArr(j).getExchangeRateCny());
								prpCJplanKindSchema.setComCode(this.getArr(i).getComCode());
								prpCJplanKindSchema.setLossType("");
								prpCJplanKindSchema.setRealPayRefFee("0");
								prpCJplanKindSchema.setFlag("");
								prpCJplanKindSchema.setOnAccFlag("0");
								prpCJplanKindSchema.setRealPayRefFlag("0");
								new DateTime();
								arg53 = new DateTime(DateTime.current(), 16);
								prpCJplanKindSchema.setInputDate(arg53.toString());
								blPrpCJplanKind.setArr(prpCJplanKindSchema);
								if (!dKindFeeTemper.containsKey(sKey)) {
									dKindFeeTemper.put(sKey,Double.valueOf(dblKindFee1));
									dKindFeeCNYTemper.put(sKey,Double.valueOf(dblKindFeeCNY1));
								} else {
									money4 = ((Double) dKindFeeTemper.get(sKey))
											.doubleValue() + dblKindFee1;
									dKindFeeTemper.put(sKey, Double.valueOf(Str.round(money4, 2)));
									money4 = ((Double) dKindFeeCNYTemper.get(sKey)).doubleValue()+ dblKindFeeCNY1;
									dKindFeeCNYTemper.put(sKey, Double.valueOf(Str.round(money4, 2)));
								}
							}
						}
					}
				}
			}
			blPrpCJplanKind.save(dbpool);
		}
	}

	public boolean chkCombinRisk(DbPool dbpool, String iRiskCode)
			throws Exception {
		DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
		dbPrpDrisk.getInfo(dbpool, iRiskCode);
		String strFlag = dbPrpDrisk.getFlag();
		return strFlag.length() >= 2 && strFlag.substring(1, 2).equals("2");
	}

	public boolean transCarShipTax(DbPool dbPool, String iCertiType,
			String iCertiNo) throws UserException, Exception {
		boolean intStatus = false;
		PrpallCarShipInterf prpallCarShipInterf = new PrpallCarShipInterf();
		int intStatus1;
		if (iCertiType.equals("P")) {
			intStatus1 = prpallCarShipInterf.transCarShipTaxC(dbPool, iCertiNo);
		} else {
			if (!iCertiType.equals("E")) {
				throw new UserException(-98, -1167,
						"PrpTransSff.transCarShipTax", "不存在该业务类型的税金处理："
								+ iCertiType);
			}

			intStatus1 = prpallCarShipInterf.transCarShipTaxE(dbPool, iCertiNo);
		}

		return intStatus1 > 0;
	}

	public void transCovernoteData(String iCertiType, String iCertiNo)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConfig.getProperty("PAYMENTDATASOURCE"));

		try {
			dbpool.beginTransaction();
			this.transCovernoteData(dbpool, iCertiType, iCertiNo);
			dbpool.commitTransaction();
		} catch (UserException arg8) {
			dbpool.rollbackTransaction();
			throw arg8;
		} catch (Exception arg9) {
			dbpool.rollbackTransaction();
			throw arg9;
		} finally {
			dbpool.close();
		}

	}

	public void transCovernoteData(DbPool dbpool, String iCertiType,
			String iCertiNo) throws UserException, Exception {
		if (iCertiType.equals("P")) {
			this.transCovernotePolicy(dbpool, iCertiNo);
		} else {
			if (!iCertiType.equals("E")) {
				throw new UserException(-98, -1167,
						"BLPrpJplanFee.transCovernoteData", "没有此业务类型："
								+ iCertiType);
			}

			this.transCovernoteEndor(dbpool, iCertiNo);
		}

		this.save(dbpool);
	}

	public void transCovernotePolicy(DbPool dbpool, String iPolicyNo)
			throws UserException, Exception {
		DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote();
		BLPrpCproject blPrpCproject = new BLPrpCproject();
		String strWherePart = "";
		boolean intReturn = false;
		double dbSumPremium = 0.0D;
		double dbSumPremiumCNY = 0.0D;
		boolean intPlanCount = true;
		boolean isChinese = true;
		boolean isCombin = false;
		int arg22 = dbPrpCmainCovernote.getInfo(dbpool, iPolicyNo);
		if (arg22 == 100) {
			throw new UserException(-98, -1167,
					"PrpTransSff.transCovernotePolicy", "无此保单信息：" + iPolicyNo);
		} else {
			strWherePart = "PolicyNo=\'" + iPolicyNo
					+ "\' AND (EndorseNo IS NULL OR EndorseNo=\'\')";
			blPrpCproject.query(dbpool, strWherePart, 0);
			int arg23 = blPrpCproject.getSize();
			PrpCJplanSchema schema = null;
			BLPrpDagent blPrpDagent = null;
			BLPrpDuser blPrpDuser = null;
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
			dbPrpDrisk.getInfo(dbpool, dbPrpCmainCovernote.getRiskCode());
			if (dbPrpDrisk.getFlag().length() >= 2
					&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			int i;
			for (i = 0; i < blPrpCproject.getSize(); ++i) {
				dbSumPremium += Str.round(Double.parseDouble(blPrpCproject
						.getArr(i).getPlanFee()), 2);
				dbSumPremiumCNY += Str.round(Double.parseDouble(blPrpCproject
						.getArr(i).getPlanFee()), 2);
			}

			for (i = 0; i < blPrpCproject.getSize(); ++i) {
				schema = new PrpCJplanSchema();
				schema.setCertiType("P");
				schema.setCertiNo(iPolicyNo);
				schema.setSerialNo(blPrpCproject.getArr(i).getSerialNo());
				schema.setPolicyNo(iPolicyNo);
				schema.setPayRefReason(blPrpCproject.getArr(i).getPayReason());
				schema.setClassCode(dbPrpCmainCovernote.getClassCode());
				schema.setRiskCode(dbPrpCmainCovernote.getRiskCode());
				schema.setContractNo(dbPrpCmainCovernote.getContractNo());
				schema.setAppliCode(dbPrpCmainCovernote.getAppliCode());
				schema.setAppliName(dbPrpCmainCovernote.getAppliName());
				schema.setInsuredCode(dbPrpCmainCovernote.getInsuredCode());
				schema.setInsuredName(dbPrpCmainCovernote.getInsuredName());
				schema.setStartDate(dbPrpCmainCovernote.getStartDate());
				schema.setEndDate(dbPrpCmainCovernote.getEndDate());
				schema.setValidDate(dbPrpCmainCovernote.getOperateDate());
				schema.setTCol1(dbPrpCmainCovernote.getInputDate());
				schema.setPayNo(blPrpCproject.getArr(i).getPayNo());
				schema.setTotalPayNo("" + arg23);
				schema.setPlanFeeCurrency(blPrpCproject.getArr(i)
						.getCurrency2());
				schema.setPlanFee(blPrpCproject.getArr(i).getPlanFee2());
				schema.setPlanFeeCNY(blPrpCproject.getArr(i).getPlanFee());
				schema.setExchangeRate("1.0");
				schema.setPlanDate(blPrpCproject.getArr(i).getPlanDate());
				schema.setComCode(dbPrpCmainCovernote.getComCode());
				schema.setMakeCom(dbPrpCmainCovernote.getMakeCom());
				schema.setChannelType(dbPrpCmainCovernote.getChannelType());
				schema.setBusinessNature(dbPrpCmainCovernote
						.getBusinessNature());
				schema.setAgentCode(dbPrpCmainCovernote.getAgentCode());
				blPrpDagent = new BLPrpDagent();
				schema.setAgentName(blPrpDagent.translateCode(
						dbPrpCmainCovernote.getAgentCode(), isChinese));
				schema.setHandler1Code(dbPrpCmainCovernote.getHandler1Code());
				blPrpDuser = new BLPrpDuser();
				schema.setHandler1Name(blPrpDuser.translateCode(
						dbPrpCmainCovernote.getHandler1Code(), isChinese));
				schema.setHandlerCode(dbPrpCmainCovernote.getHandlerCode());
				schema.setUnderWriteDate(DateTime.current().toString()
						.substring(0, 10));
				schema.setUnderWriteFlag(dbPrpCmainCovernote
						.getUnderWriteFlag());
				schema.setCoinsFlag(dbPrpCmainCovernote.getCoinsFlag());
				strWherePart = "ComCode=\'" + dbPrpCmainCovernote.getComCode()
						+ "\'";
				blPrpDcompany.query(dbpool, strWherePart);
				String strCenterCode = blPrpDcompany.getArr(0).getAcntUnit();
				String strBranchCode = strCenterCode;
				if (strCenterCode == null || strCenterCode.equals("")) {
					strBranchCode = strCenterCode;
				}

				schema.setCenterCode(strCenterCode);
				schema.setBranchCode(strBranchCode);
				if (isCombin) {
					if (schema.getClassCode().equals("05")) {
						schema.setIsCombin("2");
					} else {
						schema.setIsCombin("1");
					}
				} else {
					schema.setIsCombin("0");
				}

				schema.setRealPayRefFee("0");
				schema.setRealPayRefFeeCNY("0");
				schema.setFlag("");
				schema.setJFeeFlag("0");
				schema.setLocationFlag("1");
				schema.setOthFlag("0");
				schema.setProcessFlag("0");
				new DateTime();
				DateTime dateTime = new DateTime(DateTime.current(), 16);
				schema.setInputDate(dateTime.toString());
				this.setArr(schema);
			}

		}
	}

	public void transCovernoteEndor(DbPool dbpool, String iEndorseNo)
			throws UserException, Exception {
		boolean isChinese = true;
		boolean isCombin = false;
		ChgDate idate = new ChgDate();
		String sysDate = idate.getCurrentTime("yyyy-MM-dd");
		BLPrpPcost blPrpPcost = new BLPrpPcost();
		DBPrpPmainCovernote dbPrpPmainCovernote = new DBPrpPmainCovernote();
		DBPrpPheadCovernote dbPrpPheadCovernote = new DBPrpPheadCovernote();
		String strWherePart = "EndorseNo=\'" + iEndorseNo
				+ "\' AND ChgPremium!=0";
		blPrpPcost.query(dbpool, strWherePart);
		if (blPrpPcost.getSize() == 0) {
			throw new UserException(-98, -1167,
					"PrpTransSff.transCovernoteEndor", "无此批单信息：" + iEndorseNo);
		} else if (blPrpPcost.getSize() > 1) {
			throw new UserException(-98, -1167,
					"PrpTransSff.transCovernoteEndor", "批单" + iEndorseNo
							+ "存在多个支付币种");
		} else {
			dbPrpPmainCovernote.getInfo(dbpool, iEndorseNo);
			dbPrpPheadCovernote.getInfo(dbpool, iEndorseNo);
			PrpCJplanSchema schema = null;
			BLPrpDagent blPrpDagent = null;
			BLPrpDuser blPrpDuser = null;
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
			dbPrpDrisk.getInfo(dbpool, dbPrpPmainCovernote.getRiskCode());
			if (dbPrpDrisk.getFlag().length() >= 2
					&& dbPrpDrisk.getFlag().substring(1, 2).equals("2")) {
				isCombin = true;
			}

			for (int i = 0; i < blPrpPcost.getSize(); ++i) {
				schema = new PrpCJplanSchema();
				schema.setCertiType("E");
				schema.setCertiNo(iEndorseNo);
				schema.setSerialNo("" + (i + 1));
				schema.setPolicyNo(dbPrpPmainCovernote.getPolicyNo());
				if (dbPrpPmainCovernote.getPolicySort().equals("2")) {
					schema.setPayRefReason("R03");
				} else {
					if (!dbPrpPmainCovernote.getPolicySort().equals("5")
							&& !"C".equals(dbPrpPmainCovernote.getPolicySort())) {
						throw new UserException(-98, -1167,
								"PrpTransSff.transCovernoteEndor", "保单类型"
										+ dbPrpPmainCovernote.getPolicySort()
										+ "未定义");
					}

					schema.setPayRefReason("R01");
				}

				schema.setClassCode(dbPrpPmainCovernote.getClassCode());
				schema.setRiskCode(dbPrpPmainCovernote.getRiskCode());
				schema.setContractNo(dbPrpPmainCovernote.getContractNo());
				schema.setAppliCode(dbPrpPmainCovernote.getAppliCode());
				schema.setAppliName(dbPrpPmainCovernote.getAppliName());
				schema.setInsuredCode(dbPrpPmainCovernote.getInsuredCode());
				schema.setInsuredName(dbPrpPmainCovernote.getInsuredName());
				schema.setStartDate(dbPrpPmainCovernote.getStartDate());
				schema.setEndDate(dbPrpPmainCovernote.getEndDate());
				schema.setValidDate(dbPrpPheadCovernote.getValidDate());
				schema.setTCol1(dbPrpPmainCovernote.getInputDate());
				schema.setPayNo("1");
				schema.setTotalPayNo("" + blPrpPcost.getSize());
				schema.setPlanFeeCurrency(blPrpPcost.getArr(i).getCurrency2());
				schema.setPlanFee(blPrpPcost.getArr(i).getChgPremium2());
				schema.setPlanFeeCNY(blPrpPcost.getArr(i).getChgPremium1());
				schema.setExchangeRate(String.valueOf(Str.round(
						Double.parseDouble(Str.chgStrZero(schema
								.getPlanFeeCNY()))
								/ Double.parseDouble(Str.chgStrZero(schema
										.getPlanFee())), 6)));
				schema.setPlanDate(dbPrpPmainCovernote.getStartDate());
				schema.setComCode(dbPrpPmainCovernote.getComCode());
				schema.setMakeCom(dbPrpPmainCovernote.getMakeCom());
				schema.setChannelType(dbPrpPmainCovernote.getChannelType());
				schema.setBusinessNature(dbPrpPmainCovernote
						.getBusinessNature());
				schema.setAgentCode(dbPrpPmainCovernote.getAgentCode());
				blPrpDagent = new BLPrpDagent();
				schema.setAgentName(blPrpDagent.translateCode(
						dbPrpPmainCovernote.getAgentCode(), isChinese));
				schema.setHandler1Code(dbPrpPmainCovernote.getHandler1Code());
				blPrpDuser = new BLPrpDuser();
				schema.setHandler1Name(blPrpDuser.translateCode(
						dbPrpPmainCovernote.getHandler1Code(), isChinese));
				schema.setHandlerCode(dbPrpPmainCovernote.getHandlerCode());
				schema.setUnderWriteDate(sysDate);
				schema.setUnderWriteFlag(dbPrpPmainCovernote
						.getUnderWriteFlag());
				schema.setCoinsFlag(dbPrpPmainCovernote.getCoinsFlag());
				strWherePart = "ComCode=\'" + dbPrpPmainCovernote.getComCode()
						+ "\'";
				blPrpDcompany.query(dbpool, strWherePart);
				String strCenterCode = blPrpDcompany.getArr(0).getAcntUnit();
				String strBranchCode = strCenterCode;
				if (strCenterCode == null || strCenterCode.equals("")) {
					strBranchCode = strCenterCode;
				}

				schema.setCenterCode(strCenterCode);
				schema.setBranchCode(strBranchCode);
				if (isCombin) {
					if (schema.getClassCode().equals("05")) {
						schema.setIsCombin("2");
					} else {
						schema.setIsCombin("1");
					}
				} else {
					schema.setIsCombin("0");
				}

				schema.setRealPayRefFee("0");
				schema.setRealPayRefFeeCNY("0");
				schema.setFlag("");
				schema.setJFeeFlag("0");
				schema.setLocationFlag("1");
				schema.setOthFlag("0");
				schema.setProcessFlag("0");
				new DateTime();
				DateTime dateTime = new DateTime(DateTime.current(), 16);
				schema.setInputDate(dateTime.toString());
				this.setArr(schema);
			}

		}
	}

	public void transData0402(DbPool dbpool, String iCertiType, String iCertiNo)
			throws UserException, Exception {
		if (iCertiType.equals("P")) {
			this.transPolicy0402(dbpool, iCertiNo);
			this.save(dbpool);
		} else {
			throw new UserException(-98, -1167, "BLPrpJplanFee.transData",
					"没有此业务类型：" + iCertiType);
		}
	}

	public void transPolicy0402(DbPool dbpool, String iPolicyNo)
			throws UserException, Exception {
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
		BLPrpCplan blPrpCplan = new BLPrpCplan();
		BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
		String strWherePart = "";
		String[] dateStart = new String[2];
		String[] dateEnd = new String[2];
		double[] planfee = new double[]{0.0D, 0.0D};
		double[] planfeeCNY = new double[]{0.0D, 0.0D};
		boolean intReturn = false;
		double dbSumPremium = 0.0D;
		double dbSumPremium2 = 0.0D;
		boolean blFlag = false;
		boolean blnSffFlag = false;
		boolean isChinese = true;
		String strCenterCode = "";
		String strBranchCode = "";
		boolean isCombin = false;
		String strJFeeFlag = "";
		boolean intPlanCount = true;
		int arg31 = dbPrpCmain.getInfo(dbpool, iPolicyNo);
		if (arg31 == 100) {
			throw new UserException(-98, -1167, "BLPrpJplanFee.transPolicy",
					"无此保单信息：" + iPolicyNo);
		} else {
			boolean blTaxFlag = false;
			if (dbPrpCmain.getClassCode().equals("05")) {
				this.transCarShipTax(dbpool, "P", iPolicyNo);
			}

			strWherePart = "PolicyNo =\'" + iPolicyNo + "\'";
			blPrpCplan.query(dbpool, strWherePart, 0);
			blPrpCitemKind.query(dbpool, strWherePart);
			if (blPrpCplan.getSize() == 0) {
				throw new UserException(-98, -1167,
						"BLPrpJplanFee.transPolicy", "无此保单信息：" + iPolicyNo);
			} else {
				for (int schema = 0; schema < blPrpCitemKind.getSize(); ++schema) {
					if (blPrpCitemKind.getArr(schema).getKindCode()
							.equals("0400700")) {
						dateStart[0] = blPrpCitemKind.getArr(schema)
								.getStartDate();
						dateEnd[0] = blPrpCitemKind.getArr(schema).getEndDate();
						planfee[0] = Double.parseDouble(blPrpCitemKind.getArr(
								schema).getPremium2());
						planfeeCNY[0] = Double.parseDouble(blPrpCitemKind
								.getArr(schema).getPremium());
					} else if (blPrpCitemKind.getArr(schema).getKindCode()
							.equals("0400600")) {
						dateStart[1] = blPrpCitemKind.getArr(schema)
								.getStartDate();
						dateEnd[1] = blPrpCitemKind.getArr(schema).getEndDate();
						planfee[1] = Double.parseDouble(blPrpCitemKind.getArr(
								schema).getPremium2());
						planfeeCNY[1] = Double.parseDouble(blPrpCitemKind
								.getArr(schema).getPremium());
					} else {
						planfee[1] += Double.parseDouble(blPrpCitemKind.getArr(
								schema).getPremium2());
						planfeeCNY[1] += Double.parseDouble(blPrpCitemKind
								.getArr(schema).getPremium());
					}
				}

				int arg32 = blPrpCplan.getSize();
				PrpCJplanSchema arg33 = null;

				for (int blPrpDcompany = 0; blPrpDcompany < blPrpCplan
						.getSize(); ++blPrpDcompany) {
					dbSumPremium += Str.round(Double.parseDouble(blPrpCplan
							.getArr(blPrpDcompany).getPlanFee()), 2);
					dbSumPremium2 += Str.round(Double.parseDouble(blPrpCplan
							.getArr(blPrpDcompany).getPlanFee2()), 2);
				}

				BLPrpDcompany arg34 = null;
				BLPrpDagent blPrpDagent = null;
				BLPrpDuser blPrpDuser = null;

				int blPrpCcoins;
				for (blPrpCcoins = 0; blPrpCcoins < 2; ++blPrpCcoins) {
					arg33 = new PrpCJplanSchema();
					arg33.setCertiType("P");
					arg33.setCertiNo(iPolicyNo);
					arg33.setSerialNo(Integer.toString(blPrpCcoins + 1));
					arg33.setPolicyNo(iPolicyNo);
					arg33.setPayRefReason(blPrpCplan.getArr(0).getPayReason());
					arg33.setContractNo(dbPrpCmain.getContractNo());
					if (blnSffFlag) {
						arg33.setPayRefReason("R00");
					}

					if (dbPrpCmain.getOthFlag().length() > 10) {
						if (dbPrpCmain.getOthFlag().substring(10, 11)
								.equals("1")) {
							arg33.setPayRefReason("R04");
							if (arg33.getContractNo().equals("")) {
								throw new UserException(-98, -1167,
										"BLPrpJplanFee.transPolicy",
										"暂保单号不允许为空：" + iPolicyNo);
							}
						} else if (dbPrpCmain.getOthFlag().substring(10, 11)
								.equals("2")) {
							arg33.setPayRefReason("R00");
						}
					}

					arg33.getPayRefReason().equals("R04");
					arg33.setClassCode(dbPrpCmain.getClassCode());
					arg33.setRiskCode(dbPrpCmain.getRiskCode());
					arg33.setAppliCode(dbPrpCmain.getAppliCode());
					arg33.setAppliName(dbPrpCmain.getAppliName());
					arg33.setInsuredCode(dbPrpCmain.getInsuredCode());
					arg33.setInsuredName(dbPrpCmain.getInsuredName());
					arg33.setStartDate(dateStart[blPrpCcoins]);
					arg33.setEndDate(dateEnd[blPrpCcoins]);
					arg33.setValidDate(dbPrpCmain.getOperateDate());
					arg33.setTCol1(dbPrpCmain.getInputDate());
					arg33.setPayNo(blPrpCplan.getArr(0).getPayNo());
					arg33.setTotalPayNo("" + arg32);
					arg33.setPlanFeeCurrency(blPrpCplan.getArr(0)
							.getCurrency2());
					arg33.setPlanFee(Double.toString(planfee[blPrpCcoins]));
					arg33.setPlanFeeCNY(Double
							.toString(planfeeCNY[blPrpCcoins]));
					arg33.setExchangeRate(blPrpCplan.getArr(0)
							.getExchangeRateCNY());
					arg33.setPlanDate(blPrpCplan.getArr(0).getPlanDate());
					arg33.setComCode(dbPrpCmain.getComCode());
					arg33.setMakeCom(dbPrpCmain.getMakeCom());
					arg33.setBusinessNature(dbPrpCmain.getBusinessNature());
					arg33.setChannelType(dbPrpCmain.getChannelType());
					arg33.setAgentCode(dbPrpCmain.getAgentCode());
					blPrpDagent = new BLPrpDagent();
					arg33.setAgentName(blPrpDagent.translateCode(
							dbPrpCmain.getAgentCode(), isChinese));
					arg33.setHandler1Code(dbPrpCmain.getHandler1Code());
					blPrpDuser = new BLPrpDuser();
					arg33.setHandler1Name(blPrpDuser.translateCode(
							dbPrpCmain.getHandler1Code(), isChinese));
					arg33.setHandlerCode(dbPrpCmain.getHandlerCode());
					arg33.setUnderWriteDate(DateTime.current().toString()
							.substring(0, 10));
					arg33.setUnderWriteFlag(dbPrpCmain.getUnderWriteFlag());
					arg33.setCoinsFlag(dbPrpCmain.getCoinsFlag());
					arg33.setOthFlag("0");
					arg33.setLocationFlag(this.getLocationFlag(dbpool,
							dbPrpCmain.getNationFlag()));
					arg34 = new BLPrpDcompany();
					strWherePart = "ComCode=\'" + dbPrpCmain.getComCode()
							+ "\'";
					arg34.query(dbpool, strWherePart);
					strCenterCode = arg34.getArr(0).getAcntUnit();
					strBranchCode = strCenterCode;
					if (strCenterCode == null || strCenterCode.equals("")) {
						strBranchCode = strCenterCode;
					}

					arg33.setCenterCode(strCenterCode);
					arg33.setBranchCode(strBranchCode);
					arg33.setCarModel("");
					if (isCombin) {
						if (arg33.getClassCode().equals("05")) {
							arg33.setIsCombin("2");
						} else {
							arg33.setIsCombin("1");
						}
					} else {
						arg33.setIsCombin("0");
					}

					arg33.setAgriType(dbPrpCmain.getAgriType());
					strJFeeFlag = dbPrpCmain.getJFeeFlag();
					if (strJFeeFlag == null || strJFeeFlag.equals("")) {
						strJFeeFlag = "0";
					}

					arg33.setJFeeFlag(strJFeeFlag);
					arg33.setBankSerialNo("");
					arg33.setBankPosNo("");
					arg33.setRealPayRefFee("0");
					arg33.setRealPayRefFeeCNY("0");
					new DateTime();
					DateTime blPrpCcoinsDetail = new DateTime(
							DateTime.current(), 16);
					arg33.setInputDate(blPrpCcoinsDetail.toString());
					arg33.setFlag("");
					arg33.setProcessFlag("0");
					if (!dbPrpCmain.getCoinsFlag().equals("1")
							&& !dbPrpCmain.getCoinsFlag().equals("3")) {
						if (!dbPrpCmain.getCoinsFlag().equals("2")
								&& !dbPrpCmain.getCoinsFlag().equals("4")) {
							this.setArr(arg33);
						} else {
							strWherePart = "PolicyNo=\'" + iPolicyNo
									+ "\' AND ChiefFlag=\'1\' ";
							BLPrpCcoins blPrpCcoins1 = new BLPrpCcoins();
							blPrpCcoins1.query(dbpool, strWherePart);
							if (blPrpCcoins1.getSize() > 0) {
								arg33.setCoinsCode(blPrpCcoins1.getArr(0)
										.getCoinsCode());
								arg33.setCoinsName(blPrpCcoins1.getArr(0)
										.getCoinsName());
								arg33.setCoinsType("1");
							}

							this.setArr(arg33);
						}
					} else {
						if (blPrpCcoins == blPrpCplan.getSize() - 1) {
							blFlag = true;
						} else {
							blFlag = false;
						}

						strWherePart = " PolicyNo=\'" + iPolicyNo + "\'";
						this.transCoinsDetail(dbpool, strWherePart, arg33,
								dbSumPremium, "POLICY", blFlag);
					}
				}

				this.splitToKind(dbpool, "P", dbPrpCmain.getRiskCode());
				if (dbPrpCmain.getCoinsFlag().equals("1")
						|| dbPrpCmain.getCoinsFlag().equals("3")) {
					this.splitToKind(dbpool, "S", dbPrpCmain.getRiskCode());
				}

				if (!dbPrpCmain.getCoinsFlag().equals("1")
						&& !dbPrpCmain.getCoinsFlag().equals("3")) {
					blPrpCcoins = this.getSize();
					strWherePart = " CertiNo=\'" + iPolicyNo + "\'";
					this.transCommission(dbpool, strWherePart, blPrpCcoins);
				}

				if (dbPrpCmain.getCoinsFlag().equals("2")
						|| dbPrpCmain.getCoinsFlag().equals("4")) {
					BLPrpCcoins arg36 = new BLPrpCcoins();
					BLPrpCcoinsDetail arg35 = new BLPrpCcoinsDetail();
					strWherePart = " PolicyNo=\'" + iPolicyNo
							+ "\' And CoinsType=\'1\' ";
					arg36.query(dbpool, strWherePart);
					if (arg36.getSize() > 0) {
						strWherePart = " PolicyNo=\'" + iPolicyNo
								+ "\' And SerialNo="
								+ arg36.getArr(0).getSerialNo()
								+ " And OperateFee!=0";
						arg35.query(dbpool, strWherePart);
						if (arg35.getSize() > 0) {
							arg33 = new PrpCJplanSchema();
							arg33.setSchema(this.getArr(0));
							arg33.setCertiType("F");
							arg33.setSerialNo("" + this.getSize());
							arg33.setPayRefReason("P96");
							arg33.setPlanFee(String.valueOf(Str.round(
									Double.parseDouble(Str.chgStrZero(arg35
											.getArr(0).getOperateFee()))
											/ Double.parseDouble(arg33
													.getExchangeRate()), 2)));
							arg33.setPlanFeeCNY(arg35.getArr(0).getOperateFee());
							arg33.setUnderWriteDate(DateTime.current()
									.toString().substring(0, 10));
							this.setArr(arg33);
						}
					}
				}

			}
		}
	}

	public void transSettleData(String iPolicyNo) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConfig.getProperty("PAYMENTDATASOURCE"));

		try {
			dbpool.beginTransaction();
			this.transSettleData(dbpool, iPolicyNo);
			dbpool.commitTransaction();
		} catch (UserException arg7) {
			dbpool.rollbackTransaction();
			throw arg7;
		} catch (Exception arg8) {
			dbpool.rollbackTransaction();
			throw arg8;
		} finally {
			dbpool.close();
		}

	}

	public void transSettleData(DbPool dbpool, String iPolicyNo)
			throws UserException, Exception {
		String strContractNo = "";
		String strJudicalCode = "";
		String strWherePart = "";
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
		DBPrpCmainSub dbPrpCmainSub = new DBPrpCmainSub();
		BLPrpCplan blPrpCplan = new BLPrpCplan();
		BLPrpDcompany blPrpDcompany = null;
		BLPrpDagent blPrpDagent = null;
		BLPrpDuser blPrpDuser = null;
		boolean intReturn = false;
		boolean intPlanCount = true;
		Vector PrpCmainSubVec = null;
		String strCenterCode = "";
		String strBranchCode = "";
		boolean isChinese = true;
		double dbSumPremium = 0.0D;
		double dbSumPremium2 = 0.0D;
		boolean blFlag = false;
		int arg27 = dbPrpCmain.getInfo(dbpool, iPolicyNo);
		if (arg27 == 100) {
			throw new UserException(-98, -1167, "PrpTransSff.transSettleData",
					"无此保单信息：" + iPolicyNo);
		} else {
			blPrpCplan.query(dbpool, " PolicyNo=\'" + iPolicyNo + "\'");
			if (blPrpCplan.getSize() == 0) {
				throw new UserException(-98, -1167,
						"PrpTransSff.transSettleData", "无此保单的结算信息：" + iPolicyNo);
			} else {
				String mainPolicyNo = "";
				PrpCmainSubVec = dbPrpCmainSub
						.findByPolicyNo(dbpool, iPolicyNo);
				if (PrpCmainSubVec != null && PrpCmainSubVec.size() != 0) {
					strContractNo = dbPrpCmain.getContractNo();
					if (!"".equals(strContractNo) && strContractNo != null) {
						BLPrpCmainCovernote prpCmainSubSchema = new BLPrpCmainCovernote();
						prpCmainSubSchema.getData(strContractNo);
						if (prpCmainSubSchema.getSize() > 0) {
							strJudicalCode = prpCmainSubSchema.getArr(0)
									.getJudicalCode();
						}
					}

					if ("1".equals(strJudicalCode)) {
						for (int arg29 = 0; arg29 < blPrpCplan.getSize(); ++arg29) {
							dbSumPremium += Str.round(Double
									.parseDouble(blPrpCplan.getArr(arg29)
											.getPlanFee()), 2);
							dbSumPremium2 += Str.round(Double
									.parseDouble(blPrpCplan.getArr(arg29)
											.getPlanFee2()), 2);
						}

						PrpCmainSubSchema arg28 = (PrpCmainSubSchema) PrpCmainSubVec
								.get(0);
						mainPolicyNo = arg28.getMainPolicyNo();
						PrpCJplanSchema schema = null;

						for (int i = 0; i < blPrpCplan.getSize(); ++i) {
							schema = new PrpCJplanSchema();
							schema.setCertiType("P");
							schema.setCertiNo(iPolicyNo);
							schema.setSerialNo(String.valueOf(i + 1));
							schema.setPolicyNo(iPolicyNo);
							schema.setPayRefReason("R02");
							schema.setClassCode(dbPrpCmain.getClassCode());
							schema.setRiskCode(dbPrpCmain.getRiskCode());
							schema.setContractNo(mainPolicyNo);
							schema.setAppliCode(dbPrpCmain.getAppliCode());
							schema.setAppliName(dbPrpCmain.getAppliName());
							schema.setInsuredCode(dbPrpCmain.getInsuredCode());
							schema.setInsuredName(dbPrpCmain.getInsuredName());
							schema.setStartDate(dbPrpCmain.getStartDate());
							schema.setEndDate(dbPrpCmain.getEndDate());
							schema.setValidDate(dbPrpCmain.getOperateDate());
							schema.setTCol1(dbPrpCmain.getInputDate());
							schema.setPayNo(blPrpCplan.getArr(i).getPayNo());
							schema.setTotalPayNo("" + blPrpCplan.getSize());
							schema.setPlanFeeCurrency(blPrpCplan.getArr(i)
									.getCurrency2());
							schema.setPlanFee(blPrpCplan.getArr(i)
									.getPlanFee2());
							schema.setPlanFeeCNY(blPrpCplan.getArr(i)
									.getPlanFee());
							schema.setExchangeRate(blPrpCplan.getArr(i)
									.getExchangeRateCNY());
							schema.setPlanDate(blPrpCplan.getArr(i)
									.getPlanDate());
							schema.setComCode(dbPrpCmain.getComCode());
							schema.setMakeCom(dbPrpCmain.getMakeCom());
							schema.setBusinessNature(dbPrpCmain
									.getBusinessNature());
							schema.setChannelType(dbPrpCmain.getChannelType());
							schema.setAgentCode(dbPrpCmain.getAgentCode());
							blPrpDagent = new BLPrpDagent();
							schema.setAgentName(blPrpDagent.translateCode(
									dbPrpCmain.getAgentCode(), isChinese));
							schema.setHandler1Code(dbPrpCmain.getHandler1Code());
							blPrpDuser = new BLPrpDuser();
							schema.setHandler1Name(blPrpDuser.translateCode(
									dbPrpCmain.getHandler1Code(), isChinese));
							schema.setHandlerCode(dbPrpCmain.getHandlerCode());
							schema.setUnderWriteDate(DateTime.current()
									.toString().substring(0, 10));
							schema.setUnderWriteFlag(dbPrpCmain
									.getUnderWriteFlag());
							schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
							schema.setLocationFlag(this.getLocationFlag(dbpool,
									dbPrpCmain.getNationFlag()));
							schema.setOthFlag("0");
							blPrpDcompany = new BLPrpDcompany();
							strWherePart = "ComCode=\'"
									+ dbPrpCmain.getComCode() + "\'";
							blPrpDcompany.query(dbpool, strWherePart);
							strCenterCode = blPrpDcompany.getArr(0)
									.getAcntUnit();
							strBranchCode = strCenterCode;
							if (strCenterCode == null
									|| strCenterCode.equals("")) {
								strBranchCode = strCenterCode;
							}

							schema.setCenterCode(strCenterCode);
							schema.setBranchCode(strBranchCode);
							schema.setCarModel("");
							schema.setIsCombin("0");
							schema.setAgriType(dbPrpCmain.getAgriType());
							schema.setJFeeFlag("0");
							schema.setRealPayRefFee("0");
							schema.setRealPayRefFeeCNY("0");
							new DateTime();
							DateTime dateTime = new DateTime(
									DateTime.current(), 16);
							schema.setInputDate(dateTime.toString());
							schema.setFlag("");
							schema.setProcessFlag("0");
							schema.setBusinessNature(dbPrpCmain
									.getBusinessNature());
							if (!dbPrpCmain.getCoinsFlag().equals("1")
									&& !dbPrpCmain.getCoinsFlag().equals("3")) {
								this.setArr(schema);
							} else {
								if (i == blPrpCplan.getSize() - 1) {
									blFlag = true;
								} else {
									blFlag = false;
								}

								strWherePart = " PolicyNo=\'" + iPolicyNo
										+ "\'";
								this.transCoinsSettleData(dbpool, strWherePart,
										schema, dbSumPremium, blFlag);
							}
						}

						this.save(dbpool);
					}

				} else {
					throw new UserException(-98, -1167,
							"PrpTransSff.transSettleData", "无此保单的大保单信息："
									+ iPolicyNo);
				}
			}
		}
	}

	public void transCoinsSettleData(DbPool dbpool, String iWherePart,
			PrpCJplanSchema iSchema, double dbSumPremium, boolean iFlag)
			throws Exception {
		BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
		BLPrpCcoinsDetail blPrpCcoinsDetail = new BLPrpCcoinsDetail();
		blPrpCcoins.query(dbpool, iWherePart);
		blPrpCcoinsDetail.query(dbpool, iWherePart);
		PrpCJplanSchema schema = null;
		String strSerialNo = "";
		double dblPlanFee = 0.0D;
		int intSerialNo = 1 + (Integer.parseInt(iSchema.getPayNo()) - 1)
				* blPrpCcoins.getSize()
				+ (Integer.parseInt(iSchema.getPayNo()) - 1)
				* (blPrpCcoins.getSize() - 1);
		PrpCcoinsDetailSchema prpCcoinsDetailSchema = new PrpCcoinsDetailSchema();

		for (int i = 0; i < blPrpCcoins.getSize(); ++i) {
			for (int dblPlanFeeSum = 0; dblPlanFeeSum < blPrpCcoinsDetail
					.getSize(); ++dblPlanFeeSum) {
				if (blPrpCcoins
						.getArr(i)
						.getSerialNo()
						.equals(blPrpCcoinsDetail.getArr(dblPlanFeeSum)
								.getSerialNo())) {
					prpCcoinsDetailSchema = blPrpCcoinsDetail
							.getArr(dblPlanFeeSum);
					break;
				}
			}

			if (blPrpCcoins.getArr(i).getCoinsType().equals("1")) {
				schema = new PrpCJplanSchema();
				schema.setSchema(iSchema);
				strSerialNo = "" + intSerialNo;
				++intSerialNo;
				schema.setSerialNo(strSerialNo);
				schema.setCertiType("P");
				schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
				schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
				schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
				dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
						.getCoinsPremium());
				if (iFlag) {
					double arg18 = 0.0D;

					for (int k = 0; k < this.getSize(); ++k) {
						if (this.getArr(k).getPayRefReason()
								.equals(schema.getPayRefReason())
								&& this.getArr(k).getCoinsCode()
										.equals(schema.getCoinsCode())
								&& this.getArr(k).getCoinsType()
										.equals(schema.getCoinsType())
								&& !this.getArr(k).getPayNo()
										.equals(schema.getPayNo())) {
							arg18 += Double.parseDouble(this.getArr(k)
									.getPlanFeeCNY());
						}
					}

					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium()) - arg18;
				} else {
					dblPlanFee = Double.parseDouble(prpCcoinsDetailSchema
							.getCoinsPremium())
							* Double.parseDouble(iSchema.getPlanFeeCNY())
							/ dbSumPremium;
				}

				dblPlanFee = Str.round(dblPlanFee, 2);
				if ("JPY".equals(prpCcoinsDetailSchema.getCurrency2())) {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									0));
				} else {
					schema.setPlanFee(""
							+ Str.round(
									dblPlanFee
											/ Double.parseDouble(Str
													.chgStrZero(schema
															.getExchangeRate())),
									2));
				}

				schema.setPlanFeeCNY("" + dblPlanFee);
				this.setArr(schema);
			}
		}

	}

	private void setPayeeInfo(String sCertitype, String sPayeeCode,
			PrpCJplanSchema oSchema, DbPool oDbPool) throws UserException,
			Exception {
		if ("F".equals(sCertitype)) {
			try {
				ChgDate e = new ChgDate();
				String sCurrentDate = e.getCurrentTime("yyyy-MM-dd");
				String sCntForAccount = " ReinsCode = \'" + sPayeeCode
						+ "\' And ValidStatus = \'1\'" + " And to_date(\'"
						+ sCurrentDate + "\',\'yyyy-MM-dd\')"
						+ " Between StartDate And EndDate";
				DBManager oDBManager = oDbPool.getDBManager("ddccDataSource");
				DBPrpDAccount oDBPrpDAccount = new DBPrpDAccount(oDBManager);
				Collection aAccount = oDBPrpDAccount
						.findByConditions(sCntForAccount);
				Iterator oIterator = aAccount.iterator();
				if (oIterator.hasNext()) {
					PrpDAccountDto oPrpDAccountDto = (PrpDAccountDto) oIterator
							.next();
					oSchema.setCustomBankCode("");
					oSchema.setCustomBankName(oPrpDAccountDto.getBank());
					oSchema.setAccountCode(oPrpDAccountDto.getAccounts());
					oSchema.setOwnerName(oPrpDAccountDto.getAccountName());
				}
			} catch (Exception arg12) {
				arg12.printStackTrace();
				throw new UserException(6014, 6025,
						"PrpTransSffIntf.setPayeeInfo", "获取出单费支付对象信息异常");
			}
		}

	}

	public static void main(String[] args) throws Exception {
	}
}