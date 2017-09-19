package com.urt.web.dmp.entity;

import java.io.Serializable;
public class DeviceData implements Serializable {

	private static final long serialVersionUID = 2009047057323590514L;
	//windows和Android共有信息
	private String manufacturer = "";//硬件制造商
	private int batteryLevel = -1;//电池电量，[0-100]
	private long elapsedTime = 0;//已开机时间，包括睡眠时间
	private String model = "";//设备型号，比如Redmi Note 3
	//android特有属性
	//这些是比较固定的信息
	private String board = "";//主板
	private String brand = "";//系统定制商
	private String serial = "";//硬件序列号
	private String product = "";//手机制造商
	private String sdk_version = "";//安卓版本

    //这些是经常变化的信息
	
	//windows特有属性
	private String cpuId = "";//CPU序列号
	private String hadwareId = "";//硬盘序列号
	private String macAddress = "";//mac地址
	private String systemType = "";//操作系统类型
	private String operationVersion = "Win 10";//操作系统写死的，因为win10的版本号程序获取不准确
	private String hostName = "";//计算机名
	private String mobileModel = "";//模组
	private String mobileModelFirmwareInfo = "";//模组固件信息
	private String ip = "";//ip地址
	private String networkAdapterName = "";//网卡
	private long totlaInternalMemorySize = 0;//总的存储容量,字节数
	private long freeInternalMemorySize = 0;//空余内部存储容量，字节数
    private String networkType = "";//“WIFI”或者“MobileData”

	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSdk_version() {
		return sdk_version;
	}
	public void setSdk_version(String sdk_version) {
		this.sdk_version = sdk_version;
	}
	public int getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public long getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getCpuId() {
		return cpuId;
	}
	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}
	public String getHadwareId() {
		return hadwareId;
	}
	public void setHadwareId(String hadwareId) {
		this.hadwareId = hadwareId;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getOperationVersion() {
		return operationVersion;
	}
	public void setOperationVersion(String operationVersion) {
		this.operationVersion = operationVersion;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getMobileModel() {
		return mobileModel;
	}
	public void setMobileModel(String mobileModel) {
		this.mobileModel = mobileModel;
	}
	public String getMobileModelFirmwareInfo() {
		return mobileModelFirmwareInfo;
	}
	public void setMobileModelFirmwareInfo(String mobileModelFirmwareInfo) {
		this.mobileModelFirmwareInfo = mobileModelFirmwareInfo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNetworkAdapterName() {
		return networkAdapterName;
	}
	public void setNetworkAdapterName(String networkAdapterName) {
		this.networkAdapterName = networkAdapterName;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public long getTotlaInternalMemorySize() {
		return totlaInternalMemorySize;
	}
	public void setTotlaInternalMemorySize(long totlaInternalMemorySize) {
		this.totlaInternalMemorySize = totlaInternalMemorySize;
	}
	public long getFreeInternalMemorySize() {
		return freeInternalMemorySize;
	}
	public void setFreeInternalMemorySize(long freeInternalMemorySize) {
		this.freeInternalMemorySize = freeInternalMemorySize;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	@Override
	public String toString() {
		return "DeviceData [manufacturer=" + manufacturer + ", batteryLevel="
				+ batteryLevel + ", elapsedTime=" + elapsedTime + ", model="
				+ model + ", board=" + board + ", brand=" + brand + ", serial="
				+ serial + ", product=" + product + ", sdk_version="
				+ sdk_version + ", cpuId=" + cpuId + ", hadwareId=" + hadwareId
				+ ", macAddress=" + macAddress + ", systemType=" + systemType
				+ ", operationVersion=" + operationVersion + ", hostName="
				+ hostName + ", mobileModel=" + mobileModel
				+ ", mobileModelFirmwareInfo=" + mobileModelFirmwareInfo
				+ ", ip=" + ip + ", networkAdapterName=" + networkAdapterName
				+ ", totlaInternalMemorySize=" + totlaInternalMemorySize
				+ ", freeInternalMemorySize=" + freeInternalMemorySize
				+ ", networkType=" + networkType + "]";
	}
	
}
