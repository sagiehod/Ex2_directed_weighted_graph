package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;


class DWGraph_DS_test {

	@Test
		void test_connect_nodes() {
			directed_weighted_graph g = new DWGraph_DS();
			
			g.addNode(new NodeData(0));
			g.addNode(new NodeData(1));
			g.addNode(new NodeData(2));
			g.addNode(new NodeData(3));
			g.addNode(new NodeData(3)); //the node that already exist
			g.addNode(new NodeData(4));
			
			assertEquals(5, g.getMC());
			assertEquals(5, g.nodeSize());
			
			g.connect(0,1,5);
			g.connect(0,2,4);
			g.connect(1,3,9);
			g.connect(2,1,3);
			
			assertEquals(4, g.edgeSize());
			assertEquals(9, g.getMC());
			
			//nodes that not exist in the graph-do nothing
			g.connect(20, 21, 1);
			assertEquals(4, g.edgeSize());
			assertEquals(9, g.getMC());
			
			
			g.connect(2,1,3); // the same edge with same weight -does nothing
			assertEquals(9, g.getMC());
			
			g.connect(2,1,8); // edge that already exist and change the weight
		   assertEquals(8, g.getEdge(2,1).getWeight());
		   assertEquals(10, g.getMC());
			
			g.connect(3,3,2); // src=dest -does nothing
			
			// g.connect(1,3,-3);  // negative weight- throw exception
			
			g.connect(5,7,2); // nodes that not exist in the graph
			assertNull(g.getEdge(5,7));
			
			assertNull(g.getEdge(1,0)); // edge that not exist in the graph
			
			g.connect(0,2,3);
			assertEquals(3, g.getEdge(0,2).getWeight());
			
			g.removeNode(3);
			assertNull(g.getEdge(1,3));
			assertEquals(4, g.nodeSize());	
			
		}


		@Test
		void test_get_edge() {
			directed_weighted_graph ga = new DWGraph_DS();
			ga.addNode(new NodeData(0));
			ga.addNode(new NodeData(1));
			ga.addNode(new NodeData(2));
			ga.addNode(new NodeData(3));
			ga.addNode(new NodeData(4));
			
			assertEquals(5, ga.getMC());
			assertEquals(5, ga.nodeSize());

			ga.connect(0,1,5);
			ga.connect(1,2,4);
			ga.connect(2,3,9);
			ga.connect(2,1,2);
			ga.connect(2,0,3);
			ga.connect(3,4,7);
			
			assertEquals(11, ga.getMC());
			assertEquals(6, ga.edgeSize());
			
			assertEquals(3, ga.getEdge(2,0).getWeight()); // edge that exist
			assertNull(ga.getEdge(5,6));// nodes that not exist
			assertNotNull(ga.getEdge(0,1));// edge that exist
			 assertTrue(ga.getEdge(2,3) != null);
			 
			 assertEquals(11, ga.getMC());
				assertEquals(6, ga.edgeSize());
			 
		}
		
		
		@Test
		void test_remove_node() {
			directed_weighted_graph ga = new DWGraph_DS();
			ga.addNode(new NodeData(0));
			ga.addNode(new NodeData(1));
			ga.addNode(new NodeData(2));
			
			assertEquals(3, ga.getMC());
			assertEquals(3, ga.nodeSize());
	
			ga.connect(0,1,2);
			ga.connect(1,2,2);
			ga.connect(2,0,3);
			ga.connect(2,1,4);
			
			assertEquals(7, ga.getMC());
			assertEquals(4,ga.edgeSize());
			
			ga.removeNode(0);
			assertEquals(10, ga.getMC());
			assertEquals(2, ga.nodeSize());
			assertNull(ga.getEdge(2,0));
			
			ga.removeNode(4); // this node isnt exist in the graph
				
			assertEquals(10, ga.getMC());
			
			ga.removeNode(1);
			assertEquals(0,ga.edgeSize());
			assertEquals(1,ga.nodeSize());
			
			assertEquals(0,ga.edgeSize());
		
			ga.removeNode(2);	
			
			
			ga.addNode(new NodeData(1));
			assertEquals(1,ga.nodeSize());
			
			ga.removeNode(1);
			assertEquals(0,ga.edgeSize());
			assertEquals(0,ga.nodeSize());
			
		}
		
		@Test
		void test_remove_edge() {
			directed_weighted_graph ga = new DWGraph_DS();
			ga.addNode(new NodeData(0));
			ga.addNode(new NodeData(1));
			ga.addNode(new NodeData(2));
			ga.addNode(new NodeData(3));
			
			ga.connect(0,1,5);
			ga.connect(1,2,4);
			ga.connect(2,3,9);
			ga.connect(2,1,2);
			ga.connect(2,0,3);
			ga.connect(3,2,7);
			assertEquals(10, ga.getMC());
			assertEquals(6, ga.edgeSize());
			
			ga.removeNode(3);
			assertEquals(4, ga.edgeSize());
			assertEquals(13, ga.getMC());
			assertNull(ga.getEdge(3,2));
				
			ga.removeEdge(0, 1);
			ga.removeEdge(0, 1);//remove edge that not exist
			
			assertEquals(14, ga.getMC());
			assertEquals(3, ga.edgeSize());
			assertNull(ga.getEdge(0, 1));
			
			ga.removeEdge(0,0);
			assertEquals(14, ga.getMC());
			
			
			ga.removeEdge(1,2);
			ga.removeEdge(2,1);
			ga.removeEdge(2,0);
			assertEquals(0, ga.edgeSize());
			
			ga.removeNode(0);
			ga.removeNode(1);
			ga.removeNode(2);
		
			assertEquals(0, ga.edgeSize());
			assertEquals(0, ga.nodeSize());
			
		}
		
	}