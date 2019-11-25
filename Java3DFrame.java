package finalproject.Mia;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.vecmath.*;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class Java3DFrame extends JFrame{

	public static TransformGroup rotateThis = null;
	
	
	public Java3DFrame()
	{
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canCan = new Canvas3D(gc);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(canCan);
		
		
		this.getContentPane().add(new JButton("TITLE"), BorderLayout.SOUTH);
		
		SimpleUniverse su = new SimpleUniverse(canCan);
		BranchGroup bg = createBranchStuff();
		bg.compile();
		
		
		su.addBranchGraph(bg);
		
		su.getViewingPlatform().setNominalViewingTransform();
	}
	
	public BranchGroup createBranchStuff()
	{
		BranchGroup root = new BranchGroup();
		
		//lighting and stuff
		
		Light k = new PointLight(new Color3f(0.7f,0.7f,0.7f), new Point3f(0f,1f,1f), new Point3f(1,0,0));
		Bounds bod = new BoundingSphere(new Point3d(0, 0, 0), 2);
		k.setInfluencingBounds(bod);
		root.addChild(k);
		
		Background bac = new Background(1f, 1f, 1f);
		bac.setApplicationBounds(bod);
		root.addChild(bac);
		
		//
		Material m = new Material(new Color3f(1f,1f,1f), new Color3f(0.1f,0.1f,0.1f), new Color3f(1f,1f,1f), new Color3f(1f,1f,1f), 100f);
		
		Appearance a = new Appearance();
		PolygonAttributes pa = new PolygonAttributes();
		pa.setBackFaceNormalFlip(true);
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		pa.setPolygonMode( PolygonAttributes.POLYGON_FILL );
		a.setPolygonAttributes(pa);
		m.setLightingEnable(true);
		
		Texture tex = null;
		
		try {
			tex = new TextureLoader(ImageIO.read( new File("DiceTexture.png"))).getTexture();
			tex.setBoundaryModeS(Texture.WRAP);
			tex.setBoundaryModeT(Texture.WRAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextureAttributes texAttrib = new TextureAttributes();
		texAttrib.setTextureMode(TextureAttributes.MODULATE);
		if(tex!=null)
			a.setTexture(tex);
		
		a.setTextureAttributes(texAttrib);
		
		a.setMaterial(m);
		
		//left side
		
		TransformGroup tg = new TransformGroup();
		tg.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		rotateThis = tg;
		root.addChild(tg);
		
		GeometryArray myShape = MiaShapes.createBox();
		Geometry geom = myShape;
		
		Shape3D finalShape = new Shape3D(geom, a);
		tg.addChild(finalShape);
		
		return root;
	}
	
	/*
	public static void main(String[] args)
	{
		System.setProperty("sun.awt.noerasebackground", "true");
		
		JFrame f = new MainClass();
		f.setTitle("Mia 3D");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 640, 480);
		f.setVisible(true);
		
		float r = 0;
		while(f.isVisible())
		{
			Transform3D rotMat = new Transform3D();
			rotMat.rotY(r);
			
			Transform3D scaleMat = new Transform3D();
			scaleMat.setScale(0.3f);
			
			Transform3D finalMat = new Transform3D();
			finalMat.mul(scaleMat, rotMat);
			
			rotateThis.setTransform(finalMat);
			
			r+=0.01;
			try {
				Thread.currentThread().sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	*/
}
