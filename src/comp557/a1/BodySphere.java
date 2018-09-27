package comp557.a1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class BodySphere extends DAGNode {

	double radius;
	
	//Scale information
	double xScale;
	double yScale;
	double zScale;
		
	//Center information	
	double tx;
	double ty;
	double tz;
		
	public BodySphere(String name, double radius, double xScale, double yScale, double zScale, double px, double py, double pz) {
		super(name);
		this.radius = radius;
		this.xScale = xScale;
		this.yScale = yScale;
		this.zScale = zScale;
		this.tx = px;
		this.ty = py;
		this.tz = pz;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();
		gl.glTranslated(this.tx, this.ty, this.tz);
		gl.glScaled(this.xScale, this.yScale, this.zScale);
		glut.glutSolidSphere(this.radius, 25, 25);	
		super.display(drawable);
		gl.glPopMatrix();
	
	}

}
