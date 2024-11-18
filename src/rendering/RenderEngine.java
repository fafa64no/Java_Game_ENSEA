package rendering;

import utils.Engine;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RenderEngine extends JFrame implements Engine {
    private final List<Displayable> displayableList_layer0;
    private final List<Displayable> displayableList_layer1;
    private final List<Displayable> displayableList_layer2;

    public RenderEngine(){
        super("TD4_Java_Proj");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        displayableList_layer0 = new ArrayList<>();
        displayableList_layer1 = new ArrayList<>();
        displayableList_layer2 = new ArrayList<>();
    }

    public void addToRenderList(Displayable displayable,int layer){
        switch (layer){
            case 0:
                displayableList_layer0.add(displayable);    break;
            case 1:
                displayableList_layer1.add(displayable);    break;
            case 2:
                displayableList_layer2.add(displayable);    break;
        }
    }

    public void removeFromRenderList(Displayable displayable){
        displayableList_layer0.remove(displayable);
        displayableList_layer1.remove(displayable);
        displayableList_layer2.remove(displayable);
        displayable.clear(this);
    }

    public void paint(){
        for (Displayable displayable : displayableList_layer0)displayable.draw(this); this.setVisible(true);
        for (Displayable displayable : displayableList_layer1)displayable.draw(this); this.setVisible(true);
        for (Displayable displayable : displayableList_layer2)displayable.draw(this); this.setVisible(true);
    }

    @Override
    public void update() {
        this.paint();
        SwingUtilities.updateComponentTreeUI(this);
    }
}
