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

package commands;

import enums.*;
import robotoperations.ArmOperations;
import route.Position;
import route.PositionLookup;
import utils.Result;

/**
 *
 */
public class CommandPosition extends CommandInterface {
    private Position position;
    
    public CommandPosition(Position position) {
        this.position = position;
    }
    
    public Result execute(CommandArguments args) {
        boolean tester = false;
        
        ArmOperations ao = ArmOperations.getInstance();
        if (!tester)
            return ao.moveTo(position);
        else {
            PositionLookup pl = PositionLookup.getInstance();
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 00));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 30));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPM, 22));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 33));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 31));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 10));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 23));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 04));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 01));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPM, 12));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 03));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 11));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 20));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 13));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPL, 21));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 14));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPM, 02));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 24));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPM, 32));
            ao.moveTo(pl.shelfToPosition(CabinetType.CPR, 34));
            return ao.home();
        }
    }
    
    public String details() {
        return "Position(" + position.getName() + ")";
    }

    /**
     * This command should be allowed to run, even if we have outstanding robot errors
     * 
     * @return true to indicate we should run even during errors
     */
    public boolean ignoreErrors() {
        return true;
    }    
}
