package es.alvsanand.webpage.web.fileupload.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileItemHeadersSupport;
import org.apache.commons.fileupload.ParameterParser;

public class MemoryFileItem implements FileItemHeadersSupport, FileItem {
	private static final long serialVersionUID = -4111198009231781933L;

	public static final String DEFAULT_CHARSET = "ISO-8859-1";
    
	private ByteArrayOutputStream byteArrayOutputStream;

	private FileItemHeaders headers;

	private String contentType;
	
	private String fieldName;
	
	private String fileName;
	
	private boolean formField;

    private int sizeThreshold;
    
    public MemoryFileItem(String fieldName,
            String contentType, boolean formField, String fileName,
            int sizeThreshold) {
        this.fieldName = fieldName;
        this.contentType = contentType;
        this.formField = formField;
        this.fileName = fileName;
        this.sizeThreshold = sizeThreshold;
    }

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getName() {
		return getFileName();
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	public long getSize() {
		if (byteArrayOutputStream != null) {
			return byteArrayOutputStream.size();
		} else {
			return 0;
		}
	}

	@Override
	public byte[] get() {
		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public String getString(String encoding) throws UnsupportedEncodingException {
		return new String(get(), encoding);
	}

	@Override
	public String getString() {
		byte[] rawdata = get();
		String charset = getCharSet();
		if (charset == null) {
			charset = DEFAULT_CHARSET;
		}
		try {
			return new String(rawdata, charset);
		} catch (UnsupportedEncodingException e) {
			return new String(rawdata);
		}
	}


    public String getCharSet() {
        ParameterParser parser = new ParameterParser();
        parser.setLowerCaseNames(true);

        Map params = parser.parse(getContentType(), ';');
        return (String) params.get("charset");
    }

	@Override
	public void write(File file) throws Exception {
	}

	@Override
	public void delete() {
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public void setFieldName(String name) {
		this.fieldName = name;
	}

	@Override
	public boolean isFormField() {
		return formField;
	}

	@Override
	public void setFormField(boolean state) {
		formField = state;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		if(byteArrayOutputStream==null){
			byteArrayOutputStream = new ByteArrayOutputStream(sizeThreshold);
		}
		return byteArrayOutputStream;
	}

	@Override
	public FileItemHeaders getHeaders() {
		return headers;
	}

	@Override
	public void setHeaders(FileItemHeaders headers) {
		this.headers = headers;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getSizeThreshold() {
		return sizeThreshold;
	}

	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "MemoryFileItem [headers=" + headers + ", contentType=" + contentType + ", fieldName=" + fieldName + ", fileName="
				+ fileName + ", formField=" + formField + ", sizeThreshold=" + sizeThreshold + "]";
	}
}
