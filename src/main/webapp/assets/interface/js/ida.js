var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
var param;
var interfaceId;

var href = window.location.href;
var params = href.split("&");
interfaceId = params[0].split("=")[1];
interfaceId = interfaceId.split("#")[0];

var treeData = {Rows: []};
function alert(message) {
    $.ligerDialog.alert(message.toString(), '提示信息');
}
function tip(message) {
    $.ligerDialog.tip({title: '提示信息', content: message.toString()});
}
var manager;
$(function () {
    var setManagerCallBack = function setManagerCallBack(gridManager) {
        manager = gridManager;
    };
    idaManager.getIDAByInterfaceId(treeData, interfaceId, setManagerCallBack);

    $("#randomUUID").click(function () {
        $.ajax({
            url: '../operationInfo/randomUUID',
            type: 'GET',
            success: function (result) {
                $("#idaid").val(result.value);
            }
        });
    });
});

function bt_choice() {
    window.open("../jsp/metadataManagerChoice.jsp", "", "toolbar=no,location=no,titlebar=no,resizable=yes,scrollbars=yes,top=0,left=0");
}

function getNewLinkValue(value) {
    //获取子页面的值
    document.getElementById("metadataId").value = value;
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
    manager.deleteRow(row);
    refreshSeq();
}
var i = -1;

function getNewData(pid, withchildren) {
    i++;
    var id = $("#sdaid").val();
    var structName = $("#structName").val();
    var metadataId = $("#metadataId").val();
    var type = $("#type").val();
    var scale = $("#scale").val();
    var required = $("#required").val();
    var remark = $("#remark").val();
    var length = $("#length").val();
    var data = {
        id: id,
        pid: pid,
        structName: structName,
        metadataId: metadataId,
        type: type,
        length: length,
        scale: scale,
        remark: remark,
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
    var parentRow = selectRow;
    manager.upgrade(parentRow);
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
            console.log(sda);
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
    var idaArray = [];
    var table = document.getElementById("gridId");
    for (var i = table.rows.length - 1; i >= 0; i--) {
        var seq = table.rows[i].cells[0].innerText.replace(/\n/ig, "");
        var structName = table.rows[i].cells[1].innerText.replace(/\n/ig, "");
        var metadataId = table.rows[i].cells[2].innerText.replace(/\n/ig, "");
        var length = table.rows[i].cells[3].innerText.replace(/\n/ig, "");
        var scale = table.rows[i].cells[4].innerText.replace(/\n/ig, "");
        var type = table.rows[i].cells[5].innerText.replace(/\n/ig, "");
        var required = table.rows[i].cells[6].innerText.replace(/\n/ig, "");
        var remark = table.rows[i].cells[7].innerText.replace(/\n/ig, "");
        var id = table.rows[i].cells[8].innerText.replace(/\n/ig, "");
        var parentId = table.rows[i].cells[9].innerText.replace(/\n/ig, "");
        var ida = {
            id: id,
            structName: structName,
            metadataId: metadataId,
            length: length,
            scale: scale,
            type: type,
            seq: seq,
            required: required,
            remark: remark,
            interfaceId: interfaceId,
            parentId: parentId
        };
        idaArray.push(ida);
    }
    idaManager.saveIDAs(idaArray);
}

$(function () {
    var dialog = $("#metadataInfoDialog").dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "关闭": function () {
                dialog.dialog("close");
            }
        }
    });

    $("#showMdtInfoBtn").click(function () {
        var selectRow = manager.getSelectedRow();
        console.log(selectRow);
        var metadataId = selectRow["metadataId"];
        if (metadataId) {
            var getMetadataInfoCallBack = function getMetadataInfoCallBack(metadataInfo) {
                dialog.dialog("open");
            }
            metadataManager.getMetadataInfoById(metadataId, getMetadataInfoCallBack);
        } else {

        }
    });

});


