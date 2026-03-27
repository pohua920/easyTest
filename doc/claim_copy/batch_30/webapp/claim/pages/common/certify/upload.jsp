<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.jspsmart.upload.*" %>
<%
	try {
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String str1 = e.nextElement().toString();
			//System.out.println(str1+"%%%%%%%%%%%%%:" +request.getParameter(str1));    
		}
		//实例化上载bean
		com.jspsmart.upload.SmartUpload mySmartUpload = new com.jspsmart.upload.SmartUpload();
		//初始化
		mySmartUpload.initialize(pageContext);
		//设置上载的最大值
		mySmartUpload.setMaxFileSize(1000 * 1024 * 1024); //每个文件最大为一兆
		// 2.限制总上传数据的长度。
		mySmartUpload.setTotalMaxFileSize(2000 * 700 * 1024); //全部文件上载限制为20兆
		//上载文件
		mySmartUpload.upload();
		/*
		//循环取得所有上载的文件
		for (int i=0;i<mySmartUpload.getFiles().getCount();i++){
		//取得上载的文件
		com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(i);
		if (!myFile.isMissing()){
		//取得上载的文件的文件名
		String myFileName=myFile.getFileName();
		//取得不带後缀的文件名
		String  suffix=myFileName.substring(0,myFileName.lastIndexOf('.'));
		//取得後缀名
		String  ext= mySmartUpload.getFiles().getFile(0).getFileExt();  
		//取得文件的大小   
		int fileSize=myFile.getSize();
		//保存路径 
		String aa=request.getSession().getServletContext().getRealPath("/")+"\\";
		String trace=aa+myFileName;
		//System.out.println(";;;;;;;;;;:::" + trace);   
		//取得别的参数
		String test1 =  (String)mySmartUpload.getRequest().getParameter("test1"); 
		String test2 =  (String)mySmartUpload.getRequest().getParameter("test2");
		String[] test3 = mySmartUpload.getRequest().getParameterValues("test3");
		
		for(int i1=0;i1<test3.length;i1++){
		  //System.out.println(":::::test3::::" +i1 +":+"+test3[i1]);
		}
		//System.out.println("^^^^^^^^^^^" + request.getParameter("test3"));
		//System.out.println(":::::test1::::" + test1);
		//System.out.println(":::::test2::::" + test2);
		//将文件保存在服务器端 
		myFile.saveAs(trace,mySmartUpload.SAVE_PHYSICAL);
		
		out.println(("上载成功！！！").toString());
		} else { out.println(("上载失败！！！").toString()); } 
		}//与前面的if对应 
		//System.out.println("------1---------");
		mySmartUpload.stop();
		//System.out.println("------2---------");
		mySmartUpload=null;
		//System.out.println("------3---------");
		 */
	} catch (SecurityException ex) {
		//System.out.println("***************");    
		out.println(ex.getMessage());
	}
	/*
	RequestDispatcher rd = request.getRequestDispatcher("http://www.sina.com.cn");
	//System.out.println("dfsdffsd::::" + rd.toString());
	rd.forward(request,response);
	 */
	//response.sendRedirect("http://www.sina.com.cn");
%>

 