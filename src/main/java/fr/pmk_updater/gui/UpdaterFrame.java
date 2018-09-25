package fr.pmk_updater.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdaterFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -103233739386621286L;
	private JPanel content;	
	private JLabel loadind_gif;

	/**
	 * Create the frame.
	 * @throws IOException
	 */
	public UpdaterFrame() throws IOException {
		
		///////////// FRAME SETUP ///////////////////////////////
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	    setUndecorated(true);
	    setBackground(new Color(0,0,0,0));
	    setSize(300,500);	    
	    setIconImage(getImageURL("http://launcher.pumpmykins.eu/launcher-jar-updater/ressources/pmk_logo_500x500.png").getImage());	    
	    center();
	    
	    addWindowListener(new UpdaterFrameListener());
		
	    //////////////// PANEL CONTENT SETUP ///////////////////
	    
		content = new JPanel();	
		content.setBackground(new Color(0,0,0,0));
		setContentPane(content);
		content.setLayout(null);
		
		
		///////////////// LOG SETUP ////////////////////////////
		
		//JLabel label = new JLabel(getImageURL("http://launcher.pumpmykins.eu/launcher-jar-updater/ressources/pmk_logo_200x200.png"));
		
		JLabel logo_top = new JLabel(getImageURL("http://launcher.pumpmykins.eu/launcher-jar-updater/ressources/pmk_logo_200x200.png"));
		logo_top.setBounds(50, 40, 200, 200);
		content.add(logo_top);
		
		//////////////////////// WINDOWS SETUP ///////////////////////////////
		
		JPanel windows = new JPanel();
		windows.setBackground(new Color(140, 155, 211));
		windows.setBounds(0, 152, 300, 348);
		content.add(windows);
		windows.setLayout(null);
		
		///////////////////////// LOADING GIF SETUP /////////////////////////////
		
		loadind_gif = new JLabel("");
		setGIFAnim(true);
		loadind_gif.setBounds(100, 151, 100, 197);
		windows.add(loadind_gif);
		
		////////// ACTIVE UPDATER GUI ///////////////
		setVisible(true);
	}
	
	private ImageIcon getImageURL(String u) throws IOException {
		
			URL url = new URL(u);
			BufferedImage img = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(img);
			
			return icon;
		
	}
	
	public void setGIFAnim(boolean b) throws IOException {
		
		if(b) {
			
			loadind_gif.setIcon(new ImageIcon(new URL("http://launcher.pumpmykins.eu/launcher-jar-updater/ressources/loading_gif.gif")));
			
		}else {
			
			loadind_gif.setIcon(getImageURL("http://launcher.pumpmykins.eu/launcher-jar-updater/ressources/loading_gif.gif"));
			
		}
		
	}
	
	private void center() {
		
		Dimension windowSize = getSize();
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Point centerPoint = ge.getCenterPoint();

	    int dx = centerPoint.x - windowSize.width / 2;
	    int dy = centerPoint.y - windowSize.height / 2;    
	    setLocation(dx, dy);
		
		
	}
}
