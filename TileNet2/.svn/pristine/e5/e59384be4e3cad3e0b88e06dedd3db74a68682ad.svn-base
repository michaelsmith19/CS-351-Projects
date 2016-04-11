package com.putable.tilenet.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.putable.tilenet.Util.XMLTags.CMDTag;
import com.putable.tilenet.Util.XMLTags.HearTag;

import deprecated.XXXAgent;

public class ChatFeature extends JPanel{
	
	private HearTag hearing;
	private CMDTag saying;
	private XXXAgent thisAgent;
	private JTextField textField;
	private JTextArea log;

	public ChatFeature(){
		textField = new JTextField(20);
		log = new JTextArea(5,20);
		log.setEditable(false);
		JScrollPane jsp = new JScrollPane(log);
		textField.setBounds(0, 0, 500, 500);
		textField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
		        String text = textField.getText();
		        log.append(text + "\n");
		        textField.selectAll();

		        //Make sure the new text is visible, even if there
		        //was a selection in the text area.
		        log.setCaretPosition(log.getDocument().getLength());
			}
			
		});
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		layout.addLayoutComponent(textField, BorderLayout.SOUTH);
		layout.addLayoutComponent(log, BorderLayout.NORTH);
		this.add(textField);
		this.add(log);
	}
	
	
	public void getLatestHearTag(HearTag h){
		if (h.getTo().equals (thisAgent.getName())){
			hearing = h;
		}
	}
	
	

	
	
	public CMDTag say(String saying){
		
		saying = saying.trim();
		//if(isWhisper) TODO getObjId and use that information to get
		//this agent from the server
		CMDTag cmdTag = new CMDTag("say",null);
		cmdTag.setText(saying);
		return cmdTag;
	}
	
	public boolean isWhisper(String saying){
		if(saying.charAt(0)==('(') && saying.charAt(saying.length())==(')')){
			int i = 1;
			while(!(Character.isWhitespace(saying.charAt(i)))){
				i++;
			}
			if(isValidObjId(saying.substring(1, i))) return true;
		}
		return false;
	}
	
	private boolean isLeadingObjId(char c){
		switch(c){
		case 'a' : return true;
		case 't' : return true;
		case 'i' : return true;
		case 'm' : return true;
		case 'k' : return true;
		default : return false;
		}		
	}
	
	private boolean isValidObjId(String sub){
		if (isLeadingObjId(sub.charAt(0))){
			for (int i = 1; i < sub.length(); i++){
				if(!(Character.isDigit(sub.charAt(i))))
					return false;
			}
			return true;
		}
		return false;
	}
	

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	JFrame frame = new JFrame();
	            	JPanel chat = new ChatFeature();
	            	frame.add(chat);
	            	frame.pack();
	            	frame.setPreferredSize(new Dimension(1000,500));
	            	frame.setVisible(true);
	            }
	        });
	}
}
