package com.putable.tilenet.client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.putable.tilenet.Util.XMLTags.CMDTag;
import com.putable.tilenet.Util.XMLTags.HearTag;

public class ChatPanel extends JPanel {

	/**
	 * This versions unique id.
	 */
	private static final long serialVersionUID = -412252068617476038L;

	private JTextField textField;
	private JTextArea log;

	/**
	 * Makes a {@link JPanel} that will have a chat log and textField. You will
	 * be able to type your message into the {@link JTextField} in order to send
	 * your message.
	 */
	public ChatPanel() {
		textField = new JTextField(20);
		log = new JTextArea(5, 50);
		log.setEditable(false);
		JScrollPane jsp = new JScrollPane(log);
		textField.setBounds(0, 0, 500, 500);
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		layout.addLayoutComponent(textField, BorderLayout.SOUTH);
		layout.addLayoutComponent(jsp, BorderLayout.NORTH);

		this.add(jsp);
		this.add(textField);
	}

	/**
	 * Takes a {@link HearTag} from the server and displays the message in the
	 * log.
	 * 
	 * @param h
	 *            The {@link HearTag} from the server.
	 */
	public void processHearTag(HearTag h) {
		log.append(h.getFrom() + " says : " + h.getMessage() + "\n");
		log.setCaretPosition(log.getDocument().getLength());
		log.repaint();
	}

	public JTextField getTextField() {
		return this.textField;
	}

	/**
	 * Creates a {@link CMDTag} of type 'say' with the String that was input to
	 * the TextField.
	 * 
	 * @param saying
	 *            The message from the {@link JTextField}.
	 * @return The constructed {@link CMDTag}
	 */
	public CMDTag say(String saying) {
		saying = saying.trim();
		CMDTag cmdTag = new CMDTag("say", null);
		cmdTag.setText(saying);
		return cmdTag;
	}

	/**
	 * Returns whether or not the given message from the {@link JTextField} is a
	 * whisper or not.
	 * 
	 * @param saying
	 *            The message from the {@link JTextField}
	 * 
	 * @return true if the message follows our rules for a whisper.
	 */
	public boolean isWhisper(String saying) {
		if (saying.charAt(0) == ('(')
				&& saying.charAt(saying.length()) == (')')) {
			int i = 1;
			while (!(Character.isWhitespace(saying.charAt(i)))) {
				i++;
			}
			if (isValidObjId(saying.substring(1, i)))
				return true;
		}
		return false;
	}

	private boolean isLeadingObjId(char c) {
		switch (c) {
		case 'a':
			return true;
		case 't':
			return true;
		case 'i':
			return true;
		case 'm':
			return true;
		case 'k':
			return true;
		default:
			return false;
		}
	}

	private boolean isValidObjId(String sub) {
		if (isLeadingObjId(sub.charAt(0))) {
			for (int i = 1; i < sub.length(); i++) {
				if (!(Character.isDigit(sub.charAt(i))))
					return false;
			}
			return true;
		}
		return false;
	}
}
