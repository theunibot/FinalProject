/**
 * Adobe Edge: symbol definitions
 */
(function($, Edge, compId){
//images folder
var im='images/';

var fonts = {};    fonts['Roboto, Arial, sans-serif']='<link href=\'http://fonts.googleapis.com/css?family=Roboto:300\' rel=\'stylesheet\' type=\'text/css\'>';

var opts = {
    'gAudioPreloadPreference': 'auto',

    'gVideoPreloadPreference': 'auto'
};
var resources = [
];
var symbols = {
"stage": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "both",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
            {
                id: 'bg_gradient_01',
                type: 'image',
                rect: ['0', '0','512px','385px','auto', 'auto'],
                fill: ["rgba(0,0,0,0)",im+"bg_gradient_01.jpg",'0px','0px']
            },
            {
                id: 'banner_top_01',
                type: 'image',
                rect: ['-2303px', '-1728px','999.6%','1000.4%','auto', 'auto'],
                fill: ["rgba(0,0,0,0)",im+"banner_topandbottom_02.png",'0px','0px'],
                transform: [[],[],[],['0.1','0.1']]
            },
            {
                id: 'BackgroundTimer',
                type: 'rect',
                rect: ['110', '113','auto','auto','auto', 'auto']
            },
            {
                id: 'ErrorMonitor',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'RobotError',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'Goodbye',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'ActionDesktopRepair',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'ActionUpgradeApp',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'ActionUpgradeOS',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'ChangeOptions',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'BuildDesktop',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'AssignLayers',
                display: 'none',
                type: 'rect',
                rect: ['0px', '0px','auto','auto','auto', 'auto'],
                transform: [[],[],[],['0.99962']]
            },
            {
                id: 'Intro',
                display: 'none',
                type: 'rect',
                rect: ['-0.1%', '0%','auto','auto','auto', 'auto']
            },
            {
                id: 'Startup',
                display: 'none',
                type: 'rect',
                rect: ['0', '0','auto','auto','auto', 'auto']
            },
            {
                id: 'PasswordWindow',
                display: 'none',
                type: 'rect',
                rect: ['0px', '0px','auto','auto','auto', 'auto']
            },
            {
                id: 'PasswordSwipeBar',
                type: 'rect',
                rect: ['0px', '0px','100%','25px','auto', 'auto'],
                fill: ["rgba(192,192,192,1)"],
                stroke: [0,"rgba(0,0,0,1)","none"]
            }],
            symbolInstances: [
            {
                id: 'Goodbye',
                symbolName: 'Goodbye',
                autoPlay: {

                }
            },
            {
                id: 'Startup',
                symbolName: 'Startup',
                autoPlay: {

                }
            },
            {
                id: 'ActionUpgradeOS',
                symbolName: 'ActionUpgradeOS',
                autoPlay: {

                }
            },
            {
                id: 'AssignLayers',
                symbolName: 'AssignLayers',
                autoPlay: {

                }
            },
            {
                id: 'ActionUpgradeApp',
                symbolName: 'ActionUpgradeApp',
                autoPlay: {

                }
            },
            {
                id: 'PasswordWindow',
                symbolName: 'PasswordWindow',
                autoPlay: {

                }
            },
            {
                id: 'ErrorMonitor',
                symbolName: 'ErrorMonitor',
                autoPlay: {

                }
            },
            {
                id: 'Intro',
                symbolName: 'Intro',
                autoPlay: {

                }
            },
            {
                id: 'BackgroundTimer',
                symbolName: 'BackgroundTimer',
                autoPlay: {

                }
            },
            {
                id: 'BuildDesktop',
                symbolName: 'Build',
                autoPlay: {

                }
            },
            {
                id: 'ChangeOptions',
                symbolName: 'ChangeOptions',
                autoPlay: {

                }
            },
            {
                id: 'RobotError',
                symbolName: 'RobotError',
                autoPlay: {

                }
            },
            {
                id: 'ActionDesktopRepair',
                symbolName: 'ActionDesktopRepair',
                autoPlay: {

                }
            }
            ]
        },
    states: {
        "Base State": {
            "${_bg_gradient_01}": [
                ["style", "height", '385px'],
                ["style", "width", '512px']
            ],
            "${_ActionDesktopRepair}": [
                ["style", "display", 'none']
            ],
            "${_ActionUpgradeApp}": [
                ["style", "display", 'none']
            ],
            "${_PasswordWindow}": [
                ["style", "top", '0px'],
                ["transform", "scaleY", '1'],
                ["transform", "scaleX", '1'],
                ["style", "left", '0px'],
                ["style", "display", 'none']
            ],
            "${_PasswordSwipeBar}": [
                ["style", "top", '0px'],
                ["style", "-webkit-transform-origin", [50,50], {valueTemplate:'@@0@@% @@1@@%'} ],
                ["style", "-moz-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-ms-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "msTransformOrigin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-o-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "right", 'auto'],
                ["style", "bottom", 'auto'],
                ["style", "height", '25px'],
                ["style", "opacity", '0'],
                ["style", "left", '0px'],
                ["style", "width", '100%']
            ],
            "${_Startup}": [
                ["style", "display", 'none']
            ],
            "${_RobotError}": [
                ["style", "display", 'none']
            ],
            "${_Goodbye}": [
                ["style", "display", 'none']
            ],
            "${_banner_top_01}": [
                ["style", "top", '-1728px'],
                ["style", "height", '1000.39%'],
                ["style", "left", '-2303px'],
                ["style", "width", '999.61%']
            ],
            "${_ChangeOptions}": [
                ["style", "display", 'none']
            ],
            "${_ActionUpgradeOS}": [
                ["style", "display", 'none']
            ],
            "${_Stage}": [
                ["color", "background-color", 'rgba(255,255,255,1)'],
                ["style", "overflow", 'hidden'],
                ["style", "height", '384px'],
                ["style", "width", '512px']
            ],
            "${_AssignLayers}": [
                ["transform", "scaleX", '0.99962'],
                ["style", "display", 'none'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${_Intro}": [
                ["style", "display", 'none'],
                ["style", "left", '-0.07%'],
                ["style", "top", '0%']
            ],
            "${_BuildDesktop}": [
                ["style", "display", 'none']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: true,
            timeline: [
                { id: "eid731", tween: [ "style", "${_ActionUpgradeApp}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid671", tween: [ "style", "${_ChangeOptions}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid716", tween: [ "style", "${_Goodbye}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid613", tween: [ "style", "${_BuildDesktop}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid621", tween: [ "style", "${_Startup}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid395", tween: [ "style", "${_PasswordWindow}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid746", tween: [ "style", "${_RobotError}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid402", tween: [ "style", "${_Intro}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid401", tween: [ "style", "${_AssignLayers}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid719", tween: [ "style", "${_ActionDesktopRepair}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid730", tween: [ "style", "${_ActionUpgradeOS}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" }            ]
        }
    }
},
"LayerSymbol": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    transform: [[0, 0], [], [], ['0.1', '0.1']],
                    id: 'temp_layer_green_01',
                    type: 'image',
                    rect: ['-854px', '-340px', '1840px', '710px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/temp_layer_green_01.png', '0px', '0px']
                },
                {
                    type: 'rect',
                    id: 'Layer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    rect: ['0px', '0px', '131px', '29px', 'auto', 'auto'],
                    fill: ['rgba(192,18,18,0.00)'],
                    c: [
                    {
                        font: ['Roboto, Arial, sans-serif', 16, 'rgba(255,255,255,1)', '300', 'none', 'normal'],
                        type: 'text',
                        id: 'Layername',
                        text: '(name)',
                        align: 'center',
                        rect: ['-16px', '4px', '162px', '29px', 'auto', 'auto']
                    }]
                },
                {
                    id: 'status_pending_01',
                    type: 'image',
                    rect: ['146px', '0', '130px', '33px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_pending_01.png', '0px', '0px']
                },
                {
                    id: 'status_executing_01',
                    type: 'image',
                    rect: ['146px', '0', '129px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_executing_01.png', '0px', '0px']
                },
                {
                    id: 'status_done_01',
                    type: 'image',
                    rect: ['146px', '0', '129px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_done_01.png', '0px', '0px']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${symbolSelector}": [
                ["style", "height", '7.55%'],
                ["style", "width", '25.59%']
            ],
            "${_status_pending_01}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_status_executing_01}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_status_done_01}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_Layername}": [
                ["style", "top", '4px'],
                ["style", "width", '162px'],
                ["style", "text-align", 'center'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "height", '29px'],
                ["style", "font-weight", '300'],
                ["style", "left", '-16px'],
                ["style", "font-size", '16px']
            ],
            "${_Layer}": [
                ["color", "background-color", 'rgba(192,18,18,0.00)'],
                ["style", "bottom", 'auto'],
                ["style", "right", 'auto'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${_temp_layer_green_01}": [
                ["style", "top", '-340px'],
                ["style", "height", '710px'],
                ["style", "opacity", '0.7'],
                ["style", "left", '-854px'],
                ["style", "width", '1840px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4500,
            autoPlay: true,
            labels: {
                "Deployed": 1002,
                "Pending": 2000,
                "Unused": 3003,
                "Executing": 4000
            },
            timeline: [
                { id: "eid832", tween: [ "style", "${_status_executing_01}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid835", tween: [ "style", "${_status_executing_01}", "opacity", '0', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid837", tween: [ "style", "${_status_executing_01}", "opacity", '0', { fromValue: '0'}], position: 3003, duration: 0, easing: "easeOutQuad" },
                { id: "eid841", tween: [ "style", "${_status_executing_01}", "opacity", '1', { fromValue: '0'}], position: 4000, duration: 0, easing: "easeOutQuad" },
                { id: "eid833", tween: [ "style", "${_status_pending_01}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid834", tween: [ "style", "${_status_pending_01}", "opacity", '0.35', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid839", tween: [ "style", "${_status_pending_01}", "opacity", '0', { fromValue: '0.35'}], position: 3003, duration: 0, easing: "easeOutQuad" },
                { id: "eid842", tween: [ "style", "${_status_pending_01}", "opacity", '0', { fromValue: '0'}], position: 4000, duration: 0, easing: "easeOutQuad" },
                { id: "eid767", tween: [ "style", "${_temp_layer_green_01}", "opacity", '0.7', { fromValue: '0.7'}], position: 1002, duration: 0 },
                { id: "eid768", tween: [ "style", "${_temp_layer_green_01}", "opacity", '0.1', { fromValue: '0.7'}], position: 2000, duration: 0 },
                { id: "eid769", tween: [ "style", "${_temp_layer_green_01}", "opacity", '0', { fromValue: '0.1'}], position: 3003, duration: 0 },
                { id: "eid770", tween: [ "style", "${_temp_layer_green_01}", "opacity", '1', { fromValue: '0'}], position: 4000, duration: 0 },
                { id: "eid831", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid836", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid838", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 3003, duration: 0, easing: "easeOutQuad" },
                { id: "eid840", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 4000, duration: 0, easing: "easeOutQuad" }            ]
        }
    }
},
"AssignLayers": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'cachepoint_bg_01',
                    type: 'image',
                    rect: ['44px', '91px', '122px', '271px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/cachepoint_bg_02.png', '0px', '0px']
                },
                {
                    id: 'cachepoint_arrow_01',
                    type: 'image',
                    rect: ['143px', '148px', '185px', '157px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/cachepoint_arrow_01.png', '0px', '0px']
                },
                {
                    id: 'desktop_bg_01',
                    type: 'image',
                    rect: ['341px', '78px', '129px', '284px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/desktop_bg_01.png', '0px', '0px']
                },
                {
                    transform: [[0, 0], [], [], ['0.54586', '0.54586']],
                    type: 'rect',
                    id: 'PersonalizationSymbol',
                    opacity: 1,
                    display: 'none',
                    rect: ['294', '63', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['294px', '28px', '131px', '29px', 'auto', 'auto'],
                    id: 'DesktopSlot1',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['294px', '76px', '131px', '29px', 'auto', 'auto'],
                    id: 'DesktopSlot2',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['294px', '123px', '131px', '29px', 'auto', 'auto'],
                    id: 'DesktopSlot3',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['294px', '171px', '131px', '29px', 'auto', 'auto'],
                    id: 'DesktopSlot4',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['294px', '171px', '131px', '29px', 'auto', 'auto'],
                    id: 'DesktopOSSlot',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    transform: [[0, 0], [], [], ['0.54586', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'OSLayer',
                    rect: ['294', '297', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    type: 'rect',
                    rect: ['0px', '110px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot1',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '151px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot2',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '192px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot3',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '232px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot4',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '273px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot5',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '314px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot6',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    transform: [[0, 0], [], [], ['0.54718', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'Layer6',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.54718', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'Layer5',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.54718', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'Layer4',
                    rect: ['0px', '232px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.54718', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'Layer3',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.54718', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'Layer2',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.54718', '0.54586']],
                    display: 'none',
                    type: 'rect',
                    id: 'Layer1',
                    rect: ['0px', '110px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'DoneButton',
                    rect: ['206px', '322px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 14, 'rgba(183,18,52,1)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text',
                    text: 'Select the applications you want to include from the CachePoint on the left.',
                    align: 'center',
                    rect: ['26px', '50px', 'auto', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            {
                id: 'Layer3',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer4',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer1',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer2',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'PersonalizationSymbol',
                symbolName: 'PersonalizationSymbol',
                autoPlay: {

               }
            },
            {
                id: 'OSLayer',
                symbolName: 'OSSymbol',
                autoPlay: {

               }
            },
            {
                id: 'DoneButton',
                symbolName: 'DoneButton',
                autoPlay: {

               }
            },
            {
                id: 'Layer6',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer5',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_DesktopSlot1}": [
                ["style", "top", '170px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_DesktopOSSlot}": [
                ["style", "top", '393px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_DesktopSlot2}": [
                ["style", "top", '122px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_cachepoint_bg_01}": [
                ["style", "top", '79px'],
                ["style", "height", '283px'],
                ["style", "opacity", '1'],
                ["style", "left", '42px'],
                ["style", "width", '127px']
            ],
            "${_PersonalizationSymbol}": [
                ["style", "top", '-18px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '1'],
                ["style", "left", '551px'],
                ["transform", "scaleX", '0.54586']
            ],
            "${_Layer3}": [
                ["style", "top", '192px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["transform", "scaleX", '0.54718']
            ],
            "${_LayerSlot4}": [
                ["style", "top", '232px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_Layer1}": [
                ["style", "top", '110px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["transform", "scaleX", '0.54718']
            ],
            "${_Layer5}": [
                ["style", "top", '273px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["transform", "scaleX", '0.54718']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "overflow", 'visible'],
                ["style", "width", '100%']
            ],
            "${_Text2}": [
                ["style", "top", '-59px'],
                ["style", "left", '188px'],
                ["style", "font-size", '18px']
            ],
            "${_DesktopSlot4}": [
                ["style", "top", '27px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_Layer6}": [
                ["style", "top", '314px'],
                ["transform", "scaleY", '0.54586'],
                ["transform", "scaleX", '0.54718'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["style", "display", 'none']
            ],
            "${_OSLayer}": [
                ["style", "top", '393px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '1'],
                ["style", "left", '551px'],
                ["transform", "scaleX", '0.54586']
            ],
            "${_cachepoint_arrow_01}": [
                ["style", "top", '148px'],
                ["style", "height", '157px'],
                ["style", "opacity", '1'],
                ["style", "left", '143px'],
                ["style", "width", '185px']
            ],
            "${_Text}": [
                ["style", "top", '50px'],
                ["style", "left", '26px'],
                ["style", "font-size", '14px']
            ],
            "${_DesktopSlot3}": [
                ["style", "top", '75px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_DoneButton}": [
                ["style", "top", '322px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '206px'],
                ["style", "right", 'auto']
            ],
            "${_desktop_bg_01}": [
                ["style", "height", '284px'],
                ["style", "top", '78px'],
                ["style", "left", '341px'],
                ["style", "width", '129px']
            ],
            "${_Layer2}": [
                ["style", "top", '151px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["transform", "scaleX", '0.54718']
            ],
            "${_LayerSlot6}": [
                ["style", "top", '314px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot3}": [
                ["style", "top", '192px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot1}": [
                ["style", "top", '110px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot5}": [
                ["style", "top", '273px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot2}": [
                ["style", "top", '151px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_Layer4}": [
                ["style", "top", '232px'],
                ["transform", "scaleY", '0.54586'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["transform", "scaleX", '0.54718']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4500,
            autoPlay: false,
            labels: {
                "Start": 500
            },
            timeline: [
                { id: "eid427", tween: [ "style", "${_DesktopOSSlot}", "top", '307px', { fromValue: '393px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid252", tween: [ "style", "${_Layer6}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid253", tween: [ "style", "${_Layer6}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid448", tween: [ "style", "${_OSLayer}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid259", tween: [ "style", "${_DoneButton}", "opacity", '1', { fromValue: '0'}], position: 750, duration: 250, easing: "easeOutQuad" },
                { id: "eid261", tween: [ "style", "${_DoneButton}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid169", tween: [ "style", "${_Layer1}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid1274", tween: [ "style", "${_cachepoint_bg_01}", "top", '79px', { fromValue: '79px'}], position: 1000, duration: 0, easing: "easeOutQuad" },
                { id: "eid199", tween: [ "style", "${_LayerSlot4}", "opacity", '0', { fromValue: '0'}], position: 505, duration: 0, easing: "easeOutQuad" },
                { id: "eid235", tween: [ "style", "${_Layer3}", "top", '192px', { fromValue: '192px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid452", tween: [ "style", "${_OSLayer}", "top", '309px', { fromValue: '393px'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid1275", tween: [ "style", "${_cachepoint_bg_01}", "left", '42px', { fromValue: '42px'}], position: 1000, duration: 0, easing: "easeOutQuad" },
                { id: "eid193", tween: [ "style", "${_Layer2}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid215", tween: [ "style", "${_Layer2}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid125", tween: [ "style", "${_DesktopSlot2}", "top", '256px', { fromValue: '122px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid127", tween: [ "style", "${_DesktopSlot3}", "left", '341px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid170", tween: [ "style", "${_LayerSlot6}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid179", tween: [ "style", "${_LayerSlot5}", "left", '42px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid172", tween: [ "style", "${_LayerSlot4}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid451", tween: [ "style", "${_OSLayer}", "left", '340px', { fromValue: '551px'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid189", tween: [ "style", "${_LayerSlot6}", "left", '42px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid131", tween: [ "style", "${_DesktopSlot4}", "left", '341px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid197", tween: [ "style", "${_LayerSlot2}", "opacity", '0', { fromValue: '0'}], position: 505, duration: 0, easing: "easeOutQuad" },
                { id: "eid458", tween: [ "style", "${_PersonalizationSymbol}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid245", tween: [ "style", "${_Layer5}", "top", '273px', { fromValue: '273px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid139", tween: [ "style", "${_DesktopSlot2}", "opacity", '0', { fromValue: '0'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid244", tween: [ "style", "${_Layer4}", "left", '40px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid248", tween: [ "style", "${_Layer5}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid249", tween: [ "style", "${_Layer5}", "left", '40px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid250", tween: [ "style", "${_Layer5}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid168", tween: [ "style", "${_Layer2}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid254", tween: [ "style", "${_Layer6}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid255", tween: [ "style", "${_Layer6}", "left", '40px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid242", tween: [ "style", "${_Layer4}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid243", tween: [ "style", "${_Layer4}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid133", tween: [ "style", "${_DesktopSlot4}", "top", '203px', { fromValue: '27px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid430", tween: [ "style", "${_DesktopOSSlot}", "left", '341px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid137", tween: [ "style", "${_DesktopSlot1}", "top", '282px', { fromValue: '170px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid175", tween: [ "style", "${_LayerSlot1}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid1271", tween: [ "style", "${_cachepoint_bg_01}", "height", '283px', { fromValue: '283px'}], position: 1000, duration: 0, easing: "easeOutQuad" },
                { id: "eid123", tween: [ "style", "${_DesktopSlot2}", "left", '341px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid143", tween: [ "style", "${_DesktopSlot4}", "opacity", '0', { fromValue: '0'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid241", tween: [ "style", "${_Layer4}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid461", tween: [ "style", "${_PersonalizationSymbol}", "left", '340px', { fromValue: '551px'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid129", tween: [ "style", "${_DesktopSlot3}", "top", '230px', { fromValue: '75px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid462", tween: [ "style", "${_PersonalizationSymbol}", "top", '177px', { fromValue: '-18px'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid191", tween: [ "style", "${_Layer1}", "left", '40px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid176", tween: [ "style", "${_Layer2}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid177", tween: [ "style", "${_Layer2}", "left", '40px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid187", tween: [ "style", "${_LayerSlot3}", "left", '42px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid246", tween: [ "style", "${_Layer5}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid247", tween: [ "style", "${_Layer5}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid145", tween: [ "style", "${_DesktopSlot1}", "opacity", '0', { fromValue: '0'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid853", tween: [ "style", "${_cachepoint_arrow_01}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 483, easing: "easeOutQuad" },
                { id: "eid854", tween: [ "style", "${_cachepoint_bg_01}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 483, easing: "easeOutQuad" },
                { id: "eid181", tween: [ "style", "${_LayerSlot2}", "left", '42px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid1272", tween: [ "style", "${_cachepoint_bg_01}", "width", '127px', { fromValue: '127px'}], position: 1000, duration: 0, easing: "easeOutQuad" },
                { id: "eid135", tween: [ "style", "${_DesktopSlot1}", "left", '341px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid205", tween: [ "style", "${_LayerSlot6}", "opacity", '0', { fromValue: '0'}], position: 505, duration: 0, easing: "easeOutQuad" },
                { id: "eid238", tween: [ "style", "${_Layer3}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid239", tween: [ "style", "${_Layer3}", "left", '40px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid201", tween: [ "style", "${_LayerSlot1}", "opacity", '0', { fromValue: '0'}], position: 505, duration: 0, easing: "easeOutQuad" },
                { id: "eid257", tween: [ "style", "${_DoneButton}", "display", 'block', { fromValue: 'none'}], position: 750, duration: 0, easing: "easeOutQuad" },
                { id: "eid260", tween: [ "style", "${_DoneButton}", "display", 'none', { fromValue: 'block'}], position: 1500, duration: 0, easing: "easeOutQuad" },
                { id: "eid167", tween: [ "style", "${_DesktopSlot3}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid207", tween: [ "style", "${_Layer1}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid219", tween: [ "style", "${_Layer1}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid174", tween: [ "style", "${_LayerSlot2}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid203", tween: [ "style", "${_LayerSlot3}", "opacity", '0', { fromValue: '0'}], position: 505, duration: 0, easing: "easeOutQuad" },
                { id: "eid141", tween: [ "style", "${_DesktopSlot3}", "opacity", '0', { fromValue: '0'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid256", tween: [ "style", "${_Layer6}", "top", '314px', { fromValue: '314px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid173", tween: [ "style", "${_LayerSlot3}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid183", tween: [ "style", "${_LayerSlot4}", "left", '42px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid97", tween: [ "style", "${_Layer2}", "top", '151px', { fromValue: '151px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid236", tween: [ "style", "${_Layer3}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid237", tween: [ "style", "${_Layer3}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid171", tween: [ "style", "${_LayerSlot5}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid195", tween: [ "style", "${_LayerSlot5}", "opacity", '0', { fromValue: '0'}], position: 505, duration: 0, easing: "easeOutQuad" },
                { id: "eid251", tween: [ "style", "${_Layer6}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid240", tween: [ "style", "${_Layer3}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid165", tween: [ "style", "${_DesktopSlot4}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid185", tween: [ "style", "${_LayerSlot1}", "left", '42px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid164", tween: [ "style", "${_DesktopSlot2}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid166", tween: [ "style", "${_DesktopSlot1}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid428", tween: [ "style", "${_DesktopOSSlot}", "opacity", '0', { fromValue: '0'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid431", tween: [ "style", "${_DesktopOSSlot}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" }            ]
        }
    }
},
"Intro": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'ClickSurface',
                    type: 'group',
                    rect: ['0%', '0%', '100%', '100%', 'auto', 'auto'],
                    c: [
                    {
                        id: 'bot_01',
                        type: 'image',
                        rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                        fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                    },
                    {
                        id: 'bot_speechbubble_01',
                        type: 'image',
                        rect: ['23px', '70px', '234px', '160px', 'auto', 'auto'],
                        fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                    },
                    {
                        rect: ['54px', '103px', '169px', '120px', 'auto', 'auto'],
                        font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                        id: 'Text3',
                        text: 'Hi, I\'m Unity,<br>the VDI  robot! <br>Let\'s layer some virtual desktops!',
                        align: 'center',
                        type: 'text'
                    },
                    {
                        id: 'CheckShowtime',
                        type: 'rect',
                        rect: ['169', '113', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        rect: ['51px', '80px', 'auto', 'auto', 'auto', 'auto'],
                        font: ['Arial, Helvetica, sans-serif', 24, 'rgba(22,32,157,1.00)', 'normal', 'none', ''],
                        id: 'EnjoyShow',
                        text: 'Enjoy our show...<br>but come back and play with me!',
                        display: 'none',
                        type: 'text'
                    },
                    {
                        id: 'GenericButton',
                        type: 'rect',
                        rect: ['206px', '322px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        rect: ['239px', '325px', 'auto', 'auto', 'auto', 'auto'],
                        font: ['Roboto, Arial, sans-serif', 18, 'rgba(255,255,255,1.00)', '300', 'none', ''],
                        id: 'Text',
                        text: 'Start',
                        align: 'center',
                        type: 'text'
                    }]
                }
            ],
            symbolInstances: [
            {
                id: 'CheckShowtime',
                symbolName: 'CheckShowtime',
                autoPlay: {

               }
            },
            {
                id: 'GenericButton',
                symbolName: 'GenericButton',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_Text3}": [
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "top", '103px'],
                ["style", "left", '-311px'],
                ["style", "width", '169px']
            ],
            "${_bot_01}": [
                ["style", "height", '283px'],
                ["style", "top", '88px'],
                ["style", "left", '763px'],
                ["style", "width", '395px']
            ],
            "${_ClickSurface}": [
                ["style", "height", '100%'],
                ["style", "left", '0%'],
                ["style", "top", '0%']
            ],
            "${_EnjoyShow}": [
                ["style", "display", 'none'],
                ["color", "color", 'rgba(22,32,157,1.00)'],
                ["style", "left", '51px'],
                ["style", "top", '80px']
            ],
            "${symbolSelector}": [
                ["style", "height", '57px'],
                ["style", "width", '220px']
            ],
            "${_bot_speechbubble_01}": [
                ["style", "height", '160px'],
                ["style", "top", '70px'],
                ["style", "left", '-342px'],
                ["style", "width", '234px']
            ],
            "${_GenericButton}": [
                ["style", "left", '206px'],
                ["style", "top", '322px']
            ],
            "${_TextCopy}": [
                ["style", "top", '325px'],
                ["style", "font-weight", '300'],
                ["color", "color", 'rgba(255,255,255,1.00)'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "left", '239px'],
                ["style", "font-size", '18px']
            ],
            "${_Text}": [
                ["style", "top", '325px'],
                ["style", "text-align", 'center'],
                ["style", "font-weight", '300'],
                ["color", "color", 'rgba(255,255,255,1.00)'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "left", '239px'],
                ["style", "font-size", '18px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3000,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Showtime": 2000
            },
            timeline: [
                { id: "eid788", tween: [ "style", "${_bot_01}", "left", '188px', { fromValue: '763px'}], position: 0, duration: 428, easing: "easeInOutQuad" },
                { id: "eid810", tween: [ "style", "${_bot_01}", "left", '-419px', { fromValue: '188px'}], position: 750, duration: 280 },
                { id: "eid812", tween: [ "style", "${_bot_01}", "left", '188px', { fromValue: '-419px'}], position: 2000, duration: 0 },
                { id: "eid796", tween: [ "style", "${_GenericButton}", "top", '322px', { fromValue: '322px'}], position: 750, duration: 0 },
                { id: "eid800", tween: [ "style", "${_GenericButton}", "top", '322px', { fromValue: '322px'}], position: 1030, duration: 0, easing: "easeInOutQuad" },
                { id: "eid821", tween: [ "style", "${_Text3}", "left", '54px', { fromValue: '-311px'}], position: 340, duration: 344, easing: "easeOutQuad" },
                { id: "eid824", tween: [ "style", "${_Text3}", "left", '-551px', { fromValue: '54px'}], position: 750, duration: 280 },
                { id: "eid808", tween: [ "style", "${_GenericButton}", "left", '-401px', { fromValue: '206px'}], position: 750, duration: 280 },
                { id: "eid819", tween: [ "style", "${_Text3}", "top", '103px', { fromValue: '103px'}], position: 750, duration: 0 },
                { id: "eid783", tween: [ "style", "${_EnjoyShow}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid740", tween: [ "style", "${_EnjoyShow}", "display", 'none', { fromValue: 'none'}], position: 2000, duration: 0 },
                { id: "eid785", tween: [ "style", "${_bot_01}", "top", '88px', { fromValue: '88px'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid787", tween: [ "style", "${_bot_01}", "top", '89px', { fromValue: '88px'}], position: 428, duration: 0, easing: "easeInOutQuad" },
                { id: "eid804", tween: [ "style", "${_bot_01}", "top", '89px', { fromValue: '89px'}], position: 1030, duration: 0, easing: "easeInOutQuad" },
                { id: "eid790", tween: [ "style", "${_bot_speechbubble_01}", "top", '70px', { fromValue: '70px'}], position: 340, duration: 0, easing: "easeInOutQuad" },
                { id: "eid806", tween: [ "style", "${_bot_speechbubble_01}", "top", '70px', { fromValue: '70px'}], position: 1030, duration: 0, easing: "easeInOutQuad" },
                { id: "eid807", tween: [ "style", "${_Text}", "left", '-368px', { fromValue: '239px'}], position: 750, duration: 280 },
                { id: "eid792", tween: [ "style", "${_bot_speechbubble_01}", "left", '23px', { fromValue: '-342px'}], position: 340, duration: 344, easing: "easeOutQuad" },
                { id: "eid809", tween: [ "style", "${_bot_speechbubble_01}", "left", '-584px', { fromValue: '23px'}], position: 750, duration: 280 },
                { id: "eid811", tween: [ "style", "${_bot_speechbubble_01}", "left", '23px', { fromValue: '-584px'}], position: 2000, duration: 0 },
                { id: "eid1160", tween: [ "style", "${_Text}", "top", '322px', { fromValue: '325px'}], position: 750, duration: 21 },
                { id: "eid1161", tween: [ "style", "${_Text}", "top", '325px', { fromValue: '322px'}], position: 771, duration: 259 }            ]
        }
    }
},
"DoneButton": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'GenericButton',
                    type: 'rect',
                    rect: ['0px', '0px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(255,255,255,1.00)', '300', 'none', ''],
                    type: 'text',
                    id: 'Text',
                    text: 'Done',
                    align: 'center',
                    rect: ['0px', '0px', 'auto', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            {
                id: 'GenericButton',
                symbolName: 'GenericButton',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_TextCopy}": [
                ["color", "color", 'rgba(255,255,255,1)'],
                ["style", "font-weight", '300'],
                ["style", "left", '205px'],
                ["style", "font-size", '18px'],
                ["style", "top", '355px'],
                ["style", "height", '29px'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "width", '105px'],
                ["style", "opacity", '0']
            ],
            "${_GenericButton}": [
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${_Text}": [
                ["color", "color", 'rgba(255,255,255,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-1px'],
                ["style", "font-size", '18px'],
                ["style", "top", '3px'],
                ["style", "text-align", 'center'],
                ["style", "height", '29px'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "width", '105px'],
                ["style", "opacity", '1']
            ],
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '85px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: false,
            timeline: [
                { id: "eid1262", tween: [ "style", "${_Text}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0 },
                { id: "eid1264", tween: [ "style", "${_Text}", "left", '-1px', { fromValue: '-1px'}], position: 0, duration: 0 },
                { id: "eid1263", tween: [ "style", "${_Text}", "top", '3px', { fromValue: '3px'}], position: 0, duration: 0 }            ]
        }
    }
},
"PasswordWindow": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    type: 'rect',
                    id: 'Background',
                    stroke: [0, 'rgb(0, 0, 0)', 'solid'],
                    rect: ['0px', '0px', '100%', '99%', 'auto', 'auto'],
                    fill: ['rgba(255,255,255,1.00)']
                },
                {
                    rect: ['21.9%', '21%', '56.2%', '57.7%', 'auto', 'auto'],
                    sizeRange: ['0px', '', '', ''],
                    id: 'Frame',
                    stroke: [14, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(255,255,255,1.00)']
                },
                {
                    id: 'PasswordButton6',
                    type: 'rect',
                    rect: ['53.5%', '60.6%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'PasswordButton5',
                    type: 'rect',
                    rect: ['53.5%', '43.6%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'PasswordButton4',
                    type: 'rect',
                    rect: ['53.5%', '26.7%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'PasswordButton3',
                    type: 'rect',
                    rect: ['31.5%', '60.6%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'PasswordButton2',
                    type: 'rect',
                    rect: ['31.5%', '43.6%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'PasswordButton1',
                    type: 'rect',
                    rect: ['31.5%', '26.7%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'RestartUI',
                    rect: ['51.4%', '62.5%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'SuspendButton',
                    rect: ['27.9%', '62.5%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'CalibrateButton',
                    rect: ['52.2%', '45.8%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'HomeButton',
                    rect: ['27.9%', '46.1%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'DeEnergizeButton',
                    rect: ['52%', '35.9%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'EnergizeButton',
                    rect: ['27.9%', '36.2%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'ShowNowButton',
                    rect: ['30.9%', '38.5%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'ShowInFiveButton',
                    rect: ['27.9%', '32.3%', 'auto', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            {
                id: 'ShowInFiveButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'RestartUI',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'ShowNowButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'PasswordButton5',
                symbolName: 'PasswordButton',
                autoPlay: {

               }
            },
            {
                id: 'PasswordButton4',
                symbolName: 'PasswordButton',
                autoPlay: {

               }
            },
            {
                id: 'HomeButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'PasswordButton1',
                symbolName: 'PasswordButton',
                autoPlay: {

               }
            },
            {
                id: 'PasswordButton2',
                symbolName: 'PasswordButton',
                autoPlay: {

               }
            },
            {
                id: 'PasswordButton6',
                symbolName: 'PasswordButton',
                autoPlay: {

               }
            },
            {
                id: 'EnergizeButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'DeEnergizeButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'CalibrateButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'PasswordButton3',
                symbolName: 'PasswordButton',
                autoPlay: {

               }
            },
            {
                id: 'SuspendButton',
                symbolName: 'OpButton',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_PasswordButton5}": [
                ["style", "top", '43.64%'],
                ["style", "opacity", '0'],
                ["style", "left", '53.52%']
            ],
            "${_PasswordButton1}": [
                ["style", "top", '26.71%'],
                ["style", "opacity", '0'],
                ["style", "left", '31.45%']
            ],
            "${_CalibrateButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '51.37%'],
                ["style", "top", '45.83%']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_RestartUI}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '51.37%'],
                ["style", "top", '56.25%']
            ],
            "${_ShowInFiveButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '27.93%'],
                ["style", "top", '26.04%']
            ],
            "${_Frame}": [
                ["style", "-webkit-transform-origin", [50,50], {valueTemplate:'@@0@@% @@1@@%'} ],
                ["style", "-moz-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-ms-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "msTransformOrigin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-o-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["transform", "scaleX", '1'],
                ["style", "border-style", 'none'],
                ["style", "left", '21.88%'],
                ["style", "width", '56.2%'],
                ["style", "top", '20.98%'],
                ["style", "opacity", '0'],
                ["style", "min-width", '0px'],
                ["style", "max-width", 'none'],
                ["style", "height", '57.7%'],
                ["style", "border-width", '14px'],
                ["transform", "scaleY", '1'],
                ["color", "background-color", 'rgba(255,255,255,1.00)']
            ],
            "${_HomeButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '27.93%'],
                ["style", "top", '46.09%']
            ],
            "${_SuspendButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '27.93%'],
                ["style", "top", '56.25%']
            ],
            "${_DeEnergizeButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '51.37%'],
                ["style", "top", '35.94%']
            ],
            "${_ShowNowButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '51.37%'],
                ["style", "top", '25.78%']
            ],
            "${_PasswordButton4}": [
                ["style", "top", '26.71%'],
                ["style", "opacity", '0'],
                ["style", "left", '53.52%']
            ],
            "${_PasswordButton6}": [
                ["style", "top", '60.56%'],
                ["style", "opacity", '0'],
                ["style", "left", '53.52%']
            ],
            "${_PasswordButton2}": [
                ["style", "top", '43.64%'],
                ["style", "opacity", '0'],
                ["style", "left", '31.45%']
            ],
            "${_EnergizeButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '27.93%'],
                ["style", "top", '36.2%']
            ],
            "${_PasswordButton3}": [
                ["style", "top", '60.56%'],
                ["style", "opacity", '0'],
                ["style", "left", '31.45%']
            ],
            "${_Background}": [
                ["style", "top", '0px'],
                ["color", "background-color", 'rgba(255,255,255,1.00)'],
                ["style", "left", '0px'],
                ["style", "opacity", '0'],
                ["style", "height", '100%'],
                ["style", "border-style", 'solid'],
                ["style", "border-width", '0px'],
                ["style", "width", '100%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Close": 2000,
                "ValidPW": 3000
            },
            timeline: [
                { id: "eid348", tween: [ "style", "${_ShowNowButton}", "left", '51.37%', { fromValue: '51.37%'}], position: 3250, duration: 0 },
                { id: "eid354", tween: [ "style", "${_ShowNowButton}", "top", '25.78%', { fromValue: '25.78%'}], position: 3250, duration: 0 },
                { id: "eid619", tween: [ "style", "${_RestartUI}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid353", tween: [ "style", "${_ShowInFiveButton}", "top", '26.04%', { fromValue: '26.04%'}], position: 3250, duration: 0 },
                { id: "eid734", tween: [ "style", "${_SuspendButton}", "top", '56.25%', { fromValue: '56.25%'}], position: 3250, duration: 0 },
                { id: "eid303", tween: [ "style", "${_PasswordButton4}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid297", tween: [ "style", "${_PasswordButton4}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid314", tween: [ "style", "${_PasswordButton4}", "opacity", '0', { fromValue: '1'}], position: 3002, duration: 248 },
                { id: "eid351", tween: [ "style", "${_ShowInFiveButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid742", tween: [ "style", "${_EnergizeButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid745", tween: [ "style", "${_DeEnergizeButton}", "left", '51.37%', { fromValue: '51.37%'}], position: 3250, duration: 0 },
                { id: "eid305", tween: [ "style", "${_PasswordButton1}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid299", tween: [ "style", "${_PasswordButton1}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid333", tween: [ "style", "${_HomeButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid337", tween: [ "style", "${_CalibrateButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid620", tween: [ "style", "${_RestartUI}", "top", '56.25%', { fromValue: '56.25%'}], position: 3250, duration: 0 },
                { id: "eid735", tween: [ "style", "${_SuspendButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid304", tween: [ "style", "${_PasswordButton6}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid298", tween: [ "style", "${_PasswordButton6}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid744", tween: [ "style", "${_DeEnergizeButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid347", tween: [ "style", "${_ShowNowButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid741", tween: [ "style", "${_EnergizeButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid279", tween: [ "style", "${_Frame}", "opacity", '0.75168320528455', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid280", tween: [ "style", "${_Frame}", "opacity", '0', { fromValue: '0.75168320528455'}], position: 2000, duration: 250 },
                { id: "eid293", tween: [ "style", "${_Frame}", "opacity", '0.74523669918699', { fromValue: '0'}], position: 3000, duration: 0 },
                { id: "eid302", tween: [ "style", "${_PasswordButton5}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid296", tween: [ "style", "${_PasswordButton5}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid301", tween: [ "style", "${_PasswordButton2}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid295", tween: [ "style", "${_PasswordButton2}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid315", tween: [ "style", "${_PasswordButton2}", "opacity", '0', { fromValue: '1'}], position: 3002, duration: 248 },
                { id: "eid743", tween: [ "style", "${_DeEnergizeButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid618", tween: [ "style", "${_RestartUI}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid736", tween: [ "style", "${_SuspendButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid338", tween: [ "style", "${_CalibrateButton}", "left", '51.37%', { fromValue: '51.37%'}], position: 3250, duration: 0 },
                { id: "eid336", tween: [ "style", "${_CalibrateButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid346", tween: [ "style", "${_ShowNowButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid300", tween: [ "style", "${_PasswordButton3}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid294", tween: [ "style", "${_PasswordButton3}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid335", tween: [ "style", "${_HomeButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid350", tween: [ "style", "${_ShowInFiveButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 }            ]
        }
    }
},
"PasswordButton": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    rect: ['0px', '0px', '100%', '100%', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    id: 'RoundRect',
                    stroke: [14, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(46,56,45,1.00)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_RoundRect}": [
                ["color", "background-color", 'rgba(46,56,45,1.00)'],
                ["style", "top", '0px'],
                ["style", "height", '100%'],
                ["style", "border-style", 'none'],
                ["style", "left", '0px'],
                ["style", "width", '100%']
            ],
            "${symbolSelector}": [
                ["style", "height", '10.14%'],
                ["style", "width", '15.1%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: false,
            timeline: [
            ]
        }
    }
},
"OpButton": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'ButtonGroup',
                    type: 'group',
                    rect: ['0%', '0%', '100%', '100%', 'auto', 'auto'],
                    c: [
                    {
                        rect: ['0%', '0%', '100%', '100%', 'auto', 'auto'],
                        borderRadius: ['10px', '10px', '10px', '10px'],
                        opacity: 1,
                        id: 'ButtonFrame',
                        stroke: [14, 'rgb(0, 0, 0)', 'none'],
                        type: 'rect',
                        fill: ['rgba(79,79,79,1.00)']
                    },
                    {
                        rect: ['0%', '20%', '100%', '60%', 'auto', 'auto'],
                        font: ['Verdana, Geneva, sans-serif', [91.6, '%'], 'rgba(255,255,255,1.00)', '300', 'none', ''],
                        opacity: 1,
                        id: 'ButtonLabel',
                        text: '(title)',
                        align: 'center',
                        type: 'text'
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_ButtonFrame}": [
                ["style", "top", '0%'],
                ["color", "background-color", 'rgba(32,255,0,1.00)'],
                ["style", "opacity", '1'],
                ["style", "height", '100%'],
                ["style", "border-style", 'none'],
                ["style", "left", '0%'],
                ["style", "width", '100%']
            ],
            "${_ButtonLabel}": [
                ["color", "color", 'rgba(255,255,255,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '0%'],
                ["style", "width", '100%'],
                ["style", "top", '20%'],
                ["style", "text-align", 'center'],
                ["style", "height", '60%'],
                ["style", "font-family", 'Verdana, Geneva, sans-serif'],
                ["style", "font-weight", '300'],
                ["style", "font-size", '91.6%']
            ],
            "${symbolSelector}": [
                ["style", "height", '8.2%'],
                ["style", "width", '20.5%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 1396,
            autoPlay: true,
            labels: {
                "Start": 0,
                "Touch": 1000,
                "Repeat": 1198
            },
            timeline: [
                { id: "eid331", tween: [ "color", "${_ButtonFrame}", "background-color", 'rgba(95,187,183,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(32,255,0,1.00)'}], position: 1000, duration: 100 },
                { id: "eid332", tween: [ "color", "${_ButtonFrame}", "background-color", 'rgba(79,79,79,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(95,187,183,1.00)'}], position: 1100, duration: 98 },
                { id: "eid616", tween: [ "color", "${_ButtonFrame}", "background-color", 'rgba(95,187,183,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(79,79,79,1)'}], position: 1198, duration: 100 },
                { id: "eid617", tween: [ "color", "${_ButtonFrame}", "background-color", 'rgba(79,79,79,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(95,187,183,1.00)'}], position: 1298, duration: 98 }            ]
        }
    }
},
"OSSymbol": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    transform: [[0, 0], [], [], ['0.1', '0.1']],
                    rect: ['-853px', '-339px', '1840px', '710px', 'auto', 'auto'],
                    id: 'temp_layer_gold_01',
                    opacity: 0.70172764227642,
                    type: 'image',
                    fill: ['rgba(0,0,0,0)', 'images/temp_layer_gold_01.png', '0px', '0px']
                },
                {
                    id: 'status_pending_01',
                    type: 'image',
                    rect: ['146px', '0', '130px', '33px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_pending_01.png', '0px', '0px']
                },
                {
                    id: 'status_executing_01',
                    type: 'image',
                    rect: ['146px', '0', '129px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_executing_01.png', '0px', '0px']
                },
                {
                    id: 'status_done_01',
                    type: 'image',
                    rect: ['146px', '0', '129px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_done_01.png', '0px', '0px']
                },
                {
                    type: 'rect',
                    id: 'Layer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    rect: ['0px', '0px', '131px', '29px', 'auto', 'auto'],
                    fill: ['rgba(198,165,18,0.00)'],
                    c: [
                    {
                        font: ['Roboto, Arial, sans-serif', 16, 'rgba(255,255,255,1)', '300', 'none', 'normal'],
                        type: 'text',
                        id: 'Layername',
                        text: 'OS Gold',
                        align: 'center',
                        rect: ['0px', '4px', '131px', '29px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '131px']
            ],
            "${_status_pending_01}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_status_executing_01}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_status_done_01}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_Layername}": [
                ["style", "top", '4px'],
                ["style", "font-size", '16px'],
                ["style", "text-align", 'center'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "height", '29px'],
                ["style", "font-weight", '300'],
                ["style", "left", '0px'],
                ["style", "width", '131px']
            ],
            "${_Layer}": [
                ["color", "background-color", 'rgba(198,165,18,0.00)'],
                ["style", "bottom", 'auto'],
                ["style", "right", 'auto'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${_temp_layer_gold_01}": [
                ["style", "top", '-339px'],
                ["style", "height", '710px'],
                ["style", "opacity", '0.701728'],
                ["style", "left", '-853px'],
                ["style", "width", '1840px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3500,
            autoPlay: true,
            labels: {
                "Deployed": 1000,
                "Pending": 2008,
                "Executing": 3000
            },
            timeline: [
                { id: "eid1147", tween: [ "style", "${_status_pending_01}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid1148", tween: [ "style", "${_status_pending_01}", "opacity", '0.35', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1150", tween: [ "style", "${_status_pending_01}", "opacity", '0', { fromValue: '0.35'}], position: 3000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1143", tween: [ "style", "${_status_executing_01}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid1144", tween: [ "style", "${_status_executing_01}", "opacity", '0', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1146", tween: [ "style", "${_status_executing_01}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1188", tween: [ "style", "${_temp_layer_gold_01}", "opacity", '0.701728', { fromValue: '0.701728'}], position: 1000, duration: 0 },
                { id: "eid1191", tween: [ "style", "${_temp_layer_gold_01}", "opacity", '0.1', { fromValue: '0.701728'}], position: 2000, duration: 0 },
                { id: "eid1199", tween: [ "style", "${_temp_layer_gold_01}", "opacity", '1', { fromValue: '0.1'}], position: 3000, duration: 0 },
                { id: "eid1139", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid1140", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1142", tween: [ "style", "${_status_done_01}", "opacity", '0', { fromValue: '0'}], position: 3000, duration: 0, easing: "easeOutQuad" }            ]
        }
    }
},
"PersonalizationSymbol": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    transform: [[0, 0], [], [], ['0.1', '0.1']],
                    rect: ['-854px', '-340px', '1840px', '710px', 'auto', 'auto'],
                    id: 'temp_layer_blue_01',
                    opacity: 0.7,
                    type: 'image',
                    fill: ['rgba(0,0,0,0)', 'images/temp_layer_blue_01.png', '0px', '0px']
                },
                {
                    id: 'status_pending_01Copy',
                    type: 'image',
                    rect: ['146px', '0', '130px', '33px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_pending_01.png', '0px', '0px']
                },
                {
                    id: 'status_executing_01Copy',
                    type: 'image',
                    rect: ['146px', '0', '129px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_executing_01.png', '0px', '0px']
                },
                {
                    id: 'status_done_01Copy',
                    type: 'image',
                    rect: ['146px', '0', '129px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/status_done_01.png', '0px', '0px']
                },
                {
                    type: 'rect',
                    id: 'Layer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    rect: ['0px', '0px', '131px', '29px', 'auto', 'auto'],
                    fill: ['rgba(72,148,187,0.00)'],
                    c: [
                    {
                        font: ['Roboto, Arial, sans-serif', 16, 'rgba(255,255,255,1)', '300', 'none', 'normal'],
                        type: 'text',
                        id: 'Layername',
                        text: 'Personalization',
                        align: 'center',
                        rect: ['0px', '4px', '131px', '29px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_status_pending_01Copy}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_temp_layer_blue_01}": [
                ["style", "top", '-340px'],
                ["style", "height", '710px'],
                ["style", "opacity", '0.700000'],
                ["style", "left", '-854px'],
                ["style", "width", '1840px']
            ],
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '131px']
            ],
            "${_status_done_01Copy}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ],
            "${_Layername}": [
                ["style", "top", '4px'],
                ["style", "width", '131px'],
                ["style", "text-align", 'center'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "height", '29px'],
                ["style", "font-weight", '300'],
                ["style", "left", '0px'],
                ["style", "font-size", '16px']
            ],
            "${_Layer}": [
                ["color", "background-color", 'rgba(72,148,187,0.00)'],
                ["style", "bottom", 'auto'],
                ["style", "right", 'auto'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${_status_executing_01Copy}": [
                ["style", "left", '146px'],
                ["style", "opacity", '0']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3500,
            autoPlay: true,
            labels: {
                "Deployed": 1000,
                "Pending": 2013,
                "Executing": 3000
            },
            timeline: [
                { id: "eid1157", tween: [ "style", "${_status_pending_01Copy}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid1158", tween: [ "style", "${_status_pending_01Copy}", "opacity", '0.35', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1159", tween: [ "style", "${_status_pending_01Copy}", "opacity", '0', { fromValue: '0.35'}], position: 3000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1154", tween: [ "style", "${_status_executing_01Copy}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid1155", tween: [ "style", "${_status_executing_01Copy}", "opacity", '0', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1156", tween: [ "style", "${_status_executing_01Copy}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1194", tween: [ "style", "${_temp_layer_blue_01}", "opacity", '0.700000', { fromValue: '0.700000'}], position: 1000, duration: 0 },
                { id: "eid1198", tween: [ "style", "${_temp_layer_blue_01}", "opacity", '0.1', { fromValue: '0.700000'}], position: 2000, duration: 0 },
                { id: "eid1197", tween: [ "style", "${_temp_layer_blue_01}", "opacity", '1', { fromValue: '0.100000'}], position: 3000, duration: 0 },
                { id: "eid1151", tween: [ "style", "${_status_done_01Copy}", "opacity", '0', { fromValue: '0'}], position: 1002, duration: 0, easing: "easeOutQuad" },
                { id: "eid1152", tween: [ "style", "${_status_done_01Copy}", "opacity", '0', { fromValue: '0'}], position: 2000, duration: 0, easing: "easeOutQuad" },
                { id: "eid1153", tween: [ "style", "${_status_done_01Copy}", "opacity", '0', { fromValue: '0'}], position: 3000, duration: 0, easing: "easeOutQuad" }            ]
        }
    }
},
"Background": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    font: ['Lucida Sans Unicode, Lucida Grande, sans-serif', 27, 'rgba(132,132,132,1.00)', '400', 'none', 'normal'],
                    type: 'text',
                    id: 'Console',
                    text: 'Unibot Management Console',
                    align: 'center',
                    rect: ['auto', 'auto', 'auto', 'auto', '311px', '201px']
                },
                {
                    font: ['Lucida Sans Unicode, Lucida Grande, sans-serif', 61, 'rgba(180,180,180,1.00)', '400', 'none', 'normal'],
                    type: 'text',
                    align: 'center',
                    id: 'UMC',
                    opacity: 0.49253683943089,
                    text: 'UMC',
                    rect: ['auto', 'auto', 'auto', 'auto', '311px', '201px']
                },
                {
                    rect: ['-11%', '44.1%', '111%', '0.7%', 'auto', 'auto'],
                    transform: [[0, 0], ['-37'], [], ['1.34615']],
                    id: 'Rectangle',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(231,111,111,1.00)']
                },
                {
                    rect: ['-7.6%', '47.8%', '111%', '0.7%', 'auto', 'auto'],
                    transform: [[0, 0], ['-37'], [], ['1.34615']],
                    id: 'RectangleCopy',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(231,111,111,1.00)']
                },
                {
                    rect: ['0.1%', '55.3%', '111%', '0.7%', 'auto', 'auto'],
                    transform: [[0, 0], ['-37'], [], ['1.34615']],
                    id: 'RectangleCopy2',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(231,111,111,1.00)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_UMC}": [
                ["style", "bottom", '11px'],
                ["color", "color", 'rgba(180,180,180,1.00)'],
                ["style", "right", '26px'],
                ["style", "left", 'auto'],
                ["style", "font-size", '61px'],
                ["style", "top", 'auto'],
                ["transform", "skewY", '0deg'],
                ["transform", "skewX", '0deg'],
                ["style", "height", '64px'],
                ["style", "font-family", 'Lucida Sans Unicode, Lucida Grande, sans-serif'],
                ["style", "opacity", '0.49253683943089'],
                ["style", "width", 'auto']
            ],
            "${_RectangleCopy}": [
                ["style", "top", '47.84%'],
                ["style", "bottom", 'auto'],
                ["color", "background-color", 'rgba(231,111,111,1.00)'],
                ["transform", "rotateZ", '-37deg'],
                ["transform", "scaleX", '1.34615'],
                ["style", "right", 'auto'],
                ["style", "left", '-7.6%'],
                ["style", "width", '110.95%']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '99.15%']
            ],
            "${_Rectangle}": [
                ["color", "background-color", 'rgba(231,111,111,1.00)'],
                ["style", "bottom", 'auto'],
                ["style", "top", '44.06%'],
                ["transform", "rotateZ", '-37deg'],
                ["transform", "scaleX", '1.34615'],
                ["style", "right", 'auto'],
                ["style", "left", '-11.03%'],
                ["style", "width", '110.95%']
            ],
            "${_Console}": [
                ["style", "bottom", '-21px'],
                ["color", "color", 'rgba(132,132,132,1.00)'],
                ["style", "right", '11px'],
                ["style", "left", 'auto'],
                ["style", "font-size", '27px'],
                ["style", "top", 'auto'],
                ["transform", "skewY", '0deg'],
                ["transform", "skewX", '0deg'],
                ["style", "height", '64px'],
                ["style", "font-family", 'Lucida Sans Unicode, Lucida Grande, sans-serif'],
                ["style", "width", 'auto']
            ],
            "${_RectangleCopy2}": [
                ["color", "background-color", 'rgba(231,111,111,1.00)'],
                ["style", "bottom", 'auto'],
                ["style", "top", '55.29%'],
                ["transform", "rotateZ", '-37deg'],
                ["transform", "scaleX", '1.34615'],
                ["style", "right", 'auto'],
                ["style", "left", '0.08%'],
                ["style", "width", '110.95%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4000,
            autoPlay: true,
            timeline: [
                { id: "eid581", tween: [ "style", "${_RectangleCopy2}", "top", '58.08%', { fromValue: '55.29%'}], position: 0, duration: 1085, easing: "swing" },
                { id: "eid582", tween: [ "style", "${_RectangleCopy2}", "top", '59.49%', { fromValue: '58.08%'}], position: 1085, duration: 773, easing: "swing" },
                { id: "eid583", tween: [ "style", "${_RectangleCopy2}", "top", '52.15%', { fromValue: '59.49%'}], position: 1857, duration: 1051, easing: "swing" },
                { id: "eid584", tween: [ "style", "${_RectangleCopy2}", "top", '55.29%', { fromValue: '52.15%'}], position: 2908, duration: 1092, easing: "swing" },
                { id: "eid497", tween: [ "style", "${_Console}", "bottom", '-26px', { fromValue: '-21px'}], position: 0, duration: 881, easing: "swing" },
                { id: "eid498", tween: [ "style", "${_Console}", "bottom", '-7px', { fromValue: '-26px'}], position: 881, duration: 754, easing: "swing" },
                { id: "eid515", tween: [ "style", "${_Console}", "bottom", '-31px', { fromValue: '-7px'}], position: 1635, duration: 1258, easing: "swing" },
                { id: "eid527", tween: [ "style", "${_Console}", "bottom", '-21px', { fromValue: '-31px'}], position: 2893, duration: 1107, easing: "swing" },
                { id: "eid747", tween: [ "style", "${_UMC}", "bottom", '0px', { fromValue: '11px'}], position: 0, duration: 881, easing: "swing" },
                { id: "eid749", tween: [ "style", "${_UMC}", "bottom", '4px', { fromValue: '0px'}], position: 881, duration: 906, easing: "swing" },
                { id: "eid756", tween: [ "style", "${_UMC}", "bottom", '6px', { fromValue: '4px'}], position: 1787, duration: 1027, easing: "swing" },
                { id: "eid758", tween: [ "style", "${_UMC}", "bottom", '11px', { fromValue: '6px'}], position: 2814, duration: 1186, easing: "swing" },
                { id: "eid577", tween: [ "style", "${_RectangleCopy2}", "left", '3.48%', { fromValue: '0.08%'}], position: 0, duration: 860, easing: "swing" },
                { id: "eid578", tween: [ "style", "${_RectangleCopy2}", "left", '5.62%', { fromValue: '3.48%'}], position: 860, duration: 1254, easing: "swing" },
                { id: "eid579", tween: [ "style", "${_RectangleCopy2}", "left", '0.08%', { fromValue: '5.62%'}], position: 2114, duration: 1177, easing: "swing" },
                { id: "eid580", tween: [ "style", "${_RectangleCopy2}", "left", '0.08%', { fromValue: '0.08%'}], position: 3292, duration: 708, easing: "swing" },
                { id: "eid751", tween: [ "style", "${_UMC}", "right", '35px', { fromValue: '26px'}], position: 0, duration: 881, easing: "swing" },
                { id: "eid755", tween: [ "style", "${_UMC}", "right", '31px', { fromValue: '35px'}], position: 881, duration: 286, easing: "swing" },
                { id: "eid752", tween: [ "style", "${_UMC}", "right", '27px', { fromValue: '31px'}], position: 1167, duration: 620, easing: "swing" },
                { id: "eid753", tween: [ "style", "${_UMC}", "right", '10px', { fromValue: '27px'}], position: 1787, duration: 796, easing: "swing" },
                { id: "eid757", tween: [ "style", "${_UMC}", "right", '8px', { fromValue: '10px'}], position: 2583, duration: 667, easing: "swing" },
                { id: "eid754", tween: [ "style", "${_UMC}", "right", '26px', { fromValue: '8px'}], position: 3250, duration: 750, easing: "swing" },
                { id: "eid565", tween: [ "style", "${_Rectangle}", "top", '41.7%', { fromValue: '44.06%'}], position: 0, duration: 435, easing: "swing" },
                { id: "eid566", tween: [ "style", "${_Rectangle}", "top", '44.82%', { fromValue: '41.7%'}], position: 435, duration: 1200, easing: "swing" },
                { id: "eid567", tween: [ "style", "${_Rectangle}", "top", '38.6%', { fromValue: '44.82%'}], position: 1635, duration: 1179, easing: "swing" },
                { id: "eid568", tween: [ "style", "${_Rectangle}", "top", '44.06%', { fromValue: '38.6%'}], position: 2814, duration: 1186, easing: "swing" },
                { id: "eid573", tween: [ "style", "${_RectangleCopy}", "top", '49.68%', { fromValue: '47.84%'}], position: 0, duration: 750, easing: "swing" },
                { id: "eid574", tween: [ "style", "${_RectangleCopy}", "top", '44.82%', { fromValue: '49.68%'}], position: 750, duration: 1447, easing: "swing" },
                { id: "eid575", tween: [ "style", "${_RectangleCopy}", "top", '50.37%', { fromValue: '44.82%'}], position: 2197, duration: 711, easing: "swing" },
                { id: "eid576", tween: [ "style", "${_RectangleCopy}", "top", '47.84%', { fromValue: '50.37%'}], position: 2908, duration: 1092, easing: "swing" },
                { id: "eid499", tween: [ "style", "${_Console}", "right", '31px', { fromValue: '11px'}], position: 0, duration: 1167, easing: "swing" },
                { id: "eid500", tween: [ "style", "${_Console}", "right", '13px', { fromValue: '31px'}], position: 1167, duration: 620, easing: "swing" },
                { id: "eid516", tween: [ "style", "${_Console}", "right", '0px', { fromValue: '13px'}], position: 1787, duration: 1463, easing: "swing" },
                { id: "eid528", tween: [ "style", "${_Console}", "right", '11px', { fromValue: '0px'}], position: 3250, duration: 750, easing: "swing" },
                { id: "eid569", tween: [ "style", "${_RectangleCopy}", "left", '-5.47%', { fromValue: '-7.6%'}], position: 0, duration: 553, easing: "swing" },
                { id: "eid570", tween: [ "style", "${_RectangleCopy}", "left", '-6.67%', { fromValue: '-5.47%'}], position: 553, duration: 1835, easing: "swing" },
                { id: "eid571", tween: [ "style", "${_RectangleCopy}", "left", '-4.7%', { fromValue: '-6.67%'}], position: 2388, duration: 698, easing: "swing" },
                { id: "eid572", tween: [ "style", "${_RectangleCopy}", "left", '-7.6%', { fromValue: '-4.7%'}], position: 3086, duration: 914, easing: "swing" },
                { id: "eid561", tween: [ "style", "${_Rectangle}", "left", '-13.22%', { fromValue: '-11.03%'}], position: 0, duration: 1387, easing: "swing" },
                { id: "eid562", tween: [ "style", "${_Rectangle}", "left", '-9.99%', { fromValue: '-13.22%'}], position: 1387, duration: 1196, easing: "swing" },
                { id: "eid563", tween: [ "style", "${_Rectangle}", "left", '-11.49%', { fromValue: '-9.99%'}], position: 2584, duration: 1089, easing: "swing" },
                { id: "eid564", tween: [ "style", "${_Rectangle}", "left", '-11.03%', { fromValue: '-11.49%'}], position: 3673, duration: 327, easing: "swing" }            ]
        }
    }
},
"Build": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'bot_speechbubble_01',
                    type: 'image',
                    rect: ['23px', '70px', '176px', '120px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                },
                {
                    id: 'desktop_bg_01',
                    type: 'image',
                    rect: ['31px', '78px', '129px', '284px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/desktop_bg_01.png', '0px', '0px']
                },
                {
                    type: 'rect',
                    rect: ['3.9%', '22.1%', '33.6%', '63%', 'auto', 'auto'],
                    id: 'Desktop',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    display: 'block',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    id: 'OS',
                    type: 'rect',
                    rect: ['5.9%', '80.7%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer1',
                    type: 'rect',
                    rect: ['5.5%', '73.7%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer2',
                    type: 'rect',
                    rect: ['5.5%', '66.7%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer3',
                    type: 'rect',
                    rect: ['5.5%', '59.9%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer4',
                    type: 'rect',
                    rect: ['5.5%', '52.9%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Personalization',
                    type: 'rect',
                    rect: ['5.9%', '45.8%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'loadingcircle2',
                    type: 'rect',
                    rect: ['197px', '262px', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.5', '0.5']]
                },
                {
                    id: 'loadingDoneCheck',
                    type: 'rect',
                    rect: ['260', '161', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'bot_01',
                    type: 'image',
                    rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'speechbubble_text_1',
                    text: 'Nice! Hold on <br>while I build that desktop for you.',
                    align: 'center',
                    rect: ['54px', '107px', '169px', '120px', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'speechbubble_text_2',
                    text: 'That was easy. <br>But what about managing that desktop?',
                    align: 'center',
                    rect: ['54px', '103px', '169px', '120px', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            {
                id: 'Layer3',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer4',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'OS',
                symbolName: 'OSSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer1',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'loadingcircle2',
                symbolName: 'loadingcircle',
                autoPlay: {

               }
            },
            {
                id: 'Personalization',
                symbolName: 'PersonalizationSymbol',
                autoPlay: {

               }
            },
            {
                id: 'loadingDoneCheck',
                symbolName: 'loadingDoneCheck',
                autoPlay: {

               }
            },
            {
                id: 'Layer2',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_OS}": [
                ["style", "top", '80.73%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '0'],
                ["style", "left", '5.86%']
            ],
            "${_loadingcircle2}": [
                ["style", "top", '364px'],
                ["transform", "scaleY", '0.5'],
                ["transform", "scaleX", '0.5'],
                ["style", "opacity", '1'],
                ["style", "left", '197px']
            ],
            "${_speechbubble_text_1}": [
                ["style", "top", '107px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '169px']
            ],
            "${_Layer3}": [
                ["style", "top", '59.9%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '0'],
                ["style", "left", '5.86%']
            ],
            "${_desktop_bg_01}": [
                ["style", "top", '78px'],
                ["style", "height", '284px'],
                ["style", "left", '31px'],
                ["style", "width", '129px']
            ],
            "${_speechbubble_text_2}": [
                ["style", "top", '101px'],
                ["style", "height", '120px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '0'],
                ["style", "left", '174px'],
                ["style", "width", '169px']
            ],
            "${_Desktop}": [
                ["style", "top", '22.14%'],
                ["style", "height", '63.02%'],
                ["style", "opacity", '0'],
                ["style", "left", '3.91%'],
                ["style", "display", 'block']
            ],
            "${_Layer2}": [
                ["style", "top", '66.67%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '0'],
                ["style", "left", '5.86%']
            ],
            "${_bot_speechbubble_01}": [
                ["style", "top", '85px'],
                ["style", "height", '120px'],
                ["style", "opacity", '1'],
                ["style", "left", '521px'],
                ["style", "width", '176px']
            ],
            "${_Layer4}": [
                ["style", "top", '52.86%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '0'],
                ["style", "left", '5.86%']
            ],
            "${_Personalization}": [
                ["style", "top", '45.83%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '0'],
                ["style", "left", '5.86%']
            ],
            "${_bot_01}": [
                ["style", "top", '85px'],
                ["style", "height", '283px'],
                ["style", "left", '610px'],
                ["style", "width", '395px']
            ],
            "${_Layer1}": [
                ["style", "top", '73.7%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '0'],
                ["style", "left", '5.86%']
            ],
            "${_loadingDoneCheck}": [
                ["style", "top", '262px'],
                ["transform", "scaleY", '0.5'],
                ["transform", "scaleX", '0.5'],
                ["style", "opacity", '0.000000'],
                ["style", "left", '196px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 8000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Process": 2031,
                "Finish": 3500
            },
            timeline: [
                { id: "eid639", tween: [ "style", "${_Personalization}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid900", tween: [ "style", "${_bot_01}", "left", '258px', { fromValue: '610px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid910", tween: [ "style", "${_bot_01}", "left", '610px', { fromValue: '258px'}], position: 6983, duration: 17 },
                { id: "eid1316", tween: [ "style", "${_speechbubble_text_2}", "opacity", '0', { fromValue: '0'}], position: 0, duration: 0, easing: "easeInQuad" },
                { id: "eid1317", tween: [ "style", "${_speechbubble_text_2}", "opacity", '1', { fromValue: '0'}], position: 3500, duration: 0, easing: "easeInQuad" },
                { id: "eid1303", tween: [ "style", "${_speechbubble_text_2}", "left", '174px', { fromValue: '174px'}], position: 3627, duration: 0 },
                { id: "eid919", tween: [ "style", "${_speechbubble_text_2}", "left", '524px', { fromValue: '174px'}], position: 6983, duration: 17 },
                { id: "eid959", tween: [ "style", "${_bot_speechbubble_01}", "opacity", '0', { fromValue: '1'}], position: 2750, duration: 500 },
                { id: "eid1309", tween: [ "style", "${_bot_speechbubble_01}", "opacity", '1', { fromValue: '0'}], position: 3491, duration: 9 },
                { id: "eid643", tween: [ "style", "${_Desktop}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid653", tween: [ "style", "${_Desktop}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1182", tween: [ "style", "${_Layer3}", "top", '59.9%', { fromValue: '59.9%'}], position: 1500, duration: 0 },
                { id: "eid1183", tween: [ "style", "${_Layer3}", "top", '59.9%', { fromValue: '59.9%'}], position: 3500, duration: 0 },
                { id: "eid868", tween: [ "style", "${_bot_speechbubble_01}", "top", '85px', { fromValue: '85px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1311", tween: [ "style", "${_bot_speechbubble_01}", "top", '-135px', { fromValue: '85px'}], position: 3491, duration: 9 },
                { id: "eid1312", tween: [ "style", "${_bot_speechbubble_01}", "top", '85px', { fromValue: '-135px'}], position: 3500, duration: 127, easing: "easeInQuad" },
                { id: "eid869", tween: [ "style", "${_bot_speechbubble_01}", "top", '86px', { fromValue: '85px'}], position: 7000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid899", tween: [ "style", "${_bot_speechbubble_01}", "left", '169px', { fromValue: '521px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid909", tween: [ "style", "${_bot_speechbubble_01}", "left", '521px', { fromValue: '169px'}], position: 6983, duration: 17 },
                { id: "eid901", tween: [ "style", "${_loadingcircle2}", "left", '197px', { fromValue: '197px'}], position: 1000, duration: 0 },
                { id: "eid1220", tween: [ "style", "${_loadingcircle2}", "opacity", '1', { fromValue: '1'}], position: 1000, duration: 0 },
                { id: "eid1221", tween: [ "style", "${_loadingcircle2}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 0 },
                { id: "eid637", tween: [ "style", "${_Layer1}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid645", tween: [ "style", "${_OS}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid904", tween: [ "style", "${_loadingcircle2}", "top", '262px', { fromValue: '364px'}], position: 1000, duration: 500 },
                { id: "eid876", tween: [ "style", "${_bot_01}", "top", '85px', { fromValue: '85px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid878", tween: [ "style", "${_bot_01}", "top", '85px', { fromValue: '85px'}], position: 7000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1173", tween: [ "style", "${_Layer3}", "left", '5.9%', { fromValue: '5.86%'}], position: 1500, duration: 2000 },
                { id: "eid647", tween: [ "style", "${_Layer4}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid1169", tween: [ "style", "${_Layer1}", "left", '5.9%', { fromValue: '5.86%'}], position: 1500, duration: 2000 },
                { id: "eid1184", tween: [ "style", "${_Layer2}", "top", '66.67%', { fromValue: '66.67%'}], position: 1500, duration: 0 },
                { id: "eid1185", tween: [ "style", "${_Layer2}", "top", '66.67%', { fromValue: '66.67%'}], position: 3500, duration: 0 },
                { id: "eid649", tween: [ "style", "${_Layer2}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid1234", tween: [ "style", "${_loadingDoneCheck}", "opacity", '0.000000', { fromValue: '0.000000'}], position: 1000, duration: 0 },
                { id: "eid1235", tween: [ "style", "${_loadingDoneCheck}", "opacity", '1', { fromValue: '0.000000'}], position: 3500, duration: 0 },
                { id: "eid1177", tween: [ "style", "${_Layer2}", "left", '5.9%', { fromValue: '5.86%'}], position: 1500, duration: 2000 },
                { id: "eid1313", tween: [ "style", "${_speechbubble_text_2}", "top", '-119px', { fromValue: '101px'}], position: 3491, duration: 9 },
                { id: "eid1314", tween: [ "style", "${_speechbubble_text_2}", "top", '101px', { fromValue: '-119px'}], position: 3500, duration: 127, easing: "easeInQuad" },
                { id: "eid1165", tween: [ "style", "${_Layer4}", "left", '5.9%', { fromValue: '5.86%'}], position: 1500, duration: 2000 },
                { id: "eid924", tween: [ "style", "${_speechbubble_text_1}", "opacity", '1', { fromValue: '1'}], position: 1000, duration: 0 },
                { id: "eid958", tween: [ "style", "${_speechbubble_text_1}", "opacity", '0', { fromValue: '1'}], position: 2750, duration: 500 },
                { id: "eid921", tween: [ "style", "${_speechbubble_text_1}", "opacity", '0', { fromValue: '0'}], position: 3500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid879", tween: [ "style", "${_Desktop}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0 },
                { id: "eid1180", tween: [ "style", "${_Layer1}", "top", '73.7%', { fromValue: '73.7%'}], position: 1500, duration: 0 },
                { id: "eid1181", tween: [ "style", "${_Layer1}", "top", '73.7%', { fromValue: '73.7%'}], position: 3500, duration: 0 },
                { id: "eid898", tween: [ "style", "${_speechbubble_text_1}", "left", '175px', { fromValue: '524px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1178", tween: [ "style", "${_Layer4}", "top", '52.86%', { fromValue: '52.86%'}], position: 1500, duration: 0 },
                { id: "eid1179", tween: [ "style", "${_Layer4}", "top", '52.86%', { fromValue: '52.86%'}], position: 3500, duration: 0 },
                { id: "eid641", tween: [ "style", "${_Layer3}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" }            ]
        }
    }
},
"Startup": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    rect: ['17.4%', '15.4%', '65.2%', '71.9%', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    id: 'RoundRect',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'text',
                    rect: ['23.6%', '19.4%', 'auto', 'auto', 'auto', 'auto'],
                    id: 'Text',
                    text: 'Which controller is this?',
                    align: 'center',
                    font: ['Arial, Helvetica, sans-serif', 25, 'rgba(0,0,0,1.00)', '400', 'none', 'normal']
                },
                {
                    id: 'One',
                    type: 'rect',
                    rect: ['40.4%', '31.4%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Two',
                    type: 'rect',
                    rect: ['40.4%', '41.3%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'None',
                    type: 'rect',
                    rect: ['40.4%', '51.2%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    type: 'text',
                    rect: ['23.6%', '74.2%', '52.5%', '7%', 'auto', 'auto'],
                    id: 'RobotAddress',
                    text: '[ROBOT ADDRESS]',
                    align: 'left',
                    font: ['Arial, Helvetica, sans-serif', 24, 'rgba(0,0,0,1)', '400', 'none', 'normal']
                },
                {
                    type: 'text',
                    rect: ['23.6%', '64.9%', '52.5%', '7.3%', 'auto', 'auto'],
                    id: 'UseSimulatorCheckbox',
                    text: '[USE SIMULATOR]',
                    align: 'left',
                    font: ['Arial, Helvetica, sans-serif', 24, 'rgba(0,0,0,1)', '400', 'none', 'normal']
                }
            ],
            symbolInstances: [
            {
                id: 'One',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'None',
                symbolName: 'OpButton',
                autoPlay: {

               }
            },
            {
                id: 'Two',
                symbolName: 'OpButton',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_RobotAddress}": [
                ["style", "top", '74.22%'],
                ["style", "height", '7.03%'],
                ["style", "left", '23.63%'],
                ["style", "width", '52.54%']
            ],
            "${_RoundRect}": [
                ["style", "top", '15.36%'],
                ["style", "height", '71.88%'],
                ["style", "left", '17.38%'],
                ["style", "width", '65.24%']
            ],
            "${_Text}": [
                ["color", "color", 'rgba(0,0,0,1.00)'],
                ["style", "top", '19.41%'],
                ["style", "left", '23.63%'],
                ["style", "font-size", '25px']
            ],
            "${_UseSimulatorCheckbox}": [
                ["style", "height", '7.29%'],
                ["style", "top", '64.85%'],
                ["style", "left", '23.63%'],
                ["style", "width", '52.54%']
            ],
            "${_Two}": [
                ["style", "left", '40.43%'],
                ["style", "top", '41.28%']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_None}": [
                ["style", "left", '40.43%'],
                ["style", "top", '51.18%']
            ],
            "${_One}": [
                ["style", "left", '40.43%'],
                ["style", "top", '31.39%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 1396,
            autoPlay: false,
            labels: {
                "Start": 0
            },
            timeline: [
            ]
        }
    }
},
"ChangeOptions": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    transform: [[0, 0], [], [], ['0.49793', '0.49793']],
                    display: 'none',
                    type: 'rect',
                    id: 'OptionDesktopRepair',
                    rect: ['145px', '257px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.49793', '0.49793']],
                    display: 'none',
                    type: 'rect',
                    id: 'OptionUpgradeApp',
                    rect: ['145px', '156px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    transform: [[0, 0], [], [], ['0.49793', '0.49793']],
                    display: 'none',
                    type: 'rect',
                    id: 'OptionUpgradeOS',
                    rect: ['145px', '85px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'DoneButton',
                    type: 'rect',
                    rect: ['206px', '322px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'desktop_bg_01Copy',
                    type: 'image',
                    rect: ['31px', '78px', '129px', '284px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/desktop_bg_01.png', '0px', '0px']
                },
                {
                    id: 'OS',
                    type: 'rect',
                    rect: ['5.9%', '80.7%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer1',
                    type: 'rect',
                    rect: ['5.5%', '73.7%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer2',
                    type: 'rect',
                    rect: ['5.5%', '66.7%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer3',
                    type: 'rect',
                    rect: ['5.5%', '59.9%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Layer4',
                    type: 'rect',
                    rect: ['5.5%', '52.9%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    id: 'Personalization',
                    type: 'rect',
                    rect: ['5.9%', '45.8%', 'auto', 'auto', 'auto', 'auto'],
                    transform: [[0, 0], [], [], ['0.56219', '0.56219']]
                },
                {
                    font: ['Roboto, Arial, sans-serif', 14, 'rgba(183,18,52,1)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'TextCopy',
                    text: 'Deploying is only half the battle!  Let\'s manage the layers in  your desktop.',
                    align: 'center',
                    rect: ['33px', '50px', 'auto', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            {
                id: 'Layer3',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'OS',
                symbolName: 'OSSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer1',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'OptionDesktopRepair',
                symbolName: 'OptionDesktopRepair',
                autoPlay: {

               }
            },
            {
                id: 'DoneButton',
                symbolName: 'DoneButton',
                autoPlay: {

               }
            },
            {
                id: 'Personalization',
                symbolName: 'PersonalizationSymbol',
                autoPlay: {

               }
            },
            {
                id: 'Layer4',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'OptionUpgradeOS',
                symbolName: 'OptionUpgradeOS',
                autoPlay: {

               }
            },
            {
                id: 'Layer2',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            },
            {
                id: 'OptionUpgradeApp',
                symbolName: 'OptionUpgradeApp',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_OptionUpgradeApp}": [
                ["style", "top", '156px'],
                ["transform", "scaleY", '0.49793'],
                ["transform", "scaleX", '0.49793'],
                ["style", "left", '465px'],
                ["style", "display", 'none']
            ],
            "${_OS}": [
                ["style", "top", '80.73%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '1'],
                ["style", "left", '5.86%']
            ],
            "${_desktop_bg_01Copy}": [
                ["style", "height", '284px'],
                ["style", "top", '78px'],
                ["style", "left", '31px'],
                ["style", "width", '129px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_DoneButton}": [
                ["style", "left", '206px'],
                ["style", "top", '322px']
            ],
            "${_Layer1}": [
                ["style", "top", '73.7%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '1'],
                ["style", "left", '5.86%']
            ],
            "${_Layer2}": [
                ["style", "top", '66.67%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '1'],
                ["style", "left", '5.86%']
            ],
            "${_OptionDesktopRepair}": [
                ["style", "top", '231px'],
                ["transform", "scaleY", '0.49793'],
                ["transform", "scaleX", '0.49793'],
                ["style", "left", '465px'],
                ["style", "display", 'none']
            ],
            "${_Layer3}": [
                ["style", "top", '59.9%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '1'],
                ["style", "left", '5.86%']
            ],
            "${_OptionUpgradeOS}": [
                ["style", "top", '79px'],
                ["transform", "scaleY", '0.49793'],
                ["style", "display", 'none'],
                ["style", "left", '465px'],
                ["transform", "scaleX", '0.49793']
            ],
            "${_Personalization}": [
                ["style", "top", '45.83%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '1'],
                ["style", "left", '5.86%']
            ],
            "${_TextCopy}": [
                ["style", "top", '50px'],
                ["style", "left", '33px'],
                ["style", "font-size", '14px']
            ],
            "${_Layer4}": [
                ["style", "top", '52.86%'],
                ["transform", "scaleY", '0.56219'],
                ["transform", "scaleX", '0.56219'],
                ["style", "opacity", '1'],
                ["style", "left", '5.86%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4500,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Continue": 250,
                "ThreeOptions": 1000,
                "TwoOptions": 2000
            },
            timeline: [
                { id: "eid1279", tween: [ "style", "${_Personalization}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeOutQuad" },
                { id: "eid703", tween: [ "style", "${_OptionDesktopRepair}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid701", tween: [ "style", "${_OptionUpgradeOS}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1276", tween: [ "style", "${_Layer2}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeOutQuad" },
                { id: "eid1280", tween: [ "style", "${_Layer3}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeOutQuad" },
                { id: "eid936", tween: [ "style", "${_OptionUpgradeApp}", "left", '146px', { fromValue: '465px'}], position: 1116, duration: 500 },
                { id: "eid1278", tween: [ "style", "${_Layer4}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeOutQuad" },
                { id: "eid708", tween: [ "style", "${_OptionUpgradeOS}", "top", '79px', { fromValue: '79px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid711", tween: [ "style", "${_OptionUpgradeOS}", "top", '129px', { fromValue: '79px'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid702", tween: [ "style", "${_OptionUpgradeApp}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid707", tween: [ "style", "${_OptionUpgradeApp}", "display", 'none', { fromValue: 'block'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid710", tween: [ "style", "${_OptionDesktopRepair}", "top", '231px', { fromValue: '231px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid712", tween: [ "style", "${_OptionDesktopRepair}", "top", '203px', { fromValue: '231px'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid937", tween: [ "style", "${_OptionDesktopRepair}", "left", '145px', { fromValue: '465px'}], position: 1250, duration: 500 },
                { id: "eid951", tween: [ "style", "${_OptionDesktopRepair}", "left", '465px', { fromValue: '145px'}], position: 1957, duration: 43 },
                { id: "eid952", tween: [ "style", "${_OptionDesktopRepair}", "left", '145px', { fromValue: '465px'}], position: 2000, duration: 500 },
                { id: "eid1281", tween: [ "style", "${_Layer1}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeOutQuad" },
                { id: "eid1277", tween: [ "style", "${_OS}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeOutQuad" },
                { id: "eid934", tween: [ "style", "${_OptionUpgradeOS}", "left", '145px', { fromValue: '465px'}], position: 1000, duration: 500 },
                { id: "eid949", tween: [ "style", "${_OptionUpgradeOS}", "left", '465px', { fromValue: '145px'}], position: 1957, duration: 43 },
                { id: "eid950", tween: [ "style", "${_OptionUpgradeOS}", "left", '145px', { fromValue: '465px'}], position: 2000, duration: 500 }            ]
        }
    }
},
"OptionDesktopRepair": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'Button',
                    type: 'group',
                    rect: ['0', '0', '241', '63', 'auto', 'auto'],
                    c: [
                    {
                        id: 'update_brokendesktop_01',
                        type: 'image',
                        rect: ['0px', '0px', '485px', '114px', 'auto', 'auto'],
                        fill: ['rgba(0,0,0,0)', 'images/update_brokendesktop-blank_01.png', '0px', '0px']
                    },
                    {
                        font: ['Roboto, Arial, sans-serif', 30, 'rgba(255,255,255,1.00)', '300', 'none', 'normal'],
                        type: 'text',
                        id: 'Text3',
                        text: 'Broken desktop!<br>Let’s repair it!',
                        align: 'left',
                        rect: ['167px', '24px', '304px', '98px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Text3}": [
                ["style", "line-height", '34px'],
                ["style", "text-align", 'left'],
                ["style", "font-size", '30px'],
                ["color", "color", 'rgba(255,255,255,1.00)'],
                ["style", "top", '24px'],
                ["style", "left", '167px'],
                ["style", "width", '304px']
            ],
            "${_Button}": [
                ["style", "opacity", '1']
            ],
            "${symbolSelector}": [
                ["style", "height", '63px'],
                ["style", "width", '241px']
            ],
            "${_update_brokendesktop_01}": [
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3067,
            autoPlay: false,
            labels: {
                "Start": 500,
                "Enabled": 1000,
                "Touch": 2000,
                "Disabled": 3000
            },
            timeline: [
                { id: "eid700", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid698", tween: [ "style", "${_Button}", "opacity", '0', { fromValue: '1'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid693", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid695", tween: [ "style", "${_Button}", "opacity", '0', { fromValue: '1'}], position: 1999, duration: 304, easing: "easeInOutQuad" },
                { id: "eid697", tween: [ "style", "${_Button}", "opacity", '0.25', { fromValue: '0.000000'}], position: 2303, duration: 697, easing: "easeInOutQuad" }            ]
        }
    }
},
"OptionUpgradeOS": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'Button',
                    type: 'group',
                    rect: ['0', '0', '241', '63', 'auto', 'auto'],
                    c: [
                    {
                        id: 'update_patchos_012',
                        type: 'image',
                        rect: ['0px', '0px', '485px', '116px', 'auto', 'auto'],
                        fill: ['rgba(0,0,0,0)', 'images/update_patchos-blank_01.png', '0px', '0px']
                    },
                    {
                        font: ['Roboto, Arial, sans-serif', 32, 'rgba(255,255,255,1.00)', '300', 'none', 'normal'],
                        type: 'text',
                        id: 'Text3',
                        text: 'Patch Tuesday!<br>Let\'s update the OS.',
                        align: 'left',
                        rect: ['167px', '24px', '304px', '98px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Text3}": [
                ["style", "line-height", '32px'],
                ["style", "text-align", 'left'],
                ["style", "width", '304px'],
                ["color", "color", 'rgba(255,255,255,1)'],
                ["style", "top", '24px'],
                ["style", "left", '167px'],
                ["style", "font-size", '32px']
            ],
            "${_Button}": [
                ["style", "opacity", '0.99']
            ],
            "${_update_patchos_012}": [
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '63px'],
                ["style", "width", '241px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3067,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Enabled": 1000,
                "Touch": 2000,
                "Disabled": 3000
            },
            timeline: [
                { id: "eid1282", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0.99'}], position: 0, duration: 360 },
                { id: "eid698", tween: [ "style", "${_Button}", "opacity", '0', { fromValue: '1'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid693", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid695", tween: [ "style", "${_Button}", "opacity", '0', { fromValue: '1'}], position: 1999, duration: 304, easing: "easeInOutQuad" },
                { id: "eid697", tween: [ "style", "${_Button}", "opacity", '0.25', { fromValue: '0.000000'}], position: 2303, duration: 697, easing: "easeInOutQuad" }            ]
        }
    }
},
"OptionUpgradeApp": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'Button',
                    type: 'group',
                    rect: ['0', '0', '241', '63', 'auto', 'auto'],
                    c: [
                    {
                        id: 'update_updateapp_012',
                        type: 'image',
                        rect: ['0px', '5px', '485px', '105px', 'auto', 'auto'],
                        fill: ['rgba(0,0,0,0)', 'images/update_updateapp-blank_01.png', '0px', '0px']
                    },
                    {
                        id: 'AppLogo3',
                        type: 'rect',
                        rect: ['23', '-11', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'AppLogo1',
                        type: 'rect',
                        rect: ['23', '-11', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'AppLogo2',
                        type: 'rect',
                        rect: ['23', '-11', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        font: ['Roboto, Arial, sans-serif', 30, 'rgba(255,255,255,1.00)', '300', 'none', 'normal'],
                        type: 'text',
                        id: 'Text3',
                        text: 'New version available!<br>Upgrade [APPNAME]',
                        align: 'left',
                        rect: ['167px', '24px', '304px', '98px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            {
                id: 'AppLogo3',
                symbolName: 'AppLogo2',
                autoPlay: {

               }
            },
            {
                id: 'AppLogo1',
                symbolName: 'AppLogo1',
                autoPlay: {

               }
            },
            {
                id: 'AppLogo2',
                symbolName: 'AppLogo3',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_AppLogo3}": [
                ["style", "top", '-11px'],
                ["style", "left", '13px']
            ],
            "${_update_updateapp_012}": [
                ["style", "top", '5px'],
                ["style", "left", '0px'],
                ["style", "height", '105px']
            ],
            "${_AppLogo1}": [
                ["style", "left", '13px']
            ],
            "${_AppLogo2}": [
                ["style", "left", '13px']
            ],
            "${_Button}": [
                ["style", "opacity", '1']
            ],
            "${symbolSelector}": [
                ["style", "height", '63px'],
                ["style", "width", '241px']
            ],
            "${_Text3}": [
                ["style", "line-height", '34px'],
                ["style", "text-align", 'left'],
                ["style", "width", '304px'],
                ["color", "color", 'rgba(255,255,255,1)'],
                ["style", "top", '24px'],
                ["style", "left", '167px'],
                ["style", "font-size", '30px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3067,
            autoPlay: false,
            labels: {
                "Start": 500,
                "Enabled": 1000,
                "Touch": 2000,
                "Disabled": 3000
            },
            timeline: [
                { id: "eid700", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '1'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid698", tween: [ "style", "${_Button}", "opacity", '0', { fromValue: '1'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid693", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid695", tween: [ "style", "${_Button}", "opacity", '0', { fromValue: '1'}], position: 1999, duration: 304, easing: "easeInOutQuad" },
                { id: "eid697", tween: [ "style", "${_Button}", "opacity", '0.25', { fromValue: '0.000000'}], position: 2303, duration: 697, easing: "easeInOutQuad" }            ]
        }
    }
},
"Goodbye": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'bot_01Copy4',
                    type: 'image',
                    rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                },
                {
                    id: 'bot_speechbubble_01Copy4',
                    type: 'image',
                    rect: ['23px', '70px', '222px', '151px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text3Copy4',
                    text: 'All done!<br>Thanks for playing - now go get a demo to see the real thing!',
                    align: 'center',
                    rect: ['54px', '94px', '169px', '135px', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_bot_speechbubble_01Copy4}": [
                ["style", "top", '53px'],
                ["style", "height", '151px'],
                ["style", "opacity", '1'],
                ["style", "left", '521px'],
                ["style", "width", '222px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Text3Copy4}": [
                ["style", "top", '76px'],
                ["style", "height", '135px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '169px']
            ],
            "${_Text3Copy2}": [
                ["style", "top", '116px'],
                ["style", "height", '135px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '169px']
            ],
            "${_bot_01Copy4}": [
                ["style", "height", '283px'],
                ["style", "top", '83px'],
                ["style", "left", '610px'],
                ["style", "width", '395px']
            ],
            "${_Text3Copy3}": [
                ["style", "top", '116px'],
                ["style", "height", '135px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '169px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4000,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Exit": 3500
            },
            timeline: [
                { id: "eid1132", tween: [ "style", "${_bot_01Copy4}", "top", '83px', { fromValue: '83px'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1133", tween: [ "style", "${_bot_01Copy4}", "top", '83px', { fromValue: '83px'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1125", tween: [ "style", "${_Text3Copy4}", "left", '50px', { fromValue: '524px'}], position: 0, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1138", tween: [ "style", "${_Text3Copy4}", "left", '525px', { fromValue: '50px'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1134", tween: [ "style", "${_bot_01Copy4}", "left", '158px', { fromValue: '610px'}], position: 0, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1135", tween: [ "style", "${_bot_01Copy4}", "left", '610px', { fromValue: '158px'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1130", tween: [ "style", "${_bot_speechbubble_01Copy4}", "left", '23px', { fromValue: '521px'}], position: 0, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1131", tween: [ "style", "${_bot_speechbubble_01Copy4}", "left", '521px', { fromValue: '23px'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1319", tween: [ "style", "${_Text3Copy4}", "top", '76px', { fromValue: '76px'}], position: 3500, duration: 0 },
                { id: "eid1128", tween: [ "style", "${_bot_speechbubble_01Copy4}", "top", '53px', { fromValue: '53px'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1129", tween: [ "style", "${_bot_speechbubble_01Copy4}", "top", '86px', { fromValue: '53px'}], position: 4000, duration: 0, easing: "easeInOutQuad" }            ]
        }
    }
},
"ActionDesktopRepair": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    type: 'text',
                    id: 'Title',
                    text: 'Repair desktop...',
                    align: 'center',
                    rect: ['91px', '-80px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'bot_01Copy',
                    type: 'image',
                    rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                },
                {
                    id: 'bot_speechbubble_01Copy',
                    type: 'image',
                    rect: ['23px', '70px', '222px', '151px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text3Copy',
                    text: 'Upgrading the OS...',
                    align: 'center',
                    rect: ['54px', '116px', '169px', '135px', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'WaitingCopy2',
                    text: 'Waiting...',
                    align: 'center',
                    rect: ['-120px', '111px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'RunningCopy2',
                    text: 'Running...',
                    align: 'center',
                    rect: ['-119px', '111px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'DoneCopy2',
                    text: 'Done!',
                    align: 'center',
                    rect: ['-119px', '111px', '508px', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_RunningCopy2}": [
                ["style", "top", '111px'],
                ["style", "font-size", '18px'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-119px'],
                ["style", "width", '508px']
            ],
            "${_Title}": [
                ["style", "left", '91px'],
                ["style", "top", '-80px']
            ],
            "${_DoneCopy2}": [
                ["style", "top", '111px'],
                ["style", "width", '508px'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-119px'],
                ["style", "font-size", '18px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Text3Copy}": [
                ["style", "top", '116px'],
                ["style", "height", '135px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '169px']
            ],
            "${_bot_speechbubble_01Copy}": [
                ["style", "top", '53px'],
                ["style", "height", '151px'],
                ["style", "opacity", '1'],
                ["style", "left", '521px'],
                ["style", "width", '222px']
            ],
            "${_WaitingCopy2}": [
                ["style", "top", '111px'],
                ["style", "width", '508px'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-120px'],
                ["style", "font-size", '18px']
            ],
            "${_bot_01Copy}": [
                ["style", "top", '83px'],
                ["style", "height", '283px'],
                ["style", "left", '610px'],
                ["style", "width", '395px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 8000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Pending": 4000,
                "Executing": 5000,
                "Finish": 6000
            },
            timeline: [
                { id: "eid1097", tween: [ "style", "${_bot_speechbubble_01Copy}", "top", '53px', { fromValue: '53px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1098", tween: [ "style", "${_bot_speechbubble_01Copy}", "top", '86px', { fromValue: '53px'}], position: 8000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1093", tween: [ "style", "${_Text3Copy}", "opacity", '1', { fromValue: '1'}], position: 1000, duration: 0 },
                { id: "eid1094", tween: [ "style", "${_Text3Copy}", "opacity", '0', { fromValue: '1'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1084", tween: [ "style", "${_DoneCopy2}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1085", tween: [ "style", "${_DoneCopy2}", "display", 'block', { fromValue: 'none'}], position: 6000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1095", tween: [ "style", "${_bot_speechbubble_01Copy}", "left", '23px', { fromValue: '521px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1096", tween: [ "style", "${_bot_speechbubble_01Copy}", "left", '521px', { fromValue: '23px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1082", tween: [ "style", "${_DoneCopy2}", "top", '111px', { fromValue: '111px'}], position: 7500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1099", tween: [ "style", "${_bot_01Copy}", "left", '158px', { fromValue: '610px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1100", tween: [ "style", "${_bot_01Copy}", "left", '610px', { fromValue: '158px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1101", tween: [ "style", "${_bot_01Copy}", "top", '83px', { fromValue: '83px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1102", tween: [ "style", "${_bot_01Copy}", "top", '83px', { fromValue: '83px'}], position: 8000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1086", tween: [ "style", "${_RunningCopy2}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1087", tween: [ "style", "${_RunningCopy2}", "display", 'block', { fromValue: 'none'}], position: 5000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1088", tween: [ "style", "${_RunningCopy2}", "display", 'none', { fromValue: 'block'}], position: 6000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1089", tween: [ "style", "${_WaitingCopy2}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1090", tween: [ "style", "${_WaitingCopy2}", "display", 'block', { fromValue: 'none'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1091", tween: [ "style", "${_WaitingCopy2}", "display", 'none', { fromValue: 'block'}], position: 5000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1092", tween: [ "style", "${_Text3Copy}", "left", '50px', { fromValue: '524px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1083", tween: [ "style", "${_DoneCopy2}", "left", '356px', { fromValue: '-119px'}], position: 7500, duration: 500, easing: "easeOutQuad" }            ]
        }
    }
},
"ActionUpgradeOS": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    type: 'text',
                    id: 'Title',
                    text: 'Upgrade OS',
                    align: 'center',
                    rect: ['0px', '-89px', '512px', 'auto', 'auto', 'auto']
                },
                {
                    id: 'bot_01',
                    type: 'image',
                    rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                },
                {
                    id: 'bot_speechbubble_01Copy2',
                    type: 'image',
                    rect: ['23px', '70px', '222px', '151px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text3Copy2',
                    text: 'Upgrading the OS...',
                    align: 'center',
                    rect: ['54px', '116px', '169px', '135px', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'Waiting',
                    text: 'Waiting...',
                    align: 'center',
                    rect: ['-120px', '111px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'Running',
                    text: 'Running...',
                    align: 'center',
                    rect: ['-119px', '111px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'Done',
                    text: 'Done!',
                    align: 'center',
                    rect: ['-119px', '111px', '508px', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Done}": [
                ["style", "top", '111px'],
                ["style", "font-size", '18px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "left", '-119px'],
                ["style", "width", '508px']
            ],
            "${_Title}": [
                ["style", "top", '-89px'],
                ["style", "left", '0px'],
                ["style", "width", '512px']
            ],
            "${_bot_speechbubble_01Copy2}": [
                ["style", "top", '53px'],
                ["style", "height", '151px'],
                ["style", "opacity", '1'],
                ["style", "left", '521px'],
                ["style", "width", '222px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Running}": [
                ["style", "top", '111px'],
                ["style", "width", '508px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "left", '-119px'],
                ["style", "font-size", '18px']
            ],
            "${_Text3Copy2}": [
                ["style", "top", '116px'],
                ["style", "height", '135px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '169px']
            ],
            "${_Waiting}": [
                ["style", "top", '111px'],
                ["style", "font-size", '18px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["style", "left", '-120px'],
                ["style", "width", '508px']
            ],
            "${_bot_01}": [
                ["style", "height", '283px'],
                ["style", "top", '83px'],
                ["style", "left", '610px'],
                ["style", "width", '395px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 8000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Pending": 4000,
                "Executing": 5000,
                "Finish": 6000
            },
            timeline: [
                { id: "eid980", tween: [ "style", "${_bot_01}", "left", '158px', { fromValue: '610px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid981", tween: [ "style", "${_bot_01}", "left", '610px', { fromValue: '158px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid969", tween: [ "style", "${_Text3Copy2}", "left", '50px', { fromValue: '524px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid986", tween: [ "style", "${_Done}", "top", '111px', { fromValue: '111px'}], position: 7500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid978", tween: [ "style", "${_bot_speechbubble_01Copy2}", "left", '23px', { fromValue: '521px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid979", tween: [ "style", "${_bot_speechbubble_01Copy2}", "left", '521px', { fromValue: '23px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid988", tween: [ "style", "${_Done}", "left", '356px', { fromValue: '-119px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid720", tween: [ "style", "${_Done}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid729", tween: [ "style", "${_Done}", "display", 'block', { fromValue: 'none'}], position: 6000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid970", tween: [ "style", "${_Text3Copy2}", "opacity", '1', { fromValue: '1'}], position: 1000, duration: 0 },
                { id: "eid984", tween: [ "style", "${_Text3Copy2}", "opacity", '0', { fromValue: '1'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid973", tween: [ "style", "${_bot_speechbubble_01Copy2}", "top", '53px', { fromValue: '53px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid974", tween: [ "style", "${_bot_speechbubble_01Copy2}", "top", '86px', { fromValue: '53px'}], position: 8000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid982", tween: [ "style", "${_bot_01}", "top", '83px', { fromValue: '83px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid983", tween: [ "style", "${_bot_01}", "top", '83px', { fromValue: '83px'}], position: 8000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid722", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid725", tween: [ "style", "${_Waiting}", "display", 'block', { fromValue: 'none'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid726", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 5000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid721", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid727", tween: [ "style", "${_Running}", "display", 'block', { fromValue: 'none'}], position: 5000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid728", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 6000, duration: 0, easing: "easeInOutQuad" }            ]
        }
    }
},
"ActionUpgradeApp": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'Title',
                    text: 'Upgrade app',
                    align: 'center',
                    rect: ['4px', '-82px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    id: 'bot_01',
                    type: 'image',
                    rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                },
                {
                    id: 'bot_speechbubble_01',
                    type: 'image',
                    rect: ['23px', '70px', '222px', '151px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text3',
                    text: 'Upgrading Application...',
                    align: 'center',
                    rect: ['54px', '116px', '189px', '135px', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'WaitingCopy',
                    text: 'Waiting...',
                    align: 'center',
                    rect: ['-120px', '111px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'RunningCopy',
                    text: 'Running...',
                    align: 'center',
                    rect: ['-119px', '111px', '508px', 'auto', 'auto', 'auto']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    display: 'block',
                    id: 'DoneCopy',
                    text: 'Done!',
                    align: 'center',
                    rect: ['-119px', '111px', '508px', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_DoneCopy}": [
                ["style", "top", '111px'],
                ["style", "width", '508px'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-119px'],
                ["style", "font-size", '18px']
            ],
            "${_Title}": [
                ["style", "top", '-82px'],
                ["style", "display", 'block'],
                ["style", "opacity", '1'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ],
            "${_bot_speechbubble_01}": [
                ["style", "top", '53px'],
                ["style", "height", '151px'],
                ["style", "opacity", '1'],
                ["style", "left", '521px'],
                ["style", "width", '222px']
            ],
            "${_RunningCopy}": [
                ["style", "top", '111px'],
                ["style", "font-size", '18px'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-119px'],
                ["style", "width", '508px']
            ],
            "${_Text3}": [
                ["style", "top", '116px'],
                ["style", "height", '135px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '524px'],
                ["style", "width", '189px']
            ],
            "${_WaitingCopy}": [
                ["style", "top", '111px'],
                ["style", "width", '508px'],
                ["style", "display", 'block'],
                ["style", "font-family", 'Roboto, Arial, sans-serif'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "font-weight", '300'],
                ["style", "left", '-120px'],
                ["style", "font-size", '18px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_bot_01}": [
                ["style", "top", '83px'],
                ["style", "height", '283px'],
                ["style", "left", '610px'],
                ["style", "width", '395px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 8000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Pending": 4011,
                "Executing": 5000,
                "Finish": 6000
            },
            timeline: [
                { id: "eid1078", tween: [ "style", "${_bot_01}", "left", '158px', { fromValue: '610px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1079", tween: [ "style", "${_bot_01}", "left", '610px', { fromValue: '158px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1063", tween: [ "style", "${_DoneCopy}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1064", tween: [ "style", "${_DoneCopy}", "display", 'block', { fromValue: 'none'}], position: 6000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1071", tween: [ "style", "${_Text3}", "left", '42px', { fromValue: '524px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1068", tween: [ "style", "${_WaitingCopy}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1069", tween: [ "style", "${_WaitingCopy}", "display", 'block', { fromValue: 'none'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1070", tween: [ "style", "${_WaitingCopy}", "display", 'none', { fromValue: 'block'}], position: 5000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1072", tween: [ "style", "${_Text3}", "opacity", '1', { fromValue: '1'}], position: 1000, duration: 0 },
                { id: "eid1073", tween: [ "style", "${_Text3}", "opacity", '0', { fromValue: '1'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1065", tween: [ "style", "${_RunningCopy}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1066", tween: [ "style", "${_RunningCopy}", "display", 'block', { fromValue: 'none'}], position: 5000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1067", tween: [ "style", "${_RunningCopy}", "display", 'none', { fromValue: 'block'}], position: 6000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1080", tween: [ "style", "${_bot_01}", "top", '83px', { fromValue: '83px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1081", tween: [ "style", "${_bot_01}", "top", '83px', { fromValue: '83px'}], position: 8000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1062", tween: [ "style", "${_DoneCopy}", "left", '356px', { fromValue: '-119px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1076", tween: [ "style", "${_bot_speechbubble_01}", "top", '53px', { fromValue: '53px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1077", tween: [ "style", "${_bot_speechbubble_01}", "top", '86px', { fromValue: '53px'}], position: 8000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1074", tween: [ "style", "${_bot_speechbubble_01}", "left", '23px', { fromValue: '521px'}], position: 1000, duration: 500, easing: "easeInOutQuad" },
                { id: "eid1075", tween: [ "style", "${_bot_speechbubble_01}", "left", '521px', { fromValue: '23px'}], position: 7500, duration: 500, easing: "easeOutQuad" },
                { id: "eid1061", tween: [ "style", "${_DoneCopy}", "top", '111px', { fromValue: '111px'}], position: 7500, duration: 0, easing: "easeInOutQuad" }            ]
        }
    }
},
"BackgroundTimer": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['-72.8%', '319%', '12px', '15px', 'auto', 'auto'],
                    id: 'Timer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${symbolSelector}": [
                ["style", "height", '79px'],
                ["style", "width", '136px']
            ],
            "${_Timer}": [
                ["style", "top", '318.99%'],
                ["style", "height", '15px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '-72.71%'],
                ["style", "width", '12px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 46000,
            autoPlay: false,
            labels: {
                "Stop": 0,
                "Start": 1000,
                "Timeout": 46000
            },
            timeline: [
                { id: "eid732", tween: [ "style", "${_Timer}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid733", tween: [ "style", "${_Timer}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0 }            ]
        }
    }
},
"CheckShowtime": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    rect: ['0px', '0px', '140px', '73px', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    id: 'Monitor',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    type: 'rect',
                    fill: ['rgba(205,104,104,1.00)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${symbolSelector}": [
                ["style", "height", '73px'],
                ["style", "width", '140px']
            ],
            "${_Monitor}": [
                ["color", "background-color", 'rgba(205,104,104,1.00)'],
                ["style", "opacity", '0'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 2000,
            autoPlay: false,
            labels: {
                "Start": 1000
            },
            timeline: [
            ]
        }
    }
},
"RobotError": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'bot_01',
                    type: 'image',
                    rect: ['188px', '89px', '395px', '283px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_01.png', '0px', '0px']
                },
                {
                    id: 'bot_speechbubble_01',
                    type: 'image',
                    rect: ['23px', '70px', '234px', '160px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/bot_speechbubble_01.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 18, 'rgba(183,18,52,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text3Copy2',
                    text: 'Uh-oh. I seem to be having some trouble.<br>Hold on while my humans look into it.',
                    align: 'center',
                    rect: ['54px', '103px', '169px', '120px', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_bot_01}": [
                ["style", "top", '88px'],
                ["style", "height", '283px'],
                ["style", "left", '763px'],
                ["style", "width", '395px']
            ],
            "${_Text3Copy2}": [
                ["style", "top", '103px'],
                ["color", "color", 'rgba(183,18,52,1.00)'],
                ["style", "left", '-311px'],
                ["style", "width", '169px']
            ],
            "${_bot_speechbubble_01}": [
                ["style", "top", '70px'],
                ["style", "height", '160px'],
                ["style", "left", '-342px'],
                ["style", "width", '234px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 4750,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Loop": 1010,
                "Exit": 3500
            },
            timeline: [
                { id: "eid1240", tween: [ "style", "${_Text3Copy2}", "top", '103px', { fromValue: '103px'}], position: 3500, duration: 0 },
                { id: "eid1246", tween: [ "style", "${_bot_01}", "left", '188px', { fromValue: '763px'}], position: 0, duration: 428, easing: "easeInOutQuad" },
                { id: "eid1247", tween: [ "style", "${_bot_01}", "left", '-419px', { fromValue: '188px'}], position: 3500, duration: 280 },
                { id: "eid1248", tween: [ "style", "${_bot_01}", "left", '188px', { fromValue: '-419px'}], position: 4750, duration: 0 },
                { id: "eid1238", tween: [ "style", "${_Text3Copy2}", "left", '54px', { fromValue: '-311px'}], position: 340, duration: 344, easing: "easeOutQuad" },
                { id: "eid1239", tween: [ "style", "${_Text3Copy2}", "left", '-551px', { fromValue: '54px'}], position: 3500, duration: 280 },
                { id: "eid1249", tween: [ "style", "${_bot_01}", "top", '88px', { fromValue: '88px'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1250", tween: [ "style", "${_bot_01}", "top", '89px', { fromValue: '88px'}], position: 428, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1251", tween: [ "style", "${_bot_01}", "top", '89px', { fromValue: '89px'}], position: 3780, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1241", tween: [ "style", "${_bot_speechbubble_01}", "top", '70px', { fromValue: '70px'}], position: 340, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1242", tween: [ "style", "${_bot_speechbubble_01}", "top", '70px', { fromValue: '70px'}], position: 3780, duration: 0, easing: "easeInOutQuad" },
                { id: "eid1243", tween: [ "style", "${_bot_speechbubble_01}", "left", '23px', { fromValue: '-342px'}], position: 340, duration: 344, easing: "easeOutQuad" },
                { id: "eid1244", tween: [ "style", "${_bot_speechbubble_01}", "left", '-584px', { fromValue: '23px'}], position: 3500, duration: 280 },
                { id: "eid1245", tween: [ "style", "${_bot_speechbubble_01}", "left", '23px', { fromValue: '-584px'}], position: 4750, duration: 0 }            ]
        }
    }
},
"ErrorMonitor": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    type: 'rect',
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    rect: ['-72.8%', '319%', '12px', '15px', 'auto', 'auto'],
                    id: 'Timer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'block',
                    fill: ['rgba(192,192,192,1)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${symbolSelector}": [
                ["style", "height", '79px'],
                ["style", "width", '136px']
            ],
            "${_Timer}": [
                ["style", "top", '318.99%'],
                ["style", "height", '15px'],
                ["style", "display", 'block'],
                ["style", "opacity", '0'],
                ["style", "left", '-72.71%'],
                ["style", "width", '12px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 3000,
            autoPlay: false,
            labels: {
                "Start": 1000
            },
            timeline: [
                { id: "eid732", tween: [ "style", "${_Timer}", "display", 'block', { fromValue: 'block'}], position: 0, duration: 0 },
                { id: "eid733", tween: [ "style", "${_Timer}", "display", 'block', { fromValue: 'block'}], position: 1000, duration: 0 }            ]
        }
    }
},
"GenericButton": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'buttonbg_02',
                    type: 'image',
                    rect: ['0px', '0px', '105px', '32px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/buttonbg_02.png', '0px', '0px']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_buttonbg_02}": [
                ["style", "height", '32px'],
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["style", "width", '105px']
            ],
            "${symbolSelector}": [
                ["style", "height", '32px'],
                ["style", "width", '105px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: true,
            timeline: [
            ]
        }
    }
},
"loadingcircle": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'loading_circle_01c',
                    type: 'image',
                    rect: ['0px', '0px', '120px', '120px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/loading_circle_01c.png', '0px', '0px']
                },
                {
                    id: 'loading_circle_01b',
                    type: 'image',
                    rect: ['0px', '0px', '120px', '120px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/loading_circle_01b.png', '0px', '0px']
                },
                {
                    id: 'loading_circle_01a',
                    type: 'image',
                    rect: ['0px', '0px', '120px', '120px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/loading_circle_01a.png', '0px', '0px']
                },
                {
                    font: ['Roboto, Arial, sans-serif', 24, 'rgba(149,165,166,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text',
                    text: 'Working...',
                    align: 'center',
                    rect: ['10px', '120px', 'auto', '24px', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_loading_circle_01c}": [
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["transform", "rotateZ", '0deg']
            ],
            "${_loading_circle_01a}": [
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["transform", "rotateZ", '0deg']
            ],
            "${_loading_circle_01b}": [
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["transform", "rotateZ", '0deg']
            ],
            "${symbolSelector}": [
                ["style", "height", '120px'],
                ["style", "width", '120px']
            ],
            "${_Text}": [
                ["style", "top", '120px'],
                ["style", "text-align", 'center'],
                ["style", "height", '24px'],
                ["color", "color", 'rgba(149,165,166,1.00)'],
                ["style", "left", '10px'],
                ["style", "font-size", '24px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 6000,
            autoPlay: true,
            timeline: [
                { id: "eid863", tween: [ "transform", "${_loading_circle_01c}", "rotateZ", '360deg', { fromValue: '0deg'}], position: 0, duration: 6000 },
                { id: "eid861", tween: [ "transform", "${_loading_circle_01a}", "rotateZ", '1440deg', { fromValue: '0deg'}], position: 0, duration: 6000 },
                { id: "eid862", tween: [ "transform", "${_loading_circle_01b}", "rotateZ", '720deg', { fromValue: '0deg'}], position: 0, duration: 6000 }            ]
        }
    }
},
"loadingDoneCheck": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    font: ['Roboto, Arial, sans-serif', 24, 'rgba(34,173,95,1.00)', '300', 'none', 'normal'],
                    type: 'text',
                    id: 'Text',
                    text: 'Done!',
                    align: 'center',
                    rect: ['31px', '120px', 'auto', '24px', 'auto', 'auto']
                },
                {
                    id: 'checkmark_01',
                    type: 'image',
                    rect: ['-13px', '5px', '147px', '111px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/checkmark_01.png', '0px', '0px']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_checkmark_01}": [
                ["style", "left", '-13px'],
                ["style", "top", '5px']
            ],
            "${_Text}": [
                ["style", "top", '120px'],
                ["style", "text-align", 'center'],
                ["style", "height", '24px'],
                ["color", "color", 'rgba(34,173,95,1.00)'],
                ["style", "left", '31px'],
                ["style", "font-size", '24px']
            ],
            "${symbolSelector}": [
                ["style", "height", '120px'],
                ["style", "width", '120px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 6000,
            autoPlay: true,
            timeline: [
            ]
        }
    }
},
"AppLogo2": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'applogo-symantec',
                    type: 'image',
                    rect: ['0px', '0px', '137px', '138px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/applogo-symantec.png', '0px', '0px']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_applogo-symantec}": [
                ["style", "top", '0px'],
                ["style", "left", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '138px'],
                ["style", "width", '137px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: true,
            timeline: [
            ]
        }
    }
},
"AppLogo1": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'applogo-office',
                    type: 'image',
                    rect: ['0px', '0px', '137px', '138px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/applogo-office.png', '0px', '0px']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_applogo-office}": [
                ["style", "top", '0px'],
                ["style", "left", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '138px'],
                ["style", "width", '137px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: true,
            timeline: [
            ]
        }
    }
},
"AppLogo3": {
    version: "4.0.0",
    minimumCompatibleVersion: "4.0.0",
    build: "4.0.0.359",
    baseState: "Base State",
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
                {
                    id: 'applogo-adobe',
                    type: 'image',
                    rect: ['0px', '0px', '137px', '138px', 'auto', 'auto'],
                    fill: ['rgba(0,0,0,0)', 'images/applogo-adobe.png', '0px', '0px']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_applogo-adobe}": [
                ["style", "top", '0px'],
                ["style", "left", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '138px'],
                ["style", "width", '137px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 0,
            autoPlay: true,
            timeline: [
            ]
        }
    }
}
};


Edge.registerCompositionDefn(compId, symbols, fonts, resources, opts);

/**
 * Adobe Edge DOM Ready Event Handler
 */
$(window).ready(function() {
     Edge.launchComposition(compId);
});
})(jQuery, AdobeEdge, "EDGE-736525547");
