package br.com.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.finance.dtos.request.CategoryRequestDto;
import br.com.finance.dtos.response.CategoryReponseDto;
import br.com.finance.models.Category;
import br.com.finance.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryReponseDto> getAllCategories(){
    List<CategoryReponseDto> categories = categoryService.getAllCategories();
    return categories;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryReponseDto> getCategoryById(@PathVariable(value = "id") Long id){
    return categoryService.findCategoryById(id);
  }

  @PostMapping
  public Category createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
    return categoryService.createCategory(categoryRequestDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryRequestDto categoryRequestDto){
    return categoryService.updateCategoryByid(id, categoryRequestDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id){
    return categoryService.deleteCategoryById(id);
  }
}
