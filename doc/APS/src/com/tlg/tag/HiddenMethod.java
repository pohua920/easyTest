package com.tlg.tag;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.components.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.tlg.sys.service.AuthorityService;
import com.tlg.util.UserInfo;

@SuppressWarnings("unchecked")
public class HiddenMethod extends Component{

    public static final String ANSWER = "struts.if.answer";
    
    private String pageAuthorityName = ActionContext.getContext().getActionInvocation().getAction().getClass().getSimpleName() + "PageAuthority";
    
    Boolean answer = Boolean.FALSE;
    
    String programName;
    
    String methodName;
    
    public HiddenMethod(ValueStack stack) {
        super(stack);
    }

    
	public boolean start(Writer writer) {
	    ArrayList authorityList = new ArrayList();
		try{
	        ActionContext context = ActionContext.getContext();	        
//	        System.out.println(">>>>> pageInfo_Name = " + pageAuthorityName);
	        
//	        HttpServletRequest request = (HttpServletRequest)(context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST));
	        Map session = ActionContext.getContext().getSession();
	        if(session == null || session.get("userInfo") == null){
	        	return answer.booleanValue();
	        }
//	        //判斷是否已有權限資料，若沒有則查詢
//	        if(session.getAttribute("userPageAuthority") == null){
//	        	UserInfo ufo = (UserInfo)session.get("userInfo");
//	        	authorityList = getMethodName(this.programName,ufo.getRoleList(),ufo.getUserId());
//	        }else{
//	        	authorityList = (ArrayList)request.getAttribute("userPageAuthority");
//	        }
//	        request.setAttribute("userPageAuthority",authorityList);
//	        if(authorityList.contains(this.methodName)){
//	        	answer = Boolean.TRUE;
//	        }
	        //判斷是否已有權限資料，若沒有則查詢
	        if(session.get(pageAuthorityName) == null){
	        	UserInfo ufo = (UserInfo)session.get("userInfo");
	        	
	        	String namespace = context.getActionInvocation().getProxy().getNamespace();
				String systemId = "";
				if(!"".equals(namespace)){
//					systemId = namespace.substring(1,namespace.lastIndexOf("/")).toUpperCase();
					systemId = namespace.split("/")[1].toUpperCase();
				}
	        	
				WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext((ServletContext)context.get(org.apache.struts2.StrutsStatics.SERVLET_CONTEXT));
		        if (wac != null) { 
		        	AuthorityService authorityService = (AuthorityService) wac.getBean("authorityService"); 
		        	authorityList = (ArrayList)authorityService.jurisdictionFindByPage(systemId.toUpperCase(),programName.toUpperCase(), ufo.getUserId(), ufo.getRoleList()).getResObject();
		        }
	        	
//	        	authorityList = getMethodName(this.programName,ufo.getRoleList(),ufo.getUserId());
	        }else{
//	        	authorityList = (ArrayList)request.getAttribute("userPageAuthority");
	        	authorityList = (ArrayList)session.get(pageAuthorityName);
	        }
//	        request.setAttribute("userPageAuthority",authorityList);
	        session.put(pageAuthorityName,authorityList);
	        if(authorityList.contains(this.methodName)){
	        	answer = Boolean.TRUE;
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    stack.getContext().put(ANSWER, answer);
		}
        return answer.booleanValue();
    }

    public boolean end(Writer writer, String body) {
        stack.getContext().put(ANSWER, answer);
        return super.end(writer, body);
    }

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}