package org.nkcoder.mybatis.xml.dao;

import org.apache.ibatis.session.SqlSession;
import org.nkcoder.mybatis.xml.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerDao {

  private final SqlSession sqlSession;

  public PlayerDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insert(Player player) {
    return sqlSession.insert("insert", player);
  }

  public int delete(Integer id) {
    return sqlSession.delete("delete", id);
  }

  public int update(Player player) {
    return sqlSession.update("update", player);
  }

  public Player findById(Integer id) {
    return sqlSession.selectOne("findById", id);
  }
}
