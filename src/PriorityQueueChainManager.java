import java.util.PriorityQueue;

/**
 * 
 * A PriorityQueue implementation of ChainManager. Allows a breadth-first search of Chains with priority given to 
 * Chains with more "potential" (see the Entry inner class for more details). 
 *
 * @author carpennp.
 *         Created Dec 18, 2017.
 */
public class PriorityQueueChainManager extends ChainManager {
	
	/*
	 * Since Chains are not Comparable, the PriorityQueueChainManager must have a PriorityQueue of the Entry wrapper
	 * class to ensure proper ordering.
	 */
	private PriorityQueue<Entry> chains; 
	private String endWord;
	
	/**
	 * 
	 * Creates a PriorityChainManager with the specified end word.
	 *
	 * @param end
	 */
	public PriorityQueueChainManager(String end){
		this.chains = new PriorityQueue<>();
		this.endWord = end;
	}
	
	@Override
	public void add(Chain chain) {
		this.chains.add(new Entry(chain)); // Adds a new Chain wrapped in an Entry.
		updateMax(this.chains.size());
	}

	@Override
	public Chain next() {
		incrementNumNexts();
		if (this.isEmpty())
			return null;
		return this.chains.poll().chain; // Returns the Chain tied to a specific entry.
	}

	@Override
	public boolean isEmpty() {
		return this.chains.size() == 0;
	}
	
	/**
	 * 
	 * Calculates how many characters differ between the given word and the end word to get a decent estimate of how
	 * many more links will be need to form until the Chain that contains this word contains the end word.
	 *
	 * @param word
	 * @return The number of character differences between the specified word and the end word.
	 */
	private int estimateDistance(String word){
		int difference = 0;
		for (int i = 0; i < word.length(); i++){
			if(word.charAt(i) != this.endWord.charAt(i)){
				difference++;
			}
		}
		return difference;
	}
	
	/**
	 * 
	 * A class that wraps Chain, allowing Chains to be given a value, an estimate of how quickly a given Chain will
	 * find the end word. This allows Chains to be stored in a PriorityQueue, increasing the efficiency of the Doublets
	 * search.
	 *
	 * @author carpennp.
	 *         Created Dec 18, 2017.
	 */
	class Entry implements Comparable<Entry> {
		
		public Chain chain; // The chain wrapped by this class instance.
		private int value; // The "value" associated with this Chain. Lower values are more favorable.
		
		/**
		 * 
		 * Constructs an Entry class with the Chain it wraps. Calculates and stores the "value" of this Chain.
		 *
		 * @param chain
		 */
		public Entry(Chain chain) {
			this.chain = chain;
			this.value = chain.length() + estimateDistance(chain.getLast());
		}
		
		// Used to determine the ordering of the PriorityQueue.
		@Override
		public int compareTo(Entry e) {
			return this.value - e.value;
		}
	}

}
