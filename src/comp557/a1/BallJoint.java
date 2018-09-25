package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class BallJoint extends DAGNode {
	
	DoubleParameter rx;
	DoubleParameter ry;
	DoubleParameter rz;
	
	double x;
	double y;
	double z;
		
	public BallJoint( String name, double x, double y, double z ) {
		super(name);
		dofs.add( rx = new DoubleParameter( name+" rx", 0, -180, 180 ) );		
		dofs.add( ry = new DoubleParameter( name+" ry", 0, -180, 180 ) );
		dofs.add( rz = new DoubleParameter( name+" rz", 0, -180, 180 ) );
		
		this.x = x;
		this.y = y;
		this.z = z;
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
		gl.glRotated(ry.getValue(), 0, 1, 0);
		gl.glRotated(rz.getValue(), 0, 0, 1);
		
		super.display(drawable);
		gl.glPopMatrix();
	
	}
}
