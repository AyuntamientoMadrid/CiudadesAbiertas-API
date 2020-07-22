USE master
GO
--DROP LOGIN login_ciudadesAbiertas;
--DROP DATABASE bbdd_ciudadesAbiertas;
CREATE DATABASE bbdd_ciudadesAbiertas COLLATE SQL_Latin1_General_CP1_CI_AS;
CREATE LOGIN login_ciudadesAbiertas WITH PASSWORD='Primera1';


USE bbdd_ciudadesAbiertas
GO
CREATE USER user_ciudadesAbiertas FOR LOGIN login_ciudadesAbiertas WITH DEFAULT_SCHEMA=dbo;
ALTER LOGIN login_ciudadesAbiertas WITH DEFAULT_DATABASE = bbdd_ciudadesAbiertas;

USE bbdd_ciudadesAbiertas
GO
EXEC sp_addrolemember 'db_ddladmin', 'user_ciudadesAbiertas';
EXEC sp_addrolemember 'db_datareader', 'user_ciudadesAbiertas';
EXEC sp_addrolemember 'db_datawriter', 'user_ciudadesAbiertas';

GRANT EXECUTE TO user_ciudadesAbiertas WITH GRANT OPTION;

USE bbdd_ciudadesAbiertas
GO
CREATE SCHEMA schema_descargaMasiva AUTHORIZATION user_ciudadesAbiertas;

GO
ALTER USER user_ciudadesAbiertas WITH DEFAULT_SCHEMA=schema_descargaMasiva;

