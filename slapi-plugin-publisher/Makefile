CC = cc
LD = ld
INCLUDE_FLAGS = -I.
CFLAGS = $(INCLUDE_FLAGS) -D_REENTRANT -fPIC
LDFLAGS = -g -shared
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
	$(LD) $(LDFLAGS) -o $@ $(OBJS)

.c.o:
	$(CC) $(CFLAGS) -c $<

clean:
	-rm -f *.o *.so
	-rm -rf ws
