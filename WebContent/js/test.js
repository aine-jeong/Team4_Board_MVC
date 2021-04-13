function reply_check() {
						var frm = document.reply;
						if (frm.reply_writer.value == "" || frm.reply_content.value == ""
							|| frm.reply_pwd.value == "") {
									alert("리플 내용, 작성자, 비밀번호를 모두 입력해야합니다.");
							return false;
						}
						
						var formData = {
								idx: frm.querySelector('input[name=idx]').value,
								userid: frm.querySelector('input[name=userid]').value,
								reply_writer: frm.querySelector('input[name=reply_writer]').value,
								reply_content: frm.querySelector('textarea[name=reply_content]').value,
								reply_pwd: frm.querySelector('input[name=reply_pwd]').value
						}
						
						runAjax("ReplyOk.do", formData);
						
					}
					
					function reply_del(frm) {
						//alert("del");
						//var frm = document.replyDel;
						//alert(frm);
						if (frm.delPwd.value == "") {
							alert("비밀번호를 입력하세요");
							frm.delPwd.focus();
							return false;
						}
								
						var formData = {
								no: frm.querySelector('input[name=no]').value,
								idx: frm.querySelector('input[name=idx]').value,
								delPwd: frm.querySelector('input[name=delPwd]').value,
						}
						
						
						runAjax("ReplyDeleteOk.do", formData);
						
					}
					
					function runAjax(url, formData) {
						$.ajax(
							{
								url: url,
								type: "POST",
								data: formData,
								dataType: "json",
								success: function(responseData) {
									
									$('#span-reply').empty();

									var whynot = "";
									$.each(responseData, function(index, reply) {
										whynot += setReply(reply);										
										
									});
									$('#span-reply').html(whynot);
								},
								error: function(xhr) {
									console.log("here");
								}
							}		
						)
					}
					
					
					function setReply(reply) {
						return `<table width="80%" border="1">
										<tr>
											<th colspan="2">REPLY LIST</th>
										</tr>
										<tr align="left">
											<td width="80%">
											[${reply.writer}] : ${reply.content}
											<br> 작성일:${reply.writedate}
											</td>
											<td width="20%">
											<form action="ReplyDeleteOk.do" method="POST" name="replyDel">
												<input type="hidden" name="no" value="${reply.no}"> 
												<input type="hidden" name="idx" value="${reply.idx_fk}"> 
												password :<input type="password" name="delPwd" size="4"> 
												<input type="button" value="삭제" onclick="reply_del(this.form)">
											</form>
											</td>
										</tr>
									</table>`;
					}
					
					