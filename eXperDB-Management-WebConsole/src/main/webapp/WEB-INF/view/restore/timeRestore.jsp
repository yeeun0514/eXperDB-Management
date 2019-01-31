<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="../cmmn/cs.jsp"%>
<%@include file="../cmmn/passwordConfirm.jsp"%>

<%
	/**
	* @Class Name : timeRestore.jsp
	* @Description : timeRestore 화면
	* @Modification Information
	*
	*   수정일         수정자                   수정내용
	*  ------------    -----------    ---------------------------
	*  2019.01.09     최초 생성
	*
	* author 변승우 대리
	* since 2019.01.09
	*
	*/
%>

<script type="text/javascript">

var rman_pth = null;
var db_svr_id = "${db_svr_id}";

/* ********************************************************
 * 페이지 시작시 함수
 ******************************************************** */
$(window.document).ready(function() {
	fn_init();
	
	fn_makeHour();
	fn_makeMin();
	fn_makeSec();
	
	 $.ajax({
			async : false,
			url : "/selectPathInfo.do",
		  	data : {
		  		db_svr_id : db_svr_id
		  	},
			type : "post",
			beforeSend: function(xhr) {
		        xhr.setRequestHeader("AJAX", true);
		     },
			error : function(xhr, status, error) {
				if(xhr.status == 401) {
					alert('<spring:message code="message.msg02" />');
					top.location.href = "/";
				} else if(xhr.status == 403) {
					alert('<spring:message code="message.msg03" />');
					top.location.href = "/";
				} else {
					alert("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""));
				}
			},
			success : function(result) {
				rman_pth=result[1].PGRBAK;
			}
		}); 
});


/* ********************************************************
 * 초기설정
 ******************************************************** */
 function fn_init(){
	 $("#storage_view").hide();
}


/* ********************************************************
 * 시간
 ******************************************************** */
function fn_makeHour(){
	var hour = "";
	var hourHtml ="";
	
	hourHtml += '<select class="select t6" name="timeline_h" id="timeline_h" style="height: 25px;">';	
	for(var i=0; i<=23; i++){
		if(i >= 0 && i<10){
			hour = "0" + i;
		}else{
			hour = i;
		}
		hourHtml += '<option value="'+hour+'">'+hour+'</option>';
	}
	hourHtml += '</select> <spring:message code="schedule.our" />';	
	$( "#hour" ).append(hourHtml);
}


/* ********************************************************
 * 분
 ******************************************************** */
function fn_makeMin(){
	var min = "";
	var minHtml ="";
	
	minHtml += '<select class="select t6" name="timeline_m" id="timeline_m" style="height: 25px;">';	
	for(var i=0; i<=59; i++){
		if(i >= 0 && i<10){
			min = "0" + i;
		}else{
			min = i;
		}
		minHtml += '<option value="'+min+'">'+min+'</option>';
	}
	minHtml += '</select> <spring:message code="schedule.minute" />';	
	$( "#min" ).append(minHtml);
}

/* ********************************************************
 * 초
 ******************************************************** */
 function fn_makeSec(){
	var sec = "";
	var secHtml ="";
	
	secHtml += '<select class="select t6" name="timeline_s" id="timeline_s" style="height: 25px;">';	
	for(var i=0; i<=59; i++){
		if(i >= 0 && i<10){
			sec = "0" + i;
		}else{
			sec = i;
		}
		secHtml += '<option value="'+sec+'">'+sec+'</option>';
	}
	secHtml += '</select> <spring:message code="schedule.second" />';	
	$( "#sec" ).append(secHtml);
} 


 /* ********************************************************
  * Storage 경로 선택 (기존/신규)
  ******************************************************** */
  function fn_storage_path_set(){
 	 var asis_flag = $(":input:radio[name=asis_flag]:checked").val();

 	if(asis_flag == "0"){
 		$("#storage_view").hide();
 	}else{
 		$("#storage_view").show();
 	}
 }

 
  /* ********************************************************
   * 신규 Storage 경로 확인
   ******************************************************** */
   function fn_new_storage_check(){
 	 var new_storage = "/"+$("#restore_dir").val();
 	 
 	$("#dtb_pth").val(new_storage+$("#dtb_pth").val());
 	$("#svrlog_pth").val(new_storage+$("#svrlog_pth").val());
  }
  
  
   /* ********************************************************
    * RMAN Show 정보 확인
    ******************************************************** */
   function fn_rmanShow(){
	   
		  var frmPop= document.frmPopup;
		    var url = '/rmanShowView.do';
		    window.open('','popupView','width=1500, height=800');  
		     
		    frmPop.action = url;
		    frmPop.target = 'popupView';
		    frmPop.bck.value = rman_pth;
		    frmPop.db_svr_id.value = db_svr_id;  
		    frmPop.submit();   
	}
  
  
   /* ********************************************************
    * RMAN Restore 정보 저장
    ******************************************************** */
   function fn_execute(){
	   	var timeline_dt = $("#datepicker1").val().replace(/-/gi,'').trim();
		var asis_flag = $(":input:radio[name=asis_flag]:checked").val();

		$.ajax({
				url : "/insertRmanRestore.do",
				data : {
					db_svr_id : db_svr_id,
					asis_flag : asis_flag,
					restore_dir : $("#restore_dir").val(),
					dtb_pth : $('#dtb_pth').val(),
					pgalog_pth : $('#pgalog_pth').val(),
					svrlog_pth : $('#svrlog_pth').val(),
					bck_pth : $('#bck_pth').val(),
					restore_cndt : 1,
					restore_flag : 1,					
					timeline_dt : timeline_dt,
					timeline_h : $("#timeline_h").val(),
					timeline_m : $("#timeline_m").val(),
					timeline_s : $("#timeline_s").val(),
					restore_nm : $('#restore_nm').val(),
					restore_exp : $('#restore_exp').val()
				},
				dataType : "json",
				type : "post",
				beforeSend: function(xhr) {
			        xhr.setRequestHeader("AJAX", true);
			     },
				error : function(xhr, status, error) {
					if(xhr.status == 401) {
						alert('<spring:message code="message.msg02" />');
						top.location.href = "/";
					} else if(xhr.status == 403) {
						alert('<spring:message code="message.msg03" />');
						top.location.href = "/";
					} else {
						alert("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""));
					}
				},
				success : function(result) {
					alert("시점 복구를 시작합니다.");
				}
			}); 
	 }
   
   
 //복구명 중복체크
   function fn_check() {
   	var restore_nm = document.getElementById("restore_nm");
   	if (restore_nm.value == "") {
   		alert('<spring:message code="message.msg107" />');
   		document.getElementById('restore_nm').focus();
   		return;
   	}
   	$.ajax({
   		url : '/restore_nmCheck.do',
   		type : 'post',
   		data : {
   			restore_nm : $("#restore_nm").val()
   		},
   		success : function(result) {
 			if (result == "true") {
 				alert('등록 가능한 복구명 입니다.');
 				document.getElementById("restore_nm").focus();
 				wrk_nmChk = "success";		
 			} else {
 				scd_nmChk = "fail";
 				alert('이미 존재하는 복구명 입니다.');
 				document.getElementById("restore_nm").focus();
 			}
   		},
   		beforeSend: function(xhr) {
   	        xhr.setRequestHeader("AJAX", true);
   	     },
   		error : function(xhr, status, error) {
   			if(xhr.status == 401) {
   				alert('<spring:message code="message.msg02" />');
   				top.location.href = "/";
   			} else if(xhr.status == 403) {
   				alert('<spring:message code="message.msg03" />');
   				top.location.href = "/";
   			} else {
   				alert("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""));
   			}
   		}
   	});
   } 
</script>

<form name="frmPopup">
	<input type="hidden" name="bck"  id="bck">
	<input type="hidden" name="db_svr_id"  id="db_svr_id">
</form>

<!-- contents -->
<div id="contents">
	<div class="contents_wrap">
		<div class="contents_tit">
			<h4>시점복구<a href="#n"><img src="../images/ico_tit.png" class="btn_info" /></a></h4>
			<div class="infobox">
				<ul>
					<li>시점복구</li>
				</ul>
			</div>
			<div class="location">
				<ul>
					<li class="bold">${db_svr_nm}</li>
					<li>Restore</li>
					<li class="on">시점복구</li>
				</ul>
			</div>
		</div>
		<div class="contents">
		<div class="btn_type_01">
			<span class="btn"><button type="button" id="btnSelect" onClick="fn_passwordConfilm();">실행</button></span>
		</div>
		
		
							<table class="write" style="border:1px solid #b8c3c6; border-collapse: separate;">
						<colgroup>
							<col style="width:130px;" />
							<col />
							<col style="width:130px;" />
							<col />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">복구명</th>
								<td><input type="text" class="txt" name="restore_nm" id="restore_nm" maxlength="20" onkeyup="fn_checkWord(this,20)" placeholder="20<spring:message code='message.msg188'/>" onblur="this.value=this.value.trim()"/>
								<span class="btn btnC_01"><button type="button" class= "btn_type_02" onclick="fn_check()" style="width: 60px; margin-right: -60px; margin-top: 0;"><spring:message code="common.overlap_check" /></button></span>
								</td>
							</tr>
							<tr>
								<th scope="row" class="ico_t1">복구설명</th>
								<td>
									<div class="textarea_grp">
										<textarea name="restore_exp" id="restore_exp" maxlength="25" onkeyup="fn_checkWord(this,25)" placeholder="25<spring:message code='message.msg188'/>"></textarea>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" class="ico_t1">서버명</th>
								<td>
									<input type="text" class="txt" name="db_svr_nm" id="db_svr_nm" readonly="readonly"  value="${db_svr_nm}">
								</td>
								<th scope="row" class="ico_t1">서버아이피</th>
								<td>
									<input type="text" class="txt" name="ipadr" id="ipadr" readonly="readonly"  value="${ipadr}">						
								</td>
							</tr>
						</tbody>
					</table>
		
				
						<div class="restore_grp">				
								<div class="restore_lt">	
					
					<table class="write" >
						<colgroup>
							<col style="width:120px;" />
							<col style="width:65px;" />
							<col />
						</colgroup>
						<tbody>
							<tr>		
								<th scope="row" class="ico_t1">Storage 경로</th>					
								<td>
									<input type="radio" name="asis_flag" id="storage_path_org" value="0"  onClick="fn_storage_path_set();" checked> 기존
								</td>
								<td>
									<input type="radio" name="asis_flag" id="storage_path_new" value="1" onClick="fn_storage_path_set();"> 신규
								</td>
							</tr>
						</tbody>
					</table>
								
					<table class="write" id="storage_view">
						<colgroup>
							<col style="width:80px;" />
							<col />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">복구경로</th>
								<td><input type="text" class="txt" name="restore_dir" id="restore_dir" />
								<span class="btn btnC_01"><button type="button" class= "btn_type_02" onclick="fn_new_storage_check()" style="width: 50px; margin-right: -60px; margin-top: 0;">확인</button></span>
								</td>
							</tr>
						</tbody>
					</table>
	
	
					<table class="write" style="border:1px solid #b8c3c6; margin-top: 20px; border-collapse: separate;">
						<colgroup>
							<col style="width:130px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />						
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">시점선택</th>
								<td><button type="button" class= "btn_type_02" onclick="fn_rmanShow();" style="width: 80px; height:25px; margin-right: -60px; margin-top: 0;">복구정보</button></td>						
							</tr>
							<tr>
								<td>
									<span id="calendar">
											<div class="calendar_area">
												<a href="#n" class="calendar_btn">달력열기</a>
												<input type="text" class="calendar" id="datepicker1" name="timeline_dt" title="스케줄시간설정" readonly />
											</div>
									</span>
								</td>
								<td>
									<span id="hour"></span>
								</td>
								<td>
									<span id="min"></span>
								</td>
								<td>
									<span id="sec"></span>
								</td>
							</tr>
						</tbody>
					</table>
					
												
	
					<table class="write" style="border:1px solid #b8c3c6;padding:10px; margin-top: 20px;">
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">Database Storage</th>							
							</tr>
							<tr>
								<td><input type="text" class="txt" name="dtb_pth" id="dtb_pth" style="width: 99%;" readonly="readonly" value="${pgdata}"  />
							</tr>
						</tbody>
					</table>
					
					<table class="write" style="border:1px solid #b8c3c6;padding:10px; margin-top: 20px;">
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">Archive WAL Storage</th>							
							</tr>
							<tr>
								<td><input type="text" class="txt" name="pgalog_pth" id="pgalog_pth" style="width: 99%;" readonly="readonly" value="${pgalog}"  />
							</tr>
						</tbody>
					</table>
					
					<table class="write" style="border:1px solid #b8c3c6;padding:10px; margin-top: 20px;">
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">Server Log Storage</th>							
							</tr>
							<tr>
								<td><input type="text" class="txt" name="svrlog_pth" id="svrlog_pth" style="width: 99%;" readonly="readonly" value="${srvlog}"/>
							</tr>
						</tbody>
					</table>
					
					<table class="write" style="border:1px solid #b8c3c6;padding:10px; margin-top: 20px;">
						<tbody>
							<tr>
								<th scope="row" class="ico_t1">Backup Storage</th>							
							</tr>
							<tr>
								<td><input type="text" class="txt" name="bck_pth" id="bck_pth" style="width: 99%;" readonly="readonly" value="${pgrbak}"/>
							</tr>
						</tbody>
					</table>
					</div>
								
								
				<div class="restore_rt">
						<p class="ly_tit"><h8>Restore 실행 로그</h8></p>								
						<div class="overflow_area4" name="exelog"  id="exelog"></div>
				</div>
		</div>
	</div>
</div>
<!-- // contents -->