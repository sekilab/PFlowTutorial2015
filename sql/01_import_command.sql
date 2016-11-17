
-- create pflow_table
create table pflow_table (
  pid       int4,
  tripid    int4,
  subtripid int4,
  starttime timestamp,
  endtime   timestamp,
  lon       float8,
  lat       float8,
  gender    int4,
  age       int4,
  address   varchar(10),
  work      int4,
  purpose   int4,
  magfac1   int4,
  magfac2   int4,
  transport int4 
);

-- copy from csv file to DB table(pflow_table)
copy pflow_table from '/tmp/DB_import_file.csv' with csv;

