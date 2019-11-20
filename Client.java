package finalproject.Mia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            //get address of client machine
            InetAddress inetAddress = InetAddress.getLocalHost();
            String clientAddress = inetAddress.getHostAddress();
            System.out.println("Client Address: " + clientAddress);

            //get user input of whether to use localhost or remote server (really bad, i know)
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter \"localhost\" or \"server\": ");
            String address = scanner.next();
            String server;
            switch (address)
            {
                case "localhost":
                    server = "localhost";
                    System.out.println("\nUsing 127.0.0.1");
                    break;

                case "server":
                    server = "192.168.0.204";
                    System.out.println("\nUsing 192.168.0.204");
                    break;

                default:
                    server = "localhost";
                    break;
            }

            //create socket to connect to server address on port 4999
            Socket socket = new Socket(server, 4999);

            //open stream to write from file testInput1.txt (not included)
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new FileReader("testInput1.txt"));

            //read lines from file until empty
            String currentLine = reader.readLine();
            String message = "";
            while(currentLine != null)
            {
                message += currentLine + "\n";
                currentLine = reader.readLine();
            }

            //send entire message to server and close stream
            System.out.println("\n\nSending to server: \n" + message);
            printWriter.println(message);
            printWriter.flush();
            printWriter.close();
            //reader.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
