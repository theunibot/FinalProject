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
package robotoperations;

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
