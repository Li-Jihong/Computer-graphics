package week8_fifth._3_16;

/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: Computer graphics
 * \* @FileName: TransparencyAttributess
 * \* @author: li-jihong
 * \* Date: 2023-11-02 13:13
 */

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

public class TransparencyAttributess extends Applet {
    public TransparencyAttributess() {
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
        new MainFrame(new TransparencyAttributess(), 300, 300);
    }

    public BranchGroup createBranchGroupSceneGraph() {
        BranchGroup BrachGroupRoot = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        BrachGroupRoot.addChild(bg);
        Color3f directionalColor = new Color3f(1.f, 1.f, 1.f);
        Vector3f vec = new Vector3f(-1.f, -1.f, -1.0f);
        DirectionalLight directionalLight = new DirectionalLight(directionalColor, vec);
        directionalLight.setInfluencingBounds(bounds);
        BrachGroupRoot.addChild(directionalLight);
        Appearance app1 = new Appearance();
        Material material1 = new Material();
        material1.setDiffuseColor(new Color3f(1.0f, .0f, 0.0f));
        app1.setMaterial(material1);
//定义球体的透明度，透明模式选NICEST=1，透明度0.6f
        TransparencyAttributes transparence = new TransparencyAttributes(1, .6f);
        app1.setTransparencyAttributes(transparence);
        Appearance app2 = new Appearance();
        Material material2 = new Material();
        material2.setDiffuseColor(new Color3f(.0f, 1.0f, 0.0f));
        app2.setMaterial(material2);
        TransparencyAttributes transparence1 = new TransparencyAttributes(1, .8f);
        app2.setTransparencyAttributes(transparence1);
        Appearance app3 = new Appearance();
        Material material3 = new Material();
        material3.setDiffuseColor(new Color3f(.0f, .0f, 1.0f));
        app3.setMaterial(material3);
        app3.setTransparencyAttributes(transparence1);
        Appearance app4 = new Appearance();
        Material material4 = new Material();
        material4.setDiffuseColor(new Color3f(.0f, 1.0f, 1.0f));
        app4.setMaterial(material2);
        app4.setTransparencyAttributes(transparence1);
        TransformGroup transformgroup = new TransformGroup();
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        BrachGroupRoot.addChild(transformgroup);
        MouseRotate mouserotate = new MouseRotate();
        mouserotate.setTransformGroup(transformgroup);
        BrachGroupRoot.addChild(mouserotate);
        mouserotate.setSchedulingBounds(bounds);
        MouseZoom mousezoom = new MouseZoom();
        mousezoom.setTransformGroup(transformgroup);
        BrachGroupRoot.addChild(mousezoom);
        mousezoom.setSchedulingBounds(bounds);
        MouseTranslate mousetranslate = new MouseTranslate();
        mousetranslate.setTransformGroup(transformgroup);
        BrachGroupRoot.addChild(mousetranslate);
        mousetranslate.setSchedulingBounds(bounds);
        TransformGroup tg1 = new TransformGroup();
        tg1.addChild(new Sphere(.9f, 1, 100, app1));
        Transform3D t = new Transform3D();
        t.setTranslation(new Vector3f(-0.2f, 0.1f, 0.2f));
        TransformGroup tg2 = new TransformGroup(t);
        tg2.addChild(new Box(0.2f, 0.2f, 0.2f, app2));
        Transform3D t1 = new Transform3D();
        t1.setTranslation(new Vector3f(-0.2f, 0.1f, 0.2f));
        TransformGroup tg3 = new TransformGroup(t1);
        tg3.addChild(new Sphere(.6f, 1, 100, app3));
        Transform3D t2 = new Transform3D();
        t2.setTranslation(new Vector3f(0.4f, 0.2f, -0.4f));
        TransformGroup tg4 = new TransformGroup(t2);
        tg4.addChild(new Sphere(.3f, 1, 100, app4));
        transformgroup.addChild(tg1);
        transformgroup.addChild(tg2);
        transformgroup.addChild(tg3);
        transformgroup.addChild(tg4);
        BrachGroupRoot.compile();
        return BrachGroupRoot;
    }
}