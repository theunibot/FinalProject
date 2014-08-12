/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import enums.CommandCompletion;

/**
 *
 * @author cmidgley
 */
public class Result {
    public CommandCompletion completion;
    public String errorMessage = "";
    
    public Result() {
        this.completion = CommandCompletion.COMPLETE;
        this.errorMessage = "";
    }
    
    public Result(String error) {
        this.completion = CommandCompletion.ERROR;
        this.errorMessage = error;
        System.out.println("ERROR: " + error);
    }
    
    public Result(CommandCompletion completion) {
        this.completion = completion;
        this.errorMessage = "";
    }
    
    public Result(CommandCompletion completion, String error) {
        this.completion = completion;
        this.errorMessage = error;
        if (completion == CommandCompletion.ERROR)
            System.out.println("ERROR: " + error);
    }
    
    public boolean success() {
        if (this.completion == CommandCompletion.COMPLETE)
            return true;
        return false;
    }
}
