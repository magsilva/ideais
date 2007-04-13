CC = gcc
LD = ld
INCLUDE_FLAGS = -I.

# GSOAP_CFLAGS = pkg-config --cflags gsoap)
GSOAP_CFLAGS = -DLINUX -DWITH_DOM
# GSOAP_LDFLAGS = pkg-config --libs gsoap
GSOAP_LIBS = -lgsoap
GSOAP_CLIENT_OBJS = ws/soapClient.o ws/soapC.o

CFLAGS = $(INCLUDE_FLAGS) $(GSOAP_CFLAGS) -D_REENTRANT -O2 -g

LDFLAGS = 
LIBS = $(GSOAP_LIBS)


all: $(GSOAP_CLIENT_OBJS) soap_initialization.o
	$(CC) $(CFLAGS) -o soap_initialization soap_initialization.o $(GSOAP_CLIENT_OBJS) $(LIBS)

%.c.%.o:
	$(CC) $(CFLAGS) $(LDFLAGS) -c -o $@ $<

clean:
	-rm -f *.o
