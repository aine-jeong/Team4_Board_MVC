package kr.or.bit.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Reply;
import kr.or.bit.utils.StringUtils;

public class ReplyDeleteOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String idx_fk = request.getParameter("idx");// 댓글의 원본 게시글 번호
		String no = request.getParameter("no");// 댓글의 순번(PK)
		String pwd = request.getParameter("delPwd");// 댓글의 암호

		String msg = "";
		String url = "";

		if (idx_fk == null || no == null || pwd == null || no.trim().equals("")) {
			msg = "이상한 링크 입니다.";
			url = "BoardContent.do?idx=" + idx_fk;
		} else {
			
			
			
			try {
				BoardDao dao = new BoardDao();

				int result = dao.replyDelete(no, pwd);
				
				if (result > 0) {
					msg = "댓글 삭제 성공";
					url = "BoardContent.do?idx=" + idx_fk;
					List<Reply> replyList = new ArrayList<Reply>();
					replyList = dao.replylist(idx_fk);
			    	
			    	StringUtils utils = new StringUtils();
			    	String parsed = utils.listParseToJavascriptArray(replyList, new Reply());
			    	PrintWriter out = response.getWriter();
			    	out.print(parsed);
					
				} else {
					msg = "댓글 삭제 실패";
					url = "BoardContent.do?idx=" + idx_fk;
				}

			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		request.setAttribute("board_msg", msg);
		request.setAttribute("board_url", url);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/views/board/redirect.jsp");

		return null;
	}

}