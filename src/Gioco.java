//200612 Andrea Voci
import java.util.*;

public class Gioco {
	private int dimensione;
	private int[][] campo;
	private ArrayList<cordinata> posLibere;
	private boolean Vinta;
	private boolean Persa;
	private int max = 2;
	private ArrayList<int[][]> storia = new ArrayList<>();
	
	public int[][] getcampo(){ 
		return this.campo;
	}
	
	public int getdimensione() {
		return dimensione;
	}
	
	private class cordinata{ // Classe funzionale alla calsse gioco che identifica una cella del campo
		private int cordinatax;
		private int cordinatay;
		
		private cordinata(int a, int b) {
			cordinatax = a;
			cordinatay = b;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			cordinata other = (cordinata) obj;
			if (cordinatax != other.cordinatax)
				return false;
			if (cordinatay != other.cordinatay)
				return false;
			return true;
		}		
	}

	
	public Gioco (int d) { // costruttore
		posLibere = new ArrayList<cordinata>();
		if (d <= 2) {
			campo = new int[3][3];
			dimensione = 3;		
		}
		campo = new int[d][d];
		dimensione = d;
		for (int i=0; i<d; i++)
			for(int j=0; j<d; j++) {
				cordinata t = new cordinata(i,j);
				posLibere.add(t);
			}//inizzializzaGioco();
		start();
	}
	
	public Gioco() {
		this(4); // Utilizza il costruttore base
	}
	
	public void start() { // Inizia una nuova Partita
		posNumero();
		posNumero();
		
	}
	
	public void playGioco () { // Funzione per un'implementazione di tipo testuale.
		Scanner s = new Scanner(System.in);
		while(Vinta != true) {
			verificaPerdita();
			if (Persa) return;
			if (Persa == true) break;
			int n = s.nextInt();
			switch(n) {
			case 8:muoviup(); break;
			case 2:muovidown(); break;
			case 4:muovileft(); break;
			case 6:muoviright(); break;
			} 
			posNumero(); 
			
			verificaVittoria();
			stampa(campo);
			
		}
		s.close();
	}
	
	public void muoviup() { // Mossa di gioco verso l'alto
		AggiungiCampo();
		for(int j =0; j<campo[0].length; j++) {
			spostaUP(j);
			sommaUP(j);
			spostaUP(j);
		}
	}
	
	public void muovidown() { // Mossa di gioco verso il basso
		AggiungiCampo();
		for(int j =0; j<campo[0].length; j++) {
			spostaDOWN(j);
			sommaDOWN(j);
			spostaDOWN(j);
		}
	}
	
	public void muovileft() { // Mossa di gioco verso sinistra
		AggiungiCampo();
		for (int i = 0;i < campo.length; i++ ) {
			spostaLEFT(i);
			sommaLEFT(i);
			spostaLEFT(i);
		}
	}
	
	public void muoviright() { // Mossa di gioco verso destra
		AggiungiCampo();
		for (int i = 0;i < campo.length; i++ ) {
			spostaRIGHT(i);
			sommaRIGHT(i);
			spostaRIGHT(i);
		}
	}
	
	private void spostaUP(int j) { //sposta gli elementi della riga passata come argomento
			int posLibera = dimensione;
			for(int i =0;i<campo.length;i++) {
				if(campo[i][j]==0 && i < posLibera) posLibera = i;
				if(campo[i][j] != 0 && posLibera != dimensione) {
					campo[posLibera][j] = campo[i][j];
					campo[i][j] = 0;
					posLibere.remove(new cordinata(posLibera,j));
					posLibere.add(new cordinata(i,j));
					posLibera++;
				}
			}
	}
	
	private void sommaUP(int j) {  
			for(int i = 1; i<campo.length; i++)
				if (campo[i-1][j] == campo[i][j] && campo[i][j] != 0){
					campo[i-1][j] += campo[i][j];
					if(campo[i-1][j]>max) max = campo[i-1][j];
					campo[i][j] = 0;
					posLibere.add(new cordinata(i,j));
					i++;
				}
	}
	
	private void spostaDOWN(int j) {  //sposta gli elementi della riga passata come argomento
		int posLibera = -1;
		for(int i = campo.length-1;i>=0;i--) {
			if(campo[i][j]==0 && i > posLibera) posLibera = i;
			if(campo[i][j] != 0 && posLibera != -1) {
				campo[posLibera][j] = campo[i][j];
				campo[i][j] = 0;
				posLibere.remove(new cordinata(posLibera,j));
				posLibere.add(new cordinata(i,j));
				posLibera--;
			}
		}
	}
	
	private void sommaDOWN(int j) { //somma gli elementi adiacenti ceh risultino uguali tra loro (nella direzone della mossa)
		for(int i = campo.length-2;i>=0;i--)
			if (campo[i+1][j] == campo[i][j] && campo[i][j] != 0){
				campo[i+1][j] += campo[i][j];
				if(campo[i+1][j]>max) max = campo[i+1][j];
				campo[i][j] = 0;
				posLibere.add(new cordinata(i,j));
				i--;
			}
	}
	
