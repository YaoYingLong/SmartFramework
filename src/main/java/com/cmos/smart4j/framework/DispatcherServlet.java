package com.cmos.smart4j.framework;

import com.cmos.smart4j.framework.bean.Data;
import com.cmos.smart4j.framework.bean.Handler;
import com.cmos.smart4j.framework.bean.Param;
import com.cmos.smart4j.framework.bean.View;
import com.cmos.smart4j.framework.helper.*;
import com.cmos.smart4j.framework.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("-----DispatcherServlet init method is start-----");
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();

        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getProperty(ConfigConstant.APP_JSP_PATH) + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getProperty(ConfigConstant.APP_ASSET_PATH) + "*");

        UploadHelper.init(servletContext);
        LOGGER.info("-----DispatcherServlet init method is end-----");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("-----DispatcherServlet service method is start-----");

        ServletHelper.init(request, response);

        try {
            String requestMethod = request.getMethod().toLowerCase();
            String requestPath = request.getPathInfo();

            if (requestPath.equals("/favicon.ico")) {
                return;
            }

            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param;
                if (UploadHelper.isMultipart(request)) {
                    param = UploadHelper.createParam(request);
                } else {
                    param = RequestHelper.createParam(request);
                }
                Method actionMethod = handler.getActionMethod();
                int paramCount = actionMethod.getParameterCount();
                Object result;
                if (paramCount == 0) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                if (result instanceof View) {
                    handleViewResult((View) result, request, response);
                } else if (result instanceof Data) {
                    handleDataResult((Data) result, request, response);
                }
            }
        } finally {
            ServletHelper.destory();
        }
        LOGGER.info("-----DispatcherServlet service method is end-----");
    }

    private void handleViewResult(View result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        View view = (View) result;
        String path = view.getPath();
        LOGGER.debug("View path is: " + path);
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                String targetUrl = ConfigHelper.getProperty(ConfigConstant.APP_JSP_PATH) + path;
                LOGGER.debug("targetUrl path is: " + targetUrl);
                request.getRequestDispatcher(targetUrl).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private Map<String, Object> buildMap(HttpServletRequest request) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        LOGGER.debug("Request input stream body is: " + body);
        if (StringUtils.isNotEmpty(body)) {
            String[] params = StringUtil.splitString(body, "&");
            if (ArrayUtil.isNotEmpty(params)) {
                for (String param : params) {
                    String[] array = StringUtil.splitString(param, "=");
                    if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                        String paramName = array[0];
                        String paramValue = array[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
        }
        return paramMap;
    }


}
