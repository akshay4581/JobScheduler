
public class Job {

	public RBTImpl rbt;
	public MinHeapImpl heap;
	public int jobID;
	public long remainingTime;
	public long executedTime;
	public long totalTime;
	
	//Creates an Object for job so that it can be inserted into heap
	public Job(MinHeapImpl hp, int newId, long newExecTime) {

		executedTime = 0;
		jobID = newId;
		remainingTime = newExecTime;
		totalTime = remainingTime - executedTime;
		heap = hp;
		heap.insert(this);

	}

	//Creates an Object for job so that it can be inserted into RBT
	public Job(RBTImpl rbtree, int newId, long newExecTime) {

		executedTime = 0;
		jobID = newId;
		remainingTime = newExecTime;
		totalTime = remainingTime - executedTime;
		rbt = rbtree;
		rbt.insert(this);

	}

}
