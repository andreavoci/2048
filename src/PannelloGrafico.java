//package grafica;
//200612 Andrea Voci
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.Scanner;

public class PannelloGrafico
{	private JFrame frame;
	private JTextField[][] matriceA;
	private JTextField[][] matriceB;
	private JButton[] bottoni;
	private JLabel messaggio;
	private String titoloFinestra;
	private int numeroRigheA;
	private int numeroColonneA;
	private int numeroRigheB;
	private int numeroColonneB;
	private int numeroBottoni;
	private Gioco player1;
	private Gioco player2;
	private int turno = 1;

	/* INIZIO PARTE DA PERSONALIZZARE */
	// Attributi dell'applicazione
	/* FINE PARTE DA PERSONALIZZARE */

	public PannelloGrafico(int n)
	{	/* INIZIO PARTE DA PERSONALIZZARE */
		titoloFinestra = "2048GAME";
		numeroRigheA = n;
		numeroColonneA = n;
		numeroRigheB = n;
		numeroColonneB = n;
		numeroBottoni = 11;
		player1 = new Gioco(n);
		player2 = new Gioco(n);
		
		/* FINE PARTE DA PERSONALIZZARE */

		inizializza();
	}

	private void bottonePremuto(int nB)
	{	/* INIZIO PARTE DA PERSONALIZZARE */
		setEtichettaBottone(9,"RESTART");
		abilitaBottone(9);
		abilitaBottone(10);
		setColoreB(8, Color.magenta);
		if (turno == 1) {
		setMessaggio("Tocca al Player 1");
		for (int i = 0; i < 4; i++) {
			abilitaBottone(i);
			setColoreB(i, Color.green);
		}
		for (int i = 4; i < 8; i++) {
			setColoreB(i,Color.lightGray);
			disabilitaBottone(i);
		}
		if (nB > 4 && nB <=8) {
		if( nB == 5) player1.muoviup();
		if( nB == 6) player1.muovidown();
		if( nB == 7) player1.muovileft();
		if( nB == 8) player1.muoviright();
		player1.posNumero();
		setMatriceB(convertiDaMatriceIntera(player1.getcampo()));
		}
		}
		//
		if(turno == 2) {
		setMessaggio("Tocca al Player 2");
		for (int i = 4; i < 8; i++) {
			abilitaBottone(i);
			setColoreB(i, Color.green);
		}
		for (int i = 0; i < 4; i++) {
			setColoreB(i,Color.lightGray);
			disabilitaBottone(i);
		}
		if(nB<=4 && nB>0) {
			switch(nB) {
			case (1): player2.muoviup(); break;
			case (2): player2.muovidown();break;
			case (3): player2.muovileft();break;
			case (4): player2.muoviright();break;
			}
			player2.posNumero();
			setMatriceA(convertiDaMatriceIntera(player2.getcampo()));
		}
		}
		if (nB == 9) {
			player1.restart();
			player2.restart();
			player1.start();
			player2.start();
			setMatriceA(convertiDaMatriceIntera(player2.getcampo()));
			setMatriceB(convertiDaMatriceIntera(player1.getcampo()));
			
		}
		
		if(nB == 11) {
			player2.TornaDietro();
			setMatriceA(convertiDaMatriceIntera(player2.getcampo()));
			turno = 1;
		}
		if(nB == 10) {
			player1.TornaDietro();
			setMatriceB(convertiDaMatriceIntera(player1.getcampo()));
			turno = 2;
			}
		
		if (player1.verificaVittoria()) {
			setMessaggio("Il Player 2 ha >VINTO<");
			for (int i=0; i<matriceA.length; i++) 
				for (int j=0; j < matriceA[0].length; j++) 
					matriceA[i][j].setBackground(Color.green);
		}

		if (player2.verificaVittoria()) {
			setMessaggio("Il Player 1 ha >VINTO<");
			//TODO
		}
		if (player1.verificaPerdita()) {
			setMessaggio("Il giocatore 2 ha <PERSO>");
			for (int i=0; i<matriceA.length; i++) 
				for (int j=0; j < matriceA[0].length; j++) {
					matriceA[i][j].setBackground(Color.red);
				}
		}
		
		if (player2.verificaPerdita()) {
			setMessaggio("Il giocatore 1 ha <PERSO>");
			for (int i=0; i<matriceB.length; i++) 
				for (int j=0; j < matriceB[0].length; j++) {
					matriceB[i][j].setBackground(Color.red);
				}
		}
		turno = cambiaTurno(turno);	
		/* FINE PARTE DA PERSONALIZZARE */
	}
	
	private int cambiaTurno(int t) {
		if (t == 1) return 2;
		return 1;
	}

