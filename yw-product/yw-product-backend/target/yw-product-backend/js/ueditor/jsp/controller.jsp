<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath("/");
	
	String action= request.getParameter("action");
	String result = new ActionEnter( request, rootPath ).exec();
	
	if(action != null && (action.equals("listfile") || action.equals("listimage"))){
		
		rootPath = rootPath.replace("\\", "/");
		result = result.replace(rootPath, request.getContextPath()+"/");
	}
	System.out.println("-------富文本上传路径------- >" + result);
	out.write( result );
%>