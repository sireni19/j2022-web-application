<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 24.08.2023
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Test JSP</title>
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Rubik:400,700'>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%! private int pageCounter=0;%>
<h1>JSP-expression</h1>
<h2>Current server time: <%=new Date()%>
</h2>
<h1>Parameter from URL: <%=request.getParameter("xxx")%> </h1>

<h1>All params in request: </h1>
<table>
    <tr>
        <th>Parameter</th>
        <th>Value</th>
    </tr>
    <%
        int x =(int)(Math.random()*3)+1;
        Map<String, String[]> map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String[] values = map.get(key);
            for (int i = 0; i < values.length; i++) {%>
    <tr>
        <td><%=key%>
        </td>
        <td><%=values[i]%>
        </td>
    </tr>
    <%System.out.println("Parameter: "+key+"--->"+"Value: "+values[i]);}%>
    <%}%>
</table>
<h2>Page requested: <%=++pageCounter%></h2>
<%
    String result="";
        if(x==1) {
            result = "Хороший день";
        }else if(x==2){
            result="Обычный день";
        }else {
            result="Плохой день";
       } %>
<p><%=result%></p>

</body>
</html>
