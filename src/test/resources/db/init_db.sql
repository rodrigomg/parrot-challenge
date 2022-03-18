CREATE ROLE parrot WITH
LOGIN
SUPERUSER
INHERIT
CREATEDB
CREATEROLE
REPLICATION
PASSWORD 'parrot';

CREATE SCHEMA parrot
AUTHORIZATION parrot;

SET SCHEMA 'parrot';

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS order_details;


CREATE TABLE users (
  id serial,
  email character varying(80),
  first_name character varying(50),
  last_name character varying(80),
  CONSTRAINT pk_types PRIMARY KEY (id),
  UNIQUE(email)
);
ALTER SEQUENCE users_id_seq restart with 7;
CREATE INDEX idx_email ON users (email);

CREATE TABLE orders (
  id serial,
  user_id int,
  CONSTRAINT pk_orders PRIMARY KEY (id),
  CONSTRAINT fk_user
    FOREIGN KEY(user_id)
      REFERENCES users(id)
);
ALTER SEQUENCE orders_id_seq restart with 7;

CREATE TABLE products (
  id serial,
  product character varying(80),
  price decimal(10,2),
  stock int,
  CONSTRAINT pk_products PRIMARY KEY (id),
  UNIQUE(product)
);
ALTER SEQUENCE products_id_seq restart with 7;
CREATE INDEX idx_product ON products (product);

CREATE TABLE order_details (
  id serial,
  order_id int,
  product_id int,
  CONSTRAINT pk_order_details PRIMARY KEY (id),
  CONSTRAINT fk_order
    FOREIGN KEY(order_id)
      REFERENCES orders(id),
  CONSTRAINT fk_product
    FOREIGN KEY(product_id)
      REFERENCES products(id)
);
ALTER SEQUENCE order_details_id_seq restart with 7;

insert into users (id,email,first_name,last_name)
values (1,'roger.mtz@gmail.com','Rodrigo','Martinez Garcia');

insert into products (id,product,price,stock)
values (1,'carne',50.00,10);

insert into products (id,product,price,stock)
values (2,'queso',20.00,5);

insert into products (id,product,price,stock)
values (3,'soda',8.00,15);

insert into orders (id,user_id)
values (1,1);

insert into orders (id,user_id)
values (2,1);

insert into order_details (id,order_id,product_id)
values (1,1,1);

insert into order_details (id,order_id,product_id)
values (2,2,2);

insert into order_details (id,order_id,product_id)
values (3,2,3);
