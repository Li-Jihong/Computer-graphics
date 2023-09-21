/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: 例3.6 LineStripArray类程序实例
 * \* @FileName: DisplayLineStrip
 * \* @author: li-jihong
 * \* Date: 2023-09-21 14:12
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

public class DisplayLineStrip extends Applet {
    public DisplayLineStrip() {
        setLayout(new BorderLayout());
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(gc);
        add("Center", c);
        BranchGroup BranchGroupScene = createBranchGroupSceneGraph();
        SimpleUniverse u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(BranchGroupScene);
    }

    public static void main(String[] args) {
        new MainFrame(new DisplayLineStrip(), 300, 300);
    }

    public BranchGroup createBranchGroupSceneGraph() {
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
        transformgroup.addChild(Striplines());
        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }

    public Shape3D Striplines() {
        Shape3D Striplines0 = new Shape3D();
        float vertexes[] = {-0.8f, 0.5f, 0.0f, 0.8f, 0.9f, 0.0f,
                -0.8f, 0.2f, 0.0f, 0.8f, 0.7f, 0.0f,
                -0.8f, -0.2f, 0.0f, 0.8f, -0.5f, 0.0f,
                -0.8f, -0.5f, 0.0f, 0.8f, -.8f, 0.0f,};
        float colors[] = {1.0f, 0.f, .0f, 0.0f, 1.f, .0f,
                0.0f, 0.f, 1.f, 1.0f, 1.0f, 0.f,
                0.0f, 1.0f, 1.f, 1.f, 0.f, 1.0f,
                0.0f, .0f, 0.f, 0.3f, 0.8f, 0.0f,};
        int[] substrips = new int[2];
        substrips[0] = 4;
        substrips[1] = 4;
        //int[] substrips=new int[1];
        // substrips[0]=8;
        LineStripArray Striplines = new LineStripArray
                (8, LineArray.COORDINATES | LineArray.COLOR_3, substrips);
        Striplines.setCoordinates(0, vertexes);
        Striplines.setColors(0, colors);
        LineAttributes lineattributes = new LineAttributes();
        lineattributes.setLineWidth(9.0f);
        lineattributes.setLineAntialiasingEnable(true);
        lineattributes.setLinePattern(0);
        Appearance app = new Appearance();
        app.setLineAttributes(lineattributes);
        Striplines0.setGeometry(Striplines);
        Striplines0.setAppearance(app);
        return Striplines0;
    }
}
