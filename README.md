# automatic-watering-system-for-plants
Automatic Watering System for plants - written on Java and Groovy - eclipse kura

### How to open Kura GoGo shell
```sh
telnet 127.0.0.1 5002
```

### How to build
```sh
mvn clean package
```

####
```sh
# https://pinout.xyz
# -g will use BCM_GPIO numbering scheme
# -1 will use physical pin numbering scheme
# wiringPi pin numbering scheme
gpio readall
gpio -1 mode 36 output
gpio -1 write 36 1
```

http://central.maven.org/maven2/org/apache/camel/camel-quartz2/2.17.2/camel-quartz2-2.17.2.jar
http://central.maven.org/maven2/org/quartz-scheduler/quartz/2.2.2/quartz-2.2.2.jar

http://www.instructables.com/id/Arduino-Automatic-Watering-System-For-Plants/