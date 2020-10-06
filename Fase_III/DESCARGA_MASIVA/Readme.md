
# Descarga Masiva

Aplicación para la configuración de la descarga masiva de los distintos conjuntos de datos censados en la aplicación.

## Funcionalidades incluidas en la aplicación
  
1.	Conexión con diferentes bases de datos para permitir el acceso a las diferentes fuentes de datos y realizar la exportación de estos
2.	Generación en los siguientes formatos:
	a.	JSON
	b.	XML
	c.	CSV
3.	Posibilidad de configurar para cada exportación:
	a.	si se desea comprimir en formato Zip
	b.	si se desea partir en función del número de registros
	c.	un ruta física donde dejar el fichero
	d.	si se desea sobrescribir el fichero o realizar una copia de seguridad del anterior
	e.	fecha de lanzamiento (*)
4.	La ejecución se podrá realizar:
	a.	de manera manual
	b.	a través de una tarea programada
5.	Todos los procesos tendrán los estados: 
	a.	Pendiente
	b.	En ejecución
	c.	Procesado
	d.	Error
6.	El proceso que ejecuta los procesos generara un fichero de log donde se almacenarán las diversas comprobaciones y pasos de cada ejecución
7.	Se tendrá un sistema de avisos a través de correo electrónico que se podrá configurar por cada tarea programada


## Requerimientos

  - Java 1.8 (compatible con OpenJDK 11)
  - Git
  - Maven
  - Sistema de relacional de BBDD:  SQLServer

## Ficheros de Configuración

En este proyecto existen 2 ficheros de configuración:

    - config.properties
    - logback.xml
    
Nota: La aplicación permite crear distintas conexiones con BBDD para lo cual solo es necesario, crear un nuevo fichero de configuracion (ej:ciudadesAbiertas.properties)

### config.properties

Fichero que contiene información para la ejecución del proyecto, así como la lista de los datasets que se quieren exportar:

```
#URIBase para generar RDF, no debe acabar en /
URIBase=http://localhost:8080

#Contexto para generar RDF. Debe empezar por / Ejemplo: /OpenCitiesAPI
contexto=/dynamicAPI

#tipoAutenticacion (uweb,bbdd,mixta)
tipoAutenticacion=mixta
#Desarrollo
#UWEB_URL=http://desa6intra:8090/WSUwebv2/services/ServicesUweb
#Formación
#UWEB_URL=http://form6intra:8090/WSUwebv2/services/ServicesUweb
#Preproducción
UWEB_URL=http://pre6intra:8090/WSUwebv2/services/ServicesUweb
#Producción
#UWEB_URL=http://intranet.munimadrid.es:8090/WSUwebv2/services/ServicesUweb 
#nombreDeLaApp
#UWEB_APP=app4545
UWEB_APP=app454
  
#valores por defecto de la paginación
pagina.maximo=500
pagina.defecto=100

#Peticiones maximas por segundo
peticiones.maximas.segundo=50

#Nodo Patron para Id Estadisticas
nodo.pattern=A

#Https mode (on/off) (en producción SIEMPRE on con https!!!!) 
https=off

#vble para la configuracion de las coordenadas geograficas SOPORTAMOS (EPSG:25830,EPSG:25829,EPSG:25828,EPSG:25831,EPSG:23030,EPSG:23029,EPSG:23028,EPSG:23031)
#el formato de origen de las coordenadas se corresponde con este SRID
xy.value.epsg=EPSG:25830
#vble para la configuracion de las coordenadas geograficas  SOPORTAMOS (EPSG:4258, EPSG:4230, EPSG:4326)
lat_lon.value.epsg=EPSG:4258

geo.xETRS89.field=xETRS89
geo.yETRS89.field=yETRS89
geo.latWGS84.field=latitud
geo.lonWGS84.field=longitud
geo.geometry.field=hasGeometry

mail.prefix=[DESCARGA MASIVA]
#mail.to=jcballesteros@localidata.com,cmartinez@localidata.com
mail.to=xxxxxxxxxx@xxxxxxxxxxx.com
mail.from=descargaMasiva@localidata.com

#BBDD Configuration

#JNDI SERVER SQLServer
#db.jndi.name=jdbc/ciudadesAbiertas
#db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
#db.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
#db.jndi.env=java:comp/env/
#db.schema=apiDinamica
#FIN JNDI SERVER


#Configuracion SQLServer
db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
db.url=jdbc:sqlserver://localhost:1433;databaseName=ciudadesAbiertas
db.user=ciudadesAbiertas
db.password=Primera1
db.schema=apiDinamica
db.initialSize=5
db.maxActive=10
db.maxIdle=10
db.minIdle=0
db.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
db.validationQuery=Select 1


#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
#RSQL ON / OFF LOG para el paquete de RSQL
rsql.log.active=off

spring.mail.smtp.auth=true
spring.mail.smtp.starttls=true
spring.mail.host=smtp.gmail3.com
spring.mail.port=587
spring.mail.protocol=smtp
spring.mail.username=xxxxxxxxxxx@xxxxxx.com
spring.mail.password=ENC(xxxxxxxxxxxxxxxxxxxxxxxxxx)
spring.mail.admin=xxxxxxx@xxxxxxxx.com
...
```

