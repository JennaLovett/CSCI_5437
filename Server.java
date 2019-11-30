package finalproject.Mia;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.Mia.model.PlayerData;

import java.net.*;
import java.util.*;

/**
 * Need to modify to send objects over stream
 * ex: https://stackoverflow.com/questions/27736175/how-to-send-receive-objects-using-sockets-in-java
 */
public class Server
{
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Map<Integer, PlayerData> playerDataList = new HashMap<>();

    public static void main(String[] args)
    {
        try
        {
            //get address of machine
            InetAddress inetAddress = InetAddress.getLocalHost();
            String serverAddress = inetAddress.getHostAddress();
            System.out.println("Server Address: " + serverAddress);

            String jsonMessage = "{\"playerNumber\":\"" + 1 + "\", \"turn\":\"" + 1 +
                    "\", \"lives\":\"" + 6 + "\", \"currentScore\":\"" + 0 +
                    "\", \"screen\":\"" + "title" + "\", \"flag\":\"0\"}";
            playerDataList.put(1,objectMapper.readValue(jsonMessage, PlayerData.class));

            jsonMessage = "{\"playerNumber\":\"" + 2 + "\", \"turn\":\"" + 0 +
                    "\", \"lives\":\"" + 6 + "\", \"currentScore\":\"" + 0 +
                    "\", \"screen\":\"" + "title" + "\"}";
            playerDataList.put(2,objectMapper.readValue(jsonMessage, PlayerData.class));

            //establish socket on port 4999
            //begin accepting connections
            ServerSocket serverSocket = new ServerSocket(4999);
            Socket socket = null;

            while (true)
            {
                socket = serverSocket.accept();

                // new thread for a player
                ConnectionThread connectionThread = new ConnectionThread(socket);
                connectionThread.start();
                connectionThread.join();
                playerDataList.put(connectionThread.getPlayerData().getPlayerNumber(), connectionThread.getPlayerData());
                System.out.println("Added playerData to list from thread\n");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static Map<Integer, PlayerData> getPlayerDataList() {
        return playerDataList;
    }
}
