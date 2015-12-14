package m2geii.reparties.matrix;

import java.io.Serializable;

/**
 * Objet Matrix represente une matrice
 * @author Ganza Mykhailo
 */
public class Matrix implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int nbLines;
	private int nbCols;
	
	private float[][] tab;
	
	/**
     * Constructeur simple
     */
	public Matrix() throws MatrixException{
		
		nbLines = 0;
		nbCols = 0;
		tab = new float[0][0];
	}
	
	/**
     * Constructeur principal
     * 
     * @param nbLines
     * 			nombre des lignes dans une matrice
     * 
     * @param nbCols
     * 			nombre des colognes dans une matrice
     * 
     * @param tab
     * 			tabeau conteneur de la matrice
     */
	public Matrix(int nbLines, int nbCols, float[][] tab) throws MatrixException{
		
		if (nbLines != tab.length || nbCols != tab[0].length){
			
			throw new MatrixException("Matrix size is not coherent");
		}
		
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		this.tab = tab;
		
	}
	
	/**
     * Constructeur principal
     * 
     * @param nbLines
     * 			nombre des lignes dans une matrice
     * 
     * @param nbCols
     * 			nombre des colognes dans une matrice
     */
	public Matrix(int nbLines, int nbCols){
		
		this.nbLines = nbLines;
		this.nbCols = nbCols;
		
		tab = new float[nbLines][nbCols];
	}
	
	/**
     * Modifier une valeur de la matrice
     * 
     * @param i
     * 			l'indice de ligne de la matrice
     * 
     * @param j
     * 			l'indiece de cologne de la matrice
     * 
     * @param value
     * 			une valeur à attribuer
     */
	public void setValue(int i, int j, float value) throws MatrixException{
		
		if (i<0 || j<0 || i>nbLines-1 || j>nbCols-1){
			
			throw new MatrixException("Index out of matrix");
		}
		
		tab[i][j] = value;
		
	}
	
	/**
     * Renvoi une valeur de la matrice
     * 
     * @param i
     * 			l'indice de ligne de la matrice
     * 
     * @param j
     * 			l'indiece de cologne de la matrice
     * 
     * @return valeur de la matrice avec les coordonnées (i,j)
     */
	public float getValue(int i, int j) throws MatrixException{
		
		if (i<0 || j<0 || i>nbLines-1 || j>nbCols-1){
			
			throw new MatrixException("Index out of matrix");
		}
		
		return tab[i][j];
	}
	
	/**
     * Renvoi une matrice sous format de chaine de caractéres
     * 
     * @return une chaine des caractéres avec les lignes est colognes de la matrice
     */
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
	
	/**
     * Renvoi une nombre des colognes dans la matrice
     * 
     * @return nombre des colognes dans la matrice
     */
	public int cols(){
		
		return nbCols;
	}
	
	/**
     * Renvoi une nombre des lignes dans la matrice
     * 
     * @return nombre des lignes dans la matrice
     */
	public int rows(){
		
		return nbLines;
	}
}
