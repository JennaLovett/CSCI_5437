package finalproject.Mia;

import java.net.*;
import java.io.*;

/**
 * Need to modify to send objects over stream
 * ex: https://stackoverflow.com/questions/27736175/how-to-send-receive-objects-using-sockets-in-java
 */
public class Server
{
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
            Socket socket = serverSocket.accept();
            System.out.println("\nReceived Connection...");

            //get data from client (in this example it's a text file)
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //write data from client to a file called testOutput.txt
            System.out.println("\nWriting to file:");
            String str = bufferedReader.readLine();
            BufferedWriter writer = new BufferedWriter(new FileWriter("testOutput.txt", true));
            while(str != null)
            {
                System.out.println(str);
                writer.append(str + "\n");
                str = bufferedReader.readLine();
            }

            //close writer
            writer.close();

            //close data stream
            inputStreamReader.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
