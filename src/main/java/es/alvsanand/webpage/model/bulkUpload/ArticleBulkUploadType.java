/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package es.alvsanand.webpage.model.bulkUpload;

/**
 * Class ArticleBulkUploadType.
 * 
 * @version $Revision$ $Date$
 */
public class ArticleBulkUploadType implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2187469822561877008L;


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
	/**
     * Field _articleList.
     */
    private java.util.Vector<es.alvsanand.webpage.model.bulkUpload.Article> _articleList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ArticleBulkUploadType() {
        super();
        this._articleList = new java.util.Vector<es.alvsanand.webpage.model.bulkUpload.Article>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vArticle
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addArticle(
            final es.alvsanand.webpage.model.bulkUpload.Article vArticle)
    throws java.lang.IndexOutOfBoundsException {
        // check for the maximum size
        if (this._articleList.size() >= 10) {
            throw new IndexOutOfBoundsException("addArticle has a maximum of 10");
        }

        this._articleList.addElement(vArticle);
    }

    /**
     * 
     * 
     * @param index
     * @param vArticle
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addArticle(
            final int index,
            final es.alvsanand.webpage.model.bulkUpload.Article vArticle)
    throws java.lang.IndexOutOfBoundsException {
        // check for the maximum size
        if (this._articleList.size() >= 10) {
            throw new IndexOutOfBoundsException("addArticle has a maximum of 10");
        }

        this._articleList.add(index, vArticle);
    }

    /**
     * Method enumerateArticle.
     * 
     * @return an Enumeration over all
     * es.alvsanand.webpage.model.bulkUpload.Article elements
     */
    public java.util.Enumeration<? extends es.alvsanand.webpage.model.bulkUpload.Article> enumerateArticle(
    ) {
        return this._articleList.elements();
    }

    /**
     * Method getArticle.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * es.alvsanand.webpage.model.bulkUpload.Article at the given
     * index
     */
    public es.alvsanand.webpage.model.bulkUpload.Article getArticle(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._articleList.size()) {
            throw new IndexOutOfBoundsException("getArticle: Index value '" + index + "' not in range [0.." + (this._articleList.size() - 1) + "]");
        }

        return (es.alvsanand.webpage.model.bulkUpload.Article) _articleList.get(index);
    }

    /**
     * Method getArticle.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public es.alvsanand.webpage.model.bulkUpload.Article[] getArticle(
    ) {
        es.alvsanand.webpage.model.bulkUpload.Article[] array = new es.alvsanand.webpage.model.bulkUpload.Article[0];
        return (es.alvsanand.webpage.model.bulkUpload.Article[]) this._articleList.toArray(array);
    }

    /**
     * Method getArticleCount.
     * 
     * @return the size of this collection
     */
    public int getArticleCount(
    ) {
        return this._articleList.size();
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
    public void removeAllArticle(
    ) {
        this._articleList.clear();
    }

    /**
     * Method removeArticle.
     * 
     * @param vArticle
     * @return true if the object was removed from the collection.
     */
    public boolean removeArticle(
            final es.alvsanand.webpage.model.bulkUpload.Article vArticle) {
        boolean removed = _articleList.remove(vArticle);
        return removed;
    }

    /**
     * Method removeArticleAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public es.alvsanand.webpage.model.bulkUpload.Article removeArticleAt(
            final int index) {
        java.lang.Object obj = this._articleList.remove(index);
        return (es.alvsanand.webpage.model.bulkUpload.Article) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vArticle
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setArticle(
            final int index,
            final es.alvsanand.webpage.model.bulkUpload.Article vArticle)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._articleList.size()) {
            throw new IndexOutOfBoundsException("setArticle: Index value '" + index + "' not in range [0.." + (this._articleList.size() - 1) + "]");
        }

        this._articleList.set(index, vArticle);
    }

    /**
     * 
     * 
     * @param vArticleArray
     */
    public void setArticle(
            final es.alvsanand.webpage.model.bulkUpload.Article[] vArticleArray) {
        //-- copy array
        _articleList.clear();

        for (int i = 0; i < vArticleArray.length; i++) {
                this._articleList.add(vArticleArray[i]);
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
     * es.alvsanand.webpage.model.bulkUpload.ArticleBulkUploadType
     */
    public static es.alvsanand.webpage.model.bulkUpload.ArticleBulkUploadType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (es.alvsanand.webpage.model.bulkUpload.ArticleBulkUploadType) org.exolab.castor.xml.Unmarshaller.unmarshal(es.alvsanand.webpage.model.bulkUpload.ArticleBulkUploadType.class, reader);
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
