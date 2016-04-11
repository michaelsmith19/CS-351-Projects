package com.putable.tilenet.client;


import java.awt.BorderLayout;
import java.awt.Component;
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
import com.putable.tilenet.matrixelement.Agent;

public class ChatPanel extends JPanel{
		
		private HearTag hearing;
		private CMDTag saying;
		private Agent thisAgent;
		private JTextField textField;
		private JTextArea log;

		public ChatPanel(){
			textField = new JTextField(20);
			log = new JTextArea(5,50);
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
		
		
		public void processHearTag(HearTag h){
			hearing = h;
			log.append(h.getFrom() + " says : " + h.getMessage() + "\n");
	        textField.selectAll();
	        log.setCaretPosition(log.getDocument().getLength());
	        log.repaint();
		}
		
		public JTextField getTextField(){
			return this.textField;
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
		            	ChatPanel chat = new ChatPanel();
		            	frame.add(chat);
		            	frame.pack();
		            	frame.setPreferredSize(new Dimension(1000,500));
		            	frame.setVisible(true);
		            	chat.processHearTag(new HearTag("hello", "hi", "yo"));
		            }
		        });
		}
	
}
