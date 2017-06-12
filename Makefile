all: test.exe	
	./test.exe || /bin/true

test.exe: test.o runtime.o
	gcc runtime.o test.o -o test.exe

runtime.o: runtime.c
	gcc runtime.c -c

test.o: test.asm
	as test.asm -c -o test.o
	
test.asm: test.r1 
	java -cp target/minpiler-1.0-jar-with-dependencies.jar minpiler.Compiler test.r1 -v > minpiler.log

clean:
	rm -f runtime.o test.asm test.o text.exe minpiler.log

linecount:
	find src/. -type f -name '*.java' -exec cat {} + | wc -l