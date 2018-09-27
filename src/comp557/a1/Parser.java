package comp557.a1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A factory class to generate a DAG from XML definition. 
 */
public class Parser {

	public static DAGNode load( String filename ) {
		try {
			InputStream inputStream = new FileInputStream(new File(filename));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			return createScene( null, document.getDocumentElement() ); // we don't check the name of the document elemnet
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load simulation input file.", e);
		}
	}
	
	/**
	 * Load a DAG subtree from a XML node.
	 * Returns the root on the call where the parent is null, but otherwise
	 * all children are added as they are created and all other deeper recursive
	 * calls will return null.
	 */
	public static DAGNode createScene( DAGNode parent, Node dataNode ) {
        NodeList nodeList = dataNode.getChildNodes();
        for ( int i = 0; i < nodeList.getLength(); i++ ) {
            Node n = nodeList.item(i);
            // skip all text, just process the ELEMENT_NODEs
            if ( n.getNodeType() != Node.ELEMENT_NODE ) continue;
            String nodeName = n.getNodeName();
            DAGNode dagNode = null;
            if ( nodeName.equalsIgnoreCase( "node" ) ) {
            	dagNode = Parser.createJoint( n );
            } else if ( nodeName.equalsIgnoreCase( "geom" ) ) {        		
        		dagNode = Parser.createGeom( n ) ;            
            }
            // recurse to load any children of this node
            createScene( dagNode, n );
            if ( parent == null ) {
            	// if no parent, we can only have one root... ignore other nodes at root level
            	return dagNode;
            } else {
            	parent.add( dagNode );
            }
        }
        return null;
	}
	
	/**
	 * Create a joint
	 * 
	 * TODO: Objective 5: Adapt commented code in createJoint() to create your joint nodes when loading from xml
	 */
	public static DAGNode createJoint( Node dataNode ) {
		String type = dataNode.getAttributes().getNamedItem("type").getNodeValue();
		String name = dataNode.getAttributes().getNamedItem("name").getNodeValue();
		Tuple3d t;
		if ( type.equals("freejoint") ) {
			FreeJoint joint = new FreeJoint( name );
			return joint;
		} else if ( type.equals("ball") ) {

			double px = 0;
			double py = 0;
			double pz = 0;
			if ( (t=getTuple3dAttr(dataNode,"position")) != null ) {
				px = t.x;
				py = t.y;
				pz = t.z;
			}
			
			double rxMin = -180;
			double ryMin = -180;
			double rzMin = -180;
			if ( (t=getTuple3dAttr(dataNode,"rMins")) != null ) {
				rxMin = t.x;
				ryMin = t.y;
				rzMin = t.z;
			}
			
			double rxMax = 180;
			double ryMax = 180;
			double rzMax = 180;
			if ( (t=getTuple3dAttr(dataNode,"rMaxs")) != null ) {
				rxMax = t.x;
				ryMax = t.y;
				rzMax = t.z;
			}
			
			BallJoint joint = new BallJoint(name, rxMin, rxMax, ryMin, ryMax, rzMin, rzMax, px, py, pz);
						
			return joint;
			
		} else if ( type.equals("hinge") ) {
			
			double px = 0;
			double py = 0;
			double pz = 0;
			
			if ( (t=getTuple3dAttr(dataNode,"position")) != null ) {
				px = t.x;
				py = t.y;
				pz = t.z;
			}
			
			double axis = 0;
			double min = -180;
			double max = 180;
			
			if ( (t=getTuple3dAttr(dataNode,"angles")) != null ) {
				axis = t.x;
				min = t.y;
				max = t.z;
			}
			
			HingeJoint joint = new HingeJoint(name, axis, min, max, px, py, pz);
			return joint;
		}
		return null;
	}

	/**
	 * Creates a geometry DAG node 
	 * 
	 * TODO: Objective 5: Adapt commented code in greatGeom to create your geometry nodes when loading from xml
	 */
	public static DAGNode createGeom( Node dataNode ) {
		String type = dataNode.getAttributes().getNamedItem("type").getNodeValue();
		String name = dataNode.getAttributes().getNamedItem("name").getNodeValue();
		Tuple3d t;
		if ( type.equals("box" ) ) {
			
			//Capture center information
			double tx = 0;
			double ty = 0;
			double tz = 0;
			
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) {
				tx = t.x;
				ty = t.y;
				tz = t.z;
			}
			
			//Capture scaling information
			double xScale = 1;
			double yScale = 1;
			double zScale = 1;
			
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) {
				xScale = t.x;
				yScale = t.y;
				zScale = t.z;
			}
			
			BodyBox box = new BodyBox(name, 1, xScale, yScale, zScale, tx, ty, tz);
			return box;
		} else if ( type.equals( "sphere" )) {
			double radius = 1;
			
			//Capture center information
			double tx = 0;
			double ty = 0;
			double tz = 0;
			
			if ( (t=getTuple3dAttr(dataNode,"center")) != null ) {
				tx = t.x;
				ty = t.y;
				tz = t.z;
			}
			
			//Capture scaling information
			double xScale = 1;
			double yScale = 1;
			double zScale = 1;
			
			if ( (t=getTuple3dAttr(dataNode,"scale")) != null ) {
				xScale = t.x;
				yScale = t.y;
				zScale = t.z;
			}
			
			BodySphere sphere = new BodySphere(name, radius, xScale, yScale, zScale, tx, ty, tz);
			return sphere;
		}
		return null;		
	}
	
	/**
	 * Loads tuple3d attributes of the given name from the given node.
	 * @param dataNode
	 * @param attrName
	 * @return null if attribute not present
	 */
	public static Tuple3d getTuple3dAttr( Node dataNode, String attrName ) {
		Node attr = dataNode.getAttributes().getNamedItem( attrName);
		Vector3d tuple = null;
		if ( attr != null ) {
			Scanner s = new Scanner( attr.getNodeValue() );
			tuple = new Vector3d( s.nextDouble(), s.nextDouble(), s.nextDouble() );			
			s.close();
		}
		return tuple;
	}

}