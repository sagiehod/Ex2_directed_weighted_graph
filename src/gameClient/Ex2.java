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
		}else {
			Ex2 start=new Ex2(Integer.parseInt(a[0]),Integer.parseInt(a[1]));
			Thread client = new Thread(start);
			client.start();	
		}


		if(playerThread.isAlive()) {
			run.set(false);
		}
		playerThread.stop();


	}

	@Override
	public void run() {
		//	int scenario_num = 3;
		game_service game = Game_Server_Ex2.getServer(senrio); // you have [0,23] games
		//	int id = 999;
		//	game.login(id);
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
				//geo_location nodePos = gg.getNode(a.getNextNode()).getLocation();
				//System.out.println((long)gg.getNode(a.getNextNode()).getLocation().distance(pos)/speed);
				long t = (long) (200000*gg.getNode(a.getNextNode()).getLocation().distance(pos)/(speed/2));
				//System.out.println(t);
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
			//		for(int i=0;i<log.size();i++) {
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
				}else {
					for(node_data n: path) {
						//15 -> 2 ->3->4
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
				//System.out.println(pokemon.getValue());
				if(dest<path_min )	{ 
					path_min=dest;	
					ans = pokemon;

					//max_value=pokemon.getValue();
				}
			}
		}

		return ans;
	}


	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		//gg.init(g);
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new Game_Frame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);


		//****	
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
			//			for(int a = 0;a<rs;a++) {
			//				int ind = a%cl_fs.size();
			//				CL_Pokemon c = cl_fs.get(ind);
			//				int nn = c.get_edge().getDest();
			//				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
			//				game.addAgent(nn);
			//			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}



	public static void agentsFirstPlaceByValue(ArrayList<CL_Pokemon> pokemons, int numberOfAgents, dw_graph_algorithms g_algo, game_service game) {
		Collections.sort(pokemons, new Comparator<CL_Pokemon>() {

			@Override
			public int compare(CL_Pokemon o1, CL_Pokemon o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});
		//System.out.println(pokemons);
		for (int i = 0; i < numberOfAgents; i++) {
			game.addAgent(pokemons.get(i).get_edge().getSrc());
		}
	}
}












//******************************
//private void init(game_service game) {
//	
//	String g = game.getGraph();
//	String fs = game.getPokemons();
//	directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//	DWGraph_Algo _g= new DWGraph_Algo();
//	_g.init(gg);
//	//gg.init(g);
//	_ar = new Arena();
//	_ar.setGraph(gg);
//	_ar.setPokemons(Arena.json2Pokemons(fs));
//	_win = new Game_Frame("test Ex2");
//	_win.setSize(1000, 700);
//	_win.update(_ar);
//
//
//	//**	
//	_win.setVisible(true);
//	String info = game.toString();
//	JSONObject line;
//	try {
//		line = new JSONObject(info);
//		JSONObject ttt = line.getJSONObject("GameServer");
//		int rs = ttt.getInt("agents");
//		//			System.out.println(info);
//		//			System.out.println(game.getPokemons());
//		//			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
//		ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
//		for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
//		agentsFirstPlace(cl_fs, rs, _g, game);
//
//
//		//			for(int a = 0;a<rs;a++) {
//		//				int ind = a%cl_fs.size();
//		//				CL_Pokemon c = cl_fs.get(ind);
//		//				int nn = c.get_edge().getDest();
//		//				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
//		//				
//		//				game.addAgent(nn);
//		//			}
//	}
//	catch (JSONException e) {e.printStackTrace();}
//}
//
////למקסם פריסה על הגרף
////למקסם ניקוד
////למקסם תפיסת פוקימונים
////u1, u2, ..., uk
////(v1, d1) , (v2, d2) ,..., (vn, dn) vi = value, di = distance
////di = min{J}(dist(uj,di))
////m = forall v in V :max{j}(uj, v)
////score =  v1/d1 + v2/d2 + ... + vn/dn
////O((|V| choose k)(|V|+|E|)|V|)
//public static void agentsFirstPlace(ArrayList<CL_Pokemon> pokemons, int numberOfAgents, DWGraph_Algo g_algo, game_service game) {
//double min_score = Double.POSITIVE_INFINITY;
//double maxDistance = Double.POSITIVE_INFINITY;
//List<Integer> ans = null;
//List<List<Integer>> allOptions = getVchooseK(g_algo.getGraph().getV(), numberOfAgents);
//for(List<Integer> spots : allOptions) {
//	double score = 0;
//	for(CL_Pokemon p : pokemons) {
//		double v = p.getValue();
//		double d_min = Double.POSITIVE_INFINITY;
//		for(int s : spots) {
//			double d = g_algo.shortestPathDist(s, p.get_edge().getSrc()) + p.get_edge().getWeight();
//			if(d < d_min) {
//				d_min = d;
//			}
//		}
//		score += v/d_min;
//	}
//	if(score < min_score) {
//		min_score = score;
//		ans = spots;
//		double max = 0;
//		for(int s : spots) {
//			for(node_data n : g_algo.getGraph().getV()) {
//				double d = g_algo.shortestPathDist(s, n.getKey());
//				if(d > max) {
//					max = d;
//				}
//			}
//		}
//		maxDistance = max;
//	}
//	else if(score == min_score) {
//		double max = 0;
//		for(int s : spots) {
//			for(node_data n : g_algo.getGraph().getV()) {
//				double d = g_algo.shortestPathDist(s, n.getKey());
//				if(d > max) {
//					max = d;
//				}
//			}
//		}
//		if(max < maxDistance) {
//			maxDistance = max;
//			ans = spots;
//		}
//	}
//}
//for(int s : ans) {
//	game.addAgent(s);
//}
//}
//
//private static List<List<Integer>> getVchooseK(Collection<node_data> v, int k) {
//List<List<Integer>> ans = new LinkedList<List<Integer>>();
//List<Integer> temp = new LinkedList<Integer>();
//List<node_data> lst = new LinkedList<>(v);
//getVchooseK(ans, lst, k, 0, temp);
//return ans;
//}
//
//private static void getVchooseK(List<List<Integer>> ans, List<node_data> v, int k, int i, List<Integer> temp) {
//if(i == v.size()) {
//	if(k == 0) {
//		List<Integer> t = new LinkedList<Integer>();
//		for(int a : temp) t.add(a);
//		ans.add(t);
//	}
//}
//else {
//	getVchooseK(ans, v, k, i+1, temp);
//	temp.add(v.get(i).getKey());
//	getVchooseK(ans, v, k-1, i+1, temp);
//	temp.remove((Object)v.get(i).getKey());
//}
//}
//}