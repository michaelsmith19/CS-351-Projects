package deprecated;

import java.awt.Color;

import com.putable.tilenet.matrixelement.Image;


public interface XXXSetTag {
	
	public int getX();

	public int getY();

	public Color getBgcolor();

	public Color getFgcolor();

	public int getTileWidth();

	public int getTileHeight();

	public int getEnergy();

	public String getText();

	public Image getImage();
	
	public String getName();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public void setBgcolor(Color bgcolor);
	
	public void setFgcolor(Color fgcolor);
	
	public void setTileWidth(int width);
	
	public void setTileHeight(int height);
	
	public void setEnergy(int energy);
	
	public void setText(String text);
	
	public void setImage(Image image);
	
	public void setName(String name);
	
	
}
