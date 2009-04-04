all:
	javac -cp . MotifFinderV5.java
	wget http://weblogo.berkeley.edu/release/weblogo.2.8.2.tar.gz
	tar zxvf weblogo.2.8.2.tar.gz
	rm weblogo.2.8.2.tar.gz

clean:
	rm *.class

update:
	rm output_image/*
	rm output/*
	rm input/0*
