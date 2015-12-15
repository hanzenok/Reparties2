package m2geii.reparties.papp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import m2geii.reparties.inters.ManagerAppInterface;
import m2geii.reparties.inters.ProcessingAppInterface;
import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.queue.Process;
import m2geii.reparties.queue.Queue;

/**
 * Un thread qui hérite le Process et 
 * exécute le traitement de l'addition
 * matricielle.
 * Affichage de l'état de serveur.
 * 
 * Le temps de traitement est défini par une constante COEF et
 * la capcité de serveur ps
 * 
 * @author Ganza Mykhailo
 */
public class ProcessAddM extends Process{
	
	private Matrix m1,m2;
	private final static int COEF = 10;//coef de l'operation
	private ProcessingAppInterface pa; //processing pas qui execute le thread
	private ManagerAppInterface ma; //manager
	private String clientname; //nom de client qui demande les service
	
	private int duration; //duration d'operation
	
	private String message; //le chaine des caracteres conteneur de message a afficher
	private Queue q; //utilise pour affichage de l'état de la fils d'attente
	
	/**
	 * Constructeur principal
	 * 
	 * @param pa serveur qui execute ce thread
	 * @param ma manager
	 * @param q	la Queue à quel ce thread appartient; utilise pour l'affichage de l'état de fils d'attente
	 * @param m1 premier matrice à additionner
	 * @param m2 deuxieme matrice à additionner
	 * @param clientname le nom de client demandeur de traitement
	 * @param ps la capacité de serveur
	 */
	public ProcessAddM(ProcessingAppInterface pa, ManagerAppInterface ma, Queue q, Matrix m1, Matrix m2, String clientname, int ps) {
		
		super(ps*COEF);
		
		duration = ps*COEF;
		
		this.q = q;
		
		this.pa = pa;
		this.ma = ma;
		this.clientname = clientname;
		
		this.m1 = m1;
		this.m2 = m2;
		
		message = new String("Matrix addition, client " + clientname + "\n");
	}
	
	/**
	 * La fonction principale qui réalise le caclule mathematique
	 * Renvoi le résultat vers le client grâce à la méthode ma.sendtToClient()
	 * 
	 * Réalise l'affichage de l'état de serveur et file d'attente
	 */
	@Override
	public void runProcess() {
		
		//attente&affichage
		while(duration!=0) {
			
			try {
				
				System.out.printf("\033[H\033[2J");//effacage d'affichage
				System.out.flush();
				
				System.out.println("Server: " + pa.getName() + ", ps = " + pa.getPs());
				System.out.println("Total duration: " + q.getDuration()  + "s, clients: " + q.getSize() + "\n"); //affichage de la fil d'attente de serveur
				
				//message interactif
				message += ".";
				System.out.println(message);
				
				Thread.sleep(1000); //pause d'une seconde
				
				setDuration(duration--); //decrementation de la duree
				
			} 
			catch(InterruptedException e) {Thread.currentThread().interrupt();} 
			catch (RemoteException e) {e.printStackTrace();}
		}
		System.out.println();
		
		//calcul
		int i,j;
		int nbl1 = m1.rows();
		int nbc1 = m1.cols();
		int nbl2 = m2.rows();
		int nbc2 = m2.rows();
		
		//si les tailles ne corespondent pas
		if((nbc1 != nbc2) || (nbl1 != nbl2))
			try { throw new MatrixException("Sizes of two matrix do not correspond"); } 
			catch (MatrixException e1) {e1.printStackTrace();}
		
		//calcule de resultat
		Matrix M = new Matrix(nbl1, nbc2);
		for(i=0;i<nbl1;i++){
			
			for(j=0;j<nbc1;j++){
				
				try {
					
					M.setValue(i, j,  m1.getValue(i, j) + m2.getValue(i, j)); //somme
					
				} 
				catch (MatrixException e) {e.printStackTrace();}
			}
		}
		
		//agir sur manager pour envoyer la reponse vers client
		try { ma.sendToClient(clientname, M); } 
		catch (RemoteException e) { e.printStackTrace(); } 
		catch (NotBoundException e) { e.printStackTrace(); }
		
		//effacage d'affichage
		System.out.printf("\033[H\033[2J");
		System.out.flush();
		try {System.out.println("Server: " + pa.getName() + ", ps = " + pa.getPs());} 
		catch (RemoteException e) {e.printStackTrace();}
	}
}