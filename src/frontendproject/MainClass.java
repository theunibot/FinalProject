/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontendproject;
import nanohttpd.ServerRunner;

/**
 *
 * @author kyle
 */
public class MainClass 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ServerRunner.run(WebServer.class);
    }

}
