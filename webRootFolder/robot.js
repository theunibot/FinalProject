/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var routeFromCabinet = "";
var routeToCabinet = "";
var routeFromShelf = 0;
var routeToShelf = 0;
var routeEffect = "EFFICIENT";
var calDepth = 1;
var calSpeed = 500;
var calPlunge = 1;

function setCalibrate(depth, speed) {
    if (depth == -1)
        if ((depth = prompt("Depth of plunge (1 for desktop; 1 (bottom) or 2 (top) disc in CP)?", calDepth)) == null)
            return;
    if (speed == -1)
        if ((speed = prompt("Speed to run arm at during calibration?", calSpeed)) == null)
            return;
    calDepth = depth;
    calSpeed = speed;
}


function r12runRoute(fromCabinet, fromShelf, toCabinet, toShelf, effect) {
    if (fromCabinet == '' || fromCabinet == null)
        if ((fromCabinet = prompt("Starting cabinet?", routeFromCabinet)) == null)
            return;
    if (fromShelf == -1)
        if ((fromShelf = prompt("Starting shelf?", routeFromShelf)) == null)
            return;
    if (toCabinet == '' || toCabinet == null)
        if ((toCabinet = prompt("Ending cabinet?", routeToCabinet)) == null)
            return;
    if (toShelf == -1)
        if ((toShelf = prompt("Ending shelf?", routeToShelf)) == null)
            return;
    if (effect == '' || effect == null)
        if ((effect = prompt("Effect?", routeEffect)) == null)
            return;
    r12("ENQUEUE?queue=0&status=0&command=ROUTE&fromcabinet=" + fromCabinet + 
            "&fromshelf=" + fromShelf + "&tocabinet=" + toCabinet + "&toshelf=" + toShelf +
            "&effect=" + effect);
    routeFromCabinet = fromCabinet;
    routeFromShelf = fromShelf;
    routeToCabinet = toCabinet;
    routeToShelf = toShelf;
    routeEffect = effect;
}

function r12move(cabinet, shelf, effect) {
    if ( (cabinet == null) || (cabinet == "") )
        if ((cabinet = prompt('Which cabinet (CPL, CPM, CPR, D1, or D2)?', routeToCabinet)) == null)
            return;
    if ( (shelf == null) || (shelf == -1) )
        if ((shelf = prompt('Which shelf?', routeToShelf)) == null)
            return;
    if ( (effect == null) || (effect == "") )
        if ((effect = prompt('Which effect?', routeEffect)) == null)
            return;
    cmd = 'ENQUEUE?queue=0&status=0&command=POSITION&cabinet=' + cabinet + '&shelf=' + shelf + '&effect=' + effect;
    routeToCabinet = cabinet;
    routeToShelf = shelf;
    routeEffect = effect;
    console.log('r12move: moving to ' + cmd);
    r12(cmd);
    console.log('r12move: now setting cal position: ' + cmd);
    r12CalPosition('out-top', -1);
}

function r12CalPosition(plunge, speed) {
    if ( (plunge == null) || (plunge == -1) || (plunge == "") )
        if ((plunge = prompt('Plunge position?', calPlunge)) == null)
            return;
    cmd = "ENQUEUE?queue=0&status=0&command=POSITION-CALIBRATE&option=move&cabinet=" + routeToCabinet +
            "&shelf=" + routeToShelf + "&plunge=" + plunge + "&depth=" + calDepth + "&speed=" + speed;
    r12(cmd);
    calPlunge = plunge;
}

function r12CalAdjust(axis, distance) {
    if (axis == null)
        if ((axis = prompt("Which axis to adjust (x, y, z, pitch, yaw, roll)?")) == null)
            return;
    if (distance == -1)
        if ((distance = prompt("Move amount (positive or negative, mm or degrees)?")) == null)
            return;
    cmd = "ENQUEUE?queue=0&status=0&command=POSITION-CALIBRATE&option=" + axis + "&value=" + distance + "&speed=" + calSpeed;
    r12(cmd);
}

function r12mount(desktop, layer, shelf) {
    if (layer == -1)
        if ((layer = prompt('CachePoint layer (00-34)?')) == null)
            return;
    if (desktop == -1)
        if ((desktop = prompt('Desktop (1 or 2)?')) == null)
            return;
    if (shelf == -1)
        if ((shelf = prompt('Desktop shelf (0-5)?')) == null)
            return;
    cmd = 'ENQUEUE?status=0&command=MOUNT-LAYER&queue=' + desktop + '&desktop=' + desktop + 
        '&layer=' + layer + '&shelf=' + shelf + '&effect=efficient';
    r12(cmd);
}

function r12(str) {
    console.log('Sending r12 command: ' + str);
    $.ajax({
      url: '/unibot/' + str,
      dataType: 'json',
      async: false,
      success: function(data) {
          console.log('r12 command complete');
      }
});}

