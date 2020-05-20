package com.k4m.dx.tcontrol.scale.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.k4m.dx.tcontrol.scale.service.InstanceScaleVO;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
* @author 
* @see aws scale 관련 화면 dao
* 
*      <pre>
* == 개정이력(Modification Information) ==
*
*   수정일                 수정자                   수정내용
*  -------     --------    ---------------------------
*  2020.03.24              최초 생성
*      </pre>
*/
@Repository("instanceScaleDAO")
public class InstanceScaleDAO extends EgovAbstractMapper{
	
	/**
	 * scale log 조회
	 * 
	 * @param dataConfigVO
	 * @throws Exception
	 */
	public Map<String, Object> selectScaleLog(Map<String, Object> param) throws SQLException {
		return (Map<String, Object>) selectOne("instanceScaleSql.selectScaleLog", param);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map<String, Object>> selectSvrIpadrList(int db_svr_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("instanceScaleSql.selectSvrIpadrList", db_svr_id);
		return sl;
	}
	
	/**
	 * scale log list 조회
	 * 
	 * @param dataConfigVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map<String, Object>> selectScaleHistoryList(InstanceScaleVO instanceScaleVO) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("instanceScaleSql.selectScaleHistoryList", instanceScaleVO);		
		return sl;
	}
	
	/**
	 * scale 발생 log list 조회
	 * 
	 * @param dataConfigVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map<String, Object>> selectScaleOccurHistoryList(InstanceScaleVO instanceScaleVO) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("instanceScaleSql.selectScaleOccurHistoryList", instanceScaleVO);		
		return sl;
	}
	
	/**
	 * scale 실패 이력정보 조회
	 * 
	 * @param scale_wrk_sn
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Map<String, Object> selectScaleWrkErrorMsg(InstanceScaleVO instanceScaleVO) {
		return (Map<String, Object>) selectOne("instanceScaleSql.selectScaleWrkErrorMsg", instanceScaleVO);
	}
	
	/**
	 * scale 실행이력 상세정보 조회
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Map<String, Object> selectScaleWrkInfo(InstanceScaleVO instanceScaleVO) {
		return (Map<String, Object>) selectOne("instanceScaleSql.selectScaleWrkInfo", instanceScaleVO);
	}
	
	/**
	 * scale Auto 설정 list 조회
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectScaleCngList(InstanceScaleVO instanceScaleVO) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("instanceScaleSql.selectScaleCngList", instanceScaleVO);		
		return sl;
	}

	/**
	 * scale Auto 설정 중복검사
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	public int selectAutoScaleSetChk(InstanceScaleVO instanceScaleVO) {
		int resultSet = 0;
		resultSet = (int) getSqlSession().selectOne("instanceScaleSql.selectAutoScaleSetChk", instanceScaleVO);
		return resultSet;
	}
	
	/**
	 * scale Auto 설정 등록
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void insertAutoScaleSetting(InstanceScaleVO instanceScaleVO) {
		insert("instanceScaleSql.insertAutoScaleSetting", instanceScaleVO);	
	}
	
	/**
	 * scale Auto 설정 수정
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void updateAutoScaleSetting(InstanceScaleVO instanceScaleVO) {
		insert("instanceScaleSql.updateAutoScaleSetting", instanceScaleVO);	
	}
	
	/**
	 * scale 설정정보 상세정보 조회
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Map<String, Object> selectAutoScaleCngInfo(InstanceScaleVO instanceScaleVO) {
		return (Map<String, Object>) selectOne("instanceScaleSql.selectAutoScaleCngInfo", instanceScaleVO);
	}

	
	/**
	 * scale Auto 설정 삭제
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void deleteAutoScaleSetting(InstanceScaleVO instanceScaleVO) {
		insert("instanceScaleSql.deleteAutoScaleSetting", instanceScaleVO);	
	}
	
}