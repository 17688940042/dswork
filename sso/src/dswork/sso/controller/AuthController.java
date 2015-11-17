/**
 * 获取用户信息
 */
package dswork.sso.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.core.util.EncryptUtil;
import dswork.sso.listener.SessionListener;
import dswork.sso.model.LoginUser;
import dswork.sso.service.AuthFactoryService;
import dswork.sso.service.TicketService;
import dswork.web.MyAuthCodeServlet;
import dswork.web.MyCookie;
import dswork.web.MyRequest;

@Controller
public class AuthController
{
	static Logger log = LoggerFactory.getLogger(AuthController.class.getName());
	@Autowired
	private AuthFactoryService service;

	/**
	 * 用户登录系统的入口
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		MyRequest req = new MyRequest(request);
		String serviceURL = req.getString("service", request.getContextPath() + "/ticket.jsp");
		if(log.isDebugEnabled())
		{
			log.debug(serviceURL);
		}
		MyCookie cookie = new MyCookie(request, response);
		String cookieTicket = cookie.getValue(SessionListener.COOKIETICKET);
		if(cookieTicket != null)// 有cookie存在
		{
			String account;
			account = TicketService.getAccountByTicket(cookieTicket);
			if(account != null)
			{
				// 无需登录，生成ticket给应用去登录
				response.sendRedirect(serviceURL += ((serviceURL.indexOf("?") != -1) ? "&ticket=" : "?ticket=") + TicketService.getOnceTicket(cookieTicket));
				return null;
			}
			removeLoginInfo(request, response);// 把相关信息删除
		}
		request.setAttribute("service", serviceURL);
		request.setAttribute("errorMsg", "");
		return "/login.jsp";
	}

	/**
	 * 用户登录系统的验证方法
	 */
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String account = req.getString("account").toLowerCase();
		String password = req.getString("password");
		String authcode = req.getString("authcode");
		String serviceURL = req.getString("service", request.getContextPath() + "/ticket.jsp");
		if(log.isDebugEnabled())
		{
			log.debug(serviceURL);
		}
		try
		{
			String msg = "用户名或密码错误！";
			String randcode = String.valueOf(request.getSession().getAttribute(MyAuthCodeServlet.SessionName_Randcode)).trim();
			if(randcode.equals("null") || randcode.equals(""))
			{
				msg = "验证码已过期";
			}
			else if(!randcode.toLowerCase().equals(authcode.toLowerCase()))
			{
				msg = "验证码输入错误,请重新输入";
			}
			else
			{
				request.getSession().setAttribute(dswork.web.MyAuthCodeServlet.SessionName_Randcode, "");// 对了再清除
				LoginUser user = service.getLoginUserByAccount(account);
				if(user != null)
				{
					if(user.getStatus() != 1)// Status:1允许，0禁止
					{
						msg = "用户已禁用，请联系管理员！";
					}
					else if((EncryptUtil.encryptMd5(user.getPassword()+authcode).equals(password)))
					{
						String ticket = putLoginInfo(request, response, user.getAccount());
						try
						{
							service.saveLogLogin(ticket, getClientIp(request), user.getAccount(), user.getName(), true);
						}
						catch(Exception logex)
						{
						}
						// 成功就跳到切换系统视图
						response.sendRedirect(serviceURL += ((serviceURL.indexOf("?") != -1) ? "&ticket=" : "?ticket=") + TicketService.getOnceTicket(ticket));
						return null;
					}
				}
			}
			// 失败则转回来
			request.setAttribute("account", account);
			request.setAttribute("service", serviceURL);
			request.setAttribute("errorMsg", msg);
			try
			{
				service.saveLogLogin("", getClientIp(request), account, "", false);
			}
			catch(Exception logex)
			{
			}
			return "/login.jsp";
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 用户登出系统
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			MyCookie cookie = new MyCookie(request, response);
			String ticket = String.valueOf(cookie.getValue(SessionListener.COOKIETICKET));
			service.saveLogLogout(String.valueOf(ticket), false, false);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		removeLoginInfo(request, response);// 试着删除
		MyRequest req = new MyRequest(request);
		String serviceURL = java.net.URLEncoder.encode(req.getString("service", request.getContextPath() + "/ticket.jsp"), "UTF-8");
		response.sendRedirect(request.getContextPath() + "/login?service=" + String.valueOf(serviceURL));
		return;
	}

	private String putLoginInfo(HttpServletRequest request, HttpServletResponse response, String account)
	{
		MyCookie cookie = new MyCookie(request, response);
		String ticket = String.valueOf(cookie.getValue(SessionListener.COOKIETICKET));
		TicketService.removeSession(ticket);// 删除
		ticket = TicketService.saveSession(account);
		cookie.addCookie(SessionListener.COOKIETICKET, ticket);// 更新
		return ticket;
	}

	private void removeLoginInfo(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		String ticket = String.valueOf(cookie.getValue(SessionListener.COOKIETICKET));
		TicketService.removeSession(ticket);// 删除
		cookie.delCookie(SessionListener.COOKIETICKET);
	}

	public static String getClientIp(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");
		if(ip != null && ip.length() > 0 && !"null".equalsIgnoreCase(ip) && !"unKnown".equalsIgnoreCase(ip))
		{
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if(index != -1)
			{
				return ip.substring(0, index);
			}
			else
			{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(ip != null && ip.length() > 0 && !"null".equalsIgnoreCase(ip) && !"unKnown".equalsIgnoreCase(ip))
		{
			return ip;
		}
		return request.getRemoteAddr();
	}
}