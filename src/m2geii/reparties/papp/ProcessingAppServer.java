package m2geii.reparties.papp;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import m2geii.reparties.inters.ProcessingAppInterface;

public class ProcessingAppServer {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		if(args.length <= 1){
			
			System.out.println("Usage:\n./papp.sh {server name} {ps}\n");
			
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
			
			System.setSecurityManager(new SecurityManager());
		}
		
		ProcessingAppInterface pa = new ProcessingApp(Integer.parseInt(args[1]));
		
	    Registry registry = LocateRegistry.getRegistry();
	    registry.rebind(args[0], pa);
	    System.out.println("Server " + args[0] + " is launched. ps is " + ((ProcessingApp) pa).getPs() + "\n");

	}
}