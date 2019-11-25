package finalproject.Mia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.j3d.utils.universe.SimpleUniverse;
import finalproject.Mia.model.PlayerData;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends JFrame
{
    //variables involving data to send/receiving to/from server
    private int turn;
    private final int lives = 6;
    private int currentScore = 0;
    private int playerNumber;
    private String screen;
    private String jsonMessage = "";
    private ObjectMapper objectMapper = new ObjectMapper();
    private PlayerData playerData;

    //variables involving screen and rendering
    JPanel jPanel;

    public static void main(String[] args)
    {
        Client player1 = new Client();
        player1.setPlayerNumber(1);
        player1.setTurn(1);
        player1.loadTitleScreen();

        Client player2 = new Client();
        player2.setPlayerNumber(2);
        player2.setTurn(0);
        player2.loadTitleScreen();
    }

    public Client()
    {

    }

    public void connectToServer()
    {
        try
        {
            //get address of client machine
            InetAddress inetAddress = InetAddress.getLocalHost();
            String clientAddress = inetAddress.getHostAddress();
            System.out.println("Client Address: " + clientAddress);

            String server;
            server = "localhost";

            //create socket to connect to server address on port 4999
            Socket socket = new Socket(server, 4999);

            //open stream to write from file testInput1.txt (not included)
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            //send entire message to server and close stream
            System.out.println("\n\nSending to server:\t" + jsonMessage);
            printWriter.println(jsonMessage);

            // to read data from the server
            printWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            jsonMessage = bufferedReader.readLine();
            System.out.println("Receiving from server:\t" + jsonMessage);

            //close connection
            printWriter.close();
            bufferedReader.close();

            playerData = objectMapper.readValue(jsonMessage, PlayerData.class);
            System.out.println("New playerData = \t" + playerData.toString());

            //load screen
            loadScreen(playerData.getScreen());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void loadTitleScreen()
    {
        setTitle("Mia - " + getPlayerNumber());
        setScreen("title");
        jsonMessage = "{\"playerNumber\":\"" + getPlayerNumber() + "\", \"turn\":\"" + getTurn() +
                "\", \"lives\":\"" + getLives() + "\", \"currentScore\":\"" + getCurrentScore() +
                "\", \"screen\":\"" + getScreen() + "\"}";
        jPanel = new JPanel();
        initialize(jPanel);
        getContentPane().add(jPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void initialize(JPanel jPanel)
    {
        jPanel.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setSize(400, 400);
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        BranchGroup branchGroup = new BranchGroup();
        addTitleText(branchGroup);
        addLights(branchGroup);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(branchGroup);
        jPanel.add(canvas3D, BorderLayout.CENTER);

        JButton startBtn = new JButton("Start Game");
        startBtn.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent m)
            {
                connectToServer();
            }

        });
        jPanel.add(startBtn, BorderLayout.SOUTH);
    }

    public void loadScreen(String screen)
    {
        if(screen.equalsIgnoreCase("waiting"))
        {
            jPanel.setLayout(new BorderLayout());
            GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            Canvas3D canvas3D = new Canvas3D(config);
            canvas3D.setSize(400, 400);
            SimpleUniverse universe = new SimpleUniverse(canvas3D);
            BranchGroup branchGroup = new BranchGroup();
            addWaitingText(branchGroup);
            addLights(branchGroup);
            universe.getViewingPlatform().setNominalViewingTransform();
            universe.addBranchGraph(branchGroup);
            jPanel.add(canvas3D, BorderLayout.CENTER);
        }
        getContentPane().add(jPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void addLights(BranchGroup branchGroup) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                1000.0);
        Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color,
                light1Direction);
        light1.setInfluencingBounds(bounds);
        branchGroup.addChild(light1);
        Color3f ambientColor = new Color3f(.1f, .1f, .1f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        branchGroup.addChild(ambientLightNode);
        Background back = new Background(1, 1, 1);
        back.setApplicationBounds(bounds);
        branchGroup.addChild(back);
    }

    private void addTitleText(BranchGroup branchGroup) {
        Font3D font3D = new Font3D(new Font("Arial", Font.BOLD, 1),
                new FontExtrusion());

        Text3D firstName = new Text3D(font3D, "Welcome to", new Point3f(-2f, 2f, -4.8f));
        firstName.setString("Welcome to");

        Text3D lastName = new Text3D(font3D, "Mia", new Point3f(-.1f, -.3f, -4.8f));
        lastName.setString("Mia");

        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f blueish = new Color3f(.35f, 0.1f, 0.6f);
        Appearance appearance = new Appearance();
        Material material = new Material(blueish, blueish, blueish, white, 82.0f);
        material.setLightingEnable(true);
        appearance.setMaterial(material);

        Shape3D shape3D1 = new Shape3D();
        shape3D1.setGeometry(firstName);
        shape3D1.setAppearance(appearance);

        Shape3D shape3D2 = new Shape3D();
        shape3D2.setGeometry(lastName);
        shape3D2.setAppearance(appearance);

        TransformGroup transformGroup = new TransformGroup();
        Transform3D transform3D = new Transform3D();
        Vector3f v3f = new Vector3f(-1.0f, -1.0f, -4f);
        transform3D.setTranslation(v3f);
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(shape3D1);
        transformGroup.addChild(shape3D2);
        branchGroup.addChild(transformGroup);
    }

    private void addWaitingText(BranchGroup branchGroup) {
        Font3D font3D = new Font3D(new Font("Arial", Font.BOLD, 1),
                new FontExtrusion());

        Text3D firstName = new Text3D(font3D, "WAITING", new Point3f(-1.5f, 1f, -4.8f));
        firstName.setString("WAITING");

        Color3f red = new Color3f(1.0f, 0f, 0f);
        Color3f reddish = new Color3f(1.0f, 0.1f, 0.1f);
        Appearance appearance = new Appearance();
        Material material = new Material(reddish, reddish, reddish, red, 82.0f);
        material.setLightingEnable(true);
        appearance.setMaterial(material);

        Shape3D shape3D1 = new Shape3D();
        shape3D1.setGeometry(firstName);
        shape3D1.setAppearance(appearance);

        TransformGroup transformGroup = new TransformGroup();
        Transform3D transform3D = new Transform3D();
        Vector3f v3f = new Vector3f(-1.0f, -1.0f, -4f);
        transform3D.setTranslation(v3f);
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(shape3D1);
        branchGroup.addChild(transformGroup);
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getLives() {
        return lives;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }
}
