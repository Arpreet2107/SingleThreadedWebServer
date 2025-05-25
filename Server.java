import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * A simple server that listens for incoming connections on port 8010.
 * It accepts connections, sends a greeting message to the client,
 * and reads a line of input from the client.
 */

public class Server{

    public void run(){
        int port = 8010;
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(10000);
            while(true){
                try {
                    System.out.println("Server is running on port " + port);
                    Socket acceptedConnection = socket.accept();
                    System.out.println("Accepted connection from " + acceptedConnection.getRemoteSocketAddress());
                    PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                    toClient.println("Hello from the server!");
                    //read a line from the client
                    try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()))) {
                        String clientMessage = fromClient.readLine();
                        System.out.println("Received from client: " + clientMessage);
                    }
                }catch (IOException ex) {
                    System.err.println("Error accepting connection: " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.err.println("Server failed to start: " + ex.getMessage());
        }
    }
    public static void main (String[] args){
        //IP ADDRESS: 192.168.31.89
        Server server = new Server();
        try{
            server.run();
        } catch (Exception ex) {
            System.err.println("An error occurred: " + ex.getMessage());
            // ex.printStackTrace();
        }
    }
}