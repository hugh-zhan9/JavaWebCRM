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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
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

		$("#addtestbtn").click(function(){

			// 向服务端查询数据
			$.ajax({
				url:"activity/activities.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					// 将查询到的数据取出来，使用EL表达式
					var userid = "${user.id}";

					var html = "";

					$.each(data,function (i,n) {
						html += "<option value='"+ n.id +"'>"+n.name+"</option>";
					})
					// 将数据加载到create-marketActivityOwner中
					$("#create-marketActivityOwner").html(html);

					// 将当前登录的用户，设置为下拉框默认的选项
					$("#create-marketActivityOwner").val(userid);

					// 所有者下拉框处理完成之后，再展现模态窗口
					$("#createActivityModal").modal("show");
				}
			})
		})

		// 创建模态窗口中的保存按钮
		$("#saveBtn").click(function () {
			var jsonData1 = JSON.stringify({
				"owner":$.trim($("#create-marketActivityOwner").val()),
				"name":$.trim($("#create-marketActivityName").val()),
				"startDate":$.trim($("#create-startTime").val()),
				"endDate":$.trim($("#create-endTime").val()),
				"cost":$.trim($("#create-cost").val()),
				"description":$.trim($("#create-describe").val())
			});

			$.ajax({
				url:"activity/activities.do",
				data:jsonData1,
				type:"post",
				dataType:"json",
				contentType:"application/json",
				success:function (data) {
					if (data.success){
						// 局部刷新市场活动信息列表
						//$("#activity-information").
						/*
						$.ajax({
							url:"activity/activitiesRemark.do",
							type:"get",
							success:function (data) {
								// 将查询出的结果展示出来
								var info_html = "<table class=\"table table-hover\" id=\"activity-information\">\n" +
										"    <thead>\n" +
										"        <tr style=\"color: #B3B3B3;\">\n" +
										"            <td><input type=\"checkbox\" /></td>\n" +
										"            <td>名称</td>\n" +
										"            <td>所有者</td>\n" +
										"            <td>开始日期</td>\n" +
										"            <td>结束日期</td>\n" +
										"        </tr>\n" +
										"    </thead>\n" +
										"    <tbody>";
								/*
								$.each(data,function (i,n) {
									info_html += "<tr class=\"active\">\n" +
											"<td><input type=\"checkbox\" /></td>\n" +
											"<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detail.jsp';\">"+n.name+"</a></td>\n" +
											"<td>"+n.owner+"</td>\n" +
											"<td>"+n.startDate+"</td>\n" +
											"<td>"+n.endDate+"</td>\n" +
											"</tr>";

								})
								info_html += "</tbody>\n" +
										"</table>";

								$("#activity-information-div").html(info_html)
							}
						})
						 */
						// 清空模态窗口中的数据
						// $("#activityAddForm").reset(); 无效方法，JQuery没有提供，但是原生JavaScript提供了
						// JQuery对象转换成Dom对象
						$("#activityAddForm")[0].reset();

						// 关闭添加操作的模态窗口
						$("#createActivityModal").modal("hide")
						pageList(1,5);
					}else{
						alert("保存失败")
					}

				}
			})
		})

		// 查询按钮
		$("#searchButton").click(function () {
			// 每次查询前将搜索框中的内容保存到隐藏域中
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));
			pageList(1,5);

		})



		pageList(1,5);


		// 为全选的复选框绑定事件
		$("#checkAllBtn").click(function () {
			$(".checkBox").prop("checked", this.checked);
		})
		// 让复选框来触发全选操作
		/*
		$(".checkBox").click(function () {
			alert("测试")
		})

		 上面这么写是有问题的，因为不能这么给动态生成的对象绑定事件，需要通过on方法来进行绑定
		 语法：
		 	$(需要绑定元素的有效的外层元素).on("绑定事件的方式"，需要绑定的元素的jQuery对象，回调函数)
		*/

		// 复选框全选时触发全选框选择
		$("#activity-information").on("click",$(".checkBox"),function () {
			$("#checkAllBtn").prop("checked",$(".checkBox").length==$(".checkBox:checked").length)
		})


		// 删除按钮
		$("#deleteBtn").click(function () {
			var params = "";
			var $userChecked = $(".checkBox:checked");
			if ($userChecked.size()==0){
				alert("请选择需要删除的对象")
			}else{
				for (var i=0;i<$userChecked.length;i++){
					params += "id=" + $($userChecked[i]).val();
					if(i< $userChecked.length-1){
						params += "&";
					}
				}
				if(confirm("确定删除所选的记录吗？")){
					$.ajax({
						url:"activity/delete.do",
						data:params,
						dataType:"json",
						type:"post",
						success:function (data) {
							if(data.success){
								pageList(1,5);
							}else{
								alert("删除失败");
							}
						}
					})
				}
			}
		})

		// 修改按钮
		$("#updateBtn").click(function () {
			var $userChecked = $(".checkBox:checked");
			if ($userChecked.size()==0){
				alert("请选择需要修改的对象")
			}else {
				if ($userChecked.size()!=1) {
					alert("每次只能修改一个对象")
				}else{
					var uid = "id="+$userChecked.val();
					$.ajax({
						url:"activity/activity.do",
						data:uid,
						type:"get",
						dataType:"json",
						success:function (data) {
							$.ajax({
								url:"activity/activities.do",
								type:"get",
								dataType:"json",
								success:function (data) {
									// 将查询到的数据取出来，使用EL表达式
									var userid = "${user.id}";
									var html = "";
									$.each(data,function (i,n) {
										html += "<option value='"+n.id+"'>"+n.name+"</option>";
									})
									// 将数据加载到edit-marketActivityOwner中
									$("#edit-marketActivityOwner").html(html);
									// 将当前登录的用户，设置为下拉框默认的选项
									$("#edit-marketActivityOwner").val(userid);
								}
							})


							$("#edit-marketActivityName").val(data.name);
							$("#edit-startTime").val(data.startDate);
							$("#edit-endTime").val(data.endDate);
							$("#edit-cost").val(data.cost);
							$("#edit-describe").val(data.description);
							$("#editActivityModal").modal("show");
						}
					})
				}
			}
		})

		// 修改模态窗口中保存按钮
		$("#saveUpdateBtn").click(function () {
			var id = $(".checkBox:checked").val();
			var name = "${user.name}";
			var jsonData = JSON.stringify({
				"id":id,
				"owner":$.trim($("#edit-marketActivityOwner").val()),
				"name":$.trim($("#edit-marketActivityName").val()),
				"startDate":$.trim($("#edit-startTime").val()),
				"endDate":$.trim($("#edit-endTime").val()),
				"cost":$.trim($("#edit-cost").val()),
				"description":$.trim($("#edit-describe").val()),
				"editBy":name
			})
			if(confirm("确定修改吗？")){
				$.ajax({
					url:"activity/update.do",
					data:jsonData,
					type:"post",
					dataType:"json",
					contentType:"application/json",
					success:function (data) {
						if (data.success){
							// 局部刷新
							pageList(1,5);
							// 关闭模态窗口
							$("#editActivityModal").modal("hide")
						}else{
							alert("修改失败");
						}
					}
				})
			}

		})


	});



	// 市场活动信息列表局部刷新
	function pageList(pageNo, pageSize) {
		// 每次刷新之后将全选的复选框的值清空
		$("#selectAll").prop("checked",false);

		// 每次查询前将hidden域中的值取出，赋予search框
		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));

		var jsonData2 =JSON.stringify({
			"pageNo":pageNo,
			"pageSize":pageSize,
			"name":$.trim($("#search-name").val()),
			"owner":$.trim($("#search-owner").val()),
			"startDate":$.trim($("#search-startDate").val()),
			"endDate":$.trim($("#search-endDate").val())
		});

		$.ajax({
			url:"activity/activitiesRemark.do",
			data:jsonData2,
			type:"post",
			dataType:"json",
			contentType:"application/json",
			success:function (data) {
				/*
                    data需要包含的信息
                    [{市场活动1}，{市场活动2}，...]
                    bootStrap需要的信息——总记录的条数
                    {total:100}
                    所以后台传出的数据格式应该是：
                    {"total":100,"listReuslt":[{市场活动1},{市场活动2},{市场活动3}...]}
                 */
				var info_html="";
				$.each(data.listReuslt, function (i,n) {
					// 这里少写了一个+号调试了一天的bug
					info_html += '<tr class="active">';
					info_html += '<td><input type="checkbox" class="checkBox" value="'+n.id+'"/></td>';
					info_html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					info_html += '<td>'+n.owner+'</td>';
					info_html += '<td>'+n.startDate+'</td>';
					info_html += '<td>'+n.endDate+'</td>';
					info_html += '</tr>';
				})
				$("#activity-information").html(info_html)
				// 计算总页数
				var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

				// 数据处理完毕之后，结合分页查询，对前端展现分页信息
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 10, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					// 该回调函数在点击分页组件时触发的
					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})
	}

