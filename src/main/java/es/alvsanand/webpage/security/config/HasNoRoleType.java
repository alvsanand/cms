/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.security.config;

/**
 * Class HasNoRoleType.
 * 
 * @version $Revision$ $Date$
 */
public class HasNoRoleType implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1203623604221697607L;


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
	/**
     * Field _items.
     */
    private java.util.Vector<es.alvsanand.webpage.security.config.HasNoRoleTypeItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public HasNoRoleType() {
        super();
        this._items = new java.util.Vector<es.alvsanand.webpage.security.config.HasNoRoleTypeItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vHasNoRoleTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHasNoRoleTypeItem(
            final es.alvsanand.webpage.security.config.HasNoRoleTypeItem vHasNoRoleTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.addElement(vHasNoRoleTypeItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vHasNoRoleTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHasNoRoleTypeItem(
            final int index,
            final es.alvsanand.webpage.security.config.HasNoRoleTypeItem vHasNoRoleTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vHasNoRoleTypeItem);
    }

    /**
     * Method enumerateHasNoRoleTypeItem.
     * 
     * @return an Enumeration over all
     * es.alvsanand.webpage.security.config.HasNoRoleTypeItem
     * elements
     */
    public java.util.Enumeration<? extends es.alvsanand.webpage.security.config.HasNoRoleTypeItem> enumerateHasNoRoleTypeItem(
    ) {
        return this._items.elements();
    }

    /**
     * Method getHasNoRoleTypeItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * es.alvsanand.webpage.security.config.HasNoRoleTypeItem at
     * the given index
     */
    public es.alvsanand.webpage.security.config.HasNoRoleTypeItem getHasNoRoleTypeItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getHasNoRoleTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (es.alvsanand.webpage.security.config.HasNoRoleTypeItem) _items.get(index);
    }

    /**
     * Method getHasNoRoleTypeItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public es.alvsanand.webpage.security.config.HasNoRoleTypeItem[] getHasNoRoleTypeItem(
    ) {
        es.alvsanand.webpage.security.config.HasNoRoleTypeItem[] array = new es.alvsanand.webpage.security.config.HasNoRoleTypeItem[0];
        return (es.alvsanand.webpage.security.config.HasNoRoleTypeItem[]) this._items.toArray(array);
    }

    /**
     * Method getHasNoRoleTypeItemCount.
     * 
     * @return the size of this collection
     */
    public int getHasNoRoleTypeItemCount(
    ) {
        return this._items.size();
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
    public void removeAllHasNoRoleTypeItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeHasNoRoleTypeItem.
     * 
     * @param vHasNoRoleTypeItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeHasNoRoleTypeItem(
            final es.alvsanand.webpage.security.config.HasNoRoleTypeItem vHasNoRoleTypeItem) {
        boolean removed = _items.remove(vHasNoRoleTypeItem);
        return removed;
    }

    /**
     * Method removeHasNoRoleTypeItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public es.alvsanand.webpage.security.config.HasNoRoleTypeItem removeHasNoRoleTypeItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (es.alvsanand.webpage.security.config.HasNoRoleTypeItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vHasNoRoleTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setHasNoRoleTypeItem(
            final int index,
            final es.alvsanand.webpage.security.config.HasNoRoleTypeItem vHasNoRoleTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setHasNoRoleTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vHasNoRoleTypeItem);
    }

    /**
     * 
     * 
     * @param vHasNoRoleTypeItemArray
     */
    public void setHasNoRoleTypeItem(
            final es.alvsanand.webpage.security.config.HasNoRoleTypeItem[] vHasNoRoleTypeItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vHasNoRoleTypeItemArray.length; i++) {
                this._items.add(vHasNoRoleTypeItemArray[i]);
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
     * es.alvsanand.webpage.security.config.HasNoRoleType
     */
    public static es.alvsanand.webpage.security.config.HasNoRoleType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.security.config.HasNoRoleType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.security.config.HasNoRoleType.class, reader);
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