	public static void main(String[] args)
	{	Scanner s = new Scanner(System.in);
		System.out.print("SELEZIONARE LA DIMENSIONE DI GIOCO: - ");
		int n = 0; // Impostere a zero per scegliere la dimenisone (oppure fissare la dimesione da codice)
		while (!(n > 3 && n < 11 && n%2 == 0)) { n = s.nextInt();}
		s.close();
		PannelloGrafico p = new PannelloGrafico(n);
		/* INIZIO PARTE DA PERSONALIZZARE */
		p.setEtichettaBottone(1, "UP");
		p.setEtichettaBottone(2, "DOWN");
		p.setEtichettaBottone(3, "LEFT");
		p.setEtichettaBottone(4, "RIGHT");
		p.setEtichettaBottone(5, "UP");
		p.setEtichettaBottone(6, "DOWN");
		p.setEtichettaBottone(7, "LEFT");
		p.setEtichettaBottone(8, "RIGHT");
		p.setEtichettaBottone(9, "START");
		p.setEtichettaBottone(10, "BACK2");
		p.setEtichettaBottone(11, "BACK1");
		/* FINE PARTE DA PERSONALIZZARE */
	}

	private void setMessaggio(String m)
	{	messaggio.setText(m);
	}
	
	private void setColoreB (int n, Color c) {
		bottoni[n].setBackground(c);
		bottoni[n].setOpaque(true);
		bottoni[n].setBorderPainted(false);
	}
	
	private void disabilitaBottone(int i) {
		bottoni[i].setEnabled(false);
	}
	
	private void abilitaBottone(int i) {
		bottoni[i].setEnabled(true);	
	}
	
	private void setEtichettaBottone(int numeroBottone, String etichetta)
	{	bottoni[numeroBottone-1].setText(etichetta);		
	}

	private String[][] getMatriceA()
	{	String[][] ret = new String[numeroRigheA][numeroColonneA];
		for(int i=0;i<numeroRigheA;i++)
			for(int j=0;j<numeroColonneA;j++)
				ret[i][j] = matriceA[i][j].getText();
		return ret;
	}

	private String[][] getMatriceB()
	{	String[][] ret = new String[numeroRigheB][numeroColonneB];
		for(int i=0;i<numeroRigheB;i++)
			for(int j=0;j<numeroColonneB;j++)
				ret[i][j] = matriceB[i][j].getText();
		return ret;
	}

	private void setMatriceA(String[][] A)
	{	for(int i=0;i<numeroRigheA;i++)
			for(int j=0;j<numeroColonneA;j++)
				matriceA[i][j].setText(A[i][j]);
	}

	private void setMatriceB(String[][] B)
	{	for(int i=0;i<numeroRigheB;i++)
			for(int j=0;j<numeroColonneB;j++)
				matriceB[i][j].setText(B[i][j]);
	}
	
	private void bloccaMatriceA()
	{	for(int i=0;i<numeroRigheA;i++)
			for(int j=0;j<numeroColonneA;j++)
				matriceA[i][j].setEditable(false);;
	}

	private void sbloccaMatriceA()
	{	for(int i=0;i<numeroRigheA;i++)
			for(int j=0;j<numeroColonneA;j++)
				matriceA[i][j].setEditable(true);;
	}
	
	private void bloccaMatriceB()
	{	for(int i=0;i<numeroRigheB;i++)
			for(int j=0;j<numeroColonneB;j++)
				matriceB[i][j].setEditable(false);;
	}

	private void sbloccaMatriceB()
	{	for(int i=0;i<numeroRigheB;i++)
			for(int j=0;j<numeroColonneB;j++)
				matriceB[i][j].setEditable(true);;
	}
	
	private int[][] convertiInMatriceIntera(String[][] m)
	{	int nRighe=m.length;
		int nColonne=m[0].length;
		int[][] ret = new int[nRighe][nColonne];
		for(int i=0;i<nRighe;i++)
			for(int j=0;j<nColonne;j++)
				if(m[i][j].equals(""))
					ret[i][j]=0;
				else
					ret[i][j]=Integer.parseInt(m[i][j]);
		return ret;
	}

	private String[][] convertiDaMatriceIntera(int[][] m)
	{	int nRighe=m.length;
		int nColonne=m[0].length;
		String[][] ret = new String[nRighe][nColonne];
		for(int i=0;i<nRighe;i++)
			for(int j=0;j<nColonne;j++)
				if (m[i][j] != 0) ret[i][j]=""+m[i][j];
		return ret;
	}

