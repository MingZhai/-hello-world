package vsue.rmi;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class VSAuctionServiceImpl implements VSAuctionService {
	private final HashMap<String,VSAuction> auctions = new HashMap <>();
	private final HashMap<VSAuction,VSAuctionuser> highest = new HashMap <>();
	private Object auctionName;
	private int price;
	private VSAuctionuser userName;
	public void registerAuction(VSAuction auction, int duration, VSAuctionEventHandler handler) throws VSAuctionException, RemoteException
	{
		synchronized(this){
			if(auctions.containsKey(auction.getName())){
				throw new VSAuctionException("an auction with this name already exists");
			}
			auctions.put(auction.getName(), auction);
		}
		Timer.schedule(new VSAuctionendTask(this,auction ,handler), duration*1000);
	}
	public VSAuction[] getAuctions() throws RemoteException{
		Collection<VSAuction> values;
		synchronized(this){
		values = auctions.values();
		}
		return values.toArray(new VSAuction[auctions.size()]);
	}
	public boolean placeBid(String userName, String auctionName, int price, VSAuctionEventHandler handler) throws VSAuctionException, RemoteException {
		VSAuction result;
		result = auctions.get(auctionName);
		if (result == null){
			throw new VSAuctionException("auction with "+ auctionName+ "not exist");
		}
		if(result.getPrice()>price){
			throw new VSAuctionException("your price is not highest");
			return false;
		}
		highest.put(result, new VSAuctionuser(auctionName,handler));
		result.price = price;
		if()
		return true;
		}
		
	public static final class VSAuctionuser{
		private final String _name;
		private final VSAuctionEventHandler _handler;
		public VSAuctionuser(String name, VSAuctionEventHandler handler){
			_name = name;
			_handler = handler;
		}
		public String getname(){
			return _name;
		}
		public VSAuctionEventHandler gethandler(){
			return _handler;
		}
	}
	private final class VSAuctionendTask() extends TimerTask
	{
		//TODO: finish this auction and see who wins
		private final VSAuction _auction;
		private final VSAuctionEventHandler creatorHandler;
		private final VSAuctionServiceImpl _owner;
		
		public VSAuctionendTask(VSAuctionServiceImpl owner,VSAuction auction, VSAuctionEventHandler handler){
			if (owner == null)
            {
                throw new IllegalArgumentException("The argument 'owner' must not be null.");
            }
			if (auction == null)
            {
                throw new IllegalArgumentException("The argument 'auction' must not be null.");
            }
		    _auction = auction;
			creatorHandler = handler;
			}
		public void run(){
			VSAuctionuser winner;
			auctions.remove(_auction.getName());
			winner = highest.remove(_auction);
			if(winner != null){
				 Thread responsethread = new Thread(); 
			}
		}
	}
	private static final  class callback implements Runnable{
		private final VSAuction _auction;
		private final VSAuctionEventHandler _handler;
		private final VSAuctionEventType _type;
		public callback(VSAuction auction,VSAuctionEventHandler handler,VSAuctionEventType type){
			_auction = auction;
			_handler = handler;
			_type = type;
		}
		public void run(){
			_handler.handleEvent(_type,_auction);
		}
	}


