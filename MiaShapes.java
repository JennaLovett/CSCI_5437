package finalproject.Mia;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.QuadArray;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.GeometryInfo;

public class MiaShapes {

	@SuppressWarnings("deprecation")
	public static GeometryArray createCup(int steps, float radius, float height)
	{
		//centered at 0, 0, 0
		
		int maxQuads = steps*8;
		
		QuadArray shape = new QuadArray(maxQuads, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2 | GeometryArray.NORMALS);
		
		float texValInc = 1f/steps;
		float coordInc = (float) ((2f*Math.PI) / steps);
		
		for(int i=0, v=0; i<steps; i++, v+=4)
		{
			float x = (float) (Math.cos(coordInc * i) * radius);
			float y = (float) (height/2);
			float z = (float) (Math.sin(coordInc * i) * radius);
			
			float x2 = (float) (Math.cos(coordInc * (i+1)) * radius);
			float y2 = (float) -(height/2);
			float z2 = (float) (Math.sin(coordInc * (i+1)) * radius);
			
			shape.setCoordinate(v, new Point3f(x, y, z));
			shape.setCoordinate(v+1, new Point3f(x2, y, z2));
			shape.setCoordinate(v+2, new Point3f(x2, y2, z2));
			shape.setCoordinate(v+3, new Point3f(x, y2, z));
			
			Vector3f n1 = new Vector3f(x, 0, z);
			n1.normalize();
			
			Vector3f n2 = new Vector3f(x2, 0, z2);
			n2.normalize();
			
			shape.setNormal(v, n1);
			shape.setNormal(v+1, n2);
			shape.setNormal(v+2, n2);
			shape.setNormal(v+3, n1);
			
			shape.setTextureCoordinate(v, new Point2f(texValInc*i, 0));
			shape.setTextureCoordinate(v+1, new Point2f(texValInc*(i+1), 0));
			shape.setTextureCoordinate(v+2, new Point2f(texValInc*(i+1), 1));
			shape.setTextureCoordinate(v+3, new Point2f(texValInc*i, 1));
		}
		
		int offset = steps*4;
		//cover the bottom
		for(int i=0, v=0; i<steps; i++, v+=4)
		{
			float x = (float) (Math.cos(coordInc * i) * radius);
			float y = (float) -(height/2);
			float z = (float) (Math.sin(coordInc * i) * radius);
			
			float x2 = (float) (Math.cos(coordInc * (i+1)) * radius);
			float y2 = (float) -(height/2);
			float z2 = (float) (Math.sin(coordInc * (i+1)) * radius);
			
			float x3 = 0;
			float y3 = -(height/2);
			float z3 = 0;
			
			shape.setCoordinate(offset+v, new Point3f(x, y, z));
			shape.setCoordinate(offset+v+1, new Point3f(x2, y2, z2));
			shape.setCoordinate(offset+v+2, new Point3f(x3, y3, z3));
			shape.setCoordinate(offset+v+3, new Point3f(x3, y3, z3));
			
			Vector3f n1 = new Vector3f(0, -1, 0);
			n1.normalize();
			
			shape.setNormal(offset+v, n1);
			shape.setNormal(offset+v+1, n1);
			shape.setNormal(offset+v+2, n1);
			shape.setNormal(offset+v+3, n1);
			
			shape.setTextureCoordinate(offset+v, new Point2f(texValInc*i, 0));
			shape.setTextureCoordinate(offset+v+1, new Point2f(texValInc*(i+1), 0));
			shape.setTextureCoordinate(offset+v+2, new Point2f(0.5f, 0.5f));
			shape.setTextureCoordinate(offset+v+3, new Point2f(0.5f, 0.5f));
		}
		
		return shape;
	}
	
