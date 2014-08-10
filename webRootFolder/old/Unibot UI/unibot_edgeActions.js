/***********************
* Adobe Edge Animate Composition Actions
*
* Edit this file with caution, being careful to preserve 
* function signatures and comments starting with 'Edge' to maintain the 
* ability to interact with these actions from within Adobe Edge Animate
*
***********************/
(function($, Edge, compId){
var Composition = Edge.Composition, Symbol = Edge.Symbol; // aliases for commonly used Edge classes

   //Edge symbol: 'stage'
   (function(symbolName) {
      
      
      Symbol.bindElementAction(compId, symbolName, "document", "compositionReady", function(sym, e) {
	
	// define a sequencing function to manage the order of the scenes
	sym.done = function(sym, symName) {
		var next = '';
		var stage = sym.getComposition().getStage();
		var label = 'Start';
	
		// make sure our timer is stopped
		var timer = stage.getSymbol('TimerRestart');
		timer.play('Stop');
	
		// make sure panels are hidden and stopped
		stage.getSymbol('Startup').getSymbolElement().hide();
		stage.getSymbol('Intro').getSymbolElement().hide();
		stage.getSymbol('AssignLayers').getSymbolElement().hide();
		stage.getSymbol('BuildDesktop').getSymbolElement().hide();
		stage.getSymbol('ChangeOptions').getSymbolElement().hide();
		stage.getSymbol('ActionUpgradeOS').getSymbolElement().hide();
		stage.getSymbol('ActionUpgradeApp').getSymbolElement().hide();
		stage.getSymbol('ActionDesktopRepair').getSymbolElement().hide();
		stage.getSymbol('Goodbye').getSymbolElement().hide();
		stage.getSymbol('PasswordWindow').getSymbolElement().hide();	
	
		stage.getSymbol('Startup').stop();
		stage.getSymbol('Intro').stop();
		stage.getSymbol('AssignLayers').stop();
		stage.getSymbol('BuildDesktop').stop();
		stage.getSymbol('ChangeOptions').stop();
		stage.getSymbol('ActionUpgradeOS').stop();
		stage.getSymbol('ActionUpgradeApp').stop();
		stage.getSymbol('ActionDesktopRepair').stop();
		stage.getSymbol('Goodbye').stop();
		stage.getSymbol('PasswordWindow').stop();	
	
		// decide what the next step is
		switch (symName) {
			case 'Start':
				next = 'Startup';
				break;
			case 'Startup':
				next = 'Intro';
				break;
			case 'Intro':
				next = 'AssignLayers';
				// and clear out the queue to ensure we are ready to rock w/no conflicts
				stage.unibot.clearQueue(stage.controller);
				break;
			case 'AssignLayers':
				next = 'BuildDesktop';
				break;
			case 'BuildDesktop':
				next = 'ChangeOptions';
				break;
			case 'ChangeOptions':
				next = 'Goodbye';
				break;
			case 'ChangeOptions.ActionDesktopRepair':
				next = 'ActionDesktopRepair';
				break;
			case 'ChangeOptions.ActionUpgradeOS':
				next = 'ActionUpgradeOS';
				break;
			case 'ChangeOptions.ActionUpgradeApp':
				next = 'ActionUpgradeApp';
				break;
			case 'ActionUpgradeOS':
				next = 'ChangeOptions';
				label = 'Continue';
				break;
			case 'ActionUpgradeApp':
				next = 'ChangeOptions';
				label = 'Continue';
				break;
			case 'ActionDesktopRepair':
				next = 'ChangeOptions';
				label = 'Continue';
				break;
			case 'TimerExpire':
				next = 'Goodbye';
				break;
			case 'Goodbye':
				next = 'Intro';
				break;
		}
	
		// if getStage is defined, use it.  Otherwise go straight to symbol (when startup sym is in use)
		var nextSym = null;
		nextSym = stage.getSymbol(next);
	
		if (nextSym) {
			console.log('Transistion from scene ' + symName + ' to scene ' + next + ' (at label ' + label + ')');
			nextSym.getSymbolElement().show();
			nextSym.play(label);
			// see if we need to start the restart timer
			if ( (next == 'AssignLayers') || (next == 'ChangeOptions') ) {
				// start the timer to ensure we restart the software if no interaction
				timer.play('Start');
			}
			// track if we are "in use"
			if ( (next == 'Startup') || (next == 'Intro') || (next == 'Goodbye') )
				stage.unibot.setVariable('inUse' + stage.controller, false);
			else
				stage.unibot.setVariable('inUse' + stage.controller, true);
	
		} else
			console.log('Scene symbol ' + symName + ' unknown in stage.done');
	}
	
	
	// define the UniBot REST interface
	sym.unibot = {};
	sym.unibot.simulated = true;		// true uses the simulator, false uses real json
	sym.unibot.variables = {};
	sym.unibot.jsontest = function() {
		var jsonData = null;
	    $.ajax({
	        type: 'GET',
	        url: 'http://echo.jsontest.com/key/ajax/one/works',
	        dataType: 'json',
	        success: function(data) { console.log('in success'); console.log(data); jsonData = data; },
	        error: function() { console.log('ajax json failed'); jsonData = null; },
	        data: {},
	        async: false
	    });
	    console.log('ajax call done');
	    alert(jsonData.key + ' = ' + jsonData.one);
	}
	//sym.unibot.jsontest();
	sym.unibot.json = function(command, parameters) {
		var result = null;
		var url = '/unibot/' + command;
		var first = true;
		for (var key in parameters) {
			url += (first ? '?' : '&') + key + '=' + encodeURIComponent(parameters[key]);
			first = false;
		}
		console.log('JSON call: ' + url);
	    $.ajax({
	        type: 'GET',
	        url: url,
	        dataType: 'json',
	        success: function(data) { result = data; },
	        error: function() { console.log('AJAX JSON failed!'); result = null; },
	        data: {},
	        async: false
	    });
	    console.log('JSON result:');
	    console.log(result);
	    return result;
	};
	
	sym.unibot.enqueue = function(command, parameters) { 
		if (sym.unibot.simulated) {
			if (isNaN(sym.unibot.id))
				sym.unibot.id = 0;
			var id = 'Q' + sym.unibot.id++;
			response = { id: id };
			// track mounts in a global array... using a number
			// that represents a random amount of attempts as it sequences toward completion
			if (isNaN(sym.unibot.mounts))
				sym.unibot.mounts = [];
			sym.unibot.mounts[id] = Math.floor(Math.random() * 30) + 10;
			// debug output
				var msg = '';
	
				for (var key in parameters)
					msg += ', ' + key + ': ' + parameters[key];
			console.log('REST: ENQUEUE(' + command + msg + ') <- ' + id);
			return response;
		} else {
			var updated = parameters;
			updated.command = command;
			return sym.unibot.json('ENQUEUE', updated);
		}
	};
	sym.unibot.status = function(id) { 
		if (sym.unibot.simulated) {
			if (isNaN(sym.unibot.mounts[id]))
				response = { status: "unknown" };
			else {
				--sym.unibot.mounts[id];
				if (sym.unibot.mounts[id] > 10)
					response = { status: "pending" };
				else if (sym.unibot.mounts[id] > 0)
					response = { status: "executing" };
				else {
					response = { status: "complete" }; 
					delete sym.unibot.mounts[id];
				}
			}
			console.log('REST: STATUS(' + id + ') <- ' + response.status);
			return response;
		} else {
			return sym.unibot.json('STATUS', { id: id });
		}
	};
	sym.unibot.clearQueue = function(queue) { 
		if (sym.unibot.simulated) {
			console.log('REST: CLEAR-QUEUE(' + queue + ')');
		} else {
			return sym.unibot.json('CLEAR-QUEUE', { queue: queue });
		}
	};
	sym.unibot.setVariable = function(key, value) { 
		if (sym.unibot.simulated) {
			sym.unibot.variables[key] = value;
			console.log('REST: SET-VARIABLE(' + key + ', ' + value + ')');
		} else {
			return sym.unibot.json('SET-VARIABLE', { key: key, value: value });
		}
	};
	sym.unibot.getVariable = function(key) { 
		if (sym.unibot.simulated) {
			if (key in sym.unibot.variables) {
				console.log('REST: GET-VARIABLE(' + key + ') <- ' + sym.unibot.variables[key]);
				return sym.unibot.variables[key];
			} 
				console.log('REST: GET-VARIABLE(' + key + ') <- (no such key)');
			return false; 
		} else {
			var result = sym.unibot.json('GET-VARIABLE', { key: key });
			if ( (result.value == null) || (result.value == '') )
				return false;
			return result.value;
		}
	};
	
	// do we know our controller (from a cookie)?
	if (isNaN($.cookie('unibot-controller'))) {
		// don't know the ID, so ask the user
		sym.done(sym, 'Start');
	}
	else {
		sym.getComposition().getStage().controller = $.cookie('unibot-controller');
		sym.done(sym, 'Startup');
	}

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_PasswordSwipeBar}", "swiperight", function(sym, e) {
         // handle swipe if on a touch device
         if (window.Modernizr.touch) {
         	sym.$("PasswordWindow").show();
         	sym.getSymbol("PasswordWindow").play('Start');
         }

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_PasswordSwipeBar}", "click", function(sym, e) {
         // use click on a non-touch device
         if (!window.Modernizr.touch) {
         	sym.$("PasswordWindow").show();
         	sym.getSymbol("PasswordWindow").play('Start');
         }
         

      });
      //Edge binding end


   })("stage");
   //Edge symbol end:'stage'

   //=========================================================
   
   //Edge symbol: 'Layer1'
   (function(symbolName) {   
   
      Symbol.bindElementAction(compId, symbolName, "${_Layer}", "touchstart", function(sym, e) {
         if (window.Modernizr.touch)
         	sym.getComposition().getStage().animateLayer(sym);

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Layer}", "click", function(sym, e) {
         if (!window.Modernizr.touch) {
         	sym.getComposition().getStage().animateLayer(sym);
         }

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         sym.play('Deployed');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2500, function(sym, e) {
         sym.play('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3500, function(sym, e) {
         sym.play('Unused');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 4500, function(sym, e) {
         sym.play('Executing');

      });
      //Edge binding end

   })("LayerSymbol");
   //Edge symbol end:'LayerSymbol'

   //=========================================================
   
   //Edge symbol: 'AssignLayers'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1000, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         // advance to the next scene
         var stage = sym.getComposition().getStage();
         stage.done(sym, 'AssignLayers');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 500, function(sym, e) {
         // fix up layer names
         sym.getSymbol('Layer1').$('Layername').html('Skype');
         sym.getSymbol('Layer2').$('Layername').html('Adobe');
         sym.getSymbol('Layer3').$('Layername').html('Office');
         sym.getSymbol('Layer4').$('Layername').html('ArcGIS');
         sym.getSymbol('Layer5').$('Layername').html('Epic');
         sym.getSymbol('Layer6').$('Layername').html('Printer');
         
         // set layer start points
         sym.getSymbol('Layer1').play('Deployed');
         sym.getSymbol('Layer2').play('Deployed');
         sym.getSymbol('Layer3').play('Deployed');
         sym.getSymbol('Layer4').play('Deployed');
         sym.getSymbol('Layer5').play('Deployed');
         sym.getSymbol('Layer6').play('Deployed');
         
         
         // and initialize layer tracking
         sym.getSymbol('Layer1').setVariable('id', 1);
         sym.getSymbol('Layer2').setVariable('id', 2);
         sym.getSymbol('Layer3').setVariable('id', 3);
         sym.getSymbol('Layer4').setVariable('id', 4);
         sym.getSymbol('Layer5').setVariable('id', 5);
         sym.getSymbol('Layer6').setVariable('id', 6);
         
         sym.getSymbol('Layer1').setVariable('location', 0);
         sym.getSymbol('Layer2').setVariable('location', 0);
         sym.getSymbol('Layer3').setVariable('location', 0);
         sym.getSymbol('Layer4').setVariable('location', 0);
         sym.getSymbol('Layer5').setVariable('location', 0);
         sym.getSymbol('Layer6').setVariable('location', 0);
         
         sym.getComposition().getStage().bom = {1: 0, 2: 0, 3: 0, 4: 0};
         
         // define a shared animate function
         sym.getComposition().getStage().animateLayer = function(sym) {
         	var dest = null;
         	var bom = sym.getComposition().getStage().bom;
         
         // are we in a layer or desktop slot?
         	loc = sym.getVariable('location');
         	if (loc == 0) {
         		// we are in a layer slot, so find a free desktop slot
         		for (slot = 1; slot <= 4; ++slot) {
         			if (bom[slot] == 0) {
         				dest = sym.getParentSymbol().$('DesktopSlot' + slot);
         				bom[slot] = sym.getVariable('id');
         				sym.setVariable('location', slot);
         				break;
         			}
         		}
         	} else {
         		// we are in a desktop slot
         		dest = sym.getParentSymbol().$('LayerSlot' + sym.getVariable('id'));
         		bom[sym.getVariable('location')] = 0;
         		sym.setVariable('location', 0);
         	}
         
         	if (dest)
         		sym.getSymbolElement().transition({translate: dest.css('translate')}, 1000);
         };
         
         

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_DoneButton}", "click", function(sym, e) {
         if (!window.Modernizr.touch)
         	sym.play();

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_DoneButton}", "touchstart", function(sym, e) {
         if (window.Modernizr.touch)
         	sym.play();

      });
      //Edge binding end

   })("AssignLayers");
   //Edge symbol end:'AssignLayers'

   //=========================================================
   
   //Edge symbol: 'Intro'
   (function(symbolName) {   
   
      

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 500, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 988, function(sym, e) {
         sym.getSymbol('CheckShowtime').stop();
         sym.getComposition().getStage().done(sym, 'Intro');

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_ClickSurface}", "click", function(sym, e) {
         var stage = sym.getComposition().getStage();
         stage.unibot.enqueue('SHOW-SIGN', { queue: 0, status: 0, layer: 'X#-welcome', effect: 'near-user-' + stage.controller });
         sym.play()

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_ClickSurface}", "touchstart", function(sym, e) {
         var stage = sym.getComposition().getStage();
         stage.unibot.enqueue('SHOW-SIGN', { queue: 0, status: 0, layer: 'X#-welcome', effect: 'near-user-' + stage.controller });
         sym.play()
         

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 7, function(sym, e) {
         // make sure we are visible ... this has to be done past the first play position else the startup
         // of edge overrides our request
         sym.getSymbolElement().show();
         // and set up the callback on the CheckShowtime object so we can manage our timeline
         sym.getSymbol('CheckShowtime').showtime = function(inShow) {
         	if (isNaN(this.inShow))
         		this.inShow = false;
         	if (this.inShow != inShow) {
         		this.inShow = inShow;
         		if (inShow)
         			sym.play('Showtime');
         		else
         			sym.play('Start');
         	}
         }
         // and make sure we monitor the status
         sym.getSymbol('CheckShowtime').play('Start');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3000, function(sym, e) {
         var stage = sym.getComposition().getStage();
         var inShow = stage.unibot.getVariable('inShow');
         if (!inShow) {
         	stage.showTimer = 0;
         	sym.play('Start');
         }
         else 
         	sym.play('Showtime');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2009, function(sym, e) {
         var stage = sym.getComposition().getStage();
         // we are in a show.  Have we dealt with the sign yet?
         if (isNaN(stage.showTimer))
         	stage.showTimer = 0;
         // are we the primary controller?
         if (stage.controller == 1) {
         	// lookup if our partner is done
         	var inUse = stage.unibot.getVariable('inUse2');
         	if (!inUse) {
         		// if this is the first second, bring up the sign
         		switch (stage.showTimer) {
         			case 0:
         				stage.unibot.enqueue('SHOW-SIGN', { queue: 0, status: 0, layer: 'X#-showtime', effect: 'continuous' });
         				break;
         			case 5:
         				// concept is to then do a HOME, CALIBRATE and return to SHOW-SIGN
         				// however, for HOME and CALIBRATE, need to track completion, so that's yet another flag...
         				console.log('5 seconds');
         				break;
         			case 10:
         				console.log('10 seconds');
         				break;
         		}
         		// track total time passing (one second per execution)
         		++stage.showTimer;
         	}
         }

      });
      //Edge binding end

   })("Intro");
   //Edge symbol end:'Intro'

   //=========================================================
   
   //Edge symbol: 'DoneButton'
   (function(symbolName) {   
   
   })("DoneButton");
   //Edge symbol end:'DoneButton'

   //=========================================================
   
   //Edge symbol: 'PasswordWindow'
   (function(symbolName) {   
   
      Symbol.bindElementAction(compId, symbolName, "${_Background}", "touchstart", function(sym, e) {
         sym.play('Close');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Background}", "click", function(sym, e) {
         sym.play('Close');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2250, function(sym, e) {
         sym.getParentSymbol().$("PasswordWindow").hide();
         

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1000, function(sym, e) {
         for (id = 1; id <= 6; ++id) {
         	var pwsym = sym.getSymbol('PasswordButton' + id);
         	pwsym.setVariable('id', id.toString());
         	pwsym.getSymbolElement().show();
         }
         sym.setVariable('password', '');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 4000, function(sym, e) {
         // insert code here
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 0, function(sym, e) {
         // set up the management buttons
         sym.getSymbol('HomeButton').$('ButtonLabel').html('Home');
         sym.getSymbol('HomeButton').setVariable('activate', function(sym, execFlag) { 
         	var stage = sym.getComposition().getStage();
         	if (execFlag) {
         		stage.homeResponse = stage.unibot.enqueue('ARM-HOME', { queue: 0, status: 1 });
         		return false;
         	} else {
         		inquiry = stage.unibot.status(stage.homeResponse.id);
         		switch (inquiry.status) {
         			case 'pending':
         				return false;
         			case 'executing':
         				return false;
         			case 'complete':
         				return true;
         			default:
         				alert('Request failed: ' + inquiry.status);
         		}
         		return true;	
         	}
         });
         
         sym.getSymbol('CalibrateButton').$('ButtonLabel').html('Calibrate');
         sym.getSymbol('CalibrateButton').setVariable('activate', function(sym, execFlag) { 
         	var stage = sym.getComposition().getStage();
         	if (execFlag) {
         		stage.calibrateResponse = stage.unibot.enqueue('ARM-CALIBRATE', { queue: 0, status: 1 });
         		return false;
         	} else {
         		inquiry = stage.unibot.status(stage.calibrateResponse.id);
         		switch (inquiry.status) {
         			case 'pending':
         				return false;
         			case 'executing':
         				return false;
         			case 'complete':
         				return true;
         			default:
         				alert('Request failed: ' + inquiry.status);
         		}
         		return true;	
         	}
         });
         
         sym.getSymbol('ShowInFiveButton').$('ButtonLabel').html('Show in 5');
         sym.getSymbol('ShowInFiveButton').setVariable('activate', function(sym, execFlag) { 
         	var stage = sym.getComposition().getStage();
         	if (execFlag) {
         		stage.showInFiveResponse = stage.unibot.enqueue('SHOW-SIGN', { queue: 0, status: 0, layer: 'X#-fiveminutes', effect: 'broad' });
         		return false;
         	} else {
         		inquiry = stage.unibot.status(stage.showInFiveResponse.id);
         		switch (inquiry.status) {
         			case 'pending':
         				return false;
         			case 'executing':
         				return false;
         			case 'complete':
         				return true;
         			default:
         				alert('Request failed: ' + inquiry.status);
         		}
         		return true;	
         	}
         });
         
         var stage = sym.getComposition().getStage();
         var inShow = stage.unibot.getVariable('inShow');
         if (inShow)
         	sym.getSymbol('ShowNowButton').$('ButtonLabel').html('End show');
         else
         	sym.getSymbol('ShowNowButton').$('ButtonLabel').html('Start show');
         
         sym.getSymbol('ShowNowButton').setVariable('activate', function(sym, execFlag) { 
         	var stage = sym.getComposition().getStage();
         	if (!execFlag) {
         		var inShow = stage.unibot.getVariable('inShow');
         		inShow = !inShow;
         		if (inShow)
         			sym.$('ButtonLabel').html('End show');
         		else
         			sym.$('ButtonLabel').html('Start show');
         		stage.unibot.setVariable('inShow', inShow);
         		return true;	
         	}
         });
         
         sym.getSymbol('RestartUI').$('ButtonLabel').html('Restart UI');
         sym.getSymbol('RestartUI').setVariable('activate', function(sym, execFlag) {
         	if (!execFlag) {
         		console.log('Restart UI');
         		sym.getComposition().getStage().done(sym, 'Start');
         	}
         	return true;
         });
         
         var stage = sym.getComposition().getStage();
         if (stage.unibot.simulated)
         	sym.getSymbol('RobotSimulator').$('ButtonLabel').html('Live robot');
         else
         	sym.getSymbol('RobotSimulator').$('ButtonLabel').html('Simulated');
         
         sym.getSymbol('RobotSimulator').setVariable('activate', function(sym, execFlag) {
         	if (!execFlag) {
         		var stage = sym.getComposition().getStage();
         		stage.unibot.simulated = !stage.unibot.simulated;
         		if (stage.unibot.simulated)
         			sym.$('ButtonLabel').html('Live robot');
         		else
         			sym.$('ButtonLabel').html('Simulated');
         		console.log('Flip robot simulator');
         	}
         	return true;
         });
         
         
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3250, function(sym, e) {
         // insert code here
      });
      //Edge binding end

   })("PasswordWindow");
   //Edge symbol end:'PasswordWindow'

   //=========================================================
   
   //Edge symbol: 'PasswordButton'
   (function(symbolName) {   
   
      Symbol.bindElementAction(compId, symbolName, "${_RoundRect}", "touchstart", function(sym, e) {
         if (window.Modernizr.touch) {
         	sym.getSymbolElement().hide();
         	var pw = sym.getParentSymbol().getVariable('password');
         	pw += sym.getVariable('id');
         	//alert('id is ' + sym.getVariable('id'));
         	sym.getParentSymbol().setVariable('password', pw);
         	//alert('password is ' + pw);
         
         	if (pw == '1653') {
         		sym.getComposition().getStage().getSymbol("PasswordWindow").play('ValidPW');
         	}
         }

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_RoundRect}", "click", function(sym, e) {
         if (!window.Modernizr.touch) {
         	sym.getSymbolElement().hide();
         	var pw = sym.getParentSymbol().getVariable('password');
         	pw += sym.getVariable('id');
         	sym.getParentSymbol().setVariable('password', pw);
         
         	if (pw == '1653') {
         		sym.getComposition().getStage().getSymbol("PasswordWindow").play('ValidPW');
         	}
         }

      });
      //Edge binding end

   })("PasswordButton");
   //Edge symbol end:'PasswordButton'

   //=========================================================
   
   //Edge symbol: 'OpButton'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 0, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1198, function(sym, e) {
         	var activate = sym.getVariable('activate');
         	if (activate) {
         		if (activate(sym, false))
         			sym.stop();
         	} else
         		sym.stop();

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_ButtonGroup}", "click", function(sym, e) {
         if (!window.Modernizr.touch) {
         	sym.play('Touch');
         }

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_ButtonGroup}", "touchstart", function(sym, e) {
         if (window.Modernizr.touch) {
         	sym.play('Touch');
         }

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1100, function(sym, e) {
         var activate = sym.getVariable('activate');
         if (activate)
         	activate(sym, true);

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1396, function(sym, e) {
         sym.play('Repeat');

      });
      //Edge binding end

   })("OpButton");
   //Edge symbol end:'OpButton'

   //=========================================================
   
   //Edge symbol: 'LayerSymbol_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1513, function(sym, e) {
         sym.play('Deployed');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2495, function(sym, e) {
         sym.play('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3500, function(sym, e) {
         sym.play('Executing');

      });
      //Edge binding end

      })("OSSymbol");
   //Edge symbol end:'OSSymbol'

   //=========================================================
   
   //Edge symbol: 'OSSymbol_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1493, function(sym, e) {
         sym.play('Deployed');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2514, function(sym, e) {
         sym.play('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3500, function(sym, e) {
         sym.play('Executing');

      });
      //Edge binding end

      })("PersonalizationSymbol");
   //Edge symbol end:'PersonalizationSymbol'

   //=========================================================
   
   //Edge symbol: 'Background'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 4000, function(sym, e) {
         sym.play(0);

      });
      //Edge binding end

   })("Background");
   //Edge symbol end:'Background'

   //=========================================================
   
   //Edge symbol: 'Intro_1'
   (function(symbolName) {   

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 4000, function(sym, e) {
         sym.getComposition().getStage().done(sym, 'BuildDesktop');

      });
         //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1000, function(sym, e) {
         //sym.getSymbolElement().show();
         var stage = sym.getComposition().getStage();
         var bom = stage.bom;
         var pending = {};
         
         // set layer names and state
         for (desktop = 0; desktop < 1; ++desktop) {
         	for (slotNum = 1; slotNum <= 4; ++slotNum) {
         		if (bom[slotNum] != 0) {
         			// set the assigned layer name
         			var layerName = stage.getSymbol('AssignLayers').getSymbol('Layer' + bom[slotNum]).$('Layername').html();
         			sym.getSymbol('Layer' + slotNum).$('Layername').html(layerName);
         			sym.getSymbol('Layer' + slotNum).play('Pending');
         		} else {
         			// set it to unknown
         			sym.getSymbol('Layer' + slotNum).$('Layername').html('');
         			sym.getSymbol('Layer' + slotNum).play('Unused');
         		}
         		sym.getSymbol('Layer' + slotNum).getSymbolElement().show();
         	}
         }
         
         // set OS and personalization
         sym.getSymbol('OS').play('Pending');
         sym.getSymbol('OS').getSymbolElement().show();
         sym.getSymbol('Personalization').play('Pending');
         sym.getSymbol('Personalization').getSymbolElement().show();
         
         
         
         // execute the desktop commands to the robot
         for (desktop = 0; desktop < 1; ++desktop) {
         	var desktopId = stage.controller; // * 2 - 2 + desktop;
         	var response = stage.unibot.enqueue('MOUNT-LAYER', {queue: stage.controller, status: 1, layer: 'X#-OS1', shelf: 0, desktop: desktopId, effect: 'dramatic' });
console.log(response);
         	var layerSym = sym.getSymbol('OS');
         	var construction = { 0: { layer: "Windows", id: response.id, status: "pending", symbol: layerSym } };
         	for (slotNum = 1; slotNum <= 4; ++slotNum) {
         		if (bom[slotNum] != 0) {
         			layerSym = sym.getSymbol('Layer' + slotNum);
         			var layerName = layerSym.$('Layername').html();
         			response = stage.unibot.enqueue('MOUNT-LAYER', {queue: stage.controller, status: 1, layer: 'X' + bom[slotNum] + '-' + layerName, shelf: slotNum, desktop: desktopId, effect: 'efficient'}); 
         			construction[slotNum] = { layer: layerName, id: response.id, status: "pending", symbol: layerSym };
         		}
         	}
         	response = stage.unibot.enqueue('MOUNT-LAYER', {queue: stage.controller, status: 1, layer: 'X#-PERS', shelf: 5, desktop: desktopId, effect: 'dramatic' });
         	layerSym = sym.getSymbol('Personalization');
         	construction[5] = { layer: "Personalization", id: response.id, status: "pending", symbol: layerSym };
         
         	pending[desktop] = construction;
         }
         stage.pending = pending;

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2150, function(sym, e) {
         // check the status of the layers.  Loop back to 'Process' on the timeline until
         // all layers have finished execution.
         var stage = sym.getComposition().getStage();
         var pending = stage.pending;
         
         // check the status of the items in the robot queue
         var pendingRobot = false;
         for (index = 0; index < 6; ++index) {
         	// is something in that slot?
         	if (pending[0][index]) {
         		var disc = pending[0][index];
         		if (disc.status != 'complete') {
         			pendingRobot = true;
         			// get the status
         			var response = stage.unibot.status(disc.id);
         			if (response.status != disc.status) {
         				console.log('status of disc ' + disc.layer + ' changed to ' + response.status);
         				switch (response.status) {
         					case 'executing':
         						disc.symbol.play('Executing');
         						break;
         					case 'complete':
         						disc.symbol.play('Deployed');
         						break;
         				}
         				disc.status = response.status;
         			}
         		}
         	}
         }
         
         // continue looping until all discs are deployed
         if (pendingRobot)
         	sym.play('Process');
         else
         	sym.play('Finish');

      });
      //Edge binding end

      })("Build");
   //Edge symbol end:'Build'

   //=========================================================
   
   //Edge symbol: 'Intro_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 7, function(sym, e) {
         // make sure we are visible ... this has to be done past the first play position else the startup
         // of edge overrides our request
         sym.getSymbolElement().show();

      });
         //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 500, function(sym, e) {
         sym.stop();

      });
         //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 547, function(sym, e) {
         sym.getComposition().getStage().done(sym, 'Startup');

      });
         //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 0, function(sym, e) {
         sym.getSymbol('One').$('ButtonLabel').html('One');
         sym.getSymbol('One').setVariable('activate', function(sym, execFlag) { 
         	if (execFlag) {
         		sym.getComposition().getStage().controller = 1;
         		$.cookie('unibot-controller', 1, {expires: 7});
         	}
         	else
         		sym.getParentSymbol().play();
         	return true;
         });
         sym.getSymbol('Two').$('ButtonLabel').html('Two');
         sym.getSymbol('Two').setVariable('activate', function(sym, execFlag) { 
         	if (execFlag) {
         		sym.getComposition().getStage().controller = 2;
         		$.cookie('unibot-controller', 2, {expires: 7});
         	}
         	else
         		sym.getParentSymbol().play();
         	return true;
         });
         sym.getSymbol('None').$('ButtonLabel').html('None');
         sym.getSymbol('None').setVariable('activate', function(sym, execFlag) { 
         	if (execFlag) {
         		sym.getComposition().getStage().controller = 0;
         		$.cookie('unibot-controller', 0, {expires: 7});
         	}
         	else
         		sym.getParentSymbol().play();
         	return true;
         });

      });
      //Edge binding end

      })("Startup");
   //Edge symbol end:'Startup'

   //=========================================================
   
   //Edge symbol: 'Intro_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 250, function(sym, e) {
         sym.getSymbolElement().show();
         var stage = sym.getComposition().getStage();
         var bom = stage.bom;
         stage.upgradeLayer = null;
         
         // set up the callbacks for the buttons
         sym.getSymbol('OptionUpgradeOS').setVariable('activate', function(buttonSym){buttonSym.disabled = true; buttonSym.getComposition().getStage().done(sym, 'ChangeOptions.ActionUpgradeOS');});
         sym.getSymbol('OptionUpgradeApp').setVariable('activate', function(buttonSym){buttonSym.disabled = true; buttonSym.getComposition().getStage().done(sym, 'ChangeOptions.ActionUpgradeApp');});
         sym.getSymbol('OptionDesktopRepair').setVariable('activate', function(buttonSym){buttonSym.disabled = true; buttonSym.getComposition().getStage().done(sym, 'ChangeOptions.ActionDesktopRepair');});
         
         // did the user layer an app that we can upgrade?
         for (slotNum = 1; slotNum <= 4; ++slotNum) {
         	if ( (bom[slotNum] == 1) ||
         		(bom[slotNum] == 2) ||
         		(bom[slotNum] == 3) ) {
         			stage.upgradeLayer = { layer: bom[slotNum], slot: slotNum, name: stage.getSymbol('AssignLayers').getSymbol('Layer' + bom[slotNum]).$('Layername').html() };
         			break;
         	}
         }
         
         // since this page is called multiple times (at the Continue label), check if we are done
         // (all buttons used) - if so, trigger completion
         if (sym.getSymbol('OptionUpgradeOS').disabled &&
         	(sym.getSymbol('OptionUpgradeApp').disabled || !stage.upgradeLayer) &&
         	sym.getSymbol('OptionDesktopRepair').disabled) {
         	stage.done(sym, 'ChangeOptions');
         } else {
         	// reset button state
         	sym.getSymbol('OptionUpgradeOS').play('Start');
         	sym.getSymbol('OptionDesktopRepair').play('Start');	
         	// set up the stage based on two or three options
         	if (stage.upgradeLayer) {
         		sym.getSymbol('OptionUpgradeApp').play('Start');
         		sym.play('ThreeOptions');
         	} else {
         		sym.play('TwoOptions');
         	}
         }

      });
         //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 0, function(sym, e) {
         // reset button state
         sym.getSymbol('OptionUpgradeOS').disabled = false;
         sym.getSymbol('OptionUpgradeApp').disabled = false;
         sym.getSymbol('OptionDesktopRepair').disabled = false;

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2500, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_DoneButton}", "click", function(sym, e) {
         if (!window.Modernizr.touch)
         	sym.getComposition().getStage().done(sym, 'ChangeOptions');
         

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_DoneButton}", "touchstart", function(sym, e) {
         if (window.Modernizr.touch)
         	sym.getComposition().getStage().done(sym, 'ChangeOptions');
         

      });
      //Edge binding end

      })("ChangeOptions");
   //Edge symbol end:'ChangeOptions'

   //=========================================================
   
   //Edge symbol: 'Option'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3067, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         sym.play('Enabled');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 500, function(sym, e) {
         if (sym.disabled)
         	sym.play('Disabled');
         else
         	sym.play('Enabled');
         

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Button}", "click", function(sym, e) {
         if (!sym.disabled && !window.Modernizr.touch) {
         	sym.play('Touch');
         }
         
         

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Button}", "touchstart", function(sym, e) {
         if (!sym.disabled && window.Modernizr.touch) {
         	sym.play('Touch');
         }
         

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2303, function(sym, e) {
         var activate = sym.getVariable('activate');
         if (activate) {
         	activate(sym);
         }
         sym.stop();

      });
      //Edge binding end

   })("OptionDesktopRepair");
   //Edge symbol end:'OptionDesktopRepair'

   //=========================================================
   
   //Edge symbol: 'OptionDesktopRepair_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 500, function(sym, e) {
         if (sym.disabled)
         	sym.play('Disabled');
         else
         	sym.play('Enabled');
         

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         sym.play('Enabled');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2303, function(sym, e) {
         var activate = sym.getVariable('activate');
         if (activate) {
         	activate(sym);
         }
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3067, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Button}", "click", function(sym, e) {
         if (!sym.disabled && !window.Modernizr.touch) {
         	sym.play('Touch');
         }
         
         

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Button}", "touchstart", function(sym, e) {
         if (!sym.disabled && window.Modernizr.touch) {
         	sym.play('Touch');
         }
         

      });
      //Edge binding end

   })("OptionUpgradeOS");
   //Edge symbol end:'OptionUpgradeOS'

   //=========================================================
   
   //Edge symbol: 'OptionDesktopRepair_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 500, function(sym, e) {
         var stage = sym.getComposition().getStage();
         // set the name of the app onto the button title
         sym.$('Title').html('Upgrade ' + stage.upgradeLayer.name);
         if (sym.disabled)
         	sym.play('Disabled');
         else
         	sym.play('Enabled');
         

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1500, function(sym, e) {
         sym.play('Enabled');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2303, function(sym, e) {
         var activate = sym.getVariable('activate');
         if (activate) {
         	activate(sym);
         }
         sym.stop();

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3067, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Button}", "click", function(sym, e) {
         if (!sym.disabled && !window.Modernizr.touch) {
         	sym.play('Touch');
         }
         
         

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Button}", "touchstart", function(sym, e) {
         if (!sym.disabled && window.Modernizr.touch) {
         	sym.play('Touch');
         }
         

      });
      //Edge binding end

   })("OptionUpgradeApp");
   //Edge symbol end:'OptionUpgradeApp'

   //=========================================================
   
   //Edge symbol: 'Intro_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 7, function(sym, e) {
         // make sure we are visible ... this has to be done past the first play position else the startup
         // of edge overrides our request
         sym.getSymbolElement().show();

      });
         //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 4000, function(sym, e) {
         sym.getComposition().getStage().done(sym, 'Goodbye');

      });
         //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Rectangle}", "click", function(sym, e) {
         if (!window.Modernizr.touch)
         	sym.play('Exit');

      });
      //Edge binding end

      Symbol.bindElementAction(compId, symbolName, "${_Rectangle}", "touchstart", function(sym, e) {
         if (window.Modernizr.touch)
         	sym.play('Exit');
         	

      });
      //Edge binding end

      })("Goodbye");
   //Edge symbol end:'Goodbye'

   //=========================================================
   
   //Edge symbol: 'Goodbye_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1000, function(sym, e) {
         // make sure we are visible ... this has to be done past the first play position else the startup
         // of edge overrides our request
         sym.getSymbolElement().show();
         // and tell the robot to do the desktop repair
         var stage = sym.getComposition().getStage();
         var response = stage.unibot.enqueue('SHOW-LAYER', {queue: stage.controller, status: 1, shelf: 5, desktop: stage.controller, effect: 'shake' });
         sym.actionId = response.id;
         sym.status = 'pending';
         
         sym.process = function(label) {
         	var response = stage.unibot.status(sym.actionId);
         	if (response.status != sym.status) {
         		console.log('status changed to ' + response.status);
         		switch (response.status) {
         			case 'executing':
         				sym.play('Executing');
         				break;
         			case 'complete':
         				sym.play('Finish');
         				break;
         			default:
         				// unknown answer - just let it finish
         				console.log('Unknown status ' + response.status + ' from robot in Pending loop');
         				sym.play('Finish');
         				break;
         		}
         		sym.status = response.status;
         	} else
         		sym.play(label);
         }

      });
            //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 6000, function(sym, e) {
         sym.getComposition().getStage().done(sym, 'ActionDesktopRepair');

      });
            //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2060, function(sym, e) {
         sym.process('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1517, function(sym, e) {
         sym.play('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3065, function(sym, e) {
         sym.process('Executing');

      });
      //Edge binding end

         })("ActionDesktopRepair");
   //Edge symbol end:'ActionDesktopRepair'

   //=========================================================
   
   //Edge symbol: 'ActionDesktopRepair_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1000, function(sym, e) {
         // make sure we are visible ... this has to be done past the first play position else the startup
         // of edge overrides our request
         sym.getSymbolElement().show();
         // and tell the robot to do the OS upgrade
         var stage = sym.getComposition().getStage();
         var response = stage.unibot.enqueue('REPLACE-LAYER', {queue: stage.controller, status: 1, layer: 11, shelf: 0, desktop: stage.controller, effect: 'dramatic' });
         sym.actionId = response.id;
         sym.status = 'pending';
         
         sym.process = function(label) {
         	var response = stage.unibot.status(sym.actionId);
         	if (response.status != sym.status) {
         		console.log('status changed to ' + response.status);
         		switch (response.status) {
         			case 'executing':
         				sym.play('Executing');
         				break;
         			case 'complete':
         				sym.play('Finish');
         				break;
         			default:
         				// unknown answer - just let it finish
         				console.log('Unknown status ' + response.status + ' from robot in Pending loop');
         				sym.play('Finish');
         				break;
         		}
         		sym.status = response.status;
         	} else
         		sym.play(label);
         }

      });
               //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1517, function(sym, e) {
         sym.play('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2060, function(sym, e) {
         sym.process('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3065, function(sym, e) {
         sym.process('Executing');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 6000, function(sym, e) {
         sym.getComposition().getStage().done(sym, 'ActionUpgradeOS');

      });
               //Edge binding end

            })("ActionUpgradeOS");
   //Edge symbol end:'ActionUpgradeOS'

   //=========================================================
   
   //Edge symbol: 'ActionDesktopRepair_1'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1000, function(sym, e) {
         // make sure we are visible ... this has to be done past the first play position else the startup
         // of edge overrides our request
         sym.getSymbolElement().show();
         // and tell the robot to do the app upgrade
         var stage = sym.getComposition().getStage();
         console.log(stage.upgradeLayer);
         var response = stage.unibot.enqueue('REPLACE-LAYER', {queue: stage.controller, status: 1, layer: stage.upgradeLayer.layer + 6, shelf: stage.upgradeLayer.slot, desktop: stage.controller, effect: 'dramatic' });
         sym.actionId = response.id;
         sym.status = 'pending';
         sym.$('Title').html('Upgrade ' + stage.upgradeLayer.name);
         
         sym.process = function(label) {
         	var response = stage.unibot.status(sym.actionId);
         	if (response.status != sym.status) {
         		console.log('status changed to ' + response.status);
         		switch (response.status) {
         			case 'executing':
         				sym.play('Executing');
         				break;
         			case 'complete':
         				sym.play('Finish');
         				break;
         			default:
         				// unknown answer - just let it finish
         				console.log('Unknown status ' + response.status + ' from robot in Pending loop');
         				sym.play('Finish');
         				break;
         		}
         		sym.status = response.status;
         	} else
         		sym.play(label);
         }

      });
               //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 1517, function(sym, e) {
         sym.play('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2060, function(sym, e) {
         sym.process('Pending');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 3065, function(sym, e) {
         sym.process('Executing');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 6000, function(sym, e) {
         sym.getComposition().getStage().done(sym, 'ActionUpgradeApp');

      });
               //Edge binding end

            })("ActionUpgradeApp");
   //Edge symbol end:'ActionUpgradeApp'

   //=========================================================
   
   //Edge symbol: 'TimerRestart'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 46000, function(sym, e) {
         var stage = sym.getComposition().getStage();
         stage.done(sym, 'TimerExpire');

      });
      //Edge binding end

      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 13, function(sym, e) {
         sym.stop();

      });
      //Edge binding end

   })("TimerRestart");
   //Edge symbol end:'TimerRestart'

   //=========================================================
   
   //Edge symbol: 'CheckShowtime'
   (function(symbolName) {   
   
      Symbol.bindTriggerAction(compId, symbolName, "Default Timeline", 2000, function(sym, e) {
         var stage = sym.getComposition().getStage();
         var inShow = stage.unibot.getVariable('inShow');
         sym.showtime(inShow);
         sym.play('Start');

      });
      //Edge binding end

   })("CheckShowtime");
   //Edge symbol end:'CheckShowtime'

})(jQuery, AdobeEdge, "EDGE-736525547");