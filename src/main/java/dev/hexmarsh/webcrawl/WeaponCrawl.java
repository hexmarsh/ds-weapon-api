package dev.hexmarsh.webcrawl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hexmarsh.model.AttackPower;
import dev.hexmarsh.model.Bow;
import dev.hexmarsh.model.DamageReduction;
import dev.hexmarsh.model.ParameterBonus;
import dev.hexmarsh.model.Requirements;
import dev.hexmarsh.model.Weapon;

public class WeaponCrawl {
    public final static String url = "https://darksouls.wiki.fextralife.com";
    public final static ArrayList<String> daggerException = new ArrayList<String>(List.of("Dagger", "Parrying Dagger", "Bandit's Knife", "Ghost Blade", "Priscilla's Dagger"));

    public static void main(String[] args) throws IOException {
        long startTime;
        long endTime;
        long elapsedTime = 0;

        System.out.println("Starting crawl...");
        startTime = System.currentTimeMillis();
        ArrayList<String> links = getLinks();
        ArrayList<Object> objects = new ArrayList<>();

        for (String link : links) {
            Document doc = Jsoup.connect(link).timeout(6000).get();
            System.out.println("Crawling..." + link);
            Elements body = doc.select("tbody tr");

            for (int i = 2; i < body.size(); i++) {
                Element stuff = body.get(i);
                Elements e = stuff.select("td");
                String category = getCategory(link);
                String name = e.get(1).text();
                String atk_phys;
                String prot_phys;
                String atk_magic;
                String prot_magic;
                String atk_fire;
                String prot_fire;
                String atk_lightning;
                String prot_lightning;
                String atk_critical;
                String prot_stability;
                String req_strength = "";
                String req_dex = "";
                String req_intelligence = "";
                String req_faith = "";
                String weightAndDurability = "";
                String attackType = "";
                if (link.equals(url + "/Flames")) {
                    atk_phys =       e.get(3).select("span").get(0).text();
                    prot_phys =      e.get(3).select("span").get(1).text();
                    atk_magic =      e.get(4).select("span").get(0).text();
                    prot_magic =     e.get(4).select("span").get(1).text();
                    atk_fire =       e.get(5).select("span").get(0).text();
                    prot_fire =      e.get(5).select("span").get(1).text();
                    atk_lightning =  e.get(6).select("span").get(0).text();
                    prot_lightning = e.get(6).select("span").get(1).text();
                    atk_critical =   e.get(7).select("span").get(0).text();
                    prot_stability = e.get(7).select("span").get(1).text();
                } else {
                    atk_phys =       e.get(2).select("span").get(0).text();
                    prot_phys =      e.get(2).select("span").get(1).text();
                    atk_magic =      e.get(3).select("span").get(0).text();
                    prot_magic =     e.get(3).select("span").get(1).text();
                    atk_fire =       e.get(4).select("span").get(0).text();
                    prot_fire =      e.get(4).select("span").get(1).text();
                    atk_lightning =  e.get(5).select("span").get(0).text();
                    prot_lightning = e.get(5).select("span").get(1).text();
                    atk_critical =   e.get(6).select("span").get(0).text();
                    prot_stability = e.get(6).select("span").get(1).text();
                }

                if (daggerException.contains(name)) {
                    req_strength = e.get(11).text();
                    req_dex = e.get(12).text();
                    req_intelligence = e.get(13).text();
                    req_faith = e.get(14).text();
                    weightAndDurability = e.get(15).text();
                    attackType = e.get(16).text();
                } else if (link.equals(url + "/Catalysts") || link.equals(url + "/Talismans")) {
                    req_strength = e.get(7).text();
                    req_dex = e.get(8).text();
                    req_intelligence = e.get(9).text();
                    req_faith = e.get(10).text();
                    weightAndDurability = e.get(11).text();
                    if (name.equals("Tin Banishment Catalyst")){
                       attackType = "Thrust";
                    } else {
                       attackType = e.get(12).text();
                    }
                } else if (name.equals("Obsidian Greatsword")) {
                    atk_phys = "320";
                    prot_phys = "60";
                    atk_magic = "0";
                    prot_magic = "10";
                    atk_fire = "0";
                    prot_fire = "40";
                    atk_lightning = "0";
                    prot_lightning = "40";
                    atk_critical = "100";
                    prot_stability = "38";
                    req_strength = "20 - -";
                    req_dex = "16 - -";
                    req_intelligence = "0 - -";
                    req_faith = "0 - -";
                    weightAndDurability = "350 8.0";
                    attackType = "Regular Special";
                } else {
                     req_strength = e.get(8).text();
                     req_dex = e.get(9).text();
                     req_intelligence = e.get(10).text();
                     req_faith = e.get(11).text();
                     if (link.equals(url + "/Flames")) {
                        weightAndDurability = e.get(12).text() + " " + e.get(13).text();
                        attackType = e.get(14).text();
                     } else {
                        weightAndDurability = e.get(12).text();
                        attackType = e.get(13).text();
                     }
                }
                attackType = attackType.toLowerCase().replace(" ", "/");

                AttackPower atkpwr = new AttackPower(
                    Integer.parseInt(atk_phys),
                    Integer.parseInt(atk_magic),
                    Integer.parseInt(atk_fire),
                    Integer.parseInt(atk_lightning),
                    Integer.parseInt(atk_critical));
                DamageReduction dmgRed = new DamageReduction(
                    Integer.parseInt(prot_phys),
                    Integer.parseInt(prot_magic),
                    Integer.parseInt(prot_fire),
                    Integer.parseInt(prot_lightning),
                    Integer.parseInt(prot_stability));

                String[] strength = req_strength.split(" ");
                String[] dexterity = req_dex.split(" ");
                String[] intelligence = req_intelligence.split(" ");
                String[] faith = req_faith.split(" ");

                Requirements requirements = new Requirements(
                    Integer.parseInt(strength[0]),
                    Integer.parseInt(dexterity[0]),
                    Integer.parseInt(intelligence[0]),
                    Integer.parseInt(faith[0])
                );

                ParameterBonus parameterBonus = new ParameterBonus(
                    strength[1],
                    dexterity[1],
                    intelligence[1],
                    faith[1]
                );

                String[] weightDurability = weightAndDurability.split(" ");

                int durability = Integer.parseInt(weightDurability[0]);
                double weight = Double.parseDouble(weightDurability[1]);

                if (category.equals("bow") || category.equals("greatbow") || category.equals("crossbow")) {
                    Bow bow = new Bow(name, atkpwr, dmgRed, requirements, parameterBonus, durability, weight, Integer.parseInt(attackType), category);
                    objects.add(bow);
                } else {
                    Weapon weapon = new Weapon(name, atkpwr, dmgRed, requirements, parameterBonus, durability, weight, attackType, category);
                    objects.add(weapon);
                }

                endTime = System.currentTimeMillis();

                elapsedTime = (endTime - startTime) / 1000;

                System.out.print("Crawling..." + elapsedTime + " s" + "\r");

                // System.out.printf("%s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s\n",
                // name, atk_phys, prot_phys, atk_magic, prot_magic, atk_fire, prot_fire,
                //  atk_lightning, prot_lightning, atk_critical, prot_stability, req_strength, req_dex,
                //  req_intelligence, req_faith, weightAndDurability, attackType);
            }
        }

        System.out.println("Crawl complete... elapsed time: " + elapsedTime + " s");
        ObjectMapper mapper = new ObjectMapper();

        try {
            String location = "weapons.json";
            System.out.printf("Sending data to %s...\n", location);
            PrintWriter writer = new PrintWriter(new FileOutputStream(location));
            String jsonIntString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objects);
            writer.println(jsonIntString);
            writer.close();
            System.out.printf("%s created.", location);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<String> getLinks() throws IOException {
        Document doc = Jsoup.connect(url + "/Weapons").timeout(6000).get();
        Elements body = doc.select(".col-lg-1 .wiki_link");
        ArrayList<String> links = new ArrayList<>();

        for (Element e : body)
        {
            links.add(url + e.attr("href"));
        }

        return links;
    }

    public static String getCategory(String url) throws IOException
    {
        String category = "";
        switch (url)
        {
            case "https://darksouls.wiki.fextralife.com/Daggers"            ->     category = "dagger";
            case "https://darksouls.wiki.fextralife.com/Straight+Swords"    ->     category = "straight-sword";
            case "https://darksouls.wiki.fextralife.com/Greatswords"        ->     category = "greatsword";
            case "https://darksouls.wiki.fextralife.com/Ultra+Greatswords"  ->     category = "ultra-greatsword";
            case "https://darksouls.wiki.fextralife.com/Curved+Swords"      ->     category = "curved-sword";
            case "https://darksouls.wiki.fextralife.com/Katanas"            ->     category = "katana";
            case "https://darksouls.wiki.fextralife.com/Curved+Greatswords" ->     category = "curved-greatsword";
            case "https://darksouls.wiki.fextralife.com/Piercing+Swords"    ->     category = "piercing-sword";
            case "https://darksouls.wiki.fextralife.com/Axes"               ->     category = "axe";
            case "https://darksouls.wiki.fextralife.com/Great+Axes"         ->     category = "great-axe";
            case "https://darksouls.wiki.fextralife.com/Hammers"            ->     category = "hammer";
            case "https://darksouls.wiki.fextralife.com/Great+Hammers"      ->     category = "great-hammer";
            case "https://darksouls.wiki.fextralife.com/Fist+Weapons"       ->     category = "fist-weapon";
            case "https://darksouls.wiki.fextralife.com/Spears"             ->     category = "spear";
            case "https://darksouls.wiki.fextralife.com/Halberds"           ->     category = "halberd";
            case "https://darksouls.wiki.fextralife.com/Whips"              ->     category = "whip";
            case "https://darksouls.wiki.fextralife.com/Bows"               ->     category = "bow";
            case "https://darksouls.wiki.fextralife.com/Greatbows"          ->     category = "greatbow";
            case "https://darksouls.wiki.fextralife.com/Crossbows"          ->     category = "crossbow";
            case "https://darksouls.wiki.fextralife.com/Catalysts"          ->     category = "catalyst";
            case "https://darksouls.wiki.fextralife.com/Flames"             ->     category = "flame";
            case "https://darksouls.wiki.fextralife.com/Talismans"          ->     category = "talisman";
        }
        return category;
    }
}
