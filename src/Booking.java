import java.sql.Date;

public class Booking {

	private Date initialTime;
	private Date endTime;
	private User user;
	private Room room;
	private int bookingId;
	
	public Date getInitialTime() {
		return initialTime;
	}
	public void setInitialTime(Date initialTime) {
		this.initialTime = initialTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
}
