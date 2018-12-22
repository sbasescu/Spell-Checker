import java.util.*;
public class Word{

	private String word; //modified original word without punctuation
	private String punctuation; //word with original punctuation
	private String replaced; //replacement for the word
	private ArrayList<String> suggestions = new ArrayList<String>(); 
	private boolean ignored;

	public Word(String w)
	{
		word = w;
		replaced = w;
		ignored = false;
	}
	public void setWord(String w)
	{
		word = w;
	}
	
	public String getWord()
	{
		return word;
	}
	public void setPunctuation(String p) {
		punctuation = p;
	}
	public String getPunctuation () {
		return punctuation;
	}
	
	public void setReplaced(String r) {
		replaced = r;
	}
	
	public String getReplaced() {
		return replaced;
	}
		
	public void setIgnored () {
		ignored = true;
	}
	
	public boolean getIgnored() {
		return ignored;
	}
	
	public ArrayList <String> getSuggestions (){
		return suggestions;
	}
	public void setSuggestions(String s) {
		suggestions.add(s);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	
	/*
	 * equals method that compares two words
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}	
}
