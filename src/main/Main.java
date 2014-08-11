/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import threads.ThreadCommand;
import server.WebServer;
import server.nanohttpd.ServerRunner;

/**
 *
 * @author kyle
 */
public class Main
{

    public static final boolean DEBUG = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ThreadCommand threadCommand = new ThreadCommand();
        threadCommand.start();
        // start up the web server
        ServerRunner.run(WebServer.class);
        // web server is down; make sure the command thread is down
        threadCommand.kill();
    }

}