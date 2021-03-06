package com.k4m.dx.tcontrol.accesscontrol.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.k4m.dx.tcontrol.accesscontrol.service.AccessControlHistoryVO;
import com.k4m.dx.tcontrol.accesscontrol.service.AccessControlService;
import com.k4m.dx.tcontrol.accesscontrol.service.AccessControlVO;
import com.k4m.dx.tcontrol.accesscontrol.service.DbAutVO;
import com.k4m.dx.tcontrol.accesscontrol.service.DbIDbServerVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("AccessControlServiceImpl")
public class AccessControlServiceImpl extends EgovAbstractServiceImpl implements AccessControlService {

	@Resource(name = "accessControlDAO")
	private AccessControlDAO accessControlDAO;

	@Override
	public List<DbIDbServerVO> selectDatabaseList(DbAutVO dbAutVO) throws Exception {
		return accessControlDAO.selectDatabaseList(dbAutVO);
	}
	@Override
	public List<AccessControlVO> selectCodeMethod(String grp_cd) throws Exception {
		return accessControlDAO.selectCodeMethod(grp_cd);
	}
	
	@Override
	public List<AccessControlVO> selectCodeType(String grp_cd) throws Exception {
		return accessControlDAO.selectCodeType(grp_cd);
	}

	@Override
	public void deleteDbAccessControl(int db_svr_id) throws Exception {
		accessControlDAO.deleteDbAccessControl(db_svr_id);
	}

	@Override
	public void insertAccessControl(AccessControlVO accessControlVO) throws Exception {
		accessControlDAO.insertAccessControl(accessControlVO);
	}

	@Override
	public void insertAccessControlHistory(AccessControlHistoryVO accessControlHistoryVO) throws Exception {
		accessControlDAO.insertAccessControlHistory(accessControlHistoryVO);
		
	}

	@Override
	public List<AccessControlHistoryVO> selectLstmdfdtm(int db_svr_id) throws Exception {
		return accessControlDAO.selectLstmdfdtm(db_svr_id);
	}

	@Override
	public int selectCurrenthisrp() throws Exception{
		return accessControlDAO.selectCurrenthisrp();
	}

	@Override
	public List<AccessControlHistoryVO> selectAccessControlHistory(AccessControlHistoryVO accessControlHistoryVO)
			throws Exception {
		return accessControlDAO.selectAccessControlHistory(accessControlHistoryVO);
	}
	@Override
	public int selectCurrentCntrid() throws Exception {
		return accessControlDAO.selectCurrentCntrid();
	}





}
