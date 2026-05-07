package com.knockbet.backend_knockbet.Reglas;

public class MetricasDeScore {
    // --- ACTIVIDAD ---
    // Escala temporal de inactividad (en meses).
    // Controla qué tan rápido decae el rendimiento por inactividad.
    // Es el parámetro de la función exponencial: a mayor valor → caída más lenta.
    public static final double ESCALA_INACTIVIDAD_MESES = 6.0;
}
