package gameClient;

import javazoom.jl.player.*;
import java.io.FileInputStream;

public class music implements Runnable
{ 
    private String path;

	public music(String path)
	{
    	this.path = path;
    }
	
    public void play()
    {
        try
        {
             FileInputStream fis = new FileInputStream(path);
             Player playMP3 = new Player(fis);
             playMP3.play();
        }  
        catch(Exception e)
        {
        	System.out.println(e);
        }
    }

	@Override
	public void run() 
	{
		play();
	}



}