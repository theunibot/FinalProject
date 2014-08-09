/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontendproject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimeType;
import nanohttpd.NanoHTTPD;
import static nanohttpd.NanoHTTPD.MIME_HTML;
import static nanohttpd.NanoHTTPD.MIME_PLAINTEXT;

/**
 *
 * @author kyle
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
    private final String CLEAR_QUEUE = "/CLEAR_QUEUE";
    private final String GET_VAR = "/GET-VARIABLE";
    private final String SET_VAR = "/SET-VARIABLE";

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
            System.out.println("Unparsed: " + method + " '" + uri + "' ");
            if (uri.endsWith("/"))//remove the last slash on a uri
            {
                uri = uri.substring(0, uri.length() - 1);
            }
            if (uri.startsWith(API_SUBDIR + "/"))
            {
                uri = uri.replaceFirst(API_SUBDIR, "");
            }
            System.out.println("Parsed:   " + method + " '" + uri + "' ");
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
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
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
            else//standard file
            {
                System.out.println("URI: " + uri);
                String[] pieces = uri.split("\\.");
                String extension = null;

                if (pieces.length != 0)
                {
                    extension = pieces[pieces.length - 1];
                }
                System.out.print("Extension: " + extension + " ");

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
                    String fail = "{file:notFound}";
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
