--ユーザ作成
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
CREATE ROLE "postgresUser";
ALTER ROLE "postgresUser" WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN NOREPLICATION PASSWORD 'md575160f068a4f1fca9f94f3b9a4a86db6';
CREATE ROLE postgresuser;
ALTER ROLE postgresuser WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION PASSWORD 'md53f49afe3f612c07e8a95ccdc303a82a1';
CREATE ROLE root;
ALTER ROLE root WITH SUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION PASSWORD 'md52845e10ad19ccc5104762fcf53c68ebc';

--DB作成
-- Database: irdb
-- ユーザーセッションを確認し、irdb データベースに接続されているセッションを切断します。
SELECT pg_terminate_backend (pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'irdb';


DROP DATABASE irdb;

CREATE DATABASE irdb
    WITH 
	TEMPLATE = template0
    OWNER = postgresuser
    ENCODING = 'UTF8'
    LC_COLLATE = 'Japanese_Japan.932'
    LC_CTYPE = 'Japanese_Japan.932'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
-- DROP DATABASE irdb;
-- CREATE DATABASE irdb WITH TEMPLATE = template0 OWNER = postgresuser;

--権限変更
REVOKE ALL ON DATABASE template1 FROM PUBLIC;
REVOKE ALL ON DATABASE template1 FROM postgres;
GRANT ALL ON DATABASE template1 TO postgres;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;

