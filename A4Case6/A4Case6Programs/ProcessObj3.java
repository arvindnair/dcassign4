import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;
import java.rmi.*;
public class ProcessObj3 extends UnicastRemoteObject implements processObj3Interface{

String name;
	
    static boolean flagpo3start=false;
	static int probpo3t=26;//Process Object 3 t unit sending probability
	static int probpo3=99;//Process Object 3 message sending probability
	static int counterpo3=0;//Process Object 3 counter
	static int offsetpo3=0;//Process Object 3 offset
	static boolean flagpo3=false;//Process Object 3 flag indicator
	
	/*Constructor of Process Object 3 which takes in the parameter name*/
	ProcessObj3(String name) throws Exception{
		this.name=name;
	}
    
	 
	 /*Function to receive the clock values and adjust Process Object 3's clock accordingly if necessary*/
	public void receivepo3(int c){
		 if(c<counterpo3){
			 counterpo3++;
			}
			else{
				counterpo3=c+1;
			}
		 
	 }
	
	 
	 /*Function to receive the offset from Master Object for Process Object 3*/
	public void receiveoffsetpo3(int o){
		 offsetpo3=o;
		 flagpo3=true;
		
	 }
	
	
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating Process Object 3!");
			String name = "//10.234.140.30:2016/ProcessObj3";
			ProcessObj3 PO3 = new ProcessObj3(name);
			System.out.println("Process Object 3: binding it to name: " +
			name);
			Naming.rebind(name, PO3);
			System.out.println("Process Object 3 is Ready!");
			
			PO3Thread PO3t=new PO3Thread("PO3t");
			PO3t.start();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class PO3Thread extends Thread {
	String name;
	 
	PO3Thread(String threadname) throws InterruptedException{
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
					
					System.out.println("Ready to connect to PO2?(y/n)");
					String ans3=br.readLine();	
					if(ans3.equalsIgnoreCase(yes)){	
			
						String name3="//10.234.140.29:2016/ProcessObj2";
						processObj2Interface PO2= (processObj2Interface) Naming.lookup(name3);
						
						System.out.println("Ready to connect to PO4?(y/n)");
						String ans4=br.readLine();	
						if(ans4.equalsIgnoreCase(yes)){
						
							String name4="//10.234.140.31:2016/ProcessObj4";
							processObj4Interface PO4= (processObj4Interface) Naming.lookup(name4);
			
							System.out.println("Ready to start PO3?(y/n)");
							String ans5=br.readLine();	
							if(ans5.equalsIgnoreCase(yes)){
							
				for(int i=0;i<120;i++){
				if(i%ProcessObj3.probpo3t==0){//specify the time to send Master Object the clock value of Process Object 1
					
					/*Function to initiate after a unit of time sending of clock value to Master Object*/
					MO.correctorpo(3,ProcessObj3.counterpo3);
					ProcessObj3.counterpo3++;
				}
			else{
			if(ProcessObj3.flagpo3==true){//flag notifying Process Object 3 to adjust clock value as per offset
				
				/*Function to adjust the clock value of Process Object 3 as per the offset*/
				ProcessObj3.counterpo3=ProcessObj3.counterpo3+ProcessObj3.offsetpo3+1;
				ProcessObj3.flagpo3=false;
			}
			
			else{
			Random rn=new Random();
		     int j=rn.nextInt(100); 
			     if(j>=0&&j<=ProcessObj3.probpo3){//Probability to make Process Object 3 decide whether to send message to other Process Objects
			    	 Random rn1=new Random();
			    	 int k=rn1.nextInt(3);//Probability to make Process Object 3 decide which Process Object to send message to
			    	 
			    	 /*Function to send messages to other Process Objects and MAster Object */
			    	 if(k==0){
			    	 		PO1.receivepo1(ProcessObj3.counterpo3);
			    	 		MO.receivemo(3,ProcessObj3.counterpo3);
			    	 		ProcessObj3.counterpo3++;
			    	 		
			    	 	 }
			    	 	 else if(k==1){
			    	 		 PO2.receivepo2(ProcessObj3.counterpo3); 
			    	 		 MO.receivemo(3,ProcessObj3.counterpo3);
			    	 		ProcessObj3.counterpo3++;
			    	 		
			    	 	 }
			    	 	 else if(k==2){
			    	 		 PO4.receivepo4(ProcessObj3.counterpo3);
			    	 		 MO.receivemo(3,ProcessObj3.counterpo3);
			    	 		ProcessObj3.counterpo3++;
			    	  		
			    	 	 }
			    	
				}
			}
			}
				System.out.println("PO3 Counter: "+ProcessObj3.counterpo3);
			Thread.sleep(1500);
		}
							}//ans5 if
						}//ans4 if
					}//ans3 if
				}//ans2 if
			}//ans1 if
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}