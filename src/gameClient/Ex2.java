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

		music player = new music("Pokemon.mp3");
		Thread playerThread = new Thread(player);
		playerThread.start();
		if(a.length==0) {
			login_gui  l = new login_gui();
			l.chose();
		}else 
		{
			Ex2 start=new Ex2(Integer.parseInt(a[0]),Integer.parseInt(a[1]));
			Thread client = new Thread(start);
			client.start();	
		}
		if(playerThread.isAlive()) {
			run.set(false);
		}
		playerThread.stop();
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

		String g = game.getGraph();
		String pks = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		init(game);

		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;

		music player = new music("Pokémon music.mp3");
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

		System.out.println(game.toString());

		System.exit(0);
	}

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
				System.out.println("Next is: " + a.getNextNode());
				long t = (long) (200000*gg.getNode(a.getNextNode()).getLocation().distance(pos)/(speed/2));
				if(t < time) time = t;
				if(t > maxTime) maxTime = t;
			}
			else {
				for(CL_Pokemon p : onEdge) {
					noPokemon = false;
					System.out.println("Pokemon!");
					long t = (long) (100000*p.getLocation().distance(pos)/speed);
					if(t < time) time = maxTime = t;
				}
			}
		}
		System.out.println("moves:" + move + " waiting: " + time+20);
		if(noPokemon) return 220;
		return time+20;
	}
	/** 
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
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
			// if agent move to pokemon continuo
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
	private static CL_Pokemon getEdgeClose( int src, List<CL_Agent> log) {
		//init graph algo
		algo.init(graph);
		// init variable
		CL_Pokemon ans = null;
		double path_min= Double.POSITIVE_INFINITY;
		double dest=-1;


		double max_value=-1;
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
					path_min=dest;	
					ans = pokemon;
				}
			}
		}
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




