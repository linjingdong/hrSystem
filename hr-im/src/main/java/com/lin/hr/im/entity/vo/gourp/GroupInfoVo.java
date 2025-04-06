package com.lin.hr.im.entity.vo.gourp;

import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.im.entity.po.UserContact;
import lombok.Data;

import java.util.List;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/6 23:33
 **/
@Data
public class GroupInfoVo {
    private GroupInfo groupInfo;
    private List<UserContact> userContactList;
}
