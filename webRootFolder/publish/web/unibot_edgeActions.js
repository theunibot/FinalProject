
(function($,Edge,compId){var Composition=Edge.Composition,Symbol=Edge.Symbol;
//Edge symbol: 'stage'
(function(symbolName){Symbol.bindElementAction(compId,symbolName,"document","compositionReady",function(sym,e){window.welcomeSign='00';window.showInFiveSign='10';window.showNowSign='30';window.playWithMeSign='40';window.os={v1:'01',v2:'02'};window.personalization={u1:'41',u2:'42'};window.app={a1:{v1:'11',v2:'13'},a2:{v1:'12',v2:'23'},a3:{v1:'33',v2:'31'},a4:{v1:'32'},a5:{v1:'22'},a6:{v1:'03'}};sym.done=function(sym,symName){var next='';var stage=sym.getComposition().getStage();var label='Start';var timer=stage.getSymbol('BackgroundTimer');timer.play('Stop');var monitor=stage.getSymbol('ErrorMonitor');monitor.stop();stage.getSymbol('Startup').stop();stage.getSymbol('Intro').stop();stage.getSymbol('AssignLayers').stop();stage.getSymbol('BuildDesktop').stop();stage.getSymbol('ChangeOptions').stop();stage.getSymbol('ActionUpgradeOS').stop();stage.getSymbol('ActionUpgradeApp').stop();stage.getSymbol('ActionDesktopRepair').stop();stage.getSymbol('Goodbye').stop();stage.getSymbol('PasswordWindow').stop();stage.getSymbol('RobotError').stop();stage.getSymbol('Startup').getSymbolElement().hide();stage.getSymbol('Intro').getSymbolElement().hide();stage.getSymbol('AssignLayers').getSymbolElement().hide();stage.getSymbol('BuildDesktop').getSymbolElement().hide();stage.getSymbol('ChangeOptions').getSymbolElement().hide();stage.getSymbol('ActionUpgradeOS').getSymbolElement().hide();stage.getSymbol('ActionUpgradeApp').getSymbolElement().hide();stage.getSymbol('ActionDesktopRepair').getSymbolElement().hide();stage.getSymbol('Goodbye').getSymbolElement().hide();stage.getSymbol('PasswordWindow').getSymbolElement().hide();stage.getSymbol('RobotError').getSymbolElement().hide();switch(symName){case'Start':next='Startup';break;case'Startup':next='Intro';break;case'Intro':next='AssignLayers';stage.unibot.clearQueue(stage.controller);break;case'AssignLayers':next='BuildDesktop';break;case'BuildDesktop':next='ChangeOptions';break;case'ChangeOptions':next='Goodbye';break;case'ChangeOptions.ActionDesktopRepair':next='ActionDesktopRepair';break;case'ChangeOptions.ActionUpgradeOS':next='ActionUpgradeOS';break;case'ChangeOptions.ActionUpgradeApp':next='ActionUpgradeApp';break;case'ActionUpgradeOS':next='ChangeOptions';label='Continue';break;case'ActionUpgradeApp':next='ChangeOptions';label='Continue';break;case'ActionDesktopRepair':next='ChangeOptions';label='Continue';break;case'TimerExpire':next='Goodbye';break;case'Goodbye':next='Intro';break;case'Error':next='RobotError';break;case'RobotError':next='Intro';break;}
if(stage.controller==0){if((next!='RobotError')&&(next!='Startup')&&(next!='Intro'))
next='Intro';}
var nextSym=null;nextSym=stage.getSymbol(next);if(nextSym){console.log('Transistion from scene '+symName+' to scene '+next+' (at label '+label+')');nextSym.getSymbolElement().show();nextSym.play(label);if((next=='AssignLayers')||(next=='ChangeOptions')){timer.play('Start');}
if(next!='RobotError'){monitor.play('Start');}
if((next=='Startup')||(next=='Intro')||(next=='Goodbye'))
stage.unibot.setVariable('inUse'+stage.controller,'');else
stage.unibot.setVariable('inUse'+stage.controller,'active');}else
console.log('Scene symbol '+symName+' unknown in stage.done');}
sym.unibot={};sym.unibot.variables={};sym.unibot.json=function(command,parameters){var result=null;var url=sym.unibot.url+'/unibot/'+command;var first=true;for(var key in parameters){url+=(first?'?':'&')+key+'='+encodeURIComponent(parameters[key]);first=false;}
console.log('JSON call: '+url);$.ajax({type:'GET',url:url,dataType:'json',success:function(data){result=data;},error:function(jqXHR,textStatus,errorThrown){console.log('AJAX JSON failed!');console.log(jqXHR);console.log(textStatus);console.log(errorThrown);result=null;},data:{},async:false});console.log('JSON result:');console.log(result);return result;};sym.unibot.enqueue=function(command,parameters){if(sym.unibot.simulated){if(isNaN(sym.unibot.id))
sym.unibot.id=0;var id='Q'+sym.unibot.id++;response={id:id};if(isNaN(sym.unibot.mounts))
sym.unibot.mounts=[];sym.unibot.mounts[id]=Math.floor(Math.random()*30)+10;var msg='';for(var key in parameters)
msg+=', '+key+': '+parameters[key];console.log('REST: ENQUEUE('+command+msg+') <- '+id);return response;}else{var updated=parameters;updated.command=command;return sym.unibot.json('ENQUEUE',updated);}};sym.unibot.status=function(id,autoRedirect){var response;if(typeof(autoRedirect)==='undefined')
autoRedirect=true;if(sym.unibot.simulated){if(isNaN(sym.unibot.mounts[id]))
response={status:"unknown"};else{--sym.unibot.mounts[id];if(sym.unibot.mounts[id]>10)
response={status:"pending"};else if(sym.unibot.mounts[id]>0)
response={status:"executing"};else{response={status:"complete"};delete sym.unibot.mounts[id];}}
console.log('REST: STATUS('+id+') <- '+response.status);}else{response=sym.unibot.json('STATUS',{id:id});}
if((typeof(response.error)!=='undefined')&&(response.error!='')){console.log('REST: Robot Error!');sym.unibot.setVariable('hasError',response.error);if(autoRedirect)
sym.done(sym,'Error');}
if(typeof(response.status)==='undefined'){response.status='error';response.error='Missing status response on REST STATUS call';}
return response;};sym.unibot.clearQueue=function(queue){if(sym.unibot.simulated){console.log('REST: CLEAR-QUEUE('+queue+')');}else{return sym.unibot.json('CLEAR-QUEUE',{queue:queue});}};sym.unibot.setVariable=function(key,value){if(sym.unibot.simulated){sym.unibot.variables[key]=String(value);console.log('REST: SET-VARIABLE('+key+', '+value+')');}else{return sym.unibot.json('SET-VARIABLE',{key:key,value:value});}};sym.unibot.getVariable=function(key){if(sym.unibot.simulated){if(key in sym.unibot.variables){console.log('REST: GET-VARIABLE('+key+') <- '+sym.unibot.variables[key]);return sym.unibot.variables[key];}
console.log('REST: GET-VARIABLE('+key+') <- (no such key)');return false;}else{var result=sym.unibot.json('GET-VARIABLE',{key:key});if((result==null)||(typeof(result.value)==='undefined')||(result.value==null))
return'';return result.value;}};if(isNaN($.cookie('unibot-simulated')))
sym.unibot.simulated=true;else
sym.unibot.simulated=($.cookie('unibot-simulated')=='1');if(typeof($.cookie('unibot-url'))==='undefined')
sym.unibot.url='http://unibot.perf.local';else
sym.unibot.url=$.cookie('unibot-url');if(isNaN($.cookie('unibot-controller'))){sym.done(sym,'Start');}
else{sym.getComposition().getStage().controller=$.cookie('unibot-controller');sym.done(sym,'Startup');}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_PasswordSwipeBar}","swiperight",function(sym,e){if(window.Modernizr.touch){sym.$("PasswordWindow").show();sym.getSymbol("PasswordWindow").play('Start');}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_PasswordSwipeBar}","click",function(sym,e){if(!window.Modernizr.touch){sym.$("PasswordWindow").show();sym.getSymbol("PasswordWindow").play('Start');}});
//Edge binding end
})("stage");
//Edge symbol end:'stage'

//=========================================================

//Edge symbol: 'Layer1'
(function(symbolName){Symbol.bindElementAction(compId,symbolName,"${_Layer}","touchstart",function(sym,e){if(window.Modernizr.touch)
sym.getComposition().getStage().animateLayer(sym);});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Layer}","click",function(sym,e){if(!window.Modernizr.touch){sym.getComposition().getStage().animateLayer(sym);}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){sym.play('Deployed');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2500,function(sym,e){sym.play('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3500,function(sym,e){sym.play('Unused');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4500,function(sym,e){sym.play('Executing');});
//Edge binding end
})("LayerSymbol");
//Edge symbol end:'LayerSymbol'

//=========================================================

//Edge symbol: 'AssignLayers'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1000,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){var stage=sym.getComposition().getStage();stage.done(sym,'AssignLayers');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",500,function(sym,e){sym.getSymbol('Layer1').$('Layername').html('Skype');sym.getSymbol('Layer2').$('Layername').html('Adobe');sym.getSymbol('Layer3').$('Layername').html('Office');sym.getSymbol('Layer4').$('Layername').html('ArcGIS');sym.getSymbol('Layer5').$('Layername').html('Epic');sym.getSymbol('Layer6').$('Layername').html('Printer');sym.getSymbol('Layer1').play('Deployed');sym.getSymbol('Layer2').play('Deployed');sym.getSymbol('Layer3').play('Deployed');sym.getSymbol('Layer4').play('Deployed');sym.getSymbol('Layer5').play('Deployed');sym.getSymbol('Layer6').play('Deployed');sym.getSymbol('Layer1').setVariable('id',1);sym.getSymbol('Layer2').setVariable('id',2);sym.getSymbol('Layer3').setVariable('id',3);sym.getSymbol('Layer4').setVariable('id',4);sym.getSymbol('Layer5').setVariable('id',5);sym.getSymbol('Layer6').setVariable('id',6);sym.getSymbol('Layer1').setVariable('location',0);sym.getSymbol('Layer2').setVariable('location',0);sym.getSymbol('Layer3').setVariable('location',0);sym.getSymbol('Layer4').setVariable('location',0);sym.getSymbol('Layer5').setVariable('location',0);sym.getSymbol('Layer6').setVariable('location',0);sym.getComposition().getStage().bom={1:0,2:0,3:0,4:0};sym.getComposition().getStage().animateLayer=function(sym){var dest=null;var bom=sym.getComposition().getStage().bom;loc=sym.getVariable('location');if(loc==0){for(slot=1;slot<=4;++slot){if(bom[slot]==0){dest=sym.getParentSymbol().$('DesktopSlot'+slot);bom[slot]=sym.getVariable('id');sym.setVariable('location',slot);break;}}}else{dest=sym.getParentSymbol().$('LayerSlot'+sym.getVariable('id'));bom[sym.getVariable('location')]=0;sym.setVariable('location',0);}
if(dest)
sym.getSymbolElement().transition({translate:dest.css('translate')},1000);};});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_DoneButton}","click",function(sym,e){if(!window.Modernizr.touch)
sym.play();});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_DoneButton}","touchstart",function(sym,e){if(window.Modernizr.touch)
sym.play();});
//Edge binding end
})("AssignLayers");
//Edge symbol end:'AssignLayers'

