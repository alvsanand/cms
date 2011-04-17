/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.web.mobile.config;

/**
 * Class MobileConfigType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class MobileConfigType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _noMobilePattern.
     */
    private java.lang.String _noMobilePattern;

    /**
     * Field _defaultURL.
     */
    private java.lang.String _defaultURL;

    /**
     * Field _excludedResources.
     */
    private es.alvsanand.webpage.web.mobile.config.ExcludedResources _excludedResources;


      //----------------/
     //- Constructors -/
    //----------------/

    public MobileConfigType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'defaultURL'.
     * 
     * @return the value of field 'DefaultURL'.
     */
    public java.lang.String getDefaultURL(
    ) {
        return this._defaultURL;
    }

    /**
     * Returns the value of field 'excludedResources'.
     * 
     * @return the value of field 'ExcludedResources'.
     */
    public es.alvsanand.webpage.web.mobile.config.ExcludedResources getExcludedResources(
    ) {
        return this._excludedResources;
    }

    /**
     * Returns the value of field 'noMobilePattern'.
     * 
     * @return the value of field 'NoMobilePattern'.
     */
    public java.lang.String getNoMobilePattern(
    ) {
        return this._noMobilePattern;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'defaultURL'.
     * 
     * @param defaultURL the value of field 'defaultURL'.
     */
    public void setDefaultURL(
            final java.lang.String defaultURL) {
        this._defaultURL = defaultURL;
    }

    /**
     * Sets the value of field 'excludedResources'.
     * 
     * @param excludedResources the value of field
     * 'excludedResources'.
     */
    public void setExcludedResources(
            final es.alvsanand.webpage.web.mobile.config.ExcludedResources excludedResources) {
        this._excludedResources = excludedResources;
    }

    /**
     * Sets the value of field 'noMobilePattern'.
     * 
     * @param noMobilePattern the value of field 'noMobilePattern'.
     */
    public void setNoMobilePattern(
            final java.lang.String noMobilePattern) {
        this._noMobilePattern = noMobilePattern;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * es.alvsanand.webpage.web.mobile.config.MobileConfigType
     */
    public static es.alvsanand.webpage.web.mobile.config.MobileConfigType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.web.mobile.config.MobileConfigType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.web.mobile.config.MobileConfigType.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
