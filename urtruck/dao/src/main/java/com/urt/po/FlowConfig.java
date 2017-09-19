package com.urt.po;

import java.io.Serializable;

public class FlowConfig implements Serializable {
    private Short id;

    private String flowsize;

    private String price;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getFlowsize() {
        return flowsize;
    }

    public void setFlowsize(String flowsize) {
        this.flowsize = flowsize == null ? null : flowsize.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

	@Override
	public String toString() {
		return "FlowConfig [id=" + id + ", flowsize=" + flowsize + ", price="
				+ price + "]";
	}
    
}