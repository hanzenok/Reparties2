package m2geii.reparties.capp;

/**
 * Une classe principale de côte client.
 * Traite les parametres de programme etnrées par l'utilisateur.
 * Passe diférrentes traitements vers le ClientApp.
 * 
 * L'afichage de résultat des calcule est solicité par le manager
 * @author Ganza Mykhailo
 */

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.inters.ClientAppInterface;
import m2geii.reparties.inters.ManagerAppInterface;

public class ClientAppServer {
	
	final static java.util.Random rand = new java.util.Random(); //générateur aléatoire
	
	public static void main(String[] args) throws MatrixException, MalformedURLException, RemoteException, NotBoundException {
		
		if(args.length != 5){

			System.out.println("Usage:\n./capp.sh {clientname} localhost {nb lines} {nb colomns} {option}\n./capp.sh {clientname} 192.168.120.2 {nb lines} {nb colomns} {option}");
			System.out.println("\noption = 1-scalar multiplication, 2-matrix multiplication, 3-matrix addition, 4-transposition");
			
			//notice
			System.out.println("Execute before launching: export CLASSPATH=$(pwd)/capp.jar:inters.jar:matrix.jar");
			
			System.exit(0);
		}
		
		//securité
		if(System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
		
		int i,j;
		int n=Integer.parseInt(args[2]);//nombre des lignes
		int m=Integer.parseInt(args[3]);//nombre des colognes
		
		//connexion
		Registry registry = LocateRegistry.getRegistry(args[1]);
		ManagerAppInterface ma = (ManagerAppInterface)registry.lookup("122");
		
		ClientAppInterface ca = new ClientApp(args[0], ma);
		
		//traitement
		int option = Integer.parseInt(args[4]); //défini type des calcule à faire
		
		switch(option){
		
			case 1: //multiplication de matrice par un scalaire
				
				//effacage d'affichage
				System.out.printf("\033[H\033[2J");
				System.out.flush();
				
				//chargement de la table avec les valeurs aleatoires
				float[][] tab = new float[n][m];
				for(i=0;i<n;i++)
					for(j=0;j<m;j++)
						tab[i][j] = rand.nextInt(100);
					
				Matrix M = new Matrix(n, m, tab);
				
				int scal = rand.nextInt(6);//valeur scalaire aleatoire
				
				//affichage
				System.out.println("Multiplication of\n");
				System.out.println(M);
				System.out.println("by " + scal + "\n");
				
				//calcul
				ca.mult(M, scal);
				//l'affichage de resultat sera solicite par manager
				
				break;
	
			case 2: //multiplication de matrice par une matrice
				
				//effacage d'affichage
				System.out.printf("\033[H\033[2J");
				System.out.flush();
				
				//chargement de la table avec les valeurs aleatoires
				float[][] tab1 = new float[n][m];
				float[][] tab2 = new float[n][m];
				for(i=0;i<n;i++)
					for(j=0;j<m;j++){
						tab1[i][j] = rand.nextInt(100);
						tab2[i][j] = rand.nextInt(100);
					}
					
					
				Matrix m1 = new Matrix(n, m, tab1);
				Matrix m2 = new Matrix(n, m, tab2);
				
				//affichage
				System.out.println("Multiplication of\n");
				System.out.println(m1);
				System.out.println("by \n\n" + m2);
				
				//calcul
				ca.mult(m1, m2);
				//l'affichage de resultat sera solicite par manager
				
				break;
				
			case 3: //addition de matrice avec une matrice
				
				//effacage d'affichage
				System.out.printf("\033[H\033[2J");
				System.out.flush();
				
				//chargement de la table avec les valeurs aleatoires
				float[][] Tab1 = new float[n][m];
				float[][] Tab2 = new float[n][m];
				for(i=0;i<n;i++)
					for(j=0;j<m;j++){
						Tab1[i][j] = rand.nextInt(100);
						Tab2[i][j] = rand.nextInt(100);
					}
					
					
				Matrix M1 = new Matrix(n, m, Tab1);
				Matrix M2 = new Matrix(n, m, Tab2);
				
				//affichage
				System.out.println("Addition of\n");
				System.out.println(M1);
				System.out.println("with \n\n" + M2);
				
				//calcul
				ca.add(M1, M2);
				//l'affichage de resultat sera solicite par manager
				
				break;
				
			case 4: //transposition d'une matrice
				
				//effacage d'affichage
				System.out.printf("\033[H\033[2J");
				System.out.flush();
				
				//chargement de la table avec les valeurs aleatoires
				float[][] TAB = new float[n][m];
				for(i=0;i<n;i++)
					for(j=0;j<m;j++){
						TAB[i][j] = rand.nextInt(100);
					}
					
					
				Matrix mat = new Matrix(n, m, TAB);
				
				
				//affichage
				System.out.println("Transposition of\n");
				System.out.println(mat + "\n");
				
				//calcul
				ca.transpose(mat);
				//l'affichage de resultat sera solicite par manager
				
				break;
				
			default:
				break;
		}
		
		
		
	}

}