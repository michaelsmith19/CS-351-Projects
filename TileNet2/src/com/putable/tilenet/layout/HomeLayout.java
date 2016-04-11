package com.putable.tilenet.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.putable.tilenet.Util.AttributeTransformer;
import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.ElementOp;
import com.putable.tilenet.Util.ElementOp.OpType;
import com.putable.tilenet.Util.R2Vect;
import com.putable.tilenet.Util.XMLTags.CMDTag;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.blueprints.Layout;
import com.putable.tilenet.client.VKMap;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrixelement.Agent;
import com.putable.tilenet.matrixelement.Token;

@SuppressWarnings("serial")
public class HomeLayout implements Layout {
	private int matrixWidth = 1;
	private int matrixHeight = 1;
	private int defaultTileWidth;
	private int defaultTileHeight;
	private SetTag matrixTag;
	private Connection conn;

	private final Color defaultFgColor = Color.RED;
	private final Color defaultBgColor = Color.BLUE;
	
	private BufferedImage display;
	private JPanel panel;
	
	private final ConcurrentHashMap<String, BufferedImage> convertedHexData;	
	private final ConcurrentHashMap<R2Vect, SetTag> tagLocation;
	private final List<SetTag> allTags;
	
	private final ConcurrentHashMap<String, Token> tokens;
	private final ConcurrentHashMap<Agent, List<Token>> agentsSpecificTokens;
	
	private final ConcurrentHashMap<String,SetTag> keys;
	
	public HomeLayout(){
		this.allTags = new ArrayList<SetTag>();
		this.convertedHexData = new ConcurrentHashMap<String, BufferedImage>();		
		this.tagLocation = new ConcurrentHashMap<R2Vect, SetTag>();
		this.tokens = new ConcurrentHashMap<String, Token>();
		this.agentsSpecificTokens = new ConcurrentHashMap<Agent, List<Token>>();
		this.keys = new ConcurrentHashMap<String,SetTag>();
	}	

	private BufferedImage tokenWithImage(SetTag tag, BufferedImage bi) throws IOException {
		BufferedImage tmp = convertedHexData.get(tag.getImage());
		if(tmp == null){
			System.err.println("image not found");
			tmp = ImageIO.read(new File("resource/Rainbow-Dash.png"));
		}		
		//Will scale it to fit This is quite nice if i don't say so myself.
		AffineTransform at = new AffineTransform();
		at.scale(bi.getWidth()/(double)tmp.getWidth(), bi.getHeight()/(double)tmp.getHeight());
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(tmp, bi);
	}

//	private void addTextCentered(String text, BufferedImage bi){
//		//TODO ..... yeah
//		Graphics g = bi.getGraphics();
//	}

	private void reset() {
		allTags.clear();
		convertedHexData.clear();
		tagLocation.clear();
		tokens.clear();
		agentsSpecificTokens.clear();
	}


	@Override
	public void update(SetTag tag) {
		allTags.add(tag);
		switch (tag.getObjid().charAt(0)){
		case 't':
			updateToken(tag);
			break;
		case 'm':
			updateMatrix(tag);
			break;
		case 'i':
			updateImage(tag);
			break;
		case 'k':
			updateKey(tag);
			break;
		default:
			System.err.println("Layout cannot handle tags like this yet: " + tag);
			tagLocation.remove(new R2Vect(tag.getX(), tag.getY()));
			break;				
		}		
	}

