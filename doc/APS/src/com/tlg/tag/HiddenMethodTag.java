/*
 * Created on 2004/10/19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.tlg.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class HiddenMethodTag extends ComponentTagSupport  {
	
    protected String programName;
    
    protected String methodName;

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new HiddenMethod(stack);
    }

    protected void populateParams() {   
    	((HiddenMethod) getComponent()).setProgramName(programName);
    	((HiddenMethod) getComponent()).setMethodName(methodName);
    }

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

    
    
}
