package org.nkcoder.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.nkcoder.mybatis.entity.Player;

@Mapper
public interface PlayerMapper {

  @Insert("insert into player(name, team, join_at) values (#{name}, #{team}, #{joinAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Player player);

  @Delete("delete from player where id = #{id}")
  int delete(@Param("id") Integer id);

  @Update("update player set name = #{name}, team = #{team}, join_at = #{joinAt} where id = #{id}")
  int update(Player player);

  @Select("select id, name, team, join_at from player where id = #{id}")
  Player findById(@Param("id") Integer id);

}
