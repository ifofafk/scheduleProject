package cn.com.jxTec.schedulePro.common.constant;

/**
 * 用户枚举类
 * @author Administrator
 *
 */
public enum UserEmun implements IConstant {
	
	USER_KEY("USER", "存在Session中的用户KEY"),
	RATION_KEY("RATION","存在Session中的用户权限KEY"),
//	USER_MODULE_LIST("","USER_MODULE_LIST"),
	USER_NOT_EXIST("110000","用户不存在"),
	USER_ERROR_PWD("110001","用户密码错误"),
	USER_ERROR_NAME_OR_PWD("110002","用户名或原密码错误"),
	USER_EMPTY_NAME_OR_PWD("110003","用户或密码为空"),
	USER_RIGHT("110004","登陆成功!"),
	PRIVILAGE_NOT_ALLOW("123061","权限不足，不允许访问"),
	SESSION_EXPIRES("503", "登录超时"),
	NO_FACE_DELETE("666","识别不到人脸");

	private String code;
	private String desc;
	
	UserEmun(String code, String desc) {
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
