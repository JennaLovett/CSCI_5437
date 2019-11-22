package finalProject.Mia;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.QuadArray;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.GeometryInfo;

public class MiaShapes {

	public static GeometryArray createCup()
	{
		IndexedQuadArray shape = new IndexedQuadArray(8, GeometryArray.COORDINATES, 4*8);

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
