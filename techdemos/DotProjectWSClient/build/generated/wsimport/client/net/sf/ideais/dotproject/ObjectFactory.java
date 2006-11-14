
package net.sf.ideais.dotproject;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.ideais.dotproject package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTaskResponse_QNAME = new QName("http://dotproject.ideais.sf.net/", "GetTaskResponse");
    private final static QName _SampleOperation_QNAME = new QName("http://dotproject.ideais.sf.net/", "sample_operation");
    private final static QName _GetTask_QNAME = new QName("http://dotproject.ideais.sf.net/", "GetTask");
    private final static QName _SampleOperationResponse_QNAME = new QName("http://dotproject.ideais.sf.net/", "sample_operationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.ideais.dotproject
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SampleOperationResponse }
     * 
     */
    public SampleOperationResponse createSampleOperationResponse() {
        return new SampleOperationResponse();
    }

    /**
     * Create an instance of {@link Task }
     * 
     */
    public Task createTask() {
        return new Task();
    }

    /**
     * Create an instance of {@link GetTaskResponse }
     * 
     */
    public GetTaskResponse createGetTaskResponse() {
        return new GetTaskResponse();
    }

    /**
     * Create an instance of {@link SampleOperation }
     * 
     */
    public SampleOperation createSampleOperation() {
        return new SampleOperation();
    }

    /**
     * Create an instance of {@link GetTask }
     * 
     */
    public GetTask createGetTask() {
        return new GetTask();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dotproject.ideais.sf.net/", name = "GetTaskResponse")
    public JAXBElement<GetTaskResponse> createGetTaskResponse(GetTaskResponse value) {
        return new JAXBElement<GetTaskResponse>(_GetTaskResponse_QNAME, GetTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SampleOperation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dotproject.ideais.sf.net/", name = "sample_operation")
    public JAXBElement<SampleOperation> createSampleOperation(SampleOperation value) {
        return new JAXBElement<SampleOperation>(_SampleOperation_QNAME, SampleOperation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dotproject.ideais.sf.net/", name = "GetTask")
    public JAXBElement<GetTask> createGetTask(GetTask value) {
        return new JAXBElement<GetTask>(_GetTask_QNAME, GetTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SampleOperationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dotproject.ideais.sf.net/", name = "sample_operationResponse")
    public JAXBElement<SampleOperationResponse> createSampleOperationResponse(SampleOperationResponse value) {
        return new JAXBElement<SampleOperationResponse>(_SampleOperationResponse_QNAME, SampleOperationResponse.class, null, value);
    }

}
