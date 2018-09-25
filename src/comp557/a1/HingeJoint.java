package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class HingeJoint extends DAGNode {

	DoubleParameter rx;
	
	double x;
	double y;
	double z;
		
	public HingeJoint( String name, double min, double max, double px, double py, double pz) {
		super(name);
		dofs.add( rx = new DoubleParameter( name+" rx", 0, min, max ) );
		this.x = px;
		this.y = py;
		this.z = pz;
	}

	
	public void setPosition(Tuple3d t) {
		this.x = t.x;
		this.y = t.y;
		this.z = t.z;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();
		gl.glTranslated(this.x, this.y, this.z);
		gl.glRotated(rx.getValue(), 1, 0, 0);
		
		super.display(drawable);
		gl.glPopMatrix();
	
	}
}
