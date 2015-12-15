package m2geii.reparties.papp;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.queue.Queue;
import m2geii.reparties.inters.ManagerAppInterface;
import m2geii.reparties.inters.ProcessingAppInterface;

/**
 * Un objet ProcessingApp qui réalise 
 * les calcules matricielles
 * dans un file des threads
 * @author Ganza Mykhailo
 */
public class ProcessingApp extends UnicastRemoteObject implements ProcessingAppInterface {
	
	private static final long serialVersionUID = 1L;
	
	private int ps; //capacite de serveur
	private String name;
	private Queue q; //file d'attente des threads
	
	private ManagerAppInterface ma; //reference vers le manger
	
	/**
	 * Constructeur principale
	 * 
	 * @param name nome de serveur
	 * @param ps capacite de serveur
	 * 
	 * @throws RemoteException
	 * 
	 * A la crétion de serveur, l'exécution de la file d'attente est lancé
	 */
	protected ProcessingApp(String name, int ps) throws RemoteException{
		
		super();
		
		this.name = name;
		this.ps = ps;
		
		q = new Queue();
		q.start();
	}
	
	/**
	 * Constructeur simple
	 * 
	 * @throws RemoteException
	 * 
	 * A la crétion de serveur, l'exécution de la file d'attente est lancé
	 */
	protected ProcessingApp() throws RemoteException {
		
		super();
		
		ps = 1;
		
		q = new Queue();
		q.start();
	}
	
	/**
	 * Réalise la multiplication scalire
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param M une matrice à multiplier
	 * @param scal un scalaire par quel multiplier la matrice
	 * 
	 * Le résultat est transmis à manager dans le ProcessMultS
	 */
	@Override
	public void mult(final String clientname, final Matrix M, final float scal) throws RemoteException, MatrixException {
		
		q.addProcess(new ProcessMultS(this, ma, q, M, clientname, scal, ps));
	}
	
	/**
	 * Réalise la multiplication matricielle
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param m1 premier matrice à multiplier
	 * @param m2 2eme matrice à multiplier
	 * 
	 * Le résultat est transmis à manager dans le ProcessMultM
	 */
	@Override
	public void mult(final String clientname, Matrix m1, Matrix m2) throws RemoteException {
		
		q.addProcess(new ProcessMultM(this, ma, q, m1, m2, clientname, ps));
	}
	/**
	 * Réalise l'addition matricielle
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param m1 premier matrice à additionner
	 * @param m2 2eme matrice à additionner
	 * 
	 * Le résultat est transmis à manager dans le ProcessAddM
	 */
	@Override
	public void add(final String clientname, Matrix m1, Matrix m2) throws RemoteException {
		
		q.addProcess(new ProcessAddM(this, ma, q, m1, m2, clientname, ps));
	}

	/**
	 * Réalise la transposition de la matrice
	 * 
	 * @param clientname nom de client qui demande le cacule
	 * @param m une matrice à transposer
	 * 
	 * Le résultat est transmis à manager dans le ProcessTransM
	 */
	@Override
	public void transpose(final String clientname, Matrix m) throws RemoteException {
		
		q.addProcess(new ProcessTransM(this, ma, q, m, clientname, ps));
	}
	
	/**
	 * Renvoi le temps restant de calcul de toutes
	 * les traitement dans serveur
	 * 
	 * @return temps restant d'exécution
	 */
	@Override
	public int getBusyness() throws RemoteException {
		
		return q.getDuration();
	}
	
	/**
	 * Renvoi le nom de serveur
	 * 
	 * @return nom des serveur
	 */
	@Override
	public String getName() throws RemoteException {
		
		return name;
	}
	
	/**
	 * Renovoi la capacite de serveur
	 * 
	 * @return capcité de serveur
	 */
	@Override
	public int getPs() throws RemoteException {
		
		return ps;
	}
	
	/**
	 * Permet d'enregistrer la reference vers le manager
	 *
	 *@param ma manager
	 */
	@Override
	public void registerManager(ManagerAppInterface ma) throws RemoteException {
		
		this.ma = ma;
	}
	

}
