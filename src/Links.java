import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Links implements LinksInterface {
	
	// links maps a given dictionary word to all one word manipulations of that word that exist in the same dictionary.
	private HashMap<String, HashSet<String>> links;
	
	/**
	 * 
	 * Constructs a Links object which loads a specified dictionary.
	 *
	 * @param fileName
	 */
	public Links(String fileName) {
		this.links = new HashMap<>();
		initialize(fileName);
	}
	
	/**
	 * 
	 * Initializes links with the specified dictionary/word-list.
	 *
	 * @param fileName
	 */
	private void initialize(String fileName) {
		HashSet<String> dictionary = generateDictionary(fileName);
	
		for (String s : dictionary) {
			this.links.put(s, new HashSet<String>());
			// Checks to see whether the new String, s, is a canidate of a pre-existing key.
			for (String key : this.links.keySet()) { 
				/*
				 * To save time, length comparisons are made before data comparisons as equal length is a 
				 * necessary precondition to words being each other's candiates.
				 */
				if (key.length() == s.length()) { 
					int differences = 0;
					for (int i = 0; i < key.length(); i++) {				
						if (key.charAt(i) != s.charAt(i))
							differences++;
						if (differences > 1)
							break;
					}
					// Words are only doublets if there is exactly one difference between them.
					if (differences == 1) {
						/*
						 *  The new word, s, and the key are added to each other's candiates since if a word is 
						 *  canidate of another word, the other word is a canidate of that word. 
						 */
						this.links.get(key).add(s);
						this.links.get(s).add(key);
					}
				}
			}
		}	
	}
	
	/**
	 * 
	 * Creates a HashSet that contains all the words in the specified file. This slightly increases parse time of the 
	 * dictionary in the 'inialize' method.
	 *
	 * @param fileName
	 * @return A HashSet of Strings drawn from the specified file.
	 */
	private HashSet<String> generateDictionary(String fileName) {
		HashSet<String> dictionary = new HashSet<>();
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new File(fileName));
		} catch (FileNotFoundException exception) {
			System.err.println("File Not Found!");
			exception.printStackTrace();
		}
		while (fileReader.hasNextLine()) {
			dictionary.add(fileReader.nextLine());
		}
		
		fileReader.close();
		return dictionary;
	}

	@Override
	public Set<String> getCandidates(String word) {
		if (!this.links.containsKey(word) || this.links.get(word).isEmpty())
			return null;
		return this.links.get(word);
	}

	@Override
	public boolean exists(String word) {
		for (String key : this.links.keySet()) {
			if (key.equals(word))
				return true;
		}
		return false;
	}
}
