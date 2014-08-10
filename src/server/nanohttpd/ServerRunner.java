package server.nanohttpd;

import java.io.IOException;

public class ServerRunner
{

    public static void run(Class serverClass)
    {
        try
        {
            executeInstance((NanoHTTPD) serverClass.newInstance());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void executeInstance(NanoHTTPD server)
    {
        try
        {
            server.start();
        }
        catch (IOException ioe)
        {
            System.err.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }

        if (main.Main.DEBUG)
        {
            System.out.println("Server started, Hit Enter to stop.\n");

            try
            {
                System.in.read();
            }
            catch (IOException ignored)
            {
            }

            server.stop();
            System.out.println("Server stopped.\n");
        }

    }
}
