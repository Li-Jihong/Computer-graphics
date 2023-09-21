/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: 例3.2 点的显示程序实例
 * \* @FileName: DisplayPoints
 * \* @author: li-jihong
 * \* Date: 2023-09-21 13:57
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

public class DisplayPoints extends Applet {
    public DisplayPoints() {
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
        new MainFrame(new DisplayPoints(), 300, 300);
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
        float vertexes[] = {.5f, 0.6f, 0.0f, -0.5f, 0.6f, 0.0f,
                0.5f, 0.05f, 0.0f, -0.5f, 0.05f, 0.f,
                -0.5f, -0.7f, 0.0f, 0.5f, -0.7f, 0.1f,};
        //定义点颜色
        float pointcolors[] = {1.0f, 0.f, 0.0f, 0.0f, 1.f, 0.0f,
                0.0f, 0.f, 1.0f, 1.0f, 1.0f, 0.f,
                0.0f, 1.0f, 1.f, 1.f, 0.f, 1.0f};
        int vCount = 6;
        PointArray points = new PointArray(vCount, PointArray.COORDINATES |
                PointArray.COLOR_3);
        points.setCoordinates(0, vertexes);
        points.setColors(0, pointcolors);
        Appearance app = new Appearance();
        //定义点的属性
        PointAttributes pointsattributes = new PointAttributes();
        //定义点的大小
        pointsattributes.setPointSize(70.0f);
        //如有下面这项，空间点显示为圆球形；如无，空间点显示为正方形
        pointsattributes.setPointAntialiasingEnable(true);
        app.setPointAttributes(pointsattributes);
        shapepoints.setGeometry(points);
        shapepoints.setAppearance(app);
        transformgroup.addChild(shapepoints);
        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }
}