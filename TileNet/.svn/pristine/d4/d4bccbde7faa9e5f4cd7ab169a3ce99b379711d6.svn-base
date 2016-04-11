package deprecated;

import javax.swing.ImageIcon;

import com.putable.tilenet.Util.XMLTags.SetTag;

import deprecated.XXXMatrixElementFactory.ElemType;

public abstract class XXXMatrixElement extends XXXTileNetElement{
	private String objid;
	
	public XXXMatrixElement(ElemType type){
		++uniqueObjID;
		setid("" + type.getPrefix() + uniqueObjID);
	}
	
	public XXXMatrixElement(SetTag tag){
		//setFgcolor(tag.getFgColor()); TODO change the SetTag getFgColor to
		//setBgcolor(tag.getBgColor()); return a Color and not a String
		setText(tag.getText());
		setName(tag.getName());
		setEnergy(tag.getEnergy());
		//setImage(tag.getImage()); TODO change the setImage to take a String
		setX(tag.getX());
		setY(tag.getY());
	}

	public abstract ImageIcon getDisplay(int tileWidth, int tileHeight);
}
