package org.nkcoder.mybatis.xml.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.nkcoder.mybatis.xml.entity.Player;

@Mapper
public interface PlayerMapper {

  int insert(Player player);

  int delete(@Param("id") Integer id);

  int update(Player player);

  Player findById(@Param("id") Integer id);

}
