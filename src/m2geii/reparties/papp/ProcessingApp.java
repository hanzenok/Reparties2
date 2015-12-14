package m2geii.reparties.papp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import m2geii.reparties.matrix.Matrix;
import m2geii.reparties.matrix.MatrixException;
import m2geii.reparties.queue.Queue;
import m2geii.reparties.inters.ManagerAppInterface;
import m2geii.reparties.inters.ProcessingAppInterface;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessingApp extends UnicastRemoteObject implements ProcessingAppInterface {
	
	private static final long serialVersionUID = 1L;
	
	private int ps; //capacite de serveur
//	private int nb_clients;
	private Queue q;
	
	private ManagerAppInterface ma;
	
	protected ProcessingApp(int ps) throws RemoteException{
		
		super();
		
		this.ps = ps;
		
		q = new Queue();
		q.start();
	}
	
	protected ProcessingApp() throws RemoteException {
		
		super();
		
		ps = 1;
		
		q = new Queue();
		q.start();
	}

	@Override
	public void mult(final String clientname, final Matrix M, final float scal) throws RemoteException, MatrixException {
		
		q.addProcess(new ProcessMultS(q, M, clientname, ma, scal, ps));
	}

	@Override
	public void mult(Matrix m1, Matrix m2) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public void add(Matrix m1, Matrix m2) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public void transpose(Matrix m) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getBusyness() throws RemoteException {
		
		return q.getDuration();
	}

	@Override
	public int getPs() throws RemoteException {
		
		return ps;
	}
	
	@Override
	public void registerManager(ManagerAppInterface ma) throws RemoteException {
		
		this.ma = ma;
	}
	

}
