import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;
import java.rmi.*;
public class ProcessObj4 extends UnicastRemoteObject implements processObj4Interface{

String name;
	
	static int probpo4t=17;//Process Object 4 t unit sending probability
	static int probpo4=25;//Process Object 4 message sending probability
	static int counterpo4=0;//Process Object 4 counter
	static int offsetpo4=0;//Process Object 4 offset
	static boolean flagpo4=false;//Process Object 4 flag indicator
	
	/*Constructor of Process Object 4 which takes in the parameter name*/
	ProcessObj4(String name) throws Exception{
		this.name=name;
	}
    
	 
	
	
	 /*Function to receive the clock values and adjust Process Object 4's clock accordingly if necessary*/
	public void receivepo4(int c){
		 if(c<counterpo4){
			 counterpo4++;
			}
			else{
				counterpo4=c+1;
			}
		
	 } 
	
	 
	 /*Function to receive the offset from Master Object for Process Object 4*/
	public void receiveoffsetpo4(int o){
		 offsetpo4=o;
		 flagpo4=true;
		
	 }
	
	public static void main(String[] args) {
		try{
			System.out.println("Creating Process Object 4!");
			String name = "//10.234.140.31:2016/ProcessObj4";
			ProcessObj4 PO4 = new ProcessObj4(name);
			System.out.println("Process Object 4: binding it to name: " +
			name);
			Naming.rebind(name, PO4);
			System.out.println("Process Object 4 is Ready!");
			
			PO4Thread PO4t=new PO4Thread("PO4t");
			PO4t.start();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}


class PO4Thread extends Thread {
	String name;
	 
	PO4Thread(String threadname) throws InterruptedException{
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
						
						System.out.println("Ready to connect to PO3?(y/n)");
						String ans4=br.readLine();	
						if(ans4.equalsIgnoreCase(yes)){
						
							String name4="//10.234.140.30:2016/ProcessObj3";
							processObj3Interface PO3= (processObj3Interface) Naming.lookup(name4);
			
							System.out.println("Ready to start PO4?(y/n)");
							String ans5=br.readLine();	
							if(ans5.equalsIgnoreCase(yes)){
							
							
			for(int i=0;i<120;i++){
				if(i%ProcessObj4.probpo4t==0){//specify the time to send Master Object the clock value of Process Object 1
					
					/*Function to initiate after a unit of time sending of clock value to Master Object*/
					MO.correctorpo(4,ProcessObj4.counterpo4);
					ProcessObj4.counterpo4++;
				}
				else{
				if(ProcessObj4.flagpo4==true){//flag notifying Process Object 4 to adjust clock value as per offset
					
					 /*Function to adjust the clock value of Process Object 4 as per the offset*/
					ProcessObj4.counterpo4=ProcessObj4.counterpo4+ProcessObj4.offsetpo4+1;
					ProcessObj4.flagpo4=false;
				}
				
				else{
				Random rn=new Random();
			    int j=rn.nextInt(100);
				
			     if(j>=0&&j<=ProcessObj4.probpo4){//Probability to make Process Object 4 decide whether to send message to other Process Objects
				    	 Random rn1=new Random();
				    	 int k=rn1.nextInt(3);//Probability to make Process Object 4 decide which Process Object to send message to
				    	 
				    	 /*Function to send messages to other Process Objects and Master Object */
				    	 if(k==0){
				    	 		PO1.receivepo1(ProcessObj4.counterpo4);
				    	 		MO.receivemo(4,ProcessObj4.counterpo4);
				    	 		ProcessObj4.counterpo4++;
				    	 		
				    	 	 }
				    	 	 
				    			else if(k==1){
				    	 		 PO2.receivepo2(ProcessObj4.counterpo4); 
				    	 		 MO.receivemo(4,ProcessObj4.counterpo4);
				    	 		ProcessObj4.counterpo4++;
				    	 		 
				    	 	 }
				    	 	
				    			else if(k==2){
				    	 		 PO3.receivepo3(ProcessObj4.counterpo4);
				    	 		 MO.receivemo(4,ProcessObj4.counterpo4);
				    	 		ProcessObj4.counterpo4++;
				    	 		 
				    	 	 }
				    	}
				}
				}
				System.out.println("PO4 Counter: "+ProcessObj4.counterpo4);
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