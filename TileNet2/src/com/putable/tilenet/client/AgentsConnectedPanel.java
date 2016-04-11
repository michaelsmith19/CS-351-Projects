package com.putable.tilenet.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.putable.tilenet.matrixelement.Agent;

public class AgentsConnectedPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JList agentList;
	DefaultListModel model;
	private HashMap<String, String> objid2name;

	/**
	 * Creates a {@link JPanel} that will display the agents in the current
	 * matrix. It will simply print out their names in a list.
	 */

	public AgentsConnectedPanel() {
		this.objid2name = new HashMap<String, String>();
		this.setPreferredSize(new Dimension(200, 500));
		this.setLayout(new BorderLayout());
		model = new DefaultListModel();
		model.addElement("Agents Connected");
		this.agentList = new JList(model);
		this.add(agentList, BorderLayout.CENTER);
		this.setVisible(true);
	}

	/**
	 * Adds an agent to the {@link AgentsConnectedPanel} when it enters the
	 * matrix associated with this Panel.
	 * 
	 * @param a
	 *            The agent to be added.
	 */
	public void addAgent(Agent a) {
		if (!(model.contains(a))) {
			objid2name.put(a.getObjid(), a.getName());
			model.addElement(objid2name.get(a.getObjid()));
			agentList.repaint();
		}
	}
	
	/**
	 * Removes an Agent from the {@link AgentsConnectedPanel}. Should be called
	 * when an Agent moves to a new matrix.
	 * 
	 * @param a The agent to be removed
	 */
	public void removeAgent(Agent a) {
		if(objid2name.containsKey(a.getObjid())){
			model.removeElement(objid2name.remove(a.getObjid()));
		}
		agentList.repaint();
	}

}
