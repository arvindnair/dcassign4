import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;
import java.rmi.*;
public class ProcessObj1 extends UnicastRemoteObject implements processObj1Interface{

String name;
	
	static boolean flagpo1start=false;
	static int probpo1t=15;//Process Object 1 t unit sending probability
	static int probpo1=75;//Process Object 1 message sending probability
	static int counterpo1=0;//Process Object 1 counter
	static int offsetpo1=0;//Process Object 1 offset
	static boolean flagpo1=false;//Process Object 1 flag indicator
	
	/*Constructor of Process Object 1 which takes in the parameter name*/
	 ProcessObj1(String name) throws Exception{
		this.name=name;
	}
	 
	 
	 /*Function to receive the clock values and adjust Process Object 1's clock accordingly if necessary*/
	 public void receivepo1(int c){
		if(c<counterpo1){
		 counterpo1++;
		}
		else{
			counterpo1=c+1;
		}
		 
	 }
	 
	 /*Function to receive the offset from Master Object for Process Object 1*/
	 public void receiveoffsetpo1(int o){
		 offsetpo1=o;
		 flagpo1=true;
		 
	 }
	 
	
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating Process Object 1!");
			String name = "//10.234.140.28:2016/ProcessObj1";
			ProcessObj1 PO1 = new ProcessObj1(name);
			System.out.println("ProcessObject1: binding it to name: " +
			name);
			Naming.rebind(name, PO1);
			System.out.println("Process Object 1 is Ready!");
			PO1Thread PO1t=new PO1Thread("PO1t");
			PO1t.start();
			
		}catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();

	}

}
}

class PO1Thread extends Thread {
	String name;
	 
	PO1Thread(String threadname) throws InterruptedException{
			this.name=threadname;	
		}
	
	public void run(){
		try{
			
			String yes="y";
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ready to connect to MO?(y/n)");
			String ans1=br.readLine();
			if(ans1.equalsIgnoreCase(yes)){
			
				String name1="//10.234.140.27:2016/MasterObj";
				masterObjInterface MO= (masterObjInterface) Naming.lookup(name1);
				
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
			
							System.out.println("Ready to start PO1?(y/n)");
							String ans5=br.readLine();	
							if(ans5.equalsIgnoreCase(yes)){
			
			
			for(int i=0;i<120;i++){
				if(i%ProcessObj1.probpo1t==0){//specify the time to send Master Object the clock value of Process Object 1
					
					/*Function to initiate after a unit of time sending of clock value to Master Object*/
					MO.correctorpo(1,ProcessObj1.counterpo1);
					ProcessObj1.counterpo1++;
				}
				
				else{
				
					if(ProcessObj1.flagpo1==true){//flag notifying Process Object 1 to adjust clock value as per offset
						
						 /*Function to adjust the clock value of Process Object 1 as per the offset*/
						ProcessObj1.counterpo1=ProcessObj1.counterpo1+ProcessObj1.offsetpo1+1;
						ProcessObj1.flagpo1=false;
				}
				
					else{
				    Random rn=new Random();
			        int j=rn.nextInt(100);
			     
			         if(j>=0&&j<=ProcessObj1.probpo1){//Probability to make Process Object 1 decide whether to send message to other Process Objects
			    	 Random rn1=new Random();
			    	 int k=rn1.nextInt(3);//Probability to make Process Object 1 decide which Process Object to send message to
			    	 
			    	 /*Function to send messages to other Process Objects and MAster Object */
			    	 if(k==0){
				    		PO2.receivepo2(ProcessObj1.counterpo1);
				    		MO.receivemo(1,ProcessObj1.counterpo1);
				    		ProcessObj1.counterpo1++;
				    		
				    	 }
				    	 else if(k==1){
				    		 PO3.receivepo3(ProcessObj1.counterpo1); 
				    		 MO.receivemo(1,ProcessObj1.counterpo1);
				    		 ProcessObj1.counterpo1++;
				    		
				    	 }
				    	 else if(k==2){
				    		 PO4.receivepo4(ProcessObj1.counterpo1);
				    		 MO.receivemo(1,ProcessObj1.counterpo1);
				    		 ProcessObj1.counterpo1++;
				    	}
			     }	
				} 
				}
				System.out.println("PO1 Counter: "+ProcessObj1.counterpo1); 
				Thread.sleep(1500);
			}
							}//ans5 if
						}//ans4 if
					}//ans3 if
				}//ans2 if
			}//ans1 if
			
		} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
