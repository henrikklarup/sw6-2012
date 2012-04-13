package dk.aau.cs.giraf.wombat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLProgressBar {
	private float vertices[] = {
		//bar frame top points
			-12.2f, 2f, //point0
			12.2f, 2f, //point1
			-12.2f, 1.8f, //point2
			12.2f, 1.8f, //point3
		//bar frame bottom points
			-12.2f, -2f, //point4
			12.2f, -2f, //point5
			-12.2f, -1.8f, //point6
			12.2f, -1.8f, //point7
		//bar frame left points
			-12.2f, 2f, //point8
			-12.2f, -2f, //point9
			-12.4f, -2f, //point10
			-12.4f, 2f, //point11
		//bar frame right points
			12.2f, 2f, //point12
			12.2f, -2f, //point13
			12.4f, -2f, //point14
			12.4f, 2f, //point15
		//bar time spent points - change as time progress - 
		//0.2f corresponds to 1 minute if the time is set to 60 minutes
			-12f, 1.6f, //point16 - change x-coordinate
			8f, 1.6f, //point17
			8f, -1.6f, //point18
			-12f, -1.6f, //point19 - change x-coordinate
//		//bar time left points 
//			7.8f, 1.6f, //point20 - change x-coordinate
//			-12f, 1.6f, //point21
//			-12f, -1.6f, //point22
//			7.8f, -1.6f, //point23 - change x-coordinate
			
		};
		
		private FloatBuffer vertBuff;
		private short[] pIndex = {
				//bar frame top
				0,1,3,
				3,2,0,
				//bar frame bottom
				4,5,7,
				7,6,4,
				//bar frame left
				8,9,10,
				10,11,8,
				//bar frame right
				12,13,14,
				14,15,12,
				//time spent
				16,17,18,
				18,19,16,
//				//time left
//				20,21,22,
//				22,23,10,
				
			}; // holder de specifikke punkter. Der er 3 punkter i en trekant
		private ShortBuffer pBuff;
		
		public GLProgressBar(){
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
