# social-app-TACS

#### Pre-Requisitos
* Java 7 JDK
* Maven

**Java JDK**

En caso de no tener Java 7 en linux se recomienda utilizar la JDK de Oracle.

Si se esta en *Linux* se puede instalar con:

***Ubuntu/Mint***
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer
```

***Debian***
```
su -
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
apt-get update
apt-get install oracle-java7-installer
exit
```

Caso de estar en *Windows* descargarla de la pagina oficial: [Aqui](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

**Maven**

Para maven bajar de [Aqui](http://mirrors.nxnethosting.com/apache/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.zip). Descomprimir y luego seguir las instrucciones de su README.


#### Levantar servidor

Para levantar el servidor alcanza con correr el siguiente comand en la carpeta donde se clono/descargo el proyecto
(el binario de git debe ser accesible desde la consola y debe haber una conexión a internet)

```
mvn jetty:run
```
