package seu.qz.qzserver.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录检查
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session域中的loginUser
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("loginUser");
        //根据loginUser对象是否存在，判断用户是否已登录
        if(userName == null){
            //若未登录，则转发请求到登录界面（视图映射由index.html到page-login.html
            request.setAttribute("msg", "没有权限，请先登录!");
            request.getRequestDispatcher("/index").forward(request, response);
            return false;
        }else{
            //若已登录，则拦截器放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
