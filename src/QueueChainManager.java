import java.util.LinkedList;

/**
 * 
 * A Queue implementation of ChainManager. Allows for a breadth-first search of Chains.
 *
 * @author carpennp.
 *         Created Dec 18, 2017.
 */
public class QueueChainManager extends ChainManager {
	
	private LinkedList<Chain> chains;
	
	public QueueChainManager() {
		this.chains = new LinkedList<>();
	}

	@Override
	public void add(Chain chain) {
		this.chains.offer(chain); // Puts the added chain to the back of the list, inherited from Queue.
		updateMax(this.chains.size());
	}

	@Override
	public Chain next() {
		incrementNumNexts();
		if (this.isEmpty())
			return null;
		return this.chains.pop();
	}

	@Override
	public boolean isEmpty() {
		return this.chains.peek() == null;
	}
}
