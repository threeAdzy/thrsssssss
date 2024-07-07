package com.drivingschool.mapper;

import com.drivingschool.pojo.dto.ApplyNoDTO;
import com.drivingschool.pojo.dto.ApplyUpdateDTO;
import com.drivingschool.pojo.dto.ApplypageDTO;
import com.drivingschool.pojo.entity.Apply;
import com.drivingschool.pojo.vo.ApplyPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplyMapper {

    @Insert("INSERT INTO apply (person_id, lv, content_id,  create_time)\n" +
            "VALUES (#{personId}, #{lv}, #{contentId},  #{createTime});")
    void insertCoach(Apply apply);
    @Insert("INSERT INTO apply (person_id, lv, content_id,  create_time)\n" +
            "VALUES (#{personId}, #{lv}, #{contentId},  #{createTime});")
    void insertUser(Apply apply);

    /**
     * 管理员分页查申请记录
     * @param applypageDTO
     * @return
     */
    Page<ApplyPageVO> pageApply(ApplypageDTO applypageDTO);

    @Select("SELECT COUNT(*) FROM apply WHERE person_id = #{personId} AND lv = #{lv} AND apply_state = 0")
    Integer applyNo(ApplyNoDTO applyNoDTO);

    /**
     * 删除申请记录
     * @param applyId
     */
    @Delete("delete from apply where apply_id = #{applyId}")
    void deleteById(Long applyId);

    void update(ApplyUpdateDTO applyUpdateDTO);
}