//=========================================================

//Edge symbol: 'Intro'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",684,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){sym.getSymbol('CheckShowtime').stop();sym.getComposition().getStage().done(sym,'Intro');});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_ClickSurface}","click",function(sym,e){var stage=sym.getComposition().getStage();stage.unibot.enqueue('SHOW-SIGN',{queue:0,status:0,layer:window.welcomeSign,effect:'near-user-'+stage.controller});sym.play()});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_ClickSurface}","touchstart",function(sym,e){var stage=sym.getComposition().getStage();stage.unibot.enqueue('SHOW-SIGN',{queue:0,status:0,layer:window.welcomeSign,effect:'near-user-'+stage.controller});sym.play()});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",7,function(sym,e){sym.getSymbolElement().show();sym.getSymbol('CheckShowtime').showtime=function(inShow){if(typeof(this.inShow)==="undefined")
this.inShow=false;if(this.inShow!=inShow){this.inShow=inShow;if(inShow){sym.getSymbol('CheckShowtime').stop();sym.play('Showtime');}
else
sym.play('Start');}}
sym.getSymbol('CheckShowtime').play('Start');var stage=sym.getComposition().getStage();if(stage.controller==0){stage.$("PasswordWindow").show();stage.getSymbol("PasswordWindow").play('Start');}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3000,function(sym,e){var stage=sym.getComposition().getStage();var inShow=(stage.unibot.getVariable('inShow')=='show');if(!inShow){stage.showTimer=0;stage.unibot.enqueue('SHOW-SIGN',{queue:0,status:0,layer:window.playWithMeSign,effect:''});sym.play('Start');}
else
sym.play('Showtime');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2009,function(sym,e){var stage=sym.getComposition().getStage();if(isNaN(stage.showTimer))
stage.showTimer=0;if(stage.controller==1){var inUse=(stage.unibot.getVariable('inUse2')=='active');if(!inUse){switch(stage.showTimer){case 0:stage.signResponse=stage.unibot.enqueue('SHOW-SIGN',{queue:0,status:1,layer:window.showNowSign,effect:''});break;case 1:var inquiry=stage.unibot.status(stage.signResponse.id,true);if((inquiry.status=='pending')||(inquiry.status=='executing'))
--stage.showTimer;break;case 2:console.log('Executing HOME (during show)');stage.homeResponse=stage.unibot.enqueue('ARM-HOME',{queue:0,status:1});break;case 3:var inquiry=stage.unibot.status(stage.homeResponse.id,true);if((inquiry.status=='pending')||(inquiry.status=='executing'))
--stage.showTimer;break;case 4:console.log('Executing CALIBRATE (during show)');stage.calibrateResponse=stage.unibot.enqueue('ARM-CALIBRATE',{queue:0,status:1});break;case 5:var inquiry=stage.unibot.status(stage.calibrateResponse.id,true);if((inquiry.status=='pending')||(inquiry.status=='executing'))
--stage.showTimer;break;}
++stage.showTimer;}}});
//Edge binding end
})("Intro");
//Edge symbol end:'Intro'

