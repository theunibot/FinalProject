/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotoperations;

/**
 *
 * @author kyle
 */
/**
 * Wrapper object holding if successful and possible error message.
 */
public class ResponseObject
{

    private String msg;
    private boolean successful;

    public ResponseObject(String msg, boolean successful)
    {
        this.msg = msg;
        this.successful = successful;
    }

    public String getMsg()
    {
        return msg;
    }

    public boolean isSuccessful()
    {
        return successful;
    }

}