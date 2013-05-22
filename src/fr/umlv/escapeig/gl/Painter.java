package fr.umlv.escapeig.gl;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class Painter {
	
	private static final int COORDS_PER_VERTEX = 3;
	
	private final TextureManager textureManager;
	private final FloatBuffer verticesCoordsBuffer;
	private final FloatBuffer textureCoordsBuffer;
	private final ShortBuffer drawListBuffer;
	private final int program;
	private final int bytesPerVertex;
	private final int nbVerticesToDraw;
	
	private float color[] = { 1.0f, 1.0f, 1.0f, 0.0f };
	private float[] modelMatrix = new float[16];
	private float[] tempMatrix = new float[16];
	private float[] vpMatrix;
	
	Painter(TextureManager textureManager,
			int program,
			FloatBuffer verticesCoordsBuffer,
			ShortBuffer drawListBuffer,
			FloatBuffer textureCoordsBuffer
			) {
		this.textureManager = textureManager;
		this.program = program;
		this.verticesCoordsBuffer = verticesCoordsBuffer;
		this.drawListBuffer = drawListBuffer;
		this.textureCoordsBuffer = textureCoordsBuffer;
		this.bytesPerVertex = verticesCoordsBuffer.capacity();
		this.nbVerticesToDraw = drawListBuffer.capacity();
		Matrix.setIdentityM(modelMatrix, 0);
	}
	
	public void move (float x, float y) {
		Matrix.translateM(modelMatrix, 0, x, y, 0);
	}
	
	public void rotate (float a) {
		Matrix.rotateM(modelMatrix, 0, a, 0, 0, -1.0f);
	}
	
	public void reset () {
		Matrix.setIdentityM(modelMatrix, 0);
	}
	
	public void setRect(float width, float height) {
		Matrix.scaleM(modelMatrix, 0, width, height, 1);
	}
	
	void setProjection (float viewProjection[]) {
		vpMatrix = viewProjection;
	}
	
	public void drawImage(int image) {
		int textureHandle = textureManager.loadTexture(image);
		// Add program to OpenGL environment
		GLES20.glUseProgram(program);
		int textureUniformHandle = GLES20.glGetUniformLocation(program, "u_Texture");
		int positionHandle = GLES20.glGetAttribLocation(program, "a_Position");
		int colorHandle = GLES20.glGetUniformLocation(program, "a_Color");
		int mvpMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
		int textureCoordinateHandle = GLES20.glGetAttribLocation(program, "a_TexCoordinate");
		//Texture
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(textureUniformHandle, 0);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		// Pass in the texture coordinate information
		textureCoordsBuffer.position(0);
		GLES20.glVertexAttribPointer(textureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 
									 0, textureCoordsBuffer);
		GLES20.glEnableVertexAttribArray(textureCoordinateHandle);
		
		//Position
		GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
									 GLES20.GL_FLOAT, false,
									 bytesPerVertex, verticesCoordsBuffer);
		GLES20.glEnableVertexAttribArray(positionHandle);
		
		//Color
		GLES20.glUniform4fv(colorHandle, 1, color, 0);
		
		//Projection & transformation
		Matrix.multiplyMM(tempMatrix, 0, vpMatrix, 0, modelMatrix, 0);
		
		GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, tempMatrix, 0);
		//Draw
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, nbVerticesToDraw,
							  GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
		// Disable vertex array
		GLES20.glDisableVertexAttribArray(textureCoordinateHandle);
		GLES20.glDisableVertexAttribArray(positionHandle);
	}
}