//=========================================================

//Edge symbol: 'DoneButton'
(function(symbolName){})("DoneButton");
//Edge symbol end:'DoneButton'

//=========================================================

//Edge symbol: 'PasswordWindow'
(function(symbolName){Symbol.bindElementAction(compId,symbolName,"${_Background}","touchstart",function(sym,e){var stage=sym.getComposition().getStage();if(stage.controller!=0)
sym.play('Close');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Background}","click",function(sym,e){var stage=sym.getComposition().getStage();if(stage.controller!=0)
sym.play('Close');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2250,function(sym,e){sym.getParentSymbol().$("PasswordWindow").hide();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1000,function(sym,e){var stage=sym.getComposition().getStage();if(stage.controller==0)
sym.play('ValidPW');else{for(id=1;id<=6;++id){var pwsym=sym.getSymbol('PasswordButton'+id);pwsym.setVariable('id',id.toString());pwsym.getSymbolElement().show();}
sym.setVariable('password','');}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4000,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3010,function(sym,e){sym.getComposition().getStage().updateButtons=function(){sym.getSymbol('HomeButton').$('ButtonLabel').html('Home');sym.getSymbol('HomeButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(execFlag){stage.homeResponse=stage.unibot.enqueue('ARM-HOME',{queue:0,status:1});return false;}else{inquiry=stage.unibot.status(stage.homeResponse.id,false);switch(inquiry.status){case'pending':return false;case'executing':return false;case'complete':return true;case'error':stage.updateButtons();alert('Error: '+inquiry.error);break;default:alert('Request failed: '+inquiry.status);}
return true;}});sym.getSymbol('CalibrateButton').$('ButtonLabel').html('Calibrate');sym.getSymbol('CalibrateButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(execFlag){stage.calibrateResponse=stage.unibot.enqueue('ARM-CALIBRATE',{queue:0,status:1});return false;}else{inquiry=stage.unibot.status(stage.calibrateResponse.id,false);switch(inquiry.status){case'pending':return false;case'executing':return false;case'complete':return true;case'error':stage.updateButtons();alert('Error: '+inquiry.error);break;default:alert('Request failed: '+inquiry.status);}
return true;}});sym.getSymbol('EnergizeButton').$('ButtonLabel').html('Energize');sym.getSymbol('EnergizeButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(execFlag){stage.homeResponse=stage.unibot.enqueue('ARM-ENERGIZE',{queue:0,status:1});return false;}else{inquiry=stage.unibot.status(stage.homeResponse.id,false);switch(inquiry.status){case'pending':return false;case'executing':return false;case'complete':return true;case'error':stage.updateButtons();alert('Error: '+inquiry.error);break;default:alert('Request failed: '+inquiry.status);}
return true;}});sym.getSymbol('DeEnergizeButton').$('ButtonLabel').html('De-energize');sym.getSymbol('DeEnergizeButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(execFlag){stage.calibrateResponse=stage.unibot.enqueue('ARM-DE-ENERGIZE',{queue:0,status:1});return false;}else{inquiry=stage.unibot.status(stage.calibrateResponse.id,false);switch(inquiry.status){case'pending':return false;case'executing':return false;case'complete':return true;case'error':stage.updateButtons();alert('Error: '+inquiry.error);break;default:alert('Request failed: '+inquiry.status);}
return true;}});sym.getSymbol('ShowInFiveButton').$('ButtonLabel').html('Show in five');sym.getSymbol('ShowInFiveButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(execFlag){stage.showInFiveResponse=stage.unibot.enqueue('SHOW-SIGN',{queue:0,status:0,layer:window.showInFiveSign,effect:'broad'});return false;}else{inquiry=stage.unibot.status(stage.showInFiveResponse.id,false);switch(inquiry.status){case'pending':return false;case'executing':return false;case'complete':return true;case'error':stage.updateButtons();alert('Error: '+inquiry.error);break;default:alert('Request failed: '+inquiry.status);}
return true;}});var stage=sym.getComposition().getStage();var inShow=(stage.unibot.getVariable('inShow')=='show');if(inShow)
sym.getSymbol('ShowNowButton').$('ButtonLabel').html('Show over');else
sym.getSymbol('ShowNowButton').$('ButtonLabel').html('Show now');sym.getSymbol('ShowNowButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(!execFlag){var inShow=!(stage.unibot.getVariable('inShow')=='show');if(inShow)
sym.$('ButtonLabel').html('Show over');else
sym.$('ButtonLabel').html('Show now');stage.unibot.setVariable('inShow',(inShow?'show':''));return true;}});var errorMessage=stage.unibot.getVariable('hasError');if(errorMessage=='')
sym.getSymbol('SuspendButton').$('ButtonLabel').html('Suspend');else
sym.getSymbol('SuspendButton').$('ButtonLabel').html('Resume ('+errorMessage+')');sym.getSymbol('SuspendButton').setVariable('activate',function(sym,execFlag){var stage=sym.getComposition().getStage();if(!execFlag){var errorMessage=stage.unibot.getVariable('hasError');if(errorMessage==''){sym.$('ButtonLabel').html('Resume (Manually suspended from console)');stage.unibot.setVariable('hasError','Manually suspended from console');}else{sym.$('ButtonLabel').html('Suspend');stage.unibot.setVariable('hasError','');}
return true;}});sym.getSymbol('RestartUI').$('ButtonLabel').html('Restart UI');sym.getSymbol('RestartUI').setVariable('activate',function(sym,execFlag){if(!execFlag){console.log('Restart UI');sym.getComposition().getStage().done(sym,'Start');}
return true;});};sym.getComposition().getStage().updateButtons();});
//Edge binding end
})("PasswordWindow");
//Edge symbol end:'PasswordWindow'

