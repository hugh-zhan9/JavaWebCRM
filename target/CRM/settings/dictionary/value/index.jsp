<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>

	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='save.jsp'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-default" onclick="window.location.href='edit.jsp'"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${appellation}" var="a">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>${a.value}</td>
					<td>${a.text}</td>
					<td>${a.orderNo}</td>
					<td>appellation</td>
				</tr>
			</c:forEach>
			<c:forEach items="${clueState}" var="b">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td>${b.value}</td>
					<td>${b.text}</td>
					<td>${b.orderNo}</td>
					<td>clueState</td>
				</tr>
			</c:forEach>
			<c:forEach items="${returnPriority}" var="c">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>3</td>
					<td>${c.value}</td>
					<td>${c.text}</td>
					<td>${c.orderNo}</td>
					<td>returnPriority</td>
				</tr>
			</c:forEach>
			<c:forEach items="${returnState}" var="e">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>4</td>
					<td>${e.value}</td>
					<td>${e.text}</td>
					<td>${e.orderNo}</td>
					<td>returnState</td>
				</tr>
			</c:forEach>
			<c:forEach items="${source}" var="f">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>5</td>
					<td>${f.value}</td>
					<td>${f.text}</td>
					<td>${f.orderNo}</td>
					<td>source</td>
				</tr>
			</c:forEach>
			<c:forEach items="${stage}" var="g">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>6</td>
					<td>${g.value}</td>
					<td>${g.text}</td>
					<td>${g.orderNo}</td>
					<td>stage</td>
				</tr>
			</c:forEach>
			<c:forEach items="${transactionType}" var="h">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>7</td>
					<td>${h.value}</td>
					<td>${h.text}</td>
					<td>${h.orderNo}</td>
					<td>transactionType</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>