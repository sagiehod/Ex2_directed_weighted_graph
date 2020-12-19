

package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;


public class Game_Frame extends JFrame{
	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private Game_Panel panel;
	static long timeToEnd=0;static int senrio=0;

	/**
	 * a default constructor.
	 */
	Game_Frame(String a) {

		super(a);
	}

	public void update(Arena ar) {
		this._ar = ar;
		panel = new Game_Panel();
		this.add(panel);
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
		panel.update(_ar);
		panel.repaint();
	}

	/**
	 * paint all  the frame
	 * @param g The Graphics 
	 */
	public void paint(Graphics g) {
		updateFrame();

	}

	 /**
     * when the game run is tell us the the senrio and How much time is left until the end of the game
     * @param timeToEnd 
     * @param senrio
     */
	public void print_time(long timeToEnd, int senrio) {
		this.timeToEnd=timeToEnd;
		this.senrio=senrio;
	}
}




