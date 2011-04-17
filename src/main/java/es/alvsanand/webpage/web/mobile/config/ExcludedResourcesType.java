/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.web.mobile.config;

/**
 * Class ExcludedResourcesType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class ExcludedResourcesType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _urlPatternList.
     */
    private java.util.Vector<java.lang.String> _urlPatternList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ExcludedResourcesType() {
        super();
        this._urlPatternList = new java.util.Vector<java.lang.String>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vUrlPattern
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addUrlPattern(
            final java.lang.String vUrlPattern)
    throws java.lang.IndexOutOfBoundsException {
        this._urlPatternList.addElement(vUrlPattern);
    }

    /**
     * 
     * 
     * @param index
     * @param vUrlPattern
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addUrlPattern(
            final int index,
            final java.lang.String vUrlPattern)
    throws java.lang.IndexOutOfBoundsException {
        this._urlPatternList.add(index, vUrlPattern);
    }

    /**
     * Method enumerateUrlPattern.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration<? extends java.lang.String> enumerateUrlPattern(
    ) {
        return this._urlPatternList.elements();
    }

    /**
     * Method getUrlPattern.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getUrlPattern(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._urlPatternList.size()) {
            throw new IndexOutOfBoundsException("getUrlPattern: Index value '" + index + "' not in range [0.." + (this._urlPatternList.size() - 1) + "]");
        }

        return (java.lang.String) _urlPatternList.get(index);
    }

    /**
     * Method getUrlPattern.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getUrlPattern(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._urlPatternList.toArray(array);
    }

    /**
     * Method getUrlPatternCount.
     * 
     * @return the size of this collection
     */
    public int getUrlPatternCount(
    ) {
        return this._urlPatternList.size();
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
    public void removeAllUrlPattern(
    ) {
        this._urlPatternList.clear();
    }

    /**
     * Method removeUrlPattern.
     * 
     * @param vUrlPattern
     * @return true if the object was removed from the collection.
     */
    public boolean removeUrlPattern(
            final java.lang.String vUrlPattern) {
        boolean removed = _urlPatternList.remove(vUrlPattern);
        return removed;
    }

    /**
     * Method removeUrlPatternAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeUrlPatternAt(
            final int index) {
        java.lang.Object obj = this._urlPatternList.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vUrlPattern
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setUrlPattern(
            final int index,
            final java.lang.String vUrlPattern)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._urlPatternList.size()) {
            throw new IndexOutOfBoundsException("setUrlPattern: Index value '" + index + "' not in range [0.." + (this._urlPatternList.size() - 1) + "]");
        }

        this._urlPatternList.set(index, vUrlPattern);
    }

    /**
     * 
     * 
     * @param vUrlPatternArray
     */
    public void setUrlPattern(
            final java.lang.String[] vUrlPatternArray) {
        //-- copy array
        _urlPatternList.clear();

        for (int i = 0; i < vUrlPatternArray.length; i++) {
                this._urlPatternList.add(vUrlPatternArray[i]);
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
     * es.alvsanand.webpage.web.mobile.config.ExcludedResourcesType
     */
    public static es.alvsanand.webpage.web.mobile.config.ExcludedResourcesType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.web.mobile.config.ExcludedResourcesType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.web.mobile.config.ExcludedResourcesType.class, reader);
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
