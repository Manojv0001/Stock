var responseData;

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
                        $("#fb_button").attr('disabled',false);
                        $("#fav_button").attr('disabled',false);
                        responseData = JSON.parse(response);
                        displayHistoricalChart(responseData);
                    },
                    error: function (xhr,status,error) {
                       document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Historical Stock Data</div>";
                       // document.getElementById("hisDiv").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Historical Charts Data</div>";
                       // document.getElementById("pDiv").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Price Data</div>";
                    }
                });
             }catch(Exception){
                 document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Historical Stock Data</div>";
                // document.getElementById("hisDiv").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Historical Charts Data</div>";
                 //document.getElementById("pDiv").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get Price Data</div>";
             }
   }

   function displayHistoricalChart(responseObject){
           try{
               var dataArray = [];
           var dateArray=[];
               var valueArray=[];
               var j=0;
               var total;
               var size = Object.keys(responseObject.priceSeries).length;
               if(size>1000){
                   total=1000;
               }else{
                   total = size;
               }
               for(var i=total-1;i>=0;i--){
                   var temp=[];
                   var d = Object.keys(responseObject.priceSeries)[i];
                   var date = new Date(Object.keys(responseObject.priceSeries)[i]);
                   dateArray[j] = Date.UTC(date.getFullYear(),date.getMonth(),date.getDate()+1);
                   valueArray[j] = parseFloat(responseObject.priceSeries[d]["4. close"]);
                   temp.push(dateArray[j],valueArray[j]);
                   dataArray.push(temp);

                   j++;
               }
           Highcharts.stockChart('container', {
               rangeSelector: {
                       buttons: [{
                           type: 'week',
                           count: 1,
                           text: '1w'
                       }, {
                           type: 'month',
                           count: 1,
                           text: '1m'
                       }, {
                           type: 'month',
                           count: 3,
                           text: '3m'
                       }, {
                           type: 'month',
                           count: 6,
                           text: '6m'
                       }, {
                           type: 'ytd',
                           text: 'YTD'
                       },{
                           type: 'year',
                           count: 1,
                           text: '1y'
                       },{
                           type: 'all',
                           text: 'All'
                       }],
                       selected: 0
                   },
               title: {
                   text: responseObject.price.symbol+' Stock Value'
               },
               subtitle: {
                   text:'<a href=" https://www.alphavantage.co/">Source: Alpha Vantage</a>',
                   useHTML:true
               },
               yAxis: [{ // Primary yAxis
                   title: {
                       text: 'Stock Value',
                       style: {
                           color: Highcharts.getOptions().colors[1]
                       }
                   }
               }],
               series: [{
                   name: responseObject.price.symbol,
                   data: dataArray,
                   type: 'area',
                   threshold: null,

                   tooltip: {
                       shared:true,
                       valueSuffix:""
                   },
                   color: "#66b3ff",
                   fillColor:"#66b3ff"
               }]
           });
           }catch(Exception){
               document.getElementById("container").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error! Failed to get History Data</div>";
           }

           }