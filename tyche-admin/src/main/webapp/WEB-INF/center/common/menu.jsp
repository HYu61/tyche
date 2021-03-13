<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- BEGIN CONTAINER -->
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar-wrapper">
        <!-- BEGIN SIDEBAR -->
        <div class="page-sidebar navbar-collapse collapse">
            <!-- BEGIN SIDEBAR MENU -->
            <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="false" data-slide-speed="200" style="padding-top: 20px">
                <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <li class="sidebar-toggler-wrapper hide">
                    <div class="sidebar-toggler">
                        <span>
                        	
                        </span>
                    </div>
                </li>
                
               	<!-- 控制台  start -->
               	<li class="nav-item">
                	<a href="<%=request.getContextPath() %>/">
                    	<i class="icon-home"></i>
                        	<span class="title">Home</span>
					</a>
               	</li>
               	
               	<!-- 用户管理 start -->
				<li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-user"></i>
                        <span class="title">User Info</span>
						<span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item ">
                            <a href="<%=request.getContextPath() %>/users/showList.action" class="ajaxify nav-link ">
                                <span class="title">User List</span>
                            </a>
                        </li>
                    </ul>
               	</li>
               	
               	<!-- 背景音乐 start -->
				<li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-basket-loaded"></i>
                        <span class="title">BGM Management</span>
						<span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
						<li class="nav-item ">
                            <a id="bgmListMenu" href="<%=request.getContextPath() %>/bgms/showBgmList.action" class="ajaxify nav-link " id="bgmListMenu">
                                <span class="title">BGM List</span>
                            </a>
                        </li>
                        <li class="nav-item ">
                            <a href="<%=request.getContextPath() %>/bgms/showAddBgm.action" class="ajaxify nav-link ">
                                <span class="title">Add BGM</span>
                            </a>
                        </li>
                    </ul>
               	</li>
               	
               	<!-- 举报管理 start -->
				<li class="nav-item ">
                    <a href="javascript:;" class="nav-link nav-toggle">
                        <i class="icon-notebook"></i>
                        <span class="title">Illegal Video Report Management</span>
						<span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
						<li class="nav-item ">
                            <a href="<%=request.getContextPath() %>/videos/showReportList.action" class="ajaxify nav-link ">
                                <span class="title">Illegal Video Report List</span>
                            </a>
                        </li>
                    </ul>
               	</li>
               	
            </ul>
            <!-- END SIDEBAR MENU -->
        </div>
        <!-- END SIDEBAR -->
    </div>
    <!-- END SIDEBAR -->