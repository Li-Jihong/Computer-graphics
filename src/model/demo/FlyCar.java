package model.demo;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.applet.Applet;
import java.awt.*;
import java.net.URL;

public class FlyCar extends Applet {
    public FlyCar() {
        setLayout(new BorderLayout());
        Panel p = new Panel();

        // 创建一个新的 Font 对象
        Font font = new Font("Dialog", Font.ROMAN_BASELINE, 20);  // 可以选择您喜欢的字体样式和大小

        Label label = new Label("Designed By Jihong Li-20213002624");
        label.setFont(font);  // 将字体应用到 Label 上
        p.add(label);

        add(p, BorderLayout.NORTH);

        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(gc);
        add("Center", c);

        BranchGroup BranchGroupScene = createBranchGroupSceneGraph();
        SimpleUniverse u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(BranchGroupScene);
    }

    public static void main(String[] args) {
        new MainFrame(new FlyCar(), 800, 600);
    }

    public BranchGroup createBranchGroupSceneGraph() {
        BranchGroup BranchGroupRoot = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // 定义背景
        URL url = getClass().getClassLoader().getResource("model/demo/bg.png");
        TextureLoader textureLoader = new TextureLoader(url, this);
        ImageComponent2D image1 = textureLoader.getImage();

        if (image1 == null) {
            System.out.println("Image loading failed!");
        } else {
            System.out.println("Image loaded successfully!");
        }


        // 设置背景外观
        Background bg = new Background();
        bg.setImage(image1);
        bg.setImageScaleMode(Background.SCALE_FIT_MAX); // 调整图像以填充整个背景
        bg.setApplicationBounds(bounds);
        BranchGroupRoot.addChild(bg);

        Color3f directionalColor = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f vec = new Vector3f(0.f, 0.f, -1.0f);
        DirectionalLight directionalLight = new DirectionalLight(directionalColor, vec);
        directionalLight.setInfluencingBounds(bounds);
        BranchGroupRoot.addChild(directionalLight);


        // 鼠标缩放部分----------------------------------------------------------------------------------------------------
        Transform3D tr = new Transform3D();
        tr.setScale(0.65);
        //定义总的TransformGroup：transformgroup
        TransformGroup transformgroup = new TransformGroup(tr);

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

        //重新定义鼠标缩放功能默认滚轮
        MouseWheelZoom mouseWheelZoom = new MouseWheelZoom();
        mouseWheelZoom.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mouseWheelZoom);
        mouseWheelZoom.setSchedulingBounds(bounds);

        //鼠标平移功能默认鼠标右键
        MouseTranslate mousetranslate = new MouseTranslate();
        mousetranslate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mousetranslate);
        mousetranslate.setSchedulingBounds(bounds);
        // 鼠标缩放部分----------------------------------------------------------------------------------------------------

        //定义外观app1
        Appearance app1 = new Appearance();


        // 创建 ColoringAttributes 并设置颜色
        ColoringAttributes newColoringAttributes = new ColoringAttributes();
        newColoringAttributes.setColor(new Color3f(0.0f, 0.0f, 1.0f)); // 设置为蓝色

        // 创建 Material 并设置颜色
        Material newMaterial = new Material();
        newMaterial.setDiffuseColor(new Color3f(0.0f, 0.0f, 1.0f)); // 设置漫反射颜色，即蓝色

        // 应用新的 ColoringAttributes 和 Material 到外观 app1
        app1.setColoringAttributes(newColoringAttributes);
        app1.setMaterial(newMaterial);

        // 加载纹理图片
        TextureLoader loader = new TextureLoader("src/model/demo/xiaohui.png", 2, this);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        texture.setEnable(true);
