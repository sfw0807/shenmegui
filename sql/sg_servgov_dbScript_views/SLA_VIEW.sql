CREATE VIEW "ESB"."SLA_VIEW"
as
(
select distinct tl.operation_id,o.operation_name,tl.service_id,s.service_name,tl.ecode,i.interface_name,sys.sys_name,sys.sys_ab, 
(select s.SLA_VALUE from SG_SLA s where s.SERVICE_ID = tl.service_id and s.OPERATION_ID = tl.operation_id and s.SLA_NAME = '并发数') CURRENTCOUNT,
(select s.SLA_VALUE from SG_SLA s where s.SERVICE_ID = tl.service_id and s.OPERATION_ID = tl.operation_id and s.SLA_NAME = '业务成功率') SUCCESSRATE,
(select s.SLA_VALUE from SG_SLA s where s.SERVICE_ID = tl.service_id and s.OPERATION_ID = tl.operation_id and s.SLA_NAME = '平均响应时间') AVERAGETIME,
(select s.SLA_VALUE from SG_SLA s where s.SERVICE_ID = tl.service_id and s.OPERATION_ID = tl.operation_id and s.SLA_NAME = '超时时间') TIMEOUT,
(select distinct s.SLA_REMARK from SG_SLA s where s.SERVICE_ID = tl.service_id and s.OPERATION_ID = tl.operation_id ) SLA_REMARK
from
(select distinct t.service_id,t.operation_id,ir.ecode,ir.provide_sys_id 
from (select distinct SERVICE_ID,OPERATION_ID from SG_SLA) t,invoke_relation ir
where t.service_id = ir.service_id and t.operation_id = ir.operation_id) tl
left join sg_operation o on tl.operation_id = o.operation_id
left join service s on tl.service_id = s.service_id
left join sg_system sys on tl.provide_sys_id = sys.sys_id
left join interface i on tl.ecode = i.ecode
)