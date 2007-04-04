CC = cc
LD = ld
INCLUDE_FLAGS = -I.
CFLAGS = $(INCLUDE_FLAGS) -D_REENTRANT -fPIC
LDFLAGS = -g -shared
OBJS = slapi-plugin-publisher.o

all: slapi-plugin-publisher.so

slapi-plugin-publisher.so: $(OBJS)
	$(LD) $(LDFLAGS) -o $@ $(OBJS)

.c.o:
	$(CC) $(CFLAGS) -c $<

clean:
	-rm -f *.o *.so
