/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import enums.DiskType;

/**
 *
 * @author kyle
 */
public class Shelf
{

    private DiskType disk;

    public Shelf()
    {
    }

    public Shelf(DiskType disk)
    {
        this.disk = disk;
    }

    public DiskType getDisk()
    {
        return disk;
    }

    public void setDisk(DiskType disk)
    {
        this.disk = disk;
    }

}