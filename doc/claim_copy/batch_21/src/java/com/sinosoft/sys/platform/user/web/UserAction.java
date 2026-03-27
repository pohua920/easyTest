package com.sinosoft.sys.platform.user.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.sys.platform.common.MD5CapitalPasswordEncoder;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.user.service.facade.UserService;

public class UserAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private SaaUser prpDuser;
	private String opreateType;
	private String userCode;
	private String userName;
	private String password;
	
	private String oldPwd;
	private String newPwd;
	private String message;
	private String addTag;

	public String query() {
		String userCode = (String) getSession().getAttribute("UserCode");
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 20;
		}
		QueryRule queryRule = QueryRule.getInstance();
		if (!CommonUtils.isEmpty(prpDuser.getUserCode())) {
			queryRule.addEqual("userCode", prpDuser.getUserCode());
		}
		if (!CommonUtils.isEmpty(prpDuser.getUserName())) {
			queryRule.addLike("userName", "%"+prpDuser.getUserName()+"%");
		}
		if (!CommonUtils.isEmpty(prpDuser.getComCode())) {
			queryRule.addEqual("comCode", prpDuser.getComCode());
		}
		if (!CommonUtils.isEmpty(prpDuser.getNewUserCode())) {
			queryRule.addEqual("newUserCode", prpDuser.getNewUserCode());
		}
		if (!CommonUtils.isEmpty(prpDuser.getValidStatus())) {
			queryRule.addEqual("validStatus", prpDuser.getValidStatus());
		}
		queryRule.addAscOrder("comCode");
		queryRule.addAscOrder("userCode");
		try {
			if(!"00000000".equals(userCode)){
				queryRule.addNotEqual("userCode", "00000000");
			}
			Page page = userService.findUser(userCode,queryRule, pageNo, pageSize);
			writeJSONData(page, new String[] { "userCode", "userName", "newUserCode","comCode","validStatus","comCName"});
		} catch (Exception e) {
			e.printStackTrace();
			writeJSONMsg(e.getMessage());
		}
		return "none";
	}

	public String update() {
		MD5CapitalPasswordEncoder md5EnCoder = new MD5CapitalPasswordEncoder();
		if (oldPwd.length()!=prpDuser.getPassword().length()) {
			prpDuser.setPassword(md5EnCoder.encodePassword(prpDuser.getPassword(), null));
		}
		userService.update(prpDuser);
		String newUrl= "/user/prepareQuery.do";
	    getRequest().setAttribute("newUrl", newUrl);
		getRequest().setAttribute("operate", "query");
		return "success";
	}

	public String prepareUpdate() {
		logger.debug((new StringBuilder("\u51C6\u5907\u66F4\u65B0")).append(userCode).append("\u5458\u5DE5\u4FE1\u606F").toString());
		opreateType = "edit";
		prpDuser = userService.getUser(userCode);
		return "success";
	}

	public String add() {
		logger.debug((new StringBuilder("\u589E\u52A0")).append(prpDuser.getUserCode()).append("\u5458\u5DE5\u4FE1\u606F").toString());
		prpDuser.setMakeCom(prpDuser.getComCode());
		prpDuser.setNewUserCode(prpDuser.getUserCode());
		MD5CapitalPasswordEncoder md5EnCoder = new MD5CapitalPasswordEncoder();
		prpDuser.setPassword(md5EnCoder.encodePassword(prpDuser.getPassword(), null));
		
		userService.save(prpDuser);
		String newUrl= "/user/prepareQuery.do";
	    getRequest().setAttribute("newUrl", newUrl);
	    getRequest().setAttribute("operate","addUser");
	    return "success";
		
	}

	public String prepareAdd() {
		logger.debug("\u51C6\u5907\u589E\u52A0\u65B0\u5458\u5DE5\u4FE1\u606F");
		opreateType = "add";
		return "success";
	}

	public String delete() {
		logger.debug((new StringBuilder("\u5220\u9664")).append(userCode).append("\u5458\u5DE5\u4FE1\u606F").toString());
		userService.delete(userCode);
		return "none";
	}

	public String prepareQuery() {
		logger.debug("\u5458\u5DE5\u83DC\u5355\u8DF3\u8F6C");
		return "success";
	}

	public String view() {
		logger.debug("\u67E5\u770B\u5458\u5DE5\u4FE1\u606F");
		opreateType = "view";
		prpDuser = userService.getUser(userCode);
		return "success";
	}
	public String prepareChUserPw() {
		return "success";
	}
	/**
	 * 修改用户密码
	 * @return
	 */
	public String ChUserPw()throws Exception{
		MD5CapitalPasswordEncoder md5EnCoder = new MD5CapitalPasswordEncoder();
		if(oldPwd==null ||"".equals(oldPwd.trim()) ){
			throw new BusinessException("原密碼不能為空",true);
		}
		if(newPwd==null ||"".equals(newPwd.trim()) ){
			throw new BusinessException("新密碼不能為空",true);
		}
		
		SaaUser tempUser = userService.getUser((String)getRequest().getSession().getAttribute("UserCode"));
		boolean flag = (md5EnCoder.encodePassword(oldPwd, null)).equals(tempUser.getPassword());
		if(flag){
			tempUser.setPassword(md5EnCoder.encodePassword(newPwd, null));
			userService.update(tempUser);
		}else{
			throw new BusinessException("原密碼有誤",true);
		}
		getRequest().getSession().removeAttribute("UserCode");
		return SUCCESS;
		
	}
	
	public void checkUserCode() throws Exception{
		SaaUser oldUser = userService.findUserByUserCode(userCode);
		@SuppressWarnings("unused")
		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (oldUser==null) {
				// 可以录入该时间段该部门的周计划
				printWriter.print("ok");
			} else {
				// 该时间段该部门已经有了周计划，不可再次录入
				printWriter.print("no");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public String help(){
		String forward = "helpPage";
		return forward;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SaaUser getPrpDuser() {
		return prpDuser;
	}

	public void setPrpDuser(SaaUser prpDuser) {
		this.prpDuser = prpDuser;
	}

	public String getOpreateType() {
		return opreateType;
	}

	public void setOpreateType(String opreateType) {
		this.opreateType = opreateType;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAddTag() {
		return addTag;
	}

	public void setAddTag(String addTag) {
		this.addTag = addTag;
	}
	
}
