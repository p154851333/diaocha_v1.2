package com.ec.survey.ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ec.survey.dao.DAOFactory;
import com.ec.survey.dao.UserDAO;
import com.ec.survey.dto.User;
import com.swufe.util.StringUtil;

public class UserManageAction extends BaseAction {
	public String addUser(){
		String email = request.getParameter("email");
		String nickname = StringUtil.encodeString(request.getParameter("nickname"));
		String password = request.getParameter("password");
		UserDAO dao = DAOFactory.getUserDAO();
		User user = new User();
		user.setEmail(email);
		user.setNickname(nickname);
		user.setPassword(password);
		try {
			dao.addUser(user);
			request.getSession().setAttribute("user", user);
			//response.sendRedirect("../OpResult.jsp?op=reg&ret=true");
			return "success";
		} catch (Exception e) {
		//	response.sendRedirect("../OpResult.jsp?op=reg&ret=false");
			return "fail";
		}
	}
	
	private String login;
	private String pwd;
	private String code1;
	private String code2;
	public void userLogin() throws IOException{
		UserDAO dao = DAOFactory.getUserDAO();
		User user;
		try {
			user = dao.findOneUserByEmail(login);
			if (user==null) {
				user = dao.findOneUserByNickname(login);
			}
			if ((user.getPassword().equals(pwd))&&(code1.equalsIgnoreCase(code2))) {
				request.getSession().setAttribute("user", user);
				//response.sendRedirect("../OpResult.jsp?op=login&ret=true");
				String ok = "{\"Status\":\"ok\",\"Text\":\"登陆成功<br /><br />欢迎回来\"}";
				ServletActionContext.getResponse().getWriter().write(ok);
				
				//return "success";
			} else {
				//response.sendRedirect("../OpResult.jsp?op=login&ret=false");
				System.out.println("err");
				String err = "{\"Status\":\"Erro\",\"Erro\":\"账号名或密码或验证码有误\"}";
				ServletActionContext.getResponse().getWriter().write(err);
				//return "fail";
			}
		} catch (Exception e) {
			//response.sendRedirect("../OpResult.jsp?op=login&ret=false");
			//return "fail";
			System.out.println("Exception");
			String err = "{\"Status\":\"Erro\",\"Erro\":\"账号名或密码或验证码有误\"}";
			ServletActionContext.getResponse().getWriter().write(err);
		}
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
}
