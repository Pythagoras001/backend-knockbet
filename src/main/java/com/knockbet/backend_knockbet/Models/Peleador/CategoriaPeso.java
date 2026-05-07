package com.knockbet.backend_knockbet.Models.Peleador;

public enum CategoriaPeso {
    PESO_MOSCA,
    PESO_GALLO,
    PESO_PLUMA,
    PESO_LIGERO,
    PESO_WELTER,
    PESO_MEDIO,
    SEMIPESADO,
    PESO_PESADO;

    public static CategoriaPeso calcularTipoPeso(int pesoKg) {
        if (pesoKg <= 0) throw new IllegalArgumentException("El peso debe ser mayor que 0");

        if (pesoKg <= 57) return PESO_MOSCA;
        if (pesoKg <= 61) return PESO_GALLO;
        if (pesoKg <= 66) return PESO_PLUMA;
        if (pesoKg <= 70) return PESO_LIGERO;
        if (pesoKg <= 77) return PESO_WELTER;
        if (pesoKg <= 84) return PESO_MEDIO;
        if (pesoKg <= 93) return SEMIPESADO;
        return PESO_PESADO;
    }
}
