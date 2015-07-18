$(function(){
	
    var options = {
        bootstrapMajorVersion:3,
        currentPage: 1,
        numberOfPages: 10,
        itemTexts: function(type, page, current) { //修改显示文字
            switch (type) {
            case "first":
                return "首页";
            case "prev":
                return "上一页";
            case "next":
                return "下一页";
            case "last":
                return "尾页";
            case "page":
                return page;
            }
        },
        shouldShowPage:function(type, page, current){
            switch(type)
            {
                case "first":return true;
                case "last":return true;
                default:return true;
            }
        },
        onPageClicked: function (event, originalEvent, type, page) { //换页
        	options.currentPage=page;
        	var startIndex=(options.currentPage-1)*options.numberOfPages;
            var endIndex=options.currentPage*options.numberOfPages;
        	$.ajax({
                url: "getMoreNews.action",
                data:{"startIndex":startIndex,"endIndex":endIndex,"typeName":$('#content').data("dataTypeName"),"searchText":$('#content').data("dataSearch")},
                success: function (data) {
                	var ulList="";
                	
                	$(data.data).each(function(i,val){
                		ulList +="<li><a class='liList' href='#'>"+val.newsTitle+"</a>&nbsp;&nbsp;<font>("+val.createTime+")</font></li>";
                	});
                	$("#ulList").html(ulList);
                }
            });
        }
    }
	//获得数据
    var startIndex=(options.currentPage-1)*options.numberOfPages;
    var endIndex=options.currentPage*options.numberOfPages;
	  $.ajax({
        url: "getMoreNews.action",
        data:{"startIndex":startIndex,"endIndex":endIndex,"typeName":$('#content').data("dataTypeName"),"searchText":$('#content').data("dataSearch")},
        success: function (data) {
        	var ulList="";
        	
        	$(data.data).each(function(i,val){
        		ulList +="<li><a class='liList' data-id='"+val.newsId+"' href='#'>"+val.newsTitle+"</a>&nbsp;&nbsp;<font>("+val.createTime+")</font></li>";
        	});
        	$("#ulList").html(ulList);
        	//监听li点击事件
        	$('a.liList').click(function (e) {
        		//获得当前的新闻id
        		e.preventDefault();
        		var newsId=$(this).attr('data-id');
        		$.ajax({
        	        url: "getNews.action",
        	        data:{"newsId":newsId},
        	        success: function (data) {
        	        	var newsFileList="";
        	        	$("#myModalLabel").html(data.newsTitle);
        	        	$("#newsContent").html(data.newsContent);
        	        	$.ajax({
                            url: "admin/getNewsFile.action",
                            data: {"newsId":newsId}, 
                            success: function (data) {
                              //先清空循环给uploadifive赋值
                          	  $(data.data).each(function(i,val){
                          		newsFileList +='<li><a href="download.action?fileId='+val.fileId+'">'+val.fileName+'</a></li>';
                          	  })
                          	  $("#newsFile").html(newsFileList);
                          	  $("#myModal").modal("show");
                            }
                        });
                		
        	        }
        		});
        		
        	});
        	//获得总数
        	$.ajax({
                url: "getMoreNewsTotal.action",
                data:{"typeName":$('#content').data("dataTypeName"),"searchText":$('#content').data("dataSearch")},
                success: function (data) {
                	options.totalPages=(data+options.numberOfPages-1)/options.numberOfPages;
                	$('#example').bootstrapPaginator(options);
                }
            });
        }
    });
	
});
