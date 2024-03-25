package example.mapper;

import example.model.GuestbookMessage;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface GuestbookMessageMapper {
    @Delete({
        "delete from guestbook_message",
        "where message_id = #{messageId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer messageId);

    @Insert({
        "insert into guestbook_message (message_id, guest_name, ",
        "password, message)",
        "values (#{messageId,jdbcType=INTEGER}, #{guestName,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{message,jdbcType=LONGVARCHAR})"
    })
    int insert(GuestbookMessage row);

    @Select({
        "select",
        "message_id, guest_name, password, message",
        "from guestbook_message",
        "where message_id = #{messageId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="message_id", property="messageId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="guest_name", property="guestName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="message", property="message", jdbcType=JdbcType.LONGVARCHAR)
    })
    GuestbookMessage selectByPrimaryKey(Integer messageId);

    @Select({
        "select",
        "message_id, guest_name, password, message",
        "from guestbook_message"
    })
    @Results({
        @Result(column="message_id", property="messageId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="guest_name", property="guestName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="message", property="message", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<GuestbookMessage> selectAll();

    @Update({
        "update guestbook_message",
        "set guest_name = #{guestName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "message = #{message,jdbcType=LONGVARCHAR}",
        "where message_id = #{messageId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(GuestbookMessage row);
}