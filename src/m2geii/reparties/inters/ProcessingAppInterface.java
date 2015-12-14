package m2geii.reparties.inters;

/**
 * Interface d'un objet ProcessingApp
 * @author Ganza Mykhailo
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;

public interface ProcessingAppInterface extends Remote {
	
	public void mult(String clientname, Matrix m, float scal) throws RemoteException, MatrixException;
	public void mult(String clientname, Matrix m1, Matrix m2) throws RemoteException, MatrixException;
	public void add(String clientname, Matrix m1, Matrix m2) throws RemoteException, MatrixException;
	public void transpose(String clientname, Matrix m) throws RemoteException, MatrixException;
	
	public int getBusyness() throws RemoteException;
	public String getName() throws RemoteException;
	public int getPs() throws RemoteException;
	
	public void registerManager(ManagerAppInterface ma) throws RemoteException;
	
}
