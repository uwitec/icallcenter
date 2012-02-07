/***************应用对象*************/
var app = {
	server : ["http://192.168.1.108:8080"],
	viewState : [],
	toast : function(msg){
		if($("#toast")[0]){
			$("#toast").html("<span>" + msg + "</span>");
		}else{
			$("body").append("<div id='toast'><span>" + msg + "</span></div>");
		}
		setTimeout(function(){
			$("#toast").remove();
		}, 3000);
	},
	display : function(id, title, data){
		if(app.viewState.length > 0){
			//保存当前状态
			var state = app.viewState.pop();
			state.scrollTop = document.body.scrollTop;
			app.viewState.push(state);		
			$("#" + state.id).hide();
		}
		//创建新状态
		var state = new Object();
		state.id = id;
		state.title = title;
		app.viewState.push(state);		
	
		//显示视图
		$("#title").html(title);
		$("#content").append(data);
		document.body.scrollTop = 0;
	},
	goback : function(){
		if(app.viewState.length <= 1){
			if(navigator.notification){
				navigator.notification.confirm("确定要退出吗?", function(button){
					if(button == 1){
						navigator.app.exitApp();
					}			
				}, "提示", "确定,取消");			
			}
		}else{		
			$("#" + app.viewState.pop().id).remove();	
			//显示视图
			var state = app.viewState[app.viewState.length - 1];
			$("#title").html(state.title);	
			$("#" + state.id).show();
			document.body.scrollTop = state.scrollTop;
		}
	},
	init: function(){
		//执行错误
		window.onerror = function(){
			app.toast("处理失败");
		};	
		//返回按键
		$(document).bind("backbutton", function(){
			app.goback();		
		});
		//加载地图
		main.loadMap();	
	}
};

/***************主模块*************/
var main = {
	loadMap: function(){
		$("body").html("<div id='map' style='width:100%; height:" + window.screen.height + "px;'></div>");
		//var latitude = position.coords.latitude;
		//var longitude = position.coords.longitude;
		var latitude = 22.543099;
		var longitude = 114.057868;			
		var myOptions = {
			  zoom: 15,
			  center: new google.maps.LatLng(latitude, longitude),
			  mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		window.map = new google.maps.Map(document.getElementById('map'), myOptions);	
	}
}