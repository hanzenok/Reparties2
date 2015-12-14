package m2geii.reparties.matrix;

public class MatrixException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public MatrixException(){}
	
	public MatrixException(String message){
		
		super(message);
	}
	
	public MatrixException(Throwable cause){
		
		super(cause);
	}

	public MatrixException(String message, Throwable cause){
		
		super(message, cause);
	}

	public MatrixException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
