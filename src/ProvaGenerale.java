public class ProvaGenerale  {
	public static void stampa (int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j <m[0].length; j++)
				System.out.print(m[i][j]+"\t");
			System.out.println("");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
        
		Gioco partita1 = new Gioco();
		stampa(partita1.getcampo());
		partita1.playGioco();
	}

}
