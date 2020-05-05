 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class StateDiagramCode{
	//variable declaration
	static int character;
	static char lex[] = new char[100];
	static char nxtCharacter;
	static int lexLen;
	static int tok;
	static int nextTok;
	static File in_fp;
	static int state = 0;
	 
	//state
	static final int START = 0;
	static final int NUM = 2;
	static final int FL_NUM = 4;
	static final int STR = 6;
	
	//End state
	static final int END_NAME = 8;
	static final int END_FLOAT = 10;
	static final int END_STRING = 12;
	static final int END_INTEGER = 14;
	static final int END_E = 16;

	//Char Types
	static final int LETTER = 0;
	static final int DIGIT = 1;
	static final int UNKNOWN = 99;
	static final int EOF = -1;

	//char values
	static final int STRING_LITERAL = 11;
	static final int IDENT = 13;
	static final int INTEGER_LITERAL = 15;
	static final int dot = 17;
	static final int FLOAT_LITERAL = 19;
    static final int QUOTES = 21;
	
	static public void state(int state) {
		switch(state) {
		case END_STRING:
			System.out.println("End state is a String Literal. "+"("+state+")");
			break;
		case END_INTEGER:
			System.out.println("End state is an Integer Literal. "+"("+state+")");
			break;
		case END_NAME:
			System.out.println("End state is a Variable name. "+"("+state+")");
			break;
		case END_FLOAT:
			System.out.println("End state is a Floating Point Literal. "+"("+state+")");
			break;
		}
	}
    static int lookup(char ch){
		switch (ch) {
		case '.':
			addChar();
			nextTok = dot;
			break;
		case '"':
			addChar();
			nextTok = QUOTES;
			break;
		default:
			addChar();
			nextTok = 0;
			break;
		}
		return nextTok;
	}

	static void addChar(){
		if (lexLen <= 98) {
			lex[lexLen++] = nxtCharacter;
			lex[lexLen] = 0;
		} else {
			System.out.println("Error - lex is too long \n");
		}
	}
	static void getChar(BufferedReader br) throws IOException{
		int nc;
		if ((nc = br.read()) != -1) { 
			nxtCharacter = (char) nc;
			if (Character.isLetter(nxtCharacter)) {
				character = LETTER;
				state = STR;
			}
			else if (Character.isDigit(nxtCharacter)) {
				character = DIGIT;
				state = NUM;
			}
			else {
				character = UNKNOWN;
			}
		} else {
			character = EOF;
			state = END_E;
		}
	}
	static void getNonBlank(BufferedReader br) throws IOException {
		while (Character.isWhitespace(nxtCharacter)) {
			getChar(br);
			}
		state = START;
		
	}
	static int lex(BufferedReader br) throws IOException {
		lexLen = 0;
		getNonBlank(br);
		switch (character) {
		/* Identifiers */
		case LETTER:
			addChar();
			getChar(br);
			while (character == LETTER || character == DIGIT) {
				addChar();
				getChar(br);
			}
			nextTok = IDENT;
			state = END_INTEGER;
			break;
			/* Integer literals */
		case DIGIT:
			addChar();
			getChar(br);
			while (character == DIGIT) {
				addChar();
				getChar(br);
			}
			nextTok = INTEGER_LITERAL;
			state = END_NAME;
			break;
		case UNKNOWN:
			lookup(nxtCharacter);
			//Float Literals lookup
			if(nextTok == dot) {
				getChar(br);
				while(character == DIGIT) {
					addChar();
					getChar(br);
				}
				nextTok = FLOAT_LITERAL;
				state = END_FLOAT;
			}
			//String literals lookup
			if(nextTok == QUOTES) {
				getChar(br);
				while (character == LETTER || character == DIGIT) {
					addChar();
					getChar(br);
				}
				nextTok = STRING_LITERAL;	
				state = END_STRING;
			}
			break;
			/* EOF */
		case EOF:
			nextTok = 0;
			state = END_E;
			lex[0] = 0;
			break;
		} /* End of switch */
		System.out.print("Lexeme is: ");
		for(int i=0; i<lex.length;i++) {
			System.out.print(lex[i]);
		}
		System.out.print("\n");
		state(state);
		System.out.print("\n");
		return nextTok;
	}
	public static void main(String[]args) throws IOException{
		System.out.println("State Diagram Recognition");
		if ((in_fp = new File("C:\\Users\\kelvi\\Desktop\\GSU\\Spring 2020\\Programming Language Concepts\\file.txt")) == null) {
			System.out.println("ERROR - cannot open file \n");
		}
		 else {
			BufferedReader br = new BufferedReader(new FileReader(in_fp));
		 	getChar(br);
		 do {
		 	lex(br);
		 } while (nextTok != 0);
		 br.close();
		 }
	}	
}
