/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frontendtests;

import java.util.Map;
import nanohttpd.NanoHTTPD;

/**
 *
 * @author kyle
 */
public class ServerTests
{
    private NanoHTTPD.Response helloWorldServe(NanoHTTPD.IHTTPSession session)
    {
        NanoHTTPD.Method method = session.getMethod();
        String uri = session.getUri();
        System.out.println(method + " '" + uri + "' ");

        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null)
        {
            msg
                    += "<form action='?' method='get'>\n"
                    + "  <p>Your name: <input type='text' name='username'></p>\n"
                    + "</form>\n";
        }
        else
        {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }

        msg += "</body></html>\n";

        return new NanoHTTPD.Response(msg);
    }
}
