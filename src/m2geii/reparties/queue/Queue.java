package m2geii.reparties.queue;

import java.util.LinkedList;

public class Queue {

	LinkedList<Process> queue=new LinkedList<Process>();
	Process runningProcess;
	QueueThread th;
	
	public void addProcess(Process p){
		
		queue.add(p);
	}
	
	public Process removeProcess(){
		
		Process p = null;
		
		if(!queue.isEmpty())
			p = queue.pop();
		
		return p;
	}
	
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
	
	public int getSize(){
		
		return queue.size() + 1; //+1 car le processus en cours d'execution ne pas comptee
	}
	
	public void start(){
		
		th = new QueueThread(this);
		th.start();
	}
	

	void stop(){
		
		while (getDuration()!=0);
		th.stopThread();
	}
	
}