//设置纹理的放大与缩小过滤
        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);


        app1.setTexture(texture);

        PolygonAttributes polygona1 = new PolygonAttributes();
        polygona1.setBackFaceNormalFlip(true);
        polygona1.setCullFace(PolygonAttributes.CULL_NONE);
        app1.setPolygonAttributes(polygona1);
        app1.setCapability(Appearance.ALLOW_TEXGEN_WRITE);

        //定义外观app2
        Appearance app2 = new Appearance();
        PolygonAttributes polygona2 = new PolygonAttributes();
        polygona2.setBackFaceNormalFlip(true);
        polygona2.setCullFace(PolygonAttributes.CULL_NONE);
        app2.setPolygonAttributes(polygona2);
        app2.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
// 加载纹理图片
        TextureLoader loader2 = new TextureLoader("src/model/demo/wheel.png", 2, this);
        ImageComponent2D image2 = loader2.getImage();
        Texture2D texture2 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                image2.getWidth(), image2.getHeight());
        texture2.setImage(0, image2);
        texture2.setEnable(true);
//设置纹理的放大与缩小过滤
        texture2.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        texture2.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        app2.setTexture(texture2);
        // 创建 ColoringAttributes 并设置颜色
        ColoringAttributes newColoringAttributes2 = new ColoringAttributes();
        newColoringAttributes2.setColor(new Color3f(0.0f, 1.0f, 0.0f)); // 设置为黑色

        // 创建 Material 并设置颜色
        Material newMaterial2 = new Material();
        newMaterial2.setDiffuseColor(new Color3f(0.0f, 1.0f, 0.0f)); // 设置漫反射颜色，即黑色

        // 应用新的 ColoringAttributes 和 Material 到外观 app1
        app2.setColoringAttributes(newColoringAttributes2);
        app2.setMaterial(newMaterial2);

        //定义外观app3
        Appearance app3 = new Appearance();
        PolygonAttributes polygona3 = new PolygonAttributes();
        polygona3.setBackFaceNormalFlip(true);
        polygona3.setCullFace(PolygonAttributes.CULL_NONE);
        app3.setPolygonAttributes(polygona3);
        app3.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
        // 加载纹理图片
        TextureLoader loader3 = new TextureLoader("src/model/demo/xiaohui.png", 2, this);
        ImageComponent2D image3 = loader3.getImage();
        Texture2D texture3 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                image3.getWidth(), image3.getHeight());
        texture3.setImage(0, image3);
        texture3.setEnable(true);
