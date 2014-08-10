/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import threads.ThreadCommand;
import server.WebServer;
import nanohttpd.ServerRunner;

/**
 *
 * @author kyle
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
//        ThreadCommand threadCommand = new ThreadCommand();
//        threadCommand.start();
        ServerRunner.run(WebServer.class);
    }
    
}
