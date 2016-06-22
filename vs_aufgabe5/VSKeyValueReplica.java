package vsue.replica;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
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
		VSKeyValueRequest _request ;
		VSKeyValueReply _reply;
		long time;
		String value;
		VSKeyValueReplyHandler client;
		private Hashtable<String, String> VSKeyValue = new Hashtable<String, String>();
		Registry registry = null;
		
	//export this replicate
	public VSKeyValueReplica(String ID, String replicaAddresses){
		this.RepId = "0";
		this.RepAdd = replicaAddresses;
		
	}
	// export this replicate
	public void init(){
		try {
			UnicastRemoteObject.exportObject(this, 0);
			
		} catch(RemoteException re) {
			System.err.println("Unable to export client: " + re);
			System.err.println("The client will not be able to receive replies");
		}
		
		try {
			registry = LocateRegistry.createRegistry(12345);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
		try {
			registry.bind(RepId, this);
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
	// handle request from client
	@Override
	public void handleRequest(VSKeyValueRequest request) throws RemoteException {
		// TODO Auto-generated method stub
		 _request = request;	
		 client = (VSKeyValueReplyHandler) _request.getClientStub();
			
			
		// decide which action to do
		 if(_request.getvalue() != null){
			 System.out.println("put");
			 VSKeyValue.put(request.getkey(),request.getvalue()); // put
			  time = System.currentTimeMillis();
		 }
		 
		 else if(_request.getrequest() == 1){
			 
			 System.out.println("get");
			 value = VSKeyValue.get(request.getkey()); // get
			 _reply = new VSKeyValueReply(value);
			 try {
				 client.handleReply(_reply);
			} catch (VSKeyValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		 }
		 else if(_request.getrequest() == 2){
			 System.out.println("remove");
			 VSKeyValue.remove(request.getkey()); // delete
		 }
		 else if(_request.getrequest() == 3){
			 System.out.println("exist");
			 value = VSKeyValue.get(request.getkey()); // exist
			if(value != null){
				_reply = new VSKeyValueReply(time, value);
				try {
					client.handleReply(_reply);
				} catch (VSKeyValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				_reply = new VSKeyValueReply(-1);
				try {
				 client.handleReply(_reply);
				} catch (VSKeyValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		 }
	}
	public static void main(String[] args) throws IOException{
		VSKeyValueReplica replica = new VSKeyValueReplica(args[0],args[1]);
		replica.init();
		System.out.println("replica start successfully");
	}
}
