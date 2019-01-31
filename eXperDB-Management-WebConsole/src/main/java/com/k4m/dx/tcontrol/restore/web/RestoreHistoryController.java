package com.k4m.dx.tcontrol.restore.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.k4m.dx.tcontrol.admin.accesshistory.service.AccessHistoryService;
import com.k4m.dx.tcontrol.admin.menuauthority.service.MenuAuthorityService;
import com.k4m.dx.tcontrol.backup.service.BackupService;
import com.k4m.dx.tcontrol.backup.service.WorkLogVO;
import com.k4m.dx.tcontrol.backup.service.WorkVO;
import com.k4m.dx.tcontrol.cmmn.CmmnUtils;
import com.k4m.dx.tcontrol.common.service.HistoryVO;
import com.k4m.dx.tcontrol.restore.service.RestoreRmanVO;
import com.k4m.dx.tcontrol.restore.service.RestoreService;

/**
 * [Restore] DB  복구이력 클래스를 정의한다.
 *
 * @author 변승우
 * @see
 * 
 *      <pre>
 * == 개정이력(Modification Information) ==
 *
 *   수정일       수정자           수정내용
 *  -------     --------    ---------------------------
 *  2019.01.09   변승우 최초 생성
 *      </pre>
 */

@Controller
public class RestoreHistoryController {
	
	@Autowired
	private BackupService backupService;
	
	@Autowired
	private MenuAuthorityService menuAuthorityService;
	
	@Autowired
	private AccessHistoryService accessHistoryService;

	@Autowired
	private RestoreService restoreService;
	
	private List<Map<String, Object>> menuAut;
	
	
	/**
	 * [Restore] 복구이력 화면을 보여준다.
	 * 
	 * @param historyVO
	 * @param request
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/restoreHistory.do")
	public ModelAndView restoreHistory(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request, @ModelAttribute("workVo") WorkVO workVO) {
		
		//해당메뉴 권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN000303");
		
		ModelAndView mv = new ModelAndView();
		try {
			//읽기 권한이 없는경우 에러페이지 호출 [추후 Exception 처리예정]
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			}else{
				// 화면접근이력 이력 남기기
				CmmnUtils.saveHistory(request, historyVO);
				historyVO.setExe_dtl_cd("DX-T0009");
				historyVO.setMnu_id(11);
				accessHistoryService.insertHistory(historyVO);
				
				mv.addObject("read_aut_yn", menuAut.get(0).get("read_aut_yn"));
				mv.addObject("wrt_aut_yn", menuAut.get(0).get("wrt_aut_yn"));
				mv.setViewName("restore/restoreHistory");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Get DBServer Name
		try {
			mv.addObject("db_svr_nm", backupService.selectDbSvrNm(workVO).getDb_svr_nm());
			mv.addObject("dbList",backupService.selectDbList(workVO));
			mv.addObject("db_svr_id",workVO.getDb_svr_id());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	
	
	/**
	 * Rman restore Log List
	 * @param WorkLogVO
	 * @return List<WorkLogVO>
	 * @throws Exception
	 */
	@RequestMapping(value="/rmanRestoreHistory.do")
	@ResponseBody
	public List<RestoreRmanVO> rmanRestoreHistory(@ModelAttribute("restoreRmanVO") RestoreRmanVO restoreRmanVO, HttpServletRequest request, @ModelAttribute("historyVO") HistoryVO historyVO) throws Exception{
		
		// 화면접근이력 이력 남기기
		try {
		/*	CmmnUtils.saveHistory(request, historyVO);
			if(RestoreRmanVO.getBck_bsn_dscd().equals("TC000201")){
				historyVO.setExe_dtl_cd("DX-T0026_01");
			}else{
				historyVO.setExe_dtl_cd("DX-T0026_02");
			}
			accessHistoryService.insertHistory(historyVO);*/
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
				
		List<RestoreRmanVO> resultSet = null;
		resultSet = restoreService.rmanRestoreHistory(restoreRmanVO);

		return resultSet;
	}
}