import {
  NgModule
} from '@angular/core';
import {
  CommonModule
} from '@angular/common';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class ConstantsModule {

  HTTP_STATUS ={
    "SUCCESS":"SUCCESS"
  }

  selectedFilterParams =null;
  IS_EXTERNAL_SYSTEM:boolean = false;
  IS_RAINFALL_EMBEDED_TABLE: boolean = false;
  MILLIS_IN_A_DAY = 24 * 60 * 60 * 1000;
  DEBUGGER = false;
  EVENTGEN_YEAR = 'yyyy';
  EVENTGEN_MONTH = 'yyyyMM';
  EVENTGEN_DAY = 'yyyyMMdd';
  KERALA_AREA = 38863;
  mmToKm = 0.000001;
  cubicKmToTMC = 35.314666721;
  McmToTMC = 28.31684;

  DEFAULT_STATE = "Kerala";
  KERALA_UUID = "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6";
  KERALA = "Kerala";
  KERALA_SW = "Kerala SW";
  PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuDEWABVXfJ9se3bqfYC7zPnMjmivjb7XbvfIJiQEosQ48Iws6kMfAsPyu1aHiI1PSy0PvpXR08ZNyUKzTdqb3BZ7tIprSRPtGZKV9hoWQEcSL7w+Og6j1pmvlT5vxMbnDrzlbvV6EDG11I9CFULWw2w4yQD2vm9z7KSAN9eawUwIDAQAB";


  SOURCES = {
    CWC: "CWC",
    IMD: "IMD",
    KERALA_SW: "Kerala SW",
    STATE_AND_CENTRAL_STATION: "STATE_AND_CENTRAL_STATION",
    IMD_GRID : "IMD GRID",
    IMD_1 : 'IMD_1',
    GPM_GRID : "GPM GRID",
    IDRB:"IDRB",
    IDRB_VALIDATED: "IDRB VALIDATED",
    KSEB:'KSEB',
    KWA:'KWA',
    CGWB :'CGWB',
    NRSC :'NRSC',
    IMD_05:"IMD_05",
    LSGD: "LSGD",
    CPCB:"CPCB",
    RTDAS:"RTDAS",
    SKYMET:"SKYMET",
    KERALA_GWD:"Kerala GWD"
  }

  GPM_GRID ="GPM GRID";
  CWC = "CWC";
  IMD = "IMD";
  IDRB = 'IDRB';
  IMD_GRID = "IMD GRID";
  IMD_1 = 'IMD_1'
  IMD_05 = 'IMD_05'
  // IMD_1 = "IMD Grid 1 deg"
  LOW = "LOW";
  VERY_LOW = "VERY_LOW"
  MED = "MED";
  HIGH = "HIGH";
  ADMIN = "ADMIN";
  BASIN = "BASIN";

  MOBILE_APP_REPORT = "Mobile App Report"

  RF_REPORT_DISTRICT_WISE = "District Wise Timeseries";
  RF_REPORT_MANDAL_WISE = "Block Wise Timeseries";
  RF_REPORT_STATION_WISE = "Station Wise Report";
  RF_REPORT_BASIN_WISE = "Basin Wise Timeseries";
  RF_REPORT_PANCHAYATH_WISE="Panchayath Wise Timeseries;"


  RESERVOIR_STATION_WISE = "Level & Storage Timeseries";
  TEMPERATURE_STATION_WISE="Station wise Report";
  HUMIDITY_STATION_WISE="Station wise Report";
  WINDSPEED_STATION_WISE="Station wise Report";
  MI_PONDS_STATION_WISE="Mi Ponds Report";
  WC_STRUCTURES_STATION_WISE="Station wise Report";
  LI_SCHEMES_STATION_WISE="Station wise Report";
  SWATERQUALITY_STATION_WISE="Station wise Report";
  GWATERQUALITY_STATION_WISE="Station wise Report";T
  SOILMOISTURE_STATION_WISE="Station wise Report";
  DOMESTIC_DEMAND_STATION_WISE="Station wise Report";
  INDUSTRIAL_DEMAND_STATION_WISE="Station wise Report";
  CROP_DEMAND_STATION_WISE="Station wise Report";
  HYDRO_POWER_STATION_WISE="Station wise Report";
  WATER_AUDIT_STATION_WISE="Station wise Report";
  BASIN_LEVEL_WATER_BUDGET_STATION_WISE="Basin Level WaterShed Report";
  PANCHAYATH_LEVEL_WATER_BUDGET_STATION_WISE="Admin Level Panchayath  Report";

  SWQ_REPORT_STATION_WISE = "Surface Water Quality Station Wise ";
  GWQ_REPORT_STATION_WISE = "Ground Water Quality Station Wise ";

  
  
  RESERVOIR_STORAGE_COMPARISON = "Storage Comparison";
  RESERVOIR_REPORT_STORAGE_LEVEL = "Level & Storage Bulletin";
  RESERVOIR_REPORT_STORAGE_TREND = "Storage Timeseries";
  RESERVOIR_REPORT_LEVEL_TREND = "Level Timeseries";
  RIVERPOINT_LEVEL_REPORT = "Level & Flow Timeseries";
  RF_SIDETAB_DOWNLOAD = "downloadreport";
  GROUNDWATER_REPORT_STATE_WISE = "State wise Level Report";
  GROUNDWATER_REPORT_DISTRICT_WISE = "District wise Level Report";
  GROUNDWATER_REPORT_STATION_WISE = "Station wise Level Report";
  GROUNDWATER_REPORT_STATE_STATION_WISE = "State Wise Station Level Report";
  GROUNDWATER_SEASONAL_FLUCTUATION = "Report of Seasonal Fluctuation";
  GROUNDWATER_ANNUAL_FLUCTUATION="Report of Annual Fluctuation";
  GROUNDWATER_DECADE_FLUCTUATION="Report of Decadal Water Level Fluctuation";
  GROUNDWATER_DEPTH_TO_WATER_LEVEL ="Report of Depth to Water Level";
  GROUNDWATER_TRENDS ="Report of Trends of Water Level";
  RESERVOIR_SIDETAB_STATE = "admin";
  RESERVOIR_SIDETAB_REGION = "region";
  RESERVOIR_SIDETAB_BASIN = "basin";
  RIVERPOINT_FLOW_COMPARISON = "flow comparison";

  COMPONENT = {
    RAINFALL: 'rainfall',
    RAINFALLACTUAL: 'rainfall-actual',
    RESERVOIR: 'reservoir',
    RESERVOIRACTUAL:'reservoir-actual',
    RIVERPOINT: 'River Authority',
    GROUNDWATER: 'groundwater',
    EVAPOTRANSPIRATION: 'et',
    SOILMOISTURE: 'soilmoisture',
    GWATERQUALITY: 'groundwater_wq',
    SWATERQUALITY: 'surfacewater_wq',
    WATERAUDIT: 'wateraudit',
    WATER_AUDIT: 'water_audit',
    CONTENT_MANAGEMENT: 'contentmanagement',
    MOBILE_APP_REGISTRATION: 'mobileappregistration',
    MI_TANKS: 'mitank',
    MI_PONDS: 'miponds',
    SURFACEWATER: 'surfacewater',
    WCSTRUCTURE: 'wcstructure',
    'WC STRUCTURE':'wc structure',
    TEMPERATURE: 'temperature',
    RIVER_POINT:'riverpoint',
    HUMIDITY: 'humidity',
    WINDSPEED: 'wind-speed',
    WIND: 'wind', 
    LISCHEMES: 'lischemes',
    LI_SCHEMES:'LISCHEME', //Latest
    WC_STRUCTURES:'WC_STRUCTURE', //Latest,
    AGRICULTURE:'agriculture',
    DOMESTIC:'domestic',
    WATER_DEMAND:'water-demand',
    CROP_DEMAND:'CROP_DEMAND',
    INDUSTRY:'industry',
    HYDRO_POWER:'hydro-power',
    INLAND:'inland',
    BASIN_LEVEL_WATER_BUDGET:'basin_level_wb',
    PANCHYATH_LEVEL_WATER_BUDGET:'panchayat_level_wb',
    RAINFALL_FORECAST:'rainfall-forecast',
    WINDSPEED_FORECAST:'wind-speed-forecast',
    TEMPERATURE_FORECAST: 'temperature-forecast',
    HUMIDITY_FORECAST: 'humidity-forecast', 
    DEMAND :'demand',
    POWER_STATION: 'POWER_STATION',
    POPULATION: 'POPULATION', 
    INFLOW: 'inflow',
    OUTFLOW: 'outflow',
    HYBRID_RIVER_POINT:'hydbridRiverPoint'
  };


  COMPONENTFORROUTING = {
    RAINFALL: 'rainfall',
    RAINFALLACTUAL: 'rainfall-actual',
    RESERVOIR: 'reservoir',
    RIVERPOINT: 'River Authority',
    RIVER_POINT: 'riverpoint',
    RIVERPOINTS :'river-points',
    GROUNDWATER: 'ground-water',
    GROUND_WATER: 'groundwater',
    EVAPOTRANSPIRATION: 'et',
    SOILMOISTURE: 'soilmoisture',
    SOIL__MOISTURE: 'soil-moisture',
    SOIL_MOISTURE: 'soilmoisture',
    GWATERQUALITY: 'groundwater_wq',
    SWATERQUALITY: 'surfacewater_wq',
    CONTENT_MANAGEMENT: 'contentmanagement',
    MOBILE_APP_REGISTRATION: 'mobileappregistration',
    MI_TANKS: 'mitank',
    MI_PONDS: 'mi-ponds',
    MIPONDS: 'miponds',
    SURFACEWATER: 'surfacewater',
    WCSTRUCTURE: 'wcstructure',
    TEMPERATURE: 'temperature',
    HUMIDITY: 'humidity',
    HUMIDITY_FORECAST : 'humidity-forecast/24/maks',
    WINDSPEED: 'wind-speed',
    WINDSPEED_FORECAST : 'wind-speed-forecast/24/maks',
    LISCHEMES: 'li-schemes',
    LI_SCHEMES: 'lischemes',
    RAINFALLFORECAST : 'rainfall-forecast',
    TEMPERATUREFORECAST: 'temperature-forecast',
    HUMIDITYFORECAST: 'humidity-forecast',
    WINDSPEEDFORECAST: 'wind-speed-forecast',
    RESERVOIRACTUAL: 'reservoir-actual',
    RESERVOIRFORECAST: 'reservoir-forecast',
    GROUNDWATERQUALITY : 'gw_quality',
    SURFACEWATERQUALITY : 'sw_quality',
    AGRICULTURE:'agriculture',
    DOMESTIC:'domestic',
    INDUSTRY:'industry',
    HYDRO_POWER:'hydro-power',
    INLAND:'inland',
    BASIN_LEVEL_WATER_BUDGET:'basin_level_wb',
    PANCHYATH_LEVEL_WATER_BUDGET:'panchayat_level_wb',
    WATERAUDIT :'water_audit',
    WATER_AUDIT:'wateraudit',
    WEATHER_DATA :'Weather Data',
    WATER_RESOURCE_INFORMATION : 'Water Resource Information',
    WATER_RESOURCE_MANAGEMENT : 'Water Resource Management',
    WATER_DEMAND : 'Water Demand'
  };

  MODULE_FOR_ROUTES ={

    BASE_ROUTE_FOR_MIS:'mis',
    BASE_ROUTE_FOR_GIS:'gis',

    //Water Resource Management
    BASIN_LEVEL_WATER_BUDGET:'basin_level_wb',
    PANCHYATH_LEVEL_WATER_BUDGET:'panchayat_level_wb',
    WATERAUDIT:'water_audit',


    //Water Availability
    RESERVOIR:'reservoir-actual',
    MI_PONDS:'mi-ponds',
    WC_STRUCTURES:'wc-structures',
    LI_SCHEMES:'li-schemes',
    SOIL_MOISTURE:'soil-moisture',
    RIVER_POINTS:'river-points',
    GROUND_WATER:'ground-water',
    SURFACE_WATER_QUALITY:'sw_quality',
    GROUND_WATER_QUALITY:'gw_quality',


    //Water Demand
    AGRICULTURE:'agriculture',
    DOMESTIC:'domestic',
    INDUSTRY:'industry',
    HYDRO_POWER:'hydro-power',
    INLAND:'inland',


    //Weather Data
    RAINFALL:'rainfall-actual',
    HUMIDITY:'humidity',
    TEMPERATURE:'temperature',
    WINDSPEED:'wind-speed',

    //Home Routes For Dashboards
    WEATHER_DATA_HOME:'wd/home' ,
    WATER_RESOURCE_MANAGEMENT_HOME:'wrm/home',
    WATER_AVAILABLITY_HOME:'wa-home',
    WATER_DEMAND_HOME:'water-demand-home',
    WATER_RESOURCE_INFORMATION_HOME:'wri',
    WATER_RESOURCE_INFORMATION_HOME_WITH_WA:'wri/wa-home',
    WATER_RESOURCE_INFORMATION_HOME_WITH_WD:'wri/water-demand-home'

  }

 DASHBOARD_MAPPING ={
   GIS:{
    WEATHER_DATA :'Weather Data',
    WATER_RESOURCE_INFO :'Water Resource Information',
    WATER_DEMAND : 'Water Demand',
    WATER_RESOURCE_MANAGEMENT : 'Water Resource Management',
    SURFACE_WATER:'Surface Water',
    GROUND_WATER:'Ground Water',
    WATER_BUDGET:'Water Budget',
    WATER_AVAILABILITY:'Water Availability',
    WATER_QUALITY:'Water Quality',
   },
   MIS:{
    WEATHER_DATA :'Weather Data',
    WATER_RESOURCE_INFO :'Water Resource Information',
    WATER_DEMAND : 'Water Demand',
    WATER_RESOURCE_MANAGEMENT : 'Water Resource Management',
    SURFACE_WATER:'Surface Water',
    WATER_BUDGET:'Water Budget',
    WATER_AVAILABILITY:'Water Availability',
    WATER_QUALITY:'Water Quality',
   },
 };

  IS_REPORTS_SPECIFIC_USER:boolean = false;
  IS_COMPONENT_SPECIFIC_USER:boolean = false;
  DASHBOARD_INFO ={
    MAIN_DASHBOARDS:{
      GIS:{
        [this.DASHBOARD_MAPPING.GIS.WEATHER_DATA]:[
          this.COMPONENT.RAINFALL,this.COMPONENT.TEMPERATURE,this.COMPONENT.HUMIDITY,this.COMPONENT.WINDSPEED
        ],
        [this.DASHBOARD_MAPPING.GIS.WATER_RESOURCE_INFO]:[
          this.COMPONENT.RESERVOIR,this.COMPONENT.RIVER_POINT,this.COMPONENT.MI_TANKS,this.COMPONENT.WCSTRUCTURE,
          this.COMPONENT.LISCHEMES,this.COMPONENT.SOILMOISTURE,this.COMPONENT.SWATERQUALITY,
          this.COMPONENT.GROUNDWATER,this.COMPONENT.GWATERQUALITY,
          this.COMPONENT.AGRICULTURE,this.COMPONENT.INDUSTRY,this.COMPONENT.DOMESTIC,this.COMPONENT.INLAND,this.COMPONENT.HYDRO_POWER
        ],
        [this.DASHBOARD_MAPPING.GIS.WATER_RESOURCE_MANAGEMENT]:[
          this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET,this.COMPONENT.BASIN_LEVEL_WATER_BUDGET,this.COMPONENT.WATERAUDIT
        ],
      },
      MIS:{
        [this.DASHBOARD_MAPPING.MIS.WEATHER_DATA]:[
          this.MODULE_FOR_ROUTES.RAINFALL,this.MODULE_FOR_ROUTES.TEMPERATURE,this.MODULE_FOR_ROUTES.HUMIDITY,this.MODULE_FOR_ROUTES.WINDSPEED
        ],
        [this.DASHBOARD_MAPPING.MIS.WATER_RESOURCE_INFO]:[
          this.MODULE_FOR_ROUTES.RESERVOIR,this.MODULE_FOR_ROUTES.RIVER_POINTS,this.MODULE_FOR_ROUTES.MI_PONDS,this.MODULE_FOR_ROUTES.WC_STRUCTURES,
          this.MODULE_FOR_ROUTES.LI_SCHEMES,this.MODULE_FOR_ROUTES.SOIL_MOISTURE,this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY,
          this.MODULE_FOR_ROUTES.GROUND_WATER,this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY,
          this.MODULE_FOR_ROUTES.AGRICULTURE,this.MODULE_FOR_ROUTES.INDUSTRY,this.MODULE_FOR_ROUTES.DOMESTIC,this.MODULE_FOR_ROUTES.INLAND,this.MODULE_FOR_ROUTES.HYDRO_POWER
        ],
        [this.DASHBOARD_MAPPING.MIS.WATER_RESOURCE_MANAGEMENT]:[
          this.MODULE_FOR_ROUTES.PANCHYATH_LEVEL_WATER_BUDGET,this.MODULE_FOR_ROUTES.BASIN_LEVEL_WATER_BUDGET,this.MODULE_FOR_ROUTES.WATERAUDIT
        ],
      },
    },
    SUB_DASHBAORDS:{
      GIS:{
        [this.DASHBOARD_MAPPING.GIS.WATER_DEMAND]:[
          this.COMPONENT.AGRICULTURE,this.COMPONENT.INDUSTRY,this.COMPONENT.DOMESTIC,this.COMPONENT.INLAND,this.COMPONENT.HYDRO_POWER
        ],
        [this.DASHBOARD_MAPPING.GIS.SURFACE_WATER]:[
          this.COMPONENT.RESERVOIR,this.COMPONENT.RIVER_POINT,this.COMPONENT.MI_TANKS,this.COMPONENT.WCSTRUCTURE,
          this.COMPONENT.LISCHEMES,this.COMPONENT.SOILMOISTURE,this.COMPONENT.SWATERQUALITY
        ],
        [this.DASHBOARD_MAPPING.GIS.GROUND_WATER]:[
          this.COMPONENT.GROUNDWATER,this.COMPONENT.GWATERQUALITY
        ]
        
      } ,
      MIS:{
        [this.DASHBOARD_MAPPING.MIS.WATER_DEMAND]:[
          this.MODULE_FOR_ROUTES.AGRICULTURE,this.MODULE_FOR_ROUTES.INDUSTRY,this.MODULE_FOR_ROUTES.DOMESTIC,this.MODULE_FOR_ROUTES.INLAND,this.MODULE_FOR_ROUTES.HYDRO_POWER
        ],
        [this.DASHBOARD_MAPPING.MIS.SURFACE_WATER]:[
          this.MODULE_FOR_ROUTES.RESERVOIR,this.MODULE_FOR_ROUTES.RIVER_POINTS,this.MODULE_FOR_ROUTES.MI_PONDS,this.MODULE_FOR_ROUTES.WC_STRUCTURES,
          this.MODULE_FOR_ROUTES.LI_SCHEMES,this.MODULE_FOR_ROUTES.SOIL_MOISTURE
        ],
        [this.DASHBOARD_MAPPING.MIS.WATER_AVAILABILITY]:[
          this.MODULE_FOR_ROUTES.RESERVOIR,this.MODULE_FOR_ROUTES.RIVER_POINTS,this.MODULE_FOR_ROUTES.MI_PONDS,this.MODULE_FOR_ROUTES.WC_STRUCTURES,
          this.MODULE_FOR_ROUTES.LI_SCHEMES,this.MODULE_FOR_ROUTES.SOIL_MOISTURE,
          this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY,
          this.MODULE_FOR_ROUTES.GROUND_WATER,this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY,
        ],
        [this.DASHBOARD_MAPPING.MIS.WATER_QUALITY]:[
          this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY,this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY
        ],
        [this.DASHBOARD_MAPPING.MIS.WATER_BUDGET]:[
          this.MODULE_FOR_ROUTES.PANCHYATH_LEVEL_WATER_BUDGET,this.MODULE_FOR_ROUTES.BASIN_LEVEL_WATER_BUDGET,
        ]
        
      } 
    }
  }

  COMPONENT_USER_SPECIFIC_DASHBOARD_STATUS ={
    GIS:{
      [this.DASHBOARD_MAPPING.GIS.WATER_DEMAND]:false,
      [this.DASHBOARD_MAPPING.GIS.SURFACE_WATER]:false,
      [this.DASHBOARD_MAPPING.GIS.GROUND_WATER]:false,
      [this.DASHBOARD_MAPPING.GIS.WATER_RESOURCE_MANAGEMENT]:false,
      [this.DASHBOARD_MAPPING.GIS.WATER_RESOURCE_INFO]:false,
      [this.DASHBOARD_MAPPING.GIS.WEATHER_DATA]:false,
    },
    MIS:{
      [this.DASHBOARD_MAPPING.MIS.WATER_DEMAND]:false,
      [this.DASHBOARD_MAPPING.MIS.SURFACE_WATER]:false,
      [this.DASHBOARD_MAPPING.MIS.WATER_RESOURCE_MANAGEMENT]:false,
      [this.DASHBOARD_MAPPING.MIS.WATER_RESOURCE_INFO]:false,
      [this.DASHBOARD_MAPPING.MIS.WEATHER_DATA]:false,
      [this.DASHBOARD_MAPPING.MIS.WATER_BUDGET]:false,
      [this.DASHBOARD_MAPPING.MIS.WATER_QUALITY]:false,
      [this.DASHBOARD_MAPPING.MIS.WATER_AVAILABILITY]:false,
    }
  }

  COMPONENTS_INFO ={
    DEFAULT_ROUTE:null,
    [this.MODULE_FOR_ROUTES.RAINFALL]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.RAINFALL,
        GIS:this.COMPONENT.RAINFALL
      },
      PRE_ROUTE:{
        MIS:'/mis/wd/home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.TEMPERATURE]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.TEMPERATURE,
        GIS:this.COMPONENT.TEMPERATURE
      },
      PRE_ROUTE:{
        MIS:'/mis/wd/home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.RIVER_POINTS]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.RIVER_POINTS,
        GIS:this.COMPONENT.RIVER_POINT
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.RESERVOIR]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.RESERVOIR,
        GIS:this.COMPONENT.RESERVOIR
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.GROUND_WATER]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.GROUND_WATER,
        GIS:this.COMPONENT.GROUNDWATER
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.WC_STRUCTURES]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.WC_STRUCTURES,
        GIS:this.COMPONENT.WCSTRUCTURE
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.SOIL_MOISTURE]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.SOIL_MOISTURE,
        GIS:this.COMPONENT.SOILMOISTURE
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.MI_PONDS]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.MI_PONDS,
        GIS:this.COMPONENT.MI_TANKS
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.LI_SCHEMES]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.LI_SCHEMES,
        GIS:this.COMPONENT.LISCHEMES
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },

    [this.MODULE_FOR_ROUTES.AGRICULTURE]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.AGRICULTURE,
        GIS:this.COMPONENT.AGRICULTURE
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/water-demand-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.DOMESTIC]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.DOMESTIC,
        GIS:this.COMPONENT.DOMESTIC
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/water-demand-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.INLAND]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.INLAND,
        GIS:this.COMPONENT.INLAND
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/water-demand-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.INDUSTRY]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.INDUSTRY,
        GIS:this.COMPONENT.INDUSTRY
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/water-demand-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.HYDRO_POWER]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.HYDRO_POWER,
        GIS:this.COMPONENT.HYDRO_POWER
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/water-demand-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.BASIN_LEVEL_WATER_BUDGET]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.BASIN_LEVEL_WATER_BUDGET,
        GIS:this.COMPONENT.BASIN_LEVEL_WATER_BUDGET
      },
      PRE_ROUTE:{
        MIS:'/mis/wrm/home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.PANCHYATH_LEVEL_WATER_BUDGET]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.PANCHYATH_LEVEL_WATER_BUDGET,
        GIS:this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET
      },
      PRE_ROUTE:{
        MIS:'/mis/wrm/home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.WATERAUDIT]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.WATERAUDIT,
        GIS:this.COMPONENT.WATERAUDIT
      },
      PRE_ROUTE:{
        MIS:'/mis/wrm/home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.WINDSPEED]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.WINDSPEED,
        GIS:this.COMPONENT.WINDSPEED
      },
      PRE_ROUTE:{
        MIS:'/mis/wd/home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.HUMIDITY]:{
      SELECTED:false,
      SOURCE_LIST:[],
        ACCESS:{
          MIS:false,
          GIS:false,
        },
        ROUTES:{
          MIS:this.MODULE_FOR_ROUTES.HUMIDITY,
          GIS:this.COMPONENT.HUMIDITY
        },
        PRE_ROUTE:{
          MIS:'/mis/wd/home/',
          GIS:'/gis/'
        }
    },
    [this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY]:{
      SELECTED:false,
      SOURCE_LIST:[],
      ACCESS:{
        MIS:false,
        GIS:false,
      },
      ROUTES:{
        MIS:this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY,
        GIS:this.COMPONENT.SWATERQUALITY
      },
      PRE_ROUTE:{
        MIS:'/mis/wri/wa-home/',
        GIS:'/gis/'
      }
    },
    [this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY]:{
      SELECTED:false,
      SOURCE_LIST:[],
        ACCESS:{
          MIS:false,
          GIS:false,
        },
        ROUTES:{
          MIS:this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY,
          GIS:this.COMPONENT.GWATERQUALITY
        },
        PRE_ROUTE:{
          MIS:'/mis/wri/wa-home/',
          GIS:'/gis/'
        }
    },
    ALL_COMPONENT_SOURCES:[]
  };

  

  DASHBOARDS_COMPONENT_ROUTE_MAPPING ={
    GIS:{ 
      [this.COMPONENT.RAINFALL]: this.MODULE_FOR_ROUTES.RAINFALL,
      [this.COMPONENT.TEMPERATURE]: this.MODULE_FOR_ROUTES.TEMPERATURE,
      [this.COMPONENT.RIVER_POINT]:  this.MODULE_FOR_ROUTES.RIVER_POINTS,
      [this.COMPONENT.RESERVOIR]:  this.MODULE_FOR_ROUTES.RESERVOIR,
      [this.COMPONENT.GROUNDWATER]:  this.MODULE_FOR_ROUTES.GROUND_WATER,
      [this.COMPONENT.WCSTRUCTURE] : this.MODULE_FOR_ROUTES.WC_STRUCTURES,
      [this.COMPONENT.SOILMOISTURE] :  this.MODULE_FOR_ROUTES.SOIL_MOISTURE,
      [this.COMPONENT.MI_TANKS] : this.MODULE_FOR_ROUTES.MI_PONDS,
      [this.COMPONENT.LISCHEMES] :  this.MODULE_FOR_ROUTES.LI_SCHEMES,
      [this.COMPONENT.AGRICULTURE] :  this.MODULE_FOR_ROUTES.AGRICULTURE,
      [this.COMPONENT.DOMESTIC] :  this.MODULE_FOR_ROUTES.DOMESTIC,
      [this.COMPONENT.INLAND] :  this.MODULE_FOR_ROUTES.INLAND,
      [this.COMPONENT.INDUSTRY] :  this.MODULE_FOR_ROUTES.INDUSTRY,
      [this.COMPONENT.HYDRO_POWER] :  this.MODULE_FOR_ROUTES.HYDRO_POWER,
      [this.COMPONENT.BASIN_LEVEL_WATER_BUDGET]: this.MODULE_FOR_ROUTES.BASIN_LEVEL_WATER_BUDGET,
      [this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET]: this.MODULE_FOR_ROUTES.PANCHYATH_LEVEL_WATER_BUDGET,
      [this.COMPONENT.WATERAUDIT]: this.MODULE_FOR_ROUTES.WATERAUDIT,
      [this.COMPONENT.HUMIDITY]: this.MODULE_FOR_ROUTES.HUMIDITY,
      [this.COMPONENT.WINDSPEED]: this.MODULE_FOR_ROUTES.WINDSPEED,
      [this.COMPONENT.GWATERQUALITY]: this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY,
      [this.COMPONENT.SWATERQUALITY]: this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY,
    },
    MIS:{
      [this.MODULE_FOR_ROUTES.RAINFALL]: this.COMPONENT.RAINFALL ,
      [this.MODULE_FOR_ROUTES.TEMPERATURE]: this.COMPONENT.TEMPERATURE ,
      [this.MODULE_FOR_ROUTES.RIVER_POINTS]: this.COMPONENT.RIVER_POINT ,
     [ this.MODULE_FOR_ROUTES.RESERVOIR]: this.COMPONENT.RESERVOIR ,
      [this.MODULE_FOR_ROUTES.GROUND_WATER]: this.COMPONENT.GROUNDWATER ,
     [this.MODULE_FOR_ROUTES.WC_STRUCTURES]: this.COMPONENT.WCSTRUCTURE , 
     [ this.MODULE_FOR_ROUTES.SOIL_MOISTURE]: this.COMPONENT.SOILMOISTURE , 
    [ this.MODULE_FOR_ROUTES.MI_PONDS]: this.COMPONENT.MI_TANKS , 
    [  this.MODULE_FOR_ROUTES.LI_SCHEMES]: this.COMPONENT.LISCHEMES , 
     [ this.MODULE_FOR_ROUTES.AGRICULTURE]: this.COMPONENT.AGRICULTURE , 
      [this.MODULE_FOR_ROUTES.DOMESTIC]: this.COMPONENT.DOMESTIC , 
     [ this.MODULE_FOR_ROUTES.INLAND]: this.COMPONENT.INLAND , 
     [ this.MODULE_FOR_ROUTES.INDUSTRY]: this.COMPONENT.INDUSTRY , 
     [ this.MODULE_FOR_ROUTES.HYDRO_POWER]: this.COMPONENT.HYDRO_POWER , 
     [this.MODULE_FOR_ROUTES.BASIN_LEVEL_WATER_BUDGET]: this.COMPONENT.BASIN_LEVEL_WATER_BUDGET ,
     [this.MODULE_FOR_ROUTES.PANCHYATH_LEVEL_WATER_BUDGET]: this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET ,
     [this.MODULE_FOR_ROUTES.WATERAUDIT]: this.COMPONENT.WATERAUDIT ,
     [this.MODULE_FOR_ROUTES.HUMIDITY]: this.COMPONENT.HUMIDITY ,
     [this.MODULE_FOR_ROUTES.WINDSPEED]: this.COMPONENT.WINDSPEED ,
     [this.MODULE_FOR_ROUTES.GROUND_WATER_QUALITY]: this.COMPONENT.GWATERQUALITY ,
     [this.MODULE_FOR_ROUTES.SURFACE_WATER_QUALITY]: this.COMPONENT.SWATERQUALITY ,
    }

  }


  LOCATION_TYPES = ['country', 'state', 'district', 'mandal', 'block', 'village', 'basin', 'subbasin'];

  //Bottom to Up Spatially
  SORTED_DISTRICT_LIST = [{'locationName':'Thiruvananthapuram','locationUUID':'787d19b3-b511-4161-b08c-aa23082318be'}, {'locationName':'Kollam','locationUUID':'19bf47d8-4ba3-42ff-9dea-f1581c9d7ab7'}, {'locationName':'Pathanamthitta','locationUUID':'273d765a-fc5f-417a-bee8-a4257f271ca6'}, {'locationName':'Alappuzha','locationUUID':'46d05795-d880-42fb-824a-4c9ec61034a2'}, {'locationName':'Kottayam','locationUUID':'93bf1cb2-38d6-404c-8c21-a7beeec5c644'}, {'locationName':'Idukki','locationUUID':'97ace365-2913-4b3d-8682-e796acfdc689'}, {'locationName':'Ernakulam','locationUUID':'3915d00f-2e7d-47e0-9113-f3824b61daae'}, {'locationName':'Thrissur','locationUUID':'f81337dc-d04d-4618-9714-3609fe891919'}, {'locationName':'Palakkad','locationUUID':'1270f554-20cc-43ee-803e-1532f00e047c'}, {'locationName':'Malappuram','locationUUID':'10c9393d-79e0-4670-816a-8a05b395b13e'}, {'locationName':'Kozhikode','locationUUID':'8189474d-71b9-488c-b45a-34bbcda95308'}, {'locationName':'Wayanad','locationUUID':'9638dab0-6fbe-4b7c-9f13-8908cc768221'}, 
  // {'locationName' :'Mahe', 'locationUUID':'0d2a8063-c944-4b01-a5e5-9401cc32cddf' } 
  {'locationName':'Kannur','locationUUID':'02122ce9-dea4-4840-8745-5d3743e087a7'}, {'locationName':'Kasaragod','locationUUID':'4a0e14b2-de3e-4a7b-b1a4-a39e66ddbe48'}]

  AGGR_TYPES = {
    SUM: "SUM",
    MIN: "MIN",
    MAX: "MAX",
    AVG: "AVG",
    LATEST: "LATEST"
  };

  DATEPICKER_DATE_FORMAT = {
    YEAR: 'yyyy',
    MONTH: 'yyyyMM',
    DAY: 'yyyyMMdd',
    SEASON: 'season'
  };

 DATE_FORMAT = {
    [this.DATEPICKER_DATE_FORMAT.YEAR]:'Yearly',
    [this.DATEPICKER_DATE_FORMAT.MONTH]:'Monthly',
    [this.DATEPICKER_DATE_FORMAT.DAY]:'Daily',
    [this.DATEPICKER_DATE_FORMAT.SEASON]:"Season"
  };




  VIEWS = {
    ADMIN: "ADMIN",
    BASIN: "BASIN",
    REGION: "REGION",
    RIVER: "RIVER"
  }
  LOCATION_CONSTANT = {
    COUNTRY: "COUNTRY",
    STATE: "STATE",
    DISTRICT: "DISTRICT",
    MANDAL: "MANDAL",
    PANCHAYATH: "PANCHAYATH",
    BLOCK: "BLOCK",
    BASIN: "BASIN",
    SUBBASIN: "SUBBASIN",
    STATION: "STATION",
    RESERVOIR: "RESERVOIR",
    MI_TANKS: 'Minor Irrigation Tanks',
    RIVERPOINT: "River Authority",
    GROUNDWATER: "Ground Water",
    REGION: "REGION",
    RIVER: "RIVER",
    WC_STRUCTURE:'Wc Structure',
    RAINFALL:"RAINFALL",
    WATERSHED:"WATERSHED",
    LISCHEME:"LISCHEME",
    INDUSTRY:"INDUSTRY",
    POWER_STATION:"POWER_STATION",
    MI_TANK: 'MITANK',

  };
  WC_STRUCTURE:'Wc Structure';
  // Used for displaying info on Infobox and Map Title
  SELECTED_COMPONENT_CORRESPONDING_DATA = {
    SOURCE:'',
    MIN_DATE:'',
    MAX_DATE:'',
    // For Seasonal
    sDate:'',
    eDate:''
  }

  LOCATION_LEVEL = {
    [this.VIEWS.ADMIN]: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT,
      this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,this.LOCATION_CONSTANT.STATION
    ],
    [this.VIEWS.BASIN]: []
  }

  ADMIN_PARENT_CHILD_MAP = {
    // [this.LOCATION_CONSTANT.COUNTRY]:this.LOCATION_CONSTANT.STATE,
    [this.LOCATION_CONSTANT.STATE]: this.LOCATION_CONSTANT.DISTRICT,
    [this.LOCATION_CONSTANT.DISTRICT]: this.LOCATION_CONSTANT.PANCHAYATH
  };

  BASIN_PARENT_CHILD_MAP = {
    // [this.LOCATION_CONSTANT.COUNTRY]:this.LOCATION_CONSTANT.STATE,
    [this.LOCATION_CONSTANT.STATE]: this.LOCATION_CONSTANT.BASIN,
    [this.LOCATION_CONSTANT.BASIN]: this.LOCATION_CONSTANT.STATION
  };

  ADMIN_PARENT_CHILD_STATION_MAP = {
    // [this.LOCATION_CONSTANT.COUNTRY]:this.LOCATION_CONSTANT.STATE,
    [this.LOCATION_CONSTANT.STATE]:this.LOCATION_CONSTANT.DISTRICT,
    [this.LOCATION_CONSTANT.DISTRICT]:this.LOCATION_CONSTANT.MANDAL,
    [this.LOCATION_CONSTANT.MANDAL]:this.LOCATION_CONSTANT.PANCHAYATH,
    [this.LOCATION_CONSTANT.PANCHAYATH]:this.LOCATION_CONSTANT.STATION,
    [this.LOCATION_CONSTANT.STATION]:this.LOCATION_CONSTANT.STATION,
    [this.LOCATION_CONSTANT.RAINFALL]: this.LOCATION_CONSTANT.RAINFALL
  };

  BASIN_PARENT_CHILD_STATION_MAP = {
    // [this.LOCATION_CONSTANT.COUNTRY]:this.LOCATION_CONSTANT.STATE,
    [this.LOCATION_CONSTANT.STATE]:this.LOCATION_CONSTANT.BASIN,
    [this.LOCATION_CONSTANT.BASIN]:this.LOCATION_CONSTANT.STATION,
    [this.LOCATION_CONSTANT.STATION]:this.LOCATION_CONSTANT.STATION,
    [this.LOCATION_CONSTANT.RAINFALL]: this.LOCATION_CONSTANT.RAINFALL
  };

  CHILD_TO_PARENT_LOCATION_MAP={
    [this.LOCATION_CONSTANT.DISTRICT] :this.LOCATION_CONSTANT.STATE,
    [this.LOCATION_CONSTANT.MANDAL]:this.LOCATION_CONSTANT.DISTRICT,
    [this.LOCATION_CONSTANT.PANCHAYATH]:this.LOCATION_CONSTANT.MANDAL,
    [this.LOCATION_CONSTANT.STATION]:this.LOCATION_CONSTANT.PANCHAYATH,
    [this.LOCATION_CONSTANT.RAINFALL]:this.LOCATION_CONSTANT.STATE,
    [this.BASIN] : this.LOCATION_CONSTANT.STATE
  }

  // BASIN_PARENT_CHILD_MAP = {
  //   [this.LOCATION_CONSTANT.COUNTRY]:this.LOCATION_CONSTANT.BASIN,
  //   [this.LOCATION_CONSTANT.BASIN]:this.LOCATION_CONSTANT.SUBBASIN
  // };
  // BASIN_PARENT_CHILD_STATION_MAP = {
  //   [this.LOCATION_CONSTANT.COUNTRY]:this.LOCATION_CONSTANT.BASIN,
  //   [this.LOCATION_CONSTANT.BASIN]:this.LOCATION_CONSTANT.SUBBASIN,
  //   [this.LOCATION_CONSTANT.SUBBASIN]:this.LOCATION_CONSTANT.STATION,
  // };


  COMPONENT_SOURCE_HIERARCHY_MAP = {
    [this.COMPONENT.WINDSPEED]: {
      [this.SOURCES.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH ,this.LOCATION_CONSTANT.RAINFALL],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,this.LOCATION_CONSTANT.RAINFALL]
      },
      [this.SOURCES.IMD]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH ,this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,this.LOCATION_CONSTANT.STATION]
      },
    },
    [this.COMPONENT.WCSTRUCTURE]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH , this.LOCATION_CONSTANT.WC_STRUCTURE],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
    },
    [this.COMPONENT.WATERAUDIT]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL ],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN]
      },
    },
    [this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH ],
      },
    },
    [this.COMPONENT.BASIN_LEVEL_WATER_BUDGET]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN ]
      },
    },
    [this.COMPONENT.AGRICULTURE]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH ],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
    },
    [this.COMPONENT.DOMESTIC]: {
      [this.SOURCES.KWA]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH ],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ]
      },
    },
    [this.COMPONENT.HYDRO_POWER]: {
      [this.SOURCES.KSEB]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,this.LOCATION_CONSTANT.POWER_STATION ],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,this.LOCATION_CONSTANT.POWER_STATION]
      },
    },
    [this.COMPONENT.INDUSTRY]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH , this.LOCATION_CONSTANT.WC_STRUCTURE],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
    },
    [this.COMPONENT.INLAND]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        RIVER: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.RIVER ]
      }
    },
    [this.COMPONENT.RAINFALL]: {
      [this.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN,this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN,this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.CWC]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN,this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.IMD]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN,this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.IMD_GRID]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN,this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.GPM_GRID]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.COUNTRY, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.COUNTRY, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.SOURCES.NRSC]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.COUNTRY, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.SOURCES.RTDAS]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.COUNTRY, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.SOURCES.SKYMET]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.COUNTRY, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      }


    },
    [this.COMPONENT.RESERVOIR]: {
      [this.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.SOURCES.KWA]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.SOURCES.KSEB]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.IDRB]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
      [this.CWC]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      }
    },
    [this.COMPONENT.SWATERQUALITY]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION],
      },
      [this.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION],
      },
      [this.CWC]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION],
      }
    },
    [this.COMPONENT.RIVERPOINT]: {
      [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION],
        RIVER: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.RIVER, this.LOCATION_CONSTANT.STATION]
      },
      [this.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION],
        RIVER: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.RIVER, this.LOCATION_CONSTANT.STATION]
      },
      [this.CWC]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION],
        RIVER: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.RIVER, this.LOCATION_CONSTANT.STATION]
      }
    },
    [this.COMPONENT.TEMPERATURE]: {

      [this.IMD_1]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.IMD_GRID]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
      [this.IMD]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,"RAINFALL"],
        BASIN: [ this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,"RAINFALL"]
      },
      [this.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,"RAINFALL"],
        BASIN: [ this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,"RAINFALL"]
      },
      [this.SOURCES.IMD_05]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,],
        BASIN: [ this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      }
    },
    [this.COMPONENT.GROUNDWATER]: {
      [this.SOURCES.CGWB]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH , this.LOCATION_CONSTANT.STATION],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.STATION]
      },
    },
    
    [this.COMPONENT.SOILMOISTURE]: {
      [this.SOURCES.NRSC]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH ],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN , this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
      },
    },
    [this.COMPONENT.MI_TANKS]: {
      [this.SOURCES.LSGD]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, "MITANK"],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.WATERSHED, "MITANK"]
      },
      [this.SOURCES.IDRB_VALIDATED]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH, "MITANK"],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.WATERSHED, "MITANK"]
      }
    },
    [this.COMPONENT.LISCHEMES]: {
      [this.SOURCES.IDRB]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.LISCHEME],
        BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.WATERSHED, this.LOCATION_CONSTANT.LISCHEME]
      }
    },
    [this.COMPONENT.HUMIDITY]: {
      [this.SOURCES.IMD]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,"RAINFALL"],
        BASIN: [ this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,"RAINFALL"]
      },
      [this.SOURCES.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,"RAINFALL"],
        BASIN: [ this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,"RAINFALL"]
      }
    },
    [this.COMPONENT.GWATERQUALITY]: {
      [this.SOURCES.KERALA_SW]: {
        ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH,this.LOCATION_CONSTANT.STATION],
        BASIN: [ this.LOCATION_CONSTANT.STATE,this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED ,this.LOCATION_CONSTANT.STATION]
      }
    }
    // [this.COMPONENT.AGRICULTURE]: {
    //   [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
    //     ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
    //     BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.WATERSHED] 
    //   }
    // },
    // [this.COMPONENT.DOMESTIC]: {
    //   [this.SOURCES.STATE_AND_CENTRAL_STATION]: {
    //     ADMIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.PANCHAYATH],
    //     BASIN: [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.WATERSHED]
    //   }
    // }
  };

  RAINFALL_DONUT_PARAMS = {
    'rfDonut': {
      "cType": "DISTRICT",
      "lType": "STATE",
      "lUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "aggr": "SUM",
      "component": "rainfall",
      "eDate": "2018",
      "format": "yyyy",
      "sDate": "2018",
      "view": "admin",
      "src": "Kerala SW",
      "chartType": "pie",
      "summary": false,
      "pEUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6"
    }
  };

  RAINFALL_TOP10_PARAMS = {
    'rfTop': {
      "cType": "DISTRICT",
      "lType": "STATE",
      "lUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "aggr": "SUM",
      "component": "rainfall",
      "eDate": "2018",
      "format": "yyyy",
      "sDate": "20180501",
      "view": "admin",
      "src": "Kerala SW",
      "chartType": "top",
      "summary": false,
      "pEUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6"
    }
  };

  RAINFALL_BOTTOM10_PARAMS = {
    'rfBottom': {
      "cType": "DISTRICT",
      "lType": "STATE",
      "lUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "aggr": "SUM",
      "component": "rainfall",
      "eDate": "2018",
      "format": "yyyy",
      "sDate": "2018",
      "view": "admin",
      "src": "Kerala SW",
      "chartType": "bottom",
      "summary": false,
      "pEUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6"
    }
  };

  RAINFALL_KPI_PARAMS = {
    'rfKpi': {
      "cType": "DISTRICT",
      "lType": "STATE",
      "pUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "aggr": "SUM",
      "component": "rainfall",
      "eDate": "2018",
      "format": "yyyy",
      "sDate": "2018",
      "pDate": "2018",
      "src": "Kerala SW",
      "summary": true,
      "sUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6"
    }
  }




  // Rainfall Forecast Params


  RAINFALL_FORECAST_TOP10_PARAMS = {
    'fcTop': {
      "cType": "DISTRICT",
      "component" : "RAINFALL",
      "pType": "STATE",
      "forecastLength" : "24",
      "pUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "sUUID" :  "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "primarySource" : "IMD_SHORT_RANGE",
      "secondarySource" : "IMD_LONG_RANGE",
      "chartType": "top",
      "view" : "Admin"
    }
  };



  RAINFALL_FORECAST_BOTTOM10_PARAMS = {
    'fcBottom': {
      "cType": "DISTRICT",
      "component" : "RAINFALL",
      "pType": "STATE",
      "forecastLength" : "24",
      "pUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "sUUID" :  "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "primarySource" : "IMD_SHORT_RANGE",
      "secondarySource" : "IMD_LONG_RANGE",
      "chartType": "bottom",
      "view" : "Admin"
    }
  };

  RESERVOIR_FUNNEL_PARAMS = {
    'reservoirFunnel': {
      "component": "reservoir",
      "view": "admin",
      "chartType": "pieChart",
      "pEUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "format": "yyyyMMdd",
      "lType": "STATE",
      "lUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "src": "STATE_AND_CENTRAL_STATION",
      "sDate": "20190604"
    }
  };


  // actual to short name districts name mapping
  ACTUAL_TOSHORTNAME  = {
     "TVM" : "Thiruvananthapuram",
     "KLM" : "Kollam" ,
     "ALP" : "Alappuzha",
     "PTA" : "Pathanamthitta",
     "KTM" : "Kottayam",
     "IDK" : "Idukki",
     "EKM" : "Ernakulam",
     "TSR" : "Thrissur",
     "MPM" : "Malappuram",
     "PKD" : "Palakkad",
     "KKD" : "Kozhikode",
     "WYD" : "Wayanad",
     "KNR" : "Kannur",
     "KGD" : "Kasaragod"
  }

  DISTRICT_ORDER_NAME = ["TVM","KLM","PTA","ALP","KTM","IDK","EKM","TSR","PKD","MPM","KKD","WYD","KNR","KGD"]

  DISTRICT_ORDER_ACTUAL_NAME =
  [
  "Thiruvananthapuram",
  "Kollam",
  "Pathanamthitta",
  "Alappuzha",
  "Kottayam",
  "Idukki",
  "Ernakulam",
  "Thrissur",
  "Palakkad",
  "Malappuram",
  "Kozhikode",
  "Wayanad",
  // "Mahe",
  "Kannur",
  "Kasaragod"
  ]

  boldColumns = {
    RAINFALL: ['actual', 'normal', 'deviation']
  }

  chartOptions = {
    ['riverpoint']: {
      component: 'riverpoint',
      exporting: {
        enabled: true
      },
      credits: {
        enabled: false
      },
      chart: {
        height : '380px',
      },

    //   tooltip: {
    //     backgroundColor: '#FCFFC5',
    //     borderColor: 'black',
    //     borderRadius: 10,
    //     borderWidth: 3
    // },
      // plotOptions: {
      //   column: {
      //     // colorByPoint: true
      //   }
      // },
      // colors: [],
      title: {
        text: '',
        style: {
          fontSize: "1.2em"
        }
      },
      xAxis: {
        categories: []
      },
      yAxis: {
        title: {
          text: ""
        }
      },
      tooltip: {
        shape: 'square',
        headerShape: 'callout',
        borderWidth: 0,
        shadow: false,
        enabled: true,
        split: true,
        shared: true,
        useHTML: true,
        valueDecimals: 1,
        // valueSuffix: ' mm',
        positioner: function (width, height, point) {
          let chart = this.chart;
          let position;
          if (point.isHeader) {
            position = {
              x: Math.max(
                // Left side limit
                chart.plotLeft,
                Math.min(
                  point.plotX + chart.plotLeft - width / 2,
                  // Right side limit
                  chart.chartWidth - width - chart.marginRight
                )
              ),
              y: point.plotY
            };
          } else {
            position = {
              x: point.series.chart.plotLeft,
              y: point.series.yAxis.top - chart.plotTop
            };
          }
          return position;
        }
      },
      series: []
    },

    ['fcTop']: {
      component: 'fcTop',
      exporting: {
        enabled: true
      },
      credits: {
        enabled: false
      },
      chart: {
        height : '380px',
      },
      // plotOptions: {
      //   column: {
      //     colorByPoint: true
      //   }
      // },
      title: {
        text: '',
        style: {
          fontSize: "1.2em"
        }
      },
      xAxis: {
        categories: []
      },
      yAxis: {
        title: {
          text: ""
        }
      },
      tooltip: {
        valueSuffix: " mm ",
        valueDecimals: 1
      },
      series: [{
        name: 'Cumulative Rainfall (mm)',
        showInLegend: true,
        type: 'column',
        color: '#66bb6a',
        data: [],
        dataLabels: [{
          enabled: false
        }]
      },]
    },

    ['fcBottom']: {
      component: 'fcBottom',
      exporting: {
        enabled: true
      },
      credits: {
        enabled: false
      },
      chart: {
        height : '380px',
      },
      // plotOptions: {
      //   column: {
      //     colorByPoint: true
      //   }
      // },
      // colors: [],
      title: {
        text: '',
        style: {
          fontSize: "1.2em"
        }
      },
      xAxis: {
        categories: []
      },
      yAxis: {
        title: {
          text: ""
        }
      },
      tooltip: {
        valueSuffix: " mm ",
        valueDecimals: 1
      },
      series: [{
        name: 'Cumulative Rainfall (mm)',
        showInLegend: true,
        type: 'column',
        color: '#66bb6a',
        data: [],
        dataLabels: [{
          enabled: false
        }]
      },]
    },

    ['reservoirDonut']: {
      component: 'reservoirDonut',
      exporting: {
        enabled: true
      },
      chart: {
        plotBorderWidth: null,
        plotShadow: false,
      },
      plotOptions: {
        pie: {
          shadow: false,
          center: ['50%', '50%'],
          size: '100%',
          innerSize: '50%',
          colorByPoint: true,
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: false
          },
          showInLegend: true
        },
      },
      credits: {
        enabled: false
      },
      title: {
        verticalAlign: 'middle',
        text: '',
        style: {
          fontSize: "1em"
        },
        y : -8
      },
      xAxis: {
        categories: []
      },
      yAxis: {
        title: {
          text: ""
        }
      },
      tooltip: {
        valueDecimals: 1,
        pointFormat: '<b>{point.percentage:.1f}%</b>'
      },
      colors: [
        '#2D99FF',
        "#2CD9C5",
        "#FF6C40",
        "#717B85"
      ],
      series: [{
        type: 'pie',
        data: [],
      }, ]
    },

    ['riverPointStationLevel']: {
      component: 'riverPointStationLevel',
      exporting: {
        enabled: true
      },
      credits: {
        enabled: false
      },
      chart: {
        // type: 'column'
      },
      colors: [ ],
      title: {
        text: '',
        style: {
          fontSize: "1.2em"
        },
      },
      xAxis: {
        categories: []
      },
      yAxis: [{ // Primary yAxis
        labels: {
            format: '{value}',
        },
        title: {
            text: 'Discharge (Cumecs)'
        }
    }, { // Secondary yAxis
        title: {
            text: 'Level (m)',
        },
        labels: {
            format: '{value}',
        },
        opposite: true
    }],
    legend: {
    },
    tooltip: {
        shared: true,
    },
    plotOptions: {
        // column: {
        //     stacking: 'normal',
        //     dataLabels: {
        //         enabled: false
        //     },
        // },
        series: {
          pointWidth: 40
      }

    },
    series: []
  },

  ['riverPointRiverChart']: {
    component: 'riverPointRiverChart',
    exporting: {
      enabled: true
    },
    credits: {
      enabled: false
    },
    chart: {
    },
    title: {
      text: '',
      style: {
        fontSize: "1.2em"
      },
    },
    xAxis: {
      categories: []
    },
    yAxis: {
      labels: {
          format: '{value}',
      },
      title: {
          text: 'Discharge (Cumecs)'
      }
    },
    legend: {
  },
  tooltip: {
  },
  plotOptions: {
  },
  series: []
},
  }

  TEMPERATURE_FORECAST_TOP10_PARAMS = {
    'fcTop': {
      "cType": "DISTRICT",
      "component" : "RAINFALL",
      "pType": "STATE",
      "forecastLength" : "24",
      "pUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "sUUID" :  "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "primarySource" : "IMD_SHORT_RANGE",
      "secondarySource" : "IMD_LONG_RANGE",
      "chartType": "top",
      "view" : "Admin"
    }
  };



  TEMPERATURE_FORECAST_BOTTOM10_PARAMS = {
    'fcBottom': {
      "cType": "DISTRICT",
      "component" : "RAINFALL",
      "pType": "STATE",
      "forecastLength" : "24",
      "pUUID": "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "sUUID" :  "b3a3109c-1d8d-11ea-9f34-04ea56f9ded6",
      "primarySource" : "IMD_SHORT_RANGE",
      "secondarySource" : "IMD_LONG_RANGE",
      "chartType": "bottom",
      "view" : "Admin"
    }
  };


  DonutChartLegend = ['L.Excess', 'Excess', 'Normal', 'Deficient', 'L.Deficient'];

  ReservoirFunnelChartLegend = {
    "Flood Cushion" : "Flood Cushion",
    "Current Live Storage" : "Live Storage",
    "Dead Storage" : "Dead Storage",
  };

  locationTypeListForView ={
    ADMIN_HIERARCHY:[this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.RESERVOIR],
    BASIN_HIERARCHY:[this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.RESERVOIR]
  }

  // COMPONENT_REPORT_TYPES = {
  //   [this.COMPONENT.RESERVOIR] : [this.RESERVOIR_REPORT_STORAGE_LEVEL, this.RESERVOIR_REPORT_STORAGE_TREND, this.RESERVOIR_STORAGE_COMPARISON,this.RESERVOIR_REPORT_LEVEL_TREND],
  //   [this.COMPONENT.RAINFALL]: [this.RF_REPORT_STATE_WISE, this.RF_REPORT_DISTRICT_WISE, this.RF_REPORT_STATION_WISE,this.RF_REPORT_BASIN_WISE],
  //   [this.COMPONENT.RIVERPOINT]: [this.RIVERPOINT_LEVEL_REPORT],
  //   [this.COMPONENT.GROUNDWATER] : [this.GROUNDWATER_REPORT_STATE_WISE, this.GROUNDWATER_REPORT_DISTRICT_WISE,this.GROUNDWATER_REPORT_STATE_STATION_WISE, this.GROUNDWATER_SEASONAL_FLUCTUATION,
  //   this.GROUNDWATER_ANNUAL_FLUCTUATION, this.GROUNDWATER_DECADE_FLUCTUATION, this.GROUNDWATER_DEPTH_TO_WATER_LEVEL, this.GROUNDWATER_TRENDS],
  // }

  
  COMPONENT_REPORT_TYPES = {
    [this.COMPONENT.RAINFALL]: [this.MOBILE_APP_REPORT, this.RF_REPORT_STATION_WISE, this.RF_REPORT_DISTRICT_WISE, this.RF_REPORT_MANDAL_WISE,this.RF_REPORT_PANCHAYATH_WISE,this.RF_REPORT_BASIN_WISE],
    [this.COMPONENT.RIVERPOINT]: [this.MOBILE_APP_REPORT, this.RIVERPOINT_LEVEL_REPORT],
    [this.COMPONENT.RESERVOIR]: [this.MOBILE_APP_REPORT,this.RESERVOIR_STATION_WISE],
    [this.COMPONENT.TEMPERATURE]:[this.MOBILE_APP_REPORT],
    [this.COMPONENT.HUMIDITY]:[this.MOBILE_APP_REPORT],
    [this.COMPONENT.WINDSPEED]:[this.MOBILE_APP_REPORT],
    [this.COMPONENT.MI_TANKS]:[this.MOBILE_APP_REPORT],
    // [this.COMPONENT.WC_STRUCTURES]:[this.MOBILE_APP_REPORT,this.WC_STRUCTURES_STATION_WISE],
    // [this.COMPONENT.LI_SCHEMES]:[this.MOBILE_APP_REPORT,this.LI_SCHEMES_STATION_WISE],
    // [this.COMPONENT.SWATERQUALITY]:[this.MOBILE_APP_REPORT,this.SWATERQUALITY_STATION_WISE],
    // [this.COMPONENT.GWATERQUALITY]:[this.MOBILE_APP_REPORT,this.GWATERQUALITY_STATION_WISE]   
      //  [this.COMPONENT.SOILMOISTURE]:[this.MOBILE_APP_REPORT,this.SOILMOISTURE_STATION_WISE],
      //  [this.COMPONENT.INDUSTRY]:[this.MOBILE_APP_REPORT,this.INDUSTRIAL_DEMAND_STATION_WISE],
      //  [this.COMPONENT.DOMESTIC]: [this.MOBILE_APP_REPORT,this.DOMESTIC_DEMAND_STATION_WISE],
      // [this.COMPONENT.CROP_DEMAND]:[this.MOBILE_APP_REPORT,this.CROP_DEMAND_STATION_WISE],
      // [this.COMPONENT.HYDRO_POWER]:[this.MOBILE_APP_REPORT,this.HYDRO_POWER_STATION_WISE],
      [this.COMPONENT.BASIN_LEVEL_WATER_BUDGET]: [this.BASIN_LEVEL_WATER_BUDGET_STATION_WISE],
      [this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET]: [this.PANCHAYATH_LEVEL_WATER_BUDGET_STATION_WISE],
      // [this.COMPONENT.WATER_AUDIT]:[this.MOBILE_APP_REPORT,this.WATER_AUDIT_STATION_WISE]

  }


  REPORT_COMPONENT_LIST = [{
    "key": "rainfall",
    "value": "Rainfall"
  },
   {
    "key": "River Authority",
    "value": "River Point"
  }, 
 {
    "key": "reservoir",
    "value": "Reservoir"
  }, 
  { 
    "key":"temperature",
    "value":"Temperature"
  },
  { 
    "key":"humidity",
    "value":"Humidity"
  },
  {
    "key":"wind-speed",
    "value":"WindSpeed"
  },
  {
    "key":"mitank",
    "value":"MI Ponds"
  },
// {
//     "key": "groundwater",
//     "value": "Ground Water"
//   }, {
//     "key": "waterquality",
//     "value": "Water Quality"
//   }
    // {
    // "key":"WC_STRUCTURE",
    //  "value":"WC-Structures"
    // },
    // {
    //   "key":"LISCHEME",
    //   "value":"Li-Schemes"
    // },
    // {
    //   "key":"surfacewater_wq",
    //   "value":"Surface Water Quality"
    // },
    // {
    //   "key":"groundwater_wq",
    //   "value":"Ground Water Quality"
    // }
      //  {
      //    "key":"soilmoisture",
      //    "value":"Soil Moisture"
      //  },
      //  {
      //    "key":"domestic",
      //    "value":"Domestic Demand"
      //  },
      //  {
      //    "key":"industry",
      //    "value":"Industrial Demand"
      //  }
          // {
          //   "key":"CROP_DEMAND",
          //    "value":"Crop Demand"
          // },
          // {
          //   "key":"hydro-power",
          //    "value":"Hydro Power Demand"
          // },
          {
            "key":"basin_level_wb",
            "value":"Basin level Water Budget"
          },
          {
            "key":"panchayat_level_wb",
            "value":"Admin level Water Budget"
          },
          // {
          //   "key":"water_audit",
          //   "value":"Water Audit"
          // }
];

