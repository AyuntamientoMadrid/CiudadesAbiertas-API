
# Cargador de SOLR para realizar búsquedas indexada en la aplicación API REST de Ciudades Abiertas

Esta aplicación sirve para realizar la carga de SOLR a partir de la API desarrollada en la actuación D2.


# Pasos previos a la ejecución

Como requesitos previos se necesita un servidor con SOLR version 7.7 en ejecución.

La API de Ciudades Abiertas también debe estar ejecutandose de manera que el proceso lea todos los datos almacenados en la API y los indexe en el servidor SOLR.

## Requisitos 
Se debe de tener instalado:
* Oracle Java 1.8
* maven >= 3.5.0


## Configuración config.properties
Este es el fichero de configuración necesario para la correcta ejecución de la carga. Aquí se especificarán los parámetros para conectar con la BBDD.


El fichero se encuentra en ../solr_loader_CA/config.properties


### Parámetros del fichero de configuración

**solrCoreURI:** URL donde se encuentra el core donde vamos a realizar la carga de datos. Este campo es obligatorio.

**apiBaseURI:** Dirección base de la API para realizar las peticiones y almacenar la ruta de cada elemento. Este campo es obligatorio.

**datasetsToLoad**: Listado de conjuntos de datos que se van cargar. Separados por "," y sin espacios. Este campo es obligatorio.

### Ejemplo de configuración

solrCoreURI=http://asusoldjc:8983/solr/ciudadesAbiertas

apiBaseURI=http://localhost:8080/API/

datasetsToLoad=subvencion,equipamiento,agenda,agrupacionComercial,licenciaActividad,localComercial,terraza,puntoWifi


# Previos de ejecución en SOLR (Opcional)

A continuación se explican los pasos a seguir en una instalación por defecto de SOLR en un Ubuntu Server 16 

1. Creación del core "ciudadesAbiertas":

```sh
sudo su - solr -c "/opt/solr/bin/solr create -c ciudadesAbiertas  -n data_driven_schema_configs"
```

Si la seguridad se ha habilitado, nos dará un error de autenticación. La manera más rápida es dehabilitar la seguridad temporalmente.

2. Creación de un campo donde se almacenan todas las cadenas de texto concatenadas por cada elemento. Es necesario para las búsquedas globales

```sh
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-copy-field" : {"source":"*","dest":"_text_"}}' http://localhost:8983/solr/ciudadesAbiertas/schema
```

3. Establecimiento de los tipos de algunos campos. Esto ocurre porque SOLR no detecta bien algunos campos, como el teléfono, el lo detecta como número, pero en realidad es un string

```sh
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field": {"name":"telephone", "type":"text_general", "multiValued":false, "stored":true}}' http://localhost:8983/solr/ciudadesAbiertas/schema
```

# Carga de prueba (Opcional)
 
Para realizar una carga de prueba se deben ejecutar los siguientes comandos:

```sh
mvn install
mvn exec:java -Dexec.mainClass="org.ciudadesabiertas.solrLoader.Loader" -Dexec.args="loadTest"
```

Esto carga una serie de ficheros para realizar las pruebas cuando el módulo "busquedaIndexada" esta activado en la API


# Carga de API

Al ejecutar las siguientes lineas empieza el proceso de lanzar peticiones a la API para almacenar cada resultado en SOLR

```sh
mvn install
mvn exec:java -Dexec.mainClass="org.ciudadesabiertas.solrLoader.Loader"
```
 


