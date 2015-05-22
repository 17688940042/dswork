<%@page language="java" pageEncoding="UTF-8"%>
<div class="foucs">
	<ul class="img">
		<li href="http://browser.qq.com/">
			<img alt="图片" src="${ctx}/themes/web/header.jpg"/><div></div><span>本网站</span>
		</li>
		<li href="http://www.zhihu.com/">
			<img alt="图片" src="http://static.zhihu.com/static/img/201304_sign/logo.png"/><div></div><span>与世界分享你的知识、经验和见解</span>
		</li>
		<li href="http://email.163.com/">
			<img alt="图片" src="http://mimg.127.net/index/email/img/2012/ntes_logo.png"/><div></div><span>网易邮箱</span>
		</li>
		<li href="https://www.baidu.com/">
			<img alt="图片" src="https://www.baidu.com/img/bdlogo.png"/><div></div><span>百度一下，你就知道</span>
		</li>
	</ul>
	<ul class="menu">
		<li><img alt="图片" src="${ctx}/themes/web/header.jpg"/></li>
		<li><img alt="图片" src="http://static.zhihu.com/static/img/201304_sign/logo.png"/></li>
		<li><img alt="图片" src="http://mimg.127.net/index/email/img/2012/ntes_logo.png"/></li>
		<li><img alt="图片" src="https://www.baidu.com/img/bdlogo.png"/></li>
	</ul>
</div>
<script type="text/javascript">
(function(){
	var oFocus = $('.foucs');
	var oList = oFocus.find('.img li');
	var oMenu = oFocus.find('.menu li');
	var index = 0, time =false;
	oMenu.eq(0).addClass('on');
	oFocus.hover(function(){
		if(time){clearInterval(time);}
	},function(){
		paly();
	});
	oMenu.click(function(){
		index = oMenu.index(this);
		play();return false;
	});
	oList.click(function(){
		window.open($(this).attr("href"));
		return false;
	});
	function play(){
		if(index>=oList.length) index = 0;
		oList.stop(true).eq(index).fadeIn(300).siblings().hide();
		oMenu.eq(index).addClass('on').siblings().removeClass('on');
	};
	function paly(){
		time = setInterval(function(){index ++;play();},3000)
	}
	paly();
})();
</script>