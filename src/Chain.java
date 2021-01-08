import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * TODO Put here a description of what this class does.
 *
 * @author xuy8.
 *         Created Dec 11, 2017.
 */
public class Chain implements Iterable<String>{
	
	private LinkedHashSet<String> words;
	private String lastWord;
	
	/**
	 * 
	 * The default constructor. Used for an empty chain.
	 *
	 */
	public Chain() {
		this.words = new LinkedHashSet<>();
	}
	
	/**
	 * 
	 * The constructor add calls to account for Chain being immutable. This constructor should not be called directly
	 *
	 * @param words
	 * @param newWord
	 */
	private Chain(LinkedHashSet<String> words, String newWord) {
		this.words = new LinkedHashSet<>(words);
		this.lastWord = newWord;
		this.words.add(newWord);
	}
	
	/**
	 * 
	 * Creates an identical Chain with word appended.
	 *
	 * @param word
	 * @return A new chain containing the contents of the calling chain and 'word'.
	 */
	public Chain addLast(String word) {
		return new Chain(this.words, word);
	}
	
	/**
	 * This methods is used to access the last word stored in this chain.
	 * 
	 * @return The last word stored in the chain.
	 */
	public String getLast() {
		return this.lastWord;
	}
	
	public int length() {
		return this.words.size();
	}
	
	/**
	 * 
	 * Checks whether a chain contains the specified word.
	 *
	 * @param word
	 * @return True if this chain contains 'word', returns false otherwise.
	 */
	public boolean contains(String word) {
		return this.words.contains(word);
	}

	@Override
	public Iterator<String> iterator() {
		return this.words.iterator();
	}
	
	@Override
	public String toString() {
		return this.words.toString();
	}
}
