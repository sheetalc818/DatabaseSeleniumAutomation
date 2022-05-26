# DatabaseSeleniumAutomation

select * from uploadcsv.user ;

INSERT INTO uploadcsv.user (user_id, user_name, user_type)
VALUES (5, '7756994000', 'prime');

delete from uploadcsv.user where user_id between 4 AND 18 ;
delete from uploadcsv.user where user_name = 'ramesh'; 

UPDATE uploadcsv.user SET user_name = '7756994045' WHERE user_id = 1

CREATE TABLE user(
user_id int,
user_name varchar(20),
user_type varchar(20),
PRIMARY KEY(user_id)
);
