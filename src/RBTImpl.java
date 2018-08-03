
public class RBTImpl {

	private final int RED = 0;
	private final int BLACK = 1;
	public static int count=0;
	public static int cNode=0;

	public Job J = null;
	jobscheduler inp = new jobscheduler();
	Node goal,temp,temp2;
	double min = Double.MAX_VALUE;

	private final Node nil = new Node(null); 
	private Node root = nil;

	public Job J1 = null;
	jobscheduler inp1 = new jobscheduler();
	Node goal_prev,temp1;
	double min1 = Double.MAX_VALUE;

	//Node is defined
	public class Node {

		public Job job = null;
		int  color = BLACK;
		Node left = nil, right = nil, parent = nil;
		Node(Job p) {
			this.job = p;
			cNode++;
		} 
	}

	//Inserts a new job to the tree with key as jobID
	public void insert(Job key) {

		Node node=new Node(key);
		Node temp = root;
		if (root == nil) {
			root = node;
			node.color = BLACK;
			node.parent = nil;
		} 
		else {
			node.color = RED;
			while (true) {
				if (node.job.jobID < temp.job.jobID ) {
					if (temp.left == nil) {
						temp.left = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if (node.job.jobID  >= temp.job.jobID ) {
					if (temp.right == nil) {
						temp.right = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			fixTree(node);
		}

	}
	
	//Deletes whole tree
	void deleteTree(){
		root = nil;
	}
	

	private void fixTree(Node node) {
		while (node.parent.color == RED) {
			Node uncle = nil;
			//If parent is left child
			if (node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				} 
				if (node == node.parent.right) {
					//Double rotation needed
					node = node.parent;
					rotateLeft(node);
				} 
				node.parent.color = BLACK;
				node.parent.parent.color = RED;

				rotateRight(node.parent.parent);
			} else {

				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {
					//Double rotation needed
					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				rotateLeft(node.parent.parent);
			}
		}
		root.color = BLACK;
	}

	void rotateLeft(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) { 
				// node is left child
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {
			//Need to rotate root
			Node right = root.right;
			root.right = right.left;
			right.left.parent = root;
			root.parent = right;
			right.left = root;
			right.parent = nil;
			root = right;
		}
	}

	void rotateRight(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {//Need to rotate root
			Node left = root.left;
			root.left = root.left.right;
			left.right.parent = root;
			root.parent = left;
			left.right = root;
			left.parent = nil;
			root = left;
		}
	}



	void transplant(Node target, Node with){ 
		if(target.parent == nil){
			root = with;
		}else if(target == target.parent.left){
			target.parent.left = with;
		}else
			target.parent.right = with;
		with.parent = target.parent;
	}

	//delete the job key in the tree
	boolean delete(Job key){
		Node z=new Node(key);
		if((z = findNode(z, root))==null)
			return false;
		Node x;
		Node y = z;
		int y_original_color = y.color;

		if(z.left == nil){
			x = z.right;  
			transplant(z, z.right);  
		}else if(z.right == nil){
			x = z.left;
			transplant(z, z.left); 
		}else{
			y = treeMinimum(z.right);
			y_original_color = y.color;
			x = y.right;
			if(y.parent == z)
				x.parent = y;
			else{
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color; 
		}
		if(y_original_color==BLACK)
			deleteFixup(x);  
		return true;
	}

	//Delete cases
	void deleteFixup(Node n){
		while(n!=root && n.color == BLACK){ 
			if(n == n.parent.left){
				Node w = n.parent.right;
				if(w.color == RED){
					w.color = BLACK;
					n.parent.color = RED;
					rotateLeft(n.parent);
					w = n.parent.right;
				}
				if(w.left.color == BLACK && w.right.color == BLACK){
					w.color = RED;
					n = n.parent;
					continue;
				}
				else if(w.right.color == BLACK){
					w.left.color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = n.parent.right;
				}
				if(w.right.color == RED){
					w.color = n.parent.color;
					n.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(n.parent);
					n = root;
				}
			}else{
				Node w = n.parent.left;
				if(w.color == RED){
					w.color = BLACK;
					n.parent.color = RED;
					rotateRight(n.parent);
					w = n.parent.left;
				}
				if(w.right.color == BLACK && w.left.color == BLACK){
					w.color = RED;
					n = n.parent;
					continue;
				}
				else if(w.left.color == BLACK){
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = n.parent.left;
				}
				if(w.left.color == RED){
					w.color = n.parent.color;
					n.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(n.parent);
					n = root;
				}
			}
		}
		n.color = BLACK; 
	}

	//Prints the details of the job with the given jobID
	public void printJob(int j_id){
		J = jobscheduler.get_job(j_id);
		Node n = new Node(J);
		if (n == nil) {
			return;
		}
		if(n.job.executedTime == n.job.totalTime){
			System.out.println("(0,0,0)");
		}
		else
			System.out.println("("+n.job.jobID+","+n.job.executedTime+","+n.job.totalTime+")"); 	
	}


	/*
	 * Prints the job which are between low and high
	 */
	public void printJob(int low,int high){
		count = 0;
		print(root,low,high);
		if(count == 0)
			System.out.print("(0,0,0)");
	}

	void print(Node node1, int low, int high)
	{
		//if node doesn't exist return null
		if ( node1 == nil ){
			return;
		}
		//go this loop only if low < mynode.job.JobId	  
		if ( low < node1.job.jobID)
			print(node1.left, low, high);

		if ( low <= node1.job.jobID && high >= node1.job.jobID ){
			count++;
			if(count ==1)
				System.out.print("("+node1.job.jobID+","+node1.job.executedTime+","+node1.job.totalTime+")");
			else 
				System.out.print(",("+node1.job.jobID+","+node1.job.executedTime+","+node1.job.totalTime+")"); 
		}
		//go this loop only if low < mynode.job.JobId	 
		if ( high > node1.job.jobID )
			print(node1.right, low, high);
	}


	/*
	 * Prints the triplet for the job with smallest ID greater than a given jobID. 
	 * 
	 */

	public void nextJob(int j_id) {
		J = jobscheduler.get_job(j_id);
		Node n = root;
		if (n == nil) {
			System.out.println("(0,0,0)");
			return;
		} 
		temp = nearestID(n, j_id);
		if(temp == null){
			System.out.println("(0,0,0)"); 	
		}
		else
			System.out.println("("+temp.job.jobID+","+temp.job.executedTime+","+temp.job.totalTime+")"); 	

	}

	public Node nearestID(Node root, double target) {   
		sub(root, target);
		return goal;
	}

	public void sub(Node root, double target){
		if(root.job==null)
		{
			return;
		}
		if((root.job.jobID - target) < min && (root.job.jobID) > target && (root.job.jobID - target) != 0 ){
			min = (root.job.jobID-target);
			goal = root;
		}
		else if(root.job.jobID == target){
			goal = null;
		}

		if(target < root.job.jobID && root.left!=null){
			sub(root.left, target);
		}
		else if(root.right!=null){
			sub(root.right, target);
		}
	}



	/*
	 * Prints the triplet for the job with greatest ID smaller than a given jobID. 
	 */

	public void previousJob(int j_id) {
		J1 = jobscheduler.get_job(j_id);
		//System.out.println(J1.JobId);
		Node n = root;
		//double t = n.job.JobId;
		if (n == nil) {
			System.out.println("(0,0,0)");
			return;
		} 
		temp1 = nearestIDPrev(n, j_id);
		if(temp1 == null){
			System.out.println("(0,0,0)"); 
		}
		else
			System.out.println("("+temp1.job.jobID+","+temp1.job.executedTime+","+temp1.job.totalTime+")"); 	

	}

	public Node nearestIDPrev(Node root, double target) {   
		sub_prev(root, target);
		return goal_prev;
	}

	public void sub_prev(Node root, double target){
		if(root.job==null){
			return;
		}
		//System.out.println("target"+target+"actual"+root.job.JobId);
		if((target - root.job.jobID) < min1 && (root.job.jobID) < target){
			min1 = (target-root.job.jobID);
			goal_prev = root;
		} 
		else if (goal_prev!=null && goal_prev.job.jobID == target){
			goal_prev = null;
		}

		if(target <= root.job.jobID && root.left!=null){
			sub_prev(root.left, target);
		}else if(root.right!=null){
			sub_prev(root.right, target);
		}
	}



	//This function finds the node in a given RedBlackTree
	private Node findNode(Node findNode, Node node) {

		if (root== nil) {
			return null;
		}

		if (findNode.job.jobID < node.job.jobID) {
			if (node.left != nil) {
				return findNode(findNode, node.left);
			}
		} else if (findNode.job.jobID  > node.job.jobID ) {
			if (node.right != nil) {
				return findNode(findNode, node.right);
			}
		} else if (findNode.job.jobID  == node.job.jobID ) {
			return node;
		}
		return null;
	}

	//calculates tree minimum. This method is uses in delete operation
	Node treeMinimum(Node subTreeRoot){
		while(subTreeRoot.left!=nil){
			subTreeRoot = subTreeRoot.left;
		}
		return subTreeRoot;
	}

}