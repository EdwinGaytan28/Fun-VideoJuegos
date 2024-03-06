package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.Random;

public class Main extends SimpleApplication {
    public Node[] planetas_spatial = new Node[6];
    // Declarar una variable para almacenar el cubo adicional
    public Geometry cubo_geom;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Node sistema_solar = new Node("sistema_solar");
        Box b = new Box(1, 1, 1);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap",assetManager.loadTexture("Textures/pizzaplaneta.jpg"));
        
        Geometry sol_geom = new Geometry("sol_geom", b);
        sol_geom.setMaterial(mat);
        sistema_solar.attachChild(sol_geom);
        
        for(int i = 0; i < 6; i++) {
            Node planeta_node = new Node("planeta_node" + i);
            Geometry planeta_geom = new Geometry("planeta_geom" + i, b);
            planeta_geom.setMaterial(mat);
            planeta_geom.move(i*3, 0, 0);
            planeta_geom.scale(0.5f);
            planeta_node.attachChild(planeta_geom);
            sistema_solar.attachChild(planeta_node);
            planetas_spatial[i] = planeta_node;
            
        }
        
        // Crear un nuevo material para el cubo adicional
        Material mat_cubo = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        // Asignar un color aleatorio al cubo adicional
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float c = rand.nextFloat();
        mat_cubo.setColor("Color", new ColorRGBA(r, g, c, 1));
        
        // Crear el cubo adicional y asignarle el material
        cubo_geom = new Geometry("cubo_geom", b);
        cubo_geom.setMaterial(mat_cubo);
        // Posicionar el cubo adicional cerca del tercer planeta
        cubo_geom.move(9, 1, 0);
        // Escalar el cubo adicional a un tamaño menor
        cubo_geom.scale(0.2f);
        // Añadir el cubo adicional al nodo del tercer planeta
        planetas_spatial[2].attachChild(cubo_geom);
               
        rootNode.attachChild(sistema_solar);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(planetas_spatial[0] == null){
            for(int i = 0; i < 6; i++) {
                planetas_spatial[i] = (Node) rootNode.getChild("planeta_node" + i);
            }
        }
        
        for(int i = 0; i < 6; i++) {
            planetas_spatial[i].rotate(0, tpf/(i+1), 0);
            planetas_spatial[i].getChild("planeta_geom" + i).rotate(0, -tpf, 0);
        }
        
        // Hacer que el cubo adicional gire alrededor del tercer planeta
        cubo_geom.rotate(0, tpf, 0);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}