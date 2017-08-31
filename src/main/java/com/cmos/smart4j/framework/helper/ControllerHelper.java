package com.cmos.smart4j.framework.helper;

import com.cmos.smart4j.framework.annotation.Action;
import com.cmos.smart4j.framework.bean.Handler;
import com.cmos.smart4j.framework.bean.Request;
import com.cmos.smart4j.framework.utils.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ������������
 */
public final class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        // ��ȡ���е�Controller��
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            // ������ЩController��
            for (Class<?> controllerClass : controllerClassSet) {
                //��ȡController�ж���ķ���
                Method[] methods = controllerClass.getMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    //������ЩController�еķ���
                    for (Method method : methods) {
                        LOGGER.debug("controller method name is: " + method.getName());
                        // �жϵ�ǰ�����Ƿ����Actionע��
                        if (method.isAnnotationPresent(Action.class)) {
                            // ��Actionע���л�ȡURLӳ�����
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            LOGGER.debug("Action annotation value is: " + mapping);
                            // ��֤URLӳ�����
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    // ��ȡ����������·��
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    // ��ʼ�� Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ��ȡ Handler
     *
     * @param requestMethod ����ķ���
     * @param requestPath   �����·��
     * @return һ��Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
