<%@ page contentType="text/html; charset=UTF-8"%><% 
	java.util.List codeValues = (java.util.List)request.getAttribute("codeValues"); 

    if(codeValues.size() == 0) {
        //not found
    }
    else if(codeValues.size()== 1) {
        out.print( codeValues.get(0) );
    }
    else {
        System.out.println("WARN:CodeChange return " + codeValues.size() + " value.Please contact admin.");
    } 
%>