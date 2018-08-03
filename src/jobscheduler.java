

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class jobscheduler {
	
	static File file = null;
	
	public static int i;
	public static int j;
	public static int k;
	public static int l;
	
	public static RBTImpl rbt;
	public static MinHeapImpl hp;
	
	static ArrayList<Job> jobs = new ArrayList<Job>();
	static List<String> list = new ArrayList<String>();
	static List<Integer> timers = new ArrayList<Integer>();
	
	public static void main(String[] args) throws FileNotFoundException 
	{
			rbt=new RBTImpl();
			hp=new MinHeapImpl();

			File file = new File("input1");
			Scanner scan = new Scanner(file);
		   
			
			/*file = new File(args[0]);
			Scanner scan = new Scanner(file);*/
			
			File file1 = new File("output_file.txt");
			FileOutputStream fos = new FileOutputStream(file1);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);

			while (scan.hasNextLine()) {
			    list.add(scan.nextLine());
			}
			
			/* 
			 *  All the arrival times which are before colon are inserted into the list 
			 * 
			 */
			for(int i=0;i< list.size();i++){
				String str = list.get(i);
				String t = str.substring(0, str.indexOf(":"));
				int p = Integer.parseInt(t);
				timers.add(p);
			}
			timers.add(0);
			
			for(int i=0;i< list.size();i++){
				String str = list.get(i);
				k = timers.get(i);
				j = timers.get(i+1);
				l = j-k;
				
				if(k == Scheduler.global_timer){
			
			/* 
			 * Job is inserted into heap and RBT 
			 */
				if(str.contains("Insert")){
					String ans1 = str.substring(str.indexOf("(")+1,str.indexOf(","));
					int l = Integer.parseInt(ans1);
					String ans2 = str.substring(str.indexOf(",")+1,str.indexOf(")"));
					int m = Integer.parseInt(ans2);
					jobs.add(new Job(hp,l,m));
					jobs.add(new Job(rbt,l,m));
					Scheduler sc=new Scheduler();
				    sc.scheduleHeap(hp,rbt);
					
				}
			
				else if(str.contains("PrintJob")){
					//Prints the jobs which are in the range
					if(str.contains(","))  
					{
						String stmt1 = str.substring(str.indexOf("(")+1,str.indexOf(","));
						String stmt2 = str.substring(str.indexOf(",")+1,str.indexOf(")"));
						int k1 = Integer.parseInt(stmt1);
						int k2 = Integer.parseInt(stmt2);
						rbt.printJob(k1,k2);
						System.out.println("");
							Scheduler sc=new Scheduler();
						    sc.scheduleHeap(hp,rbt);  
					}
					//prints the job by finding the job using key
					else{
						String answer = str.substring(str.indexOf("(")+1,str.indexOf(")"));
						int k = Integer.parseInt(answer);
							rbt.printJob(k);
							Scheduler sc=new Scheduler();
						    sc.scheduleHeap(hp,rbt);
						}
				}
				/* If contains NextJob, print the NextJob and call the Scheduler from the Scheduler.java class. 
				 * The NextJob is invoked from RedBlackTree.java
				 */
				else if(str.contains("NextJob")){
					String answer = str.substring(str.indexOf("(")+1,str.indexOf(")"));
					int k = Integer.parseInt(answer);
					rbt.nextJob(k);
					Scheduler sc=new Scheduler();
				    sc.scheduleHeap(hp,rbt);
				}
				/* If contains PreviousJob, print the PreviousJob and call the Scheduler from the Scheduler.java class. 
				 * The PreviousJob is invoked from RedBlackTree.java
				 */
				else if(str.contains("PreviousJob")){
					String answer = str.substring(str.indexOf("(")+1,str.indexOf(")"));
					int k = Integer.parseInt(answer);
					rbt.previousJob(k);
					Scheduler sc=new Scheduler();
				    sc.scheduleHeap(hp,rbt);
				}
				}
				
			}
			//Keep running for the remaining amount of time till next operation comes in
			Scheduler sc=new Scheduler();
		    sc.heapScheduleRemaining(hp,rbt);
			
	}
	
	public static Job b;
	public static  Job get_job(int j_id){
		int i=0;
		int k=0;	
		for(i=0;i<jobs.size();i++){	
			if(jobs.get(i).jobID==j_id){
				k=i;
				break;
			}   
			else{
				k=-1;
			}
		} 
		if(k > 0)
			return jobs.get(k);	
		else{
			b=jobs.get(0);
			b.remainingTime = 0;
			b.totalTime = 0;
			b.executedTime = 0;
			b.jobID = 0;
			return b;
		}
			
	}
			
}		
