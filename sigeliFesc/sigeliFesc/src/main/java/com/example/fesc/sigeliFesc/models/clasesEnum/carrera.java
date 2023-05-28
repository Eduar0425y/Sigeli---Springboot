package com.example.fesc.sigeliFesc.models.clasesEnum;

public enum carrera {

    Software(1), DisenoGrafico(2), Modas(3), turismo(4), financiera(5);

    carrera(int id) {
    }

    public static String ordinal(int i) {

        if(i == 1){
            return carrera.Software.toString();
        }
        else if (i == 2){
            return carrera.DisenoGrafico.toString();
        }
        else if (i == 3){
            return carrera.Modas.toString();
        }
        else if (i == 4){
            return carrera.turismo.toString();
        }
        else if (i == 5){
            return carrera.financiera.toString();
        }
        else{
            return "No especificada";
        }

    }
}
