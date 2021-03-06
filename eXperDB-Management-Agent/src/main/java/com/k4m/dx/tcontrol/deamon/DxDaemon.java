package com.k4m.dx.tcontrol.deamon;

/**
* @author 박태혁
* @see
* 
*      <pre>
* == 개정이력(Modification Information) ==
*
*   수정일       수정자           수정내용
*  -------     --------    ---------------------------
*  2018.04.23   박태혁 최초 생성
*      </pre>
*/

public interface DxDaemon {
	/**
	 * <p>실제로 실행할 코드가 들어가는 곳</p>
	 */
	public void startDaemon();

	/**
	 * <p>데몬이 종료될 때 실행할 구문들</p>
	 */
	public void shutdown();
	
	
	public void chkDir();

}