//=========================================================

//Edge symbol: 'PasswordButton'
(function(symbolName){Symbol.bindElementAction(compId,symbolName,"${_RoundRect}","touchstart",function(sym,e){if(window.Modernizr.touch){sym.getSymbolElement().hide();var pw=sym.getParentSymbol().getVariable('password');pw+=sym.getVariable('id');sym.getParentSymbol().setVariable('password',pw);if(pw=='1653'){sym.getComposition().getStage().getSymbol("PasswordWindow").play('ValidPW');}}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_RoundRect}","click",function(sym,e){if(!window.Modernizr.touch){sym.getSymbolElement().hide();var pw=sym.getParentSymbol().getVariable('password');pw+=sym.getVariable('id');sym.getParentSymbol().setVariable('password',pw);if(pw=='1653'){sym.getComposition().getStage().getSymbol("PasswordWindow").play('ValidPW');}}});
//Edge binding end
})("PasswordButton");
//Edge symbol end:'PasswordButton'

//=========================================================

//Edge symbol: 'OpButton'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",0,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1198,function(sym,e){var activate=sym.getVariable('activate');if(activate){if(activate(sym,false))
sym.stop();}else
sym.stop();});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_ButtonGroup}","click",function(sym,e){if(!window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_ButtonGroup}","touchstart",function(sym,e){if(window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1100,function(sym,e){var activate=sym.getVariable('activate');if(activate)
activate(sym,true);});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1396,function(sym,e){sym.play('Repeat');});
//Edge binding end
})("OpButton");
//Edge symbol end:'OpButton'

