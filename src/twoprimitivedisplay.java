/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: 例3.1 基本体组合的显示实例
 * \* @FileName: twoprimitivedisplay
 * \* @author: li-jihong
 * \* Date: 2023-09-14 14:00
 */

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
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
import java.awt.event.MouseEvent;

public class twoprimitivedisplay extends Applet {   //第一次计算机图形学实验 2023-09-14晚上9-10节课
    public twoprimitivedisplay() {//设置显示界面的相关参数
        setLayout(new BorderLayout());//创建投影平面Canvas3D
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(gc);
        //将投影平面上的图象显示在显示平面的中间
        add("Center", c);
        //设置SimpleUniverse，由系统选择视点在z轴的正向，观察方向沿z轴反向
        BranchGroup BranchGroupScene = createBranchGroup();
        SimpleUniverse u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();//将BranchGroup：BranchGroupScene加入到SimpleUniverse：u中
        u.addBranchGraph(BranchGroupScene);
    }

    public static void main(String[] args) {//通过MainFrame显示图象
        new MainFrame(new twoprimitivedisplay(), 400, 400);
    }

    public BranchGroup createBranchGroup() {//定义BranchGroup
        BranchGroup BranchGroupRoot = new BranchGroup();

//        TransformGroup sceneTG=null;
//        BranchGroup scenceBranchGroupRoot =null;

        //创建球心在坐标系原点球形范围
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        //定义背景颜色
        Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        BranchGroupRoot.addChild(bg);
        //定义平行光、颜色、照射方向与作用范围
        Color3f directionalColor = new Color3f(1.f, 1.f, 1.f);
        Vector3f vec = new Vector3f(-1.f, -1.f, -1.0f);
        DirectionalLight directionalLight = new DirectionalLight(directionalColor, vec);
        directionalLight.setInfluencingBounds(bounds);
        BranchGroupRoot.addChild(directionalLight);
        //定义两个三维型体的外观


//        Appearance app1 = new Appearance();
//        Material material1 = new Material();
//        material1.setDiffuseColor(new Color3f(0.0f, .0f, 1.0f));
//        app1.setMaterial(material1);

        // 创建具有光滑表面属性的 Appearance
        Appearance app1 = new Appearance();

        // 设置高光反射属性
        Material material1 = new Material();
        material1.setDiffuseColor(new Color3f(0.0f, 0.0f, 1.0f)); // 设置漫反射颜色
        material1.setSpecularColor(new Color3f(1.0f, 1.0f, 1.0f)); // 设置高光反射颜色
        material1.setShininess(2500.0f); // 设置光滑度，值越高越光滑
        app1.setMaterial(material1);

//        Appearance app2 = new Appearance();
//        Material material2 = new Material();
//        material2.setDiffuseColor(new Color3f(.0f, 0.0f, 0.0f));
//        app2.setMaterial(material2);


        // 创建具有透明度的 Appearance
        Appearance app2 = new Appearance();
        TransparencyAttributes transparency = new TransparencyAttributes();
        transparency.setTransparencyMode(TransparencyAttributes.BLENDED);
        transparency.setTransparency(0.5f); // 设置透明度，范围从 0.0（完全不透明）到 1.0（完全透明）
        app2.setTransparencyAttributes(transparency);
        // 其他属性设置
        Material material2 = new Material();
        material2.setDiffuseColor(new Color3f(0.0f, 1.0f, 0.0f));
        app2.setMaterial(material2);


        //定义总的TransformGroup：transformgroup
        TransformGroup transformgroup = new TransformGroup();
        //设置对该TransformGroup的读写能力
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        //将该TransformGroup加入到BranchGroupRoot中
        BranchGroupRoot.addChild(transformgroup);
        //定义鼠标对场景的旋转、平移与放大功能

        //鼠标旋转功能
        MouseRotate mouserotate = new MouseRotate();
        mouserotate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mouserotate);
        mouserotate.setSchedulingBounds(bounds);


        //这是之前的不能进行缩放
        MouseZoom mousezoom = new MouseZoom();
        mousezoom.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mousezoom);
        mousezoom.setSchedulingBounds(bounds);

        //重新定义鼠标缩放功能 默认滚轮
        MouseWheelZoom mouseWheelZoom = new MouseWheelZoom();
        mouseWheelZoom.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mouseWheelZoom);
        mouseWheelZoom.setSchedulingBounds(bounds);

        //鼠标平移功能 默认鼠标右键
        MouseTranslate mousetranslate = new MouseTranslate();
        mousetranslate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mousetranslate);
        mousetranslate.setSchedulingBounds(bounds);


