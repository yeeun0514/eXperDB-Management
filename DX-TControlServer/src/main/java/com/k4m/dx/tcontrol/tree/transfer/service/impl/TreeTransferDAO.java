package com.k4m.dx.tcontrol.tree.transfer.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.k4m.dx.tcontrol.accesscontrol.service.DbIDbServerVO;
import com.k4m.dx.tcontrol.tree.transfer.service.TransferDetailVO;
import com.k4m.dx.tcontrol.tree.transfer.service.TransferRelationVO;
import com.k4m.dx.tcontrol.tree.transfer.service.TransferTargetVO;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("TreeTransferDAO")
public class TreeTransferDAO extends EgovAbstractMapper {

	/**
	 * 전송대상 등록
	 * 
	 * @param transferTargetVO
	 * @throws SQLException
	 */
	public void insertTransferTarget(TransferTargetVO transferTargetVO) throws SQLException {
		insert("treeTransferSql.insertTransferTarget", transferTargetVO);

	}

	/**
	 * 전송대상 전체 삭제
	 * 
	 * @param cnr_id
	 * @throws SQLException
	 */
	public void deleteTransferTarget(int cnr_id) throws SQLException {
		delete("treeTransferSql.deleteTransferTarget", cnr_id);
	}

	/**
	 * 전송상세설정 조회
	 * 
	 * @param transferDetailVO
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<TransferDetailVO> selectTransferDetail(TransferDetailVO transferDetailVO) throws SQLException {
		List<TransferDetailVO> result = null;
		result = (List<TransferDetailVO>) list("treeTransferSql.selectTransferDetail", transferDetailVO);
		return result;
	}

	/**
	 * 데이터베이스 조회
	 * 
	 * @param db_svr_nm
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DbIDbServerVO> selectServerDbList(String db_svr_nm) throws SQLException {
		List<DbIDbServerVO> result = null;
		result = (List<DbIDbServerVO>) list("treeTransferSql.selectServerDbList", db_svr_nm);
		return result;
	}

	/**
	 * DB,SERVER 조회
	 * 
	 * @param db_id
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<DbIDbServerVO> selectServerDb(int db_id) throws SQLException {
		List<DbIDbServerVO> result = null;
		result = (List<DbIDbServerVO>) list("treeTransferSql.selectServerDb", db_id);
		return result;
	}

	/**
	 * 전송대상매핑관계 등록
	 * 
	 * @param transferRelationVO
	 * @throws SQLException
	 */
	public void insertTransferRelation(TransferRelationVO transferRelationVO) {
		insert("treeTransferSql.insertTransferRelation", transferRelationVO);

	}
}
