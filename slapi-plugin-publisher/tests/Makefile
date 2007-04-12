CC = gcc
LD = ld
INCLUDE_FLAGS = -I..

# GSOAP_CFLAGS = pkg-config --cflags gsoap)
GSOAP_CFLAGS = -DLINUX -DWITH_DOM
# GSOAP_LDFLAGS = pkg-config --libs gsoap
GSOAP_LIBS = -lgsoap
CFLAGS = $(INCLUDE_FLAGS) $(GSOAP_CFLAGS) -D_REENTRANT -fPIC -O2 -g

LDFLAGS = 
LIBS = $(GSOAP_LIBS)


all: soap_initialization.o

.c.o:
	$(CC) $(CFLAGS) $(LDFLAGS) -o $@ $? $(LIBS) 

clean:
	-rm -f *.o
