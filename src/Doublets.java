import java.util.Scanner;

/**
 * @author <<Nicklas Carpenter>>
 */
public class Doublets {
	private LinksInterface links;

	public Doublets(LinksInterface links) {
		this.links = links;
	}

	/**
	 * 
	 * A CUI implementation of the doublets search program.
	 *
	 * @param args
	 */
	public static void main(String[] args) {	
		// Links are generated in the beggining since they only need to be generated once per call.
		Links links = new Links("english.cleaned.all.35.txt");
		// Doublets created here to avoid repetative constructions.
		Doublets doublet = new Doublets(links); 

		System.out.println("Welcome to Doublets, a game of \"verbal torture.\"");
		
		while (true) {
			Scanner input = new Scanner(System.in); // Scans user input
			
			System.out.print("Enter starting word: ");
			String start = input.nextLine();
			System.out.print("Enter ending word: ");
			String end = input.nextLine();
			
			if (!links.exists(start)) {
				System.out.println("\nThe word \"" + start + "\" is not valid. Please try again. ");
				continue;
			}

			else if (!links.exists(end)) {
				System.out.println("The word \"" + end + "\" is not valid. Please try again. ");
				continue;
			}

			/*
			 * The following block runs is responsible for initializing the ChainManager acording to the user's desire
			 * or exiting the program. It runs until the user passes valid input at wich point the program either
			 * initializes manager or exits.
			 */
			ChainManager manager = null;
			while (manager == null) {
				System.out.print("Enter a chain manager (s: stack, q: queue, p: priority queue, x: exit): ");
				String choice = input.nextLine();
				switch (choice) {
				case "s":
					manager = new StackChainManager();
					break;
				case "q":
					manager = new QueueChainManager();
					break;
				case "p":
					manager = new PriorityQueueChainManager(end);
					break;
				case "x":
					System.out.println("Goodbye!");
					return; // Exits the program.
				default:
					System.out.println("Invalid option.\n");
				}
			}

			Chain resultChain = doublet.findChain(start, end, manager);
			if (resultChain == null) {
				System.out.println("No doublet chain exists from " + start + " to " + end + ".");
				continue;
			}
			System.out.println("Chain: " + resultChain);
			System.out.println("Length: " + resultChain.length() + "\nCanidates: " 
			+ manager.getNumberOfNexts() + "\nMax size: " + manager.maxSize());
		}
	}

	/**
	 * 
	 * Finds a chain that contains a valid doublet path of links from the specified start word to the specified end 
	 * word. 
	 *
	 * @param start
	 * @param end
	 * @param manager
	 * @return The chain containing a valid link path if one exists. If no path exists, returns null.
	 */
	public Chain findChain(String start, String end, ChainManager manager) {
		// If the start and end words are of differing lengths, there is no valid path betwixt them.
		if (start.length() != end.length())
			return null;

		Chain chain = new Chain();
		manager.add(chain.addLast(start));

		// Runs while the manager has chains to check
		while (!manager.isEmpty()) {
			chain = manager.next();
			if (chain.getLast().equals(end))
				// As soon as we find a valid string we return it; we don't need to find the shortest chain.
				return chain; 
			// Checks to ensure that the last word of the chain has valid candidates, preventing null pointer exceptions.
			if (this.links.getCandidates(chain.getLast()) != null) {
				for (String s : this.links.getCandidates(chain.getLast())) {
					Chain newChain = chain.addLast(s);
					if (newChain.length() > chain.length())
						manager.add(newChain);
				}
			}
		}
		return null; // If no chain has been found at this point, there is no valid path between 'start' and 'end'.
	}

}
