import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;
import java.rmi.*;
public class MasterObj extends UnicastRemoteObject implements masterObjInterface{

String name;
	
	static int countermo=0;//Master Object counter/clock
	static boolean flagmo=false;//Master Object Flag indicator
	static int[] a={0,0,0,0,0};//Array to store Process Objects' clock values
	static int[] b=new int[5];//Array to use for calculating offset
	static boolean flagmostart=false;
	
	/*Constructor of Master Object which takes in the parameter name*/
	 MasterObj(String name) throws Exception{
		this.name=name;	
	}
	 
	 /*Function to receive Process Object clock values */
	 public void receivemo(int pid,int c){
		 if(c<countermo){
			 countermo++;
			}
			else{
				countermo=c+1;
			}
		 a[0]=countermo;
		 a[pid]=c;
		 
	 }
	 
	 /*Function to receive clock adjustment after a unit of time*/
	 public void correctorpo(int pid,int c){
		 countermo++;
		 a[0]=countermo;
		 a[pid]=c;
		 b=a.clone();
		 flagmo=true;
		 
	 }
	
	
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating a Master Object!");
			String name = "//10.234.140.27:2016/MasterObj";
			MasterObj MO = new MasterObj(name);
			System.out.println("MasterObject: binding it to name: " +
			name);
			Naming.rebind(name, MO);
			System.out.println("Master Object Ready!");
			MOThread MOt=new MOThread("MOt");
			MOt.start();
			
		} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}

class MOThread extends Thread {
	String name;
	 
	MOThread(String threadname) throws InterruptedException{
			this.name=threadname;	
		}
	
	public void run(){
		try{
		String yes="y";
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Ready to connect to PO1?(y/n)");
		String ans1=br.readLine();
		if(ans1.equalsIgnoreCase(yes)){
		
			String name1="//10.234.140.28:2016/ProcessObj1";
			processObj1Interface PO1= (processObj1Interface) Naming.lookup(name1);
			
			System.out.println("Ready to connect to PO2?(y/n)");
			String ans2=br.readLine();	
			if(ans2.equalsIgnoreCase(yes)){
				
				String name2="//10.234.140.29:2016/ProcessObj2";
				processObj2Interface PO2= (processObj2Interface) Naming.lookup(name2);
				
				System.out.println("Ready to connect to PO3?(y/n)");
				String ans3=br.readLine();	
				if(ans3.equalsIgnoreCase(yes)){	
		
					String name3="//10.234.140.30:2016/ProcessObj3";
					processObj3Interface PO3= (processObj3Interface) Naming.lookup(name3);
					
					System.out.println("Ready to connect to PO4?(y/n)");
					String ans4=br.readLine();	
					if(ans4.equalsIgnoreCase(yes)){
					
						String name4="//10.234.140.31:2016/ProcessObj4";
						processObj4Interface PO4= (processObj4Interface) Naming.lookup(name4);
					
						System.out.println("Ready to start MO?(y/n)");
						String ans5=br.readLine();	
						if(ans5.equalsIgnoreCase(yes)){
						
					for(int i=0;i<120;i++){
			 
			if(MasterObj.flagmo==true){//Flag notifying Master Object to calculate the offsets of each Process Objects
				
				/*Function to calculate the offset for each Process Object*/
				int sum=0;
				 for(int k=0;k<5;k++){
					 sum=sum+MasterObj.b[k];
				 }
				 int avg=sum/5;
				 int offsetmo=avg-MasterObj.countermo;
				 MasterObj.countermo=MasterObj.countermo+offsetmo;
				 int offsetpo=0;
				 for(int j=1;j<5;j++){
					 if(j==1){
						 offsetpo=avg-MasterObj.b[j];
						 PO1.receiveoffsetpo1(offsetpo);
						 MasterObj.countermo++;
					 }
					 else if(j==2){
						 offsetpo=avg-MasterObj.b[j];
						 PO2.receiveoffsetpo2(offsetpo); 
						 MasterObj.countermo++;
					 }
					 else if(j==3){
						 offsetpo=avg-MasterObj.b[j];
						 PO3.receiveoffsetpo3(offsetpo);
						 MasterObj.countermo++;
					 }
					 else if(j==4){
						 offsetpo=avg-MasterObj.b[j];
						 PO4.receiveoffsetpo4(offsetpo);
						 MasterObj.countermo++;
					 }
				 }
				 MasterObj.flagmo=false;
				 
			
		}
			System.out.println("MO Counter: "+MasterObj.countermo);
		 Thread.sleep(1500);
	}
		
						}//ans5 if
				 }//ans4 if
				}//ans3 if
			}//ans2 if
		}//ans1 if	
		}catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
