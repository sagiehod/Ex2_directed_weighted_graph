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
		this.agent=new ImageIcon("./data/ash.png").getImage();
		this.charmander=Toolkit.getDefaultToolkit().createImage("./data/charmander.gif");
		this.charmeleon=Toolkit.getDefaultToolkit().createImage("./data/charmeleon.gif");
		this.backgraound = new ImageIcon("data\\beckGround.jpeg").getImage();
		this.setBackground(Color.white);
		this.charizard = Toolkit.getDefaultToolkit().createImage("./data/charizard.gif");
		this.Misty = Toolkit.getDefaultToolkit().createImage("./data/Misty.gif");
		this.Brock = new ImageIcon("./data/Brock.png").getImage();;


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
	//		  public static void importPictures() {
	//		        try {
	//		            background = ImageIO.read(new File("resource/background.jpg"));
	//		            logo = ImageIO.read(new File("resource/logo.png"));
	//		            blur = ImageIO.read(new File("resource/blur.png"));
	//		            ash = Toolkit.getDefaultToolkit().createImage("resource/ash.gif");
	//		            pokador = ImageIO.read(new File("resource/pokador.png"));
	//		            pikachu = Toolkit.getDefaultToolkit().createImage("resource/pikachu.gif");
	//		            charizard = Toolkit.getDefaultToolkit().createImage("resource/charizard.gif");
	//		            mewtwo = Toolkit.getDefaultToolkit().createImage("resource/mewtwo.gif");
	//		        } catch (Exception ex) {
	//		            ex.printStackTrace();
	//		        }
	//		    }

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
		drawPokemons(g);
		drawGraph(g);
		drawAgants(g);
		drawInfo(g);

		//drawTimer(g);
		drawScore(g);
		g.setColor(Color.RED);
		g.setFont(new Font("Arial",Font.BOLD,18));
		String s="Time: "+Game_Frame.timeToEnd + "     num senrio "+ Game_Frame.senrio;
		g.drawString(s, 50, 100);


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
		//					Image image = createImage(5000,5000);
		//					Graphics g1 = image.getGraphics();
		//					g.drawImage(map.myImage,-8 ,-8, getWidth(), getHeight(), this);
		//					Iterator<CL_Pokemon> ItP = game.CL_Pokemon.iterator();

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
						g.drawImage(charizard,(int)fp.x()-r, (int)fp.y()-r, 5*r, 5*r,null);
					}
					else {



						if	(f.getValue()>7) {
							g.drawImage(charmeleon,(int)fp.x()-r, (int)fp.y()-r, 4*r, 4*r,null);
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
		g.setColor(Color.red);
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			int r=8;
			String s="agent "+rs.get(i).getID()+" :	 "+rs.get(i).getValue();
			i++;
			if(c!=null) {
               if(i==1) {
				geo_location fp = this._w2f.world2frame(c);
				//g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
				g.drawImage(agent,(int)fp.x()-r, (int)fp.y()-r, 5*r, 5*r,null);

				g.drawString(s,(int)fp.x()-r, (int)(fp.y()-4*r));
				g.drawString(rs.get(i-1).getSpeed()+ " ", 800,100+i*30);
				g.drawString((long)(rs.get(i-1).getLocation().x()*10000000000l)+ " "+ (long)(rs.get(i-1).getLocation().y()*10000000000l)+ " ",850, 100+i*30);
               }
               if(i==2) {
   				geo_location fp = this._w2f.world2frame(c);
   				//g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
   				g.drawImage(Misty,(int)fp.x()-r, (int)fp.y()-r, 5*r, 5*r,null);

   				g.drawString(s,(int)fp.x()-r, (int)(fp.y()-4*r));
   				g.drawString(rs.get(i-1).getSpeed()+ " ", 800,100+i*30);
   				g.drawString((long)(rs.get(i-1).getLocation().x()*10000000000l)+ " "+ (long)(rs.get(i-1).getLocation().y()*10000000000l)+ " ",850, 100+i*30);
                  }
               if(i==3) {
      				geo_location fp = this._w2f.world2frame(c);
      				//g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
      				g.drawImage(Brock,(int)fp.x()-r, (int)fp.y()-r, 4*r, 4*r,null);

      				g.drawString(s,(int)fp.x()-r, (int)(fp.y()-4*r));
      				g.drawString(rs.get(i-1).getSpeed()+ " ", 800,100+i*30);
      				g.drawString((long)(rs.get(i-1).getLocation().x()*10000000000l)+ " "+ (long)(rs.get(i-1).getLocation().y()*10000000000l)+ " ",850, 100+i*30);
                     }
              
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

	//	private void drawTimer(Graphics g) {
	//		g.setColor(Color.BLACK);
	//		g.setFont(new Font("Arial",Font.BOLD,12));
	//		g.drawString("Time: "+_ar.getGame().timeToEnd()/1000,40 , 60);
	//	}
	   /**
     * draws the score in ths panal 
     * @param g - This Graphics2D 
     */
	private void drawScore (Graphics g){
		List<CL_Agent> rs = _ar.getAgents();
		g.setColor(Color.RED);
		g.setFont(new Font("Arial",Font.BOLD,12));
		int y=60;
		int i=0;
		for (CL_Agent a : rs){
			g.drawString("agent "+ a.getID()+" :	 "+a.getValue(),900 , y+i);
			i+=20;
		}
	}


}
