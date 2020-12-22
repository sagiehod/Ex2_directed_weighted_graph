package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.dw_graph_algorithms;
import api.game_service;
import gameClient.Arena;
import gameClient.CL_Pokemon;
import gameClient.Ex2;

public class Ex2_test {
	game_service game= Game_Server_Ex2.getServer(11);		
	
	dw_graph_algorithms algo1 = new DWGraph_Algo();
	Ex2 Pokemon =new Ex2(123456789,11);
// test to load graph 
	//Checks if it loads the graph correctly


	@Test
	void reade_Graph() {	
		Pokemon.reade_Graph(game.getGraph(),"graph_Test");
		algo1.load("graph_Test");
		int edge =algo1.getGraph().edgeSize();
		int nodes= algo1.getGraph().nodeSize();
		
		assertEquals(edge,80);
		assertEquals(nodes,31);
		assertTrue(algo1.isConnected());
	}
	}
