/***************视图对象*************/
var view = {
	appServer : "http://192.168.1.108:8080",
	pageState : [],
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
		if(view.pageState.length > 0){
			//保存当前状态
			var state = view.pageState.pop();
			state.scrollTop = document.body.scrollTop;
			view.pageState.push(state);		
			$("#" + state.id).hide();
		}
		//创建新状态
		var state = new Object();
		state.id = id;
		state.title = title;
		view.pageState.push(state);		
	
		//显示视图
		$("#title").html(title);
		$("#content").append(data);
		document.body.scrollTop = 0;
	},
	goback : function(){
		if(view.pageState.length <= 1){
			if(navigator.notification){
				navigator.notification.confirm("确定要退出吗?", function(button){
					if(button == 1){
						navigator.app.exitApp();
					}			
				}, "提示", "确定,取消");			
			}
		}else{		
			$("#" + view.pageState.pop().id).remove();	
			//显示视图
			var state = view.pageState[view.pageState.length - 1];
			$("#title").html(state.title);	
			$("#" + state.id).show();
			document.body.scrollTop = state.scrollTop;
		}
	}		
};

/***************初始化*************/
$.ready(function(){
	//执行错误
	window.onerror = function(){
		view.toast("处理失败");
	};	
	//返回按键
	$(document).bind("backbutton", function(){
		view.goback();		
	});
	$("body").bind("onunload", function(){
		GUnload();
	});
	$("body").append("<div id='map' style='width:100%; height:" + window.screen.height + "px;'></div>");
	if (GBrowserIsCompatible()) {
		navigator.geolocation.getCurrentPosition(function(position){
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;	      
			var map = new GMap2(document.getElementById("map"));
			map.setCenter(new GLatLng(latitude, longitude), 15);	

			map.addOverlay(new GMarker(new GLatLng(latitude, longitude)));		
		}, null);

	}
});