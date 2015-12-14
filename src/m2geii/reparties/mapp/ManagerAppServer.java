package m2geii.reparties.mapp;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import m2geii.reparties.inters.ManagerAppInterface;

public class ManagerAppServer {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		
		if(args.length == 0){
			
			System.out.println("Usage:\n./mapp.sh localhost {list of servernames}\n./mapp.sh 192.168.120.2 {list of servernames}\n");
			
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
			
			System.setSecurityManager(new SecurityManager());
		}
		
		ManagerAppInterface ma = new ManagerApp(args);
		
	    Registry registry = LocateRegistry.getRegistry();
	    registry.rebind("122", ma);
	    System.out.println("Manager at " + args[0] + " launched. Serves servers ");
	    for(int i=1; i<args.length; i++){
	    	
	    	System.out.println(args[i]);
	    }
	    System.out.println("\n");
	    
	    ma.showStats();

	}
}
