<!-- include some java object -->
<%@ page import="com.dianrong.common.uniauth.common.cons.AppConstants" %>
<%@ page import="com.dianrong.common.uniauth.cas.model.CasLoginCaptchaInfoModel"%>
<jsp:directive.include file="top.jsp" />

<div id="cookiesDisabled" class="errors" style="display:none;">
    <h2><spring:message code="screen.cookies.disabled.title" /></h2>
    <p><spring:message code="screen.cookies.disabled.message" /></p>
</div>

<div class="detail"  id="login">
    <form:form method="post" id="fm1" style="vertical-align:middle;margin:0 auto;" commandName="${commandName}" htmlEscape="true">
		<div class="logo">
			<img src="<%=path %>/images/image/logo.png"/>
		</div>
		<div class="error2" hidden="hidden">
			<img src="<%=path %>/images/image/false.png" />
		</div>
        <form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false" />
		<c:if test="${not empty param.dupsession}">
			<p><font color="red"><spring:message code="screen.tips.session.dup" /></font></p><br>
		</c:if>
		
		<!-- show login page title -->
		<c:if test="${empty edituserinfo}">
        	<%-- <h2><spring:message code="screen.welcome.instructions" /></h2> --%>
        </c:if>
        <c:if test="${not empty edituserinfo}">
        	<h2 class="text_decoration_none">
				<a href="login"><spring:message code="screen.password.reset.step.backtofirstpage" /></a>
				&gt;
				<spring:message code="screen.personal.info.edit.title" />
			</h2>
		</c:if>
		<!-- show login page title -->
			<c:choose>  
			   <c:when test="${not empty edituserinfo}">  
			   			<section class="row hiddenbtn"  id="login_domain_select">
			   </c:when>  
			   <c:otherwise> 
			   			<section class="row" id="login_domain_select">
			   </c:otherwise>  
			</c:choose>  
		 <section class="row" style="text-align:left;">	
	            <label for="domain"><spring:message code="screen.welcome.label.domain" /></label>
	            <spring:message code="screen.welcome.label.domain.accesskey" var="domainAccessKey" />
	            
		            <form:select id="domain" tabindex="0" accesskey="${domainAccessKey}" path="domain">
		            	<c:if test="${not empty domains}">
		            		<c:forEach items="${domains}" var="domain">
	  							<form:option value="${domain.zkDomainUrlEncoded}"  customLoginPage="${domain.isCustomizedLoginPage}">${domain.code}</form:option>
							</c:forEach>
		            	</c:if>
		            </form:select>
	           
   		 </section>
 		<c:if test="${not empty edituserinfo}">
  		   <section class="row height55">
            	<label class="notice-red padding-top20"><spring:message code="screen.personal.info.goto.edit.relogin.notice"/></label>
           </section>
	     </c:if>
   		 
        <section class="row">
            <label for="username" style="text-align:left;" ><spring:message code="screen.welcome.label.netid" /></label><br />
            <c:choose>
                <c:when test="${not empty sessionScope.openIdLocalId}">
                    <strong><c:out value="${sessionScope.openIdLocalId}" /></strong>
                    <input type="hidden" style="width:90%;height:25px;" id="username" name="username" value="<c:out value="${sessionScope.openIdLocalId}" />" />
                </c:when>
                <c:otherwise>
                    <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
                    <form:input cssClass="required" style="width:90%;height:25px;" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="off" htmlEscape="true" value="${sessionScope.pwdg_emailVal}"/>
                </c:otherwise>
            </c:choose>
        </section>
    
        <section class="row">
            <label for="password" style="text-align:left;"><spring:message code="screen.welcome.label.password" /></label>
            <span style="float: right;color: #4B85EA;margin-right:26px;">
			<a href="uniauth/forgetPassword?step=0" title="<spring:message code="screen.welcome.link.password.forget.title"/>" id="to_reset_pwd_btn"><spring:message code="screen.welcome.link.password.forget"/></a>
			</span><br />
                <%--
                NOTE: Certain browsers will offer the option of caching passwords for a user.  There is a non-standard attribute,
                "autocomplete" that when set to "off" will tell certain browsers not to prompt to cache credentials.  For more
                information, see the following web page:
                http://www.technofundo.com/tech/web/ie_autocomplete.html
                --%>
            <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
            <form:password cssClass="required" style="width:90%;height:25px;" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
            <span id="capslock-on" style="display:none;"><p><img src="<%=path %>/images/warning.png" valign="top"> <spring:message code="screen.capslock.on" /></p></span>
        </section>
        <section class="row">
			<label for="validCode" style="text-align:left;"><spring:message code="sms.content.text" /></label><br/>
			<input type="text"  tabindex="3" style="width: 50%;height:25px;"  id="validCode" name="validCode" />
			<input type="button" class="getCode " id="send-submit" style="width: 38%;height:30px;border-radius: 2px;background: #4b85ea;	color: #fff;border: none;"  value="<spring:message code="sms.send.button.content"/>" onclick = "send()"/>
		</section>

		<!-- captcha box -->
		<%
			Object casCaptchaObj = session.getAttribute(AppConstants.CAS_USER_LOGIN_CAPTCHA_VALIDATION_SESSION_KEY);
			if(casCaptchaObj != null){
				CasLoginCaptchaInfoModel  tcasCaptchaObj = (CasLoginCaptchaInfoModel)casCaptchaObj;
				if(!tcasCaptchaObj.canLoginWithoutCaptcha()){
					%>
						<section class="row">
				            <label for="captcha" style="text-align:left"><spring:message code="screen.welcome.label.captcha" /></label><br/>
						            <input type="text" style="width: 50%;height:25px;" tabindex="4" id="captcha" name="captcha">
						    		<img alt="picture" src="<%=path %>/uniauth/verification/captcha" title="<spring:message code="screen.init.password.step1.content.verifycode.title"/>"  id="cas_login_captcha_change_img" >
									 <a  href="javascript:void(0);" tabindex="5"  id="cas_login_captcha_change_a"><spring:message code="screen.welcome.button.captcha.change"/></a>
				        </section>
					<%
				}
			}
		%>
		
        <section class="row btn-row">
            <input type="hidden" name="lt" value="${loginTicket}" />
            <input type="hidden" name="execution" value="${flowExecutionKey}" />
            <input type="hidden" name="_eventId" value="submit" />

            <input class="btn-submit  enable-after-init-success button"  name="cas_submit"  disabled="disabled"  accesskey="l"  value="<spring:message code="screen.welcome.button.login" />"  tabindex="6"   id="btn_cas_submit"   type="button" />
            <%-- <input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="7" type="reset" /> --%>
            
            <%-- <c:if test="${empty edituserinfo}">
            		<div class="personal-info-link text_decoration_none" style="text-align:center;">
		            	
		            		<a href="cas/login?edituserinfo=go"  title="<spring:message code="screen.welcome.link.userinfo.goedit.title"/>" id="to_edit_userinfo_btn"><spring:message code="screen.welcome.link.userinfo.goedit"/></a>
		            	
			            	<a href="uniauth/forgetPassword?step=0" title="<spring:message code="screen.welcome.link.password.forget.title"/>" id="to_reset_pwd_btn"><spring:message code="screen.welcome.link.password.forget"/></a>
			           
		            </div>
        	</c:if> --%>
        </section>
		
    </form:form>
	
</div>
</div>



<script type="text/javascript" src="<%=path %>/js/loginpage.js?v=<%=version %>" ></script>
<script type="text/javascript" src="<%=path %>/js/pwdforget.js?v=<%=version %>" ></script>
<script type="text/javascript" src="<%=path %>/js/userinfoedit.js?v=<%=version %>" ></script>
<script type="text/javascript">
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
				$("#send-submit").css("color","gray");
				$("#send-submit").append(flag);
			}else if(code == 2){
				$("#send-submit").attr("disabled", true);
				$("#send-submit").css("color","gray");
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
        $("#send-submit").css("color", "white");
        obj.val(button_msg_0);
        countdown = 60; 
        return;
    } else { 
        obj.attr('disabled',true);
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
</script>

<div style="margin:0 auto;">
	<jsp:directive.include file="bottom.jsp" />
	