package week7_fourth._3_8;
/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: 例3.8 TriangleStripArray类程序实例
 * \* @FileName: week7_fourth._3_8.TriangleStripArrays
 * \* @author: li-jihong
 * \* Date: 2023-09-21 14:15
 */

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

public class TriangleStripArrays extends Applet {
    public TriangleStripArrays() {
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
        new MainFrame(new TriangleStripArrays(), 450, 450);
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
        transformgroup.addChild(new TriangleStrip());
        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }
}

class TriangleStrip extends Shape3D {
    public TriangleStrip() {
        int vertexesCount = 12;
        int stripCount[] = new int[1];
        float vertexes[] = {-0.9f, 0.9f, 0.0f, -0.8f, -0.9f, 0.2f,
                -0.6f, 0.8f, -0.2f, -0.4f, -0.8f, 0.2f,
                -0.3f, 0.9f, -0.2f, -0.2f, -0.9f, 0.2f,
                0.4f, 0.7f, 0.0f, 0.4f, -0.7f, 0.3f,
                0.6f, 0.9f, -0.3f, 0.6f, -.9f, 0.0f,
                0.9f, 0.8f, 0.2f, 0.8f, -0.8f, 0.3f};
        float colors[] = {0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 1.0f,
                0.0f, 0.8f, .0f, 1.0f, 0.0f, 0.3f,
                0.0f, 1.0f, 0.5f, 0.9f, 1.0f, 0.0f,
                0.5f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
                1.0f, 0.5f, 0.0f, 1.0f, 0.0f, 0.5f,
                1.0f, 0.8f, 0.0f, 1.0f, 0.5f, 0.0f};
        //生成三个子Strip，每个含有四个顶点
        stripCount[0] = 12;
//        stripCount[1] = 6;
//        stripCount[2] = 3;
        TriangleStripArray triangleStriparray = new TriangleStripArray(vertexesCount,
                TriangleStripArray.COORDINATES | TriangleStripArray.COLOR_3, stripCount);
        triangleStriparray.setCoordinates(0, vertexes);
        triangleStriparray.setColors(0, colors);
        PolygonAttributes polygonattributes = new PolygonAttributes();
        polygonattributes.setCullFace(PolygonAttributes.CULL_NONE);
        Appearance app = new Appearance();
        app.setPolygonAttributes(polygonattributes);
        this.setGeometry(triangleStriparray);
        this.setAppearance(app);
    }
}