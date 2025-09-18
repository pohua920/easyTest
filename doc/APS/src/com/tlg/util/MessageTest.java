package com.tlg.util;

import java.io.Serializable;

import com.tlg.exception.SystemException;

/**
 * @version 1.0
 * @created
 */
public class MessageTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4907098624810141480L;

	private String messageString = "";

	private String opCode        = "";

	private enum Code {
		PUB100, PUB101, PUB102, PUB105, PUB106, PUB107,
		PUB110, PUB111, PUB115, PUB116,
		PUB120, PUB121, PUB122, PUB125, PUB126,
		PUB130, PUB131, PUB132, PUB133,
		PUB200, PUB201, PUB210, PUB211,
		PUB300, PUB301,
		PUB400, PUB401,
		PUB601, PUB602, PUB603, PUB604
	}
  
	public MessageTest() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 取得訊息
	 * 
	 * @param opCode
	 */
	public static MessageTest getMessage(String opCode) throws SystemException {
		MessageTest message = null;
		try {
			message = MessageTest.getFixMessage(opCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
  
	private static MessageTest getFixMessage(String opCode) throws Exception {

		String msg = "";
		Code code = Code.valueOf(opCode);

		switch (code) {
		case PUB100:
			msg = "新增成功";
			break;
		case PUB101:
			msg = "新增失敗";
			break;
		case PUB102:
			msg = "新增失敗，相同代碼編號已存在";
			break;
		case PUB105:
			msg = "明細新增成功";
			break;
		case PUB106:
			msg = "明細新增失敗";
			break;
		case PUB107:
			msg = "明細新增失敗，相同代碼編號已存在";
			break;
		case PUB110:
			msg = "資料刪除成功";
			break;
		case PUB111:
			msg = "刪除失敗";
			break;
		case PUB115:
			msg = "明細資料刪除成功";
			break;
		case PUB116:
			msg = "明細刪除失敗";
			break;
		case PUB120:
			msg = "儲存成功";
			break;
		case PUB121:
			msg = "儲存失敗";
			break;
		case PUB122:
			msg = "上傳檔案過大，超過{0}MB";
			break;
		case PUB125:
			msg = "明細儲存成功";
			break;
		case PUB126:
			msg = "明細儲存失敗";
			break;
		case PUB130:
			msg = "查無符合資料";
			break;
		case PUB131:
			msg = "資料不存在";
			break;
		case PUB132:
			msg = "該資料仍有被其他資料參照，不可刪";
			break;
		case PUB133:
			msg = "查無明細資料";
			break;
		case PUB200:
			msg = "送出成功";
			break;
		case PUB201:
			msg = "送出失敗";
			break;
		case PUB210:
			msg = "審核成功";
			break;
		case PUB211:
			msg = "審核失敗";
			break;
		case PUB300:
			msg = "批次成功";
			break;
		case PUB301:
			msg = "批次失敗";
			break;
		case PUB400:
			msg = "上傳成功";
			break;
		case PUB401:
			msg = "上傳失敗";
			break;
		case PUB601:
			msg = "查無關卡";
			break;
		case PUB602:
			msg = "新增關卡失敗";
			break;
		case PUB603:
			msg = "改變關卡狀態失敗";
			break;
		case PUB604:
			msg = "改變關卡狀態成功";
			break;
		}
		MessageTest msgObj = null;
		if (!StringUtil.isSpace(msg)) {
			msgObj = new MessageTest();
			msgObj.setOpCode(opCode);
			msgObj.setMessageString(msg);
			System.out.println(">>>>>> opCode = " + opCode + ",Message = " + msg);
		}
		return msgObj;
	}

	public String toString() {
    // TODO Auto-generated method stub
		return messageString;
	}

	/**
	 * 	@return Returns the messageString.
	 */
	public String getMessageString() {
		return messageString;
	}

	/**
	 * @param messageString
	 *          The messageString to set.
	 */
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	/**
	 * @return Returns the opCode.
	 */
	public String getOpCode() {
		return opCode;
	}

	/**
	 * @param opCode
	 *          The opCode to set.
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

}