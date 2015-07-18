
$(function () {
	//初始化文件上传控件
	initUploadifive();
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
        "order": [[ 2, "asc" ]],
        "ajax": 'getPictureList.action',
        "deferRender": true,                     //延迟加载
        "columns": [
                    { "data": "picId" },
                    { "data": "picTitle" },
                    { "data": "picOrder" },
                    { "data": "picName" },
                ],
        "columnDefs": [
                   {
                     "targets": [ 0 ],
                     "visible": false,
                     "searchable": false
                   },
                ],
        "fnInitComplete": function (oSettings, json) {
            $('<div style="margin-bottom:8px">'+ '&nbsp;' +
            '<a href="#" id="add" class="btn btn-primary btn-sm" >新增</a> ' + '&nbsp;' +
            '<a href="#" id="edit" class="btn btn-primary btn-sm" >修改</a> ' + '&nbsp;' +
            '<a href="#" id="delete" class="btn btn-danger btn-sm" >删除</a>' + '&nbsp;</div>').appendTo($('.myBtn'));
            $("#add").click(function(){
            	$('#myModal').modal('show');
            	$("#myModalLabel").text("新增");
            });
            //修改按钮操作
            $("#edit").click(function(){
            	data1=table.row('.selected').data();
	        	 if(table.row('.selected').length==0)
	              	{
	             		alert("请选中需要修改的图片！");
	              	}
	              else
	              	{
	            	  $("#myModalLabel").text("修改");
	            	  $.ajax({
                          url: "getPicture.action",
                          data: {
                          	"picId":data1["picId"]
                          }, success: function (data) {
                          	 //先清空循环给uploadifive赋值
                        	  $("#uploadifive-file_upload-queue").html("");
                        	  $("#file_queue").html("");
                        	  $(data).each(function(i,val){
                        		  //添加删除事件
                        		  if(val.picName!=null)
                        			  {
		                        		  $("#uploadifive-file_upload-queue").append('<div class="uploadifive-queue-item complete" id="uploadifive-file_upload-file-'+(i+100)+'"><a href="#" onclick="removeQueue('+(i+100)+',\''+val.picName+'\')" class="close">X</a><div><span class="filename">'+val.picName+'</span><span class="fileinfo"> - 已完成</span></div></div>');
		                        		  $("#file_queue").append('<div data-path="'+val.picPath+'" data-name="'+val.picName+'" class="hidden"></div>');
                        			  }
                          	})
                          	//$("#uploadifive-file_upload-queue").html(queueHtml);	 
                          }
                      });
                	  $('#myModal').modal('show');
	              	  $("#myModal #picId").val(data1["picId"]);
	              	  $("#myModal #picTitle").val(data1["picTitle"]);
	              	  $("#myModal #picOrder").val(data1["picOrder"]); 	 
	              	}
            });
            
            //删除按钮操作
            $("#delete").click(function(){
            	if(table.row('.selected').length==0)
             	{
            		alert("请选中需要删除的图片！");
             	}
             else
             	{
            	 $('#deleteModal').modal('show');
             	}
            });
        },
        "fnRowCallback" : function(nRow, aData, iDisplayIndex) {
           
        }
    });
    //确认删除按钮
    $("#del").click(function(){
    	data=table.row('.selected').data();
    	$.ajax({
            url: "deletePicture.action",
            data: {
            	"picId":data["picId"]
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
		'fileType'     : 'image/*',   //允许上传文件类型
		'fileSizeLimit' : "10MB",		  //文件大小限制kb
		'multi'        : false,
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

function editForm(){
	var flag=$("#myModalLabel").text();
	var ajaxUrl="";
	if(flag=="新增")
		{
			ajaxUrl="insertPicture.action";
		}
	else if(flag=="修改")
		{
			ajaxUrl="editPicture.action";
		}
	
	var PictureList = new Array();
	$("#file_queue>").each(function(i,val){
		var data = new Object();
		//data.fileId="";
		data.picName=val.attributes["data-name"].value;
		data.picPath=val.attributes["data-path"].value;
		PictureList.push(data);
	});
	$.ajax({
        url: ajaxUrl,
        type:"post",
        data: {
        	"picId":$("#picId").val(),
			"picTitle":$("#picTitle").val(),
			"picOrder":$("#picOrder").val(),
			"picJson":JSON.stringify(PictureList)
        }, success: function (data) {
        	//清空所有
        	$("#picId").val("");
        	$("#picTitle").val("");
        	$("#picOrder").val("1");
        	$("#file_queue").html("");
        	$("#uploadifive-file_upload-queue").html("");
        	$("#myModal").modal("hide");
        	table.ajax.reload(); 
        }
	});
}

//每次关闭模态框时清除数据
$("#myModal").on("hide.bs.modal", function() {
	$("#myModal :text").each(function(){
        $(this).val("")
    })
    $("#picId").val("");
	$("#picTitle").val("");
	$("#picOrder").val("1");
	$("#file_queue").html("");
	$("#uploadifive-file_upload-queue").html("");
});

//删除已上传的队列
function removeQueue(id,fileName)
{
	$("#uploadifive-file_upload-file-"+id).remove();
	$("#file_queue>[data-name='"+fileName+"']").remove();
}