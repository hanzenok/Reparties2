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
			
			//notice
			System.out.println("Execute before launching: export CLASSPATH=$(pwd)/papp.jar:inters.jar:matrix.jar:queue.jar");
			System.out.println("Then: rmiregistry&");
			
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
			
			System.setSecurityManager(new SecurityManager());
		}
		
		ProcessingAppInterface pa = new ProcessingApp(args[0], Integer.parseInt(args[1]));
		
	    Registry registry = LocateRegistry.getRegistry();
	    registry.rebind(args[0], pa);

		//effacage d'affichage
		System.out.printf("\033[H\033[2J");
		System.out.flush();
		
		//affichage de l'etat de serveru
		System.out.println("Server: " + pa.getName() + ", ps = " + pa.getPs());

	}
}