//设置纹理的放大与缩小过滤
        texture3.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        texture3.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        app3.setTexture(texture3);
        // 创建 ColoringAttributes 并设置颜色
        ColoringAttributes newColoringAttributes3 = new ColoringAttributes();
        newColoringAttributes3.setColor(new Color3f(1.0f, 0.0f, 0.0f)); // 设置为红色

        // 创建 Material 并设置颜色
        Material newMaterial3 = new Material();
        newMaterial3.setDiffuseColor(new Color3f(1.0f, 0.0f, 0.0f)); // 设置漫反射颜色，即红色

        // 应用新的 ColoringAttributes 和 Material 到外观 app1
        app3.setColoringAttributes(newColoringAttributes3);
        app3.setMaterial(newMaterial3);

        //定义控制顶点
        float[][][] back = {
                {{-0.3f, 0.f, 0.1f, 1.f},
                        {-0.2f, 0.f, 0.1f, 1.f},
                        {-0.1f, 0.f, 0.1f, 1.f},
                        {-0.f, -0.f, 0.1f, 1.f}},

                {{-0.3f, 0.f, 0.05f, 1.f},
                        {-0.2f, 0.f, 0.05f, 1.f},
                        {-0.1f, 0.f, 0.05f, 1.f},
                        {-0.f, -0.f, 0.05f, 1.f}},


                {{-0.3f, 0.f, -0.05f, 1.f},
                        {-0.2f, 0.f, -0.05f, 1.f},
                        {-0.1f, 0.f, -0.05f, 1.f},
                        {-0.f, -0.f, -0.05f, 1.f}},

                {{-0.3f, 0.f, -0.1f, 1.f},
                        {-0.2f, 0.f, -0.1f, 1.f},
                        {-0.1f, 0.f, -0.1f, 1.f},
                        {-0.f, -0.f, -0.1f, 1.f}}};


        float[][][] body = {
                {{-0.3f, 0.f, 0.1f, 1.f},
                        {-0.3f, 0.f, 0.05f, 1.f},
                        {-0.3f, 0.f, -0.05f, 1.f},
                        {-0.3f, -0.f, -0.1f, 1.f}},

                {{-0.2f, 0.2f, 0.1f, 1.f},
                        {-0.2f, 0.2f, 0.05f, 1.f},
                        {-0.2f, 0.2f, -0.05f, 1.f},
                        {-0.2f, 0.2f, -0.1f, 1.f}},


                {{-0.1f, 0.2f, 0.1f, 1.f},
                        {-0.1f, 0.2f, 0.05f, 1.f},
                        {-0.1f, 0.2f, -0.05f, 1.f},
                        {-0.1f, 0.2f, -0.1f, 1.f}},

                {{-0.f, 0.f, 0.1f, 1.f},
                        {-0.f, 0.f, 0.05f, 1.f},
                        {-0.f, 0.f, -0.05f, 1.f},
                        {-0.f, -0.f, -0.1f, 1.f}}};

        float[][][] ceshen1 = {
                {{-0.3f, 0.f, 0.1f, 1.f},
                        {-0.2f, 0.f, 0.1f, 1.f},
                        {-0.1f, 0.f, 0.1f, 1.f},
                        {-0.f, -0.f, 0.1f, 1.f}},


                {{-0.3f, 0.f, 0.13f, 1.f},
                        {-0.2f, 0.08f, 0.13f, 1.f},
                        {-0.1f, 0.08f, 0.13f, 1.f},
                        {0.f, 0.f, 0.13f, 1.f}},


                {{-0.3f, 0.f, 0.13f, 1.f},
                        {-0.2f, 0.15f, 0.13f, 1.f},
                        {-0.1f, 0.15f, 0.13f, 1.f},
                        {0.f, 0.f, 0.13f, 1.f}},

                {{-0.3f, 0.f, 0.1f, 1.f},
                        {-0.2f, 0.2f, 0.1f, 1.f},
                        {-0.1f, 0.2f, 0.1f, 1.f},
                        {0.f, 0.f, 0.1f, 1.f}},
        };

        float[][][] ceshen2 = {
                {{-0.3f, 0.f, -0.1f, 1.f},
                        {-0.2f, 0.f, -0.1f, 1.f},
                        {-0.1f, 0.f, -0.1f, 1.f},
                        {-0.f, -0.f, -0.1f, 1.f}},


                {{-0.3f, 0.f, -0.13f, 1.f},
                        {-0.2f, 0.08f, -0.13f, 1.f},
                        {-0.1f, 0.08f, -0.13f, 1.f},
                        {0.f, 0.f, -0.13f, 1.f}},


                {{-0.3f, 0.f, -0.13f, 1.f},
                        {-0.2f, 0.15f, -0.13f, 1.f},
                        {-0.1f, 0.15f, -0.13f, 1.f},
                        {0.f, 0.f, -0.13f, 1.f}},

                {{-0.3f, 0.f, -0.1f, 1.f},
                        {-0.2f, 0.2f, -0.1f, 1.f},
                        {-0.1f, 0.2f, -0.1f, 1.f},
                        {0.f, 0.f, -0.1f, 1.f}},
        };

        //螺旋桨1
        float[][][] P1 = {
                {{-0.16f, 0.15f, 0.03f, 1.f},
                        {-0.153f, 0.1523f, 0.03f, 1.f},
                        {-0.147f, 0.1547f, 0.03f, 1.f},
                        {-0.14f, 0.157f, 0.03f, 1.f}},

                {{-0.167f, 0.15f, 0.23f, 1.f},
                        {-0.152f, 0.1523f, 0.23f, 1.f},
                        {-0.148f, 0.1547f, 0.23f, 1.f},
                        {-0.133f, 0.157f, 0.23f, 1.f}},

                {{-0.173f, 0.15f, 0.43f, 1.f},
                        {-0.158f, 0.1523f, 0.43f, 1.f},
                        {-0.142f, 0.1547f, 0.43f, 1.f},
                        {-0.127f, 0.157f, 0.43f, 1.f}},

                {{-0.18f, 0.15f, 0.63f, 1.f},
                        {-0.163f, 0.1523f, 0.63f, 1.f},
                        {-0.14f, 0.1547f, 0.63f, 1.f},
                        {-0.12f, 0.157f, 0.63f, 1.f}}
        };


        //螺旋桨2
        float[][][] P2 = {
                {{-0.16f, 0.157f, -0.03f, 1.f},
                        {-0.153f, 0.1547f, -0.03f, 1.f},
                        {-0.147f, 0.1523f, -0.03f, 1.f},
                        {-0.14f, 0.15f, -0.03f, 1.f}},

                {{-0.167f, 0.157f, -0.23f, 1.f},
                        {-0.152f, 0.1547f, -0.23f, 1.f},
                        {-0.148f, 0.1523f, -0.23f, 1.f},
                        {-0.133f, 0.15f, -0.23f, 1.f}},

                {{-0.173f, 0.157f, -0.43f, 1.f},
                        {-0.158f, 0.1547f, -0.43f, 1.f},
                        {-0.142f, 0.1523f, -0.43f, 1.f},
                        {-0.127f, 0.15f, -0.43f, 1.f}},

                {{-0.18f, 0.157f, -0.63f, 1.f},
                        {-0.163f, 0.1547f, -0.63f, 1.f},
                        {-0.14f, 0.1523f, -0.63f, 1.f},
                        {-0.12f, 0.15f, -0.63f, 1.f}}
        };


        //螺旋桨3
        float[][][] P3 = {
                {
                        {-0.15f, 0.15f, 0.01f, 1.0f},
                        {-0.15f, 0.1523f, 0.003f, 1.0f},
                        {-0.15f, 0.1547f, -0.003f, 1.0f},
                        {-0.15f, 0.157f, -0.01f, 1.0f}
                },
                {
                        {0.19f, 0.15f, 0.016f, 1.0f},
                        {0.19f, 0.1523f, 0.016f, 1.0f},
                        {0.19f, 0.1547f, -0.016f, 1.0f},
                        {0.19f, 0.157f, -0.016f, 1.0f}
                },
                {
                        {0.39f, 0.15f, 0.023f, 1.0f},
                        {0.39f, 0.1523f, 0.023f, 1.0f},
                        {0.39f, 0.1547f, -0.023f, 1.0f},
                        {0.39f, 0.157f, -0.023f, 1.0f}
                },
                {
                        {0.45f, 0.15f, 0.03f, 1.0f},
                        {0.45f, 0.1523f, 0.01f, 1.0f},
                        {0.45f, 0.1547f, -0.01f, 1.0f},
                        {0.45f, 0.157f, -0.03f, 1.0f}
                }
        };


        //螺旋桨4
        float[][][] P4 = {
                {
                        {-0.15f, 0.157f, 0.01f, 1.0f},
                        {-0.15f, 0.1547f, 0.003f, 1.0f},
                        {-0.15f, 0.1523f, -0.003f, 1.0f},
                        {-0.15f, 0.15f, -0.01f, 1.0f}
                },
                {
                        {-0.35f, 0.157f, 0.016f, 1.0f},
                        {-0.35f, 0.1547f, 0.016f, 1.0f},
                        {-0.35f, 0.1523f, -0.016f, 1.0f},
                        {-0.35f, 0.15f, -0.016f, 1.0f}
                },
                {
                        {-0.55f, 0.157f, 0.023f, 1.0f},
                        {-0.55f, 0.1547f, 0.023f, 1.0f},
                        {-0.55f, 0.1523f, -0.023f, 1.0f},
                        {-0.55f, 0.15f, -0.023f, 1.0f}
                },
                {
                        {-0.75f, 0.157f, 0.03f, 1.0f},
                        {-0.75f, 0.1547f, 0.01f, 1.0f},
                        {-0.75f, 0.1523f, -0.01f, 1.0f},
                        {-0.75f, 0.15f, -0.03f, 1.0f}
                }
        };


        // 创建车底曲面
        Shape3D carTopPart = new BezierThreeOrderSurfaceface(back, app1);
        transformgroup.addChild(carTopPart);

        //创建车身曲面
        Shape3D carWheelPart = new BezierThreeOrderSurfaceface(body, app1);
        transformgroup.addChild(carWheelPart);

        //创建侧车身曲面1
        Shape3D carceshen1 = new BezierThreeOrderSurfaceface(ceshen1, app1);
        transformgroup.addChild(carceshen1);

        //创建侧车身曲面1
        Shape3D carceshen2 = new BezierThreeOrderSurfaceface(ceshen2, app1);
        transformgroup.addChild(carceshen2);

        Shape3D luo1 = new BezierThreeOrderSurfaceface(P1, app1);

        Shape3D luo2 = new BezierThreeOrderSurfaceface(P2, app1);

        Shape3D luo3 = new BezierThreeOrderSurfaceface(P3, app1);

        Shape3D luo4 = new BezierThreeOrderSurfaceface(P4, app1);

        //螺旋桨组
        TransformGroup tg = new TransformGroup();
        tg.addChild(luo1);
        tg.addChild(luo2);
        tg.addChild(luo3);
        tg.addChild(luo4);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformgroup.addChild(tg);

        //螺旋桨中心
        Transform3D t = new Transform3D();
        t.setTranslation(new Vector3f(-0.15f, 0.15f, 0.f));
        TransformGroup tg1 = new TransformGroup(t);
        tg1.addChild(new Cylinder(0.04f, 0.05f, app3));

        transformgroup.addChild(tg1);

        //四个轮子
        t = new Transform3D();
        t.rotX(Math.PI / 2);
        t.setTranslation(new Vector3f(-0.25f, 0.f, 0.1f));
        TransformGroup tg2 = new TransformGroup(t);
        tg2.addChild(new Cylinder(0.04f, 0.05f, app2));
        transformgroup.addChild(tg2);

        t = new Transform3D();
        t.rotX(Math.PI / 2);
        t.setTranslation(new Vector3f(-0.05f, 0.f, 0.1f));
        TransformGroup tg3 = new TransformGroup(t);
        tg3.addChild(new Cylinder(0.04f, 0.05f, app2));
        transformgroup.addChild(tg3);

        t = new Transform3D();
        t.rotX(Math.PI / 2);
        t.setTranslation(new Vector3f(-0.25f, 0.f, -0.1f));
        TransformGroup tg4 = new TransformGroup(t);
        tg4.addChild(new Cylinder(0.04f, 0.05f, app2));
        transformgroup.addChild(tg4);

        t = new Transform3D();
        t.rotX(Math.PI / 2);
        t.setTranslation(new Vector3f(-0.05f, 0.f, -0.1f));
        TransformGroup tg5 = new TransformGroup(t);
        tg5.addChild(new Cylinder(0.04f, 0.05f, app2));
        transformgroup.addChild(tg5);

        //螺旋桨旋转
        Alpha rotationAlpha1 = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 2000, 0, 0, 0, 0, 0);
        Transform3D zAxis1 = new Transform3D();
        zAxis1.setTranslation(new Vector3f(-0.15f, 0.15f, 0.f));
        RotationInterpolator rotator1 = new RotationInterpolator(rotationAlpha1, tg, zAxis1, 0.0f, (float) Math.PI * 2.0f);
        rotator1.setSchedulingBounds(bounds);
        transformgroup.addChild(rotator1);

        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }
}

