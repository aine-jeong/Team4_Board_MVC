package kr.or.bit.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Reply;
import kr.or.bit.utils.StringUtils;
import net.sf.json.JSONArray;

public class ReplyAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String writer = request.getParameter("reply_writer");
		String content = request.getParameter("reply_content");
		String pwd = request.getParameter("reply_pwd");
		String idx = request.getParameter("idx");
		String userid = "empty";
		
		//Reply reply = new Reply();
		String msg="";
	    String url="";
		
	    List<Reply> replyList = null;
	    
		try {
			BoardDao dao = new BoardDao();
			int idx_fk = Integer.parseInt(idx);
			
			/*
			reply.setWriter(writer);
			reply.setContent(content);
			reply.setPwd(pwd);
			reply.setIdx_fk(idx_fk);
			reply.setUserid(userid);
			*/
			
			int result = dao.replywrite(idx_fk, writer, userid, content, pwd);
			
			
			if(result > 0){
		    	msg ="댓글 입력 성공";
		    	url ="BoardContent.do?idx="+idx_fk;
		    	
		    	replyList = dao.replylist(idx);
		    	
		    	StringUtils utils = new StringUtils();
		    	String parsed = utils.listParseToJavascriptArray(replyList, new Reply());
		    	PrintWriter out = response.getWriter();
		    	out.print(parsed);
		    	
//              JSONArray parsedJson = JSONArray.fromObject(replyList);
//             PrintWriter out = response.getWriter();
//             out.print(parsedJson);
		    	
//		    	response.setCharacterEncoding("utf-8");
//                PrintWriter out = response.getWriter();
//		    	
//		    	for(Reply reply : replyList) {
//		    	    out.print("<table width=\"80%\" border=\"1\">");
//	                out.print("<tr>");
//	                out.print("<th colspan=\"2\">REPLY LIST</th>");
//	                out.print("</tr>");
//	                out.print("<tr align=\"left\">");
//	                out.print("<td width=\"80%\">");
//	                out.print("[" + reply.getWriter() + "] : " + reply.getContent());
//	                out.print("<br> 작성일:" + reply.getWritedate());
//	                out.print("</td>");
//	                out.print("<td width=\"20%\">");
//	                out.print("<form action=\"ReplyDeleteOk.do\" method=\"POST\" name=\"replyDel\">");
//	                out.print("<input type=\"hidden\" name=\"no\" value=\"" + reply.getNo() + "\">");
//	                out.print("<input type=\"hidden\" name=\"idx\" value=\"" + idx + "\">");
//	                out.print("password :<input type=\"password\" name=\"delPwd\" size=\"4\">");
//	                out.print("<input type=\"button\" value=\"삭제\" onclick=\"reply_del(this.form)\">");
//	                out.print("</form>");
//	                out.print("</td>");
//	                out.print("</tr>");
//	                out.print("</table>");
//		    	}
		    	
		    }else{
		    	msg="댓글 입력 실패";
		    	url="BoardContent.do?idx="+idx_fk;
		    }
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		request.setAttribute("board_msg", msg);
		request.setAttribute("board_url", url);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/views/board/redirect.jsp");

		return null;
	}

}
