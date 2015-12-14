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
	
	final static java.util.Random rand = new java.util.Random();
	
	public static void main(String[] args) throws MatrixException, MalformedURLException, RemoteException, NotBoundException {
		
		if(args.length != 5){

			System.out.println("Usage:\n./capp.sh {clientname} localhost {nb lines} {nb colomns} {option}\n./capp.sh {clientname} 192.168.120.2 {nb lines} {nb colomns} {option}");
			System.out.println("\noption = 1-scalar multiplication, 2-matrix multiplication, 3-addition, 4-transposition");
			
			//notice
			System.out.println("Execute before launching: export CLASSPATH=$(pwd)/capp.jar:inters.jar:matrix.jar");
			
			System.exit(0);
		}
		
		if(System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
		
		int i,j;
		int n=Integer.parseInt(args[2]);
		int m=Integer.parseInt(args[3]);
		
		//chargement de la table avec les valeurs aleatoires
		float[][] tab = new float[n][m];
		for(i=0;i<n;i++)
			for(j=0;j<m;j++)
				tab[i][j] = rand.nextInt(100);
			
		Matrix M = new Matrix(n, m, tab);
		
		Registry registry = LocateRegistry.getRegistry(args[1]);
		ManagerAppInterface ma = (ManagerAppInterface)registry.lookup("122");
		
		ClientAppInterface ca = new ClientApp(args[0], ma);
		
		//traitement
		int option = Integer.parseInt(args[4]);
		
		switch(option){
		
		case 1: //multiplication de matrice par un scalaire
			
			//effacage d'affichage
			System.out.printf("\033[H\033[2J");
			System.out.flush();
			
			int scal = rand.nextInt(6);//valeur scalaire aleatoire
			
			//affichage
			System.out.println("Multiplication of\n");
			System.out.println(M);
			System.out.println("by " + scal + "\n");
			
			//calcul
			ca.mult(M, scal);
			//l'affichage de resultat sera solicite par manager
			
			break;

		default:
			break;
		}
		
		
		
	}

}