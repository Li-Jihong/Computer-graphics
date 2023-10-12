package week7_fourth._3_9;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.applet.Applet;
import java.awt.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: 例3.9 TriangleFanArray类程序实例
 * \* @FileName: week7_fourth._3_9.TriangleFanArray
 * \* @author: li-jihong
 * \* Date: 2023-10-12 20:11
 */

public class TriangleFanArray2 extends Applet {
    public TriangleFanArray2() {
        setLayout(new BorderLayout());
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(gc);
        add("Center", c);
        BranchGroup BranchGroupScene = createBranchGroup();
        SimpleUniverse u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(BranchGroupScene);
    }

    public static void main(String[] args) {
        new MainFrame(new TriangleFanArray2(), 450, 450);
    }

    public BranchGroup createBranchGroup() {
        BranchGroup BranchGroupRoot = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        BranchGroupRoot.addChild(bg);
        Color3f directionalColor = new Color3f(1.f, 0.f, 0.f);
        Vector3f vec = new Vector3f(0.f, 0.f, -1.0f);
        DirectionalLight directionalLight = new DirectionalLight(directionalColor, vec);
        directionalLight.setInfluencingBounds(bounds);
        BranchGroupRoot.addChild(directionalLight);
        TransformGroup transformgroup = new TransformGroup();
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        BranchGroupRoot.addChild(transformgroup);
        MouseRotate mouserotate = new MouseRotate();
        mouserotate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mouserotate);
        mouserotate.setSchedulingBounds(bounds);
        MouseZoom mousezoom = new MouseZoom();
        mousezoom.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mousezoom);
        mousezoom.setSchedulingBounds(bounds);
        MouseTranslate mousetranslate = new MouseTranslate();
        mousetranslate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mousetranslate);
        mousetranslate.setSchedulingBounds(bounds);
        transformgroup.addChild(new ShapeTriangleFanArray());
        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }
}

class ShapeTriangleFanArray extends Shape3D {
    public ShapeTriangleFanArray() {
        int vertexesCount = 12;
//        int stripCount[] = new int[1];
//        int stripCount[]=new int[2];
        int stripCount[]=new int[3];
        float vertexes[] = {.0f, 0.9f, 0.0f, -1.f, -0.8f, 0.f,
                -0.8f, -0.6f, -0.2f, -0.6f, -0.9f, 0.2f,
                -0.4f, -0.8f, -0.2f, 0.f, -0.8f, 0.2f,
                0.2f, -0.5f, 0.0f, 0.4f, -0.6f, -0.5f,
                0.6f, -0.8f, -0.3f, 0.8f, -0.9f, -0.2f,
                0.9f, -0.7f, -0.2f, 1.1f, -0.8f, -0.3f};
        float colors[] = {0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 1.0f,
                0.0f, 0.8f, .0f, 1.0f, 0.0f, 0.3f,
                0.0f, 1.0f, 0.5f, 0.9f, 1.0f, 0.0f,
                0.5f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
                1.0f, 0.5f, 0.0f, 1.0f, 0.0f, 0.5f,
                1.0f, 0.8f, 0.0f, 1.0f, 0.5f, 0.0f};
        stripCount[0] = 4;
        stripCount[1] = 4;
        stripCount[2] = 4;
        TriangleFanArray triangleFanarray = new TriangleFanArray(vertexesCount,
                TriangleFanArray.COORDINATES | TriangleFanArray.COLOR_3, stripCount);
        triangleFanarray.setCoordinates(0, vertexes);
        triangleFanarray.setColors(0, colors);
        PolygonAttributes polygonattributes = new PolygonAttributes();
        polygonattributes.setCullFace(PolygonAttributes.CULL_NONE);

//        polygonattributes.setPolygonMode(PolygonAttributes.POLYGON_LINE); // 设置为线模型

        Appearance app = new Appearance();
        app.setPolygonAttributes(polygonattributes);
        this.setGeometry(triangleFanarray);
        this.setAppearance(app);
    }
}