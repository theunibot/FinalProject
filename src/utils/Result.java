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
package utils;

import enums.CommandCompletion;


/**
 *
 */
public class Result {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public CommandCompletion completion;
    public String errorMessage = "";

    public Result() {
        this.completion = CommandCompletion.COMPLETE;
        this.errorMessage = "";
    }

    public Result(String error) {
        this.completion = CommandCompletion.ERROR;
        this.errorMessage = error;
        System.err.println(ANSI_RED + "ERROR: " + error.replace("\r", ANSI_RED + "\r") + ANSI_RESET);
    }

    public Result(CommandCompletion completion) {
        this.completion = completion;
        this.errorMessage = "";
    }

    public Result(CommandCompletion completion, String error) {
        this.completion = completion;
        this.errorMessage = error;
        if (completion == CommandCompletion.ERROR)
            System.err.println(ANSI_RED + "ERROR: " + error.replace("\r", ANSI_RED + "\r") + ANSI_RESET);
    }

    public boolean success() {
        if (this.completion == CommandCompletion.COMPLETE)
            return true;
        return false;
    }
}
