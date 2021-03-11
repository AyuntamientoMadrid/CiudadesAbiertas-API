USE master
GO
--DROP LOGIN apiDinamica;
--DROP DATABASE apiDinamica;
CREATE DATABASE apiDinamica COLLATE SQL_Latin1_General_CP1_CI_AS;
CREATE LOGIN apiDinamica WITH PASSWORD='Primera1';


USE apiDinamica
GO
CREATE USER apiDinamica FOR LOGIN apiDinamica WITH DEFAULT_SCHEMA=dbo;
ALTER LOGIN apiDinamica WITH DEFAULT_DATABASE = apiDinamica;

USE apiDinamica
GO
EXEC sp_addrolemember 'db_ddladmin', 'apiDinamica';
EXEC sp_addrolemember 'db_datareader', 'apiDinamica';
EXEC sp_addrolemember 'db_datawriter', 'apiDinamica';

GRANT EXECUTE TO apiDinamica WITH GRANT OPTION;

USE apiDinamica
GO
CREATE SCHEMA apiDinamica AUTHORIZATION apiDinamica;

GO
ALTER USER apiDinamica WITH DEFAULT_SCHEMA=apiDinamica;