	@SuppressWarnings("deprecation")
	public static GeometryArray createBox()
	{
		QuadArray shape = new QuadArray(24, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2 | GeometryArray.NORMALS);

		//top
		shape.setCoordinate(0, new Point3f(1, 1, 1));
		shape.setCoordinate(1, new Point3f(1, 1, -1));
		shape.setCoordinate(2, new Point3f(-1, 1, -1));
		shape.setCoordinate(3, new Point3f(-1, 1, 1));
		
		shape.setTextureCoordinate(0, new Point2f(0f, 3f/3));
		shape.setTextureCoordinate(1, new Point2f(1f/3, 3f/3));
		shape.setTextureCoordinate(2, new Point2f(1f/3, 2f/3));
		shape.setTextureCoordinate(3, new Point2f(0f, 2f/3));
		
		shape.setNormal(0, new Vector3f(0, 1, 0));
		shape.setNormal(1, new Vector3f(0, 1, 0));
		shape.setNormal(2, new Vector3f(0, 1, 0));
		shape.setNormal(3, new Vector3f(0, 1, 0));
		
		//bottom
		shape.setCoordinate(4, new Point3f(1, -1, 1));
		shape.setCoordinate(5, new Point3f(-1, -1, 1));
		shape.setCoordinate(6, new Point3f(-1, -1, -1));
		shape.setCoordinate(7, new Point3f(1, -1, -1));
		
		shape.setTextureCoordinate(4, new Point2f(1f/3, 3f/3));
		shape.setTextureCoordinate(5, new Point2f(2f/3, 3f/3));
		shape.setTextureCoordinate(6, new Point2f(2f/3, 2f/3));
		shape.setTextureCoordinate(7, new Point2f(1f/3, 2f/3));
		
		shape.setNormal(4, new Vector3f(0, -1, 0));
		shape.setNormal(5, new Vector3f(0, -1, 0));
		shape.setNormal(6, new Vector3f(0, -1, 0));
		shape.setNormal(7, new Vector3f(0, -1, 0));
		
		//left
		shape.setCoordinate(8, new Point3f(-1, -1, 1));
		shape.setCoordinate(9, new Point3f(-1, 1, 1));
		shape.setCoordinate(10, new Point3f(-1, 1, -1));
		shape.setCoordinate(11, new Point3f(-1, -1, -1));
		
		shape.setTextureCoordinate(8, new Point2f(2f/3, 3f/3));
		shape.setTextureCoordinate(9, new Point2f(3f/3, 3f/3));
		shape.setTextureCoordinate(10, new Point2f(3f/3, 2f/3));
		shape.setTextureCoordinate(11, new Point2f(2f/3, 2f/3));
		
		shape.setNormal(8, new Vector3f(-1, 0, 0));
		shape.setNormal(9, new Vector3f(-1, 0, 0));
		shape.setNormal(10, new Vector3f(-1, 0, 0));
		shape.setNormal(11, new Vector3f(-1, 0, 0));
		
		//right
		shape.setCoordinate(12, new Point3f(1, 1, -1));
		shape.setCoordinate(13, new Point3f(1, 1, 1));
		shape.setCoordinate(14, new Point3f(1, -1, 1));
		shape.setCoordinate(15, new Point3f(1, -1, -1));
		
		shape.setTextureCoordinate(12, new Point2f(0f/3, 2f/3));
		shape.setTextureCoordinate(13, new Point2f(1f/3, 2f/3));
		shape.setTextureCoordinate(14, new Point2f(1f/3, 1f/3));
		shape.setTextureCoordinate(15, new Point2f(0f/3, 1f/3));
		
		shape.setNormal(12, new Vector3f(1, 0, 0));
		shape.setNormal(13, new Vector3f(1, 0, 0));
		shape.setNormal(14, new Vector3f(1, 0, 0));
		shape.setNormal(15, new Vector3f(1, 0, 0));
		
		//back
		shape.setCoordinate(16, new Point3f(-1, 1, -1));
		shape.setCoordinate(17, new Point3f(1, 1, -1));
		shape.setCoordinate(18, new Point3f(1, -1, -1));
		shape.setCoordinate(19, new Point3f(-1, -1, -1));
		
		shape.setTextureCoordinate(16, new Point2f(1f/3, 2f/3));
		shape.setTextureCoordinate(17, new Point2f(2f/3, 2f/3));
		shape.setTextureCoordinate(18, new Point2f(2f/3, 1f/3));
		shape.setTextureCoordinate(19, new Point2f(1f/3, 1f/3));
		
		shape.setNormal(16, new Vector3f(0, 0, -1));
		shape.setNormal(17, new Vector3f(0, 0, -1));
		shape.setNormal(18, new Vector3f(0, 0, -1));
		shape.setNormal(19, new Vector3f(0, 0, -1));
		
		//front
		shape.setCoordinate(20, new Point3f(-1, 1, 1));
		shape.setCoordinate(21, new Point3f(-1, -1, 1));
		shape.setCoordinate(22, new Point3f(1, -1, 1));
		shape.setCoordinate(23, new Point3f(1, 1, 1));
		
		shape.setTextureCoordinate(20, new Point2f(2f/3, 2f/3));
		shape.setTextureCoordinate(21, new Point2f(3f/3, 2f/3));
		shape.setTextureCoordinate(22, new Point2f(3f/3, 1f/3));
		shape.setTextureCoordinate(23, new Point2f(2f/3, 1f/3));
		
		shape.setNormal(20, new Vector3f(0, 0, 1));
		shape.setNormal(21, new Vector3f(0, 0, 1));
		shape.setNormal(22, new Vector3f(0, 0, 1));
		shape.setNormal(23, new Vector3f(0, 0, 1));
		
		return shape;
	}
}
