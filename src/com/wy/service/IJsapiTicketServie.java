package com.wy.service;

import java.util.List;
import com.wy.model.JsapiTicket;

public interface IJsapiTicketServie {
	public JsapiTicket getJsapiTicketById(Integer jsapiTicketId);
	public List<JsapiTicket> getJsapiTicketList();
	public void insert(JsapiTicket jsapiTicket);
	public void update(JsapiTicket jsapiTicket);
	public void delete(JsapiTicket jsapiTicket);
	public void deleteByJsapiTicketId(Integer jsapiTicketId);
}
