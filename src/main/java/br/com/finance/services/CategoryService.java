package br.com.finance.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.finance.dtos.request.CategoryRequestDto;
import br.com.finance.dtos.response.CategoryReponseDto;
import br.com.finance.models.Category;
import br.com.finance.repositories.CategoryRepository;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  ModelMapper modelMapper;

   public List<CategoryReponseDto> getAllCategories(){
    List<Category> categories = categoryRepository.findAll();
    List<CategoryReponseDto> categoryDto = categories.stream().map(category -> modelMapper.map(category, CategoryReponseDto.class)).toList();
    return categoryDto;
  }

  public ResponseEntity<CategoryReponseDto> findCategoryById(Long id){
    Optional<Category> categoryId = categoryRepository.findById(id);
    if(categoryId.isPresent()){
      Category category = categoryId.get();
      CategoryReponseDto categoryDto = modelMapper.map(category, CategoryReponseDto.class);
      return ResponseEntity.ok().body(categoryDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public Category createCategory(CategoryRequestDto categoryRequestDto){
    Category category = modelMapper.map(categoryRequestDto, Category.class);
    return categoryRepository.save(category);
  }

  public ResponseEntity<Category> updateCategoryByid(Long id, CategoryRequestDto categoryRequestDto){
    Optional<Category> categoryId = categoryRepository.findById(id);
    if(categoryId.isPresent()){
      Category categoryToUpdate = categoryId.get();
      modelMapper.map(categoryRequestDto, categoryToUpdate);
      Category updateCategory = categoryRepository.save(categoryToUpdate);
      return ResponseEntity.ok().body(updateCategory);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public ResponseEntity<Object> deleteCategoryById(Long id){
    return categoryRepository.findById(id).map(categoryToDelete -> {
      categoryRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }).orElse(ResponseEntity.notFound().build());
  }
}
