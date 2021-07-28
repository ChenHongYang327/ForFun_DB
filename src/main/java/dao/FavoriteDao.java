package dao;

import java.util.List;

import member.bean.Favorite;


public interface FavoriteDao {

	int insert(Favorite favorite);
	
	int deleteById(int favoriteId);
	
	int deleteByPublishId(int publishId);
	
	int update(Favorite favorite);
		
	Favorite selectById(int favoriteId);
	
	List<Favorite> selectByMemberId(int memberId);
	
	Favorite selectByMemberIdAndPublishId(int memberId, int publishId);
}
