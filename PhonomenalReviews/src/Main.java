import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static int numOfNodes;
	static int numOfPhos;
	static boolean[] phoRestaurants;
	static Node[] tree=null;
	
	static int chainLength=0;
	static int maxChainLength=0;
	static int numCutNodes=0;
	
	static int firstPhoNode=-1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadInput();
		CutNonPhoLeafs();

		int node=findFurthestNode(-1, firstPhoNode);
		node=findFurthestNode(-1, node);
		
		System.out.println((numCutNodes-1-chainLength)*2+chainLength);

	}


	

	private static int findFurthestNode(int incomeNodeId, int nodeId) {
		// TODO Auto-generated method stub
		Node node=tree[nodeId];
		int returnNodeId=-1;
		if (node.isLeaf()&& incomeNodeId!=0) {
			chainLength=0;
			returnNodeId=nodeId;
		}
		for (Node connectNode:node.connectedNodes) {
			if (connectNode.id==incomeNodeId) {
				continue;
			}
			int furthestNodeId=findFurthestNode(node.id,connectNode.id);
			chainLength++;
			if (maxChainLength<chainLength) {
				maxChainLength=chainLength;
				returnNodeId=furthestNodeId;
			}
			
		}
		return returnNodeId;
	}




	private static void CutNonPhoLeafs() {
		// TODO Auto-generated method stub
		for (int i=0;i<numOfNodes;i++) {
			Node node=tree[i];
			
			if (node!=null && !node.isPhoRestaurant) {
				if (node.isLeaf()) {
					tree[node.id]=null;
					for (Node connectNode:node.connectedNodes) {
						connectNode.connectedNodes.remove(node);
					}
					CutNonPhoLeafs();
				}
			}
		}
	}


	private static void ReadInput() {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		numOfNodes=sc.nextInt();
		numOfPhos=sc.nextInt();
		phoRestaurants=new boolean[numOfNodes];
		
		for (int i=0;i<numOfNodes;i++) {
			phoRestaurants[i]=false;
		}
		
		for (int i=0;i<numOfPhos;i++) {
			int nodeId=sc.nextInt();
			if (firstPhoNode==-1) {
				firstPhoNode=nodeId;
			}
			phoRestaurants[nodeId]=true;
		}
		
		tree=new Node[numOfNodes];
		for (int i=0;i<numOfNodes;i++) {
			tree[i]=null;
		}
		for (int i=0;i<numOfNodes-1;i++) {
			int nodeId1=sc.nextInt();
			int nodeId2=sc.nextInt();
			Node node1;
			Node node2;
			if (tree[nodeId1]==null) {
				node1=tree[nodeId1]=new Node(nodeId1,phoRestaurants[nodeId1]);
			} else node1=tree[nodeId1];
			
			if (tree[nodeId1]==null) {
				node2=tree[nodeId2]=new Node(nodeId2,phoRestaurants[nodeId2]);
			} else node2=tree[nodeId2];
			
			node1.connectedNodes.add(node2);
			node2.connectedNodes.add(node1);
		}
	}

}

class Node {
	public Node(int id, boolean pho) {
		this.id=id;
		this.isPhoRestaurant=pho;
	}
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return this.connectedNodes.size()<=1;
	}
	int id;
	boolean isPhoRestaurant=false;
	ArrayList<Node> connectedNodes=new ArrayList<>();
}
