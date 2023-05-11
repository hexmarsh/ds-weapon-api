package dev.hexmarsh.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import dev.hexmarsh.model.Weapon;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, String> {

    @Query("{ name: ?0 }")
    List<Weapon> findByName(String name);

    @Query("{ weaponType: ?0 }")
    List<Weapon> findByWeaponType(String weaponType);

    @Query("{ attackType: ?0 }")
    List<Weapon> findByAttackType(String attackType);

    @Query(value = "{'name' : ?0}", delete = true)
    public Weapon deleteByName(String name);

    @Query("{ range: ?0 }")
    List<Weapon> findByRange(int range);
}
