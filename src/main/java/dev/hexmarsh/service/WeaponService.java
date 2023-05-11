package dev.hexmarsh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.hexmarsh.model.Weapon;
import dev.hexmarsh.repository.WeaponRepository;

@Service
public class WeaponService {

    @Autowired
    private WeaponRepository repository;

    //CRUD CREATE, READ, UPDATE, DELETE

    public Weapon addWeapon(Weapon weapon)
    {
        return repository.save(weapon);
    }

    public List<Weapon> findAllWeapons()
    {
        return repository.findAll();
    }

    public List<Weapon> getWeaponByName(String name)
    {
        return repository.findByName(name);
    }

    public List<Weapon> getWeaponByAttackType(String attackType)
    {
        return repository.findByAttackType(attackType);
    }

    public List<Weapon> getWeaponByWeaponType(String weaponType)
    {
        return repository.findByWeaponType(weaponType);
    }

    public Weapon updateWeapon(Weapon weapon)
    {
        Weapon existingWeapon = repository.findById(weapon.getName()).get();
        existingWeapon.setName(weapon.getName());
        existingWeapon.setAttackPower(weapon.getAttackPower());
        existingWeapon.setDamageReduction(weapon.getDamageReduction());
        existingWeapon.setRequirements(weapon.getRequirements());
        existingWeapon.setParameterBonus(weapon.getParameterBonus());
        existingWeapon.setDurability(weapon.getDurability());
        existingWeapon.setWeight(weapon.getWeight());
        existingWeapon.setAttackType(weapon.getAttackType());
        existingWeapon.setWeaponType(weapon.getAttackType());
        return repository.save(existingWeapon);
    }

    public String deleteWeapon(String name)
    {
        repository.deleteByName(name);
        return "Weapon deleted from database.";
    }
}
