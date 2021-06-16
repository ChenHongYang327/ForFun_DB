package member.bean;

import java.sql.Timestamp;

public class Area {

	private Integer areaId;
	private Integer cityId;
	private String areaName;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp deleteTime;

	public Area(Integer areaId, Integer cityId, String areaName, Timestamp createTime, Timestamp updateYime,
			Timestamp deleteTime) {
		super();
		this.areaId = areaId;
		this.cityId = cityId;
		this.areaName = areaName;
		this.createTime = createTime;
		this.updateTime = updateYime;
		this.deleteTime = deleteTime;
	}

	public Area() {

	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateYime() {
		return updateTime;
	}

	public void setUpdateYime(Timestamp updateYime) {
		this.updateTime = updateYime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

}
