var Login = function() {
    var e = function() {
        $(".login-form").validate({
            errorElement: "span",
            errorClass: "help-block",
            focusInvalid: !1,
            rules: {
                username: {
                    required: !0
                },
                password: {
                    required: !0
                },
				verifycode:{
					required: !0
				},
                remember: {
                    required: !1
                }
            },
            messages: {
                username: {
                    required: "用户名不能为空。"
                },
                password: {
                    required: "密码不能为空."
                },
				verifycode:{
					required: "验证码不能为空。"
				}
            },
            invalidHandler: function(e, r) {
                $(".alert-danger", $(".login-form")).show()
            },
            highlight: function(e) {
                $(e).closest(".form-group").addClass("has-error")
            },
            success: function(e) {
                e.closest(".form-group").removeClass("has-error"),
                e.remove()
            },
            errorPlacement: function(e, r) {
                e.insertAfter(r.closest(".input-icon"))
            }
        }),
        $(".login-form input").keypress(function(e) {
			if(13 == e.which){
				var isOK = $(".login-form").validate().form();
				if(isOK){
					submitForm();
				}
			}
			
        }),
		$("#submitform").click(function(){
			var isOK = $(".login-form").validate().form();
				if(isOK){
					submitForm();
				}
		});
    },
	submitForm=function(){
    	var userName = $("#username").val();
    	var password = $("#password").val();
    	var verifycode = $("#verifycode").val();
    	var rememberChkBox = $("#remember");
    	
    	var remember = rememberChkBox[0].checked == true ? 1 : 0;
    	var formData = {"userName":userName,"password":password,"verifyCode":verifycode,"remember":remember};
    	$.ajax({
    		
    		type:"post",
    		url:"loginCheck.action",
    		dataType:"json",
    		contentType:"application/json",
    		data:JSON.stringify(formData),
    		success:function(result){
    			if(result.status != 0){
    				alert(result.message);
    				change_verify_code();
    			}
    			else{
    				alert("ok");
    			}
    		},
    		error:function(result){
    			console.log(result);
    		}
    		
    	});
    	
    	
	}
      , r = function() {
        $(".forget-form").validate({
            errorElement: "span",
            errorClass: "help-block",
            focusInvalid: !1,
            ignore: "",
            rules: {
                email: {
                    required: !0,
                    email: !0
                }
            },
            messages: {
                email: {
                    required: "请输入邮箱地址。"
                }
            },
            invalidHandler: function(e, r) {},
            highlight: function(e) {
                $(e).closest(".form-group").addClass("has-error")
            },
            success: function(e) {
                e.closest(".form-group").removeClass("has-error"),
                e.remove()
            },
            errorPlacement: function(e, r) {
                e.insertAfter(r.closest(".input-icon"))
            },
            submitHandler: function(e) {
                e.submit()
            }
        }),
        $(".forget-form input").keypress(function(e) {
            return 13 == e.which ? ($(".forget-form").validate().form() && $(".forget-form").submit(),
            !1) : void 0
        }),
        jQuery("#forget-password").click(function() {
            jQuery(".login-form").hide(),
            jQuery(".forget-form").show()
        }),
        jQuery("#back-btn").click(function() {
            jQuery(".login-form").show(),
            jQuery(".forget-form").hide()
        })
    }
      , s = function() {
        function e(e) {
            if (!e.id)
                return e.text;
            var r = $('<span><img src="../assets/global/img/flags/' + e.element.value.toLowerCase() + '.png" class="img-flag" /> ' + e.text + "</span>");
            return r
        }
        jQuery().select2 && $("#country_list").size() > 0 && ($("#country_list").select2({
            placeholder: '<i class="fa fa-map-marker"></i>&nbsp;Select a Country',
            templateResult: e,
            templateSelection: e,
            width: "auto",
            escapeMarkup: function(e) {
                return e
            }
        }),
        $("#country_list").change(function() {
            $(".register-form").validate().element($(this))
        })),
        $(".register-form").validate({
            errorElement: "span",
            errorClass: "help-block",
            focusInvalid: !1,
            ignore: "",
            rules: {
                fullname: {
                    required: !0
                },
                email: {
                    required: !0,
                    email: !0
                },
                address: {
                    required: !0
                },
                username: {
                    required: !0
                },
                password: {
                    required: !0
                },
                rpassword: {
                    equalTo: "#register_password"
                },
                //tnc:{
                //	required: !0
                //},

            },
            messages: {
                //tnc: {
                //    required: "清先同意相关条款 ."
               // }
            },
            invalidHandler: function(e, r) {},
            highlight: function(e) {
                $(e).closest(".form-group").addClass("has-error")
            },
            success: function(e) {
                e.closest(".form-group").removeClass("has-error"),
                e.remove()
            },
        }),
        $(".register-form input").keypress(function(e) {
            
            if(13 == e.which){
            	registUser();
            }
        }),
        jQuery("#register-btn").click(function() {
            jQuery(".login-form").hide(),
            jQuery(".register-form").show()
        }),
        jQuery("#register-back-btn").click(function() {
            jQuery(".login-form").show(),
            jQuery(".register-form").hide()
        }),
        $("#register-submit-btn").on("click", function(){
        	registUser();
        })
    };
    return {
        init: function() {
            e(),
            r(),
            s(),
            $.backstretch(["../assets/pages/media/bg/5.jpg"], {
                fade: 1e3,
                duration: 8e3
            })
        }
    }
}();
jQuery(document).ready(function() {
    Login.init()
});

$("#change_code").click(function(){
	
	change_verify_code();

});

function change_verify_code(){
	
		var img = $("#verify_code");
		
		img.attr("src", "getVerifyCode.action?i=" + Math.random());
		
		img.src = "getVerifyCode.action?i=" + Math.random();
};

$("#regist_username").blur(function(){
	var userName = $("#regist_username").val();
	
	var data = {"userName":userName};
	
	if(userName != ""){
		$.ajax({
			
			type:"post",
			url:"checkUserExist.action",
			dataType:"json",
			contentType:"application/json",
			data:JSON.stringify(data),
			success:function(result){
				if(result.status != 0){
					var f = $("#regist_username").closest(".form-group");
					f.addClass("has-error");
					$("#userNameExistError").html(result.message);
					$("#userNameExistError").show();
				}
			},
			error:function(result){
				alert("error");
			}
		});
	}
	
});

$("#regist_username").on("input",function(){
	$("#userNameExistError").hide();
});

function registUser(){
	 if($(".register-form").validate().form() && $("#userNameExistError").css("display") == "none"){
		 
		 var userName = $("#regist_username").val();
		 var password = $("#register_password").val();
		 
		 var data = {"userName":userName, "password":password};
		 
		 $.ajax({
			 
			 type:"post",
			 url:"registAcount.action",
			 dataType:"json",
			 contentType:"application/json",
			 data:JSON.stringify(data),
			 success:function(result){
				 
				 if(result.status != 0){
					 $(".regist-defeat").find("span").html(result.message);
					 $(".regist-defeat").fadeIn();
				 }
				 
				 $(".alert-success").find("span").html(result.message);
				 window.location.href= "login.action";
			 },
			 error:function(result){
				 $(".regist-defeat").find("span").html("error");
				 $(".regist-defeat").fadeIn();
			 }
		 });
		 
	 }
	
}