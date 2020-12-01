<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

	Map<String,String> map = (Map<String, String>) application.getAttribute("stage2Possibility");
	Set<String> set = map.keySet();
%>
<!DOCTYPE html>
<html>
<head>

	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>


	<script>
		// 拼凑json字符串
		var json = {
			<%
				for(String key : set){
					String value = map.get(key);
			%>
			"<%=key%>":<%=value%>,
			<%
				}
			%>
		}


		$(function () {

			// 时间控件
			$(".time").datetimepicker({
				language:  "zh-CN",
				format: "yyyy-mm-dd",//显示格式hh:ii:ss
				minView: "month",//设置只显示到月份
				initialDate: new Date(),//初始化当前日期
				autoclose: true,//选中自动关闭
				todayBtn: true, //显示今日按钮
				clearBtn : true,
				pickerPosition: "bottom-left"
			});

			// 查询所有用户
			$.ajax({
				url:"activity/activities.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					var html = "";
					$.each(data,function (i,n) {
						if (${tran.owner} = n.name){
							html += "<option selected value='" + n.name + "'>" + n.name + "</option>";
						}else {
							html += "<option value='" + n.name + "'>" + n.name + "</option>";
						}
					})
					$("#edit-transactionOwner").html(html)
				}
			})


			// 查询所有市场活动
			$.ajax({
				url:"clue/getAllActivity.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					var html = "";
					$.each(data.ac,function (i,n) {
						html+='<tr>';
						html+='<td><input type="radio" class="radio1" name="activity" value="'+n.id+'"/></td>';
						html+='<td id='+n.id+'>'+ n.name +'</td>';
						html+='<td>'+ n.startDate +'</td>';
						html+='<td>'+ n.endDate +'</td>';
						html+='<td>'+ n.owner +'</td>';
						html+='</tr>';
					})
					$("#activityBody").html(html)

					// 单选按钮被选中之后的操作
					$(".radio1").click(function () {
						var $radio = $(".radio1:checked")
						if (this.checked){
							var id = $radio.val();
							var name = $("#"+id).html();
							$("#edit-activitySrc").val(name);
							$("#edit-activityId").val(id);

							$("#findMarketActivity").modal("hide");
						}
					})
				}
			})


			/*$.ajax({
				url:"clue/getAllClue.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					var html = "";
					$.each(data.ac,function (i,n) {
						// 这句判断也点问题
						if (${tran.activityId} = n.id){
							html+='<tr>';
							html+='<td><input type="radio" name="activity" id="'+n.id+'" checked/></td>';
							html+='<td>'+ n.name +'</td>';
							html+='<td>'+ n.startDate +'</td>';
							html+='<td>'+ n.endDate +'</td>';
							html+='<td>'+ n.owner +'</td>';
							html+='</tr>';
						}else {
							html+='<tr>';
							html+='<td><input type="radio" name="activity" id="'+n.id+'"/></td>';
							html+='<td>'+ n.name +'</td>';
							html+='<td>'+ n.startDate +'</td>';
							html+='<td>'+ n.endDate +'</td>';
							html+='<td>'+ n.owner +'</td>';
							html+='</tr>';
						}
					})
					$("#activityBody").html(html)
				}
			})*/

			// 查询所有联系人
			$.ajax({
				url:"tran/getAllContacts.do",
				dataType:"json",
				success:function (data) {
					var html = "";
					$.each(data, function (i,n) {
						html+='<tr>';
						html+='<td><input type="radio" class="radio2" value="'+n.id+'"></td>';
						html+='<td id="'+n.id+'">'+n.fullname+'</td>';
						html+='<td>'+n.email+'</td>';
						html+='<td>'+n.mphone+'</td>';
						html+='</tr>';

					})
					$("#contactsBody").html(html)

					$(".radio2").click(function () {
						var $radio = $(".radio2:checked")
						if (this.checked){
							var id = $radio.val();
							var name = $("#"+id).html();
							$("#edit-contactsName").val(name);
							$("#edit-contactsId").val(id);

							$("#findContacts").modal("hide");
						}
					})
				}

			})


			// 更新按钮执行修改操作
			$("#savaTranBtn").click(function () {
				var data = JSON.stringify({
					"id":"${tran.id}",
					"owner":$("#edit-transactionOwner").val(),
					"money":$("#edit-amountOfMoney").val(),
					"name":$("#edit-transactionName").val(),
					"expectedDate":$("#edit-expectedClosingDate").val(),
					"customerId":$("#edit-accountId").val(),
					"stage":$("#edit-transactionStage").val(),
					"type":$("#edit-transactionType").val(),
					"percent":$("#edit-possibility").val(),
					"source":$("#edit-clueSource").val(),
					"activityId":$("#edit-activityId").val(),
					"contactsId":$("#edit-contactsId").val(),
					"editBy":"${user.name}",
					"description":$("#create-describe").val(),
					"contactSummary":$("#create-contactSummary").val(),
					"nextContactTime":$("#create-nextContactTime").val()
				})
				$.ajax({
					url:"tran/editTran.do",
					data:data,
					dataType:"json",
					method:"post",
					contentType:"application/json",
					success:function (data) {
						if (data.success){
							alert("修改成功")
						}else {
							alert("修改失败")
						}
					}
				})
			})


			// 自动补全插件代码
			$("#edit-accountName").typeahead({
				source: function (query, process) {
					$.get(
							"tran/getAccountName.do",
							{ "name" : query },
							function (data) {
								//alert(data);
								// 后台传出的数据, 将浏览器默认提示关闭autocomplete="off"
								process(data);
							},
							"json"
					);
				},
				// 延迟加载时间
				delay: 200
			});


			// 根据阶段的值自动获取可能性
			$("#edit-transactionStage").change(function () {
				/*
					目标： 感觉stage的值自动填写可能性

					阶段stage和可能性之间的关系为一一对应的关系存储在map集合中
					但是map时Java中的对象，在js中不能直接使用，所以需要将map对象转化成json字符串

					这种动态的json不能使用json.key来获取值，需要使用json[key]来取值
				 */
				var stage = $("#edit-transactionStage").val();
				var possibility = json[stage];
				$("#edit-possibility").val(possibility);
			})


		})


	</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable4" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="activityBody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contactsBody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>更新交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="savaTranBtn">更新</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="edit-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="edit-transactionOwner">

				</select>
			</div>
			<label for="edit-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-amountOfMoney" value="${tran.money}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-transactionName" value="${tran.name}">
			</div>
			<label for="edit-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="edit-expectedClosingDate" value="${tran.expectedDate}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-accountName" value="${tran.customerName}" placeholder="支持自动补全，输入客户不存在则新建">
				<input type="hidden" id="edit-accountId" value="${tran.customerId}">
			</div>
			<label for="edit-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="edit-transactionStage">
			  	<c:forEach items="${stage}" var="s">
					<option value="${s.value}">${s.text}</option>
				</c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="edit-transactionType">
				  <option></option>
				  <option>已有业务</option>
				  <option selected>新业务</option>
				</select>
			</div>
			<label for="edit-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-possibility" value="${tran.percent}" readonly="readonly">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="edit-clueSource">
				  <c:forEach items="${source}" var="s">
					  <option value="${s.value}">${s.text}</option>
				  </c:forEach>
 				</select>
			</div>
			<label for="edit-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-activitySrc" value="${tran.activityName}">
				<input type="hidden" id="edit-activityId" value="${tran.activityId}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-contactsName" value="${tran.contactsName}">
				<input type="hidden" id="edit-contactsId" value="${tran.contactsId}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe">${tran.description}</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary" >${tran.contactSummary}</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="create-nextContactTime" value="${tran.nextContactTime}">
			</div>
		</div>
		
	</form>
</body>
</html>