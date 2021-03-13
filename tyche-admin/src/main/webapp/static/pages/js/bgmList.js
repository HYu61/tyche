
function deleteBgm(bgmId) {
	
	let flag = window.confirm("Delete Confirm");
	if (!flag) {
		return;
	}
	
	$.ajax({
		url: $("#hdnContextPath").val() + '/bgms/delBgm.action?bgmId=' + bgmId,
		type: "DELETE",
		success: function(data) {
			if (data.code == 200 && data.message == 'ok') {
				alert('BGM Deleted!!!');
				let jqGrid = $("#bgmList");
				jqGrid.jqGrid().trigger("reloadGrid");
			}
		}
	})
}

let BgmList = function() {

	// 构建bgm列表对象
    let handleBgmList = function() {
    	
    	// 上下文对象路径
		let hdnContextPath = $("#hdnContextPath").val();
		let bgmServer = $("#bgmServer").val();
		
		
		let jqGrid = $("#bgmList");
        jqGrid.jqGrid({  
            caption: "BGM List",
            url: hdnContextPath + "/bgms/getBgmList.action",
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
            datatype: "json",  
            colNames: ['ID', 'BGM Name','Author', 'Stored Path', 'Operation'],
            colModel: [  
                { name: 'id', index: 'id', width: 30 },  
                { name: 'name', index: 'name', width: 30 },
                { name: 'author', index: 'author', width: 20 },
                { name: 'path', index: 'path', width: 50, 
                	formatter: function(cellvalue, option, rowObject){
                		
                		let src = bgmServer + cellvalue;
                		let html = "<a href='" + src + "' target='_blank'>play</a>"
                		
                		return html;
                	}
                },
                { name: '', index: '', width: 50, 
                	formatter: function(cellvalue, option, rowObject){
                		
                		let html = '<button class="btn btn-outline blue-chambray" id="" onclick=deleteBgm("' + rowObject.id + '") style="padding: 1px 3px 1px 3px;">Delete</button>';
                		
                		return html;
                	}
                }
            ],  
            viewrecords: true,  		// 定义是否要显示总记录数
            rowNum: 10,					// 在grid上显示记录条数，这个参数是要被传递到后台
            rownumbers: true,  			// 如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
            autowidth: true,  			// 如果为ture时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
            height: 400,				// 表格高度，可以是数字，像素值或者百分比
            rownumWidth: 36, 			// 如果rownumbers为true，则可以设置行号 的宽度
            pager: "#bgmListPager",		// 分页控件的id  
            subGrid: false				// 是否启用子表格
        }).navGrid('#bgmListPager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        
        
        // 随着窗口的变化，设置jqgrid的宽度  
        $(window).bind('resize', function () {  
            let width = $('.bgmList_wrapper').width()*0.99;
            jqGrid.setGridWidth(width);  
        });  
        
        // 不显示水平滚动条
        jqGrid.closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    	
    }
    
    return {
        init: function() {
        	handleBgmList();
        }

    };

}();

jQuery(document).ready(function() {
	BgmList.init();
});