	private void inizializza()
	{	frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{	System.out.println(e);
			System.exit(0);
		}
		frame.setBackground(Color.black);
		frame.setVisible(true);
		frame.setBounds(100, 100, 780, 500);
		frame.setTitle(titoloFinestra);

		messaggio = new JLabel("");
		frame.getContentPane().add(messaggio);
		messaggio.setBounds(240, 270, 300, 30);
		messaggio.setHorizontalAlignment(JLabel.CENTER);

		bottoni=new JButton[numeroBottoni];
		ActionListener listener = new PressioneBottoni();
		for(int i=0;i<2;i++)
		{	JButton bottone=new JButton();
			bottone.setBounds(140, 300+70*i, 100, 30);
			bottoni[i]=bottone;
			bottone.addActionListener(listener);
			frame.getContentPane().add(bottone);
		}
		
		for(int i=2;i<4;i++)
		{	JButton bottone=new JButton();
			bottone.setBounds(85+105*(i-2), 335, 100, 30);
			bottoni[i]=bottone;
			bottone.addActionListener(listener);
			frame.getContentPane().add(bottone);
		}
		//
		for(int i=4;i<6;i++)
		{	JButton bottone=new JButton();
			bottone.setBounds(520, 300+70*(i-4), 100, 30);
			bottoni[i]=bottone;
			bottone.addActionListener(listener);
			frame.getContentPane().add(bottone);
		}
		
		for(int i=6;i<8;i++)
		{	JButton bottone=new JButton();
			bottone.setBounds(465+105*(i-6), 335, 100, 30);
			bottoni[i]=bottone;
			bottone.addActionListener(listener);
			frame.getContentPane().add(bottone);
		}

		JButton bottone=new JButton();
		bottone.setBounds(330, 330, 100, 30); 
		bottone.setBackground(Color.orange);
		bottone.setOpaque(true);
		bottone.setBorderPainted(false);
		bottone.setForeground(Color.BLUE);
		bottoni[8]=bottone;
		bottone.addActionListener(listener);
		frame.getContentPane().add(bottone);
		
		JButton bottone2=new JButton();
		bottone2.setBounds(390, 380, 100, 30); 
		bottone2.setBackground(Color.BLACK);
		bottone2.setOpaque(true);
		bottone2.setBorderPainted(false);
		bottone2.setForeground(Color.blue);
		bottoni[9]=bottone2;
		bottone2.addActionListener(listener);
		frame.getContentPane().add(bottone2);

		JButton bottone3=new JButton();
		bottone3.setBounds(270, 380, 100, 30); 
		bottone3.setBackground(Color.BLACK);
		bottone3.setOpaque(true);
		bottone3.setBorderPainted(false);
		bottone3.setForeground(Color.blue);
		bottoni[10]=bottone3;
		bottone3.addActionListener(listener);
		frame.getContentPane().add(bottone3);

		matriceA = new JTextField[numeroRigheA][numeroColonneA];
		for(int i=0;i<numeroRigheA;i++)
			for(int j=0;j<numeroColonneA;j++)
			{	JTextField campoTesto = new JTextField("");
				frame.getContentPane().add(campoTesto);
				campoTesto.setBounds(50+30*j, 60+20*i, 30, 20);
				campoTesto.setHorizontalAlignment(JTextField.CENTER);
				campoTesto.setBackground(Color.cyan);
				matriceA[i][j] = campoTesto;
			}
		/*for(int i=0;i<numeroRigheA;i++)
		{	JLabel numeroRigaA = new JLabel(""+i);
			frame.getContentPane().add(numeroRigaA);
			numeroRigaA.setBounds(30, 60+20*i, 30, 20);
		}
		for(int j=0;j<numeroColonneA;j++)
		{	JLabel numeroColonnaA = new JLabel(""+j);
			frame.getContentPane().add(numeroColonnaA);
			numeroColonnaA.setBounds(60+30*j, 40, 30, 20);
		}*/

		if(numeroRigheB != 0 || numeroColonneB != 0)
		{	matriceB = new JTextField[numeroRigheB][numeroColonneB];
			for(int i=0;i<numeroRigheB;i++)
				for(int j=0;j<numeroColonneB;j++)
				{	JTextField campoTesto = new JTextField("");
					frame.getContentPane().add(campoTesto);
					campoTesto.setBounds(430+30*j, 60+20*i, 30, 20);
					campoTesto.setHorizontalAlignment(JTextField.CENTER);
					campoTesto.setBackground(Color.cyan);
					matriceB[i][j] = campoTesto;
				}
			/*for(int i=0;i<numeroRigheB;i++)
			{	JLabel numeroRigaB = new JLabel(""+i);
				frame.getContentPane().add(numeroRigaB);
				numeroRigaB.setBounds(410, 60+20*i, 30, 20);
			}
			for(int j=0;j<numeroColonneB;j++)
			{	JLabel numeroColonnaB = new JLabel(""+j);
				frame.getContentPane().add(numeroColonnaB);
				numeroColonnaB.setBounds(440+30*j, 40, 30, 20);
			}*/
		}
		for(int i = 0; i <8 ;i++) disabilitaBottone(i);
		disabilitaBottone(9);
		disabilitaBottone(10);
		bloccaMatriceA();
		bloccaMatriceB();
		setMatriceA(convertiDaMatriceIntera(player1.getcampo()));
		setMatriceB(convertiDaMatriceIntera(player2.getcampo()));
	}

	private class PressioneBottoni implements ActionListener
	{	public void actionPerformed(ActionEvent e)
		{	int numeroBottonePremuto = -1;
			for(int i=0;i<numeroBottoni;i++)
				if(e.getSource()==bottoni[i])
					numeroBottonePremuto = i + 1;
			bottonePremuto(numeroBottonePremuto);
		}
	}
}

//200612 Voci Andrea

