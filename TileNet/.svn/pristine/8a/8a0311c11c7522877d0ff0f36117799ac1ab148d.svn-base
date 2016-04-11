package deprecated;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import deprecated.XXXMatrixElementFactory.ElemType;


public class XXXToken extends XXXMatrixElement{
	
	public XXXToken() {
		super(ElemType.TOKEN);
	}

	@Override
	public ImageIcon getDisplay(int tileWidth,int tileHeight) {
		ImageIcon ic = new ImageIcon(makeBufferedImage(tileWidth, tileHeight),getText());		
		return ic;
	};

	public BufferedImage makeBufferedImage(int tileWidth, int tileHeight){
		BufferedImage buffImage = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = buffImage.getGraphics();
		g.setColor(getBgcolor());
		g.fillRect(0, 0, tileWidth, tileHeight);
		if (getImage() != null){
			//g.drawImage(getImage(), 0, 0,);
		}
		g.setColor(getFgcolor());
		g.drawString(getName(), (tileWidth/2), (tileHeight/2));
	
		
		return buffImage;
	}
}
