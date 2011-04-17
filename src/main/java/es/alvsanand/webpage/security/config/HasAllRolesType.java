/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.security.config;

/**
 * Class HasAllRolesType.
 * 
 * @version $Revision$ $Date$
 */
public class HasAllRolesType implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9185454378099118749L;


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
	/**
     * Field _items.
     */
    private java.util.Vector<es.alvsanand.webpage.security.config.HasAllRolesTypeItem> _items;


      //----------------/
     //- Constructors -/
    //----------------/

    public HasAllRolesType() {
        super();
        this._items = new java.util.Vector<es.alvsanand.webpage.security.config.HasAllRolesTypeItem>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vHasAllRolesTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHasAllRolesTypeItem(
            final es.alvsanand.webpage.security.config.HasAllRolesTypeItem vHasAllRolesTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.addElement(vHasAllRolesTypeItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vHasAllRolesTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHasAllRolesTypeItem(
            final int index,
            final es.alvsanand.webpage.security.config.HasAllRolesTypeItem vHasAllRolesTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        this._items.add(index, vHasAllRolesTypeItem);
    }

    /**
     * Method enumerateHasAllRolesTypeItem.
     * 
     * @return an Enumeration over all
     * es.alvsanand.webpage.security.config.HasAllRolesTypeItem
     * elements
     */
    public java.util.Enumeration<? extends es.alvsanand.webpage.security.config.HasAllRolesTypeItem> enumerateHasAllRolesTypeItem(
    ) {
        return this._items.elements();
    }

    /**
     * Method getHasAllRolesTypeItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * es.alvsanand.webpage.security.config.HasAllRolesTypeItem at
     * the given index
     */
    public es.alvsanand.webpage.security.config.HasAllRolesTypeItem getHasAllRolesTypeItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("getHasAllRolesTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        return (es.alvsanand.webpage.security.config.HasAllRolesTypeItem) _items.get(index);
    }

    /**
     * Method getHasAllRolesTypeItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public es.alvsanand.webpage.security.config.HasAllRolesTypeItem[] getHasAllRolesTypeItem(
    ) {
        es.alvsanand.webpage.security.config.HasAllRolesTypeItem[] array = new es.alvsanand.webpage.security.config.HasAllRolesTypeItem[0];
        return (es.alvsanand.webpage.security.config.HasAllRolesTypeItem[]) this._items.toArray(array);
    }

    /**
     * Method getHasAllRolesTypeItemCount.
     * 
     * @return the size of this collection
     */
    public int getHasAllRolesTypeItemCount(
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
    public void removeAllHasAllRolesTypeItem(
    ) {
        this._items.clear();
    }

    /**
     * Method removeHasAllRolesTypeItem.
     * 
     * @param vHasAllRolesTypeItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeHasAllRolesTypeItem(
            final es.alvsanand.webpage.security.config.HasAllRolesTypeItem vHasAllRolesTypeItem) {
        boolean removed = _items.remove(vHasAllRolesTypeItem);
        return removed;
    }

    /**
     * Method removeHasAllRolesTypeItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public es.alvsanand.webpage.security.config.HasAllRolesTypeItem removeHasAllRolesTypeItemAt(
            final int index) {
        java.lang.Object obj = this._items.remove(index);
        return (es.alvsanand.webpage.security.config.HasAllRolesTypeItem) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vHasAllRolesTypeItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setHasAllRolesTypeItem(
            final int index,
            final es.alvsanand.webpage.security.config.HasAllRolesTypeItem vHasAllRolesTypeItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._items.size()) {
            throw new IndexOutOfBoundsException("setHasAllRolesTypeItem: Index value '" + index + "' not in range [0.." + (this._items.size() - 1) + "]");
        }

        this._items.set(index, vHasAllRolesTypeItem);
    }

    /**
     * 
     * 
     * @param vHasAllRolesTypeItemArray
     */
    public void setHasAllRolesTypeItem(
            final es.alvsanand.webpage.security.config.HasAllRolesTypeItem[] vHasAllRolesTypeItemArray) {
        //-- copy array
        _items.clear();

        for (int i = 0; i < vHasAllRolesTypeItemArray.length; i++) {
                this._items.add(vHasAllRolesTypeItemArray[i]);
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
     * es.alvsanand.webpage.security.config.HasAllRolesType
     */
    public static es.alvsanand.webpage.security.config.HasAllRolesType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.security.config.HasAllRolesType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.security.config.HasAllRolesType.class, reader);
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
