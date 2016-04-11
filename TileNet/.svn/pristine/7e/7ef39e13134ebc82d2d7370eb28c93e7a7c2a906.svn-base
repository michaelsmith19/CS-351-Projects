package com.putable.tilenet.matrixelement;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.factory.MatrixElementFactory;

public class Token extends MatrixElement {
	MatrixElementFactory thisUses;

	public Token(MatrixElementFactory thisSpecificfactory) {
		this.thisUses = thisSpecificfactory;
	}

	public Token(SetTag setTag) {
		this.type = ElemType.TOKEN;
		this.tag = setTag;
	}

	public Token() {
		this.type = ElemType.TOKEN;
		this.tag = new SetTag("t847");
	}

	@Override
	void makeElement() {
		// tag = thisUses.addSetTag();
		type = thisUses.addElemType();
	}

	public ImageIcon draw(int tileWidth, int tileHeight) {
		ImageIcon ic = new ImageIcon(makeBufferedImage(tileWidth, tileHeight),
				this.getSetTag().getText());
		return ic;
	};

	public BufferedImage makeBufferedImage(int tileWidth, int tileHeight) {
		BufferedImage buffImage = new BufferedImage(tileWidth, tileHeight,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = buffImage.getGraphics();
		g.setColor(this.getSetTag().getBgColor());
		g.fillRect(0, 0, tileWidth, tileHeight);
		if (this.getSetTag().getImage() != null) {
			// g.drawImage(getImage(), 0, 0,);
		}
		g.setColor(this.getSetTag().getFgColor());
		g.drawString(this.getSetTag().getName(), (tileWidth / 2),
				(tileHeight / 2));

		return buffImage;
	}
}
