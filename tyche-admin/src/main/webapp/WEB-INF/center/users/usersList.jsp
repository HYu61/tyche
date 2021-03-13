<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath() %>/static/pages/js/usersList.js?v=1.1" type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>Home Page</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>User Info</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>User List</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
        
    <!-- 用户信息列表 jqgrid start -->                
	<div class="row">
	
		<!-- 搜索内容 -->
		<div class="col-md-12">
			<br/>
				<form id="searchUserListForm" class="form-inline" method="post" role="form">
					<div class="form-group">
						<label class="sr-only" for="username">Username:</label>
						<input id="username" name="username" type="text" class="form-control" placeholder="Username 用户名" />
					</div>
					<div class="form-group">
						<label class="sr-only" for="nickname">Nickname:</label>
						<input id="nickname" name="nickname" type="text" class="form-control" placeholder="Nickname 昵称" />
					</div>
					<button id="searchUserListButton" class="btn yellow-casablanca" type="button">Search</button>
				</form>
			</div>
	
	
    	<div class="col-md-12">
			<br/>
			
			<div class="usersList_wrapper">  
			    <table id="usersList"></table>  
			    <div id="usersListPager"></div>  
			</div>  
			
		</div>
	</div>
	<!-- 用户信息列表 jqgrid end -->
	
