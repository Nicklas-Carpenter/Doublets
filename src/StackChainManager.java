import java.util.Stack;

/**
 * 
 * A Stack implementation of ChainManager. Allows a depth-first search of Chains. 
 *
 * @author carpennp.
 *         Created Dec 18, 2017.
 */
public class StackChainManager extends ChainManager{
	
	private Stack<Chain> chains;
	
	public StackChainManager() {
		this.chains = new Stack<>();
	}

	@Override
	public void add(Chain chain) {
		this.chains.push(chain);
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
		return this.chains.empty();
	}
}
