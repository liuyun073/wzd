package com.liuyun.site.model.borrow;

import com.liuyun.site.domain.Reservation;

public class ReservationModel extends Reservation {
	private static final long serialVersionUID = 6227166783859660460L;
	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}