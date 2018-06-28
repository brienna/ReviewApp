
public class Row {
	private int reviewId;
	private String appName;
	private String versionNum;
	private String userName;
	private String date;
	private int rating;
	private String title;
	private String text;
	private String infoGiv;
	private String infoSeek;
	private String featRequest;
	private String hasBug;
	private String sentScore;
	
	public Row(int _reviewId, String _appName, String _versionNum, String _userName, String _date, int _rating, String _title, String _text, String _infoGiv, String _infoSeek, String _featRequest, String _hasBug, String _sentScore) {
		reviewId = _reviewId;
		appName = _appName;
		versionNum = _versionNum;
		userName = _userName;
		date = _date;
		rating=_rating;
		title=_title;
		text = _text;
		infoGiv = _infoGiv;
		infoSeek = _infoSeek;
		featRequest = _featRequest;
		hasBug = _hasBug;
		sentScore =_sentScore;
	}
	public int getReviewId() {
		return reviewId;
	}
	public String getAppName() {
		return appName;
	}

	public String versionNum() {
		return versionNum;
	}

	public String getText() {
		return text;
	}

	public String getInfoGiv() {
		return infoGiv;
	}

	public String getInfoSeek() {
		return infoSeek;
	}

	public String getFeatRequest() {
		return featRequest;
	}

	public String getHasBug() {
		return hasBug;
	}

	public String getSentScore() {
		return sentScore;
	}


}