REPORT_COMPONENT_SOURCE_HIERARCHY_MAP = {
  [this.COMPONENT.RAINFALL] : {
    [this.IMD] : {
      ADMIN : [this.LOCATION_CONSTANT.DISTRICT,this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.STATION],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN]

    },
    [this.IMD_GRID] : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN]
    },
    [this.CWC] : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.STATION],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.STATION]
    },
  [this.KERALA_SW]: {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.STATION],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.STATION]
    },
   'STATE_AND_CENTRAL_STATION':  {
    ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.STATION],
    BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.STATION]
    },
  'STATION_REPORTS_DEFAULT': {
    ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.STATION],
    BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.STATION]
        },
  'NRSC': {
     ADMIN: [this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.STATION],
     BASIN: [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.STATION]
        },
  'GPM GRID': {
     ADMIN: [this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL, this.LOCATION_CONSTANT.STATION],
     BASIN: [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN, this.LOCATION_CONSTANT.STATION]
        },
    DEFAULT : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.STATION],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN]
    }, 
  },
  [this.COMPONENT.RESERVOIR] : {
    DEFAULT : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.RESERVOIR],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.RESERVOIR]
    }
    // [this.CWC] : {
    //   ADMIN : [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.RESERVOIR],
    //   BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.RESERVOIR],
    // },
  },
  [this.COMPONENT.RIVERPOINT] : {
    // [this.CWC] : {
    //   ADMIN : [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.STATION],
    //   BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.STATION]
    // },
    // [this.CNS] : {
    //   ADMIN : [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.STATION],
    //   BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.STATION]
    //   },
      DEFAULT : {
        ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.STATION],
        BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.STATION]
      }
  },
  [this.COMPONENT.PANCHYATH_LEVEL_WATER_BUDGET] : {
    DEFAULT : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.PANCHAYATH],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
    }
    // [this.CWC] : {
    //   ADMIN : [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.RESERVOIR],
    //   BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.RESERVOIR],
    // },
    
  },
  [this.COMPONENT.BASIN_LEVEL_WATER_BUDGET
  ] : {
    DEFAULT : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.PANCHAYATH],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.WATERSHED]
    }
  },
  [this.COMPONENT.MI_TANKS] : {
    DEFAULT : {
      ADMIN : [ this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.MANDAL,this.LOCATION_CONSTANT.MI_TANKS],
      BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.SUBBASIN,this.LOCATION_CONSTANT.MI_TANKS]
    }
    // [this.CWC] : {
    //   ADMIN : [this.LOCATION_CONSTANT.STATE, this.LOCATION_CONSTANT.DISTRICT, this.LOCATION_CONSTANT.RESERVOIR],
    //   BASIN : [this.LOCATION_CONSTANT.BASIN, this.LOCATION_CONSTANT.RESERVOIR],
    // },
  },
};



DATAUPLOAD_COMPONENT_LIST = [{
  "key": "rainfall",
  "value": "Rainfall"
},
 {
  "key": "River Authority",
  "value": "River Point"
}, 
{
  "key": "reservoir",
  "value": "Reservoir"
}, 
];


}
