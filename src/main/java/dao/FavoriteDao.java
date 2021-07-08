package dao;

import java.util.List;

import member.bean.Favorite;


public interface FavoriteDao {

	int insert(Favorite favorite);
	
	int deleteById(int favoriteId);
	
	int update(Favorite favorite);
		
	Favorite selectById(int favoriteId);
	
	List<Favorite> selectByMemberId(int memberId);
}
