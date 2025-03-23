package com.lin.hr.im.entity.query;


/**
 * 用户基础表参数
 */
public class UserInfoQuery extends BaseParam {


	/**
	 * 用户唯一UUID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 账户
	 */
	private String account;

	private String accountFuzzy;

	/**
	 * 用户名称
	 */
	private String username;

	private String usernameFuzzy;

	/**
	 * 性别 0-女 1-男
	 */
	private Integer sex;

	/**
	 * 加密后密码
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 绑定邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 手机号（加密存储）
	 */
	private String phone;

	private String phoneFuzzy;

	/**
	 * 用户类型 ENUM('patient','doctor','admin')
	 */
	private String userType;

	private String userTypeFuzzy;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 地区
	 */
	private String areaName;

	private String areaNameFuzzy;

	/**
	 * 地区编号
	 */
	private String areaCode;

	private String areaCodeFuzzy;

	/**
	 * ✨微信OpenID（加密存储）
	 */
	private String openid;

	private String openidFuzzy;

	/**
	 * ✨微信UnionID
	 */
	private String unionId;

	private String unionIdFuzzy;

	/**
	 * ✨最后登录设备ID
	 */
	private String lastDeviceId;

	private String lastDeviceIdFuzzy;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后离开时间
	 */
	private String lastOffTime;

	private String lastOffTimeStart;

	private String lastOffTimeEnd;


	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setAccountFuzzy(String accountFuzzy){
		this.accountFuzzy = accountFuzzy;
	}

	public String getAccountFuzzy(){
		return this.accountFuzzy;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setUsernameFuzzy(String usernameFuzzy){
		this.usernameFuzzy = usernameFuzzy;
	}

	public String getUsernameFuzzy(){
		return this.usernameFuzzy;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}

	public Integer getSex(){
		return this.sex;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPasswordFuzzy(String passwordFuzzy){
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPasswordFuzzy(){
		return this.passwordFuzzy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy(){
		return this.emailFuzzy;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setPhoneFuzzy(String phoneFuzzy){
		this.phoneFuzzy = phoneFuzzy;
	}

	public String getPhoneFuzzy(){
		return this.phoneFuzzy;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return this.userType;
	}

	public void setUserTypeFuzzy(String userTypeFuzzy){
		this.userTypeFuzzy = userTypeFuzzy;
	}

	public String getUserTypeFuzzy(){
		return this.userTypeFuzzy;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return this.areaName;
	}

	public void setAreaNameFuzzy(String areaNameFuzzy){
		this.areaNameFuzzy = areaNameFuzzy;
	}

	public String getAreaNameFuzzy(){
		return this.areaNameFuzzy;
	}

	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}

	public String getAreaCode(){
		return this.areaCode;
	}

	public void setAreaCodeFuzzy(String areaCodeFuzzy){
		this.areaCodeFuzzy = areaCodeFuzzy;
	}

	public String getAreaCodeFuzzy(){
		return this.areaCodeFuzzy;
	}

	public void setOpenid(String openid){
		this.openid = openid;
	}

	public String getOpenid(){
		return this.openid;
	}

	public void setOpenidFuzzy(String openidFuzzy){
		this.openidFuzzy = openidFuzzy;
	}

	public String getOpenidFuzzy(){
		return this.openidFuzzy;
	}

	public void setUnionId(String unionId){
		this.unionId = unionId;
	}

	public String getUnionId(){
		return this.unionId;
	}

	public void setUnionIdFuzzy(String unionIdFuzzy){
		this.unionIdFuzzy = unionIdFuzzy;
	}

	public String getUnionIdFuzzy(){
		return this.unionIdFuzzy;
	}

	public void setLastDeviceId(String lastDeviceId){
		this.lastDeviceId = lastDeviceId;
	}

	public String getLastDeviceId(){
		return this.lastDeviceId;
	}

	public void setLastDeviceIdFuzzy(String lastDeviceIdFuzzy){
		this.lastDeviceIdFuzzy = lastDeviceIdFuzzy;
	}

	public String getLastDeviceIdFuzzy(){
		return this.lastDeviceIdFuzzy;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart(){
		return this.createTimeStart;
	}
	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}

	public void setLastOffTime(String lastOffTime){
		this.lastOffTime = lastOffTime;
	}

	public String getLastOffTime(){
		return this.lastOffTime;
	}

	public void setLastOffTimeStart(String lastOffTimeStart){
		this.lastOffTimeStart = lastOffTimeStart;
	}

	public String getLastOffTimeStart(){
		return this.lastOffTimeStart;
	}
	public void setLastOffTimeEnd(String lastOffTimeEnd){
		this.lastOffTimeEnd = lastOffTimeEnd;
	}

	public String getLastOffTimeEnd(){
		return this.lastOffTimeEnd;
	}

}
