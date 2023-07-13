update source_foursquare.data_checkin set poi_category = 'school' where poi_category ='College & University';
update source_foursquare.data_checkin set poi_category = 'restaurant' where poi_category ='Food';
update source_foursquare.data_checkin set poi_category = 'mall' where poi_category ='Shop & Service';
update source_foursquare.data_checkin set poi_category = 'Entertainment' where poi_category ='Arts & Entertainment';
update source_foursquare.data_checkin set poi_category = 'work' where poi_category ='Professional & Other Places';

select source_foursquare.data_checkin where 
poi_category in ('school','restaurant','mall','Entertainment','work') or
poi_name in ('The City College of New York', 'New York','Liberty State Park') or
state in ('New Jersey') or
poi_name ~* '(Central Park)' or
poi_name ~* 'New York Sports Clubs'

dimPoi.category = 'Travel & Transport' " + 
				"  		and dimPoi.name ~* '(bus|subway)'

user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country


-------------------AQUI
create table tb_input_foursquare as
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
state in ('New Jersey', 'New York') and
(
poi_category in ('school','restaurant','mall','Entertainment','work', 'Residence') or
poi_name in ('The City College of New York', 'Liberty State Park')
)
 or
(
poi_name ~* '(Central Park)' or
poi_name ~* 'New York Sports Clubs' or
poi_name ~* '(bus|subway)' or
poi_name ~* '(Times Square)'
);

update tb_input_foursquare set poi_name = 'Central Park' where poi_name ~* '(Central Park)';
update tb_input_foursquare set poi_name = 'bus' where poi_name ~* '(bus)';
update tb_input_foursquare set poi_name = 'subway' where poi_name ~* '(subway)';
update tb_input_foursquare set poi_name = 'Times Square' where poi_name ~* '(Times Square)';

update tb_input_foursquare set poi_name = 'na' 
where poi_name not in ('Central Park','bus','subway','Times Square',
'The City College of New York', 'Liberty State Park','New York Sports Clubs');

update tb_input_foursquare set lon = FLOOR(RANDOM() * 100) + 1;
update tb_input_foursquare set lat = FLOOR(RANDOM() * 100) + 1;

---------------------------------
create table tb_input_foursquare as
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name in ('The City College of New York') limit 1) as t1

union
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name in ('Liberty State Park') limit 1) as t2

union
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name ~* '(Central Park)' limit 1) as t3

union
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name ~* 'New York Sports Clubs' limit 1) as t4

union
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name ~* '(bus)' limit 1) as t5

union 
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name ~* '(subway)' limit 1) as t6

union
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_name ~* '(Times Square)' limit 1) as t7

union
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_category in ('school') limit 1) as t8

union 
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_category in ('restaurant') limit 1) as t9

union 
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_category in ('mall') limit 1) as t10

union 
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_category in ('Entertainment') limit 1) as t11

union 
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_category in ('work') limit 1) as t12

union 
select * from (
select anonymized_user_id, tid, lat, lon, date_time, day, poi_name, poi_category, price, rating, weather, city, state, country 
from source_foursquare.data_checkin where
poi_category in ('Residence') limit 1) as t13

