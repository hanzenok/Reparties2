package m2geii.reparties.papp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import m2geii.reparties.inters.ManagerAppInterface;
import m2geii.reparties.inters.ProcessingAppInterface;
import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.queue.Process;
import m2geii.reparties.queue.Queue;

public class ProcessTransM extends Process{
	
	private Matrix M;
	private int ps; //capacite
	private final static int COEF = 5;//coef de l'operation
	private ProcessingAppInterface pa; //processing pas qui execute le thread
	private ManagerAppInterface ma; //manager
	private String clientname; //nom de client qui demande les service
	
	private int duration; //duration d'operation
	
	private String message; //le chaine des caracteres conteneur de message a afficher
	private Queue q; 
	
	public ProcessTransM(ProcessingAppInterface pa, ManagerAppInterface ma, Queue q, Matrix M, String clientname, int ps) {
		
		super(ps*COEF);
		
		duration = ps*COEF;
		
		this.q = q;
		
		this.pa = pa;
		this.ma = ma;
		this.clientname = clientname;
		
		this.M = M;
		this.ps = ps;
		
		message = new String("Matrix transposition, client " + clientname + "\n");
	}

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
		int nbl = M.rows();
		int nbc = M.cols();
		
		//calcule de resultat
		Matrix new_M = new Matrix(nbl, nbc);
		
		for(i=0;i<nbl;i++){
			
			for(j=0;j<nbc;j++){
				
				try {
					
					new_M.setValue(i, j,  M.getValue(j, i)); //somme
					
				} 
				catch (MatrixException e) {e.printStackTrace();}
			}
		}
		
		//agir sur manager pour envoyer la reponse vers client
		try { ma.sendToClient(clientname, new_M); } 
		catch (RemoteException e) { e.printStackTrace(); } 
		catch (NotBoundException e) { e.printStackTrace(); }
		
		//effacage d'affichage
		System.out.printf("\033[H\033[2J");
		System.out.flush();
		try {System.out.println("Server: " + pa.getName() + ", ps = " + pa.getPs());} 
		catch (RemoteException e) {e.printStackTrace();}
	}
}
