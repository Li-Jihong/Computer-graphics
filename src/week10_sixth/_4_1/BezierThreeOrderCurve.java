package week10_sixth._4_1;

/**
 * \* Created with IntelliJ IDEA.
 * \* @ProjectName: Computer graphics
 * \* @FileName: BezierThreeOrderCurve
 * \* @author: li-jihong
 * \* Date: 2023-11-02 10:50
 */

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

public class BezierThreeOrderCurve extends Applet {
    public BezierThreeOrderCurve() {
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
        new MainFrame(new BezierThreeOrderCurve(), 300, 300);
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
        Shape3D Bezier = new BezierThreeOrderCurve0();
        transformgroup.addChild(Bezier);
        BranchGroupRoot.compile();
        return BranchGroupRoot;
    }
}

class BezierThreeOrderCurve0 extends Shape3D {
    public BezierThreeOrderCurve0() {
        int i, j, k;
        float[] u = new float[100];
        //下面定义存放[1 u u2 u3]参数矩阵的数组
        float[][] UU = new float[1][4];
        //下面定义存放Bezier曲线上点的坐标的数组
        float[][] curvepoints = new float[100][4];
        //存放控制顶点坐标的数组
        float[][] P = {{-0.9f, -0.6f, 0.f, 1.f},
                {-0.2f, 0.7f, 0.f, 1.f},
                {0.2f, 0.9f, 0.f, 1.f},
                {0.9f, -0.7f, 0.f, 1.f}};
        //存放系数矩阵M的数组
        float[][] M = {{1.f, 0.f, 0.f, 0.f},
                {-3.f, 3.f, 0.f, 0.f},
                {3.f, -6.f, 3.f, 0.f},
                {-1.f, 3.f, -3.f, 1.f}};
        int n;//对参数u在[0，1]区间的等分点数
        float division;//参数u在[0，1]区间的等分线段长度
        n = 5;
//        n = 50;
        division = 1.f / n;
        for (i = 0; i < n + 1; i++) {
            u[i] = i * division;
        }
        for (i = 0; i < n + 1; i++) {
            UU[0][0] = 1.f;
            UU[0][1] = u[i];
            UU[0][2] = u[i] * u[i];
            UU[0][3] = u[i] * u[i] * u[i];
            matrixm g0 = new matrixm(1, 4, 4, UU, M);
            matrixm g1 = new matrixm(1, 4, 4, g0.CC, P);
            curvepoints[i][0] = g1.CC[0][0];
            curvepoints[i][1] = g1.CC[0][1];
            curvepoints[i][2] = g1.CC[0][2];
            curvepoints[i][3] = g1.CC[0][3];
            //在齐次坐标表示中，前三维的的坐标要除第四维的坐标，才能得到三维坐标系下的坐标值。
            //在该程序中，第四维的值为1,也可以不除
            curvepoints[i][0] = g1.CC[0][0] / curvepoints[i][3];
            curvepoints[i][1] = g1.CC[0][1] / curvepoints[i][3];
            curvepoints[i][2] = g1.CC[0][2] / curvepoints[i][3];
        }
        //定义曲线上点的数组
        Point3f[] curvepoints0 = new Point3f[100];
        for (i = 0; i < 100; i++) curvepoints0[i] = new Point3f();
        for (k = 0; k < n + 1; k++) { //将曲线上点的二维数组转换为Point3f类型的一维数组
            curvepoints0[k].x = curvepoints[k][0];
            curvepoints0[k].y = curvepoints[k][1];
            curvepoints0[k].z = curvepoints[k][2];
        }
        //将控制顶点的二维数组转换为Point3f类型的一维数组
        Point3f[] contralpoints = new Point3f[4];
        for (i = 0; i < 4; i++) contralpoints[i] = new Point3f();
        for (i = 0; i < 4; i++) {
            contralpoints[i].x = P[i][0];
            contralpoints[i].y = P[i][1];
            contralpoints[i].z = P[i][2];
        }
        int[] contralpointscount = new int[1];
        int[] curvepointscount = new int[1];
        contralpointscount[0] = 4;
        curvepointscount[0] = n + 1;
        LineStripArray contralpointslines = new
                LineStripArray(4, LineArray.COORDINATES, contralpointscount);
        contralpointslines.setCoordinates(0, contralpoints);
        LineStripArray curvepointslines = new
                LineStripArray(100, LineArray.COORDINATES, curvepointscount);
        curvepointslines.setCoordinates(0, curvepoints0);
        //设置线的属性
        LineAttributes lineattributes = new LineAttributes();
        lineattributes.setLineWidth(4.0f);
        lineattributes.setLineAntialiasingEnable(true);
        lineattributes.setLinePattern(0);
        Appearance app = new Appearance();
        app.setLineAttributes(lineattributes);
        //针对整个曲线定义颜色，而不是针对顶点定义颜色
        ColoringAttributes color = new ColoringAttributes();
        color.setColor(1.f, 0.f, 0.f);
        app.setColoringAttributes(color);
        this.addGeometry(curvepointslines);
        this.addGeometry(contralpointslines);
        this.setAppearance(app);
    }
}

//下面是实现两矩阵相乘的类
class matrixm {
    public float CC[][] = new float[4][4];
    int ll, mm, kk;

    public matrixm(int mmm, int kkk, int nnn, float a[][], float b[][]) {
        for (ll = 0; ll < mmm; ll++) {
            for (mm = 0; mm < nnn; mm++) {
                CC[ll][mm] = 0.f;
            }
        }
        for (ll = 0; ll < mmm; ll++) {
            for (mm = 0; mm < nnn; mm++) {
                for (kk = 0; kk < kkk; kk++)
                    CC[ll][mm] = CC[ll][mm] + a[ll][kk] * b[kk][mm];
            }
        }
    }
}