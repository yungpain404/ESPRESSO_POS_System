package util;

import entity.TaiKhoan;

public class SessionManager {
	private static TaiKhoan currentUser;

	public static TaiKhoan getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(TaiKhoan user) {
		currentUser = user;
	}
	
	public static void logout() {
        currentUser = null;
    }
}
