package dk.aau.cs.giraf.wombat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLTimeTimer {
	private float vertices[] = {
			0f, 1f, //point1
			1f, -1f, //point2
			-1f, -1f //point3
		};
		
		private FloatBuffer vertBuff;
		private short[] pIndex = { 0, 1, 2 }; // holder de specifikke punkter. Der er 3 punkter i en trekant
		private ShortBuffer pBuff;
		
		public GLTimeTimer(){
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
		}
		
		public void draw(GL10 gl){
			gl.glFrontFace(GL10.GL_CW); //clock-wise - fronten mod os/mod skærmen
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuff);
			gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pBuff);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		};
}
