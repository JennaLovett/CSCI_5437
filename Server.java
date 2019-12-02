package finalproject.Mia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.Mia.model.PlayerData;

import java.net.*;
import java.util.*;

public class Server
{
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Map<Integer, PlayerData> playerDataList = new HashMap<>();

    public static void main(String[] args)
    {
        try
        {
            //establish socket on port 4999
            //begin accepting connections
            ServerSocket serverSocket = new ServerSocket(4999);
            Socket socket = null;

            initializeMap();

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

    private static void initializeMap()
    {
        String jsonMessage = "{\"playerNumber\":\"" + 1 + "\", \"turn\":\"" + 1 +
                "\", \"lives\":\"" + 6 + "\", \"currentScore\":\"" + 0 +
                "\", \"screen\":\"" + "title" + "\", \"flag\":\"0\"}";
        try
        {
            playerDataList.put(1,objectMapper.readValue(jsonMessage, PlayerData.class));
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        jsonMessage = "{\"playerNumber\":\"" + 2 + "\", \"turn\":\"" + 0 +
                "\", \"lives\":\"" + 6 + "\", \"currentScore\":\"" + 0 +
                "\", \"screen\":\"" + "title" + "\"}";
        try
        {
            playerDataList.put(2,objectMapper.readValue(jsonMessage, PlayerData.class));
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

}
