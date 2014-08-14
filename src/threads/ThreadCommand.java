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
package threads;

import commandprocessor.CommandProcessor;
import robotoperations.ArmOperations;
import route.PositionLookup;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class ThreadCommand extends Thread
{
    private ArmOperations ao;
    private CommandProcessor cp = null;

    @Override
    public void run()
    {
        ao = ArmOperations.getInstance();
        Result result = ao.init();
        if (result.success())
        {
            System.out.println("All Arm Inits successful");
            // now initialize the positions
            PositionLookup plt = PositionLookup.getInstance();
            result = plt.init();
            if (result.success()) {
                System.out.println("All positions initialized successfully");
                // now bring up the command processor
                cp = CommandProcessor.getInstance();
                cp.processCommands();
            } else
                System.err.println("PositionLookup init failed");
        } else
            System.err.println("ArmOperations init failed");
        System.err.println("CommandProcess thread terminating");
        // indicate we are down
        cp = null;
    }
    
    /**
     * kill the background command processor and wait for it to complete
     */
    public void kill() {
        if (cp != null) {
            // kill the thread
            cp.kill();
            // and wait for it to complete
            while (cp != null)
                Utils.sleep(250);            
        }
    }

}