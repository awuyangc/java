$(document).ready(function () {
	 $('a.moreNews').click(function (e) {
	    	//不要执行a标签的默认跳转动作
	        e.preventDefault();
	    	var $clink = $(this);
	    	var typeName=$clink.attr('data-typename');
	    	$('#content').data("dataTypeName",typeName);
	        $.ajax({
	            url: $clink.attr('href'),
	            data:{"typeName":$clink.attr('data-typename')},
	            success: function (msg) {
	                //$('#content').html($(msg).find('#content').html());
	            	$('#content').hide();
	            	$('#content').html(msg);
	            	$('div.panel-heading').html(typeName);
	            	$('#content').fadeIn();     
	            }
	        });
	    });
	
});
