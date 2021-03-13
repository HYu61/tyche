let Login = function() {

	// 构建登录对象
    let handleLogin = function() {

    	// jquery-form-validate 前端的验证框架
        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },

            messages: {
                username: {
                    required: "Username is required!"
                },
                password: {
                    required: "Password is required!"
                }
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

            	let loginForm = $('.login-form');
            	let hdnContextPath = $("#hdnContextPath").val();
            	loginForm.ajaxSubmit({
            		dataType: "json",
                    type: "post", // 提交方式 get/post
                    url: hdnContextPath + '/admin/login.action', // 需要提交的 url
                    data: loginForm.serialize(),
                    success: function(data) {
            		    console.log(data.code)
                        // 登录成功或者失败的提示信息
                        if (data.code == 200 && data.message == "ok") {

                        	window.location.href = hdnContextPath + "/center.action";

                        } else {
                       	SweetAlert.error(data.message);
                        	// alert(data.msg);
                        }
                    },
                    error: function (data) {
                        console.log("error" + data)
                    }
                });

            }
        });

    }

    return {
        init: function() {
            handleLogin();
        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});