package com.tlg.aps.bs.carAddrUploadAndImportService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.carAddrUploadAndImportService.CarAddrUploadAndImportService;
import com.tlg.prpins.entity.CarAddrImportlist;
import com.tlg.prpins.entity.Tii30nometp;
import com.tlg.prpins.entity.TiiTvmetp;
import com.tlg.prpins.service.CarAddrImportlistService;
import com.tlg.prpins.service.Tii30nometpService;
import com.tlg.prpins.service.TiiTvmetpService;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class CarAddrUploadAndImportServiceImpl implements CarAddrUploadAndImportService {
	private static final Logger logger = Logger.getLogger(CarAddrUploadAndImportServiceImpl.class);
	
	private CarAddrImportlistService carAddrImportlistService;
	private Tii30nometpService tii30nometpService;
	private TiiTvmetpService tiiTvmetpService;
	
	@Override
	public Result RenewalDataUploadAndImport(String userId, File uploadFile, String filename, String ultype) throws Exception {
		List<String> rawDataList = readFileReader(uploadFile);
		if(rawDataList.size() == 0) {
			return getReturnResult("檔案內無資料");
		}
		// 因採全刪全增方式執行，須先進行下列資料刪除動作
		CarAddrImportlist carAddrImportlist = new CarAddrImportlist();
		carAddrImportlist.setFilenameOri(filename);
		carAddrImportlistService.removeCarAddrImportlist(carAddrImportlist);
		carAddrImportlist.setFilenameOri(filename);
		carAddrImportlist.setQtyAll(rawDataList.size());
		carAddrImportlist.setUltype(ultype);
		carAddrImportlist.setDcreate(new Date());
		carAddrImportlist.setIcreate(userId);
		carAddrImportlistService.insertAddrImportlist(carAddrImportlist);

		if ("1".equals(ultype)) {
			TiiTvmetp tiiTvmetp = new TiiTvmetp();
			tiiTvmetp.setFilename(filename);
			tiiTvmetpService.removeTiiTvmetp(tiiTvmetp);
			for (String rawdata : rawDataList) {
				rawdata = rawdata.replace("\"", "");
				String[] ary = rawdata.split("\\|\\|");
				TiiTvmetp entity = new TiiTvmetp();
				entity.setCarno(ary[0]);
				entity.setBodyno(ary[1]);
				entity.setCarnodate(ary[2]);
				entity.setInsuredcardno(ary[3]);
				entity.setCitizenid(ary[4]);
				entity.setIsexistflag(ary[5]);
				entity.setInsbegindate(ary[6]);
				entity.setOperatedate(new Date());
				entity.setFilename(filename);
				tiiTvmetpService.insertTiiTvmetp(entity);
			}
		} else if ("2".equals(ultype)) {
			Tii30nometp tii30nometp = new Tii30nometp();
			tii30nometp.setFilename(filename);
			tii30nometpService.removeTii30nometp(tii30nometp);
			for (String rawdata : rawDataList) {
				rawdata = rawdata.replace("\"", "");
				String[] ary = rawdata.split("\\|\\|");
				Tii30nometp entity = new Tii30nometp();
				entity.setInsuredcardno(ary[0]);
				entity.setCitizenid(ary[1]);
				entity.setInsbegindate(ary[2]);
				entity.setInsenddate(ary[3]);
				entity.setOperatedate(new Date());
				entity.setFilename(filename);
				tii30nometpService.insertTii30nometp(entity);
			}
		}
		return getReturnResult("上傳完成");
	}
	
	private List<String> readFileReader(File file) throws IOException {
		List<String> rawDataList = new ArrayList<String>();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while((line = br.readLine()) != null){
            //process the line
        	if(line.trim().length() > 0) {
        		System.out.println(line);
                rawDataList.add(line);
        	}
        }
        br.close();
        isr.close();
        br = null;
        isr = null;
        return rawDataList;
    }
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public CarAddrImportlistService getCarAddrImportlistService() {
		return carAddrImportlistService;
	}

	public void setCarAddrImportlistService(CarAddrImportlistService carAddrImportlistService) {
		this.carAddrImportlistService = carAddrImportlistService;
	}

	public Tii30nometpService getTii30nometpService() {
		return tii30nometpService;
	}

	public void setTii30nometpService(Tii30nometpService tii30nometpService) {
		this.tii30nometpService = tii30nometpService;
	}

	public TiiTvmetpService getTiiTvmetpService() {
		return tiiTvmetpService;
	}

	public void setTiiTvmetpService(TiiTvmetpService tiiTvmetpService) {
		this.tiiTvmetpService = tiiTvmetpService;
	}

	public static Logger getLogger() {
		return logger;
	}
	
}
