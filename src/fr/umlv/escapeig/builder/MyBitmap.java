package fr.umlv.escapeig.builder;

import java.io.Serializable;

class MyBitmap implements Serializable {

	private static final long serialVersionUID = -2794100016742406170L;
	private byte[] bitmapBytes = null;

	public MyBitmap(byte[] bitmapBytes) {
		this.bitmapBytes = bitmapBytes;
	}

	public byte[] getBitmapBytes() {
		return this.bitmapBytes;
	}
}