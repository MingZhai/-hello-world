package vsue.rmi;


import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import vsue.rpc.VSConnection;
import vsue.rpc.VSObjectConnection;
import vsue.rpc.VSRemoteObjectManager;
import vsue.rpc.VSRevMsg;
import vsue.rpc.VSSenMsg;

public class ServerThread  extends Thread {
	
	private ServerSocket socket;
	
	public ServerThread (){
		
		//this.socket=socket;
		
		System.out.println("neu Thread created!");
	}
	
	public void run() {
		//new VSServer(socket).aufRuf();
		
		try {
			socket=new ServerSocket(0);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println(socket.getLocalPort() + " accepting");
		Socket newSocket=null;
		try {
			newSocket =socket.accept();//new Socket(Inet4Address.getLocalHost().getHostAddress(),socket.getLocalPort());//Inet4Address.getLocalHost().getHostAddress()
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(socket.getLocalPort() + " accepted");
		VSObjectConnection connect=new VSObjectConnection(new VSConnection(newSocket));
		Serializable se=null;
		Object o=null;
		
		do {
			try {
				    //Anfrage empfangen
					se=connect.receiveObject();
					
					//System.out.println("receive from client "+se);
					
					if(se!=null){
						
						VSSenMsg senMsg=(VSSenMsg)se;
						
						o=VSRemoteObjectManager.getInstance().invokeMethod(senMsg.getObjectID(),senMsg.getMethodName(),senMsg.getParameters());
						
						VSRevMsg revMsg=new VSRevMsg(o,"fehler");
						connect.sendObject(revMsg);
				
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
		
		}while(true);	
	}
}