//=========================================================

//Edge symbol: 'LayerSymbol_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1513,function(sym,e){sym.play('Deployed');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2495,function(sym,e){sym.play('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3500,function(sym,e){sym.play('Executing');});
//Edge binding end
})("OSSymbol");
//Edge symbol end:'OSSymbol'

//=========================================================

//Edge symbol: 'OSSymbol_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1493,function(sym,e){sym.play('Deployed');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2514,function(sym,e){sym.play('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3500,function(sym,e){sym.play('Executing');});
//Edge binding end
})("PersonalizationSymbol");
//Edge symbol end:'PersonalizationSymbol'

//=========================================================

//Edge symbol: 'Background'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4000,function(sym,e){sym.play(0);});
//Edge binding end
})("Background");
//Edge symbol end:'Background'

//=========================================================

//Edge symbol: 'Intro_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",7000,function(sym,e){sym.getComposition().getStage().done(sym,'BuildDesktop');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1000,function(sym,e){var stage=sym.getComposition().getStage();var bom=stage.bom;var pending={};for(desktop=0;desktop<1;++desktop){for(slotNum=1;slotNum<=4;++slotNum){if(bom[slotNum]!=0){var layerName=stage.getSymbol('AssignLayers').getSymbol('Layer'+bom[slotNum]).$('Layername').html();sym.getSymbol('Layer'+slotNum).$('Layername').html(layerName);sym.getSymbol('Layer'+slotNum).play('Pending');}else{sym.getSymbol('Layer'+slotNum).$('Layername').html('');sym.getSymbol('Layer'+slotNum).play('Unused');}
sym.getSymbol('Layer'+slotNum).getSymbolElement().show();}}
sym.getSymbol('OS').play('Pending');sym.getSymbol('OS').getSymbolElement().show();sym.getSymbol('Personalization').play('Pending');sym.getSymbol('Personalization').getSymbolElement().show();for(desktop=0;desktop<1;++desktop){var desktopId=stage.controller;var response=stage.unibot.enqueue('MOUNT-LAYER',{queue:stage.controller,status:1,layer:window.os.v1,shelf:0,desktop:desktopId,effect:'dramatic'});var layerSym=sym.getSymbol('OS');var construction={0:{layer:"Windows",id:response.id,status:"pending",symbol:layerSym}};for(slotNum=1;slotNum<=4;++slotNum){if(bom[slotNum]!=0){layerSym=sym.getSymbol('Layer'+slotNum);var layerName=layerSym.$('Layername').html();response=stage.unibot.enqueue('MOUNT-LAYER',{queue:stage.controller,status:1,layer:window.app['a'+bom[slotNum]].v1,shelf:slotNum,desktop:desktopId,effect:'efficient'});construction[slotNum]={layer:layerName,id:response.id,status:"pending",symbol:layerSym};}}
response=stage.unibot.enqueue('MOUNT-LAYER',{queue:stage.controller,status:1,layer:window.personalization['u'+stage.controller],shelf:5,desktop:desktopId,effect:'dramatic'});layerSym=sym.getSymbol('Personalization');construction[5]={layer:"Personalization",id:response.id,status:"pending",symbol:layerSym};pending[desktop]=construction;}
stage.pending=pending;});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2031,function(sym,e){var stage=sym.getComposition().getStage();var pending=stage.pending;var pendingRobot=false;for(index=0;index<6;++index){if(pending[0][index]){var disc=pending[0][index];if(disc.status!='complete'){pendingRobot=true;var response=stage.unibot.status(disc.id);if(response.status!=disc.status){switch(response.status){case'executing':disc.symbol.play('Executing');break;case'complete':disc.symbol.play('Deployed');break;case'error':console.log('found error in loop; returning');return;}
console.log('status of disc '+disc.layer+' changed to '+response.status);disc.status=response.status;}}}}
if(pendingRobot)
sym.play('Process');else
sym.play('Finish');});
//Edge binding end
})("Build");
//Edge symbol end:'Build'

