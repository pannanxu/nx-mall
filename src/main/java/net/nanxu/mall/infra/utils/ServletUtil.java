package net.nanxu.mall.infra.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

/**
 * @author: Pan
 **/
@Slf4j
public class ServletUtil {


    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }

    /**
     * 获取远程客户端IP地址
     *
     * @return IP
     */
    public static String getRemoteAddr() {
        return getRequest().getRemoteAddr();
    }

    public static void renderJson(HttpServletResponse response, String json) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readRequestBody() {
        HttpServletRequest request = getRequest();
        return readRequestBody(request);
    }

    public static String readRequestBody(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            log.error("Servlet|获取请求参数异常|{}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static void addHeader(String name, String value) {
        getResponse().addHeader(name, safeHttpHeader(value));
    }

    /**
     * 下载文件
     */
    public static void downloadFile(String fileName, InputStream in) {
        downloadFile(getRequest(), getResponse(), fileName, in);
    }

    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName, InputStream in) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", safeHttpHeader("attachment; filename=" + fileName));
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error("文件下载|异常|{}", e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * CRLF fix.
     */
    private static String safeHttpHeader(String value) {
        return StringUtils.trimToEmpty(value)
                .replace("\r", "")
                .replace("\n", "")
                .replace("\r\n", "")
                .replace("\t", "")
                .replace(" ", "")
                .replace("'", "")
                ;
    }

}
