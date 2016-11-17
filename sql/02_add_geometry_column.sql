-- create additional geometry column to pflow_table
alter table pflow_table add column geom geometry('POINT',4326);

-- create POINT geometry from (lon,lat) position and set it to geometry column
update pflow_table set geom=ST_SetSrid(ST_MakePoint(lon,lat),4326);
