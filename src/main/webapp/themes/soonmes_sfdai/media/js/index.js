jQuery(function(){


        function nav(obj,childUl,childBox){
            var box = $(obj);
            var oLi = $("li",childUl);
            var oDiv = $(childBox);
            function showDiv(){
                oLi.each(function(i){
                    if($(oLi[i]).hasClass("hover2"))
                    {
                        oDiv.addClass("hide")
                        $(oDiv[i]).removeClass("hide");
                    }
                })
            }
            oLi.mouseenter(function(){
                var index = oLi.index(this);
                $(oLi[index]).addClass("hover").siblings().removeClass("hover");
                oDiv.addClass("hide");
                $(oDiv[index]).removeClass("hide");
            })
            box.mouseleave(function(){
                oLi.removeClass("hover");
                showDiv();
            })
        }
        nav(".nav",".navul",".navlist");

	function Tabs(obj){
		var box = $(obj);
		var oUl = $("ul:first",box);
		var oLi = $("li",oUl);
		var oDiv = $("div:first>ul",obj);
		oLi.mouseenter(function(){
			var index = oLi.index(this);
			$(oLi[index]).addClass("hover").siblings().removeClass("hover");
			oDiv.hide();
			$(oDiv[index]).show();
			});
		}
		Tabs(".news-txt");
		Tabs(".question-txt");
		Tabs(".save-tab1");
		Tabs(".save-tab2");
		Tabs(".invest-suc");
		Tabs(".zh-user-tab");

	function slideTop(id){
		var $this = $(id);
		$this.hover(function(){clearInterval(scrollTimer)},function(){
				scrollTimer = setInterval(function(){
					scrollNews($this);
					},3000)
			});
			var scrollTimer = setInterval(function(){
				scrollNews($this);
				},3000);		
			
			function scrollNews(obj){
				var $self = obj.find("ul:first");
				var heights = $self.find("li:first").height();
				$self.animate({"marginTop":-heights+"px"},800,function(){
						$self.css({marginTop:0}).find("li:first").appendTo($self);
					})
				}
		}
		slideTop(".slideupbox");
		
		function choose(){
			var oBox = $(".chooseJS");
			oBox.each(function(){
				change(this);
				}) 	
			function change(obj){
				var box = $(obj);
				var oLi = $("li",box);
				oLi.click(function(){
					var index = oLi.index(this);
					$(oLi[index]).addClass("clicks").siblings().removeClass("clicks");
					
					var url=window.location.href;
					var index = url.indexOf("?");
					if(index>0){
						url=url.substring(0,index);
					}
					//url=url.match(/(.*?)\?/)[1];
					url=url+"?"+getSearchString()+"search=union";
					window.location.href=url;
				})
			}	
		}	
		choose();
		
		function getQueryString(name){     
			var reg=new  RegExp("(^|&)"+name+"=([^&]*)(&|$)");     
		    var r =window.location.search.substr(1).match(reg);     
		    if(r!=null) 
		    	return  unescape(r[2]);  
		    return  null;     
		}
		
		var searchNames=["sType","sApr","sLimit","sAccount"];
		for(var item in searchNames){
			searchDisplay(searchNames[item]);
		}
		
		function searchDisplay(searchName){
			var sTypeValue=getQueryString(searchName);
			if(sTypeValue==null) sTypeValue="all";
			var sTypeLi=$("#"+searchName).children();
			sTypeLi.each(function(i){
				$(this).removeClass("clicks");
				var value=$(this).attr("data-value");
				if(value==sTypeValue) $(this).addClass("clicks");
			});
		}
		
		function getSearchString(){
			var searchStr="";
			for(var item in searchNames){
				var searchName=searchNames[item];
				var sTypeLi=$("#"+searchName).children();
				sTypeLi.each(function(i){
					if($(sTypeLi[i]).hasClass("clicks")){
						searchStr+=searchName+"="+$(sTypeLi[i]).attr("data-value")+"&";
					}
					
				})
			}
			return searchStr;
		}
		function Tab(obj,childLi,childBox){
        var box = $(obj);
        var oLi = $(childLi,box);
        var oDiv = $(childBox , box);
        oLi.mouseenter(function(){
            var index = oLi.index(this);
            $(oLi[index]).addClass("hover").siblings().removeClass("hover");
            oDiv.hide();
            $(oDiv[index]).show();
        })
    }
   	 Tab(".lz-tab","li",".lz-tabtxt");
     Tab(".list_li_3",".toolul li",".m_l_bor");
	 
	 function hidenav(){
		 var box = $("#floatBtn");
		 var oA = $("a",box);
		 var handle = $(".fb5",box);
		 handle.toggle(function(){
			 handle.text("更多");
			 oA.slideUp();
			 },function(){
				handle.text("隐藏");
			 	oA.slideDown(); 
				 })
		 }
	hidenav();	 
})		
	jQuery(function(){ 
		var $ = jQuery;
		$(window).scroll(function (){
			var offsetTop = $(window).scrollTop() + 180 +"px";
			$("#floatBtn").animate({top : offsetTop},{duration:500 , queue:false});
			});
	})	
	

$(function(){
	   $("[onclick='change_picktime()']").each(function(){
        this.className = "js-datetime";
		})
		
	jQuery(function(){  
		$.datepicker.regional['zh-CN'] = {  
		  clearText: '清除',  
		  clearStatus: '清除已选日期',  
		  closeText: '关闭',  
		  closeStatus: '不改变当前选择',  
		  prevText: '<上月',  
		  prevStatus: '显示上月',  
		  prevBigText: '<<',  
		  prevBigStatus: '显示上一年',  
		  nextText: '下月>',  
		  nextStatus: '显示下月',  
		  nextBigText: '>>',  
		  nextBigStatus: '显示下一年',  
		  currentText: '今天',  
		  currentStatus: '显示本月',  
		  monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],  
		  monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],  
		  monthStatus: '选择月份',  
		  yearStatus: '选择年份',  
		  weekHeader: '周',  
		  weekStatus: '年内周次',  
		  dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		  dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		  dayNamesMin: ['日','一','二','三','四','五','六'],  
		  dayStatus: '设置 DD 为一周起始',  
		  dateStatus: '选择 m月 d日, DD',  
		  dateFormat: 'yy-mm-dd',  
		  firstDay: 1,  
		  initStatus: '请选择日期',  
		  isRTL: false  
		};  
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);  
    $('#datepicker').datepicker({changeMonth:true,changeYear:true});  
  });  

		
		$(".js-datetime").datepicker({ buttonText: "Choose" , changeMonth: true,  changeYear: true, dateFormat: "yy-mm-dd",currentText: 'Now', showOtherMonths: true,gotoCurrent: true,yearRange:"1900:2020"});
	
	})