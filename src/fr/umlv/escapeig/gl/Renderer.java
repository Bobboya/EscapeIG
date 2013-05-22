package fr.umlv.escapeig.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import fr.umlv.escapeig.Game;
import fr.umlv.escapeig.R;

public class Renderer implements GLSurfaceView.Renderer {

	private final Game game;
	private final float[] viewProjection = new float[16];
	private final Painter painter;
	private final PainterFactory painterFactory;
	
	public Renderer (Game game) {
		this.game = game;
		this.painterFactory = new PainterFactory(game);
		this.painter = painterFactory.create();
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// No culling of back faces
		GLES20.glDisable(GLES20.GL_CULL_FACE);
		// No depth testing
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		// Enable blending
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		painter.reset();
		painter.drawImage(R.drawable.missile);
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		float halfWidth = width/2;
		float halfHeight = height/2;
		
		//Set the viewports
		GLES20.glViewport(0, 0, width, height);
		
		float projection[] = new float[16];
		Matrix.orthoM(projection, 0, -halfWidth, halfWidth, -halfHeight, halfHeight, 1, 10);
		
		float view[] = new float[16];
		Matrix.setLookAtM(view, 0,
				0f, 0f, 1f,
				0f, 0f, 0f,
				0f, 1.0f, 0.0f);
		
		Matrix.multiplyMM(viewProjection, 0, projection, 0, view, 0);
		painter.setProjection(viewProjection);
	}
}