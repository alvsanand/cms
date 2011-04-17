/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.security.config;

/**
 * Class SecurityConfigType.
 * 
 * @version $Revision$ $Date$
 */
public class SecurityConfigType implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3093641124074408920L;


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/


	/**
     * Field _interceptURLList.
     */
    private java.util.Vector<es.alvsanand.webpage.security.config.InterceptURL> _interceptURLList;

    /**
     * Field _defaultURL.
     */
    private java.lang.String _defaultURL;


      //----------------/
     //- Constructors -/
    //----------------/

    public SecurityConfigType() {
        super();
        this._interceptURLList = new java.util.Vector<es.alvsanand.webpage.security.config.InterceptURL>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vInterceptURL
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInterceptURL(
            final es.alvsanand.webpage.security.config.InterceptURL vInterceptURL)
    throws java.lang.IndexOutOfBoundsException {
        this._interceptURLList.addElement(vInterceptURL);
    }

    /**
     * 
     * 
     * @param index
     * @param vInterceptURL
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInterceptURL(
            final int index,
            final es.alvsanand.webpage.security.config.InterceptURL vInterceptURL)
    throws java.lang.IndexOutOfBoundsException {
        this._interceptURLList.add(index, vInterceptURL);
    }

    /**
     * Method enumerateInterceptURL.
     * 
     * @return an Enumeration over all
     * es.alvsanand.webpage.security.config.InterceptURL elements
     */
    public java.util.Enumeration<? extends es.alvsanand.webpage.security.config.InterceptURL> enumerateInterceptURL(
    ) {
        return this._interceptURLList.elements();
    }

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
     * Method getInterceptURL.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * es.alvsanand.webpage.security.config.InterceptURL at the
     * given index
     */
    public es.alvsanand.webpage.security.config.InterceptURL getInterceptURL(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._interceptURLList.size()) {
            throw new IndexOutOfBoundsException("getInterceptURL: Index value '" + index + "' not in range [0.." + (this._interceptURLList.size() - 1) + "]");
        }

        return (es.alvsanand.webpage.security.config.InterceptURL) _interceptURLList.get(index);
    }

    /**
     * Method getInterceptURL.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public es.alvsanand.webpage.security.config.InterceptURL[] getInterceptURL(
    ) {
        es.alvsanand.webpage.security.config.InterceptURL[] array = new es.alvsanand.webpage.security.config.InterceptURL[0];
        return (es.alvsanand.webpage.security.config.InterceptURL[]) this._interceptURLList.toArray(array);
    }

    /**
     * Method getInterceptURLCount.
     * 
     * @return the size of this collection
     */
    public int getInterceptURLCount(
    ) {
        return this._interceptURLList.size();
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
     */
    public void removeAllInterceptURL(
    ) {
        this._interceptURLList.clear();
    }

    /**
     * Method removeInterceptURL.
     * 
     * @param vInterceptURL
     * @return true if the object was removed from the collection.
     */
    public boolean removeInterceptURL(
            final es.alvsanand.webpage.security.config.InterceptURL vInterceptURL) {
        boolean removed = _interceptURLList.remove(vInterceptURL);
        return removed;
    }

    /**
     * Method removeInterceptURLAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public es.alvsanand.webpage.security.config.InterceptURL removeInterceptURLAt(
            final int index) {
        java.lang.Object obj = this._interceptURLList.remove(index);
        return (es.alvsanand.webpage.security.config.InterceptURL) obj;
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
     * 
     * 
     * @param index
     * @param vInterceptURL
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setInterceptURL(
            final int index,
            final es.alvsanand.webpage.security.config.InterceptURL vInterceptURL)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._interceptURLList.size()) {
            throw new IndexOutOfBoundsException("setInterceptURL: Index value '" + index + "' not in range [0.." + (this._interceptURLList.size() - 1) + "]");
        }

        this._interceptURLList.set(index, vInterceptURL);
    }

    /**
     * 
     * 
     * @param vInterceptURLArray
     */
    public void setInterceptURL(
            final es.alvsanand.webpage.security.config.InterceptURL[] vInterceptURLArray) {
        //-- copy array
        _interceptURLList.clear();

        for (int i = 0; i < vInterceptURLArray.length; i++) {
                this._interceptURLList.add(vInterceptURLArray[i]);
        }
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
     * es.alvsanand.webpage.security.config.SecurityConfigType
     */
    public static es.alvsanand.webpage.security.config.SecurityConfigType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.security.config.SecurityConfigType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.security.config.SecurityConfigType.class, reader);
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
