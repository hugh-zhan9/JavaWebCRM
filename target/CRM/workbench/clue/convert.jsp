<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
		// 打开市场活动模态窗口
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});

		// 加载已经关联的市场活动
		$("#showActivity").click(function () {
			$.ajax({
				url:"clue/getAllBoundActivity.do",
				data:{"clueId":"${clue.id}"},
				dataType:"json",
				method:"get",
				success:function (data) {
					var html = "";
					$.each(data, function (i,n) {
						html+='<tr>'
						html+='<td><input type="radio" name="activity" value="'+n.id+'"/></td>'
						html+='<td id="'+n.id+'">'+n.name+'</td>'
						html+='<td>'+n.startDate+'</td>'
						html+='<td>'+n.endDate+'</td>'
						html+='<td>'+n.owner+'</td>'
						html+='</tr>'
					})
					$("#activityBody").html(html);
					$(":radio").click(function () {
						if (this.checked){
							var id = this.value;
							var name = $("#"+id).html();
							$("#activity").val(name);
							$("#activityId").val(id);
							$("#searchActivityModal").modal("hide");
						}
					})
				}
			})
		})


		// 搜索市场活动模态窗口的搜索框
		$("#searchActivity").keydown(function (event) {
			if (event.keyCode==13){
				$.ajax({
					url:"clue/getBoundActivity.do",
					data:{"name":$.trim($("#searchActivity").val()),"clueId":"${clue.id}"},
					dataType:"json",
					method:"get",
					success:function (data) {
						// 将结果铺上去
						var html = "";
						$.each(data, function (i,n) {
							html+='<tr>';
							html+='<td><input type="radio" name="activity"  value="'+n.id+'"/></td>';
							html+='<td id="'+n.id+'">'+n.name+'</td>';
							html+='<td>'+n.startDate+'</td>';
							html+='<td>'+n.endDate+'</td>';
							html+='<td>'+n.owner+'</td>';
							html+='</tr>';
						})
						$("#activityBody").html(html);

						/* 单选框选中之后关闭模态窗口
							$("#searchActivityModal").modal("hide");
						 */
						$(":radio").click(function () {
							var $radio = $(":radio:checked")
							if (this.checked){
								var id = $radio.val();
								var name = $("#"+id).html();
								$("#activity").val(name);
								$("#activityId").val(id);
								$("#searchActivityModal").modal("hide");
							}
						})

						$("#searchActivity").val("");
					}

				})
				return false;
			}
		})


		// 绑定转换按钮的功能
		$("#convertBtn").click(function () {
			/* 判断checkbox是否被选中
				$("#isCreateTransaction").prop("checked")
			 */
			if($("#isCreateTransaction").prop("checked")){
				/* 将模态窗口中的线索存入*/
				var jsonData = JSON.stringify({
					"owner":"${user.name}",
					"money":$.trim($("#amountOfMoney").val()),
					"name":$.trim($("#tradeName").val()),
					"expectedDate":$.trim($("#expectedClosingDate").val()),
					"stage":$.trim($("#stage").val()),
					"source":"${clue.source}",
					"activityId":$.trim($("#activityId").val()),
					"createBy":"${clue.createBy}",
					"createTime":"${clue.createTime}",
					"editBy":"${clue.editBy}",
					"editTime":"${clue.editTime}",
					"description":"${clue.description}",
					"contactSummary":"${clue.contactSummary}",
					"nextContactTime":"${clue.nextContactTime}",
					"fullname":"${clue.fullname}",
					"appellation":"${clue.appellation}",
					"company":"${clue.company}",
					"job":"${clue.job}",
					"email":"${clue.email}",
					"phone":"${clue.phone}",
					"website":"${clue.website}",
					"mphone":"${clue.mphone}",
					"state":"${clue.state}",
					"address":"${clue.address}"
				});
				$.ajax({
					url:"clue/convertToTran.do",
					data:jsonData,
					dataType:"json",
					method:"post",
					contentType:"application/json",
					success:function (data) {
						// 按下按钮取消选中
						$("#isCreateTransaction").prop("checked",false);
						$("#create-transaction2").hide(200);
						// 清空之前的信息
						$("#amountOfMoney").val("");
						$("#tradeName").val("");
						$("#expectedClosingDate").val("");
						$("#stage").val("");
						$("#activity").val("");

					}
				})
			}else{
				/* 将线索分别转换成客户和联系人
			 */
				var clueId = JSON.stringify({
					"fullname":"${clue.fullname}",
					"appellation":"${clue.appellation}",
					"owner":"${clue.owner}",
					"company":"${clue.company}",
					"job":"${clue.job}",
					"email":"${clue.email}",
					"phone":"${clue.phone}",
					"website":"${clue.website}",
					"mphone":"${clue.mphone}",
					"state":"${clue.state}",
					"source":"${clue.source}",
					"createBy":"${clue.createBy}",
					"createTime":"${clue.createTime}",
					"editBy":"${clue.editBy}",
					"editTime":"${clue.editTime}",
					"description":"${clue.description}",
					"contactSummary":"${clue.contactSummary}",
					"nextContactTime":"${clue.nextContactTime}",
					"address":"${clue.address}"
				})
				$.ajax({
					url:"clue/convertToCustomerAndContacts.do",
					data:clueId,
					dataType:"json",
					method:"post",
					contentType:"application/json",
					success:function (data) {
						if (data.success){
							alert("转换成功")
						}
					}
				})
			}
		})


	});


</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询" id="searchActivity">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="activityBody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${clue.fullname}${clue.appellation}-${clue.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${clue.fullname}${clue.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control" id="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
				<option value=""></option>
		    	<c:forEach items="${stage}" var="a">
					<option value="${a.value}" >${a.text}</option>
				</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#searchActivityModal" style="text-decoration: none;" id="showActivity"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
			  <input type="hidden" id="activityId">
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${user.name}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" value="转换" id="convertBtn">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>