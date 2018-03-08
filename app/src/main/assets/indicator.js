var responseData;
            var responseDataSMA;
            var responseDataEMA;
            var responseDataADX;
            var responseDataCCI;
            var responseDataSTOCH;
            var responseDataRSI;
            var responseDataBBANDS;
            var responseDataMACD;
            var responseDataNews;
            var responseSymbol;

            $(document).ready(function(){

                //document.getElementById("chart").innerHTML = "This is not executed";


        });
            function pad(s) { return (s < 10) ? '0' + s : s; }
            function getAndSetData(symbol){
             var parameters = {
                    symbol:symbol

             }
             try{
                 $.ajax({
                   // url:'http://localhost:3000/price',
                    url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/price',
                    data:parameters,
                    type:'GET',
                    success: function (response,status,xhr) {
                        console.log(JSON.parse(response));
                        responseData = JSON.parse(response);
                        displayPriceChart();
                    },
                    error: function (xhr,status,error) {

                        document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Price Data</div>";
                    }
                });
             }catch(Exception){
                // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Price Data</div>";
             }
            try{
                $.ajax({
                    //url:'http://localhost:3000/sma',
                    url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/sma',
                    data:parameters,
                    type:'GET',
                    success: function (response,status,xhr) {
                        console.log(JSON.parse(response));
                        responseDataSMA = JSON.parse(response);
                        if(responseDataSMA.sma=={}){
                        throw("Exception");
                        }
                    },
                    error: function (xhr,status,error) {
                       // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get SMA Data</div>";
                    }
                });
            }catch(Exception){
               // document.getElementById("smaDiv").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get SMA Data</div>";
            }
            try{
                $.ajax({
                   //url:'http://localhost:3000/ema',
                   url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/ema',
                    data:parameters,
                    type:'GET',
                success: function (response,status,xhr) {
                    console.log(JSON.parse(response));
                    responseDataEMA = JSON.parse(response);
                    if(responseDataEMA.ema=={}){
                        throw("Exception");
                    }
                },
                error: function (xhr,status,error) {
                   //  document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get EMA Data</div>";
                }
                });
            }catch(Exception){
              //  document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get EMA Data</div>";
            }

            try{
                $.ajax({
                   // url:'http://localhost:3000/stoch',
                    url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/stoch',
                    data:parameters,
                    type:'GET',
                success: function (response,status,xhr) {
                    console.log(JSON.parse(response));
                    responseDataSTOCH = JSON.parse(response);
                    if(responseDataSTOCH.stoch=={}){
                        throw("Exception");
                    }
                },
                error: function (xhr,status,error) {
                    //document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get STOCH Data</div>";
                }
                });
            }catch(Exception){
                //document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get STOCH Data</div>";
            }
            try{
                $.ajax({
                   // url:'http://localhost:3000/rsi',
                    url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/rsi',
                    data:parameters,
                    type:'GET',
                success: function (response,status,xhr) {
                    console.log(JSON.parse(response));

                    responseDataRSI = JSON.parse(response);
                    if(responseDataRSI.rsi=={}){
                        throw("Exception");
                    }

                },
                error: function (xhr,status,error) {
                    // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get RSI Data</div>";
                }
                });
            }catch(Exception){
               // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get RSI Data</div>";
            }

            try{
                $.ajax({
                //url:'http://localhost:3000/adx',
                url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/adx',
                data:parameters,
                type:'GET',
            success: function (response,status,xhr) {
                console.log(JSON.parse(response));
                responseDataADX = JSON.parse(response);
                if(responseDataADX.adx=={}){
                        throw("Exception");
                    }
            },
            error: function (xhr,status,error) {
               // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get ADX Data</div>";
            }
            });
            }catch(Exception){
               // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get ADX Data</div>";
            }
            try{
            $.ajax({
               // url:'http://localhost:3000/cci',
                url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/cci',
                data:parameters,
                type:'GET',
            success: function (response,status,xhr) {
                console.log(JSON.parse(response));
                responseDataCCI = JSON.parse(response);
                if(responseDataCCI.cci=={}){
                        throw("Exception");
                    }

            },
            error: function (xhr,status,error) {
               // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get CCI Data</div>";
            }
            });
            }catch(Exception){
               // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get CCI Data</div>";
            }

            try{
                $.ajax({
                //url:'http://localhost:3000/bbands',
                url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/bbands',
                data:parameters,
                type:'GET',
                success: function (response,status,xhr) {
                    console.log(JSON.parse(response));
                    responseDataBBANDS = JSON.parse(response);
                    if(responseDataBBANDS.bbands=={}){
                        throw("Exception");
                    }
                },
                error: function (xhr,status,error) {
                    // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get BBANDS Data</div>";
                }
            });
            }catch(Exception){
                //document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get BBANDS Data</div>";
            }

            try{
                $.ajax({
               // url:'http://localhost:3000/macd',
                url:'http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/macd',
                data:parameters,
                type:'GET',
                success: function (response,status,xhr) {
                    console.log(JSON.parse(response));
                    responseDataMACD = JSON.parse(response);
                    if(responseDataMACD.macd=={}){
                        throw(Exception);
                    }

                },
                error: function (xhr,status,error) {
                   // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get MACD Data</div>";
                }
            });
            }catch(Ec){
               // document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get MACD Data</div>";
            }
        }

        function displayPriceChart(){
        try{
            var dateArray=[];
            var valueArray=[];
            var volumeArray = [];
            var j=0;
            for(var i=130;i>=0;i--){
                var d = Object.keys(responseData.priceSeries)[i];
                var date = new Date(Object.keys(responseData.priceSeries)[i]);
                dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
                valueArray[j] = parseFloat(responseData.priceSeries[d]["4. close"]);
                volumeArray[j] = parseInt(responseData.priceSeries[d]["5. volume"]/1000000);
                j++;

            }
            Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                borderColor:'#b3b4b5',
                zoomType: 'x',
                height:330

            },
            title: {
                text: responseData.price.symbol+' Stock Price and Volume',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                pointStart: dateArray[0],
              //  pointInterval: 24 * 3600 * 1000*7, // one day
                tickInterval:7,
                categories: dateArray
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'Stock Price',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
            }, { // Secondary yAxisss
                tickInterval:50,
                title: {
                    text: 'Volume',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                labels: {
                    format: '{value} mn',
                    style: {
                        color:  Highcharts.getOptions().colors[1]
                    }
                },
                opposite: true
            }],
            tooltip: {
                shared: false
            },
            legend: {
                layout: 'horizontal',
                x:150,
                align: 'bottom',
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                area: {
                    lineColor: '#0033cc',
                    fillColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color('#0033cc').setOpacity(0).get('rgba')]
                        ]
                    },
                    lineWidth: 3,
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    }
                }
            },
            series: [{
                name: responseData.price.symbol,
                type: 'area',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#0033cc',
                },{
                name: responseData.price.symbol+' Volume',
                type: 'column',
                yAxis: 1,
                data: volumeArray,
                tooltip: {
                    valueSuffix: ' mn'
                },
                color:'#e60000'
            }]
        });
        }catch(Exception){
         //   document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Price Data</div>";
        }

        }
        function displaySMAChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataSMA.sma["Technical Analysis: SMA"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataSMA.sma["Technical Analysis: SMA"])[i].SMA);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                borderColor:'#b3b4b5',
                zoomType: 'x',
                height:330

            },
            title: {
                text: 'Simple Moving Average (SMA)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                tickInterval:7,
                pointStart: dateArray[0],
                categories: dateArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'SMA',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 150,
                verticalAlign: 'bottom',

                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {
                    lineColor: '#e50202',
                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataSMA.sma["Meta Data"]["1: Symbol"],
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                }]
        });
        }catch(Exception){
             document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get SMA Data</div>";
        }

        }
        function displayEMAChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataEMA.ema["Technical Analysis: EMA"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataEMA.ema["Technical Analysis: EMA"])[i].EMA);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                borderColor:'#b3b4b5',
                zoomType: 'x',
                height:330
            },
            title: {
                text: 'Exponential Moving Average (EMA)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                tickInterval:7,
                pointStart: dateArray[0],
                categories: dateArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'EMA',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                verticalAlign: 'bottom',
                x:100,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {
                    lineColor: '#e50202',
                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataEMA.ema["Meta Data"]["1: Symbol"],
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get EMA Data</div>";
        }

        }
        function displaySTOCHChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var valueArray2 = [];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataSTOCH.stoch["Technical Analysis: STOCH"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataSTOCH.stoch["Technical Analysis: STOCH"])[i].SlowD);
            valueArray2[j] = parseFloat(Object.values(responseDataSTOCH.stoch["Technical Analysis: STOCH"])[i].SlowK);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                borderColor:'#b3b4b5',
                zoomType: 'x',
                height:330

            },
            title: {
                text: 'Stochastic Oscillator (STOCH)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                pointStart: dateArray[0],
                tickInterval:7,
                categories: dateArray,
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'STOCH',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 150,
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {

                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataSTOCH.stoch["Meta Data"]["1: Symbol"]+" SlowK",
                type: 'line',
                data: valueArray2,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                },{
                name: responseDataSTOCH.stoch["Meta Data"]["1: Symbol"]+" SlowD",
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#5990ea',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get STOCH Data</div>";
        }

        }
        function displayRSIChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataRSI.rsi["Technical Analysis: RSI"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataRSI.rsi["Technical Analysis: RSI"])[i].RSI);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                borderColor:'#b3b4b5',
                zoomType: 'x',
                height:330
            },
            title: {
                text: 'Relative Strength Index (RSI)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                pointStart: dateArray[0],
                tickInterval:7,
                categories: dateArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'RSI',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 150,
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {
                    lineColor: '#e50202',
                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataRSI.rsi["Meta Data"]["1: Symbol"],
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get RSI Data</div>";
        }

        }
        function displayADXChart(){
        try{
                var dateArray=[];
        var valueArray=[];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataADX.adx["Technical Analysis: ADX"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataADX.adx["Technical Analysis: ADX"])[i].ADX);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                borderColor:'#b3b4b5',
                zoomType: 'x',
                height:330
            },
            title: {
                text: 'Average Directional Movement Index (ADX)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                tickInterval:7,
                pointStart: dateArray[0],
                categories: dateArray,
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'ADX',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 150,
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {
                    lineColor: '#e50202',
                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataADX.adx["Meta Data"]["1: Symbol"],
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get ADX Data</div>";
        }

        }
        function displayCCIChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataCCI.cci["Technical Analysis: CCI"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataCCI.cci["Technical Analysis: CCI"])[i].CCI);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                zoomType: 'x',
                height:330
            },
            title: {
                text: 'Commodity Channel Index (CCI)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                tickInterval:7,
                pointStart: dateArray[0],
                categories: dateArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'CCI',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 150,
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {
                    lineColor: '#e50202',
                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataCCI.cci["Meta Data"]["1: Symbol"],
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get CCI Data</div>";
        }

        }
        function displayBBANDSChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var valueArray2 = [];
        var valueArray3 = [];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataBBANDS.bbands["Technical Analysis: BBANDS"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataBBANDS.bbands["Technical Analysis: BBANDS"])[i]["Real Middle Band"]);
            valueArray2[j] = parseFloat(Object.values(responseDataBBANDS.bbands["Technical Analysis: BBANDS"])[i]["Real Upper Band"]);
            valueArray3[j] = parseFloat(Object.values(responseDataBBANDS.bbands["Technical Analysis: BBANDS"])[i]["Real Lower Band"]);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                zoomType: 'x',
                height:330
            },
            title: {
                text: 'Bollinger Bands (BBANDS)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                pointStart: dateArray[0],
                tickInterval:7,
                categories: dateArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'BBANDS',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 120,
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {

                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataBBANDS.bbands["Meta Data"]["1: Symbol"]+" Real Middle Band",
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                },{
                name: responseDataBBANDS.bbands["Meta Data"]["1: Symbol"]+" Real Upper Band",
                type: 'line',
                data: valueArray2,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#000000',
                },{
                name: responseDataBBANDS.bbands["Meta Data"]["1: Symbol"]+" Real Lower Band",
                type: 'line',
                data: valueArray3,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#3FFF58',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get BBANDS Data</div>";
        }

        }
        function displayMACDChart(){
        try{
            var dateArray=[];
        var valueArray=[];
        var valueArray2 = [];
        var valueArray3 = [];
        var j=0;
        for(var i=130;i>=0;i--){
            var date = new Date(Object.keys(responseDataMACD.macd["Technical Analysis: MACD"])[i]);
            dateArray[j] = pad(date.getUTCMonth()+1)+"/"+pad((date.getDate()+1));
            valueArray[j] = parseFloat(Object.values(responseDataMACD.macd["Technical Analysis: MACD"])[i]["MACD"]);
            valueArray3[j] = parseFloat(Object.values(responseDataMACD.macd["Technical Analysis: MACD"])[i]["MACD_Signal"]);
            valueArray2[j] = parseFloat(Object.values(responseDataMACD.macd["Technical Analysis: MACD"])[i]["MACD_Hist"]);
            j++;

        }
        Highcharts.chart('container', {
            chart: {
                marginBottom:100,
                zoomType: 'x',
                height:330

            },
            title: {
                text: 'Moving Average Convergence/Divergence (MACD)',
                useHTML:true
            },
            subtitle: {
                text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                useHTML:true
            },
            xAxis: [{
                type: 'datetime',
                pointStart: dateArray[0],
                tickInterval:7,
                categories: dateArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'MACD',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }],
            legend: {
                layout: 'horizontal',
                align: 'bottom',
                x: 120,
                verticalAlign: 'bottom',
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            plotOptions: {
                line: {

                    lineWidth: 1,
                    marker: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true

                            }
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                name: responseDataMACD.macd["Meta Data"]["1: Symbol"]+" MACD",
                type: 'line',
                data: valueArray,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#ef6e6e',
                },{
                name: responseDataMACD.macd["Meta Data"]["1: Symbol"]+" MACD_Hist",
                type: 'line',
                data: valueArray2,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#e6e600',
                },{
                name: responseDataMACD.macd["Meta Data"]["1: Symbol"]+" MACD_Signal",
                type: 'line',
                data: valueArray3,
                tooltip: {
                    valueSuffix: ''
                },
                color:'#4334FF',
                }]
        });
        }catch(Exception){
            document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get MACD Data</div>";
        }

        }