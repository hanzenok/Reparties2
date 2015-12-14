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
	private String name;
	private Queue q;
	
	private ManagerAppInterface ma;
	
	protected ProcessingApp(String name, int ps) throws RemoteException{
		
		super();
		
		this.name = name;
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
		
		q.addProcess(new ProcessMultS(this, ma, q, M, clientname, scal, ps));
	}

	@Override
	public void mult(final String clientname, Matrix m1, Matrix m2) throws RemoteException {
		
		q.addProcess(new ProcessMultM(this, ma, q, m1, m2, clientname, ps));
	}

	@Override
	public void add(final String clientname, Matrix m1, Matrix m2) throws RemoteException {
		
		q.addProcess(new ProcessAddM(this, ma, q, m1, m2, clientname, ps));
	}

	@Override
	public void transpose(final String clientname, Matrix m) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getBusyness() throws RemoteException {
		
		return q.getDuration();
	}
	
	@Override
	public String getName() throws RemoteException {
		
		return name;
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
