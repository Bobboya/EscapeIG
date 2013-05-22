package fr.umlv.escapeig.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class PainterFactory {

	private static final short DRAW_ORDER[] = { 0, 1, 2, 2, 1, 3 };
	private static final float VERTICES[] = {
		-0.5f, -0.5f, 1.0f,// bottom left
		0.5f, -0.5f, 1.0f, // bottom right
		-0.5f,  0.5f, 1.0f,// top left
		0.5f,  0.5f, 1.0f  // top right
	};
	private static final float TEXTURE_COORDS[] = {							
		0.0f, 0.0f,
		1.0f, 0.0f,
		0.0f, 1.0f,
		1.0f, 1.0f
	};
	
	private static final int VERTEX_BUFFER_SIZE = VERTICES.length * Float.SIZE / Byte.SIZE;
	private static final int DRAW_ORDER_BUFFER_SIZE = DRAW_ORDER.length * Short.SIZE / Byte.SIZE;
	private static final int TEXTURE_COORDS_BUFFER_SIZE = TEXTURE_COORDS.length * Float.SIZE / Byte.SIZE;
	
	private static final String VERTEX_CODE =
			"uniform mat4 u_MVPMatrix;"+
			"varying vec2 v_TexCoordinate;"+ // Passed to the fragment shader
			"varying vec4 v_Color;"+		 // Passed to the fragment shader
			"attribute vec4 a_Position;"+
			"attribute vec2 a_TexCoordinate;"+
			"uniform vec4 a_Color;"+
			"void main() {"+
			"	v_Color = a_Color;"+
			"	v_TexCoordinate = a_TexCoordinate;"+
			"	gl_Position = u_MVPMatrix * a_Position;"+
			"}";
	
	private static final String FRAGMENT_CODE =
			"precision mediump float;"+
			"uniform sampler2D u_Texture;"+ // The input texture.
			"varying vec2 v_TexCoordinate;"+
			"void main() {"+
				"gl_FragColor = texture2D(u_Texture, v_TexCoordinate);"+
			"}";
	
	private final int program;
	private final int vertexShader;
	private final int fragmentShader;
	private final TextureManager textureManager;
	
	public PainterFactory (Context context) {
		textureManager = new TextureManager(context);
		
		vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_CODE);
		fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_CODE);
		
		if (fragmentShader == 0) {
			Log.d("Shader", "Fragment Error 0");
		}
		if (vertexShader == 0) {
			Log.d("Shader", "Vertex Error 0");
		}
		
		program = GLES20.glCreateProgram();			 // create empty OpenGL Program
		GLES20.glAttachShader(program, vertexShader);   // add the vertex shader to program
		GLES20.glAttachShader(program, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(program);
	}
	
	@Override
	public void finalize () {
//		textureManager.unloadTextures();
//		GLES20.glDetachShader(program, vertexShader);
//		GLES20.glDetachShader(program, fragmentShader);
//		GLES20.glDeleteShader(vertexShader);
//		GLES20.glDeleteShader(fragmentShader);
//		GLES20.glDeleteProgram(program);
	}
	
	private int loadShader(int type, String shaderCode){
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		return shader;
	}
	
	private Painter create (int width, int height) {
		FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(VERTEX_BUFFER_SIZE)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(scaleSquare(width, height));
		vertexBuffer.position(0);
		
		ShortBuffer drawListBuffer = ByteBuffer.allocateDirect(DRAW_ORDER_BUFFER_SIZE)
				.order(ByteOrder.nativeOrder()).asShortBuffer();
		drawListBuffer.put(DRAW_ORDER);
		drawListBuffer.position(0);
		
		FloatBuffer cubeTextureCoordinates = ByteBuffer.allocateDirect(TEXTURE_COORDS_BUFFER_SIZE)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		cubeTextureCoordinates.put(TEXTURE_COORDS);
		cubeTextureCoordinates.position(0);
		
		return new Painter(textureManager, program, vertexBuffer, drawListBuffer, cubeTextureCoordinates);
	}
	
	public Painter create () {
		return create(10,10);
	}
	
	private float[] scaleSquare (int width, int height) {
		float vertices[] = new float[VERTICES.length];
		for (int i=0; i<12; i+=3) {
			int j=i+1;
			vertices[i] = VERTICES[i]*width;
			vertices[j] = VERTICES[j]*height;
		}
		return vertices;
	}

}
