package group11project.demo.configurations.intercepors;

import group11project.demo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceport implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");

        if(user == null){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }

        System.out.println(session.getAttribute("id"));
        return true;
    }
}
