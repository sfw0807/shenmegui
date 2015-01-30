CREATE VIEW "ESB"."PUBLISH_VIEW"
as
(select pi.ID,ir.ECODE,ir.SERVICE_ID,ir.OPERATION_ID,ir.PROVIDE_SYS_ID,ir.CONSUME_SYS_ID,pi.ONLINE_DATE,ir.VERSIONST 
from PUBLISH_INFO pi 
left join 
(select i.ECODE,i.SERVICE_ID,i.OPERATION_ID,i.PROVIDE_SYS_ID,i.CONSUME_SYS_ID,i.ID,ts.VERSIONST 
from INVOKE_RELATION i left join trans_state ts on i.id = ts.id)
ir on pi.IR_ID = ir.ID)