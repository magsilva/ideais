#include <ws/soapH.h>
#include <ws/ns3.nsmap>


int main()
{
	struct _ns1__postMsgToQueue message;
	struct _ns1__postMsgToQueueResponse response;
	struct soap *soap; // gSOAP runtime environment

	message.in0 = "queue/testQueue";
	message.in1 = "teste message";

	soap = soap_new();
	if (!soap) {
		return 1;
	}

	/*
	if (*soap.error) {
		soap_print_fault(soap, stderr);
		exit(1);
	}
	*/
	
	soap_destroy(soap);
	soap_end(soap);
	soap_done(soap);
}
