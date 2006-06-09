o<?php
/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2006 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/


require_once("DocumentSoapClient.class.php");

class BrokerClient
{
	/**
	 * The WSDL for the broker currently in use.
	 */
	private $wsdl;	

	/**
	 * The SoapClient for the broker.
	 */
	private $broker;

	/**
	 * Enable/Disable the output of debug information. This is most useful
	 * for the message exchange: it allows the output of the raw XML
	 * document transmitted.
	 */
	private $debug = True;
	
	/**
	 * SOAP version to be used. The default is 'SOAP_1_1'. Possible values are
	 * 'SOAP_1_1' and 'SOAP_1_2'.
	 */
	private $soapVersion = 'SOAP_1_1';

	/**
	 * Connection (request) timeout.
	 */
	private $connectionTimeout = 30;
	
	/**
	 * Request timeout (how long the request takes to finish).
	 */
	private $requestTimeout = 90;

	/**
	 * The Web Service location. This is always informed in the WSDL. However,
	 * if the same service is deployed in several places, the location set in
	 * the WSDL must also be changed (and that sucks). So you can set an
	 * alternative location, so you don't have to bother about updating the
	 * WSDL.
	 */
	private $location;
	
	
	/**
	 * Enable the use of compression in the HTTP transport layer.
	 */
	private $enableCompression = False;
	
	/**
	 * Create a new client for the broker that announces itself at $url. Any
	 * option you may want to set in the SOAP client is done here. Take a look
	 * in "http://www.php.net/manual/en/function.soap-soapclient-construct.php"
	 * for more information about the available options.
		
	 * @param $url [string] The URL for the broker.
	 */
	public function __construct($url, $prefix = "WS")
	{
		$this->wsdl = $url;
		
		$parms = array();
		if ($this->enableCompression) {
			$parms['compression'] = SOAP_COMPRESSION_ACCEPT | SOAP_COMPRESSION_GZIP;
		}
		$parms['connection_timeout'] = $this->connectionTimeout;
		$parms['exception'] = true;
		if (!empty($this->location)) $parms['location'] = $this->location;
		$parms['soap_version'] = $this->soapVersion;
		$parms['trace'] = $this->debug;
		if ($this->debug) {
			ini_set("soap.wsdl_cache_enabled", "0");
		}

		ini_set('default_socket_timeout', $this->requestTimeout);
		
		$this->broker = WSDLProxyGenerator::createSoapClient($this->wsdl, $parms, $prefix);
	}
	
	public function poke()
	{
		try {
			if ($this->broker->ping()) {
				echo("The service is up and running."); 
			} else {
				echo("The service is there, but don't want to be disturbed.");
			}
		} catch (SoapFault $e) {
			echo("The service isn't there.");
			echo($e->faultstring);
		}
	}	
	
	/**
	 * Discover the available brokers in the network.
	 * 
	 * @return Array with the URL for the WSDL of each broker.
	 */
	public static function discoverBrokers()
	{
		$brokers = Array();
	
		// TODO: This is hardcoded just for test purposes. This method should
		// employ Zero Network Configuration service discovery (or any other
		// method) or, as the very last resource, a hard coded value set on
		// a configuration file. We really don't want to rely on configuration
		// files, things should be plug'n'play.
		$brokers[] = "http://localhost/~magsilva/Projects/ideais/broker/broker.wsdl";
		
		return $brokers;
	}
}

?>
