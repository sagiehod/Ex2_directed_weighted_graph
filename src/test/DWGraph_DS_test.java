package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
//import api.edge_data;
//import api.node_data;

class DWGraph_DS_test {
//	directed_weighted_graph g;
//	@BeforeEach
//	void testBuild() {
//		g =new DWGraph_DS();
//		for (int i=0;i<10;i++){
//			node_data n = new NodeData(i);
//			g.addNode(n);
//		}
//		g.connect(0,1,2.2);
//		g.connect(0,3,4.3);
//		g.connect(0,4,3.8);
//		g.connect(1,2,4.1);
//		g.connect(2,1,3.0);
//		g.connect(2,3,6.1);
//		g.connect(3,2,5.0);
//		g.connect(4,0,2.7);
//		g.connect(4,5,1.7);
//		g.connect(5,3,0.5);
//		
//	}
		  @Test
		    void addNode() {
			  directed_weighted_graph ga = new DWGraph_DS();
		        ga.addNode(new NodeData(0));
		        ga.addNode(new NodeData(1));
		        ga.addNode(new NodeData(2));
		        ga.addNode(new NodeData(3));
		        ga.addNode(new NodeData(3)); //the node that already exist
		        ga.addNode(new NodeData(4));
		        
		        assertEquals(5, ga.getMC());
		        assertEquals(5, ga.nodeSize());
		        
		        ga.connect(0,1,1);
		        ga.connect(0,2,2);
		        ga.connect(1,3,3);
		        ga.connect(2,1,3);
		        ga.connect(2,1,3);
		        ga.connect(2,1,8);
		        
		       assertEquals(4, ga.edgeSize());
		      assertEquals(5, ga.nodeSize());
		      assertEquals(10, ga.getMC());
		    }
}
		
	
//	@Test
//	void DW_connect() {
//		g.connect(1,2,1);       //add new edge
//		g.connect(1,2,3);       //update edge weight
//		g.connect(5,5,1);       //edge to itself SHOULD NOT add
//		g.connect(8,5,3);       //exist edge
//		g.connect(100,141,3);
//		assertEquals(13, g.edgeSize());
//		assertEquals(3, g.getEdge(1,2).getWeight());
//	}
//	
//	   @Test
//	    void testGetEdge() {
////	        edge_data e = g.getEdge(2,4);
////	        assertEquals(1.5,e.getWeight());
//	        edge_data e1 = g.getEdge(4,2);      //not exist
//	        assertNull(e1);
//	        edge_data e3 = g.getEdge(2,2);      //edge to itself
//	        assertNull(e3);
//	    }
//}
