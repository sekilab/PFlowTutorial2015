-- csv export
Copy(
	select
		pid,
		starttime,
		lon,
		lat,
		gender,
		age,
		work,
		purpose,
		magfac1,
		transport
	from 
		pflow_table 
	where 
		'2008-10-01 00:00:00' <= starttime and endtime <= '2008-10-01 23:59:59'
	order by 
		pid, starttime
) 
to '/tmp/pflow_data_for_mobmap.csv' 
(delimiter ',', format csv, header true) -- ヘッダ付きCSV出力する場合は "with CSV header" でも良い．
;