	private void updateMatrix(SetTag tag) {				
		//If a reset
		if(matrixTag != null){
			reset();			
		}
		this.matrixTag = tag;
		//If not displaying
		if(display == null){			
			return;
		}			
		
		matrixWidth = tag.getX();
		matrixHeight = tag.getY();
		
		defaultTileHeight = display.getHeight()/matrixHeight;
		defaultTileWidth = display.getWidth()/matrixWidth;
		
		//TODO matrix background stuff (image)
		Color fg = tag.getFgColor();
		Color bg = tag.getBgColor();
		if(fg == null)
			fg = defaultFgColor;
		if(bg == null)
			bg = defaultBgColor;
		Graphics g = display.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, display.getWidth(), display.getHeight());		
	}

	private void updateImage(SetTag tag) {
		BufferedImage bi = AttributeTransformer.hex2Image(tag.getText());
		convertedHexData.put(tag.getObjid(), bi);		
	}
	
	private void updateKey(SetTag tag){
		if(tag.getX() != null && tag.getX() < 0 && keys.containsKey(tag.getName())){
			keys.remove(tag);
		}
		keys.put(tag.getName(), tag);
	}

	private void updateToken(SetTag tag) {		
		//Place new token with cords
		tagLocation.put(new R2Vect(tag.getX(), tag.getY()), tag);
		Token tok = (Token) Common.toElement(tag);
		tokens.put(tag.getObjid(), tok);
		if(display == null){			
			return;
		}		
		
		BufferedImage bi = new BufferedImage(defaultTileWidth, defaultTileHeight, BufferedImage.TYPE_3BYTE_BGR);		
		
		//Get default color
		Color fg = tag.getFgColor();
		Color bg = tag.getBgColor();
		if(fg == null)
			fg = defaultFgColor;
		if(bg == null)
			bg = defaultBgColor;
		
		//Color it
		Graphics g = bi.getGraphics();
		g.setColor(bg);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
		//Color the text
		g.setColor(fg);
		//addTextCentered(tag.getText(), bi);
		if (tag.getName() != null)
			g.drawString(tag.getName(), 0, bi.getHeight()/2);
		
		//Do stuff with image if there is one
		if(tag.getImage() != null )
			try {
				bi = tokenWithImage(tag, bi);
			} catch (IOException e) {
				System.err.println("Error reading rainbow dash!");
			}
		

		//Now draw directly. this fits nice.
		//TODO stop tinting everything a certain color somehow
		g = display.getGraphics();
		g.drawImage(bi,
				tag.getX() * defaultTileWidth,
				tag.getY() * defaultTileHeight,
				bg,null);
		
		
		//Drawing a grid
		for(int x = 0; x < matrixWidth; x++){
			for(int y = 0; y < matrixHeight; y++){
				g.setColor(Color.BLACK);
				g.drawLine(x*defaultTileWidth, 0, x*defaultTileWidth, display.getHeight());
				g.drawLine(0, y*defaultTileHeight, display.getWidth(), y*defaultTileHeight);
			}
		}
		
		
		panel.repaint();
	}

	@Override
	public JPanel toJPanel(int height, int width) {
		display = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);		
		
		//Panel stuff
		panel = new JPanel() {
			@Override
		    protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(display, 0, 0, null);
		    }
		};
		panel.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (keys.containsKey(VKMap.getKeyName(arg0.getKeyCode()))){
					if (display == null) return;
					String keyName = VKMap.getKeyName(arg0.getKeyCode());
					CMDTag presscmd = new CMDTag("press", keys.get(keyName).getObjid());
					conn.sendMessage(presscmd);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		panel.addMouseListener(new MouseListener() {			
			
			@Override
			public void mouseClicked(MouseEvent e) {				
				if(display == null)
					return;
				R2Vect v = new R2Vect(e.getX() / defaultTileWidth ,e.getY() / defaultTileHeight);
				if (tagLocation.get(v) != null){
				CMDTag cmd = new CMDTag("click", tagLocation.get(v).getObjid());
				conn.sendMessage(cmd);
				panel.requestFocus();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panel.setSize(width, height);
		panel.setPreferredSize(new Dimension(width, height));	
		panel.revalidate();
		panel.repaint();
		
		//This is the place where a panel wasn't displayed, so we need to draw on it
		//This will also sort the lists, so we get our data in the correct order
		if(allTags.size() != 0)
			for(SetTag tag: toSetTags())
				update(tag);
		
		return panel;
	}
	
	@Override
	public void setMoveOp(ElementOp moveOp, String description, Agent agent){
		for (SetTag s : allTags){
			if(s.getObjid().charAt(0) == 't'){
				if (s.getName() != null && s.getName().equals(description)){
					//Setting the tokens Op to a op that has nothing inside but
					//is of type move, so that way we know to look up that agent's specific
					//move op if this token is clicked
					tokens.get(s.getObjid()).setOp(new ElementOp(OpType.MOVE));
					s.setEnergy(100);
					Token t = (Token) Common.toElement(s);
					t.setOp(moveOp);
					if (!(agentsSpecificTokens.containsKey(agent))){
						agentsSpecificTokens.put(agent, new ArrayList<Token>());
						agentsSpecificTokens.get(agent).add(t);
					}
					else agentsSpecificTokens.get(agent).add(t);
					break;
				}
			}
		}
	}
	
	@Override
	public ElementOp getAgentsMoveOp(Agent a, String tokenID){
		for (Token t: agentsSpecificTokens.get(a)){
			if (t.getObjid().equals(tokenID))
				return t.getOp();
		};
		System.err.println("Could not find the agent's moveOp");
		return null;
	}
	
	
	
	@Override
	public void addToken(Token tok){
		tokens.put(tok.getSetTag().getObjid(), tok);
	}
	

	@Override
	public SetTag getMatrixTag() {
		return this.matrixTag;
	}

	@Override
	public List<SetTag> toSetTags() {
		//Sorts list for sending in correct order
		List<SetTag> tokens = new ArrayList<SetTag>();
		for(SetTag tag : allTags)			
			switch (tag.getObjid().charAt(0)){
			case 'k':
				tokens.add(tag);
				break;
			case 't':
				tokens.add(tag);
				break;
			case 'm':
				tokens.add(0, tag);
				break;
			case 'i':
				tokens.add(1, tag);
				break;
			default:
				System.err.println("Rouge settag in this matrix! " + tag);				
				break;				
			}		
		return tokens;
	}	
	
	@Override
	public Token getTokenFromID(String id){
		return tokens.get(id);
	}
	
	@Override
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	
	
}
