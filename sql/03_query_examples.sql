-- 0) データを全部出力してみる
select * from pflow_table;


-- 1) データのレコード数をカウントする
select count(*) from pflow_table;


-- 2) ユニークID数をカウントする
select count(distinct pid) from pflow_table;


-- 3) 午前10:00に移動しているID数をカウントする
select count(distinct pid) from pflow_table 
where starttime<='2008-10-01 10:00:00' and '2008-10-01 10:00:00' < endtime and transport<>97; 


-- 4) 3)の条件に合致するデータの(PID, 時刻, 移動目的, 交通手段, 経緯度）を取得
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
  starttime <= '2008-10-01 10:00:00' and '2008-10-01 10:00:00' < endtime and transport<>97 
order by 
  pid
;


-- 5) 東京駅近くを通る人のデータを取得（PostGIS 空間検索）
select 
  pid,
  starttime,
  purpose,
  transport
from 
  pflow_table
where
  	ST_Intersects(
	  	geom,
	  	ST_SetSRID(
	  		ST_MakeBox2D(
	  			ST_MakePoint(139.76325989,35.67870325),
	  			ST_MakePoint(139.76948261,35.68320008)
	  		),
	  		4326
	  	)
	)
;
