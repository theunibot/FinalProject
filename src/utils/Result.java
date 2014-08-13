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
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */

package utils;

import enums.CommandCompletion;

/**
 *
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
