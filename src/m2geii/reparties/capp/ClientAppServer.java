package m2geii.reparties.capp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.inters.ClientAppInterface;
import m2geii.reparties.inters.ManagerAppInterface;

public class ClientAppServer {
	
	public static void main(String[] args) throws MatrixException, MalformedURLException, RemoteException, NotBoundException {
		
		if(args.length <= 1){
			
			System.out.println("Usage:\n./capp.sh {clientname} localhost\n./capp.sh {clientname} 192.168.120.2\n");
			
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
		
		int i,j;
		int n=3, m=2;
		
		float[][] tab = new float[n][m];
		
		for(i=0;i<n;i++)
			for(j=0;j<m;j++)
				tab[i][j] = i+j;
			
		Matrix M = new Matrix(n, m, tab);
		
		Registry registry = LocateRegistry.getRegistry(args[1]);
		ManagerAppInterface ma = (ManagerAppInterface)registry.lookup("122");
		
		ClientAppInterface ca = new ClientApp(args[0], ma);
		
		System.out.println("Before calc (client " + ca.getName() + "): \n" + M);
		
		ca.mult(M, 2);
		
	}

}