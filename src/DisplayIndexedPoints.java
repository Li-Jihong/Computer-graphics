/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: 例3.3 IndexedPointArray类应用
 * \* @FileName: DisplayIndexedPoints
 * \* @author: li-jihong
 * \* Date: 2023-09-21 14:01
 */

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import java.applet.Applet;
import java.awt.*;

public class DisplayIndexedPoints extends Applet {
    public DisplayIndexedPoints() {
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
        new MainFrame(new DisplayIndexedPoints(), 300, 300);
    }

    public BranchGroup createBranchGroup() {
        BranchGroup BranchGroupRoot = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        BranchGroupRoot.addChild(bg);
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
        Shape3D shapepoints = new Shape3D();
        //定义顶点坐标
        float vertexes[] = {.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f,
                0.5f, 0.3f, 0.0f, -0.5f, 0.3f, 0.0f,
                -0.5f, -0.1f, 0.0f, 0.5f, -0.1f, 0.1f};
        //定义点颜色
        float pointcolors[] = {1.0f, 0.f, 0.0f, 0.0f, 1.f, 0.0f,
                0.0f, 0.f, 1.0f, 1.0f, 1.0f, 0.f,
                0.0f, 1.0f, 1.f, 1.f, 0.f, 1.0f};
        int vCount = 6;
        int indexCount = 3;
        int index[] = {0, 3, 5};
        IndexedPointArray points = new IndexedPointArray
                (vCount, PointArray.COORDINATES | PointArray.COLOR_3, indexCount);
        points.setCoordinates(0, vertexes);
        points.setCoordinateIndices(0, index);
        points.setColors(0, pointcolors);
        points.setColorIndices(0, index);
        Appearance app = new Appearance();
        PointAttributes pointsattributes = new PointAttributes();
        pointsattributes.setPointSize(70.0f);
        pointsattributes.setPointAntialiasingEnable(true);
        app.setPointAttributes(pointsattributes);
        shapepoints.setGeometry(points);
        shapepoints.setAppearance(app);
        transformgroup.addChild(shapepoints);
        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }
}