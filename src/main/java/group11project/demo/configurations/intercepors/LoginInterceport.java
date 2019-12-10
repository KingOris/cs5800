package group11project.demo.configurations.intercepors;

import group11project.demo.annotation.onlyadmin;
import group11project.demo.entity.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class LoginInterceport implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("id");

        if(user == null){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }

        boolean haveAnnotataion = handler.getClass().isAssignableFrom(HandlerMethod.class);
        if (haveAnnotataion) {
            onlyadmin oa = ((HandlerMethod)handler).getMethodAnnotation(onlyadmin.class);
            if (oa != null) {
                if (user.isAdmin() != true) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/json;charset=utf-8");
                    PrintWriter pw = response.getWriter();
                    pw.flush();
                    pw.println("{\"msg\":\"Only Admin can visit this\"}");
                    return false;
                }
            }
        }
        return true;
    }
}
