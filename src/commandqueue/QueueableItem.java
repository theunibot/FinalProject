/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import enums.EffectType;
import enums.ShelfUnit;

/**
 *
 * @author kyle
 */
public class QueueableItem
{

    private long id;
    private String content;

    private ShelfUnit desktop;
    private int desktopShelf;
    private int cachePointLayer;
    private EffectType effect;

    public QueueableItem(long id, String content)
    {
        this.id = id;
        this.content = content;
    }

    public QueueableItem(long id, ShelfUnit desktop, String content)
    {
        this.id = id;
        this.desktop = desktop;
        this.content = content;
    }

    public QueueableItem(long id, ShelfUnit desktop, int desktopShelf, String content)
    {
        this.id = id;
        this.desktop = desktop;
        this.desktopShelf = desktopShelf;
        this.content = content;
    }
    
    public QueueableItem(long id, ShelfUnit desktop, int desktopShelf, int cachePointLayer, EffectType effect)
    {
        this.id = id;
        this.content = content;
        this.desktop = desktop;
        this.desktopShelf = desktopShelf;
        this.cachePointLayer = cachePointLayer;
        this.effect = effect;
    }

    public long getId()
    {
        return id;
    }
    
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        
        result.append("Command (toString not implement)");
        
        return result.toString();
    }
    
    

}