</script>
</head>
<body>

	<input type="hidden" id="hidden-name" />
	<input type="hidden" id="hidden-owner" />
	<input type="hidden" id="hidden-startDate" />
	<input type="hidden" id="hidden-endDate" />

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="activityAddForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="time form-control" id="create-startTime" readonly="readonly">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="time form-control" id="create-endTime" readonly="readonly">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" readonly="readonly">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" readonly="readonly">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveUpdateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!--
	删除市场活动的模态窗口
	<div class="modal fade" id="delectActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel3">删除市场活动</h4>
				</div>
				<div class="modal-body">
			<font size="8px">确定要删除该市场活动吗？</font><br><br>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="delete-btn">删除</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>>
	</div>
	-->

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startDate" readonly="readonly" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endDate" readonly="readonly" />
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchButton">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">

					<!--
						data-toggle="modal" 表示触发该按钮打开一个模态窗口
						data-target 表示模态窗口的id值

						但是现在这个功能是写死在button的属性中，这会导致不能对按钮的功能进行扩充
						所以未来的开发中一定不要将出发模态窗口的操作写死在按钮的属性中，可以写一个 jquery函数
					-->

				  <button type="button" id="addtestbtn" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" id="updateBtn" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" id="deleteBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>

			<div style="position: relative;top: 10px;" id="activity-information-div">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAllBtn"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activity-information">
					<!--
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>
                        -->

					</tbody>
				</table>

			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage">

				</div>

				<!--
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
				-->
			</div>
			
		</div>
		
	</div>
</body>
</html>