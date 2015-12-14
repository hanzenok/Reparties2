package m2geii.reparties.inters;

/**
 * Interface d'un objet ManagerApp
 * @author Ganza Mykhailo
 */

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;

public interface ManagerAppInterface extends Remote {
	
	public void mult(String clientname, Matrix m, float scal) throws RemoteException, MatrixException;
	public void mult(String clientname, Matrix m1, Matrix m2) throws RemoteException, MatrixException;
	public void add(String clientname, Matrix m1, Matrix m2) throws RemoteException, MatrixException;
	public void transpose(String clientname, Matrix m) throws RemoteException, MatrixException;
	
	public void registerClient(ClientAppInterface ca) throws RemoteException;
	public void sendToClient(String clientname, Matrix M) throws RemoteException, NotBoundException;
	
	public void showStats() throws RemoteException;
}
