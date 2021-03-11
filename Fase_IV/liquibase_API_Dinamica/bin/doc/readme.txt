Crear esquema:

Edit file src\main\resources\liquibase\liquibase.properties
mvn liquibase:update


Generar xml a partir de BBDD existente:
mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data

Documentación: http://shengwangi.blogspot.com/2016/04/liquibase-helloworld-example.html