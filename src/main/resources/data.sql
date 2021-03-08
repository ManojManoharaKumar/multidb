DROP TABLE IF EXISTS PERSONS;

create table PERSONS (
	id INT,
	first_name VARCHAR(50),
	last_name VARCHAR(50)
);

insert into PERSONS (id, first_name, last_name) values (1, 'Manoj', 'ManoharaKumar');
