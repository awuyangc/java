package com.wy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wy.dao.AccessTokenMapper;
import com.wy.dao.JsapiTicketMapper;
import com.wy.model.AccessToken;
import com.wy.model.JsapiTicket;
import com.wy.service.IAccessTokenServie;
import com.wy.service.IJsapiTicketServie;

@Service("jsapiTicketService")
public class JsapiTicketServiceImpl implements IJsapiTicketServie {
	
	@Resource
	private JsapiTicketMapper jsapiTicketMapper;
	
	@Override
	public JsapiTicket getJsapiTicketById(Integer accessTokenId) {
		// TODO Auto-generated method stub
		return jsapiTicketMapper.selectByPrimaryKey(accessTokenId);
	}

	@Override
	public List<JsapiTicket> getJsapiTicketList() {
		// TODO Auto-generated method stub
		return jsapiTicketMapper.selectAll();
	}

	@Override
	public void insert(JsapiTicket jsapiTicket) {
		// TODO Auto-generated method stub
		jsapiTicketMapper.insert(jsapiTicket);
	}

	@Override
	public void update(JsapiTicket jsapiTicket) {
		// TODO Auto-generated method stub
		jsapiTicketMapper.updateByPrimaryKey(jsapiTicket);
	}

	@Override
	public void delete(JsapiTicket jsapiTicket) {
		// TODO Auto-generated method stub
		jsapiTicketMapper.deleteByPrimaryKey(jsapiTicket.getId());
	}

	@Override
	public void deleteByJsapiTicketId(Integer jsapiTicketId) {
		// TODO Auto-generated method stub
		jsapiTicketMapper.deleteByPrimaryKey(jsapiTicketId);
	}

}
