package m2geii.reparties.inters;

import java.rmi.Remote;
import java.rmi.RemoteException;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;

/**
 * Interface d'un objet ClientApp
 * @author Ganza Mykhailo
 */
public interface ClientAppInterface extends Remote{
	
	public void showResult() throws RemoteException;
	public void setResult(Matrix M) throws RemoteException;
	public Matrix getResult() throws RemoteException;
	
	public void mult(Matrix m, float scal) throws RemoteException, MatrixException;
	public void mult(Matrix m1, Matrix m2) throws RemoteException, MatrixException;
	public void add(Matrix m1, Matrix m2) throws RemoteException, MatrixException;
	public void transpose(Matrix m) throws RemoteException, MatrixException;
	
	public String getName() throws RemoteException;
}
