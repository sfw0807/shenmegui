$(function(){
     function listData(result){
        var data = { Rows : result};
        $("#maingrid").ligerGrid({
                columns: [
                { display: '服务大类', name: 'firstCategory', align: 'center' },
                { display: '服务子类', name: 'secondCategory', align: 'center'},
                { display: '服务数量', name: 'serviceCount', align: 'center' },
                { display: '操作数量', name: 'operationCount', align: 'center'},
                { display: '服务总计', name: 'serviceTotal', align: 'center'},
                { display: '操作总计', name: 'operationTotal', align: 'center'}
                ],   
                isScroll: false,  data: data, sortName: 'firstCategory', usePager: false, width:1200
         }); 
         var tb = document.getElementById("gridId");
         var rows = tb.rows;
         // 克隆节点
         var cloneNode = rows[0].cloneNode(true);
         var index = 0;
         // console.log(rows[0].cells[0].innerText) 计算总数
         var serviceTotal =  parseInt(rows[0].cells[2].innerText);
         var operationTotal = parseInt(rows[0].cells[3].innerText);
         for(var i = 1;i<rows.length; i++){
                serviceTotal += parseInt(rows[i].cells[2].innerText);
                operationTotal += parseInt(rows[i].cells[3].innerText);
                if(rows[index].cells[0].innerText == rows[i].cells[0].innerText){
	                rows[i].removeChild(rows[i].cells[0]);
	                rows[i].removeChild(rows[i].lastChild.previousSibling);
	                rows[i].removeChild(rows[i].lastChild);
	                rows[index].cells[0].rowSpan = (rows[index].cells[0].rowSpan | 0) + 1;
	                rows[index].cells[4].rowSpan = (rows[index].cells[4].rowSpan | 0) + 1;
	                rows[index].cells[5].rowSpan = (rows[index].cells[5].rowSpan | 0) + 1;
                }
                else{
                index = i;
                }
         }
         tb.lastChild.appendChild(cloneNode);
         // 最后一行做处理
         var lastRow = tb.rows[tb.rows.length - 1];
         lastRow.removeChild(lastRow.childNodes[1]);
         lastRow.removeChild(lastRow.childNodes[2]);
         lastRow.removeChild(lastRow.childNodes[3]);
         lastRow.cells[0].colSpan = 4;
         // last row set value
         var row = tb.rows[tb.rows.length - 1];
         row.childNodes[0].innerText = '总计';
         row.childNodes[1].innerText = serviceTotal;
         row.childNodes[2].innerText = operationTotal;
    }
    $.ajax({
         url: "../serviceCategory/total",
         type: "GET",
     		dataType: "json",
     		success: function(result) {
     		     listData(result);
     		}
    });
});