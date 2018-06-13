package cn.com.jxTec.schedulePro.common.constant;

/**
 * 用户枚举类
 * @author Administrator
 *
 */
public enum SysEmun implements IConstant {
	
	ERROR("505", "系统发生错误"),
	RATION_KEY("RATION","存在Session中的用户权限KEY");


	private String code;
	private String desc;
	
	SysEmun(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}
}
