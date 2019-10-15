-- 0º BORRAR LA BASE DE DATOS Y CREARLA VACIA

DROP DATABASE inmobiliaria;

CREATE DATABASE inmobiliaria_prod
  WITH ENCODING='UTF8'
       OWNER=postgres
       CONNECTION LIMIT=-1;
       
CREATE DATABASE inmobiliaria
  WITH OWNER = dba_inmobiliaria
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Argentina.1252'
       LC_CTYPE = 'Spanish_Argentina.1252'
       CONNECTION LIMIT = -1;
       
--1º Abro la consola de comandos de windows --> ejecuto cmd
--2º Me posiciono en la carpeta que tiene los ejecutables de postgres
--		cd c:\Program Files\PostgreSQL\9.2\bin
--3º (YA NO) Copiar el backup a ese directorio para no tener problemas de referenciarlo.
--4º Ejecutar psql.exe -U postgres -d inmobiliaria_prod -f C:\Pablo\desarrollos\Inmobiliaria\Acuario\backupbd\julio2013.backup
--		contraseña dba_inmobiliaria --> dba

-- para borrar
-- 		dropdb.exe -U postgres -d inmobiliaria
--		Si da error por que hay otras conecciones ir a "Administrador de Tareas de Windows" y detener e iniciar el servicio POSTGRESQL
--		