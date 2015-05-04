DROP TABLE ESB.SG_FUNCTION;
CREATE TABLE ESB.SG_FUNCTION  ( 
	FUNCTION_ID       	INTEGER NOT NULL,
	FUNCTION_NAME     	VARCHAR(255) NOT NULL,
	FUNCTION_URL      	VARCHAR(255) NOT NULL,
	FUNCTION_PARENT_ID	VARCHAR(255) NOT NULL,
	FUNCTION_REMARK   	VARCHAR(255) 
	)
;
COMMENT ON TABLE ESB.SG_FUNCTION IS '�˵�������Ϣ��'
;
COMMENT ON COLUMN ESB.SG_FUNCTION.FUNCTION_ID IS '����ID'
;
COMMENT ON COLUMN ESB.SG_FUNCTION.FUNCTION_NAME IS '�˵�����'
;
COMMENT ON COLUMN ESB.SG_FUNCTION.FUNCTION_URL IS 'URL��ַ'
;
COMMENT ON COLUMN ESB.SG_FUNCTION.FUNCTION_PARENT_ID IS '��ID'
;
COMMENT ON COLUMN ESB.SG_FUNCTION.FUNCTION_REMARK IS '����'
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(1, '�������', '#', '0', 'һ���˵��������')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(2, '��Դ����', '#', '0', 'һ���˵���Դ����')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(3, '��ͼ��ѯ', '#', '0', 'һ���˵���ͼ��ѯ')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(4, 'ϵͳ����', '#', '0', 'һ���˵�ϵͳ����')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(5, '��������', '/jsp/operationManager.jsp', '1', '�����˵���������')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(6, '�������', '/jsp/serviceInfoManager.jsp', '1', '�����˵��������')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(7, '�ӿڹ���', '/html/interfaceManagement.html', '1', '�����˵��ӿڹ���')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(8, '�������', '/jsp/audit.jsp', '1', '�����˵��������')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(9, '����������', '/jsp/serviceCategoryManager.jsp', '1', '�����˵�����������')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(10, 'Ԫ���ݹ���', '/jsp/metadataManager.jsp', '1', '�����˵�Ԫ���ݹ���')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(11, 'Ԫ���ݽṹ����', '/jsp/metadataStructsManager.jsp', '1', '�����˵�Ԫ���ݽṹ����')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(12, '����ϵͳ����', '/jsp/systemInfoManager.jsp', '1', '�����˵�����ϵͳ����')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(13, '���ù�ϵ����', '/jsp/invokeManager.jsp', '1', '�����˵����ù�ϵ����')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(14, '��Դ����', '/jsp/resourceImport.jsp', '2', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(29, '�û�����', '/jsp/userManager.jsp', '4', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(15, '��Դ����', '/jsp/resourceExport.jsp', '2', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(16, 'ӳ���ĵ�����', '/html/exportExcel.html', '2', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(17, 'ϵͳ����SQL����', '/jsp/sysSql.jsp', '2', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(18, 'PDF����', '/jsp/pdf.jsp', '2', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(19, '������ù�ϵ��ѯ', '/jsp/svcAsmRelateView.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(20, '������ϸ��Ϣ��ѯ', '/jsp/serviceDetailView.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(21, '������ѯ', '/jsp/serviceStore.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(22, '����ͳ����Ϣ', '/jsp/serviceTotalView.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(23, '���񿪷�ͳ����Ϣ��ѯ', '/jsp/serviceDevProgressView.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(24, '����SLA��Ϣ��ѯ', '/jsp/sla.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(25, '����ϵͳ����ͳ�Ʊ�', '/jsp/sysInvokeServiceView.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(26, '����ϵͳ����ͼ', '/jsp/sysTopology.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(28, '����ͳ����Ϣ��ѯ', '/jsp/publishTotalView.jsp', '3', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(30, '��ɫ����', '/jsp/roleManager.jsp', '4', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(31, 'Ȩ�޹���', '/jsp/functionManager.jsp', '4', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(32, '��������', '/jsp/orgManager.jsp', '4', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(33, '�����޸�', '/jsp/passwordManager.jsp', '4', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(34, '��־��ѯ', '/html/operationLogQuery.html', '4', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(35, '�������׹���', '#', '0', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(36, '�������׵���', '/jsp/tranimport.jsp', '35', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(37, '�������׹���', '/jsp/tranlink.jsp', '35', '')
;
INSERT INTO ESB.SG_FUNCTION(FUNCTION_ID, FUNCTION_NAME, FUNCTION_URL, FUNCTION_PARENT_ID, FUNCTION_REMARK)
  VALUES(38, '��������ͳ��', '/jsp/trancount.jsp', '35', '')
;
COMMIT;
COMMIT;