package com.urt.web.common.util;

import java.io.Serializable;

/**
 * 测试树节点实体类
 * @author zhangbt3
 *
 */
public class ZTreeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String pId;
	private String name;
	private boolean isParent;
	private boolean checked;
	private boolean open;
	private boolean chkDisabled;
	
	public ZTreeBean(String id, String pId, String name, boolean isParent) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.isParent = isParent;
	}

	public ZTreeBean(String id, String pId, String name, boolean isParent,boolean checked,boolean open) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.isParent = isParent;
		this.checked = checked;
		this.open = open;
		
	}
	
	public ZTreeBean(String id, String pId, String name, boolean isParent,boolean open) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.isParent = isParent;
		this.open = open;
		
	}
	
	public ZTreeBean(String id, String pId, String name, boolean isParent,boolean checked,boolean open,boolean chkDisabled) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.isParent = isParent;
		this.checked = checked;
		this.open = open;
		this.chkDisabled = chkDisabled;
	}
	
	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	}
	

	public String getpId() {
		return pId;
	}
	

	public void setpId(String pId) {
		this.pId = pId;
	}
	

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	

	public boolean isParent() {
		return isParent;
	}
	

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}
	

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

	public boolean isOpen() {
		return open;
	}
	

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	
	
	
	
}
