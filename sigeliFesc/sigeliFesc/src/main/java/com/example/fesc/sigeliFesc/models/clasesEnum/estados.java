package com.example.fesc.sigeliFesc.models.clasesEnum;

public enum estados {

    No_Disponible(0),
    Disponible(1), Entregado(2),
    en_Deuda(3), atrasado(4),
    pago(5),
    no_Pago(6),
    condenado(7);

    estados(int id) {
    }

}
