# Requirement

* OS: Linux - this program is only tested on *Debian/Ubuntu*.
* Java: JDK (javac) is required to compile java source code
* make: 
* PHP
* Web Server: apache or nginx

## (Optional) Test with Docker

* If you already have debian or ubuntu installed, please skip this step.
```
~$ docker run -it --name f-motif -h f-motif -p 80:80 debian:latest /bin/bash
root@f-motif:/# cat /etc/debian_version 
8.7
root@f-motif:/# cd
```

# Installation 

* install related packages

```
root@f-motif:~# apt-get update                            
root@f-motif:~# apt-get -y install apache2 libapache2-mod-php5 make ghostscript openjdk-7-jdk wget locales git
root@f-motif:~# echo "zh_TW.UTF-8 UTF-8" >> /etc/locale.gen
root@f-motif:~# locale-gen zh_TW.UTF-8
root@f-motif:~# export LC_ALL=zh_TW.UTF-8
root@f-motif:~# cd /var/www/html/
root@f-motif:/var/www/html# git clone https://github.com/jazzwang/f-motif.git
root@f-motif:/var/www/html# make clean; make weblogo; make build; make update
root@f-motif:~# /etc/init.d/apache2 start
```

* check http://YOUR-IP/ for f-motif Web UI
