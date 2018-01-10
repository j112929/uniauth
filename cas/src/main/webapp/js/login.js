function send(){
	var username = $('#username').val();
	var tenancy_code = $('#domain option:selected').text();
	var flag = '<input type="text" hidden="hidden" value="sent" id="sent">';
	var msg = $.i18n.prop('send.sms.notice');
	var username_msg = $.i18n.prop('username.required');
	var is_have = $('#msg').val();
	if(is_have != undefined || is_have == ""){
		$('#msg').remove();
		$(".error2").attr("hidden","hidden");
	}
	if(username == ""){
		$(".error2").removeAttr("hidden");
		$(".error2").append('<p id="msg">'+username_msg+'</p>');
		return;
	}
	var msg0 = $.i18n.prop('validCode.send.error');
	var msg3 = $.i18n.prop('validCode.check.error');
	var msg4 = $.i18n.prop('validCode.query.user.error');
	var msg5 = $.i18n.prop('validCode.user.not.found');
	var msg6 = $.i18n.prop('validCode.put.session.error');
	$.ajax({
		type:'POST',
		url:"/cas/v1/msg/send",
		data:{
			email:username,
			tenancyCode:tenancy_code
		},
		success: function(data){
			var code = data.data.code;
			
			if(code == 1){
				$("#send-submit").attr("disabled", true);
				$("#send-submit").append(flag);
			}else if(code == 2){
				$("#send-submit").attr("disabled", true);
				$("#send-submit").append(flag);
				alert(msg);
			}else{
				$(".error2").removeAttr("hidden");
				if(code == 0){
					$(".error2").append('<p id="msg">'+msg0+'</p>');
				}else if(code == 3){
					$(".error2").append('<p id="msg">'+msg3+'</p>');
				}else if(code == 4){
					$(".error2").append('<p id="msg">'+msg4+'</p>');
				}else if(code == 5){
					$(".error2").append('<p id="msg">'+msg5+'</p>');
				}else if(code == 6){
					$(".error2").append('<p id="msg">'+msg6+'</p>');
				}
			}
		},
		async:false
	});
	//setTimeout("call()", 60000);
	var obj = $('#send-submit');
	settime(obj);
}
function call(){
	$("#send-submit").attr("disabled", false);
	$("#send-submit").removeAttr("color");
	$("#send-submit").css("background","#4b85ea");
}
var countdown=60;
function settime(obj) { //发送验证码倒计时
	var button_msg_0 = $.i18n.prop('button.content');
	var button_msg_1 = $.i18n.prop('button.wait.content');
	 
    if (countdown == 0) { 
        obj.attr('disabled',false); 
        //obj.removeattr("disabled"); 
        obj.css("color", "black");
        obj.val(button_msg_0);
        countdown = 60; 
        return;
    } else { 
        obj.attr('disabled',true);
        obj.css("color", "black");
        obj.val("(" + countdown + ")"+button_msg_1);
        countdown--; 
    } 
    
setTimeout(function() { 
    settime(obj);
    }, 1000); 
}
$(".error2").click(function(){
  $('#msg').remove();
  $(".error2").attr("hidden","hidden");
});
$(".errors").click(function(){
  $('#msg').remove();
});	