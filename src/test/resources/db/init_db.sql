/*
USER & SCHEMA
 */

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

/*
PARROT SCHEMA
 */

SET SCHEMA 'parrot';

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id serial,
  email character varying(80),
  first_name character varying(50),
  last_name character varying(80),
  CONSTRAINT pk_types PRIMARY KEY (id),
  UNIQUE(email)
);
ALTER SEQUENCE users_id_seq restart with 7;
CREATE INDEX idx_users_name ON users (email);