	private void spostaLEFT(int j) { //sposta gli elementi della riga passata come argomento
		int posLibera = dimensione;
		for(int i =0;i<campo.length;i++) {
			if(campo[j][i]==0 && j < posLibera) posLibera = i;
			if(campo[j][i] != 0 && posLibera != dimensione) {
				campo[j][posLibera] = campo[j][i];
				campo[j][i] = 0;
				posLibere.remove(new cordinata(j,posLibera));
				posLibere.add(new cordinata(j,i));
				posLibera++;
			}
		}
	}

	private void sommaLEFT(int j) { //somma gli elementi adiacenti ceh risultino uguali tra loro (nella direzone della mossa)
		for(int i = 1; i<campo.length; i++)
			if (campo[j][i-1] == campo[j][i] && campo[j][i] != 0){
				campo[j][i-1] += campo[j][i];
				if(campo[j][i-1]>max) max = campo[j][i-1];
				campo[j][i] = 0;
				posLibere.add(new cordinata(j,i));
				i++;
			}
	}
	
	private void spostaRIGHT(int j) { //sposta gli elementi della riga passata come argomento
		int posLibera = -1;
		for(int i = campo.length-1;i>=0;i--) {
			if(campo[j][i]==0 && i > posLibera) posLibera = i;
			if(campo[j][i] != 0 && posLibera != -1) {
				campo[j][posLibera] = campo[j][i];
				campo[j][i] = 0;
				posLibere.remove(new cordinata(j,posLibera));
				posLibere.add(new cordinata(j,i));
				posLibera--;
			}
		}
	}

	private void sommaRIGHT(int j) { //somma gli elementi adiacenti ceh risultino uguali tra loro (nella direzone della mossa)
		for(int i = campo.length-2;i>=0;i--)
			if (campo[j][i+1] == campo[j][i] && campo[j][i] != 0){
				campo[j][i+1] += campo[j][i];
				if(campo[j][i+1]>max) max = campo[j][i+1];
				campo[j][i] = 0;
				posLibere.add(new cordinata(j,i));
				i--;
			}
	}
	
	public void TornaDietro () { // Permette di ripetere la mossa precedente (con una sequenza di posizionamento dei numeri NUOVA)
		if(storia.size()==0)return;
		for(int i=0; i<campo.length;i++)
			for(int j=0; j<campo[0].length;j++)
				campo[i][j] = storia.get(storia.size()-1)[i][j];
		storia.remove(storia.size()-1);
	}
	
	private void AggiungiCampo () { //Inserisce in memoria lo stato del campo
		int [][] ris = new int[dimensione][dimensione];
		for(int i=0; i<campo.length;i++)
			for(int j=0; j<campo[0].length;j++)
				ris[i][j]=campo[i][j];
		storia.add(ris);
	}
	
	public boolean verificaPerdita() { // verifica che il campo sia pieno.
		if (posLibere.isEmpty()) Persa = true;	
		return Persa;
	}

	public boolean verificaVittoria() { // verifica che il giocatore sia arrivato 2^11
		if (max == 2048) Vinta = true;
		return Vinta;
		
	}
	
	public void restart() { // Permette di iniziare una nuova partita azzerando quella in corso.
		posLibere.clear();
		for(int i = 0; i < campo.length; i++)
			for(int j = 0; j < campo[0].length; j++) {
				campo[i][j] = 0;
				posLibere.add(new cordinata(i,j));
			}
	}

	public void posNumero() { // Posiziona un numero casuale in una cella libera
		Random r = new Random();
		cordinata pos = posLibere.get(0);
		int indice = 0; 
		if (posLibere.size() != 1) {
			indice = r.nextInt(posLibere.size());
			pos = posLibere.get(indice);
		}
		Random p = new Random();
		int n = p.nextInt(10000);
		if (n <= 7000) campo[pos.cordinatax][pos.cordinatay] = 2;	// nel 70% dei casi inserisce 2 (circa)			
		else campo[pos.cordinatax][pos.cordinatay] = 4;	// nel 30% dei casi inserisce 4 (circa)
		posLibere.remove(indice);
	}
	
