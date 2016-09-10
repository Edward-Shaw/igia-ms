$(function(){
	$("#btn_search").click(function(){
		window.location.href = "./search" + "?content=" + $("#input_search").val();
	});
	$("#btn_register").click(function(){
		window.location.href = "./register";
	});
	$("#btn_login").click(function(){
		window.location.href = "./login";
	});
});

