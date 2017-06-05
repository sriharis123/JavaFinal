package rmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import cards.Card;
import javax.swing.Timer;

import main.Game;

public abstract class GameClient /*extends UnicastRemoteObject */implements Serializable, ActionListener {
	//, Remote {//, Runnable {
	
	//TODO implement ActionListener for when the new set of commands is received.
	
	private static final long serialVersionUID = 1L;
	
	protected boolean connected = false;
	
	private int tag = 2, serverPort = 1099;
	
	protected GameServerInterface remoteServer;
	
	protected String name, serverIP, serverName;
	private ArrayList<GameServerInterface> clients;
	private ArrayList<ClientCommand> currentMoves;
	protected static Registry serverRegistry;
	
	protected Timer timer;
	
	/*protected Thread thread;
	
	private boolean running;
	
	private void startThread() {
		if(running){
			return;
		}
		else {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run() {
		while(running) {
			
		}
	}*/
	
	/**
	 * DO NOT USE
	 */
	public static void main(String[] args) {
		/*try {
			GameClient client = new GameClient(0, "127.0.0.1", 1099, null, null);
			client.connectToServer();
		} catch(Exception e) {
			e.printStackTrace();	
		}*/
	}
	
	public GameClient() throws RemoteException {
		
	}
	
	public GameClient(String refTag) throws RemoteException {
		try {
			if(refTag == null)
				serverIP = java.net.InetAddress.getLocalHost().getHostAddress(); 
			else {
				serverIP = "127.0.0.1";
				name = "Client @" + serverIP + "," + refTag;
				return;
			}
		} catch (UnknownHostException e) {
			serverIP = null;
			e.printStackTrace();
			return;
		}
		name = "Client @" + serverIP;
	}
	
	/**
	 * Use this constructor for multiple computer connection
	 * @param tag Tag of this client
	 * @param serverIP IP of other client
	 * @param otherPort Port of other client
	 * @throws RemoteException
	 */
	public GameClient(int tag, String serverIP, int serverPort) throws RemoteException {
		this.serverPort = serverPort;
		this.tag = tag;
		this.serverIP = serverIP;
			serverName = "Server @" + serverIP;
	}
	
	/**
	 * 
	 * @param tag
	 * @param serverIP IP of other client
	 * @param port Port to connect to on other device
	 * @param refTag Only has to be filled out if testing is carried out on same device. Make note of the refTag when using it. 
	 * 			Null if multiple devices
	 * @throws RemoteException
	 */
	public GameClient(int tag, String serverIP, String refTag) throws RemoteException{
		this(refTag);
		this.tag = tag;
		this.serverIP = serverIP;
		/*if(refTag != null) {
			serverName = "Server @" + serverIP + "," + refTag;
		}
		else {*/
			serverName = "Server @" + serverIP;
		//}
		System.out.println("Name:" + name + "   ServerName:" + serverName);
	}
	
	public boolean connectToServer() {
		try {
			System.out.println("Server: " + serverName);
			serverRegistry = LocateRegistry.getRegistry(serverIP, serverPort);
			System.out.println("Looking for " + serverName);
			remoteServer = (GameServerInterface) serverRegistry.lookup(serverName);
			System.out.println("Connected to server.");
			connected = true;
			test();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}

		if(connected) {
			timer = new Timer(1000, this);
			timer.start();
		}
		return connected;
	}
	
	public void sendOverNetwork(Card cardChanged) {
		ArrayList<Card> al = new ArrayList<Card>();
		al.add(cardChanged);
		
		try {
			remoteServer.receiveRecentCardChanges(name, al);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void test() {
		String a;
		try {
			a = remoteServer.getName();
			System.out.println(a);
			
			String[] t = remoteServer.getData(new String[]{"Test1", "Test2"});
			System.out.println(t[0] + t[1]);
			
			String[] b = remoteServer.getData(new String[]{"a1", "b2", "c3"});
			System.out.println(b[0] + b[1]);
			
			
			ArrayList<ClientCommand> cc = new ArrayList<ClientCommand>();
			/*cc.add(new ClientCommand(CommandEnum.MOVE_DOWN));
			cc.add(new ClientCommand(CommandEnum.MOVE_UP));
			cc.add(new ClientCommand(e));*/
			cc.add(new ClientCommand(CommandEnum.values()[tag], new Card("r", "p") {
			}));
			
			remoteServer.receiveRecentCommands(this.name, cc);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}	
	}
	
	public boolean isConnected() {
		return connected;
	}
}
