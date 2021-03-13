<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	            <span>Add BGM</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
                        
	<div class="row">
    	<div class="col-md-12">
			<br/>
			<!-- 意见反馈 start -->
			<div class="tabbable-line boxless tabbable-reversed">
            	<div class="portlet box green-jungle">
                	<div class="portlet-title">
                    	<div class="caption">
                    		<i class="icon-plus"></i>Add BGM
                    	</div>
					</div>
					
					<div class="portlet-body form">
					
	                    <!-- BEGIN FORM-->
	                    <form id="addBgmForm" class="form-horizontal">
		                    <div class="form-body">
		                    
		                    	<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>Author：</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
		                            		<input id="author" name="author" type="text" class="form-control" placeholder="Author">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>BGM Name：</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
		                            		<input id="name" name="name" type="text" class="form-control" placeholder="BGM Name">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>BGM Clip</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
	                            			<input type="hidden" id="path" name="path" class="form-control"/>

											<input id="file" type="file" name="file" data-url="<%=request.getContextPath() %>/bgms/bgmUpload.action" accept="audio/*"/>
											
											<div id="bgmContent"></div>
	                            		</div>
	                            		
									</div>
								</div>
		                                                  
							</div>
	                                                        
							<div class="form-actions">
			                    <div class="row">
			                        <div class="col-md-offset-3 col-md-9">
			                            <button type="submit" class="btn green-jungle">Submit</button>
			                            <button type="reset" class="btn grey-salsa btn-outline">Cancel</button>
			                        </div>
			                    </div>
							</div>
						</form>
						<!-- END FORM-->
						
					</div>
				</div>
			</div>
                            
		</div>
	</div>
	
<script type="text/javascript">
    
    $("#file").fileupload({
    	pasteZone: "#bgmContent",
    	dataType: "json",
    	done: function(e, data) {
    		console.log(data);
    		
    		if (data.result.code != '200') {
    			alert(data.result.message);
    		} else {
				let bgmServer = $("#bgmServer").val();
    			let url = bgmServer + data.result.data;
    			$("#bgmContent").html("<a href='" + url + "' target='_blank'>Play</a>");
    			$("#path").attr("value", data.result.data);
    		}
    		
    	}
    });
    
    let submitBgm = function() {
    	$('#addBgmForm').ajaxSubmit({
    		url: $('#hdnContextPath').val() + '/bgms/addBgm.action',
    		type: 'POST',
    		success: function(data) {

    			if (data.code == 200 && data.message == 'ok') {
    				alert('BGM添加成功...')
    			} else {
    				alert('BGM添加失败...')
    			}
    			
    			$("#bgmListMenu").click();
    		}
    	});
    }
    
    $('#addBgmForm').validate({
    	errorElement: 'span', //default input error message container
        errorClass: 'help-block', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "", // validate all fields including form hidden input
        rules: {
        	author: {
                required: true,
                rangelength: [1,10]
            },
            name: {
                required: true,
                rangelength: [1,50]
            },
            path: {
                required: true
            }
        },
        messages: {
        	author: {
                required: "The author is required",
                rangelength: "The length is between 1 to 10"
            },
            name: {
                required: "The name is required",
                rangelength: "The length is between 1 to 50"
            },
            path: {
                required: "The path is required"
            }
        },
        invalidHandler: function(event, validator) { //display error alert on form submit   
            $('.alert-danger', $('#addBgmForm')).show();
        },

        highlight: function(element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        success: function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function(error, element) {
            error.insertAfter(element.closest('#input-error'));
        },
        submitHandler: function(form) {
        	// FIXME
        	submitBgm();
        }
    });
    
    
</script>
