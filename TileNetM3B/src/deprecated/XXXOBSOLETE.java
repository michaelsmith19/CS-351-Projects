package deprecated;

import java.awt.Color;
import java.awt.Image;

import deprecated.XXXMatrixElementFactory.ElemType;

/**Tried to implement builder pattern, not what we want. i will take these into factory
 * @author ClassCastExceptions
 *
 */
public class XXXOBSOLETE {
	public static long getUniqueObjID() {
		return uniqueObjID;
	}

	

	public static long uniqueObjID = 0;
	
	private final long objid;			//Never supplied
	private final int x, y;				//Required
	private final String name;			//Required
	private final Color bgcolor, fgcolor;				//Optional
	private final int tileWidth, tileHeight, energy;  //Optional
	private final String text;						//Optional
	private final Image image;						//Optional
	
	
	private XXXOBSOLETE(MatrixElementBuilder builder){
		this.objid = builder.objid;
		this.x = builder.x;
		this.y = builder.y;
		this.name = builder.name;
		this.bgcolor = builder.bgcolor;
		this.fgcolor = builder.fgcolor;
		this.tileHeight = builder.tileHeight;
		this.tileWidth= builder.tileWidth;
		this.energy = builder.energy;
		this.text = builder.text;
		this.image = builder.image;		
	}
	
	public String getName(){
		return name;
	}
	
	public static class MatrixElementBuilder {

		private long objid;
		private final int x;
		private final int y;
		private final String name;
		private Color bgcolor;
		private Color fgcolor;
		private int tileWidth;
		private int tileHeight;
		private int energy;
		private String text;
		private Image image;
		
		public MatrixElementBuilder(ElemType type, int x, int y, String name){
			this.x = x;
			this.y = y;
			this.name = name;
		}
		
		public MatrixElementBuilder bgcolor(Color bgcolor){
			this.bgcolor = bgcolor;
			return this;
		}
		public MatrixElementBuilder fgcolor(Color fgcolor){
			this.fgcolor = fgcolor;
			return this;
		}
		public MatrixElementBuilder tileWidth(int tileWidth){
			this.tileWidth = tileWidth;
			return this;
		}
		public MatrixElementBuilder tileHeight(int tileHeight){
			this.tileHeight = tileHeight;
			return this;
		}
		public MatrixElementBuilder energy(int energy){
			this.energy = energy;
			return this;
		}
		public MatrixElementBuilder text(String text){
			this.text = text;
			return this;
		}
		public MatrixElementBuilder image(Image image){
			this.image = image;
			return this;
		}		
		public XXXOBSOLETE build() {
			return new XXXOBSOLETE(this);
		}
		
	}

}