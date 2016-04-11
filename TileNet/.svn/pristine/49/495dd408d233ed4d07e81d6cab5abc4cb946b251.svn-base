package deprecated;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;

/**
 * @author ClassCastExeptions Recieve log-in tag
 */
public class XXXMatrix extends XXXTileNetElement {

	public ConcurrentHashMap<Point, XXXToken> layout = new ConcurrentHashMap<Point, XXXToken>();

	public ArrayList<XXXAgent> agentsConnected = new ArrayList<XXXAgent>();

	public ArrayList<XXXKey> keyList = new ArrayList<XXXKey>();

	private int tileWidth, tileHeight, tileRow, tileCol;

	public XXXMatrix() {
		++uniqueObjID;
		setid("m" + uniqueObjID);
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public XXXMatrix(int tileWidth, int tileHeight, int tileRow, int tileCol) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileRow = tileRow;
		this.tileCol = tileCol;
	}

	public int getTileRow() {
		return tileRow;
	}

	public void setTileRow(int tileRow) {
		this.tileRow = tileRow;
	}

	public int getTileCol() {
		return tileCol;
	}

	public void setTileCol(int tileCol) {
		this.tileCol = tileCol;
	}

	public ConcurrentHashMap<Point, XXXToken> getLayout() {
		return layout;
	}

	public void addToken(Point point, XXXToken t) {
		if (t.getBgcolor() == null)
			t.setBgcolor(this.getBgcolor());
		if (t.getFgcolor() == null)
			t.setFgcolor(this.getFgcolor());
		layout.put(point, t);
	}

	public void addKey(XXXKey key) {
		keyList.add(key);
	}

	public void addAgent(XXXAgent agent) {
		agentsConnected.add(agent);
	}

	/**
	 * Takes a generic {@link XMLTag} and executes the changes that are required
	 * for that tag (this can be 0 or many).
	 * 
	 * @param xe
	 *            the SMLTag to be executed.
	 */
	// TODO process an XmlElement to determine if changes were made to the
	// matrix.
	public void processXmlElement(XMLTag xe) {
		switch (xe.getTagType()) {
		case LOGGEDIN:
			;
			break;
		case SET:
			processXmlSetTag((SetTag) xe);
			break;
		case CLIENT:
			break;
		case CMD:
			break;
		case HEAR:
			break;
		case LOGGEDOUT:
			break;
		case LOGIN:
			break;
		case LOGOUT:
			break;
		case SERVER:
			break;
		case XREQUEST:
			break;
		case XRESPONSE:
			break;
		default:
			break;
		}

	}

	public void processXmlSetTag(SetTag st) {
		// TODO change the setAttributes method to accept colors as a String or
		// change the way the the getBgColor() and getFgColor in the SetTag
		// class
		// setAttributes((st.getBgColor()), st.getFgColor(), st.getX(),
		// st.getY(), st.getName(),
		// st.getText(), st.getImage());
	}

}
