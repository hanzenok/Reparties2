package m2geii.reparties.queue;

/**
 * Un thread qui gère la liste 
 * d'éxecution des threads Queue
 * @author Ganza Mykhailo
 */
class QueueThread extends Thread {
	
	Queue q; // liste des threads à gèrer
	private boolean stopThread = false;
	
	/**
	 * Constructeur principale
	 * 
	 * @param q liste des threads à gèrer
	 */
	QueueThread(Queue q) {
		
		this.q=q;
	}
	
	/**
	 * Méthode principale qui réalise 
	 * la travail de gestion de la Queue
	 */
	public void run(){
		
		boolean end=false;
		
		while(!end){
			
			try {
				Process p = q.removeProcess();
				q.runningProcess=p;
				
				if(p!=null)
					p.runProcess();
				
				synchronized(this){
					
					Thread.yield();
					end=this.stopThread;
				}
			} catch(Exception e) {
				
			}
		}
	}
	
	/**
	 * Aretêr le thread
	 */
	public synchronized void stopThread(){
		
		this.stopThread = true;
	}
}
