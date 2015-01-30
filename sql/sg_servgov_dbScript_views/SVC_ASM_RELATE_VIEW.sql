CREATE VIEW "ESB"."SVC_ASM_RELATE_VIEW" AS                           
	(
SELECT
	* 
FROM
	( (	SELECT
			r.ID,
			r.SERVICE_ID,
			r.OPERATION_ID,
			r.ECODE,
			r.CONSUME_MSG_TYPE,
			r.PROVIDE_MSG_TYPE,
			s.SERVICE_NAME,
			o.OPERATION_NAME,
			i.INTERFACE_NAME,
			r.DIRECTION,
			o.VERSION,
			csmsys.SYS_ID      AS csmSysId,
			csmsys.SYS_AB      AS csmSysAb,
			csmsys.SYS_NAME    AS csmSysName,
			passbysys.SYS_AB   AS passbysysAb,
			passbysys.SYS_NAME AS passbySysName,
			prdsys.SYS_ID      AS prdSysId,
			prdsys.SYS_AB      AS prdSysAb,
			prdsys.SYS_NAME    AS prdSysName,
			r.THROUGH,
			ts.VERSIONST,
			(	SELECT
					MAX(online_date) 
				FROM
					PUBLISH_INFO 
				WHERE
					IR_ID = r.id )                 AS online_date,
			(	SELECT
					MAX(INTERFACE_VERSION) 
				FROM
					PUBLISH_INFO 
				WHERE
					IR_ID = r.id )                 AS online_version,
			(	SELECT
					DISTINCT FIELD 
				FROM
					PUBLISH_INFO 
				WHERE
					IR_ID = r.id )                 AS field 
		FROM
			INVOKE_RELATION r 
			LEFT JOIN SERVICE s 
			ON r.SERVICE_ID = s.SERVICE_ID 
			LEFT JOIN SG_OPERATION o 
			ON r.OPERATION_ID = o.OPERATION_ID AND
			r.SERVICE_ID = o.SERVICE_ID 
			LEFT JOIN INTERFACE i 
			ON r.ECODE = i.INTERFACE_ID 
			LEFT JOIN SG_SYSTEM csmsys 
			ON r.CONSUME_SYS_ID = csmsys.SYS_ID 
			LEFT JOIN SG_SYSTEM passbysys 
			ON r.PASSBY_SYS_ID = passbysys.SYS_AB 
			LEFT JOIN SG_SYSTEM prdsys 
			ON r.PROVIDE_SYS_ID = prdsys.SYS_ID 
			LEFT JOIN TRANS_STATE ts 
			ON r.ID = ts.ID ) ) )