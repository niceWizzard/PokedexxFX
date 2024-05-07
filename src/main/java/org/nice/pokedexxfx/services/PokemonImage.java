package org.nice.pokedexxfx.services;

import javafx.scene.image.Image;
import org.nice.pokedexxfx.Utils;
import org.nice.pokedexxfx.models.PokemonModel;

import java.util.HashMap;
import java.util.Map;

public class PokemonImage {
    private static Map<String, Image> thumbnailMap = new HashMap<>();
    private static Map<String, Image> hiresMap = new HashMap<>();
    private static Map<String, Image> spriteMap = new HashMap<>();


    public static Image getThumbnail(PokemonModel pokemon) {
        var id = pokemon.id();
        var key = String.valueOf(id);
        if(thumbnailMap.containsKey(key)) {
            return thumbnailMap.get(key);
        }
        var image = Utils.getImage(
          pokemon.image().thumbnail()
        );

        thumbnailMap.put(key, image);
        return image;
    }
    public static Image getSprite(PokemonModel pokemon) {
        var id = pokemon.id();
        var key = String.valueOf(id);
        if(spriteMap.containsKey(key)) {
            return spriteMap.get(key);
        }
        var image = Utils.getImage(
                PokemonService.getInstance().getPokemon(id).get().image().sprite()
        );

        spriteMap.put(key, image);
        return image;
    }

    public static Image getHires(PokemonModel pokemon) {
        var id = pokemon.id();
        var key = String.valueOf(id);

        if(hiresMap.containsKey(key)) {
            return hiresMap.get(key);
        }
        var p = PokemonService.getInstance().getPokemon(id).get();
        var image = Utils.getImage(
                p.image().hires().orElse(p.image().thumbnail())
        );

        hiresMap.put(key, image);
        return image;
    }



}
