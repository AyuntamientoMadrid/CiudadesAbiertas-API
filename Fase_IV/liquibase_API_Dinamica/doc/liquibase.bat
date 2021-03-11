@ECHO OFF
cls

D:
cd D:\git\CiudadesAbiertas\liquibase_BaseMadrid
call mvn install
rem actualizar bbdd destino
call mvn liquibase:update
rem exportar estructuras de origen
rem call mvn liquibase:generateChangeLog
rem datos de origen
rem call mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
PAUSE
