import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;
import java.rmi.*;
public class ProcessObj2 extends UnicastRemoteObject implements processObj2Interface{

String name;
	
    static boolean flagpo2start=false;
	static int probpo2t=15;//Process Object 1 t unit sending probability
	static int probpo2=99;//Process Object 2 message sending probability
	static int counterpo2=0;//Process Object 2 counter
	static int offsetpo2=0;//Process Object 2 offset
	static boolean flagpo2=false;//Process Object 2 flag indicator
	
	/*Constructor of Process Object 2 which takes in the parameter name*/
	ProcessObj2(String name) throws Exception{
		this.name=name;
	}
    
	 
	
	
	 /*Function to receive the clock values and adjust Process Object 2's clock accordingly if necessary*/	
	public void receivepo2(int c){
		 if(c<counterpo2){
			 counterpo2++;
			}
			else{
				counterpo2=c+1;
			}
		 
	 }
	
	
	
	 
	 /*Function to receive the offset from Master Object for Process Object 2*/
	public void receiveoffsetpo2(int o){
		 offsetpo2=o;
		 flagpo2=true;
		
	 }
	
	
	
	
	
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating Process Object 2!");
			String name = "//10.234.140.29:2016/ProcessObj2";
			ProcessObj2 PO2 = new ProcessObj2(name);
			System.out.println("Process Object 2: binding it to name: " +
			name);
			Naming.rebind(name, PO2);
			System.out.println("Process Object 2 is Ready!");
			
			PO2Thread PO2t=new PO2Thread("PO2t");
			PO2t.start();
			
		}catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}


class PO2Thread extends Thread {
	String name;
	 
	PO2Thread(String threadname) throws InterruptedException{
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
				
				System.out.println("Ready to connect to PO1?(y/n)");
				String ans2=br.readLine();	
				if(ans2.equalsIgnoreCase(yes)){
					
					String name2="//10.234.140.28:2016/ProcessObj1";
					processObj1Interface PO1= (processObj1Interface) Naming.lookup(name2);
					
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
			
							System.out.println("Ready to start PO2?(y/n)");
							String ans5=br.readLine();	
							if(ans5.equalsIgnoreCase(yes)){
			
			for(int i=0;i<120;i++){
				if(i%ProcessObj2.probpo2t==0){//specify the time to send Master Object the clock value of Process Object 1
					
					 /*Function to initiate after a unit of time sending of clock value to Master Object*/
					MO.correctorpo(2,ProcessObj2.counterpo2);
					ProcessObj2.counterpo2++;
				}	
			else{
				if(ProcessObj2.flagpo2==true){//flag notifying Process Object 2 to adjust clock value as per offset
					
					 /*Function to adjust the clock value of Process Object 2 as per the offset*/
					ProcessObj2.counterpo2=ProcessObj2.counterpo2+ProcessObj2.offsetpo2+1;
					ProcessObj2.flagpo2=false;
				
				}
				else{
				Random rn=new Random();
			     int j=rn.nextInt(100); 
				if(j>=0&&j<=ProcessObj2.probpo2){//Probability to make Process Object 2 decide whether to send message to other Process Objects
				    	 Random rn1=new Random();
				    	 int k=rn1.nextInt(3);//Probability to make Process Object 2 decide which Process Object to send message to
				    	 
				    	 /*Function to send messages to other Process Objects and Master Object */
				    	 if(k==0){
				    	 	   PO1.receivepo1(ProcessObj2.counterpo2);
				    	 		MO.receivemo(2,ProcessObj2.counterpo2);
				    	 		ProcessObj2.counterpo2++;
				    	 		
				    	 	 }
				    	 	 else if(k==1){
				    	 		 PO3.receivepo3(ProcessObj2.counterpo2); 
				    	 		 MO.receivemo(2,ProcessObj2.counterpo2);
				    	 		ProcessObj2.counterpo2++;
				    	 		
				    	 	 }
				    	 	 else if(k==2){
				    	 		 PO4.receivepo4(ProcessObj2.counterpo2);
				    	 		 MO.receivemo(2,ProcessObj2.counterpo2);
				    	 		ProcessObj2.counterpo2++;
				    	 		
				    	 	 }
				    	 
					}
				}
			}
				System.out.println("PO2 Counter: "+ProcessObj2.counterpo2);
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
		