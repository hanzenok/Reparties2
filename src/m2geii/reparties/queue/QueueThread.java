package m2geii.reparties.queue;

class QueueThread extends Thread {
	
	Queue q;
	private boolean stopThread = false;
	
	QueueThread(Queue q) {
		
		this.q=q;
	}
	
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
	

	public synchronized void stopThread(){
		
		this.stopThread = true;
	}
}
