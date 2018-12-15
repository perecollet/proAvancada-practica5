package Backtracking;

import java.util.Random;
import Keyboard.Keyboard;

/**
 * @author pcollet
 * 
 * 1.- Per qu� l�esquema de backtracking �s aplicable per a resoldre aquest enunciat.
 * 		�s un problema en el que la soluci� pot expressar-se com una seq��ncia de decisions; aquestes venen
 *		donades per decidir quin ex�men se li ha de donar a cada persona. S�ha de
 *		trobar aquella que compleixi la restricci� i resolgui el problema generan el n�mero d'ex�mens m�s petit
 *		possible.
 * 
 * 2.- Quines decisions ha de prendre la t�cnica del backtracking en aquest exercici.
 * 		Ha de assignar un ex�men a cada persona 
 * 
 * 3.- Quin �s el criteri per determinar si una decisi� �s o no acceptable.
 * 		Una decisi� ser� acceptable si t� ex�men i una persona ve�na no t� el mateix
 * 
 * 4.- Quin �s el criteri per determinar si un conjunt de decisions �s o no completable.
 * 		Que tothom tingui ex�men i que ning� tingui un ve� amb el amb el mateix ex�men que ell
 * 
 * 5.- Quin �s el criteri per determinar si un conjunt de decisions s�n o no soluci�.
 * 		Si el n�mero de ex�mens necessaris que proposa aquesta decisi�, es menor al n�mero d'ex�mens
 * 		necessaris proposatas per la situaci� actual.
 * 
 * 6.- Dibuixeu l�espai de cerca del problema, �s a dir l�arbre que recorrer� la t�cnica del
 * 	   backtracking, especificant quina ser� l�al�ada i l�amplada, indicant si s�n valors exactes
 * 	   o valors m�xims. Usareu marcatge?.
 * 		L'amplada i al�ada sera les mateixes ja que seran el n�mero d'estudiants, ja que el cas m�s dolent 
 * 		seria que cadasc� tigu�s un ex�men diferent (normalment, ja que si tots fossin ve�ns, seria la �nica 
 * 		soluci�).
 *
 */

public class Backtracking {
	
	private char [] identificador;
	private Boolean [][] relacions;
	private int numEstudiants;
	private int numExamens;
	private Integer [] solucio;
	private Integer [] millorSolucio;
	
	
	public Backtracking(int size) {
		
		Random random = new Random();
		
		this.identificador = new char [size];
		this.relacions = new Boolean [size][size];
		this.solucio = new Integer [size];
		this.millorSolucio = new Integer [size];
		this.numEstudiants = size;
		this.numExamens = size;
		
		for (int i = 0; i < size; i++) {
			identificador[i] = (char) ('A'+ i);
			for (int x = 0; x < size; x++) {
				if (relacions[i][x] == null) {
					if (x == i) {
						relacions[i][x] = false;
					}else {
						relacions[i][x] = random.nextBoolean();
						relacions[x][i] = relacions[i][x];
					}
				}
			}
		}
	}
	
	public static void main (String args[]){
		
		System.out.println("Quants estudiants faran l'ex�men? ");
		int num = Keyboard.readInt();
		Backtracking bk = new Backtracking(num);
		bk.tecnicaBacktracking(1);
		bk.veureRelacio();
		System.out.println("===================================");
		System.out.println("Assignaci� de ex�men a cada persona");
		System.out.println("===================================");
		bk.examenPers();
		System.out.println("===============================");
		System.out.println("N�mero de copies de cada ex�men");
		System.out.println("===============================");
		bk.copiesExamen();
	}
	
	
	public boolean acceptable () { 
		
		for (int i = 0; i < numEstudiants; i++) {
			if (solucio[i] == null) return false; // no es acceptable si tothom no t� ex�men
			else {
				for (int x = 0; x < numEstudiants; x++) {
					if (relacions[i][x]) {
						if (solucio[i] == solucio[x]) {
							return false; //no �s acceptable si dos persones ve�nes tenen el mateix ex�men
						}
					}
				}
			}
		}
		return true;
	}
	
	public int quantsExamens() {
		int contador = 0;
		for (int i = 0; i < numEstudiants; i++) {
			if (solucio[i] > contador) {
				contador = solucio[i];
			}
		}
		
		return contador;
	}
	
	public void tecnicaBacktracking(int k) {
		int j = 1;
		while(j <= numEstudiants){
			if(k <= numEstudiants){
				this.solucio[k-1]= j;
				if(this.acceptable()){
					int examens = this.quantsExamens();
					if(examens < this.numExamens){
						this.numExamens = examens;
						this.millorSolucio = this.solucio.clone();
					}
				}else{
					tecnicaBacktracking(k+1);
				}
				
				this.solucio[k-1] = 1;
			}
			j++;
		}
	}
	
	public void veureRelacio() {
		for (int i = 0; i < numEstudiants; i++) {
			System.out.println("--------------------------------------");
			for (int x = 0; x < numEstudiants; x++) {
				System.out.print("| Pers. "+ identificador[i] + " amb Pers. "+ identificador[x] +" s�n ve�nes "
						+ relacions[i][x]);
				if (relacions[i][x] ) {
					System.out.println(" |");
				}else {
					System.out.println("|");
				}
				
			}
			if (i == (numEstudiants-1)) {
				System.out.println("--------------------------------------");
			}
		}
		
	}
	
	public void copiesExamen() {
		for (int i = 0; i < numExamens; i++) {
			System.out.println("Examen "+(i+1)+" "+numTipusExamen(i+1)+" c�pies");
		}
	}
	
	public int numTipusExamen(int num) {
		int contador = 0;
		for (int i = 0; i < millorSolucio.length; i++) {
			if (millorSolucio[i] == num) {
				contador++;
			}
		}
		return contador;
	}
	
	public void examenPers() {
		for (int i = 0; i < numEstudiants; i++) {
			System.out.println("Pers. "+ identificador[i]+ " fa ex�men: "+ millorSolucio[i]);
		}
	}
	


}
