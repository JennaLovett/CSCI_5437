package finalproject.Mia;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.Mia.model.PlayerData;

import java.io.*;
import java.net.Socket;

public class ConnectionThread extends Thread
{
    protected Socket socket;

    public ConnectionThread(Socket clientSocket)
    {
        this.socket = clientSocket;
    }

    public void run()
    {
        try
        {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = bufferedReader.readLine();
            String json = str;
            ObjectMapper objectMapper = new ObjectMapper();
            PlayerData playerData = objectMapper.readValue(json, PlayerData.class);
            System.out.println(playerData.toString());
        }
        catch(IOException e)
        {

        }
    }
}
