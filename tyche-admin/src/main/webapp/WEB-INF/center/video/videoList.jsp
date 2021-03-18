<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath() %>/static/pages/js/videoList.js?v=1.1" type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>Home</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>Video Info</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>Video List</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
        
    <!-- 列表 jqgrid start -->                
	<div class="row">


		<!-- 搜索内容 -->
		<div class="col-md-12">
			<br/>
			<form id="searchVideoListForm" class="form-inline" method="post" role="form">
				<div class="form-group">
					<label class="sr-only" for="username">Username:</label>
					<input id="username" name="username" type="text" class="form-control" placeholder="Username 用户名" />
				</div>
				<div class="form-group">
					<label class="sr-only" for="status">Nickname:</label>
					<input id="status" name="status" type="text" class="form-control" placeholder="-1 inactive 0 regular 1 vip" />
				</div>
				<button id="searchVideoListButton" class="btn yellow-casablanca" type="button">Search</button>
			</form>
		</div>
	
    	<div class="col-md-12">
			<br/>
			<div class="videoList_wrapper">
			    <table id="videoList"></table>
			    <div id="videoListPager"></div>
			</div>  
			
		</div>
	</div>
	<!-- 列表 jqgrid end -->
	
