package Backtracking;

import java.util.Random;
import Keyboard.Keyboard;

/**
 * @author pcollet
 * 
 * 1.- Per què l’esquema de backtracking és aplicable per a resoldre aquest enunciat.
 * 		És un problema en el que la solució pot expressar-se com una seqüència de decisions; aquestes venen
 *		donades per decidir quin exàmen se li ha de donar a cada persona. S’ha de
 *		trobar aquella que compleixi la restricció i resolgui el problema generan el número d'exàmens més petit
 *		possible.
 * 
 * 2.- Quines decisions ha de prendre la tècnica del backtracking en aquest exercici.
 * 		Ha de assignar un exàmen a cada persona 
 * 
 * 3.- Quin és el criteri per determinar si una decisió és o no acceptable.
 * 		Una decisió serà acceptable si té exàmen i una persona veïna no té el mateix
 * 
 * 4.- Quin és el criteri per determinar si un conjunt de decisions és o no completable.
 * 		Que tothom tingui exàmen i que ningú tingui un veï amb el amb el mateix exàmen que ell
 * 
 * 5.- Quin és el criteri per determinar si un conjunt de decisions són o no solució.
 * 		Si el número de exàmens necessaris que proposa aquesta decisió, es menor al número d'exàmens
 * 		necessaris proposatas per la situació actual.
 * 
 * 6.- Dibuixeu l’espai de cerca del problema, és a dir l’arbre que recorrerà la tècnica del
 * 	   backtracking, especificant quina serà l’alçada i l’amplada, indicant si són valors exactes
 * 	   o valors màxims. Usareu marcatge?.
 * 		L'amplada i alçada sera les mateixes ja que seran el número d'estudiants, ja que el cas més dolent 
 * 		seria que cadascú tigués un exàmen diferent (normalment, ja que si tots fossin veïns, seria la única 
 * 		solució).
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
		
		System.out.println("Quants estudiants faran l'exàmen? ");
		int num = Keyboard.readInt();
		Backtracking bk = new Backtracking(num);
		bk.tecnicaBacktracking(1);
		bk.veureRelacio();
		System.out.println("===================================");
		System.out.println("Assignació de exàmen a cada persona");
		System.out.println("===================================");
		bk.examenPers();
		System.out.println("===============================");
		System.out.println("Número de copies de cada exàmen");
		System.out.println("===============================");
		bk.copiesExamen();
	}
	
	
	public boolean acceptable () { 
		
		for (int i = 0; i < numEstudiants; i++) {
			if (solucio[i] == null) return false; // no es acceptable si tothom no té exàmen
			else {
				for (int x = 0; x < numEstudiants; x++) {
					if (relacions[i][x]) {
						if (solucio[i] == solucio[x]) {
							return false; //no és acceptable si dos persones veïnes tenen el mateix exàmen
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
				System.out.print("| Pers. "+ identificador[i] + " amb Pers. "+ identificador[x] +" són veïnes "
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
			System.out.println("Examen "+(i+1)+" "+numTipusExamen(i+1)+" còpies");
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
			System.out.println("Pers. "+ identificador[i]+ " fa exàmen: "+ millorSolucio[i]);
		}
	}
	


}
