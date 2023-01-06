package bingo.jylim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener,ItemListener{
	Container frame = this.getContentPane();
	JPanel panel1,panel2,panel3;
	
	ImageIcon img = new ImageIcon("iloveyou.jpg");
	JLabel icon = new JLabel(img);
	JLabel source = new JLabel("Cartoon Network");
	JLabel title = new JLabel("BINGO GAME");
	Font font = new Font("BINGO GAME", Font.BOLD,36);
	Font font1 = new Font("",Font.BOLD,15);
	Font font2 = new Font("",Font.BOLD,13);
	JLabel records;
	JLabel setNum = new JLabel("빙고판의 크기를 선택하세요 ");
	String[] data = {"3","4","5","6","7"};
	JRadioButton[] radiobutton = new JRadioButton[5];
	ButtonGroup g = new ButtonGroup();
	JButton startbtn = new JButton("게임시작");
	int num = -1;
	
	JLabel[][] userBingoLabel;
	JLabel order = new JLabel("영단어를 입력하세요");
	JTextField engWord = new JTextField(20);
	JLabel userMessage = new JLabel("");
	JLabel comMessage = new JLabel("");
	String userinput;
	
	Word[][] userBingo;
	Word[][] comBingo;
	
	Integer win;
	Integer lose;
	Integer draw;
	double percentage;
	
	static Scanner scan = new Scanner(System.in);
	File recordFile = new File("record.txt");
	VocManager voc = new VocManager("202212128 임지예");
	
	MyFrame(String title){
		super(title);
		this.setSize(700,700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		voc.makeVoc("words.txt");	//단어장 생성
		readRecord();
		init();
		this.setVisible(true);
	}
	
	private void readRecord() {
		try(Scanner scan = new Scanner(recordFile)){
			if(scan.hasNext()) {
				win = scan.nextInt();
				lose = scan.nextInt();
				draw = scan.nextInt();
				percentage = Math.round((double)win/(win+lose)*100*100)/100.0;
			}else {
				win=0;
				lose=0;
				draw=0;
				percentage=0;
			}
			System.out.println("record.txt 파일을 읽었습니다.");
			
		}catch(FileNotFoundException e) {
			System.out.println("record.txt 파일이 존재하지 않습니다.");
		}
	}
	
	private void init() {
		initPanel1();
		initPanel2();
		initPanel3();
	}
	
	private void initPanel1() {
		panel1 = new JPanel(new GridLayout(2,1));
		title.setFont(font);
		title.setHorizontalAlignment(JLabel.CENTER);
		panel1.add(title);
		records = new JLabel("   "+win+"승 "+draw+"무 "+lose+"패   승률: "+percentage+"%");
		records.setFont(font2);
		panel1.add(records);
		panel1.setBackground(Color.YELLOW);
		frame.add(panel1,BorderLayout.NORTH);
	}
	
	private void initPanel2() {
		panel2 = new JPanel();
		panel2.setFont(font2);
		panel2.add(setNum);
		for(int i=0;i<data.length;i++) {
			radiobutton[i] = new JRadioButton(data[i]);
			radiobutton[i].addItemListener(this);
			g.add(radiobutton[i]);
			panel2.add(radiobutton[i]);
		}
		startbtn.addActionListener(this);
		panel2.add(startbtn);
		panel2.add(icon);
		panel2.add(source);
		frame.add(panel2,BorderLayout.CENTER);
	}
	
	private void initPanel3() {
		panel3 = new JPanel(new GridLayout(3,2,10,10));
		order.setFont(font2);
		userMessage.setFont(font2);
		comMessage.setFont(font2);
		order.setHorizontalAlignment(JLabel.CENTER);
		userMessage.setHorizontalAlignment(JLabel.CENTER);
		comMessage.setHorizontalAlignment(JLabel.CENTER);
		panel3.add(order);
		panel3.add(engWord);
		panel3.add(userMessage);
		panel3.add(comMessage);
		
		engWord.addActionListener(e->{
			userinput = engWord.getText().trim();
			engWord.setText("");
			startGame();
		});
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		for(int i=0;i<data.length;i++) {
			if(e.getSource()==radiobutton[i]) {
				num =i+3;
				break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(num>0) {
		userBingo = voc.makeBingo(num);
		comBingo = voc.makeBingo(num);
		panel2.remove(setNum);
		panel2.remove(startbtn);
		panel2.remove(icon);
		panel2.remove(source);
		for(int i=0;i<radiobutton.length;i++) {
			panel2.remove(radiobutton[i]);
		}
		
		panel2.setLayout(new GridLayout(num,num));
		userBingoLabel = new JLabel[num][num];
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				userBingoLabel[i][j] = new JLabel(userBingo[i][j].eng);
				userBingoLabel[i][j].setFont(font1);
				userBingoLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
				panel2.add(userBingoLabel[i][j]);
			}
		}
		panel2.revalidate();
		frame.add(panel3,BorderLayout.SOUTH);
		engWord.requestFocus();
		
		}
	}

	private void startGame() {
		userTurn();
		Word comSel =comTurn();
		comTurnUser(comSel);
		int userBingoCount = checkBingo(userBingo);
		int comBingoCount = checkBingo(comBingo);
		
		if(userBingoCount>comBingoCount) {
			System.out.println(userBingoCount+":"+comBingoCount+" 승리");
			win++;
			percentage = Math.round((double)win/(win+lose)*100*100)/100.0;
			JOptionPane.showMessageDialog(null,userBingoCount+":"+comBingoCount+" (으)로 승리하였습니다.\n"+win+"승 "+draw+"무 "+lose+"패     승률: "+percentage+"%");
			terminate();
		}else if(userBingoCount<comBingoCount) {
			System.out.println(userBingoCount+":"+comBingoCount+" 패배");
			lose++;
			percentage = Math.round((double)win/(win+lose)*100*100)/100.0;
			JOptionPane.showMessageDialog(null,userBingoCount+":"+comBingoCount+" (으)로 패배하였습니다.\n"+win+"승 "+draw+"무 "+lose+"패     승률: "+percentage+"%");
			terminate();
		}else if(drawcheck()) {
			System.out.println(userBingoCount+":"+comBingoCount+" 무승부");
			draw++;
			percentage = Math.round((double)win/(win+lose)*100*100)/100.0;
			JOptionPane.showMessageDialog(null,userBingoCount+":"+comBingoCount+" (으)로 비겼습니다.\n"+win+"승 "+draw+"무 "+lose+"패     승률: "+percentage+"%");
			terminate();
		}
	}

	private void userTurn() {
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				if(userBingo[i][j].eng.equals(userinput)) {
					userBingoLabel[i][j].setOpaque(true);
					userBingoLabel[i][j].setBackground(Color.CYAN);
					userBingo[i][j].selected=true;
					userMessage.setText("user가 "+userBingo[i][j]+"를 선택하였습니다.");
				}
				if(comBingo[i][j].eng.equals(userinput)) {
					comBingo[i][j].selected=true;
				}
			}
		}
	}
	
	private Word comTurn() { //1. 가로, 세로, 대각선에서 하나만 추가하면 되는 경우 2. 선택된 단어들의 가로, 세로, 대각선에 가장 많이 해당되는 단어
		//행 체크
		for(int i=0;i<num;i++) {
			int rowCount = 0;
			int noneidx= 0 ;
			for(int j=0;j<num;j++) {
				if(comBingo[i][j].selected)
					rowCount++;
				else
					noneidx=j;
			}
			if(rowCount==num-1) {
				comBingo[i][noneidx].selected=true;
				comMessage.setText("computer가 "+comBingo[i][noneidx]+"를 선택하였습니다.");
				return comBingo[i][noneidx];
			}else if(rowCount>0){
				for(int j=0;j<num;j++) {
					if(!comBingo[i][j].selected)
						comBingo[i][j].count+=rowCount;
				}
			}
		}
		//열 체크
		for(int i=0;i<num;i++) {
			int colCount = 0;
			int noneidx = 0;
			for(int j=0;j<num;j++) {
				if(comBingo[j][i].selected)
					colCount++;
				else
					noneidx=j;
			}
			if(colCount==num-1) {
				comBingo[noneidx][i].selected=true;
				comMessage.setText("computer가 "+comBingo[noneidx][i]+"를 선택하였습니다.");
				return comBingo[noneidx][i];
			}else if(colCount>0){
				for(int j=0;j<num;j++) {
					if(!comBingo[j][i].selected)
						comBingo[j][i].count+=colCount;
				}
			}
		}
		//대각선 체크
		int diaCount1 = 0;
		int noneidx = 0;
		for(int i=0;i<num;i++) {
			if(comBingo[i][i].selected)
				diaCount1++;
			else
				noneidx=i;
		}
		if(diaCount1==num-1) {
			comBingo[noneidx][noneidx].selected=true;
			comMessage.setText("computer가 "+comBingo[noneidx][noneidx]+"를 선택하였습니다.");
			return comBingo[noneidx][noneidx];
		}else if(diaCount1>0) {
			for(int i=0;i<num;i++) {
				if(!comBingo[i][i].selected)
					comBingo[i][i].count+=diaCount1;
			}
		}
		
		int diaCount2 = 0;
		noneidx = 0;
		for(int i=0;i<num;i++) {
			if(comBingo[i][num-i-1].selected)
				diaCount2++;
			else
				noneidx=i;
		}
		if(diaCount2==num-1) {
			comBingo[noneidx][num-noneidx-1].selected=true;
			comMessage.setText("computer가 "+comBingo[noneidx][num-noneidx-1]+"를 선택하였습니다.");
			return comBingo[noneidx][num-noneidx-1];
		}else if(diaCount2>0) {
			for(int i=0;i<num;i++) {
				if(!comBingo[i][num-i-1].selected)
					comBingo[i][num-i-1].count+=diaCount2;
			}
		}
		
		Word max = comBingo[0][0];
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				if(comBingo[i][j].count>max.count)
					max=comBingo[i][j];
			}
		}
		max.selected=true;
		comMessage.setText("computer가 "+max+"를 선택하였습니다.");
		return max;
	}
	
	private void comTurnUser(Word comSel) {
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				comBingo[i][j].count=0;
			}
		}
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				if(userBingo[i][j].eng.equals(comSel.eng)) {
					userBingoLabel[i][j].setOpaque(true);
					userBingoLabel[i][j].setBackground(Color.CYAN);
					userBingo[i][j].selected=true;
				}
			}
		}
	}
	
	private int checkBingo(Word[][] bingo) {
		int bingoCount=0;
		//행 체크
		for(int i=0;i<num;i++) {
			int rowCount = 0;
			for(int j=0;j<num;j++) {
				if(bingo[i][j].selected)
					rowCount++;
			}
			if(rowCount==num)
				bingoCount++;
		}
		//열 체크
		for(int i=0;i<num;i++) {
			int colCount = 0;
			for(int j=0;j<num;j++) {
				if(bingo[j][i].selected)
					colCount++;
			}
			if(colCount==num)
				bingoCount++;
		}
		//대각선 체크
		int diaCount = 0;
		for(int i=0;i<num;i++) {
			if(bingo[i][i].selected)
				diaCount++;
		}
		if(diaCount==num)
			bingoCount++;
		
		diaCount = 0;
		for(int i=0;i<num;i++) {
			if(bingo[i][num-i-1].selected)
				diaCount++;
		}
		if(diaCount==num)
			bingoCount++;
		
		return bingoCount;
	}
	
	private boolean drawcheck() {
		int user=0;
		int com=0;
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				if(userBingo[i][j].selected)
					user++;
				if(comBingo[i][j].selected)
					com++;
			}
		}
		if(user==num*num || com==num*num)
			return true;
		else
			return false;
	}
	
	private void terminate() {	
		 try {
	            if (!recordFile.exists()) {
	                recordFile.createNewFile();
	            }
	            FileWriter fw = new FileWriter(recordFile);
	            BufferedWriter writer = new BufferedWriter(fw);
	 
	            writer.write(win.toString()+"\n");
	            writer.write(lose.toString()+"\n");
	            writer.write(draw.toString()+"\n");
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		System.exit(0);
	}
	
}
