package finalproject.Mia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import finalproject.Mia.model.PlayerData;

import javax.imageio.ImageIO;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

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
    private JTextField textField;

    //variables involving screen and rendering
    private JPanel jPanel;

    private SimpleUniverse myUniverse;
    private BranchGroup myBranchGroup;
    private Canvas3D myCanvas;

    //timer variable
    private int timerCount = 0;

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

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //load screen
        if(playerData.getScreen().equalsIgnoreCase("waiting"))
        {
            loadScreen(playerData.getScreen());
            checkForScreenChange();
        }
        else {
            loadScreen(playerData.getScreen());
        }
    }

    /**
     * Used when player on waiting screen
     */
    private void checkForScreenChange()
    {
        String server;
        server = "localhost";

        Timer timer = new Timer();

        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    if(playerData.getScreen().equalsIgnoreCase("waiting"))
                    {
                        System.out.print("In timer for " + playerNumber + " timer = " + timerCount);
                        if(timerCount == 2)
                        {
                            PlayerData tempData = objectMapper.readValue(jsonMessage, PlayerData.class);
                            tempData.setFlag(0);
                            jsonMessage = tempData.toString();
                        }
                        //create socket to connect to server address on port 4999
                        Socket socket = new Socket(server, 4999);

                        //open stream to write to server
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

                        //open stream to read from server
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        //send entire message to server and close stream
                        System.out.println("\n\nSending to server:\t" + jsonMessage);
                        printWriter.println(jsonMessage);
                        printWriter.flush();

                        // to read data from the server
                        jsonMessage = bufferedReader.readLine();
                        System.out.println("Receiving from server:\t" + jsonMessage);

                        playerData = objectMapper.readValue(jsonMessage, PlayerData.class);
                        System.out.println("New playerData = \t" + playerData.toString());

                        //close connection
                        printWriter.close();
                        bufferedReader.close();
                        timerCount++;
                    }
                    else
                    {
                        PlayerData tempData = objectMapper.readValue(jsonMessage, PlayerData.class);
                        tempData.setFlag(0);
                        jsonMessage = tempData.toString();
                        playerData.setFlag(0);
                        loadScreen(playerData.getScreen());
                        timerCount = 0;
                        timer.cancel();
                        timer.purge();
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }, 0, 7000);
    }

    public void loadTitleScreen()
    {
        setTitle("Mia - " + getPlayerNumber());
        setScreen("title");
        jsonMessage = "{\"playerNumber\":\"" + getPlayerNumber() + "\", \"turn\":\"" + getTurn() +
                "\", \"lives\":\"" + getLives() + "\", \"currentScore\":\"" + getCurrentScore() +
                "\", \"screen\":\"" + getScreen() + "\", \"flag\":\"0\"}";
        jPanel = new JPanel();
        initialize(jPanel);
        getContentPane().add(jPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private void clearPanel()
    {
    	jPanel.removeAll();

    }

    public void initialize(JPanel jPanel)
    {
        jPanel.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        myCanvas = new Canvas3D(config);
        myCanvas.setSize(400, 400);
        myUniverse = new SimpleUniverse(myCanvas);

        myBranchGroup = createTitleScreenBranchGroup();

        myUniverse.getViewingPlatform().setNominalViewingTransform();
        myUniverse.addBranchGraph(myBranchGroup);
        jPanel.add(myCanvas, BorderLayout.CENTER);

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

    private BranchGroup createTitleScreenBranchGroup()
    {
    	BranchGroup branchGroup = new BranchGroup();
    	addTitleText(branchGroup);
    	addLights(branchGroup);
    	branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
    	return branchGroup;
    }

    private BranchGroup createWaitingScreenBranchGroup()
    {
    	BranchGroup branchGroup = new BranchGroup();
        addWaitingText(branchGroup);
        addLights(branchGroup);
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
    	return branchGroup;
    }

    private BranchGroup createPlayingScreenBranchGroup()
    {
    	BranchGroup branchGroup = createPlayScreen();
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        return branchGroup;
    }

    public void loadScreen(String screen)
    {
    	myBranchGroup.detach();
    	myBranchGroup.removeAllChildren();

        if(screen.equalsIgnoreCase("waiting"))
        {
        	clearPanel();
        	myBranchGroup = createWaitingScreenBranchGroup();
            myUniverse.addBranchGraph(myBranchGroup);
            jPanel.add(myCanvas, BorderLayout.CENTER);
        }
        else if(screen.equalsIgnoreCase("play"))
        {
        	clearPanel();
        	jPanel.setLayout(new BorderLayout());

        	myBranchGroup = createPlayingScreenBranchGroup();
            myUniverse.addBranchGraph(myBranchGroup);
            jPanel.add(myCanvas, BorderLayout.CENTER);

            textField = new JTextField(1);
            jPanel.add(textField, BorderLayout.NORTH);
            JButton passBtn = new JButton("Pass Dice");
            passBtn.setVisible(true);

            passBtn.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent m)
                {
                    connectToServer();
                }

            });
            jPanel.add(passBtn, BorderLayout.SOUTH);

        }

        this.revalidate();
        this.repaint();

        //this.repaint();
        //getContentPane().add(jPanel, BorderLayout.CENTER);
        //pack();
        //setVisible(true);
    }

    private BranchGroup createPlayScreen() {
        BranchGroup root = new BranchGroup();
        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);
        //object
        Appearance ap = new Appearance();
        ap.setMaterial(new Material());
        PolygonAttributes pa = new PolygonAttributes();
        pa.setBackFaceNormalFlip(true);
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        ap.setPolygonAttributes(pa);

        //load texture for cup
        Texture tex = null;
		try {
			tex = new TextureLoader(ImageIO.read( new File("/Users/jlovett/Desktop/CSCI5437/src/finalproject/Mia/CupTexture.png"))).getTexture();
			tex.setBoundaryModeS(Texture.WRAP);
			tex.setBoundaryModeT(Texture.WRAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextureAttributes texAttrib = new TextureAttributes();
		texAttrib.setTextureMode(TextureAttributes.MODULATE);
		if(tex!=null)
		{
			ap.setTexture(tex);
			ap.setTextureAttributes(texAttrib);
		}

        Shape3D shape = new Shape3D(MiaShapes.createCup(40,1,2), ap);
        //transformation
        Transform3D tr = new Transform3D();
        tr.setScale(0.4);
        TransformGroup tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(shape);
        Alpha alpha = new Alpha(-1, 10000);
        RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100);
        rotator.setSchedulingBounds(bounds);
        spin.addChild(rotator);
        //light
        PointLight light = new PointLight(new Color3f(Color.white),
                new Point3f(0.5f,0.5f,1f),
                new Point3f(1f,0.2f,0f));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        //background
        Background background = new Background(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(bounds);
        root.addChild(background);

        return root;
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
