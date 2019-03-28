package com.example.demo.web.controller;

import com.example.demo.business.entities.Category;
import com.example.demo.business.entities.repositories.CarRepository;
import com.example.demo.business.entities.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/addcategory")
    public String categoryForm(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", new Category());
        return "category";
    }

    @PostMapping("/processcategory")
    public String processSubject(@Valid Category category,
                                 BindingResult result,
                                 Model model){
        if(result.hasErrors()){
            for (ObjectError e : result.getAllErrors()){
                System.out.println(e);
            }
            return "category";
        }
        if(categoryRepository.findByTitle(category.getTitle()) != null){
            model.addAttribute("message", "You already have a category called " +
                    category.getTitle() + "!" + " Try something else.");
            return "category";
        }
        categoryRepository.save(category);
        return "redirect:/";
    }

    @RequestMapping("/detailcategory/{id}")
    public String showCarsByCategory(@PathVariable("id") long id, Model model){
        model.addAttribute("cars", carRepository.findAllByCategory_Id(id));
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    @PostConstruct
    public void fillTables(){
       /* Category category = new Category();
        category.setTitle("Compact");
        categoryRepository.save(category);

        category = new Category();
        category.setTitle("Medium Size");
        categoryRepository.save(category);

        category = new Category();
        category.setTitle("Full Size");
        categoryRepository.save(category);

        category = new Category();
        category.setTitle("Luxury");
        categoryRepository.save(category);*/
    }
}
