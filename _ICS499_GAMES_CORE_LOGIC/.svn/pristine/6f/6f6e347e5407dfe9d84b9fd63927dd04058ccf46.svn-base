package core;

import core.words.BigWord;
import core.words.BigWordCollection;

/**
 * This class is an external iterator to support the naviation
 * of BigWordCollection.
 * 
 * The collection can not be modified. All the navigations are read-only.
 * This is an external iterator and hence multiple iterators can be generated.
 * @author srj
 *
 */
public class BWCIterator {
	
	private BigWordCollection bwc;
	private int currentIndex = 0;
	private int manyItems = 0;
	
	/**
	 * Constructor for holding the reference to a BigWordCollection.
	 * This collection will be navigated. 
	 * 
	 * @param some_bwc
	 */
	public BWCIterator(BigWordCollection some_bwc)
	{
		bwc = some_bwc;
		currentIndex = 0;
		manyItems = some_bwc.size();
	}
	
	/**
	 * For getting the current element
	 * @return
	 */
	public BigWord getCurrent()
	{
		return bwc.getBigWord(currentIndex);
	}

	/**
	 * For getting the previous element
	 * @return
	 */
	public BigWord getPrevious()
	{
		if (currentIndex == 0)
		{
			System.out.println("There is no previous");
			return getCurrent();
		}
		currentIndex--;
		return getCurrent();
		
	}
	
	/**
	 * For getting the next element
	 * @return
	 */
	public BigWord getNext()
	{
		if (currentIndex == manyItems-1)
		{
			System.out.println("There is no next");
			return getCurrent();
		}
		currentIndex++;
		return getCurrent();
		
	}
	
	/**
	 * For moving the pointer to a previous element.
	 * If we are already at the first element, then do nothing
	 * @return
	 */
	public void previous()
	{
		if (currentIndex == 0)
		{
			System.out.println("There is no previous");
			return;
		}
		currentIndex--;		
	}
	
	/**
	 * For moving the pointer to a next element.
	 * If we are already at the last element, then do nothing
	 * @return
	 */
	public void next()
	{
		if (currentIndex == manyItems-1)
		{
			System.out.println("There is no next");
			return;
		}
		currentIndex++;	
	}
	
	public boolean hasNext() {
		if (currentIndex == manyItems-1)
			return false;
		return true;
	}
	

	/**
	 * For moving the pointer to the start of the collection.
	 * @return
	 */
	public void start()
	{
		currentIndex = 0;
	}
	
	/**
	 * For moving the pointer to the end of the collection.
	 * @return
	 */
	public void end()
	{
		currentIndex = manyItems-1;
	}
	
}
