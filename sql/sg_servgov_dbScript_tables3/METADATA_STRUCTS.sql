DROP TABLE ESB.METADATA_STRUCTS;
CREATE TABLE ESB.METADATA_STRUCTS  ( 
	STRUCTID  	VARCHAR(50) NOT NULL,
	STRUCTNAME	VARCHAR(100),
	REMARK    	VARCHAR(500) 
	)
;
COMMENT ON TABLE ESB.METADATA_STRUCTS IS '元数据结构基本信息表'
;
COMMENT ON COLUMN ESB.METADATA_STRUCTS.STRUCTID IS '结构体ID'
;
COMMENT ON COLUMN ESB.METADATA_STRUCTS.STRUCTNAME IS '结构体名称'
;
COMMENT ON COLUMN ESB.METADATA_STRUCTS.REMARK IS '结构体描述'
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('UserBscInfo', '用户基本信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('UserAdvInfo', '用户高级信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('UserCpcInfo', 'CPC用户信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('OrgInfoList', '机构信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('GrpInfoList', '用户组信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('OrgCpcInfoList', '机构CPC信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RoleInfo', '角色信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('TaskInfo', '任务信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('FrontFuncInfo', '前端功能列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ChildInsts', '子机构信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ChildTasks', '子任务列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ChildFrontFunc', '子功能点', '')
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ChildGrpList', '子用户组信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('BPMPageInfo', '分页信息结构体', '')
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('EcifHeader', 'ECIF报文头', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('QueryHeader', '查询报文头', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('SimpFncOrgInfo', '简单金融机构信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ClntComInfo', '客户公共信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RtlClntInfo', '对私客户信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RtlAsmbInfo', '对私客户组合信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RltNameInfo', '对私客户名称信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('CorpClntInfo', '对公客户信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('CorpExtendInfo', '对公客户扩展信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('CorpNameInfo', '对公客户名称信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('FISpclInfo', '金融机构特有信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('CtfInfo', '证件信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('AdrInfo', '地址信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('CtcModeInfo', '联系方式信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('FncInfo', '客户财务信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RelClntInfo', '客户关联人信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RelCorpInfo', '客户关联企业信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('LevelInfo', '客户等级信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RiskLevelInfo', '客户风险信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('MgrInfo', '客户管理人员信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ComPayeeDmst', '常用收款人(本币)', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('ComPayeeOvs', '常用收款人(外币)', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('OthrAttrInfo', '客户其他属性', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RgstCptlInfo', '注册资金信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('RltnpInfo', '客户关系信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('SpclListInfo', '特殊名单信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('AcctInfo', '账户信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('AgrmInfo', '协议信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('MedmInfo', '介质信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('SimpAcctInfo', '简单账户信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('SimpMedmInfo', '简单介质信息', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('GrpInfo', '分组信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('InstInfo', '机构信息列表', NULL)
;
INSERT INTO ESB.METADATA_STRUCTS(STRUCTID, STRUCTNAME, REMARK)
  VALUES('OrgCpcInfo', '机构Cpc信息列表', NULL)
;
COMMIT;