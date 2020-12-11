package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;


class DWGraph_Algo_test {


	directed_weighted_graph g= new DWGraph_DS();
	dw_graph_algorithms ga= new DWGraph_Algo();


	//		@Test
	//		void test_copy() {
	//		
	//			directed_weighted_graph g_copy= ga.copy();
	//			assertTrue(g.equals(g_copy));
	//			assertEquals(true, g_copy.nodeSize()==ga.getGraph().nodeSize());
	//			assertEquals(true, g_copy.edgeSize()==ga.getGraph().edgeSize());
	//			g.removeNode(7);
	//			assertNull(g.getNode(7));
	//			assertNotEquals(true,g_copy.nodeSize()==ga.getGraph().nodeSize());
	//			assertNotEquals(true, g_copy.getMC()==ga.getGraph().getMC());
	//		}

	@Test
	void test_isconnected() {
		directed_weighted_graph g = new DWGraph_DS();

		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		//the node that already exist
		g.addNode(new NodeData(4));
		g.addNode(new NodeData(5));
		g.addNode(new NodeData(6));


		g.connect(0,1,2);
		g.connect(0,5,3);
		g.connect(1,2,1);
		g.connect(2,0,5);
		g.connect(2,6,2);
		g.connect(4,5,8);
		g.connect(5,6,1);
		g.connect(6,0,1);
		g.connect(6,4,3);

		ga.init(g);

		assertTrue(ga.isConnected());

		//	connect between 2 nodes that does not exist in the graph
		g.connect(10,11,12);
		assertTrue(ga.isConnected());

		g.addNode(new NodeData(7));
		g.connect(7,5,3);
		ga.init(g);
		assertFalse(ga.isConnected());

		// 7 node that does not exist in the graph- do nothing
		g.removeNode(7);
		g.removeEdge(7, 5);
		ga.init(g);
		assertTrue(ga.isConnected());


		//connect 3-->5 and 5-->3 now the graph is connected
		g.addNode(new NodeData(3));
		g.connect(3,5,3);
		g.connect(5,3,3);
		ga.init(g);
		assertTrue(ga.isConnected());


		//There are no nodes in the graph
		ga.init(null);
		assertTrue(ga.isConnected());

		g= new DWGraph_DS();
		ga.init(g);
		assertTrue(ga.isConnected());

		//one node in the graph
		g.addNode(new NodeData(8));
		assertTrue(ga.isConnected());

		// 8, 10 there are 2 nodes in the graph without connect between them
		g.addNode(new NodeData(10));
		assertFalse(ga.isConnected());


	}

	@Test
	void test_shortestpathDist() {
		directed_weighted_graph g = new DWGraph_DS();

		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		//the node that already exist
		g.addNode(new NodeData(4));
		g.addNode(new NodeData(5));
		g.addNode(new NodeData(6));


		g.connect(0,1,2);
		g.connect(0,5,3);
		g.connect(1,2,1);
		g.connect(2,0,5);
		g.connect(2,6,2);
		g.connect(4,5,8);
		g.connect(5,6,1);
		g.connect(6,0,1);
		g.connect(6,4,3);

		ga.init(g);

		//check length of the shortest path between src to dest
		assertEquals(4, ga.shortestPathDist(0,6));
		assertEquals(3, ga.shortestPathDist(6,4));


		//path between the same node;
		assertEquals(0, ga.shortestPathDist(0,0));
		//node 13 is not exist in the graph
		assertEquals(-1, ga.shortestPathDist(13, 13));

		//2-->6-->5
		assertEquals(6, ga.shortestPathDist(2, 5));
		g.removeNode(6);
		//new path from 2-->5 
		assertEquals(8, ga.shortestPathDist(2, 5));
		//there is not path between them 
		assertEquals(-1, ga.shortestPathDist(2, 6));

		//short path between the same node
		assertEquals(1, ga.shortestPath(1,1).size());

		g.removeNode(1);
		assertEquals(-1, ga.shortestPathDist(2,6));


	}
	@Test
	void test_shortestpath() {
		directed_weighted_graph g = new DWGraph_DS();

		g.addNode(new NodeData(0));
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		//the node that already exist
		g.addNode(new NodeData(4));
		g.addNode(new NodeData(5));
		g.addNode(new NodeData(6));


		g.connect(0,1,2);
		g.connect(0,5,3);
		g.connect(1,2,1);
		g.connect(2,0,5);
		g.connect(2,6,2);
		g.connect(4,5,8);
		g.connect(5,6,1);
		g.connect(6,0,1);
		g.connect(6,4,3);

		ga.init(g);
		//check the number of the list of the shortest path
		assertEquals(3, ga.shortestPath(1,6).size());
		assertEquals(4, ga.shortestPath(2,5).size());

		// 11 is node that not exist in the graph
		assertEquals(null, ga.shortestPath(1,11));
		//path from the same node--> 1
		assertEquals(1, ga.shortestPath(2,2).size());
		//two nodes that not exist in the graph
		assertEquals(null, ga.shortestPath(12,12));

		//there is not path between them- 6 is not exist in the graph
		g.removeNode(6);
		assertEquals(null, ga.shortestPath(2,6));	
		assertEquals(3, ga.shortestPath(0,2).size());

		assertEquals(3, ga.shortestPath(2,5).size());

		g.removeEdge(2,0);
		ga.init(g);
	
		g.removeEdge(0, 1);
		assertEquals(null, ga.shortestPath(0,2));
		//there is not path between them- 6 is not exist in the graph
		assertEquals(null, ga.shortestPath(2,0));


	}
}

