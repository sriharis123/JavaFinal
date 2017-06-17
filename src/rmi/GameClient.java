package rmi;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import cards.Card;

import javax.swing.Timer;

/**
 * Abstract class representing any GameClient to a GameServer.
 * @author Srihari Subramanian
 *
 */
public abstract class GameClient implements Serializable, ActionListener {
	
	private static final long serialVersionUID = -6238246696085178736L;

	protected boolean connected = false;
	
	private int tag = 2, serverPort = 1099;
	
	/**
	 * Please do not change the value of remoteServer or  
	 * set to null. It is protected for ease of access.
	 */
	protected GameServerInterface remoteServer;
	
	private String name, serverIP, serverName;
	
	/**
	 * Please do not change the value of serverRegistry or  
	 * set to null. It is protected for ease of access.
	 */
	protected static Registry serverRegistry;
	
	protected Timer timer;
	//protected ClientInfo myClient;
	
	protected ClientInfo clientInfo;
	
	public GameClient() throws RemoteException {
		
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
		//myClient = new ClientInfo(tag, serverIP, serverPort, serverName);
	}
	
	/**
	 * Method used to connect this GameClient to a host GameServer.  
	 * @return whether there was a successful connection or not.
	 */
	public boolean connectToServer() {
		try {
			System.out.println("Server: " + serverName);
			serverRegistry = LocateRegistry.getRegistry(serverIP, serverPort);
			System.out.println("Looking for " + serverName);
			remoteServer = (GameServerInterface) serverRegistry.lookup(serverName);
			clientInfo = new ClientInfo(tag);
			remoteServer.connect(clientInfo);
			System.out.println("Connected to server.");
			connected = true;
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
	
	public int getTag() {
		return tag;
	}
	
	public boolean isConnected() {
		return connected;
	}
	/*
	public ClientInfo getClientInfo() {
		return myClient;
	}
	*/
	public String getName() {
		return name;
	}
	public String getIP() {
		return serverIP;
	}
}
