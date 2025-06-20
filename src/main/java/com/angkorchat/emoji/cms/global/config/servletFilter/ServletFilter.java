package com.angkorchat.emoji.cms.global.config.servletFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import com.angkorchat.emoji.cms.global.error.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component("ServletFilterBean")
public class ServletFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(ServletFilter.class);

	/*
	 * 필터 예외 URI
	 */
	List<String> whiteReqs = null;
	List<String> staticResources = null;

	private static final String URI_ROOT = "/";
	private static final String URI_LOGIN = "/login";
	private static final String URI_LOGOUT = "/logout";
	private static final String URI_RESOURCE = "/resources/**";

	/*
	 * 서블릿에서 Open 할경우 무조건 사용자에게 서비스 한다.
	 */

	public ServletFilter() {

		whiteReqs = new ArrayList<>();
		
		whiteReqs.add(URI_ROOT); // 파비콘 요청
		whiteReqs.add(URI_LOGIN); // 파비콘 요청
		whiteReqs.add("/index"); // 파비콘 요청
		whiteReqs.add("/dashboard"); // 파비콘 요청

		staticResources = new ArrayList<>();
		staticResources.add(URI_RESOURCE);

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		Enumeration<String> hname = req.getHeaderNames();

		// URI 정보 로그
		log.info("URI 정보 : " + req.getMethod() + " " + req.getRequestURI());

		// Header 정보 로그
		while (hname.hasMoreElements()) {
			String name = (String) hname.nextElement();
			String value = req.getHeader(name);

			log.info("Header 정보 :" + name + " : " + value);

		}

		// Parameter 정보 로그
		Enumeration<String> parameterNames = req.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			String value = req.getParameter(name);

			log.info("Parameter 정보 : {} = {}", name, value);
		}

		// Body 정보 로그
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int byteRead;
		try (ServletInputStream in = req.getInputStream()) {
			while ((byteRead = in.read(buf)) != -1) {
				byteArrayOutputStream.write(buf, 0, byteRead);
			}
		} catch (IOException e) {
			log.info("Body 정보 확인 실패");
		}
		byte[] bodyArray = byteArrayOutputStream.toByteArray();
		if (bodyArray.length > 0) {
			String body = new String(bodyArray, StandardCharsets.UTF_8);

			log.info("Body 정보 : {}", body);
		}

		// 읽어드린 리퀘스트 정보를 다시 사용할 수 있는 상태로 재포장
		HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(req) {
			@Override
			public ServletInputStream getInputStream() throws IOException {
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyArray);

				return new ServletInputStream() {
					@Override
					public boolean isFinished() {
						return byteArrayInputStream.available() == 0;
					}

					@Override
					public boolean isReady() {
						return false;
					}

					@Override
					public void setReadListener(ReadListener readListener) {
					}

					@Override
					public int read() throws IOException {
						return byteArrayInputStream.read();
					}
				};
			}
		};

		IncaResponseRequestWrapper myres = new IncaResponseRequestWrapper(res);

		try {
			chain.doFilter(wrappedRequest, myres);
		} catch (IOException | ServletException e) {
			System.out.println(e.getLocalizedMessage());
			throw new BaseException("error");
		}
	}
	
	
	private boolean isWhiteList(String uri) {
		boolean isWhiteReq = false;
		
		if(uri.contains("/resources/") ||  uri.contains("/api")) {
			isWhiteReq = true;
			return isWhiteReq;
		}
		for (String whiteReq : whiteReqs) {
			if (uri.startsWith(whiteReq)) {
				isWhiteReq = true;
				break;
			}
		}
		
		return isWhiteReq;
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// FilterConfig를 통한 filter 설정
		// 서블릿 컨테이너가 필터 인스턴스 생성한 후 초기화 하기 위해서 사용 전 호출하는 메소드

	}

}
