package m2geii.reparties.queue;

/**
 * Un class abstrait qui represente un Thread,
 * avec la gestion de la durée d'éxecution
 * @author Ganza Mykhailo
 */
public abstract class Process extends Thread{

	protected int duration;
	
	/**
     * Constructeur principal
     * 
     * @param duration
     * 			durée d'éxection d'un thread
     */
	public Process(int duration){
		
		this.duration=duration;
	}
	
	/**
     * Renvoi la durée restante d'éxecution de thread
     * 
     * @return durée restante d'éxecution
     */
	public int getDuration(){
		
		return duration;
	}
	
	/**
     * Modifier la valeur de la durée
     * 
     * @param duration
     * 			valeur de la durée
     */
	public void setDuration(int duration){
		
		this.duration = duration;
	}
	
	/**
     * Méthode abstraite de l'éxecution de la tâche d'une thread
     */
	public abstract void runProcess();
}
