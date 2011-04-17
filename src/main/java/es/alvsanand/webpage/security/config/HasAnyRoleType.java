/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.security.config;

/**
 * Class HasAnyRoleType.
 * 
 * @version $Revision$ $Date$
 */
public class HasAnyRoleType implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4278911947224053809L;


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
	/**
     * Field _items.
     */
    private java.util.Vector<es.alvsanand.webpage.security.config.HasAnyRoleTypeItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public HasAnyRoleType() {
        super();
        this._items = new java.util.Vector<es.alvsanand.webpage.security.config.HasAnyRoleTypeItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vHasAnyRoleTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHasAnyRoleTypeItem(
            final es.alvsanand.webpage.security.config.HasAnyRoleTypeItem vHasAnyRoleTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.addElement(vHasAnyRoleTypeItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vHasAnyRoleTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHasAnyRoleTypeItem(
            final int index,
            final es.alvsanand.webpage.security.config.HasAnyRoleTypeItem vHasAnyRoleTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vHasAnyRoleTypeItem);
    }

    /**
     * Method enumerateHasAnyRoleTypeItem.
     * 
     * @return an Enumeration over all
     * es.alvsanand.webpage.security.config.HasAnyRoleTypeItem
     * elements
     */
    public java.util.Enumeration<? extends es.alvsanand.webpage.security.config.HasAnyRoleTypeItem> enumerateHasAnyRoleTypeItem(
    ) {
        return this._items.elements();
    }

    /**
     * Method getHasAnyRoleTypeItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * es.alvsanand.webpage.security.config.HasAnyRoleTypeItem at
     * the given index
     */
    public es.alvsanand.webpage.security.config.HasAnyRoleTypeItem getHasAnyRoleTypeItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getHasAnyRoleTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (es.alvsanand.webpage.security.config.HasAnyRoleTypeItem) _items.get(index);
    }

    /**
     * Method getHasAnyRoleTypeItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public es.alvsanand.webpage.security.config.HasAnyRoleTypeItem[] getHasAnyRoleTypeItem(
    ) {
        es.alvsanand.webpage.security.config.HasAnyRoleTypeItem[] array = new es.alvsanand.webpage.security.config.HasAnyRoleTypeItem[0];
        return (es.alvsanand.webpage.security.config.HasAnyRoleTypeItem[]) this._items.toArray(array);
    }

    /**
     * Method getHasAnyRoleTypeItemCount.
     * 
     * @return the size of this collection
     */
    public int getHasAnyRoleTypeItemCount(
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
    public void removeAllHasAnyRoleTypeItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeHasAnyRoleTypeItem.
     * 
     * @param vHasAnyRoleTypeItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeHasAnyRoleTypeItem(
            final es.alvsanand.webpage.security.config.HasAnyRoleTypeItem vHasAnyRoleTypeItem) {
        boolean removed = _items.remove(vHasAnyRoleTypeItem);
        return removed;
    }

    /**
     * Method removeHasAnyRoleTypeItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public es.alvsanand.webpage.security.config.HasAnyRoleTypeItem removeHasAnyRoleTypeItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (es.alvsanand.webpage.security.config.HasAnyRoleTypeItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vHasAnyRoleTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setHasAnyRoleTypeItem(
            final int index,
            final es.alvsanand.webpage.security.config.HasAnyRoleTypeItem vHasAnyRoleTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setHasAnyRoleTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vHasAnyRoleTypeItem);
    }

    /**
     * 
     * 
     * @param vHasAnyRoleTypeItemArray
     */
    public void setHasAnyRoleTypeItem(
            final es.alvsanand.webpage.security.config.HasAnyRoleTypeItem[] vHasAnyRoleTypeItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vHasAnyRoleTypeItemArray.length; i++) {
                this._items.add(vHasAnyRoleTypeItemArray[i]);
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
     * es.alvsanand.webpage.security.config.HasAnyRoleType
     */
    public static es.alvsanand.webpage.security.config.HasAnyRoleType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.security.config.HasAnyRoleType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.security.config.HasAnyRoleType.class, reader);
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
