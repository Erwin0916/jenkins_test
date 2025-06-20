package com.angkorchat.emoji.cms.global.config.servletFilter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

//향후 Response의 내용을 수정 하기 위해서 사용
public class IncaResponseRequestWrapper extends HttpServletResponseWrapper{

	public IncaResponseRequestWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}
}