//        /*定义一个球体与一个长方体的大小、外观属性与坐标变换，并定义相应的TransformGroup：tg1、tg2*/
//        TransformGroup tg1 = new TransformGroup();
//        tg1.addChild(new Sphere(0.4f, app1));
//        Transform3D t = new Transform3D();
//        t.setTranslation(new Vector3f(0.f, -0.425f, 0.f));
//        TransformGroup tg2 = new TransformGroup(t);
//        tg2.addChild(new Box(0.5f, 0.005f, 0.5f, app2));
//
//        //将定义好的两个TransformGroup(tg1、tg2)加入到总的transformgroup
//        transformgroup.addChild(tg1);
//        transformgroup.addChild(tg2);
//
//        //对BranchGroupRoot预编译
//        BranchGroupRoot.compile();

        /*定义一个球体与一个长方体的大小、外观属性与坐标变换，并定义相应的TransformGroup：tg1、tg2*/
        TransformGroup tg1 = new TransformGroup();
        tg1.addChild(new Sphere(0.4f, app1));

        // 创建上方的长方体
        TransformGroup tg2Above = new TransformGroup();
        Transform3D tAbove = new Transform3D();
        tAbove.setTranslation(new Vector3f(0.f, 0.5f, 0.f)); // 将长方体放在球的上方
        TransformGroup tg2 = new TransformGroup(tAbove);
        tg2.addChild(new Box(0.5f, 0.005f, 0.5f, app2));

        // 创建下方的长方体
        TransformGroup tg2Below = new TransformGroup();
        Transform3D tBelow = new Transform3D();
        tBelow.setTranslation(new Vector3f(0.f, -0.5f, 0.f)); // 将长方体放在球的下方
        TransformGroup tg3 = new TransformGroup(tBelow);
        tg3.addChild(new Box(0.5f, 0.005f, 0.5f, app2));

        // 创建前方的长方体
        TransformGroup tg2Front = new TransformGroup();
        Transform3D tFront = new Transform3D();
        tFront.setTranslation(new Vector3f(0.f, 0.f, -0.5f)); // 将长方体放在球的前方
        TransformGroup tg4 = new TransformGroup(tFront);
        tg4.addChild(new Box(0.5f, 0.5f, 0.005f, app2));

        // 创建后方的长方体
        TransformGroup tg2Back = new TransformGroup();
        Transform3D tBack = new Transform3D();
        tBack.setTranslation(new Vector3f(0.f, 0.f, 0.5f)); // 将长方体放在球的后方
        TransformGroup tg5 = new TransformGroup(tBack);
        tg5.addChild(new Box(0.5f, 0.5f, 0.005f, app2));

        // 创建左边的长方体
        TransformGroup tg2Left = new TransformGroup();
        Transform3D tLeft = new Transform3D();
        tLeft.setTranslation(new Vector3f(-0.5f, 0.f, 0.f)); // 将长方体放在球的左边
        TransformGroup tg6 = new TransformGroup(tLeft);
        tg6.addChild(new Box(0.005f, 0.5f, 0.5f, app2));

        // 创建右边的长方体
        TransformGroup tg2Right = new TransformGroup();
        Transform3D tRight = new Transform3D();
        tRight.setTranslation(new Vector3f(0.5f, 0.f, 0.f)); // 将长方体放在球的右边
        TransformGroup tg7 = new TransformGroup(tRight);
        tg7.addChild(new Box(0.005f, 0.5f, 0.5f, app2));

        // 将定义好的所有TransformGroup加入到总的transformgroup
        transformgroup.addChild(tg1);
        transformgroup.addChild(tg2);
        transformgroup.addChild(tg3);
        transformgroup.addChild(tg4);
        transformgroup.addChild(tg5);
        transformgroup.addChild(tg6);
//        transformgroup.addChild(tg7);

        // 对BranchGroupRoot预编译
        BranchGroupRoot.compile();

        //通过方法名返回BranchGroupRoot
        return BranchGroupRoot;
    }
}