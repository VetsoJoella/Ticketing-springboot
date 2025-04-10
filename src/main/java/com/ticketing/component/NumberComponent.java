// package com.ticketing.component;

// import org.springframework.stereotype.Component;

// import com.fasterxml.jackson.databind.JavaType;
// import com.fasterxml.jackson.databind.type.TypeFactory;
// import com.fasterxml.jackson.databind.util.Converter;

// @Component
// public class NumberComponent implements Converter<String, Integer>{
    
//     @Override
//     public Integer convert(String source) {
//         if (source == null || source.isBlank()) return 0; // ou null
//         return Integer.valueOf(source);
//     }

//     // Ajoutez cette méthode pour résoudre l'erreur
//     @Override
//     public JavaType getInputType(TypeFactory typeFactory) {
//         return typeFactory.constructType(String.class);
//     }
// }
