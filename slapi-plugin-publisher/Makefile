CC = gcc
LD = ld
INCLUDE_FLAGS = -I.

# GSOAP_CFLAGS = pkg-config --cflags gsoap)
GSOAP_CFLAGS = -DLINUX -DWITH_DOM
# GSOAP_LDFLAGS = pkg-config --libs gsoap
GSOAP_LIBS = -lgsoap
CFLAGS = $(INCLUDE_FLAGS) $(GSOAP_CFLAGS) -D_REENTRANT -fPIC -O2 -g

# SLAPI
SLAPI_LIBS = -lslapi-2.2

LDFLAGS = -shared
LIBS = $(GSOAP_LIBS) $(SLAPI_LIBS)
OBJS = slapi-plugin-publisher.o
TARGET = /usr/lib/ldap
WSDL = http://localhost:8191/JMSService?wsdl


all: ws slapi-plugin-publisher.so

ws:
	-mkdir ws
	wsdl2h -c -o ws/jms2webservice.h $(WSDL)
	soapcpp2 -C -c -x -w -d ws ws/jms2webservice.h
	

install: all
	cp slapi-plugin-publisher.so $(TARGET)/plugin-publisher.so

slapi-plugin-publisher.so: $(OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) -o $@ $(OBJS) $(LIBS)

.c.o:
	$(CC) $(CFLAGS) -c $<

clean:
	-rm -f *.o *.so
	-rm -rf ws
