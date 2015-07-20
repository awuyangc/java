package com.wy.model;

import java.util.Date;

public class JsapiTicket {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_jsapi_ticket.id
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_jsapi_ticket.expires_in
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	private Integer expiresIn;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_jsapi_ticket.ticket_expires
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	private Long ticketExpires;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_jsapi_ticket.ticket_expiresCH
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	private Date ticketExpiresch;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column t_jsapi_ticket.ticket
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	private String ticket;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_jsapi_ticket.id
	 * @return  the value of t_jsapi_ticket.id
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_jsapi_ticket.id
	 * @param id  the value for t_jsapi_ticket.id
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_jsapi_ticket.expires_in
	 * @return  the value of t_jsapi_ticket.expires_in
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public Integer getExpiresIn() {
		return expiresIn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_jsapi_ticket.expires_in
	 * @param expiresIn  the value for t_jsapi_ticket.expires_in
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_jsapi_ticket.ticket_expires
	 * @return  the value of t_jsapi_ticket.ticket_expires
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public Long getTicketExpires() {
		return ticketExpires;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_jsapi_ticket.ticket_expires
	 * @param ticketExpires  the value for t_jsapi_ticket.ticket_expires
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public void setTicketExpires(Long ticketExpires) {
		this.ticketExpires = ticketExpires;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_jsapi_ticket.ticket_expiresCH
	 * @return  the value of t_jsapi_ticket.ticket_expiresCH
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public Date getTicketExpiresch() {
		return ticketExpiresch;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_jsapi_ticket.ticket_expiresCH
	 * @param ticketExpiresch  the value for t_jsapi_ticket.ticket_expiresCH
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public void setTicketExpiresch(Date ticketExpiresch) {
		this.ticketExpiresch = ticketExpiresch;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column t_jsapi_ticket.ticket
	 * @return  the value of t_jsapi_ticket.ticket
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column t_jsapi_ticket.ticket
	 * @param ticket  the value for t_jsapi_ticket.ticket
	 * @mbggenerated  Fri Jun 26 11:03:33 CST 2015
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}