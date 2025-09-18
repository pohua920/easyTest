package com.tlg.aps.action;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.liaAnnounceService.AnnounceService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS010Action extends BaseAction {
	
	private String batchType;
	private String email;
	private File upload = null;//上傳的檔案
	private String fileType = "txt";//檔案型態
	private String uploadFileName; //檔案名稱
	private AnnounceService announceService;

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	/** APS010E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			if("1".equals(this.batchType)){
				announceService.undwrtComm051WAToAnnounceService();
			}
			if("2".equals(this.batchType)){
				announceService.rcvComm051WBToAnnounceService();
			}
			if("3".equals(this.batchType)){
				announceService.undwrtAssocToAnnounceService();
			}
			if("4".equals(this.batchType)){
				announceService.rcvAssocToAnnounceService();
			}
			if("5".equals(this.batchType)){
				announceService.undwrtAnnounceService();
			}
			if("6".equals(this.batchType)){
				announceService.rcvAnnounceService();
			}
			if("7".equals(this.batchType)){
				announceService.ipb902iService();
			}
			if("8".equals(this.batchType)){
				announceService.coreIpb902iService();
			}
			this.setMessage("執行完成～");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/**點選確定轉入鍵，開始上傳檔案 */
	public String btnFileUpd() throws Exception{
		try {
			Result result = fileUpload();
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		
		return Action.SUCCESS;		
	}
	
	//上傳檔案
	public Result fileUpload() throws Exception {
		Result result = new Result();	
		if (upload == null) {
			throw new SystemException("請先選擇檔案！");
		}
		if (StringUtil.isSpace(email)) {
			throw new SystemException("請輸入查詢後預寄送的email！");
		}
//		if (upload.length() == 0) {
//			String path = upload.getPath();
//			File file = new File(path);
//			FileUtils.writeStringToFile(file, "");
//			file = null;
//		}
		
		uploadFileName = StringUtil.nullToSpace(getUploadFileName());
		String filtType = "";
		int pos = uploadFileName.lastIndexOf(".");
		if (pos != -1) {
			filtType = uploadFileName.substring(pos + 1);
		} else {
			filtType = "";
		}
		if (!"".equals(fileType) && !"".equals(filtType) && fileType.indexOf(filtType) == -1) {
				throw new SystemException("上傳文件只允許." + fileType + "副檔名！");
		}
		
		announceService.queryRcvAndUndwrtByFileService(upload, email);

		
		Message m = new Message();
		m.setMessageString("查詢完成!\\r\\n請收mail，若查無資料時，則不會寄送檔案～");
		result.setMessage(m);
		
		return result;
	}


	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public AnnounceService getAnnounceService() {
		return announceService;
	}

	public void setAnnounceService(AnnounceService announceService) {
		this.announceService = announceService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
