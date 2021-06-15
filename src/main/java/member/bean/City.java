package member.bean;

import java.sql.Timestamp;

public class City {

	private Integer cityId;
	private String cityName;
	private Timestamp createTime;
	private Timestamp updateYime;
	private Timestamp deleteTime;

	public City(Integer cityId, String cityName, Timestamp createTime, Timestamp updateYime, Timestamp deleteTime) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.createTime = createTime;
		this.updateYime = updateYime;
		this.deleteTime = deleteTime;
	}

	public City() {

	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateYime() {
		return updateYime;
	}

	public void setUpdateYime(Timestamp updateYime) {
		this.updateYime = updateYime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

}
