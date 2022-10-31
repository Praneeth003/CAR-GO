package com.cargobackend.pojo.dao.cargo;

public class AddOn {
	
	public enum AddOnComputeStrategy {
		PER_DAY,
		FIXED
	}

	Integer addOnId;
	String addOnName;
	String addOnDescription;
	AddOnComputeStrategy addOnComputeStrategy;
	Double addOnValue;
	
	public Integer getAddOnId() {
		return addOnId;
	}
	public void setAddOnId(Integer addOnId) {
		this.addOnId = addOnId;
	}
	public String getAddOnName() {
		return addOnName;
	}
	public void setAddOnName(String addOnName) {
		this.addOnName = addOnName;
	}
	public String getAddOnDescription() {
		return addOnDescription;
	}
	public void setAddOnDescription(String addOnDescription) {
		this.addOnDescription = addOnDescription;
	}
	public void setAddOnComputeStrategy(String addOnComputeStrategy) {
		AddOnComputeStrategy addOnComputeStrategyEnum = null;
		for (AddOnComputeStrategy b : AddOnComputeStrategy.values()) {
            if (b.toString().equalsIgnoreCase(addOnComputeStrategy)) {
            	addOnComputeStrategyEnum = b;
            }
        }
		this.addOnComputeStrategy = addOnComputeStrategyEnum;
	}
	public AddOnComputeStrategy getAddOnComputeStrategy() {
		return addOnComputeStrategy;
	}
	public void setAddOnComputeStrategy(AddOnComputeStrategy addOnComputeStrategy) {
		this.addOnComputeStrategy = addOnComputeStrategy;
	}
	public Double getAddOnValue() {
		return addOnValue;
	}
	public void setAddOnValue(Double addOnValue) {
		this.addOnValue = addOnValue;
	}

	@Override
	public String toString() {
		return "AddOn [addOnId=" + addOnId + ", addOnName=" + addOnName + ", addOnDescription=" + addOnDescription
				+ ", addOnComputeStrategy=" + addOnComputeStrategy + ", addOnValue=" + addOnValue + "]";
	}
}
