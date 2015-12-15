package m2geii.reparties.mapp;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import m2geii.reparties.inters.ManagerAppInterface;

/**
 * Une classe principale de côte manager.
 * 
 * Depose les objets distantes ManagerApp
 * 
 * @author Ganza Mykhailo
 */
public class ManagerAppServer {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		
		if(args.length == 0){
			
			System.out.println("Usage:\n./mapp.sh localhost {list of servernames}\n./mapp.sh 192.168.120.2 {list of servernames}\n");
			
			//notice
			System.out.println("Execute before launching: export CLASSPATH=$(pwd)/mapp.jar:inters.jar:matrix.jar");
			
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
			
			System.setSecurityManager(new SecurityManager());
		}
		
		ManagerAppInterface ma = new ManagerApp(args);
		
	    Registry registry = LocateRegistry.getRegistry();
	    registry.rebind("122", ma);
	    
		//effacage d'affichage
		System.out.printf("\033[H\033[2J");
		System.out.flush();
	    
	    //affichage de l'etat de manager
	    ma.showStats();

	}
}
