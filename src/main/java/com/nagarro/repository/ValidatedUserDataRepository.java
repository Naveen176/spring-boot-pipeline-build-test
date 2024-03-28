package com.nagarro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nagarro.entity.ValidatedUserData;

@Repository
public interface ValidatedUserDataRepository extends CrudRepository<ValidatedUserData, Integer> {

	@Query(value = "SELECT * FROM ( SELECT * FROM validated_user_data LIMIT :limit OFFSET :offset ) AS UserData ORDER BY MOD(age, 2);", nativeQuery = true)
	List<ValidatedUserData> UsersSortedByAgeEven(@Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT * FROM ( SELECT * FROM validated_user_data LIMIT :limit OFFSET :offset ) AS UserData ORDER BY MOD(age, 2) Desc", nativeQuery = true)
	List<ValidatedUserData> UsersSortedByAgeOdd(@Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT * FROM ( SELECT * FROM validated_user_data LIMIT :limit OFFSET :offset ) AS subquery ORDER BY MOD(LENGTH(name),2)", nativeQuery = true)
	List<ValidatedUserData> UsersSortedByNameEven(@Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT * FROM ( SELECT * FROM validated_user_data LIMIT :limit OFFSET :offset ) AS subquery ORDER BY MOD(LENGTH(name),2) Desc", nativeQuery = true)
	List<ValidatedUserData> UsersSortedByNameOdd(@Param("limit") int limit, @Param("offset") int offset);
}