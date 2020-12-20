package gameClient;

import Server.Game_Server_Ex2;
import api.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;

public class Ex2 implements Runnable{

	private static Game_Frame _win;
	private static Arena _ar;
	private int id=123456789;
	private int senrio=0;
	private int move = 0;
	private final static AtomicBoolean run = new AtomicBoolean();
	private static dw_graph_algorithms algo= new DWGraph_Algo();;
	private static List<CL_Pokemon> ffs = new ArrayList<>();
	private static directed_weighted_graph graph;
	/**
	 * constructor of Ex2
	 * @param id2
	 * @param senrio2
	 * 
	 */
	//constructor
	public Ex2(int id2, int senrio2) {
		this.id=id2;
		this.senrio=senrio2;
	}


	public static void main(String[] a) {
		if(a.length==0) {
		login_gui  l = new login_gui();
		l.chose();
		music player = new music("./data/Pokemon.mp3");
		Thread playerThread = new Thread(player);
		playerThread.start();
		while(l.flag != true) {
			System.out.println("");
		}
		l.exit();
		playerThread.stop();
		Ex2 start=new Ex2(l.id,l.senrio);
		Thread client = new Thread(start);
		client.start();
		}else if(a.length==2) {
			Ex2 start=new Ex2(Integer.parseInt(a[0]),Integer.parseInt(a[1]));
			Thread client = new Thread(start);
			client.start();	
		}
	}
		