class BezierThreeOrderSurfaceface extends Shape3D {
    public BezierThreeOrderSurfaceface(float[][][] P, Appearance app) {
        int i, j, k;
        int n0;//定义对参数u、v在[0，1]区间的等分点数
        float division;//参数u在[0，1]区间的等分线段长度
        n0 = 50;
        division = 1.f / n0;
//分别定义存放控制顶点x、y、z坐标与第四维坐标的数组
        float[][] PX = new float[4][4];
        float[][] PY = new float[4][4];
        float[][] PZ = new float[4][4];
        float[][] P4 = new float[4][4];
//定义系数矩阵及其转置矩阵
        float[][] M1 = {{1.f, 0.f, 0.f, 0.f},
                {-3.f, 3.f, 0.f, 0.f},
                {3.f, -6.f, 3.f, 0.f},
                {-1.f, 3.f, -3.f, 1.f}};
        float[][] M2 = {{1.f, -3.f, 3.f, -1.f},
                {0.f, 3.f, -6.f, 3.f},
                {0.f, 0.f, 3.f, -3.f},
                {0.f, 0.f, 0.f, 1.f}};
//定义Bezier曲面的u、v参数分割点坐标数组
        float[][][] UV = new float[n0 + 1][n0 + 1][2];
//定义U、V矩阵数组
        float[][] UU = new float[1][4];
        float[][] VV = new float[4][1];
//定义存放曲面上点的坐标的数组
        float[][][] SurfaceXYZ = new float[n0 + 1][n0 + 1][4];
        for (i = 0; i < n0 + 1; i++)
            for (j = 0; j < n0 + 1; j++) {
                UV[i][j][0] = i * division;
                UV[i][j][1] = j * division;
            }
        for (i = 0; i < 4; i++)
            for (j = 0; j < 4; j++) {
                PX[i][j] = P[i][j][0];
                PY[i][j] = P[i][j][1];
                PZ[i][j] = P[i][j][2];
                P4[i][j] = P[i][j][3];
            }
        //计算曲面上点的坐标值
        for (i = 0; i < n0 + 1; i++)
            for (j = 0; j < n0 + 1; j++) {
                UU[0][0] = 1.f;
                UU[0][1] = UV[i][j][0];
                UU[0][2] = UV[i][j][0] * UV[i][j][0];
                UU[0][3] = UV[i][j][0] * UV[i][j][0] * UV[i][j][0];
                VV[0][0] = 1.f;
                VV[1][0] = UV[i][j][1];
                VV[2][0] = UV[i][j][1] * UV[i][j][1];
                VV[3][0] = UV[i][j][1] * UV[i][j][1] * UV[i][j][1];
                //计算一点的x坐标
                matrixm g0 = new matrixm(1, 4, 4, UU, M1);
                matrixm g1 = new matrixm(1, 4, 4, g0.CC, PX);
                matrixm g2 = new matrixm(1, 4, 4, g1.CC, M2);
                matrixm g3 = new matrixm(1, 4, 1, g2.CC, VV);
                SurfaceXYZ[i][j][0] = g3.CC[0][0];
                //计算一点的y坐标
                matrixm g4 = new matrixm(1, 4, 4, UU, M1);
                matrixm g5 = new matrixm(1, 4, 4, g4.CC, PY);
                matrixm g6 = new matrixm(1, 4, 4, g5.CC, M2);
                matrixm g7 = new matrixm(1, 4, 1, g6.CC, VV);
                SurfaceXYZ[i][j][1] = g7.CC[0][0];
                //计算一点的z坐标
                matrixm g8 = new matrixm(1, 4, 4, UU, M1);
                matrixm g9 = new matrixm(1, 4, 4, g8.CC, PZ);
                matrixm g10 = new matrixm(1, 4, 4, g9.CC, M2);
                matrixm g11 = new matrixm(1, 4, 1, g10.CC, VV);
                SurfaceXYZ[i][j][2] = g11.CC[0][0];
                //计算一点的第4维坐标
                matrixm g12 = new matrixm(1, 4, 4, UU, M1);
                matrixm g13 = new matrixm(1, 4, 4, g12.CC, P4);
                matrixm g14 = new matrixm(1, 4, 4, g13.CC, M2);
                matrixm g15 = new matrixm(1, 4, 1, g14.CC, VV);
                SurfaceXYZ[i][j][3] = g15.CC[0][0];
                //将齐次坐标转换为三维坐标系坐标，如果第4维为1，则不用除第4维
                SurfaceXYZ[i][j][0] = SurfaceXYZ[i][j][0] / SurfaceXYZ[i][j][3];
                SurfaceXYZ[i][j][1] = SurfaceXYZ[i][j][1] / SurfaceXYZ[i][j][3];
                SurfaceXYZ[i][j][2] = SurfaceXYZ[i][j][2] / SurfaceXYZ[i][j][3];
            }
        QuadArray BezierQuadsurfaceface = new QuadArray(n0 * n0 * 4, GeometryArray.COORDINATES
                | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2);
        int c = 0;//以顶点数累加的方式设置顶点的序号
        for (i = 0; i < n0; i++) {
            for (j = 0; j < n0; j++) {//设置一个平面上的4个点
                Point3f A = new Point3f(SurfaceXYZ[i][j][0],
                        SurfaceXYZ[i][j][1], SurfaceXYZ[i][j][2]);
                Point3f B = new Point3f(SurfaceXYZ[i + 1][j][0],
                        SurfaceXYZ[i + 1][j][1], SurfaceXYZ[i + 1][j][2]);
                Point3f C = new Point3f(SurfaceXYZ[i + 1][j + 1][0],
                        SurfaceXYZ[i + 1][j + 1][1], SurfaceXYZ[i + 1][j + 1][2]);
                Point3f D = new Point3f(SurfaceXYZ[i][j + 1][0],
                        SurfaceXYZ[i][j + 1][1], SurfaceXYZ[i][j + 1][2]);
//计算由四个点形成的平面的法向量
                 Vector3f a = new Vector3f(A.x - B.x, A.y - B.y, A.z - B.z);
                Vector3f b = new Vector3f(C.x - B.x, C.y - B.y, C.z - B.z);
                Vector3f n = new Vector3f();
                n.cross(b, a);
                n.normalize();
//设置点的序号
                BezierQuadsurfaceface.setCoordinate(c, A);
                BezierQuadsurfaceface.setCoordinate(c + 1, B);
                BezierQuadsurfaceface.setCoordinate(c + 2, C);
                BezierQuadsurfaceface.setCoordinate(c + 3, D);
//设置点的法向量
                BezierQuadsurfaceface.setNormal(c, n);
                BezierQuadsurfaceface.setNormal(c + 1, n);
                BezierQuadsurfaceface.setNormal(c + 2, n);
                BezierQuadsurfaceface.setNormal(c + 3, n);
                //设置纹理坐标
                TexCoord2f texCoords = new TexCoord2f(i * 1.f / n0, 1.f - j * 1.f / n0);
                BezierQuadsurfaceface.setTextureCoordinate(0, c, texCoords);
                texCoords = new TexCoord2f((i + 1) * 1.f / n0, 1.f - j * 1.f / n0);
                BezierQuadsurfaceface.setTextureCoordinate(0, c + 1, texCoords);
                texCoords = new TexCoord2f((i + 1) * 1.f / n0, 1.f - (j + 1) * 1.f / n0);
                BezierQuadsurfaceface.setTextureCoordinate(0, c + 2, texCoords);
                texCoords = new TexCoord2f(i * 1.f / n0, 1.f - (j + 1) * 1.f / n0);
                BezierQuadsurfaceface.setTextureCoordinate(0, c + 3, texCoords);
                c = c + 4;
            }
        }
        this.addGeometry(BezierQuadsurfaceface);
        this.setAppearance(app);
    }
}

class matrixm {
    public float CC[][] = new float[4][4];
    int ll, mm, kk;

    public matrixm(int mmm, int kkk, int nnn, float a[][], float b[][]) {
        for (ll = 0; ll < mmm; ll++)
            for (mm = 0; mm < nnn; mm++) {
                CC[ll][mm] = 0.f;
            }
        for (ll = 0; ll < mmm; ll++)
            for (mm = 0; mm < nnn; mm++) {
                for (kk = 0; kk < kkk; kk++) CC[ll][mm] = CC[ll][mm] + a[ll][kk] * b[kk][mm];
            }
    }
}