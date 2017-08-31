package com.k4m.dx.tcontrol.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.k4m.dx.tcontrol.db.SqlSessionManager;
import com.k4m.dx.tcontrol.db.repository.service.SystemServiceImpl;
import com.k4m.dx.tcontrol.socket.ErrCodeMng;
import com.k4m.dx.tcontrol.socket.ProtocolID;
import com.k4m.dx.tcontrol.socket.SocketCtl;
import com.k4m.dx.tcontrol.socket.TranCodeType;

/**
 * BottledWater 실행/종료
 *
 * @author 박태혁
 * @see <pre>
 * == 개정이력(Modification Information) ==
 *
 *   수정일       수정자           수정내용
 *  -------     --------    ---------------------------
 *  2017.05.22   박태혁 최초 생성
 * </pre>
 */

public class DxT013 extends SocketCtl{
	
	private static Logger errLogger = LoggerFactory.getLogger("errorToFile");
	private static Logger socketLogger = LoggerFactory.getLogger("socketLogger");
	
	public DxT013(Socket socket, BufferedInputStream is, BufferedOutputStream	os) {
		this.client = socket;
		this.is = is;
		this.os = os;
	}

	public void execute(String strDxExCode, JSONObject jObj) throws Exception {
		byte[] sendBuff = null;
		String strErrCode = "";
		String strErrMsg = "";
		String strSuccessCode = "0";

		
		String execTxt = (String) jObj.get(ProtocolID.EXEC_TXT);
		String commandCode = (String) jObj.get(ProtocolID.COMMAND_CODE);
		String trfTrgId = (String) jObj.get(ProtocolID.TRF_TRG_ID);
		
		ApplicationContext context;

		context = new ClassPathXmlApplicationContext(new String[] {"context-tcontrol.xml"});
		SystemServiceImpl service = (SystemServiceImpl) context.getBean("SystemService");
		
	
		
		JSONObject outputObj = new JSONObject();
		
		try {
			
			if(commandCode.equals(ProtocolID.RUN)) {

				shellCmd(execTxt);
				
			} else if(commandCode.equals(ProtocolID.STOP)) {
				String strCmd = "ps -ef| grep bottledwater |grep " + execTxt + " | awk '{print $2}'";
				String strPid = getPidExec(strCmd);
				
				String strStopCmd = "kill -9 " + strPid ;
				shellCmd(strStopCmd);
				
				deleteSlot(strDxExCode, jObj, execTxt);
				
				String strDeleteSlotCmd = "rm -rf /tmp/bw_" + execTxt + ".pid";
				shellCmd(strDeleteSlotCmd);
				
				//service.updateT_TRFTRGCNG_I(vo);
			} 
			
			
			outputObj = DxT013ResultJSON(strDxExCode, strSuccessCode, strErrCode, strErrMsg);
	        
	        sendBuff = outputObj.toString().getBytes();
	        send(4, sendBuff);

			
		} catch (Exception e) {
			errLogger.error("DxT013 {} ", e.toString());
			
			outputObj.put(ProtocolID.DX_EX_CODE, TranCodeType.DxT013);
			outputObj.put(ProtocolID.RESULT_CODE, "1");
			outputObj.put(ProtocolID.ERR_CODE, TranCodeType.DxT013);
			outputObj.put(ProtocolID.ERR_MSG, "DxT013 Error [" + e.toString() + "]");
			
			sendBuff = outputObj.toString().getBytes();
			send(4, sendBuff);
			
		} finally {
		}	

	}
	
	   public static String shellCmd(String command) throws Exception {
		   
		   //ps -ef| grep bottledwater |grep test25 | awk '{print $2}'

		   String strResult = "";

           Runtime runtime = Runtime.getRuntime();
           
           Process process = runtime.exec(new String[]{"/bin/sh", "-c", command});

           
          return strResult;
	   }
	   
	   
	   public static String getPidExec(String command) throws Exception {
		   
		   //ps -ef| grep bottledwater |grep test25 | awk '{print $2}'

		   String strResult = "";

           Runtime runtime = Runtime.getRuntime();

           Process process = runtime.exec(new String[]{"/bin/sh", "-c", command});
           
           strResult = getPid(process);

           
          return strResult;
	   }
	   
	   public static String  getPid(Process p) throws Exception{

		   StringBuffer sb = new StringBuffer();

		   BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
           String cl = null;
           while((cl=in.readLine())!=null){
               sb.append(cl);
               break;
           }

           in.close();

		   return sb.toString();
		}
	   
	   /**
	    * delete slot
	    * @param strDxExCode
	    * @param jObj
	    * @param strSlotName
	    * @throws Exception
	    */
		public void deleteSlot(String strDxExCode, JSONObject jObj, String strSlotName) throws Exception {
			byte[] sendBuff = null;
			String strErrCode = "";
			String strErrMsg = "";
			String strSuccessCode = "0";
			
			JSONObject objSERVER_INFO = (JSONObject) jObj.get(ProtocolID.SERVER_INFO);
			
			SqlSessionFactory sqlSessionFactory = null;

			sqlSessionFactory = SqlSessionManager.getInstance();
			
			String poolName = "" + objSERVER_INFO.get(ProtocolID.SERVER_NAME) + "_" + objSERVER_INFO.get(ProtocolID.DATABASE_NAME);
			
			Connection connDB = null;
			SqlSession sessDB = null;

			
			JSONObject outputObj = new JSONObject();
			
			try {
				
				SocketExt.setupDriverPool(objSERVER_INFO, poolName);

				try {
				//DB 컨넥션을 가져온다.
				connDB = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + poolName);
				sessDB = sqlSessionFactory.openSession(connDB);
				
				} catch(Exception e) {
					strErrCode += ErrCodeMng.Err001;
					strErrMsg += ErrCodeMng.Err001_Msg + " " + e.toString();
					strSuccessCode = "1";
				}
			
				HashMap hp = new HashMap();
				hp.put("SLOT_NAME", strSlotName);
				
				sessDB.selectOne("app.deleteSlot", hp);


				
			} catch (Exception e) {
				errLogger.error("DxT013 {} ", e.toString());
				
				outputObj.put(ProtocolID.DX_EX_CODE, TranCodeType.DxT013);
				outputObj.put(ProtocolID.RESULT_CODE, "1");
				outputObj.put(ProtocolID.ERR_CODE, TranCodeType.DxT013);
				outputObj.put(ProtocolID.ERR_MSG, "DxT002 Error [" + e.toString() + "]");
				
				sendBuff = outputObj.toString().getBytes();
				send(4, sendBuff);
				
			} finally {
				sessDB.close();
			}	        


		}
}
