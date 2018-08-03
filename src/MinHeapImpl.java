
import java.util.ArrayList;

public class MinHeapImpl
{
	private static ArrayList<Job> Heap;
	private static final int FRONT = 1;

	public MinHeapImpl(){

		Heap=new ArrayList<Job>();
		Heap.add(null);

	}
	//Find the parent node
	private int parent(int pos)
	{
		return pos / 2;
	}
	//left child index is returned
	private int leftChild(int pos)
	{
		return (2 * pos);
	}
	//right child index is returned 
	private int rightChild(int pos)
	{
		return (2 * pos) + 1;
	}

	private boolean isLeaf(int pos)
	{
		if (pos >=  (Heap.size() / 2)  &&  pos <= Heap.size())
		{ 
			return true;
		}
		return false;
	}

	//Funtion to swap two numbers
	private void swap(int fpos, int spos)
	{
		Job tmp;
		tmp = Heap.get(fpos);
		Heap.set (fpos, Heap.get(spos));
		Heap.set(spos, tmp);
	}

	//To heapify the heap so that it satisfies min heap properties
	public void minHeapify(int pos)
	{
		if (!isLeaf(pos))
		{ 
			if ( (Heap.get(pos)).executedTime > Heap.get(leftChild(pos)).executedTime  || (Heap.get(pos)).executedTime > Heap.get(rightChild(pos)).executedTime)
			{
				if (Heap.get(leftChild(pos)).executedTime < Heap.get(rightChild(pos)).executedTime)
				{
					swap(pos, leftChild(pos)); // swap left child and parent
					minHeapify(leftChild(pos));
				}
				else 
				{
					swap(pos, rightChild(pos)); //swap right child and parent
					minHeapify(rightChild(pos));
				}
			}
		}
	}

	//printing the heap
	public void print()
	{
		for (int i = 1; i <= Heap.size() -1; i++ )
		{
			System.out.print(Heap.get(i).jobID +" "+Heap.get(i).executedTime+" "+Heap.get(i).remainingTime+"\t");
		}
		System.out.println("\n");
	}
	
	//Inserting a job inside heap
	public void insert(Job job)
	{
		Heap.add(job);
		int current = Heap.size()-1;

		while (current!=1&&(Heap.get(current).executedTime < Heap.get(parent(current)).executedTime))
		{	 
			swap(current,parent(current));
			current = parent(current);
		}
		// printHeap(); 
	}


	public void minHeap()
	{
		for (int pos = (Heap.size()-1 / 2); pos >= 1 ; pos--)
		{
			minHeapify(pos);
		}
	}

	//removing the job
	public Job remove()
	{
		Job pr=null;
		if(Heap.size()>1)
		{
			pr = Heap.get(FRONT);
			Heap.set(FRONT,Heap.get(Heap.size()-1)); 
			minHeapify(FRONT);
			Heap.remove(Heap.size()-1);
		}


		return pr;
	}
	public int remove_job(Job J)
	{
		int g = 0;
		if(Heap.size()>1)
		{
			g = Heap.indexOf(J);
			Heap.remove(g);
		}
		return g;
	}

	public int getSize()
	{
		return Heap.size()-1;
	}


}