	private static void stampa (int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j <m[0].length; j++)
				System.out.print(m[i][j]+"\t");
			System.out.println("");
		}
		System.out.println("");
	}
}



	/*Funzione pi� efficente che non rispetta in tutto la loggica 
	 * del gioco originale ma permette di modificare la matrice 
	 * con solamente con 2 cicli che scorrono l'intera matrice
	 */
	
	/*public void muoviright() {
		for (int j = 0; j<campo[0].length; j++) {
			int posLibera = -1; //trovare la prima pos = 0
			for (int i = campo.length-1; i >= 0; i--) {
				if(campo[j][i] == 0 && posLibera < i) posLibera = i;
				if(campo[j][i] != 0 && posLibera >= 0) {
					campo[j][posLibera] = campo[j][i];// sposta verso il destra se c'� una casella vuota
					campo[j][i] = 0;
					posLibere.add(new cordinata(j,i));
					posLibere.remove(new cordinata(j,posLibera));
					if(posLibera != dimensione-1 && campo[j][posLibera+1] == campo[j][posLibera] ) {
						campo[j][posLibera+1] += campo[j][posLibera+1]; 
						campo[j][posLibera] = 0;
						posLibere.add(new cordinata(j,posLibera));
						posLibera ++ ;
					}
					posLibera--;
				}
				if (campo[j][i] != 0 && i != dimensione-1 && campo[j][i+1] == campo[j][i] ) {
					campo[j][i+1] += campo[j][i]; 
					campo[j][i] = 0;
					posLibere.add(new cordinata(j,i));
					posLibera = i ;
				}
				
			}
		}
	}

	public void muovileft() {
		for (int j = 0; j<campo[0].length;j++) {
			int posLibera = dimensione; //trovare la prima pos = 0
			for (int i = 0; i < campo.length; i++) {
				if(campo[j][i] == 0 && posLibera > i) posLibera = i;
				if(campo[j][i] != 0 && posLibera < dimensione) {
					campo[j][posLibera] = campo[j][i];// sposta verso sinistra se c'� una casella vuota
					campo[j][i] = 0;
					posLibere.add(new cordinata(j,i));
					posLibere.remove(new cordinata(j,posLibera));
					if(posLibera != 0 && campo[j][posLibera-1] == campo[j][posLibera] ) {
						campo[j][posLibera-1] += campo[j][posLibera]; 
						campo[j][posLibera] = 0;
						posLibere.add(new cordinata(j,posLibera));
						posLibera -- ;
					}
					posLibera++;
				}
				if (campo[j][i] != 0 && i != 0 && campo[j][i-1] == campo[j][i] ) {
					campo[j][i-1] += campo[j][i];
					campo[j][i] = 0;
					posLibere.add(new cordinata(j,i));
					posLibera = i ;
				}
				
			}
		}	
	}

	public void muovidown() {
		for (int j = 0; j<campo[0].length; j++) {
			int posLibera = -1; //trovare la prima pos = 0
			for (int i = campo.length-1; i >= 0; i--) {
				if(campo[i][j] == 0 && posLibera < i) posLibera = i;
				if(campo[i][j] != 0 && posLibera >= 0) {
					campo[posLibera][j] = campo[i][j];// sposta verso il basso se c'� una casella vuota
					campo[i][j] = 0;
					posLibere.add(new cordinata(i,j));
					posLibere.remove(new cordinata(posLibera,j));
					if(posLibera != dimensione-1 && campo[posLibera+1][j] == campo[posLibera][j] ) {
						campo[posLibera+1][j] += campo[posLibera][j]; 
						campo[posLibera][j] = 0;
						posLibere.add(new cordinata(posLibera,j));
						posLibera ++ ;
					}
					posLibera--;
				}
				if (campo[i][j] != 0 && i != dimensione-1 && campo[i+1][j] == campo[i][j] ) {
					campo[i+1][j] += campo[i][j]; 
					campo[i][j] = 0;
					posLibere.add(new cordinata(i,j));
					posLibera = i ;
				}
				
			}
		}	
	}

	public void muoviup() {
		for (int j = 0; j<campo[0].length;j++) { // scorre le colonne
			int posLibera = dimensione; //trovare la prima pos = 0
			for (int i = 0; i < campo.length;i++) { // scorre sulla riga
				if(campo[i][j] == 0 && posLibera > i) posLibera = i; // aggiorna la pos vuota
				if(campo[i][j] != 0 && posLibera < dimensione) {
					campo[posLibera][j] = campo[i][j];// sposta verso l'alto se c'� una casella vuota
					campo[i][j] = 0;
					posLibere.add(new cordinata(i,j));
					posLibere.remove(new cordinata(posLibera,j));
					if(posLibera != 0 && campo[posLibera-1][j] == campo[posLibera][j] ) {
						campo[posLibera-1][j] += campo[posLibera][j]; //controllare anche su pos precedenti 
						campo[posLibera][j] = 0;
						posLibere.add(new cordinata(posLibera,j));
						posLibera -- ;
					}
					posLibera++;
				}
				if (campo[i][j] != 0 && i != 0 && campo[i-1][j] == campo[i][j] ) {
					campo[i-1][j] += campo[i][j]; 
					campo[i][j] = 0;
					posLibere.add(new cordinata(i,j));
					posLibera = i ;
				}			
			}
		}	
	}*/

//200612 Andrea Voci