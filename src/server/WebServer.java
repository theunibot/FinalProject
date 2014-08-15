/*
    This file is part of theunibot.

    theunibot is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    theunibot is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimeType;
import server.nanohttpd.NanoHTTPD;
import static server.nanohttpd.NanoHTTPD.MIME_HTML;
import static server.nanohttpd.NanoHTTPD.MIME_PLAINTEXT;

/**
 *
 */
public class WebServer extends NanoHTTPD
{

    private static final int PORT = 8080;

    private final String ROOT_DIR = "./webRootFolder";

    private final String API_SUBDIR = "/unibot";

    private Map<String, String> mimeTypes = new LinkedHashMap<String, String>();

    private final String[] MAIN =
    {
        "/", ""
    };
    private final String ENQUEUE = "/ENQUEUE";
    private final String STATUS = "/STATUS";
    private final String CLEAR_QUEUE = "/CLEAR-QUEUE";
    private final String GET_VAR = "/GET-VARIABLE";
    private final String SET_VAR = "/SET-VARIABLE";
    private final String INVENTORY = "/INVENTORY";

    private final String XSS_KEY = "Access-Control-Allow-Origin";
    private final String XSS_VALUE = "*";

    //serve pages
    private final String PAGE_TO_SERVE = ROOT_DIR + "/unibot.html";

    private ServerHooks sh = null;

    private String mimeType = MIME_PLAINTEXT;

    public WebServer()
    {
        super(PORT);
        sh = ServerHooks.getInstance();
        mimeTypes.put("js", "application/javascript");
        mimeTypes.put("html", MIME_HTML);
        mimeTypes.put("htm", MIME_HTML);
        mimeTypes.put("json", "application/json");
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session)
    {
        Response response = new Response("");
        response.addHeader(XSS_KEY, XSS_VALUE);

        NanoHTTPD.Method method = session.getMethod();
        String responseStr = "";
        if (method == NanoHTTPD.Method.GET)
        {
            Map<String, String> params = session.getParms();
            String uri = session.getUri();

            //=== PARSE URI===
            if (main.Main.DEBUG)
            {
                System.out.println("Unparsed: " + method + " '" + uri + "' ");
            }
            if (uri.endsWith("/"))//remove the last slash on a uri
            {
                uri = uri.substring(0, uri.length() - 1);
            }
            if (uri.startsWith(API_SUBDIR + "/"))
            {
                uri = uri.replaceFirst(API_SUBDIR, "");
            }
            if (main.Main.DEBUG)
            {
                System.out.println("Parsed:   " + method + " '" + uri + "' ");
            }
            //===END PARSE URI===

            if (uri.equals(MAIN[0]) || uri.equals(MAIN[1]))
            {
                try
                {
//                    return new NanoHTTPD.Response(NanoHTTPD.Response.Status.ACCEPTED, MIME_HTML, new FileInputStream(PAGE_TO_SERVE));//
                    response.setData(new FileInputStream(PAGE_TO_SERVE));
                    response.setMimeType(MIME_HTML);
                    response.setStatus(Response.Status.OK);
                    return response;
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
                    String fail = "{main file not found}";
                    response.setData(new ByteArrayInputStream(fail.getBytes()));
                    response.setMimeType(MIME_HTML);
                    response.setStatus(Response.Status.OK);
                    return response;
//                    return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, MIME_HTML, "Failed to load page.");
                }
            }
            else if (uri.startsWith(ENQUEUE))
            {
                responseStr = sh.enqueue(params);
            }
            else if (uri.startsWith(STATUS))
            {
                responseStr = sh.status(params);
            }
            else if (uri.startsWith(CLEAR_QUEUE))
            {
                responseStr = sh.clearQueue(params);
            }
            else if (uri.startsWith(GET_VAR))
            {
                responseStr = sh.getVar(params);
            }
            else if (uri.startsWith(SET_VAR))
            {
                responseStr = sh.setVar(params);
            }
            else if (uri.startsWith(INVENTORY)) 
            {
                responseStr = sh.inventory(params);
            }
            else//standard file
            {
                System.out.println("URI: " + uri);
                String[] pieces = uri.split("\\.");
                String extension = null;

                if (pieces.length != 0)
                {
                    extension = pieces[pieces.length - 1];
                }
                if (main.Main.DEBUG)
                {
                    System.out.print("Extension: " + extension + " ");
                }

                if (extension != null && mimeTypes.get(extension) != null)
                {
                    mimeType = mimeTypes.get(extension);
                }
                else
                {
                    mimeType = MIME_PLAINTEXT;
                }
                System.out.println("MimeType: " + mimeType);

                try
                {
                    response.setData(new FileInputStream(ROOT_DIR + uri));
                    response.setMimeType(mimeType);
                    response.setStatus(Response.Status.OK);
                    return response;
                }
                catch (FileNotFoundException ex)
                {
                    System.err.println("File not found: " + ROOT_DIR + uri);
                    String fail = "{\"file\":\"notFound\"}";
                    response.setData(new ByteArrayInputStream(fail.getBytes()));
                    response.setMimeType(mimeType);
                    response.setStatus(Response.Status.OK);
                    return response;
                }

//                String fail = "{}";
//                response.setData(new ByteArrayInputStream(fail.getBytes()));
//                response.setMimeType(mimeType);
//                response.setStatus(Response.Status.OK);
//                return response;
                //return a plaintext of any file
//                    return new NanoHTTPD.Response(NanoHTTPD.Response.Status.ACCEPTED, MIME_PLAINTEXT, new FileInputStream(ROOT_DIR + uri));
            }
            //Real response
            mimeType = mimeTypes.get("json");
            response.setData(new ByteArrayInputStream(responseStr.getBytes()));
            response.setMimeType(mimeType);
            response.setStatus(Response.Status.OK);
            return response;
            //return the response string
//            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.ACCEPTED, mimeType, responseStr);
        }
        else//not GET, 
        {
            System.err.println("NOT GET");
            mimeType = mimeTypes.get("json");
            String fail = "{}";
            response.setData(new ByteArrayInputStream(fail.getBytes()));
            response.setMimeType(mimeType);
            response.setStatus(Response.Status.OK);
            return response;
//            System.err.println("Not GET request");
//            return new NanoHTTPD.Response("Please use GET Requests.");
        }
    }

}
//else if (uri.startsWith(FAVICON))
//            {
//                System.out.println("Loading favicon: " + ROOT_DIR + "/favicon.ico");
//                try
//                {
//                    return new NanoHTTPD.Response(NanoHTTPD.Response.Status.ACCEPTED, "image/x-icon", new FileInputStream(ROOT_DIR + "/favicon.ico"));//
//                }
//                catch (FileNotFoundException ex)
//                {
//                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
