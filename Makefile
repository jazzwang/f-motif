all:	help
help:
	@echo "Usage:"
	@echo " make weblogo - download Weblogo"
	@echo " make build   - Build f-motif java program"
	@echo " make clean   - Remove f-motif java program class files"
	@echo " make update  - Clean up input, output files"

weblogo:
	@wget http://weblogo.berkeley.edu/release/weblogo.2.8.2.tar.gz
	@tar zxvf weblogo.2.8.2.tar.gz
	@rm weblogo.2.8.2.tar.gz
	@apt-get install ghostscript
build:	weblogo
	@javac -cp . fmotif/MotifFinderV6_5.java
	@chown -R www-data:www-data *
	@chown -R www-data:www-data .*

clean:
	@rm fmotif/*.class

update:
	@rm -f output_image/*
	@rm -f output/*
	@rm -f input/[0-9]*
