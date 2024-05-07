package org.nice.pokedexxfx.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nice.pokedexxfx.Utils;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.models.PokemonType;
import rx.subjects.BehaviorSubject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class PokemonService {
    private static PokemonService instance;
    public static PokemonService getInstance() {
        return instance;
    }

    ArrayList<PokemonModel> pokemonList = new ArrayList<>();

    public ObservableList<PokemonModel> filteredPokemonList;

    private BehaviorSubject<PokemonModel> currentPokemon;

    public rx.Observable<PokemonModel> onCurrentPokemon() {
        return currentPokemon.asObservable();
    }

    public static void init() {
        if(instance == null) {
            instance = new PokemonService();
        }
    }


    /** Sets the currentPokemon field and fires the change event
     * @param model The pokemon model to be the current
     */
    public void setCurrentPokemon(PokemonModel model) {
        currentPokemon.onNext(model);
    }

    public List<PokemonModel> getPokemonList() {
        return pokemonList;
    }

    public SortedList<PokemonModel> filterPokemons(List<PokemonType> filter, Optional<String> search) {
        return filteredPokemonList
                .filtered(pokemon -> {
                    var appearedOnSearch = search
                            .map(v -> pokemon.name().toLowerCase().contains(v.toLowerCase()))
                            .orElse(true);
                    return appearedOnSearch && filter.stream().allMatch(filterType -> pokemon.type().contains(filterType.name()));
                })
                .sorted(Comparator.comparingInt(PokemonModel::id));
    }

    public SortedList<PokemonModel> filterPokemons(List<PokemonType> filter) {
        return filterPokemons(filter, Optional.empty());
    }

    public Optional<PokemonModel> getPokemon(int id) {
        for(var p : pokemonList) {
            if(p.id()== id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private PokemonService() {
        loadFromFile();
        System.out.println("SIZE " + pokemonList.size());
        filteredPokemonList = FXCollections.observableArrayList(pokemonList);
        currentPokemon = BehaviorSubject.create( pokemonList.get(0));
    }

    private void loadFromFile() {
        var file = new File(Utils.getResource("/pokedex.json"));

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(
                    Files.readString(file.toPath())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parseJsonArray(jsonArray);

    }

    private void parseJsonArray(JSONArray jsonArray) {
        var list = pokemonList;

        for(var item : jsonArray) {
            list.add(
                    parseAsPokemon((JSONObject)item)
            );
        }
    }

    private PokemonModel parseAsPokemon(JSONObject item) {
        var id = item.getInt("id");
        var name = item.getJSONObject("name").getString("english");
        var type = parseAsPokemonType(item.getJSONArray("type"));
        var baseStats = parseAsPokemonStats(item);
        String species = item.getString("species");
        var description = item.getString("description");
        PokemonModel.Profile profile = parseAsPokemonProfile(item.getJSONObject("profile"));
        PokemonModel.Image image = parseAsImages(item.getJSONObject("image"));
        PokemonModel.Evolution evolution = parseAsPOkemonEvolution(item);
        var pokemon = new PokemonModel(
                id,
                name,
                type,
                baseStats,
                species,
                description,
                evolution,
                profile,
                image,
                pokemonList
        );

        return pokemon;
    }

    private PokemonModel.Evolution parseAsPOkemonEvolution(JSONObject pokemonJson) {
        var evolutionJson =  pokemonJson.getJSONObject("evolution");
        Optional<PokemonModel.EvolutionData> prevEvolution = Optional.empty();
        ArrayList<PokemonModel.EvolutionData> nextEvolution = new ArrayList<>();
        if(evolutionJson.has("prev")) {
            var prevJson = evolutionJson.getJSONArray("prev");
            prevEvolution = Optional.of(
                    new PokemonModel.EvolutionData(
                            Integer.parseInt(prevJson.getString(0)),
                            prevJson.getString(1)
                    )
            );
        }
        if(evolutionJson.has("next")) {
            var nextEvolutionListJson = evolutionJson.getJSONArray("next");
            for(var current : nextEvolutionListJson) {
                var nextEvolJson = (JSONArray)current;
                nextEvolution.add(new PokemonModel.EvolutionData(
                        Integer.parseInt(nextEvolJson.getString(0)),
                        nextEvolJson.getString(1)
                ));

            }
        }
        return new PokemonModel.Evolution(
                nextEvolution,
                prevEvolution
        );
    }

    private PokemonModel.Image parseAsImages(JSONObject jsonObj) {
        Optional<String> hires = Optional.empty();
        var toReplace = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master";
        if(jsonObj.has("hires")) {
            hires = Optional.of(jsonObj.getString("hires").replace(toReplace, "").replace("/pokedex", ""));
        }
        return new PokemonModel.Image(
                jsonObj.getString("sprite").replace(toReplace, "").replace("/pokedex", ""),
                jsonObj.getString("thumbnail").replace(toReplace, "").replace("/pokedex", ""),
                hires
        );
    }

    private PokemonModel.Profile parseAsPokemonProfile(JSONObject profile) {
        Optional<List<String>> egg = Optional.empty();
        if(profile.has("egg")) {
            var eggJsonArray = profile.getJSONArray("egg");
            ArrayList<String> list = new ArrayList<>();
            for(var e : eggJsonArray) {
                list.add(e.toString());
            }
            egg = Optional.of(
                    list
            );
        }
        return new PokemonModel.Profile(
                profile.getString("height"),
                profile.getString("weight"),
                egg,
                parseAsAbility(profile.getJSONArray("ability")),
                profile.getString("gender")
        );
    }

    private List<PokemonModel.Ability> parseAsAbility(JSONArray abilities) {
        ArrayList<PokemonModel.Ability> list = new ArrayList<>();

        for(var ab : abilities) {
            var abJson = (JSONArray)ab;
            list.add(
                    new PokemonModel.Ability(
                            abJson.getString(0),
                            abJson.getBoolean(1)
                    )
            );
        }

        return list;    }

    private Optional<PokemonModel.BaseStats> parseAsPokemonStats(JSONObject item) {
        if(item.has("base")) {
            var base = item.getJSONObject("base");
            return Optional.of(new PokemonModel.BaseStats(
                    base.getInt("HP"),
                    base.getInt("Attack"),
                    base.getInt("Defense"),
                    base.getInt("Sp. Attack"),
                    base.getInt("Sp. Defense"),
                    base.getInt("Speed")
            ));
        }
        return Optional.empty();
    }

    private List<String> parseAsPokemonType(JSONArray type) {
        return type.toList().stream().map(Object::toString).toList();
    }


}
