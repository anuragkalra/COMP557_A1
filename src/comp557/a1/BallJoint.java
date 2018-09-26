package comp557.a1;

import javax.vecmath.Tuple3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import mintools.parameters.DoubleParameter;

public class BallJoint extends DAGNode {
	
	DoubleParameter rx;
	DoubleParameter ry;
	DoubleParameter rz;
	
	//Position information
	double tx;
	double ty;
	double tz;
		
	/**
	 * @param  name   name of the BallJoint
	 * @param  rxMin  min bound for rotation in x direction
	 * @param  rxMax  max bound for rotation in x direction
	 * @param  ryMin  min bound for rotation in y direction
	 * @param  ryMax  max bound for rotation in y direction
	 * @param  rzMin  min bound for rotation in z direction
	 * @param  rzMax  max bound for rotation in z direction
	 * @param  px x position
	 * @param  py y position
	 * @param  pz z position
	 * @return      The instantiated BallJoint
	 */
	public BallJoint(String name, double rxMin, double rxMax, double ryMin, double ryMax, double rzMin, double rzMax, double px, double py, double pz) {
		super(name);
	    dofs.add( rx = new DoubleParameter( name+" rx", 0, rxMin, rxMax ) );
		dofs.add( ry = new DoubleParameter( name+" ry", 0, ryMin, ryMax ) );
		dofs.add( rz = new DoubleParameter( name+" rz", 0, rzMin, rzMax ) );

	    this.tx = px;
	    this.ty = py;
	    this.tz = pz;
	  }
	
	public void setPosition(Tuple3d t) {
		this.tx = t.x;
		this.ty = t.y;
		this.tz = t.z;
	}
	
	//COMPLETE
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();

		gl.glTranslated(this.tx, this.ty, this.tz);
		gl.glRotated(rx.getValue(), 1, 0, 0);
		gl.glRotated(ry.getValue(), 0, 1, 0);
		gl.glRotated(rz.getValue(), 0, 0, 1);
		
		super.display(drawable);
		gl.glPopMatrix();
	
	}
}
