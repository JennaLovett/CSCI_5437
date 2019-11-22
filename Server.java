package finalproject.Mia;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;

/**
 * Need to modify to send objects over stream
 * ex: https://stackoverflow.com/questions/27736175/how-to-send-receive-objects-using-sockets-in-java
 */
public class Server
{
    private static ObjectMapper objectMapper;

    public static void main(String[] args)
    {
        try
        {
            //get address of machine
            InetAddress inetAddress = InetAddress.getLocalHost();
            String serverAddress = inetAddress.getHostAddress();
            System.out.println("Server Address: " + serverAddress);

            //establish socket on port 4999
            //begin accepting connections
            ServerSocket serverSocket = new ServerSocket(4999);
            Socket socket = null;

            while (true)
            {
                socket = serverSocket.accept();

                // new thread for a player
                new ConnectionThread(socket).start();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
