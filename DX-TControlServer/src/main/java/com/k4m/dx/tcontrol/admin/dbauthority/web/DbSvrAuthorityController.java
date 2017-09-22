package com.k4m.dx.tcontrol.admin.dbauthority.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.k4m.dx.tcontrol.admin.accesshistory.service.AccessHistoryService;
import com.k4m.dx.tcontrol.admin.dbauthority.service.DbAuthorityService;
import com.k4m.dx.tcontrol.admin.menuauthority.service.MenuAuthorityService;
import com.k4m.dx.tcontrol.admin.usermanager.service.UserManagerService;
import com.k4m.dx.tcontrol.cmmn.CmmnUtils;
import com.k4m.dx.tcontrol.common.service.HistoryVO;
import com.k4m.dx.tcontrol.login.service.UserVO;

@Controller
public class DbSvrAuthorityController {
	
	@Autowired
	private MenuAuthorityService menuAuthorityService;
	
	@Autowired
	private AccessHistoryService accessHistoryService;

	@Autowired
	private UserManagerService userManagerService;
	
	@Autowired
	private DbAuthorityService dbAuthorityService;
	
	private List<Map<String, Object>> menuAut;
	
	
	/**
	 * DB서버 메뉴 권한관리 화면을 보여준다.
	 * 
	 * @param historyVO
	 * @param request
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/dbServerAuthority.do")
	public ModelAndView dbServerAuthority(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {
		
		//해당메뉴 권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN000502");
				
		ModelAndView mv = new ModelAndView();
		try {
			//읽기 권한이 없는경우 에러페이지 호출 [추후 Exception 처리예정]
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			}else{
				
				// DB권한관리 이력 남기기
				CmmnUtils.saveHistory(request, historyVO);
				historyVO.setExe_dtl_cd("DX-T0035");
				accessHistoryService.insertHistory(historyVO);
				
				mv.addObject("read_aut_yn", menuAut.get(0).get("read_aut_yn"));
				mv.addObject("wrt_aut_yn", menuAut.get(0).get("wrt_aut_yn"));
				mv.setViewName("admin/dbAuthority/dbServerAuthority");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;

	}
	
	
	/**
	 * 사용자 리스트를 조회한다.
	 * 
	 * @param request
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDBSvrAutUserManager.do")
	public @ResponseBody List<UserVO> selectDBSvrAutUserManager(HttpServletRequest request, HttpServletResponse response) {
		
		//해당메뉴 권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN000502");
				
		List<UserVO> resultSet = null;
		Map<String, Object> param = new HashMap<String, Object>();
		try {	
				//읽기 권한이 없는경우 에러페이지 호출 [추후 Exception 처리예정]
				if(menuAut.get(0).get("read_aut_yn").equals("N")){
					response.sendRedirect("/autError.do");
					return resultSet;
				}else{
					String type=request.getParameter("type");
					String search = request.getParameter("search");
					String use_yn = request.getParameter("use_yn");
								
					param.put("type", type);
					param.put("search", search);
					param.put("use_yn", use_yn);
				
					resultSet = userManagerService.selectUserManager(param);	
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;

	}
	
	
	
	/**
	 * 서버 권한정보를 조회한다.
	 * 
	 * @param request
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDBSrvAutInfo.do")
	public @ResponseBody List<Map<String, Object>> selectDBSrvAutInfo(HttpServletRequest request, HttpServletResponse response) {
		
		//해당메뉴 권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN000502");
		List<Map<String, Object>> resultSet = null;
		
		try {	
			//읽기 권한이 없는경우 에러페이지 호출 [추후 Exception 처리예정]
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				response.sendRedirect("/autError.do");
				return resultSet;
			}else{
				resultSet = dbAuthorityService.selectSvrList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;

	}
	
	
	/**
	 * 서버 권한정보를 조회한다.
	 * 
	 * @param request
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUsrDBSrvAutInfo.do")
	public @ResponseBody List<Map<String, Object>> selectUsrDBSrvAutInfo(HttpServletRequest request, HttpServletResponse response) {
		
		//해당메뉴 권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN000502");
				
		List<Map<String, Object>> resultSet = null;
		
		try {	
			//읽기 권한이 없는경우 에러페이지 호출 [추후 Exception 처리예정]
//			if(menuAut.get(0).get("read_aut_yn").equals("N")){
//				response.sendRedirect("/autError.do");
//				return resultSet;
//			}else{
				String usr_id ="";
				
				if(request.getParameter("usr_id") == null){
					usr_id = (String) request.getSession().getAttribute("usr_id");
				}else{
					usr_id = request.getParameter("usr_id");
				}
				
				resultSet = dbAuthorityService.selectUsrDBSrvAutInfo(usr_id);	
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	
	/**
	 * DB서버권한 업데이트
	 * 
	 * @param
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUsrDBSrvAutInfo.do")
	@ResponseBody
	public void updateUsrDBSrvAutInfo(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request, HttpServletResponse response) {
		
		//해당메뉴 권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN000502");
				
		int cnt = 0; 
		
		try {
			//쓰기 권한이 없는경우 에러페이지 호출 [추후 Exception 처리예정]
			if(menuAut.get(0).get("wrt_aut_yn").equals("N")){
				response.sendRedirect("/autError.do");
			}else{
				// DB권한관리 이력 남기기
				CmmnUtils.saveHistory(request, historyVO);
				historyVO.setExe_dtl_cd("DX-T0035_01");
				accessHistoryService.insertHistory(historyVO);
											
				String strRows = request.getParameter("datasArr").toString().replaceAll("&quot;", "\"");
				JSONArray rows = (JSONArray) new JSONParser().parse(strRows);
						
				for(int i=0; i<rows.size(); i++){
					cnt = dbAuthorityService.selectUsrDBSrvAutInfoCnt(rows.get(i));
					
					if(cnt==0){
						dbAuthorityService.insertUsrDBSrvAutInfo(rows.get(i));
					}else{
						dbAuthorityService.updateUsrDBSrvAutInfo(rows.get(i));
					}			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}