//=========================================================

//Edge symbol: 'Intro_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",500,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",547,function(sym,e){sym.getComposition().getStage().done(sym,'Startup');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",24,function(sym,e){sym.getSymbolElement().show();stage=sym.getComposition().getStage();sym.$('UseSimulatorCheckbox').html('<input type="checkbox" id="simulated"'+(stage.unibot.simulated?' checked':'')+'> Use robot simulator');sym.$('#simulated').change(function(){$('#addressprompt').toggle(!this.checked);});sym.$('RobotAddress').html('<div id="addressprompt">Robot URL: <input style="width: 100%" type="text" size="30" maxlength="30" id="address" value="'+stage.unibot.url+'"></div>');if(stage.unibot.simulated)
$('#addressprompt').hide();sym.getSymbol('One').$('ButtonLabel').html('Door on left');sym.getSymbol('One').setVariable('activate',function(sym,execFlag){if(execFlag){sym.getComposition().getStage().controller=1;$.cookie('unibot-controller',1,{expires:7});stage.unibot.simulated=sym.$('#simulated').is(':checked');stage.unibot.url=sym.$('#address').val();$.cookie('unibot-simulated',(stage.unibot.simulated?'1':'0'),{expires:7});$.cookie('unibot-url',stage.unibot.url,{expires:7});}
else
sym.getParentSymbol().play();return true;});sym.getSymbol('Two').$('ButtonLabel').html('Door on right');sym.getSymbol('Two').setVariable('activate',function(sym,execFlag){if(execFlag){sym.getComposition().getStage().controller=2;$.cookie('unibot-controller',2,{expires:7});stage.unibot.simulated=sym.$('#simulated').is(':checked');stage.unibot.url=sym.$('#address').val();$.cookie('unibot-simulated',(stage.unibot.simulated?'1':'0'),{expires:7});$.cookie('unibot-url',stage.unibot.url,{expires:7});}
else
sym.getParentSymbol().play();return true;});sym.getSymbol('None').$('ButtonLabel').html('Management');sym.getSymbol('None').setVariable('activate',function(sym,execFlag){if(execFlag){sym.getComposition().getStage().controller=0;$.cookie('unibot-controller',0,{expires:7});stage.unibot.simulated=sym.$('#simulated').is(':checked');stage.unibot.url=sym.$('#address').val();$.cookie('unibot-simulated',(stage.unibot.simulated?'1':'0'),{expires:7});$.cookie('unibot-url',stage.unibot.url,{expires:7});}
else
sym.getParentSymbol().play();return true;});});
//Edge binding end
})("Startup");
//Edge symbol end:'Startup'

//=========================================================

