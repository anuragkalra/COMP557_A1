package comp557.a1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class BodyBox extends DAGNode {

	float size;
	
	//Scale information
	double xScale;
	double yScale;
	double zScale;
	
	//Centering information
	double tx;
	double ty;
	double tz;
	
	public BodyBox(String name, float size, double xScale, double yScale, double zScale) {
		super(name);
		this.size = size;
		this.xScale = xScale;
		this.yScale = yScale;
		this.zScale = zScale;
		this.tx = 0;
		this.ty = 0;
		this.tz = 0;
	}
	
	public BodyBox(String name, float size, double xScale, double yScale, double zScale, double px, double py, double pz) {
		super(name);
		this.size = size;
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
		glut.glutSolidCube(this.size);
		super.display(drawable);
		gl.glPopMatrix();
	
	}
}
