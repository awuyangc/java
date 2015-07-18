var table;
$(function () {
	//初始化类型的下拉菜单
	initTypeSelect();
	//初始化文件上传控件
	initUploadifive();
	//初始化富文本编辑器
	initToolbarBootstrapBindings();
	//保存按钮操作
	$("#btnSave").click(function (){
		editForm();
	})
	//datatable
	table=$('#myTable').DataTable({
        "sDom": "<'row'<'col-md-8'<'myBtn' and T>><'col-md-4'f>>Rtr<'row'<'col-md-6'l><'col-md-6 right-text'i><'col-md-6 center-block'p>>",
        "sPaginationType": "bootstrap",
        "processing":true,
        "oLanguage": {
        	"sProcessing" : "正在加载中......",
            "sLengthMenu" : "每页显示 _MENU_ 条记录",
            "sZeroRecords" : "",
            "sEmptyTable" : "表中无数据存在！",
            "sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoEmpty" : "显示0到0条记录",
            "sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
            "sSearch": "查找：",
            "oPaginate" : {
                "sFirst" : "首页",
                "sPrevious" : "上一页",
                "sNext" : "下一页",
                "sLast" : "末页"
            }
        },
        "tableTools": {
        	"sSwfPath": "bower_components/datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
        	"sRowSelect": "single"					//单选
        		},
        "bServerSide": false,                    //指定从服务器端获取数据    
        "bStateSave": false,
        "order": [[ 4, "desc" ],[5,"desc"]],
        "ajax": 'getNewsList.action',
        "deferRender": true,                     //延迟加载
        "columns": [
                    { "data": "newsId" },
                    { "data": "newsTitle" },
                    { "data": "typeId" },
                    { "data": "newsType.typeName" },
                    { "data": "publish" },
                    { "data": "istop" },
                    { "data": "createTime" }
                ],
        "columnDefs": [
                   {
                     "targets": [ 0 ],
                     "visible": false,
                     "searchable": false
                   },
                   {
                       "targets": [ 2 ],
                       "visible": false,
                       "searchable": false
                     }
                ],
        "fnInitComplete": function (oSettings, json) {
            $('<div style="margin-bottom:8px"><a href="#" id="publish" class="btn btn-primary btn-sm">发布</a>' + '&nbsp;' +
            '<a href="#" id="unPublish" class="btn btn-primary btn-sm">取消发布</a>' + '&nbsp;' +
            '<a href="#" id="top" class="btn btn-primary btn-sm">置顶</a>' + '&nbsp;' +
            '<a href="#" id="unTop" class="btn btn-primary btn-sm">取消置顶</a>' + '&nbsp;' +
            '<a href="#" id="edit" class="btn btn-primary btn-sm" >修改</a> ' + '&nbsp;' +
            '<a href="#" id="delete" class="btn btn-danger btn-sm" >删除</a>' + '&nbsp;</div>').appendTo($('.myBtn'));
            
            //发布
            $("#publish").click(function(){
            	data=table.row('.selected').data();
            	$.ajax({
                    url: "publishNews.action",
                    data: {
                    	"newsId":data["newsId"],
                    	"publish":1
                    }, success: function (data) {
                    	table.ajax.reload(); 
                    }
                });
            });
            
            //取消发布
            $("#unPublish").click(function(){
            	data=table.row('.selected').data();
            	$.ajax({
                    url: "publishNews.action",
                    data: {
                    	"newsId":data["newsId"],
                    	"publish":0
                    }, success: function (data) {
                    	table.ajax.reload(); 
                    }
                });
            });
            
            //置顶
            $("#top").click(function(){
            	data=table.row('.selected').data();
            	$.ajax({
                    url: "topNews.action",
                    data: {
                    	"newsId":data["newsId"],
                    	"istop":1
                    }, success: function (data) {
                    	table.ajax.reload(); 
                    }
                });
            });
            //取消置顶
            $("#unTop").click(function(){
            	data=table.row('.selected').data();
            	$.ajax({
                    url: "topNews.action",
                    data: {
                    	"newsId":data["newsId"],
                    	"istop":0
                    }, success: function (data) {
                    	table.ajax.reload(); 
                    }
                });
            });
            
            //修改按钮操作
            $("#edit").click(function(){
            	data1=table.row('.selected').data();
	        	 if(table.row('.selected').length==0)
	              	{
	             		alert("请选中需要修改的新闻！");
	              	}
	              else
	              	{
	            	  $.ajax({
                          url: "getNewsFile.action",
                          data: {
                          	"newsId":data1["newsId"]
                          }, success: function (data) {
                          	 //先清空循环给uploadifive赋值
                        	  $("#uploadifive-file_upload-queue").html("");
                        	  $("#file_queue").html("");
                        	  $(data.data).each(function(i,val){
                        		  //添加删除事件
                        		  $("#uploadifive-file_upload-queue").append('<div class="uploadifive-queue-item complete" id="uploadifive-file_upload-file-'+(i+100)+'"><a href="#" onclick="removeQueue('+(i+100)+',\''+val.fileName+'\')" class="close">X</a><div><span class="filename">'+val.fileName+'</span><span class="fileinfo"> - 已完成</span></div></div>');
                        		  $("#file_queue").append('<div data-path="'+val.filePath+'" data-name="'+val.fileName+'" class="hidden"></div>');
                          	});
                        	  $.ajax({
                                  url: "getNews.action",
                                  data: {
                                  	"newsId":data1["newsId"]
                                  }, success: function (data) {
                                  	 
                                	  $('#myModal').modal('show');
                	              	  $("#myModal #newsId").val(data["newsId"]);
                	              	  $("#myModal #newsTitle").val(data["newsTitle"]);
                	              	  $("#myModal #typeId").val(data["typeId"]);
                	              	  $("#myModal #editor").html(data["newsContent"]); 
                                  }
                              });
                          }
                      });
	              	}
            });
            
            //删除按钮操作
            $("#delete").click(function(){
            	if(table.row('.selected').length==0)
             	{
            		alert("请选中需要删除的新闻！");
             	}
             else
             	{
            	 $('#deleteModal').modal('show');
             	}
            });
        },
        "fnRowCallback" : function(nRow, aData, iDisplayIndex) {
            if (aData.publish == 0)  
                $('td:eq(2)', nRow).html('<span class="label-default label label-danger">未发布</span>');  
            if (aData.publish == 1)  
                $('td:eq(2)', nRow).html('<span class="label-success label label-default">已发布</span>');
            if (aData.istop == 0)  
                $('td:eq(3)', nRow).html('<span class="label-warning label label-default">未置顶</span>');  
            if (aData.istop == 1)  
                $('td:eq(3)', nRow).html('<span class="label-success label label-default">已置顶</span>');
            return nRow;  
        }
    });
    
    //行点击事件
    $('#myTable tbody').on('click', 'tr', function () {
        var namePublish = $('td', this).eq(2).text();
        if(namePublish=="已发布")
    	{
    		$("#publish").attr("disabled", true);
    		$("#unPublish").attr("disabled", false);
    	}
        else if(namePublish=="未发布")
    	{
        	$("#publish").attr("disabled", false);
    		$("#unPublish").attr("disabled", true);
    	}
        var nameTop = $('td', this).eq(3).text();
        if(nameTop=="已置顶")
    	{
    		$("#top").attr("disabled", true);
    		$("#unTop").attr("disabled", false);
    	}
        else if(nameTop=="未置顶")
    	{
        	$("#top").attr("disabled", false);
    		$("#unTop").attr("disabled", true);
    	}
    } );
    
    //确认删除按钮
    $("#del").click(function(){
    	data=table.row('.selected').data();
    	$.ajax({
            url: "deleteNews.action",
            data: {
            	"newsId":data["newsId"],
            	"isdelete":1
            }, success: function (data) {
            	$("#deleteModal").modal("hide");
            	table.ajax.reload(); 
            }
        });
    });
});

