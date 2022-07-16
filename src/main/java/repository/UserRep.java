package repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import model.User;

@Repository
public interface UserRep extends JpaRepository<User, Long> {

	@Query("select u from User u where u.name= :username and u.password = :password ")
	public User validateUserNameAndPassword(@Param("username") String usernme, @Param("password") String password);

	@Modifying
	@Transactional
	@Query("update User u set u.name=:name , u.password =:password where u.recid=:recid")
	public void update(@Param("name") String name, @Param("recid") Long recid, @Param("password") String password);
	// public void update(@Param("name1") String name,@Param("passw") String
	// password,@Param("recid") Long id);

	@Query("select n from User n")
	public List<User> getAll();

	//findByOfferingId
	@Query("select u from User u where u.username=:username")
 	public User findUserName(@Param("username") String username);


}
