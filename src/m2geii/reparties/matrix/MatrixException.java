package m2geii.reparties.matrix;

/**
 * Un gestionnaire d'erreur de la matrice
 * Herite de la classe Exception
 * @author Ganza Mykhailo
 */
public class MatrixException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructeur simple
     */
	public MatrixException(){}
	
	/**
     * Constructeur avec le message
     */
	public MatrixException(String message){
		
		super(message);
	}
	
	/**
     * Constructeur avec la cause
     */
	public MatrixException(Throwable cause){
		
		super(cause);
	}
	
	/**
     * Constructeur avec la cause et message
     */
	public MatrixException(String message, Throwable cause){
		
		super(message, cause);
	}
	
	/**
     * Constructeur avec toutes les parametres
     */
	public MatrixException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
