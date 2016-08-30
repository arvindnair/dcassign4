
public interface masterObjInterface extends java.rmi.Remote{

	void receivemo(int pid,int c)throws java.rmi.RemoteException;
	void correctorpo(int pid,int c)throws java.rmi.RemoteException;
}