//Edge symbol: 'Intro_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",250,function(sym,e){sym.getSymbolElement().show();var stage=sym.getComposition().getStage();var bom=stage.bom;stage.upgradeLayer=null;sym.getSymbol('OptionUpgradeOS').setVariable('activate',function(buttonSym){buttonSym.disabled=true;buttonSym.getComposition().getStage().done(sym,'ChangeOptions.ActionUpgradeOS');});sym.getSymbol('OptionUpgradeApp').setVariable('activate',function(buttonSym){buttonSym.disabled=true;buttonSym.getComposition().getStage().done(sym,'ChangeOptions.ActionUpgradeApp');});sym.getSymbol('OptionDesktopRepair').setVariable('activate',function(buttonSym){buttonSym.disabled=true;buttonSym.getComposition().getStage().done(sym,'ChangeOptions.ActionDesktopRepair');});for(slotNum=1;slotNum<=4;++slotNum){if((bom[slotNum]==1)||(bom[slotNum]==2)||(bom[slotNum]==3)){stage.upgradeLayer={layer:bom[slotNum],slot:slotNum,name:stage.getSymbol('AssignLayers').getSymbol('Layer'+bom[slotNum]).$('Layername').html()};break;}}
if(sym.getSymbol('OptionUpgradeOS').disabled&&(sym.getSymbol('OptionUpgradeApp').disabled||!stage.upgradeLayer)&&sym.getSymbol('OptionDesktopRepair').disabled){stage.done(sym,'ChangeOptions');}else{sym.getSymbol('OptionUpgradeOS').play('Start');sym.getSymbol('OptionDesktopRepair').play('Start');if(stage.upgradeLayer){sym.getSymbol('OptionUpgradeApp').play('Start');sym.play('ThreeOptions');}else{sym.play('TwoOptions');}}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",0,function(sym,e){sym.getSymbol('OptionUpgradeOS').disabled=false;sym.getSymbol('OptionUpgradeApp').disabled=false;sym.getSymbol('OptionDesktopRepair').disabled=false;});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1750,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2750,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_DoneButton}","click",function(sym,e){if(!window.Modernizr.touch)
sym.getComposition().getStage().done(sym,'ChangeOptions');});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_DoneButton}","touchstart",function(sym,e){if(window.Modernizr.touch)
sym.getComposition().getStage().done(sym,'ChangeOptions');});
//Edge binding end
})("ChangeOptions");
//Edge symbol end:'ChangeOptions'

//=========================================================

//Edge symbol: 'Option'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3067,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){sym.play('Enabled');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",500,function(sym,e){if(sym.disabled)
sym.play('Disabled');else
sym.play('Enabled');});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Button}","click",function(sym,e){if(!sym.disabled&&!window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Button}","touchstart",function(sym,e){if(!sym.disabled&&window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2303,function(sym,e){var activate=sym.getVariable('activate');if(activate){activate(sym);}
sym.stop();});
//Edge binding end
})("OptionDesktopRepair");
//Edge symbol end:'OptionDesktopRepair'

//=========================================================

//Edge symbol: 'OptionDesktopRepair_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",500,function(sym,e){if(sym.disabled)
sym.play('Disabled');else
sym.play('Enabled');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){sym.play('Enabled');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2303,function(sym,e){var activate=sym.getVariable('activate');if(activate){activate(sym);}
sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3067,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Button}","click",function(sym,e){if(!sym.disabled&&!window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Button}","touchstart",function(sym,e){if(!sym.disabled&&window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
})("OptionUpgradeOS");
//Edge symbol end:'OptionUpgradeOS'

//=========================================================

//Edge symbol: 'OptionDesktopRepair_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",500,function(sym,e){var stage=sym.getComposition().getStage();sym.$('Title').html('Upgrade '+stage.upgradeLayer.name);if(sym.disabled)
sym.play('Disabled');else
sym.play('Enabled');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){sym.play('Enabled');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2303,function(sym,e){var activate=sym.getVariable('activate');if(activate){activate(sym);}
sym.stop();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3067,function(sym,e){sym.stop();});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Button}","click",function(sym,e){if(!sym.disabled&&!window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
Symbol.bindElementAction(compId,symbolName,"${_Button}","touchstart",function(sym,e){if(!sym.disabled&&window.Modernizr.touch){sym.play('Touch');}});
//Edge binding end
})("OptionUpgradeApp");
//Edge symbol end:'OptionUpgradeApp'

//=========================================================

//Edge symbol: 'Intro_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",7,function(sym,e){sym.getSymbolElement().show();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4000,function(sym,e){sym.getComposition().getStage().done(sym,'Goodbye');});
//Edge binding end
})("Goodbye");
//Edge symbol end:'Goodbye'

//=========================================================

//Edge symbol: 'Goodbye_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1000,function(sym,e){sym.getSymbolElement().show();var stage=sym.getComposition().getStage();var response=stage.unibot.enqueue('SHOW-LAYER',{queue:stage.controller,status:1,shelf:5,desktop:stage.controller,effect:'shake'});sym.actionId=response.id;sym.status='pending';sym.process=function(label){var response=stage.unibot.status(sym.actionId);if(response.status!=sym.status){console.log('status changed to '+response.status);switch(response.status){case'executing':sym.play('Executing');break;case'complete':sym.play('Finish');break;case'error':return;default:console.log('Unknown status '+response.status+' from robot in Pending loop');sym.play('Finish');break;}
sym.status=response.status;}else
sym.play(label);}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",8000,function(sym,e){sym.getComposition().getStage().done(sym,'ActionDesktopRepair');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4060,function(sym,e){sym.process('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3879,function(sym,e){sym.play('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",5065,function(sym,e){sym.process('Executing');});
//Edge binding end
})("ActionDesktopRepair");
//Edge symbol end:'ActionDesktopRepair'