function initUploadifive()
{
	$('#file_upload').uploadifive({
		'uploadScript' : 'fileUpload.action', //处理上传文件Action路径
		'fileObjName' : 'file',        //文件对象
		'buttonText'   : '选择附件',   //按钮显示文字
		'fileType'     : 'false',   //允许上传文件类型
		'fileSizeLimit' : "50MB",		  //文件大小限制kb
		'onUploadComplete' : function(file, data) { //文件上传成功后执行
			//将文件名file.name和文件地址data记录下来，在保存时保存到数据库中
			$("#file_queue").html();
			$("#file_queue").append('<div class="hidden" data-name="'+file.name+'" data-path="'+data+'">');
		},
		'onDrop'       : function(file, fileDropCount) {
            alert(fileDropCount + ' files were dropped onto the queue.');
        },
        'onCancel'     : function(file) { //点击X按钮触发
            //删除file_queue队列中的值，不删除文件，因为目录是”年月日时分“，如果同一分钟内一个文件被多个新闻引用，可能会导致文件丢失，
        	$("#file_queue>[data-name='"+file.name+"']").remove();
        },
        'onClearQueue' : function(queue) {
        	alert("ClearQueue")
            queue.css('border', '2px solid #F00');
        },
        'onDestroy'    : function() {
            alert('Oh noes!  you destroyed UploadiFive!');
        }
    });
}

