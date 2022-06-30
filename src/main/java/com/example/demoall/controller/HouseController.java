package com.example.demoall.controller;

import com.example.demoall.model.House;
import com.example.demoall.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/houses")
@CrossOrigin("*")
public class HouseController {
    @Autowired
    HouseService houseService;
    @GetMapping
    public ResponseEntity<Iterable<House>> findAll() {
        return new ResponseEntity<>(houseService.findAll(), HttpStatus.OK);
    }
    @PostMapping("")     //Thêm mới
    public ResponseEntity add(@Valid @RequestBody House house) {
        houseService.save(house);
        return new ResponseEntity(HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @PostMapping("/upload")
    public ResponseEntity<House> handleFileUpload(@RequestParam("file") MultipartFile file, House house) {
        String fileName = file.getOriginalFilename();
        house.setImage(fileName);
        try {
            file.transferTo(new File("D:\\Modul4\\DemoAll\\src\\main\\resources\\templates\\image\\" + fileName));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        houseService.save(house);
        return ResponseEntity.ok(house);
    }

}
