package com.example.fesc.sigeliFesc.models.clasesEnum;

public enum carrera {

    Software(1), DiseñoGrafico(2);

    carrera(int id) {
    }

    public static String ordinal(int i) {

        if(i == 1){
            return carrera.Software.toString();
        }
        else if (i == 2){
            return carrera.DiseñoGrafico.toString();
        }
        else{
            return "No especificada";
        }

    }
}
