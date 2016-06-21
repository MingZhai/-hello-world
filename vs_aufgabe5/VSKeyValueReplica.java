package vsue.replica;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

//----------------------------------------------code added 21.06

public class VSKeyValueReplica implements VSKeyValueRequestHandler{
		String RepId;
		String RepAdd;
	//export this replicate
	public VSKeyValueReplica(String ID, String replicaAddresses){
		this.RepId = ID;
		this.RepAdd = replicaAddresses;
	}
	// export this replicate
	public void init(){
		Remote SR = null;
		try {
			  SR = UnicastRemoteObject.exportObject(this, 0);
			
		} catch(RemoteException re) {
			System.err.println("Unable to export client: " + re);
			System.err.println("The client will not be able to receive replies");
		}
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(12345);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
		try {
			registry.bind(RepId, SR);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Hashtable<String, String> VSKeyValue = new Hashtable<String, String>();
	
	@Override
	public void handleRequest(VSKeyValueRequest request) throws RemoteException {
		// TODO Auto-generated method stub
		VSKeyValueRequest _request = new VSKeyValueRequest();
		_request = request;
	}
	public static void main(String[] args) throws IOException{
		VSKeyValueReplica replica = new VSKeyValueReplica(args[0],args[1]);
		replica.init();
	}
}
