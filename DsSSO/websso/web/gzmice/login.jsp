<%@page language="java" pageEncoding="UTF-8"
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><c:set var="ctx" value="${pageContext.request.contextPath}"
/><!DOCTYPE html>
<html>
<head>
<title>登录</title><meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body{margin:0;padding:0;background-color:#ececec;font-family:"Segoe UI","Lucida Grande",Helvetica,Arial,"Microsoft YaHei",FreeSans,Arimo,"Droid Sans","wenquanyi micro hei","Hiragino Sans GB","Hiragino Sans GB W3",FontAwesome,sans-serif;}
.logo{background-color:#37bafe;}
.logo>div{padding:50px 0;}
.logo>div>a{display:block;width:150px;margin:0 auto;}
.logo>div>a>img{width:100%;}
.main{max-width:640px;margin:0 auto;}
.card{position:relative;top:-25px;width:80%;margin:0 auto;background-color:#fff;border-radius:10px;box-shadow:1px 5px 8px #D9CDB4;}
.card .content{padding:20px;}
.card .content .item{margin-bottom:20px;border-bottom:1px solid #ececec;}
.card .content .item img{width:25px;height:25px;}
.card .content .item input{height:25px;width:50%;margin-left:10px;font-size:20px;border:0;}
.card .content .item input:focus{outline:none;}
.card .content .item .msg{color:#f00;display:none;}
.card .content .button{display:block;border:0;padding:0.2em 1em 0.3em 1em;margin:0 auto;line-height:1.5em;cursor:pointer;background-color:#2996ff;color:#fff;border-radius:3px;}
.card .content .button{width:80%;height:40px;font-size:20px;}
.card .content .info{text-align:center;color:#777;}
</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jskey_des.js"></script>
<script type="text/javascript" src="js/validator.js"></script>
<script type="text/javascript">
$(function(){
	$('#authcode').click();
	$('form').submit(function(){
		if($(this).validate()){
			var $pwd = $(this).find('input[name="password"]');
			$pwd.val($jskey.encodeDes($pwd.val(), 'dswork'));
			$(this).ajaxSubmit({success:function(result){
				var ss = result.split(':');
				if(ss[0] != 0){
					location.href = '${param.url!=null?param.url:"index.jsp"}';
					return;
				}
				alert(ss[1]);
				location.reload();
			}});
		}
		return false;
	});
});
</script>
</head>
<body style="background-color:#fff;">
<div class="logo">
	<div><a><img src="image/logo.png"></a></div>
</div>
<div class="main">
	<div class="card">
		<div class="content">
		<form action="loginAction.jsp" method="post">
			<div class="item"><img src="image/icon_account.png"><input name="account" type="text" value="" data-type="char" data-show="msg1" placeholder="账号"><div class="msg" id="msg1">账号必须为字母或数字</div></div>
			<div class="item"><img src="image/icon_password.png"><input name="password" type="password" id="password" value="" data-type="char" data-show="msg2" placeholder="密码"><div class="msg" id="msg2">密码必须为字母或数字</div></div>
			<div class="item"><img src="image/icon_authcode.png"><input name="authcode" type="text" value="" data-type="require" data-show="msg3" placeholder="验证码" style="width:40%;"><img id="authcode" alt="请点击" style="vertical-align:middle;width:40%;height:30px;" src="about:blank" onclick="this.src='${ctx}/authcode?r=' + Math.random();" /><div class="msg" id="msg3">验证码不能为空</div></div>
			<div><input type="submit" class="button" value="登录"></div>
			<div class="info">没有账号？<a href="register.jsp">注册</a></div>
		</form>
		</div>
	</div>
</div>
</body>
</html>