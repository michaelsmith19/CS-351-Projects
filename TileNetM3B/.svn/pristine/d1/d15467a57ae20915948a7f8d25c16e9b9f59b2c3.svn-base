package deprecated;

import java.awt.Color;
import java.awt.Point;

public class XXXHomeMatrix extends XXXMatrix{

	private final int MATRIX_COL_NUM = 2, MATRIX_ROW_NUM = 2, MATRIX_TILE_WIDTH = 200,
			MATRIX_TILE_HEIGHT = 200;
	//private Matrix hm;
	int tileRow, tileCol, tileWidth, tileHeight;
	
	
	public XXXHomeMatrix(){
		XXXToken tok = new XXXToken();
		XXXToken tok2 = new XXXToken();
		XXXToken tok3 = new XXXToken();
		XXXToken tok4 = new XXXToken();
		this.setTileWidth(MATRIX_TILE_WIDTH);
		this.setTileHeight(MATRIX_TILE_HEIGHT);
		this.setTileRow(MATRIX_ROW_NUM);
		this.setTileCol(MATRIX_COL_NUM);
		tok.setAttributes(Color.gray, Color.BLACK, 200, 200, "Welcome", "whatever", null);
		tok2.setAttributes(Color.BLACK, Color.gray, 200, 200, "to", "stuff", null);
		tok3.setAttributes(Color.BLACK, Color.gray, 200, 200, "Tile", "stuff", null);
		tok4.setAttributes(Color.gray, Color.BLACK, 200, 200, "Net", "stuff", null);
		this.addToken(new Point(0,0), tok);
		this.addToken(new Point(0,1), tok2);
		this.addToken(new Point(1,0), tok3);
		this.addToken(new Point(1,1), tok4);
	}
	
}
