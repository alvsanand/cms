/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.security.config;

/**
 * Class InterceptURLType.
 * 
 * @version $Revision$ $Date$
 */
public class InterceptURLType implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8242081923316673405L;

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
	/**
     * Field _pattern.
     */
    private java.lang.String _pattern;

    /**
     * Field _hasAllRoles.
     */
    private es.alvsanand.webpage.security.config.HasAllRoles _hasAllRoles;

    /**
     * Field _hasAnyRole.
     */
    private es.alvsanand.webpage.security.config.HasAnyRole _hasAnyRole;

    /**
     * Field _hasNoRole.
     */
    private es.alvsanand.webpage.security.config.HasNoRole _hasNoRole;

    /**
     * Field _authenticated.
     */
    private boolean _authenticated;

    /**
     * keeps track of state for field: _authenticated
     */
    private boolean _has_authenticated;


      //----------------/
     //- Constructors -/
    //----------------/

    public InterceptURLType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteAuthenticated(
    ) {
        this._has_authenticated= false;
    }

    /**
     * Returns the value of field 'authenticated'.
     * 
     * @return the value of field 'Authenticated'.
     */
    public boolean getAuthenticated(
    ) {
        return this._authenticated;
    }

    /**
     * Returns the value of field 'hasAllRoles'.
     * 
     * @return the value of field 'HasAllRoles'.
     */
    public es.alvsanand.webpage.security.config.HasAllRoles getHasAllRoles(
    ) {
        return this._hasAllRoles;
    }

    /**
     * Returns the value of field 'hasAnyRole'.
     * 
     * @return the value of field 'HasAnyRole'.
     */
    public es.alvsanand.webpage.security.config.HasAnyRole getHasAnyRole(
    ) {
        return this._hasAnyRole;
    }

    /**
     * Returns the value of field 'hasNoRole'.
     * 
     * @return the value of field 'HasNoRole'.
     */
    public es.alvsanand.webpage.security.config.HasNoRole getHasNoRole(
    ) {
        return this._hasNoRole;
    }

    /**
     * Returns the value of field 'pattern'.
     * 
     * @return the value of field 'Pattern'.
     */
    public java.lang.String getPattern(
    ) {
        return this._pattern;
    }

    /**
     * Method hasAuthenticated.
     * 
     * @return true if at least one Authenticated has been added
     */
    public boolean hasAuthenticated(
    ) {
        return this._has_authenticated;
    }

    /**
     * Returns the value of field 'authenticated'.
     * 
     * @return the value of field 'Authenticated'.
     */
    public boolean isAuthenticated(
    ) {
        return this._authenticated;
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
     * Sets the value of field 'authenticated'.
     * 
     * @param authenticated the value of field 'authenticated'.
     */
    public void setAuthenticated(
            final boolean authenticated) {
        this._authenticated = authenticated;
        this._has_authenticated = true;
    }

    /**
     * Sets the value of field 'hasAllRoles'.
     * 
     * @param hasAllRoles the value of field 'hasAllRoles'.
     */
    public void setHasAllRoles(
            final es.alvsanand.webpage.security.config.HasAllRoles hasAllRoles) {
        this._hasAllRoles = hasAllRoles;
    }

    /**
     * Sets the value of field 'hasAnyRole'.
     * 
     * @param hasAnyRole the value of field 'hasAnyRole'.
     */
    public void setHasAnyRole(
            final es.alvsanand.webpage.security.config.HasAnyRole hasAnyRole) {
        this._hasAnyRole = hasAnyRole;
    }

    /**
     * Sets the value of field 'hasNoRole'.
     * 
     * @param hasNoRole the value of field 'hasNoRole'.
     */
    public void setHasNoRole(
            final es.alvsanand.webpage.security.config.HasNoRole hasNoRole) {
        this._hasNoRole = hasNoRole;
    }

    /**
     * Sets the value of field 'pattern'.
     * 
     * @param pattern the value of field 'pattern'.
     */
    public void setPattern(
            final java.lang.String pattern) {
        this._pattern = pattern;
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
     * es.alvsanand.webpage.security.config.InterceptURLType
     */
    public static es.alvsanand.webpage.security.config.InterceptURLType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.security.config.InterceptURLType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.security.config.InterceptURLType.class, reader);
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
