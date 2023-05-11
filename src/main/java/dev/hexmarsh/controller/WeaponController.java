package dev.hexmarsh.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import dev.hexmarsh.model.Weapon;
import dev.hexmarsh.service.WeaponService;

@RestController
@RequestMapping("/weapons")
public class WeaponController {

    @Autowired
    private WeaponService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Weapon createWeapon(@RequestBody Weapon weapon)
    {
        return service.addWeapon(weapon);
    }

    @GetMapping
    public List<Weapon> getWeapons()
    {
        return service.findAllWeapons();
    }

    @GetMapping("/name/{name}")
    public List<Weapon> findWeaponByName(@PathVariable String name)
    {
        return service.getWeaponByName(name);
    }

    @GetMapping("/weapontype/{weaponType}")
    public List<Weapon> findWeaponByWeaponType(@PathVariable String weaponType)
    {
        return service.getWeaponByWeaponType(weaponType);
    }

    @GetMapping("/attacktype/{attackType}")
    public List<Weapon> findWeaponByAttackType(@PathVariable String attackType)
    {
        return service.getWeaponByAttackType(attackType);
    }

    @PutMapping
    public Weapon updateWeapon(@RequestBody Weapon weapon)
    {
        return service.updateWeapon(weapon);
    }

    @DeleteMapping("/{name}")
    public String deleteWeapon(String name)
    {
        return service.deleteWeapon(name);
    }
}
