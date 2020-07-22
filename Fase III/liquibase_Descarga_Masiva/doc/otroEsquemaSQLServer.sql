USE master
GO
DROP LOGIN datosCA;
DROP DATABASE datosCA;
CREATE DATABASE datosCA COLLATE SQL_Latin1_General_CP1_CI_AS;
CREATE LOGIN datosCA WITH PASSWORD='Primera1';


USE datosCA
GO
CREATE USER datosCA FOR LOGIN datosCA WITH DEFAULT_SCHEMA=dbo;
ALTER LOGIN datosCA WITH DEFAULT_DATABASE = datosCA;

GO
EXEC sp_addrolemember 'db_ddladmin', 'datosCA';
EXEC sp_addrolemember 'db_datareader', 'datosCA';
EXEC sp_addrolemember 'db_datawriter', 'datosCA';

GRANT EXECUTE TO datosCA WITH GRANT OPTION;


USE datosCA
GO
CREATE SCHEMA descargaMasiva AUTHORIZATION datosCA;


