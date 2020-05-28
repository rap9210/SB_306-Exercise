package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeCtrl {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;


    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("albums", albumRepository.findAll());
        return "index";
    }
    @GetMapping("/newAlbum")
    public String newAlbumForm(Model model){
        model.addAttribute("album", new Album());
        return "newAlbum";
    }

    @PostMapping("/processAlbum")
    public String processAlbum(@Valid Album album, BindingResult result){
        if(result.hasErrors()){
            return "newAlbum";
        }
        else{
            albumRepository.save(album);
            return "redirect:/";
        }
    }

    @RequestMapping("/details/{id}")
    public String loadDetails(@PathVariable("id") long id, Model model){
        model.addAttribute("album", albumRepository.findById(id).get());
        return "songList";
    }

    @GetMapping("/newSong")
    public String newSongForm(Model model){

//        model.addAttribute("album", albumRepository.findById(id).get());

        model.addAttribute("song", new Song());
        model.addAttribute("albums", albumRepository.findAll());

        return "newSong";
    }

    @PostMapping("/processSong")
    public String processSong(@Valid Song song, BindingResult result){
        if(result.hasErrors()){
            return "newSong";
        }
        else{
            songRepository.save(song);
            return "redirect:/";
        }
    }

    @RequestMapping("/remove/{id}")
    public String removeAlbum(@PathVariable("id") long id){
        if(albumRepository.existsById(id)){
            albumRepository.deleteById(id);
        }
        else if(songRepository.existsById(id)){
            songRepository.deleteById(id);
        }

        return "redirect:/";
    }

}
