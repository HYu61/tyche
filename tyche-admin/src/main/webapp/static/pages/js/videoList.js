var forbidVideo = function(videoId) {
	
	var flag = window.confirm("Forbid the video?");
	if (!flag) {
		return;
	}

	let operation = -1;
	$.ajax({
    	url: $("#hdnContextPath").val() + "/videos/"+videoId+".action?operation="+ operation,
    	type: "PATCH",
    	async: false,
    	success: function(data) {
            if(data.code == 200 && data.message == "ok") {
            	alert("OK");
            	var jqGrid = $("#usersReportsList");  
				jqGrid.jqGrid().trigger("reloadGrid");
            } else {
            	console.log(JSON.stringify(data));
            }
    	}
	})
}

var deleteReport = function(id) {

    var flag = window.confirm("Forbid the video?");
    if (!flag) {
        return;
    }

    let reportId = id;
    $.ajax({
        url: $("#hdnContextPath").val() + "/reports/"+reportId+".action",
        type: "delete",
        async: false,
        success: function(data) {
            if(data.code == 200 && data.message == "ok") {
                alert("OK");
                var jqGrid = $("#usersReportsList");
                jqGrid.jqGrid().trigger("reloadGrid");
            } else {
                console.log(JSON.stringify(data));
            }
        }
    })
}


// 定义举报列表对象
var UsersReportsList = function () {
	
    // 举报列表
	var handleUsersReportsList = function() {
    	
		// 上下文对象路径
		var hdnContextPath = $("#hdnContextPath").val();
		var apiServer = $("#apiServer").val();
		debugger;
		var jqGrid = $("#usersReportsList");  
        jqGrid.jqGrid({  
            caption: "Reported Video List",
            url: hdnContextPath + "/videos/reportedVideoList.action",
            mtype: "get",
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式  
            datatype: "json",  
            colNames: ['ID', 'Title', 'Content', 'Publisher', 'Video Id', 'Video', 'Video Status', 'User', 'Date', "Inactive", "Delete"],
            colModel: [  
                { name: 'id', index: 'id', width: 30, sortable: false, hidden: false },  
                { name: 'title', index: 'title', width: 25, sortable: false },
                { name: 'content', index: 'content', width: 50, sortable: false },
                { name: 'reportedUsername', index: 'reportedUsername', width: 30, sortable: false },
                { name: 'reportedVideoId', index: 'reportedVideoId', width: 30, sortable: false },
                { name: 'videoPath', index: 'videoPath', width: 20, sortable: false,
                	formatter:function(cellvalue, options, rowObject) {
                		var src = apiServer + cellvalue;
                		var display = "<a href='" + src + "' target='_blank'>Play</a>"
			    		return display;
			    	}
                },
                { name: 'status', index: 'status', width: 30, sortable: false, hidden: false,
                	formatter:function(cellvalue, options, rowObject) {
                        let result;
                        if(cellvalue == 1){
                            result = 'VIP';
                        }else if(cellvalue == 0){
                            result = 'Active';
                        }else{
                            result = 'Inactive';
                        }
			    		return result;
			    	}
			    },
                { name: 'submitUsername', index: 'submitUsername', width: 20, sortable: false },
                { name: 'createTime', index: 'createTime', width: 25, sortable: false, hidden: false,
                	formatter:function(cellvalue, options, rowObject) {
                		var createTime = Common.formatTime(cellvalue,'yyyy-MM-dd HH:mm:ss');
			    		return createTime;
			    	}
			    },
                { name: '', index: '', width: 35, sortable: false, hidden: false,
                	formatter:function(cellvalue, options, rowObject) {
                		var button = '<button class="btn btn-outline blue-chambray" id="" onclick=forbidVideo("' + rowObject.reportedVideoId + '") style="padding: 1px 3px 1px 3px;">Inactive Video</button>';
			    		return button;
			    	}
			    },
                { name: '', index: '', width: 30, sortable: false, hidden: false,
                    formatter:function(cellvalue, options, rowObject) {
                        var button = '<button class="btn btn-outline blue-chambray" id="" onclick=deleteReport("' + rowObject.id + '") style="padding: 1px 3px 1px 3px;">Delete Report</button>';
                        return button;
                    }
                }
            ],  
            viewrecords: true,  		// 定义是否要显示总记录数
            rowNum: 10,					// 在grid上显示记录条数，这个参数是要被传递到后台
            rownumbers: true,  			// 如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
            autowidth: true,  			// 如果为ture时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
            height: 500,				// 表格高度，可以是数字，像素值或者百分比
            rownumWidth: 36, 			// 如果rownumbers为true，则可以设置行号 的宽度
            pager: "#usersReportsListPager",		// 分页控件的id  
            subGrid: false				// 是否启用子表格
        }).navGrid('#usersReportsListPager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        
  
        // 随着窗口的变化，设置jqgrid的宽度  
        $(window).bind('resize', function () {  
            var width = $('.usersReportsList_wrapper').width()*0.99;  
            jqGrid.setGridWidth(width);  
        });  
        
        // 不显示水平滚动条
        jqGrid.closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    }
    
    return {
        // 初始化各个函数及对象
        init: function () {debugger;
        	handleUsersReportsList();
        }

    };

}();


jQuery(document).ready(function() {debugger;
	UsersReportsList.init();
});