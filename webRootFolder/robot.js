/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var calCabinet = "";
var calShelf = "";
var calDepth = "1";
var calSpeed = "300";

function setCalibrate(cabinet, shelf, depth, speed) {
    if (cabinet == null)
        if ((cabinet = prompt("Cabinet to calibrate (D1, D2, CPL, CPM, CPR)?", calCabinet)) == null)
            return;
    if (shelf == -1)
        if ((shelf = prompt("Shelf to calibrate (00-34)?", calShelf)) == null)
            return;
    if (depth == -1)
        if ((depth = prompt("Depth of plunge (1 for desktop; 1 (bottom) or 2 (top) disc in CP)?", calDepth)) == null)
            return;
    if (speed == -1)
        if ((speed = prompt("Speed to run arm at during calibration?", calSpeed)) == null)
            return;
    calCabinet = cabinet;
    calShelf = shelf;
    calDepth = depth;
    calSpeed = speed;
}

var routeFromCabinet = "";
var routeToCabinet = "";
var routeFromShelf = 0;
var routeToShelf = 0;
var routeEffect = "EFFICIENT";

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
        if ((shelf = prompt('Which shelf?', routeToCabinet)) == null)
            return;
    if ( (effect == null) || (effect == "") )
        if ((effect = prompt('Which effect?', routeEffect)) == null)
            return;
    cmd = 'ENQUEUE?queue=0&status=0&command=POSITION&cabinet=' + cabinet + '&shelf=' + shelf + '&effect=' + effect;
    routeToCabinet = cabinet;
    routeToShelf = shelf;
    routeEffect = effect;
    r12(cmd);
}

function r12CalPosition(plunge) {
    if ( (plunge == null) || (plunge == -1) || (plunge == "") )
        if ((plunge = prompt('Plunge position?')) == null)
            return;
    cmd = "ENQUEUE?queue=0&status=0&command=POSITION-CALIBRATE&option=move&cabinet=" + calCabinet +
            "&shelf=" + calShelf + "&plunge=" + plunge + "&depth=" + calDepth + "&speed=" + calSpeed;
    r12(cmd);
}

function r12CalAdjust(axis, distance) {
    if (axis == null)
        if ((axis = prompt("Which axis to adjust (x, y, z, pitch, yaw, roll)?")) == null)
            return;
    if (distance == -1)
        if ((distance = prompt("Move amount (positive or negative, mm or degrees)?")) == null)
            return;
    cmd = "ENQUEUE?queue=0&status=0&command=POSITION-CALIBRATE&option=" + axis + "&value=" + distance;
    r12(cmd);
}

function r12reload(name) {
    if (name == '')
        if ((name = prompt('Reload filter (empty is all; examples are CPL0, CPR23, D1_2, CPL_D1)?')) == null)
            return;
    cmd = 'ENQUEUE?queue=0&status=0&command=PROGRAM-CONTROLLER&name=' + name;
    r12(cmd);
}


function r12adjust(cmd) {
    if ((amount = prompt('Amount to adjust ' + cmd + ' in mm?')) == null)
        return;
    r12debug('debug=' + cmd + '&value=' + amount);
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

function r12debug(cmd) {
    r12('DEBUG?' + cmd);
}

function r12(str) {
    $.getJSON('/unibot/' + str);
}