//=========================================================

//Edge symbol: 'ActionDesktopRepair_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1000,function(sym,e){sym.getSymbolElement().show();var stage=sym.getComposition().getStage();var response=stage.unibot.enqueue('REPLACE-LAYER',{queue:stage.controller,status:1,layer:window.os.v2,shelf:0,desktop:stage.controller,effect:'dramatic'});sym.actionId=response.id;sym.status='pending';sym.process=function(label){var response=stage.unibot.status(sym.actionId);if(response.status!=sym.status){console.log('status changed to '+response.status);switch(response.status){case'executing':sym.play('Executing');break;case'complete':sym.play('Finish');break;case'error':return;default:console.log('Unknown status '+response.status+' from robot in Pending loop');sym.play('Finish');break;}
sym.status=response.status;}else
sym.play(label);}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3895,function(sym,e){sym.play('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4060,function(sym,e){sym.process('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",5065,function(sym,e){sym.process('Executing');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",8000,function(sym,e){sym.getComposition().getStage().done(sym,'ActionUpgradeOS');});
//Edge binding end
})("ActionUpgradeOS");
//Edge symbol end:'ActionUpgradeOS'

//=========================================================

//Edge symbol: 'ActionDesktopRepair_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1000,function(sym,e){sym.getSymbolElement().show();var stage=sym.getComposition().getStage();var response=stage.unibot.enqueue('REPLACE-LAYER',{queue:stage.controller,status:1,layer:window.app['a'+stage.upgradeLayer.layer].v2,shelf:stage.upgradeLayer.slot,desktop:stage.controller,effect:'dramatic'});sym.actionId=response.id;sym.status='pending';sym.$('Title').html('Upgrade '+stage.upgradeLayer.name);sym.process=function(label){var response=stage.unibot.status(sym.actionId);if(response.status!=sym.status){console.log('status changed to '+response.status);switch(response.status){case'executing':sym.play('Executing');break;case'complete':sym.play('Finish');break;case'error':return;default:console.log('Unknown status '+response.status+' from robot in Pending loop');sym.play('Finish');break;}
sym.status=response.status;}else
sym.play(label);}});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3856,function(sym,e){sym.play('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4071,function(sym,e){sym.process('Pending');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",5065,function(sym,e){sym.process('Executing');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",8000,function(sym,e){sym.getComposition().getStage().done(sym,'ActionUpgradeApp');});
//Edge binding end
})("ActionUpgradeApp");
//Edge symbol end:'ActionUpgradeApp'

//=========================================================

//Edge symbol: 'BackgroundTimer'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",46000,function(sym,e){var stage=sym.getComposition().getStage();stage.done(sym,'TimerExpire');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",13,function(sym,e){sym.stop();});
//Edge binding end
})("BackgroundTimer");
//Edge symbol end:'BackgroundTimer'

//=========================================================

//Edge symbol: 'CheckShowtime'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",2000,function(sym,e){var stage=sym.getComposition().getStage();var inShow=(stage.unibot.getVariable('inShow')=='show');sym.showtime(inShow);sym.play('Start');});
//Edge binding end
})("CheckShowtime");
//Edge symbol end:'CheckShowtime'

//=========================================================

//Edge symbol: 'Goodbye_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",7,function(sym,e){sym.getSymbolElement().show();});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",4000,function(sym,e){sym.getComposition().getStage().done(sym,'RobotError');});
//Edge binding end
Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",1500,function(sym,e){var stage=sym.getComposition().getStage();var hasError=(stage.unibot.getVariable('hasError')!='')
if(hasError)
sym.play('Loop');else
sym.play('Exit');});
//Edge binding end
})("RobotError");
//Edge symbol end:'RobotError'

//=========================================================

//Edge symbol: 'BackgroundTimer_1'
(function(symbolName){Symbol.bindTriggerAction(compId,symbolName,"Default Timeline",3000,function(sym,e){var stage=sym.getComposition().getStage();var hasError=(stage.unibot.getVariable('hasError')!='');if(hasError){stage.done(sym,'Error');sym.stop();return;}
sym.play('Start');});
//Edge binding end
})("ErrorMonitor");
//Edge symbol end:'ErrorMonitor'

//=========================================================

//Edge symbol: 'GenericButton'
(function(symbolName){})("GenericButton");
//Edge symbol end:'GenericButton'

//=========================================================

//Edge symbol: 'loadingcircle'
(function(symbolName){})("loadingcircle");
//Edge symbol end:'loadingcircle'
})(jQuery,AdobeEdge,"EDGE-736525547");