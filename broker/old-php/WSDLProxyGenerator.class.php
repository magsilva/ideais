<?php
/*
 * The contents of this file are of public domain. The original code was
 * obtained from the article 'Using XML, Part 5: SOAP and WSDL', from
 * Adam Delves and published at the PHPBuilder site. The article URL is:
 * 
 * http//www.phpbuilder.com/columns/adam_delves20060606.php3?page=2&print_mode=1
 */

/**
 * When initialised with a WSDL file, the __getTypes() function of the
 * SoapClient object returns useful information about the data types supported
 * by the web service. Being an untyped language, most of this information is
 * irrelevant to us. However, for Document/Literal messagess, THE object data
 * types can still be replicated in PHP and mapped to the data types exposed by
 * the web service.
 * 
 * The proxy generator class creates and returns an instance of the SoapClient.
 * However, prior to this it uses the __getTypes() function to create all the
 * class objects and maps them in the classmap array passed in the SoapClient's
 * constructor.
 */
class WSDLProxyGenerator
{ 
    /**
     * Creates an instance of a SoapClient object and all necessary suporting types.
     * 
     * @param string $clientName The prefix for the classes's names. Default is
     * 'WsdlGeneratedWebService'.
     *
     */
	public static function createSoapClient($wsdlUri, $parms = array(), $prefix='WsdlGeneratedWebService')
	{
		$soap = new SoapClient($wsdlUri);   
		$types = array();
           
		foreach ($soap->__getTypes() as $type) {
			/* match the type information  using a regular expression */
			preg_match("/([a-z0-9_]+)\s+([a-z0-9_]+(\[\])?)(.*)?/si", $type, $matches);
			
			$type = $matches[1];
			switch ($type) {
				/* if the data type is struct, we create a class with this name */
				case 'struct':
					$name = $matches[2];
					$className = $prefix . $name;
					$types[$name] = $className;
					/* Create a dumb class for those not defined. */
					if (! class_exists($className)) {
						eval("class $className {}");
					}
                    break;
			}
		}
		// TODO: Lazy SoapClient instantiation. Remove the class evalution above
		// and wait until the call to instantiate the SoapClient with the mapping.
		// This will give the application the time necessary to create it's own
		// classes. Or check if PHP let you redefine a class later on (so this
		// lazy instantiation is not required).
		
		/* create another instance of the SoapClient, this time using the classmap */
		if (! empty($types)) {
			$parms['classmap'] = $types;
		}           
        return new SoapClient($wsdlUri, $parms);
    }
       
	/* this class cannot be instantiated as an object */
	private function __construct()
	{           
	}           
}
?>