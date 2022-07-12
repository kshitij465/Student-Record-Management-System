import Includes.*;
import java.util.*;

public class MerkleTree{
	
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numdocs;
	public TreeNode[] data;

	public String Build(String[] documents){
		// Implement Code here
		CRF en=new CRF(64);
		int i=documents.length;
		int num=i;
		TreeNode[] lenss=new TreeNode[i];
		for(int j=0;j<i;j++){
			TreeNode k=new TreeNode();
			k.val=documents[j];
			lenss[j]=k;
		}
		while(i!=1){
			int ind=0;
			for(int j=0;j<i;j++){
				TreeNode k=new TreeNode();
				if(ind+1<=i){
					k.left=lenss[ind];
					lenss[ind].parent=k;
					lenss[j]=k;
				}
				else{j=i;break;}
				
				if(ind+2<=i){
					ind=ind+1;
					k.right=lenss[ind];
					lenss[ind].parent=k;
					k.val=en.Fn(k.left.val+"#"+k.right.val);
				}
				else{ind+=1;
					k.val=en.Fn(k.left.val+"#");
					j=i;}
				ind+=1;
			}
			i=ind/2;
		}
		this.rootnode=lenss[0];
		this.rootnode.parent=null;
		this.numdocs=num;
		return this.rootnode.val;
	}

	/* 
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second, that can be set either manually or
		using the constructor

		Edit: The constructor is added
	*/
		
	public List<Pair<String,String>> QueryDocument(int doc_idx){
		// Implement Code here
		int kk=doc_idx;
		int lmao=kk;
		int x=numdocs;
		TreeNode k=rootnode;
		while(x>1){
			if(lmao>x/2){
				k=k.right;
				lmao-=x/2;
				x/=2;
			}
			else{
				k=k.left;
				x/=2;
			}
		}
		

		ArrayList<Pair<String,String>> akk=new ArrayList<Pair<String,String>>();
		while(k.parent!=null){
			Pair ak=new Pair<String,String>(k.parent.left.val,k.parent.right.val);
			akk.add(ak);
			k=k.parent;
		}
		String aa=null;
		Pair ak=new Pair<String,String>(rootnode.val,aa);
		akk.add(ak);

		return akk;
	}
	public static boolean Authenticate(List<Pair<String,String>> path, String summary){
		// Implement Code here
		CRF en=new CRF(64);
		boolean ak=true;
		int ind=1;
		while(ak  && ind!=path.size()){
			String su=en.Fn(path.get(ind-1).get_first()+"#"+path.get(ind-1).get_second());
			if(path.get(ind).get_first().equals(su) || path.get(ind).get_second().equals(su)){ind=ind+1;}
			else{ak=false;}
		}
		if(!ak){return false;}
		else if(summary.equals(path.get(ind-1).get_first()) || summary.equals(path.get(ind-1).get_second())){
			return true;
		}
		else{return false;}


	}
	public String UpdateDocument(int doc_idx, String new_document){
		// Implement Code here
		CRF en=new CRF(64);
		int kk=doc_idx;
		int lmao=kk;
		int x=numdocs;
		TreeNode k=rootnode;
		while(x>1){
			if(lmao>x/2){
				k=k.right;
				lmao-=x/2;
				x/=2;
			}
			else{
				k=k.left;
				x/=2;
			}
		}
		k.val=new_document;
		while(k.parent!=null){
			if(k.parent.right!=null){
				k.parent.val=en.Fn(k.parent.left.val+"#"+k.parent.right.val);
			}
			else{
			k.parent.val=en.Fn(k.parent.left.val+"#");
			}
			k=k.parent;
		}


		return rootnode.val;
	}
}
