DROP TABLE  PROTOCOLINFO;
CREATE TABLE  PROTOCOLINFO  ( 
	SYSID       	VARCHAR(50) NOT NULL,
	CONNECTMODE 	VARCHAR(50),
	SYSADDR     	VARCHAR(300),
	APPSCENE    	VARCHAR(100),
	MESSAGETYPE 	VARCHAR(50),
	TIMEOUT     	VARCHAR(20),
	SYSTYPE     	VARCHAR(50),
	CODETYPE    	VARCHAR(50),
	MACFLAG     	VARCHAR(50),
	CURRENTTIMES	VARCHAR(10),
	AVGRESTIME  	VARCHAR(10),
	SUCCESSRATE 	VARCHAR(10) 
	)
;
COMMENT ON TABLE  PROTOCOLINFO IS '接入系统协议信息表'
;
COMMENT ON COLUMN  PROTOCOLINFO.SYSID IS '系统ID'
;
COMMENT ON COLUMN  PROTOCOLINFO.CONNECTMODE IS '协议类型'
;
COMMENT ON COLUMN  PROTOCOLINFO.SYSADDR IS '访问地址'
;
COMMENT ON COLUMN  PROTOCOLINFO.APPSCENE IS '应用场景'
;
COMMENT ON COLUMN  PROTOCOLINFO.MESSAGETYPE IS '报文类型'
;
COMMENT ON COLUMN  PROTOCOLINFO.TIMEOUT IS '超时时间'
;
COMMENT ON COLUMN  PROTOCOLINFO.SYSTYPE IS '系统类型'
;
COMMENT ON COLUMN  PROTOCOLINFO.CODETYPE IS '编码格式'
;
COMMENT ON COLUMN  PROTOCOLINFO.MACFLAG IS 'MAC校验'
;
COMMENT ON COLUMN  PROTOCOLINFO.CURRENTTIMES IS '并发数'
;
COMMENT ON COLUMN  PROTOCOLINFO.AVGRESTIME IS '承诺平均响应时间(S)'
;
COMMENT ON COLUMN  PROTOCOLINFO.SUCCESSRATE IS '承诺成功率(%)'
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0008', 'HTTP', '', '', 'SOAP', '60', '提供方', 'UTF-8', '', '10', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0437', 'HTTP', '', '', 'SOP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0005', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0006', 'TCP/HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0437', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0003', 'TCP', '', '', 'SOP', '', '提供方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0014', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0015', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0017', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0020', 'TCP', '', '', 'SOP', '50S', '提供方', 'GB18030', '', '10', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0023', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0025', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0025', 'TCP', '', '', 'SOP', '', '提供方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0026', 'TCP', '', '', 'SOP', '35S', '提供方', 'GB18030', '', '25', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0027', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0027', 'WTC', '', '', 'SOP', '60S', '提供方', 'GB18030', '', '200', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0028', 'Tuxedo', '', '', 'SOP', '30S', '提供方', 'GB18030', '', '100', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0029', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0029', 'WTC', '', '', 'SOP', '60S', '提供方', 'GB18030', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0030', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0030', 'Tuxedo', '', '', 'SOP', '300S', '提供方', 'GB18030', '', '10', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0033', 'Tuxedo', '', '', 'SOP', '', '提供方', 'IOS-8859-1', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0034', 'TCP', '', '', 'SOP', '', '提供方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0037', 'TCP', '', '', 'SOP', '60S', '提供方', 'GB18030', '', '10', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0044', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0044', 'TCP', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0044', 'TCP', '', '', 'SOP', '', '提供方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0046', 'TCP', '', '', 'SOP', '', '提供方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0076', 'TCP', '', '', 'SOP', '', '调用方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0370', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0362', 'XML-JCO', '', '', 'XML', '300', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0201', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0201', 'HTTP', '', '', 'SOAP', '5S', '提供方', 'UTF-8', '', '128', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0202', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0202', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '100', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0203', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0204', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0204', 'HTTP', '', '', 'SOAP', '60S', '提供方', 'UTF-8', '', '250', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0205', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0205', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0209', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0211', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0211', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0213', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0217', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0307', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0317', 'Tuxedo', '', '', 'SOP', '', '提供方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0327', 'TCP', '', '', 'SOP', '', '调用方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0327', 'TCP', '', '', 'SOP', '', '提供方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0329', 'TCP', '', '', 'SOP', '', '提供方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0337', 'HTTP', '', '', 'SOAP', '180S', '提供方', 'UTF-8', '', '50', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0362', 'XML-JCO', '', '', 'JCO', '300', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0366', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0366', 'TCP/HTTP', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0366', 'TCP', '', '', 'SOP', '10S', '提供方', 'GB2312', '', '160', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0366', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0149', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '否', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0084', 'TCP', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0425', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0200', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0439', '', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0718', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0825', 'TCP', '', '', 'SOP', '30S', '提供方', 'GB18030', '', '30', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0200', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0212', 'HTTP', '', '', 'SOAP', '', '提供方', 'UTF-8', '否', '100', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0212', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '否', '20', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0001', 'Tuxedo', '', '', 'SOP', '', '提供方', 'GB18030', '是', '10', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0112', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '否', '30', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0004', 'TCP', '', '', 'SOP', '', '调用方', 'GB18030', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0214', NULL, NULL, NULL, 'SOAP', NULL, '提供方', 'UTF-8', NULL, NULL, NULL, NULL)
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0214', NULL, NULL, NULL, 'SOAP', NULL, '调用方', 'UTF-8', NULL, NULL, NULL, NULL)
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0001', 'Tuxedo', '', '', 'SOP', '', '调用方', 'GB18030', '是', '10', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('111111', '', '', '', 'SOP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0010', 'HTTP', '', '', 'SOAP', '', '调用方', 'UTF-8', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0010', 'TCP', '', '', 'SOP', '', '提供方', 'GBK', '', '', '', '')
;
INSERT INTO  PROTOCOLINFO(SYSID, CONNECTMODE, SYSADDR, APPSCENE, MESSAGETYPE, TIMEOUT, SYSTYPE, CODETYPE, MACFLAG, CURRENTTIMES, AVGRESTIME, SUCCESSRATE)
  VALUES('0003', 'TCP', '', '', 'SOP', '', '调用方', 'GB18030', '是', '', '', '')
;
COMMIT;
COMMIT;