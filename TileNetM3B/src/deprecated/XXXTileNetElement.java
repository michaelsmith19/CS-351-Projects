package deprecated;

import java.awt.Color;
import java.awt.Image;

public abstract class XXXTileNetElement {
	public static long uniqueObjID = 0;
	private String objid;
	private Color bgcolor, fgcolor;
	private int x, y, tileWidth, tileHeight, energy;
	private String name, text;
	private Image image;

	public void setAttributes(Color bgcolor, Color fgcolor, int x, int y,
			String name, String text, Image image) {
		setBgcolor(bgcolor);
		setFgcolor(fgcolor);
		setX(x);
		setY(y);
		setName(name);
		setText(text);
		setImage(image);
	}

	public void setAttributes(XXXSetTag st) {

	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public int getTileWidth() {
		return tileHeight;
	}

	public int getTileHeight() {
		return tileWidth;
	}

	public String getid() {
		return this.objid;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setid(String objid) {
		this.objid = objid;
	}

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

	public Color getFgcolor() {
		return fgcolor;
	}

	public void setFgcolor(Color fgcolor) {
		this.fgcolor = fgcolor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

}
