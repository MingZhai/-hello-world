package vsue.rmi;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import vsue.rpc.VSAuctionService;
import vsue.rpc.VSAuctionServiceImpl;
import vsue.rpc.VSRemoteObjectManager;

public class VSAuctionRMIServer {

	public static void main(String[] args) throws Exception {
		// Remote-Objekt erzeugen
		VSAuctionService serviceimpl = new VSAuctionServiceImpl();    //schnittstell erstellen

		VSAuctionService  vsAuctionService = (VSAuctionService)(VSRemoteObjectManager.getInstance().exportObject(serviceimpl) );
		
		// Remote-Objekt bekannt machen
		//Registry registry =LocateRegistry.createRegistry(12345);
		
		//

		System.out.println("Service Start!");
		
		
		int port = 1234;
		ServerSocket server= new ServerSocket(port);
		
		Socket socket = null;
		boolean flag = true;
		
		while(flag){
			socket=server.accept();
		
			new ServerThread(socket).start();
			

		}

	}

}
