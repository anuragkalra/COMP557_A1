package comp557.a1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class BodySphere extends DAGNode {

	double radius;
	double tx;
	double ty;
	double tz;
	
	public BodySphere(String name, double radius) {
		super(name);
		this.radius = radius;
	}
	
	public BodySphere(String name, double radius, double px, double py, double pz) {
		super(name);
		this.radius = radius;
		this.tx = px;
		this.ty = py;
		this.tz = pz;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();		
		glut.glutSolidSphere(this.radius, 25, 25);
		
		super.display(drawable);
		gl.glPopMatrix();
	
	}

}
