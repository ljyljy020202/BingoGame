package bingo.jylim;

public class Word {
	String eng;
	String kor;
	boolean selected = false;
	int count=0;
	
	public Word(String eng,String kor) {
		super();
		this.eng = eng;
		this.kor = kor;
	}

	@Override
	public String toString() {
		return eng+"("+kor+")";
	}
	
}
