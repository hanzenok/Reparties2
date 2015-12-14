package m2geii.reparties.capp;

/**
 * Un objet ClientApp qui réalise 
 * les calcules matricielles
 * grâce à un objet distant RMI
 * @author Ganza Mykhailo
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.inters.ClientAppInterface;
import m2geii.reparties.inters.ManagerAppInterface;

public class ClientApp extends UnicastRemoteObject implements ClientAppInterface{
	
	private static final long serialVersionUID = 1L;
	
	private ManagerAppInterface ma; //manager associee a client
	private Matrix M; //matrice avec le resultat des calcules
	
	private String name; //identifiant de client
	
	/**
     * Constructeur sans parametres
     * 
     */
	protected ClientApp() throws RemoteException {
		
		super();
	}
	
	/**
     * Constructeur principal
     * 
     * @param name
     * 			nom de client
     * 
     * @param ma
     * 			reference vers manager qui gere le client
     * 
     * Dans cet constructeur le client s'enregistre 
     * au niveau de manager
     */
	
	protected ClientApp(String name, ManagerAppInterface ma) throws RemoteException{
		
		super();
		
		this.ma = ma;
		this.name = name;
		
		ma.registerClient(this);
	}

	
	/**
     * Affichage de la matrice avec le résultat des calcules
     * 
     * Fonction éxecuté dans le côte manager
     */
	@Override
	public void showResult() throws RemoteException {
		
		System.out.println("Result: \n\n" + M);
	}
	
	/**
     * Charger la matrice avec le résultat de calcule
     * 
     * @param M
     * 			matrice avec le résultat
     * 
     * Fonction éxecuté dans le côte manager
     */
	@Override
	public void setResult(Matrix M) throws RemoteException {
		
		this.M = M;
	}
	
	/**
     * Renvoi la matrice
     * 
     * @return la matrice avec le résultat de calcule
     * 
     * Fonction éxecuté dans le côte manager
     */
	@Override
	public Matrix getResult() throws RemoteException {
		
		return M;
	}
	
	/**
     * Réalise la multiplication scalaire
     * 
     * @param m
     * 			matrice à multiplier
     * 
     * @param scal
     * 			scalaire par quel multiplier
     * 
     * Fais appel à un objet distant RMI
     */
	@Override
	public void mult(Matrix m, float scal) throws RemoteException, MatrixException {
		
		ma.mult(name,m,scal);
	}

	/**
     * Réalise la multiplication matricielle
     * 
     * @param m1
     * 			premier matrice à multiplier
     * 
     * @param m2
     * 			deuxiemme matrice à multiplier
     * 
     * Fais appel à un objet distant RMI
     */
	@Override
	public void mult(Matrix m1, Matrix m2) throws RemoteException, MatrixException {

		ma.mult(name, m1, m2);
	}

	/**
     * Réalise l'addition matricielle
     * 
     * @param m1
     * 			premier matrice de l'addition
     * 
     * @param m2
     * 			deuxiemme matrice de l'addition
     * 
     * Fais appel à un objet distant RMI
     */
	@Override
	public void add(Matrix m1, Matrix m2) throws RemoteException, MatrixException {
		
		ma.add(name, m1, m2);
	}
	
	/**
     * Réalise la transposition d'une matrice
     * 
     * @param m
     * 			matrice à transposer
     * 
     * Fais appel à un objet distant RMI
     */
	@Override
	public void transpose(Matrix m) throws RemoteException, MatrixException {
		
		ma.transpose(name, m);
	}
	
	/**
     * Renvoi le nom de client
     * 
     * @return le nom de client
     */
	@Override
	public String getName() {
		
		return name;
	}

	
}
