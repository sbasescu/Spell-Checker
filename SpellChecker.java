import java.util.*;
import java.io.*;

public class SpellChecker {
	public static void main(String args[]) throws IOException {
		boolean go = true;
		QuadraticProbingHashTable<Word> dictionary = new QuadraticProbingHashTable <Word>(); //hashtable for dictionary words
		System.out.println("Reading in Dictionary...");
		Scanner dict = new Scanner(new File(args[0]));
		Scanner kb = new Scanner(System.in);
		while (dict.hasNextLine()) {
			Word w = new Word(dict.nextLine());
			dictionary.insert(w);
		} 
		System.out.println("Dictionary Read.");
		while (go) {
			System.out.println("Please enter a file to spell check>>");
			String fileName = kb.nextLine(); 
			listIncorrectWords(dictionary, fileName); 
			boolean newFile = true;
			while (newFile) {
				System.out.println("Print words (p), enter new file (f), or quit (q)?");
				String command = kb.nextLine();
				if (command.equals("p")) {
					printWords(dictionary, fileName);
					System.out.println("Spell check complete!");
				}
				else if (command.equals("f"))
					newFile = false; //quits this while loop and restarts the other while loop
				else  {// if (command.equals("q"))  
					newFile = false;
					go = false;
				}
			}
		}
		System.out.println("Goodbye!");
	}
	/*
	 * Loops through the desired file from the user. For each word, creates a word object and processes it 
	 * according to whether it is already in the wrongWords hashtable, it is set to ignored, or has been replaced. 
	 */
	public static void printWords(QuadraticProbingHashTable<Word> d, String fn) throws IOException {
		int line = 0; //keeps track of line number
		QuadraticProbingHashTable<Word> wrongWords = new QuadraticProbingHashTable <Word>();
		Scanner kb = new Scanner(System.in);
		Scanner fr = new Scanner(new File(fn));
		BufferedWriter out = new BufferedWriter(new FileWriter(fn.substring(0, fn.length() - 4) + "_corrected.txt"));
		while (fr.hasNextLine()) {
			line++; 
			String content = fr.nextLine(); //sets string equal to contents of second line of file
			String [] words = content.split(" "); //puts into an array all of the words in the line
			for (int i = 0; i < words.length; i++) { //loops through words in a given line
				String str = words[i];
				String temp = ""; //will become the string for the word without punctuation
				String punct = ""; //will have the punctuation for the given word
				for (int k = 0; k < str.length(); k++) {
					if (Character.isLetter(str.charAt(k)))
						temp += str.charAt(k); //temp now consists of all the characters in a word
					//that are letters or numbers
					else
						punct += str.charAt(k);
				}
				Word w = new Word(temp); //creates word object for each element in the file
				w.setPunctuation(punct); //sets punctuation for the word, will be blank if none
				if (!d.contains(w)) { 
					if (!wrongWords.contains(w)) 
						wrongWords.insert(w);
					Word x = wrongWords.get(w); //gets the instance of the word already in the hashtable
					if (x.getIgnored() == false) {
						System.out.println("--" + w.getWord() + " " + line);
						System.out.println("ignore all (i), replace all (r), next(n), or quit(q)?");
						String c = kb.nextLine();
						if (c.equals("i")) { //if user wants to ignore all subsequent instances of the word
							x.setIgnored();
							out.write(w.getReplaced() + w.getPunctuation() + " ");
						}
						else if (c.equals("r")) { //if user wants to replace
							replacer(d, w);
							delete(d, w);
							if (w.getSuggestions().size() != 0) { //if replacements were found
								System.out.println("Replace with ");
								ArrayList <String> suggest = w.getSuggestions();
								for (int k = 0; k < suggest.size(); k++) {
									System.out.print("(" + (k + 1) + ")" + suggest.get(k) + ", " );
								}
								System.out.print("or next (n)?");
								String response = kb.nextLine();
								if (Character.isDigit(response.charAt(0))) {
									int r = Integer.parseInt(response);
									w.setReplaced(suggest.get(r - 1));
									w.setIgnored();
									out.write(w.getReplaced() + w.getPunctuation() + " ");
								}
							}
							else {//if no replacements were found
								System.out.println("There are no words to replace it with.");
								out.write(w.getReplaced() + w.getPunctuation() + " ");
							}
						}	
						else // if c.equals("n") do nothing
							out.write(w.getReplaced() + w.getPunctuation() + " ");
					}
					else //if ignored == true
						out.write(x.getReplaced() + w.getPunctuation() + " ");		
				}
				else //if word exists in dictionary
					out.write(w.getReplaced() + w.getPunctuation() + " ");
			}
			out.write("\n"); //outwrites a new line when there is a new line in the file
		}
		out.close();
	}
	/*
	 * Writes out a text file containing all of the wrong words and lines on which they appear
	 */
	public static void listIncorrectWords(QuadraticProbingHashTable<Word> d, String fn) throws IOException {
		Scanner fr = new Scanner(new File(fn));
		BufferedWriter out = new BufferedWriter(new FileWriter(fn.substring(0, fn.length() - 4) + "_order.txt"));
		int line = 0;
		while (fr.hasNextLine()) {
			line++;
			String content = fr.nextLine(); //sets string equal to contents of second line of file
			String [] words = content.split(" "); //puts into an array all of the commands
			for (int i = 0; i < words.length; i++) {
				String str = words[i];
				String temp = ""; //will become the string for the word without punctuation
				for (int k = 0; k < str.length(); k++) {
					if (Character.isLetter(str.charAt(k)))
						temp += str.charAt(k); //temp now consists of all the characters in a word
					//that are letters or numbers
				}
				Word w = new Word(temp); //creates word object for each element in the file
				if (!d.contains(w))
					out.write(w.getWord() + " " + line + "\n");
			}
		}
		out.close();
	}
	/*
	 * Generates replacements for a word by replacing each individual character in the word
	 * with each element of the alphabet
	 */
	public static void replacer(QuadraticProbingHashTable<Word> d, Word wd) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String str = wd.getWord();
		for (int i = 0; i < str.length(); i++) {
			char [] chars = str.toCharArray();
			for (int j = 0; j < alphabet.length(); j++) {
				chars[i] = alphabet.charAt(j);
				String temp  = "";
				for (int k = 0; k < chars.length; k++) 
					temp += chars[k];
				Word w = new Word (temp);
				if (d.contains(w))
					wd.setSuggestions(w.getWord()); 
			}
		}
	}
	/*
	 * Generates replacements for a word by deleting each individual character and seeing if the resulting word exists
	 */
	public static void delete(QuadraticProbingHashTable<Word> d, Word wd) {
		String str = wd.getWord();
		for (int i = 0; i < str.length(); i++) {
			String temp = "";
			if (i == 0) {
				temp = str.substring(1);
			}
			else 
				temp = str.substring(0, i) + str.substring(i + 1);
			Word w = new Word(temp);
			if (d.contains(w))
				wd.setSuggestions(w.getWord());
		}
	}
}

