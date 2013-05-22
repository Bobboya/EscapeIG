package fr.umlv.escapeig.gl;

import java.nio.IntBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.SparseIntArray;

/**
 * Singleton loading images and stocking them
 *
 */
public class TextureManager {
	
	private final SparseIntArray textureHolder;
	private final Context context;

	public TextureManager (Context context) {
		this.context = context;
		this.textureHolder = new SparseIntArray();
	}
	
	@Override
	public void finalize () {
		unloadTextures();
	}
	
	public int loadTexture(int resourceId) {
		if (context == null) throw new IllegalStateException("No context defined");
		
		int text = textureHolder.get(resourceId);
		if (text != 0) return text;
		
		//GL part
		final int[] handle = new int[1];
		GLES20.glGenTextures(1, handle, 0);
		if (handle[0] == 0) throw new RuntimeException("Error loading texture.");
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		// Read in the resource
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
		// Bind to the texture in OpenGL
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handle[0]);
		// Set filtering
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		// Load the bitmap into the bound texture.
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, bitmap, 0);
		// Recycle the bitmap, since its data has been loaded into OpenGL.
		bitmap.recycle();
		
		textureHolder.put(resourceId, handle[0]);
		
		return handle[0];
	}
	
	public void unloadTextures () {
		IntBuffer ib = IntBuffer.allocate(textureHolder.size());
		ib.position(0);
		
		for (int i=0; i<textureHolder.size(); i++) {
			ib.put(textureHolder.valueAt(i));
		}
		GLES20.glDeleteTextures(ib.capacity(), ib);
	}
	
	

}
