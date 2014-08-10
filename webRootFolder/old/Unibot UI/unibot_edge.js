/**
 * Adobe Edge: symbol definitions
 */
(function($, Edge, compId){
//images folder
var im='images/';

var fonts = {};
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
    scaleToFit: "none",
    centerStage: "none",
    initialState: "Base State",
    gpuAccelerate: false,
    resizeInstances: false,
    content: {
            dom: [
            {
                id: 'TimerRestart',
                type: 'rect',
                rect: ['110', '113','auto','auto','auto', 'auto']
            },
            {
                id: 'Background',
                type: 'rect',
                rect: ['2px', '0px','auto','auto','auto', 'auto'],
                transform: [[],[],[],['1.00787']]
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
                id: 'TimerRestart',
                symbolName: 'TimerRestart',
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
                id: 'ActionUpgradeApp',
                symbolName: 'ActionUpgradeApp',
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
                id: 'Background',
                symbolName: 'Background',
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
                id: 'ActionDesktopRepair',
                symbolName: 'ActionDesktopRepair',
                autoPlay: {

                }
            }
            ]
        },
    states: {
        "Base State": {
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
            "${_BuildDesktop}": [
                ["style", "display", 'none']
            ],
            "${_Startup}": [
                ["style", "display", 'none']
            ],
            "${_Goodbye}": [
                ["style", "display", 'none']
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
                ["style", "height", '100%'],
                ["style", "width", '100%']
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
            "${_Background}": [
                ["style", "top", '0px'],
                ["transform", "scaleX", '1.00787'],
                ["transform", "scaleY", '1'],
                ["style", "left", '2px']
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
                { id: "eid731", tween: [ "style", "${_ActionUpgradeApp}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid671", tween: [ "style", "${_ChangeOptions}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid716", tween: [ "style", "${_Goodbye}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid613", tween: [ "style", "${_BuildDesktop}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid621", tween: [ "style", "${_Startup}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid395", tween: [ "style", "${_PasswordWindow}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
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
                    rect: ['0px', '0px', '131px', '29px', 'auto', 'auto'],
                    id: 'Layer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    type: 'rect',
                    fill: ['rgba(192,18,18,1.00)'],
                    c: [
                    {
                        font: ['Arial, Helvetica, sans-serif', 18, 'rgba(255,255,255,1)', '400', 'none', 'normal'],
                        type: 'text',
                        id: 'Layername',
                        text: '(name)',
                        align: 'center',
                        rect: ['0px', '6px', '131px', '29px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Layername}": [
                ["style", "top", '6px'],
                ["style", "text-align", 'center'],
                ["style", "height", '29px'],
                ["style", "width", '131px'],
                ["style", "left", '0px'],
                ["style", "font-size", '18px']
            ],
            "${_Layer}": [
                ["color", "background-color", 'rgba(192,18,18,1.00)'],
                ["style", "bottom", 'auto'],
                ["style", "right", 'auto'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '131px']
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
                { id: "eid626", tween: [ "color", "${_Layer}", "background-color", 'rgba(215,20,20,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(192,18,18,1.00)'}], position: 1002, duration: 248 },
                { id: "eid627", tween: [ "color", "${_Layer}", "background-color", 'rgba(192,18,18,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(215,20,20,1.00)'}], position: 1250, duration: 250 },
                { id: "eid632", tween: [ "color", "${_Layer}", "background-color", 'rgba(140,140,140,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(192,18,18,1.00)'}], position: 2000, duration: 0 },
                { id: "eid635", tween: [ "color", "${_Layer}", "background-color", 'rgba(210,206,206,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(140,140,140,1.00)'}], position: 3003, duration: 0 },
                { id: "eid668", tween: [ "color", "${_Layer}", "background-color", 'rgba(84,210,70,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(210,206,206,1.00)'}], position: 4000, duration: 0 }            ]
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
                    display: 'none',
                    type: 'rect',
                    id: 'PersonalizationSymbol',
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
                    display: 'none',
                    type: 'rect',
                    id: 'OSLayer',
                    rect: ['294', '297', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    type: 'rect',
                    rect: ['0px', '79px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot1',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '118px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot2',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '158px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot3',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '197px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot4',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '237px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot5',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'rect',
                    rect: ['0px', '276px', '131px', '29px', 'auto', 'auto'],
                    id: 'LayerSlot6',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    display: 'none',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'Layer6',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'Layer5',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'Layer4',
                    rect: ['0px', '197px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'Layer3',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'Layer2',
                    rect: ['153px', '68px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'Layer1',
                    rect: ['0px', '79px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'DoneButton',
                    rect: ['auto', '24px', 'auto', 'auto', '32px', 'auto']
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
            "${_PersonalizationSymbol}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "top", '-18px']
            ],
            "${_Layer4}": [
                ["style", "top", '197px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot4}": [
                ["style", "top", '197px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_Layer1}": [
                ["style", "top", '79px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_Layer6}": [
                ["style", "top", '276px'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["style", "display", 'none']
            ],
            "${_OSLayer}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "top", '393px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "overflow", 'visible'],
                ["style", "width", '100%']
            ],
            "${_Layer5}": [
                ["style", "top", '237px'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["style", "display", 'none']
            ],
            "${_DesktopSlot3}": [
                ["style", "top", '75px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_DoneButton}": [
                ["style", "top", '24px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", 'auto'],
                ["style", "right", '32px']
            ],
            "${_LayerSlot6}": [
                ["style", "top", '276px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_Layer2}": [
                ["style", "top", '118px'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot1}": [
                ["style", "top", '79px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot3}": [
                ["style", "top", '158px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_Layer3}": [
                ["style", "top", '158px'],
                ["style", "opacity", '0'],
                ["style", "left", '1px'],
                ["style", "display", 'none']
            ],
            "${_LayerSlot5}": [
                ["style", "top", '237px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
            ],
            "${_DesktopSlot4}": [
                ["style", "top", '27px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '551px'],
                ["style", "width", '131px']
            ],
            "${_LayerSlot2}": [
                ["style", "top", '118px'],
                ["style", "opacity", '0'],
                ["style", "left", '-228px'],
                ["style", "display", 'none']
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
                { id: "eid427", tween: [ "style", "${_DesktopOSSlot}", "top", '297px', { fromValue: '393px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid252", tween: [ "style", "${_Layer6}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid253", tween: [ "style", "${_Layer6}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid448", tween: [ "style", "${_OSLayer}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid259", tween: [ "style", "${_DoneButton}", "opacity", '1', { fromValue: '0'}], position: 767, duration: 233, easing: "easeOutQuad" },
                { id: "eid261", tween: [ "style", "${_DoneButton}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 483, easing: "easeOutQuad" },
                { id: "eid169", tween: [ "style", "${_Layer1}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid251", tween: [ "style", "${_Layer6}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid235", tween: [ "style", "${_Layer3}", "top", '158px', { fromValue: '158px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid452", tween: [ "style", "${_OSLayer}", "top", '297px', { fromValue: '393px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid193", tween: [ "style", "${_Layer2}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid215", tween: [ "style", "${_Layer2}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid125", tween: [ "style", "${_DesktopSlot2}", "top", '203px', { fromValue: '122px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid127", tween: [ "style", "${_DesktopSlot3}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid170", tween: [ "style", "${_LayerSlot6}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid179", tween: [ "style", "${_LayerSlot5}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid172", tween: [ "style", "${_LayerSlot4}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid451", tween: [ "style", "${_OSLayer}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid189", tween: [ "style", "${_LayerSlot6}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid131", tween: [ "style", "${_DesktopSlot4}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid197", tween: [ "style", "${_LayerSlot2}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid212", tween: [ "style", "${_LayerSlot2}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid458", tween: [ "style", "${_PersonalizationSymbol}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid245", tween: [ "style", "${_Layer5}", "top", '237px', { fromValue: '237px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid139", tween: [ "style", "${_DesktopSlot2}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid214", tween: [ "style", "${_DesktopSlot2}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid244", tween: [ "style", "${_Layer4}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid248", tween: [ "style", "${_Layer5}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid249", tween: [ "style", "${_Layer5}", "left", '56px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid250", tween: [ "style", "${_Layer5}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid168", tween: [ "style", "${_Layer2}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid254", tween: [ "style", "${_Layer6}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid255", tween: [ "style", "${_Layer6}", "left", '56px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid242", tween: [ "style", "${_Layer4}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid243", tween: [ "style", "${_Layer4}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid133", tween: [ "style", "${_DesktopSlot4}", "top", '108px', { fromValue: '27px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid430", tween: [ "style", "${_DesktopOSSlot}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid137", tween: [ "style", "${_DesktopSlot1}", "top", '251px', { fromValue: '170px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid175", tween: [ "style", "${_LayerSlot1}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid171", tween: [ "style", "${_LayerSlot5}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid143", tween: [ "style", "${_DesktopSlot4}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid211", tween: [ "style", "${_DesktopSlot4}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid256", tween: [ "style", "${_Layer6}", "top", '276px', { fromValue: '276px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid461", tween: [ "style", "${_PersonalizationSymbol}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid129", tween: [ "style", "${_DesktopSlot3}", "top", '156px', { fromValue: '75px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid462", tween: [ "style", "${_PersonalizationSymbol}", "top", '60px', { fromValue: '-18px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid191", tween: [ "style", "${_Layer1}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid167", tween: [ "style", "${_DesktopSlot3}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid187", tween: [ "style", "${_LayerSlot3}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid246", tween: [ "style", "${_Layer5}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid247", tween: [ "style", "${_Layer5}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid145", tween: [ "style", "${_DesktopSlot1}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid220", tween: [ "style", "${_DesktopSlot1}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid181", tween: [ "style", "${_LayerSlot2}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid257", tween: [ "style", "${_DoneButton}", "display", 'block', { fromValue: 'none'}], position: 767, duration: 0, easing: "easeOutQuad" },
                { id: "eid260", tween: [ "style", "${_DoneButton}", "display", 'none', { fromValue: 'block'}], position: 1500, duration: 0, easing: "easeOutQuad" },
                { id: "eid205", tween: [ "style", "${_LayerSlot6}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid218", tween: [ "style", "${_LayerSlot6}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid238", tween: [ "style", "${_Layer3}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid239", tween: [ "style", "${_Layer3}", "left", '56px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid135", tween: [ "style", "${_DesktopSlot1}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid176", tween: [ "style", "${_Layer2}", "left", '-227px', { fromValue: '1px'}], position: 500, duration: 5, easing: "easeOutQuad" },
                { id: "eid177", tween: [ "style", "${_Layer2}", "left", '56px', { fromValue: '-227px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid201", tween: [ "style", "${_LayerSlot1}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid210", tween: [ "style", "${_LayerSlot1}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid449", tween: [ "style", "${_OSLayer}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid450", tween: [ "style", "${_OSLayer}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid459", tween: [ "style", "${_PersonalizationSymbol}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid460", tween: [ "style", "${_PersonalizationSymbol}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid207", tween: [ "style", "${_Layer1}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid219", tween: [ "style", "${_Layer1}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid141", tween: [ "style", "${_DesktopSlot3}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid223", tween: [ "style", "${_DesktopSlot3}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid203", tween: [ "style", "${_LayerSlot3}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid213", tween: [ "style", "${_LayerSlot3}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid174", tween: [ "style", "${_LayerSlot2}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid123", tween: [ "style", "${_DesktopSlot2}", "left", '294px', { fromValue: '551px'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid173", tween: [ "style", "${_LayerSlot3}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid236", tween: [ "style", "${_Layer3}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid237", tween: [ "style", "${_Layer3}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid97", tween: [ "style", "${_Layer2}", "top", '118px', { fromValue: '118px'}], position: 500, duration: 0, easing: "easeInOutQuad" },
                { id: "eid241", tween: [ "style", "${_Layer4}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid183", tween: [ "style", "${_LayerSlot4}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid195", tween: [ "style", "${_LayerSlot5}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid216", tween: [ "style", "${_LayerSlot5}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid199", tween: [ "style", "${_LayerSlot4}", "opacity", '1', { fromValue: '0'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid221", tween: [ "style", "${_LayerSlot4}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid240", tween: [ "style", "${_Layer3}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid165", tween: [ "style", "${_DesktopSlot4}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid185", tween: [ "style", "${_LayerSlot1}", "left", '56px', { fromValue: '-228px'}], position: 505, duration: 495, easing: "easeOutQuad" },
                { id: "eid164", tween: [ "style", "${_DesktopSlot2}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid166", tween: [ "style", "${_DesktopSlot1}", "display", 'block', { fromValue: 'none'}], position: 500, duration: 0, easing: "easeOutQuad" },
                { id: "eid428", tween: [ "style", "${_DesktopOSSlot}", "opacity", '1', { fromValue: '0'}], position: 500, duration: 500, easing: "easeOutQuad" },
                { id: "eid429", tween: [ "style", "${_DesktopOSSlot}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
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
                        id: 'CheckShowtime',
                        type: 'rect',
                        rect: ['169', '113', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        type: 'ellipse',
                        borderRadius: ['50%', '50%', '50%', '50%'],
                        rect: ['10%', '10%', '80%', '80%', 'auto', 'auto'],
                        id: 'Ellipse',
                        stroke: [0, 'rgba(0,0,0,1)', 'none'],
                        display: 'block',
                        fill: ['rgba(192,192,192,1)']
                    },
                    {
                        font: ['Arial, Helvetica, sans-serif', 24, 'rgba(22,32,157,1.00)', 'normal', 'none', ''],
                        type: 'text',
                        id: 'EnjoyShow',
                        text: 'Enjoy our show...<br>but come back and play with me!',
                        display: 'none',
                        rect: ['51px', '80px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1.00)', '400', 'none', 'normal'],
                        type: 'text',
                        id: 'Welcome',
                        text: 'Welcome!',
                        align: 'center',
                        rect: ['-0.1%', '-15.6%', '100%', '100%', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            {
                id: 'CheckShowtime',
                symbolName: 'CheckShowtime',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_Welcome}": [
                ["style", "-webkit-transform-origin", [50,50], {valueTemplate:'@@0@@% @@1@@%'} ],
                ["style", "-moz-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-ms-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "msTransformOrigin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-o-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "bottom", 'auto'],
                ["color", "color", 'rgba(255,0,0,1.00)'],
                ["style", "opacity", '0'],
                ["style", "left", '-0.15%'],
                ["style", "font-size", '44px'],
                ["style", "top", '-15.57%'],
                ["transform", "scaleY", '1'],
                ["style", "height", '100%'],
                ["transform", "scaleX", '1'],
                ["style", "right", 'auto'],
                ["style", "width", '100%']
            ],
            "${_Ellipse}": [
                ["style", "top", '10%'],
                ["style", "display", 'block'],
                ["style", "height", '80%'],
                ["style", "left", '10%'],
                ["style", "width", '80%']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
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
                { id: "eid388", tween: [ "style", "${_Welcome}", "top", '39.96%', { fromValue: '-15.57%'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid394", tween: [ "style", "${_Welcome}", "top", '68.34%', { fromValue: '40.01%'}], position: 500, duration: 488, easing: "easeInOutQuad" },
                { id: "eid161", tween: [ "style", "${_Welcome}", "opacity", '1', { fromValue: '0'}], position: 0, duration: 500, easing: "easeInOutQuad" },
                { id: "eid160", tween: [ "style", "${_Welcome}", "opacity", '0', { fromValue: '1'}], position: 500, duration: 488, easing: "easeInOutQuad" },
                { id: "eid740", tween: [ "style", "${_EnjoyShow}", "display", 'block', { fromValue: 'none'}], position: 2000, duration: 0 },
                { id: "eid738", tween: [ "style", "${_Ellipse}", "display", 'block', { fromValue: 'block'}], position: 0, duration: 0 },
                { id: "eid739", tween: [ "style", "${_Ellipse}", "display", 'none', { fromValue: 'block'}], position: 2000, duration: 0 },
                { id: "eid387", tween: [ "style", "${_Welcome}", "left", '-0.05%', { fromValue: '-0.15%'}], position: 0, duration: 0, easing: "easeInOutQuad" },
                { id: "eid393", tween: [ "style", "${_Welcome}", "left", '54.65%', { fromValue: '-0.09%'}], position: 500, duration: 488, easing: "easeInOutQuad" }            ]
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
                    rect: ['0px', '0px', '85px', '29px', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    id: 'RoundRect2',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(135,202,140,1.00)']
                },
                {
                    font: ['Arial, Helvetica, sans-serif', 22, 'rgba(0,0,0,1.00)', '400', 'none', 'normal'],
                    type: 'text',
                    id: 'Text3',
                    text: 'Done',
                    align: 'center',
                    rect: ['17px', '3px', 'auto', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Text3}": [
                ["style", "top", '3px'],
                ["color", "color", 'rgba(0,0,0,1.00)'],
                ["style", "opacity", '1'],
                ["style", "left", '17px'],
                ["style", "font-size", '22px']
            ],
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '85px']
            ],
            "${_RoundRect2}": [
                ["style", "top", '0px'],
                ["style", "opacity", '1'],
                ["style", "left", '0px'],
                ["color", "background-color", 'rgba(135,202,140,1.00)']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 1500,
            autoPlay: false,
            timeline: [
                { id: "eid224", tween: [ "style", "${_RoundRect2}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" },
                { id: "eid222", tween: [ "style", "${_Text3}", "opacity", '0', { fromValue: '1'}], position: 1000, duration: 500, easing: "easeOutQuad" }            ]
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
                    type: 'group',
                    id: 'FrameDiv',
                    rect: ['50%', '50%', '300px', '200px', 'auto', 'auto'],
                    userClass: 'passwordWindowFrame',
                    c: [
                    {
                        rect: ['0%', '0%', '89.9%', '85.4%', 'auto', 'auto'],
                        sizeRange: ['0px', '', '', ''],
                        id: 'Frame',
                        stroke: [14, 'rgb(0, 0, 0)', 'solid'],
                        type: 'rect',
                        fill: ['rgba(216,153,153,1.00)', [73, [['rgba(158,104,110,1.00)', 0], ['rgba(180,70,70,1.00)', 100]]]]
                    },
                    {
                        id: 'PasswordButton6',
                        type: 'rect',
                        rect: ['174px', '129px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'PasswordButton5',
                        type: 'rect',
                        rect: ['174px', '80px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'PasswordButton4',
                        type: 'rect',
                        rect: ['174px', '31px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'PasswordButton3',
                        type: 'rect',
                        rect: ['73px', '129px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'PasswordButton2',
                        type: 'rect',
                        rect: ['73px', '80px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        id: 'PasswordButton1',
                        type: 'rect',
                        rect: ['73px', '31px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        display: 'none',
                        type: 'rect',
                        id: 'RestartUI',
                        rect: ['161px', '96px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        display: 'none',
                        type: 'rect',
                        id: 'RobotSimulator',
                        rect: ['41px', '96px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        display: 'none',
                        type: 'rect',
                        id: 'CalibrateButton',
                        rect: ['137px', '113px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        display: 'none',
                        type: 'rect',
                        id: 'HomeButton',
                        rect: ['41px', '112px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        display: 'none',
                        type: 'rect',
                        id: 'ShowNowButton',
                        rect: ['52px', '56px', 'auto', 'auto', 'auto', 'auto']
                    },
                    {
                        display: 'none',
                        type: 'rect',
                        id: 'ShowInFiveButton',
                        rect: ['41px', '96px', 'auto', 'auto', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            {
                id: 'RestartUI',
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
                id: 'ShowInFiveButton',
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
                id: 'RobotSimulator',
                symbolName: 'OpButton',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_PasswordButton4}": [
                ["style", "top", '31px'],
                ["style", "opacity", '0'],
                ["style", "left", '174px']
            ],
            "${_PasswordButton5}": [
                ["style", "top", '80px'],
                ["style", "opacity", '0'],
                ["style", "left", '174px']
            ],
            "${_ShowInFiveButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '41px'],
                ["style", "top", '32px']
            ],
            "${_Frame}": [
                ["style", "-webkit-transform-origin", [50,50], {valueTemplate:'@@0@@% @@1@@%'} ],
                ["style", "-moz-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-ms-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "msTransformOrigin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-o-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "border-style", 'solid'],
                ["transform", "scaleX", '1'],
                ["gradient", "background-image", [73,[['rgba(158,104,110,1.00)',0],['rgba(180,70,70,1.00)',100]]]],
                ["style", "left", '0%'],
                ["style", "width", '89.93%'],
                ["style", "top", '0%'],
                ["style", "opacity", '0'],
                ["style", "min-width", '0px'],
                ["style", "max-width", 'none'],
                ["style", "height", '85.42%'],
                ["style", "border-width", '14px'],
                ["transform", "scaleY", '1'],
                ["color", "background-color", 'rgba(216,153,153,1.00)']
            ],
            "${_HomeButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '41px'],
                ["style", "top", '112px']
            ],
            "${_PasswordButton1}": [
                ["style", "top", '31px'],
                ["style", "opacity", '0'],
                ["style", "left", '73px']
            ],
            "${_RobotSimulator}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '41px'],
                ["style", "top", '148px']
            ],
            "${_FrameDiv}": [
                ["style", "height", '200px'],
                ["style", "top", '50%'],
                ["style", "left", '50%'],
                ["style", "width", '300px']
            ],
            "${_PasswordButton6}": [
                ["style", "top", '129px'],
                ["style", "opacity", '0'],
                ["style", "left", '174px']
            ],
            "${_ShowNowButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '161px'],
                ["style", "top", '31px']
            ],
            "${_CalibrateButton}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '161px'],
                ["style", "top", '113px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_PasswordButton2}": [
                ["style", "top", '80px'],
                ["style", "opacity", '0'],
                ["style", "left", '73px']
            ],
            "${_RestartUI}": [
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '161px'],
                ["style", "top", '148px']
            ],
            "${_PasswordButton3}": [
                ["style", "top", '129px'],
                ["style", "opacity", '0'],
                ["style", "left", '73px']
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
            autoPlay: true,
            labels: {
                "Start": 1000,
                "Close": 2000,
                "ValidPW": 3000
            },
            timeline: [
                { id: "eid348", tween: [ "style", "${_ShowNowButton}", "left", '161px', { fromValue: '161px'}], position: 3250, duration: 0 },
                { id: "eid354", tween: [ "style", "${_ShowNowButton}", "top", '31px', { fromValue: '31px'}], position: 3250, duration: 0 },
                { id: "eid619", tween: [ "style", "${_RestartUI}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid353", tween: [ "style", "${_ShowInFiveButton}", "top", '32px', { fromValue: '32px'}], position: 3250, duration: 0 },
                { id: "eid351", tween: [ "style", "${_ShowInFiveButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid305", tween: [ "style", "${_PasswordButton1}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid299", tween: [ "style", "${_PasswordButton1}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid735", tween: [ "style", "${_RobotSimulator}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid337", tween: [ "style", "${_CalibrateButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid620", tween: [ "style", "${_RestartUI}", "top", '148px', { fromValue: '148px'}], position: 3250, duration: 0 },
                { id: "eid304", tween: [ "style", "${_PasswordButton6}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid298", tween: [ "style", "${_PasswordButton6}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid346", tween: [ "style", "${_ShowNowButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid734", tween: [ "style", "${_RobotSimulator}", "top", '148px', { fromValue: '148px'}], position: 3250, duration: 0 },
                { id: "eid350", tween: [ "style", "${_ShowInFiveButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid279", tween: [ "style", "${_Frame}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid280", tween: [ "style", "${_Frame}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid293", tween: [ "style", "${_Frame}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 0 },
                { id: "eid335", tween: [ "style", "${_HomeButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid302", tween: [ "style", "${_PasswordButton5}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid296", tween: [ "style", "${_PasswordButton5}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid300", tween: [ "style", "${_PasswordButton3}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid294", tween: [ "style", "${_PasswordButton3}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid736", tween: [ "style", "${_RobotSimulator}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid301", tween: [ "style", "${_PasswordButton2}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid295", tween: [ "style", "${_PasswordButton2}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid315", tween: [ "style", "${_PasswordButton2}", "opacity", '0', { fromValue: '1'}], position: 3002, duration: 248 },
                { id: "eid338", tween: [ "style", "${_CalibrateButton}", "left", '161px', { fromValue: '161px'}], position: 3250, duration: 0 },
                { id: "eid336", tween: [ "style", "${_CalibrateButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid618", tween: [ "style", "${_RestartUI}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 },
                { id: "eid347", tween: [ "style", "${_ShowNowButton}", "opacity", '1', { fromValue: '0'}], position: 3000, duration: 250 },
                { id: "eid303", tween: [ "style", "${_PasswordButton4}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 250 },
                { id: "eid297", tween: [ "style", "${_PasswordButton4}", "opacity", '0', { fromValue: '1'}], position: 2000, duration: 250 },
                { id: "eid314", tween: [ "style", "${_PasswordButton4}", "opacity", '0', { fromValue: '1'}], position: 3002, duration: 248 },
                { id: "eid333", tween: [ "style", "${_HomeButton}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0 }            ]
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
                    rect: ['0px', '0px', '0px', '0px', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    id: 'RoundRect',
                    stroke: [14, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(97,97,97,1.00)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_RoundRect}": [
                ["color", "background-color", 'rgba(97,97,97,1.00)'],
                ["style", "top", '0px'],
                ["style", "height", '32px'],
                ["style", "border-style", 'none'],
                ["style", "left", '0px'],
                ["style", "width", '51px']
            ],
            "${symbolSelector}": [
                ["style", "height", '32px'],
                ["style", "width", '51px']
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
                    rect: ['0', '0', '97', '21', 'auto', 'auto'],
                    c: [
                    {
                        rect: ['0px', '0px', '97px', '21px', 'auto', 'auto'],
                        borderRadius: ['10px', '10px', '10px', '10px'],
                        stroke: [14, 'rgb(0, 0, 0)', 'none'],
                        id: 'ButtonFrame',
                        opacity: 1,
                        type: 'rect',
                        fill: ['rgba(79,79,79,1.00)']
                    },
                    {
                        font: ['Verdana, Geneva, sans-serif', 13, 'rgba(255,255,255,1.00)', 'normal', 'none', ''],
                        type: 'text',
                        align: 'center',
                        id: 'ButtonLabel',
                        opacity: 1,
                        text: '(title)',
                        rect: ['0px', '3px', '97px', 'auto', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_ButtonFrame}": [
                ["style", "top", '0px'],
                ["color", "background-color", 'rgba(79,79,79,1)'],
                ["style", "opacity", '1'],
                ["style", "height", '21px'],
                ["style", "border-style", 'none'],
                ["style", "left", '0px'],
                ["style", "width", '97px']
            ],
            "${_ButtonLabel}": [
                ["style", "top", '3px'],
                ["style", "font-size", '13px'],
                ["style", "text-align", 'center'],
                ["style", "opacity", '1'],
                ["color", "color", 'rgba(255,255,255,1.00)'],
                ["style", "font-family", 'Verdana, Geneva, sans-serif'],
                ["style", "left", '0px'],
                ["style", "width", '97px']
            ],
            "${symbolSelector}": [
                ["style", "height", '21px'],
                ["style", "width", '97px']
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
                "Touch": 1000,
                "Repeat": 1198
            },
            timeline: [
                { id: "eid331", tween: [ "color", "${_ButtonFrame}", "background-color", 'rgba(95,187,183,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(79,79,79,1)'}], position: 1000, duration: 100 },
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
                    rect: ['0px', '0px', '131px', '29px', 'auto', 'auto'],
                    id: 'Layer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    type: 'rect',
                    fill: ['rgba(198,165,18,1.00)'],
                    c: [
                    {
                        font: ['Arial, Helvetica, sans-serif', 18, 'rgba(255,255,255,1)', '400', 'none', 'normal'],
                        type: 'text',
                        id: 'Layername',
                        text: 'Windows',
                        align: 'center',
                        rect: ['0px', '6px', '131px', '29px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Layername}": [
                ["style", "top", '6px'],
                ["style", "text-align", 'center'],
                ["style", "height", '29px'],
                ["style", "font-size", '18px'],
                ["style", "left", '0px'],
                ["style", "width", '131px']
            ],
            "${_Layer}": [
                ["color", "background-color", 'rgba(198,165,18,1)'],
                ["style", "bottom", 'auto'],
                ["style", "right", 'auto'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '131px']
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
                { id: "eid663", tween: [ "color", "${_Layer}", "background-color", 'rgba(198,165,18,1)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(198,165,18,1)'}], position: 1000, duration: 0, easing: "easeOutQuad" },
                { id: "eid664", tween: [ "color", "${_Layer}", "background-color", 'rgba(140,140,140,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(198,165,18,1)'}], position: 2008, duration: 0, easing: "easeOutQuad" },
                { id: "eid669", tween: [ "color", "${_Layer}", "background-color", 'rgba(84,210,70,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(140,140,140,1.00)'}], position: 3000, duration: 0 }            ]
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
                    rect: ['0px', '0px', '131px', '29px', 'auto', 'auto'],
                    id: 'Layer',
                    stroke: [0, 'rgba(0,0,0,1)', 'none'],
                    type: 'rect',
                    fill: ['rgba(72,148,187,1.00)'],
                    c: [
                    {
                        font: ['Arial, Helvetica, sans-serif', 18, 'rgba(255,255,255,1)', '400', 'none', 'normal'],
                        type: 'text',
                        id: 'Layername',
                        text: 'Personalization',
                        align: 'center',
                        rect: ['0px', '6px', '131px', '29px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Layername}": [
                ["style", "top", '6px'],
                ["style", "text-align", 'center'],
                ["style", "height", '29px'],
                ["style", "width", '131px'],
                ["style", "left", '0px'],
                ["style", "font-size", '18px']
            ],
            "${_Layer}": [
                ["color", "background-color", 'rgba(72,148,187,1)'],
                ["style", "bottom", 'auto'],
                ["style", "right", 'auto'],
                ["style", "left", '0px'],
                ["style", "top", '0px']
            ],
            "${symbolSelector}": [
                ["style", "height", '29px'],
                ["style", "width", '131px']
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
                { id: "eid666", tween: [ "color", "${_Layer}", "background-color", 'rgba(72,148,187,1)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(72,148,187,1)'}], position: 1000, duration: 0, easing: "easeOutQuad" },
                { id: "eid667", tween: [ "color", "${_Layer}", "background-color", 'rgba(140,140,140,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(72,148,187,1)'}], position: 2013, duration: 0, easing: "easeOutQuad" },
                { id: "eid670", tween: [ "color", "${_Layer}", "background-color", 'rgba(84,210,70,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(140,140,140,1.00)'}], position: 3000, duration: 0 }            ]
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
                    type: 'text',
                    rect: ['auto', 'auto', 'auto', 'auto', '311px', '201px'],
                    id: 'Text',
                    text: 'Sample Background',
                    align: 'center',
                    font: ['Lucida Sans Unicode, Lucida Grande, sans-serif', null, 'rgba(180,180,180,1.00)', '400', 'none', 'normal']
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
            "${_Text}": [
                ["style", "bottom", '-21px'],
                ["color", "color", 'rgba(180,180,180,1.00)'],
                ["style", "right", '11px'],
                ["style", "left", 'auto'],
                ["style", "font-size", '25px'],
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
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '99.15%']
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
                { id: "eid565", tween: [ "style", "${_Rectangle}", "top", '41.7%', { fromValue: '44.06%'}], position: 0, duration: 435, easing: "swing" },
                { id: "eid566", tween: [ "style", "${_Rectangle}", "top", '44.82%', { fromValue: '41.7%'}], position: 435, duration: 1200, easing: "swing" },
                { id: "eid567", tween: [ "style", "${_Rectangle}", "top", '38.6%', { fromValue: '44.82%'}], position: 1635, duration: 1179, easing: "swing" },
                { id: "eid568", tween: [ "style", "${_Rectangle}", "top", '44.06%', { fromValue: '38.6%'}], position: 2814, duration: 1186, easing: "swing" },
                { id: "eid569", tween: [ "style", "${_RectangleCopy}", "left", '-5.47%', { fromValue: '-7.6%'}], position: 0, duration: 553, easing: "swing" },
                { id: "eid570", tween: [ "style", "${_RectangleCopy}", "left", '-6.67%', { fromValue: '-5.47%'}], position: 553, duration: 1835, easing: "swing" },
                { id: "eid571", tween: [ "style", "${_RectangleCopy}", "left", '-4.7%', { fromValue: '-6.67%'}], position: 2388, duration: 698, easing: "swing" },
                { id: "eid572", tween: [ "style", "${_RectangleCopy}", "left", '-7.6%', { fromValue: '-4.7%'}], position: 3086, duration: 914, easing: "swing" },
                { id: "eid499", tween: [ "style", "${_Text}", "right", '31px', { fromValue: '11px'}], position: 0, duration: 1167, easing: "swing" },
                { id: "eid500", tween: [ "style", "${_Text}", "right", '13px', { fromValue: '31px'}], position: 1167, duration: 620, easing: "swing" },
                { id: "eid516", tween: [ "style", "${_Text}", "right", '0px', { fromValue: '13px'}], position: 1787, duration: 1463, easing: "swing" },
                { id: "eid528", tween: [ "style", "${_Text}", "right", '11px', { fromValue: '0px'}], position: 3250, duration: 750, easing: "swing" },
                { id: "eid497", tween: [ "style", "${_Text}", "bottom", '-26px', { fromValue: '-21px'}], position: 0, duration: 881, easing: "swing" },
                { id: "eid498", tween: [ "style", "${_Text}", "bottom", '-7px', { fromValue: '-26px'}], position: 881, duration: 754, easing: "swing" },
                { id: "eid515", tween: [ "style", "${_Text}", "bottom", '-31px', { fromValue: '-7px'}], position: 1635, duration: 1258, easing: "swing" },
                { id: "eid527", tween: [ "style", "${_Text}", "bottom", '-21px', { fromValue: '-31px'}], position: 2893, duration: 1107, easing: "swing" },
                { id: "eid573", tween: [ "style", "${_RectangleCopy}", "top", '49.68%', { fromValue: '47.84%'}], position: 0, duration: 750, easing: "swing" },
                { id: "eid574", tween: [ "style", "${_RectangleCopy}", "top", '44.82%', { fromValue: '49.68%'}], position: 750, duration: 1447, easing: "swing" },
                { id: "eid575", tween: [ "style", "${_RectangleCopy}", "top", '50.37%', { fromValue: '44.82%'}], position: 2197, duration: 711, easing: "swing" },
                { id: "eid576", tween: [ "style", "${_RectangleCopy}", "top", '47.84%', { fromValue: '50.37%'}], position: 2908, duration: 1092, easing: "swing" },
                { id: "eid581", tween: [ "style", "${_RectangleCopy2}", "top", '58.08%', { fromValue: '55.29%'}], position: 0, duration: 1085, easing: "swing" },
                { id: "eid582", tween: [ "style", "${_RectangleCopy2}", "top", '59.49%', { fromValue: '58.08%'}], position: 1085, duration: 773, easing: "swing" },
                { id: "eid583", tween: [ "style", "${_RectangleCopy2}", "top", '52.15%', { fromValue: '59.49%'}], position: 1857, duration: 1051, easing: "swing" },
                { id: "eid584", tween: [ "style", "${_RectangleCopy2}", "top", '55.29%', { fromValue: '52.15%'}], position: 2908, duration: 1092, easing: "swing" },
                { id: "eid577", tween: [ "style", "${_RectangleCopy2}", "left", '3.48%', { fromValue: '0.08%'}], position: 0, duration: 860, easing: "swing" },
                { id: "eid578", tween: [ "style", "${_RectangleCopy2}", "left", '5.62%', { fromValue: '3.48%'}], position: 860, duration: 1254, easing: "swing" },
                { id: "eid579", tween: [ "style", "${_RectangleCopy2}", "left", '0.08%', { fromValue: '5.62%'}], position: 2114, duration: 1177, easing: "swing" },
                { id: "eid580", tween: [ "style", "${_RectangleCopy2}", "left", '0.08%', { fromValue: '0.08%'}], position: 3292, duration: 708, easing: "swing" },
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
                    type: 'rect',
                    id: 'Desktop',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    rect: ['33.2%', '22.1%', '33.6%', '63%', 'auto', 'auto'],
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    rect: ['-0.1%', '8.1%', '100%', '42px', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 29, 'rgba(0,0,0,1.00)', '400', 'none', 'normal'],
                    id: 'BuildingDesktops',
                    text: 'Layering your desktop...',
                    align: 'center',
                    type: 'text'
                },
                {
                    id: 'OS',
                    type: 'rect',
                    rect: ['37.1%', '73.2%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Layer1',
                    type: 'rect',
                    rect: ['37.1%', '63.8%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Layer2',
                    type: 'rect',
                    rect: ['37.1%', '54.2%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Layer3',
                    type: 'rect',
                    rect: ['37.1%', '44.8%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Layer4',
                    type: 'rect',
                    rect: ['37.1%', '35.2%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Personalization',
                    type: 'rect',
                    rect: ['37.1%', '25.8%', 'auto', 'auto', 'auto', 'auto']
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
                id: 'Layer2',
                symbolName: 'LayerSymbol',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_OS}": [
                ["style", "top", '73.18%'],
                ["style", "opacity", '0'],
                ["style", "left", '37.11%']
            ],
            "${_Desktop}": [
                ["style", "top", '22.14%'],
                ["style", "opacity", '0'],
                ["style", "left", '33.2%'],
                ["style", "height", '63.02%']
            ],
            "${_Layer2}": [
                ["style", "top", '54.17%'],
                ["style", "opacity", '0'],
                ["style", "left", '37.11%']
            ],
            "${_Layer3}": [
                ["style", "top", '44.79%'],
                ["style", "opacity", '0'],
                ["style", "left", '37.11%']
            ],
            "${_Personalization}": [
                ["style", "top", '25.78%'],
                ["style", "opacity", '0'],
                ["style", "left", '37.11%']
            ],
            "${_BuildingDesktops}": [
                ["style", "-webkit-transform-origin", [50,50], {valueTemplate:'@@0@@% @@1@@%'} ],
                ["style", "-moz-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-ms-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "msTransformOrigin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "-o-transform-origin", [50,50],{valueTemplate:'@@0@@% @@1@@%'}],
                ["style", "bottom", 'auto'],
                ["color", "color", 'rgba(0,0,0,1.00)'],
                ["style", "opacity", '0'],
                ["style", "left", '-0.06%'],
                ["style", "font-size", '29px'],
                ["style", "top", '8.07%'],
                ["transform", "scaleY", '1'],
                ["style", "height", '42px'],
                ["style", "width", '100%'],
                ["style", "right", 'auto'],
                ["transform", "scaleX", '1']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Layer1}": [
                ["style", "top", '63.8%'],
                ["style", "opacity", '0'],
                ["style", "left", '37.11%']
            ],
            "${_Layer4}": [
                ["style", "top", '35.16%'],
                ["style", "opacity", '0'],
                ["style", "left", '37.11%']
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
                "Start": 1000,
                "Process": 2031,
                "Finish": 3500
            },
            timeline: [
                { id: "eid639", tween: [ "style", "${_Personalization}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid651", tween: [ "style", "${_Personalization}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid161", tween: [ "style", "${_BuildingDesktops}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid614", tween: [ "style", "${_BuildingDesktops}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid647", tween: [ "style", "${_Layer4}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid655", tween: [ "style", "${_Layer4}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid641", tween: [ "style", "${_Layer3}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid652", tween: [ "style", "${_Layer3}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid637", tween: [ "style", "${_Layer1}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid650", tween: [ "style", "${_Layer1}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid649", tween: [ "style", "${_Layer2}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid656", tween: [ "style", "${_Layer2}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid645", tween: [ "style", "${_OS}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid654", tween: [ "style", "${_OS}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" },
                { id: "eid643", tween: [ "style", "${_Desktop}", "opacity", '1', { fromValue: '0'}], position: 1000, duration: 500, easing: "easeInQuad" },
                { id: "eid653", tween: [ "style", "${_Desktop}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeOutQuad" }            ]
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
                    rect: ['17.4%', '26.8%', '65.2%', '46.1%', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    id: 'RoundRect',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(192,192,192,1)']
                },
                {
                    type: 'text',
                    rect: ['23.6%', '32.7%', 'auto', 'auto', 'auto', 'auto'],
                    id: 'Text',
                    text: 'Which controller is this?',
                    align: 'center',
                    font: ['Arial, Helvetica, sans-serif', 25, 'rgba(0,0,0,1.00)', '400', 'none', 'normal']
                },
                {
                    id: 'One',
                    type: 'rect',
                    rect: ['40.4%', '44.5%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'Two',
                    type: 'rect',
                    rect: ['40.4%', '54.4%', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'None',
                    type: 'rect',
                    rect: ['40.4%', '64.3%', 'auto', 'auto', 'auto', 'auto']
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
            "${_RoundRect}": [
                ["style", "left", '17.38%'],
                ["style", "top", '26.82%']
            ],
            "${_One}": [
                ["style", "left", '40.43%'],
                ["style", "top", '44.53%']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Two}": [
                ["style", "left", '40.43%'],
                ["style", "top", '54.43%']
            ],
            "${_None}": [
                ["style", "left", '40.43%']
            ],
            "${_Text}": [
                ["color", "color", 'rgba(0,0,0,1.00)'],
                ["style", "top", '32.67%'],
                ["style", "left", '23.63%'],
                ["style", "font-size", '25px']
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
                    display: 'none',
                    type: 'rect',
                    id: 'OptionDesktopRepair',
                    rect: ['135', '257px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'OptionUpgradeApp',
                    rect: ['135', '172px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    display: 'none',
                    type: 'rect',
                    id: 'OptionUpgradeOS',
                    rect: ['135', '85px', 'auto', 'auto', 'auto', 'auto']
                },
                {
                    id: 'DoneButton',
                    type: 'rect',
                    rect: ['407', '24', 'auto', 'auto', 'auto', 'auto']
                }
            ],
            symbolInstances: [
            {
                id: 'OptionUpgradeOS',
                symbolName: 'OptionUpgradeOS',
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
                id: 'OptionUpgradeApp',
                symbolName: 'OptionUpgradeApp',
                autoPlay: {

               }
            },
            {
                id: 'OptionDesktopRepair',
                symbolName: 'OptionDesktopRepair',
                autoPlay: {

               }
            }            ]
        },
    states: {
        "Base State": {
            "${_OptionUpgradeOS}": [
                ["style", "top", '85px'],
                ["style", "display", 'none']
            ],
            "${_OptionUpgradeApp}": [
                ["style", "top", '172px'],
                ["style", "display", 'none']
            ],
            "${_OptionDesktopRepair}": [
                ["style", "top", '257px'],
                ["style", "display", 'none']
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
            duration: 2500,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Continue": 250,
                "ThreeOptions": 1000,
                "TwoOptions": 2000
            },
            timeline: [
                { id: "eid708", tween: [ "style", "${_OptionUpgradeOS}", "top", '85px', { fromValue: '85px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid711", tween: [ "style", "${_OptionUpgradeOS}", "top", '129px', { fromValue: '85px'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid702", tween: [ "style", "${_OptionUpgradeApp}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid707", tween: [ "style", "${_OptionUpgradeApp}", "display", 'none', { fromValue: 'block'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid701", tween: [ "style", "${_OptionUpgradeOS}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid710", tween: [ "style", "${_OptionDesktopRepair}", "top", '257px', { fromValue: '257px'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid712", tween: [ "style", "${_OptionDesktopRepair}", "top", '213px', { fromValue: '257px'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid703", tween: [ "style", "${_OptionDesktopRepair}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0, easing: "easeInOutQuad" }            ]
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
                        rect: ['0px', '0px', '241px', '63px', 'auto', 'auto'],
                        borderRadius: ['10px', '10px', '10px', '10px'],
                        id: 'Option',
                        stroke: [0, 'rgb(0, 0, 0)', 'none'],
                        type: 'rect',
                        fill: ['rgba(192,192,192,1)']
                    },
                    {
                        font: ['Arial, Helvetica, sans-serif', 27, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                        type: 'text',
                        id: 'Text2',
                        text: 'Repair desktop',
                        align: 'center',
                        rect: ['0px', '16px', '241px', '32px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Option}": [
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["style", "height", '63px']
            ],
            "${_Button}": [
                ["style", "opacity", '1']
            ],
            "${_Text2}": [
                ["style", "top", '16px'],
                ["style", "width", '241px'],
                ["style", "height", '32px'],
                ["color", "color", 'rgba(255,0,0,1)'],
                ["style", "left", '0px'],
                ["style", "font-size", '27px']
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
                { id: "eid697", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0.000000'}], position: 2303, duration: 697, easing: "easeInOutQuad" },
                { id: "eid691", tween: [ "color", "${_Text2}", "color", 'rgba(255,0,0,1)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(255,0,0,1)'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid690", tween: [ "color", "${_Text2}", "color", 'rgba(148,148,148,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(255,0,0,1)'}], position: 3000, duration: 0, easing: "easeInOutQuad" }            ]
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
                        rect: ['0px', '0px', '241px', '63px', 'auto', 'auto'],
                        borderRadius: ['10px', '10px', '10px', '10px'],
                        id: 'Option',
                        stroke: [0, 'rgb(0, 0, 0)', 'none'],
                        type: 'rect',
                        fill: ['rgba(192,192,192,1)']
                    },
                    {
                        font: ['Arial, Helvetica, sans-serif', 27, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                        type: 'text',
                        id: 'Text2',
                        text: 'Upgrade OS',
                        align: 'center',
                        rect: ['0px', '16px', '241px', '32px', 'auto', 'auto']
                    }]
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Option}": [
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["style", "height", '63px']
            ],
            "${_Button}": [
                ["style", "opacity", '1']
            ],
            "${_Text2}": [
                ["style", "top", '16px'],
                ["style", "font-size", '27px'],
                ["style", "height", '32px'],
                ["color", "color", 'rgba(255,0,0,1)'],
                ["style", "left", '0px'],
                ["style", "width", '241px']
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
                { id: "eid697", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0.000000'}], position: 2303, duration: 697, easing: "easeInOutQuad" },
                { id: "eid691", tween: [ "color", "${_Text2}", "color", 'rgba(255,0,0,1)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(255,0,0,1)'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid690", tween: [ "color", "${_Text2}", "color", 'rgba(148,148,148,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(255,0,0,1)'}], position: 3000, duration: 0, easing: "easeInOutQuad" }            ]
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
                        rect: ['0px', '0px', '241px', '63px', 'auto', 'auto'],
                        borderRadius: ['10px', '10px', '10px', '10px'],
                        id: 'Option',
                        stroke: [0, 'rgb(0, 0, 0)', 'none'],
                        type: 'rect',
                        fill: ['rgba(192,192,192,1)']
                    },
                    {
                        rect: ['0px', '16px', '241px', '32px', 'auto', 'auto'],
                        font: ['Arial, Helvetica, sans-serif', 27, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                        id: 'Title',
                        text: 'Upgrade App',
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
            "${_Option}": [
                ["style", "top", '0px'],
                ["style", "left", '0px'],
                ["style", "height", '63px']
            ],
            "${_Title}": [
                ["style", "top", '16px'],
                ["style", "font-size", '27px'],
                ["style", "height", '32px'],
                ["color", "color", 'rgba(255,0,0,1)'],
                ["style", "left", '0px'],
                ["style", "width", '241px']
            ],
            "${_Button}": [
                ["style", "opacity", '1']
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
                { id: "eid697", tween: [ "style", "${_Button}", "opacity", '1', { fromValue: '0.000000'}], position: 2303, duration: 697, easing: "easeInOutQuad" },
                { id: "eid691", tween: [ "color", "${_Title}", "color", 'rgba(255,0,0,1)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(255,0,0,1)'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid690", tween: [ "color", "${_Title}", "color", 'rgba(148,148,148,1.00)', { animationColorSpace: 'RGB', valueTemplate: undefined, fromValue: 'rgba(255,0,0,1)'}], position: 3000, duration: 0, easing: "easeInOutQuad" }            ]
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
                    rect: ['107px', '142px', 'auto', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    id: 'Text',
                    text: 'That\'s all folks!',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['0px', '0px', '100%', '100%', 'auto', 'auto'],
                    opacity: 0,
                    id: 'Rectangle',
                    stroke: [0, 'rgb(0, 0, 0)', 'none'],
                    type: 'rect',
                    fill: ['rgba(192,192,192,1)']
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Rectangle}": [
                ["style", "opacity", '0']
            ],
            "${_Text}": [
                ["style", "top", '142px'],
                ["style", "opacity", '0'],
                ["style", "left", '107px']
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
            duration: 4000,
            autoPlay: false,
            labels: {
                "Start": 0,
                "Exit": 3500
            },
            timeline: [
                { id: "eid714", tween: [ "style", "${_Text}", "opacity", '1', { fromValue: '0'}], position: 0, duration: 500, easing: "easeInOutQuad" },
                { id: "eid715", tween: [ "style", "${_Text}", "opacity", '0', { fromValue: '1'}], position: 3500, duration: 500, easing: "easeInOutQuad" }            ]
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
                    rect: ['91px', '70px', 'auto', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    id: 'Title',
                    text: 'Repair desktop...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Waiting',
                    text: 'Waiting...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Running',
                    text: 'Running...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Done',
                    text: 'Done!',
                    align: 'center',
                    type: 'text'
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Title}": [
                ["style", "left", '91px'],
                ["style", "top", '70px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Running}": [
                ["style", "display", 'block'],
                ["style", "font-size", '28px'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ],
            "${_Waiting}": [
                ["style", "display", 'block'],
                ["style", "width", '508px'],
                ["style", "left", '4px'],
                ["style", "font-size", '28px']
            ],
            "${_Done}": [
                ["style", "display", 'block'],
                ["style", "width", '508px'],
                ["style", "left", '4px'],
                ["style", "font-size", '28px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 6000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Pending": 2000,
                "Executing": 3000,
                "Finish": 4000
            },
            timeline: [
                { id: "eid720", tween: [ "style", "${_Done}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid729", tween: [ "style", "${_Done}", "display", 'block', { fromValue: 'none'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid721", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid727", tween: [ "style", "${_Running}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid728", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid722", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid725", tween: [ "style", "${_Waiting}", "display", 'block', { fromValue: 'none'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid726", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 3000, duration: 0, easing: "easeInOutQuad" }            ]
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
                    rect: ['0px', '70px', '512px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    id: 'Title',
                    text: 'Upgrade OS',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Waiting',
                    text: 'Waiting...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Running',
                    text: 'Running...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Done',
                    text: 'Done!',
                    align: 'center',
                    type: 'text'
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Title}": [
                ["style", "top", '70px'],
                ["style", "left", '0px'],
                ["style", "width", '512px']
            ],
            "${_Waiting}": [
                ["style", "display", 'block'],
                ["style", "font-size", '28px'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ],
            "${_Running}": [
                ["style", "display", 'block'],
                ["style", "width", '508px'],
                ["style", "left", '4px'],
                ["style", "font-size", '28px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Done}": [
                ["style", "display", 'block'],
                ["style", "font-size", '28px'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 6000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Pending": 2000,
                "Executing": 3000,
                "Finish": 4000
            },
            timeline: [
                { id: "eid722", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid725", tween: [ "style", "${_Waiting}", "display", 'block', { fromValue: 'none'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid726", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 3000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid721", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid727", tween: [ "style", "${_Running}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid728", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid720", tween: [ "style", "${_Done}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid729", tween: [ "style", "${_Done}", "display", 'block', { fromValue: 'none'}], position: 4000, duration: 0, easing: "easeInOutQuad" }            ]
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
                    rect: ['4px', '70px', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 44, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    id: 'Title',
                    text: 'Upgrade app',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Waiting',
                    text: 'Waiting...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Running',
                    text: 'Running...',
                    align: 'center',
                    type: 'text'
                },
                {
                    rect: ['4px', '193', '508px', 'auto', 'auto', 'auto'],
                    font: ['Arial, Helvetica, sans-serif', 28, 'rgba(255,0,0,1)', '400', 'none', 'normal'],
                    display: 'block',
                    id: 'Done',
                    text: 'Done!',
                    align: 'center',
                    type: 'text'
                }
            ],
            symbolInstances: [
            ]
        },
    states: {
        "Base State": {
            "${_Title}": [
                ["style", "top", '70px'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ],
            "${symbolSelector}": [
                ["style", "height", '100%'],
                ["style", "width", '100%']
            ],
            "${_Running}": [
                ["style", "display", 'block'],
                ["style", "width", '508px'],
                ["style", "left", '4px'],
                ["style", "font-size", '28px']
            ],
            "${_Waiting}": [
                ["style", "display", 'block'],
                ["style", "font-size", '28px'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ],
            "${_Done}": [
                ["style", "display", 'block'],
                ["style", "font-size", '28px'],
                ["style", "left", '4px'],
                ["style", "width", '508px']
            ]
        }
    },
    timelines: {
        "Default Timeline": {
            fromState: "Base State",
            toState: "",
            duration: 6000,
            autoPlay: false,
            labels: {
                "Start": 1000,
                "Pending": 2000,
                "Executing": 3000,
                "Finish": 4000
            },
            timeline: [
                { id: "eid722", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid725", tween: [ "style", "${_Waiting}", "display", 'block', { fromValue: 'none'}], position: 2000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid726", tween: [ "style", "${_Waiting}", "display", 'none', { fromValue: 'block'}], position: 3000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid721", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid727", tween: [ "style", "${_Running}", "display", 'block', { fromValue: 'none'}], position: 3000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid728", tween: [ "style", "${_Running}", "display", 'none', { fromValue: 'block'}], position: 4000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid720", tween: [ "style", "${_Done}", "display", 'none', { fromValue: 'block'}], position: 1000, duration: 0, easing: "easeInOutQuad" },
                { id: "eid729", tween: [ "style", "${_Done}", "display", 'block', { fromValue: 'none'}], position: 4000, duration: 0, easing: "easeInOutQuad" }            ]
        }
    }
},
"TimerRestart": {
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
                    rect: ['-72.8%', '319%', '12px', '15px', 'auto', 'auto'],
                    borderRadius: ['10px', '10px', '10px', '10px'],
                    type: 'rect',
                    id: 'TimerRestart',
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
            "${_TimerRestart}": [
                ["style", "top", '318.99%'],
                ["style", "height", '15px'],
                ["style", "display", 'none'],
                ["style", "opacity", '0'],
                ["style", "left", '-72.71%'],
                ["style", "width", '12px']
            ],
            "${symbolSelector}": [
                ["style", "height", '79px'],
                ["style", "width", '136px']
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
                { id: "eid732", tween: [ "style", "${_TimerRestart}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
                { id: "eid733", tween: [ "style", "${_TimerRestart}", "display", 'block', { fromValue: 'none'}], position: 1000, duration: 0 }            ]
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
