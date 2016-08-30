Steps to run the Assignment Program:
1.Extract the contents of NairA4.zip onto a location using Winrar or 7zip. 
2.Go into the extracted folder using cd NairA4 command on pegasus. 
3.There are 7 cases to run in each separate folder.
4.On pegasus, using cd A4Case1 to go into the directory of Case 1.
5. Again type cd A4Case1Programs and go into that directory
6. Giving permissions:
6.1 Type chmod +x *.sh to give permission to all makefiles.
6.2 Type chmod +x *.java to give permission to all .java files.
6.3 Type chmod +x *.class to give permission to all .class files.
6.4 Type chmod +x policy to give permission to policy file.
7. Change the CodeBase in the policy file to the appropriate path in your directory. The current path is 
/home/arvnair/src1/
8. Log in to machine 10.234.140.27 and go into folder A4Case1Programs and type ./rmiregistry.sh to run the registry on machine this machine.
9. Do step 6 for machines 28, 29, 30 and 31. The rmiregistry will be running on all those machines.
10. Open another instance of machines 27, 28, 29, 30 and 31 and go into A4Case1Programs folder using cd as mentioned in steps 2, 4 and 5
11. On machine 27, type ./mo.sh to run Master Object on machine 27.
12. On machine 28, type ./po1.sh to run Process Object 1 on machine 28.
13. On machine 29, type ./po2.sh to run Process Object 2 on machine 29.
14. On machine 30, type ./po3.sh to run Process Object 3 on machine 30.
15. On machine 31, type ./po4.sh to run Process Object 4 on machine 31.
16. Run the programs as per the instructions.
17. Do for all cases.

For interchanging machines and different port numbers:
1. If you want to run the MO and POs on a different hosts, make that change in MasterObj.java, ProcessObj1.java, ProcessObj2.java, ProcessObj3.java and ProcessObj4.java.
2. The corresponding line in files will be:
(eg: for MO in MasterObj.java)
String name ="//10.234.140.27:2016/MasterObj"
3. If you wish to run on a different port eg:on 2222  and machine 28 then change 
String name ="//10.234.140.28:2222/MasterObj"
5. You need to make changes in the ProcessObj1,...,ProcessObj4.java files as they access the MO.(Corresponding line variable will be as name1)
6. Also in the rmiregistry.sh file change the second line
rmiregistry 2222 from the corresponding line rmiregistry 2016
7. I have configured the registry to run on 2016 port and the MO and PO1, PO2, PO3 and PO4 to run on machines 27, 28, 29, 30 and 31 respectively.

Note:
1.Always ensure that the registry is running on all servers and then start the MO and POs.Each of the POs and MO require a registry on their machine running as all of them act as servers in my design.
2.Type y for all questions asked on console.
3.To connect to each other ensure that all are up and running.eg: when MO asks to connect to PO1 ensure that PO1 is running and asking to connect to MO.So type y in MO and then y in PO1. Do like that for all.
4. Then ensure all the consoles show ready to start MO?, ready to start PO1?,....ready to start PO4?
5. Type y and press enter for all consoles simultaneously or one after another.
6. Then the counters start individually.
7. Run the registry and the object on different instances of the machine.(eg: run registry on machine 27 and open another window of machine 27 and run the Master object there. This is done so that in the end we after the counters end we can stop the execution on all windows/instances by pressing control key and letter C together(Ctrl+C).
8. The rmiregistry.sh file has the command javac *.java for compiling all the java files.
9. You can also try as localhost on each machine and on pegasus.

Contents of NairA4 folder after extraction:
1. It contains the folders for cases from 1 to 7(A4Case1,....,A4Case7), the assignment 4 report in docx and pdf formats and this readme.txt file.

Contents of the Case folders:
1.They contain the Case programs folder(A4Case1Programs,....,A4Case7Programs) in which the programs and policy file is present for the cases are there, the pdf of the output graph showing the counter values(A4Case1Graph.pdf,...,A4Case7Graph.pdf), the case outputs in a txt file(A4Case1outputs.txt,....,A4Case7outputs.txt) and two images for screenshots(A4Case1Part1Screenshot.png, A4Case1Part2Screenshot.png,....,A4Case7Part1Screenshot.png, A4Case7Part2Screenshot.png).
 