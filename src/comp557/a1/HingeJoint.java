package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class HingeJoint extends DAGNode {

	DoubleParameter rx;
	DoubleParameter ry;
	DoubleParameter rz;
	
	//Centering information
	double tx;
	double ty;
	double tz;
	
	int axis;
		
	public HingeJoint( String name, int axis, double min, double max, double px, double py, double pz) {
		super(name);
		this.axis = axis;
		if(axis == 0) {
			dofs.add( rx = new DoubleParameter( name+" rx", 0, min, max ) );
		}
		else if(axis == 1) {
			dofs.add( ry = new DoubleParameter( name+" ry", 0, min, max ) );
		}
		else if(axis == 2){
			dofs.add( rz = new DoubleParameter( name+" rz", 0, min, max ) );
		}	
		
		this.tx = px;
		this.ty = py;
		this.tz = pz;
	}

	
	public void setPosition(Tuple3d t) {
		this.tx = t.x;
		this.ty = t.y;
		this.tz = t.z;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();
		gl.glTranslated(this.tx, this.ty, this.tz);
		
		if(axis == 0) {
			gl.glRotated(rx.getValue(), 1, 0, 0);
		}
		else if(axis == 1) {
			gl.glRotated(ry.getValue(), 0, 1, 0);
		}
		else if(axis == 2){
			gl.glRotated(rz.getValue(), 0, 0, 1);
		}
		
		super.display(drawable);
		gl.glPopMatrix();
	
	}
}
