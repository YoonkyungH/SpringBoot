package practice.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 목표: 파라미터 전송 기능
 * http://localhost:8080/request-param?username=yoon&age=100&username=yoon2
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[전체 파라미터 조회 시작]");
        request.getParameterNames().asIterator().forEachRemaining(
                paramName -> System.out.println(paramName + "=" + request.getParameter(paramName))
                // 이 예제에서 paramName은 username,
                // request.getParameter(paramName)은 yoon
        );
        System.out.println("[전체 파라미터 조회 끝]");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");  // username이 yoon, yoon2 이렇게 두개인 상태
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }
    }
}
