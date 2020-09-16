@ECHO OFF



call mvn clean install
rem call mvn liquibase:clearCheckSums
rem actualizar bbdd destino
call mvn liquibase:update
rem volver hacia atrás una version
rem call mvn liquibase:rollback -Dliquibase.rollbackTag=1.01
rem exportar estructuras de origen
rem call mvn liquibase:generateChangeLog
rem call mvn liquibase:generateChangeLog -Dliquibase.diffIncludeObjects="table:contratos.*"
rem call mvn liquibase:generateChangeLog -Dliquibase.diffIncludeObjects="territorio_pais"
rem datos de origen
rem call mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
rem call mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data -Dliquibase.diffIncludeObjects="table:contratos.*"
rem call mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data -Dliquibase.diffIncludeObjects="query"
rem documentar
rem call mvn liquibase:dbDoc
rem dejar en version 1.0
rem call mvn liquibase:rollback -Dliquibase.rollbackTag=1.0
PAUSE
