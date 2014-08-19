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
        cabinet = prompt("Cabinet to calibrate (D1, D2, CPL, CPM, CPR)?", calCabinet);
    if (shelf == -1)
        shelf = prompt("Shelf to calibrate (00-34)?", calShelf);
    if (depth == -1)
        depth = prompt("Depth of plunge (1 for desktop; 1 (bottom) or 2 (top) disc in CP)?", calDepth);
    if (speed == -1)
        speed = prompt("Speed to run arm at during calibration?", calSpeed);
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
        fromCabinet = prompt("Starting cabinet?", routeFromCabinet);
    if (fromShelf == -1)
        fromShelf = prompt("Starting shelf?", routeFromShelf);
    if (toCabinet == '' || toCabinet == null)
        toCabinet = prompt("Ending cabinet?", routeToCabinet);
    if (toShelf == -1)
        toShelf = prompt("Ending shelf?", routeToShelf);
    if (effect == '' || effect == null)
        effect = prompt("Effect?", routeEffect);
    r12("ENQUEUE?queue=0&status=0&command=ROUTE&fromcabinet=" + fromCabinet + 
            "&fromshelf=" + fromShelf + "&tocabinet=" + toCabinet + "&toshelf=" + toShelf +
            "&effect=" + effect);
    routeFromCabinet = fromCabinet;
    routeFromShelf = fromShelf;
    routeToCabinet = toCabinet;
    routeToShelf = toShelf;
    routeEffect = effect;
}
function r12CalPosition(plunge) {
    cmd = "ENQUEUE?queue=0&status=0&command=POSITION-CALIBRATE&option=move&cabinet=" + calCabinet +
            "&shelf=" + calShelf + "&plunge=" + plunge + "&depth=" + calDepth + "&speed=" + calSpeed;
    r12(cmd);
}
function r12CalAdjust(axis, distance) {
    if (axis == null)
        axis = prompt("Which axis to adjust (x, y, z, pitch, yaw, roll)?");
    if (distance == -1)
        distance = prompt("Move amount (positive or negative, mm or degrees)?");
    cmd = "ENQUEUE?queue=0&status=0&command=POSITION-CALIBRATE&option=" + axis + "&value=" + distance;
    r12(cmd);
}
function r12reload(name) {
        if (name == '')
            name = prompt('Reload filter (empty is all; examples are CPL0, CPR23, D1_2, CPL_D1)?');
        cmd = 'ENQUEUE?queue=0&status=0&command=PROGRAM-CONTROLLER&name=' + name;
        r12(cmd);
    }
    function r12move(cabinet, shelf) {
        if ( (cabinet == null) || (cabinet == "") )
            cabinet = prompt('Which cabinet (CPL, CPM, CPR, D1, or D2)?');
        if ( (shelf == null) || (shelf == -1) )
            shelf = prompt('Which shelf?');
        cmd = 'ENQUEUE?queue=0&status=0&command=POSITION&cabinet=' + cabinet + '&shelf=' + shelf;
        r12(cmd);
    }
    function r12adjust(cmd) {
        amount = prompt('Amount to adjust ' + cmd + ' in mm?');
        r12debug('debug=' + cmd + '&value=' + amount);
    }
    function r12mount(desktop, layer, shelf) {
        if (layer == -1)
            layer = prompt('CachePoint layer (00-34)?');
        if (desktop == -1)
            desktop = prompt('Desktop (1 or 2)?');
        if (shelf == -1)
            shelf = prompt('Desktop shelf (0-5)?');
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
