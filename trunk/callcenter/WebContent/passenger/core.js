/**
 * v1.0
 */
(function(undefined){
    
    window.JS = window.$ = JS = function(q,context){
        return new JS.fn.find(q,context);
    };
    /**
     * 替换prototype，不需要使用原型继承
     */
    JS.fn = JS.prototype = {
        length : 0,
        /**
         * 通过该方法构造JS对象
         */
        find : function(q,context){
            var eles=[];
            // empty selector
            if(!q){
                return this;
            }
            // DOM元素
            if(q.nodeType || q === window){
                this[0] = q;
                this.length = 1;
                return this;
            }
            // $("exp").find("exp")
            if(!context && this.length){
                for(var i=0;i<this.length;i++){
                    eles.concat(this[i].querySelectorAll(q));
                }
            }else{
                context = context || document;
                if(typeof q === "string"){   
                    eles = context.querySelectorAll(q);
                } 
            }
            
            // 转化成类数组
            for(var i=0;i<eles.length;i++){
                this[i] = eles[i];
            }
            this.length = eles.length;     
            return this;                   
        },
        /**
         * 循环操作
         */
        each: function( callback ) {
           for(var i=0;i<this.length;i++){
               if(callback.call(this[i],i) === false)
                    break;
           }
           
           return this;
        },
        /*********** 事件绑定和解绑************/
        bind: function(eventName,fn){
            for(var i=0;i<this.length;i++){
                this[i].addEventListener(eventName, fn, false);
            }
        },
        unbind: function(eventName,fn){
            for(var i=0;i<this.length;i++){
                this[i].removeEventListener(eventName, fn,false);
            }
        }
    };
    /**
     * JS最终是通过的是find对象，需要防止find对象的原型继承
     */
    JS.fn.find.prototype = JS.fn;
    
    /**
     * 继承扩展，这里只不做深度copy
     */
    JS.extend = JS.fn.extend = function(o){
        for(var key in o){
            JS.fn[key] = o[key];
        }
    };
    
    /**
     * 因ready方法用的比较多，单独出1个静态方法，不需要使用查询参数
     */
    JS.ready = function(fn){
        // 已经DOMContentLoaded
        if ( document.readyState === "complete" ) {
            fn.call(null);
        }else{
            document.addEventListener( "DOMContentLoaded", fn, false );
        }
    };
    
    /************属性操作扩展**********/
    JS.extend({
        html : function(v){  
            if(v === undefined){
                var ele = this[0];
                return (ele && ele.nodeType === 1) ? this[0].innerHTML : null;
            }		
            for(var i=0;i<this.length;i++){
               this[i].innerHTML = v; 
            }           
            return this;
        },
        text: function(v){  
            if(v === undefined){
                return JS.decodeHTML(this.html(v));
            }		
            this.html(JS.encodeHTML(v));        
            return this;
        },		
		prepend: function(v){
            for(var i=0;i<this.length;i++){
			   var e = document.createElement('div');
			   e.innerHTML = v;			
               this[i].insertBefore(e.firstChild, this[i].firstChild); 
            }           
            return this;
		},		
		append: function(v){
            for(var i=0;i<this.length;i++){
			   var e = document.createElement('div');
			   e.innerHTML = v;			
               this[i].appendChild(e.firstChild); 
            }           
            return this;
		},
        val:function(v){
            if(v === undefined){
                // 取值
                var ele = this[0];
                if(!ele) return null;
                
                var hook = JS.valHooks[ele.nodeName.toLowerCase()];
                if(hook){
                    return hook.get(ele);
                }else{
                    return ele.value;
                }
            }
            
            // 设置值
            for(var i=0;i<this.length;i++){
                var ele = this[i];
                var hook = JS.valHooks[ele.nodeName.toLowerCase()];
                if(hook){
                    hook.set(ele,v);
                }else{
                    ele.value = v;
                }
            }
            return this;
        },
        hasClass: function(v){
            var machClass = " " + v + " ";
            for(var i=0;i<this.length;i++){
                if((" " + this[i].className + " ").indexOf(machClass) != -1)
                    return true;
            }
            return false;
        },
        addClass: function(v){
            var classNames = v.split(" "),ele,oldName;
            for(var i=0;i<this.length;i++){
                ele = this[i];
                oldName = JS.trim(ele.className);
                if(!ele.className){
                    ele.className = v;
                    continue;
                }
                
                oldName = " " + oldName + " ";
                for(var k=0;k<classNames.length;k++){
                    if(oldName.indexOf(" " + classNames[k] + " ") == -1){
                        oldName += classNames[k] + " ";
                    }
                }
                
                ele.className = JS.trim(oldName);
            }
            
            return this;
        },
        removeClass: function(v){
            var ele,oldName;
            for(var i=0;i<this.length;i++){
                ele = this[i];
                oldName = ele.className;
                if(oldName){
                    ele.className = JS.trim(oldName.replace(v,"").replace(/[ ]+/g," "));
                }   
            }
            return this;
        },
        toggleClass: function(v){
            if(this.hasClass(v)){
                return this.removeClass(v);
            }else{
                return this.addClass(v);   
            }
        },
		show: function(display){
		  display = display || "block";
		  for(var i=0;i<this.length;i++){
			this[i].style.display = display;
		  }
		},
		hide: function(){
		  for(var i=0;i<this.length;i++){
			this[i].style.display = "none";
		  }
		}, 		
         /**
         * attr仅支持类似下面的操作，不支持函数操作
         * attr("src");
         * attr("src","1.htm");
         * attr({"src":"1.thm","width":"100%";})
         */
        attr:function(k,v){
            var argsType = this._getArgType(k,v),ele;
            
            if(argsType === 0){
                // 取值
                ele = this[0];
                if(ele)
                    return ele.getAttribute(k);
                else
                    return null;    
            }
                       
            for(var i=0;i<this.length;i++){
                ele = this[i];
                if(argsType === 2){
                    // 对象赋值
                    for(var key in k){
                        ele.setAttribute(key,k[key]);
                    }
                }else{
                    ele.setAttribute(k,v);
                }
            }
            
            return this;
        },
        /**
         * css仅支持类似下面的操作，不支持函数操作
         * css("top");
         * css("top","12px");
         * css({"left":"5px","top":"5px";})
         */
        css: function(k,v){
             var argsType = this._getArgType(k,v),ele;
             
             if(argsType === 0){
                // 取值
                ele = this[0];
                if(ele)
                    return this._getStyle(ele,k);
                else
                    return null;    
             }
             
              for(var i=0;i<this.length;i++){
                 ele = this[i];
                 if(argsType === 2){
                    // 对象赋值
                    for(var key in k){
                        this._setStyle(ele,key,k[key]);
                    }
                }else{
                    this._setStyle(ele,k,v);
                }
              }
              
              return this;
        },
        
        /**
         * 设置dom样式
         */
        _setStyle: function(ele,k,v){
            if(k.indexOf("-") !== -1){
                k = k.replace(/\-[a-z]/g,function(m) { return m[1].toUpperCase();});
            }
            ele.style[k] = v;
        },
        /**
         * 获取样式值
         */
        _getStyle: function(ele,k){
            k = k.replace(/\[A-Z]/g,function(m) { return '-'+m.toLowerCase();});
            
            return document.defaultView.getComputedStyle(ele, "").getPropertyValue(k);
        },
        /**
         * 获取参数的类似
         * -1:非法参数
         * 0: 取值
         * 1：K-V赋值
         * 2：对象赋值
         */
        _getArgType: function(k,v){
            if(arguments.length < 1)
                return -1;
             
            var type0 = typeof k;   
            var type1 = typeof v;   
            if(type0 === "string" && type1 === "undefined"){
                return 0;
            }
            
            if(type0 === "object"){
                return 2;
            }
            
            if(type0 === "string" && type1 === "string"){
                return 1;
            }
            return -1;    
        }
    });
	
     /**val方法处理**/
      JS.valHooks = {
          select: {
              get: function(ele){
                  var values=[],options = ele.options;
                  for(var i=0;i<options.length;i++){
                      if(options[i].selected){
                          values.push(options[i].value);
                      }
                  }
                  
                  if(values.length==1)
                    return values[0];
                  else if(values.length==0)
                    return null;
                  else  
                    return values;  
              },
              set: function(ele,v){
                  var options = ele.options
                  var isM = ele.getAttribute("multiple")?true:false;
                  
                  for(var i=0;i<options.length;i++){
                      var o = options[i];
                      o.selected = false;
                      
                      if(isM){
                          if(JS.inArray(v,o.value))
                            o.selected = true;
                      }else{
                          // 单选
                         if(v==o.value){
                             o.selected = true;
                             return;
                         }
                      }
                  }
              }
          }
      };	
   
   /************AJAX***************/
    JS.ajax = function(url, options) {
		if(url.indexOf("?") > 0){
			url = url + "&t=" + parseInt(Math.random()*1000);
		}else{
			url = url + "?t=" + parseInt(Math.random()*1000);
		}	
        var opt = options ? options : {};
        if (typeof options == "function") {
            opt = {};
            opt.callback = options;
        }
        var method = opt.method || 'get';
        var params = opt.data || null;
        
        //创建XMLHttpRequest对象
    	var xmlreq = null;
        if (window.XMLHttpRequest) { 
            xmlreq = new XMLHttpRequest(); 
        } else if (window.ActiveXObject) { 
            try { 
                xmlreq = new ActiveXObject("Msxml12.XMLHTTP"); 
            }catch (ex) { 
                try { 
                    xmlreq = new ActiveXObject("Microsoft.XMLHTTP"); 
                }catch (e) { 
                } 
            } 
        }
        if(xmlreq){
	        xmlreq.open(method, url, true); 
	        xmlreq.onreadystatechange = function(){
	            if(xmlreq.readyState == 4){ 
	                if(xmlreq.status == 200){ 
	                	if(opt.callback != null){
	                		opt.callback(xmlreq.responseText);
	                	}
	                }else{
	                	alert("获取数据失败");
	                }
	            }         
	        }; 
	        xmlreq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	        xmlreq.send(params);         
        }
    };
	JS.getJSON = function(url, callback){
		var options = {};
        options.callback = function(responseText) {
            var result = eval('(' + responseText + ')');
			if(result.status == 200){
				callback(result.data);
			}else{
				alert("内部错误");
			}
        };		
        JS.ajax(url, options);		
	};	
	JS.getJSONP = function(url, callback) {
		if(url.indexOf("?") > 0){
			url = url + "&callback=" + 'call' + parseInt(Math.random()*1000);
		}else{
			url = url + "?callback=" + 'call' + parseInt(Math.random()*1000);
		}
		//将函数名设置为window的一个方法,这样此方法就是全局的了.
		window[ c ] = window[ c ] || function( result ) {
			if(result.status == 200){
				callback(result.data);
			}else{
				alert("内部错误");
			}
			// Garbage collect
			window[ c ] = undefined;
			try {
				delete window[ c ];
			} catch(e) {}
 
			if ( head ) {
				head.removeChild( script );
			}
		};
 
		var head = document.getElementsByTagName("head")[0] || document.documentElement;
		var script = document.createElement("script");
		script.src = url;
 
		// Handle Script loading
		var done = false;
 
		// Attach handlers for all browsers
		script.onload = script.onreadystatechange = function() {
			if ( !done && (!this.readyState ||
				this.readyState === "loaded" || this.readyState === "complete") ) {
					done = true;
 
					// Handle memory leak in IE
					script.onload = script.onreadystatechange = null;
					if ( head && script.parentNode ) {
						head.removeChild( script );
					}
			}
		};
		head.insertBefore( script, head.firstChild );
		
		//定时检查是否正常请求
 		setTimeout(function(){
 			if(!done){
 				done = true;
				// Garbage collect
				window[ c ] = undefined;
				try {
					delete window[ c ];
				} catch(e) {}
	 
				if ( head ) {
					head.removeChild( script );
				}
				alert("获取数据失败");
				
				//删除失败的服务器
				var index = ajax.count % servers.length;
				servers.splice(index, 1);
				dao.setServer(servers.join(","));					
 			}
 		}, 20000);
	};
   /*********************************其他公用方法***************************/
  JS.encodeHTML = function(v){
      return v.replace(/</g,"&lt;").replace(/>/g,"&gt;");
  };
  JS.decodeHTML = function(v){
      return v.replace(/&lt;/g,"<").replace(/&gt;/g,">");
  };
  /*判断值是否在数组中*/
  JS.inArray = function(arrays,v){
      if(!arrays.length){   // 非数组
          return arrays == v;
      }
      for(var i=0;i<arrays.length;i++){
          if(arrays[i]==v){
              return true;
          }
      }
      
      return false;
  };
  
  JS.trim = function(str){
      if(str.trim)
        return str.trim();
      else
        return str.replace(/^[\s\t ]+|[\s\t ]+$/g, '')  
  };
  JS.parseJSON = function(data){
      if ( typeof data !== "string" || !data ) {
           return null;
      }
      
      if ( window.JSON && window.JSON.parse ) {
        return window.JSON.parse( data );
      }
      
      return (new Function( "return " + JS.trim(data)))();
  };
})();
