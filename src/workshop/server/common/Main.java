package workshop.server.common;

public class Main {
    public static void main(String[] args) {
        IDataAccess dataAccess = DataAccess.getInstance();
        if (dataAccess.connect()) {
            ServerThread serverThread = new ServerThread();
            serverThread.start();
        } else {
            System.out.println("Can't connect to the DB");
        }
    } 
}
