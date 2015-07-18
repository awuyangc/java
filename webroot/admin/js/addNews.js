
$(function () {
	//初始化类型的下拉菜单
	initTypeSelect();
	//初始化文件上传控件
	initUploadifive();
	//初始化富文本编辑器
	initToolbarBootstrapBindings();
	//保存按钮操作
	$("#btnPublish").click(function (){
		doForm(1);
	})
	$("#btnSave").click(function (){
		doForm(0);
	})
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

    $('[data-role=magic-overlay]').each(function () { 
      var overlay = $(this), target = $(overlay.data('target')); 
      overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
    });
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

function doForm(flag){
	
	var newsFileList = new Array();
	$("#file_queue>").each(function(i,val){
		var data = new Object();
		//data.fileId="";
		data.fileName=val.attributes["data-name"].value;
		data.filePath=val.attributes["data-path"].value;
		newsFileList.push(data);
	});
	$.ajax({
        url: "doNews.action",
        type:"post",
        data: {
			"newsTitle":$("#newsTitle").val(),
			"typeId":$("#typeId").val(),
			"newsContent":$("#editor").html(),
			"publish":flag,
			"newsFileJson":JSON.stringify(newsFileList)
        }, success: function (data) {
        	alert("操作成功！");
        	//清空所有
        	$("#newsTitle").val("");
        	$("#typeId").val("");
        	$("#editor").html("");
        	$("#file_queue").html("");
        	$("#uploadifive-file_upload-queue").html("");
        }
	});
	
}