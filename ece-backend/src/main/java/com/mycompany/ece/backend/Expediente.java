/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ece.backend;

/**
 *
 * @author Gabri
 */
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;


@MongoEntity(collection="expedientes")
public class Expediente extends PanacheMongoEntity {
    
    public String curpPaciente;
    public String nombreCompleto;
    public String tipoSangre;
    public List<String> alergias;
    public String notasClinicas;

    // Métodos de búsqueda personalizados
    public static Expediente findByCurp(String curp) {
        return find("curpPaciente", curp).firstResult();
    }
}