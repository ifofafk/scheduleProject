package cn.com.jxTec.schedulePro.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 用户基准类．用于存放当前活动用户的资料．具体应用中需要根据应用特征扩展此类．
 * <p>
 * created on 下午06:47:39
 *
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2480963488543195368L;

	public User() {

		super();
	}

	/**
	 * 系统内置的超级管理用户的ID
	 */
	public final static String SYS_DEFAULT_USERID = "system";

	/**
	 * 姓名
	 */
	protected String name;

	/**
	 * 密码
	 */
	protected String password;

	/**
	 * 当前用户 机构对象
	 */
	protected List yhjg;

	/**
	 * ip地址
	 */
	protected String ip;
	/**
	 * 手机
	 */
	protected String sj;

	/**
	 * 帐户
	 */
	protected String account;

	/**
	 * 组织机构代码
	 */
	protected String departmentCode;

	/**
	 * 组织机构名称
	 */
	protected String departmentName;

	/**
	 * 当前登陆编号，数字类型。用sequeence实现．
	 */
	protected String dlbh;

	/**
	 * 当前登陆IP.
	 */
	protected String dlip;

	/**
	 * 用户数据级别.
	 */
	protected String sjjb;

	/**
	 * 用户角色
	 */
	protected String yhjs;

	/**
	 * 是否外部调用 1是 0否. 默认否
	 */
	protected Integer isOutter = 0;

	/**
	 * 用户身份证号
	 */
	protected String sfzh;

	/**
	 * 获取当前活动用户的帐户
	 *
	 * @return 当前活动用户的帐户
	 */
	public String getAccount() {

		return account;
	}

	/**
	 * 设置当前活动用户的帐户
	 *
	 * @param account
	 *            当前活动用户的帐户
	 */
	public void setAccount(String account) {

		this.account = account;
	}

	/**
	 * 获取当前活动用户的组织机构代码
	 *
	 * @return 当前活动用户的组织机构代码
	 */
	public String getDepartmentCode() {

		return departmentCode;
	}

	/**
	 * 设置当前活动用户的组织机构代码
	 *
	 * @param departmentCode
	 *            当前活动用户的组织机构代码
	 */
	public void setDepartmentCode(String departmentCode) {

		this.departmentCode = departmentCode;
	}

	/**
	 * 获取当前活动用户的组织机构名称
	 *
	 * @return 当前活动用户的组织机构名称
	 */
	public String getDepartmentName() {

		return departmentName;
	}

	/**
	 * 设置当前活动用户的组织机构名称
	 *
	 * @param departmentName
	 *            当前活动用户的组织机构名称
	 */
	public void setDepartmentName(String departmentName) {

		this.departmentName = departmentName;
	}

	/**
	 * 获取当前活动用户姓名
	 *
	 * @return 当前活动用户姓名
	 */
	public String getName() {

		return name;
	}

	/**
	 * 设置当前活动用户姓名
	 *
	 * @param name
	 *            当前活动用户姓名
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * 获取当前活动用户的明文密码
	 *
	 * @return 当前活动用户的明文密码
	 */
	public String getPassword() {

		return password;
	}

	/**
	 * 设置当前活动用户的明文密码
	 *
	 * @param password
	 *            当前活动用户的明文密码
	 */
	public void setPassword(String password) {

		this.password = password;
	}

	/**
	 * 获取当前登陆编号．
	 *
	 * @return 当前登陆编号，数字类型。用sequeence实现．
	 */
	public String getDlbh() {

		return dlbh;
	}

	/**
	 * 设置当前登陆编号．
	 *
	 * @param dlbh
	 *            当前登陆编号，数字类型。用sequeence实现．
	 */
	public void setDlbh(String dlbh) {

		this.dlbh = dlbh;
	}

	/**
	 * 获取用户当前登陆IP.
	 *
	 * @return 当前登陆IP.
	 */
	public String getDlip() {

		return dlip;
	}

	/**
	 * 设置用户当前登陆IP.
	 *
	 * @param dlip
	 *            当前登陆IP.
	 */
	public void setDlip(String dlip) {

		this.dlip = dlip;
	}

	/**
	 * 获取用户数据级别.
	 *
	 * @return 用户数据级别.
	 */
	public String getSjjb() {
		return sjjb;
	}

	/**
	 * 设置用户数据级别.
	 *
	 * @param sjjb
	 *            户数据级别.
	 */
	public void setSjjb(String sjjb) {
		this.sjjb = sjjb;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

	/**
	 * 获取当前用户固定IP
	 *
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置当前用户固定IP
	 *
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getYhjs() {
		return yhjs;
	}

	public void setYhjs(String yhjs) {
		this.yhjs = yhjs;
	}

	public List getYhjg() {
		return yhjg;
	}

	public void setYhjg(List yhjg) {
		this.yhjg = yhjg;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public Integer getIsOutter() {
		return isOutter;
	}

	public void setIsOutter(Integer isOutter) {
		this.isOutter = isOutter;
	}

}
