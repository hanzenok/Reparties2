package m2geii.reparties.queue;

import java.util.LinkedList;

/**
 * Un file d'attente d'éxecution qui
 * gere l'éxectuion des plusieurs threads
 * et tient compte de la durée d'éxecution
 * @author Ganza Mykhailo
 */
public class Queue {

	LinkedList<Process> queue=new LinkedList<Process>(); //liste de threads
	Process runningProcess; //thread en cours d'éxecution
	QueueThread th; //gestionnaire de la liste des threads
	
	/**
     * Ajout un Process dans un file d'attente
     * 
     * @param p
     * 			Process à ajouter dans la liste
     */
	public void addProcess(Process p){
		
		queue.add(p);
	}
	
	/**
	 * Retire un Process de la liste d'attente
	 * 
	 * @return Process retiré
	 */
	public Process removeProcess(){
		
		Process p = null;
		
		if(!queue.isEmpty())
			p = queue.pop();
		
		return p;
	}
	
	/**
	 * Renvoi la durée restante de l'éxecution de toute la liste
	 * 
	 * @return durée restante de l'éxecution
	 * 
	 * La fonction somme la durée restante des toues les threads de la liste
	 */
	public int getDuration(){
		
		int duration=0;
		
		for(Process p : queue){
			
			if(p!=null)
				duration+=p.getDuration();
		}
		
		if(runningProcess!=null)
			duration+=runningProcess.getDuration();
		
		return duration;
	}
	
	/**
	 * Renvoi la taille de la liste d'attent
	 * 
	 * @return nombre des threads restantes dans la liste
	 */
	public int getSize(){
		
		return queue.size() + 1; //+1 car le processus en cours d'execution ne pas comptee
	}
	
	/**
	 * Lance l'éxecution de la liste des threads
	 */
	public void start(){
		
		th = new QueueThread(this);
		th.start();
	}
	
	/**
	 * Arete l'éxecution de la liste des threads
	 * 
	 * Attende que toutes les threads set terminent
	 */
	void stop(){
		
		while (getDuration()!=0);
		th.stopThread();
	}
	
}

