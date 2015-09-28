-- csv export
Copy(
	select
		pid,
		starttime,
		purpose,
		transport,
		lon,
		lat
	from 
		pflow_table 
	where 
		starttime <= '2008-10-01 10:00:00' and '2008-10-01 10:00:00'< endtime and transport <> 97 
	order by 
		pid 
) 
to '/tmp/export.csv' 
(delimiter ',', format csv, header true) -- ヘッダ付きCSV出力する場合は "with CSV header" でも良い．
;
