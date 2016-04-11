package com.putable.tilenet.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.putable.tilenet.AgentManager.AgentManager;
import com.putable.tilenet.AgentManager.AgentManagerImpl;
import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.ElementOp;
import com.putable.tilenet.Util.ElementOp.OpType;
import com.putable.tilenet.Util.XMLTags.CMDTag;
import com.putable.tilenet.Util.XMLTags.HearTag;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.blueprints.Grid;
import com.putable.tilenet.blueprints.Grid.GridType;
import com.putable.tilenet.blueprints.GridBuilder;
import com.putable.tilenet.blueprints.TileNetGridBuilder;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrixelement.Agent;

public class WorldModelImpl implements WorldModel {

	private Grid home, pairpanick, tripleTriad;
	private final AgentManager manager;
	private final ModelDispatch dispatch;
	private final ExecutorService ex;
	private final ConcurrentHashMap<Grid, SetTag> gridNames;

	public WorldModelImpl() {
		Thread.currentThread().setName("WORLD_MODEL");
		GridBuilder makeMatrices = new TileNetGridBuilder();
		home = makeMatrices.orderGrid(GridType.HOME);
		pairpanick = makeMatrices.orderGrid(GridType.PAIRPANICK);
		tripleTriad = makeMatrices.orderGrid(GridType.TRIPLETRIAD);
		home.addDefaultTokens();
		pairpanick.addDefaultTokens();
		tripleTriad.addDefaultTokens();

		gridNames = new ConcurrentHashMap<Grid, SetTag>();
		gridNames.put(pairpanick, pairpanick.getLayout().getMatrixTag());
		gridNames.put(home, home.getLayout().getMatrixTag());
		gridNames.put(tripleTriad, tripleTriad.getLayout().getMatrixTag());

		this.manager = new AgentManagerImpl();
		this.ex = Executors.newCachedThreadPool();
		this.dispatch = new ModelDispatch();

		
		ex.submit(dispatch);
	}

