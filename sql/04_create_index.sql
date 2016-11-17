-- インデックス作成
create index idx_pflow_table_pid		on pflow_table using btree(pid);
create index idx_pflow_table_starttime	on pflow_table using btree(starttime);
create index idx_pflow_table_endtime	on pflow_table using btree(endtime);
create index idx_pflow_table_gender		on pflow_table using btree(gender);
create index idx_pflow_table_age		on pflow_table using btree(age);
create index idx_pflow_table_work		on pflow_table using btree(work);
create index idx_pflow_table_purpose	on pflow_table using btree(purpose);
create index idx_pflow_table_transport	on pflow_table using btree(transport);
-- 空間インデックス作成(Gist)
create index idx_pflow_table_geom 		on pflow_table using Gist(the_geom);