	/**
	 * run the game
	 * init the game(graph)
	 * get the frame(gui)  and all the data
	 * 
	 */
	@Override
	public void run() {
		game_service game = Game_Server_Ex2.getServer(senrio); // you have [0,23] games

		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		init(game);
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		//int ind=(int) (game.timeToEnd()/100);
		int ind = 0;
		long dt=100;

		music player = new music("./data/Pokémon music.mp3");
		Thread playerThread = new Thread(player);
		playerThread.start();

		while(game.isRunning()) {
			move ++;
			String lg = moveAgants(game, gg);
			dt = calcTimeToNextEvent(game, gg, lg);
			_win.repaint();
			long seconds = TimeUnit.MILLISECONDS.toSeconds(game.timeToEnd());
			_win.print_time(seconds,senrio);
			try {
				if(ind%1==0) {_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

//	      JButton button = new JButton("Click to Close!");
//	      _win.setDefaultCloseOperation(_win.EXIT_ON_CLOSE);
//	      _win.setContentPane(button);
//	      button.addActionListener(e -> {
//	    	  game.stopGame();
//	    	  _win.dispose();
//	      });

		
		
		System.out.println(game.toString());
		System.exit(0);
	}
	/**
	 * Calculate the transition time to the second transition
	 * Thinks how long each agent has until the next event, 
	 * that the next event can reach its vertex (dest) or reach the earlier Pokemon of the two
	 *@param game_service game
	 *@param directed_weighted_graph gg
	 *@param string lg
	 */
	private long calcTimeToNextEvent(game_service game, directed_weighted_graph gg, String lg) {
		long time = 200;
		long maxTime = 0;
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		String fs =  game.getPokemons();
		ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		boolean noPokemon = true;
		for(CL_Agent a : log) {
			if(a.getNextNode() == -1) return 0;
			edge_data e = gg.getEdge(a.getSrcNode(), a.getNextNode());
			geo_location pos = a.getLocation();
			double speed = a.getSpeed();
			List<CL_Pokemon> onEdge = new LinkedList<>();
			for(CL_Pokemon p : ffs) {
				if(p.get_edge() == e) onEdge.add(p);
			}
			if(onEdge.isEmpty()) {
			
				long t = (long) (200000*gg.getNode(a.getNextNode()).getLocation().distance(pos)/(speed/2));
				if(t < time) time = t;
				if(t > maxTime) maxTime = t;
			}
			else {
				for(CL_Pokemon p : onEdge) {
					noPokemon = false;
				
					long t = (long) (100000*p.getLocation().distance(pos)/speed);
					if(t < time) time = maxTime = t;
				}
			}
		}
		
		if(noPokemon) return 220;
		return time+20;
	}
	/** 
	 * Moves each of the agents along the edge,by some Conditions.
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly)
	 *  and Sending the agent each time to his next rib as part of the path to the rib that the Pokemon is on,
	 *  she calculates for each agent the vertex he needs to reach..
	 * @param game
	 * @param gg
	 * @param
	 */
	private static String moveAgants(game_service game, directed_weighted_graph gg) {
		graph=gg;
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		String fs =  game.getPokemons();
		ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);

		PriorityQueue<CL_Agent> queue = new PriorityQueue<CL_Agent>(new Comparator<CL_Agent>() {
			@Override
			public int compare(CL_Agent o1, CL_Agent o2) {
				return -Double.compare(o2.getSpeed(),o1.getSpeed());
			}
		});

		for(CL_Agent ag:log) {
			ag.set_curr_fruit(null);
			queue.add(ag);
		}
		for(CL_Agent agent: queue) {
			// if agent move to pokemon continue
			CL_Agent ag =agent;
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if(dest==-1) {
				List<node_data> path=null;
				CL_Pokemon chosen = getEdgeClose(src, log);
				ag.set_curr_fruit(chosen);
				edge_data next= chosen.get_edge();
				try {

					algo.init(graph);
					path =  algo.shortestPath(src, next.getSrc());	

				}catch(NullPointerException e) {
					game.chooseNextEdge(id, next.getDest());
				}
				if(path==null) {
					game.chooseNextEdge(id,next.getDest());
				}else 
				{
					for(node_data n: path) {
						game.chooseNextEdge(id, n.getKey());
					}
					game.chooseNextEdge(id,next.getDest());
				}

			}
		}
		return lg;
	}
	/**
	 * We created a getEdgeClose function where we check for each agent which Pokemon is closest to it and we did this using the DWGraph_Algo class
	 *  where we used the Dijkstra algorithm to find the shortestpathDist which checks the shortest path and returns the weight of the edge (dest) and finds the shortest path for each agent. 
	 * Which is closest to it. And there it is sent. The location is defined by its side has src, dest))
	 *  and dest is the vertex to which it should reach.
     *  Next, we used the init function which loads the graph, gets the list of agents and by the function we created places them, 
     *    and the Pokemon positions by the Arena class and places them in the graph.
     *   Then we used the shortestpath function (which is based on the Dijkstra's algorithm) to save the shortest route
	 * @param src
	 * @param log
	 * @return ans -pokemon
	 */
	private static CL_Pokemon getEdgeClose( int src, List<CL_Agent> log) {
		//init graph algo
		algo.init(graph);
		// init variable
		CL_Pokemon ans = null;
		CL_Pokemon ans1 = null;
		if(!Ex2.ffs.isEmpty()) {
		 ans = Ex2.ffs.get(0);
		 ans1 =  Ex2.ffs.get(0);
		}
		double path_min= Double.POSITIVE_INFINITY;
		double path_min2= Double.POSITIVE_INFINITY;
		double dest=-1;

		

	
		// for to pokemon and check min path 
		for(CL_Pokemon pokemon: Ex2.ffs) {
			boolean chased = false;
			for(CL_Agent a : log) {
				if(a.get_curr_fruit() == pokemon) chased = true;
			}
			if(!chased) {
				edge_data edge = graph.getEdge(pokemon.get_edge().getSrc(),pokemon.get_edge().getDest()); 

				dest= algo.shortestPathDist(src,pokemon.get_edge().getSrc());
				
				if(dest<path_min )	{ 
					path_min2 = path_min;
					path_min=dest;	
					ans = pokemon;
				}
				else if(dest<path_min2  ){
					path_min2 = dest;
					ans1 = pokemon;
				}
			}
		}
		
		if (ans.getValue() < ans1.getValue() && path_min-path_min2==3) return ans1;
		return ans;
	}
	/**
	 * init the game
	 * get the Pokemons and Agents from the data
	 *  places the Agents in the Arena.
	 * init the GUI .
	 *
	 * @param game a game_service type.
	 */
	private void init(game_service game) {

		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new Game_Frame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);	
		_win.setVisible(true);

		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");

			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}

			dw_graph_algorithms algo =new DWGraph_Algo();
			algo.init(gg);
			agentsFirstPlaceByValue(cl_fs,rs,algo,game);
		}
		catch (JSONException e) {e.printStackTrace();}
	}
	/**
	 * Positions the agent in a strategic place in the start
	 *param numberOfAgents
	 * @param  pokemons
	 */
	public static void agentsFirstPlaceByValue(ArrayList<CL_Pokemon> pokemons, int numberOfAgents, 
			dw_graph_algorithms g_algo, game_service game) {
		Collections.sort(pokemons, new Comparator<CL_Pokemon>() {

			@Override
			public int compare(CL_Pokemon o1, CL_Pokemon o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});
		for (int i = 0; i < numberOfAgents; i++) {
			game.addAgent(pokemons.get(i).get_edge().getSrc());
		}
	}
}




