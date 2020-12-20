package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import javax.swing.*;

import Server.Game_Server_Ex2;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Iterator;
import java.util.List;


public class Game_Panel extends JPanel {
	// static Clip clip;
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private Image agent;
	private Image charmander;
	private Image backgraound;
	private Image charmeleon;
	private Image charizard;
	private Image Misty;
	private Image Brock;

	public static int scenario_num;
	public static Arena arena= new Arena();
	   /**
     * a empty constructor of Game_Panel
     *
     */
	Game_Panel() {
		super();
		this.setOpaque(false);
		this.setBackground(Color.WHITE);
		this.agent=new ImageIcon("./data/images.PNG ").getImage();
     	this.charmander=new ImageIcon("./data/charmander.PNG ").getImage();
     	this.charmeleon=new ImageIcon("./data/charmeleon.PNG ").getImage();
     	this.charizard=new ImageIcon("./data/charizard.PNG ").getImage();
		this.backgraound = new ImageIcon("data\\beckGround.jpeg").getImage();
	    this.setBackground(Color.white);
	


	}

	   /**
  *  constructor of Game_Panel
  * param a (string)
  */	Game_Panel(String a) {
		//	super(a);
		this.addComponentListener((ComponentListener) new ComponentAdapter() {
			@Override
			/**
		     * This method is used to resize the frame 
		     */
			public void componentResized(ComponentEvent e) {
				updateFrame();
			}
		});
	}

	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}
	 /**
     * This method is used  resize to   frame 
     */
	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
	}

	 /**
     * paint all  the graphics (score , backround ,size)
     *draw all the pokemon and
     *
     * @param g The Graphics 
     */
	public void paint(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		g.clearRect(0, 0, w, h);
		g.drawImage(this.backgraound,0, 0, w, h, null);
		//	updateFrame();
		drawGraph(g);
		drawPokemons(g);
		drawAgants(g);
		drawInfo(g);

		//drawTimer(g);
		drawScore(g);
		g.setColor(Color.blue);
		g.setFont(new Font("Arial Black",Font.BOLD,26));
		String s="Time: "+Game_Frame.timeToEnd + "           num senrio "+ Game_Frame.senrio;
		g.drawString(s, 190, 70);


	}
	private void drawInfo(Graphics g) {
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++) {
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}

	}
	 /**
     *draw all the the graph that we get from directed_weighted_graph 
     *
     * @param g The Graphics 
     */
	private void drawGraph(Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.BLACK);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
		}
	}
	 /**
     *draw all the pokemon on this panal 
     *There are three Pokemon sorted by level(charizard,charmeleon,charmander)
     * @param g The Graphics 
     */
	private void drawPokemons(Graphics g) {
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
			Iterator<CL_Pokemon> itr = fs.iterator();
			while(itr.hasNext()) {

				CL_Pokemon f = itr.next();
				Point3D c = f.getLocation();
				int r=10;
				g.setColor(Color.green);
				if(f.getType()<0) {g.setColor(Color.orange);}
				if(c!=null) {

					geo_location fp = this._w2f.world2frame(c);
					//fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
					//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
					if( f.getValue()>12) {
						g.drawImage(charizard,(int)fp.x()-r, (int)fp.y()-r, 6*r, 6*r,null);
					}
					else {



						if	(f.getValue()>7) {
							g.drawImage(charmeleon,(int)fp.x()-r, (int)fp.y()-r, 5*r, 5*r,null);
						}

						else {
							g.drawImage(charmander,(int)fp.x()-r, (int)fp.y()-r, 4*r, 4*r,null);

						}
						g.setColor(Color.red);
						g.drawString("" + f.getValue(), (int)fp.x()-r, (int)fp.y()-r);
					}
				}
			}
		}

	}
	 /**
     *draw all the Agents 
     *
     * @param g The Graphics 
     */
	private void drawAgants(Graphics g) {
		List<CL_Agent> rs = _ar.getAgents();
		//	Iterator<OOP_Point3D> itr = rs.iterator();
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,14));
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			int r=8;
			String s="agent "+rs.get(i).getID()+" :	 "+rs.get(i).getValue();
			i++;
			if(c!=null) {
				geo_location fp = this._w2f.world2frame(c);
				//g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
				g.drawImage(agent,(int)fp.x()-r, (int)fp.y()-r, 7*r, 7*r,null);

				g.drawString(s,(int)fp.x()-r, (int)(fp.y()-4*r));
			
               
        
              
			}
		}
	}
	 /**
     * draws all the nodes and their keys in the graph
     * @param n - a node_data type.
     * @param g - This Graphics2D
     * param r 
     */
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	   /**
     * draws the edges and their keys in the graph.
     * @param e - an edge_data type.
     * @param g - This Graphics2D .
     */

	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
		//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
	}

	
	   /**
     * draws the score in ths panal 
     * @param g - This Graphics2D 
     */
	private void drawScore (Graphics g){
		List<CL_Agent> rs = _ar.getAgents();
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS",Font.BOLD,12));
		int y=60;
		int i=0;
		if(rs!=null) {
		for (CL_Agent a : rs){
			g.drawString("agent "+ a.getID()+" :   "+a.getValue(),900 , y+i);
			i+=20;
		}
		}
	}


}
