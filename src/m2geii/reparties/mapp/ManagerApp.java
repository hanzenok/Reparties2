package m2geii.reparties.mapp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.inters.ClientAppInterface;
import m2geii.reparties.inters.ManagerAppInterface;
import m2geii.reparties.inters.ProcessingAppInterface;

/**
 * Un objet ManagerApp qui réalise 
 * les dispatchment de demandes 
 * des calcules des clients vers les serveurs
 * @author Ganza Mykhailo
 */
public class ManagerApp extends UnicastRemoteObject implements ManagerAppInterface {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ClientAppInterface> clients; //liste des clients connectees
	private ProcessingAppInterface[] servers; //liste des serveurs
	
	private String host;
	
	private Registry registry;
	
	/**
	 * Constructeur principale
	 * 
	 * @param args les parametres (l'adresse de toutes serveurs et leur noms) d'exécution de ManagerAppServer
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	protected ManagerApp(String[] args) throws RemoteException, NotBoundException{
		
		super();
		
		clients = new ArrayList<ClientAppInterface>();
		
		if(System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
		
		//hostname
		host = args[0];
		
		//initialisation des serverus
		int n = args.length - 1;//nombre des serveurs
		servers = new ProcessingAppInterface[n];
		
		registry = LocateRegistry.getRegistry(host);
		
		for(int i=1; i<args.length; i++){
			
			servers[i-1] = (ProcessingAppInterface)registry.lookup(args[i]);
			servers[i-1].registerManager(this);
		}
	}
	
	/**
	 * Constructeur simple
	 * 
	 * @throws RemoteException
	 */
	protected ManagerApp() throws RemoteException {
		
		super();
		
		clients = new ArrayList<ClientAppInterface>();
	}
	
	/**
	 * Réalise la multiplication scalire
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param M une matrice à multiplier
	 * @param scal un scalaire par quel multiplier la matrice
	 * 
	 * Le calcul est transmis à le serveur le moins occupée
	 */
	@Override
	public void mult(final String clientname, final Matrix M, final float scal) throws RemoteException, MatrixException {
		
		getLessBusyest().mult(clientname, M, scal);//reccuperer le serveur le moins occuppe et executer
		//se le serveur qui realie le calcul et solicite le client grace a la fonction sentToCLient de manager

	}
	
	/**
	 * Réalise la multiplication matricielle
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param m1 premier matrice à multiplier
	 * @param m2 2eme matrice à multiplier
	 * 
	 * Le calcul est transmis à le serveur le moins occupée
	 */
	@Override
	public void mult(final String clientname, Matrix m1, Matrix m2) throws RemoteException, MatrixException {
				
		getLessBusyest().mult(clientname, m1, m2);//reccuperer le serveur le moins occuppe et executer
		//se le serveur qui realie le calcul et solicite le client grace a la fonction sentToCLient de manager
	}

	/**
	 * Réalise l'addition matricielle
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param m1 premier matrice à additionner
	 * @param m2 2eme matrice à additionner
	 * 
	 * Le calcul est transmis à le serveur le moins occupée
	 */
	@Override
	public void add(final String clientname, Matrix m1, Matrix m2) throws RemoteException, MatrixException {
		
		getLessBusyest().add(clientname, m1, m2);//reccuperer le serveur le moins occuppe et executer
		//se le serveur qui realie le calcul et solicite le client grace a la fonction sentToCLient de manager
	}
	
	/**
	 * Réalise la transposition de la matrice
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param m une matrice à transposer
	 * 
	 * Le calcul est transmis à le serveur le moins occupée
	 */
	@Override
	public void transpose(final String clientname, Matrix m) throws RemoteException, MatrixException {
				
		getLessBusyest().transpose(clientname, m);//reccuperer le serveur le moins occuppe et executer
		//se le serveur qui realie le calcul et solicite le client grace a la fonction sentToCLient de manager
	}
	
	/**
	 * Permet d'enregistrer le client dans le manager
	 * 
	 * @param ca client à ajouter dans la liste de client de manager
	 */
	@Override
	public void registerClient(ClientAppInterface ca) throws RemoteException {

		clients.add(ca);
	}
	
	/**
	 * Fonction qui sera appele au niveu de ProcessingApp pour transmettre le résultat
	 * de caclule à client
	 * 
	 * La fonction réalise aussi le détachement de client de la liste de clients
	 * lorsque le caclule démandé est terminée
	 * 
	 * @param clientname nom de client demandeur de traitement
	 * @para M matrice avec le résultat de calcul
	 */
	@Override
	public void sendToClient(String clientname, Matrix M) throws RemoteException {
		
		//reccuperer le client specifique
		ClientAppInterface client = getClient(clientname);
		
		//mettre et afficher le resultat au niveau de client
		client.setResult(M);
		client.showResult();
		
		//detacher client de la liste des clients
		clients.remove(getClient(clientname));
	}
	
	/**
	 * La fonction qui determine le serveur
	 * les moins occupé de la liste de serveurs de manager
	 * 
	 * @return serveur le moins occupée
	 * @throws RemoteException
	 */
	public ProcessingAppInterface getLessBusyest() throws RemoteException{
		
		int i,n = servers.length;
		
		int i_min = 0; //indice d'un valeur minimale
		int min = servers[0].getBusyness(); //valeur maximale de ps
		
		if(n!=0){
			
			for(i=0; i<n; i++){
			
				if(min > servers[i].getBusyness()){
					
					i_min = i;
					min = servers[i].getBusyness();
				}
			}
			
			return servers[i_min];
		}
		else 
			return null;
	}
	
	/**
	 * Réalise un recherche de client 
	 * dans la liste des clients de manager
	 * par son nom
	 * 
	 * @param name nom de client à rechercher
	 * @return le client trouvé
	 * 
	 * @throws RemoteException
	 */
	public ClientAppInterface getClient(String name) throws RemoteException{
		
		for (ClientAppInterface client: clients){
			
			if (client.getName().equals(name)){
				
				return client;
			}
		}
		
		return null;
	}
	
	/**
	 * Une fonction appele par le serveur
	 * pour l'affichage de l'état de manageur
	 * 
	 * Affiche la liste des serveur et clients actifs
	 */
	@Override
	public void showStats() throws RemoteException {
		
		while(true){
			
			//affichage des clients conntectees
			int i,n = clients.size();
			System.out.println(n + " clients:\n");
			for(i=0; i<n; i++){
				
				System.out.println(clients.get(i).getName());
			}
			
			//affichage des serveurs connectees
			int m = servers.length;
			System.out.println("\n" + m + " servers:\n");
			for(i=0; i<m; i++){
				
				System.out.println(servers[i].getName() + ", time = " + servers[i].getBusyness() + "s");
			}
			
			//pause pour ne pas trop charger le manager
			try {Thread.sleep(1000);} 
			catch (InterruptedException e) { e.printStackTrace();}
			
			//effacage d'affichage
			System.out.printf("\033[H\033[2J");
			System.out.flush();
		}
	}


}
