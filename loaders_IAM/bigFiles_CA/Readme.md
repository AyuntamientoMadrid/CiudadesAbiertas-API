
# Big Files Generator

Este proyecto se utiliza para conectarse a una esquema de base de datos que contenga las tablas del API Ciudades Abiertas.

Una vez conectado generará la información de los distintos dataset en formato RDF y/o JSON

## Requerimientos

  - Java 1.8 (compatible con OpenJDK 11)
  - Git
  - Maven
  - Sistema de relacional de BBDD: Oracle, SQLServer y MySQL

## Ficheros de Configuración

En este proyecto existen tres ficheros de configuración:

    - hibernate.properties
    - config.properties
    - logback.xml
    
### hibernate.properties

Fichero donde se establece la conexion con la base de datos. A continuación se muestra un ejemplo configurado para MySQL:

```
hibernate.connection.driver_class=com.mysql.jdbc.Driver
hibernate.connection.url=jdbc:mysql://asusoldjc:3306/ciudadesAbiertas
hibernate.connection.username=ciudadesAbiertas
hibernate.connection.password=ciudadesAbiertas
hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

El atributo hibernate.connection.password se puede pasar de manera encriptada. Ejemplo:

hibernate.connection.password=ENC(1wiazHWG67Uq8ObFSNBXGTUaIK5MgZKNBGzCSmN8FK0=)

Para encriptar una contraseña se puede mirar en el anexo 1.

### config.properties

Fichero que contiene información para la ejecución del proyecto, así como la lista de los datasets que se quieren exportar:

```
#URI Base del servidor donde esta el API
URIBase="https://ciudadesabiertas.es";
#Contexto del API
context="/OpenCitiesAPI";
#generacion formato JSON (true/false)
formatoJSON=true
#generacion formato RDF (true/false)
formatoRDF=true
#borrar el contenido de los directorios rdfOutput y jsonOutput al comenzar (true/false)
deleteOutputFolderOnInit=true

#datasets a exportar
agendaCultural=true
alojamiento=false
aparcamiento=false
avisoQuejaSugerencia=false
calidadAireEstacion=false
calidadAireObservacion=false
calidadAireSensor=false
callejeroPortal=false
callejeroTramoVia=false
callejeroVia=false
equipamiento=false
instalacionDeportiva=false
localComercialAgrupacionComercial=false
localComercialLicenciaActividad=false
localComercial=false
localComercialTerraza=false
monumento=false
organigrama=false
puntoWifi=false
puntoInteresTuristico=false
subvencion=false
tramite=true
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
		<file>bigFiles.log</file>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!-- daily rollover. Make sure the path matches the one in the file element or else the rollover logs are placed in the working directory. -->
			<fileNamePattern>bigFiles_%d{yyyy-MM-dd}.%i.log</fileNamePattern>

			<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
				<maxFileSize>5MB</maxFileSize>
			</timeBasedFileNamingAndTriggeringPolicy>
			<!-- keep 30 days' worth of history -->
			<maxHistory>30</maxHistory>
		</rollingPolicy>

		<encoder>
			<charset>UTF-8</charset>
			<pattern>[%-15thread] %d %-4relative %-5level %logger{35} - %msg%n
			</pattern>
		</encoder>
	</appender>
	<root level="INFO">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="FILE" />
	</root>


	<!--Disabled Logs -->
	<Logger name="org.hibernate.orm.deprecation" additivity="false" level="ERROR" />
	<Logger name="io.swagger.models.parameters.AbstractSerializableParameter" additivity="false" level="ERROR" />

</configuration>
```


En la siguiente URL se puede encontrar más información sobre como configurar este fichero: https://logback.qos.ch/documentation.html


## Anexo 1: encriptar contraseña de BBDD

## Encriptación de contraseña de BBDD.

Si se desea, se puede encriptar la contraseña de la BBDD  que se introduce en el fichero hibernate.properties


Dentro del directorio bigFiles_CA (Y previa compilación del mismo: **mvn install**) ejecutar

```sh
mvn exec:java -Dexec.mainClass="org.ciudadesabiertas.utils.EncriptStringProperties"  -Dexec.args="TEXTOAENCRIPTAR"
```

El texto que aparece en consola será el siguiente:

```sh
This will encript all arguments
Now we have 1 arguments

Encripted:              B7KzbeM7EvFSULpAAT35bcujA+y8gSXC
Desencripted:   TEXTOAENCRIPTAR
For use in properties: ENC(B7KzbeM7EvFSULpAAT35bcujA+y8gSXC)
```



# Autores
 - Juan Carlos Ballesteros (Localidata)
 - Carlos Martínez de la Casa (Localidata)
 - Oscar Corcho (UPM, Localidata)


# Licencia
Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es

This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).

Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
You may not use this work except in compliance with the Licence.
You may obtain a copy of the Licence at:

https://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the Licence for the specific language governing permissions and limitations under the Licence.