function getRefString(url){          
    var ref=window.location.href;
	if(url.indexOf("?")>0){
		url=url+"&ref="+escape(ref);
	}
	window.location.href=url; 
}


$(function(){
	   $("[onclick='change_picktime()']").each(function(){
        this.className = "js-datetime";
		})
		
	jQuery(function(){  
		$.datepicker.regional['zh-CN'] = {  
		  clearText: '���',  
		  clearStatus: '�����ѡ����',  
		  closeText: '�ر�',  
		  closeStatus: '���ı䵱ǰѡ��',  
		  prevText: '<����',  
		  prevStatus: '��ʾ����',  
		  prevBigText: '<<',  
		  prevBigStatus: '��ʾ��һ��',  
		  nextText: '����>',  
		  nextStatus: '��ʾ����',  
		  nextBigText: '>>',  
		  nextBigStatus: '��ʾ��һ��',  
		  currentText: '����',  
		  currentStatus: '��ʾ����',  
		  monthNames: ['һ��','����','����','����','����','����', '����','����','����','ʮ��','ʮһ��','ʮ����'],  
		  monthNamesShort: ['һ','��','��','��','��','��', '��','��','��','ʮ','ʮһ','ʮ��'],  
		  monthStatus: 'ѡ���·�',  
		  yearStatus: 'ѡ�����',  
		  weekHeader: '��',  
		  weekStatus: '�����ܴ�',  
		  dayNames: ['������','����һ','���ڶ�','������','������','������','������'],  
		  dayNamesShort: ['����','��һ','�ܶ�','����','����','����','����'],  
		  dayNamesMin: ['��','һ','��','��','��','��','��'],  
		  dayStatus: '���� DD Ϊһ����ʼ',  
		  dateStatus: 'ѡ�� m�� d��, DD',  
		  dateFormat: 'yy-mm-dd',  
		  firstDay: 1,  
		  initStatus: '��ѡ������',  
		  isRTL: false  
		};  
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);  
    $('#datepicker').datepicker({changeMonth:true,changeYear:true});  
  });  

		
		$(".js-datetime").datepicker({ buttonText: "Choose" , changeMonth: true,  changeYear: true, dateFormat: "yy-mm-dd",currentText: 'Now', showOtherMonths: true,gotoCurrent: true,yearRange:"1900:2020"});
	
	})