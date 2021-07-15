package service;

import java.util.List;

import dao.FavoriteDao;
import dao.impl.FavoriteDaoImpl;
import member.bean.Favorite;

public class FavoriteService {
	private FavoriteDao favoriteDao;

	public FavoriteService() {
		favoriteDao=new FavoriteDaoImpl();
	}
	public List<Favorite> selectByMemberId(int memberId){
		return favoriteDao.selectByMemberId(memberId);
	}
	public int deleteById(int favoriteId) {
		return favoriteDao.deleteById(favoriteId);
	}
	
	public int insert(Favorite favorite) {
	    return favoriteDao.insert(favorite);
	}
	
	public Favorite selectByMemberIdAndPublishId(int memberId, int publishId) {
	    return favoriteDao.selectByMemberIdAndPublishId(memberId, publishId);
	}
}