	@Override
	public void processClick(CMDTag tag, Agent agent) {
		ArrayList<XMLTag> tagList = new ArrayList<XMLTag>();
		List<Agent> agentList = new ArrayList<Agent>();
		
		//if you are moving this is the list of agents you are sending
		//your agent tag with a -x number
		List<Agent> otherAgentList = new ArrayList<Agent>();
		List<XMLTag> agentTagList = new ArrayList<XMLTag>();
		
		for (Grid g : gridNames.keySet()) {
			for (SetTag s : g.getLayout().toSetTags()) {
				if (s.getObjid().equals(tag.getObjid())) {
					agentList = agentsClickEffects(g.getLayout()
							.getTokenFromID(tag.getObjid()).getOp(), agent, g);
					if (g.getLayout().getTokenFromID(tag.getObjid())
							.getSetTag().getEnergy() >= 0) {

						tagList = (ArrayList<XMLTag>) chooseOp(
								agent,
								g,
								g.getLayout().getTokenFromID(tag.getObjid())
										.getOp(),
										tag.getObjid()).doOp(agent);
						
						//if this is a move op type, we need to send to all the agents that
						//aren't moving the agent setTag with the negative x value
						if (g.getLayout().getTokenFromID(tag.getObjid()).getOp().getType()
								== OpType.MOVE){
							for (Agent a : manager.getAgentsInside(g)){
								otherAgentList.add(a);
							}
						}

					}
				}
			}
		}
		

		if (! (otherAgentList.isEmpty())){
			SetTag agentBeingRemoved = generateRemoveAgentTag(agent);
			agentTagList.add(agentBeingRemoved);
			sendTheseToThese(agentTagList, otherAgentList);
			
		}
		
		if (agentList.isEmpty() || tagList.isEmpty())
			return;
		else {
			sendTheseToThese(tagList, agentList);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.putable.tilenet.server.WorldModel#processPress(com.putable.tilenet.Util.XMLTags.CMDTag, com.putable.tilenet.matrixelement.Agent)
	 */
	@Override
	public void processPress(CMDTag tag, Agent agent) {
		// TODO Auto-generated method stub
		
	}
	
	

	/**
	 * @param tagList
	 *            the list of tags being sent
	 * @param agentList
	 *            the list of agents tags are being sent to
	 */
	private void sendTheseToThese(List<XMLTag> tagList, List<Agent> agentList) {
		for (XMLTag s : tagList) {
			try {
				dispatch.put(s, agentList);
			} catch (InterruptedException e) {
				System.err.println("Error Responding to a Click!");
				e.printStackTrace();
			}
		}
	}

	private ElementOp chooseOp(Agent agent, Grid g, ElementOp op, String tokID) {
		switch (op.getType()) {
		case MOVE:
			return g.getLayout().getAgentsMoveOp(agent, tokID);
		case BROADCAST:
			return op;
		default:
			return null;
		}
	}

	/**
	 * @param op
	 *            the Operation defined by the click
	 * @param sender
	 *            the Agent sending the click
	 * @param g
	 *            the Grid which contains the Token that was clicked
	 * @return the list of Agents that the click affected
	 */
	private List<Agent> agentsClickEffects(ElementOp op, Agent sender, Grid g) {
		List<Agent> list = new ArrayList<Agent>();
		switch (op.getType()) {
		case NONE:
			return list;
		case BROADCAST:
			list = manager.getAgentsInside(g);
			break;
		case MOVE:
			list.add(sender);
			break;
		}
		return list;
	}

	/**
	 * Converts SayTags to HearTags to be sent see (c8.2.6.1.3.7.2.2) for more
	 * details
	 * 
	 * @param tag
	 *            the CMDTag being turned into a HearTag
	 * @param agent
	 *            the Agent the CMDTag is from
	 * @param isWhisper
	 *            If the CMDTag say is in the form of a whipser
	 * @return the HearTag converted from a sayTag
	 */
	private HearTag say2Hear(CMDTag tag, boolean isWhisper, Agent agent) {
		HearTag ht;
		// If it is a whipser, the "to" part of the HearTag is to that specific
		// Agent's ID
		if (Common.isWhisper(tag.getText()))
			ht = new HearTag(Common.getToFromWhisper(tag.getText()),
					agent.getName(), Common.changeToWhisperMessage(tag
							.getText()));
		// else then the "to" part of the HearTag is to the Matrix the Agent
		// sending the
		// Say tag is in
		else {
			ht = new HearTag(manager.getGridOf(agent).getLayout()
					.getMatrixTag().getName(), agent.getName(), tag.getText());
		}
		return ht;
	}

	@Override
	public void sendMatrix(Agent agent, Grid g) throws InterruptedException {
		List<Agent> agentMatrixIsBeingSentTo = new ArrayList<Agent>();
		List<Agent> agentsInThisMatrix = new ArrayList<Agent>();
		agentMatrixIsBeingSentTo.add(agent);
		
		for (SetTag s : g.getLayout().toSetTags()) {
			dispatch.put(s, agentMatrixIsBeingSentTo);
		}
		
		for (Agent a : manager.getAgentsInside(g)){
			agentsInThisMatrix.add(a);
			if (agent != a)
				dispatch.put(a.getSetTag(), agentMatrixIsBeingSentTo);
		}
		
		dispatch.put(agent.getSetTag(), agentsInThisMatrix);

		sendHearTag(new HearTag(g.getLayoutSetTag().getName(), g
				.getLayoutSetTag().getName(), "Welcome!"));
	}

	@Override
	public void giveAgent(Agent agent, Connection connection) {
		manager.addAgent(agent, connection);
		manager.agentMove(agent, home);
		// home.setConnection(connection);
		home.getLayout().setMoveOp(moveToMatrixOp(pairpanick, agent),
				"PairPanick", agent);
		home.getLayout().setMoveOp(moveToMatrixOp(tripleTriad, agent), "Triple Triad", agent);
		pairpanick.getLayout().setMoveOp(moveToMatrixOp(home, agent), "Home",
				agent);
		tripleTriad.getLayout().setMoveOp(moveToMatrixOp(home, agent), "Home", agent);
		
		pairpanick.giveManager(manager);

		try {
			sendMatrix(agent, home);
		} catch (InterruptedException e) {
			System.err.println("Error sending home matrix!");
			e.printStackTrace();
		}

	}

	@Override
	public void processSayCMD(CMDTag tag, Agent agent) {
		sendHearTag(say2Hear(tag, Common.isWhisper(tag.getText()), agent));
	}

	@Override
	public void sendHearTag(HearTag tag) {
		boolean isToMatrix = false;
		;
		List<Agent> agents = new ArrayList<Agent>();
		List<XMLTag> tags = new ArrayList<XMLTag>();
		for (SetTag s : gridNames.values()) {
			if (s.getName().equals(tag.getTo())) {
				for (Agent a : manager
						.getAgentsInsideFromMatrixID(s.getObjid())) {
					agents.add(a);
				}
				tags.add(tag);
				isToMatrix = true;
			}
		}
		if (!isToMatrix) {
			agents.add(manager.getAgentFromName(tag.getFrom()));
			if (manager.getAgentFromName(tag.getTo()) == null) {
				tags.add(new HearTag(tag.getFrom(), tag.getFrom(),
						"ERROR: WHISPER NOT RECEIVED"));
			} else {
				agents.add(manager.getAgentFromName(tag.getTo()));
				tags.add(tag);
			}
		}
		sendTheseToThese(tags, agents);
	}
	
	
	private SetTag generateRemoveAgentTag(Agent beingRemoved){
		//TODO eventually add all the other stuff agents can have, such as energy
		SetTag s = new SetTag(beingRemoved.getObjid());
		s.setName(beingRemoved.getName());
		s.setX(-1);
		return s;
	}
	

	/**
	 * Generates and operation that can be added to a Token for when a token
	 * determines which matrix the agent is now moving to
	 * 
	 * @param moveToThis
	 *            the Grid an agent is being told to move to
	 * @param agent
	 *            the agent who is moving
	 * @return the new operation
	 */
	private ElementOp moveToMatrixOp(final Grid moveToThis, final Agent agent) {
		ElementOp moveOp = new ElementOp(OpType.MOVE) {
			@Override
			public ArrayList<XMLTag> doOp(Agent agent) {
	
				ArrayList<XMLTag> list = new ArrayList<XMLTag>();
				Grid movingFrom = manager.getGridOf(agent);
				
				if (moveToThis == home && movingFrom == pairpanick){
					pairpanick.removeAgent(agent);
				}
				for (Agent a: manager.getAgentsInside(movingFrom)){
					SetTag remover = generateRemoveAgentTag(a);
					list.add(remover);
					
				}
				
				manager.agentMove(agent, moveToThis);
				
				if (moveToThis == pairpanick) {
					pairpanick.addAgent(agent);
					pairpanick.giveManager(manager);
				}
				list.add(new HearTag(agent.getName(), moveToThis
						.getLayoutSetTag().getName(), "Welcome!"));
				for (Agent a : manager.getAgentsInside(moveToThis)){
					list.add(a.getSetTag());
				}
				
				for (SetTag s : moveToThis.getLayout().toSetTags()) {
					list.add(s);
				}
				return list;
			}
		};
		return moveOp;
	}

	private final class ModelDispatch implements Callable<Void> {
		private final LinkedBlockingQueue<XMLTag> queue;
		private final Map<XMLTag, List<Agent>> whoIsBeingSentThis;
		//private final Map<XMLTag, Agent> whoSentThis;

		public ModelDispatch() {
			this.queue = new LinkedBlockingQueue<XMLTag>();
			this.whoIsBeingSentThis = new HashMap<XMLTag, List<Agent>>();
			//this.whoSentThis = new HashMap<XMLTag, Agent>();
		}

		@Override
		public Void call() throws Exception {
			XMLTag tag = null;
			List<Agent> agents = null;
			while (true) {
				tag = queue.take();
				if (isFromModel(tag)) {
					agents = whoIsBeingSentThis.get(tag);
					whoIsBeingSentThis.remove(tag);
				}

				switch (tag.getTagType()) {
				case CLIENT:
					break;
				case HEAR:
					for (Agent a : agents) {
						Connection c = manager.getConnection(a);
						c.sendMessage(tag);
					}
					break;
				case LOGGEDIN:
					break;
				case LOGGEDOUT:
					break;
				case SERVER:
					break;
				case SET:
					/*
					 * ((c8.9)) TAG: set ((c8.9.1)) FROM: Server
					 */
					for (Agent a : agents) {
						Connection c = manager.getConnection(a);
						c.sendMessage(tag);
					}
					break;
				case XRESPONSE:
					break;
				default:
					break;
				}
			}

		}

		private void put(XMLTag tag, List<Agent> lst)
				throws InterruptedException {
			queue.put(tag);
			whoIsBeingSentThis.put(tag, lst);
		}

		private boolean isFromModel(XMLTag tag) {
			switch (tag.getTagType()) {
			case SET:
				return true;
			case LOGGEDIN:
				return true;
			case LOGGEDOUT:
				return true;
			case HEAR:
				return true;
			case XRESPONSE:
				return true;
			default:
				return false;
			}
		}

	}



}
