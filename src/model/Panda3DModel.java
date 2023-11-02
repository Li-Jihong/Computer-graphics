package model;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Panda3DModel {

    public Panda3DModel() {
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup scene = createSceneGraph();
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
    }

    private BranchGroup createSceneGraph() {
        // 创建总的分支组
        BranchGroup root = new BranchGroup();

        // 设置熊猫的外观
        Appearance pandaAppearance = new Appearance();
        // 这里你可以添加纹理和材料属性，比如颜色、光滑度等

        // 创建和定位熊猫的头部
        TransformGroup headTG = new TransformGroup();
        Sphere head = new Sphere(0.3f, Sphere.GENERATE_NORMALS, 100, pandaAppearance);
        Transform3D headT3d = new Transform3D();
        headT3d.setTranslation(new Vector3f(0.0f, 0.5f, 0.0f));
        headTG.setTransform(headT3d);
        headTG.addChild(head);

        // 创建和定位身体
        TransformGroup bodyTG = new TransformGroup();
        Sphere body = new Sphere(0.4f, Sphere.GENERATE_NORMALS, 100, pandaAppearance);
        Transform3D bodyT3d = new Transform3D();
        bodyT3d.setTranslation(new Vector3f(0.0f, 0.2f, 0.0f));
        bodyTG.setTransform(bodyT3d);
        bodyTG.addChild(body);

        // 添加身体其他部分，如四肢、耳朵、眼睛等

        // 将所有部分添加到根节点
        root.addChild(headTG);
        root.addChild(bodyTG);
        // ... 添加其他部分

        // 优化场景
        root.compile();

        return root;
    }

    public static void main(String[] args) {
        new Panda3DModel();
    }
}