function initToolbarBootstrapBindings() {
    var fonts = ['Arial','Arial Black','Courier',
          'Courier New', 'Times New Roman','宋体','新宋体','仿宋','楷体','微软雅黑', '华文行楷'],
          fontTarget = $('[title="字体"]').siblings('.dropdown-menu');
    $.each(fonts, function (idx, fontName) {
        fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
    });
    $('a[title]').tooltip({container:'body'});
  	$('.dropdown-menu input').click(function() {return false;})
		    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
      .keydown('esc', function () {this.value='';$(this).change();});

    //$('[data-role=magic-overlay]').each(function () { 
    //  var overlay = $(this), target = $(overlay.data('target')); 
    //  overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
    //});
    //$('#voiceBtn').hide(); 
    $('#editor').wysiwyg();
    window.prettyPrint && prettyPrint();
  };
 
function initTypeSelect()
{
	$.ajax({
        url: "getNewsTypeEnable.action",
        success: function (data) {
        	var dataObj = eval(data);
        	//alert(dataObj.length);
        	var TypeSelect=$("#typeId");
        	//先清空下拉选项
        	TypeSelect.empty();
        	//循环添加下拉列表
        	$(data.data).each(function(i,val){
        		TypeSelect.append("<option value='"+val.typeId+"'>"+val.typeName+"</option>");
        	})
        }
    });
}

function editForm(){
	
	var newsFileList = new Array();
	$("#file_queue>").each(function(i,val){
		var data = new Object();
		//data.fileId="";
		data.fileName=val.attributes["data-name"].value;
		data.filePath=val.attributes["data-path"].value;
		newsFileList.push(data);
	});
	$.ajax({
        url: "doEditNews.action",
        type:"post",
        data: {
        	"newsId":$("#newsId").val(),
			"newsTitle":$("#newsTitle").val(),
			"typeId":$("#typeId").val(),
			"newsContent":$("#editor").html(),
			"newsFileJson":JSON.stringify(newsFileList)
        }, success: function (data) {
        	alert("操作成功！");
        	//清空所有
        	$("#newsTitle").val("");
        	$("#typeId").val("");
        	$("#editor").html("");
        	$("#file_queue").html("");
        	$("#uploadifive-file_upload-queue").html("");
        	$("#myModal").modal("hide");
        	table.ajax.reload(); 
        }
	});	
}

//删除已上传的队列
function removeQueue(id,fileName)
{
	$("#uploadifive-file_upload-file-"+id).remove();
	$("#file_queue>[data-name='"+fileName+"']").remove();
}