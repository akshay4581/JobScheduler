
public class Scheduler {	

	public static RBTImpl rbt;
	public static MinHeapImpl hp;
	public static Integer global_timer = 0;
	public static Integer timer = 0;
	public static Job job;
	public static Job job1;

	// schedule jobs
	public void scheduleHeap(MinHeapImpl hp, RBTImpl rbt)
	{		
		for(int u = 0;u < jobscheduler.l;u++){
			if(timer % 5 == 0){
				job = hp.remove();
				rbt.delete(job);
				job1 = job;
			}
			else if(job1.remainingTime > 0 ){
				hp.remove_job(job1);
				rbt.delete(job1);				
			}
			else if(job1.remainingTime <= 0){
				job = hp.remove();
				rbt.delete(job);
				job1 = job;
				timer = 0;
			}
			if(job1 == null){
				global_timer++;
			}

			else if(job1.executedTime >= 0)
			{
				job1.executedTime = job1.executedTime+1;
				job1.remainingTime=job1.remainingTime-1;
				timer++;
				global_timer++;
				if(job1.remainingTime>0)
				{
					hp.insert(job1);
					rbt.insert(job1);
				}
			}
		}
	}
	
	//schedules all the jobs after all the statements in the input file are executed
	
	public void heapScheduleRemaining(MinHeapImpl hp, RBTImpl rbt)
	{
		while(hp.getSize()>=1){
			if(timer % 5 == 0){
				job = hp.remove();
				job1 = job;
			}
			else if(job1.remainingTime > 0 ){
				hp.remove_job(job1);
				rbt.delete(job1);	
			}
			else if(job1.remainingTime <= 0){
				job = hp.remove();
				job1 = job;
				timer = 0;
			}
			global_timer++;

			if(job1.executedTime >= 0)
			{
				job1.executedTime = job1.executedTime+1;
				job1.remainingTime=job1.remainingTime-1;
				timer++;
				if(job1.remainingTime>0)
				{
					hp.insert(job1);
					rbt.insert(job1);
				}
			}
		}
	}
}
