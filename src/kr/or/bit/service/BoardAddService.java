package kr.or.bit.service;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;
import kr.or.bit.utils.FileUpload;

public class BoardAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		MultipartRequest multi = FileUpload.getMulti(request);

		String subject = multi.getParameter("subject");
		String writer = multi.getParameter("writer");
		String email = multi.getParameter("email");
		String homepage = multi.getParameter("homepage");
		String content = multi.getParameter("content");
		String pwd = multi.getParameter("pwd");
		
		String filename = multi.getOriginalFileName((String)multi.getFileNames().nextElement());

		int result = 0;

		Board board = new Board();

		board.setSubject(subject);
		board.setWriter(writer);
		board.setEmail(email);
		board.setHomepage(homepage);
		board.setContent(content);
		board.setPwd(pwd);
		board.setFilename(filename);

		try {
			BoardDao dao = new BoardDao();

			result = dao.writeok(board);

		} catch (NamingException e) {
			e.printStackTrace();
		}

		// write.jsp 화면 >> writeok.jsp 처리 >> service >> dao > DB 작업 >
		// return dao > return service > writeok.jsp 결과처리 >> 이동 (공통) >> redirect.jsp

		String msg = "";
		String url = "";
		if (result > 0) {
			msg = "insert success";
			url = "BoardList.do";
		} else {
			msg = "insert fail";
			url = "BoardWrite.do";
		}

		request.setAttribute("board_msg", msg);
		request.setAttribute("board_url", url);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/views/board/redirect.jsp");

		return forward;

	}

}
