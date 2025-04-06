package com.lin.hr.im.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 
 */
@Setter
@Getter
public class GroupInfo implements Serializable {


	/**
	 * 群ID
	 */
	private String groupId;

	/**
	 * 群组名
	 */
	private String groupName;

	/**
	 * 群主id
	 */
	private String groupOwnerId;

	/**
	 * 群人数
	 */
	private Integer memberCount;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 群公告
	 */
	private String groupNotice;

	/**
	 * 0:直接加入, 1:需要管理员同意加入
	 */
	private Integer joinType;

	/**
	 * 状态 1:正常 0:解散
	 */
	private Integer status;


    @Override
	public String toString (){
		return "群ID:"+(groupId == null ? "空" : groupId)+"，群组名:"+(groupName == null ? "空" : groupName)+"，群主id:"+(groupOwnerId == null ? "空" : groupOwnerId)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，群公告:"+(groupNotice == null ? "空" : groupNotice)+"，0:直接加入, 1:需要管理员同意加入:"+(joinType == null ? "空" : joinType)+"，状态 1:正常 0:解散:"+(status == null ? "空" : status);
	}
}
