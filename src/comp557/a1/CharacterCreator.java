package comp557.a1;

import javax.swing.JTextField;

import comp557.a1.DAGNode;
import comp557.a1.Parser;
import mintools.parameters.BooleanParameter;

public class CharacterCreator {

	static public String name = "LIL B - Anurag Kalra 260631195";
	
	// TODO: Objective 6: change default of load from file to true once you start working with xml
	static BooleanParameter loadFromFile = new BooleanParameter( "Load from file (otherwise by procedure)", false );
	static JTextField baseFileName = new JTextField("a1data/character");
	static { baseFileName.setName("what is this?"); }
	
	/**
	 * Creates a character, either procedurally, or by loading from an xml file
	 * @return root node
	 */
	static public DAGNode create() {
		
		if ( loadFromFile.getValue() ) {
			// TODO: Objectives 6: create your character in the character.xml file 
			return Parser.load( baseFileName.getText() + ".xml");
		} else {
			// TODO: Objective 1,2,3,4: test DAG nodes by creating a small DAG in the CharacterCreator.create() method 
			
			FreeJoint root = new FreeJoint("root");
			BodyBox rootBox = new BodyBox("", 1, 2, 2, 2);

			BallJoint lShoulder = new BallJoint("lShoulder", -90, 90, -90, 90, -90, 90, 3, 3, 0);
			BallJoint rShoulder = new BallJoint("rShoulder", -90, 90, -90, 90, -90, 90, -3, 3, 0);
			
			BodyBox lShoulderBox = new BodyBox("", 1, 1, 1, 2);
			BodyBox rShoulderBox = new BodyBox("", 1, 1, 1, 2);

			root.add(rootBox);
			root.add(lShoulder);
			root.add(rShoulder);


			HingeJoint lElbow = new HingeJoint("lElbow", 1, -180, 180, 2, 0, 0);
			HingeJoint rElbow = new HingeJoint("rElbow", 1, -180, 180, -2, 0, 0);
			BodyBox lElbowBox = new BodyBox("", 1, 2, 2, 2, 3, 0, 0);
			BodyBox rElbowBox = new BodyBox("", 1, 2, 2, 2);

			lShoulder.add(lShoulderBox);
			lShoulder.add(lElbow);
			rShoulder.add(rShoulderBox);
			rShoulder.add(rElbow);

			lElbow.add(lElbowBox);
			rElbow.add(rElbowBox);

			BallJoint lHip = new BallJoint("lHip", -90, 90, -90, 90, -90, 90, 2, -2, 0);
			BallJoint rHip = new BallJoint("rHip", -90, 90, -90, 90, -90, 90, -2, -2, 0);
			BodyBox lHipBox = new BodyBox("", 1, 3, 3, 3);
			BodyBox rHipBox = new BodyBox("", 1, 3, 3, 3);

			root.add(lHip);
			root.add(rHip);

			HingeJoint lKnee = new HingeJoint("lKnee", 0, -180, 180, 0, -2, 0);
			HingeJoint rKnee = new HingeJoint("rKnee", 0, -180, 180, 0, -2, 0);
			BodyBox lKneeBox = new BodyBox("", 1, 2, 2, 2);
			BodyBox rKneeBox = new BodyBox("", 1, 2, 2, 2);

			lHip.add(lKnee);
			rHip.add(rKnee);
			lHip.add(lHipBox);
			rHip.add(rHipBox);

			lKnee.add(lKneeBox);
			rKnee.add(rKneeBox);

			BallJoint lAnkle = new BallJoint("lAnkle",-90, 90, -90, 90, -90, 90, 1, -3, 0);
			BallJoint rAnkle = new BallJoint("rAnkle",-90, 90, -90, 90, -90, 90, -1, -3, 0);
			//BodyBox lAnkleBox = new BodyBox("", 1, 1, 1, 4);
			BodySphere lAnkleSphere = new BodySphere("", 1, 2, 2, 2);
			BodyBox rAnkleBox = new BodyBox("", 1, 1, 1, 4);

			lKnee.add(lAnkle);
			rKnee.add(rAnkle);

			//lAnkle.add(lAnkleBox);
			lAnkle.add(lAnkleSphere);
			rAnkle.add(rAnkleBox);
			
			
			
			
			// Use this for testing, but ultimately it will be more interesting
			// to create your character with an xml description (see example).
			
			// Here we just return null, which will not be very interesting, so write
			// some code to create a test or partial charcter and return the root node.

			//return root;
			return root;
		}
	}
}
