<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload a file</title>
</head>
<body>
	<FORM ENCTYPE='multipart/form-data' method='POST' action='/UploadServlet'>
		<INPUT TYPE='file' NAME='upload_input'>
		<INPUT TYPE='submit' VALUE='upload'>
	</FORM>
</body>
</html>