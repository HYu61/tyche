<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="<%=request.getContextPath() %>/static/pages/js/bgmList.js?v=1.0.0.2" 
	type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>Home</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>BGM Management</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>Display all BGM</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
                        
	<div class="row">
    	<div class="col-md-12">
                   
			<div class="bgmList_wrapper">
                <table id="bgmList"></table> 
    			<div id="bgmListPager"></div>
             </div>
             
		</div>
	</div>
