package m2geii.reparties.matrix;

import java.io.Serializable;

public class Matrix implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int nbLines;
	private int nbCols;
	
	private float[][] tab;
	
	public Matrix() throws MatrixException{
		
		nbLines = 0;
		nbCols = 0;
		tab = new float[0][0];
	}
	
	public Matrix(int nbLines, int nbCols, float[][] tab) throws MatrixException{
		
		if (nbLines != tab.length || nbCols != tab[0].length){
			
			throw new MatrixException("Matrix size is not coherent");
		}
		
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		this.tab = tab;
		
	}
	
	public Matrix(int nbLines, int nbCols){
		
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		
		tab = new float[nbLines][nbCols];
	}
	
	public void setValue(int i, int j, float value) throws MatrixException{
		
		if (i<0 || j<0 || i>nbLines-1 || j>nbCols-1){
			
			throw new MatrixException("Index out of matrix");
		}
		
		tab[i][j] = value;
		
	}
	
	public float getValue(int i, int j) throws MatrixException{
		
		if (i<0 || j<0 || i>nbLines-1 || j>nbCols-1){
			
			throw new MatrixException("Index out of matrix");
		}
		
		return tab[i][j];
	}
	
	public String toString(){
		
		String str = new String("");
		int i,j;
		
		for(i=0;i<nbLines;i++){
			
			for(j=0;j<nbCols;j++){
				
				str = str.concat(String.valueOf(tab[i][j])).concat("\t");
			}
			
			str = str.concat("\n");
		}
		
		return str;
	}
	
	public int cols(){
		
		return nbCols;
	}
	
	public int rows(){
		
		return nbLines;
	}
}