### logback.xml

Fichero para configuar el sistema de log.

Por defecto esta configurado a nivel de "INFO" escribiendo en fichero y en consola.

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<layout class="ch.qos.logback.classic.PatternLayout">

			<Pattern>
				[%-15thread] %d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n
			</Pattern>

		</layout>
	</appender>
		<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>descargaMasiva.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- daily rollover. Make sure the path matches the one in the file element or else
             the rollover logs are placed in the working directory. -->
            <fileNamePattern>descargaMasiva_%d{yyyy-MM-dd}.%i.log</fileNamePattern>

            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>5MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!-- keep 30 days' worth of history -->
            <maxHistory>30</maxHistory>
        </rollingPolicy>

        <encoder>
            <charset>UTF-8</charset>
            <pattern>[%-15thread] %d %-4relative  %-5level %logger{35} - %msg%n</pattern>
        </encoder>
</appender>
	<root level="INFO">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="FILE"/>
	</root>
	
	
	<!-- Hibernate log -->
	<!-- 
	<logger name="org.hibernate" level="DEBUG" />  
    <logger name="org.hibernate.type" level="ALL" />
     -->    
    <Logger name="org.hibernate.orm.deprecation" additivity="false" level="ERROR" />
    <Logger name="io.swagger.models.parameters.AbstractSerializableParameter" additivity="false" level="ERROR" />
    <Logger name="io.swagger.models.parameters.AbstractSerializableParameter" additivity="false" level="WARN" />

</configuration>
```
En la siguiente URL se puede encontrar más información sobre como configurar este fichero: https://logback.qos.ch/documentation.html

### ciudadesAbiertas.properties

Fichero para configuar el acceso a otra BBDD distinta de la del fichero config.properties.


```
#BBDD Configuration

#JNDI SERVER
#db.jndi.name=jdbc/ciudadesAbiertas

##BBDD Configuration
#db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
#db.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
#db.jndi.env=java:comp/env/
#db.schema=datosCiudadesAbiertas

#FIN JNDI SERVER


#Configuracion SQLServer
db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
db.url=jdbc:sqlserver://localhost:1433;databaseName=ciudadesAbiertas

db.user=ciudadesAbiertas
db.password=Primera1
db.schema=ciudadesAbiertas
db.initialSize=5
db.maxActive=10
db.maxIdle=10
db.minIdle=0
db.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
db.validationQuery=Select 1


#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
#RSQL ON / OFF LOG para el paquete de RSQL
rsql.log.active=off
```



# Autores
 - Juan Carlos Ballesteros (Localidata)
 - Carlos Martínez de la Casa (Localidata)



