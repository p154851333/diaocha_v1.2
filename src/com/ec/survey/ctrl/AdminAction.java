package com.ec.survey.ctrl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.ec.survey.dao.AdminDAO;
import com.ec.survey.dao.DAOFactory;
import com.ec.survey.dao.mssqlimpl.AdminDAOimpl;
import com.ec.survey.dto.Admin;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends BaseAction
{
	private int userId;
	private String userName;
	private String userPw;
	private String email;
	private String phone;
	private String message;
	private String path;
	private int index=1;
	private AdminDAO adminDAO=new AdminDAOimpl();
	
	
	private String login;
	private String pwd;
	private String code1;
	private String code2;
	//登录ajax验证。
	public void loginAdmin() throws IOException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		boolean ret=adminDAO.checkPwd(login,pwd);
		if((ret==true)&&(code1.equalsIgnoreCase(code2))){
			Admin admin=adminDAO.findAdmin(login);
			session.setAttribute("admin", admin);
			session.setAttribute("username",login.toLowerCase());
			session.setAttribute("aid",admin.getA_id());
			String ok = "{\"Status\":\"ok\",\"Text\":\"登陆成功<br /><br />欢迎回来\"}";
			ServletActionContext.getResponse().getWriter().write(ok);
		}else{
			String err = "{\"Status\":\"Erro\",\"Erro\":\"账号名或密码或验证码有误\"}";
			ServletActionContext.getResponse().getWriter().write(err);
		}
	}
	
	public String adminAdd()
	{
		Admin admin=new Admin();
		admin.setA_user(userName);
		admin.setA_pass(userPw);
		admin.setA_email(email);
		admin.setA_phone(phone);
		adminDAO.addAdmin(admin);
		this.setMessage("操作成功");
		this.setPath("adminManage.action");
		return "success";
	}
	public String adminManage()
	{
		/*List adminList=adminDAO.listAllAdmin();
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("adminList", adminList);*/
		return "success";
	}
	public String adminDel()
	{
		adminDAO.delAdmin(userId);
		this.setMessage("删除成功");
		this.setPath("adminManage.action");
		return "success";
	}
	
	public String EditAdmin() throws Exception{
		Long aid=Long.valueOf(request.getParameter("aid"));
		String oldpwd=request.getParameter("oldpwd");
		String pwd=request.getParameter("pwd");
		String username=request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		AdminDAO dao=DAOFactory.getAdminDAO();
			if(dao.checkPwd(username, oldpwd)!=true){
				this.message=URLEncoder.encode("原始密码错误,修改失败！", "UTF-8");
				return "fail";
			}
		Admin admin=dao.findAdmin(aid);
		admin.setA_email(email);
		admin.setA_phone(phone);
		admin.setA_pass(pwd);
		boolean ret1=dao.updateAdmin(admin);
		if(ret1){
			//response.sendRedirect("../admin/AdminList.jsp");
			return "success";
		}else{
			this.message=URLEncoder.encode("编辑管理员出错！请联系管理员！", "UTF-8");
			//response.sendRedirect("../admin/OpResult.jsp?op=default&ret=false&words="+URLEncoder.encode("编辑管理员出错！请联系管理员", "UTF-8"));
			return "fail";
		}
	}



	public String getMessage()
	{
		return message;
	}

	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserPw()
	{
		return userPw;
	}

	public void setUserPw(String userPw)
	{
		this.userPw = userPw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
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
