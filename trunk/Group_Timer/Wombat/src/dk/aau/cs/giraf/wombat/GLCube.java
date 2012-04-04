package dk.aau.cs.giraf.wombat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLCube {
	
	int dimensions = 3;
	// int colorDimension = 4;

	//punkter
	private float vertices[] = {
		1, 1, -1, //point1 topFrontRight
		1, -1, -1, //point2 botFrontRight
		-1, -1, -1, //point3 botFrontLeft
		-1, 1, -1, //point4 topFrontLeft
		1, 1, 1, //point5 topBackRight
		1, -1, 1, //point6 botBackRight
		-1, -1, 1, //point7 botBackLeft
		-1, 1, 1, //point8 topBackLeft
	};
	
//	
//	//farver
//	private float rgbaVals[] = {
//			1, 1, 0, 0,
//			.25f, 0, .85f, 0,
//			0, 1, 1, 0,
//			1, 1, 0, 0,
//			.25f, 0, .85f, 0,
//			0, 1, 1, 0,
//			.25f, 0, .85f, 0,
//			1, 1, 0, 0,
//		};
	
	private FloatBuffer vertBuff, colorBuff;
	private short[] pIndex = { 
			3,4,0,  0,4,1,  3,0,1,
			3,7,4,  7,6,4,  7,3,6,
			3,1,2,  1,6,2,  6,3,2,
			1,4,5,  5,6,1,  6,5,4
	}; // holder de specifikke punkter. Der er 3 punkter i en trekant
	private ShortBuffer pBuff;
	
	public GLCube(){
		ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4); //gange 4 fordi float er 4 bytes
		bBuff.order(ByteOrder.nativeOrder());
		vertBuff = bBuff.asFloatBuffer();
		vertBuff.put(vertices);
		vertBuff.position(0);
		
		ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2); //gange 2 fordi short er 2 bytes
		pbBuff.order(ByteOrder.nativeOrder());
		pBuff = pbBuff.asShortBuffer();
		pBuff.put(pIndex);
		pBuff.position(0);
		
//		// farve-buffer
//		ByteBuffer cBuff = ByteBuffer.allocateDirect(rgbaVals.length * 4);
//		cBuff.order(ByteOrder.nativeOrder());
//		colorBuff = cBuff.asFloatBuffer();
//		colorBuff.put(rgbaVals);
//		colorBuff.position(0);
	}
	
	public void draw(GL10 gl){
		gl.glFrontFace(GL10.GL_CW); //clock-wise - fronten mod os/mod skærmen
		gl.glEnable(GL10.GL_CULL_FACE); // kan fjerne en side, hvis det er en side vi alligevel aldrig ser
		gl.glCullFace(GL10.GL_BACK); // fjerner bagsiden, så vi ikke skal tænke på den
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY); //enable color array
		gl.glVertexPointer(dimensions, GL10.GL_FLOAT, 0, vertBuff);
//		gl.glColorPointer(colorDimension, GL10.GL_FLOAT, 0, colorBuff); //enable color pointer
		gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pBuff);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	};
}
