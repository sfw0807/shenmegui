var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
var param;
var serviceId;
var operationId;

var href = window.location.href;
var params = href.split("&");
serviceId = params[0].split("=")[1];
operationId = params[1].split("=")[1];

var TreeSDAData = {Rows: []};
function alert(message) {
    $.ligerDialog.alert(message.toString(), '提示信息');
}
function tip(message) {
    $.ligerDialog.tip({title: '提示信息', content: message.toString()});
}
var manager;
$(function () {
    var setManagerCallBack = function setManagerCallBack (gridManager){
        manager = gridManager;
    };
    sdaManager.getSDAInfoByOperationId(operationId, serviceId, setManagerCallBack);
    $("#randomUUID").click(function () {
        var operationManager = {
            randomUUID: function () {
                $.ajax({
                    url: '../operationInfo/randomUUID',
                    type: 'GET',
                    success: function (result) {
                        $("#sdaid").val(result.value);
                    }
                });
            }
        }
        operationManager.randomUUID();
    });
    //manager = sdaManager.getManager();
});

function bt_choice()
{
	window.open("../jsp/metadataManagerChoice.jsp","","toolbar=no,location=no,titlebar=no,resizable=yes,scrollbars=yes,top=0,left=0");
}

function getNewLinkValue(value){
	//获取子页面的值
	document.getElementById("metadataId").value=value;
}

function refreshSeq() {
    var table = document.getElementById("gridId");
    for (var i = table.rows.length - 1; i >= 0; i--) {
        if (table.rows[i].cells[0].innerText == "") {
            for (var j = i; j < table.rows.length; j++) {
                table.rows[j].cells[0].innerHTML = '<div class="l-grid-row-cell-inner" style="width:42px;height:28px;min-height:28px; text-align:left;">' + (j + 1) + '</div>';
            }
        }
    }
    for (var i = table.rows.length - 1; i >= 0; i--) {
        table.rows[i].cells[0].innerHTML = '<div class="l-grid-row-cell-inner" style="width:42px;height:28px;min-height:28px; text-align:left;">' + (i + 1) + '</div>';
    }
}
function deleteRow() {
    var row = manager.getSelectedRow();
    var structId = row["structId"];
    if (structId == operationId || structId == "request" || structId == "response" || structId == "SvcBody") {
        alert("该节点不能删除");
        return;
    }
    manager.deleteRow(row);
    refreshSeq();
}
var i = -1;
function getNewData(pid, withchildren) {
    i++;
    var id = $("#sdaid").val();
    var structId = $("#structId").val();
    var metadataId = $("#metadataId").val();
    var type = $("#type").val();
    var required = $("#required").val();
    var remark = $("#remark").val();
    var data = {
        id: id,
        pid: pid,
        structId: structId,
        metadataId: metadataId,
        remark: remark,
        type: type,
        required: required
    };
    if (withchildren) {
        data.children = [];
        data.children.push(getNewData());
        data.children.push(getNewData());
        data.children.push(getNewData());
    }
    return data;
}
//parent:是否增加到当前节点下面
function addRow(withchildren) {
    var selectRow = manager.getSelectedRow();
    var pid = selectRow["id"];
    var data = getNewData(pid, withchildren);
    if (data.id == "" || data.structId == "" || data.metadataId == "" || type == "" || pid == "") {
        alert("节点数据不完整");
        return;
    }

    var structId = selectRow["structId"];
    if (structId == operationId || structId == "request" || structId == "response") {
        alert("该节点不能增加子节点");
        return;
    }
    var parentRow = selectRow;

    if (manager.isLeaf(parentRow)) {
        alert('叶节点不能增加子节点');
        return;
    }
    manager.add(data, null, true, parentRow);
    refreshSeq();
}
function appendToCurrentNodeUp() {
    var selectRow = manager.getSelectedRow();
    if (!selectRow) return;
    var pid = selectRow["pid"];
    var selectRowParnet = manager.getParent(selectRow);
    var data = getNewData(pid, false);
    if (data.id == "" || data.structId == "" || data.metadataId == "" || type == "") {
        alert("节点数据不完整");
        return;
    }
    manager.add(data, selectRow, true, selectRowParnet);
    refreshSeq();
}
function appendToCurrentNodeDown() {
    var selectRow = manager.getSelectedRow();
    if (!selectRow) return;
    var pid = selectRow["pid"];
    var selectRowParnet = manager.getParent(selectRow);
    var data = getNewData(pid, false);
    if (data.id == "" || data.structId == "" || data.metadataId == "" || type == "") {
        alert("节点数据不完整");
        return;
    }
    manager.add(data, selectRow, false, selectRowParnet);
    refreshSeq();
}
function getSelected() {
    var row = manager.getSelectedRow();
    if (!row) {
        alert('请选择行');
        return;
    }
    alert(JSON.stringify(row));
}
function getData() {
    var data = manager.getData();
    alert(JSON.stringify(data));
}
function hasChildren() {
    var row = manager.getSelected();
    alert(manager.hasChildren(row));
}
function upgrade() {
    var row = manager.getSelected();
    manager.upgrade(row);
}
function demotion() {
    var row = manager.getSelected();
    manager.demotion(row);
}
function isLeaf() {
    var row = manager.getSelected();
    alert(manager.isLeaf(row));
}
function toggle() {
    var row = manager.getSelected();
    manager.toggle(row);
}
function expand() {
    var row = manager.getSelected();
    manager.expand(row);
}
function up() {
    var row = manager.getSelected();
    manager.up(row);
    refreshSeq();
}
function down() {
    var row = manager.getSelected();
    manager.down(row);
    refreshSeq();
}
function saveSDA() {
    var sdaManager = {
        saveSDA: function (sda) {
            $.ajax({
                "type": "POST",
                "contentType": "application/json; charset=utf-8",
                "url": "../operationInfo/addSDANew",
                "data": JSON.stringify(sda),
                "dataType": "json",
                "success": function (result) {
                    if (result) {
                        alert("保存成功");
                        window.location.reload();
                    } else {
                        alert("保存失败");
                    }
                }
            });
        }
    };
    var sdaArray = [];
    var table = document.getElementById("gridId");
    for (var i = table.rows.length - 1; i >= 0; i--) {
        var seq = table.rows[i].cells[0].innerText;
        var id = table.rows[i].cells[6].innerText;
        var structId = table.rows[i].cells[1].innerText;
        var metadataId = table.rows[i].cells[2].innerText;
        var type = table.rows[i].cells[3].innerText;
        var required = table.rows[i].cells[4].innerText;
        var remark = table.rows[i].cells[5].innerText;
        var parentId = table.rows[i].cells[7].innerText;
        var params = {
            id: id,
            structId: structId,
            metadataId: metadataId,
            type: type,
            seq: seq,
            required: required,
            remark: remark,
            serviceId: serviceId,
            operationId: operationId,
            parentId: parentId
        };
        sdaArray.push(params);
    }
    sdaManager.saveSDA(sdaArray);
}