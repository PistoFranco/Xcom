package me.pistofranco;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class RoundManager {
    List<Entity> habilities = new ArrayList<>();
    public Entity getHabilities(Entity entity){
        if(habilities.contains(entity)){
            return entity;
        }else return null;
    }
    public void setHabilities(Entity entity){
        habilities.add(entity);
    }
}
