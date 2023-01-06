package bingo.jylim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VocManager {
	private String userName;
	private ArrayList<Word> voc = new ArrayList<>();
	
	static Scanner scan = new Scanner(System.in);
	Random r = new Random();
	
	VocManager(String userName){
		this.userName = userName;
	}
	
	void makeVoc(String fileName) {
		try(Scanner scan = new Scanner(new File(fileName))){
			while(scan.hasNextLine()) {
				String str = scan.nextLine();
				String[] temp = str.split("\t");
				voc.add(new Word(temp[0].trim(),temp[1].trim()));
			}
			System.out.println(userName+"의 단어장이 생성되었습니다.");
		}catch(FileNotFoundException e) {
			System.out.println(userName+"의 단어장이 생성되지 않았습니다.");
		}
	}
	
	Word[][] makeBingo(int num) {
		Word[][] tmp = new Word[num][num];
		int[] idxs = new int[num*num];
		int size = voc.size();
		
		for(int i=0;i<num*num;i++) {
			int cnt=i;
			int idx = r.nextInt(size);
			for(int j=0;j<i;j++) {
				if(idx == idxs[j]) {
					i--;
				}
			}
			if(i==cnt)
				idxs[i]=idx;
		}
		int count=0;
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
				Word newWord = voc.get(idxs[count]);
				tmp[i][j]=new Word(newWord.eng,newWord.kor);
				count++;
			}
		}
		return tmp;
	}
	
}
