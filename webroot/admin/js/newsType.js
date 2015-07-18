/*http://datatables.net/extensions/fixedheader/ 固定表头
http://datatables.net/extensions/autofill/ 像excel一样的自动填充
http://datatables.net/extensions/colreorder/ 表头拖动
http://datatables.net/extensions/colvis/ 列显示与隐藏
http://datatables.net/extensions/fixedcolumns/ 固定某一列
http://datatables.net/extensions/keytable/ 给表格绑定
http://datatables.net/extensions/responsive/ 响应式
http://datatables.net/extensions/scroller/ 瀑布流式翻页
http://datatables.net/extensions/tabletools/ 简单工具复制数据 导出excel 打印*/
$(function () {
	//datatable
    var table=$('#myTable').DataTable({
        "sDom": "<'row'<'col-md-6'<'myBtn' and T>><'col-md-6'f>>Rtr<'row'<'col-md-6'l><'col-md-6 right-text'i><'col-md-6 center-block'p>>",
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
        "order": [[ 1, "asc" ]],
        "ajax": 'getNewsType.action',
        "deferRender": true,                     //延迟加载
        "columns": [
                    { "data": "typeId" },
                    { "data": "typeNo" },
                    { "data": "typeName" },
                    { "data": "enable" },
                ],
        "columnDefs": [
                   {
                     "targets": [ 0 ],
                     "visible": false,
                     "searchable": false
                   }
                ],
        "fnInitComplete": function (oSettings, json) {
            $('<div style="margin-bottom:8px"><a href="#myModal" id="add" class="btn btn-primary btn-sm">新增</a>' + '&nbsp;' +
            '<a href="#" class="btn btn-primary btn-sm" id="edit">修改</a> ' + '&nbsp;' +
            '<a href="#" class="btn btn-danger btn-sm" id="delete">删除</a>' + '&nbsp;</div>').appendTo($('.myBtn'));
            var data=[];
            $("#add").click(function(){
            	$('#myModal').modal('show');
            	$("#myModal input[name='isenable'][value=1]").prop("checked", true);
            	$("#myModalLabel").text("新增");
            });
            $("#edit").click(function(){
            	 if(table.row('.selected').length==0)
             	{
            		alert("请选中需要修改的分类！");
             	}
             else
             	{
            	  $('#myModal').modal('show');
            	  $("#myModalLabel").text("修改");
             	  data=table.row('.selected').data();
             	  $("#myModal #type_id").val(data["typeId"]);
                  $("#myModal #type_no").val(data["typeNo"]);
             	  $("#myModal #type_name").val(data["typeName"]);
             	  //$("#myModal input[name='isenable']").attr("checked",false);
             	  $("#myModal input[name='isenable'][value="+data["enable"]+"]").prop("checked", true);
             	}
            });
             
            $("#delete").click(function(){
            	if(table.row('.selected').length==0)
             	{
            		alert("请选中需要删除的分类！");
             	}
             else
             	{
            	 $('#deleteModal').modal('show');
             	}
            });
        },
        "fnRowCallback" : function(nRow, aData, iDisplayIndex) {
            if (aData.enable == 0)  
                $('td:eq(2)', nRow).html('<span class="label-warning label label-default">不可用</span>');  
            if (aData.enable == 1)  
                $('td:eq(2)', nRow).html('<span class="label-success label label-default">可用</span>');
            return nRow;  
        }
    });
    //每次关闭模态框时清除数据
    $("#myModal").on("hide.bs.modal", function() {
    	$("#myModal :text").each(function(){
            $(this).val("")
        })
        $("#myModal input[name='isenable']").removeAttr("checked");
    });
    //保存按钮操作
    $("#save").click(function(){
    	var flag=$("#myModalLabel").text();
    	var ajaxUrl="";
    	if(flag=="新增")
    		{
    			ajaxUrl="addNewsType.action";
    		}
    	else if(flag=="修改")
    		{
    			ajaxUrl="editNewsType.action";
    		}
		$.ajax({
            url: ajaxUrl,
            data: {
            	"typeId": $("#type_id").val(),
            	"typeNo": $("#type_no").val(),
                "typeName": $("#type_name").val(),
                "enable": $("#myModal input:radio[name='isenable']:checked").val()
            }, success: function (data) {
            	$("#myModal").modal("hide");
            	table.ajax.reload(); 
            }
        });
    });
    //确认删除按钮操作
    $("#del").click(function(){
    	data=table.row('.selected').data();
    	$.ajax({
            url: "deleteNewsType.action",
            data: {
            	"typeId":data["typeId"]
            }, success: function (data) {
            	$("#deleteModal").modal("hide");
            	table.ajax.reload(); 
            }
        });
    